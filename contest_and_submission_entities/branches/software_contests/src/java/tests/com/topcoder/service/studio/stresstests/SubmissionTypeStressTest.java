/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.stresstests;

import com.topcoder.service.studio.submission.SubmissionType;


/**
 * <p>
 * Stress test for the class <code>SubmissionType</code>.
 * </P>
 *
 * @author WN
 * @version 1.0
 *
 */
public class SubmissionTypeStressTest extends AbstractStressTest {
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
     * Stress test for the class SubmissionType using database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSubmissionTypePersistence() throws Exception {
        SubmissionType[] submissionTypes = new SubmissionType[10];

        for (int i = 0; i < submissionTypes.length; i++) {
            submissionTypes[i] = new SubmissionType();
            submissionTypes[i].setSubmissionTypeId(i + 0L);
            submissionTypes[i].setDescription("description" + i);
        }

        long time = System.currentTimeMillis();
        session.beginTransaction();

        for (int i = 0; i < submissionTypes.length; i++) {
            session.save(submissionTypes[i]);
        }

        time = System.currentTimeMillis() - time;
        System.out.println("Stress test for SubmissionType took " + time + "ms");

        // check the entity saved
        assertEquals("The value should be matched.", submissionTypes[0],
            session.get(SubmissionType.class,
                submissionTypes[0].getSubmissionTypeId()));
    }
}
