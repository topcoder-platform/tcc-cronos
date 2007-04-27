/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

import com.topcoder.timetracker.entry.fixedbilling.j2ee.FixedBillingEntryManagerLocal;
import com.topcoder.timetracker.entry.fixedbilling.j2ee.FixedBillingEntryManagerLocalHome;
import com.topcoder.timetracker.entry.fixedbilling.j2ee.FixedBillingEntrySessionBean;
import com.topcoder.timetracker.entry.fixedbilling.j2ee.FixedBillingStatusManagerLocal;
import com.topcoder.timetracker.entry.fixedbilling.j2ee.FixedBillingStatusManagerLocalHome;
import com.topcoder.timetracker.entry.fixedbilling.j2ee.FixedBillingStatusSessionBean;
import com.topcoder.timetracker.entry.fixedbilling.j2ee.MockUserTransaction;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;

import org.mockejb.jndi.MockContextFactory;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.rmi.PortableRemoteObject;


/**
 * Unit test cases for <code>{@link ManagerFactory}</code> class. It's for invalid configuration.
 *
 * @author flytoj2ee
 * @version 1.0
 */
public class ManagerFactoryInvalidTest extends TestCase {
    /** The FixedBillingEntryManagerLocal instance for testing. */
    private FixedBillingEntryManagerLocal entryBean;

    /** The FixedBillingStatusManagerLocal instance for testing. */
    private FixedBillingStatusManagerLocal statusBean;

    /**
     * Set up the initial values.
     * @throws Exception to JUnit.
     */
    public void setUp() throws Exception {
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
            new SessionBeanDescriptor("FixedBillingEntryManagerDelegateTest",
                FixedBillingEntryManagerLocalHome.class, FixedBillingEntryManagerLocal.class,
                new FixedBillingEntrySessionBean());

        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(sampleServiceDescriptor);

        // Lookup the home
        Object entryLocalHomeObj = context.lookup("FixedBillingEntryManagerDelegateTest");

        // PortableRemoteObject does not do anything in MockEJB but it does no harm to call it
        FixedBillingEntryManagerLocalHome entryLocalHome =
            (FixedBillingEntryManagerLocalHome) PortableRemoteObject.narrow(entryLocalHomeObj,
                FixedBillingEntryManagerLocalHome.class);

        entryBean = (FixedBillingEntryManagerLocal) entryLocalHome.create();

        sampleServiceDescriptor = new SessionBeanDescriptor("FixedBillingStatusManagerDelegateTest",
                FixedBillingStatusManagerLocalHome.class, FixedBillingStatusManagerLocal.class,
                new FixedBillingStatusSessionBean());

        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(sampleServiceDescriptor);

        // Lookup the home
        entryLocalHomeObj = context.lookup("FixedBillingStatusManagerDelegateTest");

        // PortableRemoteObject does not do anything in MockEJB but it does no harm to call it
        FixedBillingStatusManagerLocalHome entryLocalHome2 =
            (FixedBillingStatusManagerLocalHome) PortableRemoteObject.narrow(entryLocalHomeObj,
                FixedBillingStatusManagerLocalHome.class);

        statusBean = (FixedBillingStatusManagerLocal) entryLocalHome2.create();

        MockUserTransaction mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);
    }

    /**
     * Test the <code>FixedBillingEntrySessionBean</code> with configuration exception. Should throw a
     * DataAccessException here.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingEntry_FixedBillingEntryboolean_ConfigException()
        throws Exception {
        try {
            entryBean.createFixedBillingEntry(null, false);
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>FixedBillingEntrySessionBean</code> with configuration exception. Should throw a
     * DataAccessException here.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingEntry_FixedBillingEntryboolean_ConfigException()
        throws Exception {
        try {
            entryBean.updateFixedBillingEntry(null, false);
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>FixedBillingEntrySessionBean</code> with configuration exception. Should throw a
     * DataAccessException here.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingEntry_longlong_ConfigException()
        throws Exception {
        try {
            entryBean.deleteFixedBillingEntry(10, false);
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>FixedBillingEntrySessionBean</code> with configuration exception. Should throw a
     * DataAccessException here.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingEntry_long_ConfigException()
        throws Exception {
        try {
            entryBean.getFixedBillingEntry(10);
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>FixedBillingEntrySessionBean</code> with configuration exception. Should throw a
     * DataAccessException here.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchFixedBillingEntries_Filter_ConfigException()
        throws Exception {
        try {
            entryBean.searchFixedBillingEntries(null);
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>FixedBillingEntrySessionBean</code> with configuration exception. Should throw a
     * DataAccessException here.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingEntries_FixedBillingEntryboolean_ConfigException()
        throws Exception {
        try {
            entryBean.createFixedBillingEntries(null, false);
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>FixedBillingEntrySessionBean</code> with configuration exception. Should throw a
     * DataAccessException here.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingEntries_FixedBillingEntryboolean_ConfigException()
        throws Exception {
        try {
            entryBean.updateFixedBillingEntries(null, false);
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>FixedBillingEntrySessionBean</code> with configuration exception. Should throw a
     * DataAccessException here.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingEntries_longboolean_ConfigException()
        throws Exception {
        try {
            entryBean.deleteFixedBillingEntries(null, false);
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>FixedBillingEntrySessionBean</code> with configuration exception. Should throw a
     * DataAccessException here.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingEntries_long_ConfigException()
        throws Exception {
        try {
            entryBean.getFixedBillingEntries(null);
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>FixedBillingEntrySessionBean</code> with configuration exception. Should throw a
     * DataAccessException here.
     *
     * @throws Exception to JUnit.
     */
    public void testAddRejectReasonToEntry_FixedBillingEntrylongboolean_ConfigException()
        throws Exception {
        try {
            entryBean.addRejectReasonToEntry(null, 10, false);
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>FixedBillingEntrySessionBean</code> with configuration exception. Should throw a
     * DataAccessException here.
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveRejectReasonFromEntry_FixedBillingEntrylongboolean_ConfigException()
        throws Exception {
        try {
            entryBean.removeRejectReasonFromEntry(null, 10, false);
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>FixedBillingEntrySessionBean</code> with configuration exception. Should throw a
     * DataAccessException here.
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveAllRejectReasonsFromEntry_FixedBillingEntryboolean_ConfigException()
        throws Exception {
        try {
            entryBean.removeAllRejectReasonsFromEntry(null, false);
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>FixedBillingEntrySessionBean</code> with configuration exception. Should throw a
     * DataAccessException here.
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllRejectReasonsForEntry_FixedBillingEntry_ConfigException()
        throws Exception {
        try {
            entryBean.getAllRejectReasonsForEntry(null);
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>FixedBillingEntrySessionBean</code> with configuration exception. Should throw a
     * DataAccessException here.
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllFixedBillingEntries_ConfigException()
        throws Exception {
        try {
            entryBean.getAllFixedBillingEntries();
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>FixedBillingEntrySessionBean</code> with configuration exception. Should throw a
     * DataAccessException here.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingEntryFilterFactory_ConfigException()
        throws Exception {
        try {
            entryBean.getFixedBillingEntryFilterFactory();
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>FixedBillingStatusSessionBean</code> with configuration exception. Should throw a
     * DataAccessException here.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingStatus_FixedBillingStatus_ConfigException()
        throws Exception {
        try {
            statusBean.createFixedBillingStatus(null);
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>FixedBillingStatusSessionBean</code> with configuration exception. Should throw a
     * DataAccessException here.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingStatus_FixedBillingStatus_ConfigException()
        throws Exception {
        try {
            statusBean.updateFixedBillingStatus(null);
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>FixedBillingStatusSessionBean</code> with configuration exception. Should throw a
     * DataAccessException here.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingStatus_long_ConfigException()
        throws Exception {
        try {
            statusBean.deleteFixedBillingStatus(10);
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>FixedBillingStatusSessionBean</code> with configuration exception. Should throw a
     * DataAccessException here.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingStatus_long_ConfigException()
        throws Exception {
        try {
            statusBean.getFixedBillingStatus(10);
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>FixedBillingStatusSessionBean</code> with configuration exception. Should throw a
     * DataAccessException here.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchFixedBillingStatuses_Filter_ConfigException()
        throws Exception {
        try {
            statusBean.searchFixedBillingStatuses(null);
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>FixedBillingStatusSessionBean</code> with configuration exception. Should throw a
     * DataAccessException here.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingStatuses_FixedBillingStatus_ConfigException()
        throws Exception {
        try {
            statusBean.createFixedBillingStatuses(null);
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>FixedBillingStatusSessionBean</code> with configuration exception. Should throw a
     * DataAccessException here.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingStatuses_FixedBillingStatus_ConfigException()
        throws Exception {
        try {
            statusBean.updateFixedBillingStatuses(null);
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>FixedBillingStatusSessionBean</code> with configuration exception. Should throw a
     * DataAccessException here.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingStatuses_long_ConfigException()
        throws Exception {
        try {
            statusBean.deleteFixedBillingStatuses(null);
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>FixedBillingStatusSessionBean</code> with configuration exception. Should throw a
     * DataAccessException here.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingStatuses_long_ConfigException()
        throws Exception {
        try {
            statusBean.getFixedBillingStatuses(null);
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>FixedBillingStatusSessionBean</code> with configuration exception. Should throw a
     * DataAccessException here.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingStatusFilterFactory_ConfigException()
        throws Exception {
        try {
            statusBean.getFixedBillingStatusFilterFactory();
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>FixedBillingStatusSessionBean</code> with configuration exception. Should throw a
     * DataAccessException here.
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllFixedBillingStatuses_ConfigException()
        throws Exception {
        try {
            statusBean.getAllFixedBillingStatuses();
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Returns the test suite of this test case.
     *
     * @return the test suite of this test case.
     */
    public static Test suite() {
        return new TestSuite(ManagerFactoryInvalidTest.class);
    }
}
