package com.example.pokerratingservice.util.handparser;


import com.example.pokerratingservice.dto.HandDto;
import com.example.pokerratingservice.dto.PlayerDto;
import com.example.pokerratingservice.model.GameType;
import com.example.pokerratingservice.model.Hand;
import com.example.pokerratingservice.model.Player;
import com.example.pokerratingservice.service.HandService;
import com.example.pokerratingservice.service.PlayerService;
import com.example.pokerratingservice.util.enums.PokerStarsHandBlockName;
import com.example.pokerratingservice.util.enums.PokerStarsKeywords;
import com.example.pokerratingservice.util.parserassistant.AssistantData;
import com.example.pokerratingservice.util.parserassistant.assistants.HandParserAssistant;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.pokerratingservice.util.enums.PokerSiteName.POKER_STARS;


@Component
@Getter
@Slf4j
public class PokerStarsHandParser extends HandParser {
    private final PlayerService playerService;
    private final HandService handService;
    private final Map<PokerStarsHandBlockName, HandParserAssistant> assistantMap;
    private final List<PokerStarsHandBlockName> handBlockNameslist;

    @Autowired
    public PokerStarsHandParser(PlayerService playerService, HandService handService, List<HandParserAssistant> assistantList) {
        super(POKER_STARS);
        this.playerService = playerService;
        this.handService = handService;
        this.assistantMap = assistantList.stream().collect(Collectors.toMap(HandParserAssistant::getPokerStarsBlockNameEnum, x -> x));
        this.handBlockNameslist = List.of(PokerStarsHandBlockName.values());
    }


    private double getAmountWon(String hand, String bet, String raise, String call, String bigBlind, String smallBlind, String totalPotString) throws IOException {
        boolean won = false;
        String playerName = "text"; // TODO get this value from file
        double ammountWon = 0;
        BufferedReader bufferedReader = new BufferedReader(new StringReader(hand));
        String text = bufferedReader.readLine();
        double stakeFromHand = getBigBlindValueFromLine(text);
        double putMoneyInPot = 0;
        double totalPotValue = 0;
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.contains(playerName) && line.contains(bigBlind)) {
                putMoneyInPot += stakeFromHand;
            }
            if (line.contains(playerName) && line.contains(smallBlind)) {
                putMoneyInPot += stakeFromHand / 2;
            }
            if (line.contains(bet) || line.contains(raise) || line.contains(call)) {
                putMoneyInPot += getHeroPutMoneyInPotFromLine(line);
            }
            if (line.contains(playerName) && line.contains(PokerStarsKeywords.WON.toString())) {
                won = true;
            }
            if (line.contains(totalPotString)) {
                totalPotValue += getHeroPutMoneyInPotFromLine(line);
            }
        }
        if (won) {
            ammountWon += (totalPotValue - putMoneyInPot);
        } else {
            ammountWon -= putMoneyInPot;
        }
        won = false;
        return ammountWon;
    }


    @Override
    public double getHeroPutMoneyInPotFromLine(String line) {
        String regex = "\\$([0-9.]+)";
        String valueAfterDollar = HandParser.getStringByRegex(line, regex, 1);

        return Double.parseDouble(valueAfterDollar);
    }




    @Override
    public AssignedData parse(BufferedReader reader) throws IOException {

        AssistantData assistantData = new AssistantData();

        String line;
        String currentBlockString = "INIT";
        PokerStarsHandBlockName currentBlock = getCurrentBlockEnumFromString(currentBlockString);
        Hand hand = new Hand();
        HandDto handDto = new HandDto();
        PlayerDto playerDto = new PlayerDto();

        handBlockNameslist.forEach(blockName -> assistantData.getStringBuilderMap().put(blockName, new StringBuilder()));
        int emptyRowCounter = 0;
        while ((line = reader.readLine()) != null) {
            if (line.contains("***")) {
                String regex = "\\*{3}\\s*(.*?)\\s*\\*{3}";
                currentBlockString = HandParser.getStringByRegex(line, regex, 1);
                currentBlock = getCurrentBlockEnumFromString(currentBlockString);
            } else if (!line.isBlank()) {
                HandParserAssistant handParserAssistant = assistantMap.get(currentBlock);
                handParserAssistant.assist(line, handDto, playerDto, assistantData);
            } else {
                if (emptyRowCounter == 0) {
                    currentBlockString = "INIT";
                    currentBlock = getCurrentBlockEnumFromString(currentBlockString);
                    clearStringBuildersBeforeNewHand(assistantData.getStringBuilderMap());
                    System.out.println(hand);
                    hand = new Hand();
                    assistantData.setPlayerDtoList(new ArrayList<>());
                    emptyRowCounter++;
                } else if (emptyRowCounter < 3) {
                    emptyRowCounter++;
                }
                if (emptyRowCounter == 3) {
                    emptyRowCounter = 0;
                }
            }
        }
        Set<Player> playerSetAssigned = assistantData.getPlayerSetAssigned();
        Set<Hand> handSetAssigned = assistantData.getHandSetAssigned();
        AssignedData assignedData = new AssignedData(playerSetAssigned, handSetAssigned);
        return assignedData;
    }


    private PokerStarsHandBlockName getCurrentBlockEnumFromString(String currentBlockString) {
        return switch (currentBlockString) {
            case "INIT" -> PokerStarsHandBlockName.INIT;
            case "HOLE CARDS" -> PokerStarsHandBlockName.HOLE_CARDS;
            case "FLOP" -> PokerStarsHandBlockName.FLOP;
            case "TURN" -> PokerStarsHandBlockName.TURN;
            case "RIVER" -> PokerStarsHandBlockName.RIVER;
            case "SHOW DOWN" -> PokerStarsHandBlockName.SHOW_DOWN;
            case "SUMMARY" -> PokerStarsHandBlockName.SUMMARY;
            default -> throw new IllegalStateException("Unexpected hand block value: " + currentBlockString);
        };
    }

    private static void clearStringBuildersBeforeNewHand(Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap) {
        stringBuilderMap.forEach((key, value) -> value.setLength(0));
    }

    @Override
    public String getPlayerNameFromLine(String line) {
        String regex = "Seat\\s\\d+:\\s(.*?)\\s\\(";
        return HandParser.getStringByRegex(line, regex, 1);
    }

    @Override
    public int getMaxPLayersFromLine(String line) {
        return -1;
    }

    @Override
    public String getTableNameFromLine(String line) {
        String regex = "'(.*?)'";
        return HandParser.getStringByRegex(line, regex, 1);
    }

    @Override
    public LocalDateTime getDateValueFromLine(String line) {
        String regex = "\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}";
        String group = HandParser.getStringByRegex(line, regex);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return LocalDateTime.parse(group, dtf);
    }

    @Override
    public double getBigBlindValueFromLine(String string) {
        String regex = "\\$([0-9.]+)";
        String group = HandParser.getStringByRegex(string, regex, 1);

        return Double.parseDouble(group);
    }

    @Override
    public long getHandIdValueFromLine(String line) {
        String regex = "#(\\d+)";
        String group = HandParser.getStringByRegex(line, regex, 1);
        return Long.parseLong(group);
    }

    @Override
    public GameType getGameTypeFromLine(String line) {
        if (line.contains("Hold'em Limit")) {
            return GameType.FL;
        } else if (line.contains("Hold'em No Limit")) {
            return GameType.NL;
        } else
            return GameType.PLO;
    }

}
