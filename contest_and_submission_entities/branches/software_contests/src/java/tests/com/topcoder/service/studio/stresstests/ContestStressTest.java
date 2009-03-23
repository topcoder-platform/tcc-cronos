/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.stresstests;

import com.topcoder.service.studio.contest.Contest;

/**
 * <p>
 * Stress test for the class <code>Contest</code>.
 * </P>
 *
 * @author WN
 * @version 1.0
 *
 */
public class ContestStressTest extends AbstractStressTest {
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
     * Stress test for the class Contest using database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testContestPersistence() throws Exception {
        Contest[] contests = new Contest[10];
        for (int i = 0; i < contests.length; i++) {
            contests[i] = createContest(i);
        }

        long time = System.currentTimeMillis();

        for (int i = 0; i < contests.length; i++) {
            session.save(contests[i]);
        }

        time = System.currentTimeMillis() - time;
        System.out.println("Stress test for Contest took " + time + "ms");

        // check the entity saved
        assertEquals("The value should be matched.", contests[0], session.get(Contest.class, contests[0]
                .getContestId()));
    }
}
