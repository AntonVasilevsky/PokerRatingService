package com.example.pokerratingservice.util;

import com.example.pokerratingservice.Model.Hand;
import com.example.pokerratingservice.Model.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Getter
public class PokerStarsHandParserAssistant extends HandParserAssistant{
    @Override
    void assist(String line, Hand hand, StringBuilder seatingBuilder, List<Player> playerList, Player player) {

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
}
