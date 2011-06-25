/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.accuracytests;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import junit.framework.TestSuite;
import junit.framework.Test;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.actions.miscellaneous.PaymentPreferencesAction;
import com.topcoder.web.tc.controller.legacy.pacts.bean.DataInterfaceBean;

/**
 * <p>
 * Accuracy Unit test cases for PaymentPreferencesAction.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class PaymentPreferencesActionAccuracyTests extends BaseAccuracyTest {
    /**
     * <p>PaymentPreferencesAction instance for testing.</p>
     */
    private PaymentPreferencesAction instance;

    /**
     * <p>Setup test environment.</p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        super.setUp();
        ActionProxy proxy = getActionProxy("/payment");
        instance = (PaymentPreferencesAction) proxy.getAction();
    }

    /**
     * <p>Tears down test environment.</p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * <p>Return all tests.</p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(PaymentPreferencesActionAccuracyTests.class);
    }

    /**
     * <p>Tests ctor PaymentPreferencesAction#PaymentPreferencesAction() for accuracy.</p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create PaymentPreferencesAction instance.", instance);
    }

    /**
     * <p>Tests PaymentPreferencesAction#setCurrentPaymentAccrualAmount(int) for accuracy.</p>
     */
    public void testSetCurrentPaymentAccrualAmount() {
        instance.setCurrentPaymentAccrualAmount(1);
        assertEquals("Failed to setCurrentPaymentAccrualAmount correctly.", 1,
            instance.getCurrentPaymentAccrualAmount());
    }

    /**
     * <p>Tests PaymentPreferencesAction#setDataInterfaceBean(DataInterfaceBean) for accuracy.</p>
     */
    public void testSetDataInterfaceBean() {
        DataInterfaceBean dataInterfaceBean = getDataInterfaceBean();
        instance.setDataInterfaceBean(dataInterfaceBean);
        assertSame("Failed to setDataInterfaceBean correctly.", dataInterfaceBean, instance.getDataInterfaceBean());
    }

    /**
     * <p>Tests PaymentPreferencesAction#execute() for accuracy.</p>
     * @throws Exception to JUnit
     */
    public void testExecute() throws Exception {
        setBasePreferencesActionDAOs(instance);
        putKeyValueToSession(instance.getBasicAuthenticationSessionKey(), createAuthentication());
        User loggedInUser = createUser(1L, "First", "Second", "handle");
        when(instance.getUserDao().find(1L)).thenReturn(loggedInUser);
        instance.setDataInterfaceBean(getDataInterfaceBean());
        instance.setAction(SUBMIT_ACTION);

        assertEquals("Failed to execute correctly.", ActionSupport.SUCCESS, instance.execute());
        verify(instance.getAuditDao(), times(1)).audit(any(AuditRecord.class));
    }

}