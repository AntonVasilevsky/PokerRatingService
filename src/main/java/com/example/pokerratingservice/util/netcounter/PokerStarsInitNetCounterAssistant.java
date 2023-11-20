package com.example.pokerratingservice.util.netcounter;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@Setter
@AllArgsConstructor
@Slf4j
public class PokerStarsInitNetCounterAssistant implements PokerStarsNetCounter {

}


