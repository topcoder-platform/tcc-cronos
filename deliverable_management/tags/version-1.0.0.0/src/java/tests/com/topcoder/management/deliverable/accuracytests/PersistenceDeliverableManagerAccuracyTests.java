/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.accuracytests;

import com.topcoder.management.deliverable.Deliverable;
import com.topcoder.management.deliverable.PersistenceDeliverableManager;
import com.topcoder.management.deliverable.accuracytests.persistence.MockDeliverablePersistence;
import com.topcoder.management.deliverable.search.DeliverableFilterBuilder;

import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * <p>
 * Accuracy test cases for <code>PersistenceDeliverableManager</code>.
 * </p>
 *
 * @author skatou
 * @version 1.0
 */
public class PersistenceDeliverableManagerAccuracyTests extends AccuracyTestsHelper {
    /** The path of the configuration file. */
    private static final String CONFIG = "accuracytests/PersistenceDeliverableManagerConfig.xml";

    /** File contains sql statement to initial database for upload search. */
    private static final String INIT_DB_SQL = "test_files/accuracytests/InitDB.sql";

    /** File contains sql statement to clear database for upload search. */
    private static final String CLEAR_DB_SQL = "test_files/accuracytests/ClearDB.sql";

    /** Represents the configuration namespace for search builder. */
    private static final String SEARCH_BUILDER_NAMESPACE = "com.topcoder.searchbuilder.DeliverableManager";

    /** The <code>SearchBundleManager</code> instance used in the tests. */
    private SearchBundleManager searchBundleManager = null;

    /** The <code>PersistenceDeliverableManager</code> instance to be tested. */
    private PersistenceDeliverableManager manager = null;

    /**
     * Sets up the test environment. Configurations are loaded and new instances of
     * <code>PersistenceDeliverableManager</code> and <code>SearchBundleManager</code> are created.
     *
     * @throws Exception pass to JUnit.
     */
    protected void setUp() throws Exception {
        unloadConfig();
        loadConfig(CONFIG);

        searchBundleManager = new SearchBundleManager(SEARCH_BUILDER_NAMESPACE);

        Map checks = new HashMap();
        checks.put("name1", new MockDeliverableChecker());
        checks.put("name2", new MockDeliverableChecker());
        checks.put("name3", new MockDeliverableChecker());
        checks.put("name4", new MockDeliverableChecker());
        checks.put("name5", new MockDeliverableChecker());
        manager = new PersistenceDeliverableManager(new MockDeliverablePersistence(), checks, searchBundleManager);
    }

    /**
     * Verifies public fields are set correctly.
     */
    public void testPublicFields() {
        assertEquals("DELIVERABLE_SEARCH_BUNDLE_NAME not set correctly", "Deliverable Search Bundle",
            PersistenceDeliverableManager.DELIVERABLE_SEARCH_BUNDLE_NAME);
        assertEquals("DELIVERABLE_WITH_SUBMISSIONS_SEARCH_BUNDLE_NAME not set correctly", "Deliverable With Submission Search Bundle",
            PersistenceDeliverableManager.DELIVERABLE_WITH_SUBMISSIONS_SEARCH_BUNDLE_NAME);
    }

    /**
     * Tests constructor with search bundles.
     */
    public void testConstructorWithSearchBundles() {
        SearchBundle deliverableSearchBundle = searchBundleManager.getSearchBundle(PersistenceDeliverableManager.DELIVERABLE_SEARCH_BUNDLE_NAME);
        SearchBundle submissionSearchBundle = searchBundleManager.getSearchBundle(PersistenceDeliverableManager.DELIVERABLE_WITH_SUBMISSIONS_SEARCH_BUNDLE_NAME);
        manager = new PersistenceDeliverableManager(new MockDeliverablePersistence(), new HashMap(),
                deliverableSearchBundle, submissionSearchBundle);
        assertNotNull("Unable to instantiate PersistenceDeliverableManager", manager);
    }

    /**
     * Tests constructor with search bundle manager.
     */
    public void testConstructorWithSearchBundleManager() {
        manager = new PersistenceDeliverableManager(new MockDeliverablePersistence(), new HashMap(), searchBundleManager);
        assertNotNull("Unable to instantiate PersistenceDeliverableManager", manager);
    }

    /**
     * Tests searchDeliverables method with deliverable id filter.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchDeliverablesByDeliverableId()
        throws Exception {
        try {
            executeBatch(CLEAR_DB_SQL);
            executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = manager.searchDeliverables(DeliverableFilterBuilder.createDeliverableIdFilter(
                        1), null);
            assertEquals("Wrong number of deliverable retrieved", 1, deliverables.length);
            assertEquals("Wrong deliverable retrieved", 1, deliverables[0].getId());

            assertTrue("Persistence method not called correctly",
                MockDeliverablePersistence.getLastCalled().startsWith("loadDeliverables"));

            // search complete deliverables
            deliverables = manager.searchDeliverables(DeliverableFilterBuilder.createDeliverableIdFilter(4),
                    new Boolean(true));
            assertEquals("Wrong number of deliverable retrieved", 1, deliverables.length);
            assertEquals("Wrong deliverable retrieved", 4, deliverables[0].getId());
        } finally {
            executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchDeliverables method with name filter.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchDeliverablesByName() throws Exception {
        try {
            executeBatch(CLEAR_DB_SQL);
            executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = manager.searchDeliverables(DeliverableFilterBuilder.createNameFilter("name2"),
                    null);
            assertEquals("Wrong number of deliverable retrieved", 1, deliverables.length);
            assertEquals("Wrong deliverable retrieved", 2, deliverables[0].getId());

            assertTrue("Persistence method not called correctly",
                MockDeliverablePersistence.getLastCalled().startsWith("loadDeliverables"));
        } finally {
            executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchDeliverables method with project id filter.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchDeliverablesByProjectId() throws Exception {
        try {
            executeBatch(CLEAR_DB_SQL);
            executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = manager.searchDeliverables(DeliverableFilterBuilder.createProjectIdFilter(2),
                    null);
            assertEquals("Wrong number of deliverable retrieved", 5, deliverables.length);

            Set ids = new HashSet();

            for (int i = 0; i < deliverables.length; ++i) {
                ids.add(new Long(deliverables[i].getId()));
            }

            assertTrue("Wrong deliverable retrieved", ids.contains(new Long(1)));
            assertTrue("Wrong deliverable retrieved", ids.contains(new Long(2)));
            assertTrue("Wrong deliverable retrieved", ids.contains(new Long(3)));
            assertTrue("Wrong deliverable retrieved", ids.contains(new Long(4)));
            assertTrue("Wrong deliverable retrieved", ids.contains(new Long(5)));

            assertTrue("Persistence method not called correctly",
                MockDeliverablePersistence.getLastCalled().startsWith("loadDeliverables"));
        } finally {
            executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchDeliverables method with phase id filter.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchDeliverablesByPhaseId() throws Exception {
        try {
            executeBatch(CLEAR_DB_SQL);
            executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = manager.searchDeliverables(DeliverableFilterBuilder.createPhaseIdFilter(4),
                    null);
            assertEquals("Wrong number of deliverable retrieved", 2, deliverables.length);

            Set ids = new HashSet();

            for (int i = 0; i < deliverables.length; ++i) {
                ids.add(new Long(deliverables[i].getId()));
            }

            assertTrue("Wrong deliverable retrieved", ids.contains(new Long(2)));
            assertTrue("Wrong deliverable retrieved", ids.contains(new Long(5)));

            assertTrue("Persistence method not called correctly",
                MockDeliverablePersistence.getLastCalled().startsWith("loadDeliverables"));
        } finally {
            executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchDeliverables method with resource role id filter.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchDeliverablesByResourceRoleId()
        throws Exception {
        try {
            executeBatch(CLEAR_DB_SQL);
            executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = manager.searchDeliverables(DeliverableFilterBuilder.createResourceIdFilter(1),
                    null);
            assertEquals("Wrong number of deliverable retrieved", 2, deliverables.length);

            Set ids = new HashSet();

            for (int i = 0; i < deliverables.length; ++i) {
                ids.add(new Long(deliverables[i].getId()));
            }

            assertTrue("Wrong deliverable retrieved", ids.contains(new Long(1)));
            assertTrue("Wrong deliverable retrieved", ids.contains(new Long(2)));

            assertTrue("Persistence method not called correctly",
                MockDeliverablePersistence.getLastCalled().startsWith("loadDeliverables"));
        } finally {
            executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchDeliverables method with required filter.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchDeliverablesByRequired() throws Exception {
        try {
            executeBatch(CLEAR_DB_SQL);
            executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = manager.searchDeliverables(DeliverableFilterBuilder.createRequiredFilter(true),
                    null);
            assertEquals("Wrong number of deliverable retrieved", 3, deliverables.length);

            Set ids = new HashSet();

            for (int i = 0; i < deliverables.length; ++i) {
                ids.add(new Long(deliverables[i].getId()));
            }

            assertTrue("Wrong deliverable retrieved", ids.contains(new Long(3)));
            assertTrue("Wrong deliverable retrieved", ids.contains(new Long(4)));
            assertTrue("Wrong deliverable retrieved", ids.contains(new Long(5)));

            assertTrue("Persistence method not called correctly",
                MockDeliverablePersistence.getLastCalled().startsWith("loadDeliverables"));
        } finally {
            executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchDeliverablesWithSubmissionFilter method with deliverable id filter.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchDeliverablesWithSubmissionFilterByDeliverableId()
        throws Exception {
        try {
            executeBatch(CLEAR_DB_SQL);
            executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = manager.searchDeliverablesWithSubmissionFilter(DeliverableFilterBuilder.createDeliverableIdFilter(
                        1), null);
            assertEquals("Wrong number of deliverable retrieved", 0, deliverables.length);

            assertTrue("Persistence method not called correctly",
                MockDeliverablePersistence.getLastCalled().startsWith("loadDeliverables"));
        } finally {
            executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchDeliverablesWithSubmissionFilter method with name filter.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchDeliverablesWithSubmissionFilterByName()
        throws Exception {
        try {
            executeBatch(CLEAR_DB_SQL);
            executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = manager.searchDeliverablesWithSubmissionFilter(DeliverableFilterBuilder.createNameFilter(
                        "name3"), null);
            assertEquals("Wrong number of deliverable retrieved", 2, deliverables.length);

            assertTrue("Persistence method not called correctly",
                MockDeliverablePersistence.getLastCalled().startsWith("loadDeliverables"));
        } finally {
            executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchDeliverablesWithSubmissionFilter method with project id filter.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchDeliverablesWithSubmissionFilterByProjectId()
        throws Exception {
        try {
            executeBatch(CLEAR_DB_SQL);
            executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = manager.searchDeliverablesWithSubmissionFilter(DeliverableFilterBuilder.createProjectIdFilter(
                        2), null);

            assertEquals("Wrong number of deliverable retrieved", 3, deliverables.length);

            assertTrue("Persistence method not called correctly",
                MockDeliverablePersistence.getLastCalled().startsWith("loadDeliverables"));
        } finally {
            executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchDeliverablesWithSubmissionFilter method with phase id filter.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchDeliverablesWithSubmissionFilterByPhaseId()
        throws Exception {
        try {
            executeBatch(CLEAR_DB_SQL);
            executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = manager.searchDeliverablesWithSubmissionFilter(DeliverableFilterBuilder.createPhaseIdFilter(
                        4), null);
            assertEquals("Wrong number of deliverable retrieved", 4, deliverables.length);

            assertTrue("Persistence method not called correctly",
                MockDeliverablePersistence.getLastCalled().startsWith("loadDeliverables"));
        } finally {
            executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchDeliverablesWithSubmissionFilter method with resource role id filter.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchDeliverablesWithSubmissionFilterByResourceRoleId()
        throws Exception {
        try {
            executeBatch(CLEAR_DB_SQL);
            executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = manager.searchDeliverablesWithSubmissionFilter(DeliverableFilterBuilder.createResourceIdFilter(
                        1), null);
            assertEquals("Wrong number of deliverable retrieved", 2, deliverables.length);

            assertTrue("Persistence method not called correctly",
                MockDeliverablePersistence.getLastCalled().startsWith("loadDeliverables"));
        } finally {
            executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchDeliverablesWithSubmissionFilter method with submission id filter.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchDeliverablesWithSubmissionFilterBySubmissionId()
        throws Exception {
        try {
            executeBatch(CLEAR_DB_SQL);
            executeBatch(INIT_DB_SQL);

            Deliverable[] deliverables = manager.searchDeliverablesWithSubmissionFilter(DeliverableFilterBuilder.createSubmissionIdFilter(
                        1), null);
            assertEquals("Wrong number of deliverable retrieved", 0, deliverables.length);

            assertTrue("Persistence method not called correctly",
                MockDeliverablePersistence.getLastCalled().startsWith("loadDeliverables"));
        } finally {
            executeBatch(CLEAR_DB_SQL);
        }
    }
}
