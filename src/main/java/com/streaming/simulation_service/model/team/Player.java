package com.streaming.simulation_service.model.team;

import com.streaming.simulation_service.model.team.position.Position;

import java.util.UUID;

/**
 * Record that will represent a {@code Player} within a Team.
 *
 * @param id       The {@link UUID} of the player.
 * @param name     The name of the player.
 * @param position The position of the player.
 */
public record Player(UUID id, String name, Position position) {
}
