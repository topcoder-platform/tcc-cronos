/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base.failuretests;

import java.util.Date;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.entry.base.ConfigurationException;
import com.topcoder.timetracker.entry.base.CutoffTimeBean;
import com.topcoder.timetracker.entry.base.DuplicateEntryException;
import com.topcoder.timetracker.entry.base.EntryNotFoundException;
import com.topcoder.timetracker.entry.base.PersistenceException;
import com.topcoder.timetracker.entry.base.persistence.InformixCutoffTimeDao;

import junit.framework.TestCase;

/**
 * <p>
 * The failure test for <code>InformixCutoffTimeDao</code>.
 * </p>
 *
 * @author Hacker_QC
 * @version 3.2
 */
public class InformixCutoffTimeDaoFailureTest extends TestCase {

    /**
     * Represents connection name used in the failure test.
     */
    private static final String CONNECTION_NAME = "informix_connect";

    /**
     * Represents ID Generator name used in the failure test.
     */
    private static final String ID_GENERATOR = "cut_off_time";

    /**
     * Represents CutoffTimeBean instance used in the failure test.
     */
    private CutoffTimeBean cutoffTimeBean;

    /**
     * Represents DBConnectionFactory instance used in the failure test.
     */
    private DBConnectionFactory dbFactory;

    /**
     * Represents InformixCutoffTimeDao instance used in the failure test.
     */
    private InformixCutoffTimeDao informixCutoffTimeDao;

    /**
     * Represents MockAuditManager instance used in the failure test.
     */
    private MockAuditManager auditManager;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        FailureTestHelper.clearConfig();
        FailureTestHelper.loadConfig("DBConnectionFactory.xml");
        FailureTestHelper.initDB();

        cutoffTimeBean = createBean();
        dbFactory = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        auditManager = new MockAuditManager();
        informixCutoffTimeDao = new InformixCutoffTimeDao(CONNECTION_NAME, ID_GENERATOR, dbFactory, auditManager);
    }

    /**
     * Clears test environment.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        FailureTestHelper.clearConfig();
        dbFactory = null;
        auditManager = null;
        informixCutoffTimeDao = null;
    }

    /**
     * <p>
     * Failure test for InformixCutoffTimeDao(String connectionName, String idGeneratorName,
     * DBConnectionFactory dbConnectionFactory, AuditManager auditManager).
     * </p>
     *
     * <p>
     * connectionName is empty.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixCutoffTimeDaoFailure1() throws Exception {
        try {
            new InformixCutoffTimeDao("", ID_GENERATOR, dbFactory, auditManager);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for InformixCutoffTimeDao(String connectionName, String idGeneratorName,
     * DBConnectionFactory dbConnectionFactory, AuditManager auditManager).
     * </p>
     *
     * <p>
     * connectionName is empty after trimmed.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixCutoffTimeDaoFailure2() throws Exception {
        try {
            new InformixCutoffTimeDao("  ", ID_GENERATOR, dbFactory, auditManager);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for InformixCutoffTimeDao(String connectionName, String idGeneratorName,
     * DBConnectionFactory dbConnectionFactory, AuditManager auditManager).
     * </p>
     *
     * <p>
     * idGeneratorName is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixCutoffTimeDaoFailure4() throws Exception {
        try {
            new InformixCutoffTimeDao(CONNECTION_NAME, null, dbFactory, auditManager);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for InformixCutoffTimeDao(String connectionName, String idGeneratorName,
     * DBConnectionFactory dbConnectionFactory, AuditManager auditManager).
     * </p>
     *
     * <p>
     * idGeneratorName is invalid.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixCutoffTimeDaoFailure5() throws Exception {
        try {
            new InformixCutoffTimeDao(CONNECTION_NAME, "invalid_id_generator", dbFactory, auditManager);
            fail("ConfigurationException is expected.");
        } catch (ConfigurationException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for InformixCutoffTimeDao(String connectionName, String idGeneratorName,
     * DBConnectionFactory dbConnectionFactory, AuditManager auditManager).
     * </p>
     *
     * <p>
     * dbConnectionFactory is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixCutoffTimeDaoFailure6() throws Exception {
        try {
            new InformixCutoffTimeDao(CONNECTION_NAME, ID_GENERATOR, null, auditManager);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for InformixCutoffTimeDao(String connectionName, String idGeneratorName,
     * DBConnectionFactory dbConnectionFactory, AuditManager auditManager).
     * </p>
     *
     * <p>
     * auditManager is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixCutoffTimeDaoFailure7() throws Exception {
        try {
            new InformixCutoffTimeDao(CONNECTION_NAME, ID_GENERATOR, dbFactory, null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for createCutoffTime(CutoffTimeBean cutoffTimeBean, boolean audit).
     * </p>
     *
     * <p>
     * auditManager is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateCutoffTimeFailure1() throws Exception {
        try {
            informixCutoffTimeDao.createCutoffTime(null, true);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for createCutoffTime(CutoffTimeBean cutoffTimeBean, boolean audit).
     * </p>
     *
     * <p>
     * cutoffTimeBean is duplicate.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateCutoffTimeFailure2() throws Exception {
        informixCutoffTimeDao.createCutoffTime(cutoffTimeBean, false);
        cutoffTimeBean.setId(cutoffTimeBean.getId() + 1);
        try {
            informixCutoffTimeDao.createCutoffTime(cutoffTimeBean, false);
            fail("DuplicateEntryException is expected");
        } catch (DuplicateEntryException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for createCutoffTime(CutoffTimeBean cutoffTimeBean, boolean audit).
     * </p>
     *
     * <p>
     * cutoffTimeBean is invalid.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateCutoffTimeFailure3() throws Exception {
        // use bean with a companyid not existing in persistence
        CutoffTimeBean cutoffTimeBean = new CutoffTimeBean();
        cutoffTimeBean.setCompanyId(1000);
        cutoffTimeBean.setCreationUser("user1");
        cutoffTimeBean.setCutoffTime(new Date());
        try {
            informixCutoffTimeDao.createCutoffTime(cutoffTimeBean, true);
            fail("PersistenceException is expected");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for createCutoffTime(CutoffTimeBean cutoffTimeBean, boolean audit).
     * </p>
     *
     * <p>
     * cutoffTimeBean is invalid.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateCutoffTimeFailure4() throws Exception {
        // use bean with a companyid not existing in persistence
        CutoffTimeBean cutoffTimeBean = new CutoffTimeBean();
        cutoffTimeBean.setCompanyId(1);
        cutoffTimeBean.setCutoffTime(new Date());
        try {
            informixCutoffTimeDao.createCutoffTime(cutoffTimeBean, true);
            fail("PersistenceException is expected");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for createCutoffTime(CutoffTimeBean cutoffTimeBean, boolean audit).
     * </p>
     *
     * <p>
     * cutoffTimeBean is invalid.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateCutoffTimeFailure5() throws Exception {
        // use bean with a companyid not existing in persistence
        CutoffTimeBean cutoffTimeBean = new CutoffTimeBean();
        cutoffTimeBean.setCompanyId(1);
        cutoffTimeBean.setCreationUser("user1");
        try {
            informixCutoffTimeDao.createCutoffTime(cutoffTimeBean, true);
            fail("PersistenceException is expected");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for deleteCutoffTime(CutoffTimeBean cutoffTimeBean, boolean audit).
     * </p>
     *
     * <p>
     * cutoffTimeBean is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteCutoffTimeFailure1() throws Exception {
        try {
            informixCutoffTimeDao.deleteCutoffTime(null, true);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for deleteCutoffTime(CutoffTimeBean cutoffTimeBean, boolean audit).
     * </p>
     *
     * <p>
     * cutoffTimeBean does not exist.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteCutoffTimeFailure2() throws Exception {
        try {
            informixCutoffTimeDao.deleteCutoffTime(cutoffTimeBean, true);
            fail("EntryNotFoundException is expected");
        } catch (EntryNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for updateCutoffTime(CutoffTimeBean cutoffTimeBean, boolean audit).
     * </p>
     *
     * <p>
     * cutoffTimeBean is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateCutoffTimeFailure1() throws Exception {
        try {
            informixCutoffTimeDao.updateCutoffTime(null, true);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for updateCutoffTime(CutoffTimeBean cutoffTimeBean, boolean audit).
     * </p>
     *
     * <p>
     * cutoffTimeBean does not exist.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateCutoffTimeFailure2() throws Exception {
        try {
            informixCutoffTimeDao.updateCutoffTime(cutoffTimeBean, true);
            fail("EntryNotFoundException is expected");
        } catch (EntryNotFoundException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for fetchCutoffTimeByCompanyID(long companyId).
     * </p>
     *
     * <p>
     * companyId is 0.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testFetchCutoffTimeByCompanyIDFailure1() throws Exception {
        try {
            informixCutoffTimeDao.fetchCutoffTimeByCompanyID(0);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for fetchCutoffTimeByCompanyID(long companyId).
     * </p>
     *
     * <p>
     * companyId is negative.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testFetchCutoffTimeByCompanyIDFailure2() throws Exception {
        try {
            informixCutoffTimeDao.fetchCutoffTimeByCompanyID(-1);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for fetchCutoffTimeById(long cutoffTimeId).
     * </p>
     *
     * <p>
     * cutoffTimeId is 0.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testFetchCutoffTimeByIdFailure1() throws Exception {
        try {
            informixCutoffTimeDao.fetchCutoffTimeById(0);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for fetchCutoffTimeById(long cutoffTimeId).
     * </p>
     *
     * <p>
     * cutoffTimeId is negative.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testFetchCutoffTimeByIdFailure2() throws Exception {
        try {
            informixCutoffTimeDao.fetchCutoffTimeById(-1);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Creates a CutoffTimeBean used in the failure test.
     *
     * @return a CutoffTimeBean
     */
    private CutoffTimeBean createBean() {
        CutoffTimeBean cutoffTimeBean = new CutoffTimeBean();
        cutoffTimeBean.setChanged(true);
        cutoffTimeBean.setCompanyId(1);
        cutoffTimeBean.setCreationDate(new Date());
        cutoffTimeBean.setCreationUser("create_user");
        cutoffTimeBean.setCutoffTime(new Date());
        cutoffTimeBean.setId(2);
        cutoffTimeBean.setModificationDate(new Date());
        cutoffTimeBean.setModificationUser("modify_user");
        return cutoffTimeBean;
    }
}
