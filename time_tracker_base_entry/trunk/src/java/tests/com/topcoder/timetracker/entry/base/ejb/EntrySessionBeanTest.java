/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base.ejb;

import com.topcoder.timetracker.entry.base.BaseEntry;
import com.topcoder.timetracker.entry.base.CutoffTimeBean;
import com.topcoder.timetracker.entry.base.EntryManagerException;
import com.topcoder.timetracker.entry.base.MockSessionContext;
import com.topcoder.timetracker.entry.base.TestHelper;

import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

import java.sql.Timestamp;

import java.util.Calendar;
import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.SessionContext;


/**
 * Test case for EntrySessionBean.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class EntrySessionBeanTest extends TestCase {
    /** Default namespace used to in ejbCreate(). */
    private static final String DEFAULT_NAMESPACE = "com.topcoder.timetracker.entry.base.ejb.EntrySessionBean";

    /** Default CutoffTimeBean instance used in test. */
    private CutoffTimeBean cutoffTimeBean;

    /** Default EntrySessionBean instance used in this test. */
    private EntrySessionBean sessionBean;

    /** Default SessionContext instance used in this test. */
    private SessionContext sessionContext;

    /**
     * Tests {@link EntrySessionBean#canSubmitEntry(BaseEntry)}. The entry should be able to be submitted
     * before the cutoffTime.
     *
     * @throws Exception to JUnit
     */
    public void testCanSubmitEntry() throws Exception {
        CutoffTimeBean bean = new CutoffTimeBean();
        bean.setCompanyId(1);
        bean.setCreationUser("user1");
        bean.setCreationDate(new Date());
        bean.setModificationDate(new Date());
        bean.setModificationUser("user1");

        Calendar cutoff = Calendar.getInstance();
        cutoff.add(Calendar.DAY_OF_YEAR, 1); //set cutoff time next day of week
        bean.setCutoffTime(cutoff.getTime());

        sessionBean.createCutoffTime(bean, true);

        BaseEntry entry = new BaseEntry() {
            };

        entry.setCompanyId(1);
        entry.setDate(new Date()); //the entry date is today

        assertTrue("entry can be submitted since the cutoff time for it is tomorrow",
                sessionBean.canSubmitEntry(entry));
    }

    /**
     * Tests {@link EntrySessionBean#canSubmitEntry(BaseEntry)}. The entry should not be able to be submitted
     * after the cutoffTime.
     *
     * @throws Exception to JUnit
     */
    public void testCanSubmitEntryAfterCutoff() throws Exception {
        CutoffTimeBean bean = new CutoffTimeBean();
        bean.setCompanyId(1);
        bean.setCreationUser("user1");
        bean.setCreationDate(new Date());
        bean.setModificationDate(new Date());
        bean.setModificationUser("user1");

        Calendar cutoff = Calendar.getInstance();
        cutoff.add(Calendar.DAY_OF_YEAR, 1); //set cutoff time next day of week
        bean.setCutoffTime(cutoff.getTime());

        sessionBean.createCutoffTime(bean, true);

        Calendar entryDate = Calendar.getInstance();
        entryDate.add(Calendar.DAY_OF_YEAR, -14); //the entry date is 2 weeks ago

        BaseEntry entry = new BaseEntry() {
            };

        entry.setCompanyId(1);
        entry.setDate(entryDate.getTime());

        assertFalse("can not submit entry after its cutoff time", sessionBean.canSubmitEntry(entry));
    }

    /**
     * Tests {@link EntrySessionBean#canSubmitEntry(BaseEntry)}. The entry in the future should not be able to
     * be submit.
     *
     * @throws Exception to JUnit
     */
    public void testCanSubmitEntryBeforeNow() throws Exception {
        CutoffTimeBean bean = new CutoffTimeBean();
        bean.setCompanyId(1);
        bean.setCreationUser("user1");
        bean.setCreationDate(new Date());
        bean.setModificationDate(new Date());
        bean.setModificationUser("user1");

        Calendar cutoff = Calendar.getInstance();
        cutoff.add(Calendar.DAY_OF_YEAR, 1); //set cutoff time next day of week
        bean.setCutoffTime(cutoff.getTime());

        sessionBean.createCutoffTime(bean, true);

        Calendar entryDate = Calendar.getInstance();
        entryDate.add(Calendar.HOUR, 1); //the entry date is 1 hour later

        BaseEntry entry = new BaseEntry() {
            };

        entry.setCompanyId(1);
        entry.setDate(entryDate.getTime());

        assertFalse("can not submit entry in the future", sessionBean.canSubmitEntry(entry));
    }

    /**
     * Tests {@link EntrySessionBean#canSubmitEntry(BaseEntry)} with null, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testCanSubmitEntryNull() throws Exception {
        try {
            sessionBean.canSubmitEntry(null);
            fail("argument is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link EntrySessionBean#canSubmitEntry(BaseEntry)} for entry having no date, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testCanSubmitEntryNullDate() throws Exception {
        BaseEntry entry = new BaseEntry() {
            };

        entry.setCompanyId(1);

        try {
            sessionBean.canSubmitEntry(entry);
            fail("entry has no date and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link EntrySessionBean#createCutoffTime(CutoffTimeBean, boolean)}. The bean will be created and
     * tested whether it can be retrieved.
     *
     * @throws Exception to JUnit
     */
    public void testCreateCutoffTime() throws Exception {
        sessionBean.createCutoffTime(cutoffTimeBean, true);

        CutoffTimeBean bean = sessionBean.fetchCutoffTimeById(cutoffTimeBean.getId());

        assertCutoffTimeBeanEquals(cutoffTimeBean, bean);
    }

    /**
     * Tests {@link EntrySessionBean#createCutoffTime(CutoffTimeBean, boolean)} when AuditManger failed to
     * create any audit.
     */
    public void testCreateCutoffTimeAuditFailed() {
        try {
            System.setProperty("exception", "AuditManagerException");

            sessionBean.createCutoffTime(cutoffTimeBean, true);
            fail("audit failed, EntryManagerException is expected");
        } catch (EntryManagerException e) {
            //success
        } finally {
            System.setProperty("exception", "");
        }
    }

    /**
     * Tests {@link EntrySessionBean#createCutoffTime(CutoffTimeBean, boolean)} with duplicate company id,
     * EntryManagerException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testCreateCutoffTimeExistentCompId() throws Exception {
        sessionBean.createCutoffTime(cutoffTimeBean, false);

        cutoffTimeBean.setId(cutoffTimeBean.getId() + 1);

        try {
            sessionBean.createCutoffTime(cutoffTimeBean, false);
            fail("entity with the same company id already exist, EntryManagerException is expected");
        } catch (EntryManagerException e) {
            //success
        }
    }

    /**
     * Tests {@link EntrySessionBean#createCutoffTime(CutoffTimeBean, boolean)} with duplicate id,
     * EntryManagerException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testCreateCutoffTimeExistentId() throws Exception {
        sessionBean.createCutoffTime(cutoffTimeBean, false);

        cutoffTimeBean.setCompanyId(2); //change to another company, but with same id

        try {
            sessionBean.createCutoffTime(cutoffTimeBean, false);
            fail("entity with the same id already exist, EntryManagerException is expected");
        } catch (EntryManagerException e) {
            //success
        }
    }

    /**
     * Tests {@link EntrySessionBean#createCutoffTime(CutoffTimeBean, boolean)} with id not being set, Id
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
        sessionBean.createCutoffTime(cutoffTimeBean, true);

        CutoffTimeBean bean = sessionBean.fetchCutoffTimeById(cutoffTimeBean.getId());

        assertCutoffTimeBeanEquals(cutoffTimeBean, bean);
    }

    /**
     * Tests {@link EntrySessionBean#createCutoffTime(CutoffTimeBean, boolean)} for a not existent company,
     * EntryManagerException is expected.
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
            sessionBean.createCutoffTime(cutoffTimeBean, true);
            fail("not such company id, EntryManagerException is expected");
        } catch (EntryManagerException e) {
            //success
        }
    }

    /**
     * Tests {@link EntrySessionBean#createCutoffTime(CutoffTimeBean, boolean)} with entity missing creation
     * user, EntryManagerException is expected.
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
        //cutoffTimeBean.setCreationUser("user1");
        cutoffTimeBean.setCutoffTime(new Date());
        cutoffTimeBean.setId(1);
        cutoffTimeBean.setModificationDate(new Date());
        cutoffTimeBean.setModificationUser("user2");

        try {
            sessionBean.createCutoffTime(cutoffTimeBean, true);
            fail("entity missing creationUser, EntryManagerException is expected");
        } catch (EntryManagerException e) {
            //success
        }
    }

    /**
     * Tests {@link EntrySessionBean#createCutoffTime(CutoffTimeBean, boolean)} with entity missing cutoffTime,
     * EntryManagerException is expected.
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
        //cutoffTimeBean.setCutoffTime(new Date());
        cutoffTimeBean.setId(1);
        cutoffTimeBean.setModificationDate(new Date());
        cutoffTimeBean.setModificationUser("user2");

        try {
            sessionBean.createCutoffTime(cutoffTimeBean, true);
            fail("entity missing cutoffTime, EntryManagerException is expected");
        } catch (EntryManagerException e) {
            //success
        }
    }

    /**
     * Tests {@link EntrySessionBean#createCutoffTime(CutoffTimeBean, boolean)} with null entity, IAE is
     * expected.
     *
     * @throws Exception to JUnit
     */
    public void testCreateCutoffTimeNullEntity() throws Exception {
        try {
            sessionBean.createCutoffTime(null, false);
            fail("bean is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link EntrySessionBean#deleteCutoffTime(CutoffTimeBean, boolean)}. The entity should be deleted.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteCutoffTime() throws Exception {
        sessionBean.createCutoffTime(cutoffTimeBean, true);

        sessionBean.deleteCutoffTime(cutoffTimeBean, true);

        assertNull("the entity should be deleted", sessionBean.fetchCutoffTimeById(cutoffTimeBean.getId()));
    }

    /**
     * Tests {@link EntrySessionBean#deleteCutoffTime(CutoffTimeBean, boolean)} when audit manager failed,
     * EntryManagerException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteCutoffTimeAuditFailed() throws Exception {
        sessionBean.createCutoffTime(cutoffTimeBean, false);

        try {
            System.setProperty("exception", "AuditManagerException");

            sessionBean.deleteCutoffTime(cutoffTimeBean, true);
            fail("audit failed, EntryManagerException is expected");
        } catch (EntryManagerException e) {
            //success
        } finally {
            System.setProperty("exception", "");
        }
    }

    /**
     * Tests {@link EntrySessionBean#deleteCutoffTime(CutoffTimeBean, boolean)} for a not existent entity,
     * EntryManagerException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteCutoffTimeNotExistentEntity()
        throws Exception {
        try {
            sessionBean.deleteCutoffTime(cutoffTimeBean, true);
            fail("the entity does not exist and  EntryManagerException is expected");
        } catch (EntryManagerException e) {
            //success
        }
    }

    /**
     * Tests {@link EntrySessionBean#deleteCutoffTime(CutoffTimeBean, boolean)} for null entity,
     * IllegalArgumentException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteCutoffTimeNullEntity() throws Exception {
        try {
            sessionBean.deleteCutoffTime(null, true);
            fail("bean is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link EntrySessionBean#ejbActivate()}, nothing should happen.
     */
    public void testEjbActivate() {
        sessionBean.ejbActivate();

        //nothing should happen
    }

    /**
     * Tests {@link EntrySessionBean#ejbCreate()}, the ejb should be created as expected.
     */
    public void testEjbCreate() {
        assertNotNull("SessionBean should be created successfully", sessionBean);
    }

    /**
     * Tests {@link EntrySessionBean#ejbCreate()} with config missing dao key, CreateException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testEjbCreateMissingDaoKey() throws Exception {
        TestHelper.removeProperty(DEFAULT_NAMESPACE, "dao_key");

        EntrySessionBean bean = new EntrySessionBean();
        bean.setSessionContext(sessionContext);

        try {
            bean.ejbCreate();
            fail("missing dao_key, can not create SessionBean");
        } catch (CreateException e) {
            //success
        }
    }

    /**
     * Tests {@link EntrySessionBean#ejbCreate()} with config missing given namespace, CreateException is
     * expected.
     *
     * @throws Exception to JUnit
     */
    public void testEjbCreateMissingNamespace() throws Exception {
        ConfigManager.getInstance().removeNamespace(DEFAULT_NAMESPACE);

        EntrySessionBean bean = new EntrySessionBean();
        bean.setSessionContext(sessionContext);

        try {
            bean.ejbCreate();
            fail("missing namespace, can not create SessionBean");
        } catch (CreateException e) {
            //success
        }
    }

    /**
     * Tests {@link EntrySessionBean#ejbCreate()} with config missing of_namespace, CreateException is
     * expected.
     *
     * @throws Exception to JUnit
     */
    public void testEjbCreateMissingOFNamespace() throws Exception {
        TestHelper.removeProperty(DEFAULT_NAMESPACE, "of_namespace");

        EntrySessionBean bean = new EntrySessionBean();
        bean.setSessionContext(sessionContext);

        try {
            bean.ejbCreate();
            fail("missing of_namespace, can not create SessionBean");
        } catch (CreateException e) {
            //success
        }
    }

    /**
     * Tests {@link EntrySessionBean#ejbPassivate()}, nothing should happen.
     */
    public void testEjbPassivate() {
        sessionBean.ejbPassivate();

        //nothing should happen
    }

    /**
     * Tests {@link EntrySessionBean#ejbRemove()}, nothing should happen.
     */
    public void testEjbRemove() {
        sessionBean.ejbRemove();

        //nothing should happen
    }

    /**
     * Tests {@link EntrySessionBean#EntrySessionBean()}, the instance should be created.
     */
    public void testEntrySessionBean() {
        assertNotNull("should be instantiated successfully", new EntrySessionBean());
    }

    /**
     * Tests {@link EntrySessionBean#fetchCutoffTimeByCompanyID(long)}, the entity should be retrieved as
     * expected.
     *
     * @throws Exception to JUnit
     */
    public void testFetchCutoffTimeByCompanyID() throws Exception {
        sessionBean.createCutoffTime(cutoffTimeBean, false);

        CutoffTimeBean bean = sessionBean.fetchCutoffTimeByCompanyID(cutoffTimeBean.getCompanyId());
        assertNotNull("the entity should be fetched", bean);

        assertCutoffTimeBeanEquals(cutoffTimeBean, bean);
    }

    /**
     * Tests {@link EntrySessionBean#fetchCutoffTimeByCompanyID(long)} for a not existent entity, null is
     * expected.
     *
     * @throws Exception to JUnit
     */
    public void testFetchCutoffTimeByCompanyIDNotExistent()
        throws Exception {
        //sessionBean.createCutoffTime(cutoffTimeBean, false);
        CutoffTimeBean bean = sessionBean.fetchCutoffTimeByCompanyID(cutoffTimeBean.getCompanyId());
        assertNull("no such entity in persistence, should return null", bean);
    }

    /**
     * Tests {@link EntrySessionBean#fetchCutoffTimeById(long)}, the entity should be retrieved as expected.
     *
     * @throws Exception to JUnit
     */
    public void testFetchCutoffTimeById() throws Exception {
        sessionBean.createCutoffTime(cutoffTimeBean, false);

        CutoffTimeBean bean = sessionBean.fetchCutoffTimeById(cutoffTimeBean.getId());
        assertNotNull("the entity should be fetched", bean);

        assertCutoffTimeBeanEquals(cutoffTimeBean, bean);
    }

    /**
     * Tests {@link EntrySessionBean#fetchCutoffTimeById(long)} for a not existent entity, null is expected.
     *
     * @throws Exception to JUnit
     */
    public void testFetchCutoffTimeByIdNotExistent() throws Exception {
        //sessionBean.createCutoffTime(cutoffTimeBean, false);
        CutoffTimeBean bean = sessionBean.fetchCutoffTimeById(cutoffTimeBean.getId());
        assertNull("no such entity in persistence, should return null", bean);
    }

    /**
     * Tests {@link EntrySessionBean#getDao()}, dao should be retrieved.
     */
    public void testGetDao() {
        assertNotNull("dao should be obtained", sessionBean.getDao());
    }

    /**
     * Tests {@link EntrySessionBean#getSessionContext()}, sessionContext should be retrieved.
     */
    public void testGetSessionContext() {
        assertEquals("sessionContext should be retrieved as expected", sessionContext, sessionBean.getSessionContext());
    }

    /**
     * Tests {@link EntrySessionBean#setSessionContext()}, sessionContext should be set.
     */
    public void testSetSessionContext() {
        assertEquals("sessionContext should be retrieved as expected", sessionContext, sessionBean.getSessionContext());
    }

    /**
     * Tests {@link EntrySessionBean#updateCutoffTime(CutoffTimeBean, boolean)}. The entity should be updated
     * as expected.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateCutoffTime() throws Exception {
        sessionBean.createCutoffTime(cutoffTimeBean, true);

        cutoffTimeBean.setCutoffTime(new Date());
        cutoffTimeBean.setChanged(true);

        sessionBean.updateCutoffTime(cutoffTimeBean, true);

        CutoffTimeBean bean = sessionBean.fetchCutoffTimeById(cutoffTimeBean.getId());
        assertCutoffTimeBeanEquals(cutoffTimeBean, bean);
    }

    /**
     * Tests {@link EntrySessionBean#updateCutoffTime(CutoffTimeBean, boolean)} when AuditManager failed to
     * create audit, EntryManagerException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateCutoffTimeAuditFailed() throws Exception {
        sessionBean.createCutoffTime(cutoffTimeBean, false);

        cutoffTimeBean.setChanged(true);

        try {
            System.setProperty("exception", "AuditManagerException");

            sessionBean.updateCutoffTime(cutoffTimeBean, true);
            fail("audit failed, EntryManagerException is expected");
        } catch (EntryManagerException e) {
            //success
        } finally {
            System.setProperty("exception", "");
        }
    }

    /**
     * Tests {@link EntrySessionBean#updateCutoffTime(CutoffTimeBean, boolean)} for a not existent entity,
     * EntryManagerException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateCutoffTimeEntityNotExistent()
        throws Exception {
        try {
            sessionBean.updateCutoffTime(cutoffTimeBean, true);
            fail("the entity does not exist in persistence, EntryManagerException is expected");
        } catch (EntryManagerException e) {
            //success
        }
    }

    /**
     * Tests {@link EntrySessionBean#updateCutoffTime(CutoffTimeBean, boolean)} with entity missing cutoffTime,
     * EntryManagerException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateCutoffTimeInvalidEntity_missiong_cutoffTime()
        throws Exception {
        sessionBean.createCutoffTime(cutoffTimeBean, false);

        //use bean without cutoffTime set
        CutoffTimeBean newBean = new CutoffTimeBean();

        newBean.setChanged(true);
        newBean.setCompanyId(cutoffTimeBean.getCompanyId());
        newBean.setCreationDate(new Date());
        newBean.setCreationUser("user1");
        //cutoffTimeBean.setCutoffTime(new Date());
        newBean.setId(cutoffTimeBean.getId());
        newBean.setModificationDate(new Date());
        newBean.setModificationUser("user2");

        try {
            sessionBean.updateCutoffTime(newBean, true);
            fail("not such company id, EntryManagerException is expected");
        } catch (EntryManagerException e) {
            //success
        }
    }

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.loadConfig("DBConnectionFactory.xml");
        TestHelper.loadConfig("EntrySessionBeanTest.xml");
        TestHelper.backupConfig("EntrySessionBeanTest.xml");
        TestHelper.initDB();

        sessionContext = new MockSessionContext();
        sessionBean = new EntrySessionBean();
        sessionBean.setSessionContext(sessionContext);
        sessionBean.ejbCreate();
        cutoffTimeBean = getBean();
    }

    /**
     * Clears test environment.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        TestHelper.restoreConfig("EntrySessionBeanTest.xml");
        TestHelper.clearConfig();
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
}
