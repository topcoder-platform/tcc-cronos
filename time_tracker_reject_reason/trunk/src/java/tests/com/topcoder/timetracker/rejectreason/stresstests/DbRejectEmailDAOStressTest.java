/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.stresstests;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.rejectreason.RejectEmail;
import com.topcoder.timetracker.rejectreason.RejectEmailDAOException;
import com.topcoder.timetracker.rejectreason.RejectEmailSearchBuilder;
import com.topcoder.timetracker.rejectreason.informix.DbRejectEmailDAO;

/**
 *
 * <p>
 * Stress test for <code>DbRejectEmailDAO</code>.
 * </p>
 *
 * @author restarter
 * @version 3.2
 */
public class DbRejectEmailDAOStressTest extends TestCase {

    /**
     * <p>
     * The DbRejectEmailDAO instance to test.
     * </p>
     */
    private DbRejectEmailDAO dao;

    /**
     * <p>
     * The AuditManager instance for audit.
     * </p>
     */
    private AuditManager auditManager;

    /**
     * <p>
     * The name of the connection.
     * </p>
     */
    private String connectionName = "informix";

    /**
     * <p>
     * The name of the connection.
     * </p>
     */
    private String user = "magicpig";

    /**
     * <p>
     * The name of the connection.
     * </p>
     */
    private String modifier = "ivern";

    /**
     * <p>
     * The name of the id sequence.
     * </p>
     */
    private String idGeneratorName = "reject_reason";

    /**
     * <p>
     * The name of the id sequence.
     * </p>
     */
    private DBConnectionFactory connectionFactory;

    /**
     * <p>
     * The RejectEmail instance to persist.
     * </p>
     */
    private RejectEmail rejectEmail;

    /**
     * <p>
     * The body for the RejectEmail instance.
     * </p>
     */
    private String body = "final fix";

    /**
     * <p>
     * The companyId for the RejectEmail instance.
     * </p>
     */
    private long Id = 1;

    /**
     * <p>
     * Set up the environment. It loads the necessary namespaces.
     * </p>
     */
    protected void setUp() throws Exception {
        StressTestHelper.loadNamespaces();
        rejectEmail = new RejectEmail();
        rejectEmail.setBody(body);
        rejectEmail.setCompanyId(Id);
        auditManager = new MockedAuditManager();
        connectionFactory =
            new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        dao = new DbRejectEmailDAO(connectionFactory, connectionName, auditManager, idGeneratorName);
    }

    /**
     * <p>
     * Clean the environment.
     * </p>
     */
    protected void tearDown() throws Exception {
        StressTestHelper.cleanDatabase();
        StressTestHelper.clearNamespaces();
    }

    /**
     * <p>
     * Stress test for <code>createRejectEmail(RejectEmail, String, boolean)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateRejectEmail() throws Exception {
        System.out.println("start stress test for createRejectEmail(RejectEmail, String, boolean):");
        long current = System.currentTimeMillis();
        for (int i = 1; i <= StressTestHelper.TIMES; i++) {
            rejectEmail = new RejectEmail();
            rejectEmail.setBody(body + " i");
            rejectEmail.setCompanyId(i % 5 + 1);
            dao.createRejectEmail(rejectEmail, user, i % 2 == 0);
        }
        assertEquals("The records is not successfully inserted.", StressTestHelper.TIMES, dao
            .listRejectEmails().length);
        System.out.println("finish stress test for createRejectEmail(RejectEmail, String, boolean) in "
            + (System.currentTimeMillis() - current) + " ms.");
    }

    /**
     * <p>
     * Stress test for <code>retrieveRejectEmail(long id)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRetrieveRejectEmail() throws Exception {
        System.out.println("start stress test for retrieveRejectEmail(long id):");
        long current = System.currentTimeMillis();
        for (int i = 1; i <= StressTestHelper.TIMES; i++) {
            rejectEmail = new RejectEmail();
            rejectEmail.setBody(body + " i");
            rejectEmail.setCompanyId(i % 5 + 1);
            dao.createRejectEmail(rejectEmail, user, i % 2 == 0);
        }
        long[] ids = StressTestHelper.retrieveIds("reject_email");
        for (int i = 0; i < StressTestHelper.TIMES; i++) {
            rejectEmail = dao.retrieveRejectEmail(ids[i]);
            assertEquals("The CreationUser is not correctly retrieved", user, rejectEmail.getCreationUser());
            assertEquals("The CreationUser is not correctly retrieved", user, rejectEmail
                .getModificationUser());
        }
        System.out.println("finish stress test for retrieveRejectEmail(long id) in "
            + (System.currentTimeMillis() - current) + " ms.");
    }

    /**
     * <p>
     * Stress test for <code>updateRejectEmail(RejectEmail, String, boolean)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateRejectEmail() throws Exception {
        System.out.println("start stress test for updateRejectEmail(RejectEmail, String, boolean):");
        long current = System.currentTimeMillis();
        for (int i = 1; i <= StressTestHelper.TIMES; i++) {
            rejectEmail = new RejectEmail();
            rejectEmail.setBody(body + " i");
            rejectEmail.setCompanyId(i % 5 + 1);
            dao.createRejectEmail(rejectEmail, user, i % 2 == 0);
        }
        RejectEmail[] mails = dao.listRejectEmails();
        for (int i = 0; i < StressTestHelper.TIMES; i++) {
            mails[i].setBody(body + mails[i].getId());
            dao.updateRejectEmail(mails[i], modifier, i % 2 == 0);
        }
        mails = dao.listRejectEmails();
        for (int i = 0; i < mails.length; i++) {
            assertEquals("The id is not correctly updated", body + mails[i].getId(), mails[i].getBody());
            assertEquals("The modificationUser is not correctly updated", modifier, mails[i]
                .getModificationUser());
        }
        System.out.println("finish stress test for updateRejectEmail(RejectEmail, String, boolean) in "
            + (System.currentTimeMillis() - current) + " ms.");
    }

    /**
     * <p>
     * Stress test for <code>deleteRejectEmail(RejectEmail, String, boolean)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteRejectEmail() throws Exception {
        System.out.println("start stress test for deleteRejectEmail(RejectEmail, String, boolean):");
        long current = System.currentTimeMillis();
        for (int i = 1; i <= StressTestHelper.TIMES; i++) {
            rejectEmail.setId(i);
            rejectEmail.setBody(body + " i");
            rejectEmail.setCompanyId(i % 5 + 1);
            dao.createRejectEmail(rejectEmail, user, i % 2 == 0);
        }
        RejectEmail[] mails = dao.listRejectEmails();
        for (int i = 0; i < StressTestHelper.TIMES; i++) {
            dao.deleteRejectEmail(mails[i], modifier, i % 2 == 0);
            assertNull("The RejectEmail instance is not deleted.", dao.retrieveRejectEmail(mails[i]
                .getId()));
        }
        System.out.println("finish stress test for deleteRejectEmail(RejectEmail, String, boolean) in "
            + (System.currentTimeMillis() - current) + " ms.");
    }

    /**
     * <p>
     * Stress test for <code>listRejectEmails()</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testListRejectEmails() throws Exception {
        System.out.println("start stress test for listRejectEmails():");
        long current = System.currentTimeMillis();
        for (int i = 1; i <= StressTestHelper.TIMES; i++) {
            rejectEmail = new RejectEmail();
            rejectEmail.setBody(body + " i");
            rejectEmail.setCompanyId(i % 5 + 1);
            dao.createRejectEmail(rejectEmail, user, i % 2 == 0);
        }
        for (int i = 0; i < 10; i++) {
            assertEquals("The RejectEmail instances are not correctly retrieved.", StressTestHelper.TIMES,
                dao.listRejectEmails().length);
        }
        System.out.println("finish stress test for listRejectEmails() in "
            + (System.currentTimeMillis() - current) + " ms.");
    }

    /**
     * <p>
     * Stress test for
     * <p>
     * searchRejectEmails(Filter)
     * </p>
     * </p>
     *
     * @throws RejectEmailDAOException to JUnit
     */
    public void testSearchRejectEmails() throws RejectEmailDAOException {
        System.out.println("start stress test for searchRejectEmails(Filter):");
        long current = System.currentTimeMillis();
        for (int i = 1; i <= StressTestHelper.TIMES; i++) {
            rejectEmail = new RejectEmail();
            rejectEmail.setCompanyId(i % 5 + 1);
            rejectEmail.setBody(body + " i");
            dao.createRejectEmail(rejectEmail, user, i % 2 == 0);
        }
        RejectEmail[] mails = dao.searchRejectEmails(new RejectEmailSearchBuilder().hasCompanyIdFilter(1));
        assertEquals("The RejectEmail instances are not correctly retrieved.", StressTestHelper.TIMES / 5,
            mails.length);
        for (int i = 0; i < mails.length; i++) {
            assertEquals("The RejectEmail instances are not correctly retrieved.", 1, mails[i].getCompanyId());
        }
        assertEquals("The RejectEmail instances are not correctly retrieved.", 0, dao
            .searchRejectEmails(new RejectEmailSearchBuilder().hasCompanyIdFilter(6)).length);
        System.out.println("finish stress test for searchRejectEmails(Filter) in "
            + (System.currentTimeMillis() - current) + " ms.");
    }
}
