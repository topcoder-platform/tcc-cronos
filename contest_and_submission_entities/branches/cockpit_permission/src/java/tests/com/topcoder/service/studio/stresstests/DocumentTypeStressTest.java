/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.stresstests;

import com.topcoder.service.studio.contest.DocumentType;


/**
 * <p>
 * Stress test for the class <code>DocumentType</code>.
 * </P>
 *
 * @author WN
 * @version 1.0
 *
 */
public class DocumentTypeStressTest extends AbstractStressTest {
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
     * Stress test for the class DocumentType using database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testDocumentTypePersistence() throws Exception {
        DocumentType[] docTypes = new DocumentType[10];

        for (int i = 0; i < docTypes.length; i++) {
            docTypes[i] = new DocumentType();
            docTypes[i].setDocumentTypeId(i + 0L);
            docTypes[i].setDescription("description" + i);
        }

        long time = System.currentTimeMillis();
        session.beginTransaction();

        for (int i = 0; i < docTypes.length; i++) {
            session.save(docTypes[i]);
        }

        time = System.currentTimeMillis() - time;
        System.out.println("Stress test for DocumentType took " + time + "ms");

        // check the entity saved
        assertEquals("The value should be matched.", docTypes[0],
            session.get(DocumentType.class, docTypes[0].getDocumentTypeId()));
    }
}
