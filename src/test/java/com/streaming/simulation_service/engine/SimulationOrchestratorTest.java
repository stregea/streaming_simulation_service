package com.streaming.simulation_service.engine;

import com.streaming.library.enums.Sport;
import com.streaming.library.model.record.Match;
import com.streaming.simulation_service.registry.ActiveMatchesRegistry;
import com.streaming.simulation_service.state.SimulationMatchState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the {@link SimulationOrchestrator} class.
 *
 * @see SimulationOrchestrator
 */
@ExtendWith(MockitoExtension.class)
public class SimulationOrchestratorTest {

    /**
     * Mocked {@link ActiveMatchesRegistry} object.
     */
    @Mock
    ActiveMatchesRegistry registry;

    /**
     * Mocked {@link SimulationLifecycleManager} object.
     */
    @Mock
    SimulationLifecycleManager lifecycleManager;

    /**
     * Mocked {@link SimulationEngine} object.
     */
    @Mock
    SimulationEngine simulationEngine;

    /**
     * The {@link SimulationOrchestrator} instance under test.
     */
    SimulationOrchestrator orchestrator;

    /**
     * Set up the test environment before each test case.
     */
    @BeforeEach
    public void setUp() {
        // Initialize the SimulationOrchestrator with mocked dependencies.
        orchestrator = new SimulationOrchestrator(registry, lifecycleManager, simulationEngine);
    }

    /**
     * Test the {@link SimulationOrchestrator#simulate()} method when a match is active.
     */
    @Test
    public void testSimulate() {
        Match match = mock(Match.class);
        SimulationMatchState matchState = mock(SimulationMatchState.class);
        InOrder inOrder = inOrder(
                simulationEngine
        );

        when(registry.getRandomMatch()).thenReturn(Optional.ofNullable(matchState));
        when(matchState.getMatch()).thenReturn(match);
        when(match.id()).thenReturn(UUID.randomUUID());
        when(match.sport()).thenReturn(Sport.SOCCER);
        when(matchState.isMatchOver()).thenReturn(false);

        // Assert no exceptions are thrown.
        assertDoesNotThrow(() ->
                orchestrator.simulate()
        );

        //
        // Assert functions were called in the correct order.
        //
        inOrder.verify(simulationEngine).simulateEvent(matchState);

        // Since the match is not over, replaceMatch should not be called.
        inOrder(lifecycleManager).verifyNoMoreInteractions();
    }

    /**
     * Test the {@link SimulationOrchestrator#simulate()} method when a match is over.
     */
    @Test
    public void testSimulateMatchOver() {
        Match match = mock(Match.class);
        SimulationMatchState matchState = mock(SimulationMatchState.class);
        InOrder inOrder = inOrder(
                simulationEngine,
                lifecycleManager
        );

        when(registry.getRandomMatch()).thenReturn(Optional.ofNullable(matchState));
        when(matchState.getMatch()).thenReturn(match);
        when(match.id()).thenReturn(UUID.randomUUID());
        when(match.sport()).thenReturn(Sport.SOCCER);
        when(matchState.isMatchOver()).thenReturn(true);

        // Assert no exceptions are thrown.
        assertDoesNotThrow(() ->
                orchestrator.simulate()
        );

        //
        // Assert functions were called in the correct order.
        //
        inOrder.verify(simulationEngine).simulateEvent(matchState);
        inOrder.verify(lifecycleManager).replaceMatch(matchState);
    }

    /**
     * Test the {@link SimulationOrchestrator#simulate()} method when there are no active matches.
     */
    @Test
    public void testSimulateNoActiveMatches() {
        when(registry.getRandomMatch()).thenReturn(Optional.empty());

        // Assert no exceptions are thrown when there are no active matches.
        assertDoesNotThrow(() ->
                orchestrator.simulate()
        );

        // Verify that simulateEvent and replaceMatch were never called.
        inOrder(simulationEngine, lifecycleManager).verifyNoMoreInteractions();
    }
}
