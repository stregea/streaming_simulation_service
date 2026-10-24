package com.streaming.simulation_service.engine;

import com.streaming.library.enums.Sport;
import com.streaming.library.model.record.Match;
import com.streaming.library.model.record.event.MatchEvent;
import com.streaming.simulation_service.kafka.producer.MatchEventProducer;
import com.streaming.simulation_service.registry.SimulatorRegistry;
import com.streaming.simulation_service.simulators.Simulator;
import com.streaming.simulation_service.state.SimulationMatchState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the {@link SimulationEngine} class.
 *
 * @see SimulationEngine
 */
@ExtendWith(MockitoExtension.class)
public class SimulationEngineTest {

    /**
     * Mocked {@link SimulatorRegistry} object.
     */
    @Mock
    SimulatorRegistry simulatorRegistry;

    /**
     * Mocked {@link MatchEventProducer} object.
     */
    @Mock
    MatchEventProducer matchEventProducer;

    /**
     * Mocked {@link Simulator} object.
     */
    @Mock
    Simulator simulator;

    /**
     * The {@link SimulationEngine} instance under test.
     */
    SimulationEngine simulationEngine;

    /**
     * Set up the test environment before each test case.
     */
    @BeforeEach
    public void setUp() {
        simulationEngine = new SimulationEngine(simulatorRegistry, matchEventProducer);
    }

    /**
     * Test the {@link SimulationEngine#simulateEvent(SimulationMatchState)} method of the SimulationEngine class.
     *
     * <p>This test verifies that the correct methods are called in the expected order when simulating a match event.</p>
     */
    @Test
    public void testSimulateEvent() {
        Sport sport = Sport.SOCCER;
        MatchEvent matchEvent = mock(MatchEvent.class);
        SimulationMatchState simulationMatchState = mock(SimulationMatchState.class);
        Match match = mock(Match.class);
        InOrder inOrder = inOrder(
                simulationMatchState,
                match,
                simulatorRegistry,
                simulator,
                matchEventProducer
        );

        when(simulationMatchState.getMatch()).thenReturn(match);
        when(match.sport()).thenReturn(sport);
        when(simulatorRegistry.getSimulator(any())).thenReturn(simulator);
        when(simulator.generateEvent(any())).thenReturn(matchEvent);

        // Assert no exceptions are thrown during the simulation of the event.
        assertDoesNotThrow(() ->
                simulationEngine.simulateEvent(simulationMatchState)
        );

        //
        // Assert functions were called in the correct order.
        //
        inOrder.verify(simulationMatchState).getMatch();
        inOrder.verify(match).sport();
        inOrder.verify(simulatorRegistry).getSimulator(sport);
        inOrder.verify(simulator).generateEvent(simulationMatchState);
        inOrder.verify(simulationMatchState).advanceClock(matchEvent);
        inOrder.verify(matchEventProducer).publishMatchEvent(matchEvent);
    }
}
