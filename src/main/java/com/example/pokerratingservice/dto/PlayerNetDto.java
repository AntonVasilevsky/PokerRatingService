package com.example.pokerratingservice.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PlayerNetDto {
    String id;
    long handId;
    LocalDateTime date;
    double vpip;
    double wonPerHand;

    @Override
    public String toString() {
        return "Player: " + id + "\nwon: " + wonPerHand;
    }

}
