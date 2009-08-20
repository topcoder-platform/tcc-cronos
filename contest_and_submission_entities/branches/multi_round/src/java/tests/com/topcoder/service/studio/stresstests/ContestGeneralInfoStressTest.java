/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.stresstests;

import com.topcoder.service.studio.contest.ContestGeneralInfo;

/**
 * <p>
 * Stress test for the class <code>ContestGeneralInfo</code>.
 * </P>
 *
 * @author morehappiness
 * @version 1.2
 *
 */
public class ContestGeneralInfoStressTest extends BaseStressTest {
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
     * Stress test for the class ContestGeneralInfo using database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testContestGeneralInfo() throws Exception {
        ContestGeneralInfo[] contestGeneralInfos = new ContestGeneralInfo[10];
        for (int i = 0; i < contestGeneralInfos.length; i++) {
            contestGeneralInfos[i] = createContestGeneralInfo(i);
        }

        long time = System.currentTimeMillis();
        session.beginTransaction();
        for (int i = 0; i < contestGeneralInfos.length; i++) {
            session.save(contestGeneralInfos[i]);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Stress test for ContestGeneralInfo took " + time + "ms");
    }
}
