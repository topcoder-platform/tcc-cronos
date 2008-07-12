package com.topcoder.service.digitalrun.track.accuracytests;

import java.lang.reflect.Method;

import javax.ejb.SessionContext;
import javax.persistence.EntityManager;

import com.topcoder.service.digitalrun.track.dao.implementations.AbstractDAO;
import com.topcoder.service.digitalrun.track.dao.implementations.JPADigitalRunPointsCalculatorDAO;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

import junit.framework.TestCase;

public class AbstractDAOAccTests extends TestCase {
    /**
     * The AbstractDAO instance for test.
     */
    private AbstractDAO dao;

    /**
     * sets up the test environment.
     */
    protected void setUp() {
        dao = new JPADigitalRunPointsCalculatorDAO();
    }

    /**
     * tear down the test environment.
     */
    protected void tearDown() {
        dao = null;
    }

    /**
     * the accuracy test for the constructor.
     */
    public void testCtor() {
        assertNotNull("The instance should not be null.", dao);
    }

    /**
     * the accuracy test for the method {@link AbstractDAO#getEntityManager()}.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testgetEntityManager() throws Exception {
        dao.setSessionContext(new MockSessionContext());

        Method info = AbstractDAO.class.getDeclaredMethod("getEntityManager");
        info.setAccessible(true);

        EntityManager manager = (EntityManager) info.invoke(dao);
        assertNotNull("The instance should not be null.", manager);
    }

    /**
     * the accuracy test for the method {@link AbstractDAO#getLogger()}.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testLogger() throws Exception {
        Method info = AbstractDAO.class.getDeclaredMethod("getLogger");
        info.setAccessible(true);

        Log logger = LogManager.getLog();
        dao.setLogger(logger);

        Log result = (Log) info.invoke(dao);
        assertEquals("The result should is inocrrect.", logger, result);
    }

    /**
     * the accuracy test for the method {@link AbstractDAO#getSessionContext()}.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testSessionContext() throws Exception {
        Method info = AbstractDAO.class.getDeclaredMethod("getSessionContext");
        dao.setSessionContext(new MockSessionContext());
        info.setAccessible(true);

        SessionContext result = (SessionContext) info.invoke(dao);
        assertNotNull("The instance should not be null.", result);
    }

    /**
     * the accuracy test for the method {@link AbstractDAO#getSessionContext()}.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testUnitName() throws Exception {
        Method info = AbstractDAO.class.getDeclaredMethod("getUnitName");
        dao.setUnitName("test");
        info.setAccessible(true);

        String result = (String) info.invoke(dao);
        assertEquals("The result should is inocrrect.", "test", result);
    }
}
