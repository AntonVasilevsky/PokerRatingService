package com.example.pokerratingservice.util.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PokerStarsKeywords {
    BET(": bets"), RAISE(": raises"), CALL(": calls"), FOLD(": folds"),
    POST_BB("posts big blind"), POST_SB("posts small blind"), FOLD_PREFLOP("folded before flop"),
    TOTAL_POT("Total pot"), WON("won"),
    HOLE_CARDS("*** HOLE CARDS ***"),
    FLOP("*** FLOP ***"), TURN("*** TURN ***"), RIVER("*** RIVER ***"),
    SUMMARY("*** SUMMARY ***");

    private final String value;

}
