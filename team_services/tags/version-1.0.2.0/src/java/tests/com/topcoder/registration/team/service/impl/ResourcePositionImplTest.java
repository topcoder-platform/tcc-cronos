/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.team.service.impl;

import com.topcoder.management.resource.Resource;
import com.topcoder.management.team.TeamPosition;

import junit.framework.TestCase;

/**
 * <p>
 * This is a test case for <code>ResourcePositionImpl</code> class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ResourcePositionImplTest extends TestCase {

    /**
     * <p>
     * Represents an instance of <code>ResourcePositionImpl</code> class.
     * </p>
     */
    private ResourcePositionImpl rp;

    /**
     * <p>
     * Represents an instance of Resource.
     * </p>
     */
    private Resource resource;

    /**
     * <p>
     * Represents an instance of TeamPosition.
     * </p>
     */
    private TeamPosition position;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        resource = new Resource(1);
        position = new TeamPosition();
        rp = new ResourcePositionImpl(resource, position);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        rp = null;
    }

    /**
     * <p>
     * Test for default constructor.
     * </p>
     * <p>
     * Tests it for accuracy, non-null instance should be created.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1Accuracy() throws Exception {
        rp = new ResourcePositionImpl();
        assertNotNull("'rp' should not be null.", rp);
    }

    /**
     * <p>
     * Test for constructor with full parameters.
     * </p>
     * <p>
     * Tests it against null resource, expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithNullResource() throws Exception {
        try {
            new ResourcePositionImpl(null, new TeamPosition());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with full parameters.
     * </p>
     * <p>
     * Tests it against null resource, expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithNullPosition() throws Exception {
        try {
            new ResourcePositionImpl(new Resource(1), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with full parameters.
     * </p>
     * <p>
     * Tests it for accuracy, non-null instance should be created.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2Accuracy() throws Exception {
        assertNotNull("'rp' should not be null.", rp);
    }

    /**
     * <p>
     * Test for <code>getMemberResource()</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, valid Resource instance should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetResource() throws Exception {
        assertEquals("Resource mismatched.", resource, rp.getMemberResource());
    }

    /**
     * <p>
     * Test for <code>setMemberResource(resource)</code> method.
     * </p>
     * <p>
     * Tests it against null resource, expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetMemberResourceWithNullResource() throws Exception {
        try {
            rp.setMemberResource(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>setMemberResource(resource)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, given resource should be set successfully.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetMemberResourceAccuracy() throws Exception {
        Resource rs = new Resource(2);
        rp.setMemberResource(rs);
        assertEquals("Member resource mismatched.", rs, rp.getMemberResource());
    }

    /**
     * <p>
     * Test for <code>getPosition()</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, valid TeamPosition instance should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetPositionWithNullPosition() throws Exception {
        assertEquals("Team position mismatched.", position, rp.getPosition());
    }

    /**
     * <p>
     * Test for <code>setPosition(position)</code> method.
     * </p>
     * <p>
     * Tests it with null position, expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetPositionWithNullPosition() throws Exception {
        try {
            rp.setPosition(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>setPosition(position)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, given position should be set successfully.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetPositionAccuracy() throws Exception {
        TeamPosition ps = new TeamPosition();
        rp.setPosition(ps);
        assertEquals("Team position mismatched.", ps, rp.getPosition());
    }
}
