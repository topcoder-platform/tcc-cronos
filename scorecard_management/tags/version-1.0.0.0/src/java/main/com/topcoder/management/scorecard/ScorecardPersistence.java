/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.scorecard;

import com.topcoder.management.scorecard.data.QuestionType;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.ScorecardStatus;
import com.topcoder.management.scorecard.data.ScorecardType;

/**
 * This interface defines the contract that any scorecard persistence must implement. The implementation classes will
 * be used by ScorecardManagerImpl to performs scorecard persistence operations. The implementation classes should
 * have a constructor that receive a namespace string parameter so that they're exchangeable with each other by
 * changing configuration settings in the manager.
 * @author tuenm, zhuzeyuan
 * @version 1.0
 */
public interface ScorecardPersistence {
    /**
     * Create the scorecard in the persistence using the given scorecard instance. The scorecard instance can include
     * sub items such as groups, sections and questions. Those sub items will be persisted as well. The operator
     * parameter is used as the creation/modification user of the scorecard and its subitems. The creation date and
     * modification date will be the current date time when the scorecard is created.
     * @param scorecard
     *            The scorecard instance to be created in the persistence.
     * @param operator
     *            The creation user of this scorecard.
     * @throws IllegalArgumentException
     *             if any input is null or the operator is empty string.
     * @throws PersistenceException
     *             if error occurred while accessing the persistence.
     */
    public void createScorecard(Scorecard scorecard, String operator) throws PersistenceException;

    /**
     * Update the given scorecard instance into the persistence. The scorecard instance can include sub items such as
     * groups, sections and questions. Those sub items will be updated as well. If sub items are removed from the
     * scorecard, they will be deleted from the persistence. Likewise, if new sub items are added, they will be
     * created in the persistence. The operator parameter is used as the modification user of the scorecard and its
     * subitems. The modification date will be the current date time when the scorecard is updated.
     * @param scorecard
     *            The scorecard instance to be updated into the persistence.
     * @param operator
     *            The modification user of this scorecard.
     * @throws IllegalArgumentException
     *             if any input is null or the operator is empty string.
     * @throws PersistenceException
     *             if error occurred while accessing the persistence.
     */
    public void updateScorecard(Scorecard scorecard, String operator) throws PersistenceException;

    /**
     * Retrieves the scorecard instance from the persistence given its id. The scorecard instance can be retrieved
     * with or without its sub items, depends on the 'complete' parameter.
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
    public Scorecard getScorecard(long id, boolean complete) throws PersistenceException;

    /**
     * Retrieves all the scorecard types from the persistence.
     * @return An array of scorecard type instances.
     * @throws PersistenceException
     *             if error occurred while accessing the persistence.
     */
    public ScorecardType[] getAllScorecardTypes() throws PersistenceException;

    /**
     * Retrieves all the question types from the persistence.
     * @return An array of question type instances.
     * @throws PersistenceException
     *             if error occurred while accessing the persistence.
     */
    public QuestionType[] getAllQuestionTypes() throws PersistenceException;

    /**
     * Retrieves all the scorecard statuses from the persistence.
     * @return An array of scorecard status instances.
     * @throws PersistenceException
     *             if error occurred while accessing the persistence.
     */
    public ScorecardStatus[] getAllScorecardStatuses() throws PersistenceException;

    /**
     * <p>
     * Retrieves an array of the scorecard instances from the persistence given their ids. The scorecard instances can
     * be retrieved with or without its sub items, depends on the 'complete' parameter.
     * </p>
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
    public Scorecard[] getScorecards(long[] ids, boolean complete) throws PersistenceException;
}
