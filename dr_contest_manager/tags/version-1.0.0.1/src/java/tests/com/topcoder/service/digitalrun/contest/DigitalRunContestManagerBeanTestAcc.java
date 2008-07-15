/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest;

import com.topcoder.service.digitalrun.entity.PointsCalculator;
import com.topcoder.service.digitalrun.entity.Track;
import com.topcoder.service.digitalrun.entity.TrackContest;
import com.topcoder.service.digitalrun.entity.TrackContestResultCalculator;
import com.topcoder.service.digitalrun.entity.TrackContestType;
import com.topcoder.service.digitalrun.entity.TrackStatus;
import com.topcoder.service.digitalrun.entity.TrackType;

/**
 * <p>
 * Accuracy tests for <code>DigitalRunContestManagerBean</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DigitalRunContestManagerBeanTestAcc extends BaseTestCase {

    /**
     * <p>
     * Create/update/retrieve/remove <code>TrackContest</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testTrackContest_Acc() throws Exception {

        PointsCalculator pointsCalculator = createPointsCalculator();
        TrackStatus trackStatus = createTrackStatus();
        TrackType trackType = createTrackType();
        Track track = createTrack(pointsCalculator, trackStatus, trackType);

        persist(pointsCalculator);
        persist(trackStatus);
        persist(trackType);
        persist(track);

        TrackContestResultCalculator trackContestResultCalculator = createTrackContestResultCalculator();
        TrackContestType trackContestType = createTrackContestType();

        persist(trackContestResultCalculator);
        persist(trackContestType);

        TrackContest entity = createTrackContest(track, trackContestResultCalculator, trackContestType);

        // Create TrackContest
        TrackContest created = getDigitalRunContestManagerRemoteEJB().createTrackContest(entity);

        assertTrue("id should be set", entity.getId() != created.getId());

        // Fetch TrackContest
        TrackContest found = getDigitalRunContestManagerRemoteEJB().getTrackContest(created.getId());

        found.setDescription("new description");

        // Update TrackContest
        getDigitalRunContestManagerRemoteEJB().updateTrackContest(found);

        // Fetch TrackContest again
        found = getDigitalRunContestManagerRemoteEJB().getTrackContest(created.getId());
        assertEquals("Description should be updated", "new description", found.getDescription());

        // Remove TrackContest
        getDigitalRunContestManagerRemoteEJB().removeTrackContest(created.getId());

        // Fetch TrackContest again
        assertNull("TrackContest should be removed",
            getDigitalRunContestManagerRemoteEJB().getTrackContest(created.getId()));
/*
        assertEquals(0,
            getDigitalRunContestManagerRemoteEJB().searchTrackContests(
                ContestFilterFactory.createContestIdsInFilter(new long[]{1L})).size());
*/
        delete(trackContestResultCalculator);
        delete(trackContestType);
        delete(track);
        delete(pointsCalculator);
        delete(trackStatus);
        delete(trackType);
    }

    /**
     * <p>
     * Create/update/retrieve/remove <code>TrackContestType</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testTrackContestType_Acc() throws Exception {
        TrackContestType entity = createTrackContestType();

        // Create TrackContestType
        TrackContestType created = getDigitalRunContestManagerRemoteEJB().createTrackContestType(entity);

        assertTrue("id should be set", entity.getId() != created.getId());

        // Fetch TrackContestType
        TrackContestType found = getDigitalRunContestManagerRemoteEJB().getTrackContestType(created.getId());

        found.setDescription("new description");

        // Update TrackContestType
        getDigitalRunContestManagerRemoteEJB().updateTrackContestType(found);

        // Fetch TrackContestType again
        found = getDigitalRunContestManagerRemoteEJB().getTrackContestType(created.getId());
        assertEquals("Description should be updated", "new description", found.getDescription());

        // Fetch all TrackContestTypes
        assertEquals("There should be 1 TrackContestType", 1,
            getDigitalRunContestManagerRemoteEJB().getAllTrackContestTypes().size());

        // Remove TrackContestType
        getDigitalRunContestManagerRemoteEJB().removeTrackContestType(created.getId());

        // Fetch TrackContestType again
        assertNull("TrackContestType should be removed",
            getDigitalRunContestManagerRemoteEJB().getTrackContestType(created.getId()));

        // Fetch all TrackContestTypes again
        assertEquals("There should be 0 TrackContestType", 0,
            getDigitalRunContestManagerRemoteEJB().getAllTrackContestTypes().size());

    }

    /**
     * <p>
     * Create/update/retrieve/remove <code>TrackContestResultCalculator</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testTrackContestResultCalculator_Acc() throws Exception {
        TrackContestResultCalculator entity = createTrackContestResultCalculator();

        // Create TrackContestResultCalculator
        TrackContestResultCalculator created = getDigitalRunContestManagerRemoteEJB().
            createTrackContestResultCalculator(entity);

        assertTrue("id should be set", entity.getId() != created.getId());

        // Fetch TrackContestResultCalculator
        TrackContestResultCalculator found = getDigitalRunContestManagerRemoteEJB().getTrackContestResultCalculator(
            created.getId());

        found.setDescription("new description");

        // Update TrackContestResultCalculator
        getDigitalRunContestManagerRemoteEJB().updateTrackContestResultCalculator(found);

        // Fetch TrackContestResultCalculator again
        found = getDigitalRunContestManagerRemoteEJB().getTrackContestResultCalculator(created.getId());
        assertEquals("Description should be updated", "new description", found.getDescription());

        // Fetch all TrackContestResultCalculators
        assertEquals("There should be 1 TrackContestResultCalculator", 1,
            getDigitalRunContestManagerRemoteEJB().getAllTrackContestResultCalculators().size());

        // Remove TrackContestResultCalculator
        getDigitalRunContestManagerRemoteEJB().removeTrackContestResultCalculator(created.getId());

        // Fetch TrackContestResultCalculator again
        assertNull("TrackContestResultCalculator should be removed",
            getDigitalRunContestManagerRemoteEJB().getTrackContestResultCalculator(created.getId()));

        // Fetch all TrackContestResultCalculators again
        assertEquals("There should be 0 TrackContestResultCalculator", 0,
            getDigitalRunContestManagerRemoteEJB().getAllTrackContestResultCalculators().size());
    }
}
