/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.failuretests;

import com.topcoder.management.resource.IdAlreadySetException;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;

import junit.framework.TestCase;

/**
 * Failure test for {@link Resource} class.
 *
 * @author mayi
 * @version 1.0
 */
public class ResourceFailureTest extends TestCase {
    /**
     * A <code>Resource</code> instance to test against.
     */
    private Resource resource = null;

    /**
     * Set up.
     * <p>Create the <code>resource</code> instance to test.</p>
     */
    protected void setUp() {
        resource = new Resource();
    }

    /**
     * Test <code>Resource(long)</code> with zero id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testConstructor_Long_ZeroId() {
        try {
            new Resource(0);
            fail("Should throw IllegalArgumentException for zero id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>Resource(long)</code> with negative id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testConstructor_Long_NegativeId() {
        try {
            new Resource(-1);
            fail("Should throw IllegalArgumentException for negative id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>Resource(long, ResourceRole)</code> with zero id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testConstructor_LongResourceRole_ZeroId() {
        try {
            new Resource(0, new ResourceRole());
            fail("Should throw IllegalArgumentException for zero id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>Resource(long, ResourceRole)</code> with negative id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testConstructor_LongResourceRole_NegativeId() {
        try {
            new Resource(-1, new ResourceRole());
            fail("Should throw IllegalArgumentException for negative id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>Resource(long, ResourceRole)</code> with null ResourceRole.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testConstructor_LongResourceRole_NullName() {
        try {
            new Resource(1L, null);
            fail("Should throw IllegalArgumentException for null ResourceRole.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }


    /**
     * Test <code>setId(long)</code> with zero id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testSetId_ZeroId() {
        try {
            resource.setId(0);
            fail("Should throw IllegalArgumentException for zero id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>setId(long)</code> with negative id after id is set.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testSetId_NegativeId() {
        resource.setId(1);

        try {
            resource.setId(-1);
            fail("Should throw IllegalArgumentException for negative id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>setId(long)</code> after id is set.
     * <p>IdAlreadySetException should be thrown.</p>
     */
    public void testSetId_AlreadySet() {
        resource.setId(1);

        try {
            resource.setId(1);
            fail("Should throw IdAlreadySetException for negative id.");
        } catch (IdAlreadySetException e) {
            // pass
        }
    }

    /**
     * Test <code>setProject(long)</code> with zero Project value.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testSetProject_ZeroId() {
        try {
            resource.setProject(new Long(0));
            fail("Should throw IllegalArgumentException for zero Project.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>setProject(long)</code> with negative Project value.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testSetProject_NegativeId() {
        try {
            resource.setProject(new Long(-1));
            fail("Should throw IllegalArgumentException for negative Project.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>setPhase(long)</code> with zero Phase value.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testSetPhase_ZeroId() {
        try {
            resource.setPhase(new Long(0));
            fail("Should throw IllegalArgumentException for zero Phase.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>setPhase(long)</code> with negative Phase value.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testSetPhase_NegativeId() {
        try {
            resource.setPhase(new Long(-1));
            fail("Should throw IllegalArgumentException for negative Phase.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>setSubmission(long)</code> with zero Submission value.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testSetSubmission_ZeroId() {
        try {
            resource.setSubmission(new Long(0));
            fail("Should throw IllegalArgumentException for zero Submission.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>setSubmission(long)</code> with negative Submission value.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testSetSubmission_NegativeId() {
        try {
            resource.setSubmission(new Long(-1));
            fail("Should throw IllegalArgumentException for negative Submission.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Set <code>setProperty(String, Object)</code> with null name.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testSetProperty_NullName() {
        try {
            resource.setProperty(null, new Object());
            fail("Should throw IllegalArgumentException for null name.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Set <code>setProperty(String, Object)</code> with null value.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testSetProperty_NullValue() {
        try {
            resource.setProperty("name", null);
            fail("Should throw IllegalArgumentException for null value.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Set <code>hasProperty(String)</code> with null name.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testHasProperty_NullName() {
        try {
            resource.hasProperty(null);
            fail("Should throw IllegalArgumentException for null name.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Set <code>getProperty(String)</code> with null name.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testGetProperty_NullName() {
        try {
            resource.getProperty(null);
            fail("Should throw IllegalArgumentException for null name.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

}
