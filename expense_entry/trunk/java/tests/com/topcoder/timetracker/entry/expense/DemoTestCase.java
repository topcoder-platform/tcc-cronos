/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.expense.criteria.CompositeCriteria;
import com.topcoder.timetracker.entry.expense.criteria.Criteria;
import com.topcoder.timetracker.entry.expense.criteria.FieldBetweenCriteria;
import com.topcoder.timetracker.entry.expense.criteria.FieldLikeCriteria;
import com.topcoder.timetracker.entry.expense.criteria.FieldMatchCriteria;
import com.topcoder.timetracker.entry.expense.criteria.NotCriteria;
import com.topcoder.timetracker.entry.expense.criteria.RejectReasonCriteria;
import com.topcoder.timetracker.entry.expense.delegate.ExpenseEntryManagerLocalDelegate;
import com.topcoder.timetracker.entry.expense.delegate.ExpenseStatusManagerLocalDelegate;
import com.topcoder.timetracker.entry.expense.delegate.ExpenseTypeManagerLocalDelegate;
import com.topcoder.timetracker.rejectreason.RejectReason;
import com.topcoder.util.config.ConfigManager;


/**
 * <p>
 * Demostrates the usage of this component. The persistence classes should not be used directly by user, and thus are
 * not demostrated.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DemoTestCase extends TestCase {
    /** Represents the connection instance for testing. */
    private Connection connection = null;

    /** The description of reject reason. */
    private final String description1 = "description1";

    /**
     * <p>
     * Sets up the test environment. The test instance is created. The test configuration namespace will be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        UnitTestHelper.clearConfig();
        UnitTestHelper.addConfig("DBConnectionFactory_Config.xml");
        UnitTestHelper.addConfig("ObjectFactory_Config.xml");
        UnitTestHelper.addConfig("InformixExpenseEntryDAO_Config.xml");
        UnitTestHelper.addConfig("BasicExpenseEntryBean_Config.xml");
        UnitTestHelper.addConfig("ExpenseEntryBean_Config.xml");
        UnitTestHelper.addConfig("ExpenseEntryManagerLocalDelegate_Config.xml");

        UnitTestHelper.addConfig("InformixExpenseStatusDAO_Config.xml");
        UnitTestHelper.addConfig("ExpenseStatusBean_Config.xml");
        UnitTestHelper.addConfig("ExpenseStatusManagerLocalDelegate_Config.xml");
        UnitTestHelper.addConfig("InformixExpenseTypeDAO_Config.xml");
        UnitTestHelper.addConfig("ExpenseTypeBean_Config.xml");
        UnitTestHelper.addConfig("ExpenseTypeManagerLocalDelegate_Config.xml");
        ConfigManager.getInstance().add("com.topcoder.naming.jndiutility", "JNDIUtils.properties",
            ConfigManager.CONFIG_PROPERTIES_FORMAT);
        connection = UnitTestHelper.getConnection();
        UnitTestHelper.prepareData(connection);

        UnitTestHelper.deployEJB();
    }

    /**
     * <p>
     * Clears the test environment. The test configuration namespace will be cleared. The test data in the table will
     * be cleared.
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

        UnitTestHelper.undeployEJB();
    }

    /**
     * <p>
     * Demostrates manipulations on expense entry statuses and persistence.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDemoStatus() throws Exception {
        // Create a manager for expense entry status from configuration
        ExpenseStatusManager manager = new ExpenseStatusManagerLocalDelegate();

        // Create an expense status with ID
        ExpenseStatus status = new ExpenseStatus(5);

        // Create an expense status without ID. The ID will be generated when adding it to the persistence.
        status = new ExpenseStatus();

        // Set fields
        status.setDescription("Description");
        status.setCreationUser("Create");
        status.setModificationUser("Create");

        // Add the expense entry status to persistence.
        boolean success = manager.addStatus(status);

        assertTrue("The instance should be added to the persistence.", success);

        // Change fields
        status.setModificationUser("Modify");
        status.setDescription("Changed");

        // Update the expense entry status to persistence
        success = manager.updateStatus(status);

        assertTrue("The instance should be updated.", success);

        // Get the expense entry status from persistence
        ExpenseStatus retrieved = manager.retrieveStatus(status.getId());

        // Get properties
        long id = retrieved.getId();
        String description = retrieved.getDescription();
        String creationUser = retrieved.getCreationUser();
        String modificationUser = retrieved.getModificationUser();
        Date creationDate = retrieved.getCreationDate();
        Date modificationDate = retrieved.getModificationDate();

        UnitTestHelper.assertEquals(status, retrieved);

        // Retrieve a list of all expense entry statuses from persistence
        ExpenseStatus[] list = manager.retrieveAllStatuses();

        // Delete one expense entry status from persistence
        success = manager.deleteStatus(status.getId());

        assertTrue("The expense entry status should be deleted.", success);

        // Delete all expense entry statuses
        manager.deleteAllStatuses();
    }

    /**
     * <p>
     * Demostrates manipulations on expense entry types and persistence.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDemoType() throws Exception {
        // Create a manager for expense entry type from configuration
        ExpenseTypeManager manager = new ExpenseTypeManagerLocalDelegate();

        // Create an expense type with ID
        ExpenseType type = new ExpenseType(4);

        // Create an expense type without ID. The ID will be generated when adding it to the persistence.
        type = new ExpenseType();

        // Set fields
        type.setDescription("Description");
        type.setCreationUser("Create");
        type.setModificationUser("Create");
        type.setCompanyId(1);

        // Add the expense entry entry to persistence.
        boolean success = manager.addType(type);

        assertTrue("The instance should be added to the persistence.", success);

        // Change fields
        type.setModificationUser("Modify");
        type.setDescription("Changed");

        // Update the expense entry type to persistence
        success = manager.updateType(type);

        assertTrue("The instance should be updated.", success);

        // Get the expense entry type from persistence
        ExpenseType retrieved = manager.retrieveType(type.getId());

        // Get properties
        long id = retrieved.getId();
        String description = retrieved.getDescription();
        String creationUser = retrieved.getCreationUser();
        String modificationUser = retrieved.getModificationUser();
        Date creationDate = retrieved.getCreationDate();
        Date modificationDate = retrieved.getModificationDate();

        UnitTestHelper.assertEquals(type, retrieved);

        // Retrieve a list of all expense entry types from persistence
        ExpenseType[] list = manager.retrieveAllTypes();

        // Delete one expense entry type from persistence
        success = manager.deleteType(type.getId());

        assertTrue("The expense entry type should be deleted.", success);

        // Delete all expense entry types
        manager.deleteAllTypes();
    }

    /**
     * <p>
     * Demostrates manipulations on expense entries and persistence.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDemoEntry() throws Exception {
        // Add one status and one type into database
        ExpenseStatusManager statusManager = new ExpenseStatusManagerLocalDelegate();
        ExpenseTypeManager typeManager = new ExpenseTypeManagerLocalDelegate();
        ExpenseStatus status = new ExpenseStatus(1);
        ExpenseType type = new ExpenseType(2);

        status.setCreationUser("Create");
        status.setDescription("Status");
        status.setModificationUser("Create");
        type.setCreationUser("Create");
        type.setDescription("Type");
        type.setModificationUser("Create");
        type.setCompanyId(1);

        statusManager.addStatus(status);
        typeManager.addType(type);

        // Create a manager for expense entry from configuration
        ExpenseEntryManager manager = new ExpenseEntryManagerLocalDelegate();

        // Create an expense entry with ID
        ExpenseEntry entry = new ExpenseEntry(5);

        // Create an expense entry without ID. The ID will be generated when adding it to the persistence.
        entry = new ExpenseEntry();
        entry.setCompanyId(1);

        // Set fields
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Create");
        entry.setAmount(new BigDecimal(100.00));
        entry.setBillable(true);
        entry.setDate(UnitTestHelper.createDate(2000, 1, 2));
        entry.setExpenseType(type);
        entry.setStatus(status);

        // Create the reject reason
        Map rejectReasons = new HashMap();
        RejectReason reason1 = new RejectReason();
        reason1.setId(1);
        reason1.setCreationDate(UnitTestHelper.createDate(2005, 1, 1));
        reason1.setModificationDate(UnitTestHelper.createDate(2005, 2, 1));
        reason1.setCreationUser("TangentZ");
        reason1.setModificationUser("Ivern");
        reason1.setDescription(description1);
        reason1.setCompanyId(1);
        rejectReasons.put(new Long(reason1.getId()), reason1);

        // add the reject reason to entry
        entry.setRejectReasons(rejectReasons);

        // Add the expense entry entry to persistence and do the audit
        boolean success = manager.addEntry(entry, true);

        assertTrue("The instance should be added to the persistence.", success);

        // for the entry, the reject reasons added above are persisted too
        ExpenseEntry[] entries = manager.retrieveAllEntries();

        for (int i = 0; i < entries.length; i++) {
            ExpenseEntry e = entries[i];
            System.out.println("is billable" + e.isBillable());

            //get the reject reasons and print them
            RejectReason[] r = (RejectReason[]) e.getRejectReasons().values().toArray(new RejectReason[0]);

            for (int j = 0; j < r.length; j++) {
                System.out.println(r[j].getId() + " " + r[j].getCreationUser() + " " + r[j].getCreationDate());
            }
        }

        // Change fields
        entry.setModificationUser("Modify");
        entry.setDescription("Changed");
        entry.setAmount(new BigDecimal(10000.00));
        entry.setBillable(false);
        entry.setDate(UnitTestHelper.createDate(2005, 2, 1));

        // delete the reject reason
        rejectReasons.remove(new Long(reason1.getId()));
        entry.setRejectReasons(rejectReasons);
        reason1.setModificationUser("me");

        // Update the expense entry to persistence and do the audit
        success = manager.updateEntry(entry, true);

        assertTrue("The instance should be updated.", success);

        // Get the expense entry from persistence. The
        ExpenseEntry retrieved = manager.retrieveEntry(entry.getId());

        // Get properties
        long id = retrieved.getId();
        String description = retrieved.getDescription();
        String creationUser = retrieved.getCreationUser();
        String modificationUser = retrieved.getModificationUser();
        Date creationDate = retrieved.getCreationDate();
        Date modificationDate = retrieved.getModificationDate();
        Date date = retrieved.getDate();
        BigDecimal amount = retrieved.getAmount();
        boolean billable = retrieved.isBillable();

        UnitTestHelper.assertEquals(entry, retrieved);

        // Retrieve a list of all expense entries from persistence
        ExpenseEntry[] list = manager.retrieveAllEntries();

        assertEquals("One expense entry should be retrieved.", 1, list.length);

        // Delete one expense entry from persistence and do the audit
        success = manager.deleteEntry(entry.getId(), true);

        assertTrue("The expense entry should be deleted.", success);

        // Delete all expense entries and do the audit
        manager.deleteAllEntries(true);

        // adding large block of entries.
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i + 1);
            expected[i].setCompanyId(1);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(UnitTestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
        }

        ExpenseEntry[] failed = manager.addEntries(expected, false, false);

        for (int i = 0; i < failed.length; i++) {
            System.out.println(failed[i].getDescription() + " adding failed");
        }

        // three entries are updated in one call, atomically (meaning if one fails, all fail
        // without any database changes)
        manager.updateEntries(new ExpenseEntry[] {expected[0], expected[1], expected[2]}, true, false);

        // three entries are updated in one call, non atomically (meaning if one fails, the
        // others are still performed independently and the failed ones are returned to
        // the user)
        failed = manager.updateEntries(new ExpenseEntry[] {expected[0], expected[1], expected[2]}, false, false);

        for (int i = 0; i < failed.length; i++) {
            System.out.println(failed[i].getDescription() + " update failed");
        }

        // three entries are retrieved in one call, atomically (meaning if one fails, all fail
        // without any results being returned)
        ExpenseEntry[] receive = manager.retrieveEntries(new long[] {1, 2, 3}, true);

        if (receive.length == 0) {
            System.out.println("retrieval failed");
        } else {
            for (int i = 0; i < receive.length; i++) {
                System.out.println(receive[i].getDescription() + " retrieved");
            }
        }

        // three entries are retrieved in one call, non atomically (meaning if one fails, the
        // others are still retrieved independently and the retrieved ones are returned to
        // the user ¨C note the difference from add/delete/update)
        receive = manager.retrieveEntries(new long[] {1, 2, 3}, false);

        for (int i = 0; i < receive.length; i++) {
            System.out.println(receive[i].getDescription() + " retrieved");
        }

        // three entries are deleted in one call, atomically (meaning if one fails, all fail
        // without any database changes)
        manager.deleteEntries(new long[] {1, 2, 3}, true, false);

        // three entries are deleted in one call, non atomically (meaning if one fails, the
        // others are still performed independently and the ids of the failed ones are
        // returned to the user)
        long[] failedIds = manager.deleteEntries(new long[] {1, 2, 3}, false, false);

        for (int i = 0; i < failedIds.length; i++) {
            System.out.println(failedIds[i] + " deletion failed");
        }
    }

    /**
     * <p>
     * Demostrates manipulations on search framework.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDemoSearch() throws Exception {
        // Add one status and one type into database
        ExpenseStatusManager statusManager = new ExpenseStatusManagerLocalDelegate();
        ExpenseTypeManager typeManager = new ExpenseTypeManagerLocalDelegate();
        ExpenseStatus status = new ExpenseStatus(1);
        ExpenseType type = new ExpenseType(2);

        status.setCreationUser("Create");
        status.setDescription("Status");
        status.setModificationUser("Create");
        type.setCreationUser("Create");
        type.setDescription("Type");
        type.setModificationUser("Create");
        type.setCompanyId(1);

        statusManager.addStatus(status);
        typeManager.addType(type);

        // Create the reject reason
        Map rejectReasons = new HashMap();
        RejectReason reason1 = new RejectReason();
        reason1.setId(1);
        reason1.setCreationDate(UnitTestHelper.createDate(2005, 1, 1));
        reason1.setModificationDate(UnitTestHelper.createDate(2005, 2, 1));
        reason1.setCreationUser("TangentZ");
        reason1.setModificationUser("Ivern");
        reason1.setDescription(description1);
        reason1.setCompanyId(1);
        rejectReasons.put(new Long(reason1.getId()), reason1);

        // Create a manager for expense entry from configuration
        ExpenseEntryManager manager = new ExpenseEntryManagerLocalDelegate();

        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i + 1);
            expected[i].setCompanyId(1);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(UnitTestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
            expected[i].setRejectReasons(rejectReasons);
            manager.addEntry(expected[i], false);
        }

        // the SEARCH framework allows expense entries being searched based
        // on different criteria
        // look for description containing a given string
        Criteria crit1 = FieldLikeCriteria.getDescriptionContainsCriteria("gambling debt");

        // look for expense status, expense type, billable flag, creation and modification users
        // matching a given value
        Criteria crit2 = new FieldMatchCriteria("expense_entry.expense_entry_id", new Integer(2));
        Criteria crit3 = new FieldMatchCriteria("expense_entry.expense_type_id", new Integer(23));
        Criteria crit4 = FieldMatchCriteria.getBillableMatchCriteria(true);
        Criteria crit5 = FieldMatchCriteria.getCreationUserMatchCriteria("me");
        Criteria crit6 = FieldMatchCriteria.getModificationUserMatchCriteria("boss");

        // look for amount between two given value
        // the null calls means open ended (the first means amount >= 1000, the second amount <=2000)
        Criteria crit7 = FieldBetweenCriteria.getAmountBetweenCriteria(BigDecimal.valueOf(1000),
                BigDecimal.valueOf(2000));
        Criteria crit8 = FieldBetweenCriteria.getAmountBetweenCriteria(BigDecimal.valueOf(1000), null);
        Criteria crit9 = FieldBetweenCriteria.getAmountBetweenCriteria(null, BigDecimal.valueOf(2000));

        // look for creation and modification dates between two given dates
        // the null calls mean open ended (the first means 2005/30/1 or later, the second today or before)
        Criteria crit10 = FieldBetweenCriteria.getCreationDateBetweenCriteria(new Date(), new Date());
        Criteria crit11 = FieldBetweenCriteria.getCreationDateBetweenCriteria(new Date(), null);
        Criteria crit12 = FieldBetweenCriteria.getCreationDateBetweenCriteria(null, new Date());
        Criteria crit13 = FieldBetweenCriteria.getModificationDateBetweenCriteria(new Date(), new Date());
        Criteria crit14 = FieldBetweenCriteria.getModificationDateBetweenCriteria(new Date(), null);
        Criteria crit15 = FieldBetweenCriteria.getModificationDateBetweenCriteria(null, new Date());

        // look for an expense entry having a given reject reason
        Criteria crit16 = new RejectReasonCriteria(50);
        Criteria crit16b = new RejectReasonCriteria(51);
        Criteria crit16c = new RejectReasonCriteria(52);

        // look for entries not matching a given criteria
        Criteria crit17 = new NotCriteria(crit10);

        // look for entries matching two criteria at the same time
        Criteria crit18 = CompositeCriteria.getAndCompositeCriteria(crit2, crit6);

        // look for entries matching any of two criteria
        // in this particular case it looks for an entry having reject reason 51 OR 52
        Criteria crit19 = CompositeCriteria.getOrCompositeCriteria(crit16b, crit16c);

        // look for entries matching more criteria at the same time
        // in this particular case it looks for an entry having reject reason 50, 51 AND 52
        Criteria crit20 = CompositeCriteria.getAndCompositeCriteria(new Criteria[] {crit16, crit16b, crit16c});

        // look for entries matching any of more criteria
        Criteria criteria = CompositeCriteria.getOrCompositeCriteria(new Criteria[] {crit2, crit6, crit12});

        // the actual search and result printing
        ExpenseEntry[] entries = manager.searchEntries(criteria);

        for (int i = 0; i < entries.length; i++) {
            System.out.println(entries[i].getDescription() + " match found");
        }
    }
}
