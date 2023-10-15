package com.example.pokerratingservice.util;

import com.example.pokerratingservice.Model.Hand;
import com.example.pokerratingservice.Model.Player;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public abstract class HandParserAssistant {
    private PokerStarsBlockName pokerStarsBlockNameEnum;
    abstract void assist(String line, Hand hand, StringBuilder seatingBuilder, List<Player> playerList, Player player);
}


