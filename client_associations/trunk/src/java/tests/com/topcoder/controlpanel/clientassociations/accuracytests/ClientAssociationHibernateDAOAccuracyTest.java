/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations.accuracytests;

import java.util.List;

import junit.framework.TestCase;

import com.topcoder.controlpanel.clientassociations.dao.ClientAssociationDAOException;
import com.topcoder.controlpanel.clientassociations.dao.hibernate.ClientAssociationHibernateDAO;

/**
 * <p>
 * Accuracy test cases for all <code>ClientAssociationHibernateDAO</code>.
 * </p>
 *
 * @author restarter
 * @version 1.0
 */
public class ClientAssociationHibernateDAOAccuracyTest extends TestCase {

	/**
	 * <p>
	 * ClientAssociationHibernateDAO instance for testing.
	 * </p>
	 */
	private ClientAssociationHibernateDAO dao;

	/**
	 * <p>
	 * Sets up the testing environment.
	 * </p>
	 *
	 * @throws Exception
	 *             to jUnit.
	 */
	protected void setUp() throws Exception {
		dao = new ClientAssociationHibernateDAO();
		AccuracyTestHelper.insertData();
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
	 * Test for the constructor.
	 * </p>
	 *
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testClientAssociationHibernateDAO() {
		assertNotNull(
				"the ClientAssociationHibernateDAO instance initialization failed.",
				dao);
	}

	/**
	 * <p>
	 * Accuracy test for <code>getClientsByComponent(long componentId)</code>
	 * </p>
	 *
	 * @throws ClientAssociationDAOException
	 *             if error occurs
	 */
	public void testAssignComponent() throws ClientAssociationDAOException {
		dao.assignComponent(201, 21);
		dao.assignComponent(201, 22);
		List<Integer> clients = dao.getClientsByComponent(201);
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
	 * @throws ClientAssociationDAOException
	 *             if error occurs
	 */
	public void testAssignUser() throws ClientAssociationDAOException {
		dao.assignUser(101, 11, false);
		dao.assignUser(101, 12, true);
		assertFalse("the assignUser does not work properly", dao.isAdminUser(
				101, 11));
		assertTrue("the assignUser does not work properly", dao.isAdminUser(
				101, 12));
		List<Integer> clients = dao.getClientsByUser(101);
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
	 * @throws ClientAssociationDAOException
	 *             if error occurs
	 */
	public void testGetClientsByComponent()
			throws ClientAssociationDAOException {
		assertEquals("the clients' ids are not properly retrieved", 0, dao
				.getClientsByComponent(202).size());
		List<Integer> clients = dao.getClientsByComponent(2);
		assertEquals("the clients' ids are not properly retrieved", 2, clients
				.size());
		assertTrue("the clients' ids are not properly retrieved", clients
				.contains(Integer.valueOf(22)));
		assertTrue("the clients' ids are not properly retrieved", clients
				.contains(Integer.valueOf(21)));
		clients = dao.getClientsByComponent(3);
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
	 * @throws ClientAssociationDAOException
	 *             if error occurs
	 */
	public void testGetClientsByUser() throws ClientAssociationDAOException {
		assertEquals("the clients' ids are not properly retrieved", 0, dao
				.getClientsByUser(202).size());
		List<Integer> clients = dao.getClientsByUser(2);
		assertEquals("the clients' ids are not properly retrieved", 2, clients
				.size());
		assertTrue("the clients' ids are not properly retrieved", clients
				.contains(Integer.valueOf(12)));
		assertTrue("the clients' ids are not properly retrieved", clients
				.contains(Integer.valueOf(11)));
		clients = dao.getClientsByUser(3);
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
	 * @throws ClientAssociationDAOException
	 *             if error occurs
	 */
	public void testGetComponentsByClient()
			throws ClientAssociationDAOException {
		assertEquals("the components' ids are not properly retrieved", 0, dao
				.getComponentsByClient(11).size());
		List<Long> comps = dao.getComponentsByClient(22);
		assertEquals("the components' ids are not properly retrieved", 2, comps
				.size());
		assertTrue("the components' ids are not properly retrieved", comps
				.contains(Long.valueOf(2)));
		assertTrue("the components' ids are not properly retrieved", comps
				.contains(Long.valueOf(3)));
		comps = dao.getComponentsByClient(21);
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
	 * @throws ClientAssociationDAOException
	 *             if error occurs
	 */
	public void testGetComponentsByUser() throws ClientAssociationDAOException {
		List<Long> comps = dao.getComponentsByUser(3);
		assertEquals("the components' ids are not properly retrieved", 1, comps
				.size());
		assertTrue("the components' ids are not properly retrieved", comps
				.contains(Long.valueOf(4)));
		assertEquals("the components' ids are not properly retrieved", 1, dao
				.getComponentsByUser(2).size());
		assertEquals("the components' ids are not properly retrieved", 0, dao
				.getComponentsByUser(4).size());
	}

	/**
	 * <p>
	 * Accuracy test for <code>getUsers(int clientId)</code>
	 * </p>
	 *
	 * @throws ClientAssociationDAOException
	 *             if error occurs
	 */
	public void testGetUsers() throws ClientAssociationDAOException {
		List<Long> users = dao.getUsers(1002);
		assertEquals("the users' ids are not properly retrieved", 0, users
				.size());
		users = dao.getUsers(12);
		assertEquals("the users' ids are not properly retrieved", 2, users
				.size());
		assertTrue("the users' ids are not properly retrieved", users
				.contains(Long.valueOf(2)));
		assertTrue("the users' ids are not properly retrieved", users
				.contains(Long.valueOf(3)));
		users = dao.getUsers(11);
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
	 * @throws ClientAssociationDAOException
	 *             if error occurs
	 */
	public void testIsAdminUser() throws ClientAssociationDAOException {
		assertFalse("the isAdminUser(long, int) does not work properly.", dao
				.isAdminUser(2, 11));
		assertTrue("the isAdminUser(long, int) does not work properly.", dao
				.isAdminUser(2, 12));
		try {
			dao.isAdminUser(2001, 12);
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
	 * @throws ClientAssociationDAOException
	 *             if error occurs
	 */
	public void testRemoveComponent() throws ClientAssociationDAOException {
		dao.removeComponent(201, 22);
		List<Long> comps = dao.getComponentsByClient(22);
		assertEquals("the removeComponent are not properly implemented.", 2,
				comps.size());
		dao.removeComponent(2, 22);
		comps = dao.getComponentsByClient(22);
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
	 * @throws ClientAssociationDAOException
	 *             if error occurs
	 */
	public void testRemoveUser() throws ClientAssociationDAOException {
		dao.removeUser(2, 101);
		List<Integer> clients = dao.getClientsByUser(2);
		assertEquals("the removeUser are not properly implemented.", 2, clients
				.size());
		dao.removeUser(2, 12);
		clients = dao.getClientsByUser(2);
		assertEquals("the removeUser are not properly implemented.", 1, clients
				.size());
		assertTrue("the removeUser are not properly implemented.", clients
				.contains(Integer.valueOf(11)));
	}

}
