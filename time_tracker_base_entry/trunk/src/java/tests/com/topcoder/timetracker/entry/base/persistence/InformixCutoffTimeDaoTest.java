/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base.persistence;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.timetracker.audit.ApplicationArea;
import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditType;
import com.topcoder.timetracker.entry.base.ConfigurationException;
import com.topcoder.timetracker.entry.base.CutoffTimeBean;
import com.topcoder.timetracker.entry.base.DuplicateEntryException;
import com.topcoder.timetracker.entry.base.EntryNotFoundException;
import com.topcoder.timetracker.entry.base.MockAuditManager;
import com.topcoder.timetracker.entry.base.PersistenceException;
import com.topcoder.timetracker.entry.base.TestHelper;

import junit.framework.TestCase;

import java.sql.Timestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Test case for InformixCutoffTimeDao.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class InformixCutoffTimeDaoTest extends TestCase {
    /** Default connection name used in dao. */
    private static final String DEFAULT_CONN_NAME = "informix_connect";

    /** Default ID Generator name used in dao. */
    private static final String DEFAULT_ID_GENERATOR_NAME = "cut_off_time";

    /** Columns in cut_off_time, used to test AuditDetail creation. */
    private static final String[] CUT_OFF_TIME_COLUMNS = {
        "cut_off_time_id", "company_id", "cut_off_time", "creation_date", "creation_user", "modification_date",
        "modification_user"
    };

    /** Default CutoffTimeBean instance used in test. */
    private CutoffTimeBean cutoffTimeBean;

    /** Default DBConnectionFactory instance used in test. */
    private DBConnectionFactory dbFactory;

    /** Default InformixCutoffTimeDao instance used in test. */
    private InformixCutoffTimeDao dao;

    /** Default MockAuditManager instance used in test. */
    private MockAuditManager auditManager;

    /**
     * Tests {@link InformixCutoffTimeDao#createCutoffTime(CutoffTimeBean, boolean)}. The bean will be created
     * and tested whether it can be retrieved. Also the audit information will be tested whether it's created as
     * expected.
     *
     * @throws Exception to JUnit
     */
    public void testCreateCutoffTime() throws Exception {
        dao.createCutoffTime(cutoffTimeBean, true);

        CutoffTimeBean bean = dao.fetchCutoffTimeById(cutoffTimeBean.getId());

        assertCutoffTimeBeanEquals(cutoffTimeBean, bean);
        assertAuditCorrect(null, cutoffTimeBean);
    }

    /**
     * Tests {@link InformixCutoffTimeDao#createCutoffTime(CutoffTimeBean, boolean)} when AuditManger failed to
     * create any audit.
     */
    public void testCreateCutoffTimeAuditFailed() {
        try {
            System.setProperty("exception", "AuditManagerException");

            dao.createCutoffTime(cutoffTimeBean, true);
            fail("audit failed, PersistenceException is expected");
        } catch (DuplicateEntryException e) {
            fail("PersistenceException is expected");
        } catch (PersistenceException e) {
            //success
        } finally {
            System.setProperty("exception", "");
        }
    }

    /**
     * Tests {@link InformixCutoffTimeDao#createCutoffTime(CutoffTimeBean, boolean)} with duplicate company id,
     * DuplicateEntryException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testCreateCutoffTimeExistentCompId() throws Exception {
        dao.createCutoffTime(cutoffTimeBean, false);

        cutoffTimeBean.setId(cutoffTimeBean.getId() + 1);

        try {
            dao.createCutoffTime(cutoffTimeBean, false);
            fail("entity with the same company id already exist, DuplicateEntryException is expected");
        } catch (DuplicateEntryException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixCutoffTimeDao#createCutoffTime(CutoffTimeBean, boolean)} with duplicate id,
     * DuplicateEntryException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testCreateCutoffTimeExistentId() throws Exception {
        dao.createCutoffTime(cutoffTimeBean, false);

        cutoffTimeBean.setCompanyId(2); //change to another company, but with same id

        try {
            dao.createCutoffTime(cutoffTimeBean, false);
            fail("entity with the same id already exist, DuplicateEntryException is expected");
        } catch (DuplicateEntryException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixCutoffTimeDao#createCutoffTime(CutoffTimeBean, boolean)} with id not being set, Id
     * Generator will generate a new id for it.
     *
     * @throws Exception to JUnit
     */
    public void testCreateCutoffTimeGenId() throws Exception {
        //use bean without id set
        CutoffTimeBean cutoffTimeBean = new CutoffTimeBean();

        cutoffTimeBean.setChanged(true);
        cutoffTimeBean.setCompanyId(1);
        cutoffTimeBean.setCreationDate(new Date());
        cutoffTimeBean.setCreationUser("user1");
        cutoffTimeBean.setCutoffTime(new Date());
        //cutoffTimeBean.setId(2);
        cutoffTimeBean.setModificationDate(new Date());
        cutoffTimeBean.setModificationUser("user2");

        //then Id generator will generate a new id for this entity
        //after creation, the id will be set back to the bean
        dao.createCutoffTime(cutoffTimeBean, true);

        CutoffTimeBean bean = dao.fetchCutoffTimeById(cutoffTimeBean.getId());

        assertCutoffTimeBeanEquals(cutoffTimeBean, bean);
        assertAuditCorrect(null, cutoffTimeBean);
    }

    /**
     * Tests {@link InformixCutoffTimeDao#createCutoffTime(CutoffTimeBean, boolean)} for a not existent
     * company, PersistenceException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testCreateCutoffTimeInvalidEntity_invalid_compId()
        throws Exception {
        //use bean with a companyid not existing in persistence
        CutoffTimeBean cutoffTimeBean = new CutoffTimeBean();

        cutoffTimeBean.setChanged(true);
        cutoffTimeBean.setCompanyId(10); //no such companyId
        cutoffTimeBean.setCreationDate(new Date());
        cutoffTimeBean.setCreationUser("user1");
        cutoffTimeBean.setCutoffTime(new Date());
        cutoffTimeBean.setId(1);
        cutoffTimeBean.setModificationDate(new Date());
        cutoffTimeBean.setModificationUser("user2");

        try {
            dao.createCutoffTime(cutoffTimeBean, true);
            fail("not such company id, PersistenceException is expected");
        } catch (PersistenceException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixCutoffTimeDao#createCutoffTime(CutoffTimeBean, boolean)} with entity missing
     * creation user, PersistenceException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testCreateCutoffTimeInvalidEntity_missing_creationUser()
        throws Exception {
        //use bean without creation user
        CutoffTimeBean cutoffTimeBean = new CutoffTimeBean();

        cutoffTimeBean.setChanged(true);
        cutoffTimeBean.setCompanyId(1);
        cutoffTimeBean.setCreationDate(new Date());
        //        cutoffTimeBean.setCreationUser("user1");
        cutoffTimeBean.setCutoffTime(new Date());
        cutoffTimeBean.setId(1);
        cutoffTimeBean.setModificationDate(new Date());
        cutoffTimeBean.setModificationUser("user2");

        try {
            dao.createCutoffTime(cutoffTimeBean, true);
            fail("entity missing creationUser, PersistenceException is expected");
        } catch (PersistenceException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixCutoffTimeDao#createCutoffTime(CutoffTimeBean, boolean)} with entity missing
     * cutoffTime, PersistenceException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testCreateCutoffTimeInvalidEntity_missing_cutoffTime()
        throws Exception {
        //use bean without id cutoffTime
        CutoffTimeBean cutoffTimeBean = new CutoffTimeBean();

        cutoffTimeBean.setChanged(true);
        cutoffTimeBean.setCompanyId(1);
        cutoffTimeBean.setCreationDate(new Date());
        cutoffTimeBean.setCreationUser("user1");
        //        cutoffTimeBean.setCutoffTime(new Date());
        cutoffTimeBean.setId(1);
        cutoffTimeBean.setModificationDate(new Date());
        cutoffTimeBean.setModificationUser("user2");

        try {
            dao.createCutoffTime(cutoffTimeBean, true);
            fail("entity missing cutoffTime, PersistenceException is expected");
        } catch (PersistenceException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixCutoffTimeDao#createCutoffTime(CutoffTimeBean, boolean)} without audit, no audit
     * message should be created.
     *
     * @throws Exception to JUnit
     */
    public void testCreateCutoffTimeNoAudit() throws Exception {
        dao.createCutoffTime(cutoffTimeBean, false);

        assertNull("should not audit", auditManager.getHeader());
    }

    /**
     * Tests {@link InformixCutoffTimeDao#createCutoffTime(CutoffTimeBean, boolean)} with null entity, IAE is
     * expected.
     *
     * @throws Exception to JUnit
     */
    public void testCreateCutoffTimeNullEntity() throws Exception {
        try {
            dao.createCutoffTime(null, false);
            fail("bean is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixCutoffTimeDao#deleteCutoffTime(CutoffTimeBean, boolean)}. The entity should be
     * deleted and audit message should be created as expected.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteCutoffTime() throws Exception {
        dao.createCutoffTime(cutoffTimeBean, true);

        dao.deleteCutoffTime(cutoffTimeBean, true);

        assertNull("the entity should be deleted", dao.fetchCutoffTimeById(cutoffTimeBean.getId()));

        assertAuditCorrect(cutoffTimeBean, null);
    }

    /**
     * Tests {@link InformixCutoffTimeDao#deleteCutoffTime(CutoffTimeBean, boolean)} when audit manager failed,
     * PersistenceException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteCutoffTimeAuditFailed() throws Exception {
        dao.createCutoffTime(cutoffTimeBean, false);

        try {
            System.setProperty("exception", "AuditManagerException");

            dao.deleteCutoffTime(cutoffTimeBean, true);
            fail("audit failed, PersistenceException is expected");
        } catch (EntryNotFoundException e) {
            fail("PersistenceException is expected");
        } catch (PersistenceException e) {
            //success
        } finally {
            System.setProperty("exception", "");
        }
    }

    /**
     * Tests {@link InformixCutoffTimeDao#deleteCutoffTime(CutoffTimeBean, boolean)} without audit, no audit
     * message should be created.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteCutoffTimeNoAudit() throws Exception {
        dao.createCutoffTime(cutoffTimeBean, false);
        auditManager.getHeader(); //clear the header if it exists

        dao.deleteCutoffTime(cutoffTimeBean, false);

        assertNull("should not audit", auditManager.getHeader());
    }

    /**
     * Tests {@link InformixCutoffTimeDao#deleteCutoffTime(CutoffTimeBean, boolean)} for a not existent entity,
     * EntryNotFoundException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteCutoffTimeNotExistentEntity()
        throws Exception {
        try {
            dao.deleteCutoffTime(cutoffTimeBean, true);
            fail("the entity does not exist and  EntryNotFoundException is expected");
        } catch (EntryNotFoundException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixCutoffTimeDao#deleteCutoffTime(CutoffTimeBean, boolean)} for null entity,
     * IllegalArgumentException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteCutoffTimeNullEntity() throws Exception {
        try {
            dao.deleteCutoffTime(null, true);
            fail("bean is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixCutoffTimeDao#fetchCutoffTimeByCompanyID(long)}, the entity should be retrieved as
     * expected.
     *
     * @throws Exception to JUnit
     */
    public void testFetchCutoffTimeByCompanyID() throws Exception {
        dao.createCutoffTime(cutoffTimeBean, false);

        CutoffTimeBean bean = dao.fetchCutoffTimeByCompanyID(cutoffTimeBean.getCompanyId());
        assertNotNull("the entity should be fetched", bean);

        assertCutoffTimeBeanEquals(cutoffTimeBean, bean);
    }

    /**
     * Tests {@link InformixCutoffTimeDao#fetchCutoffTimeByCompanyID(long)} for a not existent entity, null is
     * expected.
     *
     * @throws Exception to JUnit
     */
    public void testFetchCutoffTimeByCompanyIDNotExistent()
        throws Exception {
        //        dao.createCutoffTime(cutoffTimeBean, false);
        CutoffTimeBean bean = dao.fetchCutoffTimeByCompanyID(cutoffTimeBean.getCompanyId());
        assertNull("no such entity in persistence, should return null", bean);
    }

    /**
     * Tests {@link InformixCutoffTimeDao#fetchCutoffTimeByCompanyID(long)} with invalid id, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testFetchCutoffTimeByCompanyIDNotPositive()
        throws Exception {
        try {
            dao.fetchCutoffTimeByCompanyID(0);
            fail("id is not positive and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixCutoffTimeDao#fetchCutoffTimeById(long)}, the entity should be retrieved as
     * expected.
     *
     * @throws Exception to JUnit
     */
    public void testFetchCutoffTimeById() throws Exception {
        dao.createCutoffTime(cutoffTimeBean, false);

        CutoffTimeBean bean = dao.fetchCutoffTimeById(cutoffTimeBean.getId());
        assertNotNull("the entity should be fetched", bean);

        assertCutoffTimeBeanEquals(cutoffTimeBean, bean);
    }

    /**
     * Tests {@link InformixCutoffTimeDao#fetchCutoffTimeById(long)}, id is invalid and IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testFetchCutoffTimeByIdInvalid() throws Exception {
        try {
            dao.fetchCutoffTimeById(0);
            fail("id is not positive and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixCutoffTimeDao#fetchCutoffTimeById(long)} for a not existent entity, null is
     * expected.
     *
     * @throws Exception to JUnit
     */
    public void testFetchCutoffTimeByIdNotExistent() throws Exception {
        //        dao.createCutoffTime(cutoffTimeBean, false);
        CutoffTimeBean bean = dao.fetchCutoffTimeById(cutoffTimeBean.getId());
        assertNull("no such entity in persistence, should return null", bean);
    }

    /**
     * Tests {@link InformixCutoffTimeDao#InformixCutoffTimeDao(String, String, DBConnectionFactory,
     * com.topcoder.timetracker.audit.AuditManager)} with valid arguments, the instance should be created as expected.
     */
    public void testInformixCutoffTimeDao() {
        assertNotNull("InformixCutoffTimeDao should be instantiated successfully", dao);
    }

    /**
     * Tests {@link InformixCutoffTimeDao#InformixCutoffTimeDao(String, String, DBConnectionFactory,
     * com.topcoder.timetracker.audit.AuditManager)} with empty connection name, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testInformixCutoffTimeDaoEmptyConnName()
        throws Exception {
        try {
            new InformixCutoffTimeDao(" ", DEFAULT_ID_GENERATOR_NAME, dbFactory, auditManager);
            fail("connection is empty and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixCutoffTimeDao#InformixCutoffTimeDao(String, String, DBConnectionFactory,
     * com.topcoder.timetracker.audit.AuditManager)} with invalid connection name, any persistence relative method
     * will failed.
     *
     * @throws Exception to JUnit
     */
    public void testInformixCutoffTimeDaoInvalidConnName()
        throws Exception {
        InformixCutoffTimeDao dao = new InformixCutoffTimeDao("bad_connection", DEFAULT_ID_GENERATOR_NAME, dbFactory,
                auditManager);

        try {
            dao.fetchCutoffTimeByCompanyID(1);
            fail("connection name is invalid and PersistenceException is expected");
        } catch (PersistenceException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixCutoffTimeDao#InformixCutoffTimeDao(String, String, DBConnectionFactory,
     * com.topcoder.timetracker.audit.AuditManager)} with invalid id generator name, ConfigurationException is
     * expected.
     *
     * @throws Exception to JUnit
     */
    public void testInformixCutoffTimeDaoInvalidIDGenName()
        throws Exception {
        try {
            new InformixCutoffTimeDao(DEFAULT_CONN_NAME, "no_such_id_gen_name", dbFactory, auditManager);
            fail("id generator name does not exist and ConfigurationException is expected");
        } catch (ConfigurationException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixCutoffTimeDao#InformixCutoffTimeDao(String, String, DBConnectionFactory,
     * com.topcoder.timetracker.audit.AuditManager)} with null audit manager, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testInformixCutoffTimeDaoNullAuditMgr()
        throws Exception {
        try {
            new InformixCutoffTimeDao(DEFAULT_CONN_NAME, DEFAULT_ID_GENERATOR_NAME, dbFactory, null);
            fail("audit manager is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixCutoffTimeDao#InformixCutoffTimeDao(String, String, DBConnectionFactory,
     * com.topcoder.timetracker.audit.AuditManager)} with null conn name, default connection name will be used for the
     * subsequent db operations.
     *
     * @throws Exception to JUnit
     */
    public void testInformixCutoffTimeDaoNullConnName()
        throws Exception {
        InformixCutoffTimeDao dao = new InformixCutoffTimeDao(null, DEFAULT_ID_GENERATOR_NAME, dbFactory, auditManager);
        dao.fetchCutoffTimeByCompanyID(1);

        //if connection name is null, default connection will be obtained, and query on this connection will cause
        //no exception
    }

    /**
     * Tests {@link InformixCutoffTimeDao#InformixCutoffTimeDao(String, String, DBConnectionFactory,
     * com.topcoder.timetracker.audit.AuditManager)} with null dbfactory, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testInformixCutoffTimeDaoNullDBFactory()
        throws Exception {
        try {
            new InformixCutoffTimeDao(DEFAULT_CONN_NAME, DEFAULT_ID_GENERATOR_NAME, null, auditManager);
            fail("db connection factory is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixCutoffTimeDao#InformixCutoffTimeDao(String, String, DBConnectionFactory,
     * com.topcoder.timetracker.audit.AuditManager)} with null id generator name, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testInformixCutoffTimeDaoNullIDGenName()
        throws Exception {
        try {
            new InformixCutoffTimeDao(DEFAULT_CONN_NAME, null, dbFactory, auditManager);
            fail("id generator name is empty and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixCutoffTimeDao#updateCutoffTime(CutoffTimeBean, boolean)}. The entity should be
     * updated as expected and audit message should be created as expected.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateCutoffTime() throws Exception {
        dao.createCutoffTime(cutoffTimeBean, true);

        cutoffTimeBean.setCutoffTime(new Date());
        cutoffTimeBean.setChanged(true);

        dao.updateCutoffTime(cutoffTimeBean, true);

        CutoffTimeBean bean = dao.fetchCutoffTimeById(cutoffTimeBean.getId());
        assertCutoffTimeBeanEquals(cutoffTimeBean, bean);

        assertAuditCorrect(cutoffTimeBean, bean);
    }

    /**
     * Tests {@link InformixCutoffTimeDao#updateCutoffTime(CutoffTimeBean, boolean)} when AuditManager failed
     * to create audit, PersistenceException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateCutoffTimeAuditFailed() throws Exception {
        dao.createCutoffTime(cutoffTimeBean, false);

        cutoffTimeBean.setChanged(true);

        try {
            System.setProperty("exception", "AuditManagerException");

            dao.updateCutoffTime(cutoffTimeBean, true);
            fail("audit failed, PersistenceException is expected");
        } catch (PersistenceException e) {
            //success
        } finally {
            System.setProperty("exception", "");
        }
    }

    /**
     * Tests {@link InformixCutoffTimeDao#updateCutoffTime(CutoffTimeBean, boolean)} for a not existent entity,
     * EntryNotFoundException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateCutoffTimeEntityNotExistent()
        throws Exception {
        try {
            dao.updateCutoffTime(cutoffTimeBean, true);
            fail("the entity does not exist in persistence, EntryNotFoundException is expected");
        } catch (EntryNotFoundException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixCutoffTimeDao#updateCutoffTime(CutoffTimeBean, boolean)} with entity missing
     * cutoffTime, PersistenceException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateCutoffTimeInvalidEntity_missiong_cutoffTime()
        throws Exception {
        dao.createCutoffTime(cutoffTimeBean, false);

        //use bean without cutoffTime set
        CutoffTimeBean newBean = new CutoffTimeBean();

        newBean.setChanged(true);
        newBean.setCompanyId(cutoffTimeBean.getCompanyId());
        newBean.setCreationDate(new Date());
        newBean.setCreationUser("user1");
        //        cutoffTimeBean.setCutoffTime(new Date());
        newBean.setId(cutoffTimeBean.getId());
        newBean.setModificationDate(new Date());
        newBean.setModificationUser("user2");

        try {
            dao.updateCutoffTime(newBean, true);
            fail("not such company id, PersistenceException is expected");
        } catch (PersistenceException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixCutoffTimeDao#updateCutoffTime(CutoffTimeBean, boolean)} without audit, no audit
     * message should be created.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateCutoffTimeNoAudit() throws Exception {
        dao.createCutoffTime(cutoffTimeBean, false);
        auditManager.getHeader(); //clears the header if it exists

        cutoffTimeBean.setCutoffTime(new Date());
        cutoffTimeBean.setChanged(true);

        dao.updateCutoffTime(cutoffTimeBean, false);
        assertNull("should not audit", auditManager.getHeader());
    }

    /**
     * Tests {@link InformixCutoffTimeDao#updateCutoffTime(CutoffTimeBean, boolean)}. The entity is set as not
     * changed, thus it will not be updated.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateCutoffTimeNotUpdate() throws Exception {
        dao.createCutoffTime(cutoffTimeBean, true);

        cutoffTimeBean.setCompanyId(10); //change to another company
        cutoffTimeBean.setChanged(false); //not changed, thus no update

        dao.updateCutoffTime(cutoffTimeBean, true);

        CutoffTimeBean bean = dao.fetchCutoffTimeById(cutoffTimeBean.getId());

        assertTrue("company id will not be updated", bean.getCompanyId() != 10);
    }

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.loadConfig("DBConnectionFactory.xml");
        TestHelper.initDB();

        dbFactory = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        auditManager = new MockAuditManager();
        dao = new InformixCutoffTimeDao(DEFAULT_CONN_NAME, DEFAULT_ID_GENERATOR_NAME, dbFactory, auditManager);
        cutoffTimeBean = getBean();
    }

    /**
     * Clears test environment.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        TestHelper.clearConfig();
    }

    /**
     * Asserts whether the dao creates correct audit message for the given entities. This will test the
     * AuditHeader, and all the AuditDetail.
     *
     * @param oldValue old value of the operation, may be null if it's INSERT operation
     * @param newValue new value of the operation, may be null if it's DELETE operation
     */
    private void assertAuditCorrect(CutoffTimeBean oldValue, CutoffTimeBean newValue) {
        AuditHeader header = auditManager.getHeader();
        assertNotNull("header not created", header);

        //operation type
        int type;

        if (oldValue == null) {
            type = AuditType.INSERT;
        } else if (newValue == null) {
            type = AuditType.DELETE;
        } else {
            type = AuditType.UPDATE;
        }

        CutoffTimeBean entity = (oldValue == null) ? newValue : oldValue;

        String creationUser = (type == AuditType.INSERT) ? entity.getCreationUser() : entity.getModificationUser();

        //asserts the header
        assertEquals("audit type incorrect", type, header.getActionType());
        assertEquals("ApplicationArea incorrect", ApplicationArea.TT_TIME, header.getApplicationArea());
        assertEquals("entity id incorrect", entity.getId(), header.getEntityId());
        assertEquals("table name incorrect", "cut_off_time", header.getTableName());
        assertNotNull("creation date not set", header.getCreationDate());
        assertEquals("creation user incorrect", creationUser, header.getCreationUser());

        //parses the objects' fields to String array by converting their values into string, and ordered them
        //corresponding to CUT_OFF_TIME_COLUMNS
        String[] oldColumns = parseValues(oldValue);
        String[] newColumns = parseValues(newValue);

        int num = oldColumns.length; //the number of details

        //correct the number of details for update(unchanged value will not be audited)
        if (type == AuditType.UPDATE) {
            for (int i = 0; i < oldColumns.length; i++) {
                if (oldColumns[i].equals(newColumns[i])) {
                    num--;
                }
            }
        }

        AuditDetail[] details = header.getDetails();

        assertEquals("incorrect number of columns", num, details.length);

        //converts the details array into map, assume that we don't know its order
        Map detailMap = new HashMap();

        for (int i = 0; i < details.length; i++) {
            detailMap.put(details[i].getColumnName(), details[i]);
        }

        //for each column, we get the detail from the detailMap, and test whether the oldValue and newValue equal
        //to the expected ones.
        for (int i = 0; i < CUT_OFF_TIME_COLUMNS.length; i++) {
            AuditDetail detail = (AuditDetail) detailMap.get(CUT_OFF_TIME_COLUMNS[i]);

            if (detail == null) {
                continue;
            }

            //if this column exists in audit detail, decrease the num. It's expected that if all details have
            //the correct column names, the final result of the num will be 0.
            num--;
            assertEquals("AuditDetail.oldValue for col:" + CUT_OFF_TIME_COLUMNS[i] + " incorrect", oldColumns[i],
                detail.getOldValue());
            assertEquals("AuditDetail.newValue for col:" + CUT_OFF_TIME_COLUMNS[i] + " incorrect", newColumns[i],
                detail.getNewValue());
        }

        assertEquals("audit details has incorrect column name", 0, num);
    }

    /**
     * Asserts whether the bean is same as the expected one. For Date type, assertDateEqualsWithoutNanos() is
     * used to test whether the Dates are same without nanos. The exclusion of nanos is because during the
     * persistence, for the column type for Date is Year to Second, the nanos will not be stored. So two dates will be
     * taken as equal even if they don't have the same nanos.
     *
     * @param expected the expected one
     * @param bean the bean to test
     */
    private void assertCutoffTimeBeanEquals(CutoffTimeBean expected, CutoffTimeBean bean) {
        assertEquals("compId not equal", expected.getCompanyId(), bean.getCompanyId());
        assertEquals("creation user not equal", expected.getCreationUser(), bean.getCreationUser());
        assertDateEqualsWithoutNanos("creation date not equal", expected.getCreationDate(), bean.getCreationDate());
        assertDateEqualsWithoutNanos("cutoffTime not equal", expected.getCutoffTime(), bean.getCutoffTime());
        assertEquals("modification date not equal", expected.getModificationDate(), bean.getModificationDate());
        assertDateEqualsWithoutNanos("cutoffTime not equal", expected.getCutoffTime(), bean.getCutoffTime());
        assertEquals("modification user not equal", expected.getModificationUser(), bean.getModificationUser());
    }

    /**
     * Asserts whether the date is same as the expected one. This comparison will not include the nanos of the
     * dates.
     *
     * @param msg assert message
     * @param expected the value expected
     * @param date the date to test
     */
    private void assertDateEqualsWithoutNanos(String msg, Date expected, Date date) {
        Timestamp expectedT = new Timestamp(expected.getTime());
        expectedT.setNanos(0);

        Timestamp dateT = new Timestamp(date.getTime());
        dateT.setNanos(0);

        assertEquals(msg, expectedT, dateT);
    }

    /**
     * Returns a CutoffTimeBean used in test.
     *
     * @return a CutoffTimeBean
     */
    private CutoffTimeBean getBean() {
        CutoffTimeBean cutoffTimeBean = new CutoffTimeBean();

        cutoffTimeBean.setChanged(true);
        cutoffTimeBean.setCompanyId(1);
        cutoffTimeBean.setCreationDate(new Date());
        cutoffTimeBean.setCreationUser("user1");
        cutoffTimeBean.setCutoffTime(new Date());
        cutoffTimeBean.setId(2);
        cutoffTimeBean.setModificationDate(new Date());
        cutoffTimeBean.setModificationUser("user2");

        return cutoffTimeBean;
    }

    /**
     * Parses the object's fields to String array by converting their values into string, and ordered them
     * corresponding to CUT_OFF_TIME_COLUMNS, the columns defined in DB.
     *
     * @param bean to parse
     *
     * @return string array of bean fields
     */
    private String[] parseValues(CutoffTimeBean bean) {
        if (bean == null) {
            return new String[CUT_OFF_TIME_COLUMNS.length];
        } else {
            return new String[] {
                String.valueOf(bean.getId()), String.valueOf(bean.getCompanyId()), bean.getCutoffTime().toString(),
                bean.getCreationDate().toString(), bean.getCreationUser(), bean.getModificationDate().toString(),
                bean.getModificationUser()
            };
        }
    }
}
