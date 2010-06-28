/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

import java.net.URLEncoder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionProxy;

/**
 * <p>
 * Unit tests for <code>WikiLinkRetrievalAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class WikiLinkRetrievalActionUnitTest extends BaseStrutsSpringTestCase {

    /**
     * The instance used in testing.
     */
    private WikiLinkRetrievalAction instance;

    /**
     * Prepares the action proxy for use with struts.
     *
     * @return the prepared proxy
     * @throws Exception if an error occurs preparing the proxy
     */
    private ActionProxy prepareActionProxy() throws Exception {
        ActionProxy proxy = getActionProxy("/wikiLinkRetrievalAction");
        assertTrue("action is of wrong type", proxy.getAction() instanceof WikiLinkRetrievalAction);

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
        instance = new WikiLinkRetrievalAction();
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
     * Accuracy test for executeAction. The correct template should be stored in the model.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_Accuracy() throws Exception {
        instance.setContestServiceFacade(new MockContestServiceFacade());

        // prepare the instance for studio contest
        instance.setContestId(1);
        instance.setStudio(true);
        instance.setLinkTemplate(
            "this is a test template [contest_name] [contest_type] [contest_name] [contest_type]");

        // execute the action
        instance.executeAction();

        // validate that correct data is in model
        Object result = instance.getModel().getData("result");
        assertTrue("result is not correct type", result instanceof String);
        String expected = URLEncoder.encode(
            "this is a test template studio comp1 studio category 1 [contest_name] [contest_type]", "UTF-8");
        assertEquals("result is wrong", expected, result);

        // prepare the instance for software contest
        instance.setProjectId(1);
        instance.setStudio(false);
        instance.setLinkTemplate(
            "this is a test template [contest_name] [contest_type] [contest_name] [contest_type]");

        // execute the action
        instance.executeAction();

        // validate that correct data is in model
        result = instance.getModel().getData("result");
        assertTrue("result is not correct type", result instanceof String);
        expected = URLEncoder.encode(
            "this is a test template software comp1 software category 1 [contest_name] [contest_type]", "UTF-8");
        assertEquals("result is wrong", expected, result);

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
     * Failure test for executeAction. The AssetDTO is missing for the software contest,
     * so IllegalStateException is expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_MissingAssetDTO() throws Exception {
        WikiLinkRetrievalAction action = null;
        try {
            // execute the action using the action proxy
            ActionProxy proxy = prepareActionProxy();
            action = (WikiLinkRetrievalAction) proxy.getAction();

            TestHelper.getFieldValues().put("projectId", 3);
            TestHelper.getFieldValues().put("studio", false);
            proxy.execute();

            fail("IllegalStateException is expected");

        } catch (IllegalStateException e) {
            // make sure exception was stored in model
            Object result = action.getResult();
            assertNotNull("result from model shouldn't be null", result);
            assertTrue("result from model is not correct type", result instanceof IllegalStateException);
        }
    }

    /**
     * Failure test for executeAction. The category is missing for the software contest,
     * so IllegalStateException is expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_MissingCategory() throws Exception {
        WikiLinkRetrievalAction action = null;
        try {
            // execute the action using the action proxy
            ActionProxy proxy = prepareActionProxy();
            action = (WikiLinkRetrievalAction) proxy.getAction();

            TestHelper.getFieldValues().put("projectId", 4);
            TestHelper.getFieldValues().put("studio", false);
            proxy.execute();

            fail("IllegalStateException is expected");

        } catch (IllegalStateException e) {
            // make sure exception was stored in model
            Object result = action.getResult();
            assertNotNull("result from model shouldn't be null", result);
            assertTrue("result from model is not correct type", result instanceof IllegalStateException);
        }
    }

    /**
     * Failure test for executeAction. The contest type is null for the studio contest,
     * so IllegalStateException is expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_NullContestType() throws Exception {
        WikiLinkRetrievalAction action = null;
        try {
            // execute the action using the action proxy
            ActionProxy proxy = prepareActionProxy();
            action = (WikiLinkRetrievalAction) proxy.getAction();

            TestHelper.getFieldValues().put("contestId", 4);
            TestHelper.getFieldValues().put("studio", true);
            proxy.execute();

            fail("IllegalStateException is expected");

        } catch (IllegalStateException e) {
            // make sure exception was stored in model
            Object result = action.getResult();
            assertNotNull("result from model shouldn't be null", result);
            assertTrue("result from model is not correct type", result instanceof IllegalStateException);
        }
    }

    /**
     * Failure test for executeAction. The contest name is null for the studio contest,
     * so IllegalStateException is expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_NullContestName() throws Exception {
        WikiLinkRetrievalAction action = null;
        try {
            // execute the action using the action proxy
            ActionProxy proxy = prepareActionProxy();
            action = (WikiLinkRetrievalAction) proxy.getAction();

            TestHelper.getFieldValues().put("contestId", 3);
            TestHelper.getFieldValues().put("studio", true);
            proxy.execute();

            fail("IllegalStateException is expected");

        } catch (IllegalStateException e) {
            // make sure exception was stored in model
            Object result = action.getResult();
            assertNotNull("result from model shouldn't be null", result);
            assertTrue("result from model is not correct type", result instanceof IllegalStateException);
        }
    }

    /**
     * Accuracy test for getContestId. Verifies the returned value is correct.
     */
    @Test
    public void test_getContestId_Accuracy() {
        instance = new WikiLinkRetrievalAction();
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
        WikiLinkRetrievalAction action = (WikiLinkRetrievalAction) proxy.getAction();

        TestHelper.getFieldValues().put("studio", true);
        TestHelper.getFieldValues().put("contestId", 0);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "contestId",
            "contestId must be > 0 for studio contest");
    }

    /**
     * Accuracy test for getProjectId. Verifies the returned value is correct.
     */
    @Test
    public void test_getProjectId_Accuracy() {
        instance = new WikiLinkRetrievalAction();
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
     * Failure test for setProjectId method in struts environment. The value for ProjectId is not positive, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setProjectId_ProjectIdNotPositive() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        WikiLinkRetrievalAction action = (WikiLinkRetrievalAction) proxy.getAction();

        TestHelper.getFieldValues().put("studio", false);
        TestHelper.getFieldValues().put("projectId", 0);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "projectId",
            "projectId must be > 0 for software contest");
    }

    /**
     * Accuracy test for isStudio. Verifies the returned value is correct.
     */
    @Test
    public void test_isStudio_Accuracy() {
        instance = new WikiLinkRetrievalAction();
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
     * Accuracy test for getLinkTemplate. Verifies the returned value is correct.
     */
    @Test
    public void test_getLinkTemplate_Accuracy() {
        instance = new WikiLinkRetrievalAction();
        assertNull("incorrect default value", instance.getLinkTemplate());
        instance.setLinkTemplate("a");
        assertEquals("incorrect value after setting", "a", instance.getLinkTemplate());
    }

    /**
     * Accuracy Test for setLinkTemplate. Verifies the assigned value is correct.
     */
    @Test
    public void test_setLinkTemplate_Accuracy() {
        instance.setLinkTemplate("a");
        assertEquals("incorrect value after setting", "a", instance.getLinkTemplate());
    }

    /**
     * Failure test for setLinkTemplate method in struts environment. The value is null, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setLinkTemplate_Null() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        WikiLinkRetrievalAction action = (WikiLinkRetrievalAction) proxy.getAction();

        TestHelper.getFieldValues().put("linkTemplate", null);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "linkTemplate",
            "linkTemplate cannot be null and must contain [contest_type] and [contest_name]");
    }

    /**
     * Failure test for setLinkTemplate method in struts environment. The value doesn't contain [contest_type], so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setLinkTemplate_MissingContestType() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        WikiLinkRetrievalAction action = (WikiLinkRetrievalAction) proxy.getAction();

        TestHelper.getFieldValues().put("linkTemplate", "[contest_name] [contest_type ");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "linkTemplate",
            "linkTemplate cannot be null and must contain [contest_type] and [contest_name]");
    }

    /**
     * Failure test for setLinkTemplate method in struts environment. The value doesn't contain [contest_name], so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setLinkTemplate_MissingContestName() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        WikiLinkRetrievalAction action = (WikiLinkRetrievalAction) proxy.getAction();

        TestHelper.getFieldValues().put("linkTemplate", "[contest_type] [contest_name ");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "linkTemplate",
            "linkTemplate cannot be null and must contain [contest_type] and [contest_name]");
    }

}
