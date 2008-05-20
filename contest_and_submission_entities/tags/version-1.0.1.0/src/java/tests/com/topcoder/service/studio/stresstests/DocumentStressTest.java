/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.stresstests;

import java.util.Date;

import com.topcoder.service.studio.contest.Document;
import com.topcoder.service.studio.contest.FilePath;
import com.topcoder.service.studio.contest.MimeType;

/**
 * <p>
 * Stress test for the class <code>Document</code>.
 * </P>
 *
 * @author WN
 * @version 1.0
 *
 */
public class DocumentStressTest extends AbstractStressTest {
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
     * Stress test for the class Document using database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testDocumentPersistence() throws Exception {
        Document[] docs = new Document[10];
        for (int i = 0; i < docs.length; i++) {
            docs[i] = new Document();
            docs[i].setDocumentId(i + 0l);
            docs[i].setPath(new FilePath());
            docs[i].setOriginalFileName("originalFileName" + i);
            docs[i].setSystemFileName("systemFileName" + i);
            docs[i].setDocumentId(i + 11l);
            docs[i].setMimeType(new MimeType());
            docs[i].setCreateDate(new Date());
        }

        long time = System.currentTimeMillis();
        session.beginTransaction();
        for (int i = 0; i < docs.length; i++) {
            session.save(docs[i]);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Stress test for Document took " + time + "ms");

        // check the entity saved
        assertEquals("The value should be matched.", docs[0], session.get(Document.class, docs[0].getDocumentId()));
    }
}
