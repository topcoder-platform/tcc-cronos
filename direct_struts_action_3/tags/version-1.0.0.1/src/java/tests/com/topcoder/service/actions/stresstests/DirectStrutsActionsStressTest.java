/**
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */
package com.topcoder.service.actions.stresstests;


import com.topcoder.direct.services.view.action.contest.launch.ActiveContestPrizeIncreasingAction;
import com.topcoder.direct.services.view.action.contest.launch.ActiveContestPrizeRetrievalAction;
import com.topcoder.direct.services.view.action.contest.launch.ActiveContestScheduleExtensionAction;
import com.topcoder.direct.services.view.action.contest.launch.ActiveContestScheduleRetrievalAction;
import com.topcoder.direct.services.view.action.contest.launch.ContestReceiptRetrievalAction;
import com.topcoder.direct.services.view.action.contest.launch.ContestReceiptSendingAction;
import com.topcoder.direct.services.view.action.contest.launch.GamePlanRetrievalAction;
import com.topcoder.direct.services.view.action.contest.launch.MockContestServiceFacade;
import com.topcoder.direct.services.view.action.contest.launch.MockDirectServiceFacade;
import com.topcoder.direct.services.view.action.contest.launch.ProjectContestDataRetrievalAction;
import com.topcoder.direct.services.view.action.contest.launch.TestHelper;
import com.topcoder.direct.services.view.action.contest.launch.WikiLinkRetrievalAction;
import com.topcoder.service.facade.contest.CommonProjectContestData;
import com.topcoder.service.facade.contest.ProjectSummaryData;
import com.topcoder.service.facade.direct.ContestPlan;
import com.topcoder.service.facade.direct.ContestPrize;
import com.topcoder.service.facade.direct.ContestReceiptData;
import com.topcoder.service.facade.direct.ContestSchedule;
import com.topcoder.service.project.StudioCompetition;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p> Stress test cases for the component. </p>
 *
 * <p> This class tests the multiple thread situation for the component. </p>
 *
 * @author yuanyeyuanye, TCSDEVELOPER
 * @version 1.0
 */
public class DirectStrutsActionsStressTest extends BaseStressTest {

    /**
     * <p> Sets up test environment. </p>
     *
     * @throws Exception to jUnit.
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Tears down the test environment.
     *
     * @throws Exception to Junit.
     */
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p> This is the stress test case for <code>ActiveContestPrizeIncreasingAction</code>, because it would be used in
     * struts framework, the test cases has simulated the situation it would be used.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_ActiveContestPrizeIncreasingAction_1_100() throws Throwable {
        numThreads = 1;
        timesToExecute = 5;

        long startTime = System.currentTimeMillis();

        Action action =
            new Action() {
                public void act() throws Exception {
                    TestHelper.setupEnvironment();

                    ActiveContestPrizeIncreasingAction instance =
                        StressHelper.createAction(ActiveContestPrizeIncreasingAction.class);

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
                    instance.execute();

                    // validate that correct data is in model
                    Object result = instance.getModel().getData("result");
                    assertNull(result);

                    // validate that prizes were updated with increased prize amounts
                    ContestPrize updatedPrize = MockDirectServiceFacade.getContestPrize(1);
                    assertEquals(2, updatedPrize.getContestPrizes().length);
                    assertEquals(contestPrizes.get(0), updatedPrize.getContestPrizes()[0], 0.01);
                    assertEquals(contestPrizes.get(1), updatedPrize.getContestPrizes()[1], 0.01);

                    TestHelper.cleanupEnvironment();
                }
            };

        runActionForMultipleTimes(action, timesToExecute);
        outputTestResult(getLogMessage(startTime, numThreads, timesToExecute));
    }

    /**
     * <p> This is the stress test case for <code>ActiveContestPrizeRetrievalAction</code>, because it would be used in
     * struts framework, the test cases has simulated the situation it would be used.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_ActiveContestPrizeRetrievalAction_1_100() throws Throwable {
        numThreads = 1;
        timesToExecute = 100;

        long startTime = System.currentTimeMillis();

        Action action =
            new Action() {
                public void act() throws Exception {
                    TestHelper.setupEnvironment();

                    ActiveContestPrizeRetrievalAction instance =
                        StressHelper.createAction(ActiveContestPrizeRetrievalAction.class);

                    instance.setDirectServiceFacade(new MockDirectServiceFacade());
                    instance.setContestId(1);

                    // execute the action
                    instance.execute();

                    // validate that correct data is in model
                    Object result = instance.getModel().getData("result");
                    assertNotNull(result);
                    assertTrue(result instanceof ContestPrize);
                    ContestPrize prize = (ContestPrize) result;
                    assertTrue(prize.isStudio());
                    assertEquals(1, prize.getContestId());

                    assertEquals(2, prize.getContestPrizes().length);
                    assertEquals(100.25, prize.getContestPrizes()[0]);
                    assertEquals(20.25, prize.getContestPrizes()[1]);

                    assertEquals(2, prize.getMilestonePrizes().length);
                    assertEquals(100.35, prize.getMilestonePrizes()[0]);
                    assertEquals(80.35, prize.getMilestonePrizes()[1]);
                    TestHelper.cleanupEnvironment();
                }
            };

        runActionForMultipleTimes(action, timesToExecute);
        outputTestResult(getLogMessage(startTime, numThreads, timesToExecute));
    }

    /**
     * <p> This is the stress test case for <code>ActiveContestScheduleExtensionAction</code>, because it would be used
     * in struts framework, the test cases has simulated the situation it would be used.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_ActiveContestScheduleExtensionAction_1_100() throws Throwable {
        numThreads = 1;
        timesToExecute = 100;

        long startTime = System.currentTimeMillis();

        Action action =
            new Action() {
                public void act() throws Exception {
                    TestHelper.setupEnvironment();

                    ActiveContestScheduleExtensionAction instance =
                        StressHelper.createAction(ActiveContestScheduleExtensionAction.class);

                    // prepare the instance
                    instance.setDirectServiceFacade(new MockDirectServiceFacade());
                    instance.setContestId(1);
                    instance.setStudio(true);
                    instance.setRegistrationExtension(10);
                    instance.setMilestoneExtension(20);
                    instance.setSubmissionExtension(30);

                    // execute the action
                    instance.execute();

                    // validate that correct data is in model
                    Object result = instance.getModel().getData("result");
                    assertNull(result);

                    // validate that contest was extended
                    StudioCompetition comp = MockContestServiceFacade.getStudioCompetitions().get(1L);

                    String extensionData = comp.getNotes();
                    assertNotNull(extensionData);
                    assertEquals("extendRegistrationBy=10 extendMilestoneBy=20 extendSubmissionBy=30", extensionData);
                    TestHelper.cleanupEnvironment();
                }
            };

        runActionForMultipleTimes(action, timesToExecute);
        outputTestResult(getLogMessage(startTime, numThreads, timesToExecute));
    }

    /**
     * <p> This is the stress test case for <code>ActiveContestScheduleRetrievalAction</code>, because it would be used
     * in struts framework, the test cases has simulated the situation it would be used.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_ActiveContestScheduleRetrievalAction_1_100() throws Throwable {
        numThreads = 1;
        timesToExecute = 100;

        long startTime = System.currentTimeMillis();

        Action action =
            new Action() {
                public void act() throws Exception {
                    TestHelper.setupEnvironment();

                    ActiveContestScheduleRetrievalAction instance =
                        StressHelper.createAction(ActiveContestScheduleRetrievalAction.class);

                    instance.setDirectServiceFacade(new MockDirectServiceFacade());
                    instance.setContestId(1);
                    instance.setStudio(true);

                    // execute the action
                    instance.execute();

                    // validate that correct data is in model
                    Object result = instance.getModel().getData("result");
                    assertNotNull(result);
                    assertTrue(result instanceof ContestSchedule);

                    // validate that contest schedule is correct
                    ContestSchedule schedule = (ContestSchedule) result;
                    assertEquals(1, schedule.getContestId());
                    assertEquals(true, schedule.isStudio());

                    // prepare the instance for software contest
                    instance.setContestId(2);
                    instance.setStudio(false);

                    // execute the action
                    instance.execute();

                    // validate that correct data is in model
                    result = instance.getModel().getData("result");
                    assertNotNull(result);
                    assertTrue(result instanceof ContestSchedule);

                    // validate that contest schedule is correct
                    schedule = (ContestSchedule) result;
                    assertEquals(2, schedule.getContestId());
                    assertEquals(false, schedule.isStudio());

                    TestHelper.cleanupEnvironment();
                }
            };

        runActionForMultipleTimes(action, timesToExecute);
        outputTestResult(getLogMessage(startTime, numThreads, timesToExecute));
    }

    /**
     * <p> This is the stress test case for <code>ContestReceiptRetrievalAction</code>, because it would be used in
     * struts framework, the test cases has simulated the situation it would be used.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_ContestReceiptRetrievalAction_1_100() throws Throwable {
        numThreads = 1;
        timesToExecute = 100;

        long startTime = System.currentTimeMillis();

        Action action =
            new Action() {
                public void act() throws Exception {
                    TestHelper.setupEnvironment();

                    ContestReceiptRetrievalAction instance =
                        StressHelper.createAction(ContestReceiptRetrievalAction.class);

                    instance.setDirectServiceFacade(new MockDirectServiceFacade());
                    instance.setContestId(1);
                    instance.setStudio(true);

                    // execute the action
                    instance.execute();

                    // validate that correct data is in model
                    Object result = instance.getModel().getData("result");
                    assertNotNull(result);
                    assertTrue(result instanceof ContestReceiptData);

                    // validate that receipt data is correct
                    ContestReceiptData receipt = (ContestReceiptData) result;
                    assertEquals(1, receipt.getContestId());
                    assertEquals("studio comp1", receipt.getContestName());

                    // prepare the instance for software contest
                    instance.setStudio(false);

                    // execute the action
                    instance.execute();

                    // validate that correct data is in model
                    result = instance.getModel().getData("result");
                    assertNotNull(result);
                    assertTrue(result instanceof ContestReceiptData);

                    // validate that receipt data is correct
                    receipt = (ContestReceiptData) result;
                    assertEquals(1, receipt.getContestId());
                    assertEquals("software comp1", receipt.getContestName());

                    TestHelper.cleanupEnvironment();
                }
            };

        runActionForMultipleTimes(action, timesToExecute);
        outputTestResult(getLogMessage(startTime, numThreads, timesToExecute));
    }

    /**
     * <p> This is the stress test case for <code>ContestReceiptSendingAction</code>, because it would be used in struts
     * framework, the test cases has simulated the situation it would be used.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_ContestReceiptSendingAction_1_100() throws Throwable {
        numThreads = 1;
        timesToExecute = 100;

        long startTime = System.currentTimeMillis();

        Action action =
            new Action() {
                public void act() throws Exception {
                    TestHelper.setupEnvironment();

                    ContestReceiptSendingAction instance =
                        StressHelper.createAction(ContestReceiptSendingAction.class);

                    instance.setDirectServiceFacade(new MockDirectServiceFacade());
                    instance.setContestId(1);
                    instance.setStudio(true);
                    instance.setAdditionalEmailAddresses("a@b.com; a2@a2.com, a3@a3.com ");

                    // execute the action
                    instance.execute();

                    // validate that correct data is in model
                    Object result = instance.getModel().getData("result");
                    assertNotNull(result);
                    assertTrue(result instanceof ContestReceiptData);

                    // validate that receipt data is correct
                    ContestReceiptData receipt = (ContestReceiptData) result;
                    assertEquals(1, receipt.getContestId());
                    assertEquals("studio comp1", receipt.getContestName());

                    // validate that email was sent to recipients
                    assertEquals(3,
                                 MockDirectServiceFacade.getadditionalEmailAddresses().length);
                    assertEquals("a@b.com", MockDirectServiceFacade
                        .getadditionalEmailAddresses()[0]);
                    assertEquals(" a2@a2.com", MockDirectServiceFacade
                        .getadditionalEmailAddresses()[1]);
                    assertEquals(" a3@a3.com ", MockDirectServiceFacade
                        .getadditionalEmailAddresses()[2]);

                    TestHelper.cleanupEnvironment();
                }
            };

        runActionForMultipleTimes(action, timesToExecute);
        outputTestResult(getLogMessage(startTime, numThreads, timesToExecute));
    }

    /**
     * <p> This is the stress test case for <code>GamePlanRetrievalAction</code>, because it would be used in struts
     * framework, the test cases has simulated the situation it would be used.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_GamePlanRetrievalAction_1_100() throws Throwable {
        numThreads = 1;
        timesToExecute = 100;

        long startTime = System.currentTimeMillis();

        Action action =
            new Action() {
                public void act() throws Exception {
                    TestHelper.setupEnvironment();

                    GamePlanRetrievalAction instance =
                        StressHelper.createAction(GamePlanRetrievalAction.class);

                    instance.setDirectServiceFacade(new MockDirectServiceFacade());
                    instance.setProjectId(1);

                    // execute the action
                    instance.execute();

                    // validate that correct data is in model
                    Object result = instance.getModel().getData("result");
                    assertNotNull(result);
                    assertTrue(result instanceof List<?>);
                    List<ContestPlan> data = (List<ContestPlan>) result;

                    assertEquals(3, data.size());
                    for (int i = 1; i <= 3; ++i) {
                        assertEquals("contest" + i, data.get(i - 1).getName());
                    }

                    TestHelper.cleanupEnvironment();
                }
            };

        runActionForMultipleTimes(action, timesToExecute);
        outputTestResult(getLogMessage(startTime, numThreads, timesToExecute));

    }

    /**
     * <p> This is the stress test case for <code>ProjectContestDataRetrievalAction</code>, because it would be used in
     * struts framework, the test cases has simulated the situation it would be used.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_ProjectContestDataRetrievalAction_1_100() throws Throwable {
        numThreads = 1;
        timesToExecute = 100;

        long startTime = System.currentTimeMillis();

        Action action =
            new Action() {
                public void act() throws Exception {
                    TestHelper.setupEnvironment();

                    ProjectContestDataRetrievalAction instance =
                        StressHelper.createAction(ProjectContestDataRetrievalAction.class);

                    instance.setContestServiceFacade(new MockContestServiceFacade());
                    instance.setProjectId(1);

                    // execute the action
                    instance.execute();

                    // validate that correct data is in model
                    Object result = instance.getModel().getData("result");
                    assertNotNull(result);
                    assertTrue(result instanceof HashMap<?, ?>);
                    Map<String, Object> map = (Map<String, Object>) result;
                    assertTrue(map.containsKey("contestData"));
                    assertTrue(map.containsKey("projectSummaryData"));

                    // validate the common project contest data
                    List<CommonProjectContestData> contestData =
                        (List<CommonProjectContestData>) map.get("contestData");
                    assertEquals(2, contestData.size());
                    assertEquals("contest1", contestData.get(0)
                        .getCname());
                    assertEquals("contest2", contestData.get(1)
                        .getCname());

                    // validate the project summary data
                    List<ProjectSummaryData> projectSummaryData =
                        (List<ProjectSummaryData>) map.get("projectSummaryData");
                    assertEquals(3, projectSummaryData.size());
                    assertEquals("project1", projectSummaryData.get(0)
                        .getProjectName());
                    assertEquals("project2", projectSummaryData.get(1)
                        .getProjectName());
                    assertEquals("project3", projectSummaryData.get(2)
                        .getProjectName());

                    TestHelper.cleanupEnvironment();
                }
            };

        runActionForMultipleTimes(action, timesToExecute);
        outputTestResult(getLogMessage(startTime, numThreads, timesToExecute));

    }

    /**
     * <p> This is the stress test case for <code>WikiLinkRetrievalAction</code>, because it would be used in struts
     * framework, the test cases has simulated the situation it would be used.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_WikiLinkRetrievalAction_5_5() throws Throwable {
        numThreads = 1;
        timesToExecute = 100;

        long startTime = System.currentTimeMillis();

        Action action =
            new Action() {
                public void act() throws Exception {
                    TestHelper.setupEnvironment();

                    WikiLinkRetrievalAction instance =
                        StressHelper.createAction(WikiLinkRetrievalAction.class);

                    instance.setContestServiceFacade(new MockContestServiceFacade());

                    // prepare the instance for studio contest
                    instance.setContestId(1);
                    instance.setStudio(true);
                    instance.setLinkTemplate(
                        "this is a test template [contest_name] [contest_type] [contest_name] [contest_type]");

                    // execute the action
                    instance.execute();

                    // validate that correct data is in model
                    Object result = instance.getModel().getData("result");
                    assertTrue(result instanceof String);
                    String expected = URLEncoder.encode(
                        "this is a test template studio comp1 studio category 1 [contest_name] [contest_type]",
                        "UTF-8");
                    assertEquals(expected, result);

                    // prepare the instance for software contest
                    instance.setProjectId(1);
                    instance.setStudio(false);
                    instance.setLinkTemplate(
                        "this is a test template [contest_name] [contest_type] [contest_name] [contest_type]");

                    // execute the action
                    instance.execute();

                    // validate that correct data is in model
                    result = instance.getModel().getData("result");
                    assertTrue(result instanceof String);
                    expected = URLEncoder.encode("this is a test template software comp1 software category 1 " +
                        "[contest_name] [contest_type]", "UTF-8");
                    assertEquals(expected, result);

                    TestHelper.cleanupEnvironment();
                }
            };

        runActionForMultipleTimes(action, timesToExecute);
        outputTestResult(getLogMessage(startTime, numThreads, timesToExecute));

    }

}
