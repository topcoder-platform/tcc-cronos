/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.persistence.informix;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.cronos.onlinereview.autoscreening.tool.DAOException;
import com.cronos.onlinereview.autoscreening.tool.ScreeningResponse;
import com.cronos.onlinereview.autoscreening.tool.ScreeningResponseDAO;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

/**
 * Unit tests for <code>InformixResponseDAOTest</code>.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class InformixResponseDAOTest extends DbTestCase {
    /**
     * Represents the name of the id generator.
     */
    private static final String ID_GENERATOR_NAME = "screening_result_id_seq";

    /**
     * Represents an instance of InformixResponseDAO, whose methods are going to
     * be tested.
     */
    private InformixResponseDAO instance;

    /**
     * Represents an instance of IDGenerator, used to create an instance of
     * InformixResponseDAO.
     */
    private IDGenerator idGenerator;

    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(InformixResponseDAOTest.class);
    }

    /**
     * Sets up the test environment.
     * @throws Exception
     *             throw any exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        idGenerator = IDGeneratorFactory.getIDGenerator(ID_GENERATOR_NAME);
        instance = new InformixResponseDAO(getConnectionFactory(), idGenerator);
    }

    /**
     * Clean up the test environment.
     * @throws Exception
     *             throw any exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>InformixResponseDAO(DBConnectionFactory connectionFactory,
     * IDGenerator idGenerator)</code>.
     * </p>
     * <p>
     * An instance of InformixResponseDAO should be created successfully.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtorA1() throws Exception {
        ScreeningResponseDAO responseDAO = new InformixResponseDAO(getConnectionFactory(),
            idGenerator);
        assertNotNull("check the instance", responseDAO);
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>InformixResponseDAO(DBConnectionFactory connectionFactory,
     * IDGenerator idGenerator)</code>.
     * </p>
     * <p>
     * connectionFactory is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCtorA1() throws Exception {

        try {
            new InformixResponseDAO(null, idGenerator);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("check error message", "connectionFactory should not be null.", e
                .getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>InformixResponseDAO(DBConnectionFactory connectionFactory,
     * IDGenerator idGenerator)</code>.
     * </p>
     * <p>
     * idGenerator is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCtorA2() throws Exception {

        try {
            new InformixResponseDAO(getConnectionFactory(), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("check error message", "idGenerator should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>InformixResponseDAO(DBConnectionFactory connectionFactory,
     * String connectionName, IDGenerator idGenerator)</code>.
     * </p>
     * <p>
     * An instance of InformixResponseDAO should be created successfully.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtorB1() throws Exception {
        ScreeningResponseDAO responseDAO = new InformixResponseDAO(getConnectionFactory(),
            CONNECTION_NAME, idGenerator);
        assertNotNull("check the instance", responseDAO);
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>InformixResponseDAO(DBConnectionFactory connectionFactory,
     * String connectionName, IDGenerator idGenerator)</code>.
     * </p>
     * <p>
     * An instance of InformixResponseDAO should be created successfully.
     * connectionName can be null.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtorB2() throws Exception {
        ScreeningResponseDAO responseDAO = new InformixResponseDAO(getConnectionFactory(), null,
            idGenerator);
        assertNotNull("check the instance", responseDAO);
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>InformixResponseDAO(DBConnectionFactory connectionFactory,
     * String connectionName, IDGenerator idGenerator)</code>.
     * </p>
     * <p>
     * connectionFactory is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCtorB1() throws Exception {

        try {
            new InformixResponseDAO(null, CONNECTION_NAME, idGenerator);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("check error message", "connectionFactory should not be null.", e
                .getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>InformixResponseDAO(DBConnectionFactory connectionFactory,
     * String connectionName, IDGenerator idGenerator)</code>.
     * </p>
     * <p>
     * connectionName is empty. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCtorB2() throws Exception {

        try {
            new InformixResponseDAO(getConnectionFactory(), "   ", idGenerator);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("check error message", "connectionName should not be empty (trimmed).", e
                .getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>InformixResponseDAO(DBConnectionFactory connectionFactory,
     * String connectionName, IDGenerator idGenerator)</code>.
     * </p>
     * <p>
     * idGenerator is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCtorB3() throws Exception {

        try {
            new InformixResponseDAO(getConnectionFactory(), CONNECTION_NAME, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("check error message", "idGenerator should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>void createScreeningResponse(ScreeningResponse screeningResponse)</code>.
     * </p>
     * <p>
     * The screeningResponse should be persisted correctly. the id of the
     * screeningResponse is not set. idGenerator should be used.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCreateScreeningResponse1() throws Exception {
        ScreeningResponse screeningResponse = new ScreeningResponse();

        screeningResponse.setScreeningTaskId(1);
        screeningResponse.setResponseCodeId(1);
        screeningResponse.setDetailMessage("detail message1");

        screeningResponse.setCreateUser("creator1");
        screeningResponse.setCreateDate(new Date());
        screeningResponse.setModificationUser("modifier1");
        screeningResponse.setModificationDate(new Date());

        instance.createScreeningResponse(screeningResponse);

        assertEquals("check response id", 1, screeningResponse.getId());
        assertPersistence("check response is persisted in the database.", screeningResponse, true);
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>void createScreeningResponse(ScreeningResponse screeningResponse)</code>.
     * </p>
     * <p>
     * The screeningResponse should be persisted correctly. the id of the
     * screeningResponse is already set. idGenerator should NOT be used.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCreateScreeningResponse2() throws Exception {
        ScreeningResponse screeningResponse = new ScreeningResponse();

        screeningResponse.setId(10);
        screeningResponse.setScreeningTaskId(1);
        screeningResponse.setResponseCodeId(1);
        screeningResponse.setDetailMessage("detail message1");

        screeningResponse.setCreateUser("creator1");
        screeningResponse.setCreateDate(new Date());
        screeningResponse.setModificationUser("modifier1");
        screeningResponse.setModificationDate(new Date());

        instance.createScreeningResponse(screeningResponse);

        assertEquals("check response id", 10, screeningResponse.getId());
        assertPersistence("check response is persisted in the database.", screeningResponse, true);
    }

    /**
     * <p>
     * Failure test of the method
     * <code>void createScreeningResponse(ScreeningResponse screeningResponse)</code>.
     * </p>
     * <p>
     * screeningResponse is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateScreeningResponse1() throws Exception {
        try {
            instance.createScreeningResponse(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("check error message", "screeningResponse should not be null.", e
                .getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>void createScreeningResponse(ScreeningResponse screeningResponse)</code>.
     * </p>
     * <p>
     * unable to generate next id. DAOException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateScreeningResponse2() throws Exception {
        ScreeningResponse screeningResponse = new ScreeningResponse();

        screeningResponse.setScreeningTaskId(1);
        screeningResponse.setResponseCodeId(1);
        screeningResponse.setDetailMessage("detail message1");

        screeningResponse.setCreateUser("creator1");
        screeningResponse.setCreateDate(new Date());
        screeningResponse.setModificationUser("modifier1");
        screeningResponse.setModificationDate(new Date());

        exhaustIDGenerator("screening_result_id_seq");

        try {
            instance.createScreeningResponse(screeningResponse);
            fail("DAOException should be thrown.");
        } catch (DAOException e) {
            assertTrue("check error message", e.getMessage().startsWith(
                "Unable to generate id for the screening response."));
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>void createScreeningResponse(ScreeningResponse screeningResponse)</code>.
     * </p>
     * <p>
     * some fields are not set in screeningResponse. DAOException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateScreeningResponse3() throws Exception {
        ScreeningResponse screeningResponse = new ScreeningResponse();

        screeningResponse.setId(10);
        // screeningTaskId is not set
        screeningResponse.setResponseCodeId(1);
        screeningResponse.setDetailMessage("detail message1");

        screeningResponse.setCreateUser("creator1");
        screeningResponse.setCreateDate(new Date());
        screeningResponse.setModificationUser("modifier1");
        screeningResponse.setModificationDate(new Date());

        exhaustIDGenerator("screening_result_id_seq");

        try {
            instance.createScreeningResponse(screeningResponse);
            fail("DAOException should be thrown.");
        } catch (DAOException e) {
            // ok.
        }
    }

    /**
     * <p>
     * Verify the ScreeningResponse is in database.
     * </p>
     * @param msg
     *            the message about the assertion
     * @param screeningResponse
     *            the screeningResponse to check.
     * @param isExisted
     *            the screeningResponse should be existed or not.
     * @throws Exception
     *             if any error occurs
     */
    private void assertPersistence(String msg, ScreeningResponse screeningResponse,
        boolean isExisted) throws Exception {

        String sql = "select * from screening_result where screening_result_id="
            + screeningResponse.getId();

        // gets the connection
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (!isExisted) {
                assertFalse(msg, rs.next());
            } else {
                assertTrue(msg, rs.next());

                assertEquals(msg, screeningResponse.getScreeningTaskId(), rs
                    .getLong("screening_task_id"));
                assertEquals(msg, screeningResponse.getResponseCodeId(), rs
                    .getLong("screening_response_id"));
                assertEquals(msg, screeningResponse.getDetailMessage(), rs
                    .getString("dynamic_response_text"));

                assertEquals(msg, screeningResponse.getCreateUser(), rs.getString("create_user"));
                assertEquals(msg, screeningResponse.getModificationUser(), rs
                    .getString("modify_user"));
                assertEquals(msg, screeningResponse.getCreateDate(), new Date(rs.getTimestamp(
                    "create_date").getTime()));
                assertEquals(msg, screeningResponse.getModificationDate(), new Date(rs
                    .getTimestamp("modify_date").getTime()));
            }
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
            closeConnection(conn);
        }
    }

    /**
     * Make the specified id generator exhausted.
     * @param idName
     *            the name of the id generator
     * @throws Exception
     *             if any error occurs
     */
    private void exhaustIDGenerator(String idName) throws Exception {

        String sql = "UPDATE id_sequences SET exhausted=1 WHERE name=?";
        doSQLUpdate(sql, new Object[] {idName});
    }
}