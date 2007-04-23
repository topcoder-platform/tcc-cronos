/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail.failuretests;

import java.io.File;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.topcoder.timetracker.invoice.servicedetail.ConfigurationException;
import com.topcoder.timetracker.invoice.servicedetail.ServiceDetailManagerImpl;
import com.topcoder.timetracker.invoice.servicedetail.TransactionCreationException;
import com.topcoder.timetracker.invoice.servicedetail.ejb.LocalServiceDetail;
import com.topcoder.timetracker.invoice.servicedetail.ejb.LocalServiceDetailHome;
import com.topcoder.timetracker.invoice.servicedetail.ejb.ServiceDetailBean;

/**
 * Failure test cases for <code>ServiceDetailManagerImpl</code>.
 *
 * @author myxgyy
 * @version 1.0
 */
public class ServiceDetailManagerImplFailureTests extends TestCase {
	/**
	 * Represents the config file used for testing.
	 */
	private static final String CONFIG_FILE = "test_files" + File.separatorChar
	    + "Failure" + File.separatorChar + "ServiceDetailManagerFailure.xml";

    /**
     * <p>
     * Return the suite for this unit test.
     * </p>
     *
     * @return the suite
     */
    public static Test suite() {
        return new TestSuite(ServiceDetailManagerImplFailureTests.class);
    }

    /**
     * Sets the unit test up.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.loadXMLConfig(CONFIG_FILE);

        MockContextFactory.setAsInitial();

        Context context = new InitialContext();

        // creates an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

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
    	MockContextFactory.setAsInitial();

    	TestHelper.clearConfig();
    }

    /**
     * Test constructor for failure. Condition: namespace is null.
     * Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception to JUnit
     */
    public void testServiceDetailManagerImplWithNamespaceNull() throws Exception {
        try {
            new ServiceDetailManagerImpl(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: namespace is empty string. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception to JUnit.
     */
    public void testServiceDetailManagerImplWithNamespaceEmptyString() throws Exception {
        try {
            new ServiceDetailManagerImpl("   \n");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: namespace is unknown.
     * Expect: <code>ConfigurationException</code>.
     *
     * @throws Exception to JUnit.
     */
    public void testServiceDetailManagerImplWithNamespaceUnknown() throws Exception {
        try {
            new ServiceDetailManagerImpl("unknownnamespace");
            fail("Should throw ConfigurationException");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: there is no connection_string. Expect:
     * <code>ConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testServiceDetailManagerImplWithNamespaceNoConnectionString() throws Exception {
        try {
            new ServiceDetailManagerImpl("null");
            fail("Should throw ConfigurationException");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: there is no connection_string. Expect:
     * <code>ConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testServiceDetailManagerImplWithNamespaceEmptyConnectionString() throws Exception {
        try {
            new ServiceDetailManagerImpl("empty");
            fail("Should throw ConfigurationException");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Test <code>getService</code> for failure. Condition: the sessionBean is not in JNDI. Expect:
     * <code>TransactionCreationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetServiceNamingException() throws Exception {
        try {
            new MockServiceDetailManagerImpl("unknown").getService();
            fail("should throw TransactionCreationException");
        } catch (TransactionCreationException e) {
            // expected
        }
    }

    /**
     * <p>
     * This is a sub class of ServiceDetailManagerImpl to be used for test getService method.
     * </p>
     * @author myxgyy
     * @version 1.0
     */
    private class MockServiceDetailManagerImpl extends ServiceDetailManagerImpl {
    	/**
         * <p>
         * This constructor is used to set connectionString attribute.
         * </p>
         * @throws ConfigurationException
         *             if configuration process failed
         */
		public MockServiceDetailManagerImpl() throws ConfigurationException {
			super();
		}
		/**
	     * <p>
	     * This constructor is used to set connectionString attribute.
	     * </p>
	     * @param namespace
	     *            the given namespace
	     * @throws IllegalArgumentException
	     *             if namespace is null or empty String
	     * @throws ConfigurationException
	     *             if configuration process failed
	     */
		public MockServiceDetailManagerImpl(String namespace) throws ConfigurationException {
			super(namespace);
		}

		/**
	     * <p>
	     * This method is used to get local interface of session bean which is responsible for implementing
	     * transaction.
	     * </p>
	     * @return LocalServiceDetail instance, null impossible
	     * @throws TransactionCreationException
	     *             if impossible to create transaction
	     */
	    protected LocalServiceDetail getService() throws TransactionCreationException {
	    	return super.getService();
	    }
    }
}
