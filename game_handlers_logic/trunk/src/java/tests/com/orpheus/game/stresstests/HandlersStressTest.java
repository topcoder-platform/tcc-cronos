/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.stresstests;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;

import org.easymock.MockControl;
import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.orpheus.game.ActiveGamesHandler;
import com.orpheus.game.GameDetailHandler;
import com.orpheus.game.LeaderBoardHandler;
import com.orpheus.game.SlotValidationHandler;
import com.orpheus.game.TestDomainHandler;
import com.orpheus.game.TestHelper;
import com.orpheus.game.TestTargetObjectHandler;
import com.orpheus.game.UnlockedDomainsHandler;
import com.orpheus.game.UpcomingDomainsHandler;
import com.orpheus.game.UserGamesHandler;
import com.orpheus.game.accuracytests.mock.MockBean;
import com.orpheus.game.accuracytests.mock.MyHttpServletRequest;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataHome;
import com.topcoder.user.profile.BaseProfileType;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.SimpleUserProfileManager;
import com.topcoder.user.profile.manager.UserProfileManager;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;

/**
 * <p>
 * Stress test for all implementation of <code>Handler</code> interface.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class HandlersStressTest extends TestCase {

    /**
     * <p>
     * Represents the config file for stress testing.
     * </p>
     */
    private static final String STRESS_CONFIG_FILE = "stresstests" + File.separator + "stress.xml";

    /**
     * <p>
     * Represents the small iteration number for stress testing.
     * </p>
     */
    private static final int SMALL_ITER = 10;

    /**
     * <p>
     * Represents the medium iteration number for stress testing.
     * </p>
     */
    private static final int MEDIUM_ITER = 100;

    /**
     * <p>
     * Represents the large iteration number for stress testing.
     * </p>
     */
    private static final int LARGE_ITER = 1000;

    /**
     * Represents whether the mock ejb to be deployed.
     */
    private static boolean deployed = false;

    /**
     * Represents the MockControl.
     */
    private MockControl reqControl, resControl;

    /**
     * Sets up the environment for the TestCase.
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        loadNamespace();
        deployEJB();
        reqControl = MockControl.createNiceControl(HttpServletRequest.class);
        resControl = MockControl.createControl(HttpServletResponse.class);
    }

    /**
     * Deploys the mock ejb for testing purpose.
     * @throws Exception
     *             throw exception to junit.
     */
    private static void deployEJB() throws Exception {
        if (!deployed) {
            /*
             * Deploy EJBs to the MockContainer if we run outside of the app server In cactus mode all but one EJB are
             * deployed by the app server, so we don't need to do it.
             */

            MockContextFactory.setAsInitial();

            // Create an instance of the MockContainer and pass the JNDI context that
            // it will use to bind EJBs.
            MockContainer mockContainer = new MockContainer(new InitialContext());

            /*
             * Create the deployment descriptor of the bean. Stateless and Stateful beans both use
             * SessionBeanDescriptor.
             */
            SessionBeanDescriptor statefulSampleDescriptor = new SessionBeanDescriptor("mock_game_data",
                GameDataHome.class, GameData.class, MockBean.class);
            // Mark this bean as stateful. Stateless is the default.
            statefulSampleDescriptor.setStateful(true);

            mockContainer.deploy(statefulSampleDescriptor);

            // All EJBs are now deployed
            deployed = true;
        }
    }

    /**
     * <p>
     * Release all temporarily loaded namespaces.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    private static void releaseNamespace() throws Exception {
        for (Iterator iter = ConfigManager.getInstance().getAllNamespaces(); iter.hasNext();) {
            ConfigManager.getInstance().removeNamespace((String) iter.next());
        }
    }

    /**
     * <p>
     * Temporarily load the testing namespaces.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    private static void loadNamespace() throws Exception {
        releaseNamespace();

        ConfigManager.getInstance().add(STRESS_CONFIG_FILE);
    }

    /**
     * <p>
     * Stress test of <code>{@link ActiveGamesHandler#execute(ActionContext)}</code> method.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testActiveGamesHandlerExecuteStress1() throws Exception {
        Handler handler = new ActiveGamesHandler("games");
        // creates the mocks properly.
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        ActionContext context = new ActionContext(request, response);
        long start = System.currentTimeMillis();
        for (int i = 0; i < SMALL_ITER; i++) {
            // invoke the execute.
            assertNull("failed to execute handler", handler.execute(context));
        }
        long end = System.currentTimeMillis();
        assertTrue("result is not saved.", request.getAttribute("games") != null);
        System.out.println("Process ActiveGamesHandler#execute(ActionContext) for " + SMALL_ITER + " times takes "
            + (end - start) + " ms.");

    }

    /**
     * <p>
     * Stress test of <code>{@link ActiveGamesHandler#execute(ActionContext)}</code> method.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testActiveGamesHandlerExecuteStress2() throws Exception {
        Handler handler = new ActiveGamesHandler("games");
        // creates the mocks properly.
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        ActionContext context = new ActionContext(request, response);
        long start = System.currentTimeMillis();
        for (int i = 0; i < MEDIUM_ITER; i++) {
            // invoke the execute.
            assertNull("failed to execute handler", handler.execute(context));
        }
        long end = System.currentTimeMillis();
        assertTrue("result is not saved.", request.getAttribute("games") != null);
        System.out.println("Process ActiveGamesHandler#execute(ActionContext) for " + MEDIUM_ITER + " times takes "
            + (end - start) + " ms.");
    }

    /**
     * <p>
     * Stress test of <code>{@link ActiveGamesHandler#execute(ActionContext)}</code> method.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testActiveGamesHandlerExecute3() throws Exception {
        Handler handler = new ActiveGamesHandler("games");
        // creates the mocks properly.
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        ActionContext context = new ActionContext(request, response);
        long start = System.currentTimeMillis();
        for (int i = 0; i < LARGE_ITER; i++) {
            // invoke the execute.
            assertNull("failed to execute handler", handler.execute(context));
        }
        long end = System.currentTimeMillis();
        assertTrue("result is not saved.", request.getAttribute("games") != null);
        System.out.println("Process ActiveGamesHandler#execute(ActionContext) for " + LARGE_ITER + " times takes "
            + (end - start) + " ms.");
    }

    /**
     * <p>
     * Stress test of <code>{@link GameDetailHandler#execute(ActionContext)}</code> method.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGameDetailHandlerExecuteStress1() throws Exception {
        Handler handler = new GameDetailHandler("gameId", "game");
        // creates the mocks properly.
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setupAddParameter("gameId", "101");
        ActionContext context = new ActionContext(request, response);
        long start = System.currentTimeMillis();
        for (int i = 0; i < SMALL_ITER; i++) {
            // invoke the execute.
            assertNull("failed to execute handler", handler.execute(context));
        }
        long end = System.currentTimeMillis();
        assertTrue("result is not saved.", request.getAttribute("game") != null);
        System.out.println("Process GameDetailHandler#execute(ActionContext) for " + SMALL_ITER + " times takes "
            + (end - start) + " ms.");
    }

    /**
     * <p>
     * Stress test of <code>{@link GameDetailHandler#execute(ActionContext)}</code> method.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGameDetailHandlerExecuteStress2() throws Exception {
        Handler handler = new GameDetailHandler("gameId", "game");
        // creates the mocks properly.
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setupAddParameter("gameId", "101");
        ActionContext context = new ActionContext(request, response);
        long start = System.currentTimeMillis();
        for (int i = 0; i < MEDIUM_ITER; i++) {
            // invoke the execute.
            assertNull("failed to execute handler", handler.execute(context));
        }
        long end = System.currentTimeMillis();
        assertTrue("result is not saved.", request.getAttribute("game") != null);
        System.out.println("Process GameDetailHandler#execute(ActionContext) for " + MEDIUM_ITER + " times takes "
            + (end - start) + " ms.");
    }

    /**
     * <p>
     * Stress test of <code>{@link GameDetailHandler#execute(ActionContext)}</code> method.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGameDetailHandlerExecuteStress3() throws Exception {
        Handler handler = new GameDetailHandler("gameId", "game");
        // creates the mocks properly.
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setupAddParameter("gameId", "101");
        ActionContext context = new ActionContext(request, response);
        long start = System.currentTimeMillis();
        for (int i = 0; i < LARGE_ITER; i++) {
            // invoke the execute.
            assertNull("failed to execute handler", handler.execute(context));
        }
        long end = System.currentTimeMillis();
        assertTrue("result is not saved.", request.getAttribute("game") != null);
        System.out.println("Process GameDetailHandler#execute(ActionContext) for " + LARGE_ITER + " times takes "
            + (end - start) + " ms.");
    }

    /**
     * <p>
     * Stress test of <code>{@link LeaderBoardHandler#execute(ActionContext)}</code> method.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLeaderBoardHandlerExecuteStress1() throws Exception {
        Handler handler = new LeaderBoardHandler(createLeaderBoardMap());
        // creates the mocks properly.
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setupAddParameter("gameId", "101");
        ActionContext context = new ActionContext(request, response);
        long start = System.currentTimeMillis();
        for (int i = 0; i < SMALL_ITER; i++) {
            // invoke the execute.
            assertNull("failed to execute handler", handler.execute(context));
        }
        long end = System.currentTimeMillis();
        assertTrue("result is not saved.", request.getAttribute("profiles") != null);
        System.out.println("Process LeaderBoardHandler#execute(ActionContext) for " + SMALL_ITER + " times takes "
            + (end - start) + " ms.");
    }

    /**
     * <p>
     * Stress test of <code>{@link LeaderBoardHandler#execute(ActionContext)}</code> method.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLeaderBoardHandlerExecuteStress2() throws Exception {
        Handler handler = new LeaderBoardHandler(createLeaderBoardMap());
        // creates the mocks properly.
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setupAddParameter("gameId", "101");
        ActionContext context = new ActionContext(request, response);
        long start = System.currentTimeMillis();
        for (int i = 0; i < MEDIUM_ITER; i++) {
            // invoke the execute.
            assertNull("failed to execute handler", handler.execute(context));
        }
        long end = System.currentTimeMillis();
        assertTrue("result is not saved.", request.getAttribute("profiles") != null);
        System.out.println("Process LeaderBoardHandler#execute(ActionContext) for " + MEDIUM_ITER + " times takes "
            + (end - start) + " ms.");
    }

    /**
     * <p>
     * Stress test of <code>{@link LeaderBoardHandler#execute(ActionContext)}</code> method.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testLeaderBoardHandlerExecuteStress3() throws Exception {
        Handler handler = new LeaderBoardHandler(createLeaderBoardMap());
        // creates the mocks properly.
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setupAddParameter("gameId", "101");
        ActionContext context = new ActionContext(request, response);
        long start = System.currentTimeMillis();
        for (int i = 0; i < LARGE_ITER; i++) {
            // invoke the execute.
            assertNull("failed to execute handler", handler.execute(context));
        }
        long end = System.currentTimeMillis();
        assertTrue("result is not saved.", request.getAttribute("profiles") != null);
        System.out.println("Process LeaderBoardHandler#execute(ActionContext) for " + LARGE_ITER + " times takes "
            + (end - start) + " ms.");
    }

    /**
     * <p>
     * Create config map for LeaderBoardHandler class.
     * </p>
     * @return the map
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    private static Map createLeaderBoardMap() throws Exception {
        Map map = new HashMap();
        map.put("gameIdParamKey", "gameId");
        map.put("profilesKey", "profiles");
        map.put("maxLeaders", new Integer(5));
        UserProfileManager manager = new SimpleUserProfileManager("com.topcoder.user.profile.manager");
        map.put("profileManager", manager);

        // create testing profiles
        File file = new File(TestHelper.USER_DIR + "test_files" + File.separator + "profiles.xml");
        file.delete();
        long[] ids = new long[] {122, 58, 207, 199, 189};
        // Construct a profile
        for (int i = 0; i < ids.length; i++) {
            UserProfile profile = new UserProfile(new Long(ids[i]));
            profile.setProperty(BaseProfileType.FIRST_NAME, "User_" + i);
            manager.createUserProfile(profile);
        }

        return map;
    }

    /**
     * <p>
     * Stress test of <code>{@link SlotValidationHandler#execute(ActionContext)}</code> method.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSlotValidationHandlerExecuteStress1() throws Exception {
        Handler handler = new SlotValidationHandler("gameId", "slotId", "validation_failed");
        // creates the mocks properly.
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setupAddParameter("gameId", "3");
        request.setupAddParameter("slotId", "7");
        ActionContext context = new ActionContext(request, response);
        long start = System.currentTimeMillis();
        for (int i = 0; i < SMALL_ITER; i++) {
            // invoke the execute.
            assertNull("failed to execute handler", handler.execute(context));
        }
        long end = System.currentTimeMillis();
        System.out.println("Process SlotValidationHandler#execute(ActionContext) for " + SMALL_ITER + " times takes "
            + (end - start) + " ms.");
    }

    /**
     * <p>
     * Stress test of <code>{@link SlotValidationHandler#execute(ActionContext)}</code> method.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSlotValidationHandlerExecuteStress2() throws Exception {
        Handler handler = new SlotValidationHandler("gameId", "slotId", "validation_failed");
        // creates the mocks properly.
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setupAddParameter("gameId", "3");
        request.setupAddParameter("slotId", "7");
        ActionContext context = new ActionContext(request, response);
        long start = System.currentTimeMillis();
        for (int i = 0; i < MEDIUM_ITER; i++) {
            // invoke the execute.
            assertNull("failed to execute handler", handler.execute(context));
        }
        long end = System.currentTimeMillis();
        System.out.println("Process SlotValidationHandler#execute(ActionContext) for " + MEDIUM_ITER + " times takes "
            + (end - start) + " ms.");
    }

    /**
     * <p>
     * Stress test of <code>{@link SlotValidationHandler#execute(ActionContext)}</code> method.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSlotValidationHandlerExecuteStress3() throws Exception {
        Handler handler = new SlotValidationHandler("gameId", "slotId", "validation_failed");
        // creates the mocks properly.
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setupAddParameter("gameId", "3");
        request.setupAddParameter("slotId", "7");
        ActionContext context = new ActionContext(request, response);
        long start = System.currentTimeMillis();
        for (int i = 0; i < LARGE_ITER; i++) {
            // invoke the execute.
            assertNull("failed to execute handler", handler.execute(context));
        }
        long end = System.currentTimeMillis();
        System.out.println("Process SlotValidationHandler#execute(ActionContext) for " + LARGE_ITER + " times takes "
            + (end - start) + " ms.");
    }

    /**
     * <p>
     * Stress test of <code>{@link TestDomainHandler#execute(ActionContext)}</code> method.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestDomainHandlerExecuteStress1() throws Exception {
        Handler handler = new TestDomainHandler("domainName", "game", "not_logged_in");
        // creates the mocks properly.
        HttpServletRequest request = (HttpServletRequest) reqControl.getMock();
        HttpServletResponse response = (HttpServletResponse) resControl.getMock();
        reqControl.replay();
        MyHttpServletRequest wrapper = new MyHttpServletRequest(request);
        wrapper.setNullSesssion(false);
        wrapper.setParameter("domainName", "junit");
        ActionContext context = new ActionContext(wrapper, response);
        long start = System.currentTimeMillis();
        for (int i = 0; i < SMALL_ITER; i++) {
            // invoke the execute.
            assertNull("failed to execute handler", handler.execute(context));
        }
        long end = System.currentTimeMillis();
        assertTrue("result is not saved.", wrapper.getAttribute("game") != null);
        System.out.println("Process TestDomainHandler#execute(ActionContext) for " + SMALL_ITER + " times takes "
            + (end - start) + " ms.");
    }

    /**
     * <p>
     * Stress test of <code>{@link TestDomainHandler#execute(ActionContext)}</code> method.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestDomainHandlerExecuteStress2() throws Exception {
        Handler handler = new TestDomainHandler("domainName", "game", "not_logged_in");
        // creates the mocks properly.
        HttpServletRequest request = (HttpServletRequest) reqControl.getMock();
        HttpServletResponse response = (HttpServletResponse) resControl.getMock();
        reqControl.replay();
        MyHttpServletRequest wrapper = new MyHttpServletRequest(request);
        wrapper.setNullSesssion(false);
        wrapper.setParameter("domainName", "junit");
        ActionContext context = new ActionContext(wrapper, response);
        long start = System.currentTimeMillis();
        for (int i = 0; i < MEDIUM_ITER; i++) {
            // invoke the execute.
            assertNull("failed to execute handler", handler.execute(context));
        }
        long end = System.currentTimeMillis();
        assertTrue("result is not saved.", wrapper.getAttribute("game") != null);
        System.out.println("Process TestDomainHandler#execute(ActionContext) for " + MEDIUM_ITER + " times takes "
            + (end - start) + " ms.");
    }

    /**
     * <p>
     * Stress test of <code>{@link TestDomainHandler#execute(ActionContext)}</code> method.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestDomainHandlerExecuteStress3() throws Exception {
        Handler handler = new TestDomainHandler("domainName", "game", "not_logged_in");
        // creates the mocks properly.
        HttpServletRequest request = (HttpServletRequest) reqControl.getMock();
        HttpServletResponse response = (HttpServletResponse) resControl.getMock();
        reqControl.replay();
        MyHttpServletRequest wrapper = new MyHttpServletRequest(request);
        wrapper.setNullSesssion(false);
        wrapper.setParameter("domainName", "junit");
        ActionContext context = new ActionContext(wrapper, response);
        long start = System.currentTimeMillis();
        for (int i = 0; i < LARGE_ITER; i++) {
            // invoke the execute.
            assertNull("failed to execute handler", handler.execute(context));
        }
        long end = System.currentTimeMillis();
        assertTrue("result is not saved.", wrapper.getAttribute("game") != null);
        System.out.println("Process TestDomainHandler#execute(ActionContext) for " + LARGE_ITER + " times takes "
            + (end - start) + " ms.");
    }

    /**
     * <p>
     * Stress test of <code>{@link TestTargetObjectHandler#execute(ActionContext)}</code> method.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestTargetObjectHandlerExecuteStress1() throws Exception {
        Handler handler = new TestTargetObjectHandler(createTestTargetObjectCongfigMap());
        // creates the mocks properly.
        HttpServletRequest request = (HttpServletRequest) reqControl.getMock();
        HttpServletResponse response = (HttpServletResponse) resControl.getMock();        
        reqControl.replay();
        MyHttpServletRequest wrapper = new MyHttpServletRequest(request);
        wrapper.setNullSesssion(false);
        wrapper.setParameter("gameId", "101");
        wrapper.setParameter("seqNo", "2");
        wrapper.setParameter("text", "test");
        wrapper.setParameter("domainName", "junit");        
        ActionContext context = new ActionContext(wrapper, response);
        long start = System.currentTimeMillis();
        for (int i = 0; i < SMALL_ITER; i++) {
            // invoke the execute.
            assertEquals("failed to execute handler", handler.execute(context), "test_failed");
        }
        long end = System.currentTimeMillis();
        System.out.println("Process TestTargetObjectHandler#execute(ActionContext) for " + SMALL_ITER + " times takes "
            + (end - start) + " ms.");
    }

    /**
     * <p>
     * Stress test of <code>{@link TestTargetObjectHandler#execute(ActionContext)}</code> method.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestTargetObjectHandlerExecuteStress2() throws Exception {
        Handler handler = new TestTargetObjectHandler(createTestTargetObjectCongfigMap());
        // creates the mocks properly.
        HttpServletRequest request = (HttpServletRequest) reqControl.getMock();
        HttpServletResponse response = (HttpServletResponse) resControl.getMock();        
        reqControl.replay();
        MyHttpServletRequest wrapper = new MyHttpServletRequest(request);
        wrapper.setNullSesssion(false);
        wrapper.setParameter("gameId", "101");
        wrapper.setParameter("seqNo", "2");
        wrapper.setParameter("text", "test");
        wrapper.setParameter("domainName", "junit");        
        ActionContext context = new ActionContext(wrapper, response);
        long start = System.currentTimeMillis();
        for (int i = 0; i < MEDIUM_ITER; i++) {
            // invoke the execute.
            assertEquals("failed to execute handler", handler.execute(context), "test_failed");
        }
        long end = System.currentTimeMillis();
        System.out.println("Process TestTargetObjectHandler#execute(ActionContext) for " + MEDIUM_ITER
            + " times takes " + (end - start) + " ms.");
    }

    /**
     * <p>
     * Stress test of <code>{@link TestTargetObjectHandler#execute(ActionContext)}</code> method.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestTargetObjectHandlerExecuteStress3() throws Exception {
        Handler handler = new TestTargetObjectHandler(createTestTargetObjectCongfigMap());
        // creates the mocks properly.
        HttpServletRequest request = (HttpServletRequest) reqControl.getMock();
        HttpServletResponse response = (HttpServletResponse) resControl.getMock();        
        reqControl.replay();
        MyHttpServletRequest wrapper = new MyHttpServletRequest(request);
        wrapper.setNullSesssion(false);
        wrapper.setParameter("gameId", "101");
        wrapper.setParameter("seqNo", "2");
        wrapper.setParameter("text", "test");
        wrapper.setParameter("domainName", "junit");        
        ActionContext context = new ActionContext(wrapper, response);
        long start = System.currentTimeMillis();
        for (int i = 0; i < LARGE_ITER; i++) {
            // invoke the execute.
            assertEquals("failed to execute handler", handler.execute(context), "test_failed");
        }
        long end = System.currentTimeMillis();
        System.out.println("Process TestTargetObjectHandler#execute(ActionContext) for " + LARGE_ITER + " times takes "
            + (end - start) + " ms.");
    }

    /**
     * <p>
     * create configuration map for <code>{@link com.orpheus.game.TestTargetObjectHandler}</code> class.
     * </p>
     * @return the testing map.
     */
    private static Map createTestTargetObjectCongfigMap() {
        Map map = new HashMap();
        map.put("gameIdParamKey", "gameId");
        map.put("domainNameParamKey", "domainName");
        map.put("notLoggedInResultCode", "not_logged_in");
        map.put("sequenceNumberParamKey", "seqNo");
        map.put("testFailedResultCode", "test_failed");
        map.put("textParamKey", "text");

        return map;
    }

    /**
     * <p>
     * Stress test of <code>{@link UnlockedDomainsHandler#execute(ActionContext)}</code> method.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUnlockedDomainsHandlerExecuteStress1() throws Exception {
        Handler handler = new UnlockedDomainsHandler("gameId", "domain");
        // creates the mocks properly.
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setupAddParameter("gameId", "101");
        ActionContext context = new ActionContext(request, response);
        long start = System.currentTimeMillis();
        for (int i = 0; i < SMALL_ITER; i++) {
            // invoke the execute.
            assertNull("failed to execute handler", handler.execute(context));
        }
        long end = System.currentTimeMillis();
        assertTrue("result is not saved.", request.getAttribute("domain") != null);
        System.out.println("Process UnlockedDomainsHandler#execute(ActionContext) for " + SMALL_ITER + " times takes "
            + (end - start) + " ms.");

    }

    /**
     * <p>
     * Stress test of <code>{@link UnlockedDomainsHandler#execute(ActionContext)}</code> method.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUnlockedDomainsHandlerExecuteStress2() throws Exception {
        Handler handler = new UnlockedDomainsHandler("gameId", "domain");
        // creates the mocks properly.
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setupAddParameter("gameId", "101");
        ActionContext context = new ActionContext(request, response);
        long start = System.currentTimeMillis();
        for (int i = 0; i < MEDIUM_ITER; i++) {
            // invoke the execute.
            assertNull("failed to execute handler", handler.execute(context));
        }
        long end = System.currentTimeMillis();
        assertTrue("result is not saved.", request.getAttribute("domain") != null);
        System.out.println("Process UnlockedDomainsHandler#execute(ActionContext) for " + MEDIUM_ITER + " times takes "
            + (end - start) + " ms.");
    }

    /**
     * <p>
     * Stress test of <code>{@link UnlockedDomainsHandler#execute(ActionContext)}</code> method.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUnlockedDomainsHandlerExecuteStress3() throws Exception {
        Handler handler = new UnlockedDomainsHandler("gameId", "domain");
        // creates the mocks properly.
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setupAddParameter("gameId", "101");
        ActionContext context = new ActionContext(request, response);
        long start = System.currentTimeMillis();
        for (int i = 0; i < LARGE_ITER; i++) {
            // invoke the execute.
            assertNull("failed to execute handler", handler.execute(context));
        }
        long end = System.currentTimeMillis();
        assertTrue("result is not saved.", request.getAttribute("domain") != null);
        System.out.println("Process UnlockedDomainsHandler#execute(ActionContext) for " + LARGE_ITER + " times takes "
            + (end - start) + " ms.");
    }

    /**
     * <p>
     * Stress test of <code>{@link UpcomingDomainsHandler#execute(ActionContext)}</code> method.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpcomingDomainsHandlerExecuteStress1() throws Exception {
        Handler handler = new UpcomingDomainsHandler("gameId", "domain");
        // creates the mocks properly.
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setupAddParameter("gameId", "101");
        ActionContext context = new ActionContext(request, response);
        long start = System.currentTimeMillis();
        for (int i = 0; i < SMALL_ITER; i++) {
            // invoke the execute.
            assertNull("failed to execute handler", handler.execute(context));
        }
        long end = System.currentTimeMillis();
        assertTrue("result is not saved.", request.getAttribute("domain") != null);
        System.out.println("Process UnlockedDomainsHandler#execute(ActionContext) for " + SMALL_ITER + " times takes "
            + (end - start) + " ms.");
    }

    /**
     * <p>
     * Stress test of <code>{@link UpcomingDomainsHandler#execute(ActionContext)}</code> method.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpcomingDomainsHandlerExecuteStress2() throws Exception {
        Handler handler = new UpcomingDomainsHandler("gameId", "domain");
        // creates the mocks properly.
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setupAddParameter("gameId", "101");
        ActionContext context = new ActionContext(request, response);
        long start = System.currentTimeMillis();
        for (int i = 0; i < MEDIUM_ITER; i++) {
            // invoke the execute.
            assertNull("failed to execute handler", handler.execute(context));
        }
        long end = System.currentTimeMillis();
        assertTrue("result is not saved.", request.getAttribute("domain") != null);
        System.out.println("Process UnlockedDomainsHandler#execute(ActionContext) for " + MEDIUM_ITER + " times takes "
            + (end - start) + " ms.");
    }

    /**
     * <p>
     * Stress test of <code>{@link UpcomingDomainsHandler#execute(ActionContext)}</code> method.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpcomingDomainsHandlerExecuteStress3() throws Exception {
        Handler handler = new UpcomingDomainsHandler("gameId", "domain");
        // creates the mocks properly.
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setupAddParameter("gameId", "101");
        ActionContext context = new ActionContext(request, response);
        long start = System.currentTimeMillis();
        for (int i = 0; i < LARGE_ITER; i++) {
            // invoke the execute.
            assertNull("failed to execute handler", handler.execute(context));
        }
        long end = System.currentTimeMillis();
        assertTrue("result is not saved.", request.getAttribute("domain") != null);
        System.out.println("Process UnlockedDomainsHandler#execute(ActionContext) for " + LARGE_ITER + " times takes "
            + (end - start) + " ms.");
    }

    /**
     * <p>
     * Stress test of <code>{@link UserGamesHandler#execute(ActionContext)}</code> method.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUserGamesHandlerExecuteStress1() throws Exception {
        Handler handler = new UserGamesHandler("games", "not_logged_in");
        // creates the mocks properly.
        HttpServletRequest request = (HttpServletRequest) reqControl.getMock();
        HttpServletResponse response = (HttpServletResponse) resControl.getMock();
        MyHttpServletRequest wrapper = new MyHttpServletRequest(request);
        wrapper.setNullSesssion(false);
        reqControl.replay();
        ActionContext context = new ActionContext(wrapper, response);
        long start = System.currentTimeMillis();
        for (int i = 0; i < SMALL_ITER; i++) {
            // invoke the execute.
            assertNull("failed to execute handler", handler.execute(context));
        }
        long end = System.currentTimeMillis();
        assertTrue("result is not saved.", wrapper.getAttribute("games") != null);
        System.out.println("Process UserGamesHandler#execute(ActionContext) for " + SMALL_ITER + " times takes "
            + (end - start) + " ms.");
    }

    /**
     * <p>
     * Stress test of <code>{@link UserGamesHandler#execute(ActionContext)}</code> method.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUserGamesHandlerExecuteStress2() throws Exception {
        Handler handler = new UserGamesHandler("games", "not_logged_in");
        // creates the mocks properly.
        HttpServletRequest request = (HttpServletRequest) reqControl.getMock();
        HttpServletResponse response = (HttpServletResponse) resControl.getMock();
        MyHttpServletRequest wrapper = new MyHttpServletRequest(request);
        wrapper.setNullSesssion(false);
        reqControl.replay();
        ActionContext context = new ActionContext(wrapper, response);
        long start = System.currentTimeMillis();
        for (int i = 0; i < MEDIUM_ITER; i++) {
            // invoke the execute.
            assertNull("failed to execute handler", handler.execute(context));
        }
        long end = System.currentTimeMillis();
        assertTrue("result is not saved.", wrapper.getAttribute("games") != null);
        System.out.println("Process UserGamesHandler#execute(ActionContext) for " + MEDIUM_ITER + " times takes "
            + (end - start) + " ms.");
    }

    /**
     * <p>
     * Stress test of <code>{@link UserGamesHandler#execute(ActionContext)}</code> method.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUserGamesHandlerExecuteStress3() throws Exception {
        Handler handler = new UserGamesHandler("games", "not_logged_in");
        // creates the mocks properly.
        HttpServletRequest request = (HttpServletRequest) reqControl.getMock();
        HttpServletResponse response = (HttpServletResponse) resControl.getMock();
        MyHttpServletRequest wrapper = new MyHttpServletRequest(request);
        wrapper.setNullSesssion(false);
        reqControl.replay();
        ActionContext context = new ActionContext(wrapper, response);
        long start = System.currentTimeMillis();
        for (int i = 0; i < LARGE_ITER; i++) {
            // invoke the execute.
            assertNull("failed to execute handler", handler.execute(context));
        }
        long end = System.currentTimeMillis();
        assertTrue("result is not saved.", wrapper.getAttribute("games") != null);
        System.out.println("Process UserGamesHandler#execute(ActionContext) for " + LARGE_ITER + " times takes "
            + (end - start) + " ms.");
    }
}
