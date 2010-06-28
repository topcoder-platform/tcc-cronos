/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.service.facade.direct.ContestNotFoundException;
import com.topcoder.service.facade.direct.ContestPrize;

/**
 * <p>
 * Unit tests for <code>ActiveContestPrizeIncreasingAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ActiveContestPrizeIncreasingActionUnitTest extends BaseStrutsSpringTestCase {

    /**
     * The instance used in testing.
     */
    private ActiveContestPrizeIncreasingAction instance;

    /**
     * Prepares the action proxy for use with struts.
     *
     * @return the prepared proxy
     * @throws Exception if an error occurs preparing the proxy
     */
    private ActionProxy prepareActionProxy() throws Exception {
        ActionProxy proxy = getActionProxy("/activeContestPrizeIncreasingAction");
        assertTrue("action is of wrong type", proxy.getAction() instanceof ActiveContestPrizeIncreasingAction);

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
        TestHelper.setupEnvironment();
        instance = new ActiveContestPrizeIncreasingAction();
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
     */
    @Test
    public void testInheritance() {
        assertTrue("The inheritance is wrong", instance instanceof BaseDirectStrutsAction);
    }

    /**
     * Accuracy test for constructor. Verifies the new instance is created.
     */
    @Test
    public void testConstructor() {
        assertNotNull("unable to create instance", instance);
    }

    /**
     * Accuracy test for executeAction. The contest/milestone prize amounts should be increased properly.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_Accuracy1() throws Exception {
        instance.setDirectServiceFacade(new MockDirectServiceFacade());

        // prepare the prize data
        instance.setContestId(1);
        List<Double> contestPrizes = new ArrayList<Double>();
        contestPrizes.add(140.25);
        contestPrizes.add(55.77);
        instance.setContestPrizes(contestPrizes);
        List<Double> milestonePrizes = new ArrayList<Double>();
        milestonePrizes.add(12345.67);
        milestonePrizes.add(12345.67);
        instance.setMilestonePrizes(milestonePrizes);

        // execute the action
        instance.executeAction();

        // validate that correct data is in model
        Object result = instance.getModel().getData("result");
        assertNull("no result should be in model", result);

        // validate that prizes were updated with increased prize amounts
        ContestPrize updatedPrize = MockDirectServiceFacade.getContestPrize(1);
        assertEquals("wrong number of contest prizes", 2, updatedPrize.getContestPrizes().length);
        assertEquals("wrong prize amount for first contest prize", contestPrizes.get(0), updatedPrize
            .getContestPrizes()[0]);
        assertEquals("wrong prize amount for second contest prize", contestPrizes.get(1), updatedPrize
            .getContestPrizes()[1]);
        assertEquals("wrong number of milestone prizes", 2, updatedPrize.getMilestonePrizes().length);
        assertEquals("wrong prize amount for first milestone prize", milestonePrizes.get(0), updatedPrize
            .getMilestonePrizes()[0]);
        assertEquals("wrong prize amount for second contest prize", milestonePrizes.get(1), updatedPrize
            .getMilestonePrizes()[1]);
        assertTrue("wrong value for isEqualMilestonePrize", updatedPrize.isEqualMilestonePrize());
    }

    /**
     * Accuracy test for executeAction. The contest prize amounts should be increased properly. No
     * milestone prizes are specified in this test.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_Accuracy2() throws Exception {
        instance.setDirectServiceFacade(new MockDirectServiceFacade());

        // prepare the prize data
        instance.setContestId(4);
        List<Double> contestPrizes = new ArrayList<Double>();
        contestPrizes.add(1001.0);
        contestPrizes.add(501.0);
        instance.setContestPrizes(contestPrizes);
        List<Double> milestonePrizes = new ArrayList<Double>();
        instance.setMilestonePrizes(milestonePrizes);

        // execute the action
        instance.executeAction();

        // validate that correct data is in model
        Object result = instance.getModel().getData("result");
        assertNull("no result should be in model", result);

        // validate that prizes were updated with increased prize amounts
        ContestPrize updatedPrize = MockDirectServiceFacade.getContestPrize(4);
        assertEquals("wrong number of contest prizes", 2, updatedPrize.getContestPrizes().length);
        assertEquals("wrong prize amount for first contest prize", contestPrizes.get(0), updatedPrize
            .getContestPrizes()[0]);
        assertEquals("wrong prize amount for second contest prize", contestPrizes.get(1), updatedPrize
            .getContestPrizes()[1]);
        assertEquals("wrong number of milestone prizes", 0, updatedPrize.getMilestonePrizes().length);
    }

    /**
     * Failure test for executeAction. The <code>DirectServiceFacade</code> hasn't been injected,
     * so IllegalStateException is expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_MissingFacade() throws Exception {
        try {
            instance.executeAction();
            fail("IllegalStateException is expected");
        } catch (IllegalStateException e) {
            // make sure exception was stored in model
            Object result = instance.getResult();
            assertNotNull("result from model shouldn't be null", result);
            assertTrue("result from model is not correct type", result instanceof IllegalStateException);
        }
    }

    /**
     * Failure test for executeAction. The old milestone prizes fetched from the facade are null,
     * so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_OldMilestonePrizesNull() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        ActiveContestPrizeIncreasingAction action = (ActiveContestPrizeIncreasingAction) proxy.getAction();

        List<Double> list = new ArrayList<Double>();
        list.add(Double.valueOf(1));
        list.add(Double.valueOf(2));

        // prepare the prize data
        instance.setContestId(3);
        List<Double> contestPrizes = new ArrayList<Double>();
        contestPrizes.add(1001.0);
        contestPrizes.add(501.0);
        TestHelper.getFieldValues().put("contestPrizes", contestPrizes);
        TestHelper.getFieldValues().put("milestonePrizes", new ArrayList<Double>());
        TestHelper.getFieldValues().put("contestId", 3);
        TestHelper.getFieldValues().put("studio", true);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "milestonePrizes",
            "The old and new milestonePrizes must have same number of elements");
    }

    /**
     * Failure test for executeAction. The contest wasn't found, so <code>ContestNotFound</code> exception
     * should be stored in model and thrown.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_ContestNotFound() throws Exception {
        ActiveContestPrizeIncreasingAction action = null;
        try {
            // execute the action using the action proxy
            ActionProxy proxy = prepareActionProxy();
            action = (ActiveContestPrizeIncreasingAction) proxy.getAction();

            TestHelper.getFieldValues().put("contestId", 12345);
            proxy.execute();

            fail("ContestNotFoundException is expected");

        } catch (ContestNotFoundException e) {
            // make sure exception was stored in model
            Object result = action.getResult();
            assertNotNull("result from model shouldn't be null", result);
            assertTrue("result from model is not correct type", result instanceof ContestNotFoundException);
        }
    }

    /**
     * Failure test for executeAction. There are contest prize amounts that were not increased from old prize amounts,
     * so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_ContestPrizeNotIncreased() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        ActiveContestPrizeIncreasingAction action = (ActiveContestPrizeIncreasingAction) proxy.getAction();

        List<Double> list = new ArrayList<Double>();
        list.add(Double.valueOf(1));
        list.add(Double.valueOf(2));
        TestHelper.getFieldValues().put("contestPrizes", list);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "contestPrizes",
            "The new contestPrizes amounts must be greater than the old ones");
    }

    /**
     * Failure test for executeAction. There are milestone prize amounts that were not increased from old
     * prize amounts, so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_MilestonePrizeNotIncreased() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        ActiveContestPrizeIncreasingAction action = (ActiveContestPrizeIncreasingAction) proxy.getAction();

        List<Double> list = new ArrayList<Double>();
        list.add(Double.valueOf(400));
        list.add(Double.valueOf(300));
        TestHelper.getFieldValues().put("contestPrizes", list);

        list = new ArrayList<Double>();
        list.add(Double.valueOf(1));
        list.add(Double.valueOf(2));
        TestHelper.getFieldValues().put("milestonePrizes", list);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "milestonePrizes",
            "The new milestonePrizes amounts must be greater than the old ones");
    }

    /**
     * Failure test for executeAction. The number of old and new contest prizes are different,
     * so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setContestPrizes_WrongNumberOfContestPrizes() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        ActiveContestPrizeIncreasingAction action = (ActiveContestPrizeIncreasingAction) proxy.getAction();

        List<Double> list = new ArrayList<Double>();
        list.add(Double.valueOf(1000));
        list.add(Double.valueOf(300));
        list.add(Double.valueOf(400));
        TestHelper.getFieldValues().put("contestPrizes", list);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "contestPrizes",
            "The old and new contestPrizes must have same number of elements");
    }

    /**
     * Failure test for executeAction. The number of old and new milestone prizes are different,
     * so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setContestPrizes_WrongNumberOfMilestonePrizes() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        ActiveContestPrizeIncreasingAction action = (ActiveContestPrizeIncreasingAction) proxy.getAction();

        List<Double> list = new ArrayList<Double>();
        list.add(Double.valueOf(4000));
        list.add(Double.valueOf(3000));
        TestHelper.getFieldValues().put("contestPrizes", list);

        list = new ArrayList<Double>();
        list.add(Double.valueOf(4000));
        list.add(Double.valueOf(3000));
        list.add(Double.valueOf(2000));
        TestHelper.getFieldValues().put("milestonePrizes", list);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "milestonePrizes",
            "The old and new milestonePrizes must have same number of elements");
    }

    /**
     * Accuracy test for getContestId. Verifies the returned value is correct.
     */
    @Test
    public void test_getContestId_Accuracy() {
        instance = new ActiveContestPrizeIncreasingAction();
        assertEquals("incorrect default value", 0, instance.getContestId());
        instance.setContestId(1);
        assertEquals("incorrect value after setting", 1, instance.getContestId());
    }

    /**
     * Accuracy Test for setContestId. Verifies the assigned value is correct.
     */
    @Test
    public void test_setContestId_Accuracy() {
        instance.setContestId(1);
        assertEquals("incorrect value after setting", 1, instance.getContestId());
    }

    /**
     * Failure test for setContestId method in struts environment. The value for contestId is not positive, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setContestId_ContestIdNotPositive() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        ActiveContestPrizeIncreasingAction action = (ActiveContestPrizeIncreasingAction) proxy.getAction();

        TestHelper.getFieldValues().put("contestId", 0);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "contestId", "contestId must be > 0");
    }

    /**
     * Accuracy test for isStudio. Verifies the returned value is correct.
     */
    @Test
    public void test_isStudio_Accuracy() {
        instance = new ActiveContestPrizeIncreasingAction();
        assertEquals("incorrect default value", false, instance.isStudio());
        instance.setStudio(true);
        assertEquals("incorrect value after setting", true, instance.isStudio());
    }

    /**
     * Accuracy Test for setStudio. Verifies the assigned value is correct.
     */
    @Test
    public void test_setStudio_Accuracy() {
        instance.setStudio(true);
        assertEquals("incorrect value after setting", true, instance.isStudio());
    }

    /**
     * Accuracy test for getContestPrizes. Verifies the returned value is correct.
     */
    @Test
    public void test_getContestPrizes_Accuracy() {
        instance = new ActiveContestPrizeIncreasingAction();
        assertNull("incorrect default value", instance.getContestPrizes());
        List<Double> list = new ArrayList<Double>();
        list.add(100.0);
        list.add(200.0);
        instance.setContestPrizes(list);
        assertSame("incorrect value after setting", list, instance.getContestPrizes());
    }

    /**
     * Accuracy Test for setContestPrizes. Verifies the assigned value is correct.
     */
    @Test
    public void test_setContestPrizes_Accuracy() {
        List<Double> list = new ArrayList<Double>();
        list.add(100.0);
        list.add(200.0);
        instance.setContestPrizes(list);
        assertSame("incorrect value after setting", list, instance.getContestPrizes());
    }

    /**
     * Failure test for setContestPrizes method in struts environment. The prizes are null, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setContestPrizes_Null() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        ActiveContestPrizeIncreasingAction action = (ActiveContestPrizeIncreasingAction) proxy.getAction();

        TestHelper.getFieldValues().put("contestPrizes", null);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "contestPrizes",
            "contestPrizes cannot be null, cannot contain null elements");
    }

    /**
     * Failure test for setContestPrizes method in struts environment. The prizes contain null element, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setContestPrizes_NullElement() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        ActiveContestPrizeIncreasingAction action = (ActiveContestPrizeIncreasingAction) proxy.getAction();

        List<Double> list = new ArrayList<Double>();
        list.add(Double.valueOf(12.43));
        list.add(null);
        TestHelper.getFieldValues().put("contestPrizes", list);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "contestPrizes",
            "contestPrizes cannot be null, cannot contain null elements");
    }

    /**
     * Failure test for setContestPrizes method in struts environment. The prizes contain negative prize, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setContestPrizes_NegativePrize() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        ActiveContestPrizeIncreasingAction action = (ActiveContestPrizeIncreasingAction) proxy.getAction();

        List<Double> list = new ArrayList<Double>();
        list.add(Double.valueOf(12.43));
        list.add(Double.valueOf(-1));
        TestHelper.getFieldValues().put("contestPrizes", list);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "contestPrizes", "All prize amounts must be >= 0");
    }

    /**
     * Failure test for setContestPrizes method in struts environment. The prizes contain NaN, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setContestPrizes_NaNPrize() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        ActiveContestPrizeIncreasingAction action = (ActiveContestPrizeIncreasingAction) proxy.getAction();

        List<Double> list = new ArrayList<Double>();
        list.add(Double.valueOf(12.43));
        list.add(Double.NaN);
        TestHelper.getFieldValues().put("contestPrizes", list);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "contestPrizes", "All prize amounts must be >= 0");
    }

    /**
     * Failure test for setContestPrizes method in struts environment. The prizes contain NEGATIVE_INFINITY, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setContestPrizes_NegativeInfinityPrize() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        ActiveContestPrizeIncreasingAction action = (ActiveContestPrizeIncreasingAction) proxy.getAction();

        List<Double> list = new ArrayList<Double>();
        list.add(Double.valueOf(12.43));
        list.add(Double.NEGATIVE_INFINITY);
        TestHelper.getFieldValues().put("contestPrizes", list);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "contestPrizes", "All prize amounts must be >= 0");
    }

    /**
     * Failure test for setContestPrizes method in struts environment. The prizes contains only 1 prize, so
     * validation should fail (1st and 2nd place prize are required).
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setContestPrizes_OnlyOnePrize() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        ActiveContestPrizeIncreasingAction action = (ActiveContestPrizeIncreasingAction) proxy.getAction();

        List<Double> list = new ArrayList<Double>();
        list.add(Double.valueOf(12.43));
        TestHelper.getFieldValues().put("contestPrizes", list);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "contestPrizes",
            "1st and 2nd place prize are required for contestPrizes");
    }

    /**
     * Failure test for setContestPrizes method in struts environment. The prizes contain element with over
     * 2 decimal places for cents, so validation should fail
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setContestPrizes_TooManyDecimalPlacesForCents() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        ActiveContestPrizeIncreasingAction action = (ActiveContestPrizeIncreasingAction) proxy.getAction();

        List<Double> list = new ArrayList<Double>();
        list.add(Double.valueOf(12.43));
        list.add(Double.valueOf(1234112.452));
        TestHelper.getFieldValues().put("contestPrizes", list);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "contestPrizes",
            "All prize amounts must have at most 2 decimal places for cents");
    }

    /**
     * Failure test for setContestPrizes method in struts environment. The 2nd place contest prize is less than
     * 20% of 1st place prize, so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setContestPrizes_SecondPlacePrizeTooLow() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        ActiveContestPrizeIncreasingAction action = (ActiveContestPrizeIncreasingAction) proxy.getAction();

        List<Double> list = new ArrayList<Double>();
        list.add(Double.valueOf(1000));
        list.add(Double.valueOf(199));
        TestHelper.getFieldValues().put("contestPrizes", list);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "contestPrizes",
            "The second place prize must be at least 20% of first place prize");
    }

    /**
     * Accuracy test for getMilestonePrizes. Verifies the returned value is correct.
     */
    @Test
    public void test_getMilestonePrizes_Accuracy() {
        instance = new ActiveContestPrizeIncreasingAction();
        assertNull("incorrect default value", instance.getMilestonePrizes());
        List<Double> list = new ArrayList<Double>();
        list.add(100.0);
        list.add(200.0);
        instance.setMilestonePrizes(list);
        assertSame("incorrect value after setting", list, instance.getMilestonePrizes());
    }

    /**
     * Accuracy Test for setMilestonePrizes. Verifies the assigned value is correct.
     */
    @Test
    public void test_setMilestonePrizes_Accuracy() {
        List<Double> list = new ArrayList<Double>();
        list.add(100.0);
        list.add(200.0);
        instance.setMilestonePrizes(list);
        assertSame("incorrect value after setting", list, instance.getMilestonePrizes());
    }

    /**
     * Failure test for setMilestonePrizes method in struts environment. The prizes are null, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setMilestonePrizes_Null() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        ActiveContestPrizeIncreasingAction action = (ActiveContestPrizeIncreasingAction) proxy.getAction();

        TestHelper.getFieldValues().put("milestonePrizes", null);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "milestonePrizes",
            "milestonePrizes cannot be null, cannot contain null elements");
    }

    /**
     * Failure test for setMilestonePrizes method in struts environment. The prizes contain null element, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setMilestonePrizes_NullElement() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        ActiveContestPrizeIncreasingAction action = (ActiveContestPrizeIncreasingAction) proxy.getAction();

        List<Double> list = new ArrayList<Double>();
        list.add(Double.valueOf(12.43));
        list.add(null);
        TestHelper.getFieldValues().put("milestonePrizes", list);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "milestonePrizes",
            "milestonePrizes cannot be null, cannot contain null elements");
    }

    /**
     * Failure test for setMilestonePrizes method in struts environment. The prizes contain negative prize, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setMilestonePrizes_NegativePrize() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        ActiveContestPrizeIncreasingAction action = (ActiveContestPrizeIncreasingAction) proxy.getAction();

        List<Double> list = new ArrayList<Double>();
        list.add(Double.valueOf(12.43));
        list.add(Double.valueOf(-1));
        TestHelper.getFieldValues().put("milestonePrizes", list);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "milestonePrizes", "All prize amounts must be >= 0");
    }

    /**
     * Failure test for setMilestonePrizes method in struts environment. The prizes contain NaN, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setMilestonePrizes_NaNPrize() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        ActiveContestPrizeIncreasingAction action = (ActiveContestPrizeIncreasingAction) proxy.getAction();

        List<Double> list = new ArrayList<Double>();
        list.add(Double.valueOf(12.43));
        list.add(Double.NaN);
        TestHelper.getFieldValues().put("milestonePrizes", list);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "milestonePrizes", "All prize amounts must be >= 0");
    }

    /**
     * Failure test for setMilestonePrizes method in struts environment. The prizes contain element with over
     * 2 decimal places for cents, so validation should fail
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setMilestonePrizes_TooManyDecimalPlacesForCents() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        ActiveContestPrizeIncreasingAction action = (ActiveContestPrizeIncreasingAction) proxy.getAction();

        List<Double> list = new ArrayList<Double>();
        list.add(Double.valueOf(12.43));
        list.add(Double.valueOf(1234112.452));
        TestHelper.getFieldValues().put("milestonePrizes", list);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "milestonePrizes",
            "All prize amounts must have at most 2 decimal places for cents");
    }

}
