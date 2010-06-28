/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.service.facade.contest.CommonProjectContestData;
import com.topcoder.service.facade.contest.ContestServiceException;
import com.topcoder.service.facade.contest.ProjectSummaryData;

/**
 * <p>
 * Unit tests for <code>ProjectContestDataRetrievalAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectContestDataRetrievalActionUnitTest extends BaseStrutsSpringTestCase {

    /**
     * The instance used in testing.
     */
    private ProjectContestDataRetrievalAction instance;

    /**
     * Prepares the action proxy for use with struts.
     *
     * @return the prepared proxy
     * @throws Exception if an error occurs preparing the proxy
     */
    private ActionProxy prepareActionProxy() throws Exception {
        ActionProxy proxy = getActionProxy("/projectContestDataRetrievalAction");
        assertTrue("action is of wrong type", proxy.getAction() instanceof ProjectContestDataRetrievalAction);

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
        instance = new ProjectContestDataRetrievalAction();
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
     * Accuracy test for executeAction. The correct project contest data should be stored in model.
     *
     * @throws Exception to JUnit
     */
    @SuppressWarnings("unchecked")
    @Test
    public void test_executeAction_Accuracy() throws Exception {
        // prepare the instance
        instance.setContestServiceFacade(new MockContestServiceFacade());
        instance.setProjectId(1);

        // execute the action
        instance.executeAction();

        // validate that correct data is in model
        Object result = instance.getModel().getData("result");
        assertNotNull("result should not be null", result);
        assertTrue("result is not correct type", result instanceof HashMap<?, ?>);
        Map<String, Object> map = (Map<String, Object>) result;
        assertTrue("map should contain contestData key", map.containsKey("contestData"));
        assertTrue("map should contain projectSummaryData key", map.containsKey("projectSummaryData"));

        // validate the common project contest data
        List<CommonProjectContestData> contestData = (List<CommonProjectContestData>) map.get("contestData");
        assertEquals("The number of common project contest data objects is wrong", 2, contestData.size());
        assertEquals("The first common project contest data object is wrong", "contest1", contestData.get(0)
            .getCname());
        assertEquals("The second common project contest data object is wrong", "contest2", contestData.get(1)
            .getCname());

        // validate the project summary data
        List<ProjectSummaryData> projectSummaryData = (List<ProjectSummaryData>) map.get("projectSummaryData");
        assertEquals("The number of project summary data objects is wrong", 3, projectSummaryData.size());
        assertEquals("The first project summary data object is wrong", "project1", projectSummaryData.get(0)
            .getProjectName());
        assertEquals("The second project summary data object is wrong", "project2", projectSummaryData.get(1)
            .getProjectName());
        assertEquals("The third project summary data object is wrong", "project3", projectSummaryData.get(2)
            .getProjectName());
    }

    /**
     * Failure test for executeAction. The <code>ContestServiceFacade</code> hasn't been injected,
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
     * Failure test for executeAction. An error occurs when getting project data,
     * so ContestServiceException is expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_ErrorGettingProjectData() throws Exception {
        try {
            instance.setContestServiceFacade(new MockContestServiceFacade());
            MockContestServiceFacade.setFailWhenGettingProjectData(true);
            instance.executeAction();
            fail("ContestServiceException is expected");
        } catch (ContestServiceException e) {
            // make sure exception was stored in model
            Object result = instance.getResult();
            assertNotNull("result from model shouldn't be null", result);
            assertTrue("result from model is not correct type", result instanceof ContestServiceException);
        }
    }

    /**
     * Accuracy test for getProjectId. Verifies the returned value is correct.
     */
    @Test
    public void test_getProjectId_Accuracy() {
        instance = new ProjectContestDataRetrievalAction();
        assertEquals("incorrect default value", 0, instance.getProjectId());
        instance.setProjectId(1);
        assertEquals("incorrect value after setting", 1, instance.getProjectId());
    }

    /**
     * Accuracy Test for setProjectId. Verifies the assigned value is correct.
     */
    @Test
    public void test_setProjectId_Accuracy() {
        instance.setProjectId(1);
        assertEquals("incorrect value after setting", 1, instance.getProjectId());
    }

    /**
     * Failure test for setProjectId method in struts environment. The value for projectId is not positive, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setProjectId_ProjectIdNotPositive() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        ProjectContestDataRetrievalAction action = (ProjectContestDataRetrievalAction) proxy.getAction();

        TestHelper.getFieldValues().put("projectId", 0);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "projectId", "projectId must be > 0");
    }
}
