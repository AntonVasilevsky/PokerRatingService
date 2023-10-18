package com.example.pokerratingservice.util;


import com.example.pokerratingservice.Model.GameType;
import com.example.pokerratingservice.Model.Hand;
import com.example.pokerratingservice.Model.Player;
import com.example.pokerratingservice.Repository.HandRepository;
import com.example.pokerratingservice.Repository.PlayerRepository;
import com.example.pokerratingservice.util.parserassistants.HandParserAssistant;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Component
@Getter
public class PokerStarsHandParser implements HandParser {
    //TODO подумать какие методы наследовать, какие сделать статическими
    private final PlayerRepository playerRepository;
    private final HandRepository handRepository;
    private static final Logger logger = LoggerFactory.getLogger(PokerStarsHandParser.class);
    private final HashSet<Player> playerHashSet;
    private final HashSet<Hand> handHashSet;
    private final Map<PokerStarsHandBlockName, HandParserAssistant> assistantMap;

    public PokerStarsHandParser(PlayerRepository playerRepository, HandRepository handRepository, HashSet<Player> playerHashSet, HashSet<Hand> handHashSet, List<HandParserAssistant> assistantList) {
        this.playerRepository = playerRepository;
        this.handRepository = handRepository;
        this.playerHashSet = playerHashSet;
        this.handHashSet = handHashSet;
        this.assistantMap = assistantList.stream().collect(Collectors.toMap(HandParserAssistant::getPokerStarsBlockNameEnum, x -> x));
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
    public void parse(String path) throws IOException {
        logger.debug("Reading file: {}", path);
        try (FileInputStream fis = new FileInputStream(path);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(isr)) {
            // TODO добавить проверку есть ли раздача в бд
            String line;
            String currentBlockString = "INIT";
            PokerStarsHandBlockName currentBlock = PokerStarsHandBlockName.INIT;    //TODO сделать инициализацию методом
            List<Player> playerList = new ArrayList<>();
            Hand hand = new Hand();
            Player player = new Player();
            Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap = new HashMap<>();
            StringBuilder seatingBuilder = new StringBuilder();
            stringBuilderMap.put(PokerStarsHandBlockName.SEATING, seatingBuilder);
            StringBuilder holeCardsBuilder = new StringBuilder();
            stringBuilderMap.put(PokerStarsHandBlockName.HOLE_CARDS, holeCardsBuilder);
            StringBuilder flopBuilder = new StringBuilder();
            stringBuilderMap.put(PokerStarsHandBlockName.FLOP, flopBuilder);
            StringBuilder turnBuilder = new StringBuilder();
            stringBuilderMap.put(PokerStarsHandBlockName.TURN, turnBuilder);
            StringBuilder riverBuilder = new StringBuilder();
            stringBuilderMap.put(PokerStarsHandBlockName.RIVER, riverBuilder);
            StringBuilder summaryBuilder = new StringBuilder();
            stringBuilderMap.put(PokerStarsHandBlockName.SUMMARY, summaryBuilder);


            while ((line = reader.readLine()) != null) {

                if (line.contains("***")) {
                    String regex = "\\*{3}\\s*(.*?)\\s*\\*{3}";
                    currentBlockString = HandParser.getStringByRegex(line, regex, 1);
                    currentBlock = getCurrentBlockEnumFromString(currentBlockString);
                } else if (line.isBlank()) {
                    currentBlockString = "INIT";
                    currentBlock = getCurrentBlockEnumFromString(currentBlockString);
                    System.out.println("***********************************************");
                    hand = new Hand();
                    playerList = new ArrayList<>();
                    clearStringBuildersBeforeNewHand(stringBuilderMap);
                } else {
                    HandParserAssistant handParserAssistant = assistantMap.get(currentBlock);
                    handParserAssistant.assist(line, hand, playerList, player, playerRepository, playerHashSet, stringBuilderMap);

                }

            }
        }

        logger.info("File read successfully");

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
            default -> throw new IllegalStateException("Unexpected value: " + currentBlockString);
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
        if (line.contains("2-max")) {
            return 2;
        } else if (line.contains("6-max")) {
            return 6;
        } else if (line.contains("9-max")) {
            return 9;
        } else if (line.contains("10-max"))
            return 10;
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
