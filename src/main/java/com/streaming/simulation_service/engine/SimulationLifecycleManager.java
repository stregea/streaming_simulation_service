package com.streaming.simulation_service.engine;

import com.streaming.simulation_service.model.state.SimulationGameState;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class SimulationLifecycleManager {
    // This actively communicates with the ActiveSimulationsRegistry
    // Boostrap games
    // Replace Game

    // todo - Determine if there needs to be a function/scheduler that needs to contantly check the
    //  state of every game to make sure they've completed or not. (So it's not entirely depended on the
    //  SimulationScheduler tick that calls the orchestrator.

    private final ActiveSimulationsRegistry registry;

    public SimulationLifecycleManager(ActiveSimulationsRegistry registry) {
        this.registry = registry;
    }

    @PostConstruct
    public void bootStrapGames() {
        // todo: add max games to a config.
        for (int i = 0; i < 5; i++) {
            SimulationGameState gameState = createRandomGame();
            if (gameState != null) {
                registry.addGame(gameState.getGame().id(), gameState);
            }
        }
    }

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

    private SimulationGameState createRandomGame() {
        //todo:
        // - Randomly select a sport/game
        // - Randomly generate teams
        return null;
    }
}
