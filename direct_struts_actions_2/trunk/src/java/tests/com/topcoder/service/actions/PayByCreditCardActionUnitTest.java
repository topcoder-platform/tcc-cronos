/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.service.facade.contest.ContestServiceException;
import com.topcoder.service.payment.PaymentResult;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.project.StudioCompetition;
import com.topcoder.service.studio.ContestNotFoundException;

/**
 * <p>
 * Unit tests for <code>PayByCreditCardAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PayByCreditCardActionUnitTest extends BaseStrutsSpringTestCase {

    /**
     * The instance used in testing.
     */
    private PayByCreditCardAction instance;

    /**
     * Prepares the action proxy for use with struts.
     *
     * @return the prepared proxy
     * @throws Exception if an error occurs preparing the proxy
     */
    private ActionProxy prepareActionProxy() throws Exception {
        ActionProxy proxy = getActionProxy("/mockPayByCreditCardAction");
        assertTrue("action is of wrong type", proxy.getAction() instanceof PayByCreditCardAction);

        TestHelper.injectSessionMapIntoProxy(proxy);

        return proxy;
    }

    /**
     * Sets up the environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        TestHelper.cleanupEnvironment();
        instance = new MockPayByCreditCardAction();
        instance.prepare();
    }

    /**
     * Tears down the environment.
     *
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        super.tearDown();
        TestHelper.cleanupEnvironment();
        instance = null;
    }

    /**
     * Tests the class inheritance to make sure it is correct.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testInheritance() throws Exception {
        TestHelper.assertSuperclass(instance.getClass().getSuperclass(), PayContestAction.class);
    }

    /**
     * Accuracy test for constructor. Verifies the new instance is created.
     */
    public void testConstructor() {
        assertNotNull("unable to create instance", instance);
    }

    /**
     * Accuracy test for prepare. The <code>creditCardPaymentData</code> field should be initialized.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_prepare_Accuracy() throws Exception {
        instance = new PayByCreditCardAction();
        instance.prepare();
        assertNotNull("creditCardPaymentData should not be null", TestHelper.getInstanceField(instance,
            "creditCardPaymentData"));
    }

    /**
     * Accuracy test for processPayment. The payment should be processed successfully for the studio
     * competition and correct payment result should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_processPayment_Accuracy1() throws Exception {
        StudioCompetition comp = new StudioCompetition();
        comp.setId(1);
        MockContestServiceFacade.addStudioCompetition(comp);
        instance.setContestId(comp.getId());
        instance.setProjectId(0);
        PaymentResult result = instance.processPayment();
        assertNotNull("result should not be null", result);
        assertEquals("reference number is wrong", "ref3", result.getReferenceNumber());
    }

    /**
     * Accuracy test for processPayment. The payment should be processed successfully for the software
     * competition and correct payment result should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_processPayment_Accuracy2() throws Exception {
        SoftwareCompetition comp = new SoftwareCompetition();
        comp.setId(1);
        MockContestServiceFacade.addSoftwareCompetition(comp);
        instance.setContestId(0);
        instance.setProjectId(comp.getId());
        PaymentResult result = instance.processPayment();
        assertNotNull("result should not be null", result);
        assertEquals("reference number is wrong", "ref4", result.getReferenceNumber());
    }

    /**
     * Failure test for processPayment. The studio competition wasn't found, so ContestNotFoundException is
     * expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_processPayment_Failure1() throws Exception {
        try {
            instance.setContestId(1);
            instance.setProjectId(0);
            instance.processPayment();
            fail("ContestNotFoundException is expected");
        } catch (ContestNotFoundException e) {
            // success
        }
    }

    /**
     * Failure test for processPayment. The software competition wasn't found, so ContestServiceException is
     * expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_processPayment_Failure2() throws Exception {
        try {
            instance.setContestId(0);
            instance.setProjectId(1);
            instance.processPayment();
            fail("ContestServiceException is expected");
        } catch (ContestServiceException e) {
            // success
        }
    }

    /**
     * Accuracy test for execute in struts environment. Success should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_execute_Accuracy() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        assertEquals("result of action is incorrect", "success", proxy.execute());
    }

    /**
     * Accuracy test for execute method in struts environment. The country is not US and state is null, which
     * is valid.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_execute_Accuracy2() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("country", "Russian Federation");
        TestHelper.getFieldValues().put("state", null);

        proxy.execute();

        assertFalse("There should be no field errors since state isn't required", action.hasFieldErrors());
    }

    /**
     * Failure test for execute method in struts environment. The country is US, but state is null so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_execute_MissingState() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("country", "Us ");
        TestHelper.getFieldValues().put("state", null);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "state",
            "For country = US, state is required and must be 2 characters");
    }

    /**
     * Failure test for execute method in struts environment. The country is US, but state is empty so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_execute_EmptyState() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("country", "Us ");
        TestHelper.getFieldValues().put("state", "  ");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "state",
            "For country = US, state is required and must be 2 characters");
    }

    /**
     * Failure test for execute method in struts environment. The country is US, but state is too short so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_execute_StateTooShort() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("country", "Us ");
        TestHelper.getFieldValues().put("state", "a  ");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "state",
            "For country = US, state is required and must be 2 characters");
    }

    /**
     * Failure test for execute method in struts environment. The country is US, but state is too long so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_execute_StateTooLong() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("country", "Us ");
        TestHelper.getFieldValues().put("state", "abc  ");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "state",
            "For country = US, state is required and must be 2 characters");
    }

    /**
     * Failure test for execute method in struts environment. The card type is American Express but CSC is
     * only 3 digits, so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_execute_WrongCSCLength1() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("cardType", "AMERican ExPREss   ");
        TestHelper.getFieldValues().put("csc", "123 ");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "csc",
            "For American Express, the card security code must be 4 digits");
    }

    /**
     * Failure test for execute method in struts environment. The card type is not American Express but CSC is
     * 4 digits, so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_execute_WrongCSCLength2() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("cardType", "VISa");
        TestHelper.getFieldValues().put("csc", "1234 ");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "csc",
            "For non American Express, the card security code must be 3 digits");
    }

    /**
     * Failure test for execute method in struts environment. The card has expired, so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_execute_CardExpired() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        Calendar cal = Calendar.getInstance();
        int curYear = cal.get(Calendar.YEAR);
        int curMonth = cal.get(Calendar.MONTH) + 1;

        if (curMonth == 1) {
            // if month is January, then we cannot run this test because we would have to
            // decrement year, and if we decrement year then cardYear validation will
            // fail since cardYear must be >= current year
            return;
        }
        --curMonth;
        TestHelper.getFieldValues().put("cardExpiryYear", curYear + "");
        TestHelper.getFieldValues().put("cardExpiryMonth", curMonth + "");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "cardExpiryYear", "The credit card has expired");
    }

    /**
     * Accuracy test for getCardNumber. Verifies the returned value is correct.
     */
    @Test
    public void test_getCardNumber_Accuracy() {
        instance = new PayByCreditCardAction();
        instance.prepare();
        assertEquals("incorrect default value", null, instance.getCardNumber());
        instance.setCardNumber("1");
        assertEquals("incorrect value after setting", "1", instance.getCardNumber());
    }

    /**
     * Accuracy Test for setCardNumber. Verifies the assigned value is correct.
     */
    @Test
    public void test_setCardNumber_Accuracy() {
        instance.setCardNumber("1");
        assertEquals("incorrect value after setting", "1", instance.getCardNumber());
    }

    /**
     * Failure test for setCardNumber method in struts environment. The value for the setter is null, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCardNumber_Null() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("cardNumber", null);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "cardNumber", "cardNumber cannot be null or empty string");
    }

    /**
     * Failure test for setCardNumber method in struts environment. The value for the setter is empty, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCardNumber_Empty() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("cardNumber", " ");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "cardNumber", "cardNumber cannot be null or empty string");
    }

    /**
     * Accuracy test for getCardType. Verifies the returned value is correct.
     */
    @Test
    public void test_getCardType_Accuracy() {
        instance = new PayByCreditCardAction();
        instance.prepare();
        assertEquals("incorrect default value", null, instance.getCardType());
        instance.setCardType("Visa");
        assertEquals("incorrect value after setting", "Visa", instance.getCardType());
    }

    /**
     * Accuracy Test for setCardType. Verifies the assigned value is correct.
     */
    @Test
    public void test_setCardType_Accuracy() {
        instance.setCardType("Visa");
        assertEquals("incorrect value after setting", "Visa", instance.getCardType());
    }

    /**
     * Accuracy test for setCardType method in struts environment. Verifies that Visa is considered a valid
     * card type and the validation is case insensitive.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCardType_Accuracy2() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();
        TestHelper.getFieldValues().put("cardType", "VIsa  ");
        proxy.execute();
        assertFalse("There should be no field errors since card type is valid", action.hasFieldErrors());
    }

    /**
     * Accuracy test for setCardType method in struts environment. Verifies that MasterCard is considered a
     * valid card type and the validation is case insensitive.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCardType_Accuracy3() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();
        TestHelper.getFieldValues().put("cardType", "masterCARd  ");
        proxy.execute();
        assertFalse("There should be no field errors since card type is valid", action.hasFieldErrors());
    }

    /**
     * Accuracy test for setCardType method in struts environment. Verifies that American Express is
     * considered a valid card type and the validation is case insensitive.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCardType_Accuracy4() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();
        TestHelper.getFieldValues().put("cardType", "AmeriCAN exPresS  ");
        TestHelper.getFieldValues().put("csc", "1234");
        proxy.execute();
        assertFalse("There should be no field errors since card type is valid", action.hasFieldErrors());
    }

    /**
     * Failure test for setCardType method in struts environment. The value for the setter is null, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCardType_Null() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("cardType", null);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "cardType",
            "cardType must be Visa, MasterCard or American Express");
    }

    /**
     * Failure test for setCardType method in struts environment. The value for the setter is empty, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCardType_Empty() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("cardType", " ");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "cardType",
            "cardType must be Visa, MasterCard or American Express");
    }

    /**
     * Failure test for setCardType method in struts environment. The value for the setter is not a valid card
     * type, so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCardType_InvalidCardType() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("cardType", "Discover");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "cardType",
            "cardType must be Visa, MasterCard or American Express");
    }

    /**
     * Accuracy test for getCardExpiryMonth. Verifies the returned value is correct.
     */
    @Test
    public void test_getCardExpiryMonth_Accuracy() {
        instance = new PayByCreditCardAction();
        instance.prepare();
        assertEquals("incorrect default value", null, instance.getCardExpiryMonth());
        instance.setCardExpiryMonth("5");
        assertEquals("incorrect value after setting", "5", instance.getCardExpiryMonth());
    }

    /**
     * Accuracy Test for setCardExpiryMonth. Verifies the assigned value is correct.
     */
    @Test
    public void test_setCardExpiryMonth_Accuracy() {
        instance.setCardExpiryMonth("5");
        assertEquals("incorrect value after setting", "5", instance.getCardExpiryMonth());
    }

    /**
     * Failure test for setCardExpiryMonth method in struts environment. The value for the setter is null, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCardExpiryMonth_Null() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("cardExpiryMonth", null);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "cardExpiryMonth",
            "cardExpiryMonth must be a valid integer month");
    }

    /**
     * Failure test for setCardExpiryMonth method in struts environment. The value for the setter is empty, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCardExpiryMonth_Empty() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("cardExpiryMonth", " ");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "cardExpiryMonth",
            "cardExpiryMonth must be a valid integer month");
    }

    /**
     * Failure test for setCardExpiryMonth method in struts environment. The value for the setter is non
     * numeric, so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCardExpiryMonth_NonNumber() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("cardExpiryMonth", "abc");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "cardExpiryMonth",
            "cardExpiryMonth must be a valid integer month");
    }

    /**
     * Failure test for setCardExpiryMonth method in struts environment. The value for the setter is in wrong
     * range, so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCardExpiryMonth_WrongRange() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("cardExpiryMonth", "13");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "cardExpiryMonth", "cardExpiryMonth must be between 1 and 12");
    }

    /**
     * Accuracy test for getCardExpiryYear. Verifies the returned value is correct.
     */
    @Test
    public void test_getCardExpiryYear_Accuracy() {
        instance = new PayByCreditCardAction();
        instance.prepare();
        assertEquals("incorrect default value", null, instance.getCardExpiryYear());
        int year = Calendar.getInstance().get(Calendar.YEAR) + 5;
        instance.setCardExpiryYear(year + "");
        assertEquals("incorrect value after setting", year + "", instance.getCardExpiryYear());
    }

    /**
     * Accuracy Test for setCardExpiryYear. Verifies the assigned value is correct.
     */
    @Test
    public void test_setCardExpiryYear_Accuracy() {
        int year = Calendar.getInstance().get(Calendar.YEAR) + 5;
        instance.setCardExpiryYear(year + "");
        assertEquals("incorrect value after setting", year + "", instance.getCardExpiryYear());
    }

    /**
     * Failure test for setCardExpiryYear method in struts environment. The value for the setter is null, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCardExpiryYear_Null() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("cardExpiryYear", null);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "cardExpiryYear", "cardExpiryYear must be a valid integer year");
    }

    /**
     * Failure test for setCardExpiryYear method in struts environment. The value for the setter is empty, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCardExpiryYear_Empty() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("cardExpiryYear", " ");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "cardExpiryYear", "cardExpiryYear must be a valid integer year");
    }

    /**
     * Failure test for setCardExpiryYear method in struts environment. The value for the setter is non
     * numeric, so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCardExpiryYear_NonNumber() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("cardExpiryYear", "abc");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "cardExpiryYear", "cardExpiryYear must be a valid integer year");
    }

    /**
     * Failure test for setCardExpiryYear method in struts environment. The value for the setter is less than
     * current year, so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCardExpiryYear_LessThanCurYear() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        int year = Calendar.getInstance().get(Calendar.YEAR) - 1;
        TestHelper.getFieldValues().put("cardExpiryYear", year + "");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "cardExpiryYear", "cardExpiryYear must be >= current year");
    }

    /**
     * Accuracy test for getFirstName. Verifies the returned value is correct.
     */
    @Test
    public void test_getFirstName_Accuracy() {
        instance = new PayByCreditCardAction();
        instance.prepare();
        assertNull("incorrect default value", instance.getFirstName());
        instance.setFirstName("a");
        assertEquals("incorrect value after setting", "a", instance.getFirstName());
    }

    /**
     * Accuracy Test for setFirstName. Verifies the assigned value is correct.
     */
    @Test
    public void test_setFirstName_Accuracy() {
        instance.setFirstName("a");
        assertEquals("incorrect value after setting", "a", instance.getFirstName());
    }

    /**
     * Failure test for setFirstName method in struts environment. The value for the setter is null, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setFirstName_Null() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("firstName", null);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "firstName", "firstName cannot be null or empty string");
    }

    /**
     * Failure test for setFirstName method in struts environment. The value for the setter is empty, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setFirstName_Empty() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("firstName", " ");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "firstName", "firstName cannot be null or empty string");
    }

    /**
     * Accuracy test for getLastName. Verifies the returned value is correct.
     */
    @Test
    public void test_getLastName_Accuracy() {
        instance = new PayByCreditCardAction();
        instance.prepare();
        assertNull("incorrect default value", instance.getLastName());
        instance.setLastName("a");
        assertEquals("incorrect value after setting", "a", instance.getLastName());
    }

    /**
     * Accuracy Test for setLastName. Verifies the assigned value is correct.
     */
    @Test
    public void test_setLastName_Accuracy() {
        instance.setLastName("a");
        assertEquals("incorrect value after setting", "a", instance.getLastName());
    }

    /**
     * Failure test for setLastName method in struts environment. The value for the setter is null, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setLastName_Null() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("lastName", null);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "lastName", "lastName cannot be null or empty string");
    }

    /**
     * Failure test for setLastName method in struts environment. The value for the setter is empty, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setLastName_Empty() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("lastName", " ");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "lastName", "lastName cannot be null or empty string");
    }

    /**
     * Accuracy test for getZipCode. Verifies the returned value is correct.
     */
    @Test
    public void test_getZipCode_Accuracy() {
        instance = new PayByCreditCardAction();
        instance.prepare();
        assertNull("incorrect default value", instance.getZipCode());
        instance.setZipCode("123456");
        assertEquals("incorrect value after setting", "123456", instance.getZipCode());
    }

    /**
     * Accuracy Test for setZipCode. Verifies the assigned value is correct.
     */
    @Test
    public void test_setZipCode_Accuracy() {
        instance.setZipCode("123456");
        assertEquals("incorrect value after setting", "123456", instance.getZipCode());
    }

    /**
     * Failure test for setZipCode method in struts environment. The value for the setter is null, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setZipCode_Null() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("zipCode", null);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "zipCode", "zipCode cannot be null or empty string");
    }

    /**
     * Failure test for setZipCode method in struts environment. The value for the setter is empty, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setZipCode_Empty() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("zipCode", " ");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "zipCode", "zipCode cannot be null or empty string");
    }

    /**
     * Failure test for setZipCode method in struts environment. The value for the setter is too long, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setZipCode_TooLong() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("zipCode", "01234567891");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "zipCode",
            "zipCode is required and must be between 1 and 10 digits");
    }

    /**
     * Failure test for setZipCode method in struts environment. The value for the setter contains a non
     * digit, so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setZipCode_NonDigit() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("zipCode", "01234a5");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "zipCode",
            "zipCode is required and must be between 1 and 10 digits");
    }

    /**
     * Accuracy test for getAddress. Verifies the returned value is correct.
     */
    @Test
    public void test_getAddress_Accuracy() {
        instance = new PayByCreditCardAction();
        instance.prepare();
        assertNull("incorrect default value", instance.getAddress());
        instance.setAddress("a");
        assertEquals("incorrect value after setting", "a", instance.getAddress());
    }

    /**
     * Accuracy Test for setAddress. Verifies the assigned value is correct.
     */
    @Test
    public void test_setAddress_Accuracy() {
        instance.setAddress("a");
        assertEquals("incorrect value after setting", "a", instance.getAddress());
    }

    /**
     * Failure test for setAddress method in struts environment. The value for the setter is null, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setAddress_Null() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("address", null);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "address",
            "address cannot be null and must be between 1 and 100 characters");
    }

    /**
     * Failure test for setAddress method in struts environment. The value for the setter is empty, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setAddress_Empty() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("address", " ");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "address",
            "address cannot be null and must be between 1 and 100 characters");
    }

    /**
     * Failure test for setAddress method in struts environment. The value for the setter is too long, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setAddress_TooLong() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("address", TestHelper.STRING_OVER_100);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "address",
            "address cannot be null and must be between 1 and 100 characters");
    }

    /**
     * Accuracy test for getCity. Verifies the returned value is correct.
     */
    @Test
    public void test_getCity_Accuracy() {
        instance = new PayByCreditCardAction();
        instance.prepare();
        assertNull("incorrect default value", instance.getCity());
        instance.setCity("a");
        assertEquals("incorrect value after setting", "a", instance.getCity());
    }

    /**
     * Accuracy Test for setCity. Verifies the assigned value is correct.
     */
    @Test
    public void test_setCity_Accuracy() {
        instance.setCity("a");
        assertEquals("incorrect value after setting", "a", instance.getCity());
    }

    /**
     * Failure test for setCity method in struts environment. The value for the setter is null, so validation
     * should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCity_Null() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("city", null);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "city",
            "city cannot be null and must be between 1 and 100 characters");
    }

    /**
     * Failure test for setCity method in struts environment. The value for the setter is empty, so validation
     * should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCity_Empty() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("city", " ");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "city",
            "city cannot be null and must be between 1 and 100 characters");
    }

    /**
     * Failure test for setCity method in struts environment. The value for the setter is too long, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCity_TooLong() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("city", TestHelper.STRING_OVER_100);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "city",
            "city cannot be null and must be between 1 and 100 characters");
    }

    /**
     * Accuracy test for getState. Verifies the returned value is correct.
     */
    @Test
    public void test_getState_Accuracy() {
        instance = new PayByCreditCardAction();
        instance.prepare();
        assertNull("incorrect default value", instance.getState());
        instance.setState("a");
        assertEquals("incorrect value after setting", "a", instance.getState());
    }

    /**
     * Accuracy Test for setState. Verifies the assigned value is correct.
     */
    @Test
    public void test_setState_Accuracy() {
        instance.setState("a");
        assertEquals("incorrect value after setting", "a", instance.getState());
    }

    /**
     * Accuracy test for getCountry. Verifies the returned value is correct.
     */
    @Test
    public void test_getCountry_Accuracy() {
        instance = new PayByCreditCardAction();
        instance.prepare();
        assertNull("incorrect default value", instance.getCountry());
        instance.setCountry("a");
        assertEquals("incorrect value after setting", "a", instance.getCountry());
    }

    /**
     * Accuracy Test for setCountry. Verifies the assigned value is correct.
     */
    @Test
    public void test_setCountry_Accuracy() {
        instance.setCountry("a");
        assertEquals("incorrect value after setting", "a", instance.getCountry());
    }

    /**
     * Failure test for setCountry method in struts environment. The value for the setter is null, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCountry_Null() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("country", null);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "country",
            "country cannot be null and must be between 1 and 100 characters");
    }

    /**
     * Failure test for setCountry method in struts environment. The value for the setter is empty, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCountry_Empty() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("country", " ");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "country",
            "country cannot be null and must be between 1 and 100 characters");
    }

    /**
     * Failure test for setCountry method in struts environment. The value for the setter is too long, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCountry_TooLong() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("country", TestHelper.STRING_OVER_100);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "country",
            "country cannot be null and must be between 1 and 100 characters");
    }

    /**
     * Accuracy test for getPhone. Verifies the returned value is correct.
     */
    @Test
    public void test_getPhone_Accuracy() {
        instance = new PayByCreditCardAction();
        instance.prepare();
        assertNull("incorrect default value", instance.getPhone());
        instance.setPhone("(123) 456-7890");
        assertEquals("incorrect value after setting", "(123) 456-7890", instance.getPhone());
    }

    /**
     * Accuracy Test for setPhone. Verifies the assigned value is correct.
     */
    @Test
    public void test_setPhone_Accuracy() {
        instance.setPhone("(123) 456-7890");
        assertEquals("incorrect value after setting", "(123) 456-7890", instance.getPhone());
    }

    /**
     * Failure test for setPhone method in struts environment. The value for the setter is in wrong format
     * (not enough digits), so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setPhone_WrongFormat1() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("phone", "(123) 456-78");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "phone", "phone must be in \"(xxx) xxx-xxxx\" format");
    }

    /**
     * Failure test for setPhone method in struts environment. The value for the setter is in wrong format
     * (too many digits), so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setPhone_WrongFormat2() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("phone", "(123) 456-78999");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "phone", "phone must be in \"(xxx) xxx-xxxx\" format");
    }

    /**
     * Failure test for setPhone method in struts environment. The value for the setter is in wrong format
     * (letter is present), so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setPhone_WrongFormat3() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("phone", "(123) 456-789a");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "phone", "phone must be in \"(xxx) xxx-xxxx\" format");
    }

    /**
     * Failure test for setPhone method in struts environment. The value for the setter is empty, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setPhone_Empty() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("phone", " ");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "phone", "phone cannot be null or empty string");
    }

    /**
     * Failure test for setPhone method in struts environment. The value for the setter is null, so validation
     * should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setPhone_Null() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("phone", null);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "phone", "phone cannot be null or empty string");
    }

    /**
     * Accuracy test for getEmail. Verifies the returned value is correct.
     */
    @Test
    public void test_getEmail_Accuracy() {
        instance = new PayByCreditCardAction();
        instance.prepare();
        assertNull("incorrect default value", instance.getEmail());
        instance.setEmail("joey@aol.com");
        assertEquals("incorrect value after setting", "joey@aol.com", instance.getEmail());
    }

    /**
     * Accuracy Test for setEmail. Verifies the assigned value is correct.
     */
    @Test
    public void test_setEmail_Accuracy() {
        instance.setEmail("joey@aol.com");
        assertEquals("incorrect value after setting", "joey@aol.com", instance.getEmail());
    }

    /**
     * Failure test for setEmail method in struts environment. The value for the setter is in wrong format
     * (it's an invalid email address), so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setEmail_WrongFormat1() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("email", "johndoe");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "email", "email must be a valid email address");
    }

    /**
     * Failure test for setEmail method in struts environment. The value for the setter is in wrong format
     * (it's an invalid email address), so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setEmail_WrongFormat2() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("email", "johndoe@");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "email", "email must be a valid email address");
    }

    /**
     * Failure test for setEmail method in struts environment. The value for the setter is in wrong format
     * (it's an invalid email address), so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setEmail_WrongFormat3() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("email", "johndoe@nowhere@cat.com");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "email", "email must be a valid email address");
    }

    /**
     * Failure test for setEmail method in struts environment. The value for the setter is null, so validation
     * should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setEmail_Null() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("email", null);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "email",
            "email must not be null or empty and must be <= 50 characters");
    }

    /**
     * Failure test for setEmail method in struts environment. The value for the setter is empty, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setEmail_Empty() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("email", " ");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "email",
            "email must not be null or empty and must be <= 50 characters");
    }

    /**
     * Failure test for setEmail method in struts environment. The value for the setter is too long, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setEmail_TooLong() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("email", TestHelper.STRING_OVER_50);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "email",
            "email must not be null or empty and must be <= 50 characters");
    }

    /**
     * Accuracy test for getAmount. Verifies the returned value is correct.
     */
    @Test
    public void test_getAmount_Accuracy() {
        instance = new PayByCreditCardAction();
        instance.prepare();
        assertNull("incorrect default value", instance.getAmount());
        instance.setAmount("$14.23");
        assertEquals("incorrect value after setting", "$14.23", instance.getAmount());
    }

    /**
     * Accuracy Test for setAmount. Verifies the assigned value is correct.
     */
    @Test
    public void test_setAmount_Accuracy() {
        instance.setAmount("$14.23");
        assertEquals("incorrect value after setting", "$14.23", instance.getAmount());
    }

    /**
     * Accuracy test for getCsc. Verifies the returned value is correct.
     */
    @Test
    public void test_getCsc_Accuracy() {
        instance = new PayByCreditCardAction();
        instance.prepare();
        assertNull("incorrect default value", instance.getCsc());
        instance.setCsc("a");
        assertEquals("incorrect value after setting", "a", instance.getCsc());
    }

    /**
     * Accuracy Test for setCsc. Verifies the assigned value is correct.
     */
    @Test
    public void test_setCsc_Accuracy() {
        instance.setCsc("123");
        assertEquals("incorrect value after setting", "123", instance.getCsc());
    }

    /**
     * Accuracy test for setCsc method in struts environment. Verifies that 3 digit CSC is considered valid.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCsc_3Digit() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();
        TestHelper.getFieldValues().put("csc", "123  ");
        proxy.execute();
        assertFalse("There should be no field errors since csc is valid", action.hasFieldErrors());
    }

    /**
     * Accuracy test for setCsc method in struts environment. Verifies that 4 digit CSC is considered valid.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCsc_4Digit() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();
        TestHelper.getFieldValues().put("csc", "1234  ");
        TestHelper.getFieldValues().put("cardType", "American Express ");
        proxy.execute();
        assertFalse("There should be no field errors since csc is valid", action.hasFieldErrors());
    }

    /**
     * Failure test for setCsc method in struts environment. The value for the setter is null, so validation
     * should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCsc_Null() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("csc", null);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "csc",
            "card security code is required and must consist of 3 or 4 digits");
    }

    /**
     * Failure test for setCsc method in struts environment. The value for the setter is empty, so validation
     * should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCsc_Empty() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("csc", " ");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "csc",
            "card security code is required and must consist of 3 or 4 digits");
    }

    /**
     * Failure test for setCsc method in struts environment. The value for the setter is too long, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCsc_TooLong() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("csc", "12345");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "csc",
            "card security code is required and must consist of 3 or 4 digits");
    }

    /**
     * Failure test for setCsc method in struts environment. The value for the setter is too short, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCsc_TooShort() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("csc", "12");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "csc",
            "card security code is required and must consist of 3 or 4 digits");
    }

    /**
     * Failure test for setCsc method in struts environment. The value for the setter contains a non digit, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCsc_NonDigit() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("csc", "12a");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "csc",
            "card security code is required and must consist of 3 or 4 digits");
    }

}
