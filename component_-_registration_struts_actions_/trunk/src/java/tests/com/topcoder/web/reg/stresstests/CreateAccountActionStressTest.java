/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.stresstests;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.security.GroupPrincipal;
import com.topcoder.security.TCSubject;
import com.topcoder.security.UserPrincipal;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.reg.actions.registration.CreateAccountAction;

/**
 * <p>
 * This class contains Stress tests for CreateAccountAction.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class CreateAccountActionStressTest extends BaseStressTest {

    /**
     * <p>
     * Represents CreateAccountAction instance for testing.
     * </p>
     */
    private CreateAccountAction action;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        action = getBean("createAccountAction");
        MockFactory.createUserInSession(action);
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void tearDown() throws Exception {
        super.tearDown();
        action = null;
    }

    /**
     * <p>
     * Tests CreateAccountAction#execute() method with valid fields set.
     * </p>
     * <p>
     * Action should be executed successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute() throws Exception {
        action.setUser(MockFactory.getUser());
        String referrerHandle = "referrerHandle";
        action.setReferrerHandle(referrerHandle);
        String verificationCode = "verificationCode";
        action.setVerificationCode(verificationCode);
        User referralUser = MockFactory.createUser(2L, "referral first", "referral last", referrerHandle);
        when(action.getUserDAO().find(referrerHandle, true)).thenReturn(referralUser);
        for (int i = 0; i < getRunCount(); i++) {
            assertEquals("Action should be executed successfully", ActionSupport.SUCCESS, action.execute());
            assertNotNull("User activation code should be set.", action.getUser().getActivationCode());
            action.getUser().setActivationCode(null);
        }
        verify(action.getPrincipalMgr(), times(2 * getRunCount())).addUserToGroup(any(GroupPrincipal.class),
                any(UserPrincipal.class), any(TCSubject.class), anyString());
        verify((BasicAuthentication) action.getSession().get(action.getAuthenticationSessionKey()),
                times(getRunCount())).setCookie(MockFactory.getId(), true);
    }
}
