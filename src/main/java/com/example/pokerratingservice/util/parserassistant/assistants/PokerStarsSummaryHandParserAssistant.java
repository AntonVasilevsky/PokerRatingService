package com.example.pokerratingservice.util.parserassistant.assistants;

import com.example.pokerratingservice.dto.HandDto;
import com.example.pokerratingservice.dto.PlayerDto;
import com.example.pokerratingservice.model.Hand;
import com.example.pokerratingservice.model.Player;
import com.example.pokerratingservice.model.PlayerNet;
import com.example.pokerratingservice.service.HandService;
import com.example.pokerratingservice.service.PlayerNetService;
import com.example.pokerratingservice.service.PlayerService;
import com.example.pokerratingservice.util.enums.PokerStarsHandBlockName;
import com.example.pokerratingservice.util.parserassistant.AssistantData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class PokerStarsSummaryHandParserAssistant extends HandParserAssistant{

    private final PlayerService playerService;

    private final HandService handService;
    private final PlayerNetService playerNetService;

    public PokerStarsSummaryHandParserAssistant(PlayerService playerService, HandService handService, PlayerNetService playerNetService) {
        this.playerNetService = playerNetService;
        super.setPokerStarsBlockNameEnum(PokerStarsHandBlockName.SUMMARY);
        this.playerService = playerService;
        this.handService = handService;
    }



    @Override
    public void assist(String line, HandDto handDto, PlayerDto playerDto, AssistantData assistantData) {
        Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap = assistantData.getStringBuilderMap();
        stringBuilderMap.get(PokerStarsHandBlockName.SUMMARY).append(line).append("/n");

        if (line.contains("Seat 6")) { //TODO implement for different table size

            setHandDtoFieldsWithBlocks(handDto, stringBuilderMap);

            assign(handDto, assistantData);

            List<PlayerNet> playerNetList = assistantData.getPlayerNetDtoList().stream().map(playerNetService::convertDtoToPlayerNet).toList();
            playerNetService.saveAll(playerNetList);

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
    private void assign(HandDto handDto, AssistantData assistantData) {

        Set<Hand> handSetAssigned = assistantData.getHandSetAssigned();
        Set<Player> playerSetAssigned = assistantData.getPlayerSetAssigned();
        List<PlayerDto> playerDtoList = assistantData.getPlayerDtoList();

            Hand hand = handService.covertDtoToHand(handDto);
        if (!handSetAssigned.contains(hand)) {
            for (PlayerDto p : playerDtoList
            ) {
                Player player = playerService.convertDtoToPLayer(p);
                if (playerSetAssigned.contains(player)) {
                    playerSetAssigned.stream().filter(pl -> pl.getId().equals(player.getId())).findFirst().orElseThrow().getHandList().add(hand);

                } else {
                    player.setHandList(new ArrayList<>());
                    player.getHandList().add(hand);
                    playerSetAssigned.add(player);
                }

            }
            handSetAssigned.add(hand);  // сохранили связанные объекты в статику
        }



    }
    private static void assignRaw(Hand hand, List<Player> playerList, Map<Player, Void> playerMapGlobal, List<Hand> handListGlobal) {
        for (Player p : playerList
        ) {
            if(!playerMapGlobal.containsKey(p)){
                p.getHandList().add(hand);
                playerMapGlobal.put(p, null);

            }else {
                Player dto = playerMapGlobal.keySet().stream().filter(playerDto -> playerDto.equals(p)).findFirst().orElseThrow();
                dto.getHandList().add(hand);
                playerMapGlobal.put(dto, null);
            }
            handListGlobal.add(hand);
        }



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


