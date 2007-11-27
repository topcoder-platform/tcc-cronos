/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.db;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.timetracker.user.BatchOperationException;
import com.topcoder.timetracker.user.DataAccessException;
import com.topcoder.timetracker.user.TestHelper;
import com.topcoder.timetracker.user.UnrecognizedEntityException;
import com.topcoder.timetracker.user.UserType;
import com.topcoder.timetracker.user.filterfactory.UserTypeFilterFactory;
import com.topcoder.util.idgenerator.IDGenerationException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for <code>DbUserTypeDAO</code>.
 *
 * @author enefem21
 * @version 3.2.1
 * @since 3.2.1
 */
public class DbUserTypeDAOTest extends TestCase {

    /** Unit under test. */
    private DbUserTypeDAO dbUserTypeDAO;

    /**
     * <p>
     * The DBConnectionFactory instance for testing.
     * </p>
     */
    private DBConnectionFactory dbFactory;

    /**
     * <p>
     * Returns the suite for this unit test.
     * </p>
     *
     * @return the suite
     */
    public static Test suite() {
        return new TestSuite(DbUserTypeDAOTest.class);
    }

    /**
     * Sets the unit test up.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE);
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE_3_2_1);
        TestHelper.setUpDataBase();

        dbFactory = new DBConnectionFactoryImpl(TestHelper.DB_FACTORY_NAMESPACE);

        dbUserTypeDAO =
            new DbUserTypeDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.UserType",
                "com.topcoder.search.builder", "userTypeSearchBundle");
    }

    /**
     * Tears the unit test down.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        dbUserTypeDAO = null;

        TestHelper.tearDownDataBase();
        TestHelper.clearConfig();
        super.tearDown();
    }

    /**
     * Test constructor for accuracy. Condition: normal. Expect: the object is created properly.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDbUserTypeDAOAccuracy() throws Exception {
        assertNotNull("The object can't be created properly", new DbUserTypeDAO(dbFactory, "tt_user",
            "com.topcoder.timetracker.user.UserType", "com.topcoder.search.builder", "userTypeSearchBundle"));
    }

    /**
     * Test constructor for failure. Condition: connFactory is null. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDbUserTypeConnFactoryNull() throws Exception {
        try {
            new DbUserTypeDAO(null, "tt_user", "com.topcoder.timetracker.user.UserType",
                "com.topcoder.search.builder", "userTypeSearchBundle");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: connName is empty. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDbUserTypeConnNameEmpty() throws Exception {
        try {
            new DbUserTypeDAO(dbFactory, " \n", "com.topcoder.timetracker.user.UserType",
                "com.topcoder.search.builder", "userTypeSearchBundle");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: idGen is null. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDbUserTypeIdGenNull() throws Exception {
        try {
            new DbUserTypeDAO(dbFactory, "tt_user", null, "com.topcoder.search.builder",
                "userTypeSearchBundle");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: idGen is empty. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDbUserTypeIdGenEmpty() throws Exception {
        try {
            new DbUserTypeDAO(dbFactory, "tt_user", "\t", "com.topcoder.search.builder",
                "userTypeSearchBundle");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: searchBundleManagerNamespace is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDbUserTypeSearchBundleManagerNamespaceNull() throws Exception {
        try {
            new DbUserTypeDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.UserType", null,
                "userTypeSearchBundle");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: searchBundleManagerNamespace is empty. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDbUserTypeSearchBundleManagerNamespaceEmpty() throws Exception {
        try {
            new DbUserTypeDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.UserType", "    ",
                "userTypeSearchBundle");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: searchBundleName is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDbUserTypeSearchBundleNameNull() throws Exception {
        try {
            new DbUserTypeDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.UserType",
                "com.topcoder.search.builder", null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: searchBundleName is empty. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDbUserTypeSearchBundleNameEmpty() throws Exception {
        try {
            new DbUserTypeDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.UserType",
                "com.topcoder.search.builder", "    ");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>addUserTypes</code> for accuracy. Condition: normal. Expect: the user statuses are added
     * properly.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUserTypesAccuracy() throws Exception {
        UserType userType = new UserType();
        userType.setDescription("description");
        userType.setCompanyId(10);
        userType.setCreationUser("test");
        userType.setModificationUser("test");
        userType.setCreationDate(new Date());
        userType.setModificationDate(new Date());
        dbUserTypeDAO.addUserTypes(new UserType[] {userType});

        UserType[] userTypes = dbUserTypeDAO.getUserTypes(new long[] {userType.getId()});

        assertEquals("Returned value is not as expected", 1, userTypes.length);
        assertEquals("Returned description is not as expected", "description", userTypes[0].getDescription());
        assertEquals("Returned company id is not as expected", 10, userTypes[0].getCompanyId());
        assertEquals("Returned creation user is not as expected", "test", userTypes[0].getCreationUser());
        assertEquals("Returned modification user is not as expected", "test", userTypes[0]
            .getModificationUser());

    }

    /**
     * Test <code>addUserTypes</code> for failure. Condition: userTypes is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUserTypesNull() throws Exception {
        try {
            dbUserTypeDAO.addUserTypes(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>addUserTypes</code> for failure. Condition: userTypes is empty. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUserTypesEmpty() throws Exception {
        try {
            dbUserTypeDAO.addUserTypes(new UserType[0]);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>addUserTypes</code> for failure. Condition: some of userTypes are not valid. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUserTypesMissingDescription() throws Exception {
        UserType userType = new UserType();
        userType.setCompanyId(10);
        userType.setCreationUser("test");
        userType.setModificationUser("test");
        userType.setCreationDate(new Date());
        userType.setModificationDate(new Date());
        try {
            dbUserTypeDAO.addUserTypes(new UserType[] {userType});
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>addUserTypes</code> for failure. Condition: some of userTypes are not valid. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUserTypesMissingCompanyId() throws Exception {
        UserType userType = new UserType();
        userType.setDescription("description");
        userType.setCreationUser("test");
        userType.setModificationUser("test");
        userType.setCreationDate(new Date());
        userType.setModificationDate(new Date());
        try {
            dbUserTypeDAO.addUserTypes(new UserType[] {userType});
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>addUserTypes</code> for failure. Condition: some of userTypes are already added. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUserTypesAlreadyAdded() throws Exception {
        UserType userType = new UserType();
        userType.setDescription("description");
        userType.setCompanyId(10);
        userType.setCreationUser("test");
        userType.setModificationUser("test");
        userType.setCreationDate(new Date());
        userType.setModificationDate(new Date());
        dbUserTypeDAO.addUserTypes(new UserType[] {userType});

        try {
            dbUserTypeDAO.addUserTypes(new UserType[] {userType});
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }

    }

    /**
     * Test <code>addUserTypes</code> for failure. Condition: id generation is not working correctly. Expect:
     * <code>BatchOperationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUserTypesIdGenerationTableNotPrepared() throws Exception {
        Connection conn = dbFactory.createConnection();
        Statement statement = conn.createStatement();
        statement.execute("delete from id_sequences");
        statement.close();
        conn.close();

        UserType userType = new UserType();
        userType.setDescription("description");
        userType.setCompanyId(10);
        userType.setCreationUser("test");
        userType.setModificationUser("test");
        userType.setCreationDate(new Date());
        userType.setModificationDate(new Date());

        try {
            dbUserTypeDAO.addUserTypes(new UserType[] {userType});
            fail("Should throw BatchOperationException");
        } catch (BatchOperationException e) {
            if (!(e.getCauses()[0] instanceof IDGenerationException)) {
                fail("The error should be IDGenerationException");
            }
        }

    }

    /**
     * Test <code>updateUserTypes</code> for accuracy. Condition: normal. Expect: the user statuses are updated
     * properly.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUserTypesAccuracy() throws Exception {
        UserType userType = new UserType();
        userType.setDescription("description");
        userType.setCompanyId(10);
        userType.setCreationUser("test");
        userType.setModificationUser("test");
        userType.setCreationDate(new Date());
        userType.setModificationDate(new Date());
        dbUserTypeDAO.addUserTypes(new UserType[] {userType});

        userType.setCompanyId(15);
        dbUserTypeDAO.updateUserTypes(new UserType[] {userType});

        UserType[] userTypes = dbUserTypeDAO.getUserTypes(new long[] {userType.getId()});

        assertEquals("Returned value is not as expected", 1, userTypes.length);
        assertEquals("Returned description is not as expected", "description", userTypes[0].getDescription());
        assertEquals("Returned company id is not as expected", 15, userTypes[0].getCompanyId());
        assertEquals("Returned creation user is not as expected", "test", userTypes[0].getCreationUser());
        assertEquals("Returned modification user is not as expected", "test", userTypes[0]
            .getModificationUser());
    }

    /**
     * Test <code>updateUserTypes</code> for failure. Condition: userTypes is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUserTypesNull() throws Exception {

        try {
            dbUserTypeDAO.updateUserTypes(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>updateUserTypes</code> for failure. Condition: userTypes is empty. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUserTypesEmpty() throws Exception {

        try {
            dbUserTypeDAO.updateUserTypes(new UserType[0]);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>updateUserTypes</code> for failure. Condition: sore of the user statuses are unrecognized.
     * Expect: <code>BatchOperationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUserTypesUnrecognized() throws Exception {
        UserType userType = new UserType();
        userType.setId(3);
        userType.setDescription("description");
        userType.setCompanyId(10);
        userType.setCreationUser("test");
        userType.setModificationUser("test");
        userType.setCreationDate(new Date());
        userType.setModificationDate(new Date());
        try {
            dbUserTypeDAO.updateUserTypes(new UserType[] {userType});
            fail("Should throw BatchOperationException");
        } catch (BatchOperationException e) {
            if (!(e.getCauses()[0] instanceof UnrecognizedEntityException)) {
                fail("The error should be UnrecognizedEntityException");
            }
        }
    }

    /**
     * Test <code>removeUserTypes</code> for accuracy. Condition: normal. Expect: the user statuses are removed.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUserTypesAccuracy() throws Exception {
        UserType userType = new UserType();
        userType.setDescription("description");
        userType.setCompanyId(10);
        userType.setCreationUser("test");
        userType.setModificationUser("test");
        userType.setCreationDate(new Date());
        userType.setModificationDate(new Date());
        dbUserTypeDAO.addUserTypes(new UserType[] {userType});

        dbUserTypeDAO.removeUserTypes(new long[] {userType.getId()});
        UserType[] userTypes = dbUserTypeDAO.getAllUserTypes();

        assertEquals("Returned value is not as expected", 0, userTypes.length);
    }

    /**
     * Test <code>removeUserTypes</code> for failure. Condition: userTypeIds is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUserTypesNull() throws Exception {
        try {
            dbUserTypeDAO.removeUserTypes(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>removeUserTypes</code> for failure. Condition: userTypeIds is empty. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUserTypesEmpty() throws Exception {
        try {
            dbUserTypeDAO.removeUserTypes(new long[0]);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>removeUserTypes</code> for failure. Condition: some of the user status ids are negative.
     * Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUserTypesNegativeId() throws Exception {
        try {
            dbUserTypeDAO.removeUserTypes(new long[] {-1});
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>removeUserTypes</code> for failure. Condition: some of the user statuses can't be found in the
     * database. Expect: <code>BatchOperationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUserTypesUnrecognized() throws Exception {

        try {
            dbUserTypeDAO.removeUserTypes(new long[] {10});
            fail("Should throw BatchOperationException");
        } catch (BatchOperationException e) {
            if (!(e.getCauses()[0] instanceof UnrecognizedEntityException)) {
                fail("The error should be UnrecognizedEntityException");
            }
        }
    }

    /**
     * Test <code>getUserTypes</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUserTypesAccuracy() throws Exception {
        UserType userType = new UserType();
        userType.setDescription("description");
        userType.setCompanyId(10);
        userType.setCreationUser("test");
        userType.setModificationUser("test");
        userType.setCreationDate(new Date());
        userType.setModificationDate(new Date());
        dbUserTypeDAO.addUserTypes(new UserType[] {userType});

        UserType[] userTypes = dbUserTypeDAO.getUserTypes(new long[] {userType.getId()});

        assertEquals("Returned value is not as expected", 1, userTypes.length);
        assertEquals("Returned description is not as expected", "description", userTypes[0].getDescription());
        assertEquals("Returned company id is not as expected", 10, userTypes[0].getCompanyId());
        assertEquals("Returned creation user is not as expected", "test", userTypes[0].getCreationUser());
        assertEquals("Returned modification user is not as expected", "test", userTypes[0]
            .getModificationUser());
    }

    /**
     * Test <code>getUserTypes</code> for failure. Condition: userTypeIds is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUserTypesNull() throws Exception {
        try {
            dbUserTypeDAO.getUserTypes(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getUserTypes</code> for failure. Condition: userTypeIds is empty. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUserTypesEmpty() throws Exception {
        try {
            dbUserTypeDAO.getUserTypes(new long[0]);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getUserTypes</code> for failure. Condition: some of the user statuses are not in the database.
     * Expect: <code>BatchOperationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUserTypesUnrecognizedEntity() throws Exception {
        try {
            dbUserTypeDAO.getUserTypes(new long[] {10});
            fail("Should throw BatchOperationException");
        } catch (BatchOperationException e) {
            if (!(e.getCauses()[0] instanceof UnrecognizedEntityException)) {
                fail("The error should be UnrecognizedEntityException");
            }
        }
    }

    /**
     * Test <code>searchUserTypes</code> for accuracy. Condition: normal. Expect: the result should be empty.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchUserTypesAccuracy1() throws Exception {
        UserType[] userTypes = dbUserTypeDAO.searchUserTypes(UserTypeFilterFactory.createCompanyIdFilter(10));

        assertEquals("Returned value is not as expected", 0, userTypes.length);
    }

    /**
     * Test <code>searchUserTypes</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchUserTypesAccuracy2() throws Exception {
        UserType userType = new UserType();
        userType.setDescription("description");
        userType.setCompanyId(10);
        userType.setCreationUser("test");
        userType.setModificationUser("test");
        userType.setCreationDate(new Date());
        userType.setModificationDate(new Date());
        dbUserTypeDAO.addUserTypes(new UserType[] {userType});

        UserType[] userTypes = dbUserTypeDAO.searchUserTypes(UserTypeFilterFactory.createCompanyIdFilter(10));

        assertEquals("Returned value is not as expected", 1, userTypes.length);
        assertEquals("Returned description is not as expected", "description", userTypes[0].getDescription());
        assertEquals("Returned company id is not as expected", 10, userTypes[0].getCompanyId());
        assertEquals("Returned creation user is not as expected", "test", userTypes[0].getCreationUser());
        assertEquals("Returned modification user is not as expected", "test", userTypes[0]
            .getModificationUser());
    }

    /**
     * Test <code>searchUserTypes</code> for failure. Condition: the filter is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchUserTypesNull() throws Exception {
        try {
            dbUserTypeDAO.searchUserTypes(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>searchUserTypes</code> for failure. Condition: the filter is wrong. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchUserTypesWrongFilter() throws Exception {
        try {
            dbUserTypeDAO.searchUserTypes(new EqualToFilter("no_column", "value"));
            fail("Should throw DataAccessException");
        } catch (DataAccessException e) {
            // expected
        }
    }

    /**
     * Test <code>getAllUserTypes</code> for accuracy. Condition: normal. Expect: there is no user status.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllUserTypesAccuracy1() throws Exception {
        UserType[] userTypes = dbUserTypeDAO.getAllUserTypes();

        assertEquals("Returned value is not as expected", 0, userTypes.length);
    }

    /**
     * Test <code>getAllUserTypes</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllUserTypesAccuracy2() throws Exception {
        UserType userType = new UserType();
        userType.setDescription("description");
        userType.setCompanyId(10);
        userType.setCreationUser("test");
        userType.setModificationUser("test");
        userType.setCreationDate(new Date());
        userType.setModificationDate(new Date());
        dbUserTypeDAO.addUserTypes(new UserType[] {userType});

        UserType[] userTypes = dbUserTypeDAO.getAllUserTypes();

        assertEquals("Returned value is not as expected", 1, userTypes.length);

        assertEquals("Returned description is not as expected", "description", userTypes[0].getDescription());
        assertEquals("Returned company id is not as expected", 10, userTypes[0].getCompanyId());
        assertEquals("Returned creation user is not as expected", "test", userTypes[0].getCreationUser());
        assertEquals("Returned modification user is not as expected", "test", userTypes[0]
            .getModificationUser());
    }

}
