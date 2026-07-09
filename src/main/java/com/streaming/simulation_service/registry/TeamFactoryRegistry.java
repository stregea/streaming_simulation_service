package com.streaming.simulation_service.registry;

import com.streaming.simulation_service.teamfactory.TeamFactory;
import com.streaming.simulation_service.model.enums.Sport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class that will be responsible for providing the correct {@link TeamFactory} implementation based on the {@link Sport} being simulated.
 *
 * @see TeamFactory
 * @see Sport
 */
@Component
public class TeamFactoryRegistry {

    /**
     * Map of team factories that will allow for constant-time lookup.
     */
    private final Map<Sport, TeamFactory> teamFactories;

    /**
     * Construct a new {@code SimulatorFactory}.
     * <p>
     * Automatically collects all {@link TeamFactory} implementations from the Spring container
     * and builds a map keyed by {@link Sport} for constant-time lookup. Each simulator's
     * sport type is determined by calling {@link TeamFactory#getSport()}.
     *
     * @param teamFactories auto-injected list of all {@link TeamFactory} implementations.
     */
    public TeamFactoryRegistry(List<TeamFactory> teamFactories) {
        this.teamFactories = teamFactories
                .stream()
                .collect(
                        Collectors.toMap(
                                TeamFactory::getSport,
                                teamFactory -> teamFactory
                        )
                );
    }

    /**
     * Get the correct simulator based on a {@link Sport}.
     *
     * @param sport The {@link Sport} to retrieve a {@link TeamFactory} for.
     * @return The corresponding {@link TeamFactory} to a {@link Sport}.
     */
    public TeamFactory getTeamFactory(Sport sport) {
        TeamFactory teamFactory = teamFactories.get(sport);

        if (teamFactory == null) {
            throw new IllegalArgumentException("No TeamFactory registered for sport " + sport);
        }

        return teamFactory;
    }
}
