package com.example.pokerratingservice.util.handparser;

import com.example.pokerratingservice.model.Hand;
import com.example.pokerratingservice.model.Player;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class AssignedData {
    Set<Player> players;
    Set<Hand> hands;
}
