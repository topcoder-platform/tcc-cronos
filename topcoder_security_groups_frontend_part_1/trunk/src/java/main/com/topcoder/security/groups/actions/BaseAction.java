/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.security.TCSubject;
import com.topcoder.security.groups.services.AuthorizationService;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.util.log.Log;

/**
 * <p>
 * BaseAction is used to hold the common fields such as logger for the sub classes to use.
 * </p>
 * <p>
 * <strong>Thread Safety: </strong> It's mutable and not thread safe.
 * However the struts framework will guarantee that it will be used in the thread safe model.
 * </p>
 *
 * @author woodjhon, hanshuai
 * @version 1.0
 */
@SuppressWarnings("serial")
public abstract class BaseAction extends ActionSupport {
    /**
     * <p>
     * The logger used to perform logging. It is not null after initialization.
     * </p>
     */
    private Log logger;

    /**
     * Purpose: auditService is used to represents the group audit service. It's required. Usage: It's injected.
     * Legal Values: Not null after set
     */
    private GroupAuditService auditService;

    /**
     * Purpose: authorizationService is used to represents the authorization service. It's required. Usage: It's
     * injected. Legal Values: Not null after set
     */
    private AuthorizationService authorizationService;

    /**
     * <p>
     * Create the instance.
     * </p>
     */
    protected BaseAction() {
        // Empty Constructor.
    }

    /**
     * <p>
     * Check that the required fields are injected.
     * </p>
     *
     * @throws SecurityGroupsActionConfigurationException
     *             is thrown if any of these fields is null:<br>
     *             auditService, authorizationService.
     */
    public void checkInit() {
        ValidationUtility.checkNotNull(auditService, "auditService",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(authorizationService, "authorizationService",
                SecurityGroupsActionConfigurationException.class);
    }

    /**
     * <p>
     * Get current user id method.
     * </p>
     *
     * @return the current user id.
     */
    protected long getCurrentUserId() {
        TCSubject subject = DirectStrutsActionsHelper.getTCSubjectFromSession();
        return subject.getUserId();
    }

    /**
     * <p>
     * Get logger.
     * </p>
     *
     * @return the logger
     */
    public Log getLogger() {
        return logger;
    }

    /**
     * <p>
     * Set logger.
     * </p>
     *
     * @param logger
     *            the logger to set.
     */
    public void setLogger(Log logger) {
        this.logger = logger;
    }

    /**
     * <p>
     * Get audit service.
     * </p>
     *
     * @return the audit service
     */
    public GroupAuditService getAuditService() {
        return auditService;
    }

    /**
     * <p>
     * Set audit service.
     * </p>
     *
     * @param auditService
     *            the audit service to set.
     */
    public void setAuditService(GroupAuditService auditService) {
        this.auditService = auditService;
    }

    /**
     * <p>
     * Get authorization service.
     * </p>
     *
     * @return the authorization service
     */
    public AuthorizationService getAuthorizationService() {
        return authorizationService;
    }

    /**
     * <p>
     * Set authorization service.
     * </p>
     *
     * @param authorizationService
     *            the authorization service to set.
     */
    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }
}
