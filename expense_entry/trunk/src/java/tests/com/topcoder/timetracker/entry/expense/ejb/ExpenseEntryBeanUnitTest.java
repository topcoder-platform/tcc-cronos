/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.ejb;

import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseStatus;
import com.topcoder.timetracker.entry.expense.MockAuditManager;
import com.topcoder.timetracker.entry.expense.UnitTestHelper;
import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;

import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

import java.math.BigDecimal;

import java.sql.Connection;

import javax.ejb.SessionContext;


/**
 * <p>
 * Tests functionality and error cases of <code>ExpenseEntryBean</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ExpenseEntryBeanUnitTest extends TestCase {
    /** Default namespace used to in ejbCreate(). */
    private static final String DEFAULT_NAMESPACE = ExpenseEntryBean.class.getName();

    /** Represents the connection instance for testing. */
    private Connection connection = null;

    /** Represents the <code>SessionContext</code> instance used for testing. */
    private SessionContext context;

    /** Represents the <code>ExpenseEntryBean</code> instance used for testing. */
    private ExpenseEntryBean bean = null;

    /** Represents the <code>AuditManager</code> instance used for testing. */
    private MockAuditManager auditManager = null;

    /** Represents a valid expense entry entry instance. */
    private ExpenseEntry entry;

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

        // create the testing instance
        UnitTestHelper.deployEJB();
        context = new MockSessionContext();
        bean = new ExpenseEntryBean();
        bean.ejbCreate();
        bean.setSessionContext(context);

        auditManager = (MockAuditManager) UnitTestHelper.getPrivateField(bean.getDAO().getClass(), bean.getDAO(),
                "auditManager");

        entry = UnitTestHelper.BuildExpenseEntry();
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
     * Accuracy test for the constructor <code>ExpenseEntryBean()</code>. No exception is expected.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testBasicExpenseEntryBean_Accuracy() throws Exception {
        assertNotNull("The ExpenseEntryBean instance should be created.", bean);
    }

    /**
     * <p>
     * Accuracy test for the method <code>ejbCreate()</code> when the namespace which hold the property of jndi_context
     * does not exist, the default context of JNDIUtils should be used.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testEjbCreate_MissingJndiContextNamespace() throws Exception {
        ConfigManager.getInstance().removeNamespace(DEFAULT_NAMESPACE);

        bean.ejbCreate();
        assertNotNull("The bean should be created successfully.",
            UnitTestHelper.getPrivateField(bean.getClass(), bean, "basicExpenseEntryLocal"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>ejbCreate()</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testEjbCreate_Accuracy() throws Exception {
        assertNotNull("The bean should be created successfully.",
            UnitTestHelper.getPrivateField(bean.getClass(), bean, "basicExpenseEntryLocal"));
    }

    /**
     * <p>
     * Tests <code>addEntries(ExpenseEntry[], boolean)</code> when entries is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_AtomicModeNullEntries1() throws Exception {
        try {
            bean.addEntries(null, false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntries(ExpenseEntry[], boolean, boolean)</code> when entries is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_AtomicModeNullEntries2() throws Exception {
        try {
            bean.addEntries(null, true, false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntries(ExpenseEntry[], boolean)</code> when entries is empty. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_AtomicModeEmptyEntries1() throws Exception {
        try {
            bean.addEntries(new ExpenseEntry[0], false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntries(ExpenseEntry[], boolean, boolean)</code> when entries is empty. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_AtomicModeEmptyEntries2() throws Exception {
        try {
            bean.addEntries(new ExpenseEntry[0], true, false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntries(ExpenseEntry[], boolean)</code> when entries contain <code>null</code> element. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_AtomicModeEntriesContainsNullElement1() throws Exception {
        try {
            bean.addEntries(new ExpenseEntry[] {null}, false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntries(ExpenseEntry[], boolean, boolean)</code> when entries contain <code>null</code> element.
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_AtomicModeEntriesContainsNullElement2() throws Exception {
        try {
            bean.addEntries(new ExpenseEntry[] {null}, true, false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean)</code> when it is in Atomic mode. The audit flag is
     * set to true.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_AtomicModeAuditAccuracy1() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
        }

        bean.addEntries(expected, true);

        // check the AuditManager
        assertEquals("AuditManager should be properly invoked.", 5, this.auditManager.getAuditHeaders().length);

        for (int i = 0; i < this.auditManager.getAuditHeaders().length; i++) {
            assertEquals("AuditManager should be properly invoked.", 14,
                this.auditManager.getAuditHeaders()[i].getDetails().length);
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean, boolean)</code> when it is in Atomic mode. The audit
     * flag is set to true.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_AtomicModeAuditAccuracy2() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
        }

        bean.addEntries(expected, true, true);

        // check the AuditManager
        assertEquals("AuditManager should be properly invoked.", 5, this.auditManager.getAuditHeaders().length);

        for (int i = 0; i < this.auditManager.getAuditHeaders().length; i++) {
            assertEquals("AuditManager should be properly invoked.", 14,
                this.auditManager.getAuditHeaders()[i].getDetails().length);
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean)</code> when it is in Atomic mode. All the entries
     * should be added. These entries have their own id already (NOT -1).
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_AtomicModeAccuracy1() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
        }

        bean.addEntries(expected, false);

        ExpenseEntry[] actual = bean.retrieveAllEntries();
        UnitTestHelper.assertEquals(expected, actual);
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean)</code> when it is in Atomic mode. One of the entries
     * has errors to insert into the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_AtomicModeAccuracy2() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
        }

        expected[4].setStatus(new ExpenseStatus(10));

        try {
            bean.addEntries(expected, false);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean)</code> when it is in Atomic mode. One of the entries
     * has errors to insert into the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_AtomicModeAccuracy3() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
        }

        expected[4].setId(1);

        try {
            bean.addEntries(expected, false);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean, boolean)</code> when it is in Atomic mode. All the
     * entries should be added. These entries have their own id already (NOT -1).
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_AtomicModeAccuracy4() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
        }

        assertNull("Should return null", bean.addEntries(expected, true, false));

        ExpenseEntry[] actual = bean.retrieveAllEntries();
        UnitTestHelper.assertEquals(expected, actual);
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean, boolean)</code> when it is in Atomic mode. One of
     * the entries has errors to insert into the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_AtomicModeAccuracy5() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
        }

        expected[4].setStatus(new ExpenseStatus(10));

        try {
            bean.addEntries(expected, true, false);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean, boolean)</code> when it is in Atomic mode. One of
     * the entries has errors to insert into the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_AtomicModeAccuracy6() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
        }

        expected[4].setId(1);

        try {
            bean.addEntries(expected, true, false);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntries(ExpenseEntry[], boolean, boolean)</code> when entries is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_NonAtomicModeNullEntries() throws Exception {
        try {
            bean.addEntries(null, false, false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntries(ExpenseEntry[], boolean, boolean)</code> when entries is empty. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_NonAtomicModeEmptyEntries() throws Exception {
        try {
            bean.addEntries(new ExpenseEntry[0], false, false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntries(ExpenseEntry[], boolean, boolean)</code> when entries contain <code>null</code> element.
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_NonAtomicModeEntriesContainsNullElement() throws Exception {
        try {
            bean.addEntries(new ExpenseEntry[] {null}, false, false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean, boolean)</code> when it is in non-Atomic mode. All
     * the entries should be added. These entries have their own id already (NOT -1).
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_NonAtomicModeAccuracy1() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
        }

        bean.addEntries(expected, false, false);

        ExpenseEntry[] actual = bean.retrieveAllEntries();
        UnitTestHelper.assertEquals(expected, actual);
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean, boolean)</code> when it is in non-Atomic mode. One
     * of the entries has errors to insert into the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_NonAtomicModeAccuracy2() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
        }

        expected[4].getStatus().setId(10);

        ExpenseEntry[] errors = bean.addEntries(expected, false, false);
        UnitTestHelper.assertEquals(new ExpenseEntry[] {expected[4]}, errors);
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean, boolean)</code> when it is in non-Atomic mode. One
     * of the entries has errors to insert into the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_NonAtomicModeAccuracy3() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
        }

        expected[4].setId(1);

        ExpenseEntry[] errors = bean.addEntries(expected, false, false);
        UnitTestHelper.assertEquals(new ExpenseEntry[] {expected[4]}, errors);
    }

    /**
     * <p>
     * Tests <code>deleteEntries(long[], boolean)</code> when entryIds is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntries_AtomicNullEntries1() throws Exception {
        try {
            bean.deleteEntries(null, false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>deleteEntries(long[], boolean, boolean)</code> when entryIds is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntries_AtomicNullEntries2() throws Exception {
        try {
            bean.deleteEntries(null, true, false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>deleteEntries(long[], boolean)</code> when entryIds is empty. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntries_AtomicEmptyEntries1() throws Exception {
        try {
            bean.deleteEntries(new long[0], false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>deleteEntries(long[], boolean, boolean)</code> when entryIds is empty. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntries_AtomicEmptyEntries2() throws Exception {
        try {
            bean.deleteEntries(new long[0], true, false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteEntries(long[], boolean)</code> when it is in Atomic mode. The audit flag is set
     * to true.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntries_AtomicModeAuditAccuracy1() throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = UnitTestHelper.BuildExpenseEntry();
            entry.setId(i + 1);
            bean.addEntry(entry, false);
        }

        // delete
        bean.deleteEntries(new long[] {1, 2, 3, 4, 5}, true);

        // check the AuditManager
        assertEquals("AuditManager should be properly invoked.", 5, this.auditManager.getAuditHeaders().length);

        for (int i = 0; i < this.auditManager.getAuditHeaders().length; i++) {
            assertEquals("AuditManager should be properly invoked.", 14,
                this.auditManager.getAuditHeaders()[i].getDetails().length);
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteEntries(long[], boolean, boolean)</code> when it is in Atomic mode. The audit flag
     * is set to true.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntries_AtomicModeAuditAccuracy2() throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = UnitTestHelper.BuildExpenseEntry();
            entry.setId(i + 1);
            bean.addEntry(entry, false);
        }

        // delete
        bean.deleteEntries(new long[] {1, 2, 3, 4, 5}, true, true);

        // check the AuditManager
        assertEquals("AuditManager should be properly invoked.", 5, this.auditManager.getAuditHeaders().length);

        for (int i = 0; i < this.auditManager.getAuditHeaders().length; i++) {
            assertEquals("AuditManager should be properly invoked.", 14,
                this.auditManager.getAuditHeaders()[i].getDetails().length);
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteEntries(long[], boolean)</code> when it is in Atomic mode. All the entryIds should
     * be deleted.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntries_AtomicModeAccuracy1() throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = UnitTestHelper.BuildExpenseEntry();
            entry.setId(i + 1);
            bean.addEntry(entry, false);
        }

        // delete
        bean.deleteEntries(new long[] {1, 2, 3, 4, 5}, true);

        // Verify number
        assertEquals("The number of items in list should be correct.", 0, bean.retrieveAllEntries().length);
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteEntries(long[], boolean)</code> when it is in Atomic mode. One of the entryIds has
     * not exist in the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntries_AtomicModeAccuracy2() throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = UnitTestHelper.BuildExpenseEntry();
            entry.setId(i + 1);
            bean.addEntry(entry, false);
        }

        bean.deleteEntry(entry.getId(), false);

        try {
            bean.deleteEntries(new long[] {1, 2, 3, 4, 5}, false);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteEntries(long[], boolean, boolean)</code> when it is in Atomic mode. All the
     * entryIds should be deleted.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntries_AtomicModeAccuracy3() throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = UnitTestHelper.BuildExpenseEntry();
            entry.setId(i + 1);
            bean.addEntry(entry, false);
        }

        // delete
        assertNull("Should return null.", bean.deleteEntries(new long[] {1, 2, 3, 4, 5}, true, false));

        // Verify number
        assertEquals("The number of items in list should be correct.", 0, bean.retrieveAllEntries().length);
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteEntries(long[], boolean, boolean)</code> when it is in Atomic mode. One of the
     * entryIds has not exist in the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntries_AtomicModeAccuracy4() throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = UnitTestHelper.BuildExpenseEntry();
            entry.setId(i + 1);
            bean.addEntry(entry, false);
        }

        bean.deleteEntry(entry.getId(), false);

        try {
            bean.deleteEntries(new long[] {1, 2, 3, 4, 5}, true, false);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>deleteEntries(long[], boolean, boolean)</code> when entryIds is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntries_NonAtomicNullEntries() throws Exception {
        try {
            bean.deleteEntries(null, false, false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>deleteEntries(long[], boolean, boolean)</code> when entryIds is empty. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntries_NonAtomicEmptyEntries() throws Exception {
        try {
            bean.deleteEntries(new long[0], false, false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteEntries(long[], boolean, boolean)</code> when it is in non-Atomic mode. All the
     * entryIds should be deleted.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntries_NonAtomicModeAccuracy1() throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = UnitTestHelper.BuildExpenseEntry();
            entry.setId(i + 1);
            bean.addEntry(entry, false);
        }

        // delete
        bean.deleteEntries(new long[] {1, 2, 3, 4, 5}, false, true);

        // Verify number
        assertEquals("The number of items in list should be correct.", 0, bean.retrieveAllEntries().length);
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteEntries(long[], boolean, boolean)</code> when it is in non-Atomic mode. One of the
     * entryIds has not exist in the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntries_NonAtomicModeAccuracy2() throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = UnitTestHelper.BuildExpenseEntry();
            entry.setId(i + 1);
            bean.addEntry(entry, false);
        }

        bean.deleteEntry(entry.getId(), false);

        long[] errors = bean.deleteEntries(new long[] {1, 2, 3, 4, 5}, false, false);
        assertEquals("The error id should be correct.", 1, errors.length);
        assertEquals("The error id should be correct.", entry.getId(), errors[0]);
    }

    /**
     * <p>
     * Tests <code>updateEntries(ExpenseEntry[], boolean)</code> when entries is null. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_AtomicNullEntries1() throws Exception {
        try {
            bean.updateEntries(null, false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntries(ExpenseEntry[], boolean, boolean)</code> when entries is null. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_AtomicNullEntries2() throws Exception {
        try {
            bean.updateEntries(null, true, false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntries(ExpenseEntry[], boolean)</code> when entries is empty. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_AtomicEmptyEntries1() throws Exception {
        try {
            bean.updateEntries(new ExpenseEntry[0], false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntries(ExpenseEntry[], boolean, boolean)</code> when entries is empty. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_AtomicEmptyEntries2() throws Exception {
        try {
            bean.updateEntries(new ExpenseEntry[0], true, false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntries(ExpenseEntry[], boolean)</code> when entries contain <code>null</code> element. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_AtomicEntriesContainsNullElement1() throws Exception {
        try {
            bean.updateEntries(new ExpenseEntry[] {null}, false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntries(ExpenseEntry[], boolean, boolean)</code> when entries contain <code>null</code>
     * element. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_AtomicEntriesContainsNullElement2() throws Exception {
        try {
            bean.updateEntries(new ExpenseEntry[] {null}, true, false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntries(ExpenseEntry[], boolean)</code> when it is in Atomic mode. The audit flag
     * is set to true.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_AtomicModeAuditAccuracy1() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            bean.addEntry(expected[i], false);
        }

        // update it
        Thread.sleep(1000);

        for (int i = 0; i < 5; ++i) {
            expected[i].setAmount(new BigDecimal("1000"));
            expected[i].setInvoice(null);
        }

        bean.updateEntries(expected, true);

        // check the AuditManager
        assertEquals("AuditManager should be properly invoked.", 5, this.auditManager.getAuditHeaders().length);

        for (int i = 0; i < this.auditManager.getAuditHeaders().length; i++) {
            assertEquals("The " + i + "th AuditManager should be properly invoked.", 3,
                this.auditManager.getAuditHeaders()[i].getDetails().length);
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntries(ExpenseEntry[], boolean, boolean)</code> when it is in Atomic mode. The
     * audit flag is set to true.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_AtomicModeAuditAccuracy2() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            bean.addEntry(expected[i], false);
        }

        // update it
        Thread.sleep(1000);

        for (int i = 0; i < 5; ++i) {
            expected[i].setAmount(new BigDecimal("1000"));
            expected[i].setInvoice(null);
        }

        bean.updateEntries(expected, true, true);

        // check the AuditManager
        assertEquals("AuditManager should be properly invoked.", 5, this.auditManager.getAuditHeaders().length);

        for (int i = 0; i < this.auditManager.getAuditHeaders().length; i++) {
            assertEquals("The " + i + "th AuditManager should be properly invoked.", 3,
                this.auditManager.getAuditHeaders()[i].getDetails().length);
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntries(ExpenseEntry[], boolean)</code> when it is in Atomic mode. All the entries
     * should be added. These entries have their own id already (NOT -1).
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_AtomicModeAccuracy1() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            bean.addEntry(expected[i], false);
        }

        // update it
        for (int i = 0; i < 5; ++i) {
            expected[i].setAmount(new BigDecimal("1000"));
            expected[i].setInvoice(null);
        }

        bean.updateEntries(expected, true);

        // check
        ExpenseEntry[] actual = bean.retrieveAllEntries();
        UnitTestHelper.assertEquals(expected, actual);
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntries(ExpenseEntry[], boolean)</code> when it is in Atomic mode. One of the
     * entries has errors to insert into the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_AtomicModeAccuracy2() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            bean.addEntry(expected[i], false);
        }

        expected[4].setStatus(new ExpenseStatus(10));

        try {
            bean.updateEntries(expected, false);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntries(ExpenseEntry[], boolean)</code> when it is in Atomic mode. One of the
     * entries has errors to insert into the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_AtomicModeAccuracy3() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            bean.addEntry(expected[i], false);
        }

        bean.deleteEntry(1, false);

        try {
            bean.updateEntries(expected, false);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntries(ExpenseEntry[], boolean, boolean)</code> when it is in Atomic mode. All
     * the entries should be added. These entries have their own id already (NOT -1).
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_AtomicModeAccuracy4() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            bean.addEntry(expected[i], false);
        }

        // update it
        for (int i = 0; i < 5; ++i) {
            expected[i].setAmount(new BigDecimal("1000"));
            expected[i].setInvoice(null);
        }

        bean.updateEntries(expected, true, true);

        // check
        ExpenseEntry[] actual = bean.retrieveAllEntries();
        UnitTestHelper.assertEquals(expected, actual);
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntries(ExpenseEntry[], boolean, boolean)</code> when it is in Atomic mode. One of
     * the entries has errors to insert into the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_AtomicModeAccuracy5() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            bean.addEntry(expected[i], false);
        }

        expected[4].setStatus(new ExpenseStatus(10));

        try {
            bean.updateEntries(expected, true, false);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntries(ExpenseEntry[], boolean, boolean)</code> when it is in Atomic mode. One of
     * the entries has errors to insert into the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_AtomicModeAccuracy6() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            bean.addEntry(expected[i], false);
        }

        bean.deleteEntry(1, false);

        try {
            bean.updateEntries(expected, true, false);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntries(ExpenseEntry[], boolean, boolean)</code> when entries is null. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_NonAtomicNullEntries() throws Exception {
        try {
            bean.updateEntries(null, false, false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntries(ExpenseEntry[], boolean, boolean)</code> when entries is empty. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_NonAtomicEmptyEntries() throws Exception {
        try {
            bean.updateEntries(new ExpenseEntry[0], false, false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntries(ExpenseEntry[], boolean, boolean)</code> when entries contain <code>null</code>
     * element. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_NonAtomicEntriesContainsNullElement() throws Exception {
        try {
            bean.updateEntries(new ExpenseEntry[] {null}, false, false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntries(ExpenseEntry[], boolean, boolean)</code> when it is in non-Atomic mode.
     * All the entries should be added. These entries have their own id already (NOT -1).
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_NonAtomicModeAccuracy1() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            bean.addEntry(expected[i], false);
        }

        // update it
        for (int i = 0; i < 5; ++i) {
            expected[i].setAmount(new BigDecimal("1000"));
            expected[i].setInvoice(null);
        }

        bean.updateEntries(expected, false, true);

        // check
        ExpenseEntry[] actual = bean.retrieveAllEntries();
        UnitTestHelper.assertEquals(expected, actual);
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntries(ExpenseEntry[], boolean, boolean)</code> when it is in non-Atomic mode.
     * One of the entries has errors to insert into the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_NonAtomicModeAccuracy2() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            bean.addEntry(expected[i], false);
        }

        expected[4].getStatus().setId(10);

        ExpenseEntry[] errors = bean.updateEntries(expected, false, false);
        UnitTestHelper.assertEquals(new ExpenseEntry[] {expected[4]}, errors);
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntries(ExpenseEntry[], boolean)</code> when it is in non-Atomic mode. One of the
     * entries has errors to insert into the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_NonAtomicModeAccuracy3() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            bean.addEntry(expected[i], false);
        }

        bean.deleteEntry(1, false);

        ExpenseEntry[] errors = bean.updateEntries(expected, false, false);
        UnitTestHelper.assertEquals(new ExpenseEntry[] {expected[0]}, errors);
    }

    /**
     * <p>
     * Tests <code>retrieveEntries(long[])</code> when entryIds is <code>null</code>. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntries_AtomicNullEntries1() throws Exception {
        try {
            bean.retrieveEntries(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>retrieveEntries(long[], boolean)</code> when entryIds is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntries_AtomicNullEntries2() throws Exception {
        try {
            bean.retrieveEntries(null, true);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>retrieveEntries(long[], boolean)</code> when entryIds is empty. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntries_AtomicEmptyEntries1() throws Exception {
        try {
            bean.retrieveEntries(new long[0], true);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>retrieveEntries(long[])</code> when entryIds is empty. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntries_AtomicEmptyEntries2() throws Exception {
        try {
            bean.retrieveEntries(new long[0]);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveEntries(int[], boolean)</code> when it is in Atomic mode. All the entries should
     * be retrieved.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntries_AtomicModeAccuracy1() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            bean.addEntry(expected[i], false);
        }

        ExpenseEntry[] actual = bean.retrieveEntries(new long[] {1, 2, 3, 4, 5});

        UnitTestHelper.assertEquals(expected, actual);
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveEntries(int[], boolean)</code> when it is in Atomic mode. One of the entryIds
     * has not exist in the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntries_AtomicModeAccuracy2() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[3];

        // Add 3 instances
        for (int i = 0; i < 3; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            bean.addEntry(expected[i], false);
        }

        try {
            bean.retrieveEntries(new long[] {0, 1, 2, 3, 4});
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveEntries(int[], boolean)</code> when it is in Atomic mode. All the entries should
     * be retrieved.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntries_AtomicModeAccuracy3() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            bean.addEntry(expected[i], false);
        }

        ExpenseEntry[] actual = bean.retrieveEntries(new long[] {1, 2, 3, 4, 5}, true);

        UnitTestHelper.assertEquals(expected, actual);
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveEntries(int[], boolean)</code> when it is in Atomic mode. One of the entryIds
     * has not exist in the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntries_AtomicModeAccuracy4() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[3];

        // Add 3 instances
        for (int i = 0; i < 3; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            bean.addEntry(expected[i], false);
        }

        try {
            bean.retrieveEntries(new long[] {0, 1, 2, 3, 4}, true);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>retrieveEntries(long[], boolean)</code> when entryIds is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntries_NonAtomicNullEntries() throws Exception {
        try {
            bean.retrieveEntries(null, false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>retrieveEntries(long[], boolean)</code> when entryIds is empty. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntries_NonAtomicEmptyEntries() throws Exception {
        try {
            bean.retrieveEntries(new long[0], false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveEntries(int[], boolean)</code> when it is in Non-Atomic mode. All the entries
     * should be retrieved.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntries_NonAtomicModeAccuracy1() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            bean.addEntry(expected[i], false);
        }

        ExpenseEntry[] actual = bean.retrieveEntries(new long[] {1, 2, 3, 4, 5}, false);

        UnitTestHelper.assertEquals(expected, actual);
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveEntries(int[], boolean)</code> when it is in Non-Atomic mode. One of the
     * entryIds has not exist in the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntries_NonAtomicModeAccuracy2() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[3];

        // Add 3 instances
        for (int i = 0; i < 3; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            bean.addEntry(expected[i], false);
        }

        ExpenseEntry[] actual = bean.retrieveEntries(new long[] {0, 1, 2, 3, 4}, false);
        UnitTestHelper.assertEquals(expected, actual);
    }
}
