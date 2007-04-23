/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail.failuretests;

import java.io.File;

import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.topcoder.timetracker.invoice.servicedetail.ejb.LocalServiceDetail;
import com.topcoder.timetracker.invoice.servicedetail.ejb.LocalServiceDetailHome;
import com.topcoder.timetracker.invoice.servicedetail.ejb.ServiceDetailBean;

/**
 * <p>
 * Failure test cases for <code>ServiceDetailBean</code>.
 * </p>
 * @author myxgyy
 * @version 1.0
 */
public class ServiceDetailBeanFailureTests extends TestCase {
	/**
	 * Represents the config file used for testing.
	 */
	private static final String CONFIG_FILE = "test_files" + File.separatorChar
	    + "Failure" + File.separatorChar + "ServiceDetailBeanFailure.xml";

    /**
     * Context instance for testing.
     */
    private Context context;

    /**
     * <p>
     * Return the suite for this tests.
     * </p>
     * @return the suite.
     */
    public static Test suite() {
        return new TestSuite(ServiceDetailBeanFailureTests.class);
    }

    /**
     * Sets up the test.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
    	TestHelper.loadXMLConfig(CONFIG_FILE);

        MockContextFactory.setAsInitial();

        context = new InitialContext();

        // creates an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        // creates deployment descriptor of our sample bean. MockEjb does not support XML descriptors.
        SessionBeanDescriptor localServiceDetailDescriptor =
            new SessionBeanDescriptor("sessionBean", LocalServiceDetailHome.class, LocalServiceDetail.class,
                ServiceDetailBean.class);
        // Deploy operation simply creates Home and binds it to JNDI
        mockContainer.deploy(localServiceDetailDescriptor);
    }

    /**
     * Tears the unit test down.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        MockContextFactory.revertSetAsInitial();

        TestHelper.clearConfig();
    }

    /**
     * <p>
     * Test ejbCreate for failure.
     * </p>
     * <p>
     * Condition: there is no factory namespace.
     * </p>
     * <p>
     * Expect: <code>EJBException</code>.
     * </p>
     * @throws Exception to JUnit
     */
    public void testEjbCreateNoFactoryNamespace() throws Exception {
        context.rebind("java:comp/env/dao_name", "DAOName");
        LocalServiceDetailHome sampleHome = (LocalServiceDetailHome) context.lookup("sessionBean");

        try {
            sampleHome.create();
            fail("Should throw EJBException");
        } catch (EJBException e) {
            // expected
        }
    }

    /**
     * <p>
     * Test ejbCreate for failure.
     * </p>
     * <p>
     * Condition: there is no dao name.
     * </p>
     * <p>
     * Expect: <code>EJBException</code>.
     * </p>
     * @throws Exception to JUnit
     */
    public void testEjbCreateNoDaoName() throws Exception {
        context.rebind("java:comp/env/factory_namespace",
        		"com.topcoder.timetracker.invoice.servicedetail.ServiceDetailBean");
        LocalServiceDetailHome sampleHome = (LocalServiceDetailHome) context.lookup("sessionBean");

        try {
            sampleHome.create();
            fail("Should throw EJBException");
        } catch (EJBException e) {
            // expected
        }
    }

    /**
     * <p>
     * Test ejbCreate for failure.
     * </p>
     * <p>
     * Condition: the namespace is unknown.
     * </p>
     * <p>
     * Expect: <code>EJBException</code>.
     * </p>
     * @throws Exception to JUnit
     */
    public void testEjbCreateNoNamespace() throws Exception {
        context.rebind("java:comp/env/factory_namespace", "notAvailableNamespace");
        context.rebind("java:comp/env/dao_name", "DAOName");

        LocalServiceDetailHome sampleHome = (LocalServiceDetailHome) context.lookup("sessionBean");

        try {
            sampleHome.create();
            fail("Should throw EJBException");
        } catch (EJBException e) {
            // expected
        }
    }

    /**
     * Test ejbCreate for failure. Condition: the dao key is not in the object factory configuration. Expect:
     * <code>EJBException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testEjbCreateNoDao() throws Exception {
        context.rebind("java:comp/env/factory_namespace",
        		"com.topcoder.timetracker.invoice.servicedetail.ServiceDetailBean");
        context.rebind("java:comp/env/dao_name", "notAvailableDaoName");
        LocalServiceDetailHome sampleHome = (LocalServiceDetailHome) context.lookup("sessionBean");

        try {
            sampleHome.create();
            fail("Should throw EJBException");
        } catch (EJBException e) {
            // expected
        }
    }

    /**
     * Test ejbCreate for failure. Condition: the dao key is refer to wrong object. Expect:
     * <code>EJBException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testEjbCreateClassCastException() throws Exception {
        context.rebind("java:comp/env/factory_namespace", "TypeError");
        context.rebind("java:comp/env/dao_name", "DAOName");
        LocalServiceDetailHome sampleHome = (LocalServiceDetailHome) context.lookup("sessionBean");

        try {
            sampleHome.create();
            fail("Should throw EJBException");
        } catch (EJBException e) {
            // expected
        }
    }
}
