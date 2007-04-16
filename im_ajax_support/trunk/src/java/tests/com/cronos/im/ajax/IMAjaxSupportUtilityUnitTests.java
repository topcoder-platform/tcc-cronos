/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test cases for IMAjaxSupportUtility class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class IMAjaxSupportUtilityUnitTests extends TestCase {

    /**
     * Initialize the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.setUp();
    }

    /**
     * Clear the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.tearDown();
    }

    /**
     * Tests the getClientRoleName() method. The returned vaule should be as
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetClientRoleName_1() throws Exception {
        assertEquals("The getClientRoleName() method is incorrect.", "client", IMAjaxSupportUtility
                .getClientRoleName());
    }

    /**
     * Failure test for the getClientRoleName() method.
     * IMAjaxConfigurationException should be thrown if it fail to get the
     * configuration value.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetClientRoleName_Failure_1() throws Exception {
        tearDown();
        try {
            IMAjaxSupportUtility.getClientRoleName();
            fail("IMAjaxConfigurationException should be thrown if it fail to get the configuration value");
        } catch (IMAjaxConfigurationException e) {
            // ok
        }
    }

    /**
     * Tests the getManagerRoleName() method. The returned vaule should be as
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetManagerRoleName_1() throws Exception {
        assertEquals("The getManagerRoleName() method is incorrect.", "manager", IMAjaxSupportUtility
                .getManagerRoleName());
    }

    /**
     * Failure test for the getManagerRoleName() method.
     * IMAjaxConfigurationException should be thrown if it fail to get the
     * configuration value.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetManagerRoleName_Failure_1() throws Exception {
        tearDown();
        try {
            IMAjaxSupportUtility.getManagerRoleName();
            fail("IMAjaxConfigurationException should be thrown if it fail to get the configuration value");
        } catch (IMAjaxConfigurationException e) {
            // ok
        }
    }

    /**
     * Tests the getRolePropertyKey() method. The returned vaule should be as
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetRolePropertyKey_1() throws Exception {
        assertEquals("The getRolePropertyKey() method is incorrect.", "role", IMAjaxSupportUtility
                .getRolePropertyKey());
    }

    /**
     * Failure test for the getRolePropertyKey() method.
     * IMAjaxConfigurationException should be thrown if it fail to get the
     * configuration value.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetRolePropertyKey_Failure_1() throws Exception {
        tearDown();
        try {
            IMAjaxSupportUtility.getRolePropertyKey();
            fail("IMAjaxConfigurationException should be thrown if it fail to get the configuration value");
        } catch (IMAjaxConfigurationException e) {
            // ok
        }
    }

    /**
     * Tests the getUserProfileSessionKey() method. The returned vaule should be
     * as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetUserProfileSessionKey_1() throws Exception {
        assertEquals("The getUserProfileSessionKey() method is incorrect.", "user_profile_session_key",
                IMAjaxSupportUtility.getUserProfileSessionKey());
    }

    /**
     * Failure test for the getUserProfileSessionKey() method.
     * IMAjaxConfigurationException should be thrown if it fail to get the
     * configuration value.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetUserProfileSessionKey_Failure_1() throws Exception {
        tearDown();
        try {
            IMAjaxSupportUtility.getUserProfileSessionKey();
            fail("IMAjaxConfigurationException should be thrown if it fail to get the configuration value");
        } catch (IMAjaxConfigurationException e) {
            // ok
        }
    }

    /**
     * Tests the getIMMessengerKey() method. The returned vaule should be as
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetIMMessengerKey_1() throws Exception {
        assertEquals("The getIMMessengerKey() method is incorrect.", "messenger_key", IMAjaxSupportUtility
                .getIMMessengerKey());
    }

    /**
     * Failure test for the getIMMessengerKey() method.
     * IMAjaxConfigurationException should be thrown if it fail to get the
     * configuration value.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetIMMessengerKey_Failure_1() throws Exception {
        tearDown();
        try {
            IMAjaxSupportUtility.getIMMessengerKey();
            fail("IMAjaxConfigurationException should be thrown if it fail to get the configuration value");
        } catch (IMAjaxConfigurationException e) {
            // ok
        }
    }

    /**
     * Tests the getChatSessionManagerKey() method. The returned vaule should be
     * as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetChatSessionManagerKey_1() throws Exception {
        assertEquals("The getChatSessionManagerKey() method is incorrect.", "chat_session_manager_key",
                IMAjaxSupportUtility.getChatSessionManagerKey());
    }

    /**
     * Failure test for the getChatSessionManagerKey() method.
     * IMAjaxConfigurationException should be thrown if it fail to get the
     * configuration value.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetChatSessionManagerKey_Failure_1() throws Exception {
        tearDown();
        try {
            IMAjaxSupportUtility.getChatSessionManagerKey();
            fail("IMAjaxConfigurationException should be thrown if it fail to get the configuration value");
        } catch (IMAjaxConfigurationException e) {
            // ok
        }
    }

    /**
     * Tests the getChatStatusTrackerKey() method. The returned vaule should be
     * as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetChatStatusTrackerKey_1() throws Exception {
        assertEquals("The getChatStatusTrackerKey() method is incorrect.", "chat_status_tracker_key",
                IMAjaxSupportUtility.getChatStatusTrackerKey());
    }

    /**
     * Failure test for the getChatStatusTrackerKey() method.
     * IMAjaxConfigurationException should be thrown if it fail to get the
     * configuration value.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetChatStatusTrackerKey_Failure_1() throws Exception {
        tearDown();
        try {
            IMAjaxSupportUtility.getChatStatusTrackerKey();
            fail("IMAjaxConfigurationException should be thrown if it fail to get the configuration value");
        } catch (IMAjaxConfigurationException e) {
            // ok
        }
    }

    /**
     * Tests the getServiceEngineKey() method. The returned vaule should be as
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetServiceEngineKey_1() throws Exception {
        assertEquals("The getServiceEngineKey() method is incorrect.", "service_engine_key",
                IMAjaxSupportUtility.getServiceEngineKey());
    }

    /**
     * Failure test for the getServiceEngineKey() method.
     * IMAjaxConfigurationException should be thrown if it fail to get the
     * configuration value.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetServiceEngineKey_Failure_1() throws Exception {
        tearDown();
        try {
            IMAjaxSupportUtility.getServiceEngineKey();
            fail("IMAjaxConfigurationException should be thrown if it fail to get the configuration value");
        } catch (IMAjaxConfigurationException e) {
            // ok
        }
    }

    /**
     * Tests the getTimezoneCookieName() method. The returned vaule should be as
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetTimezoneCookieName_1() throws Exception {
        assertEquals("The getTimezoneCookieName() method is incorrect.", "timeZone", IMAjaxSupportUtility
                .getTimezoneCookieName());
    }

    /**
     * Failure test for the getTimezoneCookieName() method.
     * IMAjaxConfigurationException should be thrown if it fail to get the
     * configuration value.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetTimezoneCookieName_Failure_1() throws Exception {
        tearDown();
        try {
            IMAjaxSupportUtility.getTimezoneCookieName();
            fail("IMAjaxConfigurationException should be thrown if it fail to get the configuration value");
        } catch (IMAjaxConfigurationException e) {
            // ok
        }
    }

    /**
     * Tests the getCategorySessionKey() method. The returned vaule should be as
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetCategorySessionKey_1() throws Exception {
        assertEquals("The getCategorySessionKey() method is incorrect.", "category_session_key",
                IMAjaxSupportUtility.getCategorySessionKey());
    }

    /**
     * Failure test for the getCategorySessionKey() method.
     * IMAjaxConfigurationException should be thrown if it fail to get the
     * configuration value.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetCategorySessionKey_Failure_1() throws Exception {
        tearDown();
        try {
            IMAjaxSupportUtility.getCategorySessionKey();
            fail("IMAjaxConfigurationException should be thrown if it fail to get the configuration value");
        } catch (IMAjaxConfigurationException e) {
            // ok
        }
    }

    /**
     * Tests the getXMLRequestParamKey() method. The returned vaule should be as
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetXMLRequestParamKey_1() throws Exception {
        assertEquals("The getXMLRequestParamKey() method is incorrect.", "xml_request", IMAjaxSupportUtility
                .getXMLRequestParamKey());
    }

    /**
     * Failure test for the getXMLRequestParamKey() method.
     * IMAjaxConfigurationException should be thrown if it fail to get the
     * configuration value.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetXMLRequestParamKey_Failure_1() throws Exception {
        tearDown();
        try {
            IMAjaxSupportUtility.getXMLRequestParamKey();
            fail("IMAjaxConfigurationException should be thrown if it fail to get the configuration value");
        } catch (IMAjaxConfigurationException e) {
            // ok
        }
    }

    /**
     * Tests the getUserIdServiceElementKey() method. The returned vaule should
     * be as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetUserIdServiceElementKey_1() throws Exception {
        assertEquals("The getUserIdServiceElementKey() method is incorrect.", "user_id_property_key",
                IMAjaxSupportUtility.getUserIdServiceElementKey());
    }

    /**
     * Failure test for the getUserIdServiceElementKey() method.
     * IMAjaxConfigurationException should be thrown if it fail to get the
     * configuration value.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetUserIdServiceElementKey_Failure_1() throws Exception {
        tearDown();
        try {
            IMAjaxSupportUtility.getUserIdServiceElementKey();
            fail("IMAjaxConfigurationException should be thrown if it fail to get the configuration value");
        } catch (IMAjaxConfigurationException e) {
            // ok
        }
    }

    /**
     * Tests the getSessionIdServiceElementKey() method. The returned vaule
     * should be as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetSessionIdServiceElementKey_1() throws Exception {
        assertEquals("The getSessionIdServiceElementKey() method is incorrect.", "session_id_property_key",
                IMAjaxSupportUtility.getSessionIdServiceElementKey());
    }

    /**
     * Failure test for the getSessionIdServiceElementKey() method.
     * IMAjaxConfigurationException should be thrown if it fail to get the
     * configuration value.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetSessionIdServiceElementKey_Failure_1() throws Exception {
        tearDown();
        try {
            IMAjaxSupportUtility.getSessionIdServiceElementKey();
            fail("IMAjaxConfigurationException should be thrown if it fail to get the configuration value");
        } catch (IMAjaxConfigurationException e) {
            // ok
        }
    }
}
