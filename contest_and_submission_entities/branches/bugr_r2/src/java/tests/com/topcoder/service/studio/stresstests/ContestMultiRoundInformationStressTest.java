/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.stresstests;

import com.topcoder.service.studio.contest.ContestMultiRoundInformation;

/**
 * <p>
 * Stress test for the class <code>ContestGeneralInfo</code>.
 * </P>
 *
 * @author morehappiness
 * @version 1.2
 */
public class ContestMultiRoundInformationStressTest extends BaseStressTest {
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
     * Stress test for the class ContestMultiRoundInformation using database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testContestMultiRoundInformation() throws Exception {
        ContestMultiRoundInformation[] infos = new ContestMultiRoundInformation[10];
        for (int i = 0; i < infos.length; i++) {
            infos[i] = createContestMultiRoundInformation(i);
        }

        long time = System.currentTimeMillis();
        session.beginTransaction();
        for (int i = 0; i < infos.length; i++) {
            session.save(infos[i]);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Stress test for ContestMultiRoundInformation took " + time + "ms");
    }
}
