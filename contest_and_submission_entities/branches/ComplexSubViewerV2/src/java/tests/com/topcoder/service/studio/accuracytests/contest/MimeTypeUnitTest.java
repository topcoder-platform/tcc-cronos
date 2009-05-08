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
 * Accuracy test for <code>MimeType</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MimeTypeUnitTest extends TestCase {
    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(MimeTypeUnitTest.class);
    }

    /**
     * <p>
     * Test the mapping for this entity.
     * </p>
     */
    public void testMimeTypeMapping() {
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

            MimeType persisted = (MimeType) session.get(MimeType.class,
                    mimeType.getMimeTypeId());

            assertNotNull("The persisted should not be null.", persisted);
            assertNotNull("The studio file type should not be null.",
                persisted.getStudioFileType());
            assertEquals("The studio file type mapping is wrong.", "desc",
                persisted.getStudioFileType().getDescription());

            session.delete(mimeType);
            session.delete(studioFileType);

            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }
}
