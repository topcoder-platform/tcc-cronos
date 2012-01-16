/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.actions.project.milestone;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.topcoder.direct.services.project.milestone.AuthorizationException;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;
import com.topcoder.shared.util.logging.Logger;

/**
 * <p>
 * Unit test cases for <code>ActionHelper</code> class.
 * </p>
 *
 * @author fish_ship
 * @version 1.0
 */
public class ActionHelperTest {
    /**
     * <p>
     * Represents the logger for test.
     * </p>
     */
    private Logger logger = Logger.getLogger(ActionHelper.class);

    /**
     * <p>
     * Accuracy test of <code>logEntrance</code> method.<br>
     * The result is expected.
     * </p>
     */
    @Test
    public void testLogEntranceAccuracy() {
        Date date = ActionHelper.logEntrance(logger, "test");
        Assert.assertTrue("fail to log entrance", System.currentTimeMillis() >= date.getTime());
    }

    /**
     * <p>
     * Accuracy test of <code>logExit</code> method.<br>
     * The result is expected.
     * </p>
     */
    @Test
    public void testLogExitAccuracy() {
        ActionHelper.logExit(logger, "test", new Date());
    }

    /**
     * <p>
     * Failure test for <code>checkDuplicated</code> method.<br>
     * Expected IllegalArgumentException because the list can not contain duplicated element.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCheckDuplicatedFailure() throws Exception {
        List<String> list = new ArrayList<String>();
        list.add("abc");
        list.add("abc");
        ActionHelper.checkDuplicated(list, "test");
    }

    /**
     * <p>
     * Accuracy test for <code>checkDuplicated</code> method.<br>
     * No exception is thrown when list is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testCheckDuplicated() throws Exception {
        ActionHelper.checkDuplicated(null, "test");
    }

    /**
     * <p>
     * Failure test for <code>checkMilestones</code> method.<br>
     * Expected IllegalArgumentException because the milestones is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCheckMilestonesFailure() throws Exception {
        ActionHelper.checkMilestones(null, new TCSubject(1), new CreateMilestonesAction());
    }

    /**
     * <p>
     * Failure test for <code>validatePermission</code> method.<br>
     * Expected AuthorizationException because the current user has not write permission to the given project.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testValidatePermissionFailure() throws Exception {
        try {
            DirectUtils.permission = false;
            ActionHelper.validatePermission(new TCSubject(1), 1, new CreateMilestonesAction());
            Assert.fail("the AuthorizationException is expected.");
        } catch (AuthorizationException e) {
            DirectUtils.permission = true;
        }
    }
}
