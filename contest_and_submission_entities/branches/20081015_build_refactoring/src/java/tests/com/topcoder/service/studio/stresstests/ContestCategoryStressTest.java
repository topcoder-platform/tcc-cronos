/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.stresstests;

import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.StudioFileType;


/**
 * <p>
 * Stress test for the class <code>ContestCategory</code>.
 * </P>
 *
 * @author WN
 * @version 1.0
 *
 */
public class ContestCategoryStressTest extends AbstractStressTest {
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
     * Stress test for the class ContestCategory using database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testContestCategoryPersistence() throws Exception {
        ContestChannel[] categories = new ContestChannel[10];

        for (int i = 0; i < categories.length; i++) {
            categories[i] = new ContestChannel();
            categories[i].setContestChannelId(i + 0L);
            categories[i].setFileType(new StudioFileType());
        }

        long time = System.currentTimeMillis();

        for (int i = 0; i < categories.length; i++) {
            session.save(categories[i]);
        }

        time = System.currentTimeMillis() - time;
        System.out.println("Stress test for ContestCategory took " + time + "ms");

        // check the entity saved
        assertEquals("The value should be matched.", categories[0],
            session.get(ContestChannel.class, categories[0].getContestChannelId()));
    }
}
