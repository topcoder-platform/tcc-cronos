/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.common.model.Email;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.actions.profile.ContactInfoTaskChecker;
import com.topcoder.web.reg.actions.profile.ProfileTaskReport;

/**
 * <p>
 * Accuracy tests for the {@link ContactInfoTaskChecker}.
 * </p>
 *
 * @author extra
 * @version 1.0
 */
public class ContactInfoTaskCheckerTest {
    /** Represents the instance used to test again. */
    private ContactInfoTaskChecker testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new ContactInfoTaskChecker();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to JAccuracy
     */
    @After
    public void tearDown() throws Exception {
        testInstance = null;
    }

    /**
     * <p>
     * Accuracy test for {@link ContactInfoTaskChecker#ContactInfoTaskChecker()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void testContactInfoTaskChecker() throws Exception {
        new ContactInfoTaskChecker();

        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link ContactInfoTaskChecker#getTaskReport()}.
     * </p>
     * <p>
     *
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getTaskReport() throws Exception {
        User user = new User();
        Set<Email> emailAddresses = new HashSet<Email>();
        Email email1=  new Email();
        email1.setAddress("test@topcoder.com");
        emailAddresses.add(email1);
        Email email2=  new Email();
        email2.setAddress("tc@topcoder.com");
        emailAddresses.add(email2);
        user.setEmailAddresses(emailAddresses);
        testInstance.setTaskName("taskName");
        ProfileTaskReport report = testInstance.getTaskReport(user);
        Assert.assertEquals("taskName", "taskName", report.getTaskName());
        Assert.assertEquals("completed", true, report.getCompleted());
    }
}