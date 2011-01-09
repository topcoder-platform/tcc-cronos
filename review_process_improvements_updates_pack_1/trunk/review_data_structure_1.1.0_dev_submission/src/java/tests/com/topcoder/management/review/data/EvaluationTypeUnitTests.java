/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.data;

import java.io.Serializable;

import junit.framework.TestCase;

/**
 * Unit tests for <code>EvaluationType</code> class.
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class EvaluationTypeUnitTests extends TestCase {

    /**
     * The <code>EvaluationType</code> instance used to test against.
     */
    private EvaluationType type = null;

    /**
     * Set up the test environment.
     */
    protected void setUp() {
        type = new EvaluationType(20060624, "EvaluationType");
    }

    /**
     * Test whether <code>EvaluationType</code> class implements the <code>Serializable</code> interface.
     */
    public void testImplements() {
        assertTrue("EvaluationType should implement Serializable interface",
                type instanceof Serializable);
    }

    /**
     * Test the constructor <code>EvaluationType()</code>, all fields should have their default unassigned values.
     */
    public void testConstructorWithNoArgu() {
        EvaluationType ct = new EvaluationType();
        assertNotNull("EvaluationType instance should be created", ct);
        assertTrue("id field should be -1", ct.getId() == -1);
        assertNull("name field should be null", ct.getName());
    }

    /**
     * Test the constructor <code>EvaluationType(long)</code> with negative id, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testConstructorWithNegativeId() {
        try {
            new EvaluationType(-20060624);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>EvaluationType(long)</code> with zero id, <code>IllegalArgumentException</code> should
     * be thrown.
     */
    public void testConstructorWithZeroId() {
        try {
            new EvaluationType(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>EvaluationType(long)</code> with positive id, instance of <code>CommentType</code>
     * should be created.
     */
    public void testConstructorWithPositiveId() {
        EvaluationType ct = new EvaluationType(2006);
        assertNotNull("EvaluationType instance should be created", ct);
        assertTrue("id field should be 2006", ct.getId() == 2006);
        assertNull("name field should be null", ct.getName());
    }

    /**
     * Test the constructor <code>EvaluationType(long, String)</code> with negative id and non-null name,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithNegativeIdAndNonNullName() {
        try {
            new EvaluationType(-20060624, "type");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>EvaluationType(long, String)</code> with zero id and non-null name,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithZeroIdAndNonNullName() {
        try {
            new EvaluationType(0, "type");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>EvaluationType(long, String)</code> with positive id and null name,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithPositiveIdAndNullName() {
        try {
            new EvaluationType(2006, null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>EvaluationType(long, String)</code> with positive id and non-null name, instance of
     * <code>EvaluationType</code> should be created.
     */
    public void testConstructorWithPositiveIdAndNonNullName() {
        EvaluationType ct1 = new EvaluationType(2006, "type");
        assertNotNull("EvaluationType instance should be created", ct1);
        assertTrue("id field should be 2006", ct1.getId() == 2006);
        assertTrue("name field should be 'type'", ct1.getName().equals("type"));

        // empty name is acceptable
        EvaluationType ct2 = new EvaluationType(2006, "");
        assertNotNull("EvaluationType instance should be created", ct2);
        assertTrue("id field should be 2006", ct2.getId() == 2006);
        assertTrue("name field should be ''", ct2.getName().equals(""));

        // all whitespace name is acceptable
        EvaluationType ct3 = new EvaluationType(2006, "   ");
        assertNotNull("EvaluationType instance should be created", ct3);
        assertTrue("id field should be 2006", ct3.getId() == 2006);
        assertTrue("name field should be '   '", ct3.getName().equals("   "));
    }

    /**
     * Test the constructor <code>EvaluationType(long, String, String)</code> with negative id and non-null name,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructor3WithNegativeIdAndNonNullName() {
        try {
            new EvaluationType(-20060624, "type", "description");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>EvaluationType(long, String, String)</code> with zero id and non-null name,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructor3WithZeroIdAndNonNullName() {
        try {
            new EvaluationType(0, "type", "");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>EvaluationType(long, String, String)</code> with positive id and null name,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructor3WithPositiveIdAndNullName() {
        try {
            new EvaluationType(2006, null, null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>EvaluationType(long, String, String)</code> with positive id and non-null name,
     * instance of <code>EvaluationType</code> should be created.
     */
    public void testConstructor3WithPositiveIdAndNonNullName() {
        EvaluationType ct1 = new EvaluationType(2006, "type", "description");
        assertNotNull("EvaluationType instance should be created", ct1);
        assertTrue("id field should be 2006", ct1.getId() == 2006);
        assertTrue("name field should be 'type'", ct1.getName().equals("type"));

        // empty name is acceptable
        EvaluationType ct2 = new EvaluationType(2006, "");
        assertNotNull("EvaluationType instance should be created", ct2);
        assertTrue("id field should be 2006", ct2.getId() == 2006);
        assertTrue("name field should be ''", ct2.getName().equals(""));

        // all whitespace name is acceptable
        EvaluationType ct3 = new EvaluationType(2006, "   ");
        assertNotNull("EvaluationType instance should be created", ct3);
        assertTrue("id field should be 2006", ct3.getId() == 2006);
        assertTrue("name field should be '   '", ct3.getName().equals("   "));
    }

    /**
     * Test the method <code>setId(long)</code> with positive id, the id field should be set successfully.
     */
    public void testSetIdWithPositiveId() {
        type.setId(2006);
        assertTrue("id field should be 2006", type.getId() == 2006);
    }

    /**
     * Test the method <code>getId()</code>, the id value should be returned successfully.
     */
    public void testGetId() {
        assertTrue("getId method should return 20060624", type.getId() == 20060624);
    }

    /**
     * Test the method <code>resetId()</code>, the id field should be set to -1.
     */
    public void testResetId() {
        type.resetId();
        assertTrue("id field should be -1", type.getId() == -1);
    }

    /**
     * Test the method <code>setName(String)</code>, the name field should be set successfully.
     */
    public void testSetName() {
        type.setName("other name");
        assertTrue("name field should be 'other name'", type.getName().equals("other name"));

        // null name is acceptable
        type.setName(null);
        assertNull("name field should be null", type.getName());

        // empty name is acceptable
        type.setName("");
        assertTrue("name field should be ''", type.getName().equals(""));

        // all whitespace name is acceptable
        type.setName("   ");
        assertTrue("name field should be '   '", type.getName().equals("   "));
    }

    /**
     * Test the method <code>getName()</code>, the name value should be returned successfully.
     */
    public void testGetName() {
        assertTrue("getName method should return 'EvaluationType'",
                type.getName().equals("EvaluationType"));
    }

    /**
     * Test the method <code>resetName()</code>, the name field should be set to null.
     */
    public void testResetName() {
        type.resetName();
        assertNull("name field should be null", type.getName());
    }

    /**
     * Test the method <code>setDescription(String)</code>, the Description field should be set successfully.
     */
    public void testSetDescription() {
        type.setDescription("other Description");
        assertTrue("description field should be 'other Description'",
                type.getDescription().equals("other Description"));

        // null name is acceptable
        type.setDescription(null);
        assertNull("description field should be null", type.getDescription());

        // empty name is acceptable
        type.setDescription("");
        assertTrue("description field should be ''", type.getDescription().equals(""));

        // all whitespace name is acceptable
        type.setDescription("   ");
        assertTrue("description field should be '   '", type.getDescription().equals("   "));
    }

    /**
     * Test the method <code>getDescription()</code>, the name value should be returned successfully.
     */
    public void testGetDescription() {
        type.setDescription("EvaluationType");
        assertTrue("getDescription method should return 'EvaluationType'", type.getDescription()
                .equals("EvaluationType"));
    }

    /**
     * Test the method <code>resetDescription()</code>, the Description field should be set to null.
     */
    public void testResetDescription() {
        type.resetDescription();
        assertNull("description field should be null", type.getDescription());
    }
}
