package com.streaming.simulation_service.factory.team.soccer;

import com.streaming.library.enums.Sport;
import com.streaming.library.enums.position.Position;
import com.streaming.library.enums.position.soccer.SoccerPosition;
import com.streaming.library.model.record.Player;
import com.streaming.library.model.record.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.AssertionsKt.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class SoccerTeamFactoryTest {

    SoccerTeamFactory soccerTeamFactory;

    /**
     * Set up the test environment before each test case.
     */
    @BeforeEach
    public void setUp() {
        soccerTeamFactory = new SoccerTeamFactory();
    }

    /**
     * Test the {@link SoccerTeamFactory#getSport()} method.
     */
    @Test
    public void testGetSport() {
        assertEquals(Sport.SOCCER, soccerTeamFactory.getSport());
    }

    /**
     * Test the {@link SoccerTeamFactory#createPlayer(Position)} method.
     */
    @Test
    public void testCreatePlayer() {
        Player player = soccerTeamFactory.createPlayer(null);

        assertNotNull(player);
    }

    /**
     * Test the {@link SoccerTeamFactory#createTeam()} method.
     */
    @Test
    public void testCreateTeam() {
        Team team = soccerTeamFactory.createTeam();
        List<Player> players = team.players();

        // Using assertAll to group assertions for better readability and reporting.
        assertAll(
                () -> assertNotNull(team),
                () -> assertNotNull(players),
                () -> assertEquals(1, countPlayers(team.players(), SoccerPosition.GOALKEEPER)),
                () -> assertEquals(4, countPlayers(players, SoccerPosition.DEFENDER)),
                () -> assertEquals(3, countPlayers(players, SoccerPosition.MIDFIELD)),
                () -> assertEquals(3, countPlayers(players, SoccerPosition.FORWARD))
        );
    }

    /**
     * Count the number of players in the given list that match the specified {@link SoccerPosition}.
     *
     * @param players  The list of players to search through.
     * @param position The position to count players for.
     * @return The number of players matching the specified position.
     */
    private Integer countPlayers(List<Player> players, SoccerPosition position) {
        return Math.toIntExact(players.stream()
                .filter(player -> player.position().equals(position))
                .count());
    }

}
