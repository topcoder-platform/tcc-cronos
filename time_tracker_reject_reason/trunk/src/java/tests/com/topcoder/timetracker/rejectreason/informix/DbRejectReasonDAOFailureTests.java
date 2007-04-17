/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.informix;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.rejectreason.RejectReason;
import com.topcoder.timetracker.rejectreason.RejectReasonDAOException;
import com.topcoder.timetracker.rejectreason.RejectReasonNotFoundException;
import com.topcoder.timetracker.rejectreason.TestHelper;


/**
 * Failure tests for <code>DbRejectReasonDAO</code> class. Tests parameter checking, DAO exceptions and errors of some
 * other kind.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DbRejectReasonDAOFailureTests extends TestHelper {
    /** The Connection to use. */
    private DBConnectionFactory factory = null;

    /** The DbRejectReasonDAO instance to be tested. */
    private DbRejectReasonDAO dao = null;

    /**
     * Sets up test environment. Configuration is loaded and new instance of DbRejectReasonDAO is created.
     *
     * @throws Exception pass to JUnit.
     */
    protected void setUp() throws Exception {
        loadConfig();
        factory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
        dao = new DbRejectReasonDAO(factory, CONNECTION_NAME, new MockAuditManager(), ID_GENERATOR_NAME);
    }

    /**
     * Tests constructor with a null Connection Factory. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_nullDataSource() throws Exception {
        try {
            new DbRejectReasonDAO(null, CONNECTION_NAME, new MockAuditManager(), ID_GENERATOR_NAME);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        }
    }

    /**
     * Tests constructor with a null AuditManager. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_nullAuditManager() throws Exception {
        try {
            new DbRejectReasonDAO(factory, CONNECTION_NAME, null, ID_GENERATOR_NAME);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        }
    }

    /**
     * Tests constructor with a null id generator name. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_nullIdGeneratorName() throws Exception {
        try {
            new DbRejectReasonDAO(factory, CONNECTION_NAME, new MockAuditManager(), null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        }
    }

    /**
     * Tests constructor with an empty id generator name. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_emptyIdGeneratorName()
        throws Exception {
        try {
            new DbRejectReasonDAO(factory, CONNECTION_NAME, new MockAuditManager(), " \t");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        }
    }

    /**
     * Tests constructor with a null connection name. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_nullConnectionName() throws Exception {
        try {
            new DbRejectReasonDAO(factory, null, new MockAuditManager(), ID_GENERATOR_NAME);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        }
    }

    /**
     * Tests constructor with an empty connection name. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_emptyConnectionName()
        throws Exception {
        try {
            new DbRejectReasonDAO(factory, " ", new MockAuditManager(), ID_GENERATOR_NAME);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        }
    }

    /**
     * Tests constructor with an id sequence does not exist. RejectReasonDAOException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_noIdSequence() throws Exception {
        try {
            new DbRejectReasonDAO(factory, CONNECTION_NAME, new MockAuditManager(), "no");
            fail("RejectReasonDAOException should be thrown");
        } catch (RejectReasonDAOException e) {
            // Ok
        }
    }

    /**
     * Tests constructor with without SearchBundleManager namespace configured. RejectReasonDAOException should be
     * thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_noManagerNamespace() throws Exception {
        try {
            CM.removeNamespace(DbRejectReasonDAO.class.getName());
            CM.add("ReasonNoManagerNamespace.xml");
            new DbRejectReasonDAO(factory, CONNECTION_NAME, new MockAuditManager(), ID_GENERATOR_NAME);
            fail("RejectReasonDAOException should be thrown");
        } catch (RejectReasonDAOException e) {
            // Ok
        }
    }

    /**
     * Tests constructor with without SearchBundle name configured. RejectReasonDAOException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_noSearchBundleName() throws Exception {
        try {
            CM.removeNamespace(DbRejectReasonDAO.class.getName());
            CM.add("ReasonNoSearchBundleName.xml");
            new DbRejectReasonDAO(factory, CONNECTION_NAME, new MockAuditManager(), ID_GENERATOR_NAME);
            fail("RejectReasonDAOException should be thrown");
        } catch (RejectReasonDAOException e) {
            // Ok
        }
    }

    /**
     * Tests constructor with with unknown SearchBundleManager namespace configured. RejectReasonDAOException should be
     * thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_unknownManagerNamespace()
        throws Exception {
        try {
            CM.removeNamespace(DbRejectReasonDAO.class.getName());
            CM.add("ReasonUnknownManagerNamespace.xml");
            new DbRejectReasonDAO(factory, CONNECTION_NAME, new MockAuditManager(), ID_GENERATOR_NAME);
            fail("RejectReasonDAOException should be thrown");
        } catch (RejectReasonDAOException e) {
            // Ok
        }
    }

    /**
     * Tests constructor with with unknown SearchBundle name configured. RejectReasonDAOException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_unknownSearchBundleName()
        throws Exception {
        try {
            CM.removeNamespace(DbRejectReasonDAO.class.getName());
            CM.add("ReasonUnknownSearchBundleName.xml");
            new DbRejectReasonDAO(factory, CONNECTION_NAME, new MockAuditManager(), ID_GENERATOR_NAME);
            fail("RejectReasonDAOException should be thrown");
        } catch (RejectReasonDAOException e) {
            // Ok
        }
    }

    /**
     * Tests createRejectReason method with a null RejectReason. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testCreateRejectReason_nullRejectReason()
        throws Exception {
        try {
            dao.createRejectReason(null, "user", false);
            fail("IllegalArgumentExceptio should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        }
    }

    /**
     * Tests createRejectReason method with a null username. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testCreateRejectReason_nullUsername() throws Exception {
        try {
            RejectReason rejectReason = new RejectReason();
            rejectReason.setCompanyId(10);
            rejectReason.setDescription("description");
            dao.createRejectReason(rejectReason, null, false);
            fail("IllegalArgumentExceptio should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        }
    }

    /**
     * Tests createRejectReason method with an empty username. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testCreateRejectReason_emptyUsername()
        throws Exception {
        try {
            RejectReason rejectReason = new RejectReason();
            rejectReason.setCompanyId(10);
            rejectReason.setDescription("description");
            dao.createRejectReason(rejectReason, "    ", false);
            fail("IllegalArgumentExceptio should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        }
    }

    /**
     * Tests createRejectReason method with a RejectReason without company id set. IllegalArgumentException should be
     * thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testCreateRejectReason_reasonCompanyIdNotSet()
        throws Exception {
        try {
            RejectReason rejectReason = new RejectReason();
            rejectReason.setDescription("description");
            dao.createRejectReason(rejectReason, "user", false);
            fail("IllegalArgumentExceptio should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        }
    }

    /**
     * Tests createRejectReason method with a RejectReason without description set. IllegalArgumentException should be
     * thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testCreateRejectReason_reasonDescriptionNotSet()
        throws Exception {
        try {
            RejectReason rejectReason = new RejectReason();
            rejectReason.setCompanyId(9);
            dao.createRejectReason(rejectReason, "user", false);
            fail("IllegalArgumentExceptio should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        }
    }

    /**
     * Tests createRejectReason method. Calls on the same RejectReason twice, RejectReasonDAOException should be
     * thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testCreateRejectReason_createTwice() throws Exception {
        try {
            RejectReason rejectReason = new RejectReason();
            rejectReason.setCompanyId(9);
            rejectReason.setDescription("description");
            dao.createRejectReason(rejectReason, "user", false);
            dao.createRejectReason(rejectReason, "user", false);
            fail("RejectReasonDAOException should be thrown");
        } catch (RejectReasonDAOException e) {
            // Ok
        }
    }

    /**
     * Tests retrieveRejectReason method with id equals zero. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testRetrieveRejectReason_withZero() throws Exception {
        try {
            dao.retrieveRejectReason(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        }
    }

    /**
     * Tests retrieveRejectReason method with id less than zero. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testRetrieveRejectReason_withNegative()
        throws Exception {
        try {
            dao.retrieveRejectReason(-10);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        }
    }

    /**
     * Tests updateRejectReason method with a null RejectReason. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testUpdateRejectReason_nullRejectReason()
        throws Exception {
        try {
            dao.updateRejectReason(null, "user", false);
            fail("IllegalArgumentExceptio should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        }
    }

    /**
     * Tests updateRejectReason method with a null username. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testUpdateRejectReason_nullUsername() throws Exception {
        RejectReason rejectReason = new RejectReason();
        rejectReason.setCompanyId(4);
        rejectReason.setDescription("description");

        try {
            dao.createRejectReason(rejectReason, "user", false);
            dao.updateRejectReason(rejectReason, null, false);
            fail("IllegalArgumentExceptio should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        } finally {
            dao.deleteRejectReason(rejectReason, "user", false);
        }
    }

    /**
     * Tests updateRejectReason method with an empty username. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testUpdateRejectReason_emptyUsername()
        throws Exception {
        RejectReason rejectReason = new RejectReason();
        rejectReason.setCompanyId(3);
        rejectReason.setDescription("description");

        try {
            dao.createRejectReason(rejectReason, "user", false);
            dao.updateRejectReason(rejectReason, "  ", false);
            fail("IllegalArgumentExceptio should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        } finally {
            dao.deleteRejectReason(rejectReason, "user", false);
        }
    }

    /**
     * Tests updateRejectReason method with a RejectReason without body set. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testUpdateRejectReason_reasonBodyNotSet()
        throws Exception {
        RejectReason rejectReason = new RejectReason();
        rejectReason.setCompanyId(1);
        rejectReason.setDescription("description");

        try {
            dao.createRejectReason(rejectReason, "user", false);

            // Create new RejectReason with same id
            RejectReason rejectReason2 = new RejectReason();
            rejectReason2.setId(rejectReason.getId());
            dao.updateRejectReason(rejectReason2, "user", false);
            fail("IllegalArgumentExceptio should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        } finally {
            dao.deleteRejectReason(rejectReason, "user", false);
        }
    }

    /**
     * Tests updateRejectReason method with a RejectReason not in persistence. RejectReasonNotFoundException should be
     * thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testUpdateRejectReason_notInPersistence()
        throws Exception {
        try {
            RejectReason rejectReason = new RejectReason();
            rejectReason.setId(100);
            rejectReason.setCompanyId(1);
            rejectReason.setDescription("description");
            dao.updateRejectReason(rejectReason, "user", false);
            fail("RejectReasonNotFoundException should be thrown");
        } catch (RejectReasonNotFoundException e) {
            // Ok
        }
    }

    /**
     * Tests updateRejectReason method with a RejectReason whose id not set. RejectReasonNotFoundException should be
     * thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testUpdateRejectReason_idNotSet() throws Exception {
        try {
            RejectReason rejectReason = new RejectReason();
            rejectReason.setCompanyId(1);
            rejectReason.setDescription("description");
            dao.updateRejectReason(rejectReason, "user", false);
            fail("RejectReasonNotFoundException should be thrown");
        } catch (RejectReasonNotFoundException e) {
            // Ok
        }
    }

    /**
     * Tests deleteRejectReason method with a null RejectReason. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testDeleteRejectReason_nullRejectReason()
        throws Exception {
        try {
            dao.deleteRejectReason(null, "user", false);
            fail("IllegalArgumentExceptio should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        }
    }

    /**
     * Tests deleteRejectReason method with a null username. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testDeleteRejectReason_nullUsername() throws Exception {
        RejectReason rejectReason = new RejectReason();
        rejectReason.setCompanyId(4);
        rejectReason.setDescription("description");

        try {
            dao.createRejectReason(rejectReason, "user", false);
            dao.deleteRejectReason(rejectReason, null, false);
            fail("IllegalArgumentExceptio should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        } finally {
            dao.deleteRejectReason(rejectReason, "user", false);
        }
    }

    /**
     * Tests deleteRejectReason method with an empty username. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testDeleteRejectReason_emptyUsername()
        throws Exception {
        RejectReason rejectReason = new RejectReason();
        rejectReason.setCompanyId(3);
        rejectReason.setDescription("description");

        try {
            dao.createRejectReason(rejectReason, "user", false);
            dao.deleteRejectReason(rejectReason, "  ", false);
            fail("IllegalArgumentExceptio should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        } finally {
            dao.deleteRejectReason(rejectReason, "user", false);
        }
    }

    /**
     * Tests deleteRejectReason method with a RejectReason not in persistence. RejectReasonNotFoundException should be
     * thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testDeleteRejectReason_notInPersistence()
        throws Exception {
        try {
            RejectReason rejectReason = new RejectReason();
            rejectReason.setId(100);
            rejectReason.setCompanyId(1);
            rejectReason.setDescription("description");
            dao.deleteRejectReason(rejectReason, "user", false);
            fail("RejectReasonNotFoundException should be thrown");
        } catch (RejectReasonNotFoundException e) {
            // Ok
        }
    }

    /**
     * Tests deleteRejectReason method with a RejectReason whose id not set. RejectReasonNotFoundException should be
     * thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testDeleteRejectReason_idNotSet() throws Exception {
        try {
            RejectReason rejectReason = new RejectReason();
            rejectReason.setCompanyId(1);
            rejectReason.setDescription("description");
            dao.deleteRejectReason(rejectReason, "user", false);
            fail("RejectReasonNotFoundException should be thrown");
        } catch (RejectReasonNotFoundException e) {
            // Ok
        }
    }

    /**
     * Tests searchRejectReasons method with a null filter. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchRejectReasons_nullFilter() throws Exception {
        try {
            dao.searchRejectReasons(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        }
    }

    /**
     * Tests searchRejectReasons method with an invalid filter. RejectReasonDAOException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchRejectReasons_invalidFilter()
        throws Exception {
        try {
            Filter filter = new EqualToFilter("name", new Long(0));
            dao.searchRejectReasons(filter);
            fail("RejectReasonDAOException should be thrown");
        } catch (RejectReasonDAOException e) {
            // Ok
        }
    }
}