/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.stresstests;

import com.topcoder.service.studio.contest.ContestSpecifications;

/**
 * <p>
 * Stress test for the class <code>ContestSpecifications</code>.
 * </P>
 *
 * @author morehappiness
 * @version 1.2
 *
 */
public class ContestSpecificationsStressTest extends BaseStressTest {
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
     * Stress test for the class ContestSpecifications using database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testContestGeneralInfo() throws Exception {
        ContestSpecifications[] specifications = new ContestSpecifications[10];
        for (int i = 0; i < specifications.length; i++) {
            specifications[i] = createContestSpecifications(i);
        }

        long time = System.currentTimeMillis();
        session.beginTransaction();
        for (int i = 0; i < specifications.length; i++) {
            session.save(specifications[i]);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Stress test for ContestSpecifications took " + time + "ms");
    }
}
