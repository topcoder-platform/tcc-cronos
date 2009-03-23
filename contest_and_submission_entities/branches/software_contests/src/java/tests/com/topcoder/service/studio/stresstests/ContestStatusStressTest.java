/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.stresstests;

import com.topcoder.service.studio.contest.ContestStatus;

/**
 * <p>
 * Stress test for the class <code>ContestStatus</code>.
 * </P>
 *
 * @author WN
 * @version 1.0
 *
 */
public class ContestStatusStressTest extends AbstractStressTest {
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
     * Stress test for the class ContestStatus using database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testContestStatusPersistence() throws Exception {
        ContestStatus[] contestStatuses = new ContestStatus[10];

        for (int i = 0; i < contestStatuses.length; i++) {
            contestStatuses[i] = new ContestStatus();
            contestStatuses[i].setContestStatusId(i + 0l);
            contestStatuses[i].setDescription("description" + i);
            contestStatuses[i].setName("name" + i);
        }

        long time = System.currentTimeMillis();
        session.beginTransaction();

        for (int i = 0; i < contestStatuses.length; i++) {
            session.save(contestStatuses[i]);
        }

        time = System.currentTimeMillis() - time;
        System.out.println("Stress test for ContestStatus took " + time + "ms");

        // check the entity saved
        assertEquals("The value should be matched.", contestStatuses[0], session.get(ContestStatus.class,
                contestStatuses[0].getContestStatusId()));
    }
}
