/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.j2ee;

import com.topcoder.search.builder.filter.EqualToFilter;

import com.topcoder.timetracker.entry.fixedbilling.DataAccessException;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;
import com.topcoder.timetracker.entry.fixedbilling.StringMatchType;
import com.topcoder.timetracker.entry.fixedbilling.TestHelper;

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
 * Unit test cases for <code>{@link FixedBillingStatusSessionBean}</code> class.
 *
 * @author flytoj2ee
 * @version 1.0
 */
public class FixedBillingStatusSessionBeanTest extends TestCase {
    /** The FixedBillingStatusSessionBean instance for testing. */
    private FixedBillingStatusManagerLocal bean;

    /** The FixedBillingStatus array instance for testing. */
    private FixedBillingStatus[] statuses;

    /**
     * Set up the initial values.
     *
     * @throws Exception to junit.
     */
    public void setUp() throws Exception {
        TestHelper.loadConfigFile(TestHelper.DB_FACTORY_CONFIGE_FILE_NAME);
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
                FixedBillingStatusManagerLocalHome.class, FixedBillingStatusManagerLocal.class,
                new FixedBillingStatusSessionBean());

        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(sampleServiceDescriptor);

        // Lookup the home
        Object entryLocalHomeObj = context.lookup("FixedBillingStatusManagerDelegateTest");

        // PortableRemoteObject does not do anything in MockEJB but it does no harm to call it
        FixedBillingStatusManagerLocalHome entryLocalHome =
            (FixedBillingStatusManagerLocalHome) PortableRemoteObject.narrow(entryLocalHomeObj,
                FixedBillingStatusManagerLocalHome.class);

        bean = (FixedBillingStatusManagerLocal) entryLocalHome.create();

        MockUserTransaction mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);
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
     * Test the <code>{@link FixedBillingStatusSessionBean#FixedBillingStatusSessionBean()}</code> with success
     * process.
     */
    public void testConstructor_Success() {
        assertNotNull("Unable to create the instance.", bean);
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#createFixedBillingStatus(FixedBillingStatus)}</code> with
     * IllegalArgumentException.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingStatus_FixedBillingStatus_IAEException()
        throws Exception {
        try {
            bean.createFixedBillingStatus(null);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#createFixedBillingStatus(FixedBillingStatus)}</code> with
     * IllegalArgumentException.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingStatus_FixedBillingStatus_DAOException()
        throws Exception {
        try {
            bean.createFixedBillingStatus(new FixedBillingStatus());
            fail("Should throw a IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#createFixedBillingStatus(FixedBillingStatus)}</code> with
     * success process.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingStatus_FixedBillingStatus_Success()
        throws Exception {
        bean.createFixedBillingStatus(statuses[0]);
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#updateFixedBillingStatus(FixedBillingStatus)}</code> with
     * IllegalArgumentException.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingStatus_FixedBillingStatus_IAEException()
        throws Exception {
        try {
            bean.updateFixedBillingStatus(null);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#updateFixedBillingStatus(FixedBillingStatus)}</code> with
     * IllegalArgumentException.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingStatus_FixedBillingStatus_DAOException()
        throws Exception {
        try {
            bean.createFixedBillingStatus(statuses[0]);

            FixedBillingStatus status = new FixedBillingStatus();
            status.setId(statuses[0].getId());
            bean.updateFixedBillingStatus(status);
            fail("Should throw a IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#updateFixedBillingStatus(FixedBillingStatus)}</code> with
     * success process.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingStatus_FixedBillingStatus_Success()
        throws Exception {
        bean.createFixedBillingStatus(statuses[0]);
        bean.updateFixedBillingStatus(statuses[0]);
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#deleteFixedBillingStatus(long)}</code> with
     * IllegalArgumentException.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingStatus_long_IAEException()
        throws Exception {
        try {
            bean.deleteFixedBillingStatus(-1);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#deleteFixedBillingStatus(long)}</code> with
     * DataAccessException.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingStatus_long_DAOException()
        throws Exception {
        try {
            bean.deleteFixedBillingStatus(10);
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#deleteFixedBillingStatus(long)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingStatus_long_Success()
        throws Exception {
        bean.createFixedBillingStatus(statuses[0]);
        bean.deleteFixedBillingStatus(statuses[0].getId());
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#getFixedBillingStatus(long)}</code> with
     * IllegalArgumentException.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingStatus_long_IAEException()
        throws Exception {
        try {
            bean.getFixedBillingStatus(-1);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#getFixedBillingStatus(long)}</code> with
     * DataAccessException.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingStatus_long_DAOException()
        throws Exception {
        try {
            bean.getFixedBillingStatus(10);
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#getFixedBillingStatus(long)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingStatus_long_Success()
        throws Exception {
        bean.createFixedBillingStatus(statuses[0]);
        bean.getFixedBillingStatus(statuses[0].getId());
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#searchFixedBillingStatuses(Filter)}</code> with
     * IllegalArgumentException.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchFixedBillingStatuses_Filter_IAEException()
        throws Exception {
        try {
            bean.searchFixedBillingStatuses(null);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#searchFixedBillingStatuses(Filter)}</code> with
     * DataAccessException.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchFixedBillingStatuses_Filter_DAOException()
        throws Exception {
        try {
            bean.searchFixedBillingStatuses(new EqualToFilter("wrong", "wrong"));
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#searchFixedBillingStatuses(Filter)}</code> with success
     * process.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchFixedBillingStatuses_Filter_Success()
        throws Exception {
        bean.createFixedBillingStatus(statuses[0]);
        bean.searchFixedBillingStatuses(bean.getFixedBillingStatusFilterFactory()
                                            .createDescriptionFilter("desc0", StringMatchType.EXACT_MATCH));
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#createFixedBillingStatuses(FixedBillingStatus[])}</code>
     * with IllegalArgumentException.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingStatuses_FixedBillingStatus_IAEException()
        throws Exception {
        try {
            bean.createFixedBillingStatuses(null);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#createFixedBillingStatuses(FixedBillingStatus[])}</code>
     * with IllegalArgumentException.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingStatuses_FixedBillingStatus_DAOException()
        throws Exception {
        try {
            bean.createFixedBillingStatuses(new FixedBillingStatus[] {new FixedBillingStatus() });
            fail("Should throw a IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#createFixedBillingStatuses(FixedBillingStatus[])}</code>
     * with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingStatuses_FixedBillingStatus_Success()
        throws Exception {
        bean.createFixedBillingStatuses(statuses);
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#updateFixedBillingStatuses(FixedBillingStatus[])}</code>
     * with IllegalArgumentException.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingStatuses_FixedBillingStatus_IAEException()
        throws Exception {
        try {
            bean.updateFixedBillingStatuses(null);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#updateFixedBillingStatuses(FixedBillingStatus[])}</code>
     * with IllegalArgumentException.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingStatuses_FixedBillingStatus_DAOException()
        throws Exception {
        try {
            bean.createFixedBillingStatus(statuses[0]);

            FixedBillingStatus status = new FixedBillingStatus();
            status.setId(statuses[0].getId());
            bean.updateFixedBillingStatuses(new FixedBillingStatus[] {status });
            fail("Should throw a IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#updateFixedBillingStatuses(FixedBillingStatus[])}</code>
     * with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingStatuses_FixedBillingStatus_Success()
        throws Exception {
        bean.createFixedBillingStatuses(statuses);
        bean.updateFixedBillingStatuses(statuses);
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#deleteFixedBillingStatuses(long[])}</code> with
     * IllegalArgumentException.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingStatuses_long_IAEException()
        throws Exception {
        try {
            bean.deleteFixedBillingStatuses(null);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#deleteFixedBillingStatuses(long[])}</code> with
     * DataAccessException.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingStatuses_long_DAOException()
        throws Exception {
        try {
            bean.deleteFixedBillingStatuses(new long[] {10 });
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#deleteFixedBillingStatuses(long[])}</code> with success
     * process.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingStatuses_long_Success()
        throws Exception {
        bean.createFixedBillingStatuses(statuses);
        bean.deleteFixedBillingStatuses(new long[] {statuses[0].getId(), statuses[1].getId() });
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#getFixedBillingStatuses(long[])}</code> with
     * IllegalArgumentException.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingStatuses_long_IAEException()
        throws Exception {
        try {
            bean.getFixedBillingStatuses(null);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#getFixedBillingStatuses(long[])}</code> with
     * DataAccessException.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingStatuses_long_DAOException()
        throws Exception {
        try {
            bean.getFixedBillingStatuses(new long[] {10 });
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#getFixedBillingStatuses(long[])}</code> with success
     * process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingStatuses_long_Success()
        throws Exception {
        bean.createFixedBillingStatuses(statuses);
        bean.getFixedBillingStatuses(new long[] {statuses[0].getId(), statuses[1].getId() });
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#getFixedBillingStatusFilterFactory()}</code> with success
     * process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingStatusFilterFactory_Success()
        throws Exception {
        bean.getFixedBillingStatusFilterFactory();
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#getAllFixedBillingStatuses()}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllFixedBillingStatuses_Success()
        throws Exception {
        bean.createFixedBillingStatuses(statuses);
        bean.getAllFixedBillingStatuses();
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#ejbCreate()}</code> with success process.
     */
    public void testEjbCreate_Success() {
        new FixedBillingStatusSessionBean().ejbCreate();
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#ejbActivate()}</code> with success process.
     */
    public void testEjbActivate_Success() {
        new FixedBillingStatusSessionBean().ejbActivate();
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#ejbPassivate()}</code> with success process.
     */
    public void testEjbPassivate_Success() {
        new FixedBillingStatusSessionBean().ejbPassivate();
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#ejbRemove()}</code> with success process.
     */
    public void testEjbRemove_Success() {
        new FixedBillingStatusSessionBean().ejbRemove();
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#setSessionContext(SessionContext)}</code> with success
     * process.
     */
    public void testSetSessionContext_SessionContext_Success() {
        new FixedBillingStatusSessionBean().setSessionContext(null);
    }

    /**
     * Test the <code>{@link FixedBillingStatusSessionBean#getSessionContext()}</code> with success process.
     */
    public void testGetSessionContext_Success() {
        new FixedBillingStatusSessionBean().getSessionContext();
    }

    /**
     * Returns the test suite of this test case.
     *
     * @return the test suite of this test case.
     */
    public static Test suite() {
        return new TestSuite(FixedBillingStatusSessionBeanTest.class);
    }
}
