package com.streaming.simulation_service.factory.matchProgress.soccer;

import com.streaming.library.enums.Sport;
import com.streaming.simulation_service.factory.matchprogress.soccer.SoccerMatchProgressFactory;
import com.streaming.simulation_service.factory.matchprogress.MatchProgressFactory;
import com.streaming.simulation_service.progress.soccer.SoccerMatchProgress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the {@link SoccerMatchProgressFactory} class.
 *
 * @see SoccerMatchProgressFactory
 * @see MatchProgressFactory
 */
@ExtendWith(MockitoExtension.class)
public class SoccerMatchProgressFactoryTest {

    /**
     * The {@link SoccerMatchProgressFactory} instance under test.
     */
    SoccerMatchProgressFactory soccerMatchProgressFactory;

    /**
     * Set up the test environment before each test case.
     */
    @BeforeEach
    public void setUp() {
        soccerMatchProgressFactory = new SoccerMatchProgressFactory();
    }

    /**
     * Test and validate the {@link SoccerMatchProgressFactory#getSport()} method returns the expected {@link Sport#SOCCER} value.
     */
    @Test
    public void testGetSport() {
        assertEquals(Sport.SOCCER, soccerMatchProgressFactory.getSport());
    }

    /**
     * Test and validate the {@link SoccerMatchProgressFactory#createMatchProgress()} method returns a {@link SoccerMatchProgress} instance.
     */
    @Test
    public void testCreateSoccerMatchProgress() {
        assertEquals(SoccerMatchProgress.class, soccerMatchProgressFactory.createMatchProgress().getClass());
    }
}
