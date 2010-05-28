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
    private boolean icCompetitor;

    /**
     * Gets whether the user is a IcCompetitor.
     * 
     * @return the boolean flag to tell whether the user is a IcCompetitor.
     */
    public boolean isIcCompetitor() {
        return icCompetitor;
    }

    /**
     * Sets the flag of IcCompetitor for this user.
     * 
     * @param icCompetitor the IcCompetitor boolean flag.
     */
    public void setIcCompetitor(boolean icCompetitor) {
        this.icCompetitor = icCompetitor;
    }
}
