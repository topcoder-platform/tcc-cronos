/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.failuretests.dao.implementations;

import junit.framework.Test;
import junit.framework.TestSuite;
import com.topcoder.service.digitalrun.track.dao.implementations.JPADigitalRunPointsCalculatorDAO;
import com.topcoder.service.digitalrun.track.EntityNotFoundException;
import com.topcoder.service.digitalrun.entity.PointsCalculator;

import org.easymock.EasyMock;

/**
 * Some tests for JPADigitalRunPointsCalculatorDAO class.
 *
 * @author Orange_Cloud
 * @version 1.0
 */
public class JPADigitalRunPointsCalculatorDAOTest extends BaseTest {
    /**
     * Instance to test.
     */
    private JPADigitalRunPointsCalculatorDAO target;

    /**
     * Entity to use in tests.
     */
    private PointsCalculator entity;

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(JPADigitalRunPointsCalculatorDAOTest.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        super.setUp();
        entity = new PointsCalculator();
        entity.setDescription("abc");
        entity.setClassName("class");

        target = new JPADigitalRunPointsCalculatorDAO();
        target.setUnitName("persistence_unit");
        target.setSessionContext(context);
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Tests createPointsCalculator method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreatePointsCalculatorForNull() throws Exception {
        try {
            target.createPointsCalculator(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createPointsCalculator method for positive id. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreatePointsCalculatorForPositiveId() throws Exception {
        entity.setId(3);
        try {
            target.createPointsCalculator(entity);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests updatePointsCalculator method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUpdatePointsCalculatorForNull() throws Exception {
        try {
            target.updatePointsCalculator(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests updatePointsCalculator method when such entity does not exist. EntityNotFoundException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUpdatePointsCalculatorForNoEntity() throws Exception {
        entity.setId(1);
        EasyMock.expect(em.find(entity.getClass(), 1l)).andReturn(null);
        EasyMock.replay(context, em);
        try {
            target.updatePointsCalculator(entity);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException ex) {
            // success
        }
    }

    /**
     * Tests removePointsCalculator method for negative parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testRemovePointsCalculatorForNegative() throws Exception {
        try {
            target.removePointsCalculator(-1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests removePointsCalculator method when such entity does not exists. EntityNotFoundException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testRemovePointsCalculatorNoEntity() throws Exception {
        EasyMock.expect(em.find(PointsCalculator.class, 1l)).andReturn(null);
        EasyMock.replay(context, em);
        try {
            target.removePointsCalculator(1);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException ex) {
            // success
        }
    }

    /**
     * Tests getPointsCalculator method for negative parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetPointsCalculatorForNegative() throws Exception {
        try {
            target.getPointsCalculator(-10);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests getPointsCalculator method when such entity does not exist. EntityNotFoundException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetPointsCalculatorForNoEntity() throws Exception {
        EasyMock.expect(em.find(PointsCalculator.class, 10l)).andReturn(null);
        EasyMock.replay(context, em);
        try {
            target.getPointsCalculator(10);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException ex) {
            // success
        }
    }
}