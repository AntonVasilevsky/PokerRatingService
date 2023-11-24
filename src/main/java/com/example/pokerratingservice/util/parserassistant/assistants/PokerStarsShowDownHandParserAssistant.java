package com.example.pokerratingservice.util.parserassistant.assistants;

import com.example.pokerratingservice.dto.HandDto;
import com.example.pokerratingservice.dto.PlayerDto;
import com.example.pokerratingservice.util.parserassistant.AssistantData;


public class PokerStarsShowDownHandParserAssistant extends HandParserAssistant{
    @Override
    public void assist(String line, HandDto handDto, PlayerDto playerDto, AssistantData assistantData) {
        processWinner(line, assistantData);
    }

}
