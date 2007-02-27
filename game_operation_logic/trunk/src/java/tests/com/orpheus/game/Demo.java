/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.orpheus.game.result.MessagePollResult;

import com.topcoder.message.messenger.Messenger;

import com.topcoder.user.profile.manager.MockUserProfileManager;
import com.topcoder.user.profile.manager.ProfileTypeFactory;

import com.topcoder.util.puzzle.MockPuzzleTypeSource;
import com.topcoder.util.puzzle.MockSolutionTester;
import com.topcoder.util.rssgenerator.MockDataStore;

import com.topcoder.web.frontcontroller.ActionContext;

import junit.framework.TestCase;

import servlet.MockHttpRequest;
import servlet.MockHttpResponse;
import servlet.MockHttpSession;
import servlet.MockServletContext;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * This is a demo on the usage of this component. This component is mostly invoked by FrontController as part of
 * the web application, instead of using directly. So a sample configuration of how this component should be
 * configured is also provided in test_files/front_conf.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends TestCase {
    /** Default ActionContext used in the tests. */
    private ActionContext context;

    /** Default HttpServletResponse used in the tests. */
    private HttpServletResponse response;

    /** Default HttpSession used in the tests. */
    private HttpSession session;

    /** Default HttpServletRequest used in the tests. */
    private MockHttpRequest request;

    /** Default ServletContext used in the tests. */
    private ServletContext servletContext;

    /**
     * Demo on how KeySubmissionHandler is initiated and invoked.
     *
     * @throws Exception to junit
     */
    public void testKeySubmissionHandler() throws Exception {
        servletContext = new MockServletContext();
        servletContext.setAttribute(GameOperationLogicUtility.getInstance().getDataStoreKey(), new MockDataStore());

        session = new MockHttpSession(servletContext);

        MockHttpRequest mockRequest = new MockHttpRequest(session);
        request = mockRequest;

        response = new MockHttpResponse();
        context = new ActionContext(request, response);

        mockRequest.setParameter("gameId", "1");
        mockRequest.setParameter("submissions", "2");
        mockRequest.setParameter("submissions", new String[] { "1" });

        KeySubmissionHandler handler = new KeySubmissionHandler("gameId", "submissions", "inactive",
                "failureCountExceededResult", "failureCountNotMetResult", 1);
        handler.execute(context);
    }

    /**
     * Demo on how MessageHandler is initiated and invoked.
     *
     * @throws Exception to junit
     */
    public void testMessageHandlerTest() throws Exception {
        servletContext = new MockServletContext();
        servletContext.setAttribute(GameOperationLogicUtility.getInstance().getDataStoreKey(), new MockDataStore());

        session = new MockHttpSession(servletContext);
        request = new MockHttpRequest(session);

        response = new MockHttpResponse();
        context = new ActionContext(request, response);

        request.setAttribute("request_property_name", "request_property_value");

        Messenger messenger = Messenger.createInstance();
        messenger.registerPlugin("some_name", "com.topcoder.message.messenger.MockMessengerPlugin");

        Map map = new HashMap();
        map.put(new AttributeScope("request_property_name", "request"), "property1");

        MessageHandler handler = new MessageHandler("some_name", map);

        handler.execute(context);
    }

    /**
     * Demo on how MessagePollResult is initiated and invoked.
     *
     * @throws Exception to junit
     */
    public void testMessagePollResult() throws Exception {
        MessagePollResult messagePollResult = new MessagePollResult("date", new String[] { "gameA", "gameB", "gameC" });

        servletContext = new MockServletContext();
        servletContext.setAttribute(GameOperationLogicUtility.getInstance().getDataStoreKey(), new MockDataStore());

        session = new MockHttpSession(servletContext);
        request = new MockHttpRequest(session);

        response = new MockHttpResponse();
        context = new ActionContext(request, response);
        request.setParameter("date", "2006-10-10T12:33:32,000-05:00");

        messagePollResult.execute(context);
    }

    /**
     * Demo on how PluginDownloadHandler is initiated and invoked.
     *
     * @throws Exception to junit
     */
    public void testPluginDownloadHandler() throws Exception {
        request = new MockHttpRequest(null);
        request.setParameter("plugin", "some_plugin");

        PluginDownloadHandler handler = new PluginDownloadHandler("plugin");
        context = new ActionContext(request, new MockHttpResponse());

        handler.execute(context);
    }

    /**
     * Demo on how PuzzleRenderingHandler is initiated and invoked.
     *
     * @throws Exception to junit
     */
    public void testPuzzleRenderingHandler() throws Exception {
        Map map = new HashMap();
        map.put(new AttributeScope("request_property_name", "request"), "property1");

        PuzzleRenderingHandler handler = new PuzzleRenderingHandler("puzzle_id", "media_type", "base_name",
                "puzzle_string");

        servletContext = new MockServletContext();

        session = new MockHttpSession(servletContext);
        request = new MockHttpRequest(session);

        response = new MockHttpResponse();
        context = new ActionContext(request, response);

        request.setAttribute("puzzle_id", new Long(1));
        request.setAttribute("media_type", "type");
        servletContext.setAttribute("PuzzleTypeSource", new MockPuzzleTypeSource());

        handler.execute(context);
    }

    /**
     * Demo on how PuzzleSolutionHandler is initiated and invoked.
     *
     * @throws Exception to junit
     */
    public void testPuzzleSolutionHandler() throws Exception {
        PuzzleSolutionHandler handler = new PuzzleSolutionHandler("puzzle_id", "Slot_id", "base_name",
                "incorrect_solution_result", "slotCompletion");

        servletContext = new MockServletContext();

        session = new MockHttpSession(servletContext);
        request = new MockHttpRequest(session);

        response = new MockHttpResponse();
        context = new ActionContext(request, response);

        request.setParameter("puzzle_id", "1");
        request.setParameter("Slot_id", "1");

        session.setAttribute("base_name123", new MockSolutionTester());
        servletContext.setAttribute(GameOperationLogicUtility.getInstance().getGameManagerKey(),
            new GameDataManagerImpl(new MockPuzzleTypeSource()));

        handler.execute(context);
    }

    /**
     * Demo on how RegisterGameHandler is initiated and invoked.
     *
     * @throws Exception to junit
     */
    public void testRegisterGameHandler() throws Exception {
        Map map = new HashMap();
        map.put(new AttributeScope("request_property_name", "request"), "property1");

        RegisterGameHandler handler = new RegisterGameHandler("game_id");

        servletContext = new MockServletContext();

        session = new MockHttpSession(servletContext);
        request = new MockHttpRequest(session);

        response = new MockHttpResponse();
        context = new ActionContext(request, response);

        request.setParameter("game_id", "1");

        handler.execute(context);
    }

    /**
     * Demo on how WinnerDataHandler is initiated and invoked.
     *
     * @throws Exception to junit
     */
    public void testWinnerDataHandler() throws Exception {
        String[] profileTypeNames = new String[] { "typeA", "typeB" };
        Map profilesMap = new HashMap();
        profilesMap.put("first_name", "firstName");
        profilesMap.put("email_address", "email");

        WinnerDataHandler handler = new WinnerDataHandler(new MockUserProfileManager(), profileTypeNames, profilesMap,
                ProfileTypeFactory.getInstance("com.topcoder.user.profile.manager"));

        servletContext = new MockServletContext();

        session = new MockHttpSession(servletContext);
        request = new MockHttpRequest(session);

        response = new MockHttpResponse();
        context = new ActionContext(request, response);

        request.setParameter("firstName", "tom");
        request.setParameter("email", "tom@email.com");

        handler.execute(context);
    }

    /**
     * Sets up test environment.
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        super.setUp();
        tearDown();
        TestHelper.loadConfig();
        JNDIHelper.initJNDI();
    }

    /**
     * Clears test environment.
     *
     * @throws Exception to junit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        TestHelper.unloadConfig();
    }
}
