/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.servlet.http.HttpServletRequest;

import junit.framework.JUnit4TestAdapter;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.json.object.JSONObject;
import com.topcoder.web.tc.ContestServicesConfigurationException;
import com.topcoder.web.tc.TestHelper;

/**
 * <p>
 * Unit tests for class <code>BaseJSONParameterAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseJSONParameterActionTest {
    /**
     * <p>
     * Represents the <code>BaseJSONParameterAction</code> instance used to test against.
     * </p>
     */
    private BaseJSONParameterAction impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BaseJSONParameterActionTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new MockBaseJSONParameterAction();
    }

    /**
     * <p>
     * Tear down the test environment.
     * </p>
     */
    @After
    public void tearDown() {
        impl = null;
    }

    /**
     * <p>
     * Inheritance test, verifies <code>BaseJSONParameterAction</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof ActionSupport);
        assertTrue("The instance's subclass is not correct.", impl instanceof ServletRequestAware);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ActiveContestsManagerImpl()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getParameterAsJSONObject()</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetParameterAsJSONObject() throws Exception {
        HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
        impl.setServletRequest(servletRequest);
        EasyMock.expect(servletRequest.getParameter("parameter")).andReturn(
            "{\"columnName\":\"type\",\"sortingOrder\":\"asc\"}");

        EasyMock.replay(servletRequest);

        JSONObject result = impl.getParameterAsJSONObject();

        assertEquals("The return value should be same as ", "type", result.getString("columnName"));
        assertEquals("The return value should be same as ", "asc", result.getString("sortingOrder"));

        EasyMock.verify(servletRequest);
    }

    /**
     * <p>
     * Failure test for the method <code>getParameterAsJSONObject()</code>.<br>
     * The json string is invalid.<br>
     * Expect ContestServicesActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContestServicesActionException.class)
    public void testGetParameterAsJSONObject_JsonStringInvalid() throws Exception {
        HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
        impl.setServletRequest(servletRequest);
        EasyMock.expect(servletRequest.getParameter("parameter")).andReturn(
            "{columnName:type:sortingOrder:asc}");

        EasyMock.replay(servletRequest);

        impl.getParameterAsJSONObject();
    }

    /**
     * <p>
     * Accuracy test for the method <code>setParameterKey(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetParameterKey() throws Exception {
        String result = (String) TestHelper.getField(BaseJSONParameterAction.class, impl, "parameterKey");

        assertEquals("The initial value should be same as ", "parameter", result);

        impl.setParameterKey("ppp");

        result = (String) TestHelper.getField(BaseJSONParameterAction.class, impl, "parameterKey");

        assertEquals("The return value should be same as ", "ppp", result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setDateFormatString(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetDateFormatString() {
        assertEquals("The initial value should be same as ", "yyyy/MM/dd", impl.getDateFormatString());

        impl.setDateFormatString("yyyy-MM-dd");

        assertEquals("The return value should be same as ", "yyyy-MM-dd", impl.getDateFormatString());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setDateFormatString(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetDateFormatString() {
        assertEquals("The initial value should be same as ", "yyyy/MM/dd", impl.getDateFormatString());

        impl.setDateFormatString("yyyy-MM-dd");

        assertEquals("The return value should be same as ", "yyyy-MM-dd", impl.getDateFormatString());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setServletRequest(HttpServletRequest)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetServletRequest() throws Exception {
        HttpServletRequest result = (HttpServletRequest) TestHelper.getField(BaseJSONParameterAction.class,
            impl, "servletRequest");

        assertNull("The initial value should be null", result);

        HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
        impl.setServletRequest(servletRequest);

        result = (HttpServletRequest) TestHelper.getField(BaseJSONParameterAction.class, impl,
            "servletRequest");

        assertEquals("The return value should be same as ", servletRequest, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>checkConfiguration()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testCheckConfiguration() {
        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The parameterKey is null.<br>
     * Expect ContestServicesConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContestServicesConfigurationException.class)
    public void testCheckConfiguration_ParameterKeyNull() throws Exception {
        impl.setParameterKey(null);

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The dateFormatString is empty.<br>
     * Expect ContestServicesConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContestServicesConfigurationException.class)
    public void testCheckConfiguration_DateFormatStringEmpty() throws Exception {
        impl.setDateFormatString("  ");

        impl.checkConfiguration();
    }

    /**
     * <p>
     * The mock class for tests.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    class MockBaseJSONParameterAction extends BaseJSONParameterAction {
        // Empty
    }

}
