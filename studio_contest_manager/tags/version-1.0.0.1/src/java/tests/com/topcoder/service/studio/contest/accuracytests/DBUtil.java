/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.accuracytests;

import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.contest.Document;
import com.topcoder.service.studio.contest.DocumentType;
import com.topcoder.service.studio.contest.FilePath;
import com.topcoder.service.studio.contest.MimeType;
import com.topcoder.service.studio.contest.StudioFileType;

import com.topcoder.util.config.ConfigManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Date;

import javax.persistence.EntityManager;


/**
 * This is a helper class for database operation.
 *
 * @author Chenhong
 * @version 1.0
 */
final class DBUtil {
    /**
     * <p>
     * Represents the <code>DBConnectionFactory</code> instance for testing.
     * </p>
     */
    private static DBConnectionFactory factory;

    /**
     * Private ctor.
     */
    private DBUtil() {
        // empty.
    }

    /**
     * Clear the database.
     *
     * @throws Exception to junit
     */
    static void clearDatabase() throws Exception {
        factory = getDBConnectionFactory();

        executeScriptFile("test_files/acc_files/delete_contest_table.sql");
        executeScriptFile("test_files/acc_files/clean_project_table.sql");
    }

    /**
     * Get DBConnectionFactory instance.
     *
     * @return DBConnectionFactory instance.
     *
     * @throws Exception to junit
     */
    static DBConnectionFactory getDBConnectionFactory()
        throws Exception {
        ConfigurationFileManager configurationFileManager =
            new ConfigurationFileManager("test_files/accuracytests.properties");

        return new DBConnectionFactoryImpl(configurationFileManager.getConfiguration("InformixDBConnectionFactory"));
    }

    /**
     * <p>
     * Executes the sql script against the database.
     * </p>
     *
     * @param filename the file name.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    static void executeScriptFile(String filename) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        BufferedReader bufferedReader = null;

        try {
            conn = factory.createConnection();

            //conn.setAutoCommit(false);
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
            closeStatement(stmt);
            closeConnection(conn);

            if (null != bufferedReader) {
                bufferedReader.close();
            }
        }
    }

    /**
     * Persist a Contest into the database.
     *
     * @param entityManager the EntityManager
     *
     * @return The contest
     *
     * @throws Exception to junit
     */
    static Contest persisteOneContest(EntityManager entityManager)
        throws Exception {
        entityManager.getTransaction().begin();

        // persist StudioFileType.
        StudioFileType fileType = new StudioFileType();
        fileType.setDescription("desc");
        fileType.setExtension("ext");
        fileType.setImageFile(false);
        fileType.setSort(new Integer(10));
        entityManager.persist(fileType);

        // Persist ContestChannel.
        ContestChannel contestChannel = new ContestChannel();
        contestChannel.setDescription("desc");
        contestChannel.setName("name");
        contestChannel.setFileType(fileType);
        entityManager.persist(contestChannel);

        // Persist ContestType.
        ContestType contestType = new ContestType();
        contestType.setDescription("desc");
        contestType.setRequirePreviewFile(false);
        contestType.setRequirePreviewImage(false);
        entityManager.persist(contestType);

        // Persist ContestStatus.
        ContestStatus status = new ContestStatus();
        status.setDescription("description");
        status.setName("status");

        entityManager.persist(status);

        Date date = new Date();

        // create Contest for create.
        Contest contest = new Contest();
        contest.setName("contest1");
        contest.setCreatedUser(new Long(1));
        contest.setContestChannel(contestChannel);
        contest.setContestType(contestType);
        contest.setEndDate(date);
        contest.setEventId(new Long(1));
        contest.setForumId(new Long(1));
        contest.setProjectId(new Long(1));
        contest.setTcDirectProjectId(new Long(1));
        contest.setStartDate(new Date());
        contest.setStatus(status);
        contest.setWinnerAnnoucementDeadline(new Date());

        entityManager.persist(contest);

        // the Contest should be commited.
        entityManager.getTransaction().commit();

        return contest;
    }

    /**
     * Persist a Document instance into the database.
     *
     * @param entityManager the EntityManager instance
     *
     * @return Document persisted.
     *
     * @throws Exception to junit
     */
    static Document persisteDocument(EntityManager entityManager)
        throws Exception {
        entityManager.getTransaction().begin();

        // persist StudioFileType.
        StudioFileType fileType = new StudioFileType();
        fileType.setDescription("desc");
        fileType.setExtension("ext");
        fileType.setImageFile(false);
        fileType.setSort(new Integer(10));
        entityManager.persist(fileType);

        Document document = new Document();
        document.setCreateDate(new Date());

        MimeType type = new MimeType();
        type.setDescription("des");
        type.setStudioFileType(fileType);
        entityManager.persist(type);

        document.setMimeType(type);

        document.setOriginalFileName("name");

        FilePath path = new FilePath();
        path.setModifyDate(new Date());
        path.setPath("test_files/acc_files/temp");
        entityManager.persist(path);

        document.setPath(path);

        document.setSystemFileName("test");

        DocumentType documentType = new DocumentType();
        documentType.setDescription("documentType");

        entityManager.persist(documentType);

        document.setType(documentType);
        document.setCreateDate(new Date());


        entityManager.persist(document);

        // the document must be committed into the database.
        entityManager.getTransaction().commit();

        return document;
    }

    /**
     * Prepare project in the database.
     *
     * @throws Exception to junit
     */
    static void prepareProject() throws Exception {
        factory = getDBConnectionFactory();

        Connection connection = factory.createConnection();

        try {
            String query = "INSERT INTO tc_direct_project (project_id, name, user_id, create_date) " +
                "values (1, 'project', 10, '2008-03-19 01:01:01')";
            connection.createStatement().executeUpdate(query);
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * Persist a StudioFileType instance.
     *
     * @param entityManager the EntityMaanager instance.
     *
     * @return The StudioFileType
     *
     * @throws Exception to junit
     */
    static StudioFileType persisteStudioFileType(EntityManager entityManager)
        throws Exception {
        entityManager.getTransaction().begin();

        StudioFileType fileType = new StudioFileType();
        fileType.setDescription("updateContest");
        fileType.setExtension("doc");
        fileType.setImageFile(false);
        fileType.setSort(new Integer(100));

        entityManager.getTransaction().commit();

        return fileType;
    }

    /**
     * Persist a ContestStatus.
     *
     * @param entityManager the EntityManager
     *
     * @return ContestStatus.
     *
     * @throws Exception to junit
     */
    static ContestStatus persistContestStatus(EntityManager entityManager)
        throws Exception {
        entityManager.getTransaction().begin();

        ContestStatus status = new ContestStatus();
        status.setDescription("description");
        status.setName("update");
        entityManager.persist(status);

        entityManager.getTransaction().commit();

        return status;
    }

    /**
     * Persist a ContestType.
     *
     * @param entityManager The EntityManager
     *
     * @return ContestType.
     *
     * @throws Exception to junit.
     */
    static ContestType persistContestType(EntityManager entityManager)
        throws Exception {
        entityManager.getTransaction().begin();

        ContestType contestType = new ContestType();
        contestType.setDescription("update");
        contestType.setRequirePreviewFile(false);
        contestType.setRequirePreviewImage(false);
        entityManager.persist(contestType);

        entityManager.getTransaction().commit();

        return contestType;
    }

    /**
     * Persist a ContestChannel.
     *
     * @param entityManager the entityManager
     *
     * @return newly persisted ContestChannel.
     *
     * @throws Exception to junit
     */
    static ContestChannel persistContestChannel(EntityManager entityManager)
        throws Exception {
        entityManager.getTransaction().begin();

        StudioFileType fileType = new StudioFileType();
        fileType.setDescription("updateContest");
        fileType.setExtension("doc");
        fileType.setImageFile(false);
        fileType.setSort(new Integer(100));
        entityManager.persist(fileType);

        ContestChannel contestChannel = new ContestChannel();
        contestChannel.setDescription("update channel");
        contestChannel.setName("update");
        contestChannel.setFileType(fileType);

        entityManager.persist(contestChannel);

        entityManager.getTransaction().commit();

        return contestChannel;
    }

    /**
     * <p>
     * Closes the connection. It will be used in finally block.
     * </p>
     *
     * @param conn the database connection.
     */
    static void closeConnection(Connection conn) {
        if (null != conn) {
            try {
                conn.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * <p>
     * Close the result set. It will be used in finally block.
     * </p>
     *
     * @param rs the result set.
     */
    static void closeResultSet(ResultSet rs) {
        if (null != rs) {
            try {
                rs.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * <p>
     * Close the statement. It will be used in finally block.
     * </p>
     *
     * @param stmt the statement.
     */
    static void closeStatement(Statement stmt) {
        if (null != stmt) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }
}
