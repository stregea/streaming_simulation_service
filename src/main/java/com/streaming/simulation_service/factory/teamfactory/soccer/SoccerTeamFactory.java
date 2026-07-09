package com.streaming.simulation_service.factory.teamfactory.soccer;

import com.streaming.simulation_service.factory.teamfactory.TeamFactory;
import com.streaming.simulation_service.model.enums.Sport;
import com.streaming.simulation_service.model.enums.position.Position;
import com.streaming.simulation_service.model.enums.position.soccer.SoccerPosition;
import com.streaming.simulation_service.model.team.Player;
import com.streaming.simulation_service.model.team.Team;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Soccer-specific implementation of the {@link TeamFactory} interface.
 * <p>
 * Generates a soccer
 *
 * @see TeamFactory
 * @see Sport
 * @see Player
 */
@Component
public class SoccerTeamFactory implements TeamFactory {

    /**
     * Get the {@link Sport} associated with this {@link TeamFactory}.
     *
     * @return {@link Sport#SOCCER}
     */
    @Override
    public Sport getSport() {
        return Sport.SOCCER;
    }

    /**
     * Construct a unique {@link Player} object.
     *
     * @param position The position the {@link Player} plays.
     * @return A unique {@link Player} object with a random UUID and name.
     */
    @Override
    public Player createPlayer(Position position) {
        return new Player(UUID.randomUUID(), String.format("Player-%s", UUID.randomUUID()), position);
    }

    /**
     * Construct a soccer {@link Team} with the classic 4-3-3 positioning.
     *
     * @return A fully populated soccer {@link Team}.
     */
    @Override
    public Team createTeam() {
        List<Player> players = new ArrayList<>();

        // Add the goalkeeper
        players.add(createPlayer(SoccerPosition.GOALKEEPER));

        // Add the defenders
        for (int i = 0; i < 4; i++) {
            players.add(createPlayer(SoccerPosition.DEFENDER));
        }

        // Add the midfielders
        for (int i = 0; i < 3; i++) {
            players.add(createPlayer(SoccerPosition.MIDFIELD));
        }

        // Add the forwards
        for (int i = 0; i < 3; i++) {
            players.add(createPlayer(SoccerPosition.FORWARD));
        }

        return new Team(UUID.randomUUID(), String.format("Team-%s", UUID.randomUUID()), players);
    }
}
