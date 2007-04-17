/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.failuretests.db;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.ejb.AuditDelegate;
import com.topcoder.timetracker.entry.time.ConfigurationException;
import com.topcoder.timetracker.entry.time.DataAccessException;
import com.topcoder.timetracker.entry.time.TimeStatus;
import com.topcoder.timetracker.entry.time.db.DbTimeStatusDAO;
import com.topcoder.timetracker.entry.time.failuretests.FailureTestHelper;

import junit.framework.TestCase;


/**
 * <p>
 * Failure test cases for DbTimeStatusDAO.
 * </p>
 *
 * @author KLW
 * @version 3.2
 */
public class DbTimeStatusDAOFailureTests extends TestCase {
    /**
     * <p>
     * The DbTimeStatusDAO instance for testing.
     * </p>
     */
    private DbTimeStatusDAO instance;

    /**
     * <p>
     * The DBConnectionFactory instance for testing.
     * </p>
     */
    private DBConnectionFactory dbFactory;

    /**
     * <p>
     * The AuditManager instance for testing.
     * </p>
     */
    private AuditManager auditManager;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        FailureTestHelper.clearConfig();
        FailureTestHelper.loadXMLConfig(FailureTestHelper.CONFIG_FILE);
        FailureTestHelper.loadXMLConfig(FailureTestHelper.AUDIT_CONFIG_FILE);
        FailureTestHelper.setUpDataBase();
        FailureTestHelper.setUpEJBEnvironment(null, null, null);

        dbFactory = new DBConnectionFactoryImpl(FailureTestHelper.DB_FACTORY_NAMESPACE);
        auditManager = new AuditDelegate(FailureTestHelper.AUDIT_NAMESPACE);

        instance = new DbTimeStatusDAO(dbFactory, FailureTestHelper.CONNECTION_NAME,
                "TimeStatusIdGenerator", FailureTestHelper.SEARCH_NAMESPACE, auditManager);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        FailureTestHelper.tearDownDataBase();
        FailureTestHelper.clearConfig();

        dbFactory = null;
        auditManager = null;
        instance = null;
    }

    /**
     * <p>
     * Tests ctor DbTimeStatusDAO#DbTimeStatusDAO(DBConnectionFactory,String,String,String,AuditManager)
     * for failure.
     * </p>
     *
     * <p>
     * It tests the case that when connFactory is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor1() throws Exception {
        try {
            new DbTimeStatusDAO(null, FailureTestHelper.CONNECTION_NAME, "TimeStatusIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeStatusDAO#DbTimeStatusDAO(DBConnectionFactory,String,String,String,AuditManager)
     * for failure.
     * </p>
     *
     * <p>
     * It tests the case that when connName is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor2() throws Exception {
        try {
            new DbTimeStatusDAO(dbFactory, " ", "TimeStatusIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeStatusDAO#DbTimeStatusDAO(DBConnectionFactory,String,String,String,AuditManager)
     * for failure.
     * </p>
     *
     * <p>
     * It tests the case that when idGen is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor3() throws Exception {
        try {
            new DbTimeStatusDAO(dbFactory, FailureTestHelper.CONNECTION_NAME, null,
                FailureTestHelper.SEARCH_NAMESPACE, auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeStatusDAO#DbTimeStatusDAO(DBConnectionFactory,String,String,String,AuditManager)
     * for failure.
     * </p>
     *
     * <p>
     * It tests the case that when idGen is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor4() throws Exception {
        try {
            new DbTimeStatusDAO(dbFactory, FailureTestHelper.CONNECTION_NAME, " ",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeStatusDAO#DbTimeStatusDAO(DBConnectionFactory,String,String,String,AuditManager)
     * for failure.
     * </p>
     *
     * <p>
     * It tests the case that when searchStrategyNamespace is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor5() throws Exception {
        try {
            new DbTimeStatusDAO(dbFactory, FailureTestHelper.CONNECTION_NAME, "TimeStatusIdGenerator",
                null, auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeStatusDAO#DbTimeStatusDAO(DBConnectionFactory,String,String,String,AuditManager)
     * for failure.
     * </p>
     *
     * <p>
     * It tests the case that when searchStrategyNamespace is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor6() throws Exception {
        try {
            new DbTimeStatusDAO(dbFactory, FailureTestHelper.CONNECTION_NAME, "TimeStatusIdGenerator",
                " ", auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeStatusDAO#DbTimeStatusDAO(DBConnectionFactory,String,String,String,AuditManager)
     * for failure.
     * </p>
     *
     * <p>
     * It tests the case that when auditor is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor7() throws Exception {
        try {
            new DbTimeStatusDAO(dbFactory, FailureTestHelper.CONNECTION_NAME, "TimeStatusIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeStatusDAO#DbTimeStatusDAO(DBConnectionFactory,String,String,String,AuditManager)
     * for failure.
     * </p>
     *
     * <p>
     * It tests the case when the search strategy namespace is unknown and expects for ConfigurationException.
     * </p>
     */
    public void testCtor8() {
        try {
            new DbTimeStatusDAO(dbFactory, FailureTestHelper.CONNECTION_NAME, "TimeStatusIdGenerator",
                "unknown_namespace", auditManager);
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#createTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatuses is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses1() throws Exception {
        try {
            instance.createTimeStatuses(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#createTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatuses is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses2() throws Exception {
        try {
            instance.createTimeStatuses(new TimeStatus[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#createTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatuses contains same element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses3() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);

        try {
            instance.createTimeStatuses(new TimeStatus[] { timeStatus1, timeStatus1 });
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#createTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatuses contains null element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses4() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);

        try {
            instance.createTimeStatuses(new TimeStatus[] { timeStatus1, null });
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#createTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time status has id set and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses5() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = FailureTestHelper.createTestingTimeStatus(null);
        timeStatus2.setId(34);

        try {
            instance.createTimeStatuses(new TimeStatus[] { timeStatus1, timeStatus2 });
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#createTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time status has null creation date and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses6() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = FailureTestHelper.createTestingTimeStatus("CreationDate");

        try {
            instance.createTimeStatuses(new TimeStatus[] { timeStatus1, timeStatus2 });
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#createTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time status has null creation user and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses7() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = FailureTestHelper.createTestingTimeStatus("CreationUser");

        try {
            instance.createTimeStatuses(new TimeStatus[] { timeStatus1, timeStatus2 });
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#createTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time status has null modification date and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses8() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = FailureTestHelper.createTestingTimeStatus("ModificationDate");

        try {
            instance.createTimeStatuses(new TimeStatus[] { timeStatus1, timeStatus2 });
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#createTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time status has null modification user and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses9() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = FailureTestHelper.createTestingTimeStatus("ModificationUser");

        try {
            instance.createTimeStatuses(new TimeStatus[] { timeStatus1, timeStatus2 });
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#createTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses10() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = FailureTestHelper.createTestingTimeStatus(null);
        instance = new DbTimeStatusDAO(dbFactory, "empty", "TimeStatusIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager);

        try {
            instance.createTimeStatuses(new TimeStatus[] { timeStatus1, timeStatus2 });
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#updateTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatuses is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatuses1() throws Exception {
        try {
            instance.updateTimeStatuses(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#updateTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatuses is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatuses2() throws Exception {
        try {
            instance.updateTimeStatuses(new TimeStatus[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#updateTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatuses contains same element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatuses3() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        instance.createTimeStatuses(new TimeStatus[] { timeStatus1 });

        try {
            instance.updateTimeStatuses(new TimeStatus[] { timeStatus1, timeStatus1 });
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#updateTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatuses contains null element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatuses4() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        instance.createTimeStatuses(new TimeStatus[] { timeStatus1 });

        try {
            instance.updateTimeStatuses(new TimeStatus[] { timeStatus1, null });
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#updateTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time status has id set and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatuses5() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        instance.createTimeStatuses(new TimeStatus[] { timeStatus1 });

        TimeStatus timeStatus2 = FailureTestHelper.createTestingTimeStatus(null);

        try {
            instance.updateTimeStatuses(new TimeStatus[] { timeStatus1, timeStatus2 });
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#updateTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time status has null modification date and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatuses6() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        instance.createTimeStatuses(new TimeStatus[] { timeStatus1 });

        TimeStatus timeStatus2 = FailureTestHelper.createTestingTimeStatus("ModificationDate");
        timeStatus2.setId(100);

        try {
            instance.updateTimeStatuses(new TimeStatus[] { timeStatus1, timeStatus2 });
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#updateTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time status has null modification user and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatuses7() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        instance.createTimeStatuses(new TimeStatus[] { timeStatus1 });

        TimeStatus timeStatus2 = FailureTestHelper.createTestingTimeStatus("ModificationUser");
        timeStatus2.setId(300);

        try {
            instance.updateTimeStatuses(new TimeStatus[] { timeStatus1, timeStatus2 });
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#updateTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatuses8() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = FailureTestHelper.createTestingTimeStatus(null);
        instance.createTimeStatuses(new TimeStatus[] { timeStatus1, timeStatus2 });

        instance = new DbTimeStatusDAO(dbFactory, "empty", "TimeStatusIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager);

        try {
            instance.updateTimeStatuses(new TimeStatus[] { timeStatus1, timeStatus2 });
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#deleteTimeStatuses(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatusIds is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeStatuses1() throws Exception {
        try {
            instance.deleteTimeStatuses(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#deleteTimeStatuses(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatusIds is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeStatuses2() throws Exception {
        try {
            instance.deleteTimeStatuses(new long[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#deleteTimeStatuses(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatusIds contains equal id and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeStatuses3() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        instance.createTimeStatuses(new TimeStatus[] { timeStatus1 });

        try {
            instance.deleteTimeStatuses(new long[] { timeStatus1.getId(), timeStatus1.getId() });
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#deleteTimeStatuses(long[]) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeStatuses4() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        instance.createTimeStatuses(new TimeStatus[] { timeStatus1 });

        instance = new DbTimeStatusDAO(dbFactory, "empty", "TimeStatusIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager);

        try {
            instance.deleteTimeStatuses(new long[] { timeStatus1.getId() });
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#getTimeStatuses(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatusIds is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeStatuses1() throws Exception {
        try {
            instance.getTimeStatuses(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#getTimeStatuses(long[]) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeStatuses2() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        instance.createTimeStatuses(new TimeStatus[] { timeStatus1 });

        instance = new DbTimeStatusDAO(dbFactory, "empty", "TimeStatusIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager);

        try {
            instance.getTimeStatuses(new long[] { timeStatus1.getId() });
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#searchTimeStatuses(Filter) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when criteria is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchTimeStatuses1() throws Exception {
        try {
            instance.searchTimeStatuses(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#searchTimeStatuses(Filter) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     */
    public void testSearchTimeStatuses2() {
        Filter filter = new EqualToFilter("no_column", "value");

        try {
            instance.searchTimeStatuses(filter);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#getAllTimeStatuses() for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTimeStatuses() throws Exception {
        instance = new DbTimeStatusDAO(dbFactory, "empty", "TimeStatusIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager);

        try {
            instance.getAllTimeStatuses();
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }
}
