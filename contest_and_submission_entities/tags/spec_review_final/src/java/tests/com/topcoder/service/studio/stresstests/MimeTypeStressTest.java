/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.stresstests;

import com.topcoder.service.studio.contest.MimeType;
import com.topcoder.service.studio.contest.StudioFileType;


/**
 * <p>
 * Stress test for the class <code>MimeType</code>.
 * </P>
 *
 * @author WN
 * @version 1.0
 *
 */
public class MimeTypeStressTest extends AbstractStressTest {
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
     * Stress test for the class MimeType using database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testMimeTypePersistence() throws Exception {
        MimeType[] mimeTypes = new MimeType[10];

        for (int i = 0; i < mimeTypes.length; i++) {
            mimeTypes[i] = new MimeType();
            mimeTypes[i].setMimeTypeId(i + 0L);
            mimeTypes[i].setStudioFileType(new StudioFileType());
            mimeTypes[i].setDescription("description" + i);
        }

        long time = System.currentTimeMillis();
        session.beginTransaction();

        for (int i = 0; i < mimeTypes.length; i++) {
            session.save(mimeTypes[i]);
        }

        time = System.currentTimeMillis() - time;
        System.out.println("Stress test for MimeType took " + time + "ms");

        // check the entity saved
        assertEquals("The value should be matched.", mimeTypes[0],
            session.get(MimeType.class, mimeTypes[0].getMimeTypeId()));
    }
}
