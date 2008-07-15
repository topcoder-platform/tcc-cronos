/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.failuretests;

import java.util.HashMap;

import junit.framework.TestCase;

import com.topcoder.search.builder.SearchBundle;
import com.topcoder.service.digitalrun.entity.DigitalRunPoints;
import com.topcoder.service.digitalrun.points.dao.implementations.JPADigitalRunPointsDAO;
import com.topcoder.util.log.LogManager;

/**
 * Failure test for class JPADigitalRunPointsDAO.
 *
 * @author extra
 * @version 1.0
 */
public class JPADigitalRunPointsDAOFailureTests extends TestCase {

    /**
     * Represent the dao for test.
     */
    private JPADigitalRunPointsDAO dao;

    /**
     * Represents the digital run points.
     */
    private DigitalRunPoints points;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        dao = new JPADigitalRunPointsDAO();
        dao.setSessionContext(new MockSessionContext());
        dao.setUnitName("entityManager");
        dao.setLogger(LogManager.getLog());

        points = new DigitalRunPoints();
        points.setId(10L);
        super.setUp();
    }

    /**
     * Failure tests for method createDigitalRunPoints(DigitalRunPoints points). If points is null,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateDigitalRunPoints_Null_points() throws Exception {
        try {
            dao.createDigitalRunPoints(null);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure tests for method createDigitalRunPoints(DigitalRunPoints points). If id of points is positive,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateDigitalRunPoints_positive_points_id() throws Exception {
        points.setId(1L);
        try {
            dao.createDigitalRunPoints(points);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure tests for method updateDigitalRunPoints(DigitalRunPoints points). If points is null,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateDigitalRunPoints_Null_points() throws Exception {
        try {
            dao.updateDigitalRunPoints(null);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure tests for method removeDigitalRunPoints(long pointsId). If pointsId is negative, IllegalArgumentException
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveDigitalRunPoints_Negative_pointsId() throws Exception {
        try {
            dao.removeDigitalRunPoints(-1L);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure tests for method getDigitalRunPoints(long pointsId). If pointsId is negative, IllegalArgumentException
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetDigitalRunPointss_Negative_pointsId() throws Exception {
        try {
            dao.getDigitalRunPoints(-1L);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure tests for method searchDigitalRunPoints(Filter filter). If filter is null, IllegalArgumentException
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchDigitalRunPoints_Null_filter() throws Exception {
        dao.setSearchBundle(new SearchBundle("name", new HashMap(), "context"));
        try {
            dao.searchDigitalRunPoints(null);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure tests for method setSearchBundle(SearchBundle searchBundle). If searchBundle is null,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetSearchBundle_Null_searchBundle() throws Exception {
        try {
            dao.setSearchBundle(null);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
