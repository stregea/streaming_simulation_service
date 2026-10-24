package com.streaming.simulation_service.engine;

import com.streaming.library.model.record.Match;
import com.streaming.library.model.record.Team;
import com.streaming.simulation_service.factory.matchprogress.MatchProgressFactory;
import com.streaming.simulation_service.factory.team.TeamFactory;
import com.streaming.simulation_service.progress.MatchProgress;
import com.streaming.simulation_service.registry.ActiveMatchesRegistry;
import com.streaming.simulation_service.registry.MatchProgressRegistry;
import com.streaming.simulation_service.registry.TeamFactoryRegistry;
import com.streaming.simulation_service.state.SimulationMatchState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the {@link SimulationLifecycleManager} class.
 *
 * @see SimulationLifecycleManager
 */
@ExtendWith(MockitoExtension.class)
public class SimulationLifecycleManagerTest {

    /**
     * Mocked {@link ActiveMatchesRegistry} object.
     */
    @Mock
    ActiveMatchesRegistry registry;

    /**
     * Mocked {@link TeamFactoryRegistry} object.
     */
    @Mock
    TeamFactoryRegistry teamFactoryRegistry;

    /**
     * Mocked {@link MatchProgressRegistry} object.
     */
    @Mock
    MatchProgressRegistry matchProgressRegistry;

    /**
     * The {@link SimulationLifecycleManager} instance under test.
     */
    SimulationLifecycleManager lifecycleManager;

    /**
     * Set up the test environment before each test case.
     */
    @BeforeEach
    public void setUp() {
        lifecycleManager = new SimulationLifecycleManager(registry, teamFactoryRegistry, matchProgressRegistry);
    }

    /**
     * Test the {@link SimulationLifecycleManager#replaceMatch(SimulationMatchState)} method.
     */
    @Test
    public void testReplaceMatch() {
        Match match = new Match(UUID.randomUUID(), null, null, null);
        SimulationMatchState matchState = new SimulationMatchState();
        MatchProgressFactory matchProgressFactory = mock(MatchProgressFactory.class);
        TeamFactory teamFactory = mock(TeamFactory.class);

        // Using thenAnswer since two teams are created within createRandomMatch(), this creates separate mock teams.
        when(teamFactoryRegistry.getTeamFactory(any())).thenReturn(teamFactory);
        when(teamFactory.createTeam()).thenAnswer(i -> mock(Team.class));

        when(matchProgressRegistry.getMatchProgressFactory(any())).thenReturn(matchProgressFactory);
        when(matchProgressFactory.createMatchProgress()).thenReturn(mock(MatchProgress.class));

        // Set the match in the matchState to ensure it is not null.
        matchState.setMatch(match);

        // Add the matchState to the registry before replacing it.
        registry.addMatch(matchState);

        // Call the replaceMatch method to replace the match.
        lifecycleManager.replaceMatch(matchState);

        // Assert the registry no longer contains the original match after replacement.
        assertFalse(registry.getMatch(matchState.getMatch().id()).isPresent());
    }

    /**
     * Test the {@link SimulationLifecycleManager#replaceMatch(SimulationMatchState)} method when passing in {@code null}.
     */
    @Test
    public void testReplaceMatchWithNull() {
        // Call the replaceMatch method with a null matchState.
        lifecycleManager.replaceMatch(null);

        // Verify that no interactions occurred with the registry, teamFactoryRegistry, or matchProgressRegistry.
        inOrder(
                registry,
                teamFactoryRegistry,
                matchProgressRegistry
        ).verifyNoMoreInteractions();
    }
}
