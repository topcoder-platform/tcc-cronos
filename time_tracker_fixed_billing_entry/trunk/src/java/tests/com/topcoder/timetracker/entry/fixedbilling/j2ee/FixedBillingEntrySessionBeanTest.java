/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.j2ee;

import com.topcoder.timetracker.entry.fixedbilling.DataAccessException;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;
import com.topcoder.timetracker.entry.fixedbilling.StringMatchType;
import com.topcoder.timetracker.entry.fixedbilling.TestHelper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;

import org.mockejb.jndi.MockContextFactory;

import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.rmi.PortableRemoteObject;


/**
 * Unit test cases for <code>{@link FixedBillingEntrySessionBean}</code> class.
 *
 * @author flytoj2ee
 * @version 1.0
 */
public class FixedBillingEntrySessionBeanTest extends TestCase {
    /** The FixedBillingEntryManagerLocal instance for testing. */
    private FixedBillingEntryManagerLocal bean;

    /** The FixedBillingEntry array instance for testing. */
    private FixedBillingEntry[] entries;

    /**
     * Set up the initial values.
     *
     * @throws Exception to junit.
     */
    public void setUp() throws Exception {
        TestHelper.loadConfigFile(TestHelper.DB_FACTORY_CONFIGE_FILE_NAME);
        TestHelper.loadConfigFile(TestHelper.DB_CONFIGE_FILE_NAME);
        TestHelper.loadConfigFile(TestHelper.OBJECT_FACTORY_CONFIGE_FILE_NAME);
        entries = new FixedBillingEntry[3];

        for (int i = 0; i < entries.length; i++) {
            entries[i] = new FixedBillingEntry();

            FixedBillingStatus status = new FixedBillingStatus();
            status.setDescription("desc" + i);
            status.setCreationUser("user" + i);
            status.setModificationUser("modifyuser" + i);
            entries[i].setFixedBillingStatus(status);
            entries[i].setDescription("desc" + i);
            entries[i].setDate(new Date());
            entries[i].setCompanyId(100);
            entries[i].setCreationUser("user" + i);
            entries[i].setModificationUser("m-user" + i);
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

        bean = (FixedBillingEntryManagerLocal) entryLocalHome.create();

        MockUserTransaction mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);
        TestHelper.executeSQL("delete from fb_reject_reason");
        TestHelper.executeSQL("delete from fix_bill_entry");
        TestHelper.executeSQL("delete from fix_bill_status");
    }

    /**
     * Clear up the config.
     *
     * @throws Exception to junit.
     */
    public void tearDown() throws Exception {
        TestHelper.executeSQL("delete from fb_reject_reason");
        TestHelper.executeSQL("delete from fix_bill_entry");
        TestHelper.executeSQL("delete from fix_bill_status");
        TestHelper.removeNamespaces();
        MockContextFactory.revertSetAsInitial();
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#FixedBillingEntrySessionBean()}</code> with success process.
     */
    public void testConstructor_Success() {
        assertNotNull("Unable to create the instance.", bean);
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#createFixedBillingEntry(FixedBillingEntry, boolean)}</code>
     * with IllegalArgumentException.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingEntry_FixedBillingEntryboolean_IAEException()
        throws Exception {
        try {
            bean.createFixedBillingEntry(null, false);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#createFixedBillingEntry(FixedBillingEntry, boolean)}</code>
     * with IllegalArgumentException.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingEntry_FixedBillingEntryboolean_DAEException()
        throws Exception {
        try {
            FixedBillingEntry entry = new FixedBillingEntry();
            entry.setFixedBillingStatus(new FixedBillingStatus());
            bean.createFixedBillingEntry(entry, false);
            fail("Should throw a IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#createFixedBillingEntry(FixedBillingEntry, boolean)}</code>
     * with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingEntry_FixedBillingEntryboolean_Success()
        throws Exception {
        bean.createFixedBillingEntry(entries[0], false);
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#updateFixedBillingEntry(FixedBillingEntry, boolean)}</code>
     * with IllegalArgumentException.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingEntry_FixedBillingEntryboolean_IAEException()
        throws Exception {
        try {
            bean.updateFixedBillingEntry(null, false);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#updateFixedBillingEntry(FixedBillingEntry, boolean)}</code>
     * with IllegalArgumentException.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingEntry_FixedBillingEntryboolean_DAEException()
        throws Exception {
        try {
            FixedBillingEntry entry = new FixedBillingEntry();
            entry.setId(1000);
            entry.setFixedBillingStatus(new FixedBillingStatus());
            bean.updateFixedBillingEntry(entry, false);
            fail("Should throw a IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#updateFixedBillingEntry(FixedBillingEntry, boolean)}</code>
     * with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingEntry_FixedBillingEntryboolean_Success()
        throws Exception {
        bean.createFixedBillingEntry(entries[0], false);
        bean.updateFixedBillingEntry(entries[0], false);
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#deleteFixedBillingEntry(long, boolean)}</code> with
     * IllegalArgumentException.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingEntry_longlong_IAEException()
        throws Exception {
        try {
            bean.deleteFixedBillingEntry(-1, false);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#deleteFixedBillingEntry(long, boolean)}</code> with
     * DataAccessException.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingEntry_longlong_DAEException()
        throws Exception {
        try {
            bean.deleteFixedBillingEntry(100, false);
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#deleteFixedBillingEntry(long, boolean)}</code> with success
     * process.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingEntry_longlong_Success()
        throws Exception {
        bean.createFixedBillingEntry(entries[0], false);
        bean.deleteFixedBillingEntry(entries[0].getId(), false);
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#getFixedBillingEntry(long)}</code> with
     * IllegalArgumentException.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingEntry_long_IAEException()
        throws Exception {
        try {
            bean.getFixedBillingEntry(-1);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#getFixedBillingEntry(long)}</code> with DataAccessException.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingEntry_long_DAEException()
        throws Exception {
        try {
            bean.getFixedBillingEntry(100);
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#getFixedBillingEntry(long)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingEntry_long_Success()
        throws Exception {
        bean.createFixedBillingEntry(entries[0], false);
        bean.getFixedBillingEntry(entries[0].getId());
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#searchFixedBillingEntries(Filter)}</code> with
     * IllegalArgumentException.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchFixedBillingEntries_Filter_IAEException()
        throws Exception {
        try {
            bean.searchFixedBillingEntries(null);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#searchFixedBillingEntries(Filter)}</code> with success
     * process.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchFixedBillingEntries_Filter_Success()
        throws Exception {
        bean.createFixedBillingEntry(entries[0], false);
        bean.searchFixedBillingEntries(bean.getFixedBillingEntryFilterFactory()
                                           .createDescriptionFilter("desc0", StringMatchType.EXACT_MATCH));
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#createFixedBillingEntries(FixedBillingEntry[],
     * boolean)}</code> with IllegalArgumentException.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingEntries_FixedBillingEntryboolean_IAEException()
        throws Exception {
        try {
            bean.createFixedBillingEntries(null, false);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#createFixedBillingEntries(FixedBillingEntry[],
     * boolean)}</code> with IllegalArgumentException.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingEntries_FixedBillingEntryboolean_DAEException()
        throws Exception {
        try {
            FixedBillingEntry entry = new FixedBillingEntry();
            entry.setFixedBillingStatus(new FixedBillingStatus());
            bean.createFixedBillingEntries(new FixedBillingEntry[] {entry }, false);
            fail("Should throw a IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#createFixedBillingEntries(FixedBillingEntry[],
     * boolean)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingEntries_FixedBillingEntryboolean_Success()
        throws Exception {
        bean.createFixedBillingEntries(entries, false);
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#updateFixedBillingEntries(FixedBillingEntry[],
     * boolean)}</code> with IllegalArgumentException.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingEntries_FixedBillingEntryboolean_IAEException()
        throws Exception {
        try {
            bean.updateFixedBillingEntries(null, false);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#updateFixedBillingEntries(FixedBillingEntry[],
     * boolean)}</code> with IllegalArgumentException.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingEntries_FixedBillingEntryboolean_DAEException()
        throws Exception {
        try {
            FixedBillingEntry entry = new FixedBillingEntry();
            entry.setId(1000);
            entry.setFixedBillingStatus(new FixedBillingStatus());
            bean.updateFixedBillingEntries(new FixedBillingEntry[] {entry }, false);
            fail("Should throw a IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#updateFixedBillingEntries(FixedBillingEntry[],
     * boolean)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingEntries_FixedBillingEntryboolean_Success()
        throws Exception {
        bean.createFixedBillingEntries(entries, false);
        bean.updateFixedBillingEntries(entries, false);
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#deleteFixedBillingEntries(long[], boolean)}</code> with
     * IllegalArgumentException.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingEntries_longboolean_IAEException()
        throws Exception {
        try {
            bean.deleteFixedBillingEntries(null, false);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#deleteFixedBillingEntries(long[], boolean)}</code> with
     * DataAccessException.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingEntries_longboolean_DAEException()
        throws Exception {
        try {
            bean.deleteFixedBillingEntries(new long[] {1000 }, false);
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#deleteFixedBillingEntries(long[], boolean)}</code> with
     * success process.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingEntries_longboolean_Success()
        throws Exception {
        bean.createFixedBillingEntries(entries, false);
        bean.deleteFixedBillingEntries(new long[] {entries[0].getId(), entries[1].getId() }, false);
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#getFixedBillingEntries(long[])}</code> with
     * IllegalArgumentException.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingEntries_long_IAEException()
        throws Exception {
        try {
            bean.getFixedBillingEntries(null);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#getFixedBillingEntries(long[])}</code> with
     * DataAccessException.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingEntries_long_DAEException()
        throws Exception {
        try {
            bean.getFixedBillingEntries(new long[] {100, 10 });
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#getFixedBillingEntries(long[])}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingEntries_long_Success()
        throws Exception {
        bean.createFixedBillingEntries(entries, false);
        bean.getFixedBillingEntries(new long[] {entries[0].getId(), entries[1].getId() });
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#addRejectReasonToEntry(FixedBillingEntry, long,
     * boolean)}</code> with IllegalArgumentException.
     *
     * @throws Exception to JUnit.
     */
    public void testAddRejectReasonToEntry_FixedBillingEntrylongboolean_IAEException()
        throws Exception {
        try {
            bean.addRejectReasonToEntry(null, 10, false);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#addRejectReasonToEntry(FixedBillingEntry, long,
     * boolean)}</code> with DataAccessException.
     *
     * @throws Exception to JUnit.
     */
    public void testAddRejectReasonToEntry_FixedBillingEntrylongboolean_DAEException()
        throws Exception {
        try {
            FixedBillingEntry entry = new FixedBillingEntry();
            entry.setId(1000);
            bean.addRejectReasonToEntry(entry, 100, false);
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#addRejectReasonToEntry(FixedBillingEntry, long,
     * boolean)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testAddRejectReasonToEntry_FixedBillingEntrylongboolean_Success()
        throws Exception {
        bean.createFixedBillingEntries(entries, false);
        bean.addRejectReasonToEntry(entries[0], 100, false);
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#removeRejectReasonFromEntry(FixedBillingEntry, long,
     * boolean)}</code> with IllegalArgumentException.
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveRejectReasonFromEntry_FixedBillingEntrylongboolean_IAEException()
        throws Exception {
        try {
            bean.removeRejectReasonFromEntry(null, 10, false);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#removeRejectReasonFromEntry(FixedBillingEntry, long,
     * boolean)}</code> with DataAccessException.
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveRejectReasonFromEntry_FixedBillingEntrylongboolean_DAEException()
        throws Exception {
        try {
            FixedBillingEntry entry = new FixedBillingEntry();
            entry.setId(1000);
            bean.removeRejectReasonFromEntry(entry, 100, false);
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#removeRejectReasonFromEntry(FixedBillingEntry, long,
     * boolean)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveRejectReasonFromEntry_FixedBillingEntrylongboolean_Success()
        throws Exception {
        bean.createFixedBillingEntries(entries, false);
        bean.addRejectReasonToEntry(entries[0], 100, false);
        bean.removeRejectReasonFromEntry(entries[0], 100, false);
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#removeAllRejectReasonsFromEntry(FixedBillingEntry,
     * boolean)}</code> with IllegalArgumentException.
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveAllRejectReasonsFromEntry_FixedBillingEntryboolean_IAEException()
        throws Exception {
        try {
            bean.removeAllRejectReasonsFromEntry(null, false);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#removeAllRejectReasonsFromEntry(FixedBillingEntry,
     * boolean)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveAllRejectReasonsFromEntry_FixedBillingEntryboolean_Success()
        throws Exception {
        bean.createFixedBillingEntries(entries, false);
        bean.addRejectReasonToEntry(entries[0], 100, false);
        bean.removeAllRejectReasonsFromEntry(entries[0], false);
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#getAllRejectReasonsForEntry(FixedBillingEntry)}</code> with
     * IllegalArgumentException.
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllRejectReasonsForEntry_FixedBillingEntry_IAEException()
        throws Exception {
        try {
            bean.getAllRejectReasonsForEntry(null);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#getAllRejectReasonsForEntry(FixedBillingEntry)}</code> with
     * success process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllRejectReasonsForEntry_FixedBillingEntry_Success()
        throws Exception {
        bean.createFixedBillingEntries(entries, false);
        bean.addRejectReasonToEntry(entries[0], 100, false);
        bean.getAllRejectReasonsForEntry(entries[0]);
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#getAllFixedBillingEntries()}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllFixedBillingEntries_Success()
        throws Exception {
        bean.createFixedBillingEntries(entries, false);
        bean.addRejectReasonToEntry(entries[0], 100, false);
        bean.getAllFixedBillingEntries();
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#getFixedBillingEntryFilterFactory()}</code> with success
     * process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingEntryFilterFactory_Success()
        throws Exception {
        bean.getFixedBillingEntryFilterFactory();
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#ejbCreate()}</code> with success process.
     */
    public void testEjbCreate_Success() {
        new FixedBillingEntrySessionBean().ejbCreate();
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#ejbActivate()}</code> with success process.
     */
    public void testEjbActivate_Success() {
        new FixedBillingEntrySessionBean().ejbActivate();
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#ejbPassivate()}</code> with success process.
     */
    public void testEjbPassivate_Success() {
        new FixedBillingEntrySessionBean().ejbPassivate();
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#ejbRemove()}</code> with success process.
     */
    public void testEjbRemove_Success() {
        new FixedBillingEntrySessionBean().ejbRemove();
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#setSessionContext(SessionContext)}</code> with success
     * process.
     */
    public void testSetSessionContext_SessionContext_Success() {
        new FixedBillingEntrySessionBean().setSessionContext(null);
    }

    /**
     * Test the <code>{@link FixedBillingEntrySessionBean#getSessionContext()}</code> with success process.
     */
    public void testGetSessionContext_Success() {
        new FixedBillingEntrySessionBean().getSessionContext();
    }

    /**
     * Returns the test suite of this test case.
     *
     * @return the test suite of this test case.
     */
    public static Test suite() {
        return new TestSuite(FixedBillingEntrySessionBeanTest.class);
    }
}
