package com.streaming.simulation_service.registry;

import com.streaming.simulation_service.factory.matchprogress.MatchProgressFactory;
import com.streaming.simulation_service.model.enums.Sport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class that will be responsible for providing the correct {@link MatchProgressFactory} implementation based on the {@link Sport} being simulated.
 *
 * @see MatchProgressFactory
 * @see Sport
 */
@Component
public class MatchProgressRegistry {

    /**
     * Map of match progress factories that will allow for constant-time lookup.
     */
    private final Map<Sport, MatchProgressFactory> matchProgressFactoryMap;

    /**
     * Construct a new {@code MatchProgressFactory}.
     * <p>
     * Automatically collects all {@link MatchProgressFactory} implementations from the Spring container
     * and builds a map keyed by {@link Sport} for constant-time lookup. Each simulator's
     * sport type is determined by calling {@link MatchProgressFactory#getSport()}.
     *
     * @param matchProgressFactories Auto-injected list of all {@link MatchProgressFactory} implementations.
     */
    public MatchProgressRegistry(List<MatchProgressFactory> matchProgressFactories) {
        this.matchProgressFactoryMap = matchProgressFactories
                .stream()
                .collect(
                        Collectors.toMap(
                                MatchProgressFactory::getSport,
                                matchProgressFactory -> matchProgressFactory
                        )
                );
    }

    /**
     * Get the correct {@link MatchProgressFactory} based on a {@link Sport}.
     *
     * @param sport The {@link Sport} to retrieve a {@link MatchProgressFactory} for.
     * @return The corresponding {@link MatchProgressFactory} to a {@link Sport}.
     */
    public MatchProgressFactory getMatchProgressFactory(Sport sport) {
        MatchProgressFactory matchProgressFactory = matchProgressFactoryMap.get(sport);

        if (matchProgressFactory == null) {
            throw new IllegalArgumentException("No MatchProgressFactory registered for sport " + sport);
        }

        return matchProgressFactory;
    }
}
