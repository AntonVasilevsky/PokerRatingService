package com.example.pokerratingservice.util.parserassistants;

import com.example.pokerratingservice.Model.Hand;
import com.example.pokerratingservice.Model.Player;
import com.example.pokerratingservice.Repository.PlayerRepository;
import com.example.pokerratingservice.util.enums.PokerStarsHandBlockName;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class PokerStarsFlopHandParserAssistant extends HandParserAssistant{
    @Override
    public void assist(String line, Hand hand, List<Player> playerList, Player player, PlayerRepository playerRepository, Set<Player> playerSet, Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap) {
        appendLineToStringBuilderFromMap(getPokerStarsBlockNameEnum(), stringBuilderMap, line);
        System.out.println("Assisting in: " + this.getClass().getName());
    }
}
