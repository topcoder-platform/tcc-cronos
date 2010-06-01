/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

import com.topcoder.service.user.User;

/**
 * This class represents a Liquid user entity which extends the User entity in user service
 * component.
 * 
 * @author hohosky
 * @version 1.0
 */
public class LiquidUser extends User {
    /**
     * Flag value indicates whether the user is icCompetitor.
     */
    private boolean lcCompetitor;

    /**
     * Gets whether the user is a LC Competitor.
     * 
     * @return the boolean flag to tell whether the user is a LC Competitor.
     */
    public boolean isLcCompetitor() {
        return lcCompetitor;
    }

    /**
     * Sets the flag of IcCompetitor for this user.
     * 
     * @param icCompetitor the IcCompetitor boolean flag.
     */
    public void setLcCompetitor(boolean lcCompetitor) {
        this.lcCompetitor = lcCompetitor;
    }
}
