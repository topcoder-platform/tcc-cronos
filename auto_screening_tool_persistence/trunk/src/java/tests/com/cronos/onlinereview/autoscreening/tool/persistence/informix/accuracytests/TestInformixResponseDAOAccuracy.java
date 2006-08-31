/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.persistence.informix.accuracytests;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import com.cronos.onlinereview.autoscreening.tool.ScreeningResponse;
import com.cronos.onlinereview.autoscreening.tool.persistence.informix.InformixResponseDAO;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

import junit.framework.TestCase;

/**
 * Accuracy test for class <code>InformixResponseDAO </code>.
 *
 * @author Chenhong
 * @version 1.0
 *
 */
public class TestInformixResponseDAOAccuracy extends TestCase {
    /**
     * Represents the IDGenerator instance for test.
     */
    private IDGenerator idGen = null;

    /**
     * Represents the InformixResponseDAO instance for test.
     */
    private InformixResponseDAO dao = null;

    /**
     * Represents the DBConnectionFactory instance for test.
     */
    private DBConnectionFactory factory = null;

    /**
     * Set up the environment.
     *
     * @throws Exception
     *             to junit.
     */
    public void setUp() throws Exception {
        DBUtil.clearConfigManager();

        ConfigManager cm = ConfigManager.getInstance();
        cm.add("accuracytests/DBConnectionFactory.xml");

        DBUtil.prepareDate();

        idGen = IDGeneratorFactory.getIDGenerator("response_id_seq");
        factory = new DBConnectionFactoryImpl(DBUtil.DB_FACTORY_NAMESPACE);
        dao = new InformixResponseDAO(factory, idGen);

    }

    /**
     * Clear all table and namespaces in the config manager.
     *
     * @throws Exception
     *             to junit.
     */
    public void tearDown() throws Exception {
        DBUtil.clearTables();

        DBUtil.clearConfigManager();
    }

    /**
     * Test ctor <code>InformixResponseDAO(DBConnectionFactory connectionFactory, IDGenerator idGenerator)</code>.
     */
    public void testInformixResponseDAODBConnectionFactoryIDGenerator() {
        assertNotNull("InformixResponseDAO instance should be created.", dao);
    }

    /**
     * Test ctor <code>InformixResponseDAO(DBConnectionFactory connectionFactory, String connectionName,
     *  IDGenerator idGenerator) </code>
     *
     */
    public void testInformixResponseDAODBConnectionFactoryStringIDGenerator_1() {
        dao = new InformixResponseDAO(factory, null, idGen);
        assertNotNull("InformixResponseDAO instance should be created.", dao);
    }

    /**
     * Test ctor <code>InformixResponseDAO(DBConnectionFactory connectionFactory, String connectionName,
     *  IDGenerator idGenerator) </code>
     *
     */
    public void testInformixResponseDAODBConnectionFactoryStringIDGenerator_2() {
        dao = new InformixResponseDAO(factory, "informix_connection", idGen);
        assertNotNull("InformixResponseDAO instance should be created.", dao);
    }

    /**
     * Test method <code>void createScreeningResponse(ScreeningResponse screeningResponse) </code>.
     *
     * <p>
     * In this test case, the id of ScreeningResponse is not set.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testCreateScreeningResponse_1() throws Exception {
        ScreeningResponse response = new ScreeningResponse();
        response.setCreateDate(new Date());
        response.setCreateUser("topcoder");
        response.setDetailMessage("accuracytests");
        response.setModificationDate(new Date());
        response.setModificationUser("reviewer");
        response.setScreeningTaskId(1);
        response.setResponseCodeId(1);

        dao.createScreeningResponse(response);

        ResultSet rs = null;
        Connection connection = null;
        Statement st = null;

        try {

            connection = factory.createConnection();
            st = connection.createStatement();
            rs = st.executeQuery("select * from screening_result");
            int index = 0;
            while (rs.next()) {
                assertEquals("The screening_task_id should be 1", "1", rs.getString(2));
                assertEquals("The screening_response_id should be 1", "1", rs.getString(3));
                assertEquals("The dynamic_response_text should be 'accuracytests'", "accuracytests", rs.getString(4));
                assertEquals("The create_user should be 'topcoder'", "topcoder", rs.getString(5));
                assertEquals("The modify_user should be 'reviewer'", "reviewer", rs.getString(7));

                index++;
            }

            assertEquals("should be only one record created.", 1, index);
        } finally {
            DBUtil.closeConnection(connection);
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(st);
        }
    }

    /**
     * Test method <code>void createScreeningResponse(ScreeningResponse screeningResponse) </code>.
     *
     * <p>
     * In this test case, the id of ScreeningResponse is not set.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testCreateScreeningResponse_2() throws Exception {
        ScreeningResponse response = new ScreeningResponse();
        response.setCreateDate(new Timestamp(System.currentTimeMillis()));
        response.setCreateUser("topcoder");
        response.setDetailMessage("accuracytests");
        response.setModificationDate(new Timestamp(System.currentTimeMillis()));
        response.setModificationUser("reviewer");
        response.setScreeningTaskId(1);
        response.setResponseCodeId(1);

        dao.createScreeningResponse(response);

        ResultSet rs = null;
        Connection connection = null;
        Statement st = null;

        try {

            connection = factory.createConnection();
            st = connection.createStatement();
            rs = st.executeQuery("select * from screening_result");
            int index = 0;
            while (rs.next()) {
                assertEquals("The screening_task_id should be 1", "1", rs.getString(2));
                assertEquals("The screening_response_id should be 1", "1", rs.getString(3));
                assertEquals("The dynamic_response_text should be 'accuracytests'", "accuracytests", rs.getString(4));
                assertEquals("The create_user should be 'topcoder'", "topcoder", rs.getString(5));
                assertEquals("The modify_user should be 'reviewer'", "reviewer", rs.getString(7));

                index++;
            }

            assertEquals("should be only one record created.", 1, index);
        } finally {
            DBUtil.closeConnection(connection);
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(st);
        }
    }

    /**
     * Test method <code>void createScreeningResponse(ScreeningResponse screeningResponse) </code>.
     *
     * <p>
     * In this test case, the id of ScreeningResponse is set.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testCreateScreeningResponse_3() throws Exception {
        ScreeningResponse response = new ScreeningResponse();
        response.setId(3);
        response.setCreateDate(new Timestamp(System.currentTimeMillis()));
        response.setCreateUser("topcoder");
        response.setDetailMessage("accuracytests");
        response.setModificationDate(new Timestamp(System.currentTimeMillis()));
        response.setModificationUser("reviewer");
        response.setScreeningTaskId(1);
        response.setResponseCodeId(1);

        dao.createScreeningResponse(response);

        ResultSet rs = null;
        Connection connection = null;
        Statement st = null;

        try {
            connection = factory.createConnection();
            st = connection.createStatement();
            rs = st.executeQuery("select * from screening_result");
            int index = 0;
            while (rs.next()) {
                index++;
            }

            assertEquals("should be only one record created.", 1, index);
        } finally {
            DBUtil.closeConnection(connection);
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(st);
        }
    }
}