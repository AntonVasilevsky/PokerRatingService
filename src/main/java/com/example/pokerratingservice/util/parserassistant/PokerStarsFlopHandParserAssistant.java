package com.example.pokerratingservice.util.parserassistant;

import com.example.pokerratingservice.dto.HandDto;
import com.example.pokerratingservice.dto.PlayerDto;
import com.example.pokerratingservice.model.Hand;
import com.example.pokerratingservice.model.Player;
import com.example.pokerratingservice.service.HandService;
import com.example.pokerratingservice.service.PlayerService;
import com.example.pokerratingservice.util.enums.PokerStarsHandBlockName;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PokerStarsFlopHandParserAssistant extends HandParserAssistant{

    @Override
    public void assist(String line, HandDto handDto, List<PlayerDto> playerDtoList, PlayerDto playerDto, HandService handService, PlayerService playerService,
                       HashSet<PlayerDto> playerDtoHashSet, Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap, Map<Player, Void> playerMapGlobal,
                       Set<Player> playerSetAssigned, Set<Hand> handSetAssigned) {
        appendLineToStringBuilderFromMap(getPokerStarsBlockNameEnum(), stringBuilderMap, line);
        System.out.println("Assisting in: " + this.getClass().getName());
    }

    @Override
    public void assist(String line, HandDto hand, List<PlayerDto> playerList, PlayerDto player, HandService handService, PlayerService playerService, HashSet<PlayerDto> playerSet, Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap, List<HandDto> globalHandDtoList, Map<PlayerDto, Void> globalPlayerDtoList) {
        appendLineToStringBuilderFromMap(getPokerStarsBlockNameEnum(), stringBuilderMap, line);
        System.out.println("Assisting in: " + this.getClass().getName());
    }
}
