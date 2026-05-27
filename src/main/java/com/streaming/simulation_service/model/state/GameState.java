package com.streaming.simulation_service.model.state;

import com.streaming.simulation_service.model.game.Game;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that will represent the state of all currently active games.
 *
 * @see Game
 */
public class GameState {

    Map<String, Game> state;

    /**
     * Default Constructor.
     */
    public GameState() {
        state = new HashMap<>();
    }

    /**
     * Create a GameState from a previously created state.
     *
     * @param state The state to set.
     */
    public GameState(Map<String, Game> state) {
        this.state = state;
    }

    /**
     * Get the state.
     *
     * @return a map containing all active games within the state.
     */
    public Map<String, Game> getState() {
        return state;
    }

    /**
     * Set the state.
     *
     * @param state The state to set.
     */
    public void setState(Map<String, Game> state) {
        this.state = state;
    }
}
