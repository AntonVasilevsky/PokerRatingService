package com.example.pokerratingservice.util.parserassistant.assistants;

import com.example.pokerratingservice.dto.PlayerNetDto;
import com.example.pokerratingservice.util.parserassistant.AssistantData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.ArrayList;
import java.util.List;

class HandParserAssistantTest {
    @InjectMocks
    private HandParserAssistant assistant = new PokerStarsFlopHandParserAssistant();


   



    @Test
    void returns_player_name_on_action() {
        //Arrange
        String line = "CharlesMouse: posts big blind $2";
        String arrangedName = "CharlesMouse";
        //Act
        String expectedPlayerName = assistant.getPlayerNameOnAction(line);
        //Assert
        Assertions.assertEquals(arrangedName, expectedPlayerName);
    }

    @Test
    void set_amount_vpip_to_playerNetDto_correct() {
        //Arrange
        String name = "test";
        double expextedVpip = 4.0;
        double vpip = 2.0;
        AssistantData assistantData = new AssistantData();
        PlayerNetDto playerNetDto = new PlayerNetDto();
        playerNetDto.setName("test");
        playerNetDto.setVpip(2.0);
        assistantData.setPlayerNetDtoList(new ArrayList<>(List.of(playerNetDto)));

        //Act
        assistant.setAmountVpipToPlayerNetDto(assistantData, name, vpip);
        //Assert
        Assertions.assertEquals(expextedVpip, playerNetDto.getVpip());
    }


    @Test
    void get_player_put_money_in_pot_from_line() {
        //Arrange
        String line = "Sharp(Gosu): raises $2 to $4";
        double expected = 2.0;
        //Act
        double actual = assistant.getPlayerPutMoneyInPotFromLine(line);
        //Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void get_correct_winner_name() {
        //Arrange
        String line = "Sharp(Gosu) collected $35.50 from pot";
        String expectedWinnerName = "Sharp(Gosu)";
        //Act
        String actualWinnerName = assistant.getWinnerName(line);
        //Assert
        Assertions.assertEquals(expectedWinnerName, actualWinnerName);
    }

    @Test
    void get_total_pot() {
        //Arrange
        String line = "Sharp(Gosu) collected $35.50 from pot";
        double expectedPot = 35.5;
        //Act
        double actualPot = assistant.getTotalPot(line);
        //Assert
        Assertions.assertEquals(expectedPot, actualPot);
    }


    @Test
    void process_winner() {
        //Arrange
        String line = "Sharp(Gosu) collected $35.50 from pot";
        double expectedWon = 40;
        AssistantData assistantData = new AssistantData();
        PlayerNetDto playerNetDto = new PlayerNetDto();
        playerNetDto.setName("Sharp(Gosu)");
        playerNetDto.setVpip(4.5);
        assistantData.setPlayerNetDtoList(new ArrayList<>(List.of(playerNetDto)));
        //Act
        assistant.processWinner(line, assistantData);
        //Assert
        Assertions.assertTrue(playerNetDto.isWon());
        Assertions.assertEquals(expectedWon, playerNetDto.getWonPerHand());
    }
}