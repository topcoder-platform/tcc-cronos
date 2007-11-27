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
import com.topcoder.timetracker.user.UserStatus;
import com.topcoder.timetracker.user.filterfactory.UserStatusFilterFactory;
import com.topcoder.util.idgenerator.IDGenerationException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for <code>UserStatusManagerDelegate</code>.
 *
 * @author enefem21
 * @version 3.2.1
 * @since 3.2.1
 */
public class UserStatusManagerDelegateTest extends TestCase {

    /** Unit under test. */
    private UserStatusManagerDelegate userStatusManagerDelegate;

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
        return new TestSuite(UserStatusManagerDelegateTest.class);
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
        SessionBeanDescriptor userStatusServiceDescriptor =
            new SessionBeanDescriptor("UserStatusManagerLocalHome", UserStatusManagerLocalHome.class,
                UserStatusManagerLocal.class, new UserStatusManagerSessionBean());

        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(userStatusServiceDescriptor);

        userStatusManagerDelegate = new UserStatusManagerDelegate(UserStatusManagerDelegate.class.getName());

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
     * Test <code>createUserStatus</code> for accuracy. Condition: normal. Expect: the user status is created
     * properly.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateUserStatusAccuracy() throws Exception {
        UserStatus userStatus = new UserStatus();
        userStatus.setDescription("description");
        userStatus.setCompanyId(10);
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());

        userStatusManagerDelegate.createUserStatus(userStatus);

        userStatus = userStatusManagerDelegate.getUserStatus(userStatus.getId());

        assertEquals("Returned description is not as expected", "description", userStatus.getDescription());
        assertEquals("Returned company id is not as expected", 10, userStatus.getCompanyId());
        assertEquals("Returned creation user is not as expected", "test", userStatus.getCreationUser());
        assertEquals("Returned modification user is not as expected", "test", userStatus
            .getModificationUser());
    }

    /**
     * Test <code>createUserStatus</code> for failure. Condition: userStatus is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateUserStatusNull() throws Exception {
        try {
            userStatusManagerDelegate.createUserStatus(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>createUserStatus</code> for failure. Condition: userStatus is not valid. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateUserStatusNotValid1() throws Exception {
        UserStatus userStatus = new UserStatus();
        userStatus.setDescription("description");
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());
        try {
            userStatusManagerDelegate.createUserStatus(userStatus);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>createUserStatus</code> for failure. Condition: userStatus is not valid. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateUserStatusNotValid2() throws Exception {
        UserStatus userStatus = new UserStatus();
        userStatus.setCompanyId(10);
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());
        try {
            userStatusManagerDelegate.createUserStatus(userStatus);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>createUserStatus</code> for failure. Condition: userStatus is not valid. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateUserStatusNotValid3() throws Exception {
        UserStatus userStatus = new UserStatus();
        userStatus.setId(5);
        userStatus.setDescription("description");
        userStatus.setCompanyId(10);
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());
        try {
            userStatusManagerDelegate.createUserStatus(userStatus);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>createUserStatus</code> for failure. Condition: id generator is not valid. Expect:
     * <code>DataAccessException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateUserStatusIdGenError() throws Exception {
        Connection conn = dbFactory.createConnection();
        Statement statement = conn.createStatement();
        statement.execute("delete from id_sequences");
        statement.close();
        conn.close();

        UserStatus userStatus = new UserStatus();
        userStatus.setDescription("description");
        userStatus.setCompanyId(10);
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());
        try {
            userStatusManagerDelegate.createUserStatus(userStatus);
            fail("Should throw DataAccessException");
        } catch (DataAccessException e) {
            // expected
        }
    }

    /**
     * Test <code>updateUserStatus</code> for accuracy. Condition: normal. Expect: the user status is updated
     * properly.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUserStatusAccuracy() throws Exception {
        UserStatus userStatus = new UserStatus();
        userStatus.setDescription("description");
        userStatus.setCompanyId(10);
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());

        userStatusManagerDelegate.createUserStatus(userStatus);

        userStatus.setCompanyId(5);
        userStatusManagerDelegate.updateUserStatus(userStatus);

        userStatus = userStatusManagerDelegate.getUserStatus(userStatus.getId());

        assertEquals("Returned description is not as expected", "description", userStatus.getDescription());
        assertEquals("Returned company id is not as expected", 5, userStatus.getCompanyId());
        assertEquals("Returned creation user is not as expected", "test", userStatus.getCreationUser());
        assertEquals("Returned modification user is not as expected", "test", userStatus
            .getModificationUser());
    }

    /**
     * Test <code>updateUserStatus</code> for failure. Condition: userStatus is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUserStatusNull() throws Exception {
        try {
            userStatusManagerDelegate.updateUserStatus(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>updateUserStatus</code> for failure. Condition: userStatus id is negative. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUserStatusNegative() throws Exception {
        UserStatus userStatus = new UserStatus();
        userStatus.setDescription("description");
        userStatus.setCompanyId(10);
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());

        try {
            userStatusManagerDelegate.updateUserStatus(userStatus);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>updateUserStatus</code> for failure. Condition: userStatus is unrecognized. Expect:
     * <code>UnrecognizedEntityException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUserStatusUnrecognized() throws Exception {
        UserStatus userStatus = new UserStatus();
        userStatus.setId(5);
        userStatus.setDescription("description");
        userStatus.setCompanyId(10);
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());

        try {
            userStatusManagerDelegate.updateUserStatus(userStatus);
            fail("Should throw UnrecognizedEntityException");
        } catch (UnrecognizedEntityException e) {
            // expected
        }
    }

    /**
     * Test <code>removeUserStatus</code> for accuracy. Condition: normal. Expect: the user status is removed
     * properly.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUserStatusAccuracy() throws Exception {
        UserStatus userStatus = new UserStatus();
        userStatus.setDescription("description");
        userStatus.setCompanyId(10);
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());

        userStatusManagerDelegate.createUserStatus(userStatus);

        userStatusManagerDelegate.removeUserStatus(userStatus.getId());

        UserStatus[] userStatuses = userStatusManagerDelegate.getAllUserStatuses();
        assertEquals("Returned value is not as expected", 0, userStatuses.length);
    }

    /**
     * Test <code>removeUserStatus</code> for failure. Condition: userStatusId is negative. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUserStatusNegative() throws Exception {
        try {
            userStatusManagerDelegate.removeUserStatus(-5);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>removeUserStatus</code> for failure. Condition: userStatusId is unrecognized. Expect:
     * <code>UnrecognizedEntityException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUserStatusUnrecognized() throws Exception {
        try {
            userStatusManagerDelegate.removeUserStatus(5);
            fail("Should throw UnrecognizedEntityException");
        } catch (UnrecognizedEntityException e) {
            // expected
        }
    }

    /**
     * Test <code>getUserStatus</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUserStatusAccuracy() throws Exception {
        UserStatus userStatus = new UserStatus();
        userStatus.setDescription("description");
        userStatus.setCompanyId(10);
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());

        userStatusManagerDelegate.createUserStatus(userStatus);

        userStatus = userStatusManagerDelegate.getUserStatus(userStatus.getId());

        assertEquals("Returned description is not as expected", "description", userStatus.getDescription());
        assertEquals("Returned company id is not as expected", 10, userStatus.getCompanyId());
        assertEquals("Returned creation user is not as expected", "test", userStatus.getCreationUser());
        assertEquals("Returned modification user is not as expected", "test", userStatus
            .getModificationUser());
    }

    /**
     * Test <code>getUserStatus</code> for failure. Condition: userStatusId is negative. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUserStatusNegative() throws Exception {
        try {
            userStatusManagerDelegate.getUserStatus(-5);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getUserStatus</code> for failure. Condition: userStatusId is unrecognized. Expect:
     * <code>UnrecognizedEntityException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUserStatusUnrecognized() throws Exception {
        try {
            userStatusManagerDelegate.getUserStatus(5);
            fail("Should throw UnrecognizedEntityException");
        } catch (UnrecognizedEntityException e) {
            // expected
        }
    }

    /**
     * Test <code>addUserStatuses</code> for accuracy. Condition: normal. Expect: the user statuses are added
     * properly.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUserStatusesAccuracy() throws Exception {
        UserStatus userStatus = new UserStatus();
        userStatus.setDescription("description");
        userStatus.setCompanyId(10);
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());
        userStatusManagerDelegate.addUserStatuses(new UserStatus[] {userStatus});

        UserStatus[] userStatuses =
            userStatusManagerDelegate.getUserStatuses(new long[] {userStatus.getId()});

        assertEquals("Returned value is not as expected", 1, userStatuses.length);
        assertEquals("Returned description is not as expected", "description", userStatuses[0]
            .getDescription());
        assertEquals("Returned company id is not as expected", 10, userStatuses[0].getCompanyId());
        assertEquals("Returned creation user is not as expected", "test", userStatuses[0].getCreationUser());
        assertEquals("Returned modification user is not as expected", "test", userStatuses[0]
            .getModificationUser());
    }

    /**
     * Test <code>addUserStatuses</code> for failure. Condition: userStatuses is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUserStatusesNull() throws Exception {
        try {
            userStatusManagerDelegate.addUserStatuses(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>addUserStatuses</code> for failure. Condition: userStatuses is empty. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUserStatusesEmpty() throws Exception {
        try {
            userStatusManagerDelegate.addUserStatuses(new UserStatus[0]);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>addUserStatuses</code> for failure. Condition: some of userStatuses are not valid. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUserStatusesMissingDescription() throws Exception {
        UserStatus userStatus = new UserStatus();
        userStatus.setCompanyId(10);
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());
        try {
            userStatusManagerDelegate.addUserStatuses(new UserStatus[] {userStatus});
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>addUserStatuses</code> for failure. Condition: some of userStatuses are not valid. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUserStatusesMissingCompanyId() throws Exception {
        UserStatus userStatus = new UserStatus();
        userStatus.setDescription("description");
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());
        try {
            userStatusManagerDelegate.addUserStatuses(new UserStatus[] {userStatus});
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>addUserStatuses</code> for failure. Condition: some of userStatuses are already added. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUserStatusesAlreadyAdded() throws Exception {
        UserStatus userStatus = new UserStatus();
        userStatus.setDescription("description");
        userStatus.setCompanyId(10);
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());
        userStatusManagerDelegate.addUserStatuses(new UserStatus[] {userStatus});

        try {
            userStatusManagerDelegate.addUserStatuses(new UserStatus[] {userStatus});
            fail("Should throw BatchOperationException");
        } catch (IllegalArgumentException e) {
            // expected
        }

    }

    /**
     * Test <code>addUserStatuses</code> for failure. Condition: id generation is not working correctly. Expect:
     * <code>BatchOperationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUserStatusesIdGenerationTableNotPrepared() throws Exception {
        Connection conn = dbFactory.createConnection();
        Statement statement = conn.createStatement();
        statement.execute("delete from id_sequences");
        statement.close();
        conn.close();

        UserStatus userStatus = new UserStatus();
        userStatus.setDescription("description");
        userStatus.setCompanyId(10);
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());

        try {
            userStatusManagerDelegate.addUserStatuses(new UserStatus[] {userStatus});
            fail("Should throw BatchOperationException");
        } catch (BatchOperationException e) {
            if (!(e.getCauses()[0] instanceof IDGenerationException)) {
                fail("The error should be IDGenerationException");
            }
        }

    }

    /**
     * Test <code>updateUserStatuses</code> for accuracy. Condition: normal. Expect: the user statuses are
     * updated properly.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUserStatusesAccuracy() throws Exception {
        UserStatus userStatus = new UserStatus();
        userStatus.setDescription("description");
        userStatus.setCompanyId(10);
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());
        userStatusManagerDelegate.addUserStatuses(new UserStatus[] {userStatus});

        userStatus.setCompanyId(15);
        userStatusManagerDelegate.updateUserStatuses(new UserStatus[] {userStatus});

        UserStatus[] userStatuses =
            userStatusManagerDelegate.getUserStatuses(new long[] {userStatus.getId()});

        assertEquals("Returned value is not as expected", 1, userStatuses.length);
        assertEquals("Returned description is not as expected", "description", userStatuses[0]
            .getDescription());
        assertEquals("Returned company id is not as expected", 15, userStatuses[0].getCompanyId());
        assertEquals("Returned creation user is not as expected", "test", userStatuses[0].getCreationUser());
        assertEquals("Returned modification user is not as expected", "test", userStatuses[0]
            .getModificationUser());
    }

    /**
     * Test <code>updateUserStatuses</code> for failure. Condition: userStatuses is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUserStatusesNull() throws Exception {

        try {
            userStatusManagerDelegate.updateUserStatuses(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>updateUserStatuses</code> for failure. Condition: userStatuses is empty. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUserStatusesEmpty() throws Exception {

        try {
            userStatusManagerDelegate.updateUserStatuses(new UserStatus[0]);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>updateUserStatuses</code> for failure. Condition: sore of the user statuses are unrecognized.
     * Expect: <code>BatchOperationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUserStatusesUnrecognized() throws Exception {
        UserStatus userStatus = new UserStatus();
        userStatus.setId(4);
        userStatus.setDescription("description");
        userStatus.setCompanyId(10);
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());
        try {
            userStatusManagerDelegate.updateUserStatuses(new UserStatus[] {userStatus});
            fail("Should throw BatchOperationException");
        } catch (BatchOperationException e) {
            if (!(e.getCauses()[0] instanceof UnrecognizedEntityException)) {
                fail("The error should be UnrecognizedEntityException");
            }
        }
    }

    /**
     * Test <code>removeUserStatuses</code> for accuracy. Condition: normal. Expect: the user statuses are
     * removed.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUserStatusesAccuracy() throws Exception {
        UserStatus userStatus = new UserStatus();
        userStatus.setDescription("description");
        userStatus.setCompanyId(10);
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());
        userStatusManagerDelegate.addUserStatuses(new UserStatus[] {userStatus});

        userStatusManagerDelegate.removeUserStatuses(new long[] {userStatus.getId()});
        UserStatus[] userStatuses = userStatusManagerDelegate.getAllUserStatuses();

        assertEquals("Returned value is not as expected", 0, userStatuses.length);
    }

    /**
     * Test <code>removeUserStatuses</code> for failure. Condition: userStatusIds is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUserStatusesNull() throws Exception {
        try {
            userStatusManagerDelegate.removeUserStatuses(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>removeUserStatuses</code> for failure. Condition: userStatusIds is empty. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUserStatusesEmpty() throws Exception {
        try {
            userStatusManagerDelegate.removeUserStatuses(new long[0]);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>removeUserStatuses</code> for failure. Condition: some of the user status ids are negative.
     * Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUserStatusesNegativeId() throws Exception {
        try {
            userStatusManagerDelegate.removeUserStatuses(new long[] {-1});
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>removeUserStatuses</code> for failure. Condition: some of the user statuses can't be found in
     * the database. Expect: <code>BatchOperationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUserStatusesUnrecognized() throws Exception {

        try {
            userStatusManagerDelegate.removeUserStatuses(new long[] {10});
            fail("Should throw BatchOperationException");
        } catch (BatchOperationException e) {
            if (!(e.getCauses()[0] instanceof UnrecognizedEntityException)) {
                fail("The error should be UnrecognizedEntityException");
            }
        }
    }

    /**
     * Test <code>getUserStatuses</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUserStatusesAccuracy() throws Exception {
        UserStatus userStatus = new UserStatus();
        userStatus.setDescription("description");
        userStatus.setCompanyId(10);
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());
        userStatusManagerDelegate.addUserStatuses(new UserStatus[] {userStatus});

        UserStatus[] userStatuses =
            userStatusManagerDelegate.getUserStatuses(new long[] {userStatus.getId()});

        assertEquals("Returned value is not as expected", 1, userStatuses.length);
        assertEquals("Returned description is not as expected", "description", userStatuses[0]
            .getDescription());
        assertEquals("Returned company id is not as expected", 10, userStatuses[0].getCompanyId());
        assertEquals("Returned creation user is not as expected", "test", userStatuses[0].getCreationUser());
        assertEquals("Returned modification user is not as expected", "test", userStatuses[0]
            .getModificationUser());
    }

    /**
     * Test <code>getUserStatuses</code> for failure. Condition: userStatusIds is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUserStatusesNull() throws Exception {
        try {
            userStatusManagerDelegate.getUserStatuses(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getUserStatuses</code> for failure. Condition: userStatusIds is empty. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUserStatusesEmpty() throws Exception {
        try {
            userStatusManagerDelegate.getUserStatuses(new long[0]);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getUserStatuses</code> for failure. Condition: some of the user statuses are not in the
     * database. Expect: <code>BatchOperationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUserStatusesUnrecognizedEntity() throws Exception {
        try {
            userStatusManagerDelegate.getUserStatuses(new long[] {10});
            fail("Should throw BatchOperationException");
        } catch (BatchOperationException e) {
            if (!(e.getCauses()[0] instanceof UnrecognizedEntityException)) {
                fail("The error should be UnrecognizedEntityException");
            }
        }
    }

    /**
     * Test <code>searchUserStatuses</code> for accuracy. Condition: normal. Expect: the result should be empty.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchUserStatusesAccuracy1() throws Exception {
        UserStatus[] userStatuses =
            userStatusManagerDelegate.searchUserStatuses(UserStatusFilterFactory.createCompanyIdFilter(10));

        assertEquals("Returned value is not as expected", 0, userStatuses.length);
    }

    /**
     * Test <code>searchUserStatuses</code> for accuracy. Condition: normal. Expect: returned value is as
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchUserStatusesAccuracy2() throws Exception {
        UserStatus userStatus = new UserStatus();
        userStatus.setDescription("description");
        userStatus.setCompanyId(10);
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());
        userStatusManagerDelegate.addUserStatuses(new UserStatus[] {userStatus});

        UserStatus[] userStatuses =
            userStatusManagerDelegate.searchUserStatuses(UserStatusFilterFactory.createCompanyIdFilter(10));

        assertEquals("Returned value is not as expected", 1, userStatuses.length);
        assertEquals("Returned description is not as expected", "description", userStatuses[0]
            .getDescription());
        assertEquals("Returned company id is not as expected", 10, userStatuses[0].getCompanyId());
        assertEquals("Returned creation user is not as expected", "test", userStatuses[0].getCreationUser());
        assertEquals("Returned modification user is not as expected", "test", userStatuses[0]
            .getModificationUser());
    }

    /**
     * Test <code>searchUserStatuses</code> for failure. Condition: the filter is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchUserStatusesNull() throws Exception {
        try {
            userStatusManagerDelegate.searchUserStatuses(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>searchUserStatuses</code> for failure. Condition: the filter is wrong. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchUserStatusesWrongFilter() throws Exception {
        try {
            userStatusManagerDelegate.searchUserStatuses(new EqualToFilter("no_column", "value"));
            fail("Should throw DataAccessException");
        } catch (DataAccessException e) {
            // expected
        }
    }

    /**
     * Test <code>getAllUserStatuses</code> for accuracy. Condition: normal. Expect: there is no user status.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllUserStatusesAccuracy1() throws Exception {
        UserStatus[] userStatuses = userStatusManagerDelegate.getAllUserStatuses();

        assertEquals("Returned value is not as expected", 0, userStatuses.length);
    }

    /**
     * Test <code>getAllUserStatuses</code> for accuracy. Condition: normal. Expect: returned value is as
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllUserStatusesAccuracy2() throws Exception {
        UserStatus userStatus = new UserStatus();
        userStatus.setDescription("description");
        userStatus.setCompanyId(10);
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());
        userStatusManagerDelegate.addUserStatuses(new UserStatus[] {userStatus});

        UserStatus[] userStatuses = userStatusManagerDelegate.getAllUserStatuses();

        assertEquals("Returned value is not as expected", 1, userStatuses.length);

        assertEquals("Returned description is not as expected", "description", userStatuses[0]
            .getDescription());
        assertEquals("Returned company id is not as expected", 10, userStatuses[0].getCompanyId());
        assertEquals("Returned creation user is not as expected", "test", userStatuses[0].getCreationUser());
        assertEquals("Returned modification user is not as expected", "test", userStatuses[0]
            .getModificationUser());
    }

}
