/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game.failuretests;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.easymock.MockControl;
import org.w3c.dom.Element;

import com.orpheus.game.LeaderBoardHandler;
import com.topcoder.user.profile.manager.SimpleUserProfileManager;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

import junit.framework.TestCase;

/**
 * Test case for <code>LeaderBoardHandler</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class LeaderBoardHandlerTest extends TestCase {

    /**
     * Represents the handler to test.
     */
    private LeaderBoardHandler handler;

    /**
     * Represents the attributes to create the handler.
     */
    private Map attributes;

    /**
     * Set up the environment.
     */
    protected void setUp() throws Exception {
        super.setUp();
        FailureTestHelper.loadConfig();
        attributes = new HashMap();
        attributes.put("gameIdParamKey", "id1");
        attributes.put("profilesKey", "profilesKey");
        attributes.put("maxLeaders", new Integer(1));
        attributes.put("profileManager", new SimpleUserProfileManager("com.topcoder.user.profile.manager"));
        handler = new LeaderBoardHandler(attributes);
    }

    /**
     * Clean up the environment.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        FailureTestHelper.unloadConfig();
    }

    /**
     * Test method for LeaderBoardHandler(java.util.Map).
     * In this case, the parameter is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testLeaderBoardHandler_Map_NullMap() {
        try {
            new LeaderBoardHandler((Map)null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for LeaderBoardHandler(java.util.Map).
     * In this case, the game_id_param_key is missing.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testLeaderBoardHandler_Map_MissingGameIdParamKey() {
        try {
            attributes.remove("gameIdParamKey");
            new LeaderBoardHandler(attributes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for LeaderBoardHandler(java.util.Map).
     * In this case, the game_id_param_key is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testLeaderBoardHandler_Map_NullGameIdParamKey() {
        try {
            attributes.put("gameIdParamKey", null);
            new LeaderBoardHandler(attributes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for LeaderBoardHandler(java.util.Map).
     * In this case, the game_id_param_key is empty.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testLeaderBoardHandler_Map_EmptyGameIdParamKey() {
        try {
            attributes.put("gameIdParamKey", " ");
            new LeaderBoardHandler(attributes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for LeaderBoardHandler(java.util.Map).
     * In this case, the profilesKey is missing.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testLeaderBoardHandler_Map_MissingProfilesKey() {
        try {
            attributes.remove("profilesKey");
            new LeaderBoardHandler(attributes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for LeaderBoardHandler(java.util.Map).
     * In this case, the profilesKey is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testLeaderBoardHandler_Map_NullProfilesKey() {
        try {
            attributes.put("profilesKey", null);
            new LeaderBoardHandler(attributes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for LeaderBoardHandler(java.util.Map).
     * In this case, the profilesKey is empty.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testLeaderBoardHandler_Map_EmptyProfilesKey() {
        try {
            attributes.put("profilesKey", " ");
            new LeaderBoardHandler(attributes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for LeaderBoardHandler(java.util.Map).
     * In this case, the maxLeaders is missing.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testLeaderBoardHandler_Map_MissingMaxLeaders() {
        try {
            attributes.remove("maxLeaders");
            new LeaderBoardHandler(attributes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for LeaderBoardHandler(java.util.Map).
     * In this case, the maxLeaders is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testLeaderBoardHandler_Map_NullMaxLeaders() {
        try {
            attributes.put("maxLeaders", null);
            new LeaderBoardHandler(attributes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for LeaderBoardHandler(java.util.Map).
     * In this case, the profilesKey is not an integer.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testLeaderBoardHandler_Map_MaxLeadersNotInteger() {
        try {
            attributes.put("maxLeaders", "haha");
            new LeaderBoardHandler(attributes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for LeaderBoardHandler(java.util.Map).
     * In this case, the maxLeaders is a negative integer.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testLeaderBoardHandler_Map_MaxLeadersNegativeInteger() {
        try {
            attributes.put("maxLeaders", new Integer(-1));
            new LeaderBoardHandler(attributes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for LeaderBoardHandler(java.util.Map).
     * In this case, the maxLeaders is a zero integer.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testLeaderBoardHandler_Map_MaxLeadersZeroInteger() {
        try {
            attributes.put("maxLeaders", new Integer(0));
            new LeaderBoardHandler(attributes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for LeaderBoardHandler(java.util.Map).
     * In this case, the profileManager is missing.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testLeaderBoardHandler_Map_MissingProfileManager() {
        try {
            attributes.remove("profileManager");
            new LeaderBoardHandler(attributes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for LeaderBoardHandler(java.util.Map).
     * In this case, the profileManager is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testLeaderBoardHandler_Map_NullProfilesManager() {
        try {
            attributes.put("profileManager", null);
            new LeaderBoardHandler(attributes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for LeaderBoardHandler(java.util.Map).
     * In this case, the profileManager is a string.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testLeaderBoardHandler_Map_InvalidProfileManager() {
        try {
            attributes.put("profileManager", "haha");
            new LeaderBoardHandler(attributes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for LeaderBoardHandler(org.w3c.dom.Element).
     * In this case, the element is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testLeaderBoardHandler_Element_NullElement() {
        try {
            new LeaderBoardHandler((Element) null);
            fail("IllegalARgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for LeaderBoardHandler(org.w3c.dom.Element).
     * In this case, the game id param key is missing.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testLeaderBoardHandler_Element_MissingGameIdParamKey() throws Exception, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<game_id_param_key_1>id</game_id_param_key_1>"
                + "<profiles_key>key</profiles_key>"
                + "<max_leaders>1</max_leaders>"
                + "<object_factory_namespace>com.orpheus.game.objectfactory</object_factory_namespace>"
                + "<user_profile_manager_token>simple_user_profile_manager</user_profile_manager_token>"
                + "</handler>";
            new LeaderBoardHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalARgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for LeaderBoardHandler(org.w3c.dom.Element).
     * In this case, the game id param key is empty.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testLeaderBoardHandler_Element_InvalidGameIdParamKey() throws Exception, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<game_id_param_key> </game_id_param_key>"
                + "<profiles_key>key</profiles_key>"
                + "<max_leaders>1</max_leaders>"
                + "<object_factory_namespace>com.orpheus.game.objectfactory</object_factory_namespace>"
                + "<user_profile_manager_token>simple_user_profile_manager</user_profile_manager_token>"
                + "</handler>";
            new LeaderBoardHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalARgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for LeaderBoardHandler(org.w3c.dom.Element).
     * In this case, the profiles key is empty.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testLeaderBoardHandler_Element_MissingProfilesKey() throws Exception, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<game_id_param_key>id</game_id_param_key>"
                + "<profiles_key_1>key</profiles_key_1>"
                + "<max_leaders>1</max_leaders>"
                + "<object_factory_namespace>com.orpheus.game.objectfactory</object_factory_namespace>"
                + "<user_profile_manager_token>simple_user_profile_manager</user_profile_manager_token>"
                + "</handler>";
            new LeaderBoardHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalARgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for LeaderBoardHandler(org.w3c.dom.Element).
     * In this case, the profiles key is empty.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testLeaderBoardHandler_Element_InvalidProfilesKey() throws Exception, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<game_id_param_key>id</game_id_param_key>"
                + "<profiles_key> </profiles_key>"
                + "<max_leaders>1</max_leaders>"
                + "<object_factory_namespace>com.orpheus.game.objectfactory</object_factory_namespace>"
                + "<user_profile_manager_token>simple_user_profile_manager</user_profile_manager_token>"
                + "</handler>";
            new LeaderBoardHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalARgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for LeaderBoardHandler(org.w3c.dom.Element).
     * In this case, the max leaders is missing.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testLeaderBoardHandler_Element_MissingMaxLeaders() throws Exception, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<game_id_param_key>id</game_id_param_key>"
                + "<profiles_key>key</profiles_key>"
                + "<max_leaders_1>1</max_leaders_1>"
                + "<object_factory_namespace>com.orpheus.game.objectfactory</object_factory_namespace>"
                + "<user_profile_manager_token>simple_user_profile_manager</user_profile_manager_token>"
                + "</handler>";
            new LeaderBoardHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalARgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for LeaderBoardHandler(org.w3c.dom.Element).
     * In this case, the max leaders is not integer.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testLeaderBoardHandler_Element_NotIntegerMaxLeaders() throws Exception, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<game_id_param_key>id</game_id_param_key>"
                + "<profiles_key>key</profiles_key>"
                + "<max_leaders>test</max_leaders>"
                + "<object_factory_namespace>com.orpheus.game.objectfactory</object_factory_namespace>"
                + "<user_profile_manager_token>simple_user_profile_manager</user_profile_manager_token>"
                + "</handler>";
            new LeaderBoardHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalARgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for LeaderBoardHandler(org.w3c.dom.Element).
     * In this case, the max leaders is zero.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testLeaderBoardHandler_Element_ZeroIntegerMaxLeaders() throws Exception, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<game_id_param_key>id</game_id_param_key>"
                + "<profiles_key>key</profiles_key>"
                + "<max_leaders>0</max_leaders>"
                + "<object_factory_namespace>com.orpheus.game.objectfactory</object_factory_namespace>"
                + "<user_profile_manager_token>simple_user_profile_manager</user_profile_manager_token>"
                + "</handler>";
            new LeaderBoardHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalARgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for LeaderBoardHandler(org.w3c.dom.Element).
     * In this case, the max leaders is negative.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testLeaderBoardHandler_Element_NegativeIntegerMaxLeaders() throws Exception, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<game_id_param_key>id</game_id_param_key>"
                + "<profiles_key>key</profiles_key>"
                + "<max_leaders>-1</max_leaders>"
                + "<object_factory_namespace>com.orpheus.game.objectfactory</object_factory_namespace>"
                + "<user_profile_manager_token>simple_user_profile_manager</user_profile_manager_token>"
                + "</handler>";
            new LeaderBoardHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalARgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for LeaderBoardHandler(org.w3c.dom.Element).
     * In this case, the object factory namespace is missing.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testLeaderBoardHandler_Element_MissingObjectFactoryNS() throws Exception, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<game_id_param_key>id</game_id_param_key>"
                + "<profiles_key>key</profiles_key>"
                + "<max_leaders>1</max_leaders>"
                + "<object_factory_namespace_1>com.orpheus.game.objectfactory</object_factory_namespace_1>"
                + "<user_profile_manager_token>simple_user_profile_manager</user_profile_manager_token>"
                + "</handler>";
            new LeaderBoardHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalARgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for LeaderBoardHandler(org.w3c.dom.Element).
     * In this case, the object factory namespace is empty.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testLeaderBoardHandler_Element_EmptyObjectFactoryNS() throws Exception, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<game_id_param_key>id</game_id_param_key>"
                + "<profiles_key>key</profiles_key>"
                + "<max_leaders>1</max_leaders>"
                + "<object_factory_namespace> </object_factory_namespace>"
                + "<user_profile_manager_token>simple_user_profile_manager</user_profile_manager_token>"
                + "</handler>";
            new LeaderBoardHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalARgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for LeaderBoardHandler(org.w3c.dom.Element).
     * In this case, the object factory namespace is invalid.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testLeaderBoardHandler_Element_InvalidObjectFactoryNS() throws Exception, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<game_id_param_key>id</game_id_param_key>"
                + "<profiles_key>key</profiles_key>"
                + "<max_leaders>1</max_leaders>"
                + "<object_factory_namespace>Invalid</object_factory_namespace>"
                + "<user_profile_manager_token>simple_user_profile_manager</user_profile_manager_token>"
                + "</handler>";
            new LeaderBoardHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalARgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for LeaderBoardHandler(org.w3c.dom.Element).
     * In this case, the user profile token is missing.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testLeaderBoardHandler_Element_MissingUserProfileToken() throws Exception, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<game_id_param_key>id</game_id_param_key>"
                + "<profiles_key>key</profiles_key>"
                + "<max_leaders>1</max_leaders>"
                + "<object_factory_namespace>com.orpheus.game.objectfactory</object_factory_namespace>"
                + "<user_profile_manager_token_1>simple_user_profile_manager</user_profile_manager_token_1>"
                + "</handler>";
            new LeaderBoardHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalARgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for LeaderBoardHandler(org.w3c.dom.Element).
     * In this case, the user profile token is invalid.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testLeaderBoardHandler_Element_InvalidUserProfileToken() throws Exception, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<game_id_param_key>id</game_id_param_key>"
                + "<profiles_key>key</profiles_key>"
                + "<max_leaders>1</max_leaders>"
                + "<object_factory_namespace>com.orpheus.game.objectfactory</object_factory_namespace>"
                + "<user_profile_manager_token> </user_profile_manager_token>"
                + "</handler>";
            new LeaderBoardHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalARgumentException expected.");
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
            ActionContext ac = new ActionContext(
                    (HttpServletRequest) reqControl.getMock(),
                    (HttpServletResponse) resControl.getMock());
            handler.execute(ac);
            fail("HandlerExecutionException expected.");
        } catch (HandlerExecutionException e) {
            // should land here
        }
    }

}
