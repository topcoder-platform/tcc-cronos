/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic.accuracytests;

import java.util.Date;

import com.topcoder.database.statustracker.Entity;
import com.topcoder.database.statustracker.EntityKey;
import com.topcoder.database.statustracker.EntityStatus;
import com.topcoder.database.statustracker.Status;
import com.topcoder.database.statustracker.persistence.EntityStatusTracker;
import com.topcoder.database.statustracker.persistence.StatusTrackerPersistenceException;

/**
 * Mock EntityStatusTracker for tesing purposes.
 *
 * @author mittu
 * @version 1.0
 *
 */
public class MockEntityStatusTracker implements EntityStatusTracker {
    private EntityStatus entityStatus;

    public MockEntityStatusTracker() {
        entityStatus = new EntityStatus(null, new Status(201), new Date(), "accuracy_user");
    }

    public EntityStatus[] findByStatus(Entity type, Status[] statuses)
        throws StatusTrackerPersistenceException {
        return new EntityStatus[0];
    }

    public EntityStatus getCurrentStatus(EntityKey instance) throws StatusTrackerPersistenceException {
        return entityStatus;
    }

    public EntityStatus[] getStatusHistory(EntityKey instance) throws StatusTrackerPersistenceException {
        return new EntityStatus[0];
    }

    public EntityStatus setStatus(EntityKey instance, Status newStatus, String userName)
        throws StatusTrackerPersistenceException {
        return entityStatus;
    }
}