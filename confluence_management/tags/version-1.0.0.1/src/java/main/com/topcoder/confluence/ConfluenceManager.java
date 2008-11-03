/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence;

import com.topcoder.confluence.entities.ComponentType;
import com.topcoder.confluence.entities.ConfluenceAssetType;
import com.topcoder.confluence.entities.ConfluenceCatalog;
import com.topcoder.confluence.entities.ConfluencePageCreationResult;
import com.topcoder.confluence.entities.Page;

/**
 * <p>
 * This interface provides common operations to work with Confluence, such as login, create and retrieve pages
 * using different settings, log out.
 * </p>
 * <p>
 * <b>Thread Safety:</b> implementations need to be thread-safe.
 * </p>
 *
 * @author Margarita, TCSDEVELOPER
 * @version 1.0
 */
public interface ConfluenceManager {

    /**
     * <p>
     * This method is used to perform authorization in Confluence. Before making an API call, the caller is
     * expected to login with a user name and password, returning a token. That same token is then passed to the
     * subsequent calls into Confluence. This token is used to verify the user has access to the specific areas
     * being manipulated.
     * </p>
     *
     * @param userName
     *            the user name. cannot be null or empty
     * @param password
     *            the user password. cannot be null or empty
     * @return the authorization token. an empty string as the token to be treated as being the anonymous user
     * @throws IllegalArgumentException
     *             if userName is null or empty, if password is null or empty
     * @throws ConfluenceConnectionException
     *             if connection wasn't established or was lost
     * @throws ConfluenceAuthenticationFailedException
     *             if AuthenticationFailedException was caught, wrap it and re-throw
     * @throws ConfluenceManagerException
     *             if any other exception was caught, wrap it and re-throw
     */
    public String login(String userName, String password) throws ConfluenceConnectionException,
        ConfluenceAuthenticationFailedException, ConfluenceManagerException;

    /**
     * <p>
     * This methods logs the user out (removes the token from the list of logged in tokens).
     * </p>
     *
     * @param token
     *            the authorization token obtained from login method. cannot be null, can be empty
     * @throws IllegalArgumentException
     *             if token is null.
     * @throws ConfluenceConnectionException
     *             if connection wasn't established or was lost
     * @throws ConfluenceNotAuthorizedException
     *             if user is not logged in
     * @throws ConfluenceManagerException
     *             if any other exception was caught, wrap it and re-throw
     */
    public void logout(String token) throws ConfluenceConnectionException, ConfluenceNotAuthorizedException,
        ConfluenceManagerException;

    /**
     * <p>
     * This method is used to create page with given parameters. When creating a page, the user provides the base
     * component name and a version. If the base component already exists, a new version page is added (with its
     * contents filled from a template), but if the base component page doesn't already exist, it is first created
     * (with its contents filled from a template), and then the version page is added under it. This method should
     * be used to create pages that don't require application code (everything except Application Specification).
     * </p>
     *
     * @param token
     *            the authorization token obtained from login method. cannot be null, can be empty
     * @param pageName
     *            the component name. cannot be null or empty. this string can have 2 formats - with spaces as
     *            delimiters ('My component') or with '+' as delimiters ('My+Component')
     * @param version
     *            the version of the page that will be created. cannot be null or empty string. should be valid
     *            non-negative integer number when parsed
     * @param assetType
     *            the confluence asset type of the page being created. cannot be null
     * @param catalog
     *            the catalog to represent (java/.net). cannot be null
     * @param componentType
     *            the type of the component (generic/custom). cannot be null
     * @return an object that stores result of this operation
     * @throws IllegalArgumentException
     *             if token is null, if pageName is null or empty, if version is null or empty, if assetType is
     *             null, if catalog is null, if componentType is null.
     * @throws ConfluenceConnectionException
     *             if connection wasn't established or was lost.
     * @throws ConfluenceNotAuthorizedException
     *             if user is not logged in.
     * @throws ConfluenceManagerException
     *             if any other exception was caught, wrap it and re-throw.
     */
    public ConfluencePageCreationResult createPage(String token, String pageName, String version,
        ConfluenceAssetType assetType, ConfluenceCatalog catalog, ComponentType componentType)
        throws ConfluenceConnectionException, ConfluenceNotAuthorizedException, ConfluenceManagerException;

    /**
     * <p>
     * This method is used to create page with given parameters. When creating a page, the user provides the base
     * component name and a version. If the base component already exists, a new version page is added (with its
     * contents filled from a template), but if the base component page doesn't already exist, it is first created
     * (with its contents filled from a template), and then the version page is added under it. This method should
     * be used to create pages that require application code (Application Specification).
     * </p>
     *
     * @param token
     *            the authorization token obtained from login method. cannot be null, can be empty
     * @param pageName
     *            the component name. cannot be null or empty. this string can have 2 formats - with spaces as
     *            delimiters ('My component') or with '+' as delimiters ('My+Component')
     * @param version
     *            the version of the page that will be created. cannot be null or empty string. should be valid
     *            non-negative integer number when parsed
     * @param assetType
     *            the confluence asset type of the page being created. cannot be null
     * @param catalog
     *            the catalog to represent (java/.net). cannot be null
     * @param applicationCode
     *            the application code. cannot be null or empty
     * @return an object that stores result of this operation
     * @throws IllegalArgumentException
     *             if token is null, if pageName is null or empty, if version is null or empty, if assetType is
     *             null, if catalog is null, if applicationCode is null or empty
     * @throws ConfluenceConnectionException
     *             if connection wasn't established or was lost
     * @throws ConfluenceNotAuthorizedException
     *             if user is not logged in
     * @throws ConfluenceManagerException
     *             if any other exception was caught, wrap it and re-throw
     */
    public ConfluencePageCreationResult createPage(String token, String pageName, String version,
        ConfluenceAssetType assetType, ConfluenceCatalog catalog, String applicationCode)
        throws ConfluenceConnectionException, ConfluenceNotAuthorizedException, ConfluenceManagerException;

    /**
     * <p>
     * This method is used to create page from the provided Page object, which already stores page parameters.
     * </p>
     *
     * @param token
     *            the authorization token obtained from login method. cannot be null, can be empty
     * @param page
     *            the Page object that stores parameters of the page being created. cannot be null. should have
     *            specified, at least, asset type, component type, confluence catalog, page name, version for
     *            components and additional field 'applicationCode' for
     *            assetType=ConfluenceAssetType.APPLICATION_SPECIFICATION
     * @return an object that stores result of this operation
     * @throws IllegalArgumentException
     *             if page is null or at least one of asset type, component type, confluence catalog, page name,
     *             version for components and additional field 'applicationCode' for
     *             assetType=ConfluenceAssetType.APPLICATION_SPECIFICATION is not specified
     * @throws ConfluenceConnectionException
     *             if connection wasn't established or was lost
     * @throws ConfluenceNotAuthorizedException
     *             if user is not logged in
     * @throws ConfluenceManagerException
     *             if any other exception was caught, wrap it and re-throw
     */
    public ConfluencePageCreationResult createPage(String token, Page page) throws ConfluenceConnectionException,
        ConfluenceNotAuthorizedException, ConfluenceManagerException;

    /**
     * <p>
     * This method is used to retrieve the page from the Confluence that does not need application code (everything
     * except Application Specification).
     * </p>
     *
     * @param token
     *            the authorization token obtained from login method. cannot be null, can be empty
     * @param pageName
     *            the component name. cannot be null or empty. this string can have 2 formats - with spaces as
     *            delimiters ('My component') or with '+' as delimiters ('My+Component')
     * @param version
     *            the version of the page that will be created. cannot be null or empty string. should be valid
     *            non-negative integer number when parsed
     * @param assetType
     *            the confluence asset type of the page being created. cannot be null
     * @param catalog
     *            the catalog to represent (java/.net). cannot be null
     * @param componentType
     *            the type of the component (generic/custom). cannot be null
     * @return the Page object containing retrieved remote page parameters and content
     * @throws IllegalArgumentException
     *             if token is null, if pageName is null or empty, if version is null or empty, if assetType is
     *             null, if catalog is null, if componentType is null
     * @throws ConfluenceConnectionException
     *             if connection wasn't established or was lost
     * @throws ConfluenceNotAuthorizedException
     *             if user is not logged in
     * @throws ConfluenceManagerException
     *             if any other exception was caught, wrap it and re-throw
     */
    public Page retrievePage(String token, String pageName, String version, ConfluenceAssetType assetType,
        ConfluenceCatalog catalog, ComponentType componentType) throws ConfluenceConnectionException,
        ConfluenceNotAuthorizedException, ConfluenceManagerException;

    /**
     * <p>
     * This method is used to retrieve the page from the Confluence that needs application code (Application
     * Specification).
     * </p>
     *
     * @param token
     *            the authorization token obtained from login method. cannot be null, can be empty
     * @param pageName
     *            the component name. cannot be null or empty. this string can have 2 formats - with spaces as
     *            delimiters ('My component') or with '+' as delimiters ('My+Component')
     * @param version
     *            the version of the page that will be created. cannot be null or empty string. should be valid
     *            non-negative integer number when parsed
     * @param assetType
     *            the confluence asset type of the page being created. cannot be null
     * @param catalog
     *            the catalog to represent (java/.net). cannot be null
     * @param applicationCode
     *            the application code. cannot be null or empty
     * @return the Page object containing retrieved remote page parameters and content
     * @throws IllegalArgumentException
     *             if token is null, if pageName is null or empty, if version is null or empty, if assetType is
     *             null, if catalog is null, if applicationCode is null or empty
     * @throws ConfluenceConnectionException
     *             if connection wasn't established or was lost
     * @throws ConfluenceNotAuthorizedException
     *             if user is not logged in
     * @throws ConfluenceManagerException
     *             if any other exception was caught, wrap it and re-throw
     */
    public Page retrievePage(String token, String pageName, String version, ConfluenceAssetType assetType,
        ConfluenceCatalog catalog, String applicationCode) throws ConfluenceConnectionException,
        ConfluenceNotAuthorizedException, ConfluenceManagerException;

}
