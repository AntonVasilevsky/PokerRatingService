package com.example.pokerratingservice.util.parserassistant.assistants;

import com.example.pokerratingservice.dto.HandDto;
import com.example.pokerratingservice.dto.PlayerDto;
import com.example.pokerratingservice.service.HandService;
import com.example.pokerratingservice.service.PlayerService;
import com.example.pokerratingservice.util.enums.PokerStarsHandBlockName;
import com.example.pokerratingservice.util.enums.PokerStarsKeywords;
import com.example.pokerratingservice.util.handparser.HandParser;
import com.example.pokerratingservice.util.parserassistant.AssistantData;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter

public abstract class HandParserAssistant  {
    private PokerStarsHandBlockName pokerStarsBlockNameEnum;
    private PlayerService playerService;
    private HandService handService;

    public void appendLineToStringBuilderFromMap(PokerStarsHandBlockName blockName, Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap, String line) {
        stringBuilderMap.get(blockName).append(line).append("\n");
    }


    public abstract void assist(String line, HandDto handDto, PlayerDto playerDto, AssistantData assistantData);
    public String getPlayerNameOnAction(String line) {
        String regex = "^(.*?):";
        return HandParser.getStringByRegex(line, regex, 1);
    }
    public void setAmountVpipToPersonNetDto(AssistantData assistantData, String name, Double blind) {
        assistantData.getPlayerNetDtoList().stream().filter(p -> p.getId().equals(name)).findFirst().orElseThrow().setVpip(blind);
    }
    public double getPlayerPutMoneyInPotFromLine(String line) {
        String regex = "\\$([0-9.]+)";
        String valueAfterDollar = HandParser.getStringByRegex(line, regex, 1);
        return Double.parseDouble(valueAfterDollar);
    }
    public void processPersonNetDto(AssistantData assistantData, String line) {
        if(line.contains(PokerStarsKeywords.RAISE.getValue()) || line.contains(PokerStarsKeywords.CALL.getValue())
                || line.contains(PokerStarsKeywords.BET.getValue())) {
            String playerNameOnAction = getPlayerNameOnAction(line);
            double ammountVpip = getPlayerPutMoneyInPotFromLine(line);
            setAmountVpipToPersonNetDto(assistantData, playerNameOnAction, ammountVpip);
        }
    }
}


