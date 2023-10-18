package com.example.pokerratingservice.util.parserassistants;

import com.example.pokerratingservice.Model.Hand;
import com.example.pokerratingservice.Model.Player;
import com.example.pokerratingservice.Repository.PlayerRepository;
import com.example.pokerratingservice.util.PokerStarsHandBlockName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PokerStarsSummaryHandParserAssistant extends HandParserAssistant{

    @Override
    public void assist(String line, Hand hand, List<Player> playerList, Player player, PlayerRepository playerRepository, Set<Player> playerSet, Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap) {
        stringBuilderMap.get(PokerStarsHandBlockName.SUMMARY).append(line).append("/n");
        System.out.println("Assisting in: " + this.getClass().getName());
        if (line.contains("Seat 6")) { //TODO implement for different table size

            hand.setSeating(stringBuilderMap.get(PokerStarsHandBlockName.SEATING).toString());
            hand.setHoleCards(stringBuilderMap.get(PokerStarsHandBlockName.HOLE_CARDS).toString());
            hand.setFlop(stringBuilderMap.get(PokerStarsHandBlockName.FLOP).toString());
            hand.setTurn(stringBuilderMap.get(PokerStarsHandBlockName.TURN).toString());
            hand.setRiver(stringBuilderMap.get(PokerStarsHandBlockName.RIVER).toString());
            hand.setSummary(stringBuilderMap.get(PokerStarsHandBlockName.SUMMARY).toString());
            hand.setPlayerList(new ArrayList<>());
            System.out.println("Hand processing complete");


    }
}

}
