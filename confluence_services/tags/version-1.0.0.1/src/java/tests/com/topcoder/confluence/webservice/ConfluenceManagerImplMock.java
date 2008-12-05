/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice;

import com.topcoder.confluence.ConfluenceAuthenticationFailedException;
import com.topcoder.confluence.ConfluenceConnectionException;
import com.topcoder.confluence.ConfluenceManager;
import com.topcoder.confluence.ConfluenceManagerException;
import com.topcoder.confluence.ConfluenceNotAuthorizedException;
import com.topcoder.confluence.entities.ComponentType;
import com.topcoder.confluence.entities.ConfluenceAssetType;
import com.topcoder.confluence.entities.ConfluenceCatalog;
import com.topcoder.confluence.entities.ConfluencePageCreatedAction;
import com.topcoder.confluence.entities.ConfluencePageCreationResult;
import com.topcoder.confluence.entities.Page;

/**
 * <p>
 * Simple <code>ConfluenceManager</code> mock implementation for testing.
 * </p>
 * <p>
 * <b>Thread Safety:</b> the class is expected to be used in a manner that is thread-safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ConfluenceManagerImplMock implements ConfluenceManager {

    /**
     * <p>
     * Indicates whether the business method throw <code>ConfluenceConnectionException</code>.
     * </p>
     */
    public static boolean isThrowConfluenceConnectionException = false;

    /**
     * <p>
     * Indicates whether the business method throw <code>ConfluenceAuthenticationFailedException</code>.
     * </p>
     */
    public static boolean isThrowConfluenceAuthenticationFailedException = false;

    /**
     * <p>
     * Indicates whether the business method throw <code>ConfluenceManagerException</code>.
     * </p>
     */
    public static boolean isThrowConfluenceManagerException = false;

    /**
     * <p>
     * Indicates whether the business method throw <code>ConfluenceNotAuthorizedException</code>.
     * </p>
     */
    public static boolean isThrowConfluenceNotAuthorizedException = false;

    /**
     * <p>
     * Default empty constructor.
     * </p>
     */
    public ConfluenceManagerImplMock() {
        // does nothing
    }

    /**
     * <p>
     * check whether that business method throw which exception.
     * </p>
     *
     * @throws ConfluenceConnectionException
     *             if <code>isThrowConfluenceConnectionException</code> is true
     * @throws ConfluenceAuthenticationFailedException
     *             if <code>isThrowConfluenceAuthenticationFailedException</code> is true
     * @throws ConfluenceManagerException
     *             if <code>isThrowConfluenceManagerException</code> is true
     * @throws ConfluenceNotAuthorizedException
     *             if <code>isThrowConfluenceNotAuthorizedException</code> is true
     */
    private void checkAndThrowException() throws ConfluenceConnectionException,
        ConfluenceAuthenticationFailedException, ConfluenceManagerException, ConfluenceNotAuthorizedException {

        if (isThrowConfluenceConnectionException) {
            throw new ConfluenceConnectionException("ConfluenceConnectionException error message.");
        }

        if (isThrowConfluenceAuthenticationFailedException) {
            throw new ConfluenceAuthenticationFailedException(
                "ConfluenceAuthenticationFailedException error message.");
        }

        if (isThrowConfluenceManagerException) {
            throw new ConfluenceManagerException("ConfluenceManagerException error message.");
        }

        if (isThrowConfluenceNotAuthorizedException) {
            throw new ConfluenceNotAuthorizedException("ConfluenceNotAuthorizedException error message.");
        }
    }

    /**
     * <p>
     * This method just returns the fixed token 'tokentoken' used to test.
     * </p>
     *
     * @param userName
     *            the user name
     * @param password
     *            the user password
     * @return tokentoken string
     * @throws ConfluenceConnectionException
     *             if <code>isThrowConfluenceConnectionException</code> is true
     * @throws ConfluenceAuthenticationFailedException
     *             if <code>isThrowConfluenceAuthenticationFailedException</code> is true
     * @throws ConfluenceManagerException
     *             if <code>isThrowConfluenceManagerException</code> is true
     */
    public String login(String userName, String password) throws ConfluenceConnectionException,
        ConfluenceAuthenticationFailedException, ConfluenceManagerException {
        checkAndThrowException();
        return "tokentoken";
    }

    /**
     * <p>
     * This methods logs the user out.
     * </p>
     *
     * @param token
     *            the authorization token
     * @throws ConfluenceConnectionException
     *             if <code>isThrowConfluenceConnectionException</code> is true
     * @throws ConfluenceManagerException
     *             if <code>isThrowConfluenceManagerException</code> is true
     * @throws ConfluenceNotAuthorizedException
     *             if <code>isThrowConfluenceNotAuthorizedException</code> is true
     */
    public void logout(String token) throws ConfluenceConnectionException, ConfluenceNotAuthorizedException,
        ConfluenceManagerException {
        checkAndThrowException();
    }

    /**
     * <p>
     * This method is used to create page from the provided Page object, which already stores page parameters.
     * </p>
     *
     * @param token
     *            the authorization token
     * @param page
     *            the Page object that stores parameters of the page being created
     * @return an object that stores result of this operation for testing
     * @throws ConfluenceConnectionException
     *             if <code>isThrowConfluenceConnectionException</code> is true
     * @throws ConfluenceManagerException
     *             if <code>isThrowConfluenceManagerException</code> is true
     * @throws ConfluenceNotAuthorizedException
     *             if <code>isThrowConfluenceNotAuthorizedException</code> is true
     */
    public ConfluencePageCreationResult createPage(String token, Page page) throws ConfluenceConnectionException,
        ConfluenceNotAuthorizedException, ConfluenceManagerException {
        checkAndThrowException();
        return new ConfluencePageCreationResult(page, ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_CREATED);
    }

    /**
     * <p>
     * This method is used to create page with given parameters.
     * </p>
     *
     * @param token
     *            the authorization token
     * @param pageName
     *            the component name
     * @param version
     *            the version of the page that will be created
     * @param assetType
     *            the confluence asset type of the page being created
     * @param catalog
     *            the catalog
     * @param componentType
     *            the type of the component
     * @return an object that stores result of this operation
     * @throws ConfluenceConnectionException
     *             if <code>isThrowConfluenceConnectionException</code> is true
     * @throws ConfluenceManagerException
     *             if <code>isThrowConfluenceManagerException</code> is true
     * @throws ConfluenceNotAuthorizedException
     *             if <code>isThrowConfluenceNotAuthorizedException</code> is true
     */
    public ConfluencePageCreationResult createPage(String token, String pageName, String version,
        ConfluenceAssetType assetType, ConfluenceCatalog catalog, ComponentType componentType)
        throws ConfluenceConnectionException, ConfluenceNotAuthorizedException, ConfluenceManagerException {
        checkAndThrowException();
        Page page = new Page();
        page.setBasePageUrl(pageName + "Url");
        page.setVersion(version);
        page.setAssetType(assetType);
        page.setCatalog(catalog);
        page.setComponentType(componentType);
        return new ConfluencePageCreationResult(page, ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_CREATED);
    }

    /**
     * <p>
     * This method is used to create page with given parameters.
     * </p>
     *
     * @param token
     *            the authorization token
     * @param pageName
     *            the component name
     * @param version
     *            the version of the page that will be created
     * @param assetType
     *            the confluence asset type of the page being created
     * @param catalog
     *            the catalog
     * @param applicationCode
     *            the application code
     * @return an object that stores result of this operation
     * @throws ConfluenceConnectionException
     *             if <code>isThrowConfluenceConnectionException</code> is true
     * @throws ConfluenceManagerException
     *             if <code>isThrowConfluenceManagerException</code> is true
     * @throws ConfluenceNotAuthorizedException
     *             if <code>isThrowConfluenceNotAuthorizedException</code> is true
     */
    public ConfluencePageCreationResult createPage(String token, String pageName, String version,
        ConfluenceAssetType assetType, ConfluenceCatalog catalog, String applicationCode)
        throws ConfluenceConnectionException, ConfluenceNotAuthorizedException, ConfluenceManagerException {
        checkAndThrowException();
        Page page = new Page();
        page.setBasePageUrl(pageName + "Url");
        page.setVersion(version);
        page.setAssetType(assetType);
        page.setCatalog(catalog);
        page.setApplicationCode(applicationCode);
        return new ConfluencePageCreationResult(page, ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_CREATED);
    }

    /**
     * <p>
     * This method is used to retrieve the page.
     * </p>
     *
     * @param token
     *            the authorization token
     * @param pageName
     *            the component name
     * @param version
     *            the version of the page that will be created
     * @param assetType
     *            the confluence asset type of the page being created
     * @param catalog
     *            the catalog
     * @param componentType
     *            the type of the component
     * @return the Page object containing retrieved remote page parameters and content
     * @throws ConfluenceConnectionException
     *             if <code>isThrowConfluenceConnectionException</code> is true
     * @throws ConfluenceManagerException
     *             if <code>isThrowConfluenceManagerException</code> is true
     * @throws ConfluenceNotAuthorizedException
     *             if <code>isThrowConfluenceNotAuthorizedException</code> is true
     */
    public Page retrievePage(String token, String pageName, String version, ConfluenceAssetType assetType,
        ConfluenceCatalog catalog, ComponentType componentType) throws ConfluenceConnectionException,
        ConfluenceNotAuthorizedException, ConfluenceManagerException {
        checkAndThrowException();
        Page page = new Page();
        page.setBasePageUrl(pageName + "Url");
        page.setVersion(version);
        page.setAssetType(assetType);
        page.setCatalog(catalog);
        page.setComponentType(componentType);

        return page;
    }

    /**
     * <p>
     * This method is used to retrieve the page.
     * </p>
     *
     * @param token
     *            the authorization token
     * @param pageName
     *            the component name
     * @param version
     *            the version of the page that will be created
     * @param assetType
     *            the confluence asset type of the page being created
     * @param catalog
     *            the catalog
     * @param applicationCode
     *            the application code
     * @return the Page object containing retrieved remote page parameters and content
     * @throws ConfluenceConnectionException
     *             if <code>isThrowConfluenceConnectionException</code> is true
     * @throws ConfluenceManagerException
     *             if <code>isThrowConfluenceManagerException</code> is true
     * @throws ConfluenceNotAuthorizedException
     *             if <code>isThrowConfluenceNotAuthorizedException</code> is true
     */
    public Page retrievePage(String token, String pageName, String version, ConfluenceAssetType assetType,
        ConfluenceCatalog catalog, String applicationCode) throws ConfluenceConnectionException,
        ConfluenceNotAuthorizedException, ConfluenceManagerException {
        checkAndThrowException();
        Page page = new Page();
        page.setBasePageUrl(pageName + "Url");
        page.setVersion(version);
        page.setAssetType(assetType);
        page.setCatalog(catalog);
        page.setApplicationCode(applicationCode);

        return page;
    }
}