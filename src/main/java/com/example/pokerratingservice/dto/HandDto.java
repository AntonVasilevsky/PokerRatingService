package com.example.pokerratingservice.dto;

import com.example.pokerratingservice.model.GameType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
public class HandDto {
    private Long id;
    private LocalDateTime date;
    private String tableName;
    private GameType gameType;
    private double stake;
    private int maxPlayer;
    private String seating; //TODO int
    private String holeCards;
    private String flop;
    private String turn;
    private String river;
    private String summary;

    public void setMaxPlayer(int maxPlayers) {
       this.maxPlayer = maxPlayers;
    }

    public String toString() {
        return "Hand{" +
                "\nid=" + id + "; " +
                "\ndate=" + date + "; " +
                "\ntableName=" + tableName + "; " +
                "\ngameType=" + gameType + "; " +
                "\nstake=" + stake + "; " +
                "\nmaxPlayers=" + maxPlayer + "; " +
                "\nseating=" + seating + "; " +
                "\nholeCards=" + holeCards + "; " +
                "\nflop=" + flop + "; " +
                "\nturn=" + turn + "; " +
                "\nriver=" + river + "; " +
                "\nsummary=" + summary + "; " +
                "\n}";
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HandDto handDto)) return false;
        return Objects.equals(id, handDto.id);
    }

    public int hashCode() {
        return Objects.hash(id);
    }
}
