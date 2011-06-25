/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.topcoder.web.common.HibernateUtils;
import com.topcoder.web.reg.actions.basic.LoginAction;
import com.topcoder.web.reg.actions.basic.LogoutAction;
import com.topcoder.web.reg.actions.basic.RecoverPasswordAction;
import com.topcoder.web.reg.actions.basic.ResendPasswordRecoveryEmailAction;
import com.topcoder.web.reg.actions.basic.ResetPasswordAction;

/**
 * <p>
 * Demo tests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DemoTest {
    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DemoTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        HibernateUtils.begin();
    }

    /**
     * <p>
     * Tear down the test environment.
     * </p>
     */
    @After
    public void tearDown() {
        HibernateUtils.getSession().clear();
        HibernateUtils.close();
    }

    /**
     * <p>
     * The Demo API.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testDemoAPI() throws Exception {
        // Load the Spring context file
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
            "applicationContext.xml");

        // Get the LoginAction from Spring
        LoginAction loginAction = (LoginAction) applicationContext.getBean("loginAction");

        // Log in
        loginAction.setUsername("username");
        loginAction.setPassword("password");

        // The return string should be "firstTimeLogin"
        loginAction.execute();

        // Get the ResetPasswordAction from Spring
        ResetPasswordAction resetPasswordAction = (ResetPasswordAction) applicationContext
                        .getBean("resetPasswordAction");

        // Reset the password
        resetPasswordAction.setPasswordRecoveryId(1);

        // The return string should be "success"
        resetPasswordAction.execute();

        // Get the ResetPasswordAction from Spring
        LogoutAction logoutAction = (LogoutAction) applicationContext.getBean("logoutAction");

        // Log out
        // The return string should be "success"
        logoutAction.execute();

        // Get the RecoverPasswordAction from Spring
        RecoverPasswordAction recoverPasswordAction = (RecoverPasswordAction) applicationContext
                        .getBean("recoverPasswordAction");

        // Recover the password by handle
        recoverPasswordAction.setHandle("dok_tester");

        // The return string should be "success"
        recoverPasswordAction.execute();

        // Get the ResendPasswordRecoveryEmailAction from Spring
        ResendPasswordRecoveryEmailAction resendPasswordRecoveryEmailAction =
            (ResendPasswordRecoveryEmailAction) applicationContext.getBean("resendPasswordRecoveryEmailAction");

        // Resend the email
        resendPasswordRecoveryEmailAction.setPasswordRecoveryId(1);

        // The return string should be "success"
        resendPasswordRecoveryEmailAction.execute();
    }
}
