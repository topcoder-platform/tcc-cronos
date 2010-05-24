/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.ejb.EJBException;
import javax.naming.InitialContext;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.topcoder.service.studio.contest.Contest;


/**
 * <p>
 * Unit test for <code>{@link SubmissionManagerBean}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.2
 */
public class SubmissionManagerBeanUnitTests extends BaseTestCase {
    /**
     * <p>
     * Represents the prefix part sql for insert contest prize relation.
     * </p>
     */
    private static final String INSERT_CONTEST_PRIZE_PREFIX = "INSERT INTO contest_prize_xref" +
        " (contest_id, prize_id, create_date) VALUES ";

    /**
     * <p>
     * Represents the prefix part sql for insert submission.
     * </p>
     */
    private static final String INSERT_SUBMISSION_PREFIX = "INSERT INTO submission (submission_id," +
        " submission_status_id, submitter_id, contest_id, create_date, original_file_name, system_file_name," +
        " submission_type_id, mime_type_id, submission_date, height, width, modify_date," +
        " or_submission_id, path_id) VALUES ";

    /**
     * <p>
     * Represents the prefix part sql for insert submission with the rank field.
     * </p>
     *
     * @since 1.2
     */
    private static final String INSERT_SUBMISSION_WITH_RANK_PREFIX = "INSERT INTO submission (submission_id," +
        " submission_status_id, submitter_id, contest_id, create_date, original_file_name, system_file_name," +
        " submission_type_id, mime_type_id, submission_date, height, width, modify_date," +
        " or_submission_id, path_id,rank) VALUES ";

    /**
     * <p>
     * Represents the prefix part sql for insert contest.
     * </p>
     *
     * @since 1.2
     */
    private static final String INSERT_CONTEST_PREFIX = "INSERT INTO contest (contest_id, contest_channel_id, " +
        "name, contest_type_id, project_id, tc_direct_project_id, contest_status_id, c" +
        "ontest_detailed_status_id, forum_id, event_id, start_time, end_time,  winner_announcement_time, " +
        "create_user_id, contest_milestone_prize_id, contest_general_info_id, contest_multi_round_information_id, " +
        "contest_specifications_id)";

    /**
     * <p>
     * Represents the prefix part sql for insert prize.
     * </p>
     */
    private static final String INSERT_PRIZE_PREFIX = "INSERT INTO prize (prize_id, place, amount," +
        " prize_type_id, create_date) VALUES ";

    /**
     * <p>
     * Represents the prefix part sql for insert submission review.
     * </p>
     */
    private static final String INSERT_SUBMISSION_REVIEW_PREFIX = "INSERT INTO submission_review" +
        " (submission_id, reviewer_id, text, review_status_id, modify_date) VALUES ";

    /**
     * <p>
     * Represents the prefix part sql for insert submission prize relation.
     * </p>
     */
    private static final String INSERT_SUBMISSION_PRIZE_PREFIX = "INSERT INTO submission_prize_xref" +
        " (submission_id, prize_id, create_date) VALUES ";

    /**
     * <p>
     * Represents the prefix part sql for insert submission payment.
     * </p>
     */
    private static final String INSERT_SUBMISSION_PAYMENTS_PREFIX = "INSERT INTO submission_payment" +
        " (submission_id, payment_status_id, price, create_date) VALUES ";

    /**
     * <p>
     * Represents the <code>SubmissionManagerBean</code> instance.
     * </p>
     */
    private SubmissionManager submissionManager;

    /**
     * <p>
     * Represents the <code>InitialContext</code> instance for JNDI lookup.
     * </p>
     */
    private InitialContext ctx;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(SubmissionManagerBeanUnitTests.class);
    }

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

        executeScriptFile("/clean.sql");
        executeScriptFile("/prepare.sql");

        Properties env = new Properties();
        /*
         * If you need to using the JBOSS security control, please use the
         * commented codes
         */

        // env.setProperty(Context.SECURITY_PRINCIPAL, "admin");
        // env.setProperty(Context.SECURITY_CREDENTIALS, "admin");
        // env.setProperty(Context.INITIAL_CONTEXT_FACTORY,
        // "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        env.setProperty("java.naming.factory.initial",
            "org.jnp.interfaces.NamingContextFactory");
        env.setProperty("java.naming.provider.url", "localhost:1099");
        ctx = new InitialContext(env);

        submissionManager = (SubmissionManager) ctx.lookup("remote/SubmissionManagerBean");
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
        executeScriptFile("/clean.sql");

        super.tearDown();
    }

    /**
     * <p>
     * Unit test for <code>{@link SubmissionManagerBean#initialize()}</code>
     * method.
     * </p>
     * <p>
     * If the env entry is not defined, log is not initialized.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInitialize_EnvNotDefined() throws Exception {
        submissionManager = (SubmissionManager) ctx.lookup(
                "SubmissionManagerBeanWithoutLog");

        // make call to let the bean initialized.
        submissionManager.getSubmission(1);
    }

    /**
     * <p>
     * Unit test for <code>{@link SubmissionManagerBean#initialize()}</code>
     * method.
     * </p>
     * <p>
     * If the log name is empty, log is initialized.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInitialize_EmptyLogName() throws Exception {
        submissionManager = (SubmissionManager) ctx.lookup(
                "SubmissionManagerBeanEmptyLogName");

        // make call to let the bean initialized.
        submissionManager.getSubmission(1);
    }

    /**
     * <p>
     * Unit test for <code>{@link SubmissionManagerBean#initialize()}</code>
     * method.
     * </p>
     * <p>
     * If the log name is trimmed empty, log is initialized.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInitialize_TrimmedEmptyLogName() throws Exception {
        submissionManager = (SubmissionManager) ctx.lookup(
                "SubmissionManagerBeanTrimmedEmptyLogName");

        // make call to let the bean initialized.
        submissionManager.getSubmission(1);
    }

    /**
     * <p>
     * Unit test for <code>{@link SubmissionManagerBean#initialize()}</code>
     * method.
     * </p>
     * <p>
     * If the log name type is invalid, should throw
     * SubmissionManagementConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInitialize_InvalidLogNameType() throws Exception {
        try {
            submissionManager = (SubmissionManager) ctx.lookup(
                    "SubmissionManagerBeanTrimmedInvalidType");

            // make call to let the bean initialized.
            submissionManager.getSubmission(1);
            fail("expect SubmissionManagementConfigurationException");
        } catch (RuntimeException e) {
            assertTrue(e.getCause() instanceof SubmissionManagementConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link SubmissionManagerBean#initialize()}</code>
     * method.
     * </p>
     * <p>
     * If the unit name is not specified, should throw
     * SubmissionManagementConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInitialize_UnitNameNotDefined() throws Exception {
        try {
            submissionManager = (SubmissionManager) ctx.lookup(
                    "SubmissionManagerBeanWithoutUnitName");

            // make call to let the bean initialized.
            submissionManager.getSubmission(1);
            fail("expect SubmissionManagementConfigurationException");
        } catch (RuntimeException e) {
            assertTrue(e.getCause() instanceof SubmissionManagementConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link SubmissionManagerBean#initialize()}</code>
     * method.
     * </p>
     * <p>
     * If the unit name is empty, should throw
     * SubmissionManagementConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInitialize_EmptyUnitName() throws Exception {
        try {
            submissionManager = (SubmissionManager) ctx.lookup(
                    "SubmissionManagerBeanEmptyUnitName");

            // make call to let the bean initialized.
            submissionManager.getSubmission(1);
            fail("expect SubmissionManagementConfigurationException");
        } catch (RuntimeException e) {
            assertTrue(e.getCause() instanceof SubmissionManagementConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link SubmissionManagerBean#initialize()}</code>
     * method.
     * </p>
     * <p>
     * If the unit name is trimmed empty, should throw
     * SubmissionManagementConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInitialize_TrimmedEmptyUnitName() throws Exception {
        try {
            submissionManager = (SubmissionManager) ctx.lookup(
                    "SubmissionManagerBeanTrimmedEmptyUnitName");

            // make call to let the bean initialized.
            submissionManager.getSubmission(1);
            fail("expect SubmissionManagementConfigurationException");
        } catch (RuntimeException e) {
            assertTrue(e.getCause() instanceof SubmissionManagementConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link SubmissionManagerBean#initialize()}</code>
     * method.
     * </p>
     * <p>
     * If the unit name is invalid, should throw
     * SubmissionManagementConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInitialize_InvalidUnitName() throws Exception {
        try {
            submissionManager = (SubmissionManager) ctx.lookup(
                    "SubmissionManagerBeanTrimmedNotFoundUnitName");

            // make call to let the bean initialized.
            submissionManager.getSubmission(1);
            fail("expect SubmissionManagementConfigurationException");
        } catch (RuntimeException e) {
            assertTrue(e.getCause() instanceof SubmissionManagementConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#getSubmission(long)}</code> method.
     * </p>
     * <p>
     * If the submission does not exist, should return null.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetSubmission_NotFound() throws Exception {
        assertNull("Null expected", submissionManager.getSubmission(1));
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#getSubmission(long)}</code> method.
     * </p>
     * <p>
     * If the submission is deleted, should return null.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetSubmission_Deleted() throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 2, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)"
            });

        assertNull("Null expected", submissionManager.getSubmission(1));
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#getSubmission(long)}</code> method.
     * </p>
     * <p>
     * Should return the exact submission.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetSubmission_accuracy() throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)"
            });

        Submission submission = submissionManager.getSubmission(1);

        assertNotNull("Null not expected", submission);
        // actually not need to fully test the bean, as it is controlled by
        // hibernate.
        assertEquals("status is incorrect.", 1,
            submission.getStatus().getSubmissionStatusId().longValue());
        assertEquals("incorrect original file name", "test.jar",
            submission.getOriginalFileName());
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#getSubmissionsForContest(long, boolean)}</code>
     * method.
     * </p>
     * <p>
     * If no submissions for the specified contest, should return empty list.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetSubmissionsForContest_empty() throws Exception {
        List<Submission> submissions = submissionManager.getSubmissionsForContest(1,
                false);

        assertNotNull("Should not null.", submissions);
        assertTrue("The submissions should be empty.", submissions.isEmpty());
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#getSubmissionsForContest(long, boolean)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetSubmissionsForContest_accuracy()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)",
                
            INSERT_SUBMISSION_PREFIX +
                "(2, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)"
            });

        List<Submission> submissions = submissionManager.getSubmissionsForContest(1,
                true);

        assertNotNull("Should not null.", submissions);
        assertEquals("The submissions should have 2 elements.", 2,
            submissions.size());

        for (Submission submission : submissions) {
            assertEquals("status is incorrect.", 1,
                submission.getStatus().getSubmissionStatusId().longValue());
            assertEquals("incorrect original file name", "test.jar",
                submission.getOriginalFileName());
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#getSubmissionsForContest(long, boolean)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetSubmissionsForContest_accuracy2()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)",
                
            INSERT_SUBMISSION_PREFIX +
                "(2, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)"
            });

        List<Submission> submissions = submissionManager.getSubmissionsForContest(1,
                false);

        assertNotNull("Should not null.", submissions);
        assertEquals("The submissions should have 2 elements.", 2,
            submissions.size());

        for (Submission submission : submissions) {
            assertNull("the full submission path should be null.",
                submission.getFullSubmissionPath());
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#getAllSubmissionsByMember(long)}</code>
     * method.
     * </p>
     * <p>
     * If no submissions for the specified user, should return empty list.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetAllSubmissionsByMember_empty() throws Exception {
        List<Submission> submissions = submissionManager.getAllSubmissionsByMember(10);

        assertNotNull("Should not null.", submissions);
        assertTrue("The submissions should be empty.", submissions.isEmpty());
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#getAllSubmissionsByMember(long)}</code>
     * method.
     * </p>
     * <p>
     * If the submissions of the member is not deleted, should return the active
     * submission list.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetAllSubmissionsByMember_accuracy()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)",
                
            INSERT_SUBMISSION_PREFIX +
                "(2, 1, 2, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)"
            });

        List<Submission> submissions = submissionManager.getAllSubmissionsByMember(1);

        assertNotNull("Should not null.", submissions);
        assertEquals("The submissions should have one elements.", 1,
            submissions.size());
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#getAllSubmissionsByMember(long)}</code>
     * method.
     * </p>
     * <p>
     * Any deleted submissions should not return.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetAllSubmissionsByMember_Inactive()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)",
                
            INSERT_SUBMISSION_PREFIX +
                "(2, 2, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)"
            });

        List<Submission> submissions = submissionManager.getAllSubmissionsByMember(1);

        assertNotNull("Should not null.", submissions);
        assertEquals("The submissions should have one elements.", 1,
            submissions.size());
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updateSubmission(Submission)}</code>
     * method.
     * </p>
     * <p>
     * If the submission is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateSubmission_NullSubmission() throws Exception {
        try {
            submissionManager.updateSubmission(null);
            fail("expect IllegalArgumentException");
        } catch (EJBException e) {
            assertTrue("Incorrect cause.",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updateSubmission(Submission)}</code>
     * method.
     * </p>
     * <p>
     * If the submission does not exist in database, should throw
     * EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateSubmission_EntityNotFound() throws Exception {
        Submission submission = new Submission();
        submission.setSubmissionId(1L);

        try {
            submissionManager.updateSubmission(submission);
            fail("expect EntityNotFoundException");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updateSubmission(Submission)}</code>
     * method.
     * </p>
     * <p>
     * If the submission is already deleted, should throw
     * EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateSubmission_EntityDeleted() throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 2, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)"
            });

        Submission submission = new Submission();
        submission.setSubmissionId(1L);

        try {
            submissionManager.updateSubmission(submission);
            fail("expect EntityNotFoundException");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updateSubmission(Submission)}</code>
     * method.
     * </p>
     * <p>
     * If the submission's rank is changed, should throw
     * SubmissionManagementException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateSubmission_RankChanged1() throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 2, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)"
            });

        Submission submission = new Submission();
        submission.setSubmissionId(1L);
        submission.setRank(1);

        try {
            submissionManager.updateSubmission(submission);
            fail("expect SubmissionManagementException");
        } catch (SubmissionManagementException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updateSubmission(Submission)}</code>
     * method.
     * </p>
     * <p>
     * If the submission's rank is changed, should throw
     * SubmissionManagementException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateSubmission_RankChanged2() throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(1, 2, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,1)"
            });

        /*
         * executeSQL(new String[] {
         *
         * "INSERT INTO submission (submission_id, submission_status_id, submitter_id,"
         * +
         * " contest_id, create_date, original_file_name, system_file_name, submission_type_id, mime_type_id,"
         * +
         * " submission_date, height, width, modify_date, or_submission_id, path_id, rank) VALUES (1, 2, 1, 1,"
         * +
         * " '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1, '2008-03-16 09:00:00', 1, 1,"
         * + " '2008-03-16 09:00:00', 1, 1, 1)" });
         */
        Submission submission = new Submission();
        submission.setSubmissionId(1L);

        try {
            submissionManager.updateSubmission(submission);
            fail("expect SubmissionManagementException");
        } catch (SubmissionManagementException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updateSubmission(Submission)}</code>
     * method.
     * </p>
     * <p>
     * If the submission's rank is changed, should throw
     * SubmissionManagementException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateSubmission_RankChanged3() throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(1, 2, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,1)"
            });

        Submission submission = new Submission();
        submission.setSubmissionId(1L);
        submission.setRank(2);

        try {
            submissionManager.updateSubmission(submission);
            fail("expect SubmissionManagementException");
        } catch (SubmissionManagementException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updateSubmission(Submission)}</code>
     * method.
     * </p>
     * <p>
     * If the submission's prizes set is changed, should throw
     * SubmissionManagementException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateSubmission_PrizesChanged() throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)"
            });

        Submission submission = new Submission();
        submission.setSubmissionId(1L);

        Set<Prize> prizes = new HashSet<Prize>();
        prizes.add(new Prize());
        submission.setPrizes(prizes);

        try {
            submissionManager.updateSubmission(submission);
            fail("expect SubmissionManagementException");
        } catch (SubmissionManagementException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updateSubmission(Submission)}</code>
     * method.
     * </p>
     * <p>
     * If the submission's prizes set is changed, should throw
     * SubmissionManagementException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateSubmission_accuracy() throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)"
            });

        Submission submission = submissionManager.getSubmission(1);
        // solve lazy loading problem because remote invocation.
        submission.setPrizes(new HashSet<Prize>());

        submission.setOrSubmission(2L);

        Date newDate = new Date();
        submission.setModifyDate(newDate);
        submissionManager.updateSubmission(submission);

        // verification.
        submission = submissionManager.getSubmission(1);
        assertEquals("Incorrect or submission.", 2,
            submission.getOrSubmission().longValue());
        assertEquals("Incorrect modify date.", newDate.getTime(),
            submission.getModifyDate().getTime(), 1000);
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#removeSubmission(long)}</code> method.
     * </p>
     * <p>
     * if the submission to remove does not exist, should return false.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRemoveSubmission_NotFound() throws Exception {
        assertFalse("False should return.",
            submissionManager.removeSubmission(1L));
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#removeSubmission(long)}</code> method.
     * </p>
     * <p>
     * if the submission to remove is already deleted, should return false.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRemoveSubmission_AlreadyDeleted() throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 2, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)"
            });
        assertFalse("False should return.",
            submissionManager.removeSubmission(1L));
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#removeSubmission(long)}</code> method.
     * </p>
     * <p>
     * if the submission to remove is still active, should return true with the
     * submission deleted.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRemoveSubmission_Deleted() throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)"
            });
        assertTrue("True should return.", submissionManager.removeSubmission(1L));
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updateSubmissionStatus(long, long)}</code>
     * method.
     * </p>
     * <p>
     * if the submission to remove does not exist, should throw
     * EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateSubmissionStatus_NotFound() throws Exception {
        try {
            submissionManager.updateSubmissionStatus(1, 1);
            fail("expect EntityNotFoundException");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updateSubmissionStatus(long, long)}</code>
     * method.
     * </p>
     * <p>
     * if the submission to remove is already deleted, should throw
     * EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateSubmissionStatus_AlreadyDeleted()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 2, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)"
            });

        try {
            submissionManager.updateSubmissionStatus(1, 1);
            fail("expect EntityNotFoundException");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updateSubmissionStatus(long, long)}</code>
     * method.
     * </p>
     * <p>
     * if the status does not found, should throw EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateSubmissionStatus_StatusNotFound()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)"
            });

        try {
            submissionManager.updateSubmissionStatus(1, 3);
            fail("expect EntityNotFoundException");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updateSubmissionStatus(long, long)}</code>
     * method.
     * </p>
     * <p>
     * if the status does not found, should throw EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateSubmissionStatus_accuracy() throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)"
            });

        submissionManager.updateSubmissionStatus(1, 2);

        // verify
        Submission submission = submissionManager.getSubmission(1);
        assertNull("submission should not found.", submission);
    }

    /**
     * <p>
     * Unit test for <code>{@link SubmissionManagerBean#addPrize(Prize)}</code>
     * method.
     * </p>
     * <p>
     * If the prize is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPrize_nullPrize() throws Exception {
        try {
            submissionManager.addPrize(null);
            fail("Expect IllegalArgumentException.");
        } catch (EJBException e) {
            assertTrue("expect IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link SubmissionManagerBean#addPrize(Prize)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPrize_accuracy() throws Exception {
        PrizeType type = new PrizeType();
        type.setPrizeTypeId(1L);
        type.setDescription("Test");

        Prize prize = new Prize();
        prize.setAmount(10D);
        prize.setCreateDate(new Date());
        prize.setPlace(1);
        prize.setType(type);
        prize = submissionManager.addPrize(prize);

        assertNotNull("never return null.", prize);
        assertNotNull("Id is not populated.", prize.getPrizeId());
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updatePrize(Prize)}</code> method.
     * </p>
     * <p>
     * If the prize is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePrize_null() throws Exception {
        try {
            submissionManager.updatePrize(null);
            fail("Expect IllegalArgumentException.");
        } catch (EJBException e) {
            assertTrue("expect IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updatePrize(Prize)}</code> method.
     * </p>
     * <p>
     * If the prize to update does not exist, should throw
     * EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePrize_NotExist() throws Exception {
        PrizeType type = new PrizeType();
        type.setPrizeTypeId(2L);
        type.setDescription("Test");

        Prize prize = new Prize();
        prize.setAmount(10D);
        prize.setCreateDate(new Date());
        prize.setPlace(1);
        prize.setType(type);
        prize.setPrizeId(1L);

        try {
            submissionManager.updatePrize(prize);
            fail("Expect EntityNotFoundException.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updatePrize(Prize)}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdatePrize_accuracy() throws Exception {
        executeSQL(new String[] {
                INSERT_PRIZE_PREFIX + "(1, 1, 1, 1, '2008-03-16 09:00:00')"
            });

        PrizeType type = new PrizeType();
        type.setPrizeTypeId(1L);
        type.setDescription("Test");

        Prize prize = new Prize();
        prize.setAmount(10D);
        prize.setCreateDate(new Date());
        prize.setPlace(2);
        prize.setType(type);
        prize.setPrizeId(1L);

        // no exception should occurs.
        submissionManager.updatePrize(prize);

        prize = submissionManager.getPrize(1);
        assertEquals("Incorrect place.", 2, prize.getPlace().intValue());
        assertEquals("Incorrect amount.", 10.0,
            prize.getAmount().doubleValue(), 1e-3);
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#removePrize(long)}</code> method.
     * </p>
     * <p>
     * If the prize to remove does not exist, should return false.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRemovePrize_NotExist() throws Exception {
        assertFalse("prize does not exist.", submissionManager.removePrize(1L));
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#removePrize(long)}</code> method.
     * </p>
     * <p>
     * If the prize to remove exists and removed, should return true.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRemovePrize_accuracy() throws Exception {
        executeSQL(new String[] {
                INSERT_PRIZE_PREFIX + "(1, 1, 1, 1, '2008-03-16 09:00:00')"
            });
        assertTrue("prize should be removed.", submissionManager.removePrize(1L));
    }

    /**
     * <p>
     * Unit test for <code>{@link SubmissionManagerBean#getPrize(long)}</code>
     * method.
     * </p>
     * <p>
     * If the prize does not exist, should return null.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetPrize_NotFound() throws Exception {
        assertNull("The prize should be null.", submissionManager.getPrize(1L));
    }

    /**
     * <p>
     * Unit test for <code>{@link SubmissionManagerBean#getPrize(long)}</code>
     * method.
     * </p>
     * <p>
     * If the prize does not exist, should return null.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetPrize_Found() throws Exception {
        executeSQL(new String[] {
                INSERT_PRIZE_PREFIX + "(1, 1, 1, 1, '2008-03-16 09:00:00')"
            });

        Prize prize = submissionManager.getPrize(1L);
        assertNotNull("The prize should be not null.", prize);
        assertEquals("Incorrect place.", 1, prize.getPlace().intValue());
        assertEquals("Incorrect amount", 1, prize.getAmount().doubleValue(),
            1e-3);
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#addPrizeToSubmission(long, long)}</code>
     * method.
     * </p>
     * <p>
     * If the prize does not exist, should throw EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPrizeToSubmission_PrizeNotFound()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)"
            });

        try {
            submissionManager.addPrizeToSubmission(1L, 1L);
            fail("expect EntityNotFoundException.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#addPrizeToSubmission(long, long)}</code>
     * method.
     * </p>
     * <p>
     * If the submission does not exist, should throw EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPrizeToSubmission_SubmissionNotFound()
        throws Exception {
        executeSQL(new String[] {
                INSERT_PRIZE_PREFIX + "(1, 1, 1, 1, '2008-03-16 09:00:00')"
            });

        try {
            submissionManager.addPrizeToSubmission(1L, 1L);
            fail("expect EntityNotFoundException.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#addPrizeToSubmission(long, long)}</code>
     * method.
     * </p>
     * <p>
     * If the submission is deleted, should throw EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPrizeToSubmission_SubmissionDeleted()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 2, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)",
                
            INSERT_PRIZE_PREFIX + "(1, 1, 1, 1, '2008-03-16 09:00:00')"
            });

        try {
            submissionManager.addPrizeToSubmission(1L, 1L);
            fail("expect EntityNotFoundException.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#addPrizeToSubmission(long, long)}</code>
     * method.
     * </p>
     * <p>
     * If the submission rank does not exist, should throw
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPrizeToSubmission_RankNotSet() throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)",
                
            INSERT_PRIZE_PREFIX + "(1, 1, 1, 1, '2008-03-16 09:00:00')"
            });

        try {
            submissionManager.addPrizeToSubmission(1L, 1L);
            fail("expect IllegalArgumentException.");
        } catch (EJBException e) {
            assertTrue("expect IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#addPrizeToSubmission(long, long)}</code>
     * method.
     * </p>
     * <p>
     * If the submission rank and the prize place is inconsistent, should throw
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPrizeToSubmission_RankPlaceInconsist()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,2)",
                
            INSERT_PRIZE_PREFIX + "(1, 1, 1, 1, '2008-03-16 09:00:00')"
            });

        try {
            submissionManager.addPrizeToSubmission(1L, 1L);
            fail("expect IllegalArgumentException.");
        } catch (EJBException e) {
            assertTrue("expect IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#addPrizeToSubmission(long, long)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddPrizeToSubmission_accuracy() throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,1)",
                
            INSERT_PRIZE_PREFIX + "(1, 1, 1, 1, '2008-03-16 09:00:00')"
            });
        submissionManager.addPrizeToSubmission(1L, 1L);
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#removePrizeFromSubmission(long, long)}</code>
     * method.
     * </p>
     * <p>
     * If the prize does not exist, should throw EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRemovePrizeFromSubmission_PrizeNotFound()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)"
            });

        try {
            submissionManager.removePrizeFromSubmission(1L, 1L);
            fail("expect EntityNotFoundException.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#removePrizeFromSubmission(long, long)}</code>
     * method.
     * </p>
     * <p>
     * If the submission does not exist, should throw EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRemovePrizeFromSubmission_SubmissionNotFound()
        throws Exception {
        executeSQL(new String[] {
                INSERT_PRIZE_PREFIX + "(1, 1, 1, 1, '2008-03-16 09:00:00')"
            });

        try {
            submissionManager.removePrizeFromSubmission(1L, 1L);
            fail("expect EntityNotFoundException.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#removePrizeFromSubmission(long, long)}</code>
     * method.
     * </p>
     * <p>
     * If the submission is deleted, should throw EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRemovePrizeFromSubmission_SubmissionDeleted()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 2, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)",
                
            INSERT_PRIZE_PREFIX + "(1, 1, 1, 1, '2008-03-16 09:00:00')"
            });

        try {
            submissionManager.removePrizeFromSubmission(1L, 1L);
            fail("expect EntityNotFoundException.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#removePrizeFromSubmission(long, long)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRemovePrizeFromSubmission_acccuracy1()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)",
                
            INSERT_PRIZE_PREFIX + "(1, 1, 1, 1, '2008-03-16 09:00:00')",
                
            INSERT_SUBMISSION_PRIZE_PREFIX + "(1, 1, '2008-03-16 09:00:00')"
            });

        assertTrue(submissionManager.removePrizeFromSubmission(1L, 1L));
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#removePrizeFromSubmission(long, long)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRemovePrizeFromSubmission_acccuracy2()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)",
                
            INSERT_PRIZE_PREFIX + "(1, 1, 1, 1, '2008-03-16 09:00:00')"
            });

        assertFalse(submissionManager.removePrizeFromSubmission(1L, 1L));
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#addSubmissionPayment(SubmissionPayment)}</code>
     * method.
     * </p>
     * <p>
     * If the submissionPayment is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddSubmissionPayment_nullSubmissionPayment()
        throws Exception {
        try {
            submissionManager.addSubmissionPayment(null);
            fail("Expect IllegalArgumentException");
        } catch (EJBException e) {
            assertTrue(e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#addSubmissionPayment(SubmissionPayment)}</code>
     * method.
     * </p>
     * <p>
     * If the submission this submissionPayment for does not found, should throw
     * EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddSubmissionPayment_SubmissionNotFound()
        throws Exception {
        Submission submission = new Submission();
        submission.setSubmissionId(1L);

        SubmissionPayment submissionPayment = new SubmissionPayment();
        submissionPayment.setSubmission(submission);

        try {
            submissionManager.addSubmissionPayment(submissionPayment);
            fail("Expect EntityNotFoundException");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#addSubmissionPayment(SubmissionPayment)}</code>
     * method.
     * </p>
     * <p>
     * If the submission this submissionPayment for is deleted, should throw
     * EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddSubmissionPayment_SubmissionDeleted()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 2, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)"
            });

        Submission submission = new Submission();
        submission.setSubmissionId(1L);

        SubmissionPayment submissionPayment = new SubmissionPayment();
        submissionPayment.setSubmission(submission);

        try {
            submissionManager.addSubmissionPayment(submissionPayment);
            fail("Expect EntityNotFoundException");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#addSubmissionPayment(SubmissionPayment)}</code>
     * method.
     * </p>
     * <p>
     * If the submissionPayment already exists, should throw
     * EntityExistsException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddSubmissionPayment_alreadyExist()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)",
                
            INSERT_SUBMISSION_PAYMENTS_PREFIX +
                "(1, 1, 1,'2008-03-16 09:00:00')"
            });

        Submission submission = new Submission();
        submission.setSubmissionId(1L);

        PaymentStatus paymentStatus = new PaymentStatus();
        paymentStatus.setPaymentStatusId(1L);

        SubmissionPayment submissionPayment = new SubmissionPayment();
        submissionPayment.setSubmission(submission);
        submissionPayment.setStatus(paymentStatus);
        submissionPayment.setPrice(1.0);
        submissionPayment.setCreateDate(new Date());

        try {
            submissionManager.addSubmissionPayment(submissionPayment);
            fail("Expect EntityExistsException");
        } catch (EntityExistsException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#addSubmissionPayment(SubmissionPayment)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddSubmissionPayment_accuracy() throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)"
            });

        Submission submission = new Submission();
        submission.setSubmissionId(1L);

        PaymentStatus paymentStatus = new PaymentStatus();
        paymentStatus.setPaymentStatusId(1L);

        SubmissionPayment submissionPayment = new SubmissionPayment();
        submissionPayment.setSubmission(submission);
        submissionPayment.setStatus(paymentStatus);
        submissionPayment.setPrice(1.0);
        submissionPayment.setCreateDate(new Date());
        submissionPayment = submissionManager.addSubmissionPayment(submissionPayment);

        assertNotNull("Should not return null.", submissionPayment);
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updateSubmissionPayment(SubmissionPayment)}</code>
     * method.
     * </p>
     * <p>
     * If the submissionPayment is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateSubmissionPayment_nullSubmissionPayment()
        throws Exception {
        try {
            submissionManager.updateSubmissionPayment(null);
            fail("Expect IllegalArgumentException");
        } catch (EJBException e) {
            assertTrue(e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updateSubmissionPayment(SubmissionPayment)}</code>
     * method.
     * </p>
     * <p>
     * If the submission this submissionPayment for does not found, should throw
     * EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateSubmissionPayment_SubmissionNotFound()
        throws Exception {
        Submission submission = new Submission();
        submission.setSubmissionId(1L);

        SubmissionPayment submissionPayment = new SubmissionPayment();
        submissionPayment.setSubmission(submission);
        submissionPayment.setPrice(1.0);
        submissionPayment.setCreateDate(new Date());

        try {
            submissionManager.updateSubmissionPayment(submissionPayment);
            fail("Expect EntityNotFoundException");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updateSubmissionPayment(SubmissionPayment)}</code>
     * method.
     * </p>
     * <p>
     * If the submission this submissionPayment for is deleted, should throw
     * EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateSubmissionPayment_SubmissionDeleted()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 2, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)"
            });

        Submission submission = new Submission();
        submission.setSubmissionId(1L);

        SubmissionPayment submissionPayment = new SubmissionPayment();
        submissionPayment.setSubmission(submission);
        submissionPayment.setPrice(1.0);
        submissionPayment.setCreateDate(new Date());

        try {
            submissionManager.updateSubmissionPayment(submissionPayment);
            fail("Expect EntityNotFoundException");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updateSubmissionPayment(SubmissionPayment)}</code>
     * method.
     * </p>
     * <p>
     * If the submissionPayment does not exist, should throw
     * EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateSubmissionPayment_alreadyExist()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)"
            });

        Submission submission = new Submission();
        submission.setSubmissionId(1L);

        PaymentStatus paymentStatus = new PaymentStatus();
        paymentStatus.setPaymentStatusId(1L);

        SubmissionPayment submissionPayment = new SubmissionPayment();
        submissionPayment.setSubmission(submission);
        submissionPayment.setStatus(paymentStatus);
        submissionPayment.setPrice(1.0);
        submissionPayment.setCreateDate(new Date());

        try {
            submissionManager.updateSubmissionPayment(submissionPayment);
            fail("Expect EntityNotFoundException");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updateSubmissionPayment(SubmissionPayment)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateSubmissionPayment_accuracy()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)",
                
            INSERT_SUBMISSION_PAYMENTS_PREFIX +
                "(1, 1, 1,'2008-03-16 09:00:00')"
            });

        Submission submission = new Submission();
        submission.setSubmissionId(1L);

        PaymentStatus paymentStatus = new PaymentStatus();
        paymentStatus.setPaymentStatusId(1L);

        SubmissionPayment submissionPayment = new SubmissionPayment();
        submissionPayment.setSubmission(submission);
        submissionPayment.setStatus(paymentStatus);
        submissionPayment.setPrice(2.0);
        submissionPayment.setCreateDate(new Date());
        submissionManager.updateSubmissionPayment(submissionPayment);
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#getSubmissionPayment(long)}</code>
     * method.
     * </p>
     * <p>
     * if the submission payment does not exist, should return null.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetSubmissionPayment_NotFound() throws Exception {
        assertNull("Should return null.",
            submissionManager.getSubmissionPayment(1L));
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#getSubmissionPayment(long)}</code>
     * method.
     * </p>
     * <p>
     * if the submission payment is deleted, should return null.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetSubmissionPayment_Deleted() throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)",
                
            INSERT_SUBMISSION_PAYMENTS_PREFIX +
                "(1, 2, 1,'2008-03-16 09:00:00')"
            });

        assertNull("Should return null.",
            submissionManager.getSubmissionPayment(1L));
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#getSubmissionPayment(long)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetSubmissionPayment_accuracy() throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)",
                
            INSERT_SUBMISSION_PAYMENTS_PREFIX +
                "(1, 1, 1,'2008-03-16 09:00:00')"
            });

        assertNotNull("Should not return null.",
            submissionManager.getSubmissionPayment(1L));
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#addSubmissionReview(SubmissionReview)}</code>
     * method.
     * </p>
     * <p>
     * If the submissionReview is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddSubmissionReview_nullSubmissionReview()
        throws Exception {
        try {
            submissionManager.addSubmissionReview(null);
            fail("Expect IllegalArgumentException");
        } catch (EJBException e) {
            assertTrue(e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#addSubmissionReview(SubmissionReview)}</code>
     * method.
     * </p>
     * <p>
     * If the submission this submissionReview for does not found, should throw
     * EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddSubmissionReview_SubmissionNotFound()
        throws Exception {
        Submission submission = new Submission();
        submission.setSubmissionId(1L);

        SubmissionReview submissionReview = new SubmissionReview();
        submissionReview.setSubmission(submission);

        try {
            submissionManager.addSubmissionReview(submissionReview);
            fail("Expect EntityNotFoundException");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#addSubmissionReview(SubmissionReview)}</code>
     * method.
     * </p>
     * <p>
     * If the submission this submissionReview for is deleted, should throw
     * EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddSubmissionReview_SubmissionDeleted()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 2, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)"
            });

        Submission submission = new Submission();
        submission.setSubmissionId(1L);

        SubmissionReview submissionReview = new SubmissionReview();
        submissionReview.setSubmission(submission);

        try {
            submissionManager.addSubmissionReview(submissionReview);
            fail("Expect EntityNotFoundException");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#addSubmissionReview(SubmissionReview)}</code>
     * method.
     * </p>
     * <p>
     * If the submissionReview already exists, should throw
     * EntityExistsException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddSubmissionReview_alreadyExist()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)",
                
            INSERT_SUBMISSION_REVIEW_PREFIX +
                "(1, 1, 'Hello', 1, '2008-03-16 09:00:00')"
            });

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

        try {
            submissionManager.addSubmissionReview(submissionReview);
            fail("Expect EntityExistsException");
        } catch (EntityExistsException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#addSubmissionReview(SubmissionReview)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddSubmissionReview_accuracy() throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)"
            });

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

        submissionReview = submissionManager.addSubmissionReview(submissionReview);

        assertNotNull("Should not return null.", submissionReview);
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updateSubmissionReview(SubmissionReview)}</code>
     * method.
     * </p>
     * <p>
     * If the submissionReview is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateSubmissionReview_nullSubmissionReview()
        throws Exception {
        try {
            submissionManager.updateSubmissionReview(null);
            fail("Expect IllegalArgumentException");
        } catch (EJBException e) {
            assertTrue(e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updateSubmissionReview(SubmissionReview)}</code>
     * method.
     * </p>
     * <p>
     * If the submission this submissionReview for does not found, should throw
     * EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateSubmissionReview_SubmissionNotFound()
        throws Exception {
        Submission submission = new Submission();
        submission.setSubmissionId(1L);

        SubmissionReview submissionReview = new SubmissionReview();
        submissionReview.setSubmission(submission);

        try {
            submissionManager.updateSubmissionReview(submissionReview);
            fail("Expect EntityNotFoundException");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updateSubmissionReview(SubmissionReview)}</code>
     * method.
     * </p>
     * <p>
     * If the submission this submissionPayment for is deleted, should throw
     * EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateSubmissionReview_SubmissionDeleted()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 2, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)"
            });

        Submission submission = new Submission();
        submission.setSubmissionId(1L);

        SubmissionReview submissionReview = new SubmissionReview();
        submissionReview.setSubmission(submission);

        try {
            submissionManager.updateSubmissionReview(submissionReview);
            fail("Expect EntityNotFoundException");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updateSubmissionReview(SubmissionReview)}</code>
     * method.
     * </p>
     * <p>
     * If the submissionPayment does not exist, should throw
     * EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateSubmissionReview_NotExist() throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)"
            });

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

        try {
            submissionManager.updateSubmissionReview(submissionReview);
            fail("Expect EntityNotFoundException");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updateSubmissionReview(SubmissionReview)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateSubmissionReview_accuracy() throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)",
                
            INSERT_SUBMISSION_REVIEW_PREFIX +
                "(1, 1, 'Hello', 1, '2008-03-16 09:00:00')"
            });

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
        submissionManager.updateSubmissionReview(submissionReview);
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#getSubmissionReview(long)}</code>
     * method.
     * </p>
     * <p>
     * if the submission review does not exist, should return null.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetSubmissionReview_NotFound() throws Exception {
        assertNull("Should return null.",
            submissionManager.getSubmissionReview(1L));
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#getSubmissionReview(long)}</code>
     * method.
     * </p>
     * <p>
     * if the submission payment is deleted, should return null.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetSubmissionReview_Deleted() throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 2, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)",
                
            INSERT_SUBMISSION_REVIEW_PREFIX +
                "(1, 1, 'Hello', 1, '2008-03-16 09:00:00')"
            });

        assertNull("Should return null.",
            submissionManager.getSubmissionReview(1L));
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#getSubmissionReview(long)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetSubmissionReview_accuracy() throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)",
                
            INSERT_SUBMISSION_REVIEW_PREFIX +
                "(1, 1, 'Hello', 1, '2008-03-16 09:00:00')"
            });

        assertNotNull("Should not return null.",
            submissionManager.getSubmissionReview(1L));
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#removeSubmissionReview(long)}</code>
     * method.
     * </p>
     * <p>
     * If the submission review does not exist, should return false.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRemoveSubmissionReview_SubmissionNotFound()
        throws Exception {
        assertFalse(submissionManager.removeSubmissionReview(1L));
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#removeSubmissionReview(long)}</code>
     * method.
     * </p>
     * <p>
     * If the submission to review is already deleted, should return false.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRemoveSubmissionReview_SubmissionDeleted()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 2, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)",
                
            INSERT_SUBMISSION_REVIEW_PREFIX +
                "(1, 1, 'Hello', 1, '2008-03-16 09:00:00')"
            });

        assertFalse(submissionManager.removeSubmissionReview(1L));
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#removeSubmissionReview(long)}</code>
     * method.
     * </p>
     * <p>
     * If the submission to review is already deleted, should return false.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRemoveSubmissionReview_accuracy() throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)",
                
            INSERT_SUBMISSION_REVIEW_PREFIX +
                "(1, 1, 'Hello', 1, '2008-03-16 09:00:00')"
            });

        assertTrue(submissionManager.removeSubmissionReview(1L));
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#getSubmissionPrizes(long)}</code>
     * method.
     * </p>
     * <p>
     * if the submission does not exist, should throw EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetSubmissionPrizes_NotFound() throws Exception {
        try {
            submissionManager.getSubmissionPrizes(1L);
            fail("Expect EntityNotFoundException");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#getSubmissionPrizes(long)}</code>
     * method.
     * </p>
     * <p>
     * if the submission is deleted, should throw EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetSubmissionPrizes_Deleted() throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 2, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)"
            });

        try {
            submissionManager.getSubmissionPrizes(1L);
            fail("Expect EntityNotFoundException");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#getSubmissionPrizes(long)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetSubmissionPrizes_accuracy1() throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)"
            });

        List<Prize> prizes = submissionManager.getSubmissionPrizes(1L);
        assertNotNull("Should not null.", prizes);
        assertTrue("Should be empty.", prizes.isEmpty());
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#getSubmissionPrizes(long)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetSubmissionPrizes_accuracy2() throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)",
                
            INSERT_PRIZE_PREFIX + "(1, 1, 1, 1, '2008-03-16 09:00:00')",
                
            INSERT_PRIZE_PREFIX + "(2, 1, 1, 1, '2008-03-16 09:00:00')",
                
            INSERT_SUBMISSION_PRIZE_PREFIX + "(1, 1, '2008-03-16 09:00:00')",
                
            INSERT_SUBMISSION_PRIZE_PREFIX + "(1, 2, '2008-03-16 09:00:00')"
            });

        List<Prize> prizes = submissionManager.getSubmissionPrizes(1L);
        assertNotNull("Should not null.", prizes);
        assertEquals("Incorrect prizes set size.", 2, prizes.size());
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updateSubmissionResult(Submission)}</code>
     * method.
     * </p>
     * <p>
     * If the submission is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateSubmissionResult_null() throws Exception {
        try {
            submissionManager.updateSubmissionResult(null);
            fail("Expect IllegalArgumentException");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updateSubmissionResult(Submission)}</code>
     * method.
     * </p>
     * <p>
     * If the submission's rank is not set, should throw
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateSubmissionResult_MissingRank()
        throws Exception {
        try {
            submissionManager.updateSubmissionResult(new Submission());
            fail("Expect IllegalArgumentException");
        } catch (EJBException e) {
            assertTrue("Expect IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updateSubmissionResult(Submission)}</code>
     * method.
     * </p>
     * <p>
     * If the submission does not exist in database, should throw
     * EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateSubmissionResult_EntityNotFound()
        throws Exception {
        Submission submission = new Submission();
        submission.setSubmissionId(1L);
        submission.setRank(1);

        try {
            submissionManager.updateSubmissionResult(submission);
            fail("expect EntityNotFoundException");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updateSubmissionResult(Submission)}</code>
     * method.
     * </p>
     * <p>
     * If the submission is already deleted, should throw
     * EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateSubmissionResult_EntityDeleted()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 2, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)"
            });

        Submission submission = new Submission();
        submission.setSubmissionId(1L);
        submission.setRank(1);

        try {
            submissionManager.updateSubmissionResult(submission);
            fail("expect EntityNotFoundException");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updateSubmissionResult(Submission)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateSubmissionResult_accuracy1()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1)"
            });

        Submission submission = new Submission();
        submission.setSubmissionId(1L);
        submission.setRank(1);

        submissionManager.updateSubmissionResult(submission);

        submission = submissionManager.getSubmission(1L);
        assertEquals("Incorrect rank.", 1, submission.getRank().intValue());
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updateSubmissionResult(Submission)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateSubmissionResult_accuracy2()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,1)",
                
            INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(2, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,2)",
                
            INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(3, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,3)",
                
            INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(4, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,4)",
                
            INSERT_PRIZE_PREFIX + "(1, 1, 1, 1, '2008-03-16 09:00:00')",
                
            INSERT_PRIZE_PREFIX + "(2, 2, 1, 1, '2008-03-16 09:00:00')",
                
            INSERT_PRIZE_PREFIX + "(3, 3, 1, 1, '2008-03-16 09:00:00')",
                
            INSERT_PRIZE_PREFIX + "(4, 4, 1, 1, '2008-03-16 09:00:00')",
                
            INSERT_PRIZE_PREFIX + "(5, 1, 1, 1, '2008-03-16 09:00:00')",
                
            INSERT_PRIZE_PREFIX + "(6, 1, 1, 1, '2008-03-16 09:00:00')",
                
            INSERT_CONTEST_PRIZE_PREFIX + "(1, 1, '2008-03-16 09:00:00')",
                
            INSERT_CONTEST_PRIZE_PREFIX + "(1, 2, '2008-03-16 09:00:00')",
                
            INSERT_CONTEST_PRIZE_PREFIX + "(1, 3, '2008-03-16 09:00:00')",
                
            INSERT_CONTEST_PRIZE_PREFIX + "(1, 4, '2008-03-16 09:00:00')",
                
            INSERT_CONTEST_PRIZE_PREFIX + "(1, 5, '2008-03-16 09:00:00')",
                
            INSERT_CONTEST_PRIZE_PREFIX + "(1, 6, '2008-03-16 09:00:00')"
            });

        Submission submission = new Submission();
        submission.setSubmissionId(2L);
        submission.setRank(1);

        submissionManager.updateSubmissionResult(submission);

        // verification
        submission = submissionManager.getSubmission(1L);
        assertEquals("Incorrect rank.", 2, submission.getRank().intValue());

        submission = submissionManager.getSubmission(2L);
        assertEquals("Incorrect rank.", 1, submission.getRank().intValue());

        submission = submissionManager.getSubmission(3L);
        assertEquals("Incorrect rank.", 3, submission.getRank().intValue());

        submission = submissionManager.getSubmission(4L);
        assertEquals("Incorrect rank.", 4, submission.getRank().intValue());
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updateSubmissionResult(Submission)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateSubmissionResult_accuracy3()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,1)",
                
            INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(2, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,2)",
                
            INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(3, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,3)",
                
            INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(4, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,4)",
                
            INSERT_PRIZE_PREFIX + "(1, 1, 1, 1, '2008-03-16 09:00:00')",
                
            INSERT_PRIZE_PREFIX + "(2, 2, 1, 1, '2008-03-16 09:00:00')",
                
            INSERT_PRIZE_PREFIX + "(3, 3, 1, 1, '2008-03-16 09:00:00')",
                
            INSERT_PRIZE_PREFIX + "(4, 4, 1, 1, '2008-03-16 09:00:00')",
                
            INSERT_PRIZE_PREFIX + "(5, 1, 1, 1, '2008-03-16 09:00:00')",
                
            INSERT_PRIZE_PREFIX + "(6, 1, 1, 1, '2008-03-16 09:00:00')",
                
            INSERT_CONTEST_PRIZE_PREFIX + "(1, 1, '2008-03-16 09:00:00')",
                
            INSERT_CONTEST_PRIZE_PREFIX + "(1, 2, '2008-03-16 09:00:00')",
                
            INSERT_CONTEST_PRIZE_PREFIX + "(1, 3, '2008-03-16 09:00:00')",
                
            INSERT_CONTEST_PRIZE_PREFIX + "(1, 4, '2008-03-16 09:00:00')",
                
            INSERT_CONTEST_PRIZE_PREFIX + "(1, 5, '2008-03-16 09:00:00')",
                
            INSERT_CONTEST_PRIZE_PREFIX + "(1, 6, '2008-03-16 09:00:00')"
            });

        Submission submission = new Submission();
        submission.setSubmissionId(2L);
        submission.setRank(1);

        submissionManager.updateSubmissionResult(submission);

        // verification
        submission = submissionManager.getSubmission(1L);
        assertEquals("Incorrect rank.", 2, submission.getRank().intValue());

        submission = submissionManager.getSubmission(2L);
        assertEquals("Incorrect rank.", 1, submission.getRank().intValue());

        submission = submissionManager.getSubmission(3L);
        assertEquals("Incorrect rank.", 3, submission.getRank().intValue());

        submission = submissionManager.getSubmission(4L);
        assertEquals("Incorrect rank.", 4, submission.getRank().intValue());
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#updateSubmissionResult(Submission)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateSubmissionResult_accuracy4()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,1)",
                
            INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(2, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,2)",
                
            INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(3, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,3)",
                
            INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(4, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,4)",
                
            INSERT_PRIZE_PREFIX + "(1, 1, 1, 1, '2008-03-16 09:00:00')",
                
            INSERT_PRIZE_PREFIX + "(2, 2, 1, 1, '2008-03-16 09:00:00')",
                
            INSERT_PRIZE_PREFIX + "(3, 3, 1, 1, '2008-03-16 09:00:00')",
                
            INSERT_PRIZE_PREFIX + "(4, 4, 1, 1, '2008-03-16 09:00:00')",
                
            INSERT_PRIZE_PREFIX + "(5, 1, 1, 1, '2008-03-16 09:00:00')",
                
            INSERT_PRIZE_PREFIX + "(6, 1, 1, 1, '2008-03-16 09:00:00')",
                
            INSERT_CONTEST_PRIZE_PREFIX + "(1, 1, '2008-03-16 09:00:00')",
                
            INSERT_CONTEST_PRIZE_PREFIX + "(1, 2, '2008-03-16 09:00:00')",
                
            INSERT_CONTEST_PRIZE_PREFIX + "(1, 3, '2008-03-16 09:00:00')",
                
            INSERT_CONTEST_PRIZE_PREFIX + "(1, 4, '2008-03-16 09:00:00')",
                
            INSERT_CONTEST_PRIZE_PREFIX + "(1, 5, '2008-03-16 09:00:00')",
                
            INSERT_CONTEST_PRIZE_PREFIX + "(1, 6, '2008-03-16 09:00:00')"
            });

        Submission submission = new Submission();
        submission.setSubmissionId(1L);
        submission.setRank(3);

        submissionManager.updateSubmissionResult(submission);

        // verification
        submission = submissionManager.getSubmission(1L);
        assertEquals("Incorrect rank.", 3, submission.getRank().intValue());

        submission = submissionManager.getSubmission(2L);
        assertEquals("Incorrect rank.", 1, submission.getRank().intValue());

        submission = submissionManager.getSubmission(3L);
        assertEquals("Incorrect rank.", 2, submission.getRank().intValue());

        submission = submissionManager.getSubmission(4L);
        assertEquals("Incorrect rank.", 4, submission.getRank().intValue());
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#getMilestoneSubmissionsForContest(long)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetMilestoneSubmissionsForContest()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 2, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,1)",
                
            INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(2, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 2, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,2)",
                
            INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(3, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 3, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,3)",
                
            INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(4, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 3, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,4)",
                
            INSERT_PRIZE_PREFIX + "(1, 1, 1, 1, '2008-03-16 09:00:00')"
            });

        assertEquals(submissionManager.getMilestoneSubmissionsForContest(1L)
                                      .size(), 2);
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#getMilestoneSubmissionsForContest(long)}</code>
     * method. When not found the submission by given conditions. Return the
     * empty list.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetMilestoneSubmissionsForContest_NotFound()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 2, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,1)",
                
            INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(2, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 2, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,2)",
                
            INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(3, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 3, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,3)",
                
            INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(4, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 3, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,4)",
                
            INSERT_PRIZE_PREFIX + "(1, 1, 1, 1, '2008-03-16 09:00:00')"
            });

        /* Should return a empty list */
        assertEquals(submissionManager.getMilestoneSubmissionsForContest(3L)
                                      .size(), 0);
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#getFinalSubmissionsForContest(long)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetFinalSubmissionsForContest() throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 2, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,1)",
                
            INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(2, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 2, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,2)",
                
            INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(3, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 3, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,3)",
                
            INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(4, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 3, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,4)",
                
            INSERT_PRIZE_PREFIX + "(1, 1, 1, 1, '2008-03-16 09:00:00')"
            });
        List<Submission> submissions = submissionManager.getFinalSubmissionsForContest(1L);
        System.out.println(submissions.get(0).getContest().getClass());
        System.out.println(Contest.class.isInstance(submissions.get(0).getContest()));
        assertEquals(submissions.size(), 2);
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#getFinalSubmissionsForContest(long)}</code>
     * method. When not found the submission by given conditions. Return the
     * empty list.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetFinalSubmissionsForContest_NotFound()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 2, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,1)",
                
            INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(2, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 2, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,2)",
                
            INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(3, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 3, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,3)",
                
            INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(4, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 3, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,4)",
                
            INSERT_PRIZE_PREFIX + "(1, 1, 1, 1, '2008-03-16 09:00:00')"
            });

        assertEquals(submissionManager.getFinalSubmissionsForContest(2L).size(),
            0);
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#setSubmissionMilestonePrize(long, long)}</code>
     * method. When not found the submission by given conditions, throw the
     * entity not found exception
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSetSubmissionMilestonePrize_NotFound()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 2, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,1)"
            });

        try {
            submissionManager.setSubmissionMilestonePrize(76, 1);
            fail("The submission 76 does not exist");
        } catch (EntityNotFoundException enfe) {
            // excepted
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#setSubmissionMilestonePrize(long, long)}</code>
     * method. When the submission has been associated with milestonePrize,
     * could not be associated with it again.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSetSubmissionMilestonePrize_Existing()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 2, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,1)"
            });

        try {
            submissionManager.setSubmissionMilestonePrize(1, 1);
            submissionManager.setSubmissionMilestonePrize(1, 1);
            fail("The submission 1 has been associated with milestonePrize 1");
        } catch (EJBException ie) {
            assertTrue(ie.getCause() instanceof IllegalArgumentException);
            // excepted
        }
    }
    
    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#setSubmissionMilestonePrize(long, long)}</code>
     * method. 
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSetSubmissionMilestonePrize_Exceed()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 2, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,1)",
                INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(2, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 2, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,1)",
                INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(3, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 2, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,1)"
            });

        try {
            submissionManager.setSubmissionMilestonePrize(1, 1);
            submissionManager.setSubmissionMilestonePrize(2, 1);
            submissionManager.setSubmissionMilestonePrize(3, 1);
            fail("The max submission nubmer of milestonePrize is 2");
        } catch (NumberOfSubmissionsExceededException ne) {
            // excepted
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#setSubmissionMilestonePrize(long, long)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSetSubmissionMilestonePrize_fail1()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(2, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 2, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,1)"
            });

        try {
            submissionManager.setSubmissionMilestonePrize(1, 1);
            fail("The submission 1 dose not exist");
        } catch (SubmissionManagementException se) {
            // excepted
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#setSubmissionMilestonePrize(long, long)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSetSubmissionMilestonePrize_fail2()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 2, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,1)",
                
            "INSERT INTO contest_milestone_prize ( contest_milestone_prize_id," +
                "prize_type_id,number_of_submissions,create_date) VALUES(2,1,3," +
                "'2008-03-16 09:00:00')"
            });

        try {
            submissionManager.setSubmissionMilestonePrize(1, 3);
            fail("The milestonePrize 3 dose not exist");
        } catch (SubmissionManagementException se) {
            // excepted
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#setSubmissionMilestonePrize(long, long)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSetSubmissionMilestonePrize_Acurracy1()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 2, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,1)"
            });

        try {
            submissionManager.setSubmissionMilestonePrize(1, 1);
        } catch (SubmissionManagementException se) {
            fail("Occure the exception:" + se.getMessage());
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#setSubmissionMilestonePrize(long, long)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSetSubmissionMilestonePrize_Acurracy2()
        throws Exception {
        executeSQL(new String[] {
                INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 2, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,1)",
                
            INSERT_SUBMISSION_WITH_RANK_PREFIX +
                "(2, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 2, 1," +
                " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,1)"
            });

        try {
            submissionManager.setSubmissionMilestonePrize(1, 1);
            submissionManager.setSubmissionMilestonePrize(2, 1);
        } catch (SubmissionManagementException se) {
            fail("Occure the exception:" + se.getMessage());
        }
    }
}
