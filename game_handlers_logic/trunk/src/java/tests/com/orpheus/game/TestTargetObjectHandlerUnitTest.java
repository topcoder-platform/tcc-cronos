/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.easymock.MockControl;
import org.w3c.dom.Element;

import com.orpheus.game.accuracytests.mock.MockHostingSlot;
import com.orpheus.game.accuracytests.mock.MyHttpServletRequest;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;


/**
 * UnitTest for TestTargetObjectHandler class.
 *
 * @author mittu
 * @version 1.0
 */
public class TestTargetObjectHandlerUnitTest extends TestCase {
    /**
     * Represents the handler to test.
     */
    private TestTargetObjectHandler handler;

    /**
     * Represents the MockControl.
     */
    private MockControl reqControl, resControl;

    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(TestTargetObjectHandlerUnitTest.class);
    }

    /**
     * Sets up the environment for the TestCase.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.loadConfig(TestHelper.CONFIG);
        TestHelper.deployEJB();
        reqControl = MockControl.createNiceControl(HttpServletRequest.class);
        resControl = MockControl.createControl(HttpServletResponse.class);
        Map map = new HashMap();
        map.put("gameIdParamKey", "gameId");
        map.put("domainNameParamKey", "domainName");
        map.put("notLoggedInResultCode", "not_logged_in");
        map.put("changedURLResultCode", "url_changed");
        map.put("sequenceNumberParamKey", "seqNo");
        map.put("testFailedResultCode", "test_failed");
        map.put("textParamKey", "text");
        map.put("triggeredURLParamKey", "url");
        handler = new TestTargetObjectHandler(map);
    }

    /**
     * Tears down the environment for the TestCase.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void tearDown() throws Exception {
        handler = null;
        TestHelper.releaseConfigs();
    }

    /**
     * Accuracy test of <code>TestTargetObjectHandler(Map attributes)</code> method.
     *
     * <p>
     * Expects non null instance.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestTargetObjectHandlerAccuracy1() throws Exception {
        Map map = new HashMap();
        map.put("gameIdParamKey", "gameId");
        map.put("domainNameParamKey", "domainName");
        map.put("notLoggedInResultCode", "not_logged_in");
        map.put("changedURLResultCode", "url_changed");
        map.put("sequenceNumberParamKey", "seqNo");
        map.put("testFailedResultCode", "test_failed");
        map.put("textParamKey", "text");
        map.put("triggeredURLParamKey", "url");
        TestTargetObjectHandler targetObjectHandler = new TestTargetObjectHandler(map);
        assertNotNull("failed to create TestTargetObjectHandler", targetObjectHandler);
        assertTrue("failed to create TestTargetObjectHandler", targetObjectHandler instanceof Handler);
    }

    /**
     * Failure test of <code>TestTargetObjectHandler(Map attributes)</code> method.
     *
     * <p>
     * map is null
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestTargetObjectHandlerFailure1() throws Exception {
        try {
            new TestTargetObjectHandler((Map) null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TestTargetObjectHandler(Map attributes)</code> method.
     *
     * <p>
     * gameIdParamKey is null in map.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestTargetObjectHandlerFailure2() throws Exception {
        try {
            Map map = new HashMap();
            map.put("domainNameParamKey", "domainName");
            map.put("notLoggedInResultCode", "not_logged_in");
            map.put("sequenceNumberParamKey", "seqNo");
            map.put("testFailedResultCode", "test_failed");
            map.put("textParamKey", "text");
            new TestTargetObjectHandler(map);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TestTargetObjectHandler(Map attributes)</code> method.
     *
     * <p>
     * gameIdParamKey is empty in map.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestTargetObjectHandlerFailure3() throws Exception {
        try {
            Map map = new HashMap();
            map.put("gameIdParamKey", "  ");
            map.put("domainNameParamKey", "domainName");
            map.put("notLoggedInResultCode", "not_logged_in");
            map.put("sequenceNumberParamKey", "seqNo");
            map.put("testFailedResultCode", "test_failed");
            map.put("textParamKey", "text");
            new TestTargetObjectHandler(map);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TestTargetObjectHandler(Map attributes)</code> method.
     *
     * <p>
     * domainNameParamKey is null in map.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestTargetObjectHandlerFailure4() throws Exception {
        try {
            Map map = new HashMap();
            map.put("gameIdParamKey", "gameId");
            map.put("notLoggedInResultCode", "not_logged_in");
            map.put("sequenceNumberParamKey", "seqNo");
            map.put("testFailedResultCode", "test_failed");
            map.put("textParamKey", "text");
            new TestTargetObjectHandler(map);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TestTargetObjectHandler(Map attributes)</code> method.
     *
     * <p>
     * domainNameParamKey is null in map.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestTargetObjectHandlerFailure5() throws Exception {
        try {
            Map map = new HashMap();
            map.put("gameIdParamKey", "gameId");
            map.put("domainNameParamKey", " ");
            map.put("notLoggedInResultCode", "not_logged_in");
            map.put("sequenceNumberParamKey", "seqNo");
            map.put("testFailedResultCode", "test_failed");
            map.put("textParamKey", "text");
            new TestTargetObjectHandler(map);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TestTargetObjectHandler(Map attributes)</code> method.
     *
     * <p>
     * notLoggedInResultCode is null in map.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestTargetObjectHandlerFailure6() throws Exception {
        try {
            Map map = new HashMap();
            map.put("gameIdParamKey", "gameId");
            map.put("domainNameParamKey", "domainName");
            map.put("sequenceNumberParamKey", "seqNo");
            map.put("testFailedResultCode", "test_failed");
            map.put("textParamKey", "text");
            new TestTargetObjectHandler(map);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TestTargetObjectHandler(Map attributes)</code> method.
     *
     * <p>
     * notLoggedInResultCode is empty in map.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestTargetObjectHandlerFailure7() throws Exception {
        try {
            Map map = new HashMap();
            map.put("gameIdParamKey", "gameId");
            map.put("domainNameParamKey", "domainName");
            map.put("notLoggedInResultCode", " ");
            map.put("sequenceNumberParamKey", "seqNo");
            map.put("testFailedResultCode", "test_failed");
            map.put("textParamKey", "text");
            new TestTargetObjectHandler(map);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TestTargetObjectHandler(Map attributes)</code> method.
     *
     * <p>
     * sequenceNumberParamKey is null in map.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestTargetObjectHandlerFailure8() throws Exception {
        try {
            Map map = new HashMap();
            map.put("gameIdParamKey", "gameId");
            map.put("domainNameParamKey", "domainName");
            map.put("notLoggedInResultCode", "not_logged_in");
            map.put("testFailedResultCode", "test_failed");
            map.put("textParamKey", "text");
            new TestTargetObjectHandler(map);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TestTargetObjectHandler(Map attributes)</code> method.
     *
     * <p>
     * sequenceNumberParamKey is empty in map.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestTargetObjectHandlerFailure9() throws Exception {
        try {
            Map map = new HashMap();
            map.put("gameIdParamKey", "gameId");
            map.put("domainNameParamKey", "domainName");
            map.put("notLoggedInResultCode", "not_logged_in");
            map.put("sequenceNumberParamKey", " ");
            map.put("testFailedResultCode", "test_failed");
            map.put("textParamKey", "text");
            new TestTargetObjectHandler(map);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TestTargetObjectHandler(Map attributes)</code> method.
     *
     * <p>
     * testFailedResultCode is null in map.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestTargetObjectHandlerFailure10() throws Exception {
        try {
            Map map = new HashMap();
            map.put("gameIdParamKey", "gameId");
            map.put("domainNameParamKey", "domainName");
            map.put("notLoggedInResultCode", "not_logged_in");
            map.put("sequenceNumberParamKey", "seqNo");
            map.put("textParamKey", "text");
            new TestTargetObjectHandler(map);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TestTargetObjectHandler(Map attributes)</code> method.
     *
     * <p>
     * testFailedResultCode is empty in map.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestTargetObjectHandlerFailure11() throws Exception {
        try {
            Map map = new HashMap();
            map.put("gameIdParamKey", "gameId");
            map.put("domainNameParamKey", "domainName");
            map.put("notLoggedInResultCode", "not_logged_in");
            map.put("sequenceNumberParamKey", "seqNo");
            map.put("testFailedResultCode", " ");
            map.put("textParamKey", "text");
            new TestTargetObjectHandler(map);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TestTargetObjectHandler(Map attributes)</code> method.
     *
     * <p>
     * textParamKey is null in map.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestTargetObjectHandlerFailure12() throws Exception {
        try {
            Map map = new HashMap();
            map.put("gameIdParamKey", "gameId");
            map.put("domainNameParamKey", "domainName");
            map.put("notLoggedInResultCode", "not_logged_in");
            map.put("sequenceNumberParamKey", "seqNo");
            map.put("testFailedResultCode", "test_failed");
            new TestTargetObjectHandler(map);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TestTargetObjectHandler(Map attributes)</code> method.
     *
     * <p>
     * textParamKey is empty in map.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestTargetObjectHandlerFailure13() throws Exception {
        try {
            Map map = new HashMap();
            map.put("gameIdParamKey", "gameId");
            map.put("domainNameParamKey", "domainName");
            map.put("notLoggedInResultCode", "not_logged_in");
            map.put("sequenceNumberParamKey", "seqNo");
            map.put("testFailedResultCode", "test_failed");
            map.put("textParamKey", " ");
            new TestTargetObjectHandler(map);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>TestTargetObjectHandler(Element element)</code> method.
     *
     * <p>
     * Expects non null instance.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestTargetObjectHandlerAccuracy2() throws Exception {
        TestTargetObjectHandler targetObjectHandler = new TestTargetObjectHandler(TestHelper.getDomElement(
                "TestTargetObjectHandler.xml", false));
        assertNotNull("failed to create TestTargetObjectHandler", targetObjectHandler);
        assertTrue("failed to create TestTargetObjectHandler", targetObjectHandler instanceof Handler);
    }

    /**
     * Failure test of <code>TestTargetObjectHandler(Element element)</code> method.
     *
     * <p>
     * element is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestTargetObjectHandlerFailure14() throws Exception {
        try {
            new TestTargetObjectHandler((Element) null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TestTargetObjectHandler(Element element)</code> method.
     *
     * <p>
     * element - game_id_param_key is missing.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestTargetObjectHandlerFailure15() throws Exception {
        try {
            new TestTargetObjectHandler(TestHelper.getDomElement("TestTargetObjectHandler1.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TestTargetObjectHandler(Element element)</code> method.
     *
     * <p>
     * element - game_id_param_key is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestTargetObjectHandlerFailure16() throws Exception {
        try {
            new TestTargetObjectHandler(TestHelper.getDomElement("TestTargetObjectHandler2.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TestTargetObjectHandler(Element element)</code> method.
     *
     * <p>
     * element - domain_name_param_key is missing.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestTargetObjectHandlerFailure17() throws Exception {
        try {
            new TestTargetObjectHandler(TestHelper.getDomElement("TestTargetObjectHandler3.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TestTargetObjectHandler(Element element)</code> method.
     *
     * <p>
     * element - domain_name_param_key is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestTargetObjectHandlerFailure18() throws Exception {
        try {
            new TestTargetObjectHandler(TestHelper.getDomElement("TestTargetObjectHandler4.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TestTargetObjectHandler(Element element)</code> method.
     *
     * <p>
     * element - sequence_number_param_key is missing.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestTargetObjectHandlerFailure19() throws Exception {
        try {
            new TestTargetObjectHandler(TestHelper.getDomElement("TestTargetObjectHandler5.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TestTargetObjectHandler(Element element)</code> method.
     *
     * <p>
     * element - sequence_number_param_key is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestTargetObjectHandlerFailure20() throws Exception {
        try {
            new TestTargetObjectHandler(TestHelper.getDomElement("TestTargetObjectHandler6.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TestTargetObjectHandler(Element element)</code> method.
     *
     * <p>
     * element - text_param_key is missing.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestTargetObjectHandlerFailure21() throws Exception {
        try {
            new TestTargetObjectHandler(TestHelper.getDomElement("TestTargetObjectHandler7.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TestTargetObjectHandler(Element element)</code> method.
     *
     * <p>
     * element - text_param_key is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestTargetObjectHandlerFailure22() throws Exception {
        try {
            new TestTargetObjectHandler(TestHelper.getDomElement("TestTargetObjectHandler8.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TestTargetObjectHandler(Element element)</code> method.
     *
     * <p>
     * element - test_failed_result_code is missing.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestTargetObjectHandlerFailure23() throws Exception {
        try {
            new TestTargetObjectHandler(TestHelper.getDomElement("TestTargetObjectHandler9.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TestTargetObjectHandler(Element element)</code> method.
     *
     * <p>
     * element - test_failed_result_code is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestTargetObjectHandlerFailure24() throws Exception {
        try {
            new TestTargetObjectHandler(TestHelper.getDomElement("TestTargetObjectHandler10.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TestTargetObjectHandler(Element element)</code> method.
     *
     * <p>
     * element - not_logged_in_result_code is missing.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestTargetObjectHandlerFailure25() throws Exception {
        try {
            new TestTargetObjectHandler(TestHelper.getDomElement("TestTargetObjectHandler11.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TestTargetObjectHandler(Element element)</code> method.
     *
     * <p>
     * element - not_logged_in_result_code is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestTargetObjectHandlerFailure26() throws Exception {
        try {
            new TestTargetObjectHandler(TestHelper.getDomElement("TestTargetObjectHandler12.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>execute(ActionContext context)</code> method.
     *
     * <p>
     * Expects the method to execute without exceptions. Also does the validations.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testExecuteAccuracy() throws Exception {
        // creates the mocks properly.
        HttpServletRequest request = (HttpServletRequest) reqControl.getMock();
        HttpServletResponse response = (HttpServletResponse) resControl.getMock();        
        reqControl.replay();
        MyHttpServletRequest wrapper = new MyHttpServletRequest(request);
        wrapper.setNullSesssion(false);
        wrapper.setParameter("gameId", "2");
        wrapper.setParameter("seqNo", "999");
        wrapper.setParameter("text", "text");
        wrapper.setParameter("domainName", "domain");    
        wrapper.setParameter("url", "www.topcoder.com/tc");
        ActionContext context = new ActionContext(wrapper, response);
        // invoke the execute.
        assertEquals("failed to execute handler", handler.execute(context), "test_failed");
        wrapper.setParameter("gameId", "999");
        context = new ActionContext(wrapper, response);
        assertNull("failed to execute handler", handler.execute(context));
    }

    /**
     * Failure test of <code>execute(ActionContext context)</code> method.
     *
     * <p>
     * context is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testExecuteFailure1() throws Exception {
        try {
            handler.execute(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>execute(ActionContext context)</code> method.
     *
     * <p>
     * game id missing.
     * </p>
     *
     * <p>
     * Expect HandlerExecutionException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testExecuteFailure2() throws Exception {
        try {
            // creates the mocks properly.
            HttpServletRequest request = (HttpServletRequest) reqControl.getMock();
            HttpServletResponse response = (HttpServletResponse) resControl.getMock();
            reqControl.expectAndReturn(request.getParameter("seqNo"), "2");
            reqControl.expectAndReturn(request.getParameter("text"), "test");
            reqControl.expectAndReturn(request.getParameter("domainName"), "junit");
            reqControl.replay();
            MyHttpServletRequest wrapper = new MyHttpServletRequest(request);
            wrapper.setNullSesssion(false);
            ActionContext context = new ActionContext(wrapper, response);
            handler.execute(context);
            fail("Expect HandlerExecutionException.");
        } catch (HandlerExecutionException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>execute(ActionContext context)</code> method.
     *
     * <p>
     * seq no missing.
     * </p>
     *
     * <p>
     * Expect HandlerExecutionException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testExecuteFailure3() throws Exception {
        try {
            // creates the mocks properly.
            HttpServletRequest request = (HttpServletRequest) reqControl.getMock();
            HttpServletResponse response = (HttpServletResponse) resControl.getMock();
            reqControl.expectAndReturn(request.getParameter("gameId"), "101");
            reqControl.expectAndReturn(request.getParameter("text"), "test");
            reqControl.expectAndReturn(request.getParameter("domainName"), "junit");
            reqControl.replay();
            MyHttpServletRequest wrapper = new MyHttpServletRequest(request);
            wrapper.setNullSesssion(false);
            ActionContext context = new ActionContext(wrapper, response);
            handler.execute(context);
            fail("Expect HandlerExecutionException.");
        } catch (HandlerExecutionException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>execute(ActionContext context)</code> method.
     *
     * <p>
     * text missing.
     * </p>
     *
     * <p>
     * Expect HandlerExecutionException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testExecuteFailure4() throws Exception {
        try {
            // creates the mocks properly.
            HttpServletRequest request = (HttpServletRequest) reqControl.getMock();
            HttpServletResponse response = (HttpServletResponse) resControl.getMock();
            reqControl.expectAndReturn(request.getParameter("gameId"), "101");
            reqControl.expectAndReturn(request.getParameter("seqNo"), "2");
            reqControl.expectAndReturn(request.getParameter("domainName"), "junit");
            reqControl.replay();
            MyHttpServletRequest wrapper = new MyHttpServletRequest(request);
            wrapper.setNullSesssion(false);
            ActionContext context = new ActionContext(wrapper, response);
            handler.execute(context);
            fail("Expect HandlerExecutionException.");
        } catch (HandlerExecutionException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>execute(ActionContext context)</code> method.
     *
     * <p>
     * domainName missing.
     * </p>
     *
     * <p>
     * Expect HandlerExecutionException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testExecuteFailure5() throws Exception {
        try {
            // creates the mocks properly.
            HttpServletRequest request = (HttpServletRequest) reqControl.getMock();
            HttpServletResponse response = (HttpServletResponse) resControl.getMock();
            reqControl.expectAndReturn(request.getParameter("gameId"), "101");
            reqControl.expectAndReturn(request.getParameter("seqNo"), "2");
            reqControl.expectAndReturn(request.getParameter("text"), "test");            
            reqControl.replay();
            MyHttpServletRequest wrapper = new MyHttpServletRequest(request);
            wrapper.setNullSesssion(false);
            ActionContext context = new ActionContext(wrapper, response);
            handler.execute(context);
            fail("Expect HandlerExecutionException.");
        } catch (HandlerExecutionException e) {
            // expect
        }
    }
}
