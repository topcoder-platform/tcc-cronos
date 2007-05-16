/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic.stresstests;

import com.topcoder.database.statustracker.EntityKey;
import com.topcoder.database.statustracker.Status;
import com.topcoder.database.statustracker.Entity;
import com.topcoder.database.statustracker.EntityStatus;
import com.topcoder.database.statustracker.persistence.StatusTrackerPersistenceException;
import com.topcoder.database.statustracker.persistence.EntityStatusTracker;
import java.util.Date;
import java.util.HashMap;

/**
 * <p>
 * A mock EntityStatusTracker implementation used in the tests.
 * </p>
 * 
 * <p>
 * <b>Thread Safety:</b> This class is thread safe.
 * </p>
 * 
 * @author kaqi072821
 * @version 1.0
 */
public class MockEntityStatusTracker implements EntityStatusTracker {

    /**
     * Entity status.
     */
    private EntityStatus es;

    /**
     * Constructor.
     */
    public MockEntityStatusTracker() {
        Entity entity = new Entity(1, "name", new String[] { "a" }, new Status[] {
                new Status(TestHelper.SESSION_STATUS_CLOSE), new Status(1), new Status(2), new Status(3),
                new Status(123) });
        HashMap map = new HashMap();
        map.put("a", "a");
        EntityKey key = new EntityKey(entity, map);
        es = new EntityStatus(key, new Status(TestHelper.SESSION_STATUS_CLOSE), new Date(), "user");
    }

    /**
     * <p>
     * Sets the current status of the given Entity instance to the given status. In addition, the user who
     * made the change is recorded. The inactivation date/time of the previously current status of the given
     * Entity instance is set to the current date/time.
     * </p>
     * 
     * <p>
     * Note, setting the status to a previous value must add a new record to persistent storage and not merely
     * update the previous record, so this status will appear in the status history more than once.
     * </p>
     * 
     * @param instance
     *            the entity instance whose status is being set.
     * @param newStatus
     *            the new current status
     * @param userName
     *            the user who set the new status
     * @return an EntityStatus that describes the current Entity instance status.
     * 
     * @throws IllegalArgumentException
     *             if any argument is null, or if userName is empty after trim, or if the newStatus does not
     *             correspond to a valid status for the Entity type in the EntityKey instance.
     * @throws RecordNotFoundException
     *             if the Entity type within the entity instance was not previously registered
     * @throws StatusTrackerPersistenceException
     *             if the new status could not be saved, or if the previous status could not be inactivated.
     *             This exception will wrap the underlying exception.
     */
    public EntityStatus setStatus(EntityKey instance, Status newStatus, String userName)
            throws StatusTrackerPersistenceException {
        es = new EntityStatus(instance, newStatus, new Date(), "user");
        return es;
    }

    /**
     * <p>
     * Returns the current status of the given entity instance from persistent storage.
     * </p>
     * 
     * @return the current Entity Status for this instance. By definition, its inactivation date will be null.
     * @param instance
     *            the entity instance whose current Status we are interested in.
     * 
     * @throws IllegalArgumentException
     *             if instance is null
     * @throws RecordNotFoundException
     *             if instance has no status (i.e., it was not found in persistent storage), or if the Entity
     *             within the instance was not found in persistent storage.
     * @throws StatusTrackerPersistenceException
     *             if any other exception occurred while looking up the current status of this entity instance
     *             in persistent storage; will wrap the underlying exception.
     */
    public EntityStatus getCurrentStatus(EntityKey instance) throws StatusTrackerPersistenceException {
        return es;
    }

    /**
     * <p>
     * Returns the "status history" of the given entity instance. This means that every status this entity has
     * ever had, in chronological order, ending with the current status.
     * </p>
     * 
     * @param instance
     *            the entity instance whose status history is desired.
     * @return an array of EntityStatus objects, in inactivation date order from oldest to youngest; the last
     *         will be the current status of the given entity instance. May have zero entries, but must never
     *         be null.
     * 
     * @throws IllegalArgumentException
     *             if instance is null
     * @throws RecordNotFoundException
     *             if no history exists for the given entity instance (i.e. it was not found in persistent
     *             storage), or if the Entity with the instance was not found in persistent storage.
     * @throws StatusTrackerPersistenceException
     *             if any other exception occurred while looking up the statuses of this entity instance in
     *             persistent storage; will wrap the underlying exception.
     */
    public EntityStatus[] getStatusHistory(EntityKey instance) throws StatusTrackerPersistenceException {
        return new EntityStatus[0];
    }

    /**
     * <p>
     * Finds all instances of the given Entity type whose current status is in the given array of Status
     * objects.
     * </p>
     * 
     * @param type
     *            the Entity type whose current statuses is desired
     * @param statuses
     *            the Status values to search on.
     * @return an array of EntityStatus objects representing the Entity instances whose current status is in
     *         the array of given statuses. May have zero entries, but must never be null.
     * 
     * @throws IllegalArgumentException
     *             if either argument is null, or if statuses contains a null, or if any element of statuses
     *             is not a valid status for the given Entity type.
     * @throws RecordNotFoundException
     *             if the Entity type was not found in persistent storage.
     * @throws StatusTrackerPersistenceException
     *             if any other exception occurred while looking up the statuses of this Entity type in
     *             persistent storage; will wrap the underlying exception.
     */
    public EntityStatus[] findByStatus(Entity type, Status[] statuses)
            throws StatusTrackerPersistenceException {
        return new EntityStatus[0];
    }
}
