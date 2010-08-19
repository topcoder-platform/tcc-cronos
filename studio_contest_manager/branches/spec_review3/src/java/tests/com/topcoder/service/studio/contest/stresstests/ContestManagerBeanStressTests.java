/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.stresstests;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.naming.InitialContext;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.ContestManager;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.contest.bean.DBUtil;


/**
 * <p>
 * Stress test cases for <code>ContestManagerBean</code> class.
 * </p>
 *
 * @author woodatxc, TCSDEVELOPER
 * @version 1.3
 */
public class ContestManagerBeanStressTests extends BaseStressTests {

    /**
     * <p>
     * Represents the date format for parsing date string.
     * </p>
     */
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * <p>
     * The instance of ContestManager for testing.
     * </p>
     */
    private ContestManager manager;

    /**
     * <p>
     * The instance of InitialContext for testing.
     * </p>
     */
    private InitialContext ctx;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        Properties env = new Properties();
        //env.setProperty(Context.SECURITY_PRINCIPAL, "admin");
        //env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        //env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");

        env.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
        env.setProperty("java.naming.provider.url", "localhost:1099");
        ctx = new InitialContext(env);

        manager = (ContestManager) ctx.lookup("remote/ContestManagerBean");
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        manager = null;
        ctx.close();
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ContestManagerBeanStressTests.class);
    }

    /**
     * <p>
     * Tests <code>createContest(Contest)</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateContest() throws Exception {
        this.beginTest();

        ContestChannel category = new ContestChannel();
        category.setDescription("description");
        category.setContestChannelId(new Long(1));

        ContestType contestType = new ContestType();
        contestType.setDescription("desc");
        contestType.setRequirePreviewFile(false);
        contestType.setRequirePreviewImage(true);
        contestType.setContestType(new Long(1));

        ContestStatus status = new ContestStatus();
        status.setDescription("description");
        status.setName("name");
        status.setContestStatusId(new Long(1));

        Contest contest = new Contest();
        contest.setContestChannel(category);
        contest.setContestType(contestType);
        contest.setStatus(status);
        contest.setName("contest1");
        contest.setProjectId(new Long(1));
        contest.setTcDirectProjectId(new Long(2));
        contest.setStartDate(new Date());
        contest.setEndDate(new Date());
        contest.setWinnerAnnoucementDeadline(new Date());
        contest.setCreatedUser(new Long(1));
        contest.setStatusId(1L);
        for (int i = 0; i < RUN_TIMES; i++) {
            deleteContestTable();
            manager.createContest(contest);
        }

        this.endTest("ContestManagerBean's createContest(Contest)", 10000);
    }

    /**
     * <p>
     * Tests <code>getContest(long)</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetContest() throws Exception {
        this.beginTest();

        Long id = new Long(1);
        deleteContestTable();
        createNewContest(new Long(1));
        for (int i = 0; i < RUN_TIMES; i++) {
            manager.getContest(id);
        }

        this.endTest("ContestManagerBean's getContest(long)", 10000);
    }

    /**
     * <p>
     * Tests <code>updateContest(Contest)</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateContest() throws Exception {
        this.beginTest();

        deleteContestTable();
        deleteContestStatusTable();
        createNewContest(new Long(1), "contest1");
        createContestStatus(new Long(1));

        ContestChannel category = new ContestChannel();
        category.setDescription("description");
        category.setContestChannelId(new Long(1));

        ContestType contestType = new ContestType();
        contestType.setDescription("desc");
        contestType.setRequirePreviewFile(false);
        contestType.setRequirePreviewImage(true);
        contestType.setContestType(new Long(1));

        ContestStatus status = new ContestStatus();
        status.setDescription("description");
        status.setName("name");
        status.setContestStatusId(new Long(1));

        Contest contest = new Contest();
        contest.setContestChannel(category);
        contest.setContestType(contestType);
        contest.setStatus(status);
        contest.setName("contest2");
        contest.setProjectId(new Long(1));
        contest.setTcDirectProjectId(new Long(2));
        contest.setStartDate(new Date());
        contest.setEndDate(new Date());
        contest.setWinnerAnnoucementDeadline(new Date());
        contest.setCreatedUser(new Long(1));
        contest.setContestId(new Long(1));
        for (int i = 0; i < RUN_TIMES; i++) {
            manager.updateContest(contest, 1, "root", true);
        }

        this.endTest("ContestManagerBean's updateContest(Contest)", 10000);
    }

    /**
     * <p>
     * Tests <code>addContestStatus(ContestStatus)</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddContestStatus() throws Exception {
        this.beginTest();

        deleteContestStatusTable();
        ContestStatus status = new ContestStatus();
        status.setDescription("description");
        status.setName("name");
        status.setContestStatusId(new Long(1));
        for (int i = 0; i < RUN_TIMES; i++) {
            manager.addContestStatus(status);
        }

        this.endTest("ContestManagerBean's addContestStatus(ContestStatus)", 10000);
    }

    /**
     * <p>
     * Tests <code>removeContestStatus(long)</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveContestStatus() throws Exception {
        this.beginTest();

        deleteContestStatusTable();
        Long contestStatusId = new Long(1);
        createContestStatus(contestStatusId);
        for (int i = 0; i < RUN_TIMES; i++) {
            manager.removeContestStatus(contestStatusId);
        }

        this.endTest("ContestManagerBean's removeContestStatus(long)", 10000);
    }

    /**
     * <p>
     * Tests <code>getUserContests(String)</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     * @since 1.3
     */
    public void testGetUserContests() throws Exception {
        this.beginTest();

        ContestChannel category = new ContestChannel();
        category.setDescription("description");
        category.setContestChannelId(new Long(1));

        ContestType contestType = new ContestType();
        contestType.setDescription("desc");
        contestType.setRequirePreviewFile(false);
        contestType.setRequirePreviewImage(true);
        contestType.setContestType(new Long(1));

        ContestStatus status = new ContestStatus();
        status.setDescription("description");
        status.setName("name");
        status.setContestStatusId(new Long(1));

        Contest contest = new Contest();
        contest.setContestChannel(category);
        contest.setContestType(contestType);
        contest.setStatus(status);
        contest.setName("contest1");
        contest.setProjectId(new Long(1));
        contest.setTcDirectProjectId(new Long(2));
        contest.setStartDate(new Date());
        contest.setEndDate(new Date());
        contest.setWinnerAnnoucementDeadline(new Date());
        contest.setCreatedUser(new Long(1));
        contest.setContestId(new Long(1));

        manager.createContest(contest);

        //run the stress test
        for (int i = 0; i < RUN_TIMES; i++) {
            manager.getUserContests("user");
        }

        this.endTest("ContestManagerBean's getUserContests(String)", 10000);
    }

    /**
     * create a tuple in contest.
     * @param id
     *            id.
     * @throws Exception
     *             exception throws by sql.
     */
    private void createNewContest(Long id) throws Exception {
        createNewContest(id, "name", new Long(1));
    }

    /**
     * create a tuple in contest.
     * @param id
     *            id.
     * @param name
     *            name.
     * @throws Exception
     *             exception throws by sql.
     */
    private void createNewContest(Long id, String name) throws Exception {
        createNewContest(id, name, new Long(1));
    }

    /**
     * create a tuple in contest.
     * @param id
     *            id.
     * @param name
     *            name.
     * @param projectId
     *            project id.
     * @throws Exception
     *             exception throws by sql.
     */
    private void createNewContest(Long id, String name, Long projectId)throws Exception {
        executeSQL(new String[] {"INSERT INTO contest (contest_id, contest_channel_id,"
                + " name,contest_type_id ,project_id,tc_direct_project_id,contest_status_id,contest_detailed_status_id,"
                + "forum_id,event_id,start_time,end_time,winner_announcement_time,creator_user_id,"
                + "contest_milestone_prize_id,is_multi_round,non_winning_submissions_purchased,launch_immediately,"
                + "deleted,contest_general_info_id,contest_multi_round_information_id,contest_specifications_id)"
                + " values ("
                + id
                + ", 1, '"
                + name
                + "', 1,1,"
                + projectId
                + ",1,1,1,1,'2010-03-19 01:01:01','2010-03-19 01:01:01','2010-03-19 01:01:01',1,"
                + "1," + "'f'" + "," + "'t'" + "," + "'t'" + "," + "'f'" + ",1,1,1)" });
    }

    /**
     * delete content in table contest.
     * @throws Exception
     *             exception throws by sql.
     */
    private void deleteContestTable() throws Exception {
        executeSQL(new String[] {"delete from contest" });
    }

    /**
     * create a tuple in contest_status_lu.
     * @param id
     *            id.
     * @throws Exception
     *             exception throws by sql.
     */
    private void createContestStatus(Long id) throws Exception {
        createContestStatus(id, "name");
    }

    /**
     * create a tuple in contest_status_lu.
     * @param id
     *            id.
     * @param name
     *            name.
     * @throws Exception
     *             exception throws by sql.
     */
    private void createContestStatus(Long id, String name) throws Exception {
        executeSQL(new String[] {"INSERT INTO contest_status_lu (contest_status_id, contest_status_desc, name)"
                + " values (" + id + ", 'value','" + name + "')" });
    }

    /**
     * delete content in table contest_status_lu.
     * @throws Exception
     *             exception throws by sql.
     */
    private void deleteContestStatusTable() throws Exception {
        executeSQL(new String[] {"delete from contest_status_lu" });
    }

    /**
     * <p>
     * Executes the sql statements against the database.
     * </p>
     * @param sqls
     *            the array of sql statements.
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    private void executeSQL(String[] sqls) throws Exception {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DBUtil.getDBConnectionFactory().createConnection();
            conn.setAutoCommit(false);

            stmt = conn.createStatement();

            for (int i = 0; i < sqls.length; i++) {
                stmt.executeUpdate(sqls[i]);
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();

            throw e;
        } finally {
            closeStatement(stmt);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Close the statement. It will be used in finally block.
     * </p>
     * @param stmt
     *            the statement.
     */
    private static void closeStatement(Statement stmt) {
        if (null != stmt) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * <p>
     * Closes the connection. It will be used in finally block.
     * </p>
     * @param conn
     *            the database connection.
     */
    private static void closeConnection(Connection conn) {
        if (null != conn) {
            try {
                conn.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }
}
