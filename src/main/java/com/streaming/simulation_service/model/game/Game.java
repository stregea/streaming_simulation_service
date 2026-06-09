package com.streaming.simulation_service.model.game;

import com.streaming.simulation_service.model.enums.Sport;

import java.util.UUID;

/**
 * Record that will represent a {@code Game}.
 *
 * @param id       The {@link UUID} of the game.
 * @param sport    The sport of the game.
 * @param homeTeam The home team.
 * @param awayTeam The away team.
 * @see Sport
 * @see Team
 */
public record Game(UUID id, Sport sport, Team homeTeam, Team awayTeam) {
}
