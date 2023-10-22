package com.example.pokerratingservice.util.parserassistant;

import com.example.pokerratingservice.model.Hand;
import com.example.pokerratingservice.model.Player;
import com.example.pokerratingservice.service.HandService;
import com.example.pokerratingservice.service.PlayerService;
import com.example.pokerratingservice.util.enums.PokerStarsHandBlockName;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class PokerStarsTurnHandParserAssistant extends HandParserAssistant{
    @Override
    public void assist(String line, Hand hand, List<Player> playerList, Player player, HandService handService, PlayerService playerService, Set<Player> playerSet, Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap) {
        appendLineToStringBuilderFromMap(getPokerStarsBlockNameEnum(), stringBuilderMap, line);
        System.out.println("Assisting in: " + this.getClass().getName());
    }
}
