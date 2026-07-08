package com.streaming.simulation_service.model.team;

import java.util.List;
import java.util.UUID;

/**
 * Record that will represent a {@code Team} within a Game.
 *
 * @param id      The {@link UUID} of the team.
 * @param name    The name of the team.
 * @param players The list of {@link Player}'s that belong to the team.
 * @see Player
 */
public record Team(UUID id, String name, List<Player> players) {
}
