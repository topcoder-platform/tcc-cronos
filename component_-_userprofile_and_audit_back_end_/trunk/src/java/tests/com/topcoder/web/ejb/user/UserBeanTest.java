/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.ejb.user;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.ejb.EJBException;

import junit.framework.TestCase;

import com.topcoder.shared.util.DBMS;
import com.topcoder.web.common.RowNotFoundException;

/**
 * <p>
 * Unit tests for {@link UserBean} class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UserBeanTest extends TestCase {
    /**
     * A string representing JNDI name of DataSource to be used.
     */
    private static final String DS = "userds";

    /**
     * A UserBean instance used for testing.
     */
    private UserBean instance;

    /**
     * Sets up the environment.
     * @throws Exception
     *             to JUnit
     */
    public void setUp() throws Exception {
        instance = new UserBean();
    }

    /**
     * Tears down the test environment.
     */
    public void tearDown() {
        instance = null;
    }

    /**
     * Accuracy test case for {@link UserBean#UserBean()} constructor. Checks if the created instance is not null.
     */
    public void testUserBean() {
        assertNotNull("Error instantiating the UserBean", instance);
    }

    /**
     * Accuracy test case for {@link UserBean#createNewUser(String, char, String, String)} method. Expects a proper
     * generated user_id.
     * @throws Exception
     *             to JUnit
     */
    public void testCreateNewUser() throws Exception {
        String handle = "handle";
        long id = instance.createNewUser(handle, "A".toCharArray()[0], DS, DS);

        // get the handle persisted into database
        assertTrue("Error creating new user", handle.equals(instance.getHandle(id, DS)));

        // delete the user created
        deleteUser(id);
    }

    /**
     * Failure test case for {@link UserBean#createNewUser(String, char, String, String)} method. Passes a null handle
     * and expects an Exception.
     * @throws Exception
     *             to JUnit
     */
    public void testCreateNewUserFail() throws Exception {
        try {
            instance.createNewUser(null, "A".toCharArray()[0], DS, DS);
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Failure test case for {@link UserBean#createNewUser(String, char, String, String)} method. Passes a null
     * DataSource and expects an Exception.
     * @throws Exception
     *             to JUnit
     */
    public void testCreateNewUserFail1() throws Exception {
        try {
            instance.createNewUser("handle", "A".toCharArray()[0], null, DS);
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Failure test case for {@link UserBean#createNewUser(String, char, String, String)} method. Passes an empty handle
     * and expects an Exception.
     * @throws Exception
     *             to JUnit
     */
    public void testCreateNewUserFail2() throws Exception {
        try {
            instance.createNewUser("   ", "A".toCharArray()[0], DS, DS);
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Failure test case for {@link UserBean#createNewUser(String, char, String, String)} method. Passes an empty
     * datasource and expects an Exception.
     * @throws Exception
     *             to JUnit
     */
    public void testCreateNewUserFail3() throws Exception {
        try {
            instance.createNewUser("handle", "A".toCharArray()[0], "  ", DS);
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Accuracy test case for {@link UserBean#createUser(long, String, char, String)} method. Expects the user record to
     * be persisted into database.
     * @throws Exception
     *             to JUnit
     */
    public void testCreateUser1() throws Exception {
        String handle = "handle";
        instance.createUser(100l, handle, "A".toCharArray()[0], DS);

        // get the handle persisted into database
        assertTrue("Error creating new user", handle.equals(instance.getHandle(100l, DS)));

        // delete the user created
        deleteUser(100l);
    }

    /**
     * A private helper method to delete user record with given user_id.
     * @param userId
     *            the userId of user to be removed
     * @throws Exception
     *             to JUnit
     */
    private void deleteUser(long userId) throws Exception {
        Connection conn = DBMS.getConnection(DS);
        PreparedStatement ps = conn.prepareStatement("delete from user WHERE user_id=?");
        ps.setLong(1, userId);
        ps.executeUpdate();
        conn.close();
    }

    /**
     * Failure test case for {@link UserBean#createUser(long, String, char, String)} method. Passes a null handle and
     * expects an exception.
     * @throws Exception
     *             to JUnit
     */
    public void testCreateUserFail() throws Exception {
        try {
            instance.createUser(100l, null, "A".toCharArray()[0], DS);
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Accuracy test case for {@link UserBean#setFirstName(long, String, String)} and
     * {@link UserBean#getFirstName(long, String)} methods.
     * @throws Exception
     *             to JUnit
     */
    public void testSetGetFirstName() throws Exception {
        String firstName = "first";
        String dbName = instance.getFirstName(20l, DS);
        instance.setFirstName(20l, firstName, DS);

        // check if its persisted into database correctly
        assertTrue("Error setting first name", firstName.equals(instance.getFirstName(20l, DS)));

        // set the first name back again
        instance.setFirstName(20l, dbName, DS);
    }

    /**
     * Failure test case for {@link UserBean#setFirstName(long, String, String)} method. Passes a null and expects an
     * Exception.
     * @throws Exception
     *             to JUnit
     */
    public void testSetFirstNameFail() throws Exception {
        try {
            instance.setFirstName(20l, DS, null);
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Failure test case for {@link UserBean#setFirstName(long, String, String)} method. Passes an empty datasource and
     * expects an Exception.
     * @throws Exception
     *             to JUnit
     */
    public void testSetFirstNameFail1() throws Exception {
        try {
            instance.setFirstName(20l, DS, "   ");
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Accuracy test case for {@link UserBean#setlastName(long, String, String)} and
     * {@link UserBean#getLastName(long, String)} methods.
     * @throws Exception
     *             to JUnit
     */
    public void testSetGetlastName() throws Exception {
        String lastName = "last";
        String dbName = instance.getLastName(20l, DS);
        instance.setLastName(20l, lastName, DS);

        // check if its persisted into database correctly
        assertTrue("Error setting last name", lastName.equals(instance.getLastName(20l, DS)));

        // set the last name back again
        instance.setLastName(20l, dbName, DS);
    }

    /**
     * Failure test case for {@link UserBean#setlastName(long, String, String)} method. Passes a null and expects an
     * Exception.
     * @throws Exception
     *             to JUnit
     */
    public void testSetlastNameFail() throws Exception {
        try {
            instance.setLastName(20l, DS, null);
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Failure test case for {@link UserBean#setlastName(long, String, String)} method. Passes an empty datasource and
     * expects an Exception.
     * @throws Exception
     *             to JUnit
     */
    public void testSetlastNameFail1() throws Exception {
        try {
            instance.setLastName(20l, DS, "   ");
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Accuracy test case for {@link UserBean#setMiddleName(long, String, String)} and
     * {@link UserBean#getMiddleName(long, String)} methods.
     * @throws Exception
     *             to JUnit
     */
    public void testSetGetMiddleName() throws Exception {
        String middleName = "Middle";
        String dbName = instance.getMiddleName(20l, DS);
        instance.setMiddleName(20l, middleName, DS);

        // check if its persisted into database correctly
        assertTrue("Error setting Middle name", middleName.equals(instance.getMiddleName(20l, DS)));

        // set the Middle name back again
        instance.setMiddleName(20l, dbName, DS);
    }

    /**
     * Failure test case for {@link UserBean#setMiddleName(long, String, String)} method. Passes a null and expects an
     * Exception.
     * @throws Exception
     *             to JUnit
     */
    public void testSetMiddleNameFail() throws Exception {
        try {
            instance.setMiddleName(20l, DS, null);
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Failure test case for {@link UserBean#setMiddleName(long, String, String)} method. Passes an empty datasource and
     * expects an Exception.
     * @throws Exception
     *             to JUnit
     */
    public void testSetMiddleNameFail1() throws Exception {
        try {
            instance.setMiddleName(20l, DS, "   ");
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Accuracy test case for {@link UserBean#setActivationCode(long, String, String)} and
     * {@link UserBean#getActivationCode(long, String)} methods.
     * @throws Exception
     *             to JUnit
     */
    public void testSetGetActivationCode() throws Exception {
        String activationCode = "ActivationCode";
        String dbName = instance.getActivationCode(20l, DS);
        instance.setActivationCode(20l, activationCode, DS);

        // check if its persisted into database correctly
        assertTrue("Error setting ActivationCode", activationCode.equals(instance.getActivationCode(20l, DS)));

        // set the ActivationCode back again
        instance.setActivationCode(20l, dbName, DS);
    }

    /**
     * Failure test case for {@link UserBean#setActivationCode(long, String, String)} method. Passes a null and expects
     * an Exception.
     * @throws Exception
     *             to JUnit
     */
    public void testSetActivationCodeFail() throws Exception {
        try {
            instance.setActivationCode(20l, DS, null);
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Failure test case for {@link UserBean#setActivationCode(long, String, String)} method. Passes an empty datasource
     * and expects an Exception.
     * @throws Exception
     *             to JUnit
     */
    public void testSetActivationCodeFail1() throws Exception {
        try {
            instance.setActivationCode(20l, DS, "   ");
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Accuracy test case for {@link UserBean#setStatus(long, char, String)} and
     * {@link UserBean#getStatus(long, String)} methods.
     * @throws Exception
     *             to JUnit
     */
    public void testSetStatus() throws Exception {
        char status = 'Z';
        char dbName = instance.getStatus(20l, DS);
        instance.setStatus(20l, status, DS);

        // check if its persisted into database correctly
        assertEquals("Error setting status", status, instance.getStatus(20l, DS));

        // set the status back again
        instance.setStatus(20l, dbName, DS);
    }

    /**
     * Failure test case for {@link UserBean#setStatus(long, String, String)} method. Passes a null and expects an
     * Exception.
     * @throws Exception
     *             to JUnit
     */
    public void testSetStatusFail() throws Exception {
        try {
            instance.setStatus(20l, DS.toCharArray()[0], null);
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Failure test case for {@link UserBean#setStatus(long, String, String)} method. Passes an empty datasource and
     * expects an Exception.
     * @throws Exception
     *             to JUnit
     */
    public void testSetStatusFail1() throws Exception {
        try {
            instance.setStatus(20l, DS.toCharArray()[0], "   ");
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Failure test case for {@link UserBean#getFirstName(long, String)} method. Passes a null and expects an Exception.
     * @throws Exception
     *             to JUnit
     */
    public void testGetFirstNameFail() throws Exception {
        try {
            instance.getFirstName(20l, null);
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Failure test case for {@link UserBean#getFirstName(long, String)} method. Passes an empty datasource and expects
     * an Exception.
     * @throws Exception
     *             to JUnit
     */
    public void testGetFirstNameFail1() throws Exception {
        try {
            instance.getFirstName(20l, "    ");
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Failure test case for {@link UserBean#getLastName(long, String)} method. Passes a null and expects an Exception.
     * @throws Exception
     *             to JUnit
     */
    public void testGetLastNameFail() throws Exception {
        try {
            instance.getLastName(20l, null);
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Failure test case for {@link UserBean#getLastName(long, String)} method. Passes an empty datasource and expects
     * an Exception.
     * @throws Exception
     *             to JUnit
     */
    public void testGetLastNameFail1() throws Exception {
        try {
            instance.getLastName(20l, "    ");
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Failure test case for {@link UserBean#getMiddleName(long, String)} method. Passes a null and expects an
     * Exception.
     * @throws Exception
     *             to JUnit
     */
    public void testGetMiddleNameFail() throws Exception {
        try {
            instance.getMiddleName(20l, null);
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Failure test case for {@link UserBean#getMiddleName(long, String)} method. Passes an empty datasource and expects
     * an Exception.
     * @throws Exception
     *             to JUnit
     */
    public void testGetMiddleNameFail1() throws Exception {
        try {
            instance.getMiddleName(20l, "    ");
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Failure test case for {@link UserBean#getActivationCode(long, String)} method. Passes a null and expects an
     * Exception.
     * @throws Exception
     *             to JUnit
     */
    public void testGetActivationCodeFail() throws Exception {
        try {
            instance.getActivationCode(20l, null);
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Failure test case for {@link UserBean#getActivationCode(long, String)} method. Passes an empty datasource and
     * expects an Exception.
     * @throws Exception
     *             to JUnit
     */
    public void testGetActivationCodeFail1() throws Exception {
        try {
            instance.getActivationCode(20l, "    ");
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Accuracy test for {@link UserBean#setHandle(long, String, String)} and {@link UserBean#getHandle(long, String)}
     * methods.
     * @throws Exception
     *             to JUnit
     */
    public void testSetGetHandle() throws Exception {
        String handle = "handle";
        String dbHandle = instance.getHandle(20l, DS);
        instance.setHandle(20l, handle, DS);

        // check if its persisted into database correctly
        assertTrue("Error setting handle", handle.equals(instance.getHandle(20l, DS)));

        // set the handle back again
        instance.setHandle(20l, dbHandle, DS);
    }

    /**
     * Failure test case for {@link UserBean#setHandle(long, String, String)} method. Passes a null and expects an
     * exception.
     * @throws Exception
     *             to JUnit
     */
    public void testSetHandleFail() throws Exception {
        try {
            instance.setHandle(20l, null, DS);
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Failure test case for {@link UserBean#setHandle(long, String, String)} method. Passes a null and expects an
     * exception.
     * @throws Exception
     *             to JUnit
     */
    public void testSetHandleFail1() throws Exception {
        try {
            instance.setHandle(20l, "handle", null);
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Failure test case for {@link UserBean#setHandle(long, String, String)} method. Passes an empty string and expects
     * an exception.
     * @throws Exception
     *             to JUnit
     */
    public void testSetHandleFail2() throws Exception {
        try {
            instance.setHandle(20l, "   ", DS);
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Failure test case for {@link UserBean#setHandle(long, String, String)} method. Passes an empty string and expects
     * an exception.
     * @throws Exception
     *             to JUnit
     */
    public void testSetHandleFail3() throws Exception {
        try {
            instance.setHandle(20l, "handle", "   ");
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Failure test case for {@link UserBean#getHandle(long, String)} method. Passes a null and expects an exception.
     * @throws Exception
     *             to JUnit
     */
    public void testGetHandleFail() throws Exception {
        try {
            instance.getHandle(20l, null);
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Failure test case for {@link UserBean#getHandle(long, String)} method. Passes an empty string and expects an
     * exception.
     * @throws Exception
     *             to JUnit
     */
    public void testGetHandleFail1() throws Exception {
        try {
            instance.getHandle(20l, "   ");
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Failure test case for {@link UserBean#getStatus(long, String)} method. Passes a null and expects an exception
     * @throws Exception
     *             to JUnit
     */
    public void testGetStatusFail() throws Exception {
        try {
            instance.getStatus(20l, null);
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Accuracy test case for {@link UserBean#userExists(long, String)} method. Checks for an existing and non-existing
     * users.
     * @throws Exception
     *             to JUnit
     */
    public void testUserExists() throws Exception {
        assertTrue("Error checking for user in database", instance.userExists(20l, DS));
        assertFalse("Error checking for user in database", instance.userExists(2000l, DS));
    }

    /**
     * Failure test case for {@link UserBean#userExists(long, String)} method. Passes a null and expects an exception.
     * @throws Exception
     *             to JUnit
     */
    public void testUserExistsFail() throws Exception {
        try {
            instance.userExists(20l, null);
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Accuracy test case for {@link UserBean#userExists(String, String)} method. Checks for an existing and
     * non-existing users.
     * @throws Exception
     *             to JUnit
     */
    public void testUserExistsHandle() throws Exception {
        assertTrue("Error checking for user in database", instance.userExists("dok_tester", DS));
        assertFalse("Error checking for user in database", instance.userExists("ddok_tester", DS));
    }

    /**
     * Failure test case for {@link UserBean#userExists(String, String)} method. Passes a null and expects an exception.
     * @throws Exception
     *             to JUnit
     */
    public void testUserExistsHandleFail() throws Exception {
        try {
            instance.userExists("handle", null);
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Accuracy test case for {@link UserBean#setPassword(long, String, String)} and
     * {@link UserBean#getPassword(long, String)} methods.
     * @throws Exception
     *             to JUnit
     */
    public void testSetGetPassword() throws Exception {
        String password = "password";
        instance.setPassword(20l, password, DS);

        assertTrue("Error setting getting password", password.equals(instance.getPassword(20l, DS)));

        // restore the null value back
        Connection conn = DBMS.getConnection(DS);
        PreparedStatement ps = conn.prepareStatement("update user set password = null");
        ps.executeUpdate();
        conn.close();
    }

    /**
     * Failure test case for {@link UserBean#setPassword(long, String, String)} method. Passes a null and expects an
     * exception.
     * @throws Exception
     *             to JUnit
     */
    public void testSetPasswordFail() throws Exception {
        try {
            instance.setPassword(20l, null, DS);
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Failure test case for {@link UserBean#setPassword(long, String, String)} method. Passes a null and expects an
     * exception.
     * @throws Exception
     *             to JUnit
     */
    public void testSetPasswordFail1() throws Exception {
        try {
            instance.setPassword(20l, "pass", null);
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Failure test case for {@link UserBean#setPassword(long, String, String)} method. Passes an empty password and
     * expects an exception.
     * @throws Exception
     *             to JUnit
     */
    public void testSetPasswordFail2() throws Exception {
        try {
            instance.setPassword(20l, "   ", DS);
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Failure test case for {@link UserBean#getPassword(long, String)} method. Passes a null and expects an exception.
     * @throws Exception
     *             to JUnit
     */
    public void testGetPasswordFail() throws Exception {
        try {
            instance.getPassword(20l, null);
            fail("Exception should be thrown");
        } catch (EJBException e) {
            // pass
        }
    }

    /**
     * Failure test case for {@link UserBean#getPassword(long, String)}. Tries to get password for a user who don't
     * exist and expects an exception.
     * @throws Exception
     *             to JUnit
     */
    public void testGetPasswordFail1() throws Exception {
        try {
            instance.getPassword(2000l, DS);
            fail("Exception should be thrown");
        } catch (RowNotFoundException e) {
            // pass
        }
    }
}
