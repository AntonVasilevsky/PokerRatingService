package com.example.pokerratingservice.util.parserassistant.assistants;

import com.example.pokerratingservice.dto.HandDto;
import com.example.pokerratingservice.dto.PlayerDto;
import com.example.pokerratingservice.model.Hand;
import com.example.pokerratingservice.model.Player;
import com.example.pokerratingservice.service.HandService;
import com.example.pokerratingservice.service.PlayerService;
import com.example.pokerratingservice.util.enums.PokerStarsHandBlockName;
import com.example.pokerratingservice.util.parserassistant.AssistantData;
import org.springframework.stereotype.Component;

import java.util.*;

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
    public void assist(String line, HandDto hand, List<PlayerDto> playerList, PlayerDto player, HandService handService, PlayerService playerService, HashSet<PlayerDto> playerSet, Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap, List<HandDto> globalHandDtoList, Map<PlayerDto, Void> globalPlayerDtoList) {

    }

    @Override
    public void assist(String line, HandDto handDto, List<PlayerDto> playerDtoList, PlayerDto playerDto,
                       Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap, AssistantData assistantData) {
        stringBuilderMap.get(PokerStarsHandBlockName.SUMMARY).append(line).append("/n");
        System.out.println("Assisting in: " + this.getClass().getName());
        if (line.contains("Seat 6")) { //TODO implement for different table size

            setHandDtoFieldsWithBlocks(handDto, stringBuilderMap);
           /* List<Player> playerList = playerDtoList.stream().map(playerService::convertDtoToPLayer).toList();

            playerList.forEach(player -> player.setHandList(new ArrayList<>()));


            Hand hand = handService.covertDtoToHand(handDto);
            hand.setPlayerList(new ArrayList<>());
            assignAndSave(hand, playerList, playerService, handService);*/
            Set<Hand> handSetAssigned = assistantData.getHandSetAssigned();
            Set<Player> playerSetAssigned = assistantData.getPlayerSetAssigned();

            assign(handDto, playerDtoList, playerSetAssigned, handSetAssigned, playerService, handService);

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
    private static void assign(HandDto handDto, List<PlayerDto> playerDtoList,
                               Set<Player> playerSetAssigned, Set<Hand> handSetAssigned, PlayerService playerService, HandService handService) {
    //TODO здесь создавать связи и сохранять в статику
        // TODO create static sets of players and hands for assigned instances

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


