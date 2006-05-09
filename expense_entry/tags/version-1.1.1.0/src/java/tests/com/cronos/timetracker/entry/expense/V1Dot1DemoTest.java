/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense;

import com.cronos.timetracker.entry.expense.persistence.ExpenseEntryPersistence;
import com.cronos.timetracker.entry.expense.persistence.ExpenseEntryStatusPersistence;
import com.cronos.timetracker.entry.expense.persistence.ExpenseEntryTypePersistence;
import com.cronos.timetracker.entry.expense.search.CompositeCriteria;
import com.cronos.timetracker.entry.expense.search.Criteria;
import com.cronos.timetracker.entry.expense.search.FieldBetweenCriteria;
import com.cronos.timetracker.entry.expense.search.FieldLikeCriteria;
import com.cronos.timetracker.entry.expense.search.FieldMatchCriteria;
import com.cronos.timetracker.entry.expense.search.NotCriteria;
import com.cronos.timetracker.entry.expense.search.RejectReasonCriteria;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import junit.framework.TestCase;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.util.Date;
import java.util.List;


/**
 * <p>
 * Demostrates the usage of this component. The persistence classes should not be used directly by user, and thus are
 * not demostrated. Usage of V1.0 is included in this demo.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class V1Dot1DemoTest extends TestCase {
    /** Represents the namespace to load manager configuration. */
    private static final String NAMESPACE = "com.cronos.timetracker.entry.expense";

    /** Represents the namespace to load DB connection factory configuration. */
    private static final String DB_NAMESPACE = "com.cronos.timetracker.entry.expense.connection";

    /** The description of reject reason. */
    private final String description1 = "description1";

    /** The description of reject reason. */
    private final String description2 = "description2";

    /** Represents the database connection to access database. */
    private Connection connection;

    /** Represents a DB connection factory to create connections. */
    private DBConnectionFactory factory;

    /**
     * <p>
     * Sets up the test environment. Valid configurations are loaded. Data tables are truncated.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        V1Dot1TestHelper.clearConfiguration();
        V1Dot1TestHelper.addValidConfiguration();
        factory = new DBConnectionFactoryImpl(DB_NAMESPACE);
        connection = factory.createConnection();
        V1Dot1TestHelper.clearDatabase(connection);

        // Insert an expense type
        PreparedStatement ps = connection.prepareStatement("INSERT INTO ExpenseTypes(ExpenseTypesID, Description, " +
                "CreationUser, CreationDate, ModificationUser, ModificationDate) VALUES (?,?,?,?,?,?)");

        try {
            ps.setInt(1, 1);
            ps.setString(2, "Travel Expense");
            ps.setString(3, "TangentZ");
            ps.setDate(4, new java.sql.Date(V1Dot1TestHelper.createDate(2005, 1, 1).getTime()));
            ps.setString(5, "Ivern");
            ps.setDate(6, new java.sql.Date(V1Dot1TestHelper.createDate(2005, 2, 1).getTime()));
            ps.executeUpdate();
        } finally {
            ps.close();
        }

        // Insert an expense status
        ps = connection.prepareStatement("INSERT INTO ExpenseStatuses(ExpenseStatusesID, Description, CreationUser, " +
                "CreationDate, ModificationUser, ModificationDate) VALUES (?,?,?,?,?,?)");

        try {
            ps.setInt(1, 2);
            ps.setString(2, "Pending Approval");
            ps.setString(3, "TangentZ");
            ps.setDate(4, new java.sql.Date(V1Dot1TestHelper.createDate(2005, 1, 1).getTime()));
            ps.setString(5, "Ivern");
            ps.setDate(6, new java.sql.Date(V1Dot1TestHelper.createDate(2005, 2, 1).getTime()));
            ps.executeUpdate();
        } finally {
            ps.close();
        }

        // Insert an reject reason
        ps = connection.prepareStatement("INSERT INTO reject_reason(reject_reason_id, description, creation_date, " +
                "creation_user, modification_date, modification_user) VALUES (?,?,?,?,?,?)");

        try {
            ps.setInt(1, 1);
            ps.setString(2, description1);
            ps.setDate(3, new java.sql.Date(V1Dot1TestHelper.createDate(2005, 1, 1).getTime()));
            ps.setString(4, "TangentZ");
            ps.setDate(5, new java.sql.Date(V1Dot1TestHelper.createDate(2005, 2, 1).getTime()));
            ps.setString(6, "Ivern");
            ps.executeUpdate();

            ps.setInt(1, 3);
            ps.setString(2, description2);
            ps.setDate(3, new java.sql.Date(V1Dot1TestHelper.createDate(2005, 1, 1).getTime()));
            ps.setString(4, "TangentZ");
            ps.setDate(5, new java.sql.Date(V1Dot1TestHelper.createDate(2005, 2, 1).getTime()));
            ps.setString(6, "Ivern");
            ps.executeUpdate();
        } finally {
            ps.close();
        }
    }

    /**
     * <p>
     * Cleans up the test environment. The configurations are removed. Data tables are truncated.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        V1Dot1TestHelper.clearConfiguration();
        V1Dot1TestHelper.clearDatabase(connection);

        connection.close();
        factory = null;
        connection = null;
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
        ExpenseEntryStatusManager manager = new ExpenseEntryStatusManager(NAMESPACE);

        // Get the underlying persistence
        ExpenseEntryStatusPersistence persistence = manager.getStatusPersistence();

        // Set a database connection explicitly.
        persistence.setConnection(factory.createConnection());

        // Initialize a database connection from DB connection factory.
        persistence.initConnection("Connection");

        // Get the connection
        Connection conn = persistence.getConnection();

        // Create an expense status with ID
        ExpenseEntryStatus status = new ExpenseEntryStatus(5);

        // Create an expense status without ID. The ID will be generated when adding it to the persistence.
        status = new ExpenseEntryStatus();

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
        ExpenseEntryStatus retrieved = manager.retrieveStatus(status.getId());

        // Get properties
        int id = retrieved.getId();
        String description = retrieved.getDescription();
        String creationUser = retrieved.getCreationUser();
        String modificationUser = retrieved.getModificationUser();
        Date creationDate = retrieved.getCreationDate();
        Date modificationDate = retrieved.getModificationDate();

        V1Dot1TestHelper.assertEquals(status, retrieved);

        // Retrieve a list of all expense entry statuses from persistence
        List list = manager.retrieveAllStatuses();

        // Delete one expense entry status from persistence
        success = manager.deleteStatus(status.getId());

        assertTrue("The expense entry status should be deleted.", success);

        // Delete all expense entry statuses
        manager.deleteAllStatuses();

        // Close the connection in persistence
        persistence.closeConnection();
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
        ExpenseEntryTypeManager manager = new ExpenseEntryTypeManager(NAMESPACE);

        // Get the underlying persistence
        ExpenseEntryTypePersistence persistence = manager.getTypePersistence();

        // Set a database connection explicitly.
        persistence.setConnection(factory.createConnection());

        // Initialize a database connection from DB connection factory.
        persistence.initConnection("Connection");

        // Get the connection
        Connection conn = persistence.getConnection();

        // Create an expense type with ID
        ExpenseEntryType type = new ExpenseEntryType(4);

        // Create an expense type without ID. The ID will be generated when adding it to the persistence.
        type = new ExpenseEntryType();

        // Set fields
        type.setDescription("Description");
        type.setCreationUser("Create");
        type.setModificationUser("Create");

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
        ExpenseEntryType retrieved = manager.retrieveType(type.getId());

        // Get properties
        int id = retrieved.getId();
        String description = retrieved.getDescription();
        String creationUser = retrieved.getCreationUser();
        String modificationUser = retrieved.getModificationUser();
        Date creationDate = retrieved.getCreationDate();
        Date modificationDate = retrieved.getModificationDate();

        V1Dot1TestHelper.assertEquals(type, retrieved);

        // Retrieve a list of all expense entry types from persistence
        List list = manager.retrieveAllTypes();

        // Delete one expense entry type from persistence
        success = manager.deleteType(type.getId());

        assertTrue("The expense entry type should be deleted.", success);

        // Delete all expense entry types
        manager.deleteAllTypes();

        // Close the connection in persistence
        persistence.closeConnection();
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
        ExpenseEntryStatusManager statusManager = new ExpenseEntryStatusManager(NAMESPACE);
        ExpenseEntryTypeManager typeManager = new ExpenseEntryTypeManager(NAMESPACE);
        ExpenseEntryStatus status = new ExpenseEntryStatus(1);
        ExpenseEntryType type = new ExpenseEntryType(2);

        status.setCreationUser("Create");
        status.setDescription("Status");
        status.setModificationUser("Create");
        type.setCreationUser("Create");
        type.setDescription("Type");
        type.setModificationUser("Create");

        statusManager.addStatus(status);
        typeManager.addType(type);

        // Close the connection for status manager and type manager
        statusManager.getStatusPersistence().closeConnection();
        typeManager.getTypePersistence().closeConnection();

        // Create a manager for expense entry from configuration
        ExpenseEntryManager manager = new ExpenseEntryManager(NAMESPACE);

        // Get the underlying persistence
        ExpenseEntryPersistence persistence = manager.getEntryPersistence();

        // Set a database connection explicitly.
        persistence.setConnection(factory.createConnection());

        // Initialize a database connection from DB connection factory.
        persistence.initConnection("Connection");

        // Get the connection
        Connection conn = persistence.getConnection();

        // Create an expense entry with ID
        ExpenseEntry entry = new ExpenseEntry(5);

        // Create an expense entry without ID. The ID will be generated when adding it to the persistence.
        entry = new ExpenseEntry();

        // Set fields
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Create");
        entry.setAmount(new BigDecimal(100.00));
        entry.setBillable(true);
        entry.setDate(V1Dot1TestHelper.createDate(2000, 1, 2));
        entry.setExpenseType(type);
        entry.setStatus(status);

        // Create the reject reason
        ExpenseEntryRejectReason reason1 = new ExpenseEntryRejectReason(1);
        reason1.setCreationDate(V1Dot1TestHelper.createDate(2005, 1, 1));
        reason1.setModificationDate(V1Dot1TestHelper.createDate(2005, 2, 1));
        reason1.setCreationUser("TangentZ");
        reason1.setModificationUser("Ivern");
        reason1.setDescription(description1);

        ExpenseEntryRejectReason reason2 = new ExpenseEntryRejectReason(3);
        reason2.setCreationDate(V1Dot1TestHelper.createDate(2005, 1, 1));
        reason2.setModificationDate(V1Dot1TestHelper.createDate(2005, 2, 1));
        reason2.setCreationUser("TangentZ");
        reason2.setModificationUser("Ivern");
        reason2.setDescription(description2);

        // add the reject reason to entry
        entry.addRejectReason(reason1);
        entry.addRejectReason(reason2);

        // Add the expense entry entry to persistence.
        boolean success = manager.addEntry(entry);

        assertTrue("The instance should be added to the persistence.", success);

        // for the entry, the reject reasons added above are persisted too
        List entries = manager.retrieveAllEntries();

        for (int i = 0; i < entries.size(); i++) {
            ExpenseEntry e = (ExpenseEntry) entries.get(i);
            System.out.println("is billable" + e.isBillable());

            //get the reject reasons and print them
            ExpenseEntryRejectReason[] r = e.getRejectReasons();

            for (int j = 0; j < r.length; j++) {
                System.out.println(r[j].getRejectReasonId() + " " + r[j].getCreationUser() + " " +
                    r[j].getCreationDate());
            }

            //get the reject reason ids directly and print them
            int[] rids = e.getRejectReasonIds();

            for (int j = 0; j < rids.length; j++) {
                System.out.println(rids[j]);
            }
        }

        // Change fields
        entry.setModificationUser("Modify");
        entry.setDescription("Changed");
        entry.setAmount(new BigDecimal(10000.00));
        entry.setBillable(false);
        entry.setDate(V1Dot1TestHelper.createDate(2005, 2, 1));

        // delete the reject reason
        entry.deleteRejectReason(reason1.getRejectReasonId());
        reason2.setModificationUser("me");

        // Update the expense entry to persistence
        success = manager.updateEntry(entry);

        assertTrue("The instance should be updated.", success);

        // Get the expense entry from persistence. The
        ExpenseEntry retrieved = manager.retrieveEntry(entry.getId());

        // Get properties
        int id = retrieved.getId();
        String description = retrieved.getDescription();
        String creationUser = retrieved.getCreationUser();
        String modificationUser = retrieved.getModificationUser();
        Date creationDate = retrieved.getCreationDate();
        Date modificationDate = retrieved.getModificationDate();
        Date date = retrieved.getDate();
        BigDecimal amount = retrieved.getAmount();
        boolean billable = retrieved.isBillable();

        V1Dot1TestHelper.assertEquals(entry, retrieved);

        // Retrieve a list of all expense entries from persistence
        List list = manager.retrieveAllEntries();

        assertEquals("One expense entry should be retrieved.", 1, list.size());

        // Delete one expense entry from persistence
        success = manager.deleteEntry(entry.getId());

        assertTrue("The expense entry should be deleted.", success);

        // Delete all expense entries
        manager.deleteAllEntries();

        // adding large block of entries.
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
        }

        ExpenseEntry[] failed = manager.addEntries(expected, false);

        for (int i = 0; i < failed.length; i++) {
            System.out.println(failed[i].getDescription() + " adding failed");
        }

        // three entries are updated in one call, atomically (meaning if one fails, all fail
        // without any database changes)
        manager.updateEntries(new ExpenseEntry[] { expected[0], expected[1], expected[2] }, true);

        // three entries are updated in one call, non atomically (meaning if one fails, the
        // others are still performed independently and the failed ones are returned to
        // the user)
        failed = manager.updateEntries(new ExpenseEntry[] { expected[0], expected[1], expected[2] }, false);

        for (int i = 0; i < failed.length; i++) {
            System.out.println(failed[i].getDescription() + " update failed");
        }

        // three entries are retrieved in one call, atomically (meaning if one fails, all fail
        // without any results being returned)
        ExpenseEntry[] receive = manager.retrieveEntries(new int[] { 0, 1, 2 }, true);

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
        receive = manager.retrieveEntries(new int[] { 0, 1, 2 }, false);

        for (int i = 0; i < receive.length; i++) {
            System.out.println(receive[i].getDescription() + " retrieved");
        }

        // three entries are deleted in one call, atomically (meaning if one fails, all fail
        // without any database changes)
        manager.deleteEntries(new int[] { 0, 1, 2 }, true);

        // three entries are deleted in one call, non atomically (meaning if one fails, the
        // others are still performed independently and the ids of the failed ones are
        // returned to the user)
        int[] failedIds = manager.deleteEntries(new int[] { 0, 1, 2 }, false);

        for (int i = 0; i < failedIds.length; i++) {
            System.out.println(failedIds[i] + " deletion failed");
        }

        // Close the connection in persistence
        persistence.closeConnection();
    }

    /**
     * <p>
     * Demostrates manipulations on search framework.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDemoSearch() throws Exception {
        // Create a manager for expense entry from configuration
        ExpenseEntryManager manager = new ExpenseEntryManager(NAMESPACE);

        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Create the expense status
        ExpenseEntryStatus status = new ExpenseEntryStatus(2);

        status.setDescription("Pending Approval");
        status.setCreationDate(V1Dot1TestHelper.createDate(2005, 1, 1));
        status.setModificationDate(V1Dot1TestHelper.createDate(2005, 2, 1));
        status.setCreationUser("TangentZ");
        status.setModificationUser("Ivern");

        // Create the expense type
        ExpenseEntryType type = new ExpenseEntryType(1);

        type.setDescription("Travel Expense");
        type.setCreationDate(V1Dot1TestHelper.createDate(2005, 1, 1));
        type.setModificationDate(V1Dot1TestHelper.createDate(2005, 2, 1));
        type.setCreationUser("TangentZ");
        type.setModificationUser("Ivern");

        // Create the reject reason
        ExpenseEntryRejectReason reason1 = new ExpenseEntryRejectReason(1);
        reason1.setCreationDate(V1Dot1TestHelper.createDate(2005, 1, 1));
        reason1.setModificationDate(V1Dot1TestHelper.createDate(2005, 2, 1));
        reason1.setCreationUser("TangentZ");
        reason1.setModificationUser("Ivern");

        ExpenseEntryRejectReason reason2 = new ExpenseEntryRejectReason(3);
        reason2.setCreationDate(V1Dot1TestHelper.createDate(2005, 1, 1));
        reason2.setModificationDate(V1Dot1TestHelper.createDate(2005, 2, 1));
        reason2.setCreationUser("TangentZ");
        reason2.setModificationUser("Ivern");

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = new ExpenseEntry(i);
            expected[i].setCreationUser("Create" + i);
            expected[i].setModificationUser("Modify" + i);
            expected[i].setDescription("Description" + i);
            expected[i].setAmount(new BigDecimal(i));
            expected[i].setBillable(true);
            expected[i].setDate(V1Dot1TestHelper.createDate(2005, 2, i));
            expected[i].setExpenseType(type);
            expected[i].setStatus(status);
            expected[i].addRejectReason(reason1);
            expected[i].addRejectReason(reason2);
            manager.addEntry(expected[i]);
        }

        // the SEARCH framework allows expense entries being searched based
        // on different criteria
        // look for description containing a given string
        Criteria crit1 = FieldLikeCriteria.getDescriptionContainsCriteria("gambling debt");

        // look for expense status, expense type, billable flag, creation and modification users
        // matching a given value
        Criteria crit2 = new FieldMatchCriteria("ExpenseEntries.ExpenseStatusesID", new Integer(2));
        Criteria crit3 = new FieldMatchCriteria("ExpenseEntries.ExpenseTypesID", new Integer(23));
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
        Criteria crit20 = CompositeCriteria.getAndCompositeCriteria(new Criteria[] { crit16, crit16b, crit16c });

        // look for entries matching any of more criteria
        Criteria criteria = CompositeCriteria.getOrCompositeCriteria(new Criteria[] { crit2, crit6, crit12 });

        // the actual search and result printing
        ExpenseEntry[] entries = manager.searchEntries(criteria);

        for (int i = 0; i < entries.length; i++) {
            System.out.println(entries[i].getDescription() + " match found");
        }
    }
}
