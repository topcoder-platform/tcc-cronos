/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.stresstests;

import com.topcoder.service.studio.contest.StudioFileType;


/**
 * <p>
 * Stress test for the class <code>StudioFileType</code>.
 * </P>
 *
 * @author WN
 * @version 1.0
 *
 */
public class StudioFileTypeStressTest extends AbstractStressTest {
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
     * Stress test for the class StudioFileType using database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testStudioFileTypePersistence() throws Exception {
        StudioFileType[] studioFileTypes = new StudioFileType[10];

        for (int i = 0; i < studioFileTypes.length; i++) {
            studioFileTypes[i] = new StudioFileType();
            studioFileTypes[i].setStudioFileType(i + 0L);
            studioFileTypes[i].setDescription("description" + i);
            studioFileTypes[i].setSort(i);
            studioFileTypes[i].setImageFile(true);
            studioFileTypes[i].setExtension("extension" + i);
        }

        long time = System.currentTimeMillis();
        session.beginTransaction();

        for (int i = 0; i < studioFileTypes.length; i++) {
            session.save(studioFileTypes[i]);
        }

        time = System.currentTimeMillis() - time;
        System.out.println("Stress test for StudioFileType took " + time + "ms");

        // check the entity saved
        assertEquals("The value should be matched.", studioFileTypes[0],
            session.get(StudioFileType.class,
                studioFileTypes[0].getStudioFileType()));
    }
}
