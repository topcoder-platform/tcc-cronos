package com.topcoder.service.studio.submission.stresstests;

import java.util.Date;
import java.util.HashSet;
import java.util.Properties;

import javax.naming.InitialContext;

import junit.framework.TestCase;

import com.topcoder.service.studio.submission.BaseTestCase;
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
     * The sql file insert data.
     */
    private String insertSQLFile = "/prepare.sql";

    /**
     * The sql file delete data.
     */
    private String clearSQLFile = "/clean.sql";

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

        BaseTestCase.executeScriptFile(clearSQLFile);
        BaseTestCase.executeScriptFile(insertSQLFile);
        // prepare ENC
        Properties env = new Properties();
        // env.setProperty(Context.SECURITY_PRINCIPAL, "admin");
        // env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        // env.setProperty(Context.INITIAL_CONTEXT_FACTORY,
        // "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        env.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
        env.setProperty("java.naming.provider.url", "localhost:1099");
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
        	BaseTestCase.executeSQL(new String[] {INSERT_SUBMISSION_PREFIX + "(" + i
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
        	BaseTestCase.executeSQL(new String[] {INSERT_SUBMISSION_PREFIX + "(" + i
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
            submissionPayment.setCreateDate(new Date());
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
        BaseTestCase.executeSQL(new String[] {INSERT_SUBMISSION_PREFIX
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
        BaseTestCase.executeScriptFile(clearSQLFile);
        super.tearDown();
    }
}
