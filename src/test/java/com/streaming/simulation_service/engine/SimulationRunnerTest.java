package com.streaming.simulation_service.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

/**
 * Unit tests for the {@link SimulationRunner} class.
 *
 * @see SimulationRunner
 */
@ExtendWith(MockitoExtension.class)
public class SimulationRunnerTest {

    /**
     * Mocked {@link SimulationOrchestrator} object.
     */
    @Mock
    SimulationOrchestrator simulationOrchestrator;

    /**
     * The {@link SimulationRunner} instance under test.
     */
    SimulationRunner simulationRunner;

    /**
     * Set up the test environment before each test case.
     */
    @BeforeEach
    public void setUp() {
        simulationRunner = new SimulationRunner(simulationOrchestrator);
    }

    /**
     * Test the {@link SimulationRunner#tick()} method.
     *
     * <p>This test verifies that when the {@code tick()} method is called, it invokes the
     * {@link SimulationOrchestrator#simulate()} method. </p>
     */
    @Test
    public void testTick() {
        simulationRunner.tick();

        // Verify that the simulate method of SimulationOrchestrator is called.
        verify(simulationOrchestrator).simulate();
    }
}
