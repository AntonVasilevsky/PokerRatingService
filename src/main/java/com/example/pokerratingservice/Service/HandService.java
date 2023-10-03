package com.example.pokerratingservice.Service;

import com.example.pokerratingservice.Model.Hand;

public interface HandService {
    void saveOne(Hand hand);

    Hand getById(long id);
}
