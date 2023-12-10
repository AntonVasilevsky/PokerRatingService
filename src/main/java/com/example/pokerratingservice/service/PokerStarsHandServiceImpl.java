package com.example.pokerratingservice.service;

import com.example.pokerratingservice.dto.HandDto;
import com.example.pokerratingservice.model.Hand;
import com.example.pokerratingservice.repository.HandRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PokerStarsHandServiceImpl implements HandService {
    private final HandRepository handRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Hand> convertAllDtoToHand(List<HandDto> handDtoListGlobal) {
       return handDtoListGlobal.stream().map(hDto -> modelMapper.map(hDto, Hand.class)).toList();
    }

    @Override
    public void saveAll(List<Hand> handList) {
        handRepository.saveAll(handList);
    }

    @Override
    public void saveOne(Hand hand) {
        handRepository.save(hand);
    }

    @Override
    public Hand getById(long id) {
        return null;
    }

    @Override
    public HandDto convertHandToDto(Hand hand) {
        return modelMapper.map(hand, HandDto.class);
    }

    @Override
    public Hand covertDtoToHand(HandDto handDto) {
        return modelMapper.map(handDto, Hand.class);
    }

    @Override
    public List<Hand> getAllByName(String name) {

        return null;
    }

    @Override
    public List<Hand> getAllById(String id) {
        return null;
    }
}
