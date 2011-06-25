/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.common.model.User;
import com.topcoder.web.tc.controller.legacy.pacts.bean.DataInterfaceBean;

/**
 * <p>
 * This class contains Unit tests for PaymentPreferencesAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PaymentPreferencesActionUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents PaymentPreferencesAction action for testing.
     * </p>
     */
    private PaymentPreferencesAction action;

    /**
     * <p>
     * Represents ActionProxy instance for testing.
     * </p>
     */
    private ActionProxy proxy;

    /**
     * <p>
     * Represents logged in user for testing.
     * </p>
     */
    private User loggedInUser;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        proxy = getActionProxy("/payment");
        action = (PaymentPreferencesAction) proxy.getAction();
        setBasePreferencesActionDAOs(action);
        action.setEmailBodyTemplateFileName(EMAIL_BODY_TEMPLATE_NAME);
        // put valid class for basic authentication session key
        putKeyValueToSession(action.getBasicAuthenticationSessionKey(), createAuthentication());
        loggedInUser = createUser(1L, "First", "Second", "handle", true);
        when(action.getUserDao().find(1L)).thenReturn(loggedInUser);
        // prepare DataInterfaceBean
        action.setDataInterfaceBean(getDataInterfaceBean());
        prepareDataInterfaceBean(action.getDataInterfaceBean());
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
        proxy = null;
        loggedInUser = null;
    }

    /**
     * <p>
     * Tests PaymentPreferencesAction constructor.
     * </p>
     * <p>
     * PaymentPreferencesAction instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("PaymentPreferencesAction instance should be created successfully.", action);
    }

    /**
     * <p>
     * Tests PaymentPreferencesAction#execute() method with valid attributes.
     * </p>
     * <p>
     * Execute should be ended successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPrepare() throws Exception {
        // prepare blocked users
        action.prepare();
        assertEquals("Current payment accrual amount should be set.", 1, action.getCurrentPaymentAccrualAmount());
    }

    /**
     * <p>
     * Tests PaymentPreferencesAction#execute() method with submit action and valid attributes.
     * </p>
     * <p>
     * Execute should be ended successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_Submit() throws Exception {
        int currentPaymentAccrualAmount = 25;
        action.setCurrentPaymentAccrualAmount(currentPaymentAccrualAmount);
        action.setAction(SUBMIT_ACTION);
        long userId = 1;
        DataInterfaceBean dataInterfaceBean = action.getDataInterfaceBean();
        // execute
        assertEquals("Execute should be ended successfully.", ActionSupport.SUCCESS, action.execute());
        // 1 record should be audited
        verify(action.getAuditDao(), times(1)).audit(any(AuditRecord.class));
        // one user accrual threshold should be saved
        verify(dataInterfaceBean, times(1)).getUserAccrualThreshold(userId);
        verify(dataInterfaceBean, times(1)).saveUserAccrualThreshold(userId, currentPaymentAccrualAmount);
        assertNotNull("User payment accrual amount should be backedup.",
                ActionContext.getContext().getSession().get(action.getBackupSessionKey()));
    }

    /**
     * <p>
     * Tests PaymentPreferencesAction#execute() method with discard action and valid attributes.
     * </p>
     * <p>
     * Execute should be ended successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_Discard() throws Exception {
        int currentPaymentAccrualAmount = 25;
        int userId = 1;
        // put backup session
        putKeyValueToSession(action.getBackupSessionKey(), currentPaymentAccrualAmount);
        action.setAction(DISCARD_ACTION);
        // change user preference value
        assertEquals("Execute should be ended successfully.", ActionSupport.SUCCESS, action.execute());
        // one currentPaymentAccrualAmount should be retrieved from backup session key
        verify(action.getDataInterfaceBean(), times(1)).saveUserAccrualThreshold(userId, currentPaymentAccrualAmount);
        // no records should be audited
        verify(action.getAuditDao(), times(0)).audit(any(AuditRecord.class));
    }

    /**
     * <p>
     * Tests PaymentPreferencesAction#execute() method with discard action, valid attributes and email true flag.
     * </p>
     * <p>
     * Execute should be ended successfully and email should be sent. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_EmailSend() throws Exception {
        int currentPaymentAccrualAmount = 25;
        int userId = 1;
        // put backup session
        putKeyValueToSession(action.getBackupSessionKey(), currentPaymentAccrualAmount);
        action.setAction(DISCARD_ACTION);
        action.setEmailSendFlag(true);
        // change user preference value
        assertEquals("Execute should be ended successfully.", ActionSupport.SUCCESS, action.execute());
        // one currentPaymentAccrualAmount should be retrieved from backup session key
        verify(action.getDataInterfaceBean(), times(1)).saveUserAccrualThreshold(userId, currentPaymentAccrualAmount);
        // no records should be audited
        verify(action.getAuditDao(), times(0)).audit(any(AuditRecord.class));
        // check email sent manually
    }

    /**
     * <p>
     * Tests PaymentPreferencesAction#validate() method valid input argument passed.
     * </p>
     * <p>
     * Validate should be ended successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate() throws Exception {
        action.prepare();
        // provide validation
        action.validate();
    }
}
