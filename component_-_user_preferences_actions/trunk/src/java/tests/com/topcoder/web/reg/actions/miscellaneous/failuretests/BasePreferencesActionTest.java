/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.failuretests;

import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.web.reg.actions.miscellaneous.UserPreferencesActionConfigurationException;
import com.topcoder.web.reg.actions.miscellaneous.UserPreferencesActionExecutionException;

/**
 * Failure tests for <code>BasePreferencesAction</code>.
 * @author moon.river
 * @version 1.0
 */
public class BasePreferencesActionTest extends BaseTest {

	/**
	 * Instance to test.
	 */
	private BasePreferencesActionWrapper instance;

	/**
	 * Sets up the environment.
	 * @throws java.lang.Exception to JUnit
	 */
	public void setUp() throws Exception {
		super.setUp();
        ActionProxy proxy = getActionProxy("/base");
        instance = (BasePreferencesActionWrapper) proxy.getAction();
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
	 * Test method for {@link com.topcoder.web.reg.actions.miscellaneous.BasePreferencesAction
	 * #sendEmail()}.
	 * Note, the other fields of the email will be checked in <code>prepare</code>. No need to test here.
	 * @throws Exception to JUnit
	 */
	public void testSendEmail_TemplateNotExist() throws Exception {
		try {
	        ActionProxy proxy = getActionProxy("/base");
	        instance = (BasePreferencesActionWrapper) proxy.getAction();
	        proxy.execute();
			instance.setEmailBodyTemplateFileName("not.exist");
			instance.sendEmail();
			fail("UserPreferencesActionExecutionException expected");
		} catch (UserPreferencesActionExecutionException e) {
			// good
		}
	}

	/**
	 * Test method for {@link com.topcoder.web.reg.actions.miscellaneous.BasePreferencesAction
	 * #sendEmail()}.
	 * Note, the other fields of the email will be checked in <code>prepare</code>. No need to test here.
	 * @throws Exception to JUnit
	 */
	public void testSendEmail_TemplateInvalid() throws Exception {
		try {
	        ActionProxy proxy = getActionProxy("/base");
	        instance = (BasePreferencesActionWrapper) proxy.getAction();
	        proxy.execute();
			instance.setEmailBodyTemplateFileName("failure/invalid_template.xml");
			instance.sendEmail();
			fail("UserPreferencesActionExecutionException expected");
		} catch (UserPreferencesActionExecutionException e) {
			// good
		}
	}

	/**
	 * Tests method for prepare.
	 * @throws Exception to JUnit
	 */
	public void testPrepare_NullUserDAO() throws Exception {
		try {
			instance.setUserDao(null);
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
	public void testPrepare_NullAuditDAO() throws Exception {
		try {
			instance.setAuditDao(null);
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
	public void testPrepare_NullDocumentGenerator() throws Exception {
		try {
			instance.setDocumentGenerator(null);
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
	public void testPrepare_NullFromEmailAddress() throws Exception {
		try {
			instance.setFromEmailAddress(null);
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
	public void testPrepare_EmptyFromEmailAddress() throws Exception {
		try {
			instance.setFromEmailAddress("\t \n");
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
