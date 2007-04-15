/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.failuretests;

import com.cronos.im.ajax.IMAjaxConfigurationException;
import com.cronos.im.ajax.IMAjaxSupportUtility;

import junit.framework.TestCase;

import java.io.File;


/**
 * Failure test case for the class IMAjaxSupportUtility.
 *
 * @author waits
 * @version 1.0
 * @since Apr 6, 2007
 */
public class IMAjaxSupportUtilityFailureTests extends TestCase {
    /**
     * Test getClientRoleName method, the property 'client_role_name' is missing. IMAjaxConfigurationException
     * expected.
     *
     * @throws Exception into JUnit
     */
    public void testGetClientRoleName_missingProperty() throws Exception {
        TestHelper.addConfiguration("failure" + File.separator + "invalid_config1.xml");

        try {
            IMAjaxSupportUtility.getClientRoleName();
            fail("The client_role_name property is missing.");
        } catch (IMAjaxConfigurationException e) {
            //good
        }
    }
    /**
     * Test getClientRoleName method, the property 'client_role_name' 's value is invalid. IMAjaxConfigurationException
     * expected.
     *
     * @throws Exception into JUnit
     */
    public void testGetClientRoleName_invalidValue() throws Exception {
        TestHelper.addConfiguration("failure" + File.separator + "invalid_config2.xml");

        try {
            IMAjaxSupportUtility.getClientRoleName();
            fail("The client_role_name property with invalid value.");
        } catch (IMAjaxConfigurationException e) {
            //good
        }
    }
    /**
     * Test getManagerRoleName method, the property 'manager_role_name' is missing. IMAjaxConfigurationException
     * expected.
     *
     * @throws Exception into JUnit
     */
    public void testGetManagerRoleName_missingProperty() throws Exception {
        TestHelper.addConfiguration("failure" + File.separator + "invalid_config3.xml");

        try {
            IMAjaxSupportUtility.getManagerRoleName();
            fail("The manager_role_name property is missing.");
        } catch (IMAjaxConfigurationException e) {
            //good
        }
    }
    /**
     * Test getRolePropertyKey method, the property 'role_property_name' is missing. IMAjaxConfigurationException
     * expected.
     *
     * @throws Exception into JUnit
     */
    public void testGetRolePropertyKey_missingProperty() throws Exception {
        TestHelper.addConfiguration("failure" + File.separator + "invalid_config4.xml");

        try {
            IMAjaxSupportUtility.getRolePropertyKey();
            fail("The role_property_name property is missing.");
        } catch (IMAjaxConfigurationException e) {
            //good
        }
    }
    /**
     * Test getUserProfileSessionKey method, the property 'user_profile_session_key' is missing. IMAjaxConfigurationException
     * expected.
     *
     * @throws Exception into JUnit
     */
    public void testGetUserProfileSessionKey_missingProperty() throws Exception {
        TestHelper.addConfiguration("failure" + File.separator + "invalid_config5.xml");

        try {
            IMAjaxSupportUtility.getUserProfileSessionKey();
            fail("The user_profile_session_key property is missing.");
        } catch (IMAjaxConfigurationException e) {
            //good
        }
    }
    /**
     * Test getIMMessengerKey method, the property 'messenger_key' is missing. IMAjaxConfigurationException
     * expected.
     *
     * @throws Exception into JUnit
     */
    public void testgGetIMMessengerKey_missingProperty() throws Exception {
        TestHelper.addConfiguration("failure" + File.separator + "invalid_config6.xml");

        try {
            IMAjaxSupportUtility.getIMMessengerKey();
            fail("The messenger_key property is missing.");
        } catch (IMAjaxConfigurationException e) {
            //good
        }
    }
    /**
     * Test getChatSessionManagerKey method, the property 'chat_session_manager_key' is missing. IMAjaxConfigurationException
     * expected.
     *
     * @throws Exception into JUnit
     */
    public void testGetChatSessionManagerKey_missingProperty() throws Exception {
        TestHelper.addConfiguration("failure" + File.separator + "invalid_config7.xml");

        try {
            IMAjaxSupportUtility.getChatSessionManagerKey();
            fail("The chat_session_manager_key property is missing.");
        } catch (IMAjaxConfigurationException e) {
            //good
        }
    }
    /**
     * Test getChatStatusTrackerKey method, the property 'chat_status_tracker_key' is missing. IMAjaxConfigurationException
     * expected.
     *
     * @throws Exception into JUnit
     */
    public void testGetChatStatusTrackerKey_missingProperty() throws Exception {
        TestHelper.addConfiguration("failure" + File.separator + "invalid_config8.xml");

        try {
            IMAjaxSupportUtility.getChatStatusTrackerKey();
            fail("The chat_status_tracker_key property is missing.");
        } catch (IMAjaxConfigurationException e) {
            //good
        }
    }
    /**
     * Test getServiceEngineKey method, the property 'service_engine_key' is missing. IMAjaxConfigurationException
     * expected.
     *
     * @throws Exception into JUnit
     */
    public void testGetServiceEngineKey_missingProperty() throws Exception {
        TestHelper.addConfiguration("failure" + File.separator + "invalid_config9.xml");

        try {
            IMAjaxSupportUtility.getServiceEngineKey();
            fail("The service_engine_key property is missing.");
        } catch (IMAjaxConfigurationException e) {
            //good
        }
    }
    /**
     * Test getTimezoneCookieName method, the property 'time_zone_cookie_name' is missing. IMAjaxConfigurationException
     * expected.
     *
     * @throws Exception into JUnit
     */
    public void testGetTimezoneCookieName_missingProperty() throws Exception {
        TestHelper.addConfiguration("failure" + File.separator + "invalid_config10.xml");

        try {
            IMAjaxSupportUtility.getTimezoneCookieName();
            fail("The time_zone_cookie_name property is missing.");
        } catch (IMAjaxConfigurationException e) {
            //good
        }
    }
    /**
     * Test getCategorySessionKey method, the property 'category_session_key' is missing. IMAjaxConfigurationException
     * expected.
     *
     * @throws Exception into JUnit
     */
    public void testGetCategorySessionKey_missingProperty() throws Exception {
        TestHelper.addConfiguration("failure" + File.separator + "invalid_config11.xml");

        try {
            IMAjaxSupportUtility.getCategorySessionKey();
            fail("The category_session_key property is missing.");
        } catch (IMAjaxConfigurationException e) {
            //good
        }
    }
    /**
     * Test getUserIdServiceElementKey method, the property 'user_id_property_key' is missing. IMAjaxConfigurationException
     * expected.
     *
     * @throws Exception into JUnit
     */
    public void testGetUserIdServiceElementKey_missingProperty() throws Exception {
        TestHelper.addConfiguration("failure" + File.separator + "invalid_config13.xml");

        try {
            IMAjaxSupportUtility.getUserIdServiceElementKey();
            fail("The user_id_property_key property is missing.");
        } catch (IMAjaxConfigurationException e) {
            //good
        }
    }
    /**
     * Test getXMLRequestParamKey method, the property 'xml_request' is missing. IMAjaxConfigurationException
     * expected.
     *
     * @throws Exception into JUnit
     */
    public void testGetXMLRequestParamKey_missingProperty() throws Exception {
        TestHelper.addConfiguration("failure" + File.separator + "invalid_config12.xml");

        try {
            IMAjaxSupportUtility.getXMLRequestParamKey();
            fail("The xml_request property is missing.");
        } catch (IMAjaxConfigurationException e) {
            //good
        }
    }
    /**
     * Test getSessionIdServiceElementKey method, the property 'session_id_property_key' is missing. IMAjaxConfigurationException
     * expected.
     *
     * @throws Exception into JUnit
     */
    public void testGetSessionIdServiceElementKey_missingProperty() throws Exception {
        TestHelper.addConfiguration("failure" + File.separator + "invalid_config14.xml");

        try {
            IMAjaxSupportUtility.getSessionIdServiceElementKey();
            fail("The session_id_property_key property is missing.");
        } catch (IMAjaxConfigurationException e) {
            //good
        }
    }

    /**
     * Clear the environment.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfiguration();
    }
}
