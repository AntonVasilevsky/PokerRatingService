package com.example.pokerratingservice.util.handparser;

import com.example.pokerratingservice.dto.HandDto;
import com.example.pokerratingservice.dto.PlayerDto;
import com.example.pokerratingservice.model.GameType;
import com.example.pokerratingservice.model.Hand;
import com.example.pokerratingservice.model.Player;
import com.example.pokerratingservice.service.HandService;
import com.example.pokerratingservice.service.PlayerService;
import com.example.pokerratingservice.util.enums.PokerStarsHandBlockName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PokerStarsHandParserTest {

    private static PokerStarsHandParser pokerStarsHandParser;
    @Mock
    private PlayerService playerService;
    @Mock
    private HandService handService;

    private List<PokerStarsHandBlockName> handBlockNameslist;

    static {
        pokerStarsHandParser = new PokerStarsHandParser(new PlayerService() {
            @Override
            public void saveOne(Player player) {

            }

            @Override
            public void saveAll(List<Player> playerList) {

            }

            @Override
            public Optional<Player> getById(String id) {
                return Optional.empty();
            }

            @Override
            public PlayerDto convertPlayerToDto(Player player) {
                return null;
            }

            @Override
            public Player convertDtoToPLayer(PlayerDto playerDto) {
                return null;
            }

            @Override
            public List<Player> convertAllDtoToPlayer(Map<PlayerDto, Void> playerDtoMapGlobal) {
                return null;
            }
        }, new HandService() {
            @Override
            public void saveOne(Hand hand) {

            }

            @Override
            public Hand getById(long id) {
                return null;
            }

            @Override
            public HandDto convertHandToDto(Hand hand) {
                return null;
            }

            @Override
            public Hand covertDtoToHand(HandDto handDto) {
                return null;
            }

            @Override
            public List<Hand> convertAllDtoToHand(List<HandDto> handDtoListGlobal) {
                return null;
            }

            @Override
            public void saveAll(List<Hand> handList) {

            }

            @Override
            public List<Hand> getAllByName(String name) {
                return null;
            }

            @Override
            public List<Hand> getAllById(String id) {
                return null;
            }
        }, new ArrayList<>());
    }


    public String TWO_PLAYERS_HAND = """
            PokerStars Hand #245813409143:  Hold'em Limit ($2/$4 USD) - 2023/09/06 17:05:44 ET
            Table 'Laodamia IV' 6-max Seat #3 is the button
            Seat 1: Sharp(Gosu) ($104.21 in chips)\s
            Seat 3: Apanage ($35 in chips)\s
            Seat 4: zozek ($56.73 in chips)\s
            Seat 5: MelaninMan ($60 in chips)\s
            Seat 6: CharlesMouse ($160.71 in chips)\s
            zozek: posts small blind $1
            MelaninMan: posts big blind $2
            *** HOLE CARDS ***
            Dealt to Sharp(Gosu) [Qs 4s]
            CharlesMouse: folds\s
            Sharp(Gosu): folds\s
            Apanage: raises $2 to $4
            zozek: calls $3
            MelaninMan: folds\s
            *** FLOP *** [3s 9s Qh]
            zozek: checks\s
            Apanage: bets $2
            zozek: calls $2
            *** TURN *** [3s 9s Qh] [8s]
            zozek: checks\s
            Apanage: checks\s
            *** RIVER *** [3s 9s Qh 8s] [Kd]
            zozek: checks\s
            Apanage: checks\s
            *** SHOW DOWN ***
            zozek: shows [As 5h] (high card Ace)
            Apanage: shows [Td Ad] (high card Ace - King+Queen+Ten kicker)
            Apanage collected $13.58 from pot
            *** SUMMARY ***
            Total pot $14 | Rake $0.42\s
            Board [3s 9s Qh 8s Kd]
            Seat 1: Sharp(Gosu) folded before Flop (didn't bet)
            Seat 3: Apanage (button) showed [Td Ad] and won ($13.58) with high card Ace
            Seat 4: zozek (small blind) showed [As 5h] and lost with high card Ace
            Seat 5: MelaninMan (big blind) folded before Flop
            Seat 6: CharlesMouse folded before Flop (didn't bet)
            """;
    @Test
    void parse() throws IOException {
        //arrange
        BufferedReader br = new BufferedReader(new StringReader(TWO_PLAYERS_HAND));
        Player expectedP1 = new Player("zozek", new ArrayList<>());
        Player expectedP2 = new Player("Apanage", new ArrayList<>());
        Hand expectedHand = Hand.builder()
                .id(245813409143L)
                .gameType(GameType.FL)
                .tableName("Laodamia IV")
                .stake(2)
                .maxPlayer(6)
                .holeCards("""
                        Dealt to Sharp(Gosu) [Qs 4s]
                        CharlesMouse: folds\s
                        Sharp(Gosu): folds\s
                        Apanage: raises $2 to $4
                        zozek: calls $3
                        MelaninMan: folds\s""")
                .flop("""
                        zozek: checks\s
                        Apanage: bets $2
                        zozek: calls $2""")
                .turn("""
                        zozek: checks\s
                        Apanage: checks\s""")
                .river("""
                        zozek: checks\s
                        Apanage: checks\s""")
                .summary("""
                        Total pot $14 | Rake $0.42\s
                        Board [3s 9s Qh 8s Kd]
                        Seat 1: Sharp(Gosu) folded before Flop (didn't bet)
                        Seat 3: Apanage (button) showed [Td Ad] and won ($13.58) with high card Ace
                        Seat 4: zozek (small blind) showed [As 5h] and lost with high card Ace
                        Seat 5: MelaninMan (big blind) folded before Flop
                        Seat 6: CharlesMouse folded before Flop (didn't bet)""")
                .build();


        //act


        AssignedData actualAssignedData = pokerStarsHandParser.parse(br);

        //assert
        /*Assertions.assertEquals(expectedHand.getId(), handResult.getId());
        Assertions.assertEquals(expectedHand.getStake(), handResult.getStake());
        Assertions.assertEquals(expectedHand.getMaxPlayer(), handResult.getMaxPlayer());
        Assertions.assertEquals(expectedHand.getHoleCards(), handResult.getHoleCards());
        Assertions.assertEquals(expectedHand.getFlop(), handResult.getFlop());
        Assertions.assertEquals(expectedHand.getTurn(), handResult.getTurn());
        Assertions.assertEquals(expectedHand.getRiver(), handResult.getRiver());
        Assertions.assertEquals(expectedHand.getSummary(), handResult.getSummary());
        Assertions.assertEquals(expectedHand.getTableName(), handResult.getTableName());
        Assertions.assertEquals(expectedHand.getGameType(), handResult.getGameType());*/

    }
}