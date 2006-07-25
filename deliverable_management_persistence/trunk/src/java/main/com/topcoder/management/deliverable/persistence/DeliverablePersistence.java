/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.persistence;

import com.topcoder.management.deliverable.Deliverable;

/**
 * <p>
 * The DeliverablePersistence interface defines the methods for persisting and
 * retrieving the object model in this component.
 * </p>
 * <p>
 * This interface handles the persistence of the deliverable related classes
 * that make up the object model ¨C this consists only of the Deliverable class.
 * Unlike UploadPersistence, the DeliverablePersistence interface (currently)
 * has no support for adding items. Only retrieval is supported. This interface
 * is not responsible for searching the persistence for the various entities.
 * This is instead handled by a DeliverableManager implementation.
 * </p>
 * <p>
 * Thread Safety: Implementations of this interface are not required to be
 * thread-safe or immutable.
 * </p>
 * @author aubergineanode, TCSDEVELOPER
 * @version 1.0
 */
public interface DeliverablePersistence {
    /**
     * <p>
     * Loads the deliverables associated with the given deliverable id.
     * </p>
     * <p>
     * There may be more than one deliverable returned. If there is no matching
     * deliverable in the persistence, an empty array should be returned.
     * </p>
     * @return The matching deliverable (possibly expanded by matching with each
     *         active submission, if it is a "per submission" deliverable), or
     *         an empty array.
     * @param deliverableId
     *            The id of the deliverable
     * @throws IllegalArgumentException
     *             If deliverableId is <= 0
     * @throws DeliverablePersistenceException
     *             If there is an error when reading the persistence
     */
    public Deliverable[] loadDeliverables(long deliverableId)
        throws DeliverablePersistenceException;

    /**
     * <p>
     * Loads the deliverable associated with the given submission.
     * </p>
     * <p>
     * The deliverable must be a "per submission" deliverable and the given
     * submission must be "Active". If this is not the case, null is returned.
     * </p>
     * @return The deliverable, or null if there is no deliverable for the given
     *         id, or submission is not an 'Active' submission
     * @param deliverableId
     *            The id of the deliverable
     * @param submissionId
     *            The id of the submission the deliverable should be associated
     *            with
     * @throws IllegalArgumentException
     *             If deliverableId or submissionId is <= 0
     * @throws DeliverablePersistenceException
     *             If there is an error when reading the persistence data
     */
    public Deliverable loadDeliverable(long deliverableId, long submissionId)
        throws DeliverablePersistenceException;

    /**
     * <p>
     * Loads all Deliverables with the given ids from persistence.
     * </p>
     * <p>
     * May return a 0-length array.
     * </p>
     * @param deliverableIds
     *            The ids of deliverables to load
     * @return The loaded deliverables
     * @throws IllegalArgumentException
     *             if deliverableIds is null or any id is <= 0
     * @throws DeliverablePersistenceException
     *             if there is an error when reading the persistence data
     */
    public Deliverable[] loadDeliverables(long[] deliverableIds)
        throws DeliverablePersistenceException;

    /**
     * <p>
     * Loads per-submission deliverables that have the given deliverable id and
     * submission id.
     * </p>
     * <p>
     * The two input arrays must have the same length. Only "per-submission"
     * deliverables will be returned.
     * </p>
     * @param deliverableIds
     *            The ids of deliverables to load
     * @param submissionIds
     *            The ids ofthe submission for each deliverable
     * @return The loaded deliverables
     * @throws IllegalArgumentException
     *             If the two arguments do not have the same number of elements
     * @throws IllegalArgumentException
     *             if any array is null or any id (in either array) is <= 0
     * @throws DeliverablePersistenceException
     *             if there is an error when reading the persistence data
     */
    public Deliverable[] loadDeliverables(long[] deliverableIds, long[] submissionIds)
        throws DeliverablePersistenceException;
}
