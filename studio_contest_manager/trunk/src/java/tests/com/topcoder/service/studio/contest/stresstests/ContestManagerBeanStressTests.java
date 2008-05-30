/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.stresstests;

import com.topcoder.configuration.persistence.ConfigurationFileManager;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.ContestManager;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.contest.utils.ContestFilterFactory;

import junit.framework.Test;
import junit.framework.TestSuite;

import java.io.BufferedReader;
import java.io.FileReader;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * <p>
 * Stress test cases for <code>ContestManagerBean</code> class.
 * </p>
 *
 * @author KingStone
 * @version 1.1
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
     * Represents the property file for configuration persistence.
     * </p>
     */
    private static final String STRESSTESTS_PROPERTIES_FILE = "test_files/stresstests.properties";

    /**
     * <p>
     * Represents the <code>DBConnectionFactory</code> instance for testing.
     * </p>
     */
    private DBConnectionFactory dbConnectionFactory;

    /**
     * <p>
     * The instance of ContestManager for testing.
     * </p>
     */
    private ContestManager manager;

    /** Represents the EntityManager for testing. */
    private EntityManager entityManager;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        ConfigurationFileManager configurationFileManager = new ConfigurationFileManager(STRESSTESTS_PROPERTIES_FILE);

        dbConnectionFactory = new DBConnectionFactoryImpl(configurationFileManager
                .getConfiguration("InformixDBConnectionFactory"));

        entityManager = Persistence.createEntityManagerFactory("contest_submission").createEntityManager();

        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "admin");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");

        InitialContext ctx = new InitialContext(env);

        manager = (ContestManager) ctx.lookup("ContestManagerBean/remote");

        clearTables();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        clearTables();
        dbConnectionFactory = null;
        manager = null;
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
     * Create contest for testing.
     * @return the created contest.
     */
    private Contest createContest() {
        entityManager.getTransaction().begin();

        StudioFileType fileType = new StudioFileType();
        fileType.setSort(new Integer(1));
        fileType.setImageFile(true);
        fileType.setDescription("desc");
        fileType.setExtension(".jar");
        // fileType.setStudioFileType(1);
        entityManager.persist(fileType);

        ContestChannel category = new ContestChannel();
        category.setFileType(fileType);
        category.setName("category channel");
        category.setDescription("this is a test channel");
        entityManager.persist(category);

        ContestType contestType = new ContestType();
        contestType.setDescription("desc");
        contestType.setRequirePreviewFile(false);
        contestType.setRequirePreviewImage(true);
        entityManager.persist(contestType);

        ContestStatus status = new ContestStatus();
        status.setDescription("description");
        status.setName("name");
        entityManager.persist(status);

        entityManager.getTransaction().commit();

        Contest contest = new Contest();
        contest.setName("contest1");
        contest.setCreatedUser(new Long(1));
        contest.setContestChannel(category);
        contest.setContestType(contestType);
        contest.setEndDate(new Date());
        contest.setEventId(new Long(1));
        contest.setForumId(new Long(1));
        contest.setProjectId(new Long(1));
        contest.setTcDirectProjectId(new Long(1));
        contest.setStartDate(new Date());
        contest.setStatus(status);
        contest.setWinnerAnnoucementDeadline(new Date());

        return contest;
    }

    /**
     * <p>
     * Tests <code>createContest(Contest)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateContest() throws Exception {
        this.beginTest();

        for (int i = 0; i < RUN_TIMES; i++) {
            Contest contest = createContest();
            manager.createContest(contest);
        }

        List<Contest> contests = manager.getAllContests();

        assertEquals("contests size", RUN_TIMES, contests.size());
        this.endTest("ContestManagerBean's createContest(Contest)", 10000);
    }

    /**
     * <p>
     * Tests <code>getContest(long)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContest() throws Exception {
        this.beginTest();

        for (int i = 0; i < RUN_TIMES; i++) {
            Contest contest = createContest();
            entityManager.getTransaction().begin();
            entityManager.persist(contest);
            entityManager.getTransaction().commit();

            Contest result = manager.getContest(contest.getContestId());
            assertNotNull("should be found", result);
        }

        this.endTest("ContestManagerBean's getContest(long)", 10000);
    }

    /**
     * <p>
     * Tests <code>updateContest(Contest)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateContest() throws Exception {
        this.beginTest();

        for (int i = 0; i < RUN_TIMES; i++) {
            Contest contest = createContest();
            Contest old = manager.createContest(contest);

            manager.updateContest(old);
        }

        this.endTest("ContestManagerBean's updateContest(Contest)", 10000);
    }

    /**
     * <p>
     * Tests <code>addContestStatus(ContestStatus)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddContestStatus() throws Exception {
        this.beginTest();

        for (int i = 0; i < RUN_TIMES; i++) {
            ContestStatus status = new ContestStatus();
            status.setDescription("description");
            status.setName("name" + i);
            manager.addContestStatus(status);
        }

        this.endTest("ContestManagerBean's addContestStatus(ContestStatus)", 10000);
    }

    /**
     * <p>
     * Tests <code>removeContestStatus(long)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveContestStatus() throws Exception {
        this.beginTest();

        for (int i = 0; i < RUN_TIMES; i++) {
            manager.removeContestStatus(i);
        }
        this.endTest("ContestManagerBean's removeContestStatus(long)", 10000);
    }

    /**
     * <p>
     * Tests <code>searchContests(Filter)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchContests() throws Exception {
        for (int i = 0; i < RUN_TIMES; i++) {
            Contest contest = createContest();
            manager.createContest(contest);
        }

        this.beginTest();

        for (int i = 0; i < RUN_TIMES; i++) {
            Filter filter = ContestFilterFactory.createStudioContestDirectProjectIdFilter(i);
            manager.searchContests(filter);
        }

        this.endTest("ContestManagerBean's searchContests(Filter)", 10000);
    }

    /**
     * <p>
     * Tests <code>getAllContests()</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllContests() throws Exception {
        for (int i = 0; i < RUN_TIMES; i++) {
            Contest contest = createContest();
            manager.createContest(contest);
        }
        this.beginTest();

        for (int i = 0; i < RUN_TIMES; i++) {
            manager.getAllContests();
        }

        this.endTest("ContestManagerBean's getAllContests()", 10000);
    }

    /**
     * Clear tables.
     *
     * @throws Exception
     *             to JUnit
     */
    private void clearTables() throws Exception {
        executeScriptFile("test_files/stresstests/clearTables.sql");
    }

    /**
     * <p>
     * Executes the sql script against the database.
     * </p>
     *
     * @param filename
     *            the file name.
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    private void executeScriptFile(String filename) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        BufferedReader bufferedReader = null;

        try {
            conn = dbConnectionFactory.createConnection();

            // conn.setAutoCommit(false);
            stmt = conn.createStatement();

            String sql = null;
            bufferedReader = new BufferedReader(new FileReader(filename));

            while (null != (sql = bufferedReader.readLine())) {
                try {
                    stmt.executeUpdate(sql);
                } catch (SQLException e) {
                    System.out.println("bad sql=" + sql);

                    // ignore.
                }
            }

            conn.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());

            // ignore.
        } finally {
            if (stmt != null) {
                stmt.close();
            }

            if (conn != null) {
                conn.close();
            }

            if (null != bufferedReader) {
                bufferedReader.close();
            }
        }
    }
}
