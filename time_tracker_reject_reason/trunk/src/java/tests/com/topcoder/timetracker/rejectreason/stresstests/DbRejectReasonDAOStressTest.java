/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.stresstests;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.rejectreason.RejectReason;
import com.topcoder.timetracker.rejectreason.RejectReasonDAOException;
import com.topcoder.timetracker.rejectreason.RejectReasonSearchBuilder;
import com.topcoder.timetracker.rejectreason.informix.DbRejectReasonDAO;

/**
 *
 * <p>
 * Stress test for <code>DbRejectReasonDAO</code>.
 * </p>
 *
 * @author restarter
 * @version 3.2
 */
public class DbRejectReasonDAOStressTest extends TestCase {

    /**
     * <p>
     * The DbRejectEmailDAO instance to test.
     * </p>
     */
    private DbRejectReasonDAO dao;

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
     * The RejectReason instance to persist.
     * </p>
     */
    private RejectReason rejectReason;

    /**
     * <p>
     * The description for the RejectReason instance.
     * </p>
     */
    private String description = "final fix.";

    /**
     * <p>
     * The companyId for the RejectReason instance.
     * </p>
     */
    private long Id = 10000;

    /**
     * <p>
     * Set up the environment. It loads the necessary namespaces.
     * </p>
     */
    protected void setUp() throws Exception {
        StressTestHelper.loadNamespaces();
        rejectReason = new RejectReason();
        rejectReason.setDescription(description);
        rejectReason.setCompanyId(Id);
        auditManager = new MockedAuditManager();
        connectionFactory =
            new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        dao = new DbRejectReasonDAO(connectionFactory, connectionName, auditManager, idGeneratorName);
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
     * Stress test for <code>createRejectReason(RejectReason, String, boolean)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateRejectReason() throws Exception {
        System.out.println("start stress test for createRejectReason(RejectReason, String, boolean):");
        long current = System.currentTimeMillis();
        for (int i = 1; i <= StressTestHelper.TIMES; i++) {
            rejectReason = new RejectReason();
            rejectReason.setDescription(description + " i");
            rejectReason.setCompanyId(i % 5 + 1);
            dao.createRejectReason(rejectReason, user, i % 2 == 0);
        }
        assertEquals("The records is not successfully inserted.", StressTestHelper.TIMES, dao
            .listRejectReasons().length);
        System.out.println("finish stress test for createRejectReason(RejectReason, String, boolean) in "
            + (System.currentTimeMillis() - current) + " ms.");
    }

    /**
     * <p>
     * Stress test for <code>retrieveRejectReason(long id)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRetrieveRejectReason() throws Exception {
        System.out.println("start stress test for retrieveRejectReason(long id):");
        long current = System.currentTimeMillis();
        for (int i = 1; i <= StressTestHelper.TIMES; i++) {
            rejectReason = new RejectReason();
            rejectReason.setDescription(description + " i");
            rejectReason.setCompanyId(i % 5 + 1);
            dao.createRejectReason(rejectReason, user, i % 2 == 0);
        }
        long[] ids = StressTestHelper.retrieveIds("reject_reason");
        for (int i = 0; i < StressTestHelper.TIMES; i++) {
            rejectReason = dao.retrieveRejectReason(ids[i]);
            assertEquals("The CreationUser is not correctly retrieved", user, rejectReason.getCreationUser());
            assertEquals("The CreationUser is not correctly retrieved", user, rejectReason
                .getModificationUser());
        }
        System.out.println("finish stress test for retrieveRejectReason(long id) in "
            + (System.currentTimeMillis() - current) + " ms.");
    }

    /**
     * <p>
     * Stress test for <code>updateRejectReason(RejectReason, String, boolean)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateRejectReason() throws Exception {
        System.out.println("start stress test for updateRejectReason(RejectReason, String, boolean):");
        long current = System.currentTimeMillis();
        for (int i = 1; i <= StressTestHelper.TIMES; i++) {
            rejectReason = new RejectReason();
            rejectReason.setDescription(description + " i");
            rejectReason.setCompanyId(i % 5 + 1);
            dao.createRejectReason(rejectReason, user, i % 2 == 0);
        }
        RejectReason[] reasons = dao.listRejectReasons();
        for (int i = 0; i < StressTestHelper.TIMES; i++) {
            reasons[i].setDescription(description + reasons[i].getId());
            dao.updateRejectReason(reasons[i], modifier, i % 2 == 0);
        }
        reasons = dao.listRejectReasons();
        for (int i = 0; i < reasons.length; i++) {
            assertEquals("The id is not correctly updated", description + reasons[i].getId(), reasons[i]
                .getDescription());
            assertEquals("The modificationUser is not correctly updated", modifier, reasons[i]
                .getModificationUser());
        }

        System.out.println("finish stress test for updateRejectReason(RejectReason, String, boolean) in "
            + (System.currentTimeMillis() - current) + " ms.");
    }

    /**
     * <p>
     * Stress test for <code>deleteRejectReason(RejectReason, String, boolean)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteRejectReason() throws Exception {
        System.out.println("start stress test for deleteRejectReason(RejectReason, String, boolean):");
        long current = System.currentTimeMillis();
        for (int i = 1; i <= StressTestHelper.TIMES; i++) {
            rejectReason = new RejectReason();
            rejectReason.setDescription(description + " i");
            rejectReason.setCompanyId(i % 5 + 1);
            rejectReason.setId(i);
            dao.createRejectReason(rejectReason, user, i % 2 == 0);
        }
        RejectReason[] reasons = dao.listRejectReasons();
        for (int i = 0; i < StressTestHelper.TIMES; i++) {
            dao.deleteRejectReason(reasons[i], modifier, i % 2 == 0);
            assertNull("The RejectReason instance is not deleted.", dao.retrieveRejectReason(reasons[i]
                .getId()));
        }
        System.out.println("finish stress test for deleteRejectReason(RejectReason, String, boolean) in "
            + (System.currentTimeMillis() - current) + " ms.");
    }

    /**
     * <p>
     * Stress test for <code>listRejectReasons()</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testListRejectReasons() throws Exception {
        System.out.println("start stress test for listRejectReasons():");
        long current = System.currentTimeMillis();
        for (int i = 1; i <= StressTestHelper.TIMES; i++) {
            rejectReason = new RejectReason();
            rejectReason.setDescription(description + " i");
            rejectReason.setCompanyId(i % 5 + 1);
            dao.createRejectReason(rejectReason, user, i % 2 == 0);
        }
        for (int i = 0; i < 10; i++) {
            assertEquals("The RejectReason instances are not correctly retrieved.", StressTestHelper.TIMES,
                dao.listRejectReasons().length);
        }
        System.out.println("finish stress test for listRejectReasons() in "
            + (System.currentTimeMillis() - current) + " ms.");
    }

    /**
     * <p>
     * Stress test for
     * <p>
     * searchRejectReasons(Filter)
     * </p>
     * </p>
     *
     * @throws RejectReasonDAOException to JUnit
     */
    public void testSearchRejectReasons() throws RejectReasonDAOException {
        System.out.println("start stress test for searchRejectReasons(Filter):");
        long current = System.currentTimeMillis();
        for (int i = 1; i <= StressTestHelper.TIMES; i++) {
            rejectReason = new RejectReason();
            rejectReason.setDescription(description + " i");
            rejectReason.setCompanyId(i % 5 + 1);
            dao.createRejectReason(rejectReason, user, i % 2 == 0);
        }
        RejectReason[] mails = dao.searchRejectReasons(new RejectReasonSearchBuilder().hasCompanyIdFilter(1));
        assertEquals("The RejectReason instances are not correctly retrieved.", StressTestHelper.TIMES / 5,
            mails.length);
        for (int i = 0; i < mails.length; i++) {
            assertEquals("The RejectReason instances are not correctly retrieved.", 1, mails[i]
                .getCompanyId());
        }
        assertEquals("The RejectReason instances are not correctly retrieved.", 0, dao
            .searchRejectReasons(new RejectReasonSearchBuilder().hasCompanyIdFilter(6)).length);
        System.out.println("finish stress test for searchRejectReasons(Filter) in "
            + (System.currentTimeMillis() - current) + " ms.");
    }

}
