package com.example.pokerratingservice.util.parserassistants;

import com.example.pokerratingservice.Model.GameType;
import com.example.pokerratingservice.Model.Hand;
import com.example.pokerratingservice.Model.Player;
import com.example.pokerratingservice.Repository.PlayerRepository;
import com.example.pokerratingservice.util.enums.PokerStarsHandBlockName;
import com.example.pokerratingservice.util.HandParser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@AllArgsConstructor
@Getter
public class PokerStarsInitHandParserAssistant extends HandParserAssistant {
    private final Logger logger = LoggerFactory.getLogger(PokerStarsInitHandParserAssistant.class);
    @Override
    public void assist(String line, Hand hand, List<Player> playerList, Player player, PlayerRepository playerRepository, Set<Player> playerSet, Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap) {
        System.out.println("Assisting in: " + this.getClass().getName());
        if (line.startsWith("PokerStars")) {
            hand.setId(getHandIdValueFromLine(line));
            hand.setDate(getDateValueFromLine(line));
            hand.setGameType(getGameTypeFromLine(line));
            hand.setStake(getBigBlindValueFromLine(line));
        } else if (line.startsWith("Table")) {
            hand.setTableName(getTableNameFromLine(line));
            hand.setMaxPlayers(getMaxPLayersFromLine(line));
        } else if (line.startsWith("Seat")) {
            stringBuilderMap.get(PokerStarsHandBlockName.SEATING)
            .append(line).append("\n");

            String playerName = getPlayerNameFromLine(line);
            logger.debug("Found player in line: {}", playerName);
            Optional<Player> playerFromRepositoryById = playerRepository.findById(playerName);
            boolean playerExistsInSet = playerSet.stream().anyMatch(p -> p.getId().equals(playerName));
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
                player = playerSet.stream().filter(p -> p.getId().equals(playerName)).findAny().orElseThrow();
                playerList.add(player); // TODO check List<Hand> is null?
                logger.debug("Player {} already exists in playerHashSet.", playerName);
            }

        }
    }
    private long getHandIdValueFromLine(String line) {
        String regex = "#(\\d+)";
        String group = HandParser.getStringByRegex(line, regex, 1);
        return Long.parseLong(group);
    }
    private LocalDateTime getDateValueFromLine(String line) {
        String regex = "\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}";
        String group = HandParser.getStringByRegex(line, regex);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return LocalDateTime.parse(group, dtf);
    }
    private double getBigBlindValueFromLine(String string) {
        String regex = "\\$([0-9.]+)";
        String group = HandParser.getStringByRegex(string, regex, 1);

        return Double.parseDouble(group);
    }

    private GameType getGameTypeFromLine(String line) {
        if (line.contains("Hold'em Limit")) {
            return GameType.FL;
        } else if (line.contains("Hold'em No Limit")) {
            return GameType.NL;
        } else
            return GameType.PLO;

    }
    private String getPlayerNameFromLine(String line) {
        String regex = "Seat\\s\\d+:\\s(.*?)\\s\\(";
        return HandParser.getStringByRegex(line, regex, 1);
    }

    private int getMaxPLayersFromLine(String line) {
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
    private String getTableNameFromLine(String line) {
        String regex = "'(.*?)'";
        return HandParser.getStringByRegex(line, regex, 1);
    }
}
