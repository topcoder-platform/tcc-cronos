/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest;

import org.easymock.EasyMock;

import com.topcoder.service.digitalrun.contest.persistence.HibernateContestTrackDAO;
import com.topcoder.service.digitalrun.contest.persistence.HibernateTrackContestResultCalculatorDAO;
import com.topcoder.service.digitalrun.contest.persistence.HibernateTrackContestTypeDAO;
import com.topcoder.service.digitalrun.entity.TrackContest;
import com.topcoder.service.digitalrun.entity.TrackContestResultCalculator;
import com.topcoder.service.digitalrun.entity.TrackContestType;


/**
 * <p>
 * Unit tests for <code>DigitalRunContestManagerBean</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DigitalRunContestManagerBeanUnitTests extends BaseTestCase {

    /**
     * <p>
     * The <code>DigitalRunContestManagerBean</code> used for tests.
     * </p>
     */
    private DigitalRunContestManagerBean bean;

    /**
     * <p>
     * Setup the test case.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        EasyMock.reset(CONTEXT);
        EasyMock.expect(CONTEXT.lookup(Helper.UNIT_NAME)).andStubReturn("persistenceUnit");
        EasyMock.expect(CONTEXT.lookup(Helper.CONFIG_FILE_NAME)).andStubReturn("config.properties");
        EasyMock.expect(CONTEXT.lookup(Helper.CONFIG_NAMESPACE)).andStubReturn("BeanWithMockDAO");
        EasyMock.replay(CONTEXT);

        bean = new DigitalRunContestManagerBean();
        setField(DigitalRunContestManagerBean.class, bean, "sessionContext", CONTEXT);

        bean.initialize();
    }

    /**
     * <p>
     * Tear down the test case.
     * </p>
     */
    @Override
    protected void tearDown() {
        bean = null;
    }

    /**
     * <p>
     * Instance of <code>DigitalRunContestManagerBean</code> should be created.
     * </p>
     */
    public void testConstructor() {
        assertNotNull(new DigitalRunContestManagerBean());
    }

    /**
     * <p>
     * Test method {@link DigitalRunContestManagerBean#initialize()}.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_Acc() throws Exception {
        EasyMock.reset(CONTEXT);
        EasyMock.expect(CONTEXT.lookup(Helper.UNIT_NAME)).andStubReturn("persistenceUnit");
        EasyMock.expect(CONTEXT.lookup(Helper.CONFIG_FILE_NAME)).andStubReturn("config.properties");
        EasyMock.expect(CONTEXT.lookup(Helper.CONFIG_NAMESPACE)).andStubReturn("DigitalRunContestManagerBean");
        EasyMock.replay(CONTEXT);

        bean = new DigitalRunContestManagerBean();
        setField(DigitalRunContestManagerBean.class, bean, "sessionContext", CONTEXT);

        bean.initialize();

        assertTrue(getField(DigitalRunContestManagerBean.class, bean, "trackContestDAO")
            instanceof HibernateContestTrackDAO);

        assertTrue(getField(DigitalRunContestManagerBean.class, bean, "trackContestTypeDAO")
            instanceof HibernateTrackContestTypeDAO);

        assertTrue(getField(DigitalRunContestManagerBean.class, bean, "trackContestResultCalculatorDAO")
            instanceof HibernateTrackContestResultCalculatorDAO);
    }

    /**
     * <p>
     * Test method {@link DigitalRunContestManagerBean#initialize()}.
     * </p>
     *
     * <p>
     * Configuration file does not exist.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_NoSuchFile() throws Exception {
        EasyMock.reset(CONTEXT);
        EasyMock.expect(CONTEXT.lookup(Helper.UNIT_NAME)).andStubReturn("persistenceUnit");
        EasyMock.expect(CONTEXT.lookup(Helper.CONFIG_FILE_NAME)).andStubReturn("NoSuchFile.properties");
        EasyMock.expect(CONTEXT.lookup(Helper.CONFIG_NAMESPACE)).andStubReturn("DigitalRunContestManagerBean");
        EasyMock.replay(CONTEXT);

        bean = new DigitalRunContestManagerBean();
        setField(DigitalRunContestManagerBean.class, bean, "sessionContext", CONTEXT);

        try {
            bean.initialize();
            fail("ContestManagerConfigurationException expected");
        } catch (ContestManagerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link DigitalRunContestManagerBean#initialize()}.
     * </p>
     *
     * <p>
     * Configuration namespace does not exist.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_NoSuchNamespace() throws Exception {
        EasyMock.reset(CONTEXT);
        EasyMock.expect(CONTEXT.lookup(Helper.UNIT_NAME)).andStubReturn("persistenceUnit");
        EasyMock.expect(CONTEXT.lookup(Helper.CONFIG_FILE_NAME)).andStubReturn("config.properties");
        EasyMock.expect(CONTEXT.lookup(Helper.CONFIG_NAMESPACE)).andStubReturn("NoSuchNamespace");
        EasyMock.replay(CONTEXT);

        bean = new DigitalRunContestManagerBean();
        setField(DigitalRunContestManagerBean.class, bean, "sessionContext", CONTEXT);

        try {
            bean.initialize();
            fail("ContestManagerConfigurationException expected");
        } catch (ContestManagerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link DigitalRunContestManagerBean#initialize()}.
     * </p>
     *
     * <p>
     * Object created by Object Factory is not desired type.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_ClassCastException() throws Exception {
        EasyMock.reset(CONTEXT);
        EasyMock.expect(CONTEXT.lookup(Helper.UNIT_NAME)).andStubReturn("persistenceUnit");
        EasyMock.expect(CONTEXT.lookup(Helper.CONFIG_FILE_NAME)).andStubReturn("config.properties");
        EasyMock.expect(CONTEXT.lookup(Helper.CONFIG_NAMESPACE)).andStubReturn("ErrorBean1");
        EasyMock.replay(CONTEXT);

        bean = new DigitalRunContestManagerBean();
        setField(DigitalRunContestManagerBean.class, bean, "sessionContext", CONTEXT);

        try {
            bean.initialize();
            fail("ContestManagerConfigurationException expected");
        } catch (ContestManagerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link DigitalRunContestManagerBean#initialize()}.
     * </p>
     *
     * <p>
     * Error occurs while injecting values into DAO.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_ExceptionWhileInjection() throws Exception {
        EasyMock.reset(CONTEXT);
        EasyMock.expect(CONTEXT.lookup(Helper.UNIT_NAME)).andStubReturn("persistenceUnit");
        EasyMock.expect(CONTEXT.lookup(Helper.CONFIG_FILE_NAME)).andStubReturn("config.properties");
        EasyMock.expect(CONTEXT.lookup(Helper.CONFIG_NAMESPACE)).andStubReturn("ErrorBean2");
        EasyMock.replay(CONTEXT);

        bean = new DigitalRunContestManagerBean();
        setField(DigitalRunContestManagerBean.class, bean, "sessionContext", CONTEXT);

        try {
            bean.initialize();
            fail("ContestManagerConfigurationException expected");
        } catch (ContestManagerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Persistence error occurs.
     * </p>
     *
     * @throws Exception to JUnit.
     *
     * @see MockTrackContestDAO
     */
    public void testSearchTrackContests() throws Exception {
        try {
            bean.searchTrackContests(ContestFilterFactory.createContestDescriptionEqualsFilter("description"));
            fail("PersistenceException expected");
        } catch (DigitalRunContestManagerException e) {
            assertTrue(e.getCause() instanceof PersistenceException);
        }
    }

    /**
     * <p>
     * Persistence error occurs.
     * </p>
     *
     * @throws Exception to JUnit.
     *
     * @see MockTrackContestDAO
     */
    public void testCreateTrackContest() throws Exception {
        try {
            bean.createTrackContest(new TrackContest());
            fail("PersistenceException expected");
        } catch (DigitalRunContestManagerException e) {
            assertTrue(e.getCause() instanceof PersistenceException);
        }
    }

    /**
     * <p>
     * Persistence error occurs.
     * </p>
     *
     * @throws Exception to JUnit.
     *
     * @see MockTrackContestDAO
     */
    public void testUpdateTrackContest() throws Exception {
        try {
            bean.updateTrackContest(new TrackContest());
            fail("PersistenceException expected");
        } catch (DigitalRunContestManagerException e) {
            assertTrue(e.getCause() instanceof PersistenceException);
        }
    }

    /**
     * <p>
     * Persistence error occurs.
     * </p>
     *
     * @throws Exception to JUnit.
     *
     * @see MockTrackContestDAO
     */
    public void testGetTrackContest() throws Exception {
        try {
            bean.getTrackContest(1L);
            fail("PersistenceException expected");
        } catch (DigitalRunContestManagerException e) {
            assertTrue(e.getCause() instanceof PersistenceException);
        }
    }

    /**
     * <p>
     * Persistence error occurs.
     * </p>
     *
     * @throws Exception to JUnit.
     *
     * @see MockTrackContestDAO
     */
    public void testRemoveTrackContest() throws Exception {
        try {
            bean.removeTrackContest(1L);
            fail("PersistenceException expected");
        } catch (DigitalRunContestManagerException e) {
            assertTrue(e.getCause() instanceof PersistenceException);
        }
    }

    /**
     * <p>
     * Persistence error occurs.
     * </p>
     *
     * @throws Exception to JUnit.
     *
     * @see MockResultCalculatorDAO
     */
    public void testGetAllTrackContestResultCalculators() throws Exception {
        try {
            bean.getAllTrackContestResultCalculators();
            fail("PersistenceException expected");
        } catch (DigitalRunContestManagerException e) {
            assertTrue(e.getCause() instanceof PersistenceException);
        }
    }

    /**
     * <p>
     * Persistence error occurs.
     * </p>
     *
     * @throws Exception to JUnit.
     *
     * @see MockResultCalculatorDAO
     */
    public void testCreateTrackContestResultCalculator() throws Exception {
        try {
            bean.createTrackContestResultCalculator(new TrackContestResultCalculator());
            fail("PersistenceException expected");
        } catch (DigitalRunContestManagerException e) {
            assertTrue(e.getCause() instanceof PersistenceException);
        }
    }

    /**
     * <p>
     * Persistence error occurs.
     * </p>
     *
     * @throws Exception to JUnit.
     *
     * @see MockResultCalculatorDAO
     */
    public void testUpdateTrackContestResultCalculator() throws Exception {
        try {
            bean.updateTrackContestResultCalculator(new TrackContestResultCalculator());
            fail("PersistenceException expected");
        } catch (DigitalRunContestManagerException e) {
            assertTrue(e.getCause() instanceof PersistenceException);
        }
    }

    /**
     * <p>
     * Persistence error occurs.
     * </p>
     *
     * @throws Exception to JUnit.
     *
     * @see MockResultCalculatorDAO
     */
    public void testGetTrackContestResultCalculator() throws Exception {
        try {
            bean.getTrackContestResultCalculator(1L);
            fail("PersistenceException expected");
        } catch (DigitalRunContestManagerException e) {
            assertTrue(e.getCause() instanceof PersistenceException);
        }
    }

    /**
     * <p>
     * Persistence error occurs.
     * </p>
     *
     * @throws Exception to JUnit.
     *
     * @see MockResultCalculatorDAO
     */
    public void testRemoveTrackContestResultCalculator() throws Exception {
        try {
            bean.removeTrackContestResultCalculator(1L);
            fail("PersistenceException expected");
        } catch (DigitalRunContestManagerException e) {
            assertTrue(e.getCause() instanceof PersistenceException);
        }
    }

    /**
     * <p>
     * Persistence error occurs.
     * </p>
     *
     * @throws Exception to JUnit.
     *
     * @see MockTrackContestTypeDAO
     */
    public void testGetAllTrackContestTypes() throws Exception {
        try {
            bean.getAllTrackContestTypes();
            fail("PersistenceException expected");
        } catch (DigitalRunContestManagerException e) {
            assertTrue(e.getCause() instanceof PersistenceException);
        }
    }

    /**
     * <p>
     * Persistence error occurs.
     * </p>
     *
     * @throws Exception to JUnit.
     *
     * @see MockTrackContestTypeDAO
     */
    public void testCreateTrackContestType() throws Exception {
        try {
            bean.createTrackContestType(new TrackContestType());
            fail("PersistenceException expected");
        } catch (DigitalRunContestManagerException e) {
            assertTrue(e.getCause() instanceof PersistenceException);
        }
    }

    /**
     * <p>
     * Persistence error occurs.
     * </p>
     *
     * @throws Exception to JUnit.
     *
     * @see MockTrackContestTypeDAO
     */
    public void testUpdateTrackContestType() throws Exception {
        try {
            bean.updateTrackContestType(new TrackContestType());
            fail("PersistenceException expected");
        } catch (DigitalRunContestManagerException e) {
            assertTrue(e.getCause() instanceof PersistenceException);
        }
    }

    /**
     * <p>
     * Persistence error occurs.
     * </p>
     *
     * @throws Exception to JUnit.
     *
     * @see MockTrackContestTypeDAO
     */
    public void testGetTrackContestType() throws Exception {
        try {
            bean.getTrackContestType(1L);
            fail("PersistenceException expected");
        } catch (DigitalRunContestManagerException e) {
            assertTrue(e.getCause() instanceof PersistenceException);
        }
    }

    /**
     * <p>
     * Persistence error occurs.
     * </p>
     *
     * @throws Exception to JUnit.
     *
     * @see MockTrackContestTypeDAO
     */
    public void testRemoveTrackContestType() throws Exception {
        try {
            bean.removeTrackContestType(1L);
            fail("PersistenceException expected");
        } catch (DigitalRunContestManagerException e) {
            assertTrue(e.getCause() instanceof PersistenceException);
        }
    }
}
