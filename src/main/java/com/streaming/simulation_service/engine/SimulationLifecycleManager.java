package com.streaming.simulation_service.engine;

import com.streaming.simulation_service.model.state.SimulationGameState;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/**
 * Manages the lifecycle of all simulated games within the {@link ActiveGamesRegistry}.
 * <p>
 * Responsibilities include:
 * <ul>
 *   <li>Bootstrapping initial games on startup</li>
 *   <li>Providing random active games for event generation</li>
 *   <li>Replacing completed or expired games with new ones</li>
 * </ul>
 *
 * @see ActiveGamesRegistry
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
    private final ActiveGamesRegistry registry;

    /**
     * Construct a new {@code SimulationLifecycleManager}.
     *
     * @param registry the {@link ActiveGamesRegistry} to manage active games
     */
    public SimulationLifecycleManager(ActiveGamesRegistry registry) {
        this.registry = registry;
    }

    /**
     * Bootstrap initial games into the registry on application startup.
     * <p>
     * Invoked automatically by Spring via {@code @PostConstruct}. Creates 5 randomly
     * generated games and adds them to the {@link ActiveGamesRegistry} for simulation.
     * <p>
     * TODO: Extract max game count to application configuration.
     *
     * @see #createRandomGame()
     * @see ActiveGamesRegistry#addGame(SimulationGameState)
     */
    @PostConstruct
    private void bootStrapGames() {
        // todo: add max games to a config.
        for (int i = 0; i < 5; i++) {
            SimulationGameState gameState = createRandomGame();
            if (gameState != null) {
                registry.addGame(gameState);
            }
        }
    }

    /**
     * Replace a game within the {@link ActiveGamesRegistry}.
     *
     * @param gameState The {@link SimulationGameState} to replace.
     */
    public void replaceGame(SimulationGameState gameState) {
        if (gameState != null) {
            // Remove previous game simulation from the registry.
            registry.removeGame(gameState);

            // Create a new game.
            SimulationGameState newGameState = createRandomGame();

            // Add the new game to the registry.
            registry.addGame(newGameState);
        }
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
