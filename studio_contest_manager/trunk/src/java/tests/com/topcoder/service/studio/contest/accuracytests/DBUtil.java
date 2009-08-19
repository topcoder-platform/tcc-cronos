/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.accuracytests;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.ContestGeneralInfo;
import com.topcoder.service.studio.contest.ContestMultiRoundInformation;
import com.topcoder.service.studio.contest.ContestSpecifications;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.contest.Document;
import com.topcoder.service.studio.contest.DocumentType;
import com.topcoder.service.studio.contest.FilePath;
import com.topcoder.service.studio.contest.MimeType;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.submission.MilestonePrize;
import com.topcoder.service.studio.submission.PrizeType;


/**
 * This is a helper class for database operation.
 *
 * @author Chenhong, myxgyy
 * @version 1.3
 * @since 1.0
 */
final class DBUtil {

    /**
     * Private ctor.
     */
    private DBUtil() {
        // empty.
    }

    /**
     * Clears the database.
     *
     * @throws Exception to junit
     */
    static void clearDatabase() throws Exception {
        executeScriptFile("/acc_files/delete_contest_table.sql");
        executeScriptFile("/acc_files/clean_project_table.sql");
    }

    /**
     * Initializes the database.
     *
     * @throws Exception to junit
     */
    static void initDatabase() throws Exception {
        executeSqlFile("/acc_files/insert.sql");
    }

    /**
     * Gets DBConnectionFactory instance.
     *
     * @return DBConnectionFactory instance.
     *
     * @throws Exception to junit
     */
    static DBConnectionFactory getDBConnectionFactory()
        throws Exception {
        return com.topcoder.service.studio.contest.bean.DBUtil.getDBConnectionFactory();
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
        com.topcoder.service.studio.contest.bean.DBUtil.executeScriptFile(filename);
    }

    /**
     * Execute the sql statements in a file.
     *
     * @param filename
     *            the sql file.
     * @throws Exception
     *             to JUnit.
     */
    private static void executeSqlFile(String filename) throws Exception {
        InputStream in = DBUtil.class.getResourceAsStream(filename);

        byte[] buf = new byte[1024];
        int len = 0;
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        String content;

        try {
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }

            content = out.toString();
        } finally {
            in.close();
            out.close();
        }

        List<String> sqls = new ArrayList<String>();
        int lastIndex = 0;

        // parse the sqls
        for (int i = 0; i < content.length(); i++) {
            if (content.charAt(i) == ';') {
                sqls.add(content.substring(lastIndex, i).trim());
                lastIndex = i + 1;
            }
        }

        Connection conn = getDBConnectionFactory().createConnection();

        try {
            for (int i = 0; i < sqls.size(); i++) {
                doSQLUpdate((String) sqls.get(i), conn);
            }
        } finally {
            closeConnection(conn);
        }
    }

    /**
     * Does the update operation to the database.
     *
     * @param sql
     *            the SQL statement to be executed.
     * @param conn the database connection.
     * @throws Exception
     *             to JUnit.
     */
    private static void doSQLUpdate(String sql, Connection conn) throws Exception {
        PreparedStatement ps = null;

        try {
            // creates the prepared statement
            ps = conn.prepareStatement(sql);


            // do the update
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("bad sql=" + sql);

            // ignore.
        } finally {
            // close the resources
            closeStatement(ps);
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
        fileType.setStudioFileType(1);
        entityManager.persist(fileType);

        // Persist ContestChannel.
        ContestChannel contestChannel = new ContestChannel();
        contestChannel.setDescription("desc");
        contestChannel.setContestChannelId(1L);
        entityManager.persist(contestChannel);

        // Persist ContestType.
        ContestType contestType = new ContestType();
        contestType.setDescription("desc");
        contestType.setRequirePreviewFile(false);
        contestType.setRequirePreviewImage(false);
        contestType.setContestType(1L);
        entityManager.persist(contestType);

        // Persist ContestStatus.
        ContestStatus status = new ContestStatus();
        status.setDescription("description");
        status.setName("status");
        status.setStatusId(1L);
        status.setContestStatusId(1L);

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

        ContestSpecifications specData = new ContestSpecifications();
        specData.setAdditionalRequirementsAndRestrictions("not null");
        specData.setColors("Red");
        specData.setFonts("Arial");
        specData.setLayoutAndSize("10px");
        contest.setSpecifications(specData);

        PrizeType type = new PrizeType();
        type.setDescription("payment");
        type.setPrizeTypeId(2L);
        entityManager.persist(type);

        MilestonePrize prizeData = new MilestonePrize();
        prizeData.setType(type);
        prizeData.setCreateDate(new Date());
        prizeData.setAmount(20.00d);
        prizeData.setNumberOfSubmissions(10);
        contest.setMilestonePrize(prizeData);

        ContestMultiRoundInformation infoData = new ContestMultiRoundInformation();
        infoData.setMilestoneDate(new Date());
        infoData.setRoundOneIntroduction("one");
        infoData.setRoundTwoIntroduction("two");
        infoData.setSubmittersLockedBetweenRounds(true);
        contest.setMultiRoundInformation(infoData);

        ContestGeneralInfo generalInfoData = new ContestGeneralInfo();
        generalInfoData.setBrandingGuidelines("brandingGuidelines");
        generalInfoData.setDislikedDesignsWebsites("dislikedDesignsWebsites");
        generalInfoData.setGoals("goals");
        generalInfoData.setOtherInstructions("otherInstructions");
        generalInfoData.setTargetAudience("targetAudience");
        generalInfoData.setWinningCriteria("winningCriteria");
        contest.setGeneralInfo(generalInfoData);

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
        type.setMimeTypeId(1L);
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
        documentType.setDocumentTypeId(1L);
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

        Connection connection = getDBConnectionFactory().createConnection();

        try {
            String query = "INSERT INTO tc_direct_project (project_id, name, user_id, description, create_date, modify_date) " +
                "values (1, 'project', 10, 'desc', current, current)";
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
        status.setContestStatusId(2L);
        status.setStatusId(1L);
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
        contestType.setContestType(2L);
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
        fileType.setStudioFileType(2);
        entityManager.persist(fileType);

        ContestChannel contestChannel = new ContestChannel();
        contestChannel.setDescription("update channel");
        contestChannel.setContestChannelId(2L);

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
