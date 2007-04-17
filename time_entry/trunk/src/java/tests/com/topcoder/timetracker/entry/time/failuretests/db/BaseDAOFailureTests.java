package com.topcoder.timetracker.entry.time.failuretests.db;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.ejb.AuditDelegate;
import com.topcoder.timetracker.entry.time.ConfigurationException;
import com.topcoder.timetracker.entry.time.db.BaseDAO;
import com.topcoder.timetracker.entry.time.failuretests.FailureTestHelper;

import junit.framework.TestCase;
/**
 * <p>
 * Failure test cases for BaseDAO.
 * </p>
 *
 * @author KLW
 * @version 3.2
 */
public class BaseDAOFailureTests extends TestCase {
    /**
     * <p>
     * The constant represents the id generator namespace for testing.
     * </p>
     */
    private static final String ID_GENERATOR_NAME = "AuditIdGenerator";

    /**
     * <p>
     * The BaseDAO instance for testing.
     * </p>
     */
    private BaseDAO instance;

    /**
     * <p>
     * The DBConnectionFactory instance for testing.
     * </p>
     */
    private DBConnectionFactory connFactory;

    /**
     * <p>
     * The AuditManager instance for testing.
     * </p>
     */
    private AuditManager auditor;

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

        connFactory = new DBConnectionFactoryImpl(FailureTestHelper.DB_FACTORY_NAMESPACE);
        auditor = new AuditDelegate(FailureTestHelper.AUDIT_NAMESPACE);

        instance = new MockBaseDAO(connFactory, "tt_time_entry", ID_GENERATOR_NAME, FailureTestHelper.SEARCH_NAMESPACE, auditor);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        instance = null;
        auditor = null;
        connFactory = null;

        FailureTestHelper.tearDownDataBase();
        FailureTestHelper.clearConfig();
    }
    /**
     * <p>
     * Tests ctor BaseDAO#BaseDAO(DBConnectionFactory,String,IDGenerator,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when connFactory is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor1() throws Exception {
        try {
            new MockBaseDAO(null, "tt_time_entry", ID_GENERATOR_NAME, FailureTestHelper.SEARCH_NAMESPACE, auditor);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor BaseDAO#BaseDAO(DBConnectionFactory,String,IDGenerator,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when connName is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor2() throws Exception {
        try {
            new MockBaseDAO(connFactory, " ", ID_GENERATOR_NAME, FailureTestHelper.SEARCH_NAMESPACE, auditor);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor BaseDAO#BaseDAO(DBConnectionFactory,String,IDGenerator,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when idGen is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor3() throws Exception {
        try {
            new MockBaseDAO(connFactory, "tt_time_entry", "  ", FailureTestHelper.SEARCH_NAMESPACE, auditor);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor BaseDAO#BaseDAO(DBConnectionFactory,String,IDGenerator,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when idGen is unknown and expects ConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor4() throws Exception {
        try {
            new MockBaseDAO(connFactory, "tt_time_entry", "HelloWorld", FailureTestHelper.SEARCH_NAMESPACE, auditor);
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor BaseDAO#BaseDAO(DBConnectionFactory,String,IDGenerator,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when searchStrategyNamespace is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor5() throws Exception {
        try {
            new MockBaseDAO(connFactory, "tt_time_entry", ID_GENERATOR_NAME, " ", auditor);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor BaseDAO#BaseDAO(DBConnectionFactory,String,IDGenerator,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case when searchStrategyNamespace is invalid and expects for ConfigurationException.
     * </p>
     */
    public void testCtor6() {
        try {
            new MockBaseDAO(connFactory, "tt_time_entry", ID_GENERATOR_NAME, "invalid", auditor);
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }
}
