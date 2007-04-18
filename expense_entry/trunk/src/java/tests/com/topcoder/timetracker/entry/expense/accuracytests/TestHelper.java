/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.accuracytests;

import com.mockrunner.mock.ejb.MockUserTransaction;
import com.topcoder.timetracker.common.TimeTrackerBean;
import com.topcoder.timetracker.entry.base.BaseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseStatus;
import com.topcoder.timetracker.entry.expense.ExpenseType;
import com.topcoder.timetracker.entry.expense.ejb.BasicExpenseEntryBean;
import com.topcoder.timetracker.entry.expense.ejb.BasicExpenseEntryLocal;
import com.topcoder.timetracker.entry.expense.ejb.BasicExpenseEntryLocalHome;
import com.topcoder.timetracker.entry.expense.ejb.ExpenseEntryBean;
import com.topcoder.timetracker.entry.expense.ejb.ExpenseEntryLocal;
import com.topcoder.timetracker.entry.expense.ejb.ExpenseEntryLocalHome;
import com.topcoder.timetracker.entry.expense.ejb.ExpenseStatusBean;
import com.topcoder.timetracker.entry.expense.ejb.ExpenseStatusLocal;
import com.topcoder.timetracker.entry.expense.ejb.ExpenseStatusLocalHome;
import com.topcoder.timetracker.entry.expense.ejb.ExpenseTypeBean;
import com.topcoder.timetracker.entry.expense.ejb.ExpenseTypeLocal;
import com.topcoder.timetracker.entry.expense.ejb.ExpenseTypeLocalHome;
import com.topcoder.timetracker.rejectreason.RejectReason;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import junit.framework.Assert;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

/**
 * <p>
 * Defines helper methods used in tests.
 * </p>
 *
 * @author visualage
 * @author kr00tki
 * @version 2.0
 *
 * @since 1.0
 */
public final class TestHelper {
    /**
     * The test id of the company.
     */
    public static final int COMPANY_ID = 10;

    /** Represents the namespace to load DB connection factory configuration. */
    private static final String DB_NAMESPACE = ConfigHelper.DB_CONNECTION_FACTORY_NAMESPACE;

    /** Represents whether the mock ejb to be deployed. */
    private static boolean deployed = false;

    /**
     * <p>
     * Creates a new instance of <code>TestHelper</code> class. The private constructor prevents the creation of
     * a new instance.
     * </p>
     */
    private TestHelper() {
    }

    /**
     * <p>
     * Sorts a list of data objects according to their IDs. Smallest ID comes first.
     * </p>
     *
     * @param list the list of data objects to be sorted.
     */
    public static void sortDataObjects(List list) {
        for (int i = 0; i < list.size(); ++i) {
            for (int j = 0; j < i; ++j) {
                if (((BaseEntry) list.get(i)).getId() < ((BaseEntry) list.get(j)).getId()) {
                    Object obj = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, obj);
                }
            }
        }
    }

    /**
     * <p>
     * Asserts that two given common information should be the same. They are considered the same if and only if
     * their fields are the same.
     * </p>
     *
     * @param expected the expected common information.
     * @param actual the actual common information.
     */
    public static void assertEquals(TimeTrackerBean expected, TimeTrackerBean actual) {
        Assert.assertEquals("The ID should be correct.", expected.getId(), actual.getId());
        if (expected instanceof BaseEntry && actual instanceof BaseEntry) {
            Assert.assertEquals("The description should be correct.", ((BaseEntry) expected).getDescription(), ((BaseEntry) actual)
                    .getDescription());
        }
        Assert.assertEquals("The creation user should be correct.", expected.getCreationUser(), actual
                .getCreationUser());
        Assert.assertEquals("The modification user should be correct.", expected.getModificationUser(), actual
                .getModificationUser());
    }

    /**
     * <p>
     * Asserts that two given expense entries should be the same. They are considered the same if and only if their
     * fields are the same.
     * </p>
     *
     * @param expected the expected expense entry.
     * @param actual the actual expense entry.
     */
    public static void assertEquals(ExpenseEntry expected, ExpenseEntry actual) {
        assertEquals((BaseEntry) expected, (BaseEntry) actual);

        assertEquals(expected.getExpenseType(), actual.getExpenseType());
        assertEquals(expected.getStatus(), actual.getStatus());
        Assert.assertEquals("The billable flag should be correct.", expected.isBillable(), actual.isBillable());
        Assert.assertEquals("The amount of money should be correct.", expected.getAmount().doubleValue(), actual
                .getAmount().doubleValue(), 1E-9);
        Assert.assertEquals("Incorrect company id.", expected.getCompanyId(), actual.getCompanyId());
    }

    /**
     * <p>
     * Asserts that two given dates should be the same. They are considered the same if and only if the year,
     * month, date, hours, minutes and seconds are the same.
     * </p>
     *
     * @param message the message to be given if two given dates are not the same.
     * @param expected the expected date.
     * @param actual the actual date.
     */
    public static void assertEquals(String message, Date expected, Date actual) {
        Calendar expectedCalendar = Calendar.getInstance();
        Calendar actualCalendar = Calendar.getInstance();

        expectedCalendar.setTime(expected);
        actualCalendar.setTime(actual);

        Assert.assertEquals(message, expectedCalendar.get(Calendar.YEAR), actualCalendar.get(Calendar.YEAR));
        Assert.assertEquals(message, expectedCalendar.get(Calendar.MONTH), actualCalendar.get(Calendar.MONTH));
        Assert.assertEquals(message, expectedCalendar.get(Calendar.DAY_OF_MONTH), actualCalendar
                .get(Calendar.DAY_OF_MONTH));
    }

    /**
     * <p>
     * Creates a <code>Date</code> instance representing the given year, month and date. The time would be 0:0:0.
     * </p>
     *
     * @param year the year in the instance.
     * @param month the month in the instance.
     * @param date the date in the instance.
     *
     * @return a <code>Date</code> instance representing the year, month and date.
     */
    public static Date createDate(int year, int month, int date) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(year, month - 1, date, 0, 0, 0);

        return calendar.getTime();
    }

    /**
     * <p>
     * Clears the content in database.
     * </p>
     *
     * @param connection the database connection used to access database.
     *
     * @throws Exception if database error occurs.
     */
    public static void clearDatabase() throws Exception {
    	clearDatabase(getConnection());
    }

    /**
     * <p>
     * Clears the content in database.
     * </p>
     *
     * @param connection the database connection used to access database.
     *
     * @throws SQLException if database error occurs.
     */
    private static void clearDatabase(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        try {
            statement.addBatch("DELETE FROM comp_exp_type;");
            statement.addBatch("DELETE FROM comp_rej_reason;");
            statement.addBatch("DELETE FROM exp_reject_reason;");
            statement.addBatch("DELETE FROM expense_entry;");
            statement.addBatch("DELETE FROM expense_status;");
            statement.addBatch("DELETE FROM expense_type;");
            statement.addBatch("DELETE FROM reject_reason;");
            statement.addBatch("DELETE FROM company;");
            statement.executeBatch();
        } finally {
            statement.close();
        }
    }

    /**
     * Initialize the database for testing. It adds some data to database.
     *
     * @throws Exception to JUnit
     */
    public static void initDatabase() throws Exception {
        Connection connection = getConnection();

        clearDatabase(connection);

        try {
            Statement stmt = connection.createStatement();
            stmt.addBatch("insert into company values(10, 'a', 'a', current, 'a', current, 'a')");
            stmt.addBatch("insert into company values(20, 'a', 'a1', current, 'a', current, 'a')");
            stmt.addBatch("insert into company values(30, 'a', 'a2', current, 'a', current, 'a')");
            stmt.executeBatch();

            // Insert an expense type
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO expense_type(expense_type_id, Description, "
                            + "active, creation_user, creation_date, modification_user, modification_date) VALUES (?,?,?,?,?,?,?)");

            try {
                ps.setInt(1, 1);
                ps.setString(2, "Travel Expense");
                ps.setInt(3, 1);
                ps.setString(4, "TangentZ");
                ps.setDate(5, new java.sql.Date(TestHelper.createDate(2005, 1, 1).getTime()));
                ps.setString(6, "Ivern");
                ps.setDate(7, new java.sql.Date(TestHelper.createDate(2005, 2, 1).getTime()));
                ps.executeUpdate();
            } finally {
                ps.close();
            }
            // add company - expense type mapping
            ps = connection.prepareStatement("INSERT INTO comp_exp_type(company_id, expense_type_id, "
                    + "creation_date, creation_user, modification_date, modification_user) "
                    + "VALUES (?,?,?,?,?,?)");

            try {
                ps.setInt(1, COMPANY_ID);
                ps.setInt(2, 1);
                ps.setDate(3, new java.sql.Date(TestHelper.createDate(2005, 1, 1).getTime()));
                ps.setString(4, "TangentZ");
                ps.setDate(5, new java.sql.Date(TestHelper.createDate(2005, 2, 1).getTime()));
                ps.setString(6, "Ivern");

                ps.executeUpdate();
            } finally {
                ps.close();
            }
            // Insert an expense status
            ps = connection
                    .prepareStatement("INSERT INTO expense_status(expense_status_id, Description, creation_user, "
                            + "creation_date, modification_user, modification_date) VALUES (?,?,?,?,?,?)");

            try {
                ps.setInt(1, 2);
                ps.setString(2, "Pending Approval");
                ps.setString(3, "TangentZ");
                ps.setDate(4, new java.sql.Date(TestHelper.createDate(2005, 1, 1).getTime()));
                ps.setString(5, "Ivern");
                ps.setDate(6, new java.sql.Date(TestHelper.createDate(2005, 2, 1).getTime()));
                ps.executeUpdate();
            } finally {
                ps.close();
            }
            // Insert three reject reasons whose id is 1, 2, 3.
            ps = connection.prepareStatement("INSERT INTO reject_reason(reject_reason_id, Description, "
                    + "creation_date, creation_user, modification_date, modification_user, active) "
                    + "VALUES (?,?,?,?,?,?,?)");

            try {
                /*ps.setString(2, "reject reason1");
                ps.setDate(3, new java.sql.Date(TestHelper.createDate(2005, 1, 1).getTime()));
                ps.setString(4, "Creator");
                ps.setDate(5, new java.sql.Date(TestHelper.createDate(2005, 1, 1).getTime()));
                ps.setString(6, "Modifier");
                ps.setInt(7, 1);*/

                for (int i = 1; i <= 3; i++) {
                    ps.setInt(1, i);
                    ps.setString(2, "reject reason1");
                    ps.setDate(3, new java.sql.Date(TestHelper.createDate(2005, 1, 1).getTime()));
                    ps.setString(4, "Creator");
                    ps.setDate(5, new java.sql.Date(TestHelper.createDate(2005, 1, 1).getTime()));
                    ps.setString(6, "Modifier");
                    ps.setInt(7, 1);
                    ps.addBatch();
                }
                ps.executeBatch();
            } finally {
                ps.close();
            }
 
            // add company - reject reason mapping
            ps = connection.prepareStatement("INSERT INTO comp_rej_reason(company_id, reject_reason_id,"
                    + "creation_date, creation_user, modification_date, modification_user) "
                    + "VALUES (?, ?, current, 'system', current, 'system')");
            try {
                for (int i = 1; i <= 3; i++) {
                    ps.setInt(1, COMPANY_ID);
                    ps.setInt(2, i);
                    ps.addBatch();
                }
                ps.executeBatch();
            } finally {
                ps.close();
            }
        } finally {
            connection.close();
        }
    }

    /**
     * Create an ExpenseStatus instance according data in database which has been initialized in
     * initDatabase() method.
     *
     * @return an ExpenseStatus instance
     */
    public static ExpenseStatus createStatusInstance() {
        // Create the expense status
        ExpenseStatus status = new ExpenseStatus(2);

        status.setDescription("Pending Approval");
        status.setCreationDate(TestHelper.createDate(2005, 1, 1));
        status.setModificationDate(TestHelper.createDate(2005, 2, 1));
        status.setCreationUser("TangentZ");
        status.setModificationUser("Ivern");

        return status;
    }

    /**
     * Create an ExpenseType instance according data in database which has been initialized in initDatabase()
     * method.
     *
     * @return an ExpenseType instance
     */
    public static ExpenseType createTypeInstance() {
        // Create the expense type
        ExpenseType type = new ExpenseType(1);

        type.setDescription("Travel Expense");
        type.setCreationDate(TestHelper.createDate(2005, 1, 1));
        type.setModificationDate(TestHelper.createDate(2005, 2, 1));
        type.setCreationUser("TangentZ");
        type.setModificationUser("Ivern");
        // since 2.0
        type.setCompanyId(COMPANY_ID);

        return type;
    }

    /**
     * Create a connection and return it.
     *
     * @return a connection
     *
     * @throws Exception to JUnit
     */
    public static Connection getConnection() throws Exception {
        return new DBConnectionFactoryImpl(DB_NAMESPACE).createConnection();
    }

    /**
     * Compares the two array of ExpenseEntryRejectReason instances to judge whether they consist of the same
     * elements disregarding the order of their appearing.
     *
     * @param array1 the first array to compare
     * @param array2 the second array to compare
     *
     * @return true if they consist of the same elements disregarding the order of their appearing.
     */
    public static boolean compareRejectReasonsArray(RejectReason[] array1,
            RejectReason[] array2) {
        RejectReason[] array1Copy = (RejectReason[]) array1.clone();
        RejectReason[] array2Copy = (RejectReason[]) array2.clone();
        sortRejectReasons(array1Copy);
        sortRejectReasons(array2Copy);

        return Arrays.equals(array1Copy, array2Copy);
    }

    /**
     * Sort rejectReasons array into ascending reject reason id numerical order.
     *
     * @param array1 the RejectReason array to sort
     */
    private static void sortRejectReasons(RejectReason[] array1) {
        Arrays.sort(array1, new Comparator() {
            /**
             * Compare the id of the two objects instance of RejectReason.
             *
             * @param obj1 the object instance of RejectReason to compare
             * @param obj2 the object instance of RejectReason to compare
             *
             * @return 1 if the id of obj1 is bigger than the one of obj2; -1 if smaller; 0 if equals.
             */
            public int compare(Object obj1, Object obj2) {
                long id1 = ((RejectReason) obj1).getId();
                long id2 = ((RejectReason) obj2).getId();

                return (id1 < id2) ? (-1) : ((id1 > id2) ? 1 : 0);
            }
        });
    }

    /**
     * Compares the two array of int instances to judge whether they consist of the same int value disregarding the
     * order of their appearing.
     *
     * @param array1 the first array to compare
     * @param array2 the second array to compare
     *
     * @return true if they consist of the same int value disregarding the order of their appearing.
     */
    public static boolean compareIntArray(int[] array1, int[] array2) {
        int[] array1Copy = (int[]) array1.clone();
        int[] array2Copy = (int[]) array2.clone();
        Arrays.sort(array1Copy);
        Arrays.sort(array2Copy);

        return Arrays.equals(array1Copy, array2Copy);
    }

    /**
     * <p>
     * Deploys the mock ejb for testing purpose.
     * </p>
     *
     * @throws Exception exception to junit.
     */
    public static void deployEJB() throws Exception {
        if (!deployed) {
            /*
             * Deploy EJBs to the MockContainer if we run outside of the app server In cactus mode all but one
             * EJB are deployed by the app server, so we don't need to do it.
             */
            MockContextFactory.setAsInitial();

            // Create an instance of the MockContainer and pass the JNDI context that
            // it will use to bind EJBs.
            Context context = new InitialContext();
            context.bind("java:comp/env/SpecificationNamespace",
                    "com.topcoder.timetracker.entry.expense.objectfactory");
            context.bind("java:comp/env/ExpenseTypeDAOKey", "InformixExpenseTypeDAO");
            context.bind("java:comp/env/ExpenseStatusDAOKey", "InformixExpenseStatusDAO");
            context.bind("java:comp/env/ExpenseEntryDAOKey", "InformixExpenseEntryDAO");

            MockContainer mockContainer = new MockContainer(context);

            // deploy the EntryTypeEJB
            SessionBeanDescriptor beanDescriptor = new SessionBeanDescriptor("java:comp/env/ejb/entry/EntryTypeEJB",
                    ExpenseTypeLocalHome.class, ExpenseTypeLocal.class, new ExpenseTypeBean());
            mockContainer.deploy(beanDescriptor);

            // deploy the EntryStatusEJB
            beanDescriptor = new SessionBeanDescriptor("java:comp/env/ejb/entry/EntryStatusEJB",
                    ExpenseStatusLocalHome.class, ExpenseStatusLocal.class, new ExpenseStatusBean());
            mockContainer.deploy(beanDescriptor);

            // deploy the EntryEntryEJB
            beanDescriptor = new SessionBeanDescriptor("java:comp/env/ejb/entry/ExpenseEntryEJB",
                    ExpenseEntryLocalHome.class, ExpenseEntryLocal.class, new ExpenseEntryBean());
            mockContainer.deploy(beanDescriptor);

            // deploy the BaseEntryEntryEJB
            beanDescriptor = new SessionBeanDescriptor("java:comp/env/ejb/BasicExpenseEntry",
                    BasicExpenseEntryLocalHome.class, BasicExpenseEntryLocal.class, new BasicExpenseEntryBean());
            mockContainer.deploy(beanDescriptor);

            // add the transaction
            MockUserTransaction mockTransaction = new MockUserTransaction();
            context.rebind("javax.transaction.UserTransaction", mockTransaction);
            deployed = true;
        }
    }

    /**
     * <p>
     * Undeploys the mock ejb.
     * </p>
     */
    public static void undeployEJB() {
        if (deployed) {
            MockContextFactory.revertSetAsInitial();
            deployed = false;
        }
    }
    
    /**
     * Prepare test entry.
     * 
     * @return entry
     */
    static void prepareExpenseEntries(ExpenseEntry[] entries,ExpenseType type, ExpenseStatus status) {
        for (int i = 0; i < entries.length; ++i) {
            entries[i] = new ExpenseEntry((i + 1) * 10 );
            entries[i].setCreationUser("create" + i);
            entries[i].setModificationUser("modify" + i);
            entries[i].setDescription("description" + i);
            entries[i].setAmount(new BigDecimal(i * 1000));
            entries[i].setBillable(true);
            entries[i].setDate(TestHelper.createDate(2005, 7, i));
            entries[i].setExpenseType(type);
            entries[i].setStatus(status);
            Map rejectReasons = new HashMap();
        	RejectReason reason = new RejectReason();
        	reason.setId(i + 1);
        	reason.setCompanyId(TestHelper.COMPANY_ID);
        	rejectReasons.put(new Long(i + 1), reason);
            entries[i].setRejectReasons(rejectReasons);
            entries[i].setCompanyId(TestHelper.COMPANY_ID);
        }
    }
    
    /**
     * Prepare test entry.
     * 
     * @return entry
     */
    static ExpenseEntry prepareExpenseEntry(ExpenseType type, ExpenseStatus status) {
    	ExpenseEntry entry = new ExpenseEntry(100);
        entry.setDescription("description");
        entry.setCreationUser("create");
        entry.setModificationUser("modify");
        entry.setAmount(new BigDecimal(100.10));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 7, 29));
        entry.setCompanyId(TestHelper.COMPANY_ID);

        Map rejectReasons = new HashMap();
        for (int i = 1; i <= 3; i++) {
        	RejectReason reason = new RejectReason();
        	reason.setCompanyId(TestHelper.COMPANY_ID);
        	reason.setId(i);
        	rejectReasons.put(new Long(i), reason);
        }
        entry.setRejectReasons(rejectReasons);

        entry.setExpenseType(type);
        entry.setStatus(status);
        return entry;
    }

    /**
     * Reset create/modification date to null for testing.
     * 
     * @param entries the entires array
     */
    public static void resetCreateModificationDate(ExpenseEntry[] entries) {
    	for (int i = 0; i < entries.length; i++) {
    		resetCreateModificationDate(entries[i]);
    	}
    }
    
    /**
     * Reset create/modification date to null for testing.
     * 
     * @param entry the ExpenseEntry
     */
    public static void resetCreateModificationDate(ExpenseEntry entry) {
        entry.setCreationDate(null);
        entry.setModificationDate(null);
    }
}
