/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base.ejb;

import com.topcoder.timetracker.entry.base.BaseEntry;
import com.topcoder.timetracker.entry.base.ConfigurationException;
import com.topcoder.timetracker.entry.base.CutoffTimeBean;
import com.topcoder.timetracker.entry.base.EntryManagerException;
import com.topcoder.timetracker.entry.base.TestHelper;

import junit.framework.TestCase;

import java.sql.Timestamp;

import java.util.Calendar;
import java.util.Date;


/**
 * Test case for EntryDelegate.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class EntryDelegateTest extends TestCase {
    /** Default namespace for EntryDelegate. */
    private static final String DEFAULT_NAMESPACE = "EntryDelegate";

    /** Default CutoffTimeBean instance used in test. */
    private CutoffTimeBean cutoffTimeBean;

    /** Default instance of EntryDelegate used in this test. */
    private EntryDelegate delegate;

    /**
     * Tests {@link EntryDelegate#canSubmitEntry(BaseEntry)}. The entry should be able to be submitted before
     * the cutoffTime.
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

        delegate.createCutoffTime(bean, true);

        BaseEntry entry = new BaseEntry() {
            };

        entry.setCompanyId(1);
        entry.setDate(new Date()); //the entry date is today

        assertTrue("entry can be submitted since the cutoff time for it is tomorrow", delegate.canSubmitEntry(entry));
    }

    /**
     * Tests {@link EntryDelegate#canSubmitEntry(BaseEntry)}. The entry should not be able to be submitted
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

        delegate.createCutoffTime(bean, true);

        Calendar entryDate = Calendar.getInstance();
        entryDate.add(Calendar.DAY_OF_YEAR, -14); //the entry date is 2 weeks ago

        BaseEntry entry = new BaseEntry() {
            };

        entry.setCompanyId(1);
        entry.setDate(entryDate.getTime());

        assertFalse("can not submit entry after its cutoff time", delegate.canSubmitEntry(entry));
    }

    /**
     * Tests {@link EntryDelegate#canSubmitEntry(BaseEntry)}. The entry in the future should not be able to be
     * submit.
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

        delegate.createCutoffTime(bean, true);

        Calendar entryDate = Calendar.getInstance();
        entryDate.add(Calendar.HOUR, 1); //the entry date is 1 hour later

        BaseEntry entry = new BaseEntry() {
            };

        entry.setCompanyId(1);
        entry.setDate(entryDate.getTime());

        assertFalse("can not submit entry in the future", delegate.canSubmitEntry(entry));
    }

    /**
     * Tests {@link EntryDelegate#canSubmitEntry(BaseEntry)} with null, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testCanSubmitEntryNull() throws Exception {
        try {
            delegate.canSubmitEntry(null);
            fail("argument is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link EntryDelegate#canSubmitEntry(BaseEntry)} for entry having no date, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testCanSubmitEntryNullDate() throws Exception {
        BaseEntry entry = new BaseEntry() {
            };

        entry.setCompanyId(1);

        try {
            delegate.canSubmitEntry(entry);
            fail("entry has no date and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link EntryDelegate#createCutoffTime(CutoffTimeBean, boolean)}. The bean will be created and
     * tested whether it can be retrieved.
     *
     * @throws Exception to JUnit
     */
    public void testCreateCutoffTime() throws Exception {
        delegate.createCutoffTime(cutoffTimeBean, true);

        CutoffTimeBean bean = delegate.fetchCutoffTimeById(cutoffTimeBean.getId());

        assertCutoffTimeBeanEquals(cutoffTimeBean, bean);
    }

    /**
     * Tests {@link EntryDelegate#createCutoffTime(CutoffTimeBean, boolean)} when AuditManger failed to create
     * any audit.
     */
    public void testCreateCutoffTimeAuditFailed() {
        try {
            System.setProperty("exception", "AuditManagerException");

            delegate.createCutoffTime(cutoffTimeBean, true);
            fail("audit failed, EntryManagerException is expected");
        } catch (EntryManagerException e) {
            //success
        } finally {
            System.setProperty("exception", "");
        }
    }

    /**
     * Tests {@link EntryDelegate#createCutoffTime(CutoffTimeBean, boolean)} with duplicate company id,
     * EntryManagerException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testCreateCutoffTimeExistentCompId() throws Exception {
        delegate.createCutoffTime(cutoffTimeBean, false);

        cutoffTimeBean.setId(cutoffTimeBean.getId() + 1);

        try {
            delegate.createCutoffTime(cutoffTimeBean, false);
            fail("entity with the same company id already exist, EntryManagerException is expected");
        } catch (EntryManagerException e) {
            //success
        }
    }

    /**
     * Tests {@link EntryDelegate#createCutoffTime(CutoffTimeBean, boolean)} with duplicate id,
     * EntryManagerException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testCreateCutoffTimeExistentId() throws Exception {
        delegate.createCutoffTime(cutoffTimeBean, false);

        cutoffTimeBean.setCompanyId(2); //change to another company, but with same id

        try {
            delegate.createCutoffTime(cutoffTimeBean, false);
            fail("entity with the same id already exist, EntryManagerException is expected");
        } catch (EntryManagerException e) {
            //success
        }
    }

    /**
     * Tests {@link EntryDelegate#createCutoffTime(CutoffTimeBean, boolean)} with id not being set, Id
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
        delegate.createCutoffTime(cutoffTimeBean, true);

        CutoffTimeBean bean = delegate.fetchCutoffTimeById(cutoffTimeBean.getId());

        assertCutoffTimeBeanEquals(cutoffTimeBean, bean);
    }

    /**
     * Tests {@link EntryDelegate#createCutoffTime(CutoffTimeBean, boolean)} for a not existent company,
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
            delegate.createCutoffTime(cutoffTimeBean, true);
            fail("not such company id, EntryManagerException is expected");
        } catch (EntryManagerException e) {
            //success
        }
    }

    /**
     * Tests {@link EntryDelegate#createCutoffTime(CutoffTimeBean, boolean)} with entity missing creation user,
     * EntryManagerException is expected.
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
            delegate.createCutoffTime(cutoffTimeBean, true);
            fail("entity missing creationUser, EntryManagerException is expected");
        } catch (EntryManagerException e) {
            //success
        }
    }

    /**
     * Tests {@link EntryDelegate#createCutoffTime(CutoffTimeBean, boolean)} with entity missing cutoffTime,
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
            delegate.createCutoffTime(cutoffTimeBean, true);
            fail("entity missing cutoffTime, EntryManagerException is expected");
        } catch (EntryManagerException e) {
            //success
        }
    }

    /**
     * Tests {@link EntryDelegate#createCutoffTime(CutoffTimeBean, boolean)} with null entity, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testCreateCutoffTimeNullEntity() throws Exception {
        try {
            delegate.createCutoffTime(null, false);
            fail("bean is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link EntryDelegate#deleteCutoffTime(CutoffTimeBean, boolean)}. The entity should be deleted.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteCutoffTime() throws Exception {
        delegate.createCutoffTime(cutoffTimeBean, true);

        delegate.deleteCutoffTime(cutoffTimeBean, true);

        assertNull("the entity should be deleted", delegate.fetchCutoffTimeById(cutoffTimeBean.getId()));
    }

    /**
     * Tests {@link EntryDelegate#deleteCutoffTime(CutoffTimeBean, boolean)} when audit manager failed,
     * EntryManagerException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteCutoffTimeAuditFailed() throws Exception {
        delegate.createCutoffTime(cutoffTimeBean, false);

        try {
            System.setProperty("exception", "AuditManagerException");

            delegate.deleteCutoffTime(cutoffTimeBean, true);
            fail("audit failed, EntryManagerException is expected");
        } catch (EntryManagerException e) {
            //success
        } finally {
            System.setProperty("exception", "");
        }
    }

    /**
     * Tests {@link EntryDelegate#deleteCutoffTime(CutoffTimeBean, boolean)} for a not existent entity,
     * EntryManagerException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteCutoffTimeNotExistentEntity()
        throws Exception {
        try {
            delegate.deleteCutoffTime(cutoffTimeBean, true);
            fail("the entity does not exist and  EntryManagerException is expected");
        } catch (EntryManagerException e) {
            //success
        }
    }

    /**
     * Tests {@link EntryDelegate#deleteCutoffTime(CutoffTimeBean, boolean)} for null entity,
     * IllegalArgumentException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteCutoffTimeNullEntity() throws Exception {
        try {
            delegate.deleteCutoffTime(null, true);
            fail("bean is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link EntryDelegate#EntryDelegate(String)}.
     */
    public void testEntryDelegate() {
        assertNotNull("EntryDelegate should be created", delegate);
    }

    /**
     * Tests {@link EntryDelegate#EntryDelegate(String)} with empty string.
     *
     * @throws Exception to junit
     */
    public void testEntryDelegateEmpty() throws Exception {
        try {
            new EntryDelegate(" ");
            fail("namespace is empty and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link EntryDelegate#EntryDelegate(String)} with config missing context name.
     *
     * @throws Exception to junit
     */
    public void testEntryDelegateMissing_context_name()
        throws Exception {
        TestHelper.removeProperty(DEFAULT_NAMESPACE, "context_name");
        assertNotNull("missing context_name, default context will be used", new EntryDelegate(DEFAULT_NAMESPACE));
    }

    /**
     * Tests {@link EntryDelegate#EntryDelegate(String)} with config missing jndi home.
     *
     * @throws Exception to junit
     */
    public void testEntryDelegateMissing_jndi_home() throws Exception {
        TestHelper.removeProperty(DEFAULT_NAMESPACE, "jndi_home");

        try {
            new EntryDelegate(DEFAULT_NAMESPACE);
            fail("jndi_home is missing and  ConfigurationException is expected");
        } catch (ConfigurationException e) {
            //success
        }
    }

    /**
     * Tests {@link EntryDelegate#EntryDelegate(String)} with not existent namespace.
     *
     * @throws Exception to junit
     */
    public void testEntryDelegateNotExistentNamespace()
        throws Exception {
        try {
            new EntryDelegate("no_such_namespace");
            fail("no such namespace and ConfigurationException is expected");
        } catch (ConfigurationException e) {
            //success
        }
    }

    /**
     * Tests {@link EntryDelegate#EntryDelegate(String)} with null namespace.
     *
     * @throws Exception to junit
     */
    public void testEntryDelegateNull() throws Exception {
        try {
            new EntryDelegate(null);
            fail("namespace is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link EntryDelegate#fetchCutoffTimeByCompanyID(long)}, the entity should be retrieved as
     * expected.
     *
     * @throws Exception to JUnit
     */
    public void testFetchCutoffTimeByCompanyID() throws Exception {
        delegate.createCutoffTime(cutoffTimeBean, false);

        CutoffTimeBean bean = delegate.fetchCutoffTimeByCompanyID(cutoffTimeBean.getCompanyId());
        assertNotNull("the entity should be fetched", bean);

        assertCutoffTimeBeanEquals(cutoffTimeBean, bean);
    }

    /**
     * Tests {@link EntryDelegate#fetchCutoffTimeByCompanyID(long)} for a not existent entity, null is
     * expected.
     *
     * @throws Exception to JUnit
     */
    public void testFetchCutoffTimeByCompanyIDNotExistent()
        throws Exception {
        //delegate.createCutoffTime(cutoffTimeBean, false);
        CutoffTimeBean bean = delegate.fetchCutoffTimeByCompanyID(cutoffTimeBean.getCompanyId());
        assertNull("no such entity in persistence, should return null", bean);
    }

    /**
     * Tests {@link EntryDelegate#fetchCutoffTimeById(long)}, the entity should be retrieved as expected.
     *
     * @throws Exception to JUnit
     */
    public void testFetchCutoffTimeById() throws Exception {
        delegate.createCutoffTime(cutoffTimeBean, false);

        CutoffTimeBean bean = delegate.fetchCutoffTimeById(cutoffTimeBean.getId());
        assertNotNull("the entity should be fetched", bean);

        assertCutoffTimeBeanEquals(cutoffTimeBean, bean);
    }

    /**
     * Tests {@link EntryDelegate#fetchCutoffTimeById(long)} for a not existent entity, null is expected.
     *
     * @throws Exception to JUnit
     */
    public void testFetchCutoffTimeByIdNotExistent() throws Exception {
        //delegate.createCutoffTime(cutoffTimeBean, false);
        CutoffTimeBean bean = delegate.fetchCutoffTimeById(cutoffTimeBean.getId());
        assertNull("no such entity in persistence, should return null", bean);
    }

    /**
     * Tests {@link EntryDelegate#updateCutoffTime(CutoffTimeBean, boolean)}. The entity should be updated as
     * expected.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateCutoffTime() throws Exception {
        delegate.createCutoffTime(cutoffTimeBean, true);

        cutoffTimeBean.setCutoffTime(new Date());
        cutoffTimeBean.setChanged(true);

        delegate.updateCutoffTime(cutoffTimeBean, true);

        CutoffTimeBean bean = delegate.fetchCutoffTimeById(cutoffTimeBean.getId());
        assertCutoffTimeBeanEquals(cutoffTimeBean, bean);
    }

    /**
     * Tests {@link EntryDelegate#updateCutoffTime(CutoffTimeBean, boolean)} when AuditManager failed to create
     * audit, EntryManagerException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateCutoffTimeAuditFailed() throws Exception {
        delegate.createCutoffTime(cutoffTimeBean, false);

        cutoffTimeBean.setChanged(true);

        try {
            System.setProperty("exception", "AuditManagerException");

            delegate.updateCutoffTime(cutoffTimeBean, true);
            fail("audit failed, EntryManagerException is expected");
        } catch (EntryManagerException e) {
            //success
        } finally {
            System.setProperty("exception", "");
        }
    }

    /**
     * Tests {@link EntryDelegate#updateCutoffTime(CutoffTimeBean, boolean)} for a not existent entity,
     * EntryManagerException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateCutoffTimeEntityNotExistent()
        throws Exception {
        try {
            delegate.updateCutoffTime(cutoffTimeBean, true);
            fail("the entity does not exist in persistence, EntryManagerException is expected");
        } catch (EntryManagerException e) {
            //success
        }
    }

    /**
     * Tests {@link EntryDelegate#updateCutoffTime(CutoffTimeBean, boolean)} with entity missing cutoffTime,
     * EntryManagerException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateCutoffTimeInvalidEntity_missiong_cutoffTime()
        throws Exception {
        delegate.createCutoffTime(cutoffTimeBean, false);

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
            delegate.updateCutoffTime(newBean, true);
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
        TestHelper.loadDefaultConfig();
        TestHelper.loadConfig("DBConnectionFactory.xml");
        TestHelper.loadConfig("EntryDelegateTest.xml");
        TestHelper.backupConfig("EntryDelegateTest.xml");
        TestHelper.initDB();
        TestHelper.initJNDI();
        cutoffTimeBean = getBean();

        delegate = new EntryDelegate(DEFAULT_NAMESPACE);
    }

    /**
     * Clears test environment.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        TestHelper.restoreConfig("EntryDelegateTest.xml");
        TestHelper.restoreJNDI();
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
