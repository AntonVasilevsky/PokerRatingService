package com.example.pokerratingservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "hand")
@Data
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Hand {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "date")
    private LocalDateTime date;
    @Column(name = "table_name")
    private String tableName;
    @Column(name = "game_type")
    @Enumerated(EnumType.STRING)
    private GameType gameType;
    @Column(name = "stake")
    private double stake;
    @Column(name = "max_players")
    @Enumerated(EnumType.STRING)
    private MaxPlayer maxPlayer;
    @Column(name = "seating", length = 500)
    private String seating; //TODO int
    @Column(name = "hole_cards", length = 500)
    private String holeCards;
    @Column(name = "flop", length = 500)
    private String flop;
    @Column(name = "turn", length = 500)
    private String turn;
    @Column(name = "river", length = 500)
    private String river;
    @Column(name = "summary", length = 500)
    private String summary;
    @ManyToMany(mappedBy = "handList")
    private List<Player> playerList;

    public void setMaxPlayer(int maxPlayers) {
        switch (maxPlayers) {
            case 2 -> this.maxPlayer = MaxPlayer.TWO_MAX;
            case 6 -> this.maxPlayer = MaxPlayer.SIX_MAX;
            case 9 -> this.maxPlayer = MaxPlayer.NINE_MAX;
            case 10 -> this.maxPlayer = MaxPlayer.TEN_MAX;
            case -1 -> throw new RuntimeException("max players was not specified");
        }
    }

    @Override
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
                "\nplayerList=" + playerList +
                "\n}";
    }

    public Hand() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hand hand)) return false;
        return Objects.equals(id, hand.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
