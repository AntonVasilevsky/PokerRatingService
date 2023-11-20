package com.example.pokerratingservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor

public class PlayerDataRequest {
    private String name;
    private LocalDateTime from;
    private LocalDateTime to;
}
