/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.accuracytests;

import com.cronos.im.ajax.IMAjaxSupportUtility;

import junit.framework.TestCase;


/**
 * The test of IMAjaxSupportUtility.
 *
 * @author brain_cn
 * @version 1.0
 */
public class IMAjaxSupportUtilityAccuracyTests extends TestCase {
    /**
     * <p>
     * Setup the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        Utility.loadNamespaces();
    }

    /**
     * <p>
     * Tear down the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        Utility.releaseNamespaces();
    }

    /**
     * Test method for 'getClientRoleName()'
     *
     * @throws Exception to JUnit
     */
    public void testGetClientRoleName() throws Exception {
        String expect = "client";
        assertEquals("incorrect ClientRoleName", expect, IMAjaxSupportUtility.getClientRoleName());
    }

    /**
     * Test method for 'getManagerRoleName()'
     *
     * @throws Exception to JUnit
     */
    public void testGetManagerRoleName() throws Exception {
        String expect = "manager";
        assertEquals("incorrect ManagerRoleName", expect, IMAjaxSupportUtility.getManagerRoleName());
    }

    /**
     * Test method for 'getRolePropertyKey()'
     *
     * @throws Exception to JUnit
     */
    public void testGetRolePropertyKey() throws Exception {
        String expect = "role";
        assertEquals("incorrect RolePropertyKey", expect, IMAjaxSupportUtility.getRolePropertyKey());
    }

    /**
     * Test method for 'getUserProfileSessionKey()'
     *
     * @throws Exception to JUnit
     */
    public void testGetUserProfileSessionKey() throws Exception {
        String expect = "user_profile_session_key";
        assertEquals("incorrect UserProfileSessionKey", expect, IMAjaxSupportUtility.getUserProfileSessionKey());
    }

    /**
     * Test method for 'getIMMessengerKey()'
     *
     * @throws Exception to JUnit
     */
    public void testGetIMMessengerKey() throws Exception {
        String expect = "messenger_key";
        assertEquals("incorrect IMMessengerKey", expect, IMAjaxSupportUtility.getIMMessengerKey());
    }

    /**
     * Test method for 'getChatSessionManagerKey()'
     *
     * @throws Exception to JUnit
     */
    public void testGetChatSessionManagerKey() throws Exception {
        String expect = "chat_session_manager_key";
        assertEquals("incorrect ChatSessionManagerKey", expect, IMAjaxSupportUtility.getChatSessionManagerKey());
    }

    /**
     * Test method for 'getChatStatusTrackerKey()'
     *
     * @throws Exception to JUnit
     */
    public void testGetChatStatusTrackerKey() throws Exception {
        String expect = "chat_status_tracker_key";
        assertEquals("incorrect ChatStatusTrackerKey", expect, IMAjaxSupportUtility.getChatStatusTrackerKey());
    }

    /**
     * Test method for 'getServiceEngineKey()'
     *
     * @throws Exception to JUnit
     */
    public void testGetServiceEngineKey() throws Exception {
        String expect = "service_engine_key";
        assertEquals("incorrect ServiceEngineKey", expect, IMAjaxSupportUtility.getServiceEngineKey());
    }

    /**
     * Test method for 'getTimezoneCookieName()'
     *
     * @throws Exception to JUnit
     */
    public void testGetTimezoneCookieName() throws Exception {
        String expect = "timeZone";
        assertEquals("incorrect TimezoneCookieName", expect, IMAjaxSupportUtility.getTimezoneCookieName());
    }

    /**
     * Test method for 'getCategorySessionKey()'
     *
     * @throws Exception to JUnit
     */
    public void testGetCategorySessionKey() throws Exception {
        String expect = "category_session_key";
        assertEquals("incorrect CategorySessionKey", expect, IMAjaxSupportUtility.getCategorySessionKey());
    }

    /**
     * Test method for 'getXMLRequestParamKey()'
     *
     * @throws Exception to JUnit
     */
    public void testGetXMLRequestParamKey() throws Exception {
        String expect = "xml_request";
        assertEquals("incorrect XMLRequestParamKey", expect, IMAjaxSupportUtility.getXMLRequestParamKey());
    }

    /**
     * Test method for 'getUserIdServiceElementKey()'
     *
     * @throws Exception to JUnit
     */
    public void testGetUserIdServiceElementKey() throws Exception {
        String expect = "user_id_property_key";
        assertEquals("incorrect UserIdServiceElementKey", expect, IMAjaxSupportUtility.getUserIdServiceElementKey());
    }

    /**
     * Test method for 'getSessionIdServiceElementKey()'
     *
     * @throws Exception to JUnit
     */
    public void testGetSessionIdServiceElementKey() throws Exception {
        String expect = "session_id_property_key";
        assertEquals("incorrect SessionIdServiceElementKey", expect,
            IMAjaxSupportUtility.getSessionIdServiceElementKey());
    }
}
