/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.services.failuretests.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * <p>
 * The base class for failure test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@ContextConfiguration(locations = { "classpath:failure/beans.xml" })
public class BaseFailureTests extends AbstractTransactionalJUnit4SpringContextTests {
    /**
     * <p>
     * Represents the valid instance of ApplicationContext for failure test.
     * </p>
     */
    public static final ApplicationContext APP_CONTEXT;

    /**
     * <p>
     * Represents the invalid instance of ApplicationContext for failure test.
     * </p>
     */
    public static final ApplicationContext APP_CONTEXT_INVALID;

    /**
     * <p>
     * Represents the instance of Session for failure test.
     * </p>
     */
    public static Session session;

    /**
     * <p>
     * Initialization.
     * </p>
     */
    static {
        APP_CONTEXT = new ClassPathXmlApplicationContext("failure/beans.xml");
        APP_CONTEXT_INVALID = new ClassPathXmlApplicationContext("failure/beans_invalid.xml");
    }

    /**
     * <p>
     * Sets up for each test.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Before
    @Transactional
    public void setUp() throws Exception {
        prepareDB();
    }

    /**
     * <p>
     * Tears down for each test.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @After
    public void tearDown() throws Exception {
        clearDB();
    }

    /**
     * <p>
     * Clears test data.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Transactional
    private void clearDB() throws Exception {
        SessionFactory sessionFactory = (SessionFactory) APP_CONTEXT.getBean("sessionFactory");
        session = sessionFactory.openSession();
        session.createSQLQuery("DELETE FROM original_payment").executeUpdate();
        session.createSQLQuery("DELETE FROM member_contribution_points").executeUpdate();
        session.createSQLQuery("DELETE FROM project_contest_cp_config").executeUpdate();
        session.createSQLQuery("DELETE FROM direct_project_cp_config").executeUpdate();
        session.createSQLQuery("DELETE FROM project").executeUpdate();
        session.createSQLQuery("DELETE FROM tc_direct_project").executeUpdate();
        
        session.close();
    }

    /**
     * <p>
     * Clears test data.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Transactional
    private void prepareDB() throws Exception {
        SessionFactory sessionFactory = (SessionFactory) APP_CONTEXT.getBean("sessionFactory");
        session = sessionFactory.openSession();
        session.createSQLQuery("DELETE FROM original_payment").executeUpdate();
        session.createSQLQuery("DELETE FROM member_contribution_points").executeUpdate();
        session.createSQLQuery("DELETE FROM project_contest_cp_config").executeUpdate();
        session.createSQLQuery("DELETE FROM direct_project_cp_config").executeUpdate();
        session.createSQLQuery("DELETE FROM project").executeUpdate();
        session.createSQLQuery("DELETE FROM tc_direct_project").executeUpdate();
        session.createSQLQuery("INSERT INTO project(project_id, project_status_id,project_category_id," +
            "create_user,create_date, modify_user,modify_date,tc_direct_project_id)" +
            " VALUES(1, 1, 1, 'admin', CURRENT, 'admin', CURRENT, 1)").executeUpdate();
        session.close();
    }
}