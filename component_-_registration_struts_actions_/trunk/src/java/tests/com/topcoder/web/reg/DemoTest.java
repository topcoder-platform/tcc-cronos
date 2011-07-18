/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg;

import java.io.File;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.topcoder.web.common.HibernateUtils;
import com.topcoder.web.common.model.CoderReferral;
import com.topcoder.web.common.model.Referral;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.actions.registration.ActivateAccountAction;
import com.topcoder.web.reg.actions.registration.CreateAccountAction;
import com.topcoder.web.reg.actions.registration.PreCreateAccountAction;
import com.topcoder.web.reg.actions.registration.ResendAccountActivationEmailAction;
import com.topcoder.web.reg.actions.registration.SelectRegistrationPreferenceAction;
import com.topcoder.web.reg.actions.registration.ViewRegistrationPreferenceAction;
import com.topcoder.web.tc.Constants;

/**
 * <p>
 * Demo tests.
 * </p>
 *
 * @author stevenfrog
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

        // Get the ViewRegistrationPreferenceAction from Spring
        ViewRegistrationPreferenceAction viewRegistrationPreferenceAction =
            (ViewRegistrationPreferenceAction) applicationContext
                .getBean("viewRegistrationPreferenceAction");

        // The return string should be "success"
        // allRegistrationTypes and userRegistrationTypes will not null
        viewRegistrationPreferenceAction.execute();

        // Get the PreCreateAccountAction from Spring
        PreCreateAccountAction preCreateAccountAction = (PreCreateAccountAction) applicationContext
            .getBean("preCreateAccountAction");

        Constants.CAPTCHA_PATH = "test_files" + File.separator;

        // The return string should be "success"
        preCreateAccountAction.execute();

        // picture file has created
        String filename = preCreateAccountAction.getCapfname();
        File file = new File(Constants.CAPTCHA_PATH + filename);
        file.delete();

        // Get the ActivateAccountAction from Spring
        ActivateAccountAction activateAccountAction = (ActivateAccountAction) applicationContext
            .getBean("activateAccountAction");
        activateAccountAction.setActivationCode("1M1");

        // The return string should be "success"
        // and one email will be sent
        activateAccountAction.execute();

        // Get the SelectRegistrationPreferenceAction from Spring
        SelectRegistrationPreferenceAction selectRegistrationPreferenceAction =
            (SelectRegistrationPreferenceAction) applicationContext.getBean("selectRegistrationPreferenceAction");

        // The return string should be "success"
        // and one email will be sent
        selectRegistrationPreferenceAction.execute();

        // Get the ResendAccountActivationEmailAction from Spring
        ResendAccountActivationEmailAction resendAccountActivationEmailAction =
            (ResendAccountActivationEmailAction) applicationContext.getBean("resendAccountActivationEmailAction");

        // The return string should be "success"
        // and one email will be sent
        resendAccountActivationEmailAction.execute();

        // Get the CreateAccountAction from Spring
        CreateAccountAction createAccountAction = (CreateAccountAction) applicationContext
            .getBean("createAccountAction");
        User user = createAccountAction.getUserDAO().find(20L);
        CoderReferral coderReferral = new CoderReferral();
        user.getUserProfile().getCoder().setCoderReferral(coderReferral);
        Referral referral = new Referral();
        coderReferral.setReferral(referral);
        createAccountAction.setUser(user);
        createAccountAction.setReferrerHandle("dok_tester1");

        // The return string should be "success"
        // and one email will be sent
        createAccountAction.execute();
    }
}
