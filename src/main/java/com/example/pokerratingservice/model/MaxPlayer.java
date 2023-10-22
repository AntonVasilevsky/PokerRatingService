package com.example.pokerratingservice.model;

import lombok.Getter;

@Getter
public enum MaxPlayer {
    TWO_MAX(2), SIX_MAX(6), NINE_MAX(9), TEN_MAX(10);
    private final int number;

    MaxPlayer(int number) {
        this.number = number;
    }
}
