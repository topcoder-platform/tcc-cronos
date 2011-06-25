/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.failuretests;

import java.util.Arrays;

import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.actions.miscellaneous.BlackListAction;
import com.topcoder.web.reg.actions.miscellaneous.UserPreferencesActionConfigurationException;

/**
 * Failure tests for <code>BlackListAction</code>.
 * @author moon.river
 * @version 1.0
 */
public class BlackListActionTest extends BaseTest {

	/**
	 * Instance to test.
	 */
	private BlackListAction instance;

	/**
	 * Sets up the environment.
	 * @throws java.lang.Exception to JUnit
	 */
	public void setUp() throws Exception {
		super.setUp();
        ActionProxy proxy = getActionProxy("/blacklist");
        instance = (BlackListAction) proxy.getAction();
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
			instance.setBlockedUsers(null);
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
			instance.setBlockedUsers(Arrays.asList((User) null));
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
			instance.setPreviouslyBlockedUsers(null);
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
			instance.setPreviouslyBlockedUsers(Arrays.asList((User) null));
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
	public void testPrepare_NullDAO() throws Exception {
		try {
			instance.setMemberContactBlackListDao(null);
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
