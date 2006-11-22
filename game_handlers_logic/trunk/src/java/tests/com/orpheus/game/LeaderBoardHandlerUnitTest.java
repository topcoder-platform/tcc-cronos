/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.easymock.MockControl;
import org.mockejb.jndi.MockContextFactory;
import org.w3c.dom.Element;

import com.topcoder.user.profile.BaseProfileType;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.SimpleUserProfileManager;
import com.topcoder.user.profile.manager.UserProfileManager;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;


/**
 * UnitTest for LeaderBoardHandler class.
 *
 * @author mittu
 * @version 1.0
 */
public class LeaderBoardHandlerUnitTest extends TestCase {
    /**
     * Represents the handler to test.
     */
    private LeaderBoardHandler handler;

    /**
     * Represents the UserProfileManager.
     */
    private UserProfileManager manager;

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
        return new TestSuite(LeaderBoardHandlerUnitTest.class);
    }

    /**
     * Sets up the environment for the TestCase.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        File file = new File(TestHelper.USER_DIR + "test_files" + File.separator + "profiles.xml");
        file.delete();
        TestHelper.loadConfig(TestHelper.CONFIG);
        TestHelper.deployEJB();
        reqControl = MockControl.createNiceControl(HttpServletRequest.class);
        resControl = MockControl.createControl(HttpServletResponse.class);
        Map map = new HashMap();
        map.put("gameIdParamKey", "gameId");
        map.put("profilesKey", "profiles");
        map.put("maxLeaders", new Integer(5));
        manager = new SimpleUserProfileManager("com.topcoder.user.profile.manager");
        map.put("profileManager", manager);
        handler = new LeaderBoardHandler(map);
    }

    /**
     * Tears down the environment for the TestCase.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void tearDown() throws Exception {
        handler = null;
        manager = null;
        TestHelper.releaseConfigs();
    }

    /**
     * Accuracy test of <code>LeaderBoardHandler(Map attributes)</code> method.
     *
     * <p>
     * Expects non null instance.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLeaderBoardHandlerAccuracy1() throws Exception {
        Map map = new HashMap();
        map.put("gameIdParamKey", "gameId");
        map.put("profilesKey", "profiles");
        map.put("maxLeaders", new Integer(5));
        map.put("profileManager", manager);
        LeaderBoardHandler boardHandler = new LeaderBoardHandler(map);
        assertNotNull("failed to create LeaderBoardHandler", boardHandler);
        assertTrue("failed to create LeaderBoardHandler", boardHandler instanceof Handler);
    }

    /**
     * Failure test of <code>LeaderBoardHandler(Map attributes)</code> method.
     *
     * <p>
     * attributes is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLeaderBoardHandlerFailure1() throws Exception {
        try {
            new LeaderBoardHandler((Map) null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>LeaderBoardHandler(Map attributes)</code> method.
     *
     * <p>
     * attributes - gameIdParamKey is missing.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLeaderBoardHandlerFailure2() throws Exception {
        try {
            Map map = new HashMap();
            map.put("profilesKey", "profiles");
            map.put("maxLeaders", new Integer(5));
            map.put("profileManager", manager);
            new LeaderBoardHandler(map);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>LeaderBoardHandler(Map attributes)</code> method.
     *
     * <p>
     * attributes - gameIdParamKey is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLeaderBoardHandlerFailure3() throws Exception {
        try {
            Map map = new HashMap();
            map.put("gameIdParamKey", " ");
            map.put("profilesKey", "profiles");
            map.put("maxLeaders", new Integer(5));
            map.put("profileManager", manager);
            new LeaderBoardHandler(map);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>LeaderBoardHandler(Map attributes)</code> method.
     *
     * <p>
     * attributes - profilesKey is missing.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLeaderBoardHandlerFailure4() throws Exception {
        try {
            Map map = new HashMap();
            map.put("gameIdParamKey", "gameId");
            map.put("maxLeaders", new Integer(5));
            map.put("profileManager", manager);
            new LeaderBoardHandler(map);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>LeaderBoardHandler(Map attributes)</code> method.
     *
     * <p>
     * attributes - profilesKey is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLeaderBoardHandlerFailure5() throws Exception {
        try {
            Map map = new HashMap();
            map.put("gameIdParamKey", "gameId");
            map.put("profilesKey", " ");
            map.put("maxLeaders", new Integer(5));
            map.put("profileManager", manager);
            new LeaderBoardHandler(map);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>LeaderBoardHandler(Map attributes)</code> method.
     *
     * <p>
     * attributes - maxLeaders is missing.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLeaderBoardHandlerFailure6() throws Exception {
        try {
            Map map = new HashMap();
            map.put("gameIdParamKey", "gameId");
            map.put("profilesKey", "profiles");
            map.put("profileManager", manager);
            new LeaderBoardHandler(map);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>LeaderBoardHandler(Map attributes)</code> method.
     *
     * <p>
     * attributes - maxLeaders is invalid.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLeaderBoardHandlerFailure7() throws Exception {
        try {
            Map map = new HashMap();
            map.put("gameIdParamKey", "gameId");
            map.put("profilesKey", "profiles");
            map.put("maxLeaders", new Object());
            map.put("profileManager", manager);
            new LeaderBoardHandler(map);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>LeaderBoardHandler(Map attributes)</code> method.
     *
     * <p>
     * attributes - maxLeaders is negative.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLeaderBoardHandlerFailure8() throws Exception {
        try {
            Map map = new HashMap();
            map.put("gameIdParamKey", "gameId");
            map.put("profilesKey", "profiles");
            map.put("maxLeaders", new Integer(-8));
            map.put("profileManager", manager);
            new LeaderBoardHandler(map);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>LeaderBoardHandler(Map attributes)</code> method.
     *
     * <p>
     * attributes - profileManager is missing.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLeaderBoardHandlerFailure9() throws Exception {
        try {
            Map map = new HashMap();
            map.put("gameIdParamKey", "gameId");
            map.put("profilesKey", "profiles");
            map.put("maxLeaders", new Integer(-8));
            new LeaderBoardHandler(map);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>LeaderBoardHandler(Map attributes)</code> method.
     *
     * <p>
     * attributes - profileManager is invalid.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLeaderBoardHandlerFailure10() throws Exception {
        try {
            Map map = new HashMap();
            map.put("gameIdParamKey", "gameId");
            map.put("profilesKey", "profiles");
            map.put("maxLeaders", new Integer(-8));
            map.put("profileManager", new Object());
            new LeaderBoardHandler(map);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>LeaderBoardHandler(Element element)</code> method.
     *
     * <p>
     * Expects non null instance.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLeaderBoardHandlerAccuracy2() throws Exception {
        LeaderBoardHandler boardHandler = new LeaderBoardHandler(TestHelper.getDomElement("LeaderBoardHandler.xml",
                false));
        assertNotNull("failed to create LeaderBoardHandler", boardHandler);
        assertTrue("failed to create LeaderBoardHandler", boardHandler instanceof Handler);
    }

    /**
     * Failure test of <code>LeaderBoardHandler(Element element)</code> method.
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
    public void testLeaderBoardHandlerFailure11() throws Exception {
        try {
            new LeaderBoardHandler((Element) null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>LeaderBoardHandler(Element element)</code> method.
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
    public void testLeaderBoardHandlerFailure12() throws Exception {
        try {
            new LeaderBoardHandler(TestHelper.getDomElement("LeaderBoardHandler1.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>LeaderBoardHandler(Element element)</code> method.
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
    public void testLeaderBoardHandlerFailure13() throws Exception {
        try {
            new LeaderBoardHandler(TestHelper.getDomElement("LeaderBoardHandler2.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>LeaderBoardHandler(Element element)</code> method.
     *
     * <p>
     * element - profiles_key is missing.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLeaderBoardHandlerFailure14() throws Exception {
        try {
            new LeaderBoardHandler(TestHelper.getDomElement("LeaderBoardHandler3.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>LeaderBoardHandler(Element element)</code> method.
     *
     * <p>
     * element - profiles_key is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLeaderBoardHandlerFailure15() throws Exception {
        try {
            new LeaderBoardHandler(TestHelper.getDomElement("LeaderBoardHandler4.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>LeaderBoardHandler(Element element)</code> method.
     *
     * <p>
     * element - max_leaders is missing.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLeaderBoardHandlerFailure16() throws Exception {
        try {
            new LeaderBoardHandler(TestHelper.getDomElement("LeaderBoardHandler5.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>LeaderBoardHandler(Element element)</code> method.
     *
     * <p>
     * element - max_leaders is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLeaderBoardHandlerFailure17() throws Exception {
        try {
            new LeaderBoardHandler(TestHelper.getDomElement("LeaderBoardHandler6.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>LeaderBoardHandler(Element element)</code> method.
     *
     * <p>
     * element - max_leaders is invalid.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLeaderBoardHandlerFailure18() throws Exception {
        try {
            new LeaderBoardHandler(TestHelper.getDomElement("LeaderBoardHandler7.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>LeaderBoardHandler(Element element)</code> method.
     *
     * <p>
     * element - max_leaders is zero.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLeaderBoardHandlerFailure19() throws Exception {
        try {
            new LeaderBoardHandler(TestHelper.getDomElement("LeaderBoardHandler8.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>LeaderBoardHandler(Element element)</code> method.
     *
     * <p>
     * element - object_factory_namespace is missing.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLeaderBoardHandlerFailure20() throws Exception {
        try {
            new LeaderBoardHandler(TestHelper.getDomElement("LeaderBoardHandler9.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>LeaderBoardHandler(Element element)</code> method.
     *
     * <p>
     * element - object_factory_namespace is invalid.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLeaderBoardHandlerFailure21() throws Exception {
        try {
            new LeaderBoardHandler(TestHelper.getDomElement("LeaderBoardHandler10.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>LeaderBoardHandler(Element element)</code> method.
     *
     * <p>
     * element - user_profile_manager_token is missing.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLeaderBoardHandlerFailure22() throws Exception {
        try {
            new LeaderBoardHandler(TestHelper.getDomElement("LeaderBoardHandler11.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>LeaderBoardHandler(Element element)</code> method.
     *
     * <p>
     * element - user_profile_manager_token is invalid.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLeaderBoardHandlerFailure23() throws Exception {
        try {
            new LeaderBoardHandler(TestHelper.getDomElement("LeaderBoardHandler12.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>execute(ActionContext context)</code> method.
     *
     * <p>
     * Expects the method to execute without exceptions.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testExecuteAccuracy() throws Exception {
        createProfile();
        // creates the mocks properly.
        HttpServletRequest request = (HttpServletRequest) reqControl.getMock();
        HttpServletResponse response = (HttpServletResponse) resControl.getMock();
        reqControl.expectAndReturn(request.getParameter("gameId"), "101");
        reqControl.replay();
        ActionContext context = new ActionContext(request, response);
        // invoke the execute.
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
     * game id not present in request.
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
            // creates the mocks.
            HttpServletRequest request = (HttpServletRequest) reqControl.getMock();
            HttpServletResponse response = (HttpServletResponse) resControl.getMock();
            ActionContext context = new ActionContext(request, response);
            // invoke the execute.
            handler.execute(context);
            fail("Expect HandlerExecutionException.");
        } catch (HandlerExecutionException e) {
            // expect
        }
    }
  
    /**
     * Creates the required user profiles for testing.
     *
     * @throws Exception
     *             throw exception to junit.
     */
    private void createProfile() throws Exception {
        File file = new File(TestHelper.USER_DIR + "test_files" + File.separator + "profiles.xml");
        file.delete();
        long[] ids = new long[] {122, 58, 207, 199, 189};
        // Construct a profile
        for (int i = 0; i < ids.length; i++) {
            UserProfile profile = new UserProfile(new Long(ids[i]));
            profile.setProperty(BaseProfileType.FIRST_NAME, "User_" + i);
            manager.createUserProfile(profile);
        }
        manager.commit();
    }
}
