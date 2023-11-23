package com.example.pokerratingservice.util.parserassistant.assistants;

import com.example.pokerratingservice.dto.HandDto;
import com.example.pokerratingservice.dto.PlayerDto;
import com.example.pokerratingservice.util.handparser.HandParser;
import com.example.pokerratingservice.util.parserassistant.AssistantData;


public class PokerStarsShowDownHandParserAssistant extends HandParserAssistant{
    @Override
    public void assist(String line, HandDto handDto, PlayerDto playerDto, AssistantData assistantData) {
        String winnerName = getWinnerName(line);
        double potTotal = getTotalPot(line);
        assistantData.getPlayerNetDtoList().forEach(playerNetDto -> {
            if(playerNetDto.getId().equals(winnerName)) {
                playerNetDto.setWonPerHand(potTotal - playerNetDto.getVpip());
            }
        });
        assistantData.getPlayerNetDtoList().forEach(System.out::println);

    }
    public String getWinnerName(String line) {
        if(line.contains("collected")) {
            String regex = "^(.*?) ";
            return HandParser.getStringByRegex(line, regex, 1);
        }
        return "";
    }
    public double getTotalPot(String line) {
        if(line.contains("collected")) {
            String regex = "\\$\\d+(\\.\\d+)?";
            return Double.parseDouble(HandParser.getStringByRegex(line, regex, 1));
        }
        return 0;
    }
}
