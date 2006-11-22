/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests;

import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easymock.MockControl;
import com.orpheus.game.LeaderBoardHandler;
import com.orpheus.game.accuracytests.mock.MyHttpServletRequest;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.SimpleUserProfileManager;
import com.topcoder.user.profile.manager.UserProfileManager;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy test for LeaderBoardHandler class.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class LeaderBoardHandlerAccuracyTest extends TestCase {
    /**
     * The game key.
     */
    private static String ID_KEY = "id";

    /**
     * The profiles key.
     */
    private static String PROFILE_KEY = "profiles";

    /**
     * The max leaders key.
     */
    private static Integer MAX_KEY = new Integer(2);

    /**
     * Represents the request.
     */
    private MockControl requestControl; 
    
    /**
     * Represents the response.
     */
    private MockControl responseControl;
    
    /**
     * Set up for each test.
     * 
     * @throws Exception to JUnit
     */
    public void setUp() throws Exception  {
        super.setUp();
        AccuracyHelper.loadConfig();
        AccuracyHelper.deployEJB();
        requestControl = MockControl.createNiceControl(HttpServletRequest.class);
        responseControl = MockControl.createControl(HttpServletResponse.class);
    }
    
    /**
     * Tear down for each test.
     * 
     * @throws Exception to JUnit
     */
    public void tearDown() throws Exception  {
        AccuracyHelper.removeAllNamespace();
        super.tearDown();
    }
    
    /**
     * Test ctor LeaderBoardHandler(Map attributes),
     * an instance with the given attributes should be created.
     * 
     * @throws Exception to JUnit
     */
    public void testCtor1() throws Exception  {
        Map map = new Hashtable();
        map.put("gameIdParamKey", ID_KEY);
        map.put("profilesKey", PROFILE_KEY);
        map.put("maxLeaders", MAX_KEY);
        map.put("profileManager", new SimpleUserProfileManager("com.topcoder.user.profile.manager"));
        
        LeaderBoardHandler handler = new LeaderBoardHandler(map);
        assertTrue("The instance should be extened from Handler", handler instanceof Handler);
        assertEquals("The value not set correctly", ID_KEY, 
                AccuracyHelper.getFieldValue(LeaderBoardHandler.class, "gameIdParamKey", handler));
        assertEquals("The value not set correctly", PROFILE_KEY, 
                AccuracyHelper.getFieldValue(LeaderBoardHandler.class, "profilesKey", handler));
        assertEquals("The value not set correctly", MAX_KEY, 
                AccuracyHelper.getFieldValue(LeaderBoardHandler.class, "maxLeaders", handler));
        assertEquals("The value not set correctly", map.get("profileManager"),
                AccuracyHelper.getFieldValue(LeaderBoardHandler.class, "profileManager", handler));
    }
    
    /**
     * Test ctor LeaderBoardHandler(Element element),
     * an instance with the given element should be created.
     * 
     * @throws Exception to JUnit
     */
    public void testCtor2() throws Exception  {        
        LeaderBoardHandler handler = new LeaderBoardHandler(
                AccuracyHelper.getDomElement("LeaderBoardHandler.xml"));
        assertTrue("The instance should be extened from Handler", handler instanceof Handler);
        assertEquals("The value not set correctly", ID_KEY, 
                AccuracyHelper.getFieldValue(LeaderBoardHandler.class, "gameIdParamKey", handler));
        assertEquals("The value not set correctly", PROFILE_KEY, 
                AccuracyHelper.getFieldValue(LeaderBoardHandler.class, "profilesKey", handler));
        assertEquals("The value not set correctly", MAX_KEY, 
                AccuracyHelper.getFieldValue(LeaderBoardHandler.class, "maxLeaders", handler));
        assertNotNull("The value not set correctly", 
                AccuracyHelper.getFieldValue(LeaderBoardHandler.class, "profileManager", handler));
    }

    /**
     * Test execute(ActionContext context), the leader user should be retrieved.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testExecute_Empty() throws Exception {
        HttpServletRequest request = (HttpServletRequest) requestControl.getMock();
        HttpServletResponse response = (HttpServletResponse) responseControl.getMock();
        MyHttpServletRequest wrapper = new MyHttpServletRequest(request);
        wrapper.setParameter(ID_KEY, "1");
        ActionContext context = new ActionContext(wrapper, response);

        UserProfileManager manager = new SimpleUserProfileManager("com.topcoder.user.profile.manager");
        Map map = new Hashtable();
        map.put("gameIdParamKey", ID_KEY);
        map.put("profilesKey", PROFILE_KEY);
        map.put("maxLeaders", MAX_KEY);
        map.put("profileManager", manager);
        
        LeaderBoardHandler handler = new LeaderBoardHandler(map);
        assertNull("failed to execute handler", handler.execute(context));
        assertEquals("", 0, ((UserProfile[]) wrapper.getAttribute(PROFILE_KEY)).length);
    }

    /**
     * Test execute(ActionContext context), the leader user should be retrieved.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testExecute() throws Exception {
        HttpServletRequest request = (HttpServletRequest) requestControl.getMock();
        HttpServletResponse response = (HttpServletResponse) responseControl.getMock();
        MyHttpServletRequest wrapper = new MyHttpServletRequest(request);
        wrapper.setParameter(ID_KEY, "2");
        ActionContext context = new ActionContext(wrapper, response);

        UserProfileManager manager = new SimpleUserProfileManager("com.topcoder.user.profile.manager");
        Map map = new Hashtable();
        map.put("gameIdParamKey", ID_KEY);
        map.put("profilesKey", PROFILE_KEY);
        map.put("maxLeaders", MAX_KEY);
        map.put("profileManager", manager);
        
        LeaderBoardHandler handler = new LeaderBoardHandler(map);
        assertNull("failed to execute handler", handler.execute(context));
        UserProfile[] profiles = ((UserProfile[]) wrapper.getAttribute(PROFILE_KEY));
        assertEquals("failed to execute handler", "2", profiles[0].getIdentifier().toString());
        assertEquals("failed to execute handler", "1", profiles[1].getIdentifier().toString());
    }
}
