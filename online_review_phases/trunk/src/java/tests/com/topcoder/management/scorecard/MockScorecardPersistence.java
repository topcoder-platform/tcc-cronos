/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.scorecard;

import com.topcoder.management.scorecard.data.QuestionType;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.ScorecardStatus;
import com.topcoder.management.scorecard.data.ScorecardType;

/**
 * A simple mock instance of ScorecardPersistence. Nothing will be done in this class, except some parameters will be
 * recorded while calling those methods.
 * @author zhuzeyuan
 * @version 1.0
 */
public class MockScorecardPersistence implements ScorecardPersistence {

    /**
     * Default constructor, and reset all the backup fields.
     */
    public MockScorecardPersistence() {
    }

    /**
     * Constructor with namespace.
     * @param ns
     *            the given namespace
     */
    public MockScorecardPersistence(String ns) {
    }

    /**
     * Should be used to create a scorecard.
     * @param scorecard
     *            The scorecard instance to be created in the persistence.
     * @param operator
     *            The creation user of this scorecard.
     */
    public void createScorecard(Scorecard scorecard, String operator) {
        Helper.assertObjectNotNull(scorecard, "scorecard");
        Helper.assertStringNotEmpty(operator, "operator");
    }

    /**
     * Should be used to update a scorecard.
     * @param scorecard
     *            The scorecard instance to be updated into the persistence.
     * @param operator
     *            The modification user of this scorecard.
     */
    public void updateScorecard(Scorecard scorecard, String operator) {
        Helper.assertObjectNotNull(scorecard, "scorecard");
        Helper.assertStringNotEmpty(operator, "operator");
    }

    /**
     * Should be used to get a scorecard. The parameters will be recorded.
     * @return The scorecard instance.
     * @param id
     *            The id of the scorecard to be retrieved.
     * @param complete
     *            Indicates whether to retrieve the scorecard including its sub items.
     */
    public Scorecard getScorecard(long id, boolean complete) {
        ScorecardManagerImplTest.setId(id);
        ScorecardManagerImplTest.setComplete(complete);
        return null;
    }

    /**
     * Should be used to get all the scorecard types. A sample return value will be fecthed from
     * ScorecardManagerImplTest class, and returned.
     * @return An array of scorecard type instances.
     */
    public ScorecardType[] getAllScorecardTypes() {
        return ScorecardManagerImplTest.getReturnScorecardTypes();
    }

    /**
     * Should be used to get all the question types. A sample return value will be fecthed from
     * ScorecardManagerImplTest class, and returned.
     * @return An array of question type instances.
     */
    public QuestionType[] getAllQuestionTypes() {
        return ScorecardManagerImplTest.getReturnQuestionTypes();
    }

    /**
     * Should be used to get all the scorecard statuses. A sample return value will be fecthed from
     * ScorecardManagerImplTest class, and returned.
     * @return An array of scorecard status instances.
     */
    public ScorecardStatus[] getAllScorecardStatuses() {
        return ScorecardManagerImplTest.getReturnScorecardStatuses();
    }

    /**
     * Should be used to retrieve a bunch of scorecards. The parameters will be recorded.
     * @param ids
     *            The array of ids of the scorecards to be retrieved.
     * @param complete
     *            Indicates whether to retrieve the scorecard including its sub items.
     * @return An array of scorecard instances.
     */
    public Scorecard[] getScorecards(long[] ids, boolean complete) {
        ScorecardManagerImplTest.setIds(ids);
        ScorecardManagerImplTest.setComplete(complete);
        Helper.assertObjectNotNull(ids, "ids");
        if (ids.length == 0) {
            throw new IllegalArgumentException("ids must contain at least one element");
        }
        for (int i = 0; i < ids.length; i++) {
            Helper.assertIntegerGreaterThanZero(ids[i], "Element in ids");
        }
        return null;
    }
}
