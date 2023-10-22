package com.example.pokerratingservice.service;

import com.example.pokerratingservice.model.Hand;
import com.example.pokerratingservice.repository.HandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PokerStarsHandServiceImpl implements HandService {
    private final HandRepository handRepository;
    @Override
    public void saveOne(Hand hand) {
        handRepository.save(hand);
    }

    @Override
    public Hand getById(long id) {
        return null;
    }
}
