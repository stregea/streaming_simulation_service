package com.streaming.simulation_service.engine;

import com.streaming.simulation_service.model.state.SimulationGameState;
import org.springframework.stereotype.Component;

/**
 * The Orchestrator behind the simulation of all games. This class communicates with the {@link SimulationLifecycleManager}
 * to help manage the game states as well as communicating with the {@link SimulationEngine} to generate events for randomly selected games.
 *
 * @see SimulationRunner
 * @see SimulationLifecycleManager
 * @see SimulationEngine
 */
@Component
public class SimulationOrchestrator {

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
    public SimulationOrchestrator(SimulationLifecycleManager lifecycleManager, SimulationEngine engine) {
        this.lifecycleManager = lifecycleManager;
        this.engine = engine;
    }

    /**
     * TODO
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
     * @see SimulationLifecycleManager#getRandomGame()
     * @see SimulationEngine#simulateEvent(SimulationGameState)
     */
    public void simulate() {
        System.out.println("Simulating games...");

        // todo: bootstrap games, then uncomment.
//        SimulationGameState game = lifecycleManager.getRandomGame();
//
//        engine.simulateEvent(game);
    }
}
