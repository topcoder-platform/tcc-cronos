/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

import com.topcoder.service.project.Competition;
import com.topcoder.service.project.SoftwareCompetition;

/**
 * <p>
 * Represents a type of Result that also provides the competition just created.
 * Returned in the createCompetition method of the service.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class is mutable and not thread-safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class CreateCompetitonResult extends Result {
    /**
     * <p>
     * Represents the serial version unique id.
     * </p>
     */
    private static final long serialVersionUID = -237938201768518400L;

    /**
     * <p>
     * Represents the created competition.
     * </p>
     * <p>
     * It is set in the setter. It can be retrieved in the getter
     * </p>
     */
    private Competition competition;

    /**
     * <p>
     * Represents the created SoftwareCompetition.
     * </p>
     * <p>
     * It is set in the setter. It can be retrieved in the getter
     * </p>
     */
    private SoftwareCompetition softwareCompetition;


    /**
     * <p>
     * Default empty constructor.
     * </p>
     */
    public CreateCompetitonResult() {
    }

    /**
     * <p>
     * Gets the created competition.
     * </p>
     *
     * @return the created competition
     */
    public Competition getCompetition() {
        return competition;
    }

    /**
     * <p>
     * Sets the created competition.
     * </p>
     *
     * @param competition
     *            the created competition
     */
    public void setCompetition(Competition competition) {
        this.competition = competition;
    }


    /**
     * <p>
     * Gets the created competition.
     * </p>
     *
     * @return the created competition
     */
    public SoftwareCompetition getSoftwareCompetition() {
        return softwareCompetition;
    }

    /**
     * <p>
     * Sets the created competition.
     * </p>
     *
     * @param competition
     *            the created competition
     */
    public void setSoftwareCompetition(SoftwareCompetition softwareCompetition) {
        this.softwareCompetition = softwareCompetition;
    }
}
