/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.easymock.EasyMock;

import com.topcoder.web.common.HibernateUtils;
import com.topcoder.web.common.dao.hibernate.AuditDAOHibernate;
import com.topcoder.web.common.dao.hibernate.UserDAOHibernate;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.reg.actions.miscellaneous.mock.MockWebAuthentication;

/**
 * <p>
 * A helper class for testing.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class TestHelper {

    /**
     * A string providing the operation type for testing.
     */
    public static final String OPERATION_TYPE = "operationType";

    /**
     * String representing a username.
     */
    public static final String USER = "dok_tester";

    /**
     * Database connection url.
     */
    private static final String URL = "jdbc:informix-sqli://127.0.0.1:9090/informixoltp:INFORMIXSERVER=ol_kalc";

    /**
     * Username to be used for database connection.
     */
    private static final String USER_NAME = "informix";

    /**
     * Password to be used for database connection.
     */
    private static final String PASSWORD = "Password@2";

    /**
     * Sql to be used for deleting the inserted records during testing.
     */
    private static final String DELETE_SQL = "delete from audit_record";

    /**
     * Private constructor to prevent instantiation.
     */
    private TestHelper() {
        // to prevent instantiation
    }

    /**
     * A helper method to get a database connection.
     * @return A Connection instance
     * @throws Exception
     *             to JUnit
     */
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER_NAME, PASSWORD);
    }

    /**
     * Helper method to close connection.
     * @param conn
     *            the connection to be closed
     * @throws Exception
     *             to JUnit
     */
    public static void closeConnection(Connection conn) throws Exception {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    /**
     * Helper method to get a mock instance of HttpServletRequest.
     * @return an instance of HttpServletRequest created
     */
    public static HttpServletRequest getHttpServletRequest() {
        HttpServletRequest servletRequest = EasyMock.createNiceMock(HttpServletRequest.class);
        EasyMock.expect(servletRequest.getRemoteAddr()).andReturn("localhost").anyTimes();
        EasyMock.replay(servletRequest);
        return servletRequest;
    }

    /**
     * Helper method to mock the session.
     * @return a map representing the session
     */
    public static Map < String, Object > getSession() {
        Map < String, Object > session = new HashMap < String, Object >();
        WebAuthentication wa = new MockWebAuthentication();
        session.put("webAuthentication", wa);

        return session;
    }

    /**
     * Helper method to commit any hibernate transactions and close the connection.
     */
    public static void closeHibernateSession() {
        HibernateUtils.getSession().flush();
        HibernateUtils.commit();
        HibernateUtils.close();
    }

    /**
     * Helper method which deletes inserted records during test from database.
     * @throws Exception
     *             to JUnit
     */
    public static void clearDatabase() throws Exception {
        Connection conn = getConnection();
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(DELETE_SQL);
        } finally {
            closeConnection(conn);
        }
    }

    /**
     * Helper method to initialize all the fields in a BaseAction instance.
     * @param instance
     *            the instance whose fields are to be initialized
     * @return the BaseAction instance will all fields populated
     */
    public static BaseAction setFieldsInBaseAction(BaseAction instance) {
        instance.setUserDAO(new UserDAOHibernate());
        instance.setAuditDAO(new AuditDAOHibernate());
        instance.setSession(TestHelper.getSession());
        instance.setServletRequest(TestHelper.getHttpServletRequest());
        instance.setOperationType(TestHelper.OPERATION_TYPE);

        return instance;
    }
}
