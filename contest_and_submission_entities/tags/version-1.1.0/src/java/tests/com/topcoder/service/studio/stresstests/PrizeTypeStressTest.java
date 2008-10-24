/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.stresstests;

import com.topcoder.service.studio.submission.PrizeType;


/**
 * <p>
 * Stress test for the class <code>PrizeType</code>.
 * </P>
 *
 * @author WN
 * @version 1.0
 *
 */
public class PrizeTypeStressTest extends AbstractStressTest {
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
     * Stress test for the class PrizeType using database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testPrizeTypePersistence() throws Exception {
        PrizeType[] prizeTypes = new PrizeType[10];

        for (int i = 0; i < prizeTypes.length; i++) {
            prizeTypes[i] = new PrizeType();
            prizeTypes[i].setPrizeTypeId(i + 0L);
            prizeTypes[i].setDescription("description" + i);
        }

        long time = System.currentTimeMillis();
        session.beginTransaction();

        for (int i = 0; i < prizeTypes.length; i++) {
            session.save(prizeTypes[i]);
        }

        time = System.currentTimeMillis() - time;
        System.out.println("Stress test for PrizeType took " + time + "ms");

        // check the entity saved
        assertEquals("The value should be matched.", prizeTypes[0],
            session.get(PrizeType.class, prizeTypes[0].getPrizeTypeId()));
    }
}
