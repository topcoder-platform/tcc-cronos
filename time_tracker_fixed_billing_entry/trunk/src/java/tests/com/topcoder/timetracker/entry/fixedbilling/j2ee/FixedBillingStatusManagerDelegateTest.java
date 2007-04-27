/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.j2ee;


import com.topcoder.timetracker.entry.fixedbilling.ConfigurationException;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;
import com.topcoder.timetracker.entry.fixedbilling.StringMatchType;
import com.topcoder.timetracker.entry.fixedbilling.TestHelper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;


/**
 * Unit test cases for <code>{@link FixedBillingStatusManagerDelegate}</code> class.
 *
 * @author flytoj2ee
 * @version 1.0
 */
public class FixedBillingStatusManagerDelegateTest extends TestCase {
    /** The FixedBillingStatusManagerDelegate instance for testing. */
    private FixedBillingStatusManagerDelegate delegate;

    /** The FixedBillingStatus array instance for testing. */
    private FixedBillingStatus[] statuses;

    /**
     * Set up the initial values.
     * @throws Exception to JUnit.
     */
    public void setUp() throws Exception {
        TestHelper.loadConfigFile(TestHelper.DB_FACTORY_CONFIGE_FILE_NAME);
        TestHelper.loadConfigFile(TestHelper.J2EE_CONFIGE_FILE_NAME);
        TestHelper.loadConfigFile(TestHelper.DB_CONFIGE_FILE_NAME);
        TestHelper.loadConfigFile(TestHelper.OBJECT_FACTORY_CONFIGE_FILE_NAME);

        statuses = new FixedBillingStatus[3];

        for (int i = 0; i < statuses.length; i++) {
            statuses[i] = new FixedBillingStatus();
            statuses[i].setDescription("desc" + i);
            statuses[i].setCreationUser("user" + i);
            statuses[i].setModificationUser("modifyuser" + i);
        }

        /*
         * We need to set MockContextFactory as our JNDI provider. This method sets the necessary system properties.
         */
        MockContextFactory.setAsInitial();

        // create the initial context that will be used for binding EJBs
        Context context = new InitialContext();

        // Create an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        /*
         * Create deployment descriptor of our sample bean. MockEjb uses it instead of XML deployment descriptors
         */
        SessionBeanDescriptor sampleServiceDescriptor =
            new SessionBeanDescriptor("FixedBillingStatusManagerDelegateTest",
                FixedBillingStatusManagerLocalHome.class,
                FixedBillingStatusManagerLocal.class,
                new FixedBillingStatusSessionBean());

        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(sampleServiceDescriptor);
        MockUserTransaction mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);
        delegate = new FixedBillingStatusManagerDelegate("FixedBillingStatusManagerDelegateTest");
        TestHelper.executeSQL("delete from fix_bill_status");
    }

    /**
     * Clear up the config.
     *
     * @throws Exception to junit.
     */
    public void tearDown() throws Exception {
        TestHelper.executeSQL("delete from fix_bill_status");
        TestHelper.removeNamespaces();
        MockContextFactory.revertSetAsInitial();
    }

    /**
     * Test <code>{@link FixedBillingStatusManagerDelegate#FixedBillingStatusManagerDelegate(String)}</code> with null
     * String. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_String_Null() throws Exception {
        try {
            new FixedBillingStatusManagerDelegate(null);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test <code>{@link FixedBillingStatusManagerDelegate#FixedBillingStatusManagerDelegate(String)}</code> with empty
     * String. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_String_Empty() throws Exception {
        try {
            new FixedBillingStatusManagerDelegate(" ");
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test <code>{@link FixedBillingStatusManagerDelegate#FixedBillingStatusManagerDelegate(String)}</code> with wrong
     * String. Should throw a ConfigurationException here.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_String_Wrong() throws Exception {
        try {
            new FixedBillingStatusManagerDelegate("wrong name space");
            fail("Should throw a ConfigurationException here.");
        } catch (ConfigurationException ce) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingStatusManagerDelegate#FixedBillingStatusManagerDelegate(String)}</code> with
     * wrong String. Should throw a ConfigurationException here.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_String_EmptyLocalHomeName() throws Exception {
        try {
            new FixedBillingStatusManagerDelegate("FixedBillingStatusManagerDelegateTestEmptyLocalHomeName");
            fail("Should throw a ConfigurationException here.");
        } catch (ConfigurationException ce) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingStatusManagerDelegate#FixedBillingStatusManagerDelegate(String)}</code> with
     * empty context.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_String_EmptyContext() throws Exception {
        delegate = new FixedBillingStatusManagerDelegate("FixedBillingStatusManagerDelegateTestContext");
        assertNotNull("Unable to create the instance.", delegate);
    }

    /**
     * Test <code>{@link FixedBillingStatusManagerDelegate#FixedBillingStatusManagerDelegate(String)}</code> with
     * success process.
     * @throws Exception to JUnit.
     */
    public void testConstructor_String_Success() throws Exception {
        delegate = new FixedBillingStatusManagerDelegate("FixedBillingStatusManagerDelegateTest");
        assertNotNull("Unable to create the instance.", delegate);
    }

    /**
     * Test <code>{@link FixedBillingStatusManagerDelegate#createFixedBillingStatus(FixedBillingStatus)}</code> with
     * success process.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingStatus_FixedBillingStatus_Success()
        throws Exception {
        delegate.createFixedBillingStatus(statuses[0]);
    }

    /**
     * Test <code>{@link FixedBillingStatusManagerDelegate#updateFixedBillingStatus(FixedBillingStatus)}</code> with
     * success process.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingStatus_FixedBillingStatus_Success()
        throws Exception {
        delegate.createFixedBillingStatus(statuses[0]);
        delegate.updateFixedBillingStatus(statuses[0]);
    }

    /**
     * Test <code>{@link FixedBillingStatusManagerDelegate#deleteFixedBillingStatus(long)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingStatus_long_Success()
        throws Exception {
        delegate.createFixedBillingStatus(statuses[0]);
        delegate.deleteFixedBillingStatus(statuses[0].getId());
    }

    /**
     * Test <code>{@link FixedBillingStatusManagerDelegate#getFixedBillingStatus(long)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingStatus_long_Success()
        throws Exception {
        delegate.createFixedBillingStatus(statuses[0]);
        delegate.getFixedBillingStatus(statuses[0].getId());
    }

    /**
     * Test <code>{@link FixedBillingStatusManagerDelegate#searchFixedBillingStatuses(Filter)}</code> with success
     * process.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchFixedBillingStatuses_Filter_Success()
        throws Exception {
        delegate.createFixedBillingStatus(statuses[0]);
        delegate.searchFixedBillingStatuses(delegate.getFixedBillingStatusFilterFactory()
                                                    .createDescriptionFilter("desc0", StringMatchType.EXACT_MATCH));
    }

    /**
     * Test <code>{@link FixedBillingStatusManagerDelegate#createFixedBillingStatuses(FixedBillingStatus[])}</code>
     * with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingStatuses_FixedBillingStatus_Success()
        throws Exception {
        delegate.createFixedBillingStatuses(statuses);
    }

    /**
     * Test <code>{@link FixedBillingStatusManagerDelegate#updateFixedBillingStatuses(FixedBillingStatus[])}</code>
     * with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingStatuses_FixedBillingStatus_Success()
        throws Exception {
        delegate.createFixedBillingStatuses(statuses);
        delegate.updateFixedBillingStatuses(statuses);
    }

    /**
     * Test <code>{@link FixedBillingStatusManagerDelegate#deleteFixedBillingStatuses(long[])}</code> with success
     * process.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingStatuses_long_Success()
        throws Exception {
        delegate.createFixedBillingStatuses(statuses);
        delegate.deleteFixedBillingStatuses(new long[] {statuses[0].getId(), statuses[1].getId() });
    }

    /**
     * Test <code>{@link FixedBillingStatusManagerDelegate#getFixedBillingStatuses(long[])}</code> with success
     * process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingStatuses_long_Success()
        throws Exception {
        delegate.createFixedBillingStatuses(statuses);
        delegate.getFixedBillingStatuses(new long[] {statuses[0].getId(), statuses[1].getId() });
    }

    /**
     * Test <code>{@link FixedBillingStatusManagerDelegate#getFixedBillingStatusFilterFactory()}</code> with success
     * process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingStatusFilterFactory_Success()
        throws Exception {
        delegate.getFixedBillingStatusFilterFactory();
    }

    /**
     * Test <code>{@link FixedBillingStatusManagerDelegate#getAllFixedBillingStatuses()}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllFixedBillingStatuses_Success()
        throws Exception {
        delegate.createFixedBillingStatuses(statuses);
        delegate.getAllFixedBillingStatuses();
    }

    /**
     * Returns the test suite of this test case.
     *
     * @return the test suite of this test case.
     */
    public static Test suite() {
        return new TestSuite(FixedBillingStatusManagerDelegateTest.class);
    }
}
