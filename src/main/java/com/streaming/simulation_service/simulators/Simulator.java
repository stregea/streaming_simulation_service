package com.streaming.simulation_service.simulators;

import com.streaming.simulation_service.model.enums.Sport;
import com.streaming.simulation_service.model.enums.EventType;
import com.streaming.simulation_service.model.event.MatchEvent;
import com.streaming.simulation_service.model.state.SimulationMatchState;

import java.util.List;
import java.util.Map;

/**
 * Defines the contract for sport-specific match event simulators.
 *
 * @see SimulationMatchState
 * @see MatchEvent
 * @see EventType
 * @see Sport
 */
public interface Simulator {

    /**
     * Get the {@link Sport} associated with the simulator.
     *
     * @return the {@link Sport} that this simulator handles
     */
    Sport getSport();

    /**
     * Generate a new match event based on the current sport being played.
     *
     * @param matchState the {@link SimulationMatchState} to generate an event for.
     * @return a {@link MatchEvent} object containing new event information.
     */
    MatchEvent generateEvent(SimulationMatchState matchState);

    /**
     * Generate a list of {@link EventType} for the sport to be inserted within a {@link MatchEvent}.
     *
     * @return a list of {@link EventType}.
     */
    List<EventType> getEventTypes();

    /**
     * Generate a simulated payload to be inserted within a {@link MatchEvent}.
     *
     * @return a Map containing sports data.
     */
    Map<String, Object> generatePayload();
}
