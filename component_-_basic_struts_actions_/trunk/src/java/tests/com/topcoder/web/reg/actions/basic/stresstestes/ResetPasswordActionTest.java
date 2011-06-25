/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.basic.stresstestes;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.NoDefaultConnectionException;
import com.topcoder.web.common.HibernateUtils;
import com.topcoder.web.common.TCRequest;
import com.topcoder.web.common.TCResponse;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.hibernate.AuditDAOHibernate;
import com.topcoder.web.common.dao.hibernate.PasswordRecoveryDAOHibernate;
import com.topcoder.web.common.security.SessionPersistor;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.reg.actions.basic.MockBasicAuthentication;
import com.topcoder.web.reg.actions.basic.MockPrincipalMgrRemote;
import com.topcoder.web.reg.actions.basic.ResetPasswordAction;

/**
 * Stress tests for <code>ResetPasswordAction</code>.
 * 
 * @author moon.river
 * @version 1.0
 */
public class ResetPasswordActionTest extends TestCase {

	/**
	 * The start of the user id.
	 */
	private static final long START = 50000000;
	/**
	 * test volume.
	 */
	private static final int N = 100;

	/**
	 * Instance to test.
	 */
	private ResetPasswordAction instance;

	/**
	 * <p>
	 * Set up the test environment.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit
	 */
	@Before
	public void setUp() throws NoDefaultConnectionException, Exception {

		instance = new ResetPasswordAction() {
			public String getText(String str) {
				return str;
			}
		};

		Connection conn = new DBConnectionFactoryImpl(
				"com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")
				.createConnection("TestingConnection");

		for (long i = START; i < START + N; i++) {
			PreparedStatement ps = conn
					.prepareStatement("insert into user (user_id, handle, status) values (?,?,?)");
			ps.setLong(1, i);
			ps.setString(2, "user" + i);
			ps.setString(3, "4");
			ps.execute();
			ps.close();

			ps = conn
					.prepareStatement("insert into password_recovery (password_recovery_id, user_id, recovery_address, expire_date, used_ind)"
							+ " values (?,?,?,?,0)");
			ps.setLong(1, i);
			ps.setLong(2, i);
			ps.setString(3, "test@topcoder.com");
			ps.setDate(4,
					new Date(new java.util.Date().getTime() + 1000 * 3600));
			ps.execute();
			ps.close();
		}

		conn.close();
	}

	/**
	 * <p>
	 * Tear down the test environment.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit
	 */
	@After
	public void tearDown() throws Exception {
		// delete the objects
		Connection conn = new DBConnectionFactoryImpl(
				"com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")
				.createConnection("TestingConnection");
		PreparedStatement ps;
		ps = conn
				.prepareStatement("delete from password_recovery where user_id >= "
						+ START);
		ps.execute();

		ps = conn
				.prepareStatement("delete from user where user_id >= " + START);
		ps.execute();
		ps.close();

		conn.close();
	}

	/**
	 * Test method for
	 * {@link com.topcoder.web.reg.actions.basic.ResetPasswordAction#execute()}.
	 * 
	 * @throws Exception
	 *             to JUnit
	 */
	@Test
	public void testExecute1() throws RemoteException, Exception {
		try {
			long start = System.currentTimeMillis();

			for (long i = START; i < START + N; i++) {
				AuditDAO auditDAO = new AuditDAOHibernate();
				instance.setAuditDAO(auditDAO);
				instance.setOperationType("op");
				HttpSession httpSession = EasyMock
						.createNiceMock(HttpSession.class);
				HttpServletRequest servletRequest = EasyMock
						.createNiceMock(HttpServletRequest.class);
				instance.setServletRequest(servletRequest);
				EasyMock.expect(servletRequest.getRemoteAddr()).andReturn(
						"localhost");
				EasyMock.expect(servletRequest.getSession()).andReturn(
						httpSession);

				TCRequest tcRequest = EasyMock.createNiceMock(TCRequest.class);
				TCResponse tcResponse = EasyMock
						.createNiceMock(TCResponse.class);
				SessionPersistor persistor = new SessionPersistor(httpSession);
				WebAuthentication webAuthentication = new MockBasicAuthentication(
						persistor, tcRequest, tcResponse);
				Map<String, Object> session = new HashMap<String, Object>();
				session.put("webAuthentication", webAuthentication);
				instance.setSession(session);

				PasswordRecoveryDAOHibernate passwordRecoveryDAO = new PasswordRecoveryDAOHibernate();
				instance.setPasswordRecoveryDAO(passwordRecoveryDAO);
				instance.setPasswordRecoveryId(i);
				instance.setMinimalPasswordLength(4);
				instance.setMaximalPasswordLength(8);
				instance.setPrincipalMgrRemote(new MockPrincipalMgrRemote());

				EasyMock.replay(httpSession, servletRequest, tcRequest,
						tcResponse);

				HibernateUtils.begin();

				instance.execute();

			}
			long end = System.currentTimeMillis();

			System.err.println("Reset password when there is " + N
					+ " users took : " + (end - start) + "ms");
		} finally {
			HibernateUtils.getSession().clear();
			HibernateUtils.close();
		}
	}

	/**
	 * Test method for
	 * {@link com.topcoder.web.reg.actions.basic.ResetPasswordAction#execute()}.
	 * 
	 * @throws Exception
	 *             to JUnit
	 */
	@Test
	public void testExecute2() throws RemoteException, Exception {

		// create another 9 * N users, so we have 10 * N
		Connection conn = new DBConnectionFactoryImpl(
				"com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")
				.createConnection("TestingConnection");

		for (long i = START + N; i < START + N * 9; i++) {
			PreparedStatement ps = conn
					.prepareStatement("insert into user (user_id, handle, status) values (?,?,?)");
			ps.setLong(1, i);
			ps.setString(2, "user" + i);
			ps.setString(3, "4");
			ps.execute();
			ps.close();
			ps = conn
					.prepareStatement("insert into password_recovery (password_recovery_id, user_id, recovery_address, expire_date, used_ind)"
							+ " values (?,?,?,?,0)");
			ps.setLong(1, i);
			ps.setLong(2, i);
			ps.setString(3, "test@topcoder.com");
			ps.setDate(4,
					new Date(new java.util.Date().getTime() + 1000 * 3600));
			ps.execute();
			ps.close();
		}

		conn.close();
		try {
			long start = System.currentTimeMillis();

			for (long i = START; i < START + N; i++) {
				AuditDAO auditDAO = new AuditDAOHibernate();
				instance.setAuditDAO(auditDAO);
				instance.setOperationType("op");
				HttpSession httpSession = EasyMock
						.createNiceMock(HttpSession.class);
				HttpServletRequest servletRequest = EasyMock
						.createNiceMock(HttpServletRequest.class);
				instance.setServletRequest(servletRequest);
				EasyMock.expect(servletRequest.getRemoteAddr()).andReturn(
						"localhost");
				EasyMock.expect(servletRequest.getSession()).andReturn(
						httpSession);

				TCRequest tcRequest = EasyMock.createNiceMock(TCRequest.class);
				TCResponse tcResponse = EasyMock
						.createNiceMock(TCResponse.class);
				SessionPersistor persistor = new SessionPersistor(httpSession);
				WebAuthentication webAuthentication = new MockBasicAuthentication(
						persistor, tcRequest, tcResponse);
				Map<String, Object> session = new HashMap<String, Object>();
				session.put("webAuthentication", webAuthentication);
				instance.setSession(session);

				PasswordRecoveryDAOHibernate passwordRecoveryDAO = new PasswordRecoveryDAOHibernate();
				instance.setPasswordRecoveryDAO(passwordRecoveryDAO);
				instance.setPasswordRecoveryId(i);
				instance.setMinimalPasswordLength(4);
				instance.setMaximalPasswordLength(8);
				instance.setPrincipalMgrRemote(new MockPrincipalMgrRemote());

				EasyMock.replay(httpSession, servletRequest, tcRequest,
						tcResponse);

				HibernateUtils.begin();

				instance.execute();
			}
			long end = System.currentTimeMillis();

			System.err.println("Reset password when there is " + 10 * N
					+ " users took : " + (end - start) + "ms");
		} finally {
			HibernateUtils.getSession().clear();
			HibernateUtils.close();
		}
	}

}
