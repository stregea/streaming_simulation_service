package com.streaming.simulation_service.registry;

import com.streaming.simulation_service.model.match.Match;
import com.streaming.simulation_service.model.state.SimulationMatchState;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class that will serve as the registry of all simulations occurring.
 */
@Component
public class ActiveMatchesRegistry {

    /**
     * The ConcurrentHashMap that will serve as the primary registry of all active matches.
     */
    private final Map<UUID, SimulationMatchState> activeMatches = new ConcurrentHashMap<>();

    /**
     * Get a {@link Collection} of all active matches being run within the simulation.
     *
     * @return A {@link Collection} of {@link SimulationMatchState} objects representing all active matches.
     */
    public Collection<SimulationMatchState> getActiveMatches() {
        return activeMatches.values();
    }

    /**
     * Get an active {@link SimulationMatchState} from the registry based on a {@link Match}'s id.
     *
     * @param matchId The id of the {@link Match} within the {@link SimulationMatchState} to be retrieved from the registry.
     * @return An {@link Optional} containing the {@link SimulationMatchState} if found, otherwise empty.
     */
    public Optional<SimulationMatchState> getMatch(UUID matchId) {
        return Optional.ofNullable(activeMatches.get(matchId));
    }

    /**
     * Select a random active match from the registry.
     *
     * @return An {@link Optional} containing a randomly selected {@link SimulationMatchState}
     * or Optional.empty() if no active matches exist.
     */
    public Optional<SimulationMatchState> getRandomMatch() {
        List<SimulationMatchState> matches = new ArrayList<>(getActiveMatches());

        if (matches.isEmpty()) {
            return Optional.empty();
        }

        int randomIndex = ThreadLocalRandom.current().nextInt(matches.size());
        return Optional.of(matches.get(randomIndex));
    }

    /**
     * Add a new {@link SimulationMatchState} to the registry of active matches.
     *
     * @param matchState The {@link SimulationMatchState} object representing the current state of a {@link Match}.
     */
    public void addMatch(SimulationMatchState matchState) {
        activeMatches.put(matchState.getMatch().id(), matchState);
    }

    /**
     * Remove a {@link SimulationMatchState} from the registry of active matches.
     *
     * @param matchState The {@link SimulationMatchState} to be removed from the registry.
     */
    public void removeMatch(SimulationMatchState matchState) {
        activeMatches.remove(matchState.getMatch().id());
    }

}
