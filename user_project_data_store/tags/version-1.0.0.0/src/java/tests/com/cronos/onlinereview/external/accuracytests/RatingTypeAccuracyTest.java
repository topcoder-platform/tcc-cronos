/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * User Project Data Store 1.0
 */
package com.cronos.onlinereview.external.accuracytests;

import com.cronos.onlinereview.external.RatingType;

import junit.framework.TestCase;


/**
 * <p>
 * Tests the RatingType class.
 * </p>
 *
 * @author lyt
 * @version 1.0
 */
public class RatingTypeAccuracyTest extends TestCase {
    /**
     * <p>
     * The name of the design rating type, and the property name that stores the design phase number.
     * </p>
     */
    public static final String DESIGN_TYPE = "Design";

    /**
     * <p>
     * The name of the development rating type, and the property name that stores the development phase number.
     * </p>
     */
    public static final String DEVELOPMENT_TYPE = "Development";

    /**
     * <p>
     * The name of the development rating type, and the property name that stores the Assembly phase number.
     * </p>
     */
    public static final String ASSEMBLY_TYPE = "Assembly";

    /**
     * <p>
     * Represents the configuration file.
     * </p>
     */
    private static final String CONFIG_FILE = "Accuracy/Config.xml";

    /**
     * <p>
     * The default integer code of the development phase.
     * </p>
     */
    private static final int DEFAULT_DEV_CODE = 113;

    /**
     * <p>
     * The default integer code of the design phase.
     * </p>
     */
    private static final int DEFAULT_DESIGN_CODE = 112;

    /**
     * <p>
     * The default integer code of the assembly phase.
     * </p>
     */
    private static final int DEFAULT_ASSEMBLY_CODE = 114;

    /**
     * <p>
     * An RatingType instance for testing.
     * </p>
     */
    private RatingType ratingType = null;

    /**
     * <p>
     * Initialization.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        AccuracyHelper.addConfig(CONFIG_FILE);

        ratingType = RatingType.getRatingType(DESIGN_TYPE);
    }

    /**
     * <p>tearDown.</p>
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {

    	AccuracyHelper.clearConfig();
    }
    
    /**
     * <p>
     * Tests the accuracy of the getRatingType(String).
     * </p>
     */
    public void testGetRatingType_String_Accuracy() {
        ratingType = RatingType.getRatingType(DESIGN_TYPE);
        assertTrue("defaultRatingType should be instance of RatingType.", ratingType instanceof RatingType);

        assertEquals("The name should be set correctly.", DESIGN_TYPE, ratingType.getName());
        assertEquals("The id should be set correctly.", DEFAULT_DESIGN_CODE, ratingType.getId());
        ratingType = RatingType.getRatingType(DEVELOPMENT_TYPE);
        assertTrue("defaultRatingType should be instance of RatingType.", ratingType instanceof RatingType);

        assertEquals("The name should be set correctly.", DEVELOPMENT_TYPE, ratingType.getName());
        assertEquals("The id should be set correctly.", DEFAULT_DEV_CODE, ratingType.getId());
    }

    /**
     * <p>
     * Tests the accuracy of the getRatingType(String).
     * </p>
     */
    public void testGetRatingType_String_Configured() {
        ratingType = RatingType.getRatingType(ASSEMBLY_TYPE);

        assertNotNull("RatingInfo should be accurately created.", ratingType);
        assertTrue("defaultRatingType should be instance of RatingType.", ratingType instanceof RatingType);

        assertEquals("The name should be set correctly.", ASSEMBLY_TYPE, ratingType.getName());
        assertEquals("The id should be set correctly.", DEFAULT_ASSEMBLY_CODE, ratingType.getId());
    }

    /**
     * <p>
     * Tests the accuracy of the getRatingType(String).
     * </p>
     */
    public void testGetRatingType_String_NoConfigured() {
        ratingType = RatingType.getRatingType("NoConfig");

        assertNotNull("RatingInfo should be accurately created.", ratingType);
        assertTrue("defaultRatingType should be instance of RatingType.", ratingType instanceof RatingType);

        assertEquals("The name should be set correctly.", "NoConfig", ratingType.getName());
        assertEquals("The id should be set correctly.", 0, ratingType.getId());
    }

    /**
     * <p>
     * Tests the accuracy of the getRatingType(int).
     * </p>
     * 
     * <p>
     * The RatingType instance should be created successfully.
     * </p>
     */
    public void testGetRatingType_Int_Accuracy() {
        ratingType = RatingType.getRatingType(DEFAULT_DEV_CODE);

        assertNotNull("RatingInfo should be accurately created.", ratingType);
        assertTrue("defaultRatingType should be instance of RatingType.", ratingType instanceof RatingType);

        assertEquals("The name should be set correctly.", DEVELOPMENT_TYPE, ratingType.getName());
        assertEquals("The id should be set correctly.", DEFAULT_DEV_CODE, ratingType.getId());

        ratingType = RatingType.getRatingType(DEFAULT_DESIGN_CODE);

        assertNotNull("RatingInfo should be accurately created.", ratingType);
        assertTrue("defaultRatingType should be instance of RatingType.", ratingType instanceof RatingType);

        assertEquals("The name should be set correctly.", DESIGN_TYPE, ratingType.getName());
        assertEquals("The id should be set correctly.", DEFAULT_DESIGN_CODE, ratingType.getId());
    }

    /**
     * <p>
     * Tests the accuracy of the getRatingType(int).
     * </p>
     * 
     * <p>
     * The RatingType instance should be created successfully.
     * </p>
     */
    public void testGetRatingType_Int_Configured() {
        ratingType = RatingType.getRatingType(DEFAULT_ASSEMBLY_CODE);

        assertNotNull("RatingInfo should be accurately created.", ratingType);
        assertTrue("defaultRatingType should be instance of RatingType.", ratingType instanceof RatingType);

        assertEquals("The name should be set correctly.", ASSEMBLY_TYPE, ratingType.getName());
        assertEquals("The id should be set correctly.", DEFAULT_ASSEMBLY_CODE, ratingType.getId());
    }

    /**
     * <p>
     * Tests the accuracy of the getRatingType(int).
     * </p>
     * 
     * <p>
     * The RatingType instance should be created successfully.
     * </p>
     * 
     * <p>
     * If the ratingType is not found, null should be returned.
     * </p>
     */
    public void testGetRatingType_Int_Unknown() {
        ratingType = RatingType.getRatingType(1000);

        assertNull("If the ratingType is not found, null should be returned.", ratingType);
    }

    /**
     * <p>
     * Tests the accuracy of the getter getName().
     * </p>
     */
    public void testGetter_GetName() {
        assertEquals("The name should be got correctly.", DESIGN_TYPE, ratingType.getName());
    }

    /**
     * <p>
     * Tests the accuracy of the getter getId().
     * </p>
     */
    public void testGetter_GetId() {
        assertEquals("The id should be got correctly.", DEFAULT_DESIGN_CODE, ratingType.getId());
    }

    /**
     * <p>
     * Tests the accuracy of the method toString().
     * </p>
     */
    public void testToString() {
        assertEquals("The string should be the same.", DESIGN_TYPE, ratingType.toString());
    }

    /**
     * <p>
     * Tests the accuracy of the method hashCode().
     * </p>
     */
    public void testHashCode() {
        assertEquals("The hashCode should be the same.", DESIGN_TYPE.hashCode(), ratingType.hashCode());
    }

    /**
     * <p>
     * Tests the accuracy of the method equals().
     * </p>
     * 
     * <p>
     * The given parameter object is just equal to the defaultRatingType, they are both the RatingType and they have
     * the same hashCode.
     * </p>
     */
    public void testEquals_Accuracy1() {
        assertTrue("These two objects should be equal.", ratingType.equals(RatingType.getRatingType(DESIGN_TYPE)));
    }

    /**
     * <p>
     * Tests the accuracy of the method equals().
     * </p>
     * 
     * <p>
     * The given parameter object is not equal to the defaultRatingType, they are both the RatingType but they don't
     * have the same hashCode.
     * </p>
     */
    public void testEquals_Accuracy2() {
        assertFalse("These two objects should be non-equal.",
            ratingType.equals(RatingType.getRatingType(DEVELOPMENT_TYPE)));
    }

    /**
     * <p>
     * Tests the accuracy of the method equals().
     * </p>
     * 
     * <p>
     * The given parameter object is not equal to the defaultRatingType, the given object is null.
     * </p>
     */
    public void testEquals_Accuracy3() {
        assertFalse("These two objects should be non-equal.", ratingType.equals(null));
    }

    /**
     * <p>
     * Tests the accuracy of the method equals().
     * </p>
     * 
     * <p>
     * The given parameter object is not equal to the defaultRatingType, the given object is not null but it is not the
     * RatingType instance.
     * </p>
     */
    public void testEquals_Accuracy4() {
        assertFalse("These two objects should be non-equal.", ratingType.equals(new Object()));
    }
}
