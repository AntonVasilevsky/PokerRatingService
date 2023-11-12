package com.example.pokerratingservice.util.parserassistant;

import com.example.pokerratingservice.dto.PlayerDto;
import com.example.pokerratingservice.model.Hand;
import com.example.pokerratingservice.model.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;


@Getter
@Setter
@AllArgsConstructor
public class AssistantData {
    private HashSet<PlayerDto> playerDtoHashSet;
    private Map<Player, Void> playerMapGlobal;
    private Set<Player> playerSetAssigned;
    private Set<Hand> handSetAssigned;

    public AssistantData() {
        playerDtoHashSet = new HashSet<>();
        playerMapGlobal = new HashMap<>();
        playerSetAssigned = new HashSet<>();
        handSetAssigned = new HashSet<>();
    }

}
