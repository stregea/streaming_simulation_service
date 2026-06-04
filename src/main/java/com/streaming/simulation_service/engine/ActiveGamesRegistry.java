package com.streaming.simulation_service.engine;

import com.streaming.simulation_service.model.state.SimulationGameState;
import com.streaming.simulation_service.model.game.Game;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class that will serve as the registry of all simulations occurring.
 */
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
     * Select a random active game from the registry.
     *
     * @return An {@link Optional} containing a randomly selected {@link SimulationGameState}
     * or Optional.empty() if no active games exist.
     */
    public Optional<SimulationGameState> getRandomGame() {
        List<SimulationGameState> games = new ArrayList<>(getActiveGames());

        if (games.isEmpty()) {
            return Optional.empty();
        }

        int randomIndex = ThreadLocalRandom.current().nextInt(games.size());
        return Optional.of(games.get(randomIndex));
    }

    /**
     * Add a new {@link SimulationGameState} to the registry of active games.
     *
     * @param gameState The {@link SimulationGameState} object representing the current state of a {@link Game}.
     */
    public void addGame(SimulationGameState gameState) {
        activeGames.put(gameState.getGame().id(), gameState);
    }

    /**
     * Remove a {@link SimulationGameState} from the registry of active games.
     *
     * @param gameState The {@link SimulationGameState} to be removed from the registry.
     */
    public void removeGame(SimulationGameState gameState) {
        activeGames.remove(gameState.getGame().id());
    }

}
