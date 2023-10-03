package com.example.pokerratingservice.util;

import lombok.Getter;

@Getter
public enum PokerStarsKeywords {
    BET(": bets"), RAISE(": raises"), CALL(": calls"), FOLD(": folds"),
    POST_BB("posts big blind"), POST_SB("posts small blind"), FOLD_PREFLOP("folded before flop"),
    TOTAL_POT("Total pot"), WON("won"),
    HOLE_CARDS("*** HOLE CARDS ***"),
    FLOP("*** FLOP ***"), TURN("*** TURN ***"), RIVER("*** RIVER ***"),
    SUMMARY("*** SUMMARY ***");

    private String value;

    PokerStarsKeywords(String value) {
        this.value = value;
    }
}
