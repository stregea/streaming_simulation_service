package com.streaming.simulation_service.simulators;

import com.streaming.simulation_service.model.event.EventType;
import com.streaming.simulation_service.model.event.GameEvent;

import java.util.List;
import java.util.Map;

/**
 * Defines the contract for sport-specific game event simulators.
 *
 * @see GameEvent
 * @see EventType
 */
public interface Simulator {

    /**
     * Generate a new game event based on the current sport being played.
     *
     * @return a {@link GameEvent} object containing new event information.
     */
    GameEvent generateEvent();

    /**
     * Generate a list of {@link EventType} for the sport to be inserted within a {@link GameEvent}.
     *
     * @return a list of {@link EventType}.
     */
    List<EventType> getEventTypes();

    /**
     * Generate a simulated payload to be inserted within a {@link GameEvent}.
     *
     * @return a Map containing sports data.
     */
    Map<String, Object> generatePayload();
}
