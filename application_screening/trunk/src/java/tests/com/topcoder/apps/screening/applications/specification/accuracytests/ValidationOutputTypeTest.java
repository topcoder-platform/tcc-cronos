package com.topcoder.apps.screening.applications.specification.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.apps.screening.applications.specification.ValidationOutputType;

public class ValidationOutputTypeTest extends TestCase {

    private String error = "error";

    private String report = "report";

    /**
     * Creates a test suite for the tests in this test case.
     * 
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        return new TestSuite(ValidationOutputTypeTest.class);
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.ValidationOutputType.equals(Object)'
     */
    public final void testEqualsObjectNullValue() {
        // if null is provided, false shoud be rerurned
        assertEquals("false must be returned", false,
                ValidationOutputType.ERROR.equals(null));
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.ValidationOutputType.equals(Object)'
     */
    public final void testEqualsObjectWrongInstanceValue() {
        // if value of wrong instance is provided, false shoud be rerurned
        assertEquals("false must be returned", false,
                ValidationOutputType.ERROR.equals(new Integer(999)));
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.ValidationOutputType.equals(Object)'
     */
    public final void testEqualsObjectNotEqual() {
        // objects are not equal, false shoud be rerurned
        assertEquals("false must be returned", false,
                ValidationOutputType.ERROR.equals(ValidationOutputType.REPORT));
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.ValidationOutputType.equals(Object)'
     */
    public final void testEqualsObjectEqual() {
        // objects are equal, true shoud be rerurned
        assertEquals("true must be returned", true, ValidationOutputType.ERROR
                .equals(ValidationOutputType.ERROR));
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.ValidationOutputType.getHashCode()'
     */
    public final void testGetHashCode() {
        assertEquals("hashCode is wrong", error.hashCode(),
                ValidationOutputType.ERROR.hashCode());
        assertEquals("hashCode is wrong", report.hashCode(),
                ValidationOutputType.REPORT.hashCode());
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.ValidationOutputType.getType()'
     */
    public final void testGetType() {
        assertEquals("type is wrong", error, ValidationOutputType.ERROR
                .getType());
        assertEquals("type is wrong", report, ValidationOutputType.REPORT
                .getType());
    }
}