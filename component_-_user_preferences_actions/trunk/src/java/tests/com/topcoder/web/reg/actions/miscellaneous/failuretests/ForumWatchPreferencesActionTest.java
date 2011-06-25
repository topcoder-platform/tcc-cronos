/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.failuretests;

import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.web.reg.actions.miscellaneous.ForumWatchPreferencesAction;
import com.topcoder.web.reg.actions.miscellaneous.UserPreferencesActionConfigurationException;

/**
 * Failure tests for <code>ForumWatchPreferencesAction</code>.
 * @author moon.river
 * @version 1.0
 */
public class ForumWatchPreferencesActionTest extends BaseTest {

	/**
	 * Instance to test.
	 */
	private ForumWatchPreferencesAction instance;

	/**
	 * Sets up the environment.
	 * @throws java.lang.Exception to JUnit
	 */
	public void setUp() throws Exception {
		super.setUp();
        ActionProxy proxy = getActionProxy("/forumwatch");
        instance = (ForumWatchPreferencesAction) proxy.getAction();
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
	public void testPrepare_NullList() throws Exception {
		try {
			instance.setWatchFrequency(-1);
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
	public void testPrepare_NullInList() throws Exception {
		try {
			instance.setMarkWatchesRead(null);
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
	public void testPrepare_NullPreviousList() throws Exception {
		try {
			instance.setAutoWatchNewTopics(null);
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
	public void testPrepare_NullInPreviousList() throws Exception {
		try {
			instance.setAutoWatchReplies(null);
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
	public void testPrepare_NullEmailSubject() throws Exception {
		try {
			instance.setEmailSubject(null);
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
	public void testPrepare_EmptyEmailSubject() throws Exception {
		try {
			instance.setEmailSubject("\t\n");
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
	public void testPrepare_NullEmailText() throws Exception {
		try {
			instance.setEmailText(null);
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
	public void testPrepare_EmptyEmailText() throws Exception {
		try {
			instance.setEmailText("\t");
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
			instance.setBackupSessionKey(null);
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
			instance.setBackupSessionKey("");
			instance.prepare();
			fail("UserPreferencesActionConfigurationException expected");
		} catch (UserPreferencesActionConfigurationException e) {
			// good
		}
	}

}
