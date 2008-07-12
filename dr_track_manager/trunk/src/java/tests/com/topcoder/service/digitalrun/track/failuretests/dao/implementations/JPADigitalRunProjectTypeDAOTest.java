/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.failuretests.dao.implementations;

import junit.framework.Test;
import junit.framework.TestSuite;
import com.topcoder.service.digitalrun.track.dao.implementations.JPADigitalRunProjectTypeDAO;
import com.topcoder.service.digitalrun.track.EntityNotFoundException;
import com.topcoder.service.digitalrun.entity.ProjectType;

import org.easymock.EasyMock;

/**
 * Some tests for JPADigitalRunProjectTypeDAO class.
 *
 * @author Orange_Cloud
 * @version 1.0
 */
public class JPADigitalRunProjectTypeDAOTest extends BaseTest {
    /**
     * Instance to test.
     */
    private JPADigitalRunProjectTypeDAO target;

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(JPADigitalRunProjectTypeDAOTest.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        super.setUp();
        target = new JPADigitalRunProjectTypeDAO();
        target.setUnitName("persistence_unit");
        target.setSessionContext(context);
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Tests getProjectType method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetProjectTypeForNegative() throws Exception {
        try {
            target.getProjectType(-1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests getProjectType method for null parameter. EntityNotFoundException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetProjectTypeForNoEntity() throws Exception {
        EasyMock.expect(em.find(ProjectType.class, 1l)).andReturn(null);
        EasyMock.replay(context, em);
        try {
            target.getProjectType(1);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException ex) {
            // success
        }
    }
}