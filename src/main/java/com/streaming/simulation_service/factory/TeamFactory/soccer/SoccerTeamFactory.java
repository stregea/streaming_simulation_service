package com.streaming.simulation_service.factory.TeamFactory.soccer;

import com.streaming.simulation_service.factory.TeamFactory.TeamFactory;
import com.streaming.simulation_service.model.enums.Sport;
import com.streaming.simulation_service.model.enums.position.soccer.SoccerPosition;
import com.streaming.simulation_service.model.team.Player;
import com.streaming.simulation_service.model.team.Team;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class SoccerTeamFactory implements TeamFactory {

    @Override
    public Sport getSport() {
        return Sport.SOCCER;
    }

    /**
     * Construct a soccer team with the classic 4-3-3 positioning.
     * @return
     */
    @Override
    public Team createTeam() {
        List<Player> players = new ArrayList<>();

        // Add the goalkeeper
        players.add(new Player(UUID.randomUUID(), String.format("Player-%s", UUID.randomUUID()), SoccerPosition.GOALKEEPER));

        // Add the defenders
        for (int i = 0; i < 4; i++) {
            players.add(new Player(UUID.randomUUID(), String.format("Player-%s", UUID.randomUUID()), SoccerPosition.DEFENDER));
        }

        // Add the midfielders
        for (int i = 0; i < 3; i++) {
            players.add(new Player(UUID.randomUUID(), String.format("Player-%s", UUID.randomUUID()), SoccerPosition.MIDFIELD));
        }

        // Add the forwards
        for (int i = 0; i < 3; i++) {
            players.add(new Player(UUID.randomUUID(), String.format("Player-%s", UUID.randomUUID()), SoccerPosition.FORWARD));
        }

        return new Team(UUID.randomUUID(), String.format("Team-%s", UUID.randomUUID()), players);
    }
}
