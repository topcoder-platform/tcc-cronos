/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.stresstests;

import com.topcoder.service.studio.submission.ContestResult;

import java.util.Date;


/**
 * <p>
 * Stress test for the class <code>ContestResult</code>.
 * </P>
 *
 * @author WN
 * @version 1.0
 *
 */
public class ContestResultStressTest extends AbstractStressTest {
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
     * Stress test for the class ContestResult using database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testConfigPersistence() throws Exception {
        ContestResult[] contestResults = new ContestResult[10];

        for (int i = 0; i < contestResults.length; i++) {
            contestResults[i] = new ContestResult();
            contestResults[i].setContest(createContest(i));
            contestResults[i].setSubmission(createSubmission(i));
            contestResults[i].setCreateDate(new Date());
            contestResults[i].setPlaced(i + 100);
            contestResults[i].setFinalScore(i + 0f);
        }

        long time = System.currentTimeMillis();
        session.beginTransaction();

        for (int i = 0; i < contestResults.length; i++) {
            session.save(contestResults[i]);
        }

        time = System.currentTimeMillis() - time;
        System.out.println("Stress test for ContestResult took " + time + "ms");
    }
}
