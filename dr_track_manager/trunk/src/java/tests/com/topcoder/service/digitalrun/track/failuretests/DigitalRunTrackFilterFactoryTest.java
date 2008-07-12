/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.topcoder.service.digitalrun.track.DigitalRunTrackFilterFactory;

import java.util.Arrays;

/**
 * Some tests for DigitalRunTrackFilterFactory class.
 *
 * @author Orange_Cloud
 * @version 1.0
 */
public class DigitalRunTrackFilterFactoryTest extends TestCase {
    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(DigitalRunTrackFilterFactoryTest.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        super.setUp();
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
     * Tests createTrackIdEqualsFilter method for negative parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateTrackIdEqualsFilterForNegative() throws Exception {
        try {
            DigitalRunTrackFilterFactory.createTrackIdEqualsFilter(-3);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createTrackIdInFilter method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateTrackIdInFilterForNull() throws Exception {
        try {
            DigitalRunTrackFilterFactory.createTrackIdInFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createTrackIdInFilter method for null element in list. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateTrackIdInFilterForNullElement() throws Exception {
        try {
            DigitalRunTrackFilterFactory.createTrackIdInFilter(Arrays.asList(1l, 2l, null));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createTrackIdInFilter method for negative element in list. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateTrackIdInFilterForNegativeElement() throws Exception {
        try {
            DigitalRunTrackFilterFactory.createTrackIdInFilter(Arrays.asList(-1l, 2l, 3l));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createTrackStatusIdEqualsFilter method for negative parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateTrackStatusIdEqualsFilterForNegative() throws Exception {
        try {
            DigitalRunTrackFilterFactory.createTrackStatusIdEqualsFilter(-3);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createTrackStatusIdInFilter method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateTrackStatusIdInFilterForNull() throws Exception {
        try {
            DigitalRunTrackFilterFactory.createTrackStatusIdInFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createTrackStatusIdInFilter method for null element in list. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateTrackStatusIdInFilterForNullElement() throws Exception {
        try {
            DigitalRunTrackFilterFactory.createTrackStatusIdInFilter(Arrays.asList(1l, 2l, null));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createTrackStatusIdInFilter method for negative element in list. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateTrackStatusIdInFilterForNegativeElement() throws Exception {
        try {
            DigitalRunTrackFilterFactory.createTrackStatusIdInFilter(Arrays.asList(-1l, 2l, 3l));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createTrackDescriptionEqualsFilter method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateTrackDescriptionEqualsFilterForNull() throws Exception {
        try {
            DigitalRunTrackFilterFactory.createTrackDescriptionEqualsFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createTrackDescriptionEqualsFilter method for empty string as parameter. IllegalArgumentException
     * expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateTrackDescriptionEqualsFilterForEmpty() throws Exception {
        try {
            DigitalRunTrackFilterFactory.createTrackDescriptionEqualsFilter(" \n  \t");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createTrackDescriptionLikeFilter method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateTrackDescriptionLikeFilterForNull() throws Exception {
        try {
            DigitalRunTrackFilterFactory.createTrackDescriptionLikeFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createTrackDescriptionLikeFilter method for empty string as parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateTrackDescriptionLikeFilterForBadEscapeCharacter() throws Exception {
        try {
            DigitalRunTrackFilterFactory.createTrackDescriptionLikeFilter(" \n  \t", '*');
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createTrackTypeIdEqualsFilter method for negative parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateTrackTypeIdEqualsFilterForNegative() throws Exception {
        try {
            DigitalRunTrackFilterFactory.createTrackTypeIdEqualsFilter(-3);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createTrackTypeIdInFilter method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateTrackTypeIdInFilterForNull() throws Exception {
        try {
            DigitalRunTrackFilterFactory.createTrackTypeIdInFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createTrackTypeIdInFilter method for null element in list. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateTrackTypeIdInFilterForNullElement() throws Exception {
        try {
            DigitalRunTrackFilterFactory.createTrackTypeIdInFilter(Arrays.asList(1l, 2l, null));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createTrackTypeIdInFilter method for negative element in list. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateTrackTypeIdInFilterForNegativeElement() throws Exception {
        try {
            DigitalRunTrackFilterFactory.createTrackTypeIdInFilter(Arrays.asList(-1l, 2l, 3l));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createTrackProjectTypeIdEqualsFilter method for negative parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateTrackProjectTypeIdEqualsFilterForNegative() throws Exception {
        try {
            DigitalRunTrackFilterFactory.createTrackProjectTypeIdEqualsFilter(-3);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createTrackProjectTypedInFilter method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateTrackProjectTypedInFilterForNull() throws Exception {
        try {
            DigitalRunTrackFilterFactory.createTrackProjectTypedInFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createTrackProjectTypedInFilter method for null element in list. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateTrackProjectTypedInFilterForNullElement() throws Exception {
        try {
            DigitalRunTrackFilterFactory.createTrackProjectTypedInFilter(Arrays.asList(1l, 2l, null));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createTrackProjectTypedInFilter method for negative element in list. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateTrackProjectTypedInFilterForNegativeElement() throws Exception {
        try {
            DigitalRunTrackFilterFactory.createTrackProjectTypedInFilter(Arrays.asList(-1l, 2l, 3l));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createTrackStartDateEqualsFilter method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateTrackStartDateEqualsFilterForNull() throws Exception {
        try {
            DigitalRunTrackFilterFactory.createTrackStartDateEqualsFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createTrackStartDateGreaterOrEqualFilter method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateTrackStartDateGreaterOrEqualFilterForNull() throws Exception {
        try {
            DigitalRunTrackFilterFactory.createTrackStartDateGreaterOrEqualFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createTrackStartDateLowerOrEqualFilter method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateTrackStartDateLowerOrEqualFilterForNull() throws Exception {
        try {
            DigitalRunTrackFilterFactory.createTrackStartDateLowerOrEqualFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createTrackEndDateEqualsFilter method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateTrackEndDateEqualsFilterForNull() throws Exception {
        try {
            DigitalRunTrackFilterFactory.createTrackEndDateEqualsFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createTrackEndDateGreaterOrEqualFilter method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateTrackEndDateGreaterOrEqualFilterForNull() throws Exception {
        try {
            DigitalRunTrackFilterFactory.createTrackEndDateGreaterOrEqualFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createTrackEndDateLowerOrEqualFilter method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateTrackEndDateLowerOrEqualFilterForNull() throws Exception {
        try {
            DigitalRunTrackFilterFactory.createTrackEndDateLowerOrEqualFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createAndFilter method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateAndFilterForNull() throws Exception {
        try {
            DigitalRunTrackFilterFactory.createAndFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createOrFilter method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateOrFilterForNull() throws Exception {
        try {
            DigitalRunTrackFilterFactory.createOrFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createNotFilter method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateNotFilterForNull() throws Exception {
        try {
            DigitalRunTrackFilterFactory.createNotFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }
}