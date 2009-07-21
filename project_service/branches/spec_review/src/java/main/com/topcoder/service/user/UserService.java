/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.user;

import javax.ejb.Remote;

/**
 * <p>
 * It provides various getters on user object.
 * </p>
 * 
 * @author snow01
 * @since Cockpit Release Assembly for Receipts
 * @version 1.0
 */
@Remote
public interface UserService {

    /**
     * <p>
     * This method retrieve the email address for given user id.
     * </p>
     * 
     * @param userid
     *            user id to look for
     * 
     * @return the email address
     * 
     * @throws IllegalArgumentWSException
     *             if the argument is invalid
     * @throws UserServiceException
     *             if any error occurs when getting permissions.
     * 
     * @since Cockpit Release Assembly for Receipts
     */
    public String getEmailAddress(long userid) throws UserServiceException;
}
