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
    Long handId;
    LocalDateTime date;
    Long WonPerHand;
    Double vpip;

    @Override
    public String toString() {
        return "PlayerNetDto{" +
                "id='" + id + '\'' +
                ", handId=" + handId +
                ", date=" + date +
                '}';
    }


}
