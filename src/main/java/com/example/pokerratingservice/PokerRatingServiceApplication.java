package com.example.pokerratingservice;

import com.example.pokerratingservice.model.Hand;
import com.example.pokerratingservice.model.MaxPlayer;
import com.example.pokerratingservice.model.Player;
import com.example.pokerratingservice.service.HandService;
import com.example.pokerratingservice.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static com.example.pokerratingservice.model.GameType.NL;

@SpringBootApplication
@RequiredArgsConstructor
public class PokerRatingServiceApplication implements CommandLineRunner {

    private final PlayerService playerService;
    private final HandService handService;

    private static final String path = "C:\\java\\projects\\PokerRatingService\\threehands.txt";
    private static final String pathFolder = "C:\\Users\\r\\Desktop\\pokerstats\\psHandsTest";
    private final Logger logger = LoggerFactory.getLogger(PokerRatingServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PokerRatingServiceApplication.class, args);
    }

    /* @Override
     public void run(String... args) throws Exception {
         logger.debug("Starting run");

         pokerStarsHandParser.readFile(path);
         HashSet<Hand> handHashSet = pokerStarsHandParser.getHandHashSet();
         HashSet<Player> playerHashSet = pokerStarsHandParser.getPlayerHashSet();

         logger.info("PRINTING SETS");

         handHashSet.forEach(hand -> System.out.println(hand.getId()));
         playerHashSet.forEach(player -> System.out.println(player.getId()));

         databaseHandler.saveEntities(playerHashSet, handHashSet);


         logger.debug("DONE");
     }*/
    /*@Override

    public void run(String... args) throws Exception {
        logger.debug("Starting run");
        Player p1 = buildTestPlayer("P1");
        Player p2 = buildTestPlayer("P2");
        Hand h1 = buildTestHand(123456789123L);
        Hand h2 = buildTestHand(123456789122L);
        Hand h3 = buildTestHand(123456789121L);

        assignAndSave(p1, h1, handService, playerService);
        assignAndSave(p1, h2, handService, playerService);
        assignAndSave(p2, h1, handService, playerService);
        assignAndSave(p2, h2, handService, playerService);
        assignAndSave(p1, h3, handService, playerService);
        assignAndSave(p2, h3, handService, playerService);


        logger.debug("DONE");
    }*/
    @Override

    public void run(String... args) throws Exception {
        // pokerStarsHandParser.readFiles(pathFolder);

    }

    void assignAndSave(Player player, Hand hand, HandService handService, PlayerService playerService) {
        player.getHandList().add(hand);

        handService.saveOne(hand);
        playerService.saveOne(player);

    }

    private static Player buildTestPlayer(String name) {
        return Player.builder()
                .id(name)
                .handList(new ArrayList<>())
                .build();
    }

    private static Hand buildTestHand(long id) {
        String date = "2023/09/06 16:19:57";
        String pattern = "yyyy/MM/dd HH:mm:ss";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);

        return Hand.builder()
                .id(id)
                .tableName("Laodamia IV")
                .gameType(NL)
                .stake(2)
                .date(LocalDateTime.parse(date, dtf))
                .maxPlayer(MaxPlayer.SIX_MAX)
                .seating("""
                        Seat 1: Sharp(Gosu) ($60 in chips)
                        Seat 6: CharlesMouse ($60 in chips)""")
                .holeCards("""
                                    *** HOLE CARDS ***
                        Dealt to Sharp(Gosu) [As 4s]
                        Sharp(Gosu): raises $2 to $4
                        CharlesMouse: calls $2""")
                .flop("""
                        *** FLOP *** [2d Ac Kc]
                                    CharlesMouse: bets $2
                                    Sharp(Gosu): calls $2
                                    """)
                .turn("""
                        *** TURN *** [2d Ac Kc] [Ks]
                                    CharlesMouse: bets $4
                                    Sharp(Gosu): calls $4
                                   """)
                .river("""
                        *** RIVER *** [2d Ac Kc Ks] [9d]
                                   CharlesMouse: checks
                                   Sharp(Gosu): bets $4
                                   CharlesMouse: folds
                                   Uncalled bet ($4) returned to Sharp(Gosu)
                                   Sharp(Gosu) collected $19.60 from pot
                                   Sharp(Gosu): doesn't show hand
                                   """)
                .summary("""
                        *** SUMMARY ***
                                   Total pot $20 | Rake $0.40
                                   Board [2d Ac Kc Ks 9d]
                                   Seat 1: Sharp(Gosu) (button) (small blind) collected ($19.60)
                                   Seat 6: CharlesMouse (big blind) folded on the River""")
                .playerList(new ArrayList<>())
                .build();
    }

}
