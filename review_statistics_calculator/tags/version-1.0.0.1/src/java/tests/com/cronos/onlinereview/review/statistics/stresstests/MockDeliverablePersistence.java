/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics.stresstests;

import java.util.Date;

import com.topcoder.management.deliverable.Deliverable;
import com.topcoder.management.deliverable.persistence.DeliverablePersistence;
import com.topcoder.management.deliverable.persistence.DeliverablePersistenceException;

/**
 * Mock class for test.
 *
 * @author extra
 * @version 1.0
 */
public class MockDeliverablePersistence implements DeliverablePersistence {
    /**
     * Empty Constructor.
     */
    public MockDeliverablePersistence() {
        // does nothing
    }

    /**
     * Mock load deliverables.
     *
     * @return The matching deliverable (possibly expanded by matching with each active submission, if it is a
     *         "per submission" deliverable), or an empty array.
     * @param deliverableId
     *            The id of the deliverable
     * @param resourceId
     *            The resource id of deliverable
     * @param phaseId
     *            The phase id of the deliverable
     * @throws IllegalArgumentException
     *             If deliverableId is <= 0 or resourceId <=0
     * @throws DeliverablePersistenceException
     *             If there is an error reading the persistence
     */
    public Deliverable[] loadDeliverables(long deliverableId, long resourceId, long phaseId)
        throws DeliverablePersistenceException {
        return null;
    }

    /**
     * Mock load deliverable.
     *
     * @return The deliverable, or null if there is no deliverable for the given id, or submission is not an 'Active'
     *         submission
     * @param deliverableId
     *            The id of the deliverable
     * @param resourceId
     *            The resource id of deliverable
     * @param phaseId
     *            The phase id of the deliverable
     * @param submissionId
     *            The id of the submission the deliverable should be associated with
     * @throws IllegalArgumentException
     *             If deliverableId, resourceId or submissionId is <= 0
     * @throws DeliverablePersistenceException
     *             If there is an error reading the persistence data
     */
    public Deliverable loadDeliverable(long deliverableId, long resourceId, long phaseId, long submissionId)
        throws DeliverablePersistenceException {
        return null;
    }

    /**
     * Mock load all deliverables.
     *
     * @param deliverableIds
     *            The ids of deliverables to load
     * @param resourceIds
     *            The resource ids of deliverables to load
     * @param phaseIds
     *            The phase ids of the deliverables to load
     * @return The loaded deliverables
     * @throws IllegalArgumentException
     *             if any id is <= 0
     * @throws DeliverablePersistenceException
     *             if there is an error reading the persistence data
     */
    public Deliverable[] loadDeliverables(long[] deliverableIds, long[] resourceIds, long[] phaseIds)
        throws DeliverablePersistenceException {
        Deliverable deliverable = new Deliverable(11L, 13L, 13L, 10L, true);
        deliverable.setName("NAME");
        deliverable.setDescription("description");
        deliverable.setCreationUser("USER");
        deliverable.setCreationTimestamp(new Date());
        deliverable.setModificationUser("USER");
        deliverable.setModificationTimestamp(new Date());
        deliverable.setCompletionDate(new Date());

        return new Deliverable[] {deliverable };
    }

    /**
     * Mock load all deliverables.
     *
     * @param deliverableIds
     *            The ids of deliverables to load
     * @param resourceIds
     *            The resource ids of deliverables to load
     * @param phaseIds
     *            The phase ids of the deliverables to load
     * @param submissionIds
     *            The ids of the submission for each deliverable
     * @return The loaded deliverables
     * @throws IllegalArgumentException
     *             If the three arguments do not have the same number of elements
     * @throws IllegalArgumentException
     *             if any id (in either array) is <= 0
     * @throws DeliverablePersistenceException
     *             if there is an error reading the persistence data
     */
    public Deliverable[] loadDeliverables(long[] deliverableIds, long[] resourceIds, long[] phaseIds,
            long[] submissionIds) throws DeliverablePersistenceException {
        return null;
    }

}
