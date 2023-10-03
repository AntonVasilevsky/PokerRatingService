package com.example.pokerratingservice.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @Column(name = "seating")
    private String seating;
    @Column(name = "hole_cards")
    private String holeCards;
    @Column(name = "flop")
    private String flop;
    @Column(name = "turn")
    private String turn;
    @Column(name = "river")
    private String river;
    @Column(name = "summary")
    private String summary;
    //@JsonBackReference
    @ManyToMany(mappedBy = "handList")
    private List<Player> playerList;
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
