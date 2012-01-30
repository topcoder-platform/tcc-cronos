/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.accuracytests;

import java.util.Arrays;
import java.util.HashMap;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.struts2.ServletActionContext;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.springframework.mock.web.MockHttpServletRequest;

import com.opensymphony.xwork2.ActionContext;
import com.topcoder.security.groups.actions.SendInvitationAction;
import com.topcoder.security.groups.model.GroupAuditRecord;
import com.topcoder.security.groups.services.GroupAuditService;

/**
 * <p>
 * Accuracy test case of {@link SendInvitationAction}.
 * </p>
 * 
 * @author gjw99
 * @version 1.0
 */
public class SendInvitationActionTest extends TestCase {
	/**
	 * <p>
	 * The SendInvitationAction instance to test.
	 * </p>
	 */
	private SendInvitationAction instance;

	/**
	 * <p>
	 * Sets up test environment.
	 * </p>
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	@Override
	public void setUp() throws Exception {
		super.setUp();
		instance = new SendInvitationAction();
		instance.setGroupService(new MockGroupService());
		instance.setGroupInvitationService(new MockGroupInvitationService());
		instance
				.setCustomerAdministratorService(new MockCustomerAdministratorService());
		instance.setUserService(new MockUserService());
		instance.setAuthorizationService(new MockAuthorizationService());
		instance.setGroupNames(Arrays.asList(new String[] { "group1" }));
		instance.setHandles(Arrays.asList(new String[] { "tester" }));
		ActionContext ctx = new ActionContext(new HashMap<String, Object>());
		ActionContext.setContext(ctx);
		ServletActionContext.setContext(ctx);
		ServletActionContext.setRequest(new MockHttpServletRequest());
	}

	/**
	 * <p>
	 * Tears down test environment.
	 * </p>
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		instance = null;
	}

	/**
	 * <p>
	 * Creates a test suite for the tests in this test case.
	 * </p>
	 * 
	 * @return a TestSuite for this test case.
	 */
	public static Test suite() {
		TestSuite suite = new TestSuite(SendInvitationActionTest.class);

		return suite;
	}

	/**
	 * <p>
	 * Accuracy test for method SendInvitationAction#input().
	 * </p>
	 * 
	 * @throws Throwable
	 *             to junit
	 */
	public void test_input() throws Throwable {
		instance.input();
		assertNotNull("the groups should be set", instance.getGroups());
	}

	/**
	 * <p>
	 * Accuracy test for method SendInvitationAction#execute().
	 * </p>
	 * 
	 * @throws Throwable
	 *             to junit
	 */
	public void test_execute() throws Throwable {
		GroupAuditService auditService = EasyMock
				.createNiceMock(GroupAuditService.class);
		Capture<GroupAuditRecord> record = new Capture<GroupAuditRecord>();
		EasyMock.expect(auditService.add(EasyMock.capture(record))).andReturn(
				2l);
		instance.setAuditService(auditService);
		EasyMock.replay(auditService);
		instance.execute();
	}
}
