package com.streaming.simulation_service.model.event;

import com.streaming.simulation_service.model.enums.Sport;

import java.time.Instant;
import java.util.Map;

/**
 * Record that will contain the event information that will be sent to Kafka.
 * @param id - The ID of the GameEvent.
 * @param gameId - The ID of the game the event occurred in.
 * @param sport - The sport of the event that occurred in.
 * @param eventType - The event type.
 * @param timestamp - The time in which the event occurred.
 * @param payload - The payload that contains other sport-specific data.
 */
public record GameEvent(String id, String gameId, Sport sport, String eventType, Instant timestamp, Map<String, Object> payload) {}
