/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.common.model;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.web.common.HibernateUtils;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.dao.hibernate.AuditDAOHibernate;
import com.topcoder.web.common.dao.hibernate.UserDAOHibernate;
import com.topcoder.web.ejb.user.UserBean;

/**
 * Demonstrates the usage of this component.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends TestCase {
    /**
     * The datasource name used for testing.
     */
    private static final String DS = "userds";

    /**
     * Id of the user created.
     */
    private static long id;

    /**
     * The UserBean instance used for testing.
     */
    private UserBean userBean;

    /**
     * Sets up the test environment.
     * @throws Exception
     *             to JUnit
     */
    public void setUp() throws Exception {
        userBean = new UserBean();
    }

    /**
     * Tears down the test environment.
     */
    public void tearDown() {
        userBean = null;
    }

    /**
     * Demonstrates the usage of UserBean.
     * @throws Exception
     *             to JUnit
     */
    public void testDemo() throws Exception {
        // Create new user "test_user"
        long userId = userBean.createNewUser("test_user", 'U', DS, DS);

        // Set first name of the user
        userBean.setFirstName(userId, "John", DS);

        // Set middle name of the user
        userBean.setMiddleName(userId, "A", DS);

        // Set last name of the user
        userBean.setLastName(userId, "Smith", DS);

        // Set status of the user to active
        userBean.setStatus(userId, 'A', DS);

        // Set activation code of the user
        userBean.setActivationCode(userId, "12345", DS);

        // Set password of the user
        userBean.setPassword(userId, "test_pass", DS);

        // Change handle of the user
        userBean.setHandle(userId, "first_user", DS);

        // Retrieve the first name of the user
        String firstName = userBean.getFirstName(userId, DS);
        assertEquals("Error getting field", "John", firstName);
        // firstName must be "John"

        // Retrieve the middle name of the user
        String middleName = userBean.getMiddleName(userId, DS);
        // middleName must be "A"
        assertEquals("Error getting field", "A", middleName);

        // Retrieve the last name of the user
        String lastName = userBean.getLastName(userId, DS);
        // lastName must be "Smith"
        assertEquals("Error getting field", "Smith", lastName);

        // Retrieve the status of the user
        char status = userBean.getStatus(userId, DS);
        // status must be 'A'
        assertEquals("Error getting field", 'A', status);

        // Retrieve the activation code of the user
        String activationCode = userBean.getActivationCode(userId, DS);
        // activationCode must be "12345"
        assertEquals("Error getting field", "12345", activationCode);

        // Retrieve the handle of the user
        String handle = userBean.getHandle(userId, DS);
        // handle must be "first_user"
        assertEquals("Error getting field", "first_user", handle);

        // Retrieve the password of the user
        String password = userBean.getPassword(userId, DS);
        // password must be "test_pass"
        assertEquals("Error getting field", "test_pass", password);

        // Check if user with the specified ID exists
        assertTrue("exists should be true", userBean.userExists(userId, DS));
        // exists must be true

        // Check if user with the specified handle exists
        assertTrue("exists should be true", userBean.userExists("first_user", DS));
        // exists must be false

        id = userId;
    }

    /**
     * Demonstrates the usage of UserDAOHibernate class.
     */
    public void testHibernate() {
        UserDAO userDAO = new UserDAOHibernate();
        HibernateUtils.begin();

        // Find user by ID
        User user = userDAO.find(id);
        assertEquals("Error getting value", "first_user", user.getHandle());
        assertEquals("Error getting value", id, user.getId().intValue());
        assertEquals("Error getting value", id, user.getUserProfile().getId().intValue());

        // Specify email address for the user and save it
        Email email = new Email();
        email.setAddress("john_smith@topcoder.com");
        email.setPrimary(true);
        email.setEmailTypeId(Email.TYPE_ID_PRIMARY);
        email.setStatusId(Email.STATUS_ID_ACTIVE);
        user.addEmailAddress(email);
        userDAO.saveOrUpdate(user);

        HibernateUtils.getSession().delete(user);
        HibernateUtils.getSession().flush();
        HibernateUtils.commit();
        HibernateUtils.close();
    }

    /**
     * Demonstrates the usage of AuditDAOHibernate class.
     */
    public void testAuditDemo() {
        AuditDAO auditDAO = new AuditDAOHibernate(HibernateUtils.getSession());
        HibernateUtils.begin();

        assertFalse("Error verifying operation for user", auditDAO.hasOperation("dok_tester", "login"));

        // Audit user login operation
        AuditRecord record = new AuditRecord();
        record.setNew(true);
        record.setHandle("dok_tester");
        record.setIpAddress("111.222.111.222");
        record.setOperationType("login");
        record.setTimestamp(new Date());
        auditDAO.audit(record);

        assertTrue("Error verifying operation for user", auditDAO.hasOperation("dok_tester", "login"));

        HibernateUtils.getSession().delete(record);
        HibernateUtils.getSession().flush();
        HibernateUtils.commit();
        HibernateUtils.close();
    }
}
