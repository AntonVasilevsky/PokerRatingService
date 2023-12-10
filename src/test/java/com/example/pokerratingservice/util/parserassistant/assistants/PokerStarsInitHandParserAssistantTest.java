package com.example.pokerratingservice.util.parserassistant.assistants;

import com.example.pokerratingservice.dto.HandDto;
import com.example.pokerratingservice.dto.PlayerDto;
import com.example.pokerratingservice.dto.PlayerNetDto;
import com.example.pokerratingservice.model.GameType;
import com.example.pokerratingservice.service.PlayerService;
import com.example.pokerratingservice.util.enums.PokerStarsHandBlockName;
import com.example.pokerratingservice.util.parserassistant.AssistantData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class PokerStarsInitHandParserAssistantTest {
    @Mock
    PlayerService playerService;
    @InjectMocks
    private PokerStarsInitHandParserAssistant assistant;
    public static final LocalDateTime ARRANGED_DATE_TIME = LocalDateTime.parse("2023/09/06 16:19:57", DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));



    @Test
    void assist_sets_dto_line_starts_with_poker_stars() {
        //Arrange
        String line = "PokerStars Hand #245812437648:  Hold'em Limit ($2/$4 USD) - 2023/09/06 16:19:57 ET";
        long arrangeId = Long.parseLong("245812437648");
        long arrangeStake = 2;
        GameType arrangeGameType = GameType.FL;
        HandDto testHandDto = new HandDto();
        PlayerDto playerDto = null;
        AssistantData assistantData = null;

        //Act
        assistant.assist(line, testHandDto, playerDto, assistantData);
        //Assert
        Assertions.assertEquals(arrangeId, testHandDto.getId());
        Assertions.assertEquals(arrangeStake, testHandDto.getStake());
        Assertions.assertEquals(ARRANGED_DATE_TIME, testHandDto.getDate());
        Assertions.assertEquals(arrangeGameType, testHandDto.getGameType());

    }
    @Test
    void assist_sets_hand_dto_line_starts_with_table() {
        //Arrange
        String line = "Table 'Laodamia IV' 6-max Seat #1 is the button";
        int arrangedMaxPlayers = 6;
        String arrangedTableName = "Laodamia IV";
        HandDto testHandDto = new HandDto();
        PlayerDto playerDto = null;
        AssistantData assistantData = null;

        //Act
        assistant.assist(line, testHandDto, playerDto, assistantData);

        //Assert
        Assertions.assertEquals(arrangedMaxPlayers, testHandDto.getMaxPlayer());
        Assertions.assertEquals(arrangedTableName, testHandDto.getTableName());
    }
    @Test
    void assist_sets_player_to_assistant_data_line_starts_with_seat() {
        //Arrange
        String line = "Seat 1: Sharp(Gosu) ($60 in chips) ";
        String arrangedName = "Sharp(Gosu)";
        long arrangedId = 123L;


        HandDto testHandDto = new HandDto();
        testHandDto.setDate(ARRANGED_DATE_TIME);
        testHandDto.setId(arrangedId);
        PlayerDto playerDto = null;
        AssistantData assistantData = new AssistantData();
        assistantData.setPlayerNetDtoList(new ArrayList<>());
        assistantData.setStringBuilderMap(Map.of(PokerStarsHandBlockName.SEATING, new StringBuilder()));

        //Act
        assistant.assist(line, testHandDto, playerDto, assistantData);

        //Assert
        PlayerNetDto assertPlayerNetDto = assistantData.getPlayerNetDtoList().stream().findAny().orElseThrow();
        Assertions.assertEquals(arrangedId, assertPlayerNetDto.getHandId());
        Assertions.assertEquals(arrangedName, assertPlayerNetDto.getName());
        Assertions.assertEquals(ARRANGED_DATE_TIME, assertPlayerNetDto.getDate());
    }
}