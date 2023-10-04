package com.example.pokerratingservice.Service;

import com.example.pokerratingservice.Model.Hand;
import com.example.pokerratingservice.Model.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class DatabaseHandler {
    private final PlayerService playerService;
    private final HandService handService;


    public void saveEntities(HashSet<Player> players, HashSet<Hand>hands) {
        for (Hand hand : hands) {
            handService.saveOne(hand);
        }
        for (Player player : players
             ) {
            playerService.saveOne(player);
        }
    }
}
