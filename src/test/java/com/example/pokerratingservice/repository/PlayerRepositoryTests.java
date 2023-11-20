package com.example.pokerratingservice.repository;

import com.example.pokerratingservice.model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PlayerRepositoryTests {
    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void playerRepository_saveOne_returnsSavedPlayer() {

        Player player = Player.builder()
                .id("Tester")
                .handList(new ArrayList<>())
                .build();

        Player savedPlayer = playerRepository.save(player);

        Assertions.assertNotNull(savedPlayer);
        Assertions.assertEquals(savedPlayer.getId(), player.getId());
    }

    @Test
    public void playerRepository_saveAll_returnsListOfSavedPlayers() {

        List<Player> playerList = new ArrayList<>();
        playerList.add(Player.builder().id("p1").build());
        playerList.add(Player.builder().id("p2").build());

        List<Player> savedPlayerList = playerRepository.saveAll(playerList);

        Assertions.assertEquals(playerList, savedPlayerList);
    }
}
