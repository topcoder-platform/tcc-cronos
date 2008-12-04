/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import com.topcoder.confluence.entities.ComponentType;
import com.topcoder.confluence.entities.ConfluenceAssetType;
import com.topcoder.confluence.entities.ConfluenceCatalog;
import com.topcoder.confluence.entities.ConfluencePageCreationResult;
import com.topcoder.confluence.entities.Page;

/**
 * <p>
 * The business interface that defines all methods for accessing the Confluence Management component. It basically
 * has the same API as the ConfluenceManager interface, and is meant to be used as a pass-through.
 * </p>
 * <p>
 * <b>Thread Safety:</b> The container will assume all responsibility for thread-safety.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@WebService
public interface ConfluenceManagementService {

    /**
     * <p>
     * Performs the login of the given user with the given password to Confluence.
     * </p>
     *
     * @param username
     *            the user name that should be logged in Confluence. Should not be null or empty String
     * @param password
     *            the password for the given user name that should be logged in Confluence. Should not be null or
     *            empty String
     * @return the created token for the given userName and password. This token is then passed to the subsequent
     *         calls into Confluence to verify if the user has access to the specific areas being manipulated.
     *         Should not be null but can be empty String represents anonymous user
     * @throws IllegalArgumentException
     *             if the userName or password are null or empty Strings
     * @throws ConfluenceManagementServiceConnectionException
     *             if an there is an issue while attempting to reestablish the connection to Confluence
     * @throws ConfluenceManagementServiceAuthenticationFailedException
     *             if the user or password are invalid
     * @throws ConfluenceManagementServiceException
     *             if any error occurs while performing this operation
     */
    @WebMethod
    public String login(String username, String password) throws ConfluenceManagementServiceException;

    /**
     * <p>
     * Purpose performs the log out from the Confluence using the given token.
     * </p>
     *
     * @param token
     *            the token to be used to log out from the Confluence. This token is created by
     *            login(userName,password):String method and is used to verify if the user has access to the
     *            specific areas being manipulated. Should not be null but can be empty String represents anonymous
     *            user
     * @throws IllegalArgumentException
     *             if the token is null
     * @throws ConfluenceManagementServiceConnectionException
     *             if an there is an issue while attempting to reestablish the connection to Confluence
     * @throws ConfluenceManagementServiceNotAuthorizedException
     *             if a user attempts an unauthorized call to Confluence (the token used to authenticate is invalid
     *             or the user does not have access to the specific areas being manipulated)
     * @throws ConfluenceManagementServiceException
     *             if any error occurs while performing this operation
     */
    @WebMethod
    public void logout(String token) throws ConfluenceManagementServiceException;

    /**
     * <p>
     * Creates a confluence page with the given name, version and asset type. Uses the token to indicate it is
     * logged in.
     * </p>
     *
     * @param pageName
     *            the name of the new page
     * @param token
     *            the token to be used to create a Confluence page
     * @param assetType
     *            the confluence asset type use to create a new page
     * @param version
     *            the version of the new page
     * @param catalog
     *            the confluence catalog to create a new page
     * @param componentType
     *            the confluence componentType to create a new page
     * @return the confluence page creation result. Should not be null
     * @throws IllegalArgumentException
     *             if any argument is null or any String argument is empty except token
     * @throws ConfluenceManagementServiceConnectionException
     *             if an there is an issue while attempting to reestablish the connection to Confluence
     * @throws ConfluenceManagementServiceNotAuthorizedException
     *             if a user attempts an unauthorized call to Confluence (the token used to authenticate is invalid
     *             or the user does not have access to the specific areas being manipulated)
     * @throws ConfluenceManagementServiceException
     *             if any error occurs while performing this operation
     */
    @WebMethod(operationName = "createPageWithComponentType")
    @RequestWrapper(className = "createPageWithComponentType")
    @ResponseWrapper(className = "createPageWithComponentTypeResponse")
    public ConfluencePageCreationResult createPage(String token, String pageName, String version,
        ConfluenceAssetType assetType, ConfluenceCatalog catalog, ComponentType componentType)
        throws ConfluenceManagementServiceException;

    /**
     * <p>
     * Creates a confluence page with the given name, version, asset type, catalog, and application code Uses the
     * token to indicate it is logged in.
     * </p>
     *
     * @param token
     *            the token to be used to create a Confluence page
     * @param pageName
     *            the name of the new page
     * @param version
     *            the version of the new page
     * @param assetType
     *            the confluence asset type use to create a new page
     * @param catalog
     *            the confluence catalog to which the page is put
     * @param applicationCode
     *            the application code
     * @return the confluence page creation result. Should not be null
     * @throws IllegalArgumentException
     *             if any argument is null or any String argument is empty except token
     * @throws ConfluenceManagementServiceConnectionException
     *             if an there is an issue while attempting to reestablish the connection to Confluence
     * @throws ConfluenceManagementServiceNotAuthorizedException
     *             if a user attempts an unauthorized call to Confluence (the token used to authenticate is invalid
     *             or the user does not have access to the specific areas being manipulated)
     * @throws ConfluenceManagementServiceException
     *             if any error occurs while performing this operation
     */
    @WebMethod(operationName = "createPageWithApplicationCode")
    @RequestWrapper(className = "createPageWithApplicationCode")
    @ResponseWrapper(className = "createPageWithApplicationCodeResponse")
    public ConfluencePageCreationResult createPage(String token, String pageName, String version,
        ConfluenceAssetType assetType, ConfluenceCatalog catalog, String applicationCode)
        throws ConfluenceManagementServiceException;

    /**
     * <p>
     * Creates a confluence page with the given info Uses the token to indicate it is logged in.
     * </p>
     *
     * @param token
     *            the token to be used to create a Confluence page
     * @param page
     *            the page entity with the info to create
     * @return the confluence page creation result. Should not be null
     * @throws IllegalArgumentException
     *             if any argument is null or any String argument is empty except token
     * @throws ConfluenceManagementServiceConnectionException
     *             if an there is an issue while attempting to reestablish the connection to Confluence
     * @throws ConfluenceManagementServiceNotAuthorizedException
     *             if a user attempts an unauthorized call to Confluence (the token used to authenticate is invalid
     *             or the user does not have access to the specific areas being manipulated)
     * @throws ConfluenceManagementServiceException
     *             if any error occurs while performing this operation
     */
    @WebMethod(operationName = "createPageWithPage")
    @RequestWrapper(className = "createPageWithPage")
    @ResponseWrapper(className = "createPageWithPageResponse")
    public ConfluencePageCreationResult createPage(String token, Page page)
        throws ConfluenceManagementServiceException;

    /**
     * <p>
     * Retrieves a Confluence page with the given name, version and asset type. Uses the token to indicate it is
     * logged in.
     * </p>
     *
     * @param token
     *            the token to be used to retrieve a Confluence page
     * @param pageName
     *            the name of the existing page
     * @param version
     *            the version of the existing page
     * @param assetType
     *            the confluence asset type of the existing page
     * @param catalog
     *            the confluence catalog of the existing page
     * @param componentType
     *            the confluence componentType of the existing page
     * @return the confluence page. Should not be null
     * @throws IllegalArgumentException
     *             if any argument is null or any String argument is empty except token
     * @throws ConfluenceManagementServiceConnectionException
     *             if an there is an issue while attempting to reestablish the connection to Confluence
     * @throws ConfluenceManagementServiceNotAuthorizedException
     *             if a user attempts an unauthorized call to Confluence (the token used to authenticate is invalid
     *             or the user does not have access to the specific areas being manipulated)
     * @throws ConfluenceManagementServiceException
     *             if any error occurs while performing this operation
     */
    @WebMethod(operationName = "retrievePageWithComponentType")
    @RequestWrapper(className = "retrievePageWithComponentType")
    @ResponseWrapper(className = "retrievePageWithComponentTypeResponse")
    public Page retrievePage(String token, String pageName, String version, ConfluenceAssetType assetType,
        ConfluenceCatalog catalog, ComponentType componentType) throws ConfluenceManagementServiceException;

    /**
     * <p>
     * Retrieves a Confluence page with the given name, version, asset type, catalog, and application code Uses the
     * token to indicate it is logged in.
     * </p>
     *
     * @param token
     *            the token to be used to retrieve a Confluence page
     * @param pageName
     *            the name of the existing page
     * @param version
     *            the version of the existing page
     * @param assetType
     *            the confluence asset type of the existing page
     * @param catalog
     *            the confluence catalog of the existing page
     * @param applicationCode
     *            the application code of the existing page
     * @return the confluence page. Should not be null
     * @throws IllegalArgumentException
     *             if any argument is null or any String argument is empty except token
     * @throws ConfluenceManagementServiceConnectionException
     *             if an there is an issue while attempting to reestablish the connection to Confluence
     * @throws ConfluenceManagementServiceNotAuthorizedException
     *             if a user attempts an unauthorized call to Confluence (the token used to authenticate is invalid
     *             or the user does not have access to the specific areas being manipulated)
     * @throws ConfluenceManagementServiceException
     *             if any error occurs while performing this operation
     */
    @WebMethod(operationName = "retrievePageWithApplicationCode")
    @RequestWrapper(className = "retrievePageWithApplicationCode")
    @ResponseWrapper(className = "retrievePageWithApplicationCodeResponse")
    public Page retrievePage(String token, String pageName, String version, ConfluenceAssetType assetType,
        ConfluenceCatalog catalog, String applicationCode) throws ConfluenceManagementServiceException;
}