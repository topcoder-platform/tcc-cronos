/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.rmi.RemoteException;
import java.sql.SQLException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.web.common.model.User;
import com.topcoder.web.tc.Constants;
import com.topcoder.web.tc.controller.legacy.pacts.bean.DataInterfaceBean;

/**
 * <p>
 * This class contains Unit tests for PaymentPreferencesAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PaymentPreferencesParametersUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents PaymentPreferencesAction action for testing.
     * </p>
     */
    private PaymentPreferencesAction action;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        ActionProxy proxy = getActionProxy("/payment");
        action = (PaymentPreferencesAction) proxy.getAction();
        setBasePreferencesActionDAOs(action);
        action.setEmailBodyTemplateFileName(EMAIL_BODY_TEMPLATE_NAME);
        // put valid class for basic authentication session key
        putKeyValueToSession(action.getBasicAuthenticationSessionKey(), createAuthentication());
        User loggedInUser = createUser(1L, "First", "Second", "handle", true);
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
    }

    /**
     * <p>
     * Tests PaymentPreferencesAction#execute() with no user in session.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_Submit_NoUser() throws Exception {
        putKeyValueToSession(action.getBasicAuthenticationSessionKey(), null);
        action.setAction(SUBMIT_ACTION);
        try {
            action.execute();
            fail("UserPreferencesActionExecutionException exception is expected.");
        } catch (UserPreferencesActionExecutionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests PaymentPreferencesAction#execute() with email send fail.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_Submit_EmailFail() throws Exception {
        action.prepare();
        action.setEmailSendFlag(true);
        action.setEmailBodyTemplateFileName("Unknown");
        action.setAction(SUBMIT_ACTION);
        try {
            action.execute();
            fail("UserPreferencesActionExecutionException exception is expected.");
        } catch (UserPreferencesActionExecutionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests PaymentPreferencesAction#execute() with no backed up accrual payment in session.
     * </p>
     * <p>
     * PreferencesActionDiscardException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_Discard_NoBackPayment() throws Exception {
        action.setAction(DISCARD_ACTION);
        try {
            action.execute();
            fail("PreferencesActionDiscardException exception is expected.");
        } catch (PreferencesActionDiscardException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests PaymentPreferencesAction#execute() with discard action and exception occurs in underlying
     * dataInterfaceBean.
     * </p>
     * <p>
     * PreferencesActionDiscardException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_Discard_DataInterfaceBeanRemoteException() throws Exception {
        ActionContext.getContext().getSession().put(action.getBackupSessionKey(), 1);
        action.setAction(DISCARD_ACTION);
        doThrow(new RemoteException("just for testing.")).when(action.getDataInterfaceBean())
                .saveUserAccrualThreshold(anyLong(), anyDouble());
        try {
            action.execute();
            fail("PreferencesActionDiscardException exception is expected.");
        } catch (PreferencesActionDiscardException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests PaymentPreferencesAction#execute() with submit action and exception occurs in underlying
     * dataInterfaceBean.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_Submit_DataInterfaceBeanRemoteException() throws Exception {
        action.setCurrentPaymentAccrualAmount(1);
        action.setAction(SUBMIT_ACTION);
        doThrow(new RemoteException("just for testing.")).when(action.getDataInterfaceBean())
                .saveUserAccrualThreshold(anyLong(), anyDouble());
        try {
            action.execute();
            fail("UserPreferencesActionExecutionException exception is expected.");
        } catch (UserPreferencesActionExecutionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests PaymentPreferencesAction#execute() with submit action and exception occurs in underlying
     * dataInterfaceBean.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_Submit_DataInterfaceBeanSQLException() throws Exception {
        action.setCurrentPaymentAccrualAmount(1);
        action.setAction(SUBMIT_ACTION);
        doThrow(new SQLException("just for testing.")).when(action.getDataInterfaceBean()).getUserAccrualThreshold(
                anyLong());
        try {
            action.execute();
            fail("UserPreferencesActionExecutionException exception is expected.");
        } catch (UserPreferencesActionExecutionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests PaymentPreferencesAction#prepare() method with null dataInterfaceBean attribute.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPrepare_Null_DataInterfaceBean() throws Exception {
        action.setDataInterfaceBean(null);
        try {
            action.prepare();
            fail("UserPreferencesActionConfigurationException exception is expected.");
        } catch (UserPreferencesActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests PaymentPreferencesAction#validate() method with current accrual payment less than minimum.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Less_UserHandle() throws Exception {
        action.setCurrentPaymentAccrualAmount(Constants.MINIMUM_PAYMENT_ACCRUAL_AMOUNT - 1);
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests PaymentPreferencesAction#getCurrentPaymentAccrualAmount() method.
     * </p>
     * <p>
     * currentPaymentAccrualAmount should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetCurrentPaymentAccrualAmount() {
        int currentPaymentAccrualAmount = 1;
        action.setCurrentPaymentAccrualAmount(currentPaymentAccrualAmount);
        assertEquals("getCurrentPaymentAccrualAmount() doesn't work properly.", currentPaymentAccrualAmount,
                action.getCurrentPaymentAccrualAmount());
    }

    /**
     * <p>
     * Tests PaymentPreferencesAction#setCurrentPaymentAccrualAmount(int) method.
     * </p>
     * <p>
     * currentPaymentAccrualAmount should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetCurrentPaymentAccrualAmount() {
        int currentPaymentAccrualAmount = 1;
        action.setCurrentPaymentAccrualAmount(currentPaymentAccrualAmount);
        assertEquals("setCurrentPaymentAccrualAmount() doesn't work properly.", currentPaymentAccrualAmount,
                action.getCurrentPaymentAccrualAmount());
    }

    /**
     * <p>
     * Tests PaymentPreferencesAction#getDataInterfaceBean() method.
     * </p>
     * <p>
     * dataInterfaceBean should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetDataInterfaceBean() {
        DataInterfaceBean dataInterfaceBean = getDataInterfaceBean();
        action.setDataInterfaceBean(dataInterfaceBean);
        assertSame("getDataInterfaceBean() doesn't work properly.", dataInterfaceBean, action.getDataInterfaceBean());
    }

    /**
     * <p>
     * Tests PaymentPreferencesAction#setDataInterfaceBean(DataInterfaceBean) method.
     * </p>
     * <p>
     * dataInterfaceBean should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetDataInterfaceBean() {
        DataInterfaceBean dataInterfaceBean = getDataInterfaceBean();
        action.setDataInterfaceBean(dataInterfaceBean);
        assertSame("setDataInterfaceBean() doesn't work properly.", dataInterfaceBean, action.getDataInterfaceBean());
    }
}
