package com.example.pokerratingservice.util.parserassistant;

import com.example.pokerratingservice.model.GameType;
import com.example.pokerratingservice.model.Hand;
import com.example.pokerratingservice.model.Player;
import com.example.pokerratingservice.service.HandService;
import com.example.pokerratingservice.service.PlayerService;
import com.example.pokerratingservice.util.HandParser;
import com.example.pokerratingservice.util.enums.PokerStarsHandBlockName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@AllArgsConstructor
@Getter
public class PokerStarsInitHandParserAssistant extends HandParserAssistant {
    private final Logger logger = LoggerFactory.getLogger(PokerStarsInitHandParserAssistant.class);


    @Override
    public void assist(String line, Hand hand, List<Player> playerList, Player player, HandService handService, PlayerService playerService, Set<Player> playerSet, Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap) {
        logger.info("Assisting in: {}", this.getClass().getName());
        if (line.contains("PokerStars")) {
            hand.setId(getHandIdValueFromLine(line));
            hand.setDate(getDateValueFromLine(line));
            hand.setGameType(getGameTypeFromLine(line));
            hand.setStake(getBigBlindValueFromLine(line));
        } else if (line.startsWith("Table")) {
            hand.setTableName(getTableNameFromLine(line));
            hand.setMaxPlayer(getMaxPLayersFromLine(line));
        } else if (line.startsWith("Seat")) {
            stringBuilderMap.get(PokerStarsHandBlockName.SEATING)
                    .append(line).append("\n");
            String playerName = getPlayerNameFromLine(line);
            logger.debug("Found player in line: {}", playerName);
            Optional<Player> playerFromRepositoryById = playerService.getById(playerName);
            boolean playerExistsInSet = playerSet.stream().anyMatch(p -> p.getId().equals(playerName));
            if (playerFromRepositoryById.isEmpty() && !playerExistsInSet) {
                player = new Player();
                player.setId(playerName);
                player.setHandList(new ArrayList<>());
                playerList.add(player);
                logger.debug("Player {} not found in db. Creating new Player entity", playerName);
            } else if (playerFromRepositoryById.isPresent()) {
                player = playerFromRepositoryById.orElseThrow();
                playerList.add(player); // TODO check List<Hand> is null?
                logger.debug("Player {} already exists in db.", playerName);
            } else {
                player = playerSet.stream().filter(p -> p.getId().equals(playerName)).findAny().orElseThrow();
                playerList.add(player); // TODO check List<Hand> is null?
                logger.debug("Player {} already exists in playerHashSet.", playerName);
            }

        }
    }

    public long getHandIdValueFromLine(String line) {
        String regex = "#(\\d+)";
        String group = HandParser.getStringByRegex(line, regex, 1);
        return Long.parseLong(group);
    }

    public LocalDateTime getDateValueFromLine(String line) {
        String regex = "\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}";
        String group = HandParser.getStringByRegex(line, regex);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return LocalDateTime.parse(group, dtf);
    }

    public double getBigBlindValueFromLine(String string) {
        String regex = "\\$([0-9.]+)";
        String group = HandParser.getStringByRegex(string, regex, 1);
        return Double.parseDouble(group);
    }

    @Override
    public double getHeroPutMoneyInPotFromLine(String line) {
        return 0;
    }

    public GameType getGameTypeFromLine(String line) {
        if (line.contains("Hold'em Limit")) {
            return GameType.FL;
        } else if (line.contains("Hold'em No Limit")) {
            return GameType.NL;
        } else
            return GameType.PLO;
    }

    @Override
    public void parse(File file) throws IOException {

    }

    @Override
    public void readFiles(String path) throws IOException {

    }

    public String getPlayerNameFromLine(String line) {
        String regex = "Seat\\s\\d+:\\s(.*?)\\s\\(";
        return HandParser.getStringByRegex(line, regex, 1);
    }

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

    public String getTableNameFromLine(String line) {
        String regex = "'(.*?)'";
        return HandParser.getStringByRegex(line, regex, 1);
    }
}