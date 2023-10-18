package com.example.pokerratingservice.util.parserassistants;

import com.example.pokerratingservice.Model.Hand;
import com.example.pokerratingservice.Model.Player;
import com.example.pokerratingservice.Repository.PlayerRepository;
import com.example.pokerratingservice.util.enums.PokerStarsHandBlockName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public abstract class HandParserAssistant {
    private PokerStarsHandBlockName pokerStarsBlockNameEnum;
    public abstract void assist(String line, Hand hand, List<Player> playerList, Player player, PlayerRepository playerRepository, Set<Player> playerSet, Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap);
}


