/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.ejb;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.rejectreason.RejectReason;
import com.topcoder.timetracker.rejectreason.TestHelper;

import java.lang.reflect.Field;

import java.util.List;

import javax.ejb.SessionContext;


/**
 * Unit test for <code>RejectReasonSessionBean</code> class.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class RejectReasonSessionBeanTests extends TestHelper {
    /** The RejectReasonSessionBean instance to be tested. */
    private RejectReasonSessionBean bean = null;

    /**
     * Sets up the test environment. New instance of RejectReasonSessionBean is created. We also bind DataSource to
     * JNDI context, add necessary environment variables, and load configurations.
     *
     * @throws Exception pass to JUnit.
     */
    protected void setUp() throws Exception {
        loadConfig();
        setUpSessionBeanEnvironment();
        bean = new RejectReasonSessionBean();
    }

    /**
     * Tests constructor.
     */
    public void testConstructor() {
        assertNotNull("Unable to instantiate RejectReasonSessionBean.", bean);
    }

    /**
     * Tests ejbCreate method. Verifies no exception is thrown.
     */
    public void testEjbCreate_noException() {
        bean.ejbCreate();
    }

    /**
     * Tests ejbActivate method. Verifies no exception is thrown.
     */
    public void testEjbActivate_noException() {
        bean.ejbActivate();
    }

    /**
     * Tests ejbPassivate method. Verifies no exception is thrown.
     */
    public void testEjbPassivate_noException() {
        bean.ejbPassivate();
    }

    /**
     * Tests ejbRemove method. Verifies no exception is thrown.
     */
    public void testEjbRemove_noException() {
        bean.ejbRemove();
    }

    /**
     * Tests setSessionContext and getSessionContext methods accuracy.
     */
    public void testSetAndGetSessionContext_accuracy() {
        SessionContext context = new MockSessionContext();
        bean.setSessionContext(context);
        assertEquals("Either setSessionContext or getSessionContext does not function correctly.", context,
            bean.getSessionContext());
    }

    /**
     * Tests getDAO method.
     *
     * @throws Exception pass to JUnit.
     */
    public void testGetDAO() throws Exception {
        assertNotNull("The DAO instance returned should not be null.", bean.getDAO());
    }

    /**
     * Tests createRejectReason method accuracy. Verifies the operation is delegated correctly.
     *
     * @throws Exception pass to JUnit.
     */
    public void testCreateRejectReason_accuracy() throws Exception {
        // Set inner DAO instance to our mock implementation through reflection
        Field field = RejectReasonSessionBean.class.getDeclaredField("dao");
        field.setAccessible(true);

        MockRejectReasonDAO dao = new MockRejectReasonDAO();
        field.set(reasonBean, dao);

        RejectReason rejectReason = new RejectReason();
        bean.createRejectReason(rejectReason, "lever", true);

        assertEquals("The operation is not correctly delegated.", "createRejectReason", dao.getMethod());

        List params = dao.getParameters();
        assertEquals("The operation is not correctly delegated.", rejectReason, params.get(0));
        assertEquals("The operation is not correctly delegated.", "lever", params.get(1));
        assertEquals("The operation is not correctly delegated.", new Boolean(true), params.get(2));
    }

    /**
     * Tests retrieveRejectReason method accuracy. Verifies the operation is delegated correctly.
     *
     * @throws Exception pass to JUnit.
     */
    public void testRetrieveRejectReason_accuracy() throws Exception {
        // Set inner DAO instance to our mock implementation through reflection
        Field field = RejectReasonSessionBean.class.getDeclaredField("dao");
        field.setAccessible(true);

        MockRejectReasonDAO dao = new MockRejectReasonDAO();
        field.set(reasonBean, dao);

        bean.retrieveRejectReason(100);

        assertEquals("The operation is not correctly delegated.", "retrieveRejectReason", dao.getMethod());
        assertEquals("The operation is not correctly delegated.", new Long(100), dao.getParameters().get(0));
    }

    /**
     * Tests updateRejectReason method accuracy. Verifies the operation is delegated correctly.
     *
     * @throws Exception pass to JUnit.
     */
    public void testUpdateRejectReason_accuracy() throws Exception {
        // Set inner DAO instance to our mock implementation through reflection
        Field field = RejectReasonSessionBean.class.getDeclaredField("dao");
        field.setAccessible(true);

        MockRejectReasonDAO dao = new MockRejectReasonDAO();
        field.set(reasonBean, dao);

        RejectReason rejectReason = new RejectReason();
        bean.updateRejectReason(rejectReason, "lever", true);

        assertEquals("The operation is not correctly delegated.", "updateRejectReason", dao.getMethod());

        List params = dao.getParameters();
        assertEquals("The operation is not correctly delegated.", rejectReason, params.get(0));
        assertEquals("The operation is not correctly delegated.", "lever", params.get(1));
        assertEquals("The operation is not correctly delegated.", new Boolean(true), params.get(2));
    }

    /**
     * Tests deleteRejectReason method accuracy. Verifies the operation is delegated correctly.
     *
     * @throws Exception pass to JUnit.
     */
    public void testDeleteRejectReason_accuracy() throws Exception {
        // Set inner DAO instance to our mock implementation through reflection
        Field field = RejectReasonSessionBean.class.getDeclaredField("dao");
        field.setAccessible(true);

        MockRejectReasonDAO dao = new MockRejectReasonDAO();
        field.set(reasonBean, dao);

        RejectReason rejectReason = new RejectReason();
        bean.deleteRejectReason(rejectReason, "lever", true);

        assertEquals("The operation is not correctly delegated.", "deleteRejectReason", dao.getMethod());

        List params = dao.getParameters();
        assertEquals("The operation is not correctly delegated.", rejectReason, params.get(0));
        assertEquals("The operation is not correctly delegated.", "lever", params.get(1));
        assertEquals("The operation is not correctly delegated.", new Boolean(true), params.get(2));
    }

    /**
     * Tests listRejectReasons method accuracy. Verifies the operation is delegated correctly.
     *
     * @throws Exception pass to JUnit.
     */
    public void testListRejectReasons_accuracy() throws Exception {
        // Set inner DAO instance to our mock implementation through reflection
        Field field = RejectReasonSessionBean.class.getDeclaredField("dao");
        field.setAccessible(true);

        MockRejectReasonDAO dao = new MockRejectReasonDAO();
        field.set(reasonBean, dao);

        bean.listRejectReasons();

        assertEquals("The operation is not correctly delegated.", "listRejectReasons", dao.getMethod());
    }

    /**
     * Tests searchRejectReasons method accuracy. Verifies the operation is delegated correctly.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchRejectReasons_accuracy() throws Exception {
        // Set inner DAO instance to our mock implementation through reflection
        Field field = RejectReasonSessionBean.class.getDeclaredField("dao");
        field.setAccessible(true);

        MockRejectReasonDAO dao = new MockRejectReasonDAO();
        field.set(reasonBean, dao);

        Filter filter = new EqualToFilter("key", "value");
        bean.searchRejectReasons(filter);

        assertEquals("The operation is not correctly delegated.", "searchRejectReasons", dao.getMethod());
        assertEquals("The operation is not correctly delegated.", filter, dao.getParameters().get(0));
    }
}
