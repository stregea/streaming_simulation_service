package com.streaming.simulation_service.model.match;

import com.streaming.simulation_service.model.enums.Sport;
import com.streaming.simulation_service.model.team.Team;

import java.util.UUID;

/**
 * Record that will represent a {@code Match}.
 *
 * @param id       The {@link UUID} of the Match.
 * @param sport    The sport of the match.
 * @param homeTeam The home team.
 * @param awayTeam The away team.
 * @see Sport
 * @see Team
 */
public record Match(UUID id, Sport sport, Team homeTeam, Team awayTeam) {
}
