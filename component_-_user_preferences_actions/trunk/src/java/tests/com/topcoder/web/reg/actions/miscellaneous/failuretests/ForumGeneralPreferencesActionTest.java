/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.failuretests;

import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.web.reg.actions.miscellaneous.ForumGeneralPreferencesAction;
import com.topcoder.web.reg.actions.miscellaneous.UserPreferencesActionConfigurationException;

/**
 * Failure tests for <code>ForumGeneralPreferencesAction</code>.
 * @author moon.river
 * @version 1.0
 */
public class ForumGeneralPreferencesActionTest extends BaseTest {

	/**
	 * Instance to test.
	 */
	private ForumGeneralPreferencesAction instance;

	/**
	 * Sets up the environment.
	 * @throws java.lang.Exception to JUnit
	 */
	public void setUp() throws Exception {
		super.setUp();
        ActionProxy proxy = getActionProxy("/forumgeneral");
        instance = (ForumGeneralPreferencesAction) proxy.getAction();
	}

	/**
	 * Cleans up the environment.
	 * @throws java.lang.Exception to JUnit
	 */
	public void tearDown() throws Exception {
		super.tearDown();
		instance = null;
	}

	/**
	 * Tests method for prepare.
	 * @throws Exception to JUnit
	 */
	public void testPrepare_NegativePerPage() throws Exception {
		try {
			instance.setForumsPerPage(-1);
			instance.prepare();
			fail("UserPreferencesActionConfigurationException expected");
		} catch (UserPreferencesActionConfigurationException e) {
			// good
		}
	}

	/**
	 * Tests method for prepare.
	 * @throws Exception to JUnit
	 */
	public void testPrepare_NegativeThreadsPerPage() throws Exception {
		try {
			instance.setThreadsPerPage(-1);
			instance.prepare();
			fail("UserPreferencesActionConfigurationException expected");
		} catch (UserPreferencesActionConfigurationException e) {
			// good
		}
	}

	/**
	 * Tests method for prepare.
	 * @throws Exception to JUnit
	 */
	public void testPrepare_NegativeMessagesPerPage() throws Exception {
		try {
			instance.setMessagesPerPage(-1);
			instance.prepare();
			fail("UserPreferencesActionConfigurationException expected");
		} catch (UserPreferencesActionConfigurationException e) {
			// good
		}
	}
	/**
	 * Tests method for prepare.
	 * @throws Exception to JUnit
	 */
	public void testPrepare_NullThreadMode() throws Exception {
		try {
			instance.setThreadMode(null);
			instance.prepare();
			fail("UserPreferencesActionConfigurationException expected");
		} catch (UserPreferencesActionConfigurationException e) {
			// good
		}
	}
	/**
	 * Tests method for prepare.
	 * @throws Exception to JUnit
	 */
	public void testPrepare_NullFlatMode() throws Exception {
		try {
			instance.setFlatMode(null);
			instance.prepare();
			fail("UserPreferencesActionConfigurationException expected");
		} catch (UserPreferencesActionConfigurationException e) {
			// good
		}
	}


	/**
	 * Tests method for prepare.
	 * @throws Exception to JUnit
	 */
	public void testPrepare_NullShowPrevNextThreads() throws Exception {
		try {
			instance.setShowPrevNextThreads(null);
			instance.prepare();
			fail("UserPreferencesActionConfigurationException expected");
		} catch (UserPreferencesActionConfigurationException e) {
			// good
		}
	}

	/**
	 * Tests method for prepare.
	 * @throws Exception to JUnit
	 */
	public void testPrepare_NullDisplayMemberPhoto() throws Exception {
		try {
			instance.setDisplayMemberPhoto(null);
			instance.prepare();
			fail("UserPreferencesActionConfigurationException expected");
		} catch (UserPreferencesActionConfigurationException e) {
			// good
		}
	}

	/**
	 * Tests method for prepare.
	 * @throws Exception to JUnit
	 */
	public void testPrepare_NullDisplayAllMemberPhotos() throws Exception {
		try {
			instance.setDisplayAllMemberPhotos(null);
			instance.prepare();
			fail("UserPreferencesActionConfigurationException expected");
		} catch (UserPreferencesActionConfigurationException e) {
			// good
		}
	}

	/**
	 * Tests method for prepare.
	 * @throws Exception to JUnit
	 */
	public void testPrepare_NullCollapseRead() throws Exception {
		try {
			instance.setCollapseRead(null);
			instance.prepare();
			fail("UserPreferencesActionConfigurationException expected");
		} catch (UserPreferencesActionConfigurationException e) {
			// good
		}
	}

	/**
	 * Tests method for prepare.
	 * @throws Exception to JUnit
	 */
	public void testPrepare_NullCollapseReadDays() throws Exception {
		try {
			instance.setCollapseReadDays(null);
			instance.prepare();
			fail("UserPreferencesActionConfigurationException expected");
		} catch (UserPreferencesActionConfigurationException e) {
			// good
		}
	}

	/**
	 * Tests method for prepare.
	 * @throws Exception to JUnit
	 */
	public void testPrepare_NullUBackupSessionKey() throws Exception {
		try {
			instance.setCollapseReadPosts(null);
			instance.prepare();
			fail("UserPreferencesActionConfigurationException expected");
		} catch (UserPreferencesActionConfigurationException e) {
			// good
		}
	}

	/**
	 * Tests method for prepare.
	 * @throws Exception to JUnit
	 */
	public void testPrepare_EmptyUBackupSessionKey() throws Exception {
		try {
			instance.setCollapseReadShowReplied(null);
			instance.prepare();
			fail("UserPreferencesActionConfigurationException expected");
		} catch (UserPreferencesActionConfigurationException e) {
			// good
		}
	}

}
