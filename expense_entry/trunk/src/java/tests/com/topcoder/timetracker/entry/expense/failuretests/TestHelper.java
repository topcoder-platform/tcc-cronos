/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.failuretests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.Assert;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.mockrunner.mock.ejb.MockUserTransaction;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
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
import com.topcoder.timetracker.entry.expense.persistence.ExpenseEntryStatusDAO;
import com.topcoder.timetracker.entry.expense.persistence.ExpenseEntryTypeDAO;
import com.topcoder.timetracker.entry.expense.persistence.InformixExpenseStatusDAO;
import com.topcoder.timetracker.entry.expense.persistence.InformixExpenseTypeDAO;
import com.topcoder.timetracker.invoice.Invoice;
import com.topcoder.timetracker.rejectreason.RejectReason;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Defines helper methods used in tests.
 * </p>
 * @author myxgyy
 * @version 3.2
 */
public class TestHelper {
    /**
     * Represents connection used for testing.
     */
    public static final String CONN_NAME = "informix";

    /**
     * Represents the company id used for testing.
     */
    public static final long COMPANY_ID = 1;

    /**
     * Represents whether the mock ejb to be deployed.
     */
    private static boolean deployed = false;

    /**
     * Represents the db connection factory for testing.
     */
    private static DBConnectionFactory connFactory;

    /**
     * <p>
     * Creates a new instance of <code>UnitTestHelper</code> class. The private constructor prevents the creation of a
     * new instance.
     * </p>
     */
    private TestHelper() {
    }

    /**
     * <p>
     * Builds the invoice instance for testing.
     * </p>
     *
     * @return the invoice instance for testing.
     */
    public static Invoice BuildInvoice() {
        Invoice invoice = new Invoice();
        invoice.setId(1);
        invoice.setCompanyId(COMPANY_ID);

        return invoice;
    }

    /**
     * <p>
     * Builds the RejectReason instance for testing.
     * </p>
     *
     * @return the RejectReason instance for testing.
     */
    public static RejectReason BuildRejectReason() {
        RejectReason rejectReason = new RejectReason();
        rejectReason.setId(1);
        rejectReason.setCompanyId(COMPANY_ID);

        return rejectReason;
    }

    /**
     * <p>
     * Builds the ExpenseEntry instance for testing.
     * </p>
     *
     * @return the ExpenseEntry instance for testing.
     *
     * @throws Exception unexpected exception.
     */
    public static ExpenseEntry BuildExpenseEntry() throws Exception {
        // Create the expense status
        ExpenseEntryStatusDAO informixExpenseStatusDAO = new InformixExpenseStatusDAO();
        ExpenseStatus status = informixExpenseStatusDAO.retrieveStatus(2);

        if (status == null) {
            status = new ExpenseStatus(2);
            status.setDescription("Pending Approval");
            status.setCreationUser("Ivern");
            status.setModificationUser("TangentZ");
            informixExpenseStatusDAO.addStatus(status);
        }

        // Create the expense type
        ExpenseEntryTypeDAO informixExpenseTypeDAO = new InformixExpenseTypeDAO();
        ExpenseType type = informixExpenseTypeDAO.retrieveType(1);

        if (type == null) {
            type = new ExpenseType(1);
            type.setCompanyId(COMPANY_ID);
            type.setDescription("Travel Expense");
            type.setCreationUser("TangentZ");
            type.setModificationUser("Ivern");
            informixExpenseTypeDAO.addType(type);
        }

        // Create the expense entry
        ExpenseEntry entry = new ExpenseEntry();
        entry.setCompanyId(COMPANY_ID);
        entry.setClientId(1);
        entry.setProjectId(1);
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100.12));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);
        entry.setStatus(status);
        entry.setMileage(10);
        entry.setInvoice(TestHelper.BuildInvoice());

        Map rejectReasons = new HashMap();
        rejectReasons.put(new Long(TestHelper.BuildRejectReason().getId()), TestHelper.BuildRejectReason());
        entry.setRejectReasons(rejectReasons);

        return entry;
    }

    /**
     * <p>
     * Add the valid config files for testing.
     * </p>
     *
     * @param fileName the file to add.
     *
     * @throws Exception unexpected exception.
     */
    public static void addConfig(String fileName) throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();
        configManager.add(fileName);
    }

    /**
     * <p>
     * clear the config.
     * </p>
     *
     * @throws Exception unexpected exception.
     */
    public static void clearConfig() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        for (Iterator iter = configManager.getAllNamespaces(); iter.hasNext();) {
            configManager.removeNamespace((String) iter.next());
        }
    }

    /**
     * <p>
     * Gets the value of a private field in the given class. The field has the given name. The value is retrieved from
     * the given instance.
     * </p>
     *
     * @param type the class which the private field belongs to.
     * @param instance the instance which the private field belongs to.
     * @param name the name of the private field to be retrieved.
     *
     * @return the value of the private field or <code>null</code> if any error occurs.
     */
    public static Object getPrivateField(Class type, Object instance, String name) {
        Field field = null;
        Object obj = null;

        try {
            // Get the reflection of the field and get the value
            field = type.getDeclaredField(name);
            field.setAccessible(true);
            obj = field.get(instance);
        } catch (IllegalAccessException e) {
            e.printStackTrace();

            // ignore
        } catch (NoSuchFieldException e) {
            e.printStackTrace();

            // ignore
        } finally {
            if (field != null) {
                // Reset the accessibility
                field.setAccessible(false);
            }
        }

        return obj;
    }

    /**
     * Sets the value of a private field in the given class. The field has the given name. The value is retrieved from
     * the given instance.
     *
     * @param type the class which the private field belongs to.
     * @param instance the instance which the private field belongs to.
     * @param name the name of the private field to be setted.
     * @param value the value be given to the field.
     */
    public static void setPrivateField(Class type, Object instance, String name, Object value) {
        Field field = null;

        try {
            // Get the reflection of the field and get the value
            field = type.getDeclaredField(name);
            field.setAccessible(true);
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();

            // ignore
        } catch (NoSuchFieldException e) {
            e.printStackTrace();

            // ignore
        } finally {
            if (field != null) {
                // Reset the accessibility
                field.setAccessible(false);
            }
        }
    }

    /**
     * <p>
     * Prepare data for test.
     * </p>
     *
     * @param connection database connection.
     *
     * @throws SQLException error occurs when access the database.
     */
    public static void prepareData(Connection connection) throws SQLException {
        clearDatabase(connection);
        executeScript(connection, "FailureTests/prepare.sql");
    }

    /**
     * <p>
     * Executes the sql script from the given file.
     * </p>
     *
     * @param connection database connection.
     * @param fileName the file name.
     *
     * @throws SQLException error occurs when access the database.
     */
    private static void executeScript(Connection connection, String fileName) throws SQLException {
        Statement statement = connection.createStatement();

        try {
            InputStream input = TestHelper.class.getClassLoader().getResourceAsStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            String line = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if ((line.length() != 0) && !line.startsWith("--")) {
                    statement.executeUpdate(line);
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            statement.close();
        }
    }

    /**
     * <p>
     * Get the DBConnectionFactory instance.
     * </p>
     *
     * @return the DBConnectionFactory instance.
     *
     * @throws Exception any exception during the create DBConnectionFactory process.
     */
    public static DBConnectionFactory getDBConnectionFactory() throws Exception {
        if (connFactory == null) {
            connFactory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
        }

        return connFactory;
    }

    /**
     * <p>
     * Get the Connection from DBConnectionFactory.
     * </p>
     *
     * @return the connection created from DBConnectionFactory.
     *
     * @throws Exception any exception during the create connection process.
     */
    public static Connection getConnection() throws Exception {
        return getDBConnectionFactory().createConnection();
    }

    /**
     * <p>
     * Clears the content in database.
     * </p>
     *
     * @param connection the database connection used to access database.
     *
     * @throws SQLException error occurs when access the database.
     */
    public static void clearDatabase(Connection connection) throws SQLException {
        executeScript(connection, "FailureTests/clear.sql");
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
     * <p>
     * Asserts the given two collection are equal.
     * </p>
     *
     * @param expected the expected colleciton.
     * @param actual the actual collection.
     */
    public static void assertArrayEquals(Object[] expected, Object[] actual) {
        Assert.assertEquals("The size should be equal.", expected.length, actual.length);

        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals("The content should be equal.", expected[i], actual[i]);
        }
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
}
