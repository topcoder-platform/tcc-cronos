/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.stresstests;

import com.topcoder.service.studio.contest.ContestType;

/**
 * <p>
 * Stress test for the class <code>ContestType</code>.
 * </P>
 *
 * @author WN
 * @version 1.0
 *
 */
public class ContestTypeStressTest extends AbstractStressTest {
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
     * Stress test for the class ContestType using database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testContestTypePersistence() throws Exception {
        ContestType[] types = new ContestType[10];
        for (int i = 0; i < types.length; i++) {
            types[i] = new ContestType();
            types[i].setContestType(i + 0l);
        }

        long time = System.currentTimeMillis();
        session.beginTransaction();
        for (int i = 0; i < types.length; i++) {
            session.save(types[i]);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Stress test for ContestType took " + time + "ms");

        // check the entity saved
        assertEquals("The value should be matched.", types[0], session.get(ContestType.class, types[0]
                .getContestType()));
    }
}
