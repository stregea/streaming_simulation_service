package com.streaming.simulation_service.engine.factory;

import com.streaming.simulation_service.model.team.Player;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Factory responsible for producing {@link Player} instances for simulations.
 *
 * <p>This simple factory provides a single place to centralize player creation logic
 * (IDs, naming conventions, and default attributes). Keeping player generation
 * here makes it easy to replace the implementation with a seeded or data-driven
 * generator for tests or more realistic simulations.</p>
 *
 * @see Player
 */
@Component
public class PlayerFactory {

    /**
     * Create a new {@link Player} with a random UUID and the provided display name.
     *
     * @param playerName The name to assign to the player.
     * @return a newly created {@link Player} with a unique id and the given name.
     */
    public Player createRandomPlayer(String playerName) {
        return new Player(UUID.randomUUID(), playerName, null);
    }
}
