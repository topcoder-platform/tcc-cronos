/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.direct;

import java.io.Serializable;

/**
 * <p>
 * This class is a container for contest prize data. It is a simple JavaBean (POJO) that provides getters and setters
 * for all private attributes and performs no validation in the setters.
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class ContestPrize implements Serializable {

    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = -9017854342854466575L;

    /**
     * <p>
     * The ID of the contest. Can be any value. Has getter and setter.
     * </p>
     */
    private long contestId;

    /**
     * <p>
     * The flag indicating whether the contest is studio contest or software competition. Can be any value. Has getter
     * and setter.
     * </p>
     */
    private boolean studio;

    /**
     * <p>
     * The contest prizes for the winning submissions. The X-th element in the array represents the prize for the
     * submission winning the (X+1)-th place. Can be any value. Has getter and setter.
     * </p>
     */
    private double[] contestPrizes;

    /**
     * <p>
     * The milestone prizes for the winning submissions. The X-the element of the array represents the prize for the
     * submission winning the (X+1)-th place. Can be any value. Has getter and setter.
     * </p>
     */
    private double[] milestonePrizes;

    /**
     * <p>
     * The flag indicating whether all the prizes in the milestonePrizes must be equal. Can be any value. Has getter
     * and setter.
     * </p>
     */
    private boolean equalMilestonePrize;

    /**
     * <p>
     * Creates an instance of ContestPrize.
     * </p>
     */
    public ContestPrize() {
        // Do nothing.
    }

    /**
     * <p>
     * Retrieves the ID of the contest.
     * </p>
     *
     * @return the ID of the contest
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * <p>
     * Sets the ID of the contest.
     * </p>
     *
     * @param contestId
     *            the ID of the contest
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * <p>
     * Retrieves the flag indicating whether the contest is studio contest or software competition.
     * </p>
     *
     * @return the flag indicating whether the contest is studio contest or software competition
     */
    public boolean isStudio() {
        return studio;
    }

    /**
     * <p>
     * Sets the flag indicating whether the contest is studio contest or software competition.
     * </p>
     *
     * @param studio
     *            the flag indicating whether the contest is studio contest or software competition
     */
    public void setStudio(boolean studio) {
        this.studio = studio;
    }

    /**
     * <p>
     * Retrieves the contest prizes for the winning submissions.
     * </p>
     *
     * @return the contest prizes for the winning submissions
     */
    public double[] getContestPrizes() {
        return contestPrizes;
    }

    /**
     * <p>
     * Sets the contest prizes for the winning submissions.
     * </p>
     *
     * @param contestPrizes
     *            the contest prizes for the winning submissions
     */
    public void setContestPrizes(double[] contestPrizes) {
        this.contestPrizes = contestPrizes;
    }

    /**
     * <p>
     * Retrieves the milestone prizes for the winning submissions.
     * </p>
     *
     * @return the milestone prizes for the winning submissions
     */
    public double[] getMilestonePrizes() {
        return milestonePrizes;
    }

    /**
     * <p>
     * Sets the milestone prizes for the winning submissions.
     * </p>
     *
     * @param milestonePrizes
     *            the milestone prizes for the winning submissions
     */
    public void setMilestonePrizes(double[] milestonePrizes) {
        this.milestonePrizes = milestonePrizes;
    }

    /**
     * <p>
     * Retrieves the flag indicating whether all the prizes in the milestonePrizes must be equal.
     * </p>
     *
     * @return the flag indicating whether all the prizes in the milestonePrizes must be equal
     */
    public boolean isEqualMilestonePrize() {
        return equalMilestonePrize;
    }

    /**
     * <p>
     * Sets the flag indicating whether all the prizes in the milestonePrizes must be equal.
     * </p>
     *
     * @param equalMilestonePrize
     *            the flag indicating whether all the prizes in the milestonePrizes must be equal
     */
    public void setEqualMilestonePrize(boolean equalMilestonePrize) {
        this.equalMilestonePrize = equalMilestonePrize;
    }
}
