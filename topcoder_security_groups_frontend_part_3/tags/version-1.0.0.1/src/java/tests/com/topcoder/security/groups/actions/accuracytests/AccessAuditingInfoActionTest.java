/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.accuracytests;

import java.util.HashMap;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.struts2.ServletActionContext;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.springframework.mock.web.MockHttpServletRequest;

import com.opensymphony.xwork2.ActionContext;
import com.topcoder.security.groups.actions.AccessAuditingInfoAction;
import com.topcoder.security.groups.model.GroupAuditRecord;
import com.topcoder.security.groups.services.GroupAuditService;

/**
 * <p>
 * Accuracy test case of {@link AccessAuditingInfoAction}.
 * </p>
 * 
 * @author gjw99
 * @version 1.0
 */
public class AccessAuditingInfoActionTest extends TestCase {
	/**
	 * <p>
	 * The AccessAuditingInfoAction instance to test.
	 * </p>
	 */
	private AccessAuditingInfoAction instance;

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
		instance = new AccessAuditingInfoAction();
		instance.setGroupService(new MockGroupService());
		instance
				.setCustomerAdministratorService(new MockCustomerAdministratorService());
		instance.setUserService(new MockUserService());
		instance.setAuthorizationService(new MockAuthorizationService());
		instance.setGroupMemberService(new MockGroupMemberService());
		instance.setDirectProjectService(new MockDirectProjectService());
		instance.setClientService(new MockClientService());
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
		TestSuite suite = new TestSuite(AccessAuditingInfoActionTest.class);

		return suite;
	}

	/**
	 * <p>
	 * Accuracy test for method AccessAuditingInfoAction#input().
	 * </p>
	 * 
	 * @throws Throwable
	 *             to junit
	 */
	public void test_input() throws Throwable {
		instance.input();
		assertNotNull("the clients should be set", instance.getClients());
	}

	/**
	 * <p>
	 * Accuracy test for method AccessAuditingInfoAction#execute().
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
		assertNotNull("the user by id should be set", instance.getUserById());
		assertTrue("the user by id should be empty", instance.getUserById().isEmpty());
	}
}
