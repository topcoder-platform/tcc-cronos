/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit.ejb;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import junit.framework.AssertionFailedError;
import net.sourceforge.junitejb.EJBTestCase;
import net.sourceforge.junitejb.EJBTestRunner;
import net.sourceforge.junitejb.EJBTestRunnerHome;
import net.sourceforge.junitejb.RemoteAssertionFailedError;
import net.sourceforge.junitejb.RemoteTestException;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanFilter;
import com.topcoder.search.builder.filter.LessThanFilter;
import com.topcoder.timetracker.audit.AuditConfigurationException;
import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.UnitTestHelper;


/**
 * <p>
 * Tests functionality and error cases of <code>AuditDelegate</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class AuditDelegateUnitTest extends EJBTestCase {
    /** The EJB name on which the local test cases are run on. */
    private static final String TEST_RUNNER_NAME = "audit/EJBTestRunner";

    /** The host to get the EJB homes and beans. */
    private static final String REMOTE_NAMING_HOST = "localhost:1099";

    /**
     * A <code>Hashtable</code> containing naming context environment parameters for use when constructing an
     * InitialContext.
     */
    private static Hashtable namingContextEnv;

    static {
        namingContextEnv = new Hashtable();
        namingContextEnv.put(Context.PROVIDER_URL, REMOTE_NAMING_HOST);
        namingContextEnv.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
    }

    /** Represents the connection instance for testing. */
    private Connection connection = null;

    /** Represents the <code>AuditDelegate</code> instance used for testing. */
    private AuditDelegate auditDelegate = null;

    /**
     * The constructor of this class.
     *
     * @param testName the name of the test to run
     */
    public AuditDelegateUnitTest(String testName) {
        super(testName);
    }

    /**
     * <p>
     * Sets up the test environment. The test instance is created. The configuration namespace is loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        UnitTestHelper.addConfig();
        connection = UnitTestHelper.getConnection();
        UnitTestHelper.prepareData(connection);

        auditDelegate = new AuditDelegate(AuditDelegate.class.getName());
    }

    /**
     * <p>
     * Clears the test environment. The test configuration namespace will be cleared.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        Object obj = UnitTestHelper.getPrivateField(auditDelegate.getClass(), auditDelegate, "auditLocalObject");

        if (obj instanceof AuditLocalObject) {
            ((AuditLocalObject) obj).remove();
        }

        try {
            UnitTestHelper.clearConfig();
            UnitTestHelper.clearDatabase(connection);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        super.tearDown();
    }

    /**
     * Test the constructor <code>AuditDelegate(String)</code> when the given namespace is null,
     * IllegalArgumentException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testAuditDelegate_NullNamespace() throws Exception {
        try {
            new AuditDelegate(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the constructor <code>AuditDelegate(String)</code> when the given namespace is an empty string,
     * IllegalArgumentException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testAuditDelegate_EmptyNamespace() throws Exception {
        try {
            new AuditDelegate(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the constructor <code>AuditDelegate(String)</code> when the given namespace does not exist,
     * AuditConfigurationException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testAuditDelegate_NotExistNamespace() throws Exception {
        try {
            new AuditDelegate("NotExist");
            fail("AuditConfigurationException should be thrown.");
        } catch (AuditConfigurationException e) {
            // good
        }
    }

    /**
     * Test the constructor <code>AuditDelegate(String)</code> when the context_name property value is invalid,
     * AuditConfigurationException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testAuditDelegate_InvalidContextName() throws Exception {
        try {
            new AuditDelegate(AuditDelegate.class.getName() + ".InvalidContextName");
            fail("AuditConfigurationException should be thrown.");
        } catch (AuditConfigurationException e) {
            // good
        }
    }

    /**
     * Test the constructor <code>AuditDelegate(String)</code> when the jndi_name property value is invalid,
     * AuditConfigurationException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testAuditDelegate_InvalidJndiName() throws Exception {
        try {
            new AuditDelegate(AuditDelegate.class.getName() + ".InvalidJndiName");
            fail("AuditConfigurationException should be thrown.");
        } catch (AuditConfigurationException e) {
            // good
        }
    }

    /**
     * Accuracy test for the constructor <code>AuditDelegate(String)</code> when the namespace is correct. Everything
     * should be successfully initialized.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAuditDelegate_Accuracy() throws Exception {
        assertNotNull("The AuditDelegate instance should be created.", auditDelegate);
        assertNotNull("The auditLocalObject should be created.",
            UnitTestHelper.getPrivateField(auditDelegate.getClass(), auditDelegate, "auditLocalObject"));
    }

    /**
     * <p>
     * Accuracy test for the method of <code>createAuditRecord(AuditHeader)</code>. The persisted value should be the
     * same as the retrieved value.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuditRecord_Accuracy1() throws Exception {
        AuditHeader auditHeader = UnitTestHelper.buildAuditHeader(5);

        // add it
        auditDelegate.createAuditRecord(auditHeader);

        // get it and check it
        AuditHeader[] ret = auditDelegate.searchAudit(new EqualToFilter("client id",
                    new Long(auditHeader.getClientId())));
        assertEquals("The audit header should be added properly.", 1, ret.length);
        UnitTestHelper.assertEquals(auditHeader, ret[0]);
    }

    /**
     * <p>
     * Accuracy test for the method of <code>createAuditRecord(AuditHeader)</code> with no audit details. The persisted
     * value should be the same as the retrieved value.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuditRecord_Accuracy2() throws Exception {
        AuditHeader auditHeader = UnitTestHelper.buildAuditHeader(0);

        // add it
        auditDelegate.createAuditRecord(auditHeader);

        // get it and check it
        AuditHeader[] ret = auditDelegate.searchAudit(new EqualToFilter("client id",
                    new Long(auditHeader.getClientId())));
        assertEquals("The audit header should be added properly.", 1, ret.length);
        UnitTestHelper.assertEquals(auditHeader, ret[0]);
    }

    /**
     * <p>
     * Accuracy test for the method of <code>createAuditRecord(AuditHeader)</code> with no audit details. The persisted
     * value should be the same as the retrieved value.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuditRecord_Accuracy3() throws Exception {
        AuditHeader auditHeader = UnitTestHelper.buildAuditHeader(5);
        auditHeader.setDetails(null);

        // add it
        auditDelegate.createAuditRecord(auditHeader);

        // get it and check it
        AuditHeader[] ret = auditDelegate.searchAudit(new EqualToFilter("client id",
                    new Long(auditHeader.getClientId())));
        assertEquals("The audit header should be added properly.", 1, ret.length);

        auditHeader.setDetails(new AuditDetail[0]);
        UnitTestHelper.assertEquals(auditHeader, ret[0]);
    }

    /**
     * <p>
     * Accuracy test for the method of <code>createAuditRecord(AuditHeader)</code> with some option values for audit
     * details are not set. The persisted value should be the same as the retrieved value, the default values for
     * audit details should be persisted.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuditRecord_Accuracy4() throws Exception {
        AuditHeader auditHeader = UnitTestHelper.buildAuditHeader(5);
        AuditDetail[] details = auditHeader.getDetails();

        for (int i = 0; i < details.length; i++) {
            if ((i % 2) == 0) {
                details[i].setOldValue(null);
            }

            if ((i % 2) == 1) {
                details[i].setNewValue(null);
            }
        }

        // add it
        auditDelegate.createAuditRecord(auditHeader);

        // get it and check it
        AuditHeader[] ret = auditDelegate.searchAudit(new EqualToFilter("client id",
                    new Long(auditHeader.getClientId())));
        assertEquals("The audit header should be added properly.", 1, ret.length);

        for (int i = 0; i < details.length; i++) {
            if ((i % 2) == 0) {
                // it is the default value for DefaultValuesContainer
                details[i].setOldValue("oldValue");
            }

            if ((i % 2) == 1) {
                // it is the default value for DefaultValuesContainer
                details[i].setNewValue("newValue");
            }
        }

        UnitTestHelper.assertEquals(auditHeader, ret[0]);
    }

    /**
     * <p>
     * Accuracy test for the method of <code>searchAudit(Filter)</code> when the input Filter is null. All the records
     * in the table should be retrieved.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testSearchAudit_NullAccuracy() throws Exception {
        AuditHeader[] auditHeaders = new AuditHeader[5];

        for (int i = 0; i < auditHeaders.length; i++) {
            auditHeaders[i] = UnitTestHelper.buildAuditHeader(i);
            auditDelegate.createAuditRecord(auditHeaders[i]);
        }

        // get it and check it
        AuditHeader[] ret = auditDelegate.searchAudit(null);

        assertEquals("The audit header should be got properly.", auditHeaders.length, ret.length);

        for (int i = 0; i < auditHeaders.length; i++) {
            UnitTestHelper.assertEquals(auditHeaders[i], ret[i]);
        }
    }

    /**
     * <p>
     * Accuracy test for the method of <code>searchAudit(Filter)</code> when the input Filter is by resource id. All
     * the records for given resource id in the table should be retrieved.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testSearchAudit_ByResourceIdAccuracy() throws Exception {
        AuditHeader[] auditHeaders = new AuditHeader[5];

        for (int i = 0; i < auditHeaders.length; i++) {
            auditHeaders[i] = UnitTestHelper.buildAuditHeader(i);
            auditDelegate.createAuditRecord(auditHeaders[i]);
        }

        // get it and check it
        AuditHeader[] ret = auditDelegate.searchAudit(new EqualToFilter("resource id",
                    new Long(auditHeaders[0].getResourceId())));

        assertEquals("The audit header should be got properly.", auditHeaders.length, ret.length);

        for (int i = 0; i < auditHeaders.length; i++) {
            UnitTestHelper.assertEquals(auditHeaders[i], ret[i]);
        }
    }

    /**
     * <p>
     * Accuracy test for the method of <code>searchAudit(Filter)</code> when the input Filter is by application area
     * id. All the records for given application area id in the table should be retrieved.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testSearchAudit_ByApplicationAreaIdAccuracy() throws Exception {
        AuditHeader[] auditHeaders = new AuditHeader[5];

        for (int i = 0; i < auditHeaders.length; i++) {
            auditHeaders[i] = UnitTestHelper.buildAuditHeader(i);
            auditDelegate.createAuditRecord(auditHeaders[i]);
        }

        // get it and check it
        AuditHeader[] ret = auditDelegate.searchAudit(new EqualToFilter("application area id",
                    new Long(auditHeaders[0].getApplicationArea().getId())));

        assertEquals("The audit header should be got properly.", auditHeaders.length, ret.length);

        for (int i = 0; i < auditHeaders.length; i++) {
            UnitTestHelper.assertEquals(auditHeaders[i], ret[i]);
        }
    }

    /**
     * <p>
     * Accuracy test for the method of <code>searchAudit(Filter)</code> when the input Filter is by client id. All the
     * records for given client id in the table should be retrieved.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testSearchAudit_ByClientIdAccuracy() throws Exception {
        AuditHeader[] auditHeaders = new AuditHeader[5];

        for (int i = 0; i < auditHeaders.length; i++) {
            auditHeaders[i] = UnitTestHelper.buildAuditHeader(i);
            auditDelegate.createAuditRecord(auditHeaders[i]);
        }

        // get it and check it
        AuditHeader[] ret = auditDelegate.searchAudit(new EqualToFilter("client id",
                    new Long(auditHeaders[0].getClientId())));

        assertEquals("The audit header should be got properly.", auditHeaders.length, ret.length);

        for (int i = 0; i < auditHeaders.length; i++) {
            UnitTestHelper.assertEquals(auditHeaders[i], ret[i]);
        }
    }

    /**
     * <p>
     * Accuracy test for the method of <code>searchAudit(Filter)</code> when the input Filter is by project id. All the
     * records for given project id in the table should be retrieved.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testSearchAudit_ByProjectIdAccuracy() throws Exception {
        AuditHeader[] auditHeaders = new AuditHeader[5];

        for (int i = 0; i < auditHeaders.length; i++) {
            auditHeaders[i] = UnitTestHelper.buildAuditHeader(i);
            auditDelegate.createAuditRecord(auditHeaders[i]);
        }

        // get it and check it
        AuditHeader[] ret = auditDelegate.searchAudit(new EqualToFilter("project id",
                    new Long(auditHeaders[0].getProjectId())));

        assertEquals("The audit header should be got properly.", auditHeaders.length, ret.length);

        for (int i = 0; i < auditHeaders.length; i++) {
            UnitTestHelper.assertEquals(auditHeaders[i], ret[i]);
        }
    }

    /**
     * <p>
     * Accuracy test for the method of <code>searchAudit(Filter)</code> when the input Filter is by creation date. All
     * the records for given range of creation date in the table should be retrieved.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testSearchAudit_ByCreationDateAccuracy() throws Exception {
        AuditHeader[] auditHeaders = new AuditHeader[5];

        for (int i = 0; i < auditHeaders.length; i++) {
            auditHeaders[i] = UnitTestHelper.buildAuditHeader(i);
            auditDelegate.createAuditRecord(auditHeaders[i]);
        }

        // get it and check it
        Filter filter = new AndFilter(new GreaterThanFilter("creation date", new Timestamp(1000)),
                new LessThanFilter("creation date", new Timestamp(5000)));
        AuditHeader[] ret = auditDelegate.searchAudit(filter);

        assertEquals("The audit header should be got properly.", auditHeaders.length - 2, ret.length);

        for (int i = 1; i < (auditHeaders.length - 1); i++) {
            UnitTestHelper.assertEquals(auditHeaders[i], ret[i - 1]);
        }
    }

    /**
     * <p>
     * Accuracy test for the method of <code>rollbackAuditRecord(int)</code>. All the records in the table should be
     * removed under this audit id.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testRollbackAuditRecord_Accuracy() throws Exception {
        AuditHeader auditHeader = UnitTestHelper.buildAuditHeader(5);

        // add it
        auditDelegate.createAuditRecord(auditHeader);

        // remove it
        boolean success = auditDelegate.rollbackAuditRecord(auditHeader.getId());

        // validate
        assertEquals("There should be records in the audit table.", true, success);
        assertEquals("The records in table audit_detail should be removed.", 0,
            UnitTestHelper.getAuditDetailRecords(connection));
        assertEquals("The records in table audit should be removed.", 0, UnitTestHelper.getAuditRecords(connection));
    }

    /**
     * <p>
     * Accuracy test for the method of <code>rollbackAuditRecord(int)</code> when there is no record in the database.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testRollbackAuditRecord_NoRecordAccuracy() throws Exception {
        // remove it
        boolean success = auditDelegate.rollbackAuditRecord(1);

        // validate
        assertEquals("There should be no records in the audit table.", false, success);
    }

    /**
     * This method copied directly from EJBTestCase to work around that class' brain dead InitialContext construction.
     * This works with getEJBTestRunner(), below.
     *
     * @throws Throwable if any unexpected exception occurs.
     */
    public void runBare() throws Throwable {
        if (!isServerSide()) {
            // We're not on the server side yet, invoke the test on the serverside.
            EJBTestRunner testRunner = null;

            try {
                testRunner = getEJBTestRunner();
                assertNotNull("Obtained a null EJBTestRunner reference", testRunner);
                testRunner.run(getClass().getName(), getName());
            } catch (RemoteTestException e) {
                // if the remote test exception is from an assertion error
                // rethrow it with a sub class of AssertionFailedError so it is
                // picked up as a failure and not an error.
                // The server has to throw sub classes of Error because that is the
                // allowable scope of application exceptions. So
                // AssertionFailedError which is an instance of error has to be
                // wrapped in an exception.
                Throwable remote = e.getRemoteThrowable();

                if (remote instanceof AssertionFailedError) {
                    throw new RemoteAssertionFailedError((AssertionFailedError) remote, e.getRemoteStackTrace());
                }

                throw e;
            } finally {
                // be a good citizen, drop my ref to the session bean.
                if (testRunner != null) {
                    testRunner.remove();
                }
            }
        } else {
            // We're on the server side, so invoke the test the regular way.
            super.runBare();
        }
    }

    /**
     * Looks up the ejb test runner home in JNDI (at "ejb/EJBTestRunner") and creates a new runner.  This method is
     * invoked only on the client side.
     *
     * @return the ejb test runner created from home
     *
     * @throws Exception if any problem happens
     */
    private EJBTestRunner getEJBTestRunner() throws Exception {
        InitialContext jndiContext = new InitialContext(namingContextEnv);

        // Get a reference from this to the Bean's Home interface
        Object ref = jndiContext.lookup(TEST_RUNNER_NAME);
        EJBTestRunnerHome runnerHome = (EJBTestRunnerHome) PortableRemoteObject.narrow(ref, EJBTestRunnerHome.class);

        // create the test runner
        return runnerHome.create();
    }
}
