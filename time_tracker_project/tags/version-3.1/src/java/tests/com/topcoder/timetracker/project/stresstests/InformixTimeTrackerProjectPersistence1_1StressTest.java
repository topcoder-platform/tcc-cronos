/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.stresstests;

import com.topcoder.timetracker.project.Client;
import com.topcoder.timetracker.project.Project;
import com.topcoder.timetracker.project.persistence.InformixTimeTrackerProjectPersistence;
import com.topcoder.timetracker.project.persistence.PersistenceException;
import com.topcoder.timetracker.project.persistence.TimeTrackerProjectPersistence;
import com.topcoder.timetracker.project.searchfilters.ValueFilter;
import com.topcoder.timetracker.project.searchfilters.Filter;
import com.topcoder.timetracker.project.searchfilters.CompareOperation;

import junit.framework.TestCase;

/**
 * <p>
 * Test <code>InformixTimeTrackerProjectPersistenceStressTest.java</code> class.
 * </p>
 *
 * @author nhzp339
 * @author kr00tki
 * @version 2.0
 * @since 1.1
 */
public class InformixTimeTrackerProjectPersistence1_1StressTest extends TestCase {
    /**
     * The namespace of <code>DBConnectionFactory</code>.
     */
    private static final String DB_NS = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * <p>
     * Represents an instance of InformixTimeTrackerProjectPersistence to be used for testing.
     * </p>
     */
    private TimeTrackerProjectPersistence persistence = null;

    /**
     * The number of iterations to run each stress test.
     */
    private static final int ITERATION = 3;

    /**
     * Setup the test environment, it will remove the namespace first of <code>DBConnectionFactory</code>, and
     * load it. Next, the <code>InformixTimeTrackerProjectPersistence</code> instance will be initialized. And
     * all the tables in db will be clear.
     *
     * @throws Exception Exception thrown from unit tests.
     */
    protected void setUp() throws Exception {

        StressHelper1_1.loadAllConfiguration();
        persistence = new InformixTimeTrackerProjectPersistence(DB_NS);

        StressHelper1_1.clearTables();
    }

    /**
     * Remove all the namespaces and clear all the tables in db.
     */
    protected void tearDown() throws Exception {
        persistence.closeConnection();
        StressHelper1_1.clearTables();
        StressHelper1_1.releaseNamespaces();
    }

    /**
     * <p>
     * Test the method addProjects.
     * </p>
     *
     * @throws Exception Exception thrown from unit tests.
     */
    public void testAddProjects() throws Exception {
        Project[] projects = new Project[ITERATION];
        for (int i = 0; i < ITERATION; ++i) {
            projects[i] = StressHelper1_1.createProject(i + 1);
        }
        this.persistence.addProjects(projects, true);
        for (int i = 0; i < ITERATION; ++i) {
            assertEquals(persistence.getProject(i + 1).getName().trim(), "name" + (i + 1));
        }
    }

    /**
     * <p>
     * Test the method addClients.
     * </p>
     *
     * @throws Exception Exception thrown from unit tests.
     */
    public void testAddClients() throws Exception {
        Client[] clients = new Client[ITERATION];
        for (int i = 0; i < ITERATION; ++i) {
            clients[i] = StressHelper1_1.createClient(i + 1);
        }
        this.persistence.addClients(clients, true);
        for (int i = 0; i < ITERATION; ++i) {
            assertEquals(persistence.getClient(i + 1).getName().trim(), "name" + (i + 1));
        }
    }

    /**
     * <p>
     * Test the method searchForProjects.
     * </p>
     *
     * @throws Exception Exception thrown from unit tests.
     */
    public void testSearchForProjects() throws Exception {
        Project[] projects = new Project[ITERATION];
        for (int i = 0; i < ITERATION; ++i) {
            projects[i] = StressHelper1_1.createProject(i + 1);
        }
        this.persistence.addProjects(projects, true);
        Filter filter = new ValueFilter(CompareOperation.LIKE, "Name", "name111");
        this.persistence.searchForProjects(filter);
    }

    /**
     * <p>
     * Test the method searchForClients.
     * </p>
     *
     * @throws Exception Exception thrown from unit tests.
     */
    public void testSearchForClients() throws Exception {
        Client[] clients = new Client[ITERATION];
        for (int i = 0; i < ITERATION; ++i) {
            clients[i] = StressHelper1_1.createClient(i + 1);
        }
        this.persistence.addClients(clients, true);
        Filter filter = new ValueFilter(CompareOperation.LIKE, "Name", "name111");
        this.persistence.searchForClients(filter);
    }

    // since 2.0

    /**
     * This is a multi threated test that creates n threads and each thread has it's own persistence object
     * on which basic operations are performed.
     *
     *
     * @throws Exception to JUnit.
     */
    public void testMultiplePersistences() throws Exception {
        int count = 2;
        InformixTimeTrackerProjectPersistence[] persitences = new InformixTimeTrackerProjectPersistence[count];
        for (int i = 0; i < count; i++) {
            persitences[i] = new InformixTimeTrackerProjectPersistence(DB_NS);
        }

        Worker[] workers = new Worker[count];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Worker(persitences[i], i * count, 10);
        }

        for (int i = 0; i < workers.length; i++) {
            workers[i].start();
            //workers[i].join();
        }

        for (int i = 0; i < workers.length; i++) {
            workers[i].join();
        }

        for (int i = 0; i < workers.length; i++) {
            assertNull("Thread " + i + " faild. " + workers[i].getError(), workers[i].getError());
        }
    }

    /**
     * The private worker thread class. It takes the InformixTimeTrackerProjectPersistence reference and
     * performs operations on it.
     *
     * @author kr00tki
     * @version 2.0
     */
    private class Worker extends Thread {
        /**
         * The persistence object.
         */
        private InformixTimeTrackerProjectPersistence persistence = null;
        /**
         * The id start value.
         */
        private int start = 0;

        /**
         * The count start value.
         */
        private int count = 0;

        /**
         * The error that happen during thread work.
         */
        private Throwable error = null;

        /**
         * Creates new Worker instance.
         *
         * @param persistence the persistence object to test on.
         * @param start the start id value.
         * @param count number of iterations.
         */
        public Worker(InformixTimeTrackerProjectPersistence persistence, int start, int count) {
            this.persistence = persistence;
            this.start = start;
            this.count = count;
        }

        /**
         * It calls various methods on the <code>persistence</code> object, <code>count</code> times.
         */
        public void run() {
            try {
                for (int i = 0; i < count; i++) {

                    this.persistence.addClient(StressHelper1_1.createClient(i + start));
                    this.persistence.addProject(StressHelper1_1.createProject(i + start));
                    this.persistence.removeClient(i + start);
                    this.persistence.removeProject(i + start);
                }
            } catch (Exception ex) {
                error = ex;
                ex.printStackTrace();
            } finally {
                try {
                    persistence.closeConnection();
                } catch (PersistenceException ex) {
                    ex.printStackTrace();
                    if (error == null) {
                        error = ex;
                    }
                }
            }
        }

        /**
         * Returns the error that happend during thread work.
         *
         * @return the error exception, or null, if nothing wrong happen.
         */
        public Throwable getError() {
            return error;
        }
    }
}
