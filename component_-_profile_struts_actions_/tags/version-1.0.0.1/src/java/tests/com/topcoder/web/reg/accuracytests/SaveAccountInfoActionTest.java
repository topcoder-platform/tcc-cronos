/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.security.admin.PrincipalMgrRemote;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.reg.actions.profile.SaveAccountInfoAction;

/**
 * <p>
 * Accuracy tests for the {@link SaveAccountInfoAction}.
 * </p>
 *
 * @author extra
 * @version 1.0
 */
public class SaveAccountInfoActionTest {
    /** Represents the confirmPassword used to test again. */
    private String confirmPasswordValue = "123456";

    /** Represents the principalMgr used to test again. */
    private PrincipalMgrRemote principalMgrValue;

    /** Represents the referralUserHandle used to test again. */
    private String referralUserHandleValue;

    /** Represents the instance used to test again. */
    private SaveAccountInfoAction testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new SaveAccountInfoAction();
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
     * Accuracy test for {@link SaveAccountInfoAction#SaveAccountInfoAction()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void testSaveAccountInfoAction() throws Exception {
        new SaveAccountInfoAction();

        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link SaveAccountInfoAction#getConfirmPassword()}
     * </p>
     * <p>
     * The value of <code>confirmPassword</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getConfirmPassword() throws Exception {
        assertNull("Old value", testInstance.getConfirmPassword());
    }

    /**
     * <p>
     * Accuracy test {@link SaveAccountInfoAction#setConfirmPassword(String)}.
     * </p>
     * <p>
     * The value of <code>confirmPassword</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setConfirmPassword() throws Exception {
        testInstance.setConfirmPassword(confirmPasswordValue);
        assertEquals("New value", confirmPasswordValue, testInstance.getConfirmPassword());
    }

    /**
     * <p>
     * Accuracy test for {@link SaveAccountInfoAction#getPrincipalMgr()}
     * </p>
     * <p>
     * The value of <code>principalMgr</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getPrincipalMgr() throws Exception {
        assertNull("Old value", testInstance.getPrincipalMgr());
    }

    /**
     * <p>
     * Accuracy test {@link SaveAccountInfoAction#setPrincipalMgr(PrincipalMgrRemote)}.
     * </p>
     * <p>
     * The value of <code>principalMgr</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setPrincipalMgr() throws Exception {
        testInstance.setPrincipalMgr(principalMgrValue);
        assertEquals("New value", principalMgrValue, testInstance.getPrincipalMgr());
    }

    /**
     * <p>
     * Accuracy test for {@link SaveAccountInfoAction#getReferralUserHandle()}
     * </p>
     * <p>
     * The value of <code>referralUserHandle</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getReferralUserHandle() throws Exception {
        assertNull("Old value", testInstance.getReferralUserHandle());
    }

    /**
     * <p>
     * Accuracy test {@link SaveAccountInfoAction#setReferralUserHandle(String)}.
     * </p>
     * <p>
     * The value of <code>referralUserHandle</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setReferralUserHandle() throws Exception {
        testInstance.setReferralUserHandle(referralUserHandleValue);
        assertEquals("New value", referralUserHandleValue, testInstance.getReferralUserHandle());
    }

    /**
     * <p>
     * Accuracy test for {@link SaveAccountInfoAction#validate()}.
     * </p>
     * <p>
     *
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_validate() throws Exception {
        testInstance.validate();

        Collection<String> aErrors = testInstance.getActionErrors();
        Map<String, List<String>> fErrors = testInstance.getFieldErrors();

        Assert.assertEquals("action errors", 1, aErrors.size());
        Assert.assertEquals("field errors", 0, fErrors.size());
    }
}