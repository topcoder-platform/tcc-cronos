/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.topcoder.commons.utils.ValidationUtility;

/**
 * <p>
 * SessionAwareBaseAction is used to represents the session aware action.
 * </p>
 * <p>
 * <strong>Thread Safety: </strong> It's mutable and not thread safe.
 * However the struts framework will guarantee that it will be used in the thread safe model.
 * </p>
 * @author woodjhon, hanshuai
 * @version 1.0
 */
@SuppressWarnings("serial")
public abstract class SessionAwareBaseAction extends BaseAction implements SessionAware {
    /**
     * Purpose: session is used to represents the session map injected. It's required. Usage: It's injected. Legal
     * Values: Not null and not empty after set. The value should be non-null
     */
    private Map<String, Object> session;

    /**
     * <p>
     * Create the instance.
     * </p>
     */
    protected SessionAwareBaseAction() {
        // Empty Constructor.
    }

    /**
     * <p>
     * Check that the required fields are injected.
     * </p>
     * @throws SecurityGroupsActionConfigurationException
     *             is thrown if one of the following conditions is true:<br>
     *             <li>session is null or empty
     *             <li>session contains null values.
     *             <li>any of these fields is null:<br>
     *             auditService, authorizationService.
     */
    public void checkInit() {
        super.checkInit();
        ValidationUtility.checkNotNullNorEmpty(session, "session", SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNullValues(session, "session", SecurityGroupsActionConfigurationException.class);
    }

    /**
     * <p>
     * Set session.
     * </p>
     *
     * @param session
     *            the session to set.
     */
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    /**
     * <p>
     * Get session.
     * </p>
     *
     * @return the session
     */
    protected Map<String, Object> getSession() {
        return session;
    }
}
