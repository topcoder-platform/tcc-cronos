/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test case of {@link ContestData}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestDataTest extends TestCase {
    /**
     * <p>
     * Create an instance to test against.
     * </p>
     */
    private ContestData instance;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    protected void setUp() throws Exception {
        instance = new ContestData();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    protected void tearDown() throws Exception {
        instance = null;
    }

    /**
     * <p>
     * Test method for {@link ContestData#ContestData()}. It verifies the new
     * instance is created.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testContestData() throws Exception {
        assertNotNull("Unable to instantiate ContestData", instance);
    }

    /**
     * <p>
     * Test method for {@link ContestData#getComponentName()}.It verifies the
     * assigned value is correct.
     * </p>
     */
    public void testGetComponentName() {
        instance.setComponentName("tc");
        assertEquals("fail to set/get the field.", "tc", instance.getComponentName());
    }

    /**
     * <p>
     * Test method for {@link ContestData#setComponentName(String)}.It verifies
     * the assigned value is correct.
     * </p>
     * <p>
     * ComponentName can not be null nor empty.
     * </p>
     */
    public void testSetComponentName() {
        try {
            instance.setComponentName(" ");
            fail("IllegalArgumentException should throw.");
        } catch (IllegalArgumentException e) {
            // OK
        }
        try {
            instance.setComponentName(null);
            fail("IllegalArgumentException should throw.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * <p>
     * Test method for {@link ContestData#getContestEndDate()}.It verifies the
     * assigned value is correct.
     * </p>
     */
    public void testGetContestEndDate() {
        Date test = new Date();
        instance.setContestEndDate(test);
        assertEquals("fail to set/get the field.", test, instance.getContestEndDate());
    }

    /**
     * <p>
     * Test method for {@link ContestData#setContestEndDate(Date)}.It verifies
     * the assigned value is correct.
     * </p>
     */
    public void testSetContestEndDate() {
        try {
            instance.setContestEndDate(null);
            fail("IllegalArgumentException should throw.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * <p>
     * Test method for {@link ContestData#getDesignWinner()}.It verifies the
     * assigned value is correct.
     * </p>
     */
    public void testGetDesignWinner() {
        instance.setDesignWinner(null);
        instance.setDesignWinner("hello");
        assertEquals("fail to set/get the field.", "hello", instance.getDesignWinner());
    }

    /**
     * <p>
     * Test method for {@link ContestData#setDesignWinner(String)}.It verifies
     * the assigned value is correct.
     * </p>
     * <p>
     * argument can not be empty.
     * </p>
     */
    public void testSetDesignWinner() {
        try {
            instance.setDesignWinner(" ");
            fail("IllegalArgumentException should throw.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * <p>
     * Test method for {@link ContestData#getDesignSecondPlace()}.It verifies
     * the assigned value is correct.
     * </p>
     */
    public void testGetDesignSecondPlace() {
        instance.setDesignSecondPlace(null);
        instance.setDesignSecondPlace("topcoder");
        assertEquals("fail to set/get the field.", "topcoder", instance.getDesignSecondPlace());
    }

    /**
     * <p>
     * Test method for {@link ContestData#setDesignSecondPlace(String)}.It
     * verifies the assigned value is correct.
     * </p>
     */
    public void testSetDesignSecondPlace() {
        try {
            instance.setDesignSecondPlace(" ");
            fail("IllegalArgumentException should throw.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * <p>
     * Test method for {@link ContestData#getDesignReviewers()}.It verifies the
     * assigned value is correct.
     * </p>
     */
    public void testGetDesignReviewers() {
        instance.setDevelopmentReviewers(null);
        List<String> test = new ArrayList<String>();
        instance.setDesignReviewers(test);
        assertEquals("fail to set/get the field.", test, instance.getDesignReviewers());
    }

    /**
     * <p>
     * Test method for {@link ContestData#setDesignReviewers(List)}.It
     * verifies the assigned value is correct.
     * </p>
     */
    public void testSetDesignReviewers() {
        List<String> test = new ArrayList<String>();
        test.add(null);
        try {
            instance.setDesignReviewers(test);
            fail("IllegalArgumentException should throw.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * <p>
     * Test method for {@link ContestData#getDevelopmentWinner()}.It verifies
     * the assigned value is correct.
     * </p>
     */
    public void testGetDevelopmentWinner() {
        instance.setDevelopmentWinner(null);
        instance.setDevelopmentWinner("tc");
        assertEquals("fail to set/get the field.", "tc", instance.getDevelopmentWinner());
    }

    /**
     * <p>
     * Test method for {@link ContestData#setDevelopmentWinner()}.It
     * verifies the assigned value is correct.
     * </p>
     */
    public void testSetDevelopmentWinner() {
        try {
            instance.setDevelopmentWinner(" ");
            fail("IllegalArgumentException should throw.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * <p>
     * Test method for {@link ContestData#getDevelopmentSecondPlace()}.It
     * verifies the assigned value is correct.
     * </p>
     */
    public void testGetDevelopmentSecondPlace() {
        instance.setDevelopmentSecondPlace(null);
        instance.setDevelopmentSecondPlace("hello");
        assertEquals("fail to set/get the field.", "hello", instance.getDevelopmentSecondPlace());
    }

    /**
     * <p>
     * Test method for {@link ContestData#setDevelopmentSecondPlace(String)}.It
     * verifies the assigned value is correct.
     * </p>
     */
    public void testSetDevelopmentSecondPlace() {
        try {
            instance.setDevelopmentSecondPlace(" ");
            fail("IllegalArgumentException should throw.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * <p>
     * Test method for {@link ContestData#getDevelopmentReviewers()}.It
     * verifies the assigned value is correct.
     * </p>
     */
    public void testGetDevelopmentReviewers() {
        instance.setDevelopmentReviewers(null);
        List<String> test = new ArrayList<String>();
        instance.setDevelopmentReviewers(test);
        assertEquals("fail to set/get the field.", test, instance.getDevelopmentReviewers());
    }

    /**
     * <p>
     * Test method for {@link ContestData#setDevelopmentReviewers(List)}.It
     * verifies the assigned value is correct.
     * </p>
     */
    public void testSetDevelopmentReviewers() {
        List<String> test = new ArrayList<String>();
        test.add(null);
        try {
            instance.setDevelopmentReviewers(test);
            fail("IllegalArgumentException should throw.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * <p>
     * Test method for {@link ContestData#getSvnPath()}.It verifies the
     * assigned value is correct.
     * </p>
     */
    public void testGetSvnPath() {
        instance.setSvnPath("topcoder");
        assertEquals("fail to set/get the field.", "topcoder", instance.getSvnPath());
    }

    /**
     * <p>
     * Test method for {@link ContestData#setSvnPath(String)}.It verifies the
     * assigned value is correct.
     * </p>
     */
    public void testSetSvnPath() {
        try {
            instance.setSvnPath(" ");
            fail("IllegalArgumentException should throw.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * <p>
     * Test method for {@link ContestData#getCategory()}.It verifies the
     * assigned value is correct.
     * </p>
     */
    public void testGetCategory() {
        instance.setCategory("tc");
        assertEquals("fail to set/get the field.", "tc", instance.getCategory());
    }

    /**
     * <p>
     * Test method for {@link ContestData#setCategory(String)}.It verifies the
     * assigned value is correct.
     * </p>
     */
    public void testSetCategory() {
        try {
            instance.setCategory(" ");
            fail("IllegalArgumentException should throw.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }
}
