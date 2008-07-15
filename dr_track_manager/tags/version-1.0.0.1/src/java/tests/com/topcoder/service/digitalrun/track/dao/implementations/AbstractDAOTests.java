/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.dao.implementations;

import com.topcoder.service.digitalrun.entity.Track;
import com.topcoder.service.digitalrun.track.DigitalRunTrackManagerPersistenceException;
import com.topcoder.service.digitalrun.track.EntityNotFoundException;

import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import static org.easymock.EasyMock.*;

import org.easymock.IMocksControl;

import java.util.ArrayList;

import javax.ejb.SessionContext;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;


/**
 * <p>
 * Unit test cases for the class <code>AbstractDAO.</code>
 * </p>
 *  @author waits
 * @version 1.0
 */
public class AbstractDAOTests extends TestCase {
    /** AbstractDAO instance to test against. */
    private AbstractDAO dao = null;

    /**
     * Create instance.
     */
    protected void setUp() {
        dao = new AbstractDAO() { };
    }

    /**
     * Verify the ctor.
     */
    public void testCtor() {
        assertNotNull("Fail to create dao.", dao);
        assertNull("The logger is null.", dao.getLogger());
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#setLogger(com.topcoder.util.log.Log)} method. It is an accuracy test case.
     * </p>
     */
    public void testSetLogger() {
        Log logger = LogManager.getLog();
        dao.setLogger(logger);
        assertEquals("invalid", logger, dao.getLogger());
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#getLogger()} method. It is an accuracy test case.
     * </p>
     */
    public void testgetLogger() {
        Log logger = LogManager.getLog();
        dao.setLogger(logger);
        assertEquals("invalid", logger, dao.getLogger());
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#setLogger(com.topcoder.util.log.Log)} method with null value. IAE expected.
     * </p>
     */
    public void testSetLogger_null() {
        try {
            dao.setLogger(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#setUnitName(String)} method. It is an accuracy test case.
     * </p>
     */
    public void testSetUnitName() {
        dao.setUnitName("persistUnit");
        assertEquals("invalid", "persistUnit", dao.getUnitName());
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#getUnitName()} method. It is an accuracy test case.
     * </p>
     */
    public void testgetUnitName() {
        dao.setUnitName("persistUnit");
        assertEquals("invalid", "persistUnit", dao.getUnitName());
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#setUnitName(String)} method with null value. IAE expected.
     * </p>
     */
    public void testSetUnitName_null() {
        try {
            dao.setUnitName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#setUnitName(String)} method with empty value. IAE expected.
     * </p>
     */
    public void testSetUnitName_empty() {
        try {
            dao.setUnitName(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#setSessionContext(SessionContext)} method. It is an accuracy test case.
     * </p>
     */
    public void testsetSessionContext() {
        SessionContext context = EasyMock.createMock(SessionContext.class);
        dao.setSessionContext(context);
        assertEquals("invalid", context, dao.getSessionContext());
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#getSessionContext()} method. It is an accuracy test case.
     * </p>
     */
    public void testgetSessionContext() {
        SessionContext context = EasyMock.createMock(SessionContext.class);
        dao.setSessionContext(context);
        assertEquals("invalid", context, dao.getSessionContext());
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#setSessionContext(SessionContext)} method with null value. IAE expected.
     * </p>
     */
    public void testsetSessionContext_null() {
        try {
            dao.setSessionContext(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#getEntityManager()} method. It is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetEntityManager() throws Exception {
        EntityManager em = EasyMock.createMock(EntityManager.class);
        SessionContext context = EasyMock.createMock(SessionContext.class);
        dao.setSessionContext(context);
        dao.setUnitName("unitName");
        EasyMock.expect(context.lookup("unitName")).andReturn(em);
        EasyMock.replay(context, em);
        assertEquals("Invalid.", em, this.dao.getEntityManager());
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#getEntityManager()} method. The result is invalid.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetEntityManager_invalid() throws Exception {
        SessionContext context = EasyMock.createMock(SessionContext.class);
        dao.setSessionContext(context);
        dao.setUnitName("unitName");
        EasyMock.expect(context.lookup("unitName")).andReturn(null);
        EasyMock.replay(context);

        try {
            this.dao.getEntityManager();
            fail("invalid.");
        } catch (DigitalRunTrackManagerPersistenceException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#persist(com.topcoder.service.digitalrun.entity.BaseEntity)} method. It is an
     * accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testPersist() throws Exception {
        Track track = new Track();
        IMocksControl control = EasyMock.createNiceControl();
        EntityManager em = control.createMock(EntityManager.class);
        SessionContext context = control.createMock(SessionContext.class);

        dao.setSessionContext(context);
        dao.setUnitName("unitName");

        expect(context.lookup("unitName")).andReturn(em);

        replay(context);
        track = dao.persist(track, "track");
        em.persist(track);
        em.flush();
        verify(context);
        verify(em);
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#persist(com.topcoder.service.digitalrun.entity.BaseEntity)} method.  exception
     * occurs during persist, DigitalRunTrackManagerPersistenceException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testPersist_alreadyExists() throws Exception {
        Track track = new Track();
        IMocksControl control = EasyMock.createNiceControl();
        EntityManager em = control.createMock(EntityManager.class);
        SessionContext context = control.createMock(SessionContext.class);

        dao.setSessionContext(context);
        dao.setUnitName("unitName");

        expect(context.lookup("unitName")).andReturn(em);
        em.persist(track);
        em.flush();
        control.andThrow(new EntityExistsException());
        replay(context);

        try {
            track = dao.persist(track, "Track");
            fail("invalid.");
        } catch (DigitalRunTrackManagerPersistenceException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#persist(com.topcoder.service.digitalrun.entity.BaseEntity)} method. Exception occurs
     * during persist, DigitalRunTrackManagerPersistenceException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testPersist_TransactionRequiredException()
        throws Exception {
        Track track = new Track();
        IMocksControl control = EasyMock.createNiceControl();
        EntityManager em = control.createMock(EntityManager.class);
        SessionContext context = control.createMock(SessionContext.class);

        dao.setSessionContext(context);
        dao.setUnitName("unitName");

        expect(context.lookup("unitName")).andReturn(em);
        em.persist(track);
        em.flush();
        control.andThrow(new TransactionRequiredException());
        replay(context);

        try {
            track = dao.persist(track, "Track");
            fail("invalid.");
        } catch (DigitalRunTrackManagerPersistenceException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#persist(com.topcoder.service.digitalrun.entity.BaseEntity)} method. Exception occurs
     * during persist, DigitalRunTrackManagerPersistenceException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testPersist_PersistenceException() throws Exception {
        Track track = new Track();
        IMocksControl control = EasyMock.createNiceControl();
        EntityManager em = control.createMock(EntityManager.class);
        SessionContext context = control.createMock(SessionContext.class);

        dao.setSessionContext(context);
        dao.setUnitName("unitName");

        expect(context.lookup("unitName")).andReturn(em);
        em.persist(track);
        em.flush();
        control.andThrow(new PersistenceException());
        replay(context);

        try {
            track = dao.persist(track, "Track");
            fail("invalid.");
        } catch (DigitalRunTrackManagerPersistenceException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#update(com.topcoder.service.digitalrun.entity.BaseEntity)} method. It is an accuracy
     * test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testUpdate() throws Exception {
        Track track = new Track();
        track.setId(1);

        IMocksControl control = EasyMock.createNiceControl();
        EntityManager em = control.createMock(EntityManager.class);
        SessionContext context = control.createMock(SessionContext.class);

        dao.setSessionContext(context);
        dao.setUnitName("unitName");

        expect(context.lookup("unitName")).andReturn(em);
        expect(em.find(Track.class, 1L)).andReturn(track);
        replay(context);
        dao.update(track, "Track");
        verify(context);
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#update(com.topcoder.service.digitalrun.entity.BaseEntity)} method. It is an accuracy
     * test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testUpdate_notexist() throws Exception {
        Track track = new Track();

        IMocksControl control = EasyMock.createNiceControl();
        EntityManager em = control.createMock(EntityManager.class);
        SessionContext context = control.createMock(SessionContext.class);

        dao.setSessionContext(context);
        dao.setUnitName("unitName");
        expect(context.lookup("unitName")).andReturn(em);
        replay(context);

        try {
            dao.update(track, "Track");
            fail("Not exist.");
        } catch (EntityNotFoundException e) {
            //good
        }

        verify(context);
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#persist(com.topcoder.service.digitalrun.entity.BaseEntity)} method. Exception occurs
     * during persist, DigitalRunTrackManagerPersistenceException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testUpdate_TransactionRequiredException()
        throws Exception {
        Track track = new Track();
        track.setId(1);

        IMocksControl control = EasyMock.createNiceControl();
        EntityManager em = control.createMock(EntityManager.class);
        SessionContext context = control.createMock(SessionContext.class);

        dao.setSessionContext(context);
        dao.setUnitName("unitName");

        expect(context.lookup("unitName")).andReturn(em);
        expect(em.find(Track.class, 1L)).andReturn(track);
        expect(em.merge(track)).andReturn(track);
        em.flush();
        control.andThrow(new TransactionRequiredException());
        replay(context);

        try {
            dao.update(track, "Track");
            fail("invalid.");
        } catch (DigitalRunTrackManagerPersistenceException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#persist(com.topcoder.service.digitalrun.entity.BaseEntity)} method. Exception occurs
     * during persist, EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testUpdate_IllegalArgumentException() throws Exception {
        Track track = new Track();
        IMocksControl control = EasyMock.createNiceControl();
        EntityManager em = control.createMock(EntityManager.class);
        SessionContext context = control.createMock(SessionContext.class);

        dao.setSessionContext(context);
        dao.setUnitName("unitName");

        expect(context.lookup("unitName")).andReturn(em);

        expect(em.merge(track)).andReturn(track);
        em.flush();

        control.andThrow(new IllegalArgumentException());
        replay(context);

        try {
            dao.update(track, "Track");
            fail("invalid.");
        } catch (EntityNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#persist(com.topcoder.service.digitalrun.entity.BaseEntity)} method. Exception occurs
     * during persist, EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testUpdate_PersistenceException() throws Exception {
        Track track = new Track();
        track.setId(1);

        IMocksControl control = EasyMock.createNiceControl();
        EntityManager em = control.createMock(EntityManager.class);
        SessionContext context = control.createMock(SessionContext.class);

        dao.setSessionContext(context);
        dao.setUnitName("unitName");

        expect(context.lookup("unitName")).andReturn(em);
        expect(em.find(Track.class, 1L)).andReturn(track);
        expect(em.merge(track)).andReturn(track);
        em.flush();

        control.andThrow(new PersistenceException());
        replay(context);

        try {
            dao.update(track, "Track");
            fail("invalid.");
        } catch (DigitalRunTrackManagerPersistenceException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#update(com.topcoder.service.digitalrun.entity.BaseEntity)} method. the entity does
     * not exist. EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemove_notExist() throws Exception {
        IMocksControl control = EasyMock.createNiceControl();
        EntityManager em = control.createMock(EntityManager.class);
        SessionContext context = control.createMock(SessionContext.class);

        dao.setSessionContext(context);
        dao.setUnitName("unitName");

        expect(context.lookup("unitName")).andReturn(em);

        replay(context);

        try {
            dao.remove(Track.class, 1L);
            fail("Not exist.");
        } catch (EntityNotFoundException e) {
            //good
        }

        verify(context);
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#update(com.topcoder.service.digitalrun.entity.BaseEntity)} method. It is an accuracy
     * test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testFind() throws Exception {
        IMocksControl control = EasyMock.createNiceControl();
        EntityManager em = control.createMock(EntityManager.class);
        SessionContext context = control.createMock(SessionContext.class);

        dao.setSessionContext(context);
        dao.setUnitName("unitName");

        expect(context.lookup("unitName")).andReturn(em);
        expect(em.find(Track.class, 1L)).andReturn(new Track());
        replay(context);
        dao.find(Track.class, 1L);

        verify(context);
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#findAll(com.topcoder.service.digitalrun.entity.BaseEntity)} method. It is an
     * accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testFindAll() throws Exception {
        IMocksControl control = EasyMock.createNiceControl();
        EntityManager em = control.createMock(EntityManager.class);
        SessionContext context = control.createMock(SessionContext.class);

        dao.setSessionContext(context);
        dao.setUnitName("unitName");

        expect(context.lookup("unitName")).andReturn(em);

        Query query = control.createMock(Query.class);
        expect(em.createQuery("FROM Track")).andReturn(query);
        expect(query.getResultList()).andReturn(new ArrayList<Track>());
        replay(context);
        assertEquals("invalid.", 0, dao.findAll(Track.class).size());

        verify(context);
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#findAll(com.topcoder.service.digitalrun.entity.BaseEntity)} method. It is an
     * accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testFindAll_empty() throws Exception {
        IMocksControl control = EasyMock.createNiceControl();
        EntityManager em = control.createMock(EntityManager.class);
        SessionContext context = control.createMock(SessionContext.class);

        dao.setSessionContext(context);
        dao.setUnitName("unitName");

        expect(context.lookup("unitName")).andReturn(em);

        Query query = control.createMock(Query.class);
        expect(em.createQuery("FROM Track")).andReturn(query);
        expect(query.getResultList()).andReturn(null);
        replay(context);
        assertEquals("invalid.", 0, dao.findAll(Track.class).size());

        verify(context);
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#findAll(com.topcoder.service.digitalrun.entity.BaseEntity)} method. Exception
     * occurs.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testFindAll_exception() throws Exception {
        IMocksControl control = EasyMock.createNiceControl();
        EntityManager em = control.createMock(EntityManager.class);
        SessionContext context = control.createMock(SessionContext.class);

        dao.setSessionContext(context);
        dao.setUnitName("unitName");

        expect(context.lookup("unitName")).andReturn(em);

        replay(context);

        try {
            dao.findAll(Track.class);
            fail("fail");
        } catch (DigitalRunTrackManagerPersistenceException e) {
            //good
        }

        verify(context);
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#update(com.topcoder.service.digitalrun.entity.BaseEntity)} method. The entity does
     * not exist. EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testFind_notExist() throws Exception {
        IMocksControl control = EasyMock.createNiceControl();
        EntityManager em = control.createMock(EntityManager.class);
        SessionContext context = control.createMock(SessionContext.class);

        dao.setSessionContext(context);
        dao.setUnitName("unitName");

        expect(context.lookup("unitName")).andReturn(em);
        replay(context);

        try {
            dao.find(Track.class, 1L);
            fail("The track does not exist.");
        } catch (EntityNotFoundException e) {
            //good
        }

        verify(context);
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#update(com.topcoder.service.digitalrun.entity.BaseEntity)} method. The entity does
     * not exist. EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testFind_notExist2() throws Exception {
        IMocksControl control = EasyMock.createNiceControl();
        EntityManager em = control.createMock(EntityManager.class);
        SessionContext context = control.createMock(SessionContext.class);

        dao.setSessionContext(context);
        dao.setUnitName("unitName");

        expect(context.lookup("unitName")).andReturn(em);
        expect(em.find(Track.class, 1L)).andThrow(new IllegalArgumentException());

        replay(context);

        try {
            dao.find(Track.class, 1L);
            fail("The track does not exist.");
        } catch (EntityNotFoundException e) {
            //good
        }

        verify(context);
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#update(com.topcoder.service.digitalrun.entity.BaseEntity)} method. The entity does
     * not exist. DigitalRunTrackManagerPersistenceException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testFind_TransactionRequiredException()
        throws Exception {
        IMocksControl control = EasyMock.createNiceControl();
        EntityManager em = control.createMock(EntityManager.class);
        SessionContext context = control.createMock(SessionContext.class);

        dao.setSessionContext(context);
        dao.setUnitName("unitName");

        expect(context.lookup("unitName")).andReturn(em);
        expect(em.find(Track.class, 1L)).andThrow(new TransactionRequiredException());

        replay(context);

        try {
            dao.find(Track.class, 1L);
            fail("The track does not exist.");
        } catch (DigitalRunTrackManagerPersistenceException e) {
            //good
        }

        verify(context);
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#update(com.topcoder.service.digitalrun.entity.BaseEntity)} method. The entity does
     * not exist. DigitalRunTrackManagerPersistenceException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testFind_PersistenceException() throws Exception {
        IMocksControl control = EasyMock.createNiceControl();
        EntityManager em = control.createMock(EntityManager.class);
        SessionContext context = control.createMock(SessionContext.class);

        dao.setSessionContext(context);
        dao.setUnitName("unitName");

        expect(context.lookup("unitName")).andReturn(em);
        expect(em.find(Track.class, 1L)).andThrow(new PersistenceException());

        replay(context);

        try {
            dao.find(Track.class, 1L);
            fail("The track does not exist.");
        } catch (DigitalRunTrackManagerPersistenceException e) {
            //good
        }

        verify(context);
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#update(com.topcoder.service.digitalrun.entity.BaseEntity)} method. It is an accuracy
     * test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemove() throws Exception {
        IMocksControl control = EasyMock.createNiceControl();
        EntityManager em = control.createMock(EntityManager.class);
        SessionContext context = control.createMock(SessionContext.class);

        dao.setSessionContext(context);
        dao.setUnitName("unitName");

        expect(context.lookup("unitName")).andReturn(em);
        expect(em.find(Track.class, 1L)).andReturn(new Track());
        replay(context);
        dao.remove(Track.class, 1L);

        verify(context);
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#persist(com.topcoder.service.digitalrun.entity.BaseEntity)} method. Exception occurs
     * during persist, DigitalRunTrackManagerPersistenceException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemove_PersistenceException() throws Exception {
        IMocksControl control = EasyMock.createNiceControl();
        EntityManager em = control.createMock(EntityManager.class);
        SessionContext context = control.createMock(SessionContext.class);

        dao.setSessionContext(context);
        dao.setUnitName("unitName");

        expect(context.lookup("unitName")).andReturn(em);
        expect(em.find(Track.class, 1L)).andReturn(new Track());

        em.flush();

        control.andThrow(new PersistenceException());
        replay(context);

        try {
            dao.remove(Track.class, 1L);
            fail("invalid.");
        } catch (DigitalRunTrackManagerPersistenceException e) {
            //e.printStackTrace();
        }
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#persist(com.topcoder.service.digitalrun.entity.BaseEntity)} method. Exception occurs
     * during persist, EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemove_IllegalArgumentException() throws Exception {
        IMocksControl control = EasyMock.createNiceControl();
        EntityManager em = control.createMock(EntityManager.class);
        SessionContext context = control.createMock(SessionContext.class);

        dao.setSessionContext(context);
        dao.setUnitName("unitName");

        expect(context.lookup("unitName")).andReturn(em);
        expect(em.find(Track.class, 1L)).andReturn(new Track());

        em.flush();

        control.andThrow(new IllegalArgumentException());
        replay(context);

        try {
            dao.remove(Track.class, 1L);
            fail("invalid.");
        } catch (EntityNotFoundException e) {
            //e.printStackTrace();
        }
    }

    /**
     * <p>
     * Test the {@link AbstractDAO#persist(com.topcoder.service.digitalrun.entity.BaseEntity)} method. Exception occurs
     * during persist, DigitalRunTrackManagerPersistenceException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemove_TransactionRequiredException()
        throws Exception {
        IMocksControl control = EasyMock.createNiceControl();
        EntityManager em = control.createMock(EntityManager.class);
        SessionContext context = control.createMock(SessionContext.class);

        dao.setSessionContext(context);
        dao.setUnitName("unitName");

        expect(context.lookup("unitName")).andReturn(em);
        expect(em.find(Track.class, 1L)).andReturn(new Track());

        em.flush();

        control.andThrow(new TransactionRequiredException());
        replay(context);

        try {
            dao.remove(Track.class, 1L);
            fail("invalid.");
        } catch (DigitalRunTrackManagerPersistenceException e) {
            //good
        }
    }
}
