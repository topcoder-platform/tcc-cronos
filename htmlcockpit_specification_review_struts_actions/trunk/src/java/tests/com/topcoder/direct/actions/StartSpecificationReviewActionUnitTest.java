/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.actions;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.review.specification.SpecificationReviewService;

/**
 * <p>
 * Unit tests for <code>StartSpecificationReviewAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class StartSpecificationReviewActionUnitTest extends BaseStrutsSpringTestCase {

    /**
     * The instance used in testing.
     */
    private StartSpecificationReviewAction instance;

    /**
     * Prepares the action proxy for use with struts.
     *
     * @return the prepared proxy
     */
    private ActionProxy prepareActionProxy() {
        ActionProxy proxy = getActionProxy("/startSpecificationReviewAction");
        assertTrue("action is of wrong type", proxy.getAction() instanceof StartSpecificationReviewAction);

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
        instance = new StartSpecificationReviewAction();
        instance.setSpecificationReviewService(new MockSpecificationReviewService());
        instance.setContestServiceFacade(new MockContestServiceFacade());
        TestHelper.prepareSessionMap(instance);
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
        assertTrue("inheritance is wrong", instance instanceof SpecificationReviewAction);
    }

    /**
     * Accuracy test for constructor. Verifies the new instance is created.
     */
    @Test
    public void testConstructor() {
        assertNotNull("unable to create instance", instance);

        // test in struts environment
        assertNotNull("unable to create class in struts environment", prepareActionProxy());
    }

    /**
     * Accuracy test for execute. Verifies that specification review is started with the
     * correct date. In this test, start mode is <code>now</code>.
     */
    @Test
    public void test_execute_Accuracy1() {
        Date now = new Date();
        instance.setStartMode("now");
        instance.setContestId(1);
        instance.setStudio(false);

        // execute the action and verify the return value
        assertEquals("return value from execute method is wrong", Action.SUCCESS, instance.execute());

        // validate that specification review was started with correct date
        Date actualReviewDate =
            MockSpecificationReviewService.getSpecificationReviewStartDates().get(instance.getContestId());
        assertTrue("review date is wrong, it must be >= current date", actualReviewDate.compareTo(now) >= 0);
    }

    /**
     * Accuracy test for execute. Verifies that specification review is started with the
     * correct date. In this test, start mode is <code>later</code> and it's a studio contest.
     */
    @Test
    public void test_execute_Accuracy2() {
        instance.setStartMode("later");
        instance.setContestId(2);
        instance.setStudio(true);
        instance.setSpecificationReviewStartPeriodBeforeContest(1969);

        // execute the action and verify the return value
        assertEquals("return value from execute method is wrong", Action.SUCCESS, instance.execute());

        // validate that specification review was started with correct date
        GregorianCalendar expectedReviewDate =
            MockContestServiceFacade.getStudioCompetitions().get(instance.getContestId()).getStartTime()
                .toGregorianCalendar();
        expectedReviewDate.add(Calendar.MINUTE, -instance.getSpecificationReviewStartPeriodBeforeContest());
        Date actualReviewDate =
            MockSpecificationReviewService.getSpecificationReviewStartDates().get(instance.getContestId());
        assertEquals("review date is wrong", expectedReviewDate.getTimeInMillis(), actualReviewDate.getTime());
    }

    /**
     * Accuracy test for execute. Verifies that specification review is started with the
     * correct date. In this test, start mode is <code>later</code> and it's a software contest.
     */
    @Test
    public void test_execute_Accuracy3() {
        instance.setStartMode("later");
        instance.setContestId(1);
        instance.setStudio(false);
        instance.setSpecificationReviewStartPeriodBeforeContest(12345);

        // execute the action and verify the return value
        assertEquals("return value from execute method is wrong", Action.SUCCESS, instance.execute());

        // validate that specification review was started with correct date
        Date dt =
            MockContestServiceFacade.getSoftwareCompetitions().get(instance.getContestId()).getProjectData()
                .getStartDate();
        Calendar expectedReviewDate = Calendar.getInstance();
        expectedReviewDate.setTime(dt);
        expectedReviewDate.add(Calendar.MINUTE, -instance.getSpecificationReviewStartPeriodBeforeContest());
        Date actualReviewDate =
            MockSpecificationReviewService.getSpecificationReviewStartDates().get(instance.getContestId());
        assertEquals("review date is wrong", expectedReviewDate.getTimeInMillis(), actualReviewDate.getTime());
    }

    /**
     * Failure test for execute. The SpecificationReviewService hasn't been injected, so exception
     * should be stored in model and Action.ERROR should be returned.
     */
    @Test
    public void test_execute_Failure1() {
        instance = new StartSpecificationReviewAction();
        instance.setContestServiceFacade(new MockContestServiceFacade());
        instance.setStartMode("now");
        assertEquals("return value from execute method is wrong", Action.ERROR, instance.execute());

        // make sure exception was stored in model and check that it is proper type
        TestHelper.assertModelException(instance, StartSpecificationReviewActionException.class);
    }

    /**
     * Failure test for execute. The ContestServiceFacade hasn't been injected, so exception
     * should be stored in model and Action.ERROR should be returned.
     */
    @Test
    public void test_execute_Failure2() {
        instance = new StartSpecificationReviewAction();
        instance.setSpecificationReviewService(new MockSpecificationReviewService());
        instance.setStartMode("now");
        assertEquals("return value from execute method is wrong", Action.ERROR, instance.execute());

        // make sure exception was stored in model and check that it is proper type
        TestHelper.assertModelException(instance, StartSpecificationReviewActionException.class);
    }

    /**
     * Failure test for execute. An error occurred during persistence operation, so exception
     * should be stored in model and Action.ERROR should be returned.
     */
    @Test
    public void test_execute_Failure3() {
        instance.setStartMode("now");
        MockContestServiceFacade.setFailWhenGettingProjectData(true);
        assertEquals("return value from execute method is wrong", Action.ERROR, instance.execute());

        // make sure exception was stored in model and check that it is proper type
        TestHelper.assertModelException(instance, StartSpecificationReviewActionException.class);
    }

    /**
     * Failure test for execute. The startMode value is null, so exception
     * should be stored in model and Action.ERROR should be returned.
     */
    @Test
    public void test_execute_Failure4() {
        instance.setStartMode(null);
        assertEquals("return value from execute method is wrong", Action.ERROR, instance.execute());

        // make sure exception was stored in model and check that it is proper type
        TestHelper.assertModelException(instance, StartSpecificationReviewActionException.class);
    }

    /**
     * Failure test for execute. The startMode value is not 'now' or 'later', so exception
     * should be stored in model and Action.ERROR should be returned.
     */
    @Test
    public void test_execute_Failure5() {
        instance.setStartMode("fail");
        assertEquals("return value from execute method is wrong", Action.ERROR, instance.execute());

        // make sure exception was stored in model and check that it is proper type
        TestHelper.assertModelException(instance, StartSpecificationReviewActionException.class);
    }

    /**
     * Failure test for execute. The specificationReviewStartPeriodBeforeContest is not positive, so exception
     * should be stored in model and Action.ERROR should be returned.
     */
    @Test
    public void test_execute_Failure6() {
        instance.setStartMode("now");
        instance.setSpecificationReviewStartPeriodBeforeContest(0);
        assertEquals("return value from execute method is wrong", Action.ERROR, instance.execute());

        // make sure exception was stored in model and check that it is proper type
        TestHelper.assertModelException(instance, StartSpecificationReviewActionException.class);
    }

    /**
     * Accuracy test for getStartMode. Verifies the returned value is correct.
     */
    @Test
    public void test_getStartMode_Accuracy() {
        String test = "test";
        instance.setStartMode(test);
        assertEquals("getter is wrong", test, instance.getStartMode());
    }

    /**
     * Accuracy test for setStartMode. Verifies the assigned value is correct.
     */
    @Test
    public void test_setStartMode_Accuracy() {
        String test = "test";
        instance.setStartMode(test);
        TestHelper.assertFieldSame(instance.getClass(), test, instance, "startMode");
    }

    /**
     * Accuracy test for getSpecificationReviewStartPeriodBeforeContest. Verifies the returned value is
     * correct.
     */
    @Test
    public void test_getSpecificationReviewStartPeriodBeforeContest_Accuracy() {
        int test = 10;
        instance.setSpecificationReviewStartPeriodBeforeContest(test);
        assertEquals("getter is wrong", test, instance.getSpecificationReviewStartPeriodBeforeContest());
    }

    /**
     * Accuracy test for setSpecificationReviewStartPeriodBeforeContest. Verifies the assigned value is
     * correct.
     */
    @Test
    public void test_setSpecificationReviewStartPeriodBeforeContest_Accuracy() {
        int test = 10;
        instance.setSpecificationReviewStartPeriodBeforeContest(test);
        TestHelper.assertFieldEquals(instance.getClass(), test, instance,
            "specificationReviewStartPeriodBeforeContest");
    }

    /**
     * Accuracy test for DEFAULT_SPECIFICATION_REVIEW_START_PERIOD_BEFORE_CONTEST. Verifies that the value for
     * the constant is correct.
     */
    @Test
    public void test_DEFAULT_SPECIFICATION_REVIEW_START_PERIOD_BEFORE_CONTEST_Accuracy() {
        assertEquals("value for constant is wrong", 48 * 60,
            StartSpecificationReviewAction.DEFAULT_SPECIFICATION_REVIEW_START_PERIOD_BEFORE_CONTEST);
    }

    /**
     * Accuracy test for getSpecificationReviewService. Verifies the returned value is correct.
     */
    @Test
    public void test_getSpecificationReviewService_Accuracy() {
        SpecificationReviewService test = new MockSpecificationReviewService();
        instance.setSpecificationReviewService(test);
        assertSame("getter is wrong", test, instance.getSpecificationReviewService());
    }

    /**
     * Accuracy test for setSpecificationReviewService. Verifies the assigned value is correct.
     */
    @Test
    public void test_setSpecificationReviewService_Accuracy() {
        SpecificationReviewService test = new MockSpecificationReviewService();
        instance.setSpecificationReviewService(test);
        TestHelper.assertFieldSame(instance.getClass(), test, instance, "specificationReviewService");
    }

    /**
     * Failure test for setSpecificationReviewService. The value is null, so IllegalArgumentException
     * is expected.
     */
    @Test
    public void test_setSpecificationReviewService_Null() {
        try {
            instance.setSpecificationReviewService(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Accuracy test for getContestServiceFacade. Verifies the returned value is correct.
     */
    @Test
    public void test_getContestServiceFacade_Accuracy() {
        ContestServiceFacade test = new MockContestServiceFacade();
        instance.setContestServiceFacade(test);
        assertSame("getter is wrong", test, instance.getContestServiceFacade());
    }

    /**
     * Accuracy test for setContestServiceFacade. Verifies the assigned value is correct.
     */
    @Test
    public void test_setContestServiceFacade_Accuracy() {
        ContestServiceFacade test = new MockContestServiceFacade();
        instance.setContestServiceFacade(test);
        TestHelper.assertFieldSame(instance.getClass(), test, instance, "contestServiceFacade");
    }

    /**
     * Failure test for setContestServiceFacade. The value is null, so IllegalArgumentException
     * is expected.
     */
    @Test
    public void test_setContestServiceFacade_Null() {
        try {
            instance.setContestServiceFacade(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
