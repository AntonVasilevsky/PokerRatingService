package com.example.pokerratingservice.Service;

import com.example.pokerratingservice.Model.Player;

public interface PlayerService {
    void saveOne(Player player);
    Player getById(String id);
}
