/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard;

import com.topcoder.management.scorecard.data.MockScorecardStatus;
import com.topcoder.management.scorecard.data.QuestionType;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.ScorecardStatus;
import com.topcoder.management.scorecard.data.ScorecardType;
import com.topcoder.search.builder.filter.Filter;

/**
 * A mock implementation for <code>ScorecardManager</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockScorecardManager implements ScorecardManager {

    /**
     * Create scorecard.
     * @param scorecard the scorecard
     * @param operator the operator
     */
    public void createScorecard(Scorecard scorecard, String operator) {
    }

    /**
     * Get all question type.
     * @return all question type
     */
    public QuestionType[] getAllQuestionTypes() {
        return null;
    }

    /**
     * Get all scorecard status.
     * @return all the scorecard status.
     */
    public ScorecardStatus[] getAllScorecardStatuses() {
        ScorecardStatus active = new MockScorecardStatus();
        active.setId(1);
        active.setName("Active");

        ScorecardStatus inactive = new MockScorecardStatus();
        inactive.setId(1);
        inactive.setName("Inactive");

        return new ScorecardStatus[] {active, inactive};
    }

    /**
     * Get all question type.
     * @return all question type.
     */
    public ScorecardType[] getAllScorecardTypes() {
        return null;
    }

    /**
     * Get scorecard by id.
     * @param id the score card id
     * @return the score card
     */
    public Scorecard getScorecard(long id) {
        if (id == 1) {
            Scorecard card = new Scorecard();
            card.setId(1);
            return card;
        }
        return null;
    }

    /**
     * Search scorecards.
     * @param filter the filter
     * @param complete whether complete
     * @return scorecards found
     */
    public Scorecard[] searchScorecards(Filter filter, boolean complete) {
        return null;
    }

    /**
     * Update scorecard.
     * @param scorecard the scorecard
     * @param operator the operator
     */
    public void updateScorecard(Scorecard scorecard, String operator) {
    }

}
