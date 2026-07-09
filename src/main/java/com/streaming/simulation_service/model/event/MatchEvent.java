package com.streaming.simulation_service.model.event;

import com.streaming.simulation_service.model.enums.Sport;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

/**
 * Record that will contain the event information that will be sent to Kafka.
 *
 * @param id        - The {@link UUID} of the {@code MatchEvent}.
 * @param matchId    - The ID of the match the event occurred in.
 * @param sport     - The sport of the event that occurred in.
 * @param eventType - The event type.
 * @param timestamp - The time in which the event occurred.
 * @param payload   - The payload that contains other sport-specific data.
 */
public record MatchEvent(UUID id, UUID matchId, Sport sport, String eventType, Instant timestamp,
                         Map<String, Object> payload) {
}
