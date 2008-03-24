/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.stresstests;

import java.util.Date;

import com.topcoder.service.studio.submission.Prize;
import com.topcoder.service.studio.submission.PrizeType;

/**
 * <p>
 * Stress test for the class <code>Prize</code>.
 * </P>
 *
 * @author WN
 * @version 1.0
 *
 */
public class PrizeStressTest extends AbstractStressTest {
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
     * Stress test for the class Prize using database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testPrizePersistence() throws Exception {
        Prize[] prizes = new Prize[10];
        for (int i = 0; i < prizes.length; i++) {
            prizes[i] = new Prize();
            prizes[i].setPrizeId(i + 0l);
            prizes[i].setPlace(i);
            prizes[i].setAmount(i + 0d);
            prizes[i].setType(new PrizeType());
            prizes[i].setCreateDate(new Date());
        }

        long time = System.currentTimeMillis();
        session.beginTransaction();
        for (int i = 0; i < prizes.length; i++) {
            session.save(prizes[i]);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Stress test for Prize took " + time + "ms");

        // check the entity saved
        assertEquals("The value should be matched.", prizes[0], session.get(Prize.class, prizes[0].getPrizeId()));
    }
}
