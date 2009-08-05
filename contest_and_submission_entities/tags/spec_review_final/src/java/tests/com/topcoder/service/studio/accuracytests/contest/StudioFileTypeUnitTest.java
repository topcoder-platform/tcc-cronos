/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.accuracytests.contest;

import com.topcoder.service.studio.accuracytests.HibernateUtil;
import com.topcoder.service.studio.contest.MimeType;
import com.topcoder.service.studio.contest.StudioFileType;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.hibernate.Session;


/**
 * <p>
 * Accuracy test for <code>StudioFileType</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class StudioFileTypeUnitTest extends TestCase {
    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(StudioFileTypeUnitTest.class);
    }

    /**
     * <p>
     * Test the mapping of this entity.
     * </p>
     */
    public void testStudioFileTypeMapping() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();

            StudioFileType studioFileType = new StudioFileType();
            studioFileType.setDescription("desc");
            studioFileType.setExtension("ext");
            studioFileType.setImageFile(true);
            studioFileType.setSort(new Integer(1));
            session.save(studioFileType);

            MimeType mimeType = new MimeType();
            mimeType.setDescription("desc1");
            mimeType.setStudioFileType(studioFileType);
            session.save(mimeType);

            studioFileType.getMimeTypes().add(mimeType);
            session.merge(studioFileType);

            StudioFileType persisted = (StudioFileType) session.load(StudioFileType.class,
                    studioFileType.getStudioFileType());
            assertEquals("The mimetype mapping is wrong.", 1,
                persisted.getMimeTypes().size());

            session.delete(mimeType);
            session.delete(studioFileType);

            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }
}
