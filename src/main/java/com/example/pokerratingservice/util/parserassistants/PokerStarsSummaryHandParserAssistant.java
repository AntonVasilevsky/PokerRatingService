package com.example.pokerratingservice.util.parserassistants;

import com.example.pokerratingservice.Model.Hand;
import com.example.pokerratingservice.Model.Player;
import com.example.pokerratingservice.Repository.HandRepository;
import com.example.pokerratingservice.Repository.PlayerRepository;
import com.example.pokerratingservice.util.enums.PokerStarsHandBlockName;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class PokerStarsSummaryHandParserAssistant extends HandParserAssistant{

    @Override
    public void assist(String line, Hand hand, List<Player> playerList, Player player, PlayerRepository playerRepository, HandRepository handRepository, Set<Player> playerSet, Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap) {
        stringBuilderMap.get(PokerStarsHandBlockName.SUMMARY).append(line).append("/n");
        System.out.println("Assisting in: " + this.getClass().getName());
        if (line.contains("Seat 6")) { //TODO implement for different table size


            setHandFieldsWithBlocks(hand, stringBuilderMap);
            assignAndSave(hand, playerList, playerRepository, handRepository);

            System.out.println("Hand processing complete");

        }
    }

    private static void assignAndSave(Hand hand, List<Player> playerList, PlayerRepository playerRepository, HandRepository handRepository) {
        for (Player p : playerList
        ) {
            p.getHandList().add(hand);
        }
        handRepository.save(hand);
        playerRepository.saveAll(playerList);
    }

    private void setHandFieldsWithBlocks(Hand hand, Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap) {
        stringBuilderMap.keySet().forEach(key -> {
                    switch (key) {
                        case HOLE_CARDS -> hand.setHoleCards(getStringFromStringBuilder(stringBuilderMap, key));
                        case FLOP -> hand.setFlop(getStringFromStringBuilder(stringBuilderMap, key));
                        case TURN -> hand.setTurn(getStringFromStringBuilder(stringBuilderMap, key));
                        case RIVER -> hand.setRiver(getStringFromStringBuilder(stringBuilderMap, key));
                        case SUMMARY -> hand.setSummary(getStringFromStringBuilder(stringBuilderMap, key));

                    }
                }
        );
    }

    private static String getStringFromStringBuilder(Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap, PokerStarsHandBlockName key) {
        return stringBuilderMap.get(key).toString();
    }
}


