/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.informix;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.rejectreason.RejectEmail;
import com.topcoder.timetracker.rejectreason.RejectEmailDAOException;
import com.topcoder.timetracker.rejectreason.RejectEmailNotFoundException;
import com.topcoder.timetracker.rejectreason.TestHelper;


/**
 * Failure tests for <code>DbRejectEmailDAO</code> class. Tests parameter checking, DAO exceptions and errors of some
 * other kind.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DbRejectEmailDAOFailureTests extends TestHelper {
    /** The Connection to use. */
    private DBConnectionFactory factory = null;

    /** The DbRejectEmailDAO instance to be tested. */
    private DbRejectEmailDAO dao = null;

    /**
     * Sets up test environment. Configuration is loaded and new instance of DbRejectEmailDAO is created.
     *
     * @throws Exception pass to JUnit.
     */
    protected void setUp() throws Exception {
        loadConfig();
        factory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
        dao = new DbRejectEmailDAO(factory, CONNECTION_NAME, new MockAuditManager(), ID_GENERATOR_NAME);
    }

    /**
     * Tests constructor with a null Connection Factory. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_nullDataSource() throws Exception {
        try {
            new DbRejectEmailDAO(null, CONNECTION_NAME, new MockAuditManager(), ID_GENERATOR_NAME);
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
            new DbRejectEmailDAO(factory, CONNECTION_NAME, null, ID_GENERATOR_NAME);
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
            new DbRejectEmailDAO(factory, CONNECTION_NAME, new MockAuditManager(), null);
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
            new DbRejectEmailDAO(factory, CONNECTION_NAME, new MockAuditManager(), " \t");
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
            new DbRejectEmailDAO(factory, null, new MockAuditManager(), ID_GENERATOR_NAME);
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
            new DbRejectEmailDAO(factory, " ", new MockAuditManager(), ID_GENERATOR_NAME);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        }
    }

    /**
     * Tests constructor with an id sequence does not exist. RejectEmailDAOException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_noIdSequence() throws Exception {
        try {
            new DbRejectEmailDAO(factory, CONNECTION_NAME, new MockAuditManager(), "no");
            fail("RejectEmailDAOException should be thrown");
        } catch (RejectEmailDAOException e) {
            // Ok
        }
    }

    /**
     * Tests constructor with without SearchBundleManager namespace configured. RejectEmailDAOException should be
     * thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_noManagerNamespace() throws Exception {
        try {
            CM.removeNamespace(DbRejectEmailDAO.class.getName());
            CM.add("EmailNoManagerNamespace.xml");
            new DbRejectEmailDAO(factory, CONNECTION_NAME, new MockAuditManager(), ID_GENERATOR_NAME);
            fail("RejectEmailDAOException should be thrown");
        } catch (RejectEmailDAOException e) {
            // Ok
        }
    }

    /**
     * Tests constructor with without SearchBundle name configured. RejectEmailDAOException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_noSearchBundleName() throws Exception {
        try {
            CM.removeNamespace(DbRejectEmailDAO.class.getName());
            CM.add("EmailNoSearchBundleName.xml");
            new DbRejectEmailDAO(factory, CONNECTION_NAME, new MockAuditManager(), ID_GENERATOR_NAME);
            fail("RejectEmailDAOException should be thrown");
        } catch (RejectEmailDAOException e) {
            // Ok
        }
    }

    /**
     * Tests constructor with with unknown SearchBundleManager namespace configured. RejectEmailDAOException should be
     * thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_unknownManagerNamespace()
        throws Exception {
        try {
            CM.removeNamespace(DbRejectEmailDAO.class.getName());
            CM.add("EmailUnknownManagerNamespace.xml");
            new DbRejectEmailDAO(factory, CONNECTION_NAME, new MockAuditManager(), ID_GENERATOR_NAME);
            fail("RejectEmailDAOException should be thrown");
        } catch (RejectEmailDAOException e) {
            // Ok
        }
    }

    /**
     * Tests constructor with with unknown SearchBundle name configured. RejectEmailDAOException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_unknownSearchBundleName()
        throws Exception {
        try {
            CM.removeNamespace(DbRejectEmailDAO.class.getName());
            CM.add("EmailUnknownSearchBundleName.xml");
            new DbRejectEmailDAO(factory, CONNECTION_NAME, new MockAuditManager(), ID_GENERATOR_NAME);
            fail("RejectEmailDAOException should be thrown");
        } catch (RejectEmailDAOException e) {
            // Ok
        }
    }

    /**
     * Tests createRejectEmail method with a null RejectEmail. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testCreateRejectEmail_nullRejectEmail()
        throws Exception {
        try {
            dao.createRejectEmail(null, "user", false);
            fail("IllegalArgumentExceptio should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        }
    }

    /**
     * Tests createRejectEmail method with a null username. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testCreateRejectEmail_nullUsername() throws Exception {
        try {
            RejectEmail rejectEmail = new RejectEmail();
            rejectEmail.setCompanyId(10);
            rejectEmail.setBody("body");
            dao.createRejectEmail(rejectEmail, null, false);
            fail("IllegalArgumentExceptio should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        }
    }

    /**
     * Tests createRejectEmail method with an empty username. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testCreateRejectEmail_emptyUsername() throws Exception {
        try {
            RejectEmail rejectEmail = new RejectEmail();
            rejectEmail.setCompanyId(10);
            rejectEmail.setBody("body");
            dao.createRejectEmail(rejectEmail, "    ", false);
            fail("IllegalArgumentExceptio should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        }
    }

    /**
     * Tests createRejectEmail method with a RejectEmail without company id set. IllegalArgumentException should be
     * thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testCreateRejectEmail_emailCompanyIdNotSet()
        throws Exception {
        try {
            RejectEmail rejectEmail = new RejectEmail();
            rejectEmail.setBody("body");
            dao.createRejectEmail(rejectEmail, "user", false);
            fail("IllegalArgumentExceptio should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        }
    }

    /**
     * Tests createRejectEmail method with a RejectEmail without body set. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testCreateRejectEmail_emailBodyNotSet()
        throws Exception {
        try {
            RejectEmail rejectEmail = new RejectEmail();
            rejectEmail.setCompanyId(9);
            dao.createRejectEmail(rejectEmail, "user", false);
            fail("IllegalArgumentExceptio should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        }
    }

    /**
     * Tests createRejectEmail method. Calls on the same RejectEmail twice, RejectEmailDAOException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testCreateRejectEmail_createTwice() throws Exception {
        try {
            RejectEmail rejectEmail = new RejectEmail();
            rejectEmail.setCompanyId(9);
            rejectEmail.setBody("body");
            dao.createRejectEmail(rejectEmail, "user", false);
            dao.createRejectEmail(rejectEmail, "user", false);
            fail("RejectEmailDAOException should be thrown");
        } catch (RejectEmailDAOException e) {
            // Ok
        }
    }

    /**
     * Tests retrieveRejectEmail method with id equals zero. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testRetrieveRejectEmail_withZero() throws Exception {
        try {
            dao.retrieveRejectEmail(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        }
    }

    /**
     * Tests retrieveRejectEmail method with id less than zero. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testRetrieveRejectEmail_withNegative()
        throws Exception {
        try {
            dao.retrieveRejectEmail(-10);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        }
    }

    /**
     * Tests updateRejectEmail method with a null RejectEmail. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testUpdateRejectEmail_nullRejectEmail()
        throws Exception {
        try {
            dao.updateRejectEmail(null, "user", false);
            fail("IllegalArgumentExceptio should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        }
    }

    /**
     * Tests updateRejectEmail method with a null username. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testUpdateRejectEmail_nullUsername() throws Exception {
        RejectEmail rejectEmail = new RejectEmail();
        rejectEmail.setCompanyId(4);
        rejectEmail.setBody("body");

        try {
            dao.createRejectEmail(rejectEmail, "user", false);
            dao.updateRejectEmail(rejectEmail, null, false);
            fail("IllegalArgumentExceptio should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        } finally {
            dao.deleteRejectEmail(rejectEmail, "user", false);
        }
    }

    /**
     * Tests updateRejectEmail method with an empty username. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testUpdateRejectEmail_emptyUsername() throws Exception {
        RejectEmail rejectEmail = new RejectEmail();
        rejectEmail.setCompanyId(3);
        rejectEmail.setBody("body");

        try {
            dao.createRejectEmail(rejectEmail, "user", false);
            dao.updateRejectEmail(rejectEmail, "  ", false);
            fail("IllegalArgumentExceptio should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        } finally {
            dao.deleteRejectEmail(rejectEmail, "user", false);
        }
    }

    /**
     * Tests updateRejectEmail method with a RejectEmail without body set. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testUpdateRejectEmail_emailBodyNotSet()
        throws Exception {
        RejectEmail rejectEmail = new RejectEmail();
        rejectEmail.setCompanyId(1);
        rejectEmail.setBody("body");

        try {
            dao.createRejectEmail(rejectEmail, "user", false);

            // Create new RejectEmail with same id
            RejectEmail rejectEmail2 = new RejectEmail();
            rejectEmail2.setId(rejectEmail.getId());
            dao.updateRejectEmail(rejectEmail2, "user", false);
            fail("IllegalArgumentExceptio should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        } finally {
            dao.deleteRejectEmail(rejectEmail, "user", false);
        }
    }

    /**
     * Tests updateRejectEmail method with a RejectEmail not in persistence. RejectEmailNotFoundException should be
     * thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testUpdateRejectEmail_notInPersistence()
        throws Exception {
        try {
            RejectEmail rejectEmail = new RejectEmail();
            rejectEmail.setId(100);
            rejectEmail.setCompanyId(1);
            rejectEmail.setBody("body");
            dao.updateRejectEmail(rejectEmail, "user", false);
            fail("RejectEmailNotFoundException should be thrown");
        } catch (RejectEmailNotFoundException e) {
            // Ok
        }
    }

    /**
     * Tests updateRejectEmail method with a RejectEmail whose id not set. RejectEmailNotFoundException should be
     * thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testUpdateRejectEmail_idNotSet() throws Exception {
        try {
            RejectEmail rejectEmail = new RejectEmail();
            rejectEmail.setCompanyId(1);
            rejectEmail.setBody("body");
            dao.updateRejectEmail(rejectEmail, "user", false);
            fail("RejectEmailNotFoundException should be thrown");
        } catch (RejectEmailNotFoundException e) {
            // Ok
        }
    }

    /**
     * Tests deleteRejectEmail method with a null RejectEmail. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testDeleteRejectEmail_nullRejectEmail()
        throws Exception {
        try {
            dao.deleteRejectEmail(null, "user", false);
            fail("IllegalArgumentExceptio should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        }
    }

    /**
     * Tests deleteRejectEmail method with a null username. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testDeleteRejectEmail_nullUsername() throws Exception {
        RejectEmail rejectEmail = new RejectEmail();
        rejectEmail.setCompanyId(4);
        rejectEmail.setBody("body");

        try {
            dao.createRejectEmail(rejectEmail, "user", false);
            dao.deleteRejectEmail(rejectEmail, null, false);
            fail("IllegalArgumentExceptio should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        } finally {
            dao.deleteRejectEmail(rejectEmail, "user", false);
        }
    }

    /**
     * Tests deleteRejectEmail method with an empty username. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testDeleteRejectEmail_emptyUsername() throws Exception {
        RejectEmail rejectEmail = new RejectEmail();
        rejectEmail.setCompanyId(3);
        rejectEmail.setBody("body");

        try {
            dao.createRejectEmail(rejectEmail, "user", false);
            dao.deleteRejectEmail(rejectEmail, "  ", false);
            fail("IllegalArgumentExceptio should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        } finally {
            dao.deleteRejectEmail(rejectEmail, "user", false);
        }
    }

    /**
     * Tests deleteRejectEmail method with a RejectEmail not in persistence. RejectEmailNotFoundException should be
     * thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testDeleteRejectEmail_notInPersistence()
        throws Exception {
        try {
            RejectEmail rejectEmail = new RejectEmail();
            rejectEmail.setId(100);
            rejectEmail.setCompanyId(1);
            rejectEmail.setBody("body");
            dao.deleteRejectEmail(rejectEmail, "user", false);
            fail("RejectEmailNotFoundException should be thrown");
        } catch (RejectEmailNotFoundException e) {
            // Ok
        }
    }

    /**
     * Tests deleteRejectEmail method with a RejectEmail whose id not set. RejectEmailNotFoundException should be
     * thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testDeleteRejectEmail_idNotSet() throws Exception {
        try {
            RejectEmail rejectEmail = new RejectEmail();
            rejectEmail.setCompanyId(1);
            rejectEmail.setBody("body");
            dao.deleteRejectEmail(rejectEmail, "user", false);
            fail("RejectEmailNotFoundException should be thrown");
        } catch (RejectEmailNotFoundException e) {
            // Ok
        }
    }

    /**
     * Tests searchRejectEmails method with a null filter. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchRejectEmails_nullFilter() throws Exception {
        try {
            dao.searchRejectEmails(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok
        }
    }

    /**
     * Tests searchRejectEmails method with an invalid filter. RejectEmailDAOException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchRejectEmails_invalidFilter()
        throws Exception {
        try {
            Filter filter = new EqualToFilter("name", new Long(0));
            dao.searchRejectEmails(filter);
            fail("RejectEmailDAOException should be thrown");
        } catch (RejectEmailDAOException e) {
            // Ok
        }
    }
}
