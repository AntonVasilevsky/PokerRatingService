package com.example.pokerratingservice.service;

import com.example.pokerratingservice.model.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerService {
    void saveOne(Player player);
    void saveAll(List<Player> playerList);
    Optional<Player> getById(String id);
}