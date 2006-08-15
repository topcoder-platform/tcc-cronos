/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.failuretests;

import com.topcoder.management.scorecard.ScorecardPersistence;
import com.topcoder.management.scorecard.data.QuestionType;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.ScorecardStatus;
import com.topcoder.management.scorecard.data.ScorecardType;
import com.topcoder.management.scorecard.PersistenceException;

/**
 * This mock ScorecardPersistence is used in the test.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockScorecardPersistence implements ScorecardPersistence {
    /**
     * constructor. do nothing.
     * @param namespace
     *            namespace of ScorecardPersistence
     */
    public MockScorecardPersistence(String namespace) {
    }

    /**
     * Check if the object is null.
     * @param obj
     *            the object to check.
     * @param name
     *            the object's name
     * @throws IllegalArgumentException
     *             if the object is null.
     */
    private void assertObjectNotNull(Object obj, String name) {
        if (obj == null) {
            throw new IllegalArgumentException("the object " + name + " should not be null.");
        }
    }

    /**
     * Check if the string is empty.
     * @param str
     *            the string to check.
     * @param name
     *            the string's name.
     * @throws IllegalArgumentException
     *             if the object is empty.
     */
    private void assertStringNotEmpty(String str, String name) {
        assertObjectNotNull(str, name);
        if (str.trim().length() == 0) {
            throw new IllegalArgumentException("the string " + name + " should not be empty.");
        }
    }

    /**
     * Check if the integer is greater than zero.
     * @param number
     *            the integer number to check.
     * @param name
     *            the integer's name.
     * @throws IllegalArgumentException
     *             if the number if less than or equals to zero.
     */
    private void assertIntegerGreaterThanZero(long number, String name) {
        if (number <= 0) {
            throw new IllegalArgumentException(name + " must be positive.");
        }
    }

    /**
     * do nothing.
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
        assertObjectNotNull(scorecard, "scorecard");
        assertStringNotEmpty(operator, "operator");
    }

    /**
     * do nothing.
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
        assertObjectNotNull(scorecard, "scorecard");
        assertStringNotEmpty(operator, "operator");
    }

    /**
     * do nothing.
     * @return The scorecard instance.
     * @param id
     *            The id of the scorecard to be retrieved.
     * @param complete
     *            Indicates whether to retrieve the scorecard including its sub items.
     * @throws IllegalArgumentException
     *             if the input id is less than or equal to zero.
     * @throws PersistenceException
     *             if error occurred while accessing the persistence.
     */
    public Scorecard getScorecard(long id, boolean complete) throws PersistenceException {
        assertIntegerGreaterThanZero(id, "id");
        return null;
    }

    /**
     * do nothing.
     * @return An array of scorecard type instances.
     * @throws PersistenceException
     *             if error occurred while accessing the persistence.
     */
    public ScorecardType[] getAllScorecardTypes() throws PersistenceException {
        return null;
    }

    /**
     * do nothing.
     * @return An array of question type instances.
     * @throws PersistenceException
     *             if error occurred while accessing the persistence.
     */
    public QuestionType[] getAllQuestionTypes() throws PersistenceException {
        return null;
    }

    /**
     * do nothing.
     * @return An array of scorecard status instances.
     * @throws PersistenceException
     *             if error occurred while accessing the persistence.
     */
    public ScorecardStatus[] getAllScorecardStatuses() throws PersistenceException {
        return null;
    }

    /**
     * do nothing.
     * @param ids
     *            The array of ids of the scorecards to be retrieved.
     * @param complete
     *            Indicates whether to retrieve the scorecard including its sub items.
     * @return An array of scorecard instances.
     * @throws IllegalArgumentException
     *             if the ids is less than or equal to zero. Or the input array is null or empty.
     * @throws PersistenceException
     *             if error occurred while accessing the persistence.
     */
    public Scorecard[] getScorecards(long[] ids, boolean complete) throws PersistenceException {
        assertObjectNotNull(ids, "ids");
        if (ids.length == 0) {
            throw new IllegalArgumentException("ids must contain at least one element");
        }
        for (int i = 0; i < ids.length; i++) {
            assertIntegerGreaterThanZero(ids[i], "Element in ids");
        }
        return null;
    }
}
