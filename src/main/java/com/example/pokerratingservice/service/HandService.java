package com.example.pokerratingservice.service;

import com.example.pokerratingservice.dto.HandDto;
import com.example.pokerratingservice.model.Hand;

import java.util.List;

public interface HandService {
    void saveOne(Hand hand);

    Hand getById(long id);


    HandDto convertHandToDto(Hand hand);

    Hand covertDtoToHand(HandDto handDto);

    List<Hand> convertAllDtoToHand(List<HandDto> handDtoListGlobal);

    void saveAll(List<Hand> handList);

    List<Hand> getAllByName(String name);
    List<Hand> getAllById(String id);
}
