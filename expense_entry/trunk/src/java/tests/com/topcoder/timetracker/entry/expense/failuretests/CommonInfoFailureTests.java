/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.expense.failuretests;

import com.topcoder.timetracker.entry.expense.CommonInfo;

import junit.framework.TestCase;

/**
 * Failure tests for <c>CommonInfo</c> class.
 * 
 * @author kr00tki
 * @version 1.0
 */
public class CommonInfoFailureTests extends TestCase {

    /**
     * <c>CommonInfo</c> instance to test on.
     */
    private CommonInfo commonInfo = new DummyCommonInfo();;

    /**
     * Tests constructor failure.
     * 
     */
    public void testCommonInfoInt() {
        try {
            new DummyCommonInfo(-1);
            fail("IAE expected, id == -1");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Tests <c>setId</c> method failure.
     * 
     */
    public void testSetId() {
        try {
            commonInfo.setId(-1);
            fail("IAE expected, id == -1");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Tests <c>setDescription</c> method failure.
     * 
     */
    public void testSetDescriptionEmpty() {
        try {
            commonInfo.setDescription(" ");
            fail("IAE expected, empty string");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Tests <c>setDescription</c> method failure.
     * 
     */
    public void testSetDescriptionNull() {
        try {
            commonInfo.setDescription(null);
            fail("NPE expected, null value");
        } catch (NullPointerException e) {
            // ok
        }
    }

    /**
     * Tests <c>setCreationDate</c> method failure.
     * 
     */
    public void testSetCreationDateNull() {
        try {
            commonInfo.setCreationDate(null);
            fail("NPE expected, null value");
        } catch (NullPointerException e) {
            // ok
        }
    }

    /**
     * Tests <c>setCreationUser</c> method failure.
     * 
     */
    public void testSetCreationUserNull() {
        try {
            commonInfo.setCreationUser(null);
            fail("NPE expected, null value");
        } catch (NullPointerException e) {
            // ok
        }
    }

    /**
     * Tests <c>setCreationUser</c> method failure.
     * 
     */
    public void testSetCreationUserEmpty() {
        try {
            commonInfo.setCreationUser(" ");
            fail("IAE expected, empty string");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Tests <c>setModificationDate</c> method failure.
     * 
     */
    public void testSetModificationDateNull() {
        try {
            commonInfo.setModificationDate(null);
            fail("NPE expected, null value");
        } catch (NullPointerException e) {
            // ok
        }
    }

    /**
     * Tests <c>setModificationUser</c> method failure.
     * 
     */
    public void testSetModificationUserNull() {
        try {
            commonInfo.setModificationUser(null);
            fail("NPE expected, null value");
        } catch (NullPointerException e) {
            // ok
        }
    }

    /**
     * Tests <c>setModificationUser</c> method failure.
     * 
     */
    public void testSetModificationUserEmpty() {
        try {
            commonInfo.setModificationUser(" ");
            fail("IAE expected, empty string");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Dummy implementation od CommonInfo abstract class.
     * 
     * @author kr00tki
     * @version 1.0
     */
    private class DummyCommonInfo extends CommonInfo {
        /**
         * Constructor.
         * 
         */
        public DummyCommonInfo() {

        }

        /**
         * Constructor.
         * 
         * @param id id
         */
        public DummyCommonInfo(int id) {
            super(id);
        }
    }

}
