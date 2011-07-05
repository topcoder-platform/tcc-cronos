/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */



package com.cronos.onlinereview.login;

import com.topcoder.security.authenticationfactory.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * This interface represents an authentication cookie manager. It provides methods for setting, removing and checking
 * authentication cookie in HTTP request/response.
 * </p>
 * <p>
 * <b> Thread Safety:</b> Implementations of this interface must be thread safe. </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.1
 * @since 1.1
 */
public interface AuthCookieManager {

    /**
     * Sets the authentication cookie to the given HTTP response for the given principal and HTTP request.
     *
     * @param principal
     *            the principal
     * @param request
     *            the HTTP servlet request
     * @param response
     *            the HTTP servlet response
     * @throws IllegalArgumentException
     *             if any argument is null
     * @throws AuthCookieManagementException
     *             if some error occurred when setting the authentication cookie
     */
    public void setAuthCookie(Principal principal, HttpServletRequest request, HttpServletResponse response)
            throws AuthCookieManagementException;

    /**
     * Removes the authentication cookie from the HTTP client using the given HTTP request and response.
     *
     * @param request
     *            the HTTP servlet request
     * @param response
     *            the HTTP servlet response
     * @throws IllegalArgumentException
     *             if any argument is null
     * @throws AuthCookieManagementException
     *             if some error occurred when removing the authentication cookie
     */
    public void removeAuthCookie(HttpServletRequest request, HttpServletResponse response)
            throws AuthCookieManagementException;

    /**
     * Checks if the given HTTP request contains an authentication cookie. If it exists, this method extracts user ID
     * from the cookie and validates the user password.
     *
     * @param request
     *            the HTTP servlet request
     * @return the user ID if auth cookie exists and is valid, null otherwise
     * @throws IllegalArgumentException
     *             if request is null
     * @throws AuthCookieManagementException
     *             if some critical error occurred during the validation (but not if an error is in the cookie)
     */
    public Long checkAuthCookie(HttpServletRequest request) throws AuthCookieManagementException;
}
