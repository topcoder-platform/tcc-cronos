/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.service;

import com.topcoder.catalog.entity.Category;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Unit test case for {@link SearchCriteria}.</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public class SearchCriteriaTest extends TestCase {
    /**
     * <p>Test <code>userId</code> value.</p>
     */
    private static final Long USER_ID = 1L;
    /**
     * <p>Test <code>clientId</code> value.</p>
     */
    private static final Long CLIENT_ID = 2L;
    /**
     * <p>Test <code>categories</code> value.</p>
     */
    private static final List<Category> CATEGORIES = Arrays.asList(new Category());
    /**
     * <p>Test <code>name</code> value.</p>
     */
    private static final String NAME = "NAME";
    /**
     * <p>Test <code>description</code> value.</p>
     */
    private static final String DESCRIPTION = "DESCRIPTION";

    /**
     * <p>Represents SearchCriteria instance for testing.</p>
     */
    private SearchCriteria searchCriteria;

    /**
     * <p>Creates a new instance of <code>SearchCriteria</code>.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        searchCriteria = new SearchCriteria(USER_ID, CLIENT_ID, CATEGORIES, NAME, DESCRIPTION);
    }

    /**
     * <p>Tears down the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void tearDown() throws Exception {
        searchCriteria = null;
        super.tearDown();
    }

    /**
     * <p>Tests <code>SearchCriteria()</code> constructor.</p>
     */
    public void testSearchCriteria() {
        assertNotNull("Unable to instantiate SearchCriteria", searchCriteria);
    }

    /**
     * <p>Tests that <code>USER_ID</code> were correctly assigned.</p>
     */
    public void testUserId() {
        assertEquals("Invalid field value", USER_ID, searchCriteria.getUserId());
    }

    /**
     * <p>Tests that <code>CLIENT_ID</code> were correctly assigned.</p>
     */
    public void testClientId() {
        assertEquals("Invalid field value", CLIENT_ID, searchCriteria.getClientId());
    }

    /**
     * <p>Tests that <code>CATEGORIES</code> were correctly assigned.</p>
     */
    public void testCategories() {
        assertEquals("Invalid field value", CATEGORIES, searchCriteria.getCategories());
        assertFalse("There should be shallow copy of the list", CATEGORIES == searchCriteria.getCategories());
    }

    /**
     * <p>Tests that <code>NAME</code> were correctly assigned.</p>
     */
    public void testName() {
        assertEquals("Invalid field value", NAME, searchCriteria.getName());
    }

    /**
     * <p>Tests that <code>DESCRIPTION</code> were correctly assigned.</p>
     */
    public void testDescription() {
        assertEquals("Invalid field value", DESCRIPTION, searchCriteria.getDescription());
    }

    /**
     * <p>Tests that <code>IllegalArgumentException</code> if all parameters passed to the constructor are null.</p>
     */
    public void testAllParametersNull() {
        try {
            new SearchCriteria(null, null, null, null, null);
            fail("IllegalArgumentException is expected as all parameters are null");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * <p>Tests that <code>IllegalArgumentException</code> if all parameters passed to the constructor are null,
     * and the <code>name</code> is empty.</p>
     */
    public void testNameIsEmpty() {
        try {
            new SearchCriteria(null, null, null, " ", null);
            fail("IllegalArgumentException is expected as all parameters are null, and the name is empty");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * <p>Tests that <code>IllegalArgumentException</code> if all parameters passed to the constructor are null,
     * and the <code>description</code> is empty.</p>
     */
    public void testDescriptionIsEmpty() {
        try {
            new SearchCriteria(null, null, null, " ", null);
            fail("IllegalArgumentException is expected as all parameters are null, and the description is empty");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * <p>Tests that <code>IllegalArgumentException</code> is thrown if the <code>categories</code> list is empty.</p>
     */
    public void testCategoriesIsEmpty() {
        try {
            new SearchCriteria(1L, null, Collections.<Category>emptyList(), null, null);
            fail("IllegalArgumentException is expected as categories cannot be empty");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * <p>Tests that <code>IllegalArgumentException</code> is thrown if the <code>categories</code>
     * list contains null.</p>
     */
    public void testCategoriesContainsNull() {
        try {
            new SearchCriteria(1L, null, Arrays.asList((Category) null), null, null);
            fail("IllegalArgumentException is expected as categories cannot contain null");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * <p>Tests that <code>IllegalArgumentException</code> is not thrown if at least one parameter is not null.</p>
     * <p>The test uses complete enumeration of different combinations of nulls/not nulls parameters.</p>
     */
    public void testCategoriesForNulls() {
        for (int i = 1; i <= 0x1F; i++) {
            final Long userId =
                (i & 0x10) != 0 ? USER_ID : null;
            final Long clientId =
                (i & 0x08) != 0 ? CLIENT_ID : null;
            final List<Category> categories =
                (i & 0x04) != 0 ? CATEGORIES : null;
            final String name =
                (i & 0x02) != 0 ? NAME : (i & 0x01) != 0 ? null : " ";
            final String description =
                (i & 0x01) != 0 ? DESCRIPTION : (i & 0x02) != 0 ? null : " ";
            try {
                final SearchCriteria criteria = new SearchCriteria(userId, clientId, categories, name, description);
                assertEquals("Invalid userId value", userId, criteria.getUserId());
                assertEquals("Invalid clientId value", clientId, criteria.getClientId());
                assertEquals("Invalid categories value", categories, criteria.getCategories());
                assertEquals("Invalid name value", name, criteria.getName());
                assertEquals("Invalid description value", description, criteria.getDescription());
            } catch (IllegalArgumentException e) {
                fail("Failed to create SearchCriteria with at least one non-null parameter. The parameters: "
                    + "(" + userId + ", " + clientId + ", " + categories + ", " + name + ", " + description + ")");
            }
        }
    }

}
