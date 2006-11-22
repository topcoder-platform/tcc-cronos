/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game.failuretests;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easymock.MockControl;

import com.orpheus.game.TestTargetObjectHandler;
import com.orpheus.game.accuracytests.mock.MyHttpServletRequest;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

import junit.framework.TestCase;

/**
 * Test case for <code>TestTargetObjectHandler</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class TestTargetObjectHandlerTest extends TestCase {

    /**
     * Represents the handler to test.
     */
    private TestTargetObjectHandler handler;

    /**
     * Represents the attributes.
     */
    private Map attributes;

    /**
     * Set up the environment.
     */
    protected void setUp() throws Exception {
        super.setUp();
        FailureTestHelper.loadConfig();
        attributes = new HashMap();
        attributes.put("gameIdParamKey", "gameIdParamKey");
        attributes.put("domainNameParamKey", "domainNameParamKey");
        attributes.put("sequenceNumberParamKey", "sequenceNumberParamKey");
        attributes.put("textParamKey", "textParamKey");
        attributes.put("testFailedResultCode", "testFailedResultCode");
        attributes.put("notLoggedInResultCode", "notLoggedInResultCode");
        handler = new TestTargetObjectHandler(attributes);
    }

    /**
     * Clean up the environment.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        FailureTestHelper.unloadConfig();
    }

    /**
     * Test method for TestTargetObjectHandler(java.util.Map).
     * In this case, the map is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testTestTargetObjectHandler_Map_NullMap() {
        try {
            new TestTargetObjectHandler((Map) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for TestTargetObjectHandler(java.util.Map).
     * In this case, game id param key is missing.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testTestTargetObjectHandler_Map_MissingGameIdParamKey() {
        try {
            attributes.remove("gameIdParamKey");
            new TestTargetObjectHandler(attributes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for TestTargetObjectHandler(java.util.Map).
     * In this case, game id param key is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testTestTargetObjectHandler_Map_NullGameIdParamKey() {
        try {
            attributes.put("gameIdParamKey", null);
            new TestTargetObjectHandler(attributes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for TestTargetObjectHandler(java.util.Map).
     * In this case, game id param key is empty.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testTestTargetObjectHandler_Map_EmptyGameIdParamKey() {
        try {
            attributes.put("gameIdParamKey", " ");
            new TestTargetObjectHandler(attributes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for TestTargetObjectHandler(java.util.Map).
     * In this case, domainNameParamKey is missing.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testTestTargetObjectHandler_Map_MissingdomainNameParamKey() {
        try {
            attributes.remove("domainNameParamKey");
            new TestTargetObjectHandler(attributes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for TestTargetObjectHandler(java.util.Map).
     * In this case, domainNameParamKey is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testTestTargetObjectHandler_Map_NulldomainNameParamKey() {
        try {
            attributes.put("domainNameParamKey", null);
            new TestTargetObjectHandler(attributes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for TestTargetObjectHandler(java.util.Map).
     * In this case, domainNameParamKey is empty.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testTestTargetObjectHandler_Map_EmptydomainNameParamKey() {
        try {
            attributes.put("domainNameParamKey", " ");
            new TestTargetObjectHandler(attributes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for TestTargetObjectHandler(java.util.Map).
     * In this case, sequenceNumberParamKey is missing.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testTestTargetObjectHandler_Map_MissingsequenceNumberParamKey() {
        try {
            attributes.remove("sequenceNumberParamKey");
            new TestTargetObjectHandler(attributes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for TestTargetObjectHandler(java.util.Map).
     * In this case, sequenceNumberParamKey is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testTestTargetObjectHandler_Map_NullsequenceNumberParamKey() {
        try {
            attributes.put("sequenceNumberParamKey", null);
            new TestTargetObjectHandler(attributes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for TestTargetObjectHandler(java.util.Map).
     * In this case, sequenceNumberParamKey is empty.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testTestTargetObjectHandler_Map_EmptysequenceNumberParamKey() {
        try {
            attributes.put("sequenceNumberParamKey", " ");
            new TestTargetObjectHandler(attributes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for TestTargetObjectHandler(java.util.Map).
     * In this case, textParamKey is missing.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testTestTargetObjectHandler_Map_MissingtextParamKey() {
        try {
            attributes.remove("textParamKey");
            new TestTargetObjectHandler(attributes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for TestTargetObjectHandler(java.util.Map).
     * In this case, textParamKey is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testTestTargetObjectHandler_Map_NulltextParamKey() {
        try {
            attributes.put("textParamKey", null);
            new TestTargetObjectHandler(attributes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for TestTargetObjectHandler(java.util.Map).
     * In this case, textParamKey is empty.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testTestTargetObjectHandler_Map_EmptytextParamKey() {
        try {
            attributes.put("textParamKey", " ");
            new TestTargetObjectHandler(attributes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for TestTargetObjectHandler(java.util.Map).
     * In this case, testFailedResultCode is missing.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testTestTargetObjectHandler_Map_MissingtestFailedResultCode() {
        try {
            attributes.remove("testFailedResultCode");
            new TestTargetObjectHandler(attributes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for TestTargetObjectHandler(java.util.Map).
     * In this case, testFailedResultCode is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testTestTargetObjectHandler_Map_NulltestFailedResultCode() {
        try {
            attributes.put("testFailedResultCode", null);
            new TestTargetObjectHandler(attributes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for TestTargetObjectHandler(java.util.Map).
     * In this case, testFailedResultCode is empty.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testTestTargetObjectHandler_Map_EmptytestFailedResultCode() {
        try {
            attributes.put("testFailedResultCode", " ");
            new TestTargetObjectHandler(attributes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for TestTargetObjectHandler(java.util.Map).
     * In this case, notLoggedInResultCode is missing.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testTestTargetObjectHandler_Map_MissingnotLoggedInResultCode() {
        try {
            attributes.remove("notLoggedInResultCode");
            new TestTargetObjectHandler(attributes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for TestTargetObjectHandler(java.util.Map).
     * In this case, notLoggedInResultCode is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testTestTargetObjectHandler_Map_NullnotLoggedInResultCode() {
        try {
            attributes.put("notLoggedInResultCode", null);
            new TestTargetObjectHandler(attributes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for TestTargetObjectHandler(java.util.Map).
     * In this case, notLoggedInResultCode is empty.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testTestTargetObjectHandler_Map_EmptynotLoggedInResultCode() {
        try {
            attributes.put("notLoggedInResultCode", " ");
            new TestTargetObjectHandler(attributes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for execute(ActionContext).
     * In this case, the context is null.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testExecute_NullContext() throws Exception {
        try {
            handler.execute(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for execute(ActionContext).
     * In this case, the context is null.
     * Expected : {@link HandlerExecutionException}.
     * @throws Exception to JUnit
     */
    public void testExecute_FailedToLoad() throws Exception {
        try {
            MockControl reqControl = MockControl.createNiceControl(HttpServletRequest.class);
            MockControl resControl = MockControl.createControl(HttpServletResponse.class);
            reqControl.replay();
            MyHttpServletRequest wrapper = new MyHttpServletRequest((HttpServletRequest) reqControl.getMock());
            wrapper.setNullSesssion(false);
            ActionContext ac = new ActionContext(
                    wrapper,
                    (HttpServletResponse) resControl.getMock());
            handler.execute(ac);
            fail("HandlerExecutionException expected.");
        } catch (HandlerExecutionException e) {
            // should land here
        }
    }

}
