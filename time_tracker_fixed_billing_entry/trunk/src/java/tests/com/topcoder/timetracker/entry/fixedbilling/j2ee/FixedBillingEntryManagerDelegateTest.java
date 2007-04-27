/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.j2ee;


import com.topcoder.timetracker.entry.fixedbilling.ConfigurationException;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;
import com.topcoder.timetracker.entry.fixedbilling.StringMatchType;
import com.topcoder.timetracker.entry.fixedbilling.TestHelper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;


/**
 * Unit test cases for <code>{@link FixedBillingEntryManagerDelegate}</code> class.
 *
 * @author flytoj2ee
 * @version 1.0
 */
public class FixedBillingEntryManagerDelegateTest extends TestCase {
    /** The FixedBillingEntryManagerDelegate instance for testing. */
    private FixedBillingEntryManagerDelegate delegate;

    /** The FixedBillingEntry array instance for testing. */
    private FixedBillingEntry[] entries;

    /**
     * Set up the initial values.
     * @throws Exception to JUnit.
     */
    public void setUp() throws Exception {
        TestHelper.loadConfigFile(TestHelper.DB_FACTORY_CONFIGE_FILE_NAME);
        TestHelper.loadConfigFile(TestHelper.J2EE_CONFIGE_FILE_NAME);
        TestHelper.loadConfigFile(TestHelper.DB_CONFIGE_FILE_NAME);
        TestHelper.loadConfigFile(TestHelper.OBJECT_FACTORY_CONFIGE_FILE_NAME);
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
        MockUserTransaction mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);
        delegate = new FixedBillingEntryManagerDelegate("FixedBillingEntryManagerDelegateTest");
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
     * Test the <code>{@link FixedBillingEntryManagerDelegate#FixedBillingEntryManagerDelegate(String)}</code> with
     * null String. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_String_Null() throws Exception {
        try {
            new FixedBillingEntryManagerDelegate(null);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerDelegate#FixedBillingEntryManagerDelegate(String)}</code> with
     * empty String. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_String_Empty() throws Exception {
        try {
            new FixedBillingEntryManagerDelegate(" ");
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerDelegate#FixedBillingEntryManagerDelegate(String)}</code> with
     * wrong String. Should throw a ConfigurationException here.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_String_Wrong() throws Exception {
        try {
            new FixedBillingEntryManagerDelegate("wrong name space");
            fail("Should throw a ConfigurationException here.");
        } catch (ConfigurationException ce) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerDelegate#FixedBillingEntryManagerDelegate(String)}</code> with
     * wrong String. Should throw a ConfigurationException here.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_String_EmptyLocalHomeName() throws Exception {
        try {
            new FixedBillingEntryManagerDelegate("FixedBillingEntryManagerDelegateTestEmptyLocalHomeName");
            fail("Should throw a ConfigurationException here.");
        } catch (ConfigurationException ce) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerDelegate#FixedBillingEntryManagerDelegate(String)}</code> with
     * empty context.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_String_EmptyContext() throws Exception {
        delegate = new FixedBillingEntryManagerDelegate("FixedBillingEntryManagerDelegateTestContext");
        assertNotNull("Unable to create the instance.", delegate);
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerDelegate#FixedBillingEntryManagerDelegate(String)}</code> with
     * success process.
     */
    public void testConstructor_String_Success() {
        assertNotNull("Unable to create the instance.", delegate);
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerDelegate#createFixedBillingEntry(FixedBillingEntry,
     * boolean)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingEntry_FixedBillingEntryboolean_Success()
        throws Exception {
        delegate.createFixedBillingEntry(entries[0], false);
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerDelegate#updateFixedBillingEntry(FixedBillingEntry,
     * boolean)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingEntry_FixedBillingEntryboolean_Success()
        throws Exception {
        delegate.createFixedBillingEntry(entries[0], false);
        delegate.updateFixedBillingEntry(entries[0], false);
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerDelegate#deleteFixedBillingEntry(long, boolean)}</code> with
     * success process.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingEntry_longlong_Success()
        throws Exception {
        delegate.createFixedBillingEntry(entries[0], false);
        delegate.deleteFixedBillingEntry(entries[0].getId(), false);
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerDelegate#getFixedBillingEntry(long)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingEntry_long_Success()
        throws Exception {
        delegate.createFixedBillingEntry(entries[0], false);
        delegate.getFixedBillingEntry(entries[0].getId());
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerDelegate#searchFixedBillingEntries(Filter)}</code> with success
     * process.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchFixedBillingEntries_Filter_Success()
        throws Exception {
        delegate.createFixedBillingEntry(entries[0], false);
        delegate.searchFixedBillingEntries(delegate.getFixedBillingEntryFilterFactory()
                                                   .createDescriptionFilter("desc0", StringMatchType.EXACT_MATCH));
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerDelegate#createFixedBillingEntries(FixedBillingEntry[],
     * boolean)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingEntries_FixedBillingEntryboolean_Success()
        throws Exception {
        delegate.createFixedBillingEntries(entries, false);
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerDelegate#updateFixedBillingEntries(FixedBillingEntry[],
     * boolean)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingEntries_FixedBillingEntryboolean_Success()
        throws Exception {
        delegate.createFixedBillingEntries(entries, false);
        delegate.updateFixedBillingEntries(entries, false);
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerDelegate#deleteFixedBillingEntries(long[], boolean)}</code> with
     * success process.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingEntries_longboolean_Success()
        throws Exception {
        delegate.createFixedBillingEntries(entries, false);
        delegate.deleteFixedBillingEntries(new long[] {entries[0].getId(), entries[1].getId() }, false);
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerDelegate#getFixedBillingEntries(long[])}</code> with success
     * process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingEntries_long_Success()
        throws Exception {
        delegate.createFixedBillingEntries(entries, false);
        delegate.getFixedBillingEntries(new long[] {entries[0].getId(), entries[1].getId() });
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerDelegate#addRejectReasonToEntry(FixedBillingEntry, long,
     * boolean)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testAddRejectReasonToEntry_FixedBillingEntrylongboolean_Success()
        throws Exception {
        delegate.createFixedBillingEntries(entries, false);
        delegate.addRejectReasonToEntry(entries[0], 100, false);
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerDelegate#removeRejectReasonFromEntry(FixedBillingEntry, long,
     * boolean)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveRejectReasonFromEntry_FixedBillingEntrylongboolean_Success()
        throws Exception {
        delegate.createFixedBillingEntries(entries, false);
        delegate.addRejectReasonToEntry(entries[0], 100, false);
        delegate.removeRejectReasonFromEntry(entries[0], 100, false);
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerDelegate#removeAllRejectReasonsFromEntry(FixedBillingEntry,
     * boolean)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveAllRejectReasonsFromEntry_FixedBillingEntryboolean_Success()
        throws Exception {
        delegate.createFixedBillingEntries(entries, false);
        delegate.addRejectReasonToEntry(entries[0], 100, false);
        delegate.removeAllRejectReasonsFromEntry(entries[0], false);
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerDelegate#getAllRejectReasonsForEntry(FixedBillingEntry)}</code>
     * with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllRejectReasonsForEntry_FixedBillingEntry_Success()
        throws Exception {
        delegate.createFixedBillingEntries(entries, false);
        delegate.addRejectReasonToEntry(entries[0], 100, false);
        delegate.getAllRejectReasonsForEntry(entries[0]);
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerDelegate#getAllFixedBillingEntries()}</code> with success
     * process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllFixedBillingEntries_Success()
        throws Exception {
        delegate.createFixedBillingEntries(entries, false);
        delegate.addRejectReasonToEntry(entries[0], 100, false);
        delegate.getAllFixedBillingEntries();
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerDelegate#getFixedBillingEntryFilterFactory()}</code> with success
     * process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingEntryFilterFactory_Success()
        throws Exception {
        delegate.getFixedBillingEntryFilterFactory();
    }

    /**
     * Returns the test suite of this test case.
     *
     * @return the test suite of this test case.
     */
    public static Test suite() {
        return new TestSuite(FixedBillingEntryManagerDelegateTest.class);
    }
}
