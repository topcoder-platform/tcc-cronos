/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations.accuracytests;

import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.TestCase;

import com.topcoder.controlpanel.clientassociations.ClientAssociationServiceException;
import com.topcoder.controlpanel.clientassociations.ClientAssociationServiceRemote;
import com.topcoder.controlpanel.clientassociations.dao.ClientAssociationDAOException;

/**
 * <p>
 * Accuracy test for the EJB deployed in JBoss.
 * </p>
 *
 * @author restarter
 * @version 1.0
 */
public class ClientAssociationServiceContainerTest extends TestCase {

	/**
	 * <p>
	 * An instance of <code>ClientAssociationServiceRemote</code> used for
	 * tests.
	 * </p>
	 */
	private ClientAssociationServiceRemote remote;

	/**
	 * <p>
	 * Initialization for all tests here.
	 * </p>
	 *
	 * @throws Exception
	 *             exception to JUnit.
	 */
	protected void setUp() throws Exception {
		AccuracyTestHelper.insertData();
		Properties props = new Properties();
		props.put(Context.PROVIDER_URL, "jnp://localhost:1099");
		props.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.jnp.interfaces.NamingContextFactory");
		InitialContext context = new InitialContext(props);
		remote = (ClientAssociationServiceRemote) context
				.lookup("ejb/remote/cabean");
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// ignore
		}
	}

	/**
	 * <p>
	 * Tears down the test environment.
	 * </p>
	 *
	 * @throws Exception
	 *             exception to JUnit.
	 */
	protected void tearDown() throws Exception {
		AccuracyTestHelper.removeData();
	}

	/**
	 * <p>
	 * Accuracy test for <code>getClientsByComponent(long componentId)</code>
	 * </p>
	 *
	 * @throws ClientAssociationServiceException
	 *             if error occurs
	 */
	public void testAssignComponent() throws ClientAssociationServiceException {
		remote.assignComponent(201, 21);
		remote.assignComponent(201, 22);
		List<Integer> clients = remote.getClientsByComponent(201);
		assertEquals("the assignComponent does not work properly", 2, clients
				.size());
		assertTrue("the assignComponent does not work properly", clients
				.contains(Integer.valueOf(21)));
		assertTrue("the assignComponent does not work properly", clients
				.contains(Integer.valueOf(22)));
	}

	/**
	 * <p>
	 * Accuracy test for <code>getClientsByUser(long userId)</code>
	 * </p>
	 *
	 * @throws ClientAssociationServiceException
	 *             if error occurs
	 */
	public void testAssignUser() throws ClientAssociationServiceException {
		remote.assignUser(101, 11, false);
		remote.assignUser(101, 12, true);
		assertFalse("the assignUser does not work properly", remote
				.isAdminUser(101, 11));
		assertTrue("the assignUser does not work properly", remote.isAdminUser(
				101, 12));
		List<Integer> clients = remote.getClientsByUser(101);
		assertEquals("the assignUser does not work properly", 2, clients.size());
		assertTrue("the assignUser does not work properly", clients
				.contains(Integer.valueOf(11)));
		assertTrue("the assignUser does not work properly", clients
				.contains(Integer.valueOf(12)));
	}

	/**
	 * <p>
	 * Accuracy test for <code>getClientsByComponent(long componentId)</code>
	 * </p>
	 *
	 * @throws ClientAssociationServiceException
	 *             if error occurs
	 */
	public void testGetClientsByComponent()
			throws ClientAssociationServiceException {
		assertEquals("the clients' ids are not properly retrieved", 0, remote
				.getClientsByComponent(202).size());
		List<Integer> clients = remote.getClientsByComponent(2);
		assertEquals("the clients' ids are not properly retrieved", 2, clients
				.size());
		assertTrue("the clients' ids are not properly retrieved", clients
				.contains(Integer.valueOf(22)));
		assertTrue("the clients' ids are not properly retrieved", clients
				.contains(Integer.valueOf(21)));
		clients = remote.getClientsByComponent(3);
		assertEquals("the clients' ids are not properly retrieved", 1, clients
				.size());
		assertTrue("the clients' ids are not properly retrieved", clients
				.contains(Integer.valueOf(22)));
	}

	/**
	 * <p>
	 * Accuracy test for <code>getClientsByUser(long userId)</code>
	 * </p>
	 *
	 * @throws ClientAssociationServiceException
	 *             if error occurs
	 */
	public void testGetClientsByUser() throws ClientAssociationServiceException {
		assertEquals("the clients' ids are not properly retrieved", 0, remote
				.getClientsByUser(202).size());
		List<Integer> clients = remote.getClientsByUser(2);
		assertEquals("the clients' ids are not properly retrieved", 2, clients
				.size());
		assertTrue("the clients' ids are not properly retrieved", clients
				.contains(Integer.valueOf(12)));
		assertTrue("the clients' ids are not properly retrieved", clients
				.contains(Integer.valueOf(11)));
		clients = remote.getClientsByUser(3);
		assertEquals("the clients' ids are not properly retrieved", 1, clients
				.size());
		assertTrue("the clients' ids are not properly retrieved", clients
				.contains(Integer.valueOf(12)));
	}

	/**
	 * <p>
	 * Accuracy test for <code>getComponentsByClient(int clientId)</code>
	 * </p>
	 *
	 * @throws ClientAssociationServiceException
	 *             if error occurs
	 */
	public void testGetComponentsByClient()
			throws ClientAssociationServiceException {
		assertEquals("the components' ids are not properly retrieved", 0,
				remote.getComponentsByClient(11).size());
		List<Long> comps = remote.getComponentsByClient(22);
		assertEquals("the components' ids are not properly retrieved", 2, comps
				.size());
		assertTrue("the components' ids are not properly retrieved", comps
				.contains(Long.valueOf(2)));
		assertTrue("the components' ids are not properly retrieved", comps
				.contains(Long.valueOf(3)));
		comps = remote.getComponentsByClient(21);
		assertEquals("the components' ids are not properly retrieved", 1, comps
				.size());
		assertTrue("the components' ids are not properly retrieved", comps
				.contains(Long.valueOf(2)));

	}

	/**
	 * <p>
	 * Accuracy test for <code>getComponentsByUser(int userId)</code>
	 * </p>
	 *
	 * @throws ClientAssociationServiceException
	 *             if error occurs
	 */
	public void testGetComponentsByUser()
			throws ClientAssociationServiceException {
		List<Long> comps = remote.getComponentsByUser(3);
		assertEquals("the components' ids are not properly retrieved", 1, comps
				.size());
		assertTrue("the components' ids are not properly retrieved", comps
				.contains(Long.valueOf(4)));
		assertEquals("the components' ids are not properly retrieved", 1,
				remote.getComponentsByUser(2).size());
		assertEquals("the components' ids are not properly retrieved", 0,
				remote.getComponentsByUser(4).size());
	}

	/**
	 * <p>
	 * Accuracy test for <code>getUsers(int clientId)</code>
	 * </p>
	 *
	 * @throws ClientAssociationServiceException
	 *             if error occurs
	 */
	public void testGetUsers() throws ClientAssociationServiceException {
		List<Long> users = remote.getUsers(1002);
		assertEquals("the users' ids are not properly retrieved", 0, users
				.size());
		users = remote.getUsers(12);
		assertEquals("the users' ids are not properly retrieved", 2, users
				.size());
		assertTrue("the users' ids are not properly retrieved", users
				.contains(Long.valueOf(2)));
		assertTrue("the users' ids are not properly retrieved", users
				.contains(Long.valueOf(3)));
		users = remote.getUsers(11);
		assertEquals("the users' ids are not properly retrieved", 1, users
				.size());
		assertTrue("the users' ids are not properly retrieved", users
				.contains(Long.valueOf(2)));
	}

	/**
	 * <p>
	 * Accuracy test for <code>isAdminUser(long userId, int clientId)</code>
	 * </p>
	 *
	 * @throws ClientAssociationServiceException
	 *             if error occurs
	 */
	public void testIsAdminUser() throws ClientAssociationServiceException {
		assertFalse("the isAdminUser(long, int) does not work properly.",
				remote.isAdminUser(2, 11));
		assertTrue("the isAdminUser(long, int) does not work properly.", remote
				.isAdminUser(2, 12));
		try {
			remote.isAdminUser(2001, 12);
			fail("the isAdminUser(long, int) does not work properly");
		} catch (ClientAssociationDAOException e) {
			// expect
		}
	}

	/**
	 * <p>
	 * Accuracy test for
	 * <code>removeComponent(long componentId, int clientId)</code>
	 * </p>
	 *
	 * @throws ClientAssociationServiceException
	 *             if error occurs
	 */
	public void testRemoveComponent() throws ClientAssociationServiceException {
		remote.removeComponent(201, 22);
		List<Long> comps = remote.getComponentsByClient(22);
		assertEquals("the removeComponent are not properly implemented.", 2,
				comps.size());
		remote.removeComponent(2, 22);
		comps = remote.getComponentsByClient(22);
		assertEquals("the removeComponent are not properly implemented.", 1,
				comps.size());
		assertTrue("the removeComponent are not properly implemented.", comps
				.contains(Long.valueOf(3)));
	}

	/**
	 * <p>
	 * Accuracy test for <code>removeUser(long userId, int clientId)</code>
	 * </p>
	 *
	 * @throws ClientAssociationServiceException
	 *             if error occurs
	 */
	public void testRemoveUser() throws ClientAssociationServiceException {
		remote.removeUser(2, 101);
		List<Integer> clients = remote.getClientsByUser(2);
		assertEquals("the removeUser are not properly implemented.", 2, clients
				.size());
		remote.removeUser(2, 12);
		clients = remote.getClientsByUser(2);
		assertEquals("the removeUser are not properly implemented.", 1, clients
				.size());
		assertTrue("the removeUser are not properly implemented.", clients
				.contains(Integer.valueOf(11)));
	}

}
