/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.shared.security.User;
import com.topcoder.util.log.Log;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.reg.ProfileActionConfigurationException;
import com.topcoder.web.reg.ProfileActionException;

/**
 * <p>
 * This is a base class for all profile actions. It provides a logger, audit DAO and user DAO for logging, auditing,
 * and user management, respectively. It also provides the key for getting the authentication object from session. The
 * session map is provided as part of this actions contract as being a SessionAware entity. The HttpServletRequest is
 * provided as part of this actions contract as being a ServletRequestAware entity, although this is only for the sake
 * of getting the caller's IP address for auditing. Similarly, the audit operation type is provided for auditing. A
 * convenience method to get a pre-populated AuditRecord is also provided. Also provides the completeness report.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe, but used in thread safe manner by framework.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public abstract class BaseProfileAction extends ActionSupport implements ServletRequestAware, SessionAware {

    /**
     * <p>
     * It will be used to log all activity and errors.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * On initialization check, must be not null. This field will be injected by the container (expected to be done
     * only once), and is not expected to change afterward.
     * </p>
     */
    private Log logger;

    /**
     * <p>
     * This is the AuditDAO instance used to manage an audit.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * On initialization check, must be not null. This field will be injected by the container (expected to be done
     * only once), and is not expected to change afterward.
     * </p>
     */
    private AuditDAO auditDAO;

    /**
     * <p>
     * This is the name of the session key where the WebAuthentication instance will be stored.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * On initialization check, must be not null/empty. This field will be injected by the container (expected to be
     * done only once), and is not expected to change afterward.
     * </p>
     */
    private String authenticationSessionKey;

    /**
     * <p>
     * This is the session attribute map, provided by the Struts framework. It is used to get session values, including
     * the WebAuthentication.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * On initialization check, must be not null. This field will be injected by the container (expected to be done
     * only once), and is not expected to change afterward.
     * </p>
     */
    private Map<String, Object> session;

    /**
     * <p>
     * This is the UserDAO instance used to manage user info.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * On initialization check, must be not null. This field will be injected by the container (expected to be done
     * only once), and is not expected to change afterward.
     * </p>
     */
    private UserDAO userDAO;

    /**
     * <p>
     * This is the HttpServletRequest, provided by the Struts framework. Used to get the IP address of the caller for
     * auditing.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * On initialization check, must be not null. This field will be injected by the container (expected to be done
     * only once), and is not expected to change afterward.
     * </p>
     */
    private HttpServletRequest servletRequest;

    /**
     * <p>
     * This is the operation type name used for auditing.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * On initialization check, must be not null/empty. This field will be injected by the container (expected to be
     * done only once), and is not expected to change afterward.
     * </p>
     */
    private String auditOperationType;

    /**
     * <p>
     * This is the ProfileCompletenessRetriever instance used to get user profile completeness information.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * On initialization check, must be not null. This field will be injected by the container (expected to be done
     * only once), and is not expected to change afterward.
     * </p>
     */
    private ProfileCompletenessRetriever profileCompletenessRetriever;

    /**
     * <p>
     * Represents the information about the completion status of the registration for a user.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * It may have any value. This field will be set by the execute method.
     * </p>
     */
    private ProfileCompletenessReport profileCompletenessReport;

    /**
     * <p>
     * Creates an instance of BaseProfileAction.
     * </p>
     */
    protected BaseProfileAction() {
    }

    /**
     * <p>
     * Checks that all required values have been injected by the system right after construction and injection.
     * </p>
     * <p>
     * This is used to obviate the need to check these values each time in the execute method.
     * </p>
     * @throws ProfileActionConfigurationException If any required value has not been injected into this class
     */
    @PostConstruct
    protected void checkInitialization() {
        // Check that the following fields have required values:
        ValidationUtility.checkNotNull(logger, "logger", ProfileActionConfigurationException.class);
        ValidationUtility.checkNotNull(auditDAO, "auditDAO", ProfileActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(authenticationSessionKey, "authenticationSessionKey",
                ProfileActionConfigurationException.class);
        ValidationUtility.checkNotNull(userDAO, "userDAO", ProfileActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(auditOperationType, "auditOperationType",
                ProfileActionConfigurationException.class);
        ValidationUtility.checkNotNull(profileCompletenessRetriever, "profileCompletenessRetriever",
                ProfileActionConfigurationException.class);
    }

    /**
     * <p>
     * Gets the WebAuthentication in the session. Returns null if not found.
     * </p>
     * @return the WebAuthentication in session
     */
    protected WebAuthentication getAuthentication() {
        Object obj = this.session.get(authenticationSessionKey);
        if (!(obj instanceof WebAuthentication)) {
            return null;
        }
        return (WebAuthentication) obj;
    }

    /**
     * <p>
     * Gets the user in the WebAuthentication in the session. Returns null if not found.
     * </p>
     * @return the user in the WebAuthentication in session
     */
    protected com.topcoder.shared.security.User getAuthenticationUser() {
        WebAuthentication authentication = this.getAuthentication();
        com.topcoder.shared.security.User user = null;
        if (authentication != null) {
            user = authentication.getActiveUser();
        }
        return user;
    }

    /**
     * <p>
     * Provides an AuditRecord with several of the properties already filled.
     * </p>
     * @return the prepared AuditRecord
     * @throws ProfileActionException if any error occurs
     */
    protected AuditRecord getPreparedAuditRecord() throws ProfileActionException {
        // Create a new AuditRecord
        AuditRecord auditRecord = new AuditRecord();
        auditRecord.setOperationType(this.auditOperationType);
        User user = Helper.retrieveUserFromSession(this, "BaseProfileAction.getPreparedAuditRecord()");
        auditRecord.setHandle(user.getUserName());
        auditRecord.setIpAddress(this.getServletRequest().getRemoteAddr());
        auditRecord.setTimestamp(new Date());
        return auditRecord;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public HttpServletRequest getServletRequest() {
        return servletRequest;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param request the namesake field instance value to set
     */
    public void setServletRequest(HttpServletRequest request) {
        this.servletRequest = request;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public Log getLogger() {
        return logger;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param logger the namesake field instance value to set
     */
    public void setLogger(Log logger) {
        this.logger = logger;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public AuditDAO getAuditDAO() {
        return auditDAO;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param auditDAO the namesake field instance value to set
     */
    public void setAuditDAO(AuditDAO auditDAO) {
        this.auditDAO = auditDAO;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public String getAuthenticationSessionKey() {
        return authenticationSessionKey;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param authenticationSessionKey the namesake field instance value to set
     */
    public void setAuthenticationSessionKey(String authenticationSessionKey) {
        this.authenticationSessionKey = authenticationSessionKey;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public Map<String, Object> getSession() {
        return session;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param session the namesake field instance value to set
     */
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public UserDAO getUserDAO() {
        return userDAO;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param userDAO the namesake field instance value to set
     */
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public String getAuditOperationType() {
        return auditOperationType;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param auditOperationType the namesake field instance value to set
     */
    public void setAuditOperationType(String auditOperationType) {
        this.auditOperationType = auditOperationType;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public ProfileCompletenessRetriever getProfileCompletenessRetriever() {
        return profileCompletenessRetriever;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param profileCompletenessRetriever the namesake field instance value to set
     */
    public void setProfileCompletenessRetriever(ProfileCompletenessRetriever profileCompletenessRetriever) {
        this.profileCompletenessRetriever = profileCompletenessRetriever;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public ProfileCompletenessReport getProfileCompletenessReport() {
        return profileCompletenessReport;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param profileCompletenessReport the namesake field instance value to set
     */
    public void setProfileCompletenessReport(ProfileCompletenessReport profileCompletenessReport) {
        this.profileCompletenessReport = profileCompletenessReport;
    }
}