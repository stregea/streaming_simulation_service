package com.streaming.simulation_service.model.game;

import java.util.List;

/**
 * Record that will represent a Team within a Game.
 *
 * @param id      The ID of the team.
 * @param name    The name of the team.
 * @param players The list of {@link Player}'s that belong to the team.
 * @see Player
 */
public record Team(String id, String name, List<Player> players) {}
