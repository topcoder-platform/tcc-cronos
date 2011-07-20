/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.util.log.Log;
import com.topcoder.web.common.TCWebException;
import com.topcoder.web.common.security.BasicAuthentication;

/**
 * This is a base class for all Struts actions defined in this component. It holds a logger instance and defines
 * methods for retrieving basic authentication data of the active user.
 * <p>
 * Thread Safety:
 * This class is mutable and not thread safe. But it will be used in thread safe manner by Struts. It's assumed that
 * request scope will be set up for all Struts actions in Spring configuration, thus actions will be accessed from a
 * single thread only.
 * The injected Log instance is expected to be thread safe.
 *
 * @author saarixx, mumujava
 * @version 1.0
 */
public abstract class BaseUserCommunityManagementAction extends ActionSupport implements SessionAware {
    /**
    * <p>
    * The serial version uid.
    * </p>
    */
    private static final long serialVersionUID = 3858349512130298028L;

    /**
     * The key used for obtaining BasicAuthentication instance from the session attributes. Is expected to be
     * initialized by Spring via setter injection. Cannot be null/empty after initialization (validation is performed
     * in @PostConstruct method checkInit()). Has a setter. Is used in getAuthenticationData().
     */
    private String authenticationSessionKey;

    /**
     * The map with injected session attributes. It is expected to be injected by Struts since this class implements
     * SessionAware. Can be any value, can contain any values. Has getter and setter. Is used
     * in getAuthenticationData().
     */
    private Map<String, Object> session;

    /**
     * The logger to be used by subclasses for logging errors and debug information. If is null, logging is not
     * performed. Has a setter and a protected getter.
     */
    private Log log;

    /**
     * Creates an instance of BaseUserCommunityManagementAction.
     */
    protected BaseUserCommunityManagementAction() {
    }

    /**
     * Checks whether this action was initialized by Spring properly.
     * @throws UserCommunityManagementInitializationException if the class was not initialized properly (e.g. when
     * required properly is not specified)
     */
    @PostConstruct
    protected void checkInit() {
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(authenticationSessionKey,
                "authenticationSessionKey", UserCommunityManagementInitializationException.class);
    }

    /**
     * Retrieves the ID of the active user.
     * @throws TCWebException if any error occurred
     * @return the retrieved user ID
     */
    public long getUserId() throws TCWebException {
        return getActiveUser().getId();
    }

    /**
     * Retrieves the handle of the active user.
     *
     * @throws TCWebException if any error occurred
     * @return the retrieved user handle
     */
    public String getUserHandle() throws TCWebException {
        return getActiveUser().getUserName();
    }

    /**
     * Gets the active user.
     *
     * @return the active user.
     * @throws TCWebException if any error occurred
     */
    private com.topcoder.shared.security.User getActiveUser() throws TCWebException {
        //Get authentication data:
        BasicAuthentication authentication = getAuthenticationData();
        //Get the authenticated user data:
        com.topcoder.shared.security.User activeUser = authentication.getActiveUser();
        if (activeUser == null) {
            throw new TCWebException("No activeUser can be found");
        }
        return activeUser;
    }

    /**
     * Retrieves the map with injected session attributes.
     *
     * @return the map with injected session attributes
     */
    public Map<String, Object> getSession() {
        return session;
    }

    /**
     * Sets session attributes to this class.
     *
     * @param session the map with session attributes
     */
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    /**
     * Retrieves the BasicAuthentication instance for the current authenticated user.
     *
     * @throws TCWebException if any error occurred
     * @return the retrieved authentication data (not null)
     */
    protected BasicAuthentication getAuthenticationData() throws TCWebException {
        BasicAuthentication result = (BasicAuthentication) session.get(authenticationSessionKey);
        if (result == null) {
            throw new TCWebException("The BasicAuthentication is not injected");
        }
        return result;
    }

    /**
     * Retrieves the logger to be used by subclasses for logging errors and debug information.
     *
     * @return the logger to be used by subclasses for logging errors and debug information
     */
    protected Log getLog() {
        return log;
    }

    /**
     * Sets the logger to be used by subclasses for logging errors and debug information.
     *
     * @param log the logger to be used by subclasses for logging errors and debug information
     */
    public void setLog(Log log) {
        this.log = log;
    }

    /**
     * Sets the key used for obtaining BasicAuthentication instance from the session attributes.
     *
     * @param authenticationSessionKey the key used for obtaining BasicAuthentication
     *  instance from the session attributes
     */
    public void setAuthenticationSessionKey(String authenticationSessionKey) {
        this.authenticationSessionKey = authenticationSessionKey;
    }
}
