/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.accuracytests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.struts2.ServletActionContext;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.springframework.mock.web.MockHttpServletRequest;

import com.opensymphony.xwork2.ActionContext;
import com.topcoder.security.groups.actions.CreateCustomerAdminAction;
import com.topcoder.security.groups.model.Client;
import com.topcoder.security.groups.model.CustomerAdministrator;
import com.topcoder.security.groups.model.GroupAuditRecord;
import com.topcoder.security.groups.services.ClientService;
import com.topcoder.security.groups.services.CustomerAdministratorService;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.security.groups.services.UserService;
import com.topcoder.security.groups.services.dto.UserDTO;

/**
 * <p>
 * Accuracy test case of {@link CreateCustomerAdminAction}.
 * </p>
 * 
 * @author gjw99
 * @version 1.0
 */
public class CreateCustomerAdminActionTest extends TestCase {
	/**
	 * <p>
	 * The CreateCustomerAdminAction instance to test.
	 * </p>
	 */
	private CreateCustomerAdminAction instance;
	/**
	 * <p>
	 * The ClientService instance to test.
	 * </p>
	 */
	private ClientService clientService;
	/**
	 * <p>
	 * The CustomerAdministratorService instance to test.
	 * </p>
	 */
	private CustomerAdministratorService customerAdministratorService;
	/**
	 * <p>
	 * The UserService instance to test.
	 * </p>
	 */
	private UserService userService;

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
		instance = new CreateCustomerAdminAction();
		clientService = EasyMock.createMock(ClientService.class);
		instance.setClientService(clientService);
		customerAdministratorService = EasyMock
				.createMock(CustomerAdministratorService.class);
		instance.setCustomerAdministratorService(customerAdministratorService);
		userService = EasyMock.createMock(UserService.class);
		instance.setUserService(userService);
		instance.setClientId(2l);
		instance.setHandle("tester");
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
		TestSuite suite = new TestSuite(CreateCustomerAdminActionTest.class);

		return suite;
	}

	/**
	 * <p>
	 * Accuracy test for method CreateCustomerAdminAction#execute().
	 * </p>
	 * 
	 * @throws Throwable
	 *             to junit
	 */
	public void test_execute() throws Throwable {
		List<UserDTO> users = new ArrayList<UserDTO>();
		UserDTO user = new UserDTO();
		user.setUserId(2);
		users.add(user);
		EasyMock.expect(userService.search("tester")).andReturn(users);
		Client client = new Client();
		EasyMock.expect(clientService.get(2l)).andReturn(client);
		customerAdministratorService = EasyMock
				.createNiceMock(CustomerAdministratorService.class);
		Capture<CustomerAdministrator> capture = new Capture<CustomerAdministrator>();
		EasyMock.expect(customerAdministratorService.add(EasyMock.capture(capture))).andReturn(2l);
		instance.setCustomerAdministratorService(customerAdministratorService);
		GroupAuditService auditService = EasyMock
				.createNiceMock(GroupAuditService.class);
		Capture<GroupAuditRecord> record = new Capture<GroupAuditRecord>();
		EasyMock.expect(auditService.add(EasyMock.capture(record))).andReturn(2l);
		instance.setAuditService(auditService);
		EasyMock.replay(userService);
		EasyMock.replay(clientService);
		EasyMock.replay(customerAdministratorService);
		EasyMock.replay(auditService);
		instance.execute();
	}
}
