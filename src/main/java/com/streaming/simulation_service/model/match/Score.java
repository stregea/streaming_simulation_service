package com.streaming.simulation_service.model.match;

import com.streaming.simulation_service.model.team.Team;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that represents score of an in-progress match.
 *
 * <p>This class stores integer scores keyed by {@link Team} and provides convenience
 * methods to add points for a team and to retrieve the current score. Values are
 * initialized to {@code 0} when a team has not yet been scored.</p>
 *
 * @see Team
 */
public class Score {

    /**
     * {@link HashMap} of team -> score.
     */
    private final Map<Team, Integer> scores;

    /**
     * Construct a brand new {@code Score} object with an empty score map. All teams will have a default score of {@code 0} until points are added.
     * @param teams The list of teams in the match.
     */
    public Score(List<Team> teams) {
        this.scores = new HashMap<>();

        for (Team team : teams) {
            this.scores.put(team, 0);
        }
    }

    /**
     * Add points to the supplied team's score.
     *
     * <p>This method performs a merge operation: if the team already has a score,
     * the supplied {@code points} value is added to the existing total. If the team
     * has no recorded score yet, the supplied value becomes the initial score.</p>
     *
     * <p>Negative values are allowed and will reduce the team's score accordingly.</p>
     *
     * @param team   The {@link Team} to credit points to.
     * @param points The number of points to add.
     * @throws NullPointerException if {@code team} or {@code points} is null.
     */
    public void addPoints(Team team, Integer points) {
        if (team == null) {
            throw new NullPointerException("Team must not be null.");
        }
        if (points == null) {
            throw new NullPointerException("Points must not be null.");
        }
        scores.merge(team, points, Integer::sum);
    }

    /**
     * Return the current score for the given team.
     *
     * @param team The {@link Team} whose score to retrieve.
     * @return The current score for the specified {@link Team}.
     */
    public Integer getScore(Team team) {
        return scores.getOrDefault(team, 0);
    }
}
