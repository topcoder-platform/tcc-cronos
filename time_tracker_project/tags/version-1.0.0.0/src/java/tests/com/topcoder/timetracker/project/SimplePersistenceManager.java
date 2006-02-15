/*
 * SimplePersistenceManager.java
 *
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.timetracker.project;

import com.topcoder.timetracker.project.persistence.TimeTrackerProjectPersistence;


/**
 * A simple implementation of the ProjectPersistenceManager for testing. It returns an instance of SimplePersistence in
 * the getPersistence() method.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SimplePersistenceManager extends ProjectPersistenceManager {
    /**
     * Represents an implementation of TimeTrackerProjectPersistence. This field will be used by the ClientUtility and
     * ProjectUtility classes.
     */
    private TimeTrackerProjectPersistence persistence = null;

    /**
     * Creates a new instance of SimplePersistenceManager. The namespaces will be ignored.
     *
     * @param namespace the namespace of the Time Tracker Project configuration file
     *
     * @throws ConfigurationException if the property is missing or invalid, or something goes wrong when reading the
     *         configuration file
     */
    public SimplePersistenceManager(String namespace) throws ConfigurationException {
        super(namespace);
        persistence = new SimplePersistence();
    }

    /**
     * Getter for the implementation of TimeTrackerProjectPersistence loaded by this manager. This will be of type
     * SimplePersistence.
     *
     * @return the implementation of TimeTrackerProjectPersistence
     */
    public TimeTrackerProjectPersistence getPersistence() {
        return persistence;
    }
}
