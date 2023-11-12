package com.example.pokerratingservice.util.parserassistant;

import com.example.pokerratingservice.dto.PlayerDto;
import com.example.pokerratingservice.model.Hand;
import com.example.pokerratingservice.model.Player;
import com.example.pokerratingservice.util.enums.PokerStarsHandBlockName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;


@Getter
@Setter
@AllArgsConstructor
public class AssistantData {
    private List<PlayerDto> playerDtoList;
    private HashSet<PlayerDto> playerDtoHashSet;
    private Map<Player, Void> playerMapGlobal;
    private Set<Player> playerSetAssigned;
    private Set<Hand> handSetAssigned;
    private Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap;

    public AssistantData() {
        playerDtoList = new ArrayList<>();
        playerDtoHashSet = new HashSet<>();
        playerMapGlobal = new HashMap<>();
        playerSetAssigned = new HashSet<>();
        handSetAssigned = new HashSet<>();
        stringBuilderMap = new HashMap<>();
    }

}
