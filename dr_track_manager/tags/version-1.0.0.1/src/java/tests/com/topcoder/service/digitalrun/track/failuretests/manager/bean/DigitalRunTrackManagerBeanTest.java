/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.failuretests.manager.bean;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.topcoder.service.digitalrun.track.manager.bean.DigitalRunTrackManagerBean;
import com.topcoder.service.digitalrun.track.dao.implementations.JPADigitalRunTrackDAO;
import com.topcoder.service.digitalrun.track.dao.implementations.JPADigitalRunTrackTypeDAO;
import com.topcoder.service.digitalrun.track.dao.implementations.JPADigitalRunTrackStatusDAO;
import com.topcoder.service.digitalrun.track.dao.implementations.JPADigitalRunPointsCalculatorDAO;
import com.topcoder.service.digitalrun.track.dao.implementations.JPADigitalRunProjectTypeDAO;
import com.topcoder.service.digitalrun.entity.ProjectType;
import com.topcoder.service.digitalrun.entity.Track;

import javax.ejb.SessionContext;
import java.lang.reflect.Field;

import org.easymock.EasyMock;

/**
 * Some tests for DigitalRunTrackManagerBean class.
 *
 * @author Orange_Cloud
 * @version 1.0
 */
public class DigitalRunTrackManagerBeanTest extends TestCase {
    /**
     * Instance to test.
     */
    private DigitalRunTrackManagerBean target;

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(DigitalRunTrackManagerBeanTest.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        super.setUp();
        target = new DigitalRunTrackManagerBean();
        inject("trackDAO", new JPADigitalRunTrackDAO());
        inject("trackTypeDAO", new JPADigitalRunTrackTypeDAO());
        inject("trackStatusDAO", new JPADigitalRunTrackStatusDAO());
        inject("pointsCalculatorDAO", new JPADigitalRunPointsCalculatorDAO());
        inject("projectTypeDAO", new JPADigitalRunProjectTypeDAO());
        SessionContext context = EasyMock.createMock(SessionContext.class);
        context.setRollbackOnly();
        EasyMock.expectLastCall().once();
        inject("sessionContext", context);
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
     * Tests createTrack method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateTrackForNull() throws Exception {
        try {
            target.createTrack(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests updateTrack method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUpdateTrackForNull() throws Exception {
        try {
            target.updateTrack(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests removeTrack method for negative parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testRemoveTrackForNegative() throws Exception {
        try {
            target.removeTrack(-3);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests getTrack method for negative parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetTrackForNegative() throws Exception {
        try {
            target.getTrack(-3);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests searchTracks method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testSearchTracksForNull() throws Exception {
        try {
            target.searchTracks(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests addTrackProjectType method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testAddTrackProjectTypeForNull() throws Exception {
        try {
            target.addTrackProjectType(null, new ProjectType());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests removeTrackProjectType method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testRemoveTrackProjectTypeForNull() throws Exception {
        try {
            target.removeTrackProjectType(new Track(), null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createTrackType method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateTrackTypeForNull() throws Exception {
        try {
            target.createTrackType(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests updateTrackType method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUpdateTrackTypeForNull() throws Exception {
        try {
            target.updateTrackType(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests removeTrackType method for negative parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testRemoveTrackTypeForNegative() throws Exception {
        try {
            target.removeTrackType(-3);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests getTrackType method for negative parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetTrackTypeForNegative() throws Exception {
        try {
            target.getTrackType(-3);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createTrackStatus method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateTrackStatusForNull() throws Exception {
        try {
            target.createTrackStatus(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests updateTrackStatus method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUpdateTrackStatusForNull() throws Exception {
        try {
            target.updateTrackStatus(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests removeTrackStatus method for negative parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testRemoveTrackStatusForNegative() throws Exception {
        try {
            target.removeTrackStatus(-3);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests getTrackStatus method for negative parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetTrackStatusForNegative() throws Exception {
        try {
            target.getTrackStatus(-3);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createPointsCalculator method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreatePointsCalculatorForNull() throws Exception {
        try {
            target.createPointsCalculator(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests updatePointsCalculator method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUpdatePointsCalculatorForNull() throws Exception {
        try {
            target.updatePointsCalculator(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests removePointsCalculator method for negative parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testRemovePointsCalculatorForNegative() throws Exception {
        try {
            target.removePointsCalculator(-3);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests getPointsCalculator method for negative parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetPointsCalculatorForNegative() throws Exception {
        try {
            target.getPointsCalculator(-3);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests getProjectType method for negative parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetProjectTypeForNegative() throws Exception {
        try {
            target.getProjectType(-3);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Injects property into the tested bean.
     *
     * @param name name of the property
     * @param value value to inject
     * @throws Exception if some error occurs
     */
    private void inject(String name, Object value) throws Exception {
        Field field = DigitalRunTrackManagerBean.class.getDeclaredField(name);
        field.setAccessible(true);
        field.set(target, value);
    }
}