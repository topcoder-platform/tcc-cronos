/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.failuretests;

import javax.persistence.EntityManager;

import com.topcoder.service.digitalrun.points.DigitalRunPointsManagerPersistenceException;
import com.topcoder.service.digitalrun.points.dao.implementations.AbstractDAO;

/**
 * Mock class extends AbstractDAO for test.
 *
 * @author extra
 * @version 1.0
 */
public class MockAbstractDAO extends AbstractDAO {

    /**
     * Creates an EntityManager instance and returns it.
     *
     * @throws DigitalRunPointsManagerPersistenceException
     *             if the retrieval of the EntityManager failed
     * @return an EntityManager instance
     */
    public EntityManager getEntityManager() throws DigitalRunPointsManagerPersistenceException {
        return super.getEntityManager();
    }
}
