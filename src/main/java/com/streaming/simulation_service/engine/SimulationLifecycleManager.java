package com.streaming.simulation_service.engine;

import com.streaming.simulation_service.model.state.SimulationGameState;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Manages the lifecycle of all simulated games within the {@link ActiveSimulationsRegistry}.
 * <p>
 * Responsibilities include:
 * <ul>
 *   <li>Bootstrapping initial games on startup</li>
 *   <li>Providing random active games for event generation</li>
 *   <li>Replacing completed or expired games with new ones</li>
 * </ul>
 *
 * @see ActiveSimulationsRegistry
 * @see SimulationGameState
 */
@Component
public class SimulationLifecycleManager {
    // todo - Determine if there needs to be a function/scheduler that needs to constantly check the
    //  state of every game to make sure they've completed or not. (So it's not entirely depended on the
    //  SimulationScheduler tick that calls the orchestrator.

    /**
     * Registry that contains all active {@link SimulationGameState}'s within the simulation.
     */
    private final ActiveSimulationsRegistry registry;

    /**
     * Construct a new {@code SimulationLifecycleManager}.
     *
     * @param registry the {@link ActiveSimulationsRegistry} to manage active games
     */
    public SimulationLifecycleManager(ActiveSimulationsRegistry registry) {
        this.registry = registry;
    }

    /**
     * Bootstrap initial games into the registry on application startup.
     * <p>
     * Invoked automatically by Spring via {@code @PostConstruct}. Creates 5 randomly
     * generated games and adds them to the {@link ActiveSimulationsRegistry} for simulation.
     * <p>
     * TODO: Extract max game count to application configuration.
     *
     * @see #createRandomGame()
     * @see ActiveSimulationsRegistry#addGame(String, SimulationGameState)
     */
    @PostConstruct
    private void bootStrapGames() {
        // todo: add max games to a config.
        for (int i = 0; i < 5; i++) {
            SimulationGameState gameState = createRandomGame();
            if (gameState != null) {
                registry.addGame(gameState.getGame().id(), gameState);
            }
        }
    }

    /**
     * Replace a game within the {@link ActiveSimulationsRegistry}.
     *
     * @param gameState The {@link SimulationGameState} to replace.
     */
    public void replaceGame(SimulationGameState gameState) {
        if (gameState != null) {
            // Remove previous game simulation from the registry.
            registry.removeGame(gameState.getGame().id());

            // Create a new game.
            SimulationGameState newGameState = createRandomGame();

            // Add the new game to the registry.
            registry.addGame(gameState.getGame().id(), newGameState);
        }
    }

    /**
     * Select a random game from the {@link ActiveSimulationsRegistry}.
     *
     * @return A randomly selected {@link SimulationGameState} object.
     */
    public SimulationGameState getRandomGame() {
        Collection<SimulationGameState> activeGames = registry.getActiveGames();
        int randomIndex = ThreadLocalRandom.current().nextInt(activeGames.size());
        return activeGames.stream().toList().get(randomIndex);
    }

    /**
     * TODO
     * Generate a randomly created {@link SimulationGameState} with random sport and teams.
     *
     * @return a randomly created {@link SimulationGameState} object, or {@code null} if generation fails
     * @see SimulationGameState
     */
    private SimulationGameState createRandomGame() {
        //todo:
        // - Randomly select a sport/game
        // - Randomly generate teams
        return null;
    }
}
