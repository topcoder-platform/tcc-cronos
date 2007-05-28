/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.team.service.accuracytests.impl;

import junit.framework.TestCase;

import com.topcoder.management.resource.Resource;
import com.topcoder.management.team.TeamPosition;
import com.topcoder.registration.team.service.impl.ResourcePositionImpl;

/**
 * <p>
 * Accuracy unit tests for <code>ResourcePositionImpl</code> class.
 * </p>
 * @author 80x86
 * @version 1.0
 */
public class ResourcePositionImplUnitTests extends TestCase {

    /**
     * <p>
     * ResourcePositionImpl instance used in the tests.
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
     * Accuracy test for default constructor.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1Accuracy() throws Exception {
        rp = new ResourcePositionImpl();
        assertNotNull("should not be null.", rp);
    }

    /**
     * <p>
     * Accuracy test for constructor with full parameters.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2Accuracy() throws Exception {
        assertNotNull("'rp' should not be null.", rp);
    }

    /**
     * <p>
     * Accuracy test for <code>getMemberResource()</code> method.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetResource() throws Exception {
        assertEquals("Resource mismatched.", resource, rp.getMemberResource());
    }

    /**
     * <p>
     * Accuracy test for <code>setMemberResource(resource)</code> method.
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
     * Accuracy test for <code>getPosition()</code> method.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetPositionAccuracy() throws Exception {
        assertEquals("Team position mismatched.", position, rp.getPosition());
    }

    /**
     * <p>
     * Accuracy test for <code>setPosition(position)</code> method.
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
