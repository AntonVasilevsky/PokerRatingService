package com.example.pokerratingservice.util.parserassistant.assistants;

import com.example.pokerratingservice.dto.HandDto;
import com.example.pokerratingservice.dto.PlayerDto;
import com.example.pokerratingservice.dto.PlayerNetDto;
import com.example.pokerratingservice.model.GameType;
import com.example.pokerratingservice.model.Player;
import com.example.pokerratingservice.service.PlayerService;
import com.example.pokerratingservice.util.enums.PokerStarsHandBlockName;
import com.example.pokerratingservice.util.handparser.HandParser;
import com.example.pokerratingservice.util.parserassistant.AssistantData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;


@Getter
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PokerStarsInitHandParserAssistant extends HandParserAssistant {
    PlayerService playerService;



    public PokerStarsInitHandParserAssistant(PlayerService playerService) {
        this.playerService = playerService;
    }


    @Override
    public void assist(String line, HandDto handDto, PlayerDto playerDto, AssistantData assistantData) {
        log.info("Assisting in: {}", this.getClass().getName());
       // PlayerNetDto playerNetDto = new PlayerNetDto();
        if (line.contains("PokerStars")) {
            handDto.setId(getHandIdValueFromLine(line));
            handDto.setDate(getDateValueFromLine(line));
            handDto.setGameType(getGameTypeFromLine(line));
            handDto.setStake(getPlayerPutMoneyInPotFromLine(line));
        } else if (line.startsWith("Table")) {
            handDto.setTableName(getTableNameFromLine(line));
            handDto.setMaxPlayer(getMaxPLayersFromLine(line));
        } else if (line.startsWith("Seat")) {
            PlayerNetDto playerNetDto = new PlayerNetDto();
            assistantData.getStringBuilderMap().get(PokerStarsHandBlockName.SEATING)
                    .append(line).append("\n");
            String playerName = getPlayerNameFromLine(line);

            playerNetDto.setName(playerName);
            playerNetDto.setHandId(handDto.getId());
            playerNetDto.setDate(handDto.getDate());

            assistantData.getPlayerNetDtoList().add(playerNetDto);
            log.debug("Found player in line: {}", playerName);

            processPlayerDto(assistantData, playerName);

        } else if (line.contains("small blind")) {
            String name = getPlayerNameOnAction(line);
            double blind = handDto.getStake()/2 * -1;
            setAmountVpipToPlayerNetDto(assistantData, name, blind);
        } else if (line.contains("big blind")) {
            String name = getPlayerNameOnAction(line);
            double blind = handDto.getStake() * -1;
            setAmountVpipToPlayerNetDto(assistantData, name, blind);
        }
    }

    private void processPlayerDto(AssistantData assistantData, String playerName) {
        Optional<Player> playerFromRepositoryById = playerService.getById(playerName); //  лишнее ????
        boolean playerExistsInSet = assistantData.getPlayerMapGlobal().keySet().stream().anyMatch(p -> p.getId().equals(playerName));
        PlayerDto playerDto;
        if (playerFromRepositoryById.isEmpty() && !playerExistsInSet) {
            playerDto = new PlayerDto();
            playerDto.setId(playerName);
            playerDto.setHandDtoList(new ArrayList<>());
            addPlayerDtoToList(playerDto, assistantData);
            log.debug("Player {} not found in db. Creating new Player entity", playerName);
        } else if (playerFromRepositoryById.isPresent()) {
            playerDto = playerService.convertPlayerToDto(playerFromRepositoryById.orElseThrow());
            addPlayerDtoToList(playerDto, assistantData);
            log.debug("Player {} already exists in db.", playerName);
        } else {
            playerDto = assistantData.getPlayerDtoHashSet().stream().filter(p -> p.getId().equals(playerName)).findAny().orElseThrow();
            addPlayerDtoToList(playerDto, assistantData);
            log.debug("Player {} already exists in playerHashSet.", playerName);
        }
    }

    private static void addPlayerDtoToList(PlayerDto playerDto, AssistantData assistantData) {
        assistantData.getPlayerDtoList().add(playerDto);
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

    @Override
    public PokerStarsHandBlockName getPokerStarsBlockNameEnum() {
        return PokerStarsHandBlockName.INIT;
    }
}
