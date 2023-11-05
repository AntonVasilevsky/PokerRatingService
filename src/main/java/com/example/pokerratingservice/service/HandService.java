package com.example.pokerratingservice.service;

import com.example.pokerratingservice.dto.HandDto;
import com.example.pokerratingservice.model.Hand;

public interface HandService {
    void saveOne(Hand hand);

    Hand getById(long id);


    HandDto convertHandToDto(Hand hand);

    Hand covertDtoToHand(HandDto handDto);
}
