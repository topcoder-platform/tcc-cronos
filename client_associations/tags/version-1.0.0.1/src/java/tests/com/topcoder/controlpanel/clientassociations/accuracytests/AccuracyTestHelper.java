/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations.accuracytests;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * <p>
 * The help class used for the accuracy test.
 * </p>
 *
 * @author restarter
 * @version 1.0
 */
public class AccuracyTestHelper {

	/**
	 * <p>
	 * Hibernate session for testing purposes.
	 * </p>
	 */
	private static SessionFactory sessionFactory;

	/**
	 * <p>
	 * Initializes the sessionFactory.
	 * </p>
	 *
	 * @throws ExceptionInInitializerError
	 *             if any error occurs
	 */
	static {
		try {
			sessionFactory = new Configuration().configure()
					.buildSessionFactory();
		} catch (HibernateException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	/**
	 * <p>
	 * Constructor.
	 * </p>
	 */
	private AccuracyTestHelper() {
	}

	/**
	 * <p>
	 * Retrieves the SessionFactory.
	 * </p>
	 *
	 * @return the sessionFactory
	 */
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			sessionFactory = new Configuration().configure()
					.buildSessionFactory();
		}
		return sessionFactory;
	}

	/**
	 * <p>
	 * Inserts necessary data into the database for testing.
	 * </p>
	 */
	public static void insertData() {
		removeData();
		Session session = sessionFactory.openSession();

		try {
			session.createSQLQuery(
					"insert into client values(11, 'user client1')")
					.executeUpdate();
			session.createSQLQuery(
					"insert into client values(12, 'user client2')")
					.executeUpdate();
			session.createSQLQuery(
					"insert into client values(21, 'comp client1')")
					.executeUpdate();
			session.createSQLQuery(
					"insert into client values(22, 'comp client2')")
					.executeUpdate();
			session.createSQLQuery("insert into user_client values(2, 11, 0)")
					.executeUpdate();
			session.createSQLQuery("insert into user_client values(2, 12, 1)")
					.executeUpdate();
			session.createSQLQuery("insert into user_client values(3, 12, 1)")
					.executeUpdate();
			session.createSQLQuery("insert into comp_client values(2, 21)")
					.executeUpdate();
			session.createSQLQuery("insert into comp_client values(2, 22)")
					.executeUpdate();
			session.createSQLQuery("insert into comp_client values(3, 22)")
					.executeUpdate();
			session.createSQLQuery("insert into comp_client values(4, 12)")
					.executeUpdate();
		} finally {
			session.close();
		}
	}

	/**
	 * <p>
	 * Deletes all the data in the database.
	 * </p>
	 */
	public static void removeData() {
		Session session = sessionFactory.openSession();
		try {
			session.createSQLQuery("delete from user_client").executeUpdate();
			session.createSQLQuery("delete from comp_client").executeUpdate();
			session.createSQLQuery("delete from client").executeUpdate();
		} finally {
			session.close();
		}
	}
}
