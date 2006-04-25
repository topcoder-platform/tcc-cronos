/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.project.stresstests;

import com.cronos.timetracker.project.Client;
import com.cronos.timetracker.project.Project;
import com.cronos.timetracker.project.persistence.InformixTimeTrackerProjectPersistence;
import com.cronos.timetracker.project.persistence.TimeTrackerProjectPersistence;
import com.cronos.timetracker.project.searchfilters.ValueFilter;
import com.cronos.timetracker.project.searchfilters.Filter;
import com.cronos.timetracker.project.searchfilters.CompareOperation;

import junit.framework.TestCase;

/**
 * <p>
 * Test <code>InformixTimeTrackerProjectPersistenceStressTest.java</code>
 * class.
 * </p>
 * 
 * @author nhzp339
 * @version 1.1
 */
public class InformixTimeTrackerProjectPersistence1_1StressTest extends
		TestCase {
	/**
	 * The namespace of <code>DBConnectionFactory</code>.
	 */
	private static final String DB_NS = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

	/**
	 * <p>
	 * Represents an instance of InformixTimeTrackerProjectPersistence to be
	 * used for testing.
	 * </p>
	 */
	private TimeTrackerProjectPersistence persistence = null;

    /**
     * The number of iterations to run each stress test.
     */
    private static final int ITERATION = 3000;

	/**
	 * Setup the test environment, it will remove the namespace first of
	 * <code>DBConnectionFactory</code>, and load it. Next, the
	 * <code>InformixTimeTrackerProjectPersistence</code> instance will be
	 * initialized. And all the tables in db will be clear.
	 * 
	 * @throws Exception
	 *             Exception thrown from unit tests.
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
	 * @throws Exception
	 *             Exception thrown from unit tests.
	 */
	public void testAddProjects() throws Exception {
		Project[] projects = new Project[ITERATION];
		for (int i = 0; i < ITERATION; ++i) {
			projects[i] = StressHelper1_1.createProject(i + 1);
		}
		this.persistence.addProjects(projects, true);
		for (int i = 0; i < ITERATION; ++i) {
			assertEquals(persistence.getProject(i + 1).getName().trim(), "name"
					+ (i + 1));
		}
	}

	/**
	 * <p>
	 * Test the method addClients.
	 * </p>
	 * 
	 * @throws Exception
	 *             Exception thrown from unit tests.
	 */
	public void testAddClients() throws Exception {
		Client[] clients = new Client[ITERATION];
		for (int i = 0; i < ITERATION; ++i) {
			clients[i] = StressHelper1_1.createClient(i + 1);
		}
		this.persistence.addClients(clients, true);
		for (int i = 0; i < ITERATION; ++i) {
			assertEquals(persistence.getClient(i + 1).getName().trim(), "name"
					+ (i + 1));
		}
	}

	/**
	 * <p>
	 * Test the method searchForProjects.
	 * </p>
	 * 
	 * @throws Exception
	 *             Exception thrown from unit tests.
	 */
	public void testSearchForProjects() throws Exception {
		Project[] projects = new Project[ITERATION];
		for (int i = 0; i < ITERATION; ++i) {
			projects[i] = StressHelper1_1.createProject(i + 1);
		}
		this.persistence.addProjects(projects, true);
		Filter filter = new ValueFilter(CompareOperation.LIKE, "Name",
				"name111");
		this.persistence.searchForProjects(filter);
	}

	/**
	 * <p>
	 * Test the method searchForClients.
	 * </p>
	 * 
	 * @throws Exception
	 *             Exception thrown from unit tests.
	 */
	public void testSearchForClients() throws Exception {
		Client[] clients = new Client[ITERATION];
		for (int i = 0; i < ITERATION; ++i) {
			clients[i] = StressHelper1_1.createClient(i + 1);
		}
		this.persistence.addClients(clients, true);
		Filter filter = new ValueFilter(CompareOperation.LIKE, "Name",
				"name111");
		this.persistence.searchForClients(filter);
	}
}

