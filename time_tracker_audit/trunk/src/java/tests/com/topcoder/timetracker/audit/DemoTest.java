/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.audit.ejb.AuditDelegate;

import junit.framework.AssertionFailedError;

import net.sourceforge.junitejb.EJBTestCase;
import net.sourceforge.junitejb.EJBTestRunner;
import net.sourceforge.junitejb.EJBTestRunnerHome;
import net.sourceforge.junitejb.RemoteAssertionFailedError;
import net.sourceforge.junitejb.RemoteTestException;

import java.sql.Connection;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.rmi.PortableRemoteObject;


/**
 * <p>
 * Demonstrates the usage of this component. The usage include only one part.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DemoTest extends EJBTestCase {
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

    /**
     * The constructor of this class.
     *
     * @param testName the name of the test to run
     */
    public DemoTest(String testName) {
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
    }

    /**
     * <p>
     * Clears the test environment. The test configuration namespace will be cleared.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
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
     * <p>
     * This demo will demonstrate typical usage of this component.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testTypicalAuctionClient_Usage() throws Exception {
        // assuming a valid configuration file is provided in the ConfigManager
        // namespace com.topcoder.timetracker.audit.ejb.AuditDelegate
        AuditDelegate delegate = new AuditDelegate("com.topcoder.timetracker.audit.ejb.AuditDelegate");

        // get some audit record (i.e. generated from insert or web form)
        AuditHeader record = UnitTestHelper.buildAuditHeader(5);

        // added to Informix persistence
        delegate.createAuditRecord(record);

        // assume no problems yet
        assertTrue("The record should be persisted.", record.isPersisted());

        // add record with failure:
        // try {
        // delegate.createAuditRecord(record);
        // } catch (AuditManagerException e) {
        // should be thrown, due to duplicate id. Check:
        // assertFalse("The record should not be persisted.", record.isPersisted());
        // the record¡¯s id and detail ids should be logged
        // }
        // search audit headers for the previously added record:
        Long refId = new Long(record.getProjectId());
        Filter filter = new EqualToFilter("project id", refId);

        AuditHeader[] matches = delegate.searchAudit(filter);

        // only one can match a given audit id
        assertEquals("The searching result should be correct.", 1, matches.length);

        // check it¡¯s the same:
        AuditHeader found = matches[0];
        assertEquals("The id should be correct.", found.getId(), record.getId());
        assertEquals("The applicationArea should be correct.", found.getApplicationArea(), record.getApplicationArea());

        // remove:
        boolean removed = delegate.rollbackAuditRecord(record.getId());

        // header and details removed
        assertTrue("It should be removed.", removed);

        // search audit headers again:
        matches = delegate.searchAudit(filter);

        // no match now it has been removed
        assertEquals("The record should be removed.", 0, matches.length);

        // remove again:
        removed = delegate.rollbackAuditRecord(record.getId());

        // if no record exists, false is returned.
        assertFalse("No record should exist", removed);
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
