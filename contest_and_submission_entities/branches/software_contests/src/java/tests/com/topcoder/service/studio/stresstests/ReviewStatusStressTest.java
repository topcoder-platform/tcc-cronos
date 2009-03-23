/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.stresstests;

import com.topcoder.service.studio.submission.ReviewStatus;


/**
 * <p>
 * Stress test for the class <code>ReviewStatus</code>.
 * </P>
 *
 * @author WN
 * @version 1.0
 *
 */
public class ReviewStatusStressTest extends AbstractStressTest {
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
     * Stress test for the class ReviewStatus using database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testConfigPersistence() throws Exception {
        ReviewStatus[] reviewStatuses = new ReviewStatus[10];

        for (int i = 0; i < reviewStatuses.length; i++) {
            reviewStatuses[i] = new ReviewStatus();
            reviewStatuses[i].setReviewStatusId(i + 0L);
            reviewStatuses[i].setDescription("description" + i);
        }

        long time = System.currentTimeMillis();
        session.beginTransaction();

        for (int i = 0; i < reviewStatuses.length; i++) {
            session.save(reviewStatuses[i]);
        }

        time = System.currentTimeMillis() - time;
        System.out.println("Stress test for ReviewStatus took " + time + "ms");

        // check the entity saved
        assertEquals("The value should be matched.", reviewStatuses[0],
            session.get(ReviewStatus.class,
                reviewStatuses[0].getReviewStatusId()));
    }
}
