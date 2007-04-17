/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.ejb;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.rejectreason.RejectEmail;
import com.topcoder.timetracker.rejectreason.TestHelper;

import java.lang.reflect.Field;

import java.util.List;

import javax.ejb.SessionContext;


/**
 * Unit test for <code>RejectEmailSessionBean</code> class.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class RejectEmailSessionBeanTests extends TestHelper {
    /** The RejectEmailSessionBean instance to be tested. */
    private RejectEmailSessionBean bean = null;

    /**
     * Sets up the test environment. New instance of RejectEmailSessionBean is created. We also bind DataSource to JNDI
     * context, add necessary environment variables, and load configurations.
     *
     * @throws Exception pass to JUnit.
     */
    protected void setUp() throws Exception {
        loadConfig();
        setUpSessionBeanEnvironment();
        bean = new RejectEmailSessionBean();
    }

    /**
     * Tests constructor.
     */
    public void testConstructor() {
        assertNotNull("Unable to instantiate RejectEmailSessionBean.", bean);
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
     * Tests createRejectEmail method accuracy. Verifies the operation is delegated correctly.
     *
     * @throws Exception pass to JUnit.
     */
    public void testCreateRejectEmail_accuracy() throws Exception {
        // Set inner DAO instance to our mock implementation through reflection
        Field field = RejectEmailSessionBean.class.getDeclaredField("dao");
        field.setAccessible(true);

        MockRejectEmailDAO dao = new MockRejectEmailDAO();
        field.set(emailBean, dao);

        RejectEmail rejectEmail = new RejectEmail();
        bean.createRejectEmail(rejectEmail, "lever", true);

        assertEquals("The operation is not correctly delegated.", "createRejectEmail", dao.getMethod());

        List params = dao.getParameters();
        assertEquals("The operation is not correctly delegated.", rejectEmail, params.get(0));
        assertEquals("The operation is not correctly delegated.", "lever", params.get(1));
        assertEquals("The operation is not correctly delegated.", new Boolean(true), params.get(2));
    }

    /**
     * Tests retrieveRejectEmail method accuracy. Verifies the operation is delegated correctly.
     *
     * @throws Exception pass to JUnit.
     */
    public void testRetrieveRejectEmail_accuracy() throws Exception {
        // Set inner DAO instance to our mock implementation through reflection
        Field field = RejectEmailSessionBean.class.getDeclaredField("dao");
        field.setAccessible(true);

        MockRejectEmailDAO dao = new MockRejectEmailDAO();
        field.set(emailBean, dao);

        bean.retrieveRejectEmail(100);

        assertEquals("The operation is not correctly delegated.", "retrieveRejectEmail", dao.getMethod());
        assertEquals("The operation is not correctly delegated.", new Long(100), dao.getParameters().get(0));
    }

    /**
     * Tests updateRejectEmail method accuracy. Verifies the operation is delegated correctly.
     *
     * @throws Exception pass to JUnit.
     */
    public void testUpdateRejectEmail_accuracy() throws Exception {
        // Set inner DAO instance to our mock implementation through reflection
        Field field = RejectEmailSessionBean.class.getDeclaredField("dao");
        field.setAccessible(true);

        MockRejectEmailDAO dao = new MockRejectEmailDAO();
        field.set(emailBean, dao);

        RejectEmail rejectEmail = new RejectEmail();
        bean.updateRejectEmail(rejectEmail, "lever", true);

        assertEquals("The operation is not correctly delegated.", "updateRejectEmail", dao.getMethod());

        List params = dao.getParameters();
        assertEquals("The operation is not correctly delegated.", rejectEmail, params.get(0));
        assertEquals("The operation is not correctly delegated.", "lever", params.get(1));
        assertEquals("The operation is not correctly delegated.", new Boolean(true), params.get(2));
    }

    /**
     * Tests deleteRejectEmail method accuracy. Verifies the operation is delegated correctly.
     *
     * @throws Exception pass to JUnit.
     */
    public void testDeleteRejectEmail_accuracy() throws Exception {
        // Set inner DAO instance to our mock implementation through reflection
        Field field = RejectEmailSessionBean.class.getDeclaredField("dao");
        field.setAccessible(true);

        MockRejectEmailDAO dao = new MockRejectEmailDAO();
        field.set(emailBean, dao);

        RejectEmail rejectEmail = new RejectEmail();
        bean.deleteRejectEmail(rejectEmail, "lever", true);

        assertEquals("The operation is not correctly delegated.", "deleteRejectEmail", dao.getMethod());

        List params = dao.getParameters();
        assertEquals("The operation is not correctly delegated.", rejectEmail, params.get(0));
        assertEquals("The operation is not correctly delegated.", "lever", params.get(1));
        assertEquals("The operation is not correctly delegated.", new Boolean(true), params.get(2));
    }

    /**
     * Tests listRejectEmails method accuracy. Verifies the operation is delegated correctly.
     *
     * @throws Exception pass to JUnit.
     */
    public void testListRejectEmails_accuracy() throws Exception {
        // Set inner DAO instance to our mock implementation through reflection
        Field field = RejectEmailSessionBean.class.getDeclaredField("dao");
        field.setAccessible(true);

        MockRejectEmailDAO dao = new MockRejectEmailDAO();
        field.set(emailBean, dao);

        bean.listRejectEmails();

        assertEquals("The operation is not correctly delegated.", "listRejectEmails", dao.getMethod());
    }

    /**
     * Tests searchRejectEmails method accuracy. Verifies the operation is delegated correctly.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchRejectEmails_accuracy() throws Exception {
        // Set inner DAO instance to our mock implementation through reflection
        Field field = RejectEmailSessionBean.class.getDeclaredField("dao");
        field.setAccessible(true);

        MockRejectEmailDAO dao = new MockRejectEmailDAO();
        field.set(emailBean, dao);

        Filter filter = new EqualToFilter("key", "value");
        bean.searchRejectEmails(filter);

        assertEquals("The operation is not correctly delegated.", "searchRejectEmails", dao.getMethod());
        assertEquals("The operation is not correctly delegated.", filter, dao.getParameters().get(0));
    }
}
