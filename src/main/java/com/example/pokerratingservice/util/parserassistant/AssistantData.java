package com.example.pokerratingservice.util.parserassistant;

import com.example.pokerratingservice.dto.PlayerDto;
import com.example.pokerratingservice.dto.PlayerNetDto;
import com.example.pokerratingservice.model.Hand;
import com.example.pokerratingservice.model.Player;
import com.example.pokerratingservice.util.enums.PokerStarsHandBlockName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.*;


@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssistantData {
    List<PlayerDto> playerDtoList;
    List<PlayerNetDto> playerNetDtoList;
    HashSet<PlayerDto> playerDtoHashSet;
    Map<Player, Void> playerMapGlobal;
    Set<Player> playerSetAssigned;
    Set<Hand> handSetAssigned;
    Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap;

    public AssistantData() {
        playerDtoList = new ArrayList<>();
        playerNetDtoList = new ArrayList<>();
        playerDtoHashSet = new HashSet<>();
        playerMapGlobal = new HashMap<>();
        playerSetAssigned = new HashSet<>();
        handSetAssigned = new HashSet<>();
        stringBuilderMap = new HashMap<>();
    }

}
