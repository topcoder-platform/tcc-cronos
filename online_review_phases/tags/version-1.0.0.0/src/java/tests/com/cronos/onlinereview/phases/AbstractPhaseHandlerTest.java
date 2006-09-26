/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;
import com.topcoder.util.config.ConfigManager;

/**
 * All test cases for AbstractPhaseHandler class.
 *
 * @author bose_java
 * @version 1.0
 */
public class AbstractPhaseHandlerTest extends BaseTest {

    /**
     * sets up the environment required for test cases for this class.
     *
     * @throws Exception not under test.
     */
    protected void setUp() throws Exception {
        super.setUp();

        ConfigManager configManager = ConfigManager.getInstance();

        configManager.add(PHASE_HANDLER_CONFIG_FILE);

        configManager.add(MANAGER_HELPER_CONFIG_FILE);

        //add the component configurations as well
        for (int i = 0; i < COMPONENT_FILE_NAMES.length; i++) {
            configManager.add(COMPONENT_FILE_NAMES[i]);
        }

    }

    /**
     * cleans up the environment required for test cases for this class.
     *
     * @throws Exception not under test.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Tests the constructor AbstractPhaseHandler(String) will null argument.
     *
     * @throws ConfigurationException not under test.
     */
    public void testAbstractPhaseHandlerWithNull() throws ConfigurationException {
        try {
            new AbstractPhaseHandlerSubClass(null);
            fail("AbstractPhaseHandler(String) did not throw IllegalArgumentException for null argument.");
        } catch (IllegalArgumentException ex) {
            //expected.
        }
    }

    /**
     * Tests the constructor AbstractPhaseHandler(String) will empty argument.
     *
     * @throws ConfigurationException not under test.
     */
    public void testAbstractPhaseHandlerWithEmpty() throws ConfigurationException {
        try {
            new AbstractPhaseHandlerSubClass("  ");
            fail("AbstractPhaseHandler(String) did not throw IllegalArgumentException for empty argument.");
        } catch (IllegalArgumentException ex) {
            //expected.
        }
    }

    /**
     * Tests the constructor AbstractPhaseHandler(String) will valid argument.
     *
     * @throws ConfigurationException not under test.
     */
    public void testAbstractPhaseHandlerWithValid() throws ConfigurationException {
        new AbstractPhaseHandlerSubClass(ABSTRACT_HANDLER_NAMESPACE);
    }

    /**
     * Tests the AbstractPhaseHandler.sendEmail(Phase) method with null.
     *
     * @throws PhaseHandlingException not under test.
     * @throws ConfigurationException not under test.
     */
    public void testSendEmail() throws PhaseHandlingException, ConfigurationException {
        AbstractPhaseHandler handler = new AbstractPhaseHandlerSubClass(ABSTRACT_HANDLER_NAMESPACE);
        try {
            handler.sendEmail(null);
            fail("sendEmail(Phase) did not throw IllegalArgumentException for null argument.");
        } catch (IllegalArgumentException ex) {
            //expected.
        }
    }

    /**
     * Tests the AbstractPhaseHandler.sendEmail(Phase) method with Scheduled phase status.
     *
     * @throws Exception not under test.
     */
    public void testSendEmailWithStartPhase() throws Exception {
        AbstractPhaseHandler handler = new AbstractPhaseHandlerSubClass(ABSTRACT_HANDLER_NAMESPACE);
        try {
            cleanTables();
            setupDatabase();

            Phase phase = new Phase(getProject(), 1000);
            phase.setPhaseStatus(PhaseStatus.SCHEDULED);
            phase.setPhaseType(new PhaseType(1, "Registration"));

            ConfigManager.getInstance().add(DOC_GENERATOR_CONFIG_FILE);
            ConfigManager.getInstance().add(EMAIL_CONFIG_FILE);

            handler.sendEmail(phase);
            //manually check mailbox.
        } finally {
            cleanTables();
        }
    }

    /**
     * Tests the AbstractPhaseHandler.sendEmail(Phase) method with Open phase status.
     *
     * @throws Exception not under test.
     */
    public void testSendEmailWithStopPhase() throws Exception {
        AbstractPhaseHandler handler = new AbstractPhaseHandlerSubClass(ABSTRACT_HANDLER_NAMESPACE);
        try {
            cleanTables();
            setupDatabase();

            Phase phase = new Phase(getProject(), 1000);
            phase.setPhaseStatus(PhaseStatus.OPEN);
            phase.setPhaseType(new PhaseType(1, "Registration"));

            ConfigManager.getInstance().add(DOC_GENERATOR_CONFIG_FILE);
            ConfigManager.getInstance().add(EMAIL_CONFIG_FILE);

            handler.sendEmail(phase);
            //manually check mailbox.
        } finally {
            cleanTables();
        }
    }

    /**
     * Tests AbstractPhaseHandler.createConnection() for accuracy.
     *
     * @throws Exception not under test.
     */
    public void testCreateConnection() throws Exception {
        AbstractPhaseHandler handler = new AbstractPhaseHandlerSubClass(ABSTRACT_HANDLER_NAMESPACE);
        Connection conn = null;
        try {
            conn = handler.createConnection();
            assertNotNull("create Connection returned null.", conn);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * Tests AbstractPhaseHandler.getManagerHelper() returns non-null.
     *
     * @throws Exception not under test.
     */
    public void testGetManagerHelper() throws Exception {
        AbstractPhaseHandler handler = new AbstractPhaseHandlerSubClass(ABSTRACT_HANDLER_NAMESPACE);
        assertNotNull("ManagerHelper not instantiated.", handler.getManagerHelper());
    }


    /**
     * <p>Generates a new instance of <code>Project</code> type initialized with random data.</p>
     *
     * @return a new <code>Project</code> instance.
     */
    private static Project getProject() {
        Project result = new Project(new Date(), new DefaultWorkdays());
        result.setId(1);
        return result;
    }

    /**
     * inserts required records for this test case in database.<br/>
     *
     * @throws Exception not under test.
     */
    private void setupDatabase() throws Exception {
        Connection conn = getConnection();
        Statement stmt = null;
        PreparedStatement preparedStmt = null;

        try {
            insertProject(conn);

            //insert into notification
            String insertNotification = "insert into notification(project_id, external_ref_id, notification_type_id,"
                    + "create_user, create_date, modify_user, modify_date) values "
                    + "(1, 1, 1, 'user', ?, 'user', ?)";
            preparedStmt = conn.prepareStatement(insertNotification);
            preparedStmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            preparedStmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            preparedStmt.executeUpdate();
            closeStatement(preparedStmt);
            preparedStmt = null;

            //insert into 'user'
            stmt = conn.createStatement();
            stmt.addBatch("insert into user(user_id, first_name, last_name, handle, status)"
                    + "values (1, 'abc', 'xyz', 'abcxyz', 'ON')");
            //insert into 'user_rating'
            stmt.addBatch("insert into user_rating(user_id, phase_id) values (1, 112)");
            //insert into email
            stmt.addBatch("insert into email(user_id, email_id, address, primary_ind)"
                    + " values (1, 1, 'me@somewhere.com', 1)");
            stmt.executeBatch();
        } finally {
            closeStatement(stmt);
            closeStatement(preparedStmt);
            closeConnection();
        }
    }
}
