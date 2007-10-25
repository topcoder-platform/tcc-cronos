/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.team.service.failuretests;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.team.TeamPosition;
import com.topcoder.registration.team.service.impl.ResourcePositionImpl;

import junit.framework.TestCase;
/**
 * Failure tests for ResourcePositionImpl class.
 * @author slion
 * @version 1.0
 */
public class ResourcePositionImplFailureTests extends TestCase {
    /**
     * Represents the ResourcePositionimpl instance for testing.
     */
    private ResourcePositionImpl impl = null;

    /**
     * Represents the TeamPosition instance for testing.
     */
    private TeamPosition position;

    /**
     * Represents the Resource instance for testing.
     */
    private Resource resource;

    /**
     * Setup the test environment.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        impl = new ResourcePositionImpl();
        position = new TeamPosition();
        resource = new Resource();
    }

    /**
     * Teardown the test environment.
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        impl = null;
        position = null;
        resource = null;
    }

    /**
     * Tests ResourcePositionImpl(Resource memberResource, TeamPosition position)
     *  method with null Resource memberResource,
     * IllegalArgumentException should be thrown.
     */
    public void testResourcePositionImpl_NullMemberResource() {
        try {
            new ResourcePositionImpl(null, position);
            fail("testResourcePositionImpl_NullMemberResource is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testResourcePositionImpl_NullMemberResource.");
        }
    }

    /**
     * Tests ResourcePositionImpl(Resource memberResource, TeamPosition position)
     *  method with null TeamPosition position,
     * IllegalArgumentException should be thrown.
     */
    public void testResourcePositionImpl_NullPosition() {
        try {
            new ResourcePositionImpl(resource, null);
            fail("testResourcePositionImpl_NullPosition is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testResourcePositionImpl_NullPosition.");
        }
    }

    /**
     * Tests setMemberResource(Resource memberResource) method with null Resource memberResource,
     * IllegalArgumentException should be thrown.
     */
    public void testSetMemberResource_NullMemberResource() {
        try {
            impl.setMemberResource(null);
            fail("testSetMemberResource_NullMemberResource is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testSetMemberResource_NullMemberResource.");
        }
    }

    /**
     * Tests setPosition(TeamPosition position) method with null TeamPosition position,
     * IllegalArgumentException should be thrown.
     */
    public void testSetPosition_NullPosition() {
        try {
            impl.setPosition(null);
            fail("testSetPosition_NullPosition is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testSetPosition_NullPosition.");
        }
    }

    
}