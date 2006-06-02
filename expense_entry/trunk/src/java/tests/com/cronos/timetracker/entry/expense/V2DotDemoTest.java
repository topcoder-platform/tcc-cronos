package com.cronos.timetracker.entry.expense;

import java.sql.Connection;

import com.cronos.timetracker.entry.expense.search.CompositeCriteria;
import com.cronos.timetracker.entry.expense.search.Criteria;
import com.cronos.timetracker.entry.expense.search.FieldLikeCriteria;
import com.cronos.timetracker.entry.expense.search.FieldMatchCriteria;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import junit.framework.TestCase;

/**
 * <p>
 * Demo shows how to use this component, the funcitonality added in 2.0
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class V2DotDemoTest extends TestCase {
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
        V1Dot1TestHelper.executeSQL("insert into company values(1, 'a', 'a', current, 'a', current, 'a')", connection);
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
     * Shows how to search expense status.
     * </p>
     * @throws Exception Exception to JUnit.
     */
    public void testSearchStatus() throws Exception {
        ExpenseEntryStatusManager manager = new ExpenseEntryStatusManager(NAMESPACE);

        ExpenseEntryStatus status = new ExpenseEntryStatus();
        status.setDescription("search");
        status.setCreationUser("admin");
        status.setModificationUser("Modify");
        manager.addStatus(status);
        status = new ExpenseEntryStatus();
        status.setDescription("search");
        status.setCreationUser("client");
        status.setModificationUser("Modify");
        manager.addStatus(status);

        Criteria criteria = FieldMatchCriteria.getExpenseStatusCreationUserMatchCriteria("admin");
        ExpenseEntryStatus[] statuses = manager.searchEntries(criteria);
        assertEquals("One record should be matched", 1, statuses.length);
    }

    /**
     * <p>
     * Shows how to search expense types.
     * </p>
     * @throws Exception Exception to JUnit.
     */
    public void testSearchTypes() throws Exception {
        ExpenseEntryTypeManager manager = new ExpenseEntryTypeManager(NAMESPACE);
        for (int i = 0; i < 5; ++i) {
            ExpenseEntryType type = new ExpenseEntryType(i);
            type.setCompanyId(1);
            type.setCreationUser("Create" + i);
            type.setModificationUser("Modify" + i);
            if (i % 3 == 0) {
                type.setDescription("Description" + i);
            } else {
                type.setDescription("xxxx");
            }
            manager.addType(type);
        }
        Criteria c1 = FieldMatchCriteria.getExpenseTypeCompanyIdMatchCriteria(1);
        Criteria c2 = FieldLikeCriteria.getExpenseTypeDescriptionContainsCriteria("Des");
        Criteria criteria = CompositeCriteria.getAndCompositeCriteria(new Criteria[] {c1, c2});

        ExpenseEntryType[] types = manager.searchEntries(criteria);
        assertEquals("Failed to get the expected result.", 2, types.length);
    }
}
