package com.example.pokerratingservice.util;


import com.example.pokerratingservice.Model.GameType;
import com.example.pokerratingservice.Model.Hand;
import com.example.pokerratingservice.Model.Player;
import com.example.pokerratingservice.Repository.HandRepository;
import com.example.pokerratingservice.Repository.PlayerRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@Component
@AllArgsConstructor
@Getter
public class PokerStarsHandParser implements HandParser {
    //TODO подумать какие методы наследовать, какие сделать статическими
    private final PlayerRepository playerRepository;            //TODO нарушает ли сингл респо
    private final HandRepository handRepository;
    private static final Logger logger = LoggerFactory.getLogger(PokerStarsHandParser.class);
    private  HashSet<Player> playerHashSet;
    private  HashSet<Hand> handHashSet;



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

    public void readFile(String path) throws IOException {
        logger.debug("Reading file: {}", path);
        try (FileInputStream fis = new FileInputStream(path);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(isr)) {
            // TODO добавить проверку есть ли раздача в бд
            String line;
            String currentBlock = "INIT";
            List<Player> playerList = new ArrayList<>();
            Hand hand = new Hand();
            Player player;
            StringBuilder seatingBuilder = new StringBuilder();
            StringBuilder holeCardsBuilder = new StringBuilder();
            StringBuilder flopBuilder = new StringBuilder();
            StringBuilder turnBuilder = new StringBuilder();
            StringBuilder riverBuilder = new StringBuilder();
            StringBuilder summaryBuilder = new StringBuilder();

            while ((line = reader.readLine()) != null) {

                if (line.contains("***")) {
                    String regex = "\\*{3}\\s*(.*?)\\s*\\*{3}";
                    currentBlock = HandParser.getStringByRegex(line, regex, 1);
                } else {

                    switch (currentBlock) {
                        case "INIT" -> {
                            if (line.startsWith("PokerStars")) {
                                hand.setId(getHandIdValueFromLine(line));
                                hand.setDate(getDateValueFromLine(line));
                                hand.setGameType(getGameTypeFromLine(line));
                                hand.setStake(getBigBlindValueFromLine(line));
                            } else if (line.startsWith("Table")) {
                                hand.setTableName(getTableNameFromLine(line));
                                hand.setMaxPlayers(getMaxPLayersFromLine(line));
                            } else if (line.startsWith("Seat")) {
                                seatingBuilder.append(line).append("\n");

                                String playerName = getPlayerNameFromLine(line);
                                logger.debug("Found player in line: {}", playerName);
                                Optional<Player> playerFromRepositoryById = playerRepository.findById(playerName);
                                boolean playerExistsInSet = playerHashSet.stream().anyMatch(p -> p.getId().equals(playerName));
                                if (playerFromRepositoryById.isEmpty() && !playerExistsInSet) {
                                    player = new Player();
                                    player.setId(playerName);
                                    player.setHandList(new ArrayList<>());
                                    playerList.add(player);

                                    logger.debug("Player {} not found in db. Creating new Player entity", playerName);
                                } else if(playerFromRepositoryById.isPresent()) {
                                    player = playerFromRepositoryById.orElseThrow();
                                    playerList.add(player); // TODO check List<Hand> is null?
                                    logger.debug("Player {} already exists in db.", playerName);
                                }else {
                                    player = playerHashSet.stream().filter(p -> p.getId().equals(playerName)).findAny().orElseThrow();
                                    playerList.add(player); // TODO check List<Hand> is null?
                                    logger.debug("Player {} already exists in playerHashSet.", playerName);
                                }

                            }
                        }
                        case "HOLE CARDS" -> holeCardsBuilder.append(line).append("\n");
                        case "FLOP" -> flopBuilder.append(line).append("\n");
                        case "TURN" -> turnBuilder.append(line).append("\n");
                        case "RIVER" -> riverBuilder.append(line).append("\n");
                        case "SUMMARY" -> {
                            summaryBuilder.append(line).append("\n");

                            if (line.contains("Seat 6")) {

                                hand.setSeating(seatingBuilder.toString());
                                hand.setHoleCards(holeCardsBuilder.toString());
                                hand.setFlop(flopBuilder.toString());
                                hand.setTurn(turnBuilder.toString());
                                hand.setRiver(riverBuilder.toString());
                                hand.setSummary(summaryBuilder.toString());
                                hand.setPlayerList(new ArrayList<>());

                                for (Player p : playerList
                                ) {
                                    p.setHandList(new ArrayList<>());
                                    p.getHandList().add(hand);  //associating entities
                                    logger.debug("Adding Hand entity with ID {} to Player {}", hand.getId(), p.getId());
                                    playerHashSet.add(p);

                                }
                                logger.debug("Saving Hand entity with ID {} to the handHashSet", hand.getId());

                                handHashSet.add(hand);


                                logger.debug("Hand processing complete");





                                System.out.println(hand);
                                hand = new Hand();
                                playerList = new ArrayList<>();
                                currentBlock = "INIT";
                                clearStringBuildersBeforeNewHand(seatingBuilder, holeCardsBuilder, flopBuilder, turnBuilder, riverBuilder, summaryBuilder);


                            }
                        }


                    }

                }
            }
            logger.info("File read successfully");
        } catch (IOException e) {
            logger.error("Error reading file: {}", e.getMessage(), e);
        }


    }




    private static void clearStringBuildersBeforeNewHand(StringBuilder seatingBuilder, StringBuilder holeCardsBuilder, StringBuilder flopBuilder, StringBuilder turnBuilder, StringBuilder riverBuilder, StringBuilder summaryBuilder) {
        seatingBuilder.setLength(0);
        holeCardsBuilder.setLength(0);
        flopBuilder.setLength(0);
        turnBuilder.setLength(0);
        riverBuilder.setLength(0);
        summaryBuilder.setLength(0);
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
