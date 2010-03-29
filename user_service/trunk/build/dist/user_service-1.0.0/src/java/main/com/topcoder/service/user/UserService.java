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
 * <p>
 * Updated for Jira and Confluence User Sync Widget 1.0
 *  - Moved the methods that existed in user_sync_service component's UserService.
 * </p>
 *
 * @author snow01, TCSASSEMBLER
 * @since Cockpit Release Assembly for Receipts
 * @version 1.0
 */
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

    /**
     * <p>
     * This method retrieve the email address for given user handle.
     * </p>
     *
     * @param userHandle
     *            user handle to look for
     *
     * @return the email address
     *
     * @throws IllegalArgumentWSException
     *             if the argument is invalid
     * @throws UserServiceException
     *             if any error occurs when getting user details.
     * @since Jira & Confluence User Sync Service
     */
    public String getEmailAddress(String userHandle) throws UserServiceException;

    /**
     * <p>
     * This method retrieve the user id for given user handle.
     * </p>
     *
     * @param userHandle
     *            user handle to look for
     *
     * @return user id
     *
     * @throws IllegalArgumentWSException
     *             if the argument is invalid
     * @throws UserServiceException
     *             if any error occurs when getting user details
     */
    public long getUserId(String userHandle) throws UserServiceException;

    /**
     * <p>
     * This method returns true if given user handle is admin otherwise it returns false.
     * </p>
     *
     * @param userHandle
     *            user handle to look for
     *
     * @return returns true if given user handle is admin otherwise it returns false.
     *
     * @throws UserServiceException
     *             if any error occurs when getting user details.
     * @since Jira & Confluence User Sync Service
     */
    public boolean isAdmin(String userHandle) throws UserServiceException;

    /**
     * <p>
     * This method retrieve the user handle for given user id.
     * </p>
     *
     * @param userId
     *            user id to look for
     *
     * @return user handle
     *
     * @throws IllegalArgumentWSException
     *             if the argument is invalid
     * @throws UserServiceException
     *             if any error occurs when getting user details
     */
    public String getUserHandle(long userId) throws UserServiceException;
}
