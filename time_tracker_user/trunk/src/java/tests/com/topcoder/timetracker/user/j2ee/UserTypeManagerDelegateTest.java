/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.j2ee;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

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
 * Unit test for <code>UserTypeManagerDelegate</code>.
 *
 * @author enefem21
 * @version 3.2.1
 * @since 3.2.1
 */
public class UserTypeManagerDelegateTest extends TestCase {

    /** Unit under test. */
    private UserTypeManagerDelegate userTypeManagerDelegate;

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
        return new TestSuite(UserTypeManagerDelegateTest.class);
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

        // we need to set MockContextFactory as our JNDI provider. This method sets the necessary system
        // properties.
        MockContextFactory.setAsInitial();

        // creates the initial context that will be used for binding EJBs
        Context context = new InitialContext();

        // creates an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        // we use MockTransaction outside of the app server
        MockUserTransaction mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);

        // creates deployment descriptor of our sample bean. MockEjb uses it instead of XML deployment descriptors
        SessionBeanDescriptor userTypeServiceDescriptor =
            new SessionBeanDescriptor("UserTypeManagerLocalHome", UserTypeManagerLocalHome.class,
                UserTypeManagerLocal.class, new UserTypeManagerSessionBean());

        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(userTypeServiceDescriptor);

        userTypeManagerDelegate = new UserTypeManagerDelegate(UserTypeManagerDelegate.class.getName());
    }

    /**
     * Tears the unit test down.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {

        TestHelper.tearDownDataBase();
        TestHelper.clearConfig();
        MockContextFactory.revertSetAsInitial();

        super.tearDown();
    }

    /**
     * Test <code>createUserType</code> for accuracy. Condition: normal. Expect: the user status is created
     * properly.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateUserTypeAccuracy() throws Exception {
        UserType userType = new UserType();
        userType.setDescription("description");
        userType.setCompanyId(10);
        userType.setCreationUser("test");
        userType.setModificationUser("test");
        userType.setCreationDate(new Date());
        userType.setModificationDate(new Date());

        userTypeManagerDelegate.createUserType(userType);

        userType = userTypeManagerDelegate.getUserType(userType.getId());

        assertEquals("Returned description is not as expected", "description", userType.getDescription());
        assertEquals("Returned company id is not as expected", 10, userType.getCompanyId());
        assertEquals("Returned creation user is not as expected", "test", userType.getCreationUser());
        assertEquals("Returned modification user is not as expected", "test", userType.getModificationUser());
    }

    /**
     * Test <code>createUserType</code> for failure. Condition: userType is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateUserTypeNull() throws Exception {
        try {
            userTypeManagerDelegate.createUserType(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>createUserType</code> for failure. Condition: userType is not valid. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateUserTypeNotValid1() throws Exception {
        UserType userType = new UserType();
        userType.setDescription("description");
        userType.setCreationUser("test");
        userType.setModificationUser("test");
        userType.setCreationDate(new Date());
        userType.setModificationDate(new Date());
        try {
            userTypeManagerDelegate.createUserType(userType);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>createUserType</code> for failure. Condition: userType is not valid. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateUserTypeNotValid2() throws Exception {
        UserType userType = new UserType();
        userType.setCompanyId(10);
        userType.setCreationUser("test");
        userType.setModificationUser("test");
        userType.setCreationDate(new Date());
        userType.setModificationDate(new Date());
        try {
            userTypeManagerDelegate.createUserType(userType);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>createUserType</code> for failure. Condition: userType is not valid. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateUserTypeNotValid3() throws Exception {
        UserType userType = new UserType();
        userType.setId(5);
        userType.setDescription("description");
        userType.setCompanyId(10);
        userType.setCreationUser("test");
        userType.setModificationUser("test");
        userType.setCreationDate(new Date());
        userType.setModificationDate(new Date());
        try {
            userTypeManagerDelegate.createUserType(userType);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>createUserType</code> for failure. Condition: id generator is not valid. Expect:
     * <code>DataAccessException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateUserTypeIdGenError() throws Exception {
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
            userTypeManagerDelegate.createUserType(userType);
            fail("Should throw DataAccessException");
        } catch (DataAccessException e) {
            // expected
        }
    }

    /**
     * Test <code>updateUserType</code> for accuracy. Condition: normal. Expect: the user status is updated
     * properly.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUserTypeAccuracy() throws Exception {
        UserType userType = new UserType();
        userType.setDescription("description");
        userType.setCompanyId(10);
        userType.setCreationUser("test");
        userType.setModificationUser("test");
        userType.setCreationDate(new Date());
        userType.setModificationDate(new Date());

        userTypeManagerDelegate.createUserType(userType);

        userType.setCompanyId(5);
        userTypeManagerDelegate.updateUserType(userType);

        userType = userTypeManagerDelegate.getUserType(userType.getId());

        assertEquals("Returned description is not as expected", "description", userType.getDescription());
        assertEquals("Returned company id is not as expected", 5, userType.getCompanyId());
        assertEquals("Returned creation user is not as expected", "test", userType.getCreationUser());
        assertEquals("Returned modification user is not as expected", "test", userType.getModificationUser());
    }

    /**
     * Test <code>updateUserType</code> for failure. Condition: userType is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUserTypeNull() throws Exception {
        try {
            userTypeManagerDelegate.updateUserType(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>updateUserType</code> for failure. Condition: userType id is negative. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUserTypeNegative() throws Exception {
        UserType userType = new UserType();
        userType.setDescription("description");
        userType.setCompanyId(10);
        userType.setCreationUser("test");
        userType.setModificationUser("test");
        userType.setCreationDate(new Date());
        userType.setModificationDate(new Date());

        try {
            userTypeManagerDelegate.updateUserType(userType);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>updateUserType</code> for failure. Condition: userType is unrecognized. Expect:
     * <code>UnrecognizedEntityException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUserTypeUnrecognized() throws Exception {
        UserType userType = new UserType();
        userType.setId(5);
        userType.setDescription("description");
        userType.setCompanyId(10);
        userType.setCreationUser("test");
        userType.setModificationUser("test");
        userType.setCreationDate(new Date());
        userType.setModificationDate(new Date());

        try {
            userTypeManagerDelegate.updateUserType(userType);
            fail("Should throw UnrecognizedEntityException");
        } catch (UnrecognizedEntityException e) {
            // expected
        }
    }

    /**
     * Test <code>removeUserType</code> for accuracy. Condition: normal. Expect: the user status is removed
     * properly.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUserTypeAccuracy() throws Exception {
        UserType userType = new UserType();
        userType.setDescription("description");
        userType.setCompanyId(10);
        userType.setCreationUser("test");
        userType.setModificationUser("test");
        userType.setCreationDate(new Date());
        userType.setModificationDate(new Date());

        userTypeManagerDelegate.createUserType(userType);

        userTypeManagerDelegate.removeUserType(userType.getId());

        UserType[] userTypes = userTypeManagerDelegate.getAllUserTypes();
        assertEquals("Returned value is not as expected", 0, userTypes.length);
    }

    /**
     * Test <code>removeUserType</code> for failure. Condition: userTypeId is negative. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUserTypeNegative() throws Exception {
        try {
            userTypeManagerDelegate.removeUserType(-5);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>removeUserType</code> for failure. Condition: userTypeId is unrecognized. Expect:
     * <code>UnrecognizedEntityException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUserTypeUnrecognized() throws Exception {
        try {
            userTypeManagerDelegate.removeUserType(5);
            fail("Should throw UnrecognizedEntityException");
        } catch (UnrecognizedEntityException e) {
            // expected
        }
    }

    /**
     * Test <code>getUserType</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUserTypeAccuracy() throws Exception {
        UserType userType = new UserType();
        userType.setDescription("description");
        userType.setCompanyId(10);
        userType.setCreationUser("test");
        userType.setModificationUser("test");
        userType.setCreationDate(new Date());
        userType.setModificationDate(new Date());

        userTypeManagerDelegate.createUserType(userType);

        userType = userTypeManagerDelegate.getUserType(userType.getId());

        assertEquals("Returned description is not as expected", "description", userType.getDescription());
        assertEquals("Returned company id is not as expected", 10, userType.getCompanyId());
        assertEquals("Returned creation user is not as expected", "test", userType.getCreationUser());
        assertEquals("Returned modification user is not as expected", "test", userType.getModificationUser());
    }

    /**
     * Test <code>getUserType</code> for failure. Condition: userTypeId is negative. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUserTypeNegative() throws Exception {
        try {
            userTypeManagerDelegate.getUserType(-5);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getUserType</code> for failure. Condition: userTypeId is unrecognized. Expect:
     * <code>UnrecognizedEntityException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUserTypeUnrecognized() throws Exception {
        try {
            userTypeManagerDelegate.getUserType(5);
            fail("Should throw UnrecognizedEntityException");
        } catch (UnrecognizedEntityException e) {
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
        userTypeManagerDelegate.addUserTypes(new UserType[] {userType});

        UserType[] userTypes = userTypeManagerDelegate.getUserTypes(new long[] {userType.getId()});

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
            userTypeManagerDelegate.addUserTypes(null);
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
            userTypeManagerDelegate.addUserTypes(new UserType[0]);
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
            userTypeManagerDelegate.addUserTypes(new UserType[] {userType});
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
            userTypeManagerDelegate.addUserTypes(new UserType[] {userType});
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
        userTypeManagerDelegate.addUserTypes(new UserType[] {userType});

        try {
            userTypeManagerDelegate.addUserTypes(new UserType[] {userType});
            fail("Should throw BatchOperationException");
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
            userTypeManagerDelegate.addUserTypes(new UserType[] {userType});
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
        userTypeManagerDelegate.addUserTypes(new UserType[] {userType});

        userType.setCompanyId(15);
        userTypeManagerDelegate.updateUserTypes(new UserType[] {userType});

        UserType[] userTypes = userTypeManagerDelegate.getUserTypes(new long[] {userType.getId()});

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
            userTypeManagerDelegate.updateUserTypes(null);
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
            userTypeManagerDelegate.updateUserTypes(new UserType[0]);
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
        userType.setId(4);
        userType.setDescription("description");
        userType.setCompanyId(10);
        userType.setCreationUser("test");
        userType.setModificationUser("test");
        userType.setCreationDate(new Date());
        userType.setModificationDate(new Date());
        try {
            userTypeManagerDelegate.updateUserTypes(new UserType[] {userType});
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
        userTypeManagerDelegate.addUserTypes(new UserType[] {userType});

        userTypeManagerDelegate.removeUserTypes(new long[] {userType.getId()});
        UserType[] userTypes = userTypeManagerDelegate.getAllUserTypes();

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
            userTypeManagerDelegate.removeUserTypes(null);
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
            userTypeManagerDelegate.removeUserTypes(new long[0]);
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
            userTypeManagerDelegate.removeUserTypes(new long[] {-1});
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
            userTypeManagerDelegate.removeUserTypes(new long[] {10});
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
        userTypeManagerDelegate.addUserTypes(new UserType[] {userType});

        UserType[] userTypes = userTypeManagerDelegate.getUserTypes(new long[] {userType.getId()});

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
            userTypeManagerDelegate.getUserTypes(null);
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
            userTypeManagerDelegate.getUserTypes(new long[0]);
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
            userTypeManagerDelegate.getUserTypes(new long[] {10});
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
        UserType[] userTypes =
            userTypeManagerDelegate.searchUserTypes(UserTypeFilterFactory.createCompanyIdFilter(10));

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
        userTypeManagerDelegate.addUserTypes(new UserType[] {userType});

        UserType[] userTypes =
            userTypeManagerDelegate.searchUserTypes(UserTypeFilterFactory.createCompanyIdFilter(10));

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
            userTypeManagerDelegate.searchUserTypes(null);
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
            userTypeManagerDelegate.searchUserTypes(new EqualToFilter("no_column", "value"));
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
        UserType[] userTypes = userTypeManagerDelegate.getAllUserTypes();

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
        userTypeManagerDelegate.addUserTypes(new UserType[] {userType});

        UserType[] userTypes = userTypeManagerDelegate.getAllUserTypes();

        assertEquals("Returned value is not as expected", 1, userTypes.length);

        assertEquals("Returned description is not as expected", "description", userTypes[0].getDescription());
        assertEquals("Returned company id is not as expected", 10, userTypes[0].getCompanyId());
        assertEquals("Returned creation user is not as expected", "test", userTypes[0].getCreationUser());
        assertEquals("Returned modification user is not as expected", "test", userTypes[0]
            .getModificationUser());
    }

}
