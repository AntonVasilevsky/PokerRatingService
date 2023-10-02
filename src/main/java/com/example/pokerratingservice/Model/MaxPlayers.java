package com.anton.pokerstats.models;

import lombok.Getter;

@Getter
public enum MaxPlayers {
    TWO_MAX(2), SIX_MAX(6), NINE_MAX(9), TEN_MAX(10);
    private final int number;

    MaxPlayers(int number) {
        this.number = number;
    }
}
