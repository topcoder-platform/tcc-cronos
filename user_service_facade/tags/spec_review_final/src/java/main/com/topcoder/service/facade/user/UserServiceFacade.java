/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.user;

import javax.jws.WebService;

/**
 * <p>
 * This interface defines the Jira & Confluence back end service.
 * 
 * This service provides mechanism to create users in both Jira & Confluence.
 * </p>
 * 
 * @author snow01
 * 
 * @since Jira & Confluence User Service
 * @version 1.0
 */
@WebService(name = "UserServiceFacade")
public interface UserServiceFacade {
    /**
     * <p>
     * Creates Jira User (if does not exist already) and gets the email address of it from the Jira Service.
     * 
     * Implementation should create the Jira user if the user does not exist already.
     * </p>
     * 
     * @param handle
     *            the user handle for which to retrieve the email address from Jira Service.
     * @return the email address of the Jira user for the given handle.
     * @throws UserServiceException
     *             if any error occurs when getting user details.
     */
    public String getJiraUser(String handle) throws UserServiceFacadeException;

    /**
     * <p>
     * Creates Confluence User (if does not exist already) and gets the email address of it from the Confluence Service.
     * 
     * Implementation should create the Confluence user if the user does not exist already.
     * </p>
     * 
     * @param handle
     *            the user handle for which to retrieve the email address from Confluence Service.
     * @return the email address of the Confluence user for the given handle.
     * @throws UserServiceException
     *             if any error occurs when getting user details.
     */
    public String getConfluenceUser(String handle) throws UserServiceFacadeException;
}
