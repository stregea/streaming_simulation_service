package com.streaming.simulation_service.factory;

import com.streaming.simulation_service.model.team.Player;
import com.streaming.simulation_service.model.team.Team;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Factory responsible for constructing {@link Team} instances for simulated games.
 *
 * <p>The {@code TeamFactory} delegates player creation to {@link PlayerFactory} and
 * assembles a {@link Team} with a generated id, name and a list of players. Centralizing
 * team creation makes it straightforward to evolve naming conventions, player composition,
 * or sport-specific roster rules without cluttering lifecycle code.</p>
 *
 * @see Team
 * @see PlayerFactory
 */
@Component
public class TeamFactory {

    private final PlayerFactory playerFactory;

    /**
     * Create a new {@code TeamFactory} that will use the provided {@link PlayerFactory}
     * to generate players when constructing teams.
     *
     * @param playerFactory the {@link PlayerFactory} used to create players for teams
     */
    public TeamFactory(PlayerFactory playerFactory) {
        this.playerFactory = playerFactory;
    }

    /**
     * Create a {@link Team} populated with the given number of players.
     *
     * <p>This method currently generates placeholder player names and a generated team
     * name. It is intentionally simple so it can be replaced with more realistic
     * generation logic later (seed data, sport-aware naming, localized team lists, etc.).</p>
     *
     * @param playerCount the number of players to include on the team; must be non-negative
     * @return a newly constructed {@link Team} containing {@code playerCount} players
     */
    public Team createTeam(Integer playerCount) {
        List<Player> players = new ArrayList<>();

        for (int i = 0; i < playerCount; i++) {
            players.add(playerFactory.createRandomPlayer("Player " + (i + 1)));
        }

        // todo: create better team names, maybe pull from a list of team names based on sport?
        return new Team(UUID.randomUUID(), String.format("Team-%s", UUID.randomUUID()), players);
    }
}
