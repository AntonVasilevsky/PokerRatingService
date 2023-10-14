package com.example.pokerratingservice.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
    private MaxPlayers maxPlayers;
    @Column(name = "seating", length = 500)
    private String seating;
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
    //@JsonBackReference
    @ManyToMany(mappedBy = "handList")
    private List<Player> playerList;

    public void setMaxPlayers(int maxPlayers) {
        switch (maxPlayers) {
            case 2 -> this.maxPlayers = MaxPlayers.TWO_MAX;
            case 6 -> this.maxPlayers = MaxPlayers.SIX_MAX;
            case 9 -> this.maxPlayers = MaxPlayers.NINE_MAX;
            case 10 -> this.maxPlayers = MaxPlayers.TEN_MAX;
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
                "\nmaxPlayers=" + maxPlayers + "; " +
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
}