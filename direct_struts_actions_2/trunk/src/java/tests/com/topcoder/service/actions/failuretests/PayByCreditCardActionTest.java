/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.failuretests;

import org.apache.struts2.StrutsSpringTestCase;

import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.annotations.Before;
import com.topcoder.service.actions.PayByCreditCardAction;

/**
 * Failure tests for <code>PayByCreditCardAction</code>.
 * @author moon.river
 * @version 1.0
 */
public class PayByCreditCardActionTest extends StrutsSpringTestCase {

    /**
     * Instance to test.
     */
    private PayByCreditCardAction instance;

    /**
     * Proxy.
     */
    private ActionProxy proxy;

    /**
     * Sets up the environment.
     * @throws java.lang.Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        instance = new PayByCreditCardAction();
        instance.prepare();

        // prepare the action proxy
        proxy = getActionProxy("/FailurePayByCreditCardAction");
        Helper.setUpSession(proxy);

        FailurePayByCreditCardAction.address = "test";
        FailurePayByCreditCardAction.cardExpiryMonth = "1";
        FailurePayByCreditCardAction.cardExpiryYear = "3011";
        FailurePayByCreditCardAction.cardNumber = "4392250911111111";
        FailurePayByCreditCardAction.cardType = "VISA";
        FailurePayByCreditCardAction.city = "LA";
        FailurePayByCreditCardAction.country = "US";
        FailurePayByCreditCardAction.csc = "111";
        FailurePayByCreditCardAction.email = "a@b.com";
        FailurePayByCreditCardAction.firstName = "test";
        FailurePayByCreditCardAction.lastName = "test";
        FailurePayByCreditCardAction.zipCode = "92100";
    }

    /**
     * Cleans up the environment.
     * @throws java.lang.Exception to JUnit
     */
    public void tearDown() throws Exception {
        instance = null;
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testCardNumber_Null() throws Exception {
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        FailurePayByCreditCardAction.cardNumber = null;

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 3, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testCardNumber_Empty() throws Exception {
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        FailurePayByCreditCardAction.cardNumber = "";

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 3, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testCardType_Null() throws Exception {
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        FailurePayByCreditCardAction.cardType = null;

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 3, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testCardType_Empty() throws Exception {
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        FailurePayByCreditCardAction.cardType = "";

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 3, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testExpiryYear_Negative() throws Exception {
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        FailurePayByCreditCardAction.cardExpiryYear = "-1";

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 3, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testExpiryYear_TooBig() throws Exception {
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        FailurePayByCreditCardAction.cardExpiryYear = "2000";

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 3, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testExpiryMonth_Negative() throws Exception {
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        FailurePayByCreditCardAction.cardExpiryMonth = "-1";

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 3, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testExpiryMonth_TooBig() throws Exception {
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        FailurePayByCreditCardAction.cardExpiryMonth = "13";

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 3, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testFirstName_Null() throws Exception {
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        FailurePayByCreditCardAction.firstName = null;

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 3, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testFirstName_Empty() throws Exception {
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        FailurePayByCreditCardAction.firstName = "";

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 3, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testLastName_Null() throws Exception {
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        FailurePayByCreditCardAction.lastName = null;

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 3, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testLastName_Empty() throws Exception {
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        FailurePayByCreditCardAction.lastName = "";

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 3, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testZipCode_Null() throws Exception {
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        FailurePayByCreditCardAction.zipCode = null;

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 3, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testZipCode_Empty() throws Exception {
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        FailurePayByCreditCardAction.zipCode = "";

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 3, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testZipCode_TooLong() throws Exception {
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        FailurePayByCreditCardAction.zipCode = "11111111111111111111111111111111111111";

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 3, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testAddress_Null() throws Exception {
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        FailurePayByCreditCardAction.address = null;

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 3, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testAddress_Empty() throws Exception {
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        FailurePayByCreditCardAction.address = "";

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 3, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testAddress_TooLong() throws Exception {
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        FailurePayByCreditCardAction.address = "1";
        for (int i = 0; i < 100; i++) {
            FailurePayByCreditCardAction.address += "1";            
        }

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 3, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testCity_Null() throws Exception {
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        FailurePayByCreditCardAction.city = null;

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 3, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testCity_Empty() throws Exception {
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        FailurePayByCreditCardAction.city = "";

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 3, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testCity_TooLong() throws Exception {
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        FailurePayByCreditCardAction.city = "1";
        for (int i = 0; i < 100; i++) {
            FailurePayByCreditCardAction.city += "1";            
        }

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 3, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testPhone_Null() throws Exception {
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        FailurePayByCreditCardAction.phone = null;

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 2, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testPhone_Empty() throws Exception {
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        FailurePayByCreditCardAction.phone = "";

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 2, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testPhone_TooLong() throws Exception {
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        FailurePayByCreditCardAction.phone = "1";
        for (int i = 0; i < 100; i++) {
            FailurePayByCreditCardAction.city += "1";            
        }

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 3, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testPhone_BadFormat() throws Exception {
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        FailurePayByCreditCardAction.phone = "abc";

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 2, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testEmail_Null() throws Exception {
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        FailurePayByCreditCardAction.email = null;

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 3, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testEmail_Empty() throws Exception {
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        FailurePayByCreditCardAction.email = "";

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 3, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testEmail_BadFormat() throws Exception {
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        FailurePayByCreditCardAction.email = "abc";

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 3, action.getFieldErrors().size());
    }
}
