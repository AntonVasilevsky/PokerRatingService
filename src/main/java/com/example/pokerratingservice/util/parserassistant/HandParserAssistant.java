package com.example.pokerratingservice.util.parserassistant;

import com.example.pokerratingservice.model.Hand;
import com.example.pokerratingservice.model.Player;
import com.example.pokerratingservice.service.HandService;
import com.example.pokerratingservice.service.PlayerService;
import com.example.pokerratingservice.util.enums.PokerStarsHandBlockName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter

public abstract class HandParserAssistant  {
    private PokerStarsHandBlockName pokerStarsBlockNameEnum;

    public abstract void assist(String line, Hand hand, List<Player> playerList, Player player, HandService handService, PlayerService playerService,  Set<Player> playerSet, Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap);
    public void appendLineToStringBuilderFromMap(PokerStarsHandBlockName blockName, Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap, String line) {
        stringBuilderMap.get(blockName).append(line).append("\n");
    }





}


