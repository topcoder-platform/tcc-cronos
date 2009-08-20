/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.stresstests;

import com.topcoder.service.studio.submission.MilestonePrize;

/**
 * <p>
 * Stress test for the class <code>MilestonePrize</code>.
 * </P>
 *
 * @author morehappiness
 * @version 1.2
 *
 */
public class MilestonePrizeStressTest extends BaseStressTest {
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
     * Stress test for the class MilestonePrize using database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testMilestonePrize() throws Exception {
        MilestonePrize[] contestGeneralInfos = new MilestonePrize[10];
        for (int i = 0; i < contestGeneralInfos.length; i++) {
            contestGeneralInfos[i] = createMilestonePrize(i);
        }

        long time = System.currentTimeMillis();
        session.beginTransaction();
        for (int i = 0; i < contestGeneralInfos.length; i++) {
            session.save(contestGeneralInfos[i]);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Stress test for MilestonePrize took " + time + "ms");
    }
}
