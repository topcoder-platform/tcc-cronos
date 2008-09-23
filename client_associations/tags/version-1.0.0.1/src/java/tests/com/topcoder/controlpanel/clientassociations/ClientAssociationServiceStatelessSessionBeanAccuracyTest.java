/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations;

import java.lang.reflect.Field;
import java.util.List;

import junit.framework.TestCase;

import com.topcoder.controlpanel.clientassociations.accuracytests.AccuracyTestHelper;

/**
 * <p>
 * Accuracy test cases for
 * <code>ClientAssociationServiceStatelessSessionBean</code>.
 * </p>
 *
 * @author restarter
 * @version 1.0
 */
public class ClientAssociationServiceStatelessSessionBeanAccuracyTest extends
		TestCase {

	/**
	 * <p>
	 * The ClientAssociationServiceStatelessSessionBean instance used for
	 * testing.
	 * </p>
	 */
	private ClientAssociationServiceStatelessSessionBean bean;

	/**
	 * <p>
	 * Sets up the testing environment.
	 * </p>
	 *
	 * @throws Exception
	 *             to jUnit.
	 */
	protected void setUp() throws Exception {
		AccuracyTestHelper.insertData();
		bean = new ClientAssociationServiceStatelessSessionBean();
		bean.initialize();
	}

	/**
	 * <p>
	 * Tears down the test environment.
	 * </p>
	 *
	 * @throws Exception
	 *             to jUnit.
	 */
	protected void tearDown() throws Exception {
		AccuracyTestHelper.removeData();
	}

	/**
	 * <p>
	 * Accuracy test for the constructor.
	 * </p>
	 */
	public void testClientAssociationServiceStatelessSessionBean() {
		assertNotNull(
				"The ClientAssociationServiceStatelessSessionBean instance initialization failed.",
				bean);
	}

	/**
	 * <p>
	 * Accuracy test for <code>initialize()</code>.
	 * </p>
	 *
	 * @throws Exception
	 *             to JUnit
	 */
	public void testInitialize() throws Exception {
		assertNotNull("The bean is not initialized properly.", getField(bean,
				"dao"));
	}

	/**
	 * <p>
	 * Accuracy test for <code>cleanUp()</code>.
	 * </p>
	 *
	 * @throws Exception
	 *             to JUnit
	 */
	public void testCleanUp() throws Exception {
		bean.cleanUp();
		assertNull("The bean is not initialized properly.", getField(bean,
				"dao"));
	}

	/**
	 * <p>
	 * Retrieves the field defined by <code>fieldName</code> in Object
	 * <code>bean</code>.
	 * </p>
	 *
	 * @param bean
	 *            the object to retrieve the field
	 * @param fieldName
	 *            the name of the field
	 * @return the value of the field
	 * @throws Exception
	 *             if any error occurs
	 */
	private Object getField(ClientAssociationServiceStatelessSessionBean bean,
			String fieldName) throws Exception {
		Field field = bean.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		return field.get(bean);
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
		bean.assignComponent(201, 21);
		bean.assignComponent(201, 22);
		List<Integer> clients = bean.getClientsByComponent(201);
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
		bean.assignUser(101, 11, false);
		bean.assignUser(101, 12, true);
		assertFalse("the assignUser does not work properly", bean.isAdminUser(
				101, 11));
		assertTrue("the assignUser does not work properly", bean.isAdminUser(
				101, 12));
		List<Integer> clients = bean.getClientsByUser(101);
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
		assertEquals("the clients' ids are not properly retrieved", 0, bean
				.getClientsByComponent(202).size());
		List<Integer> clients = bean.getClientsByComponent(2);
		assertEquals("the clients' ids are not properly retrieved", 2, clients
				.size());
		assertTrue("the clients' ids are not properly retrieved", clients
				.contains(Integer.valueOf(22)));
		assertTrue("the clients' ids are not properly retrieved", clients
				.contains(Integer.valueOf(21)));
		clients = bean.getClientsByComponent(3);
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
		assertEquals("the clients' ids are not properly retrieved", 0, bean
				.getClientsByUser(202).size());
		List<Integer> clients = bean.getClientsByUser(2);
		assertEquals("the clients' ids are not properly retrieved", 2, clients
				.size());
		assertTrue("the clients' ids are not properly retrieved", clients
				.contains(Integer.valueOf(12)));
		assertTrue("the clients' ids are not properly retrieved", clients
				.contains(Integer.valueOf(11)));
		clients = bean.getClientsByUser(3);
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
		assertEquals("the components' ids are not properly retrieved", 0, bean
				.getComponentsByClient(11).size());
		List<Long> comps = bean.getComponentsByClient(22);
		assertEquals("the components' ids are not properly retrieved", 2, comps
				.size());
		assertTrue("the components' ids are not properly retrieved", comps
				.contains(Long.valueOf(2)));
		assertTrue("the components' ids are not properly retrieved", comps
				.contains(Long.valueOf(3)));
		comps = bean.getComponentsByClient(21);
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
		List<Long> comps = bean.getComponentsByUser(3);
		assertEquals("the components' ids are not properly retrieved", 1, comps
				.size());
		assertTrue("the components' ids are not properly retrieved", comps
				.contains(Long.valueOf(4)));
		assertEquals("the components' ids are not properly retrieved", 1, bean
				.getComponentsByUser(2).size());
		assertEquals("the components' ids are not properly retrieved", 0, bean
				.getComponentsByUser(4).size());
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
		List<Long> users = bean.getUsers(1002);
		assertEquals("the users' ids are not properly retrieved", 0, users
				.size());
		users = bean.getUsers(12);
		assertEquals("the users' ids are not properly retrieved", 2, users
				.size());
		assertTrue("the users' ids are not properly retrieved", users
				.contains(Long.valueOf(2)));
		assertTrue("the users' ids are not properly retrieved", users
				.contains(Long.valueOf(3)));
		users = bean.getUsers(11);
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
		assertFalse("the isAdminUser(long, int) does not work properly.", bean
				.isAdminUser(2, 11));
		assertTrue("the isAdminUser(long, int) does not work properly.", bean
				.isAdminUser(2, 12));
		try {
			bean.isAdminUser(2001, 12);
			fail("the isAdminUser(long, int) does not work properly");
		} catch (ClientAssociationServiceException e) {
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
		bean.removeComponent(201, 22);
		List<Long> comps = bean.getComponentsByClient(22);
		assertEquals("the removeComponent are not properly implemented.", 2,
				comps.size());
		bean.removeComponent(2, 22);
		comps = bean.getComponentsByClient(22);
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
		bean.removeUser(2, 101);
		List<Integer> clients = bean.getClientsByUser(2);
		assertEquals("the removeUser are not properly implemented.", 2, clients
				.size());
		bean.removeUser(2, 12);
		clients = bean.getClientsByUser(2);
		assertEquals("the removeUser are not properly implemented.", 1, clients
				.size());
		assertTrue("the removeUser are not properly implemented.", clients
				.contains(Integer.valueOf(11)));
	}
}
