package com.example.pokerratingservice.util.parserassistant;

import com.example.pokerratingservice.dto.HandDto;
import com.example.pokerratingservice.dto.PlayerDto;
import com.example.pokerratingservice.model.Hand;
import com.example.pokerratingservice.model.Player;
import com.example.pokerratingservice.service.HandService;
import com.example.pokerratingservice.service.PlayerService;
import com.example.pokerratingservice.util.enums.PokerStarsHandBlockName;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
@Component
public class PokerStarsSummaryHandParserAssistant extends HandParserAssistant{

    private final PlayerService playerService;

    private final HandService handService;

    public PokerStarsSummaryHandParserAssistant(PlayerService playerService, HandService handService) {
        super.setPokerStarsBlockNameEnum(PokerStarsHandBlockName.SUMMARY);
        this.playerService = playerService;
        this.handService = handService;
    }

    @Override
    public void assist(String line, HandDto handDto, List<PlayerDto> playerDtoList, PlayerDto playerDto, HandService handService, PlayerService playerService, HashSet<PlayerDto> playerDtoHashSet, Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap) {
        stringBuilderMap.get(PokerStarsHandBlockName.SUMMARY).append(line).append("/n");
        System.out.println("Assisting in: " + this.getClass().getName());
        if (line.contains("Seat 6")) { //TODO implement for different table size

            setHandDtoFieldsWithBlocks(handDto, stringBuilderMap);
            List<Player> playerList = playerDtoList.stream().map(playerService::convertDtoToPLayer).toList();

            playerList.forEach(player -> player.setHandList(new ArrayList<>()));


            Hand hand = handService.covertDtoToHand(handDto);
            hand.setPlayerList(new ArrayList<>());
            assignAndSave(hand, playerList, playerService, handService);

            System.out.println("Hand processing complete");

        }
    }

    private static void assignAndSave(Hand hand, List<Player> playerList, PlayerService playerService, HandService handService) {
        for (Player p : playerList
        ) {
            p.getHandList().add(hand);
        }
        handService.saveOne(hand);
        playerService.saveAll(playerList);
    }

    private void setHandDtoFieldsWithBlocks(HandDto hand, Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap) {
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


