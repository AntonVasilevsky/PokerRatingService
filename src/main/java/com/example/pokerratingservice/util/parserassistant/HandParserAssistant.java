package com.example.pokerratingservice.util.parserassistant;

import com.example.pokerratingservice.dto.HandDto;
import com.example.pokerratingservice.dto.PlayerDto;
import com.example.pokerratingservice.model.Hand;
import com.example.pokerratingservice.model.Player;
import com.example.pokerratingservice.service.HandService;
import com.example.pokerratingservice.service.PlayerService;
import com.example.pokerratingservice.util.enums.PokerStarsHandBlockName;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter

public abstract class HandParserAssistant  {
    private PokerStarsHandBlockName pokerStarsBlockNameEnum;
    private PlayerService playerService;
    private HandService handService;

    public abstract void assist(String line, HandDto hand, List<PlayerDto> playerList, PlayerDto player, HandService handService, PlayerService playerService, HashSet<PlayerDto> playerSet, Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap, List<HandDto> globalHandDtoList, Map<PlayerDto, Void> globalPlayerDtoList);
    public void appendLineToStringBuilderFromMap(PokerStarsHandBlockName blockName, Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap, String line) {
        stringBuilderMap.get(blockName).append(line).append("\n");
    }


    public abstract void assist(String line, HandDto handDto, List<PlayerDto> playerDtoList, PlayerDto playerDto, HandService handService, PlayerService playerService,
                                HashSet<PlayerDto> playerDtoHashSet, Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap, List<HandDto> handDtoListGlobal,
                                Map<PlayerDto, Void> playerDtoMapGlobal, List<Hand> handListGlobal, Map<Player, Void> playerMapGlobal,
                                Set<Player> playerSetAssigned, Set<Hand> handSetAssigned);
}


