/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.stresstests;

import com.topcoder.service.studio.contest.FilePath;

import java.util.Date;


/**
 * <p>
 * Stress test for the class <code>FilePath</code>.
 * </P>
 *
 * @author WN
 * @version 1.0
 *
 */
public class FilePathStressTest extends AbstractStressTest {
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
     * Stress test for the class FilePath using database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testFilePathPersistence() throws Exception {
        FilePath[] filePaths = new FilePath[10];

        for (int i = 0; i < filePaths.length; i++) {
            filePaths[i] = new FilePath();
            filePaths[i].setFilePathId(i + 0L);
            filePaths[i].setPath("path" + i);
            filePaths[i].setModifyDate(new Date());
        }

        long time = System.currentTimeMillis();
        session.beginTransaction();

        for (int i = 0; i < filePaths.length; i++) {
            session.save(filePaths[i]);
        }

        time = System.currentTimeMillis() - time;
        System.out.println("Stress FilePath for Config took " + time + "ms");

        // check the entity saved
        assertEquals("The value should be matched.", filePaths[0],
            session.get(FilePath.class, filePaths[0].getFilePathId()));
    }
}
