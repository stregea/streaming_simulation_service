package com.streaming.simulation_service.engine;

import com.streaming.simulation_service.model.state.SimulationGameState;
import com.streaming.simulation_service.registry.ActiveGamesRegistry;
import org.springframework.stereotype.Component;

/**
 * The Orchestrator behind the simulation of all games. This class communicates with the {@link ActiveGamesRegistry} and
 * {@link SimulationLifecycleManager} to help manage the game states as well as communicating with the {@link SimulationEngine}
 * to generate events for randomly selected games.
 *
 * @see SimulationRunner
 * @see ActiveGamesRegistry
 * @see SimulationLifecycleManager
 * @see SimulationEngine
 */
@Component
public class SimulationOrchestrator {

    /**
     * Registry that contains all active {@link SimulationGameState}'s within the simulation.
     */
    private final ActiveGamesRegistry registry;

    /**
     * Object which will manage the lifecycle of games being simulated.
     */
    private final SimulationLifecycleManager lifecycleManager;

    /**
     * The primary game engine that will generate events for the randomly selected games.
     */
    private final SimulationEngine engine;

    /**
     * Construct a new {@code SimulationOrchestrator}.
     *
     * @param lifecycleManager the {@link SimulationLifecycleManager} for managing game lifecycle.
     * @param engine           the {@link SimulationEngine} for generating events.
     */
    public SimulationOrchestrator(ActiveGamesRegistry registry, SimulationLifecycleManager lifecycleManager, SimulationEngine engine) {
        this.registry = registry;
        this.lifecycleManager = lifecycleManager;
        this.engine = engine;
    }

    /**
     * Orchestrates the overall simulation process by:
     * <ul>
     *   <li>Selecting a random active game from the lifecycle manager</li>
     *   <li>Invoking the simulation engine to generate an event for that game</li>
     * </ul>
     *
     * <p>
     * This method is called periodically by the {@link SimulationRunner}.
     *
     * @see SimulationRunner#tick()
     * @see ActiveGamesRegistry#getRandomGame()
     * @see SimulationEngine#simulateEvent(SimulationGameState)
     * @see SimulationLifecycleManager#replaceGame(SimulationGameState)
     */
    public void simulate() {
        SimulationGameState game = registry.getRandomGame().orElse(null);

        if (game == null) {
            System.out.println("No active games found to simulate.");
            return;
        }

        System.out.printf("Simulating game %s | sport %s...%n", game.getGame().id(), game.getGame().sport());

        engine.simulateEvent(game);

        if (game.isGameOver()) {
            lifecycleManager.replaceGame(game);
        }
    }
}
