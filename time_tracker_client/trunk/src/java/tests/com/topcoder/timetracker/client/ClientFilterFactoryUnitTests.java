/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.OrFilter;


/**
 * Unit test for the <code>ClientFilterFactory</code> class.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ClientFilterFactoryUnitTests extends TestCase {
    /**
     * <p>
     * Failure test for method <code>createCompanyIdFilter</code>. CreateCompanyIdFilter with zero is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateCompanyIdFilterWithZero() throws Exception {
        try {
            ClientFilterFactory.createCompanyIdFilter(0);
            fail("CreateCompanyIdFilter with zero is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>createCompanyIdFilter</code>. CreateCompanyIdFilter with negative is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateCompanyIdFilterWithNegative()
        throws Exception {
        try {
            ClientFilterFactory.createCompanyIdFilter(-1);
            fail("CreateCompanyIdFilter with zero is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>createCompanyIdFilter</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateCompanyIdFilterAccuracy() throws Exception {
        Filter filter = ClientFilterFactory.createCompanyIdFilter(1);

        assertTrue("The filter should be of type EqualToFilter", filter instanceof EqualToFilter);
    }

    /**
     * <p>
     * Failure test for method <code>createNameKeywordFilter</code>. Create with null is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateNameKeywordFilterWithNull() throws Exception {
        try {
            ClientFilterFactory.createNameKeywordFilter(null);
            fail("Create with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>createNameKeywordFilter</code>. Create with empty is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateNameKeywordFilterWithEmpty()
        throws Exception {
        try {
            ClientFilterFactory.createNameKeywordFilter("    ");
            fail("Create with empty is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>createNameKeywordFilter</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateNameKeywordFilterAccuracy() throws Exception {
        Filter filter = ClientFilterFactory.createNameKeywordFilter("name");

        assertTrue("The filter should be of type LikeFilter", filter instanceof LikeFilter);
    }

    /**
     * <p>
     * Failure test for method <code>createGreekNameKeywordFilter</code>. Create with null is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateGreekNameKeywordFilterWithNull() throws Exception {
        try {
            ClientFilterFactory.createGreekNameKeywordFilter(null);
            fail("Create with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>createGreekNameKeywordFilter</code>. Create with empty is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateGreekNameKeywordFilterWithEmpty()
        throws Exception {
        try {
            ClientFilterFactory.createGreekNameKeywordFilter("    ");
            fail("Create with empty is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>createGreekNameKeywordFilter</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateGreekNameKeywordFilterAccuracy() throws Exception {
        Filter filter = ClientFilterFactory.createGreekNameKeywordFilter("name");

        assertTrue("The filter should be of type LikeFilter", filter instanceof LikeFilter);
    }
    /**
     * <p>
     * Failure test for method <code>createCreatedInFilter</code>. Create with null is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateCreatedDateInFilterWith() throws Exception {
        try {
            ClientFilterFactory.createCreatedInFilter(null, null);
            fail("Create with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>createCreatedinFilter</code>. The returned filter should be of type
     * GreaterThanOrEqualToFilter when one bound is provided.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateCreatedinFilterAccuracy1() throws Exception {
        Filter filter = ClientFilterFactory.createCreatedInFilter(new Date(), null);

        assertTrue("The filter created should be of type CreateThanOrEqualTo",
            filter instanceof GreaterThanOrEqualToFilter);
    }

    /**
     * <p>
     * Accuracy test for method <code>createCreatedinFilter</code>. The returned filter should be of type AndFilter.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateCreatedinFilterAccuracy2() throws Exception {
        Filter filter = ClientFilterFactory.createCreatedInFilter(new Date(20), new Date(120));

        assertTrue("The filter created should be of type AndFilter", filter instanceof BetweenFilter);
    }

    /**
     * <p>
     * Failure test for method <code>createModifiedInFilter</code>. Create with null is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateModifiedDateInFilterWith() throws Exception {
        try {
            ClientFilterFactory.createModifiedInFilter(null, null);
            fail("Create with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>createModifiedinFilter</code>. The returned filter should be of type
     * GreaterThanOrEqualToFilter when one bound is provided.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateModifiedinFilterAccuracy1() throws Exception {
        Filter filter = ClientFilterFactory.createModifiedInFilter(new Date(), null);

        assertTrue("The filter created should be of type CreateThanOrEqualTo",
            filter instanceof GreaterThanOrEqualToFilter);
    }

    /**
     * <p>
     * Accuracy test for method <code>createModifiedinFilter</code>. The returned filter should be of type AndFilter.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateModifiedinFilterAccuracy2() throws Exception {
        Filter filter = ClientFilterFactory.createModifiedInFilter(new Date(20), new Date(120));

        assertTrue("The filter created should be of type AndFilter", filter instanceof BetweenFilter);
    }

    /**
     * <p>
     * Failure test for method <code>createCreatedByFilter</code>. Create with null is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateCreatedByFilterWithNull() throws Exception {
        try {
            ClientFilterFactory.createCreatedByFilter(null);
            fail("Create with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>createCreatedByFilter</code>. Create with empty is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateCreatedByFilterWithEmpty() throws Exception {
        try {
            ClientFilterFactory.createCreatedByFilter("     ");
            fail("Create with empty is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>createCreatedByFilter</code>. The created filter should be of type EqualToFilter.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateCreatedByFilterAccuracy() throws Exception {
        Filter filter = ClientFilterFactory.createCreatedByFilter("user");

        assertTrue("Filter should be of type EqualToFilter", filter instanceof EqualToFilter);
    }

    /**
     * <p>
     * Failure test for method <code>createModifiedByFilter</code>. Create with null is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateModifiedByFilterWithNull() throws Exception {
        try {
            ClientFilterFactory.createModifiedByFilter(null);
            fail("Create with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>createModifiedByFilter</code>. Create with empty is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateModifiedByFilterWithEmpty() throws Exception {
        try {
            ClientFilterFactory.createModifiedByFilter("     ");
            fail("Create with empty is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>createModifiedByFilter</code>. The created filter should be of type
     * EqualToFilter.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateModifiedByFilterAccuracy() throws Exception {
        Filter filter = ClientFilterFactory.createModifiedByFilter("user");

        assertTrue("Filter should be of type EqualToFilter", filter instanceof EqualToFilter);
    }

    /**
     * <p>
     * Failure test for method <code>andFilter</code>. Create with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testandFilterWithNull1() throws Exception {
        try {
            Filter filter1 = new EqualToFilter("name", new Long(1));

            ClientFilterFactory.andFilter(null, filter1);
            fail("Create with null is illegal, IllegalArgumetnException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>andFilter</code>. Create with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testandFilterWithNull2() throws Exception {
        try {
            Filter filter1 = new EqualToFilter("name", new Long(1));

            ClientFilterFactory.andFilter(filter1, null);
            fail("Create with null is illegal, IllegalArgumetnException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>andFilter</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testandFilterAccuracy() throws Exception {
        Filter filter1 = new EqualToFilter("name", new Long(1));
        Filter filter2 = new EqualToFilter("name", new Long(1));

        Filter filter = ClientFilterFactory.andFilter(filter1, filter2);

        assertTrue("The created filter should be of type AndFilter.", filter instanceof AndFilter);
    }

    /**
     * <p>
     * Failure test for method <code>orFilter</code>. Create with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testorFilterWithNull1() throws Exception {
        try {
            Filter filter1 = new EqualToFilter("name", new Long(1));

            ClientFilterFactory.orFilter(null, filter1);
            fail("Create with null is illegal, IllegalArgumetnException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>orFilter</code>. Create with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testorFilterWithNull2() throws Exception {
        try {
            Filter filter1 = new EqualToFilter("name", new Long(1));

            ClientFilterFactory.orFilter(filter1, null);
            fail("Create with null is illegal, IllegalArgumetnException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>orFilter</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testorFilterAccuracy() throws Exception {
        Filter filter1 = new EqualToFilter("name", new Long(1));
        Filter filter2 = new EqualToFilter("name", new Long(1));

        Filter filter = ClientFilterFactory.orFilter(filter1, filter2);

        assertTrue("The created filter should be of type OrFilter.", filter instanceof OrFilter);
    }

    /**
     * <p>
     * Failure test for method <code>notFilter</code>. Create with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testnotFilterWith() throws Exception {
        try {
            ClientFilterFactory.notFilter(null);
            fail("Create with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>notFilter</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testnotFilterAccuracy() throws Exception {
        Filter filter1 = new EqualToFilter("name", new Long(1));

        Filter filter = ClientFilterFactory.notFilter(filter1);

        assertTrue("The created filter should be of type notFilter.", filter instanceof NotFilter);
    }

    /**
     * <p>
     * Accuracy test for method <code>createActiveFilter</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateActiveFilterAccuracy() throws Exception {
        Filter filter = ClientFilterFactory.createActiveFilter(true);

        assertTrue("The created filter should be of type EqualToFilter.", filter instanceof EqualToFilter);
    }
}
