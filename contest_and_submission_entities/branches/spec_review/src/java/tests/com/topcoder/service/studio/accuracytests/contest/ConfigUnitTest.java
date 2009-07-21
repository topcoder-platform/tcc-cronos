/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.accuracytests.contest;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.hibernate.Query;
import org.hibernate.Session;

import com.topcoder.service.studio.accuracytests.HibernateUtil;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.ContestConfig;
import com.topcoder.service.studio.contest.ContestProperty;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.contest.StudioFileType;


/**
 * <p>
 * Accuracy test for <code>Config</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ConfigUnitTest extends TestCase {
    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(ConfigUnitTest.class);
    }

    /**
     * <p>
     * Test the mapping of this entity.
     * </p>
     */
    public void testConfigMapping() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();

            StudioFileType fileType = new StudioFileType();
            fileType.setSort(new Integer(1));
            fileType.setImageFile(true);
            fileType.setDescription("desc");
            fileType.setExtension(".jar");
            session.save(fileType);

            ContestChannel category = new ContestChannel();
            category.setFileType(fileType);
            session.save(category);

            ContestType contestType = new ContestType();
            contestType.setDescription("desc");
            contestType.setRequirePreviewFile(false);
            contestType.setRequirePreviewImage(true);
            session.save(contestType);

            ContestStatus status = new ContestStatus();
            status.setDescription("description");
            status.setName("name");
            session.save(status);

            Contest contest = new Contest();
            contest.setContestChannel(category);
            contest.setContestType(contestType);
            contest.setStatus(status);
            contest.setName("contest1");
            contest.setProjectId(new Long(1));
            contest.setTcDirectProjectId(new Long(2));
            contest.setStartDate(new Date());
            contest.setEndDate(new Date());
            contest.setWinnerAnnoucementDeadline(new Date());
            contest.setCreatedUser(new Long(1));
            session.save(contest);

            ContestProperty property = new ContestProperty();
            property.setDescription("description");
            session.save(property);

            ContestConfig entity = new ContestConfig();
            entity.setContest(contest);
            entity.setProperty(property);

            entity.setValue("value");

            // save the entity
            session.save(entity);

            // load the persisted object
            Query query = session.createQuery("from ContestConfig as c");
            ContestConfig persisted = (ContestConfig) query.list().get(0);

            assertEquals("Failed to persist - value mismatch", entity.getValue(), persisted.getValue());
            assertEquals("Failed to persist - contest mismatch", entity.getContest(), persisted.getContest());
            assertEquals("Failed to persist - property mismatch", entity.getProperty(), persisted.getProperty());

            // update the entity
            entity.setValue("new value");
            session.merge(entity);

            persisted = (ContestConfig) query.list().get(0);
            assertEquals("Failed to update - value mismatch", entity.getValue(), persisted.getValue());

            // delete the entity
            session.delete(entity);
            session.delete(property);
            session.delete(contest);
            session.delete(status);
            session.delete(contestType);
            session.delete(category);
            session.delete(fileType);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }
}
