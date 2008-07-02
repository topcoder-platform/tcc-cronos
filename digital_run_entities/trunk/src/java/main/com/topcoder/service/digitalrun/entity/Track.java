/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.digitalrun.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * <p>
 * The <code>Track</code> entity. Plus the attributes defined in its base entity, it holds the attributes track
 * type, points calculator, track status, start date, end date, and project types, etc.
 * </p>
 *
 * <p>
 * Thread Safety: It's mutable and not thread safe.
 * </p>
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class Track extends BaseEntity {

    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -7219295902491382643L;

    /**
     * Represents the track type attribute of the Track entity. It should be non-null after set.
     */
    private TrackType trackType;

    /**
     * Represents the track status attribute of the Track entity. It should be non-null after set.
     */
    private TrackStatus trackStatus;

    /**
     * Represents the start date attribute of the Track entity. It should be non-null after set.
     */
    private Date startDate;

    /**
     * Represents the end date attribute of the Track entity. It should be non-null after set.
     */
    private Date endDate;

    /**
     * Represents the project types attribute of the Track entity. It should be non-null and non-empty collection
     * after set and must not contain null element.
     */
    private Collection<ProjectType> projectTypes;

    /**
     * Represents the points calculator attribute of the Track entity. It should be non-null after set.
     */
    private PointsCalculator pointsCalculator;

    /**
     * Represents the digital run points attribute of the Track entity. It should be non-null and non-empty
     * collection after set and must not contain null element.
     */
    private Collection<DigitalRunPoints> digitalRunPoints;

    /**
     * Represents the track contests attribute of the Track entity. It should be non-null and non-empty collection
     * after set and must not contain null element.
     */
    private Collection<TrackContest> trackContests;

    /**
     * Creates the instance. Empty constructor.
     */
    public Track() {
        // empty
    }

    /**
     * Gets track type.
     *
     * @return the track type
     */
    public TrackType getTrackType() {
        return trackType;
    }

    /**
     * Sets track type.
     *
     * @param trackType
     *            the track type
     * @throws IllegalArgumentException
     *             if the argument is <code>null</code>
     */
    public void setTrackType(TrackType trackType) {
        Helper.checkNull(trackType, "trackType");
        this.trackType = trackType;
    }

    /**
     * Gets track status.
     *
     * @return the track status
     */
    public TrackStatus getTrackStatus() {
        return trackStatus;
    }

    /**
     * Sets track status.
     *
     * @param trackStatus
     *            the track status
     * @throws IllegalArgumentException
     *             if the argument is <code>null</code>
     */
    public void setTrackStatus(TrackStatus trackStatus) {
        Helper.checkNull(trackStatus, "trackStatus");
        this.trackStatus = trackStatus;
    }

    /**
     * Gets start date.
     *
     * @return the start date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets start date.
     *
     * @param startDate
     *            the start date
     * @throws IllegalArgumentException
     *             if the argument is <code>null</code>
     */
    public void setStartDate(Date startDate) {
        Helper.checkNull(startDate, "startDate");
        this.startDate = startDate;
    }

    /**
     * Gets end date.
     *
     * @return the end date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets end date.
     *
     * @param endDate
     *            the end date
     * @throws IllegalArgumentException
     *             if the argument is <code>null</code>
     */
    public void setEndDate(Date endDate) {
        Helper.checkNull(endDate, "endDate");
        this.endDate = endDate;
    }

    /**
     * Gets project types as a shallow copy of the inner variable. If the inner is null, return empty collection.
     *
     * @return the project types
     */
    public Collection<ProjectType> getProjectTypes() {
        return (projectTypes != null) ? new ArrayList<ProjectType>(projectTypes) : new ArrayList<ProjectType>();
    }

    /**
     * Sets project types. Makes a shallow copy of the collection and set the copy on the inner variable.
     *
     * @param projectTypes
     *            the project types
     * @throws IllegalArgumentException
     *             if the argument is <code>null</code> or contains <code>null</code>
     */
    public void setProjectTypes(Collection<ProjectType> projectTypes) {
        Helper.checkCollection(projectTypes, "projectTypes");
        this.projectTypes = new ArrayList<ProjectType>(projectTypes);
    }

    /**
     * Gets points calculator.
     *
     * @return the points calculator
     */
    public PointsCalculator getPointsCalculator() {
        return pointsCalculator;
    }

    /**
     * Sets points calculator.
     *
     * @param pointsCalculator
     *            the points calculator
     * @throws IllegalArgumentException
     *             if the argument is <code>null</code>
     */
    public void setPointsCalculator(PointsCalculator pointsCalculator) {
        Helper.checkNull(pointsCalculator, "pointsCalculator");
        this.pointsCalculator = pointsCalculator;
    }

    /**
     * Gets digital run points. Returns a shallow copy of the inner variable. If the inner is null, return empty
     * collection.
     *
     * @return the digital run points
     */
    public Collection<DigitalRunPoints> getDigitalRunPoints() {
        return (digitalRunPoints != null) ? new ArrayList<DigitalRunPoints>(digitalRunPoints)
                : new ArrayList<DigitalRunPoints>();
    }

    /**
     * Sets digital run points. Makes a shallow copy of the collection and set the copy on the inner variable.
     *
     * @param digitalRunPoints
     *            the digital run points
     * @throws IllegalArgumentException
     *             if the argument is <code>null</code> or contains <code>null</code>
     */
    public void setDigitalRunPoints(Collection<DigitalRunPoints> digitalRunPoints) {
        Helper.checkCollection(digitalRunPoints, "digitalRunPoints");
        this.digitalRunPoints = new ArrayList<DigitalRunPoints>(digitalRunPoints);
    }

    /**
     * Gets track contests. Returns a shallow copy of the inner variable. If the inner is null, return empty
     * collection.
     *
     * @return the track contests
     */
    public Collection<TrackContest> getTrackContests() {
        return (trackContests != null) ? new ArrayList<TrackContest>(trackContests)
                : new ArrayList<TrackContest>();
    }

    /**
     * Sets track contests. Makes a shallow copy of the collection and set the copy on the inner variable.
     *
     * @param trackContests
     *            the track contests
     * @throws IllegalArgumentException
     *             if the argument is <code>null</code> or contains <code>null</code>
     */
    public void setTrackContests(Collection<TrackContest> trackContests) {
        Helper.checkCollection(trackContests, "trackContests");
        this.trackContests = new ArrayList<TrackContest>(trackContests);
    }
}
