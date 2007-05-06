/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.external;

import junit.framework.TestCase;

/**
 * <p>
 * Tests the RatingType class.
 * </p>
 *
 * @author oodinary
 * @author FireIce
 * @version 2.0
 * @since 1.0
 */
public class RatingTypeUnitTest extends TestCase {

    /**
     * <p>
     * The name of the design rating type, and the property name that stores the design phase number.
     * </p>
     */
    public static final String DESIGN_NAME = "Design";

    /**
     * <p>
     * The name of the development rating type, and the property name that stores the development phase number.
     * </p>
     */
    public static final String DEVELOPMENT_NAME = "Development";

    /**
     * <p>
     * The name of the development rating type, and the property name that stores the Assembly phase number.
     * </p>
     */
    public static final String ASSEMBLY_NAME = "Assembly";

    /**
     * <p>
     * Represents the configuration file.
     * </p>
     */
    private static final String CONFIG_FILE = "SampleConfig.xml";

    /**
     * <p>
     * The default integer code of the development phase.
     * </p>
     */
    private static final int DEFAULTDEVCODE = 113;

    /**
     * <p>
     * The default integer code of the design phase.
     * </p>
     */
    private static final int DEFAULTDESIGNCODE = 112;

    /**
     * <p>
     * The default integer code of the assembly phase.
     * </p>
     */
    private static final int DEFAULTASSEMBLYCODE = 114;

    /**
     * <p>
     * An RatingType instance for testing.
     * </p>
     */
    private RatingType defaultRatingType = null;

    /**
     * <p>
     * Initialization.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        UnitTestHelper.addConfig(CONFIG_FILE);

        defaultRatingType = RatingType.getRatingType(DESIGN_NAME);
    }

    /**
     * <p>
     * Set defaultRatingType to null.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void tearDown() throws Exception {
        defaultRatingType = null;

        UnitTestHelper.clearConfig();

        super.tearDown();
    }

    /**
     * <p>
     * Tests the accuracy of the getRatingType(String).
     * </p>
     * <p>
     * The RatingType instance should be created successfully.
     * </p>
     */
    public void testGetRatingType_String() {

        assertNotNull("RatingInfo should be accurately created.", defaultRatingType);
        assertTrue("defaultRatingType should be instance of RatingType.", defaultRatingType instanceof RatingType);

        assertEquals("The name should be set correctly.", DESIGN_NAME, defaultRatingType.getName());
        assertEquals("The id should be set correctly.", DEFAULTDESIGNCODE, defaultRatingType.getId());
    }

    /**
     * <p>
     * Tests the accuracy of the getRatingType(String).
     * </p>
     * <p>
     * The RatingType instance should be created successfully.
     * </p>
     * <p>
     * The type has been configured in the SampleConfig.xml
     * </p>
     */
    public void testGetRatingType_String_Configured1() {

        defaultRatingType = RatingType.getRatingType(ASSEMBLY_NAME);

        assertNotNull("RatingInfo should be accurately created.", defaultRatingType);
        assertTrue("defaultRatingType should be instance of RatingType.", defaultRatingType instanceof RatingType);

        assertEquals("The name should be set correctly.", ASSEMBLY_NAME, defaultRatingType.getName());
        assertEquals("The id should be set correctly.", DEFAULTASSEMBLYCODE, defaultRatingType.getId());
    }

    /**
     * <p>
     * Tests the accuracy of the getRatingType(String).
     * </p>
     * <p>
     * The RatingType instance should be created successfully.
     * </p>
     * <p>
     * The type has not been configured in the SampleConfig.xml
     * </p>
     */
    public void testGetRatingType_String_NonConfigured() {
        defaultRatingType = RatingType.getRatingType("None");

        assertNotNull("RatingInfo should be accurately created.", defaultRatingType);
        assertTrue("defaultRatingType should be instance of RatingType.", defaultRatingType instanceof RatingType);

        assertEquals("The name should be set correctly.", "None", defaultRatingType.getName());
        assertEquals("The id should be set correctly.", 0, defaultRatingType.getId());
    }

    /**
     * <p>
     * Tests the failure of the getRatingType(String).
     * </p>
     * <p>
     * If the given typeName is null, IllegalArgumentException should be thrown.
     * </p>
     */
    public void testGetRatingType_String_NullTypeName() {

        try {
            RatingType.getRatingType(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests the failure of the getRatingType(String).
     * </p>
     * <p>
     * If the given typeName is empty after trimmed, IllegalArgumentException should be thrown.
     * </p>
     */
    public void testGetRatingType_String_EmptyTypeName() {

        try {
            RatingType.getRatingType("   ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests the accuracy of the getRatingType(int).
     * </p>
     * <p>
     * The RatingType instance should be created successfully.
     * </p>
     */
    public void testGetRatingType_Int() {

        defaultRatingType = RatingType.getRatingType(DEFAULTDEVCODE);

        assertNotNull("RatingInfo should be accurately created.", defaultRatingType);
        assertTrue("defaultRatingType should be instance of RatingType.", defaultRatingType instanceof RatingType);

        assertEquals("The name should be set correctly.", DEVELOPMENT_NAME, defaultRatingType.getName());
        assertEquals("The id should be set correctly.", DEFAULTDEVCODE, defaultRatingType.getId());
    }

    /**
     * <p>
     * Tests the accuracy of the getRatingType(int).
     * </p>
     * <p>
     * The RatingType instance should be created successfully.
     * </p>
     * <p>
     * If the ratingType is not found, null should be returned.
     * </p>
     */
    public void testGetRatingType_Int_NotFound() {

        defaultRatingType = RatingType.getRatingType(100);

        assertNull("If the ratingType is not found, null should be returned.", defaultRatingType);
    }

    /**
     * <p>
     * Tests the failure of the getRatingType(int).
     * </p>
     * <p>
     * If the given id is not positive, IllegalArgumentException should be thrown.
     * </p>
     * <p>
     * This test gives a zero as the parameter.
     * </p>
     */
    public void testGetRatingType_Int_NotPositive1() {

        try {
            RatingType.getRatingType(0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests the failure of the getRatingType(int).
     * </p>
     * <p>
     * If the given id is not positive, IllegalArgumentException should be thrown.
     * </p>
     * <p>
     * This test gives a negative number as the parameter.
     * </p>
     */
    public void testGetRatingType_Int_NotPositive2() {

        try {
            RatingType.getRatingType(-5);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests the accuracy of the getter getName().
     * </p>
     */
    public void testGetter_GetName() {

        assertEquals("The name should be got correctly.", DESIGN_NAME, defaultRatingType.getName());
    }

    /**
     * <p>
     * Tests the accuracy of the getter getId().
     * </p>
     */
    public void testGetter_GetId() {

        assertEquals("The id should be got correctly.", DEFAULTDESIGNCODE, defaultRatingType.getId());
    }

    /**
     * <p>
     * Tests the accuracy of the method toString().
     * </p>
     */
    public void testToString() {

        assertEquals("The string should be the same.", DESIGN_NAME, defaultRatingType.toString());
    }

    /**
     * <p>
     * Tests the accuracy of the method hashCode().
     * </p>
     */
    public void testHashCode() {

        assertEquals("The hashCode should be the same.", DESIGN_NAME.hashCode(), defaultRatingType.hashCode());
    }

    /**
     * <p>
     * Tests the accuracy of the method equals().
     * </p>
     * <p>
     * The given parameter object is just equal to the defaultRatingType, they are both the RatingType and they have the
     * same hashCode.
     * </p>
     */
    public void testEquals_EqualsObject() {
        assertTrue("These two objects should be equal.", defaultRatingType
                .equals(RatingType.getRatingType(DESIGN_NAME)));
    }

    /**
     * <p>
     * Tests the accuracy of the method equals().
     * </p>
     * <p>
     * The given parameter object is not equal to the defaultRatingType, they are both the RatingType but they don't
     * have the same hashCode.
     * </p>
     */
    public void testEquals_NonEqualsObject1() {
        assertFalse("These two objects should be non-equal.", defaultRatingType.equals(RatingType
                .getRatingType(DEVELOPMENT_NAME)));
    }

    /**
     * <p>
     * Tests the accuracy of the method equals().
     * </p>
     * <p>
     * The given parameter object is not equal to the defaultRatingType, the given object is null.
     * </p>
     */
    public void testEquals_NonEqualsObject2() {
        assertFalse("These two objects should be non-equal.", defaultRatingType.equals(null));
    }

    /**
     * <p>
     * Tests the accuracy of the method equals().
     * </p>
     * <p>
     * The given parameter object is not equal to the defaultRatingType, the given object is not null but it is not the
     * RatingType instance.
     * </p>
     */
    public void testEquals_NonEqualsObject3() {
        assertFalse("These two objects should be non-equal.", defaultRatingType.equals(new Object()));
    }
}
