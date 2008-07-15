/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.InFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.search.builder.filter.NotFilter;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * Unit test cases for the class <code>DigitalRunTrackFilterFactory</code>.
 * </p>
 * @author waits
 * @version 1.0
 */
public class DigitalRunTrackFilterFactoryTests extends TestCase {
    /** Ids for testing. */
    private List<Long> ids = new ArrayList<Long>();

    /** Filter array to test. */
    private List<Filter> filters = new ArrayList<Filter>();

    /** Filter array to test. */
    private List<Filter> emptyFilters = new ArrayList<Filter>();

    /**
     * Create instance.
     */
    protected void setUp() {
        ids.add(new Long(1));
        filters.add(DigitalRunTrackFilterFactory.createTrackEndDateEqualsFilter(new Date()));
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createAndFilter(List)} method. It is an accuracy test case.
     * </p>
     */
    public void testCreateAndFilter() {
        Filter andFilter = DigitalRunTrackFilterFactory.createAndFilter(filters);
        //verify
        assertNotNull("Fail to create instance.", andFilter);
        assertTrue("Invalid filter created.", andFilter instanceof AndFilter);
        assertEquals("The filter is invalid.", 1, ((AndFilter) andFilter).getFilters().size());
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createAndFilter(List)} method. The list is empty, iae expected.
     * </p>
     */
    public void testCreateAndFilter_emptyParam() {
        try {
            DigitalRunTrackFilterFactory.createAndFilter(emptyFilters);
            fail("The list is empty.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createAndFilter(List)} method. The list has null element, iae
     * expected.
     * </p>
     */
    public void testCreateAndFilter_nullElement() {
        try {
            emptyFilters.add(null);
            DigitalRunTrackFilterFactory.createAndFilter(emptyFilters);
            fail("The list as null element.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createAndFilter(List)} method. The list is null, iae expected.
     * </p>
     */
    public void testCreateAndFilter_nullParam() {
        try {
            DigitalRunTrackFilterFactory.createAndFilter(null);
            fail("The list is null.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createNotFilter(Filter)} method. The param is null, iae expected.
     * </p>
     */
    public void testCreateNotFilter_nullParam() {
        try {
            DigitalRunTrackFilterFactory.createNotFilter(null);
            fail("The param is null.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createNotFilter(Filter)} method. it is an accuracy test case.
     * </p>
     */
    public void testCreateNotFilter() {
        Filter filter = DigitalRunTrackFilterFactory.createNotFilter(this.filters.get(0));
        //verify
        assertNotNull("Fail to create instance.", filter);
        assertTrue("Invalid filter created.", filter instanceof NotFilter);
        assertTrue("The filter is invalid.", ((NotFilter) filter).getFilter() instanceof EqualToFilter);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createOrFilter(List)} method. It is an accuracy test case.
     * </p>
     */
    public void testCreateOrFilter() {
        Filter orFilter = DigitalRunTrackFilterFactory.createOrFilter(filters);
        //verify
        assertNotNull("Fail to create instance.", orFilter);
        assertTrue("Invalid filter created.", orFilter instanceof com.topcoder.search.builder.filter.OrFilter);
        assertEquals("The filter is invalid.", 1,
            ((com.topcoder.search.builder.filter.OrFilter) orFilter).getFilters().size());
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createOrFilter(List)} method. The list is empty, iae expected.
     * </p>
     */
    public void testCreateOrFilter_emptyParam() {
        try {
            DigitalRunTrackFilterFactory.createOrFilter(emptyFilters);
            fail("The list is empty.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createOrFilter(List)} method. The list has null element, iae
     * expected.
     * </p>
     */
    public void testCreateOrFilter_nullElement() {
        try {
            emptyFilters.add(null);
            DigitalRunTrackFilterFactory.createOrFilter(emptyFilters);
            fail("The list as null element.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createOrFilter(List)} method. The list is null, iae expected.
     * </p>
     */
    public void testCreateOrFilter_nullParam() {
        try {
            DigitalRunTrackFilterFactory.createOrFilter(null);
            fail("The list is null.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackDescriptionEqualsFilter(String)} method. It is an
     * accuracy test case.
     * </p>
     */
    public void testCreateTrackDescriptionEqualsFilter() {
        Filter filter = DigitalRunTrackFilterFactory.createTrackDescriptionEqualsFilter("dec");
        //verify
        assertNotNull("Fail to create instance.", filter);
        assertTrue("Invalid filter created.", filter instanceof EqualToFilter);
        assertEquals("The filter is invalid.", "dec", ((EqualToFilter) filter).getValue());
        assertEquals("The filter is invalid.", DigitalRunTrackFilterFactory.TRACK_DESCRIPTION,
            ((EqualToFilter) filter).getName());
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackDescriptionEqualsFilter(String)} method.  the param is
     * null, iae expected.
     * </p>
     */
    public void testCreateTrackDescriptionEqualsFilter_nullparam() {
        try {
            DigitalRunTrackFilterFactory.createTrackDescriptionEqualsFilter(null);
            fail("The param is null.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackDescriptionEqualsFilter(String)} method.  the param is
     * empty, iae expected.
     * </p>
     */
    public void testCreateTrackDescriptionEqualsFilter_emptyparam() {
        try {
            DigitalRunTrackFilterFactory.createTrackDescriptionEqualsFilter(" ");
            fail("The param is empty.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackDescriptionLikeFilter(String)} method. It is an accuracy
     * test case.
     * </p>
     */
    public void testCreateTrackDescriptionLikeFilter() {
        Filter filter = DigitalRunTrackFilterFactory.createTrackDescriptionLikeFilter("SS:dec");
        //verify
        assertNotNull("Fail to create instance.", filter);
        assertTrue("Invalid filter created.", filter instanceof LikeFilter);
        assertEquals("The filter is invalid.", "SS:dec", ((LikeFilter) filter).getValue());
        assertEquals("The filter is invalid.", DigitalRunTrackFilterFactory.TRACK_DESCRIPTION,
            ((LikeFilter) filter).getName());
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackDescriptionLikeFilter(String)} method.  the param is
     * null, iae expected.
     * </p>
     */
    public void testCreateTrackDescriptionLikeFilter_nullparam() {
        try {
            DigitalRunTrackFilterFactory.createTrackDescriptionLikeFilter(null);
            fail("The param is null.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackDescriptionLikeFilter(String)} method.  the param is
     * empty, iae expected.
     * </p>
     */
    public void testCreateTrackDescriptionLikeFilter_emptyparam() {
        try {
            DigitalRunTrackFilterFactory.createTrackDescriptionLikeFilter(" ");
            fail("The param is invalid.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackDescriptionLikeFilter(String)} method.  the param is
     * empty, iae expected.
     * </p>
     */
    public void testCreateTrackDescriptionLikeFilter_invalidparam() {
        try {
            DigitalRunTrackFilterFactory.createTrackDescriptionLikeFilter("4x");
            fail("The param is empty.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackDescriptionLikeFilter(String,char)} method. It is an
     * accuracy test case.
     * </p>
     */
    public void testCreateTrackDescriptionLikeFilter2() {
        Filter filter = DigitalRunTrackFilterFactory.createTrackDescriptionLikeFilter("SS:dec", 'c');
        //verify
        assertNotNull("Fail to create instance.", filter);
        assertTrue("Invalid filter created.", filter instanceof LikeFilter);
        assertEquals("The filter is invalid.", "SS:dec", ((LikeFilter) filter).getValue());
        assertEquals("The filter is invalid.", 'c', ((LikeFilter) filter).getEscapeCharacter());
        assertEquals("The filter is invalid.", DigitalRunTrackFilterFactory.TRACK_DESCRIPTION,
            ((LikeFilter) filter).getName());
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackDescriptionLikeFilter(String,char)} method.  the param
     * is null, iae expected.
     * </p>
     */
    public void testCreateTrackDescriptionLikeFilter2_nullparam() {
        try {
            DigitalRunTrackFilterFactory.createTrackDescriptionLikeFilter(null, 'c');
            fail("The param is null.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackDescriptionLikeFilter(String,char)} method.  the param
     * is empty, iae expected.
     * </p>
     */
    public void testCreateTrackDescriptionLikeFilter2_emptyparam() {
        try {
            DigitalRunTrackFilterFactory.createTrackDescriptionLikeFilter(" ", 'c');
            fail("The param is invalid.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackDescriptionLikeFilter(String,char)} method.  the param
     * is empty, iae expected.
     * </p>
     */
    public void testCreateTrackDescriptionLikeFilter2_invalidparam() {
        try {
            DigitalRunTrackFilterFactory.createTrackDescriptionLikeFilter("4x", 'c');
            fail("The param is empty.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackEndDateEqualsFilter(Date)} method. It is an accuracy
     * test case.
     * </p>
     */
    public void testcreateTrackEndDateEqualsFilter() {
        Date date = new Date();
        Filter filter = DigitalRunTrackFilterFactory.createTrackEndDateEqualsFilter(date);
        //verify
        assertNotNull("Fail to create instance.", filter);
        assertTrue("Invalid filter created.", filter instanceof EqualToFilter);
        assertEquals("The filter is invalid.", date, ((EqualToFilter) filter).getValue());
        assertEquals("The filter is invalid.", DigitalRunTrackFilterFactory.TRACK_END_DATE,
            ((EqualToFilter) filter).getName());
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackEndDateEqualsFilter(Date)} method.  the param is null,
     * iae expected.
     * </p>
     */
    public void testcreateTrackEndDateEqualsFilter_nullparam() {
        try {
            DigitalRunTrackFilterFactory.createTrackEndDateEqualsFilter(null);
            fail("The param is null.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackEndDateGreaterOrEqualFilter(Date)} method. It is an
     * accuracy test case.
     * </p>
     */
    public void testcreateTrackEndDateGreaterOrEqualFilter() {
        Date date = new Date();
        Filter filter = DigitalRunTrackFilterFactory.createTrackEndDateGreaterOrEqualFilter(date);
        //verify
        assertNotNull("Fail to create instance.", filter);
        assertTrue("Invalid filter created.", filter instanceof GreaterThanOrEqualToFilter);
        assertEquals("The filter is invalid.", date, ((GreaterThanOrEqualToFilter) filter).getValue());
        assertEquals("The filter is invalid.", DigitalRunTrackFilterFactory.TRACK_END_DATE,
            ((GreaterThanOrEqualToFilter) filter).getName());
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackEndDateGreaterOrEqualFilter(Date)} method.  the param is
     * null, iae expected.
     * </p>
     */
    public void testcreateTrackEndDateGreaterOrEqualFilter_nullparam() {
        try {
            DigitalRunTrackFilterFactory.createTrackEndDateGreaterOrEqualFilter(null);
            fail("The param is null.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackEndDateLowerOrEqualFilter(Date)} method. It is an
     * accuracy test case.
     * </p>
     */
    public void testcreateTrackEndDateLowerOrEqualFilter() {
        Date date = new Date();
        Filter filter = DigitalRunTrackFilterFactory.createTrackEndDateLowerOrEqualFilter(date);
        //verify
        assertNotNull("Fail to create instance.", filter);
        assertTrue("Invalid filter created.", filter instanceof LessThanOrEqualToFilter);
        assertEquals("The filter is invalid.", date, ((LessThanOrEqualToFilter) filter).getValue());
        assertEquals("The filter is invalid.", DigitalRunTrackFilterFactory.TRACK_END_DATE,
            ((LessThanOrEqualToFilter) filter).getName());
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackEndDateLowerOrEqualFilter(Date)} method.  the param is
     * null, iae expected.
     * </p>
     */
    public void testcreateTrackEndDateLowerOrEqualFilter_nullparam() {
        try {
            DigitalRunTrackFilterFactory.createTrackEndDateLowerOrEqualFilter(null);
            fail("The param is null.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackIdEqualsFilter(long)} method. It is an accuracy test
     * case.
     * </p>
     */
    public void testcreateTrackIdEqualsFilter() {
        Filter filter = DigitalRunTrackFilterFactory.createTrackIdEqualsFilter(1);
        assertNotNull("Fail to create instance.", filter);
        assertTrue("Invalid filter created.", filter instanceof EqualToFilter);
        assertEquals("The filter is invalid.", 1L, ((EqualToFilter) filter).getValue());
        assertEquals("The filter is invalid.",
                DigitalRunTrackFilterFactory.TRACK_ID, ((EqualToFilter) filter).getName());
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackIdEqualsFilter(Date)} method.  the param is negative,
     * iae expected.
     * </p>
     */
    public void testCreateTrackIdEqualsFilter_invlaidparam() {
        try {
            DigitalRunTrackFilterFactory.createTrackIdEqualsFilter(-1);
            fail("The param is invalid.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackIdInFilter(List)} method. It is an accuracy test case.
     * </p>
     */
    public void testcreateTrackIdInFilter() {
        Filter orFilter = DigitalRunTrackFilterFactory.createTrackIdInFilter(ids);
        //verify
        assertNotNull("Fail to create instance.", orFilter);
        assertTrue("Invalid filter created.", orFilter instanceof InFilter);
        assertEquals("The filter is invalid.", 1, ((InFilter) orFilter).getList().size());
        assertEquals("The filter is invalid.", DigitalRunTrackFilterFactory.TRACK_ID, ((InFilter) orFilter).getName());
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackIdInFilter(List)} method. The list is empty, iae
     * expected.
     * </p>
     */
    public void testcreateTrackIdInFilter_emptyParam() {
        try {
            ids.clear();
            DigitalRunTrackFilterFactory.createTrackIdInFilter(ids);
            fail("The list is empty.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackIdInFilter(List)} method. The list has null element, iae
     * expected.
     * </p>
     */
    public void testcreateTrackIdInFilter_nullElement() {
        try {
            ids.add(null);
            DigitalRunTrackFilterFactory.createTrackIdInFilter(ids);
            fail("The list as null element.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackIdInFilter(List)} method. The list has negative element,
     * iae expected.
     * </p>
     */
    public void testcreateTrackIdInFilter_negativeElement() {
        try {
            ids.add(-1L);
            DigitalRunTrackFilterFactory.createTrackIdInFilter(ids);
            fail("The list as negative element.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackIdInFilter(List)} method. The list is null, iae
     * expected.
     * </p>
     */
    public void testcreateTrackIdInFilter_nullParam() {
        try {
            DigitalRunTrackFilterFactory.createTrackIdInFilter(null);
            fail("The list is null.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackProjectTypedInFilter(List)} method. It is an accuracy
     * test case.
     * </p>
     */
    public void testcreateTrackProjectTypedInFilter() {
        Filter orFilter = DigitalRunTrackFilterFactory.createTrackProjectTypedInFilter(ids);
        //verify
        assertNotNull("Fail to create instance.", orFilter);
        assertTrue("Invalid filter created.", orFilter instanceof InFilter);
        assertEquals("The filter is invalid.", 1, ((InFilter) orFilter).getList().size());
        assertEquals("The filter is invalid.", DigitalRunTrackFilterFactory.TRACK_PROJECT_TYPE_ID,
            ((InFilter) orFilter).getName());
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackProjectTypedInFilter(List)} method. The list is empty,
     * iae expected.
     * </p>
     */
    public void testcreateTrackProjectTypedInFilter_emptyParam() {
        try {
            ids.clear();
            DigitalRunTrackFilterFactory.createTrackProjectTypedInFilter(ids);
            fail("The list is empty.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackProjectTypedInFilter(List)} method. The list has null
     * element, iae expected.
     * </p>
     */
    public void testcreateTrackProjectTypedInFilter_nullElement() {
        try {
            ids.add(null);
            DigitalRunTrackFilterFactory.createTrackProjectTypedInFilter(ids);
            fail("The list as null element.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackProjectTypedInFilter(List)} method. The list has
     * negative element, iae expected.
     * </p>
     */
    public void testcreateTrackProjectTypedInFilter_negativeElement() {
        try {
            ids.add(-1L);
            DigitalRunTrackFilterFactory.createTrackProjectTypedInFilter(ids);
            fail("The list as negative element.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackProjectTypedInFilter(List)} method. The list is null,
     * iae expected.
     * </p>
     */
    public void testcreateTrackProjectTypedInFilter_nullParam() {
        try {
            DigitalRunTrackFilterFactory.createTrackProjectTypedInFilter(null);
            fail("The list is null.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackStartDateEqualsFilter(Date)} method. It is an accuracy
     * test case.
     * </p>
     */
    public void testcreateTrackStartDateEqualsFilter() {
        Date date = new Date();
        Filter filter = DigitalRunTrackFilterFactory.createTrackStartDateEqualsFilter(date);
        //verify
        assertNotNull("Fail to create instance.", filter);
        assertTrue("Invalid filter created.", filter instanceof EqualToFilter);
        assertEquals("The filter is invalid.", date, ((EqualToFilter) filter).getValue());
        assertEquals("The filter is invalid.", DigitalRunTrackFilterFactory.TRACK_START_DATE,
            ((EqualToFilter) filter).getName());
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackStartDateEqualsFilter(Date)} method.  the param is null,
     * iae expected.
     * </p>
     */
    public void testcreateTrackStartDateEqualsFilter_nullparam() {
        try {
            DigitalRunTrackFilterFactory.createTrackStartDateEqualsFilter(null);
            fail("The param is null.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackStartDateGreaterOrEqualFilter(Date)} method. It is an
     * accuracy test case.
     * </p>
     */
    public void testcreateTrackStartDateGreaterOrEqualFilter() {
        Date date = new Date();
        Filter filter = DigitalRunTrackFilterFactory.createTrackStartDateGreaterOrEqualFilter(date);
        //verify
        assertNotNull("Fail to create instance.", filter);
        assertTrue("Invalid filter created.", filter instanceof GreaterThanOrEqualToFilter);
        assertEquals("The filter is invalid.", date, ((GreaterThanOrEqualToFilter) filter).getValue());
        assertEquals("The filter is invalid.", DigitalRunTrackFilterFactory.TRACK_START_DATE,
            ((GreaterThanOrEqualToFilter) filter).getName());
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackStartDateGreaterOrEqualFilter(Date)} method.  the param
     * is null, iae expected.
     * </p>
     */
    public void testcreateTrackStartDateGreaterOrEqualFilter_nullparam() {
        try {
            DigitalRunTrackFilterFactory.createTrackStartDateGreaterOrEqualFilter(null);
            fail("The param is null.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackStartDateLowerOrEqualFilter(Date)} method. It is an
     * accuracy test case.
     * </p>
     */
    public void testcreateTrackStartDateLowerOrEqualFilter() {
        Date date = new Date();
        Filter filter = DigitalRunTrackFilterFactory.createTrackStartDateLowerOrEqualFilter(date);
        //verify
        assertNotNull("Fail to create instance.", filter);
        assertTrue("Invalid filter created.", filter instanceof LessThanOrEqualToFilter);
        assertEquals("The filter is invalid.", date, ((LessThanOrEqualToFilter) filter).getValue());
        assertEquals("The filter is invalid.", DigitalRunTrackFilterFactory.TRACK_START_DATE,
            ((LessThanOrEqualToFilter) filter).getName());
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackStartDateLowerOrEqualFilter(Date)} method.  the param is
     * null, iae expected.
     * </p>
     */
    public void testcreateTrackStartDateLowerOrEqualFilter_nullparam() {
        try {
            DigitalRunTrackFilterFactory.createTrackStartDateLowerOrEqualFilter(null);
            fail("The param is null.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackStatusIdEqualsFilter(long)} method. It is an accuracy
     * test case.
     * </p>
     */
    public void testcreateTrackStatusIdEqualsFilter() {
        Filter filter = DigitalRunTrackFilterFactory.createTrackStatusIdEqualsFilter(1);
        assertNotNull("Fail to create instance.", filter);
        assertTrue("Invalid filter created.", filter instanceof EqualToFilter);
        assertEquals("The filter is invalid.", 1L, ((EqualToFilter) filter).getValue());
        assertEquals("The filter is invalid.", DigitalRunTrackFilterFactory.TRACK_STATUS_ID,
            ((EqualToFilter) filter).getName());
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackStatusIdEqualsFilter(Date)} method.  the param is
     * negative, iae expected.
     * </p>
     */
    public void testCreateTrackStatusIdEqualsFilter_invlaidparam() {
        try {
            DigitalRunTrackFilterFactory.createTrackStatusIdEqualsFilter(-1);
            fail("The param is invalid.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackProjectTypeIdEqualsFilter(long)} method. It is an
     * accuracy test case.
     * </p>
     */
    public void testCreateTrackProjectTypeIdEqualsFilter() {
        Filter filter = DigitalRunTrackFilterFactory.createTrackProjectTypeIdEqualsFilter(1);
        assertNotNull("Fail to create instance.", filter);
        assertTrue("Invalid filter created.", filter instanceof EqualToFilter);
        assertEquals("The filter is invalid.", 1L, ((EqualToFilter) filter).getValue());
        assertEquals("The filter is invalid.", DigitalRunTrackFilterFactory.TRACK_PROJECT_TYPE_ID,
            ((EqualToFilter) filter).getName());
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackProjectTypeIdEqualsFilter(long)} method.  the param is
     * negative, iae expected.
     * </p>
     */
    public void testCreateTrackProjectTypeIdEqualsFilter_invlaidparam() {
        try {
            DigitalRunTrackFilterFactory.createTrackProjectTypeIdEqualsFilter(-1);
            fail("The param is invalid.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackStatusIdInFilter(List)} method. It is an accuracy test
     * case.
     * </p>
     */
    public void testcreateTrackStatusIdInFilter() {
        Filter orFilter = DigitalRunTrackFilterFactory.createTrackStatusIdInFilter(ids);
        //verify
        assertNotNull("Fail to create instance.", orFilter);
        assertTrue("Invalid filter created.", orFilter instanceof InFilter);
        assertEquals("The filter is invalid.", 1, ((InFilter) orFilter).getList().size());
        assertEquals("The filter is invalid.", DigitalRunTrackFilterFactory.TRACK_STATUS_ID,
            ((InFilter) orFilter).getName());
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackStatusIdInFilter(List)} method. The list is empty, iae
     * expected.
     * </p>
     */
    public void testcreateTrackStatusIdInFilter_emptyParam() {
        try {
            ids.clear();
            DigitalRunTrackFilterFactory.createTrackStatusIdInFilter(ids);
            fail("The list is empty.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackStatusIdInFilter(List)} method. The list has null
     * element, iae expected.
     * </p>
     */
    public void testcreateTrackStatusIdInFilter_nullElement() {
        try {
            ids.add(null);
            DigitalRunTrackFilterFactory.createTrackStatusIdInFilter(ids);
            fail("The list as null element.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackStatusIdInFilter(List)} method. The list has negative
     * element, iae expected.
     * </p>
     */
    public void testcreateTrackStatusIdInFilter_negativeElement() {
        try {
            ids.add(-1L);
            DigitalRunTrackFilterFactory.createTrackStatusIdInFilter(ids);
            fail("The list as negative element.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackStatusIdInFilter(List)} method. The list is null, iae
     * expected.
     * </p>
     */
    public void testcreateTrackStatusIdInFilter_nullParam() {
        try {
            DigitalRunTrackFilterFactory.createTrackStatusIdInFilter(null);
            fail("The list is null.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackTypeIdEqualsFilter(long)} method. It is an accuracy test
     * case.
     * </p>
     */
    public void testcreateTrackTypeIdEqualsFilter() {
        Filter filter = DigitalRunTrackFilterFactory.createTrackTypeIdEqualsFilter(1);
        assertNotNull("Fail to create instance.", filter);
        assertTrue("Invalid filter created.", filter instanceof EqualToFilter);
        assertEquals("The filter is invalid.", 1L, ((EqualToFilter) filter).getValue());
        assertEquals("The filter is invalid.", DigitalRunTrackFilterFactory.TRACK_TYPE_ID,
            ((EqualToFilter) filter).getName());
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackTypeIdEqualsFilter(Date)} method.  the param is
     * negative, iae expected.
     * </p>
     */
    public void testcreateTrackTypeIdEqualsFilter_invlaidparam() {
        try {
            DigitalRunTrackFilterFactory.createTrackTypeIdEqualsFilter(-1);
            fail("The param is invalid.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackTypeIdInFilter(List)} method. It is an accuracy test
     * case.
     * </p>
     */
    public void testcreateTrackTypeIdInFilter() {
        Filter orFilter = DigitalRunTrackFilterFactory.createTrackTypeIdInFilter(ids);
        //verify
        assertNotNull("Fail to create instance.", orFilter);
        assertTrue("Invalid filter created.", orFilter instanceof InFilter);
        assertEquals("The filter is invalid.", 1, ((InFilter) orFilter).getList().size());
        assertEquals("The filter is invalid.", DigitalRunTrackFilterFactory.TRACK_TYPE_ID,
            ((InFilter) orFilter).getName());
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackTypeIdInFilter(List)} method. The list is empty, iae
     * expected.
     * </p>
     */
    public void testcreateTrackTypeIdInFilter_emptyParam() {
        try {
            ids.clear();
            DigitalRunTrackFilterFactory.createTrackTypeIdInFilter(ids);
            fail("The list is empty.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackTypeIdInFilter(List)} method. The list has null element,
     * iae expected.
     * </p>
     */
    public void testcreateTrackTypeIdInFilter_nullElement() {
        try {
            ids.add(null);
            DigitalRunTrackFilterFactory.createTrackTypeIdInFilter(ids);
            fail("The list as null element.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackTypeIdInFilter(List)} method. The list has negative
     * element, iae expected.
     * </p>
     */
    public void testcreateTrackTypeIdInFilter_negativeElement() {
        try {
            ids.add(-1L);
            DigitalRunTrackFilterFactory.createTrackTypeIdInFilter(ids);
            fail("The list as negative element.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackFilterFactory#createTrackTypeIdInFilter(List)} method. The list is null, iae
     * expected.
     * </p>
     */
    public void testcreateTrackTypeIdInFilter_nullParam() {
        try {
            DigitalRunTrackFilterFactory.createTrackTypeIdInFilter(null);
            fail("The list is null.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }
}
