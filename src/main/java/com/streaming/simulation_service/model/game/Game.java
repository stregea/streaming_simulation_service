package com.streaming.simulation_service.model.game;

import com.streaming.simulation_service.model.enums.Sport;

/**
 * Record that will represent a {@code Game}.
 *
 * @param id       The ID of the game.
 * @param sport    The sport of the game.
 * @param homeTeam The home team.
 * @param awayTeam The away team.
 * @see Sport
 * @see Team
 */
public record Game(String id, Sport sport, Team homeTeam, Team awayTeam) {}
