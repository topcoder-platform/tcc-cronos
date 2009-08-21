/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission.accuracytests;

import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;

import com.topcoder.service.studio.submission.Submission;
import com.topcoder.service.studio.submission.SubmissionManager;


/**
 * Accuracy test case for the SubmissionManagerBean class.
 */
public class SubmissionManagerBeanAccV12Tests extends BaseTestCase {
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
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        executeScriptFile("/clean.sql");
        executeScriptFile("/prepare.sql");

        Properties env = new Properties();
        // env.setProperty(Context.SECURITY_PRINCIPAL, "admin");
        // env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        // env.setProperty(Context.INITIAL_CONTEXT_FACTORY,
        // "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        env.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
        env.setProperty("java.naming.provider.url", "localhost:1099");
        ctx = new InitialContext(env);

        submissionManager = (SubmissionManager) ctx.lookup("remote/SubmissionManagerBean");
    }

    /**
     * <p>
     * Test the getFinalSubmissionsForContest method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetFinalSubmissionsForContest() throws Exception {
        long contestId = 1;
        List<Submission> submissions = submissionManager.getFinalSubmissionsForContest(contestId);
        assertTrue("There should be no submission for the milestone of contest.", submissions.isEmpty());

        //insert data for testing
        executeSQL(new String[] {
                "INSERT INTO submission (submission_id, submission_status_id, submitter_id, contest_id, create_date," +
                " original_file_name, system_file_name, submission_type_id, mime_type_id, submission_date," +
                " height, width, modify_date, or_submission_id, path_id, rank) VALUES (1, 1, 1, 1," +
                " '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1, '2008-03-16 09:00:00'," +
                " 1, 1, '2008-03-16 09:00:00', 1, 1, 1)",
                
            "INSERT INTO submission (submission_id, submission_status_id, submitter_id, contest_id, create_date," +
                " original_file_name, system_file_name, submission_type_id, mime_type_id, submission_date," +
                " height, width, modify_date, or_submission_id, path_id, rank) VALUES (2, 2, 1, 1," +
                " '2008-03-16 09:00:00', 'test.jar', 'test.jar', 2, 1, '2008-03-16 09:00:00'," +
                " 1, 1, '2008-03-16 09:00:00', 1, 1, 2)",
                
            "INSERT INTO submission (submission_id, submission_status_id, submitter_id, contest_id, create_date," +
                " original_file_name, system_file_name, submission_type_id, mime_type_id, submission_date," +
                " height, width, modify_date, or_submission_id, path_id, rank) VALUES (3, 1, 1, 1," +
                " '2008-03-16 09:00:00', 'test.jar', 'test.jar', 3, 1, '2008-03-16 09:00:00'," +
                " 1, 1, '2008-03-16 09:00:00', 1, 1, 3)",
                
            "INSERT INTO submission (submission_id, submission_status_id, submitter_id, contest_id, create_date," +
                " original_file_name, system_file_name, submission_type_id, mime_type_id, submission_date," +
                " height, width, modify_date, or_submission_id, path_id, rank) VALUES (4, 2, 1, 1," +
                " '2008-03-16 09:00:00', 'test.jar', 'test.jar', 3, 1, '2008-03-16 09:00:00'," +
                " 1, 1, '2008-03-16 09:00:00', 1, 1, 4)"
            });
        assertEquals(1, submissionManager.getFinalSubmissionsForContest(contestId).size());
    }

    /**
     * <p>
     * Test the getMilestoneSubmissionsForContest method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetMilestoneSubmissionsForContest()
        throws Exception {
        long contestId = 1;
        List<Submission> submissions = submissionManager.getMilestoneSubmissionsForContest(contestId);
        assertTrue("There should be no submission for the milestone of contest.", submissions.isEmpty());

        //insert data for testing
        executeSQL(new String[] {
                "INSERT INTO submission (submission_id, submission_status_id, submitter_id, contest_id, create_date," +
                " original_file_name, system_file_name, submission_type_id, mime_type_id, submission_date," +
                " height, width, modify_date, or_submission_id, path_id, rank) VALUES (1, 1, 1, 1," +
                " '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1, '2008-03-16 09:00:00'," +
                " 1, 1, '2008-03-16 09:00:00', 1, 1, 1)",
                
            "INSERT INTO submission (submission_id, submission_status_id, submitter_id, contest_id, create_date," +
                " original_file_name, system_file_name, submission_type_id, mime_type_id, submission_date," +
                " height, width, modify_date, or_submission_id, path_id, rank) VALUES (2, 2, 1, 1," +
                " '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1, '2008-03-16 09:00:00'," +
                " 1, 1, '2008-03-16 09:00:00', 1, 1, 2)",
                
            "INSERT INTO submission (submission_id, submission_status_id, submitter_id, contest_id, create_date," +
                " original_file_name, system_file_name, submission_type_id, mime_type_id, submission_date," +
                " height, width, modify_date, or_submission_id, path_id, rank) VALUES (3, 1, 1, 1," +
                " '2008-03-16 09:00:00', 'test.jar', 'test.jar', 2, 1, '2008-03-16 09:00:00'," +
                " 1, 1, '2008-03-16 09:00:00', 1, 1, 3)",
                
            "INSERT INTO submission (submission_id, submission_status_id, submitter_id, contest_id, create_date," +
                " original_file_name, system_file_name, submission_type_id, mime_type_id, submission_date," +
                " height, width, modify_date, or_submission_id, path_id, rank) VALUES (4, 2, 1, 1," +
                " '2008-03-16 09:00:00', 'test.jar', 'test.jar', 2, 1, '2008-03-16 09:00:00'," +
                " 1, 1, '2008-03-16 09:00:00', 1, 1, 4)"
            });
        assertEquals(1, submissionManager.getMilestoneSubmissionsForContest(contestId).size());
    }

    /**
     * test the setSubmissionMilestonePrize method.
     *
     * @throws Exception into JUnit
     */
    public void testSetSubmissionMilestonePrize() throws Exception {
        long submissionId = 1;
        long milestonePrizeId = 1;
        executeSQL(new String[] {
                "INSERT INTO submission (submission_id, submission_status_id, submitter_id, contest_id, create_date," +
                " original_file_name, system_file_name, submission_type_id, mime_type_id, submission_date," +
                " height, width, modify_date, or_submission_id, path_id, rank) VALUES (1, 1, 1, 1," +
                " '2008-03-16 09:00:00', 'test.jar', 'test.jar', 2, 1, '2008-03-16 09:00:00'," +
                " 1, 1, '2008-03-16 09:00:00', 1, 1, 1)"
            });
        submissionManager.setSubmissionMilestonePrize(submissionId, milestonePrizeId);
    }

    /**
     * <p>
     * Tear down the testing environment.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        executeScriptFile("/clean.sql");

        super.tearDown();
    }
}
