/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.registration;

import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.util.log.Log;
import com.topcoder.web.common.TCWebException;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.RegistrationTypeDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.reg.RegistrationActionConfigurationException;

/**
 * <p>
 * This is a base class for all basic actions. It provides a logger, audit DAO and user DAO for logging,
 * auditing, and user management, respectively. It also provides the key for getting the authentication object
 * from session. The session map is provided as part of this actions contract as being a SessionAware entity.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe, but used in thread safe manner by framework.
 * </p>
 *
 * @author argolite, stevenfrog
 * @version 1.0
 */
public abstract class BaseRegistrationAction extends ActionSupport implements SessionAware,
                ServletRequestAware {
    /**
     * <p>
     * It will be used to log all activity and errors. It is set in the setter. It can be retrieved in the
     * getter. It may have any value. This field will be injected by the container (expected to be done only
     * once), and is not expected to change afterward.
     * </p>
     */
    private Log logger;

    /**
     * <p>
     * This is the AuditDAO instance used to manage an audit. It is set in the setter. It can be retrieved in
     * the getter. It may have any value. This field will be injected by the container (expected to be done
     * only once), and is not expected to change afterward.
     * </p>
     */
    private AuditDAO auditDAO;

    /**
     * <p>
     * This is the name of the session key where the WebAuthentication instance will be stored. It is set in
     * the setter. It can be retrieved in the getter. It may have any value. This field will be injected by
     * the container (expected to be done only once), and is not expected to change afterward.
     * </p>
     */
    private String authenticationSessionKey;

    /**
     * <p>
     * This is the session attribute map, provided by the Struts framework. It is set in the setter. It can be
     * retrieved in the getter. It may have any value. This field will be injected by the container (expected
     * to be done only once), and is not expected to change afterward.
     * </p>
     */
    private Map<String, Object> session;

    /**
     * <p>
     * This is the http servlet request, provided by the Struts framework. It is set in the setter. This field
     * will be injected by the container.
     * </p>
     */
    private HttpServletRequest servletRequest;

    /**
     * <p>
     * This is the UserDAO instance used to manage user info. It is set in the setter. It can be retrieved in
     * the getter. It may have any value. This field will be injected by the container (expected to be done
     * only once), and is not expected to change afterward.
     * </p>
     */
    private UserDAO userDAO;

    /**
     * <p>
     * This is the RegistrationTypeDAO instance used to get registration types. It is set in the setter. It
     * can be retrieved in the getter. It may have any value. This field will be injected by the container
     * (expected to be done only once), and is not expected to change afterward.
     * </p>
     */
    private RegistrationTypeDAO registrationTypeDAO;

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    protected BaseRegistrationAction() {
        // Empty
    }

    /**
     * <p>
     * This method checks that all required values have been injected by the system right after construction
     * and injection. This is used to obviate the need to check these values each time in the execute method.
     * </p>
     *
     * @throws RegistrationActionConfigurationException
     *             If any required value has not been injected into this class
     */
    @PostConstruct
    protected void checkInitialization() {
        ValidationUtility.checkNotNull(logger, "logger", RegistrationActionConfigurationException.class);
        ValidationUtility.checkNotNull(auditDAO, "auditDAO", RegistrationActionConfigurationException.class);
        ValidationUtility.checkNotNull(session, "session", RegistrationActionConfigurationException.class);
        ValidationUtility.checkNotNull(userDAO, "userDAO", RegistrationActionConfigurationException.class);
        ValidationUtility.checkNotNull(registrationTypeDAO, "registrationTypeDAO",
            RegistrationActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(authenticationSessionKey,
            "authenticationSessionKey", RegistrationActionConfigurationException.class);
        ValidationUtility.checkNotNull(servletRequest, "servletRequest",
            RegistrationActionConfigurationException.class);
    }

    /**
     * <p>
     * This method will get the WebAuthentication in the session.
     * </p>
     *
     * @return the WebAuthentication instance
     * @throws TCWebException
     *             If there are any errors in the action
     */
    protected WebAuthentication getAuthentication() throws TCWebException {
        Object obj = session.get(authenticationSessionKey);
        ValidationUtility.checkNotNull(obj, "object with key[" + authenticationSessionKey + "] in session",
            TCWebException.class);
        ValidationUtility.checkNullOrInstance(obj, WebAuthentication.class, "object with key["
            + authenticationSessionKey + "] in session", TCWebException.class);
        return (WebAuthentication) obj;
    }

    /**
     * <p>
     * Audit user action.
     * </p>
     *
     * @param operationType
     *            the operation type
     * @param handle
     *            the user handle
     */
    protected void auditAction(String operationType, String handle) {
        AuditDAO auditDAO = getAuditDAO();
        AuditRecord record = new AuditRecord();
        record.setOperationType(operationType);
        record.setHandle(handle);
        record.setIpAddress(getServletRequest().getRemoteAddr());
        record.setTimestamp(new Date());
        auditDAO.audit(record);
    }

    /**
     * <p>
     * Getter method for logger, simply return the value of the namesake field.
     * </p>
     *
     * @return the logger
     */
    public Log getLogger() {
        return logger;
    }

    /**
     * <p>
     * Setter method for the logger, simply set the value to the namesake field.
     * </p>
     *
     * @param logger
     *            the logger to set
     */
    public void setLogger(Log logger) {
        this.logger = logger;
    }

    /**
     * <p>
     * Getter method for auditDAO, simply return the value of the namesake field.
     * </p>
     *
     * @return the auditDAO
     */
    public AuditDAO getAuditDAO() {
        return auditDAO;
    }

    /**
     * <p>
     * Setter method for the auditDAO, simply set the value to the namesake field.
     * </p>
     *
     * @param auditDAO
     *            the auditDAO to set
     */
    public void setAuditDAO(AuditDAO auditDAO) {
        this.auditDAO = auditDAO;
    }

    /**
     * <p>
     * Getter method for authenticationSessionKey, simply return the value of the namesake field.
     * </p>
     *
     * @return the authenticationSessionKey
     */
    public String getAuthenticationSessionKey() {
        return authenticationSessionKey;
    }

    /**
     * <p>
     * Setter method for the authenticationSessionKey, simply set the value to the namesake field.
     * </p>
     *
     * @param authenticationSessionKey
     *            the authenticationSessionKey to set
     */
    public void setAuthenticationSessionKey(String authenticationSessionKey) {
        this.authenticationSessionKey = authenticationSessionKey;
    }

    /**
     * <p>
     * Getter method for session, simply return the value of the namesake field.
     * </p>
     *
     * @return the session
     */
    public Map<String, Object> getSession() {
        return session;
    }

    /**
     * <p>
     * Setter method for the session, simply set the value to the namesake field.
     * </p>
     *
     * @param session
     *            the session to set
     */
    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    /**
     * <p>
     * Getter method for userDAO, simply return the value of the namesake field.
     * </p>
     *
     * @return the userDAO
     */
    public UserDAO getUserDAO() {
        return userDAO;
    }

    /**
     * <p>
     * Setter method for the userDAO, simply set the value to the namesake field.
     * </p>
     *
     * @param userDAO
     *            the userDAO to set
     */
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * <p>
     * Getter method for servletRequest, simply return the value of the namesake field.
     * </p>
     *
     * @return the servletRequest
     */
    public HttpServletRequest getServletRequest() {
        return servletRequest;
    }

    /**
     * <p>
     * Setter method for the servletRequest, simply set the value to the namesake field.
     * </p>
     *
     * @param servletRequest
     *            the servletRequest to set
     */
    @Override
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    /**
     * <p>
     * Getter method for registrationTypeDAO, simply return the value of the namesake field.
     * </p>
     *
     * @return the registrationTypeDAO
     */
    public RegistrationTypeDAO getRegistrationTypeDAO() {
        return registrationTypeDAO;
    }

    /**
     * <p>
     * Setter method for the registrationTypeDAO, simply set the value to the namesake field.
     * </p>
     *
     * @param registrationTypeDAO
     *            the registrationTypeDAO to set
     */
    public void setRegistrationTypeDAO(RegistrationTypeDAO registrationTypeDAO) {
        this.registrationTypeDAO = registrationTypeDAO;
    }

}
