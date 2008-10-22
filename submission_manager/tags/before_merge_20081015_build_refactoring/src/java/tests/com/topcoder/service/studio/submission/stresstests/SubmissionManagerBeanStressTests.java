package com.topcoder.service.studio.submission.stresstests;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.TestCase;

import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.service.studio.submission.PaymentStatus;
import com.topcoder.service.studio.submission.Prize;
import com.topcoder.service.studio.submission.PrizeType;
import com.topcoder.service.studio.submission.ReviewStatus;
import com.topcoder.service.studio.submission.Submission;
import com.topcoder.service.studio.submission.SubmissionManager;
import com.topcoder.service.studio.submission.SubmissionPayment;
import com.topcoder.service.studio.submission.SubmissionReview;

/**
 * This is stress test class for <code>SubmissionManagerBean</code>.
 * 
 * @author hfx
 * @version 1.0
 */
public class SubmissionManagerBeanStressTests extends TestCase {
    /**
     * <p> Represents the prefix part sql for insert submission. </p>
     */
    private static final String INSERT_SUBMISSION_PREFIX = "INSERT INTO submission (submission_id,"
            + " submission_status_id, submitter_id, contest_id, create_date, original_file_name, system_file_name,"
            + " submission_type_id, mime_type_id, submission_date, height, width, modify_date,"
            + " or_submission_id, path_id) VALUES ";
    
    /**
     * Configure file.
     */
    private String configFile = "test_files"
        + File.separator + "unittests.properties";

    /**
     * The sql file insert data.
     */
    private String insertSQLFile = "test_files" + File.separator + "prepare.sql";

    /**
     * The sql file delete data.
     */
    private String clearSQLFile = "test_files" + File.separator + "clean.sql";

    /**
     * <p>
     * Represents the <code>SubmissionManagerBean</code> instance.
     * </p>
     */
    private SubmissionManager submissionManager;

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

        executeSqlFile(clearSQLFile);
        executeSqlFile(insertSQLFile);
        // prepare ENC
        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "admin");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        InitialContext ctx = new InitialContext(env);

        submissionManager = (SubmissionManager) ctx.lookup("remote/SubmissionManagerBean");
    }

    /**
     * <p>
     * Stress test CRUD operation of submission.
     * </p>
     * 
     * @throws Exception
     *             to JUnit.
     */
    public void testCRUDSubmission() throws Exception {
        int number = 10;
        for (int i = 0; i < number; i++) {
            executeSQL(new String[] {INSERT_SUBMISSION_PREFIX + "(" + i
                    + ", 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1,"
                    + " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)"});
        }
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < number; i++) {
            // do retrive/update/remove submission
            Submission submission = submissionManager.getSubmission(i);
            submission.setHeight(121);
            submission.setPrizes(new HashSet<Prize>());
            submissionManager.updateSubmission(submission);
            submissionManager.removeSubmission(i);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("retrive/update/remove 10 submission entities takes "
                + (endTime - startTime) + "ms");
    }

    /**
     * <p>
     * Stress test CRUD operation of Payment.
     * </p>
     * 
     * @throws Exception
     *             to JUnit.
     */
    public void testCRUDPayment() throws Exception {
        int number = 10;
        for (int i = 0; i < number; i++) {
            executeSQL(new String[] {INSERT_SUBMISSION_PREFIX + "(" + i
                    + ", 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1,"
                    + " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)"});
        }
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < number; i++) {
            Submission submission = new Submission();
            submission.setSubmissionId((long) i);
            PaymentStatus paymentStatus = new PaymentStatus();
            paymentStatus.setPaymentStatusId(1L);
            SubmissionPayment submissionPayment = new SubmissionPayment();
            submissionPayment.setSubmission(submission);
            submissionPayment.setStatus(paymentStatus);
            submissionPayment.setPrice(1.0);

            // do create/retrieve/update/remove
            submissionPayment = submissionManager.addSubmissionPayment(submissionPayment);
            submissionManager.getSubmissionPayment(1);
            submissionPayment.setPrice(111.1);
            submissionManager.updateSubmissionPayment(submissionPayment);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("create/retrieve/update/remove 10 Payment entities takes "
                + (endTime - startTime) + "ms");
    }

    /**
     * <p>
     * Stress test CRUD operation of Review.
     * </p>
     * 
     * @throws Exception
     *             to JUnit.
     */
    public void testCRUDReview() throws Exception {
        int number = 10;
        executeSQL(new String[] {INSERT_SUBMISSION_PREFIX
                + "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1,"
                + " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)"});

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < number; i++) {
            Submission submission = new Submission();
            submission.setSubmissionId(1L);
            ReviewStatus reviewStatus = new ReviewStatus();
            reviewStatus.setReviewStatusId(1L);
            SubmissionReview submissionReview = new SubmissionReview();
            submissionReview.setSubmission(submission);
            submissionReview.setModifyDate(new Date());
            submissionReview.setReviewerId(1L);
            submissionReview.setText("Reviewing");
            submissionReview.setStatus(reviewStatus);

            // do create/retrieve/update/remove
            submissionReview = submissionManager.addSubmissionReview(submissionReview);
            submissionManager.getSubmissionReview(1);
            submissionReview.setText("xxx");
            submissionManager.updateSubmissionReview(submissionReview);
            submissionManager.removeSubmissionReview(1);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("create/retrieve/update/remove 10 Review entities takes "
                + (endTime - startTime) + "ms");
    }

    /**
     * <p>
     * Stress test CRUD operation of Prize.
     * </p>
     * 
     * @throws Exception
     *             to JUnit.
     */
    public void testCRUDPrize() throws Exception {
        int number = 10;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < number; i++) {
            PrizeType type = new PrizeType();
            type.setPrizeTypeId(1L);
            type.setDescription("Test");
            Prize prize = new Prize();
            prize.setAmount(10D);
            prize.setCreateDate(new Date());
            prize.setPlace(1);
            prize.setType(type);
            // do create/retrieve/update/remove
            prize = submissionManager.addPrize(prize);
            prize = submissionManager.getPrize(prize.getPrizeId());
            prize.setAmount(111.1);
            submissionManager.updatePrize(prize);
            submissionManager.removePrize(prize.getPrizeId());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("create/retrieve/update/remove 10 Prize entities takes "
                + (endTime - startTime) + "ms");
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
        executeSqlFile(clearSQLFile);
        super.tearDown();
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
            // create connection
            conn = getDBConnectionFactory().createConnection();
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
            doClose(conn, stmt, null);
        }
    }

    /**
     * Execute the sql statements in a file.
     * 
     * @param filename
     *            the sql file.
     * @throws Exception
     *             to JUnit
     */
    private final void executeSqlFile(String filename) throws Exception {
        FileReader file = null;
        StringBuffer content = new StringBuffer();
        try {
            file = new FileReader(filename);
            char[] buffer = new char[1024];
            int retLength = 0;

            while ((retLength = file.read(buffer)) >= 0) {
                content.append(buffer, 0, retLength);
            }
        } finally {
            if (file != null) {
                file.close();
            }
        }

        // get the connection
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            // Executes the SQL in the file
            statement.executeUpdate(content.toString());
        } finally {
            doClose(connection, statement, null);
        }
    }

    /**
     * Return a DBConnectionFactory instance.
     * 
     * @return a DBConnectionFactory instance
     * @throws Exception
     *             to JUnit
     */
    private final DBConnectionFactory getDBConnectionFactory() throws Exception {
        ConfigurationFileManager manager = new ConfigurationFileManager(configFile);
        return new DBConnectionFactoryImpl(manager.getConfiguration("InformixDBConnectionFactory"));
    }

    /**
     * Return a database connection.
     * 
     * @return a database connection
     * @throws Exception
     *             to JUnit
     */
    private final Connection getConnection() throws Exception {
        return getDBConnectionFactory().createConnection();
    }

    /**
     * <p>
     * Close the resources of the database.
     * </p>
     * 
     * @param connection
     *            the Connection to be closed
     * @param statement
     *            the PreparedStatement to be closed.
     * @param resultSet
     *            the ResultSet to be closed
     */
    private final void doClose(Connection connection, Statement statement, ResultSet resultSet) {
        // close the resultSet
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            // ignore to continue close the Connection and Statement
        }

        // close the PreparedStatement
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            // ignore to continue close the Connection
        }

        // close the connection
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            // ignore
        }
    }
}
