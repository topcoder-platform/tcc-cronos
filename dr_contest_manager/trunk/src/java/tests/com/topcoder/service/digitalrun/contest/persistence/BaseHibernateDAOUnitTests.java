/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest.persistence;


import javax.persistence.Query;

import org.easymock.EasyMock;

import com.topcoder.service.digitalrun.contest.BaseTestCase;
import com.topcoder.service.digitalrun.contest.PersistenceException;
import com.topcoder.service.digitalrun.entity.TrackContest;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * Unit tests for <code>BaseHibernateDAO</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseHibernateDAOUnitTests extends BaseTestCase {


    /**
     * <p>
     * Instance of <code>BaseHibernateDAO</code> should be created.
     * </p>
     */
    public void testConstructor() {
        assertNotNull(new BaseHibernateDAO() { });
    }

    /**
     * <p>
     * Test {@link BaseHibernateDAO#getSessionContext()} and
     * {@link BaseHibernateDAO#setSessionContext(javax.ejb.SessionContext)}.
     * </p>
     */
    public void testSetAndGetSessionContext() {
        BaseHibernateDAO dao = new ExtendedBaseHibernateDAO();
        assertNull("SessionContext should be null by default", dao.getSessionContext());
        dao.setSessionContext(CONTEXT);
        assertEquals("SessionContext should be set", CONTEXT, dao.getSessionContext());
    }

    /**
     * <p>
     * Test {@link BaseHibernateDAO#getLogger()} and
     * {@link BaseHibernateDAO#setLogger(Log)}.
     * </p>
     */
    public void testSetAndGetLogger() {
        BaseHibernateDAO dao = new ExtendedBaseHibernateDAO();
        assertNull("Logger should be null by default", dao.getLogger());
        Log logger = LogManager.getLog();
        dao.setLogger(logger);
        assertEquals("Logger should be set", logger, dao.getLogger());
    }

    /**
     * <p>
     * Test {@link BaseHibernateDAO#getUnitName()} and
     * {@link BaseHibernateDAO#setUnitName(String)}.
     * </p>
     */
    public void testSetAndGetUnitName() {
        BaseHibernateDAO dao = new ExtendedBaseHibernateDAO();
        assertNull("Unit name should be null by default", dao.getUnitName());
        String unitName = "SomeUnit";
        dao.setUnitName(unitName);
        assertEquals("Unit name should be set", unitName, dao.getUnitName());
    }

    /**
     * <p>
     * Test {@link BaseHibernateDAO#setSessionContext(javax.ejb.SessionContext)}.
     * </p>
     *
     * <p>
     * Given session context is null.
     * </p>
     */
    public void testSetSessionContext() {
        BaseHibernateDAO dao = new ExtendedBaseHibernateDAO();
        try {
            dao.setSessionContext(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test {@link BaseHibernateDAO#setLogger(Log)}.
     * </p>
     *
     * <p>
     * Given log is null.
     * </p>
     */
    public void testSetLogger() {
        BaseHibernateDAO dao = new ExtendedBaseHibernateDAO();
        try {
            dao.setLogger(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test {@link BaseHibernateDAO#setUnitName(String)}.
     * </p>
     *
     * <p>
     * Given unit name is null.
     * </p>
     */
    public void testSetUnitName_1() {
        BaseHibernateDAO dao = new ExtendedBaseHibernateDAO();
        try {
            dao.setUnitName(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test {@link BaseHibernateDAO#setUnitName(String)}.
     * </p>
     *
     * <p>
     * Given unit name is empty.
     * </p>
     */
    public void testSetUnitName_2() {
        BaseHibernateDAO dao = new ExtendedBaseHibernateDAO();
        try {
            dao.setUnitName(" ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link BaseHibernateDAO#getEntityManager()}.
     * </p>
     *
     * <p>
     * The session context has not been injected.
     * </p>
     */
    public void testGetEntityManager() {
        try {
            new ExtendedBaseHibernateDAO().getEntityManager();
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link BaseHibernateDAO#checkNullWithLogging(Object, String)}.
     * </p>
     *
     * <p>
     * Given object is null.
     * </p>
     */
    public void testCheckNullWithLogging() {
        try {
            new ExtendedBaseHibernateDAO().checkNullWithLogging(null, "test");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link BaseHibernateDAO#queryEntities(javax.persistence.EntityManager, String)}.
     * </p>
     *
     * <p>
     * Persistence error occurs.
     * </p>
     */
    public void testQueryEntities() {
        Query query = EasyMock.createNiceMock(Query.class);

        EasyMock.reset(EM);
        EasyMock.expect(EM.createQuery("query")).andStubReturn(query);
        query.getResultList();
        EasyMock.expectLastCall().andStubThrow(
            new javax.persistence.PersistenceException("mock exception"));
        EasyMock.replay(EM, query);

        try {
            new ExtendedBaseHibernateDAO().queryEntities(EM, "query");
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link BaseHibernateDAO#findEntity(javax.persistence.EntityManager, Class, long)}.
     * </p>
     *
     * <p>
     * Persistence error occurs.
     * </p>
     */
    public void testFindEntity() {
        EasyMock.reset(EM);
        EasyMock.expect(EM.find(TrackContest.class, 1L)).andStubThrow(
            new javax.persistence.PersistenceException("mock exception"));
        EasyMock.replay(EM);

        try {
            new ExtendedBaseHibernateDAO().findEntity(EM, TrackContest.class, 1L);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Extended <code>BaseHibernateDAO</code>.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    private static class ExtendedBaseHibernateDAO extends BaseHibernateDAO {
    }
}
