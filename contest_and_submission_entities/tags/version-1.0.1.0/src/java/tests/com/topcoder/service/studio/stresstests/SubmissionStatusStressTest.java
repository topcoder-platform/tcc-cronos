/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.stresstests;

import com.topcoder.service.studio.submission.SubmissionStatus;


/**
 * <p>
 * Stress test for the class <code>SubmissionStatus</code>.
 * </P>
 *
 * @author WN
 * @version 1.0
 *
 */
public class SubmissionStatusStressTest extends AbstractStressTest {
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
     * Stress test for the class SubmissionStatus using database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSubmissionStatusPersistence() throws Exception {
        SubmissionStatus[] submissionStatuses = new SubmissionStatus[10];

        for (int i = 0; i < submissionStatuses.length; i++) {
            submissionStatuses[i] = new SubmissionStatus();
            submissionStatuses[i].setSubmissionStatusId(i + 0L);
            submissionStatuses[i].setDescription("description" + i);
        }

        long time = System.currentTimeMillis();
        session.beginTransaction();

        for (int i = 0; i < submissionStatuses.length; i++) {
            session.save(submissionStatuses[i]);
        }

        time = System.currentTimeMillis() - time;
        System.out.println("Stress test for SubmissionStatus took " + time + "ms");

        // check the entity saved
        assertEquals("The value should be matched.", submissionStatuses[0],
            session.get(SubmissionStatus.class,
                submissionStatuses[0].getSubmissionStatusId()));
    }
}
