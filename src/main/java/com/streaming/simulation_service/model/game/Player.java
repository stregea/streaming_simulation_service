package com.streaming.simulation_service.model.game;

/**
 * Record that will represent a {@code Player} within a Team.
 *
 * @param id     The ID of the player.
 * @param teamId The id of the {@link Team} the player plays for.
 * @param name   The name of the player.
 * @see Team
 */
public record Player(String id, String teamId, String name) {}
