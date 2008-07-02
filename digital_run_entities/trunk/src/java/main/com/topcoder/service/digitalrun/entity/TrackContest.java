/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.digitalrun.entity;

/**
 * <p>
 * The <code>TrackContest</code> entity. Plus the attributes defined in its base entity, it holds the attributes
 * track contest type, track contest result calculator, and track.
 * </p>
 *
 * <p>
 * Thread Safety: It's mutable and not thread safe.
 * </p>
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class TrackContest extends BaseEntity {

    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = 7084142848697924120L;

    /**
     * Represents the track contest type attribute of the TrackContest entity. It should be non-null after set.
     */
    private TrackContestType trackContestType;

    /**
     * Represents the track contest result calculator attribute of the TrackContest entity. It should be non-null
     * after set.
     */
    private TrackContestResultCalculator trackContestResultCalculator;

    /**
     * Represents the track attribute of the TrackContest entity. It should be non-null after set.
     */
    private Track track;

    /**
     * Creates the instance. Empty constructor.
     */
    public TrackContest() {
        // empty
    }

    /**
     * Gets track contest type.
     *
     * @return the track contest type
     */
    public TrackContestType getTrackContestType() {
        return trackContestType;
    }

    /**
     * Sets track contest type.
     *
     * @param trackContestType
     *            the track contest type
     * @throws IllegalArgumentException
     *             if the argument is <code>null</code>
     */
    public void setTrackContestType(TrackContestType trackContestType) {
        Helper.checkNull(trackContestType, "trackContestType");
        this.trackContestType = trackContestType;
    }

    /**
     * Gets track contest result calculator.
     *
     * @return the track contest result calculator
     */
    public TrackContestResultCalculator getTrackContestResultCalculator() {
        return trackContestResultCalculator;
    }

    /**
     * Sets track contest result calculator.
     *
     * @param trackContestResultCalculator
     *            the track contest result calculator
     * @throws IllegalArgumentException
     *             if the argument is <code>null</code>
     */
    public void setTrackContestResultCalculator(TrackContestResultCalculator trackContestResultCalculator) {
        Helper.checkNull(trackContestResultCalculator, "trackContestResultCalculator");
        this.trackContestResultCalculator = trackContestResultCalculator;
    }

    /**
     * Gets track.
     *
     * @return the track
     */
    public Track getTrack() {
        return track;
    }

    /**
     * Sets track.
     *
     * @param track
     *            the track
     * @throws IllegalArgumentException
     *             if the argument is <code>null</code>
     */
    public void setTrack(Track track) {
        Helper.checkNull(track, "track");
        this.track = track;
    }
}
