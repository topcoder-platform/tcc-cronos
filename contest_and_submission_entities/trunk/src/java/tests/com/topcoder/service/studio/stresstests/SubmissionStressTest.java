/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.stresstests;

import com.topcoder.service.studio.submission.Submission;

/**
 * <p>
 * Stress test for the class <code>Submission</code>.
 * </P>
 *
 * @author WN
 * @version 1.0
 *
 */
public class SubmissionStressTest extends AbstractStressTest {
    /**
     * <p>
     * Sets up the necessary environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * <p>
     * Tears down the environment.
     * </P>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * Stress test for the class Submission using database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSubmissionPersistence() throws Exception {
        Submission[] submissions = new Submission[10];
        for (int i = 0; i < submissions.length; i++) {
            submissions[i] = createSubmission(i);
        }

        long time = System.currentTimeMillis();
        session.beginTransaction();
        for (int i = 0; i < submissions.length; i++) {
            session.save(submissions[i]);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Stress test for Submission took " + time + "ms");

        // check the entity saved
        assertEquals("The value should be matched.", submissions[0], session.get(Submission.class, submissions[0]
                .getSubmissionId()));
    }
}
