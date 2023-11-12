package com.example.pokerratingservice.util.parserassistant.assistants;

import com.example.pokerratingservice.dto.HandDto;
import com.example.pokerratingservice.dto.PlayerDto;
import com.example.pokerratingservice.model.GameType;
import com.example.pokerratingservice.model.Player;
import com.example.pokerratingservice.service.HandService;
import com.example.pokerratingservice.service.PlayerService;
import com.example.pokerratingservice.util.enums.PokerStarsHandBlockName;
import com.example.pokerratingservice.util.handparser.HandParser;
import com.example.pokerratingservice.util.parserassistant.AssistantData;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Getter
@Component
public class PokerStarsInitHandParserAssistant extends HandParserAssistant {
    private final Logger logger = LoggerFactory.getLogger(PokerStarsInitHandParserAssistant.class);
    private final PlayerService playerService;

    public PokerStarsInitHandParserAssistant(PlayerService playerService, HandService handService) {
        this.playerService = playerService;
        super.setPokerStarsBlockNameEnum(PokerStarsHandBlockName.INIT);
    }


    @Override
    public void assist(String line, HandDto handDto, List<PlayerDto> playerDtoList, PlayerDto playerDto,
                       Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap, AssistantData assistantData) {
        logger.info("Assisting in: {}", this.getClass().getName());
        if (line.contains("PokerStars")) {
            handDto.setId(getHandIdValueFromLine(line));
            handDto.setDate(getDateValueFromLine(line));
            handDto.setGameType(getGameTypeFromLine(line));
            handDto.setStake(getBigBlindValueFromLine(line));
        } else if (line.startsWith("Table")) {
            handDto.setTableName(getTableNameFromLine(line));
            handDto.setMaxPlayer(getMaxPLayersFromLine(line));
        } else if (line.startsWith("Seat")) {
            stringBuilderMap.get(PokerStarsHandBlockName.SEATING)
                    .append(line).append("\n");
            String playerName = getPlayerNameFromLine(line);
            logger.debug("Found player in line: {}", playerName);
            Optional<Player> playerFromRepositoryById = playerService.getById(playerName); //  лишнее ????
            boolean playerExistsInSet = assistantData.getPlayerMapGlobal().keySet().stream().anyMatch(p -> p.getId().equals(playerName));
            if (playerFromRepositoryById.isEmpty() && !playerExistsInSet) {
                playerDto = new PlayerDto();
                playerDto.setId(playerName);
                playerDto.setHandDtoList(new ArrayList<>());
                playerDtoList.add(playerDto);
                logger.debug("Player {} not found in db. Creating new Player entity", playerName);
            } else if (playerFromRepositoryById.isPresent()) {
                playerDto = playerService.convertPlayerToDto(playerFromRepositoryById.orElseThrow());

              //  playerDtoList.add(playerDto); // TODO check List<Hand> is nullssssssssssssssssssssssssssssssssssssss
                assistantData.getPlayerDtoList().add(playerDto);
                logger.debug("Player {} already exists in db.", playerName);
            } else {
                playerDto = assistantData.getPlayerDtoHashSet().stream().filter(p -> p.getId().equals(playerName)).findAny().orElseThrow();
             //   playerDtoList.add(playerDto); // TODO check List<Hand> is null?sssssssssssssssssssssssssss
                assistantData.getPlayerDtoList().add(playerDto);
                logger.debug("Player {} already exists in playerHashSet.", playerName);
            }

        }
    }

    @Override
    public void assist(String line, HandDto handDto, List<PlayerDto> playerDtoList, PlayerDto playerDto, HandService handService, PlayerService playerService, HashSet<PlayerDto> playerSet,
                       Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap, List<HandDto> globalHandDtoList, Map<PlayerDto, Void> globalPlayerDtoMap) {
        logger.info("Assisting in: {}", this.getClass().getName());
        if (line.contains("PokerStars")) {
            handDto.setId(getHandIdValueFromLine(line));
            handDto.setDate(getDateValueFromLine(line));
            handDto.setGameType(getGameTypeFromLine(line));
            handDto.setStake(getBigBlindValueFromLine(line));
        } else if (line.startsWith("Table")) {
            handDto.setTableName(getTableNameFromLine(line));
            handDto.setMaxPlayer(getMaxPLayersFromLine(line));
        } else if (line.startsWith("Seat")) {
            stringBuilderMap.get(PokerStarsHandBlockName.SEATING)
                    .append(line).append("\n");
            String playerName = getPlayerNameFromLine(line);
            logger.debug("Found player in line: {}", playerName);
            Optional<Player> playerFromRepositoryById = playerService.getById(playerName); //  лишнее ????
            boolean playerExistsInSet = playerSet.stream().anyMatch(p -> p.getId().equals(playerName));
            if (playerFromRepositoryById.isEmpty() && !playerExistsInSet) {
                playerDto = new PlayerDto();
                playerDto.setId(playerName);
                playerDto.setHandDtoList(new ArrayList<>());
                playerDtoList.add(playerDto);
                logger.debug("Player {} not found in db. Creating new Player entity", playerName);
            } else if (playerFromRepositoryById.isPresent()) {
                playerDto = playerService.convertPlayerToDto(playerFromRepositoryById.orElseThrow());

                playerDtoList.add(playerDto); // TODO check List<Hand> is null?
                logger.debug("Player {} already exists in db.", playerName);
            } else {
                playerDto = globalPlayerDtoMap.keySet().stream().filter(p -> p.getId().equals(playerName)).findAny().orElseThrow();
                playerDtoList.add(playerDto); // TODO check List<Hand> is null?
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


    public void parse(File file) throws IOException {

    }


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
