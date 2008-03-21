/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.TestCase;

import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.service.prerequisite.document.model.CompetitionDocument;
import com.topcoder.service.prerequisite.document.model.Document;
import com.topcoder.service.prerequisite.document.model.DocumentName;
import com.topcoder.service.prerequisite.document.model.DocumentVersion;
import com.topcoder.service.prerequisite.document.model.MemberAnswer;

/**
 * <p>
 * This base test case provides common functionality for configuration and database.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public abstract class BaseTestCase extends TestCase {

    /**
     * <p>
     * Represents the date format for parsing date string.
     * </p>
     */
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * <p>
     * Represents the JNDI name to look up the DocumentManagerBean.
     * </p>
     */
    public static final String JNDI_NAME = "prerequisite_document_manager/DocumentManagerBean/remote";

    /**
     * <p>
     * Represents the property file for configuration persistence.
     * </p>
     */
    private static final String UNITTEST_PROPERTIES_FILE = "test_files/unittests.properties";

    /**
     * <p>
     * Represents the <code>DBConnectionFactory</code> instance for testing.
     * </p>
     */
    private DBConnectionFactory dbConnectionFactory;

    /**
     * <p>
     * Represents the <code>InitialContext</code> to do lookup.
     * </p>
     */
    private InitialContext context;

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        ConfigurationFileManager configurationFileManager = new ConfigurationFileManager(UNITTEST_PROPERTIES_FILE);

        dbConnectionFactory = new DBConnectionFactoryImpl(configurationFileManager
                .getConfiguration("InformixDBConnectionFactory"));

        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "user");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        context = new InitialContext(env);
    }

    /**
     * <p>
     * Tear down the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        context.close();

        super.tearDown();
    }

    /**
     * <p>
     * Returns the <code>DBConnectionFactory</code> instance.
     * </p>
     *
     * @return the <code>DBConnectionFactory</code> instance
     */
    public DBConnectionFactory getDBConnectionFactory() {
        return dbConnectionFactory;
    }

    /**
     * <p>
     * Returns the <code>InitialContext</code> instance.
     * </p>
     *
     * @return the <code>InitialContext</code> instance.
     */
    public InitialContext getInitialContext() {
        return context;
    }

    /**
     * <p>
     * Executes the sql script against the database.
     * </p>
     *
     * @param filename
     *            the file name.
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void executeScriptFile(String filename) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        BufferedReader bufferedReader = null;

        try {
            conn = dbConnectionFactory.createConnection();
            conn.setAutoCommit(false);

            stmt = conn.createStatement();

            String sql = null;
            bufferedReader = new BufferedReader(new FileReader(filename));
            while (null != (sql = bufferedReader.readLine())) {
                stmt.executeUpdate(sql);
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();

            throw e;
        } finally {
            closeStatement(stmt);
            closeConnection(conn);
            if (null != bufferedReader) {
                bufferedReader.close();
            }
        }
    }

    /**
     * <p>
     * Executes the sql statements against the database.
     * </p>
     *
     * @param sqls
     *            the array of sql statements.
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void executeSQL(String[] sqls) throws Exception {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = dbConnectionFactory.createConnection();
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
     * Closes the connection. It will be used in finally block.
     * </p>
     *
     * @param conn
     *            the database connection.
     */
    public static void closeConnection(Connection conn) {
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
     * @param rs
     *            the result set.
     */
    public static void closeResultSet(ResultSet rs) {
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
     * @param stmt
     *            the statement.
     */
    public static void closeStatement(Statement stmt) {
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
     * Gets the field value of an object.
     * </p>
     *
     * @param obj
     *            the object where to get the field value.
     * @param fieldName
     *            the name of the field.
     * @return the field value
     * @throws Exception
     *             any exception occurs.
     */
    public static Object getFieldValue(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    /**
     * <p>
     * Creates a <code>DocumentVersion</code> instance, possibly make part of the field not set.
     * </p>
     * <p>
     * </p>
     * <p>
     * Not set mapping.
     * <ul>
     * <li>2 - document version will not set.</li>
     * <li>3 - version date will not set.</li>
     * <li>4 - content will not set.</li>
     * <li>5 - document will not set.</li>
     * <li>6 - document's id will not set.</li>
     * <li>7 - documentName will not set.</li>
     * <li>8 - documentName's id will not set.</li>
     * </ul>
     * </p>
     *
     * @param whichNotSet
     *            indicate which required field don't set
     * @return the created module instance.
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public static DocumentVersion createDocumentVersion(int whichNotSet) throws Exception {
        DocumentVersion documentVersion = new DocumentVersion();

        if (2 != whichNotSet) {
            documentVersion.setDocumentVersion(1);
        }

        if (3 != whichNotSet) {
            documentVersion.setVersionDate(new Date());
        }
        if (4 != whichNotSet) {
            documentVersion.setContent("Content");
        }

        if (5 != whichNotSet && 6 != whichNotSet) {
            Document document = new Document();
            document.setDocumentId(1L);
            documentVersion.setDocument(document);
        } else if (6 == whichNotSet) {
            documentVersion.setDocument(new Document());
        }

        if (7 != whichNotSet && 8 != whichNotSet) {
            DocumentName documentName = new DocumentName();
            documentName.setDocumentNameId(1L);
            documentVersion.setDocumentName(documentName);
        } else if (8 == whichNotSet) {
            documentVersion.setDocumentName(new DocumentName());
        }

        return documentVersion;
    }

    /**
     * <p>
     * Creates a <code>MemberAnswer</code> instance, possibly make part of the field not set.
     * </p>
     * <p>
     * </p>
     * <p>
     * Not set mapping.
     * <ul>
     * <li>1 - member id will not set.</li>
     * <li>2 - answer date will not set.</li>
     * <li>3 - competitionDocument will not set.</li>
     * <li>4 - competitionDocument's id will not set.</li>
     * </ul>
     * </p>
     *
     * @param whichNotSet
     *            indicate which required field don't set
     * @return the created MemberAnswer instance.
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public static MemberAnswer createMemberAnswer(int whichNotSet) throws Exception {
        MemberAnswer memberAnswer = new MemberAnswer();

        if (1 != whichNotSet) {
            memberAnswer.setMemberId(1L);
        }

        if (2 != whichNotSet) {
            memberAnswer.setAnswerDate(new Date());
        }

        if (3 != whichNotSet && 4 != whichNotSet) {
            CompetitionDocument competitionDocument = new CompetitionDocument();
            competitionDocument.setCompetitionDocumentId(1L);
            memberAnswer.setCompetitionDocument(competitionDocument);
        } else if (4 == whichNotSet) {
            memberAnswer.setCompetitionDocument(new CompetitionDocument());
        }

        return memberAnswer;
    }

    /**
     * <p>
     * Creates a <code>CompetitionDocument</code> instance, possibly make part of the field not set.
     * </p>
     * <p>
     * </p>
     * <p>
     * Not set mapping.
     * <ul>
     * <li>1 - competition id will not set.</li>
     * <li>2 - role id will not set.</li>
     * <li>3 - documentVersion will not set.</li>
     * <li>4 - documentVersionId will not set.</li>
     * </ul>
     * </p>
     *
     * @param whichNotSet
     *            indicate which required field don't set
     * @return the created CompetitionDocument instance.
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public static CompetitionDocument createCompetitionDocument(int whichNotSet) throws Exception {
        CompetitionDocument competitionDocument = new CompetitionDocument();

        if (1 != whichNotSet) {
            competitionDocument.setCompetitionId(1L);
        }

        if (2 != whichNotSet) {
            competitionDocument.setRoleId(1L);
        }

        if (3 != whichNotSet && 4 != whichNotSet) {
            DocumentVersion documentVersion = new DocumentVersion();
            documentVersion.setDocumentVersionId(1L);
            competitionDocument.setDocumentVersion(documentVersion);
        } else if (4 == whichNotSet) {
            competitionDocument.setDocumentVersion(new DocumentVersion());
        }

        return competitionDocument;
    }
}
