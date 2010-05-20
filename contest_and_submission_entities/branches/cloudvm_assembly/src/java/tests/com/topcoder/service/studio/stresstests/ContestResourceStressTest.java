/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.stresstests;

import com.topcoder.service.studio.contest.ContestResource;

/**
 * <p>
 * Stress test for the class <code>ContestResource</code>.
 * </P>
 *
 * @author morehappiness
 * @version 1.2
 *
 */
public class ContestResourceStressTest extends BaseStressTest {
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
     * Stress test for the class ContestResource using database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testContestResource() throws Exception {
        ContestResource[] resources = new ContestResource[10];
        for (int i = 0; i < resources.length; i++) {
            resources[i] = createContestResource(i);
        }

        long time = System.currentTimeMillis();
        session.beginTransaction();
        for (int i = 0; i < resources.length; i++) {
            session.save(resources[i]);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Stress test for ContestResource took " + time + "ms");
    }
}
