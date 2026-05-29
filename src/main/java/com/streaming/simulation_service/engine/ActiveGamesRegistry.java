package com.streaming.simulation_service.engine;

import com.streaming.simulation_service.model.state.SimulationGameState;
import com.streaming.simulation_service.model.game.Game;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ActiveGamesRegistry {

    /**
     * The ConcurrentHashMap that will serve as the primary registry of all active games.
     */
    private final Map<String, SimulationGameState> activeGames = new ConcurrentHashMap<>();

    /**
     * Get a {@link Collection} of all active games being run within the simulation.
     *
     * @return A {@link Collection} of {@link SimulationGameState} objects representing all active games.
     */
    public Collection<SimulationGameState> getActiveGames() {
        return activeGames.values();
    }

    /**
     * Get an active {@link SimulationGameState} from the registry based on a {@link Game}'s id.
     *
     * @param gameId The id of the {@link Game} within the {@link SimulationGameState} to be retrieved from the registry.
     * @return An {@link Optional} containing the {@link SimulationGameState} if found, otherwise empty.
     */
    public Optional<SimulationGameState> getGame(String gameId) {
        return Optional.ofNullable(activeGames.get(gameId));
    }

    /**
     * Add a new {@link SimulationGameState} to the registry of active games.
     *
     * @param gameId    The id of the {@link Game} to add to the registry.
     * @param gameState The {@link SimulationGameState} object representing the current state of a {@link Game}.
     */
    public void addGame(String gameId, SimulationGameState gameState) {
        activeGames.put(gameId, gameState);
    }

    /**
     * Remove a {@link SimulationGameState} from the registry of active games.
     *
     * @param gameId The id of the {@link Game} within the {@link SimulationGameState} to be removed from the registry.
     */
    public void removeGame(String gameId) {
        activeGames.remove(gameId);
    }

}
