/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.entry.fixedbilling.j2ee.FixedBillingEntryManagerDelegate;
import com.topcoder.timetracker.entry.fixedbilling.j2ee.FixedBillingEntryManagerLocal;
import com.topcoder.timetracker.entry.fixedbilling.j2ee.FixedBillingEntryManagerLocalHome;
import com.topcoder.timetracker.entry.fixedbilling.j2ee.FixedBillingEntrySessionBean;
import com.topcoder.timetracker.entry.fixedbilling.j2ee.FixedBillingStatusManagerDelegate;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;


/**
 * The demo of this component.
 *
 * @author flytoj2ee
 * @version 1.0
 */
public class Demo extends TestCase {
    /** The FixedBillingEntry array instance for testing. */
    private FixedBillingEntry[] entries;

    /** The FixedBillingEntryManager instance for testing. */
    private FixedBillingEntryManager entryManager;

    /** The FixedBillingStatusManager instance for testing. */
    private FixedBillingStatusManager statusManager;

    /** The FixedBillingStatus array instance for testing. */
    private FixedBillingStatus[] statuses;

    /**
     * Set up the initial values.
     *
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
        entryManager = new FixedBillingEntryManagerDelegate("FixedBillingEntryManagerDelegateTest");
        sampleServiceDescriptor = new SessionBeanDescriptor("FixedBillingStatusManagerDelegateTest",
                FixedBillingStatusManagerLocalHome.class, FixedBillingStatusManagerLocal.class,
                new FixedBillingStatusSessionBean());

        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(sampleServiceDescriptor);
        statusManager = new FixedBillingStatusManagerDelegate("FixedBillingStatusManagerDelegateTest");
        initArray();

        TestHelper.executeSQL("delete from fb_reject_reason");
        TestHelper.executeSQL("delete from fix_bill_entry");
        TestHelper.executeSQL("delete from fix_bill_status");
    }

    /**
     * Init the array.
     */
    private void initArray() {
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

        statuses = new FixedBillingStatus[3];

        for (int i = 0; i < statuses.length; i++) {
            statuses[i] = new FixedBillingStatus();
            statuses[i].setDescription("desc" + i);
            statuses[i].setCreationUser("user" + i);
            statuses[i].setModificationUser("modifyuser" + i);
        }
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
    }

    /**
     * The demo of FixedBillingEntryManager with single FixedBillingEntry.
     *
     * @throws Exception to JUnit.
     */
    public void testDemo_EntryManager_Success() throws Exception {
        //Register the entry with the manager, with auditing.
        entryManager.createFixedBillingEntry(entries[0], true);

        FixedBillingEntry changingEntry = entryManager.getFixedBillingEntry(entries[0].getId());
        changingEntry.setDescription("Purchase Replacement Parts");
        entryManager.updateFixedBillingEntry(changingEntry, true);
        //Add a RejectReason to the Entry
        entryManager.addRejectReasonToEntry(changingEntry, 100, true);

        FixedBillingEntryFilterFactory filterFactory = entryManager.getFixedBillingEntryFilterFactory();
        List criteria = new ArrayList();
        criteria.add(filterFactory.createDescriptionFilter("Purchase", StringMatchType.SUBSTRING));
        criteria.add(filterFactory.createModificationUserFilter("m-user0", StringMatchType.EXACT_MATCH));

        //Create a search filter that aggregates the criteria.
        Filter searchFilter = new AndFilter(criteria);
        FixedBillingEntry[] matchingEntries = entryManager.searchFixedBillingEntries(searchFilter);
        assertEquals("The return value should be same.", 1, matchingEntries.length);
        //Delete the users; auditing is performed.
        entryManager.deleteFixedBillingEntry(matchingEntries[0].getId(), true);
    }

    /**
     * The demo of FixedBillingEntryManager with FixedBillingEntry array.
     *
     * @throws Exception to JUnit.
     */
    public void testDemo_EntryManagerBatch_Success() throws Exception {
        entryManager.createFixedBillingEntries(entries, true);

        FixedBillingEntry[] changingEntries = entryManager.getFixedBillingEntries(new long[] {
                    entries[0].getId(), entries[1].getId(), entries[2].getId()});
        changingEntries[0].setDescription("Purchase Replacement Parts");
        entryManager.updateFixedBillingEntries(changingEntries, true);
        entryManager.addRejectReasonToEntry(changingEntries[0], 100, true);

        long[] rejectReasonIds = entryManager.getAllRejectReasonsForEntry(changingEntries[0]);

        //A for loop will be used to feed the rejectReasonIds singularly.  The application container
        //will take care of rolling back any failed transactions.
        for (int x = 0; x < rejectReasonIds.length; x++) {
            entryManager.removeRejectReasonFromEntry(changingEntries[0], 100, true);
        }

        entryManager.deleteFixedBillingEntries(
                new long[] {entries[0].getId(), entries[1].getId(), entries[2].getId() }, true);
    }

    /**
     * The demo of FixedBillingStatusManager with single FixedBillingStatus.
     *
     * @throws Exception to JUnit.
     */
    public void testDemo_StatusManager_Success() throws Exception {
        //Create the status in the manager.
        statusManager.createFixedBillingStatus(statuses[0]);
        statuses[0].setDescription("Tentative Approval");
        statusManager.updateFixedBillingStatus(statuses[0]);

        FixedBillingStatusFilterFactory statusFilterFactory = statusManager.getFixedBillingStatusFilterFactory();
        FixedBillingStatus statusResults =
            statusManager.searchFixedBillingStatuses(statusFilterFactory.createDescriptionFilter(
                    "Tentative Approval", StringMatchType.EXACT_MATCH))[0];
        statusManager.deleteFixedBillingStatus(statusResults.getId());
    }

    /**
     * The demo of FixedBillingStatusManager with FixedBillingStatus array.
     *
     * @throws Exception to JUnit.
     */
    public void testDemo_StatusManagerBatch_Success() throws Exception {
        statusManager.createFixedBillingStatuses(statuses);
        statuses[0].setDescription("Tentative Approval");
        statusManager.updateFixedBillingStatuses(statuses);
        statusManager.getFixedBillingStatuses(new long[] {statuses[0].getId(), statuses[1].getId() });
        statusManager.getAllFixedBillingStatuses();
        statusManager.deleteFixedBillingStatuses(new long[] {statuses[0].getId(), statuses[1].getId() });
    }

    /**
     * Returns the test suite of this test case.
     *
     * @return the test suite of this test case.
     */
    public static Test suite() {
        return new TestSuite(Demo.class);
    }
}
