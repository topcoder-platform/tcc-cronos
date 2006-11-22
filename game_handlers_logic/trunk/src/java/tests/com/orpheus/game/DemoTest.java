/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.user.profile.manager.SimpleUserProfileManager;
import com.topcoder.user.profile.manager.UserProfileManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * Demo for the component.
 * <p>
 * Sometimes it might be useful to create handler programmatically, when we want to configure the actions in Front
 * Controller dynamically. But we are NEVER supposed to call the execute method on our own, it is expected to be called
 * by the Front Controller.
 * </p>
 *
 * @author mittu
 * @version 1.0
 */
public class DemoTest extends TestCase {
    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(DemoTest.class);
    }

    /**
     * Sets up the environment for the TestCase.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.loadConfig(TestHelper.CONFIG);
    }

    /**
     * Tears down the environment for the TestCase.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.releaseConfigs();
    }

    /**
     * Demo for using the handles.
     *
     * @throws Exception
     *             exception to junit.
     */
    public void testDemo() throws Exception {
        // create ActiveGamesHandler
        ActiveGamesHandler handler1 = new ActiveGamesHandler("games");

        // create TestTargetObjectHandler
        Map ttoAttrs = new HashMap();
        ttoAttrs.put("gameIdParamKey", "gameId");
        ttoAttrs.put("domainNameParamKey", "domainName");
        ttoAttrs.put("sequenceNumberParamKey", "seqNo");
        ttoAttrs.put("textParamKey", "text");
        ttoAttrs.put("testFailedResultCode", "test_failed");
        ttoAttrs.put("notLoggedInResultCode", "not_logged_in");

        TestTargetObjectHandler handler2 = new TestTargetObjectHandler(ttoAttrs);

        // create UserGamesHandler
        UserGamesHandler handler3 = new UserGamesHandler("games", "not_logged_in");

        // create TestDomainHandler
        TestDomainHandler handler4 = new TestDomainHandler("domainName", "games", "not_logged_in");

        // create UpcomingDomainsHandler
        UpcomingDomainsHandler handler5 = new UpcomingDomainsHandler("gameId", "domains");

        // create UnlockedDomainsHandler
        UnlockedDomainsHandler handler6 = new UnlockedDomainsHandler("gameId", "domains");

        // create GameDetailHandler
        GameDetailHandler handler7 = new GameDetailHandler("gameId", "gameDetail");

        // create LeaderBoardHandler
        Map lbAttrs = new HashMap();
        lbAttrs.put("gameIdParamKey", "gameId");
        lbAttrs.put("profilesKey", "profiles");
        lbAttrs.put("maxLeaders", new Integer(10));

        // profileManager object is a UserProfileManager object provided by user
        UserProfileManager profileManager = new SimpleUserProfileManager("com.topcoder.user.profile.manager");
        lbAttrs.put("profileManager", profileManager);
        LeaderBoardHandler handler8 = new LeaderBoardHandler(lbAttrs);

        // create SlotValidationHandler
        SlotValidationHandler handler9 = new SlotValidationHandler("gameId", "slotId", "validation_failed");
    }
}
