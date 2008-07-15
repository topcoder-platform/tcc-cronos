/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.failuretests;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.topcoder.service.digitalrun.points.DigitalRunPointsFilterFactory;

/**
 * Failure test for class DigitalRunPointsFilterFactory.
 *
 * @author extra
 * @version 1.0
 */
public class DigitalRunPointsFilterFactoryFailureTests extends TestCase {

    /**
     * Represents pointsIds for testing.
     */
    private List<Long> pointsIds;

    /**
     * Represents trackIds for testing.
     */
    private List<Long> trackIds;

    /**
     * Represents pointsStatusIds for testing.
     */
    private List<Long> pointsStatusIds;

    /**
     * Represents pointsTypeIds for testing.
     */
    private List<Long> pointsTypeIds;

    /**
     * Represents referenceIds for testing.
     */
    private List<Long> referenceIds;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        pointsIds = new ArrayList<Long>();
        pointsIds.add(10L);

        trackIds = new ArrayList<Long>();
        trackIds.add(10L);

        pointsStatusIds = new ArrayList<Long>();
        pointsStatusIds.add(10L);

        pointsTypeIds = new ArrayList<Long>();
        pointsTypeIds.add(10L);

        referenceIds = new ArrayList<Long>();
        referenceIds.add(10L);

        super.setUp();
    }

    /**
     * Failure test for method createPointsIdEqualsFilter(long pointsId). If pointsId is negative,
     * IllegalArgumentException expected.
     */
    public void testCreatePointsIdEqualsFilter_negative_pointsId() {
        try {
            DigitalRunPointsFilterFactory.createPointsIdEqualsFilter(-1L);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPointsIdInFilter(List<Long> pointsIds). If pointsIds is null,
     * IllegalArgumentException expected.
     */
    public void testCreatePointsIdInFilter_null_pointsIds() {
        pointsIds = null;
        try {
            DigitalRunPointsFilterFactory.createPointsIdInFilter(pointsIds);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPointsIdInFilter(List<Long> pointsIds). If pointsIds is null,
     * IllegalArgumentException expected.
     */
    public void testCreatePointsIdInFilter_empty_pointsIds() {
        pointsIds.clear();
        try {
            DigitalRunPointsFilterFactory.createPointsIdInFilter(pointsIds);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPointsIdInFilter(List<Long> pointsIds). If pointsIds contains null,
     * IllegalArgumentException expected.
     */
    public void testCreatePointsIdInFilter_null_element_pointsIds() {
        pointsIds.add(null);
        try {
            DigitalRunPointsFilterFactory.createPointsIdInFilter(pointsIds);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPointsIdInFilter(List<Long> pointsIds). If pointsIds contains negative element,
     * IllegalArgumentException expected.
     */
    public void testCreatePointsIdInFilter_negative_element_pointsIds() {
        pointsIds.add(-1L);
        try {
            DigitalRunPointsFilterFactory.createPointsIdInFilter(pointsIds);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createTrackIdEqualsFilter(long trackId). If trackId is negative, IllegalArgumentException
     * expected.
     */
    public void testCreateTrackIdEqualsFilter_negative_trackId() {
        try {
            DigitalRunPointsFilterFactory.createTrackIdEqualsFilter(-1L);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createTrackIdInFilter(List<Long> trackIds). If trackIds is null,
     * IllegalArgumentException expected.
     */
    public void testCreateTrackIdInFilter_null_trackIds() {
        trackIds = null;
        try {
            DigitalRunPointsFilterFactory.createTrackIdInFilter(trackIds);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createTrackIdInFilter(List<Long> trackIds). If trackIds is null,
     * IllegalArgumentException expected.
     */
    public void testCreateTrackIdInFilter_empty_trackIds() {
        trackIds.clear();
        try {
            DigitalRunPointsFilterFactory.createTrackIdInFilter(trackIds);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createTrackIdInFilter(List<Long> trackIds). If trackIds contains null,
     * IllegalArgumentException expected.
     */
    public void testCreateTrackIdInFilter_null_element_trackIds() {
        trackIds.add(null);
        try {
            DigitalRunPointsFilterFactory.createTrackIdInFilter(trackIds);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createTrackIdInFilter(List<Long> trackIds). If trackIds contains negative element,
     * IllegalArgumentException expected.
     */
    public void testCreateTrackIdInFilter_negative_element_trackIds() {
        trackIds.add(-1L);
        try {
            DigitalRunPointsFilterFactory.createTrackIdInFilter(trackIds);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPointsDescriptionEqualsFilter(String pointsDescription). If pointsDescription is
     * null, IllegalArgumentException expected.
     */
    public void testCreatePointsDescriptionEqualsFilter_Null_pointsDescription() {
        try {
            DigitalRunPointsFilterFactory.createPointsDescriptionEqualsFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPointsDescriptionEqualsFilter(String pointsDescription). If pointsDescription is
     * empty, IllegalArgumentException expected.
     */
    public void testCreatePointsDescriptionEqualsFilter_Empty_pointsDescription() {
        try {
            DigitalRunPointsFilterFactory.createPointsDescriptionEqualsFilter(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPointsDescriptionLikeFilter(String value). If value is null,
     * IllegalArgumentException expected.
     */
    public void testCreatePointsDescriptionLikeFilter_Null_value() {
        try {
            DigitalRunPointsFilterFactory.createPointsDescriptionLikeFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPointsDescriptionLikeFilter(String value). If value is empty,
     * IllegalArgumentException expected.
     */
    public void testCreatePointsDescriptionLikeFilter_Empty_value() {
        try {
            DigitalRunPointsFilterFactory.createPointsDescriptionLikeFilter(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPointsStatusIdEqualsFilter(long pointsStatusId). If pointsStatusId is negative,
     * IllegalArgumentException expected.
     */
    public void testCreatePointsStatusIdEqualsFilter_negative_pointsStatusId() {
        try {
            DigitalRunPointsFilterFactory.createPointsStatusIdEqualsFilter(-1L);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPointsStatusIdInFilter(List<Long> pointsStatusIds). If pointsStatusIds is null,
     * IllegalArgumentException expected.
     */
    public void testCreatePointsStatusIdInFilter_null_pointsStatusIds() {
        pointsStatusIds = null;
        try {
            DigitalRunPointsFilterFactory.createPointsStatusIdInFilter(pointsStatusIds);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPointsStatusIdInFilter(List<Long> pointsStatusIds). If pointsStatusIds is null,
     * IllegalArgumentException expected.
     */
    public void testCreatePointsStatusIdInFilter_empty_pointsStatusIds() {
        pointsStatusIds.clear();
        try {
            DigitalRunPointsFilterFactory.createPointsStatusIdInFilter(pointsStatusIds);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPointsStatusIdInFilter(List<Long> pointsStatusIds). If pointsStatusIds contains
     * null, IllegalArgumentException expected.
     */
    public void testCreatePointsStatusIdInFilter_null_element_pointsStatusIds() {
        pointsStatusIds.add(null);
        try {
            DigitalRunPointsFilterFactory.createPointsStatusIdInFilter(pointsStatusIds);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPointsStatusIdInFilter(List<Long> pointsStatusIds). If pointsStatusIds contains
     * negative element, IllegalArgumentException expected.
     */
    public void testCreatePointsStatusIdInFilter_negative_element_pointsStatusIds() {
        pointsStatusIds.add(-1L);
        try {
            DigitalRunPointsFilterFactory.createPointsStatusIdInFilter(pointsStatusIds);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPointsTypeIdEqualsFilter(long pointsTypeId). If pointsTypeId is negative,
     * IllegalArgumentException expected.
     */
    public void testCreatePointsTypeIdEqualsFilter_negative_pointsTypeId() {
        try {
            DigitalRunPointsFilterFactory.createPointsTypeIdEqualsFilter(-1L);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPointsTypedInFilter(List<Long> pointsTypeIds). If pointsTypeIds is null,
     * IllegalArgumentException expected.
     */
    public void testCreatePointsTypedInFilter_null_pointsTypeIds() {
        pointsTypeIds = null;
        try {
            DigitalRunPointsFilterFactory.createPointsTypedInFilter(pointsTypeIds);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPointsTypedInFilter(List<Long> pointsTypeIds). If pointsTypeIds is null,
     * IllegalArgumentException expected.
     */
    public void testCreatePointsTypedInFilter_empty_pointsTypeIds() {
        pointsTypeIds.clear();
        try {
            DigitalRunPointsFilterFactory.createPointsTypedInFilter(pointsTypeIds);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPointsTypedInFilter(List<Long> pointsTypeIds). If pointsTypeIds contains null,
     * IllegalArgumentException expected.
     */
    public void testCreatePointsTypedInFilter_null_element_pointsTypeIds() {
        pointsTypeIds.add(null);
        try {
            DigitalRunPointsFilterFactory.createPointsTypedInFilter(pointsTypeIds);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPointsTypedInFilter(List<Long> pointsTypeIds). If pointsTypeIds contains negative
     * element, IllegalArgumentException expected.
     */
    public void testCreatePointsTypedInFilter_negative_element_pointsTypeIds() {
        pointsTypeIds.add(-1L);
        try {
            DigitalRunPointsFilterFactory.createPointsTypedInFilter(pointsTypeIds);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createReferenceIdEqualsFilter(long pointsReferenceTypeId, long referenceId). If
     * pointsReferenceTypeId is negative, IllegalArgumentException expected.
     */
    public void testCreateReferenceIdEqualsFilter_negative_pointsReferenceTypeId() {
        try {
            DigitalRunPointsFilterFactory.createReferenceIdEqualsFilter(-1L, 10L);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createReferenceIdEqualsFilter(long pointsReferenceTypeId, long referenceId). If
     * pointsReferenceTypeId is negative, IllegalArgumentException expected.
     */
    public void testCreateReferenceIdEqualsFilter_negative_referenceId() {
        try {
            DigitalRunPointsFilterFactory.createReferenceIdEqualsFilter(10L, -1L);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createReferenceIdEqualsInFilter(long pointsReferenceTypeId, List<Long> referenceIds). If
     * pointsReferenceTypeId is negative, IllegalArgumentException expected.
     */
    public void testCreateReferenceIdEqualsInFilter_negative_referenceId() {
        try {
            DigitalRunPointsFilterFactory.createReferenceIdEqualsInFilter(-1L, referenceIds);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createUserIdEqualsFilter(long userId). If userId is negative, IllegalArgumentException
     * expected.
     */
    public void testCreateUserIdEqualsFilter_negative_userId() {
        try {
            DigitalRunPointsFilterFactory.createUserIdEqualsFilter(-1L);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createUserIdInFilter(List<Long> userIds). If userId is negative,
     * IllegalArgumentException expected.
     */
    public void testCreateUserIdInFilter_null_userIds() {
        try {
            DigitalRunPointsFilterFactory.createUserIdInFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createAmountEqualsFilter(double amount). If amount is negative, IllegalArgumentException
     * expected.
     */
    public void testCreateAmountEqualsFilter_negative_amount() {
        try {
            DigitalRunPointsFilterFactory.createAmountEqualsFilter(-1D);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createAmountGreaterOrEqualFilter(double amount). If amount is negative,
     * IllegalArgumentException expected.
     */
    public void testCreateAmountGreaterOrEqualFilter_negative_amount() {
        try {
            DigitalRunPointsFilterFactory.createAmountGreaterOrEqualFilter(-1D);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createAmountLowerOrEqualFilter(double amount). If amount is negative,
     * IllegalArgumentException expected.
     */
    public void testcCreateAmountLowerOrEqualFilter_negative_amount() {
        try {
            DigitalRunPointsFilterFactory.createAmountLowerOrEqualFilter(-1D);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPointsApplicationDateEqualsFilter(Date pointsApplicationDate). If
     * pointsApplicationDate is null, IllegalArgumentException expected.
     */
    public void testCreatePointsApplicationDateEqualsFilter_null_pointsApplicationDate() {
        try {
            DigitalRunPointsFilterFactory.createPointsApplicationDateEqualsFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPointsApplicationDateGreaterOrEqualFilter(Date pointsApplicationDate). If
     * pointsApplicationDate is null, IllegalArgumentException expected.
     */
    public void testCreatePointsApplicationDateGreaterOrEqualFilter_null_pointsApplicationDate() {
        try {
            DigitalRunPointsFilterFactory.createPointsApplicationDateGreaterOrEqualFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPointsApplicationDateLowerOrEqualFilter(Date pointsApplicationDate). If
     * pointsApplicationDate is null, IllegalArgumentException expected.
     */
    public void testCreatePointsApplicationDateLowerOrEqualFilter_null_pointsApplicationDate() {
        try {
            DigitalRunPointsFilterFactory.createPointsApplicationDateLowerOrEqualFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPointsAwardDateEqualsFilter(Date pointsAwardDate). If pointsAwardDate is null,
     * IllegalArgumentException expected.
     */
    public void testCreatePointsAwardDateEqualsFilter_null_pointsAwardDate() {
        try {
            DigitalRunPointsFilterFactory.createPointsAwardDateEqualsFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPointsAwardDateGreaterOrEqualFilter(Date pointsAwardDate). If pointsAwardDate is
     * null, IllegalArgumentException expected.
     */
    public void testCreatePointsAwardDateGreaterOrEqualFilter_null_pointsAwardDate() {
        try {
            DigitalRunPointsFilterFactory.createPointsAwardDateGreaterOrEqualFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createPointsAwardDateLowerOrEqualFilter(Date pointsAwardDate). If pointsAwardDate is
     * null, IllegalArgumentException expected.
     */
    public void testCreatePointsAwardDateLowerOrEqualFilter_null_pointsAwardDate() {
        try {
            DigitalRunPointsFilterFactory.createPointsAwardDateLowerOrEqualFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createAndFilter(List<Filter> filters). If filters is null, IllegalArgumentException
     * expected.
     */
    public void testCreateAndFilter_null_filters() {
        try {
            DigitalRunPointsFilterFactory.createAndFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createOrFilter(List<Filter> filters). If filters is null, IllegalArgumentException
     * expected.
     */
    public void testCreateOrFilter_null_filters() {
        try {
            DigitalRunPointsFilterFactory.createOrFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createNotFilter(Filter filter). If filter is null, IllegalArgumentException expected.
     */
    public void testCreateNotFilter_null_filter() {
        try {
            DigitalRunPointsFilterFactory.createNotFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
