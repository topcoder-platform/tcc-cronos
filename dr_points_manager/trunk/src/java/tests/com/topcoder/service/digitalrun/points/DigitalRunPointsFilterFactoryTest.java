/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * Unit test cases for class ConfigurationProvider. All the method are tested.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DigitalRunPointsFilterFactoryTest extends TestCase {
    /**
     * Get this test suite.
     * @return this test suite
     */
    public static Test suite() {
        return new TestSuite(DigitalRunPointsFilterFactoryTest.class);
    }

    /**
     * <p>
     * Accuracy test for the <code>createPointsIdEqualsFilter(long pointsId)</code> method,
     * expects no error occurs.
     * </p>
     */
    public void testCreatePointsIdEqualsFilter_Accuracy() {
        assertNotNull("The result should not be null.", DigitalRunPointsFilterFactory
                .createPointsIdEqualsFilter(1));
    }

    /**
     * <p>
     * Accuracy test for the <code>createPointsIdInFilter(List pointsIds)</code> method, expects
     * no error occurs.
     * </p>
     */
    public void testCreatePointsIdInFilter_Accuracy() {
        List<Long> test = new ArrayList<Long>();
        test.add(new Long(1));
        test.add(new Long(2));
        assertNotNull("The result should not be null.", DigitalRunPointsFilterFactory
                .createPointsIdInFilter(test));
    }

    /**
     * <p>
     * Accuracy test for the <code>createTrackIdEqualsFilter(long trackId)</code> method, expects
     * no error occurs.
     * </p>
     */
    public void testCreateTrackIdEqualsFilter_Accuracy() {
        assertNotNull("The result should not be null.", DigitalRunPointsFilterFactory
                .createTrackIdEqualsFilter(1));
    }

    /**
     * <p>
     * Accuracy test for the <code>createTrackIdInFilter(List trackIds)</code> method, expects no
     * error occurs.
     * </p>
     */
    public void testCreateTrackIdInFilter_Accuracy() {
        List<Long> test = new ArrayList<Long>();
        test.add(new Long(1));
        test.add(new Long(2));
        assertNotNull("The result should not be null.", DigitalRunPointsFilterFactory
                .createTrackIdInFilter(test));
    }

    /**
     * <p>
     * Accuracy test for the
     * <code>createPointsDescriptionEqualsFilter(String pointsDescription)</code> method, expects
     * no error occurs.
     * </p>
     */
    public void testCreatePointsDescriptionEqualsFilter_Accuracy() {
        assertNotNull("The result should not be null.", DigitalRunPointsFilterFactory
                .createPointsDescriptionEqualsFilter("pointsDescription"));
    }

    /**
     * <p>
     * Accuracy test for the <code>createPointsDescriptionLikeFilter(String value)</code> method,
     * expects no error occurs.
     * </p>
     */
    public void testCreatePointsDescriptionLikeFilterString_Accuracy() {
        assertNotNull("The result should not be null.", DigitalRunPointsFilterFactory
                .createPointsDescriptionLikeFilter("SS:value"));
    }

    /**
     * <p>
     * Accuracy test for the
     * <code>createPointsDescriptionLikeFilter(String value, char escapeCharacter)</code> method,
     * expects no error occurs.
     * </p>
     */
    public void testCreatePointsDescriptionLikeFilterStringChar_Accuracy() {
        assertNotNull("The result should not be null.", DigitalRunPointsFilterFactory
                .createPointsDescriptionLikeFilter("SW:value", 'a'));
    }

    /**
     * <p>
     * Accuracy test for the <code>createIsPotentialEqualFilter(boolean isPotential)</code>
     * method, expects no error occurs.
     * </p>
     */
    public void testCreateIsPotentialEqualFilter_Accuracy() {
        assertNotNull("The result should not be null.", DigitalRunPointsFilterFactory
                .createIsPotentialEqualFilter(true));
    }

    /**
     * <p>
     * Accuracy test for the <code>createPointsStatusIdEqualsFilter(long pointsStatusId)</code>
     * method, expects no error occurs.
     * </p>
     */
    public void testCreatePointsStatusIdEqualsFilter_Accuracy() {
        assertNotNull("The result should not be null.", DigitalRunPointsFilterFactory
                .createPointsStatusIdEqualsFilter(1));
    }

    /**
     * <p>
     * Accuracy test for the <code>createPointsStatusIdInFilter(List pointsStatusIds)</code>
     * method, expects no error occurs.
     * </p>
     */
    public void testCreatePointsStatusIdInFilter_Accuracy() {
        List<Long> test = new ArrayList<Long>();
        test.add(new Long(1));
        test.add(new Long(2));
        assertNotNull("The result should not be null.", DigitalRunPointsFilterFactory
                .createPointsStatusIdInFilter(test));
    }

    /**
     * <p>
     * Accuracy test for the <code>createPointsTypeIdEqualsFilter(long pointsTypeId)</code>
     * method, expects no error occurs.
     * </p>
     */
    public void testCreatePointsTypeIdEqualsFilter_Accuracy() {
        assertNotNull("The result should not be null.", DigitalRunPointsFilterFactory
                .createPointsTypeIdEqualsFilter(1));
    }

    /**
     * <p>
     * Accuracy test for the <code>createPointsTypedInFilter(List pointsTypeIds)</code> method,
     * expects no error occurs.
     * </p>
     */
    public void testCreatePointsTypedInFilter_Accuracy() {
        List<Long> test = new ArrayList<Long>();
        test.add(new Long(1));
        test.add(new Long(2));
        assertNotNull("The result should not be null.", DigitalRunPointsFilterFactory
                .createPointsTypedInFilter(test));
    }

    /**
     * <p>
     * Accuracy test for the
     * <code>createReferenceIdEqualsFilter(long pointsReferenceTypeId, long referenceId)</code>
     * method, expects no error occurs.
     * </p>
     */
    public void testCreateReferenceIdEqualsFilter_Accuracy() {
        assertNotNull("The result should not be null.", DigitalRunPointsFilterFactory
                .createReferenceIdEqualsFilter(1, 2));
    }

    /**
     * <p>
     * Accuracy test for the
     * <code>createReferenceIdEqualsInFilter(long pointsReferenceTypeId, List referenceIds)</code>
     * method, expects no error occurs.
     * </p>
     */
    public void testCreateReferenceIdEqualsInFilter_Accuracy() {
        List<Long> test = new ArrayList<Long>();
        test.add(new Long(1));
        test.add(new Long(2));
        assertNotNull("The result should not be null.", DigitalRunPointsFilterFactory
                .createReferenceIdEqualsInFilter(1, test));
    }

    /**
     * <p>
     * Accuracy test for the <code>createUserIdEqualsFilter(long userId)</code> method, expects no
     * error occurs.
     * </p>
     */
    public void testCreateUserIdEqualsFilter_Accuracy() {
        assertNotNull("The result should not be null.", DigitalRunPointsFilterFactory
                .createUserIdEqualsFilter(1));
    }

    /**
     * <p>
     * Accuracy test for the <code>createUserIdInFilter(List userIds)</code> method, expects no
     * error occurs.
     * </p>
     */
    public void testCreateUserIdInFilter_Accuracy() {
        List<Long> test = new ArrayList<Long>();
        test.add(new Long(1));
        test.add(new Long(2));
        assertNotNull("The result should not be null.", DigitalRunPointsFilterFactory
                .createUserIdInFilter(test));
    }

    /**
     * <p>
     * Accuracy test for the <code>createAmountEqualsFilter(double amount)</code> method, expects
     * no error occurs.
     * </p>
     */
    public void testCreateAmountEqualsFilter_Accuracy() {
        assertNotNull("The result should not be null.", DigitalRunPointsFilterFactory
                .createAmountEqualsFilter(1.0));
    }

    /**
     * <p>
     * Accuracy test for the <code>createAmountGreaterOrEqualFilter(double amount)</code> method,
     * expects no error occurs.
     * </p>
     */
    public void testCreateAmountGreaterOrEqualFilter_Accuracy() {
        assertNotNull("The result should not be null.", DigitalRunPointsFilterFactory
                .createAmountGreaterOrEqualFilter(1.3));
    }

    /**
     * <p>
     * Accuracy test for the <code>createAmountLowerOrEqualFilter(double amount)</code> method,
     * expects no error occurs.
     * </p>
     */
    public void testCreateAmountLowerOrEqualFilter_Accuracy() {
        assertNotNull("The result should not be null.", DigitalRunPointsFilterFactory
                .createAmountLowerOrEqualFilter(1.5));
    }

    /**
     * <p>
     * Accuracy test for the
     * <code>createPointsApplicationDateEqualsFilter(Date pointsApplicationDate)</code> method,
     * expects no error occurs.
     * </p>
     */
    public void testCreatePointsApplicationDateEqualsFilter_Accuracy() {
        assertNotNull("The result should not be null.", DigitalRunPointsFilterFactory
                .createPointsApplicationDateEqualsFilter(new Date()));
    }

    /**
     * <p>
     * Accuracy test for the
     * <code>createPointsApplicationDateGreaterOrEqualFilter(Date pointsApplicationDate) </code>
     * method, expects no error occurs.
     * </p>
     */
    public void testCreatePointsApplicationDateGreaterOrEqualFilter_Accuracy() {
        assertNotNull("The result should not be null.", DigitalRunPointsFilterFactory
                .createPointsApplicationDateGreaterOrEqualFilter(new Date()));
    }

    /**
     * <p>
     * Accuracy test for the
     * <code>createPointsApplicationDateLowerOrEqualFilter(Date pointsApplicationDate)</code>
     * method, expects no error occurs.
     * </p>
     */
    public void testCreatePointsApplicationDateLowerOrEqualFilter_Accuracy() {
        assertNotNull("The result should not be null.", DigitalRunPointsFilterFactory
                .createPointsApplicationDateLowerOrEqualFilter(new Date()));
    }

    /**
     * <p>
     * Accuracy test for the <code>createPointsAwardDateEqualsFilter(Date pointsAwardDate)</code>
     * method, expects no error occurs.
     * </p>
     */
    public void testCreatePointsAwardDateEqualsFilter_Accuracy() {
        assertNotNull("The result should not be null.", DigitalRunPointsFilterFactory
                .createPointsAwardDateEqualsFilter(new Date()));
    }

    /**
     * <p>
     * Accuracy test for the
     * <code>createPointsAwardDateGreaterOrEqualFilter(Date pointsAwardDate)</code> method,
     * expects no error occurs.
     * </p>
     */
    public void testCreatePointsAwardDateGreaterOrEqualFilter_Accuracy() {
        assertNotNull("The result should not be null.", DigitalRunPointsFilterFactory
                .createPointsAwardDateGreaterOrEqualFilter(new Date()));
    }

    /**
     * <p>
     * Accuracy test for the
     * <code>createPointsAwardDateLowerOrEqualFilter(Date pointsAwardDate)</code> method, expects
     * no error occurs.
     * </p>
     */
    public void testCreatePointsAwardDateLowerOrEqualFilter_Accuracy() {
        assertNotNull("The result should not be null.", DigitalRunPointsFilterFactory
                .createPointsAwardDateLowerOrEqualFilter(new Date()));
    }

    /**
     * <p>
     * Accuracy test for the <code>createAndFilter(List filters)</code> method, expects no error
     * occurs.
     * </p>
     */
    public void testCreateAndFilter_Accuracy() {
        List<Filter> filters = new ArrayList<Filter>();
        EqualToFilter filter = new EqualToFilter("dr_points_desc", "pointsDescription");
        filters.add(filter);
        assertNotNull("The result should not be null.", DigitalRunPointsFilterFactory
                .createAndFilter(filters));
    }

    /**
     * <p>
     * Accuracy test for the <code>createOrFilter(List filters)</code> method, expects no error
     * occurs.
     * </p>
     */
    public void testCreateOrFilter_Accuracy() {
        List<Filter> filters = new ArrayList<Filter>();
        EqualToFilter filter = new EqualToFilter("dr_points_desc", "pointsDescription");
        filters.add(filter);
        assertNotNull("The result should not be null.", DigitalRunPointsFilterFactory.createOrFilter(filters));
    }

    /**
     * <p>
     * Accuracy test for the <code>createNotFilter(Filter filter)</code> method, expects no error
     * occurs.
     * </p>
     */
    public void testCreateNotFilter_Accuracy() {
        EqualToFilter filter = new EqualToFilter("dr_points_desc", "pointsDescription");
        assertNotNull("The result should not be null.", DigitalRunPointsFilterFactory.createNotFilter(filter));
    }

}
