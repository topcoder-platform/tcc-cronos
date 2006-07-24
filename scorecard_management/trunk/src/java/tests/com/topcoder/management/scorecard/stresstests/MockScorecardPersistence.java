/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.stresstests;

import com.topcoder.management.scorecard.ScorecardPersistence;
import com.topcoder.management.scorecard.data.QuestionType;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.ScorecardStatus;
import com.topcoder.management.scorecard.data.ScorecardType;
import com.topcoder.management.scorecard.persistence.PersistenceException;

/**
 * This mock ScorecardPersistence is used in the test.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockScorecardPersistence implements ScorecardPersistence {
    /**
     * constructor. do nothing.
     *
     * @param namespace
     *  namespace of ScorecardPersistence
     */
    public MockScorecardPersistence(String namespace) {
    }
    /**
     * do nothing.
     *
     * @param scorecard
     *            The scorecard instance to be created in the persistence.
     * @param operator
     *            The creation user of this scorecard.
     * @throws IllegalArgumentException
     *             if any input is null or the operator is empty string.
     * @throws PersistenceException
     *             if error occurred while accessing the persistence.
     */
    public void createScorecard(Scorecard scorecard, String operator) throws PersistenceException {
    }

    /**
     * do nothing.
     *
     * @param scorecard
     *            The scorecard instance to be updated into the persistence.
     * @param operator
     *            The modification user of this scorecard.
     * @throws IllegalArgumentException
     *             if any input is null or the operator is empty string.
     * @throws PersistenceException
     *             if error occurred while accessing the persistence.
     */
    public void updateScorecard(Scorecard scorecard, String operator) throws PersistenceException {
    }

    /**
     * do nothing.
     *
     * @return The scorecard instance.
     * @param id
     *            The id of the scorecard to be retrieved.
     * @param complete
     *            Indicates whether to retrieve the scorecard including its sub
     *            items.
     * @throws IllegalArgumentException
     *             if the input id is less than or equal to zero.
     * @throws PersistenceException
     *             if error occurred while accessing the persistence.
     */
    public Scorecard getScorecard(long id, boolean complete) throws PersistenceException {
        return null;
    }

    /**
     * do nothing.
     *
     * @return An array of scorecard type instances.
     * @throws PersistenceException
     *             if error occurred while accessing the persistence.
     */
    public ScorecardType[] getAllScorecardTypes() throws PersistenceException {
        return null;
    }

    /**
     * do nothing.
     *
     * @return An array of question type instances.
     * @throws PersistenceException
     *             if error occurred while accessing the persistence.
     */
    public QuestionType[] getAllQuestionTypes() throws PersistenceException {
        return null;
    }

    /**
     * do nothing.
     *
     * @return An array of scorecard status instances.
     * @throws PersistenceException
     *             if error occurred while accessing the persistence.
     */
    public ScorecardStatus[] getAllScorecardStatuses() throws PersistenceException {
        return null;
    }

    /**
     *  do nothing.
     *
     * @param ids
     *            The array of ids of the scorecards to be retrieved.
     * @param complete
     *            Indicates whether to retrieve the scorecard including its sub
     *            items.
     * @return An array of scorecard instances.
     * @throws IllegalArgumentException
     *             if the ids is less than or equal to zero. Or the input array
     *             is null or empty.
     * @throws PersistenceException
     *             if error occurred while accessing the persistence.
     */
    public Scorecard[] getScorecards(long[] ids, boolean complete) throws PersistenceException {
        return null;
    }
}
