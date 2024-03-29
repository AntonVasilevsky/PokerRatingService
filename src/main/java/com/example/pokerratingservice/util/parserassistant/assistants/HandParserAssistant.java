package com.example.pokerratingservice.util.parserassistant.assistants;

import com.example.pokerratingservice.dto.HandDto;
import com.example.pokerratingservice.dto.PlayerDto;
import com.example.pokerratingservice.service.PlayerNetService;
import com.example.pokerratingservice.util.enums.PokerStarsHandBlockName;
import com.example.pokerratingservice.util.enums.PokerStarsKeywords;
import com.example.pokerratingservice.util.handparser.HandParser;
import com.example.pokerratingservice.util.parserassistant.AssistantData;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Getter
@Setter

public abstract class HandParserAssistant {
  //  private PokerStarsHandBlockName pokerStarsBlockNameEnum;

    private PlayerNetService playerNetService;

    public HandParserAssistant() {
    }
    @Autowired
    public HandParserAssistant(PlayerNetService playerNetService) {
        this.playerNetService = playerNetService;
    }

    public void appendLineToStringBuilderFromMap(PokerStarsHandBlockName blockName, Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap, String line) {
        stringBuilderMap.get(blockName).append(line).append("\n");
    }


    public abstract void assist(String line, HandDto handDto, PlayerDto playerDto, AssistantData assistantData);

    public String getPlayerNameOnAction(String line) {
        String regex = "^(.*?):";
        return HandParser.getStringByRegex(line, regex, 1);
    }

    public void setAmountVpipToPlayerNetDto(AssistantData assistantData, String name, double vpip) {
        assistantData.getPlayerNetDtoList().forEach(playerNetDto -> {
            if (playerNetDto.getName().equals(name)) {
                playerNetDto.setVpip(playerNetDto.getVpip() + vpip);
            }
        });
    }

    public double getPlayerPutMoneyInPotFromLine(String line) {
        String regex = "\\$([0-9.]+)";
        String valueAfterDollar = HandParser.getStringByRegex(line, regex, 1);
        return Double.parseDouble(valueAfterDollar);
    }

    public void processPersonNetDto(AssistantData assistantData, String line) {
        if (line.contains(PokerStarsKeywords.RAISE.getValue()) || line.contains(PokerStarsKeywords.CALL.getValue())
                || line.contains(PokerStarsKeywords.BET.getValue())) {
            String playerNameOnAction = getPlayerNameOnAction(line);
            double amountVpip = getPlayerPutMoneyInPotFromLine(line) * -1;
            setAmountVpipToPlayerNetDto(assistantData, playerNameOnAction, amountVpip);
        }
        processUncalledBet(line, assistantData);
        processWinner(line, assistantData);
    }

    private void processLoser(AssistantData assistantData) {
        assistantData.getPlayerNetDtoList().forEach(playerNetDto -> {
            if (!playerNetDto.isWon()) {
                playerNetDto.setWonPerHand(playerNetDto.getVpip());
            }
        });
    }

    private void processUncalledBet(String line, AssistantData assistantData) {
        if (line.contains("Uncalled bet")) {
            String regexpName = "\\bto\\s+([^\\s]+)";
            String uncalledName = HandParser.getStringByRegex(line, regexpName, 1);
            String regexpBet = "\\$([\\d.]+)";
            String stringUncalledBet = HandParser.getStringByRegex(line, regexpBet, 1);
            double uncalledBet = Double.parseDouble(stringUncalledBet);
            setAmountVpipToPlayerNetDto(assistantData, uncalledName, uncalledBet);
        }
    }

    public String getWinnerName(String line) {
        String regex = "^(.*?) ";
        return HandParser.getStringByRegex(line, regex, 1);
    }

    public double getTotalPot(String line) {
        String regex = "\\$(\\d+(\\.\\d+)?)";
        String stringByRegex = HandParser.getStringByRegex(line, regex, 1);
        return Double.parseDouble(stringByRegex);
    }

    public void processWinner(String line, AssistantData assistantData) {
        if (line.contains("collected")) {
            double totalPot = getTotalPot(line); //TODO process returned bet after bet fold
            String winnerName = getWinnerName(line);
            assistantData.getPlayerNetDtoList().forEach(playerNetDto -> {
                if (playerNetDto.getName().equals(winnerName)) {
                    playerNetDto.setWon(true);
                    playerNetDto.setWonPerHand(totalPot + playerNetDto.getVpip());
                }
            });
            processLoser(assistantData);
        }
    }

    public abstract PokerStarsHandBlockName getPokerStarsBlockNameEnum();
}


