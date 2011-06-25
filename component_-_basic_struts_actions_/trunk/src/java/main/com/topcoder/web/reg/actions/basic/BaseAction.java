/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.basic;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.reg.BasicStrutsActionsConfigurationException;

/**
 * <p>
 * This is the base action of all actions of this component. It extends ActionSupport and implement
 * SessionAware and ServletRequestAware to get access to request and session. It provides the method to get a
 * WebAuthentication from the http session. It has a method that performs auditing.
 * </p>
 * <p>
 * Thread Safety: This class is not thread-safe because it's mutable. However, dedicated instance of struts
 * action will be created by the struts framework to process the user request, so the struts actions don't
 * need to be thread-safe.
 * </p>
 *
 * @author mekanizumu, TCSDEVELOPER
 * @version 1.0
 */
public abstract class BaseAction extends ActionSupport implements SessionAware, ServletRequestAware {
    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = BaseAction.class.getName();

    /**
     * <p>
     * The Log object used for logging. It's a constant. So it can only be that constant value It is final and
     * won't change once it is initialized as part of variable declaration to:
     * LogManager.getLog(BaseAction.class.toString()). It is used throughout the class wherever logging is
     * needed.
     * </p>
     */
    private static final Log LOG = LogManager.getLog(BaseAction.class.toString());

    /**
     * <p>
     * The AuditDAO instance used to perform auditing. It has both getter and setter. It cannot be null. (Note
     * that the above statement applies only after the setter has been called as part of the IoC
     * initialization. This field's value has no restriction before the IoC initialization) It does not need
     * to be initialized when the instance is created. It is used in getAuditDAO(), setAuditDAO(), audit().
     * </p>
     */
    private AuditDAO auditDAO;

    /**
     * <p>
     * The session key for WebAuthentication that is put into session. It is set through setter and doesn't
     * have a getter. It cannot be null or empty. (Note that the above statement applies only after the setter
     * has been called as part of the IoC initialization. This field's value has no restriction before the IoC
     * initialization) It can be changed after it is initialized as part of variable declaration to:
     * "webAuthentication". It is used in getWebAuthenticationFromSession(), setWebAuthenticationSessionKey().
     * </p>
     */
    private String webAuthenticationSessionKey = "webAuthentication";

    /**
     * <p>
     * The http session's map. This is injected by Struts. It has both getter and setter. It cannot be null
     * but can be empty. The key can be any value. The contained values can be any value. It does not need to
     * be initialized when the instance is created. It is used in getWebAuthenticationFromSession(),
     * setSession(), getSession().
     * </p>
     */
    private Map<String, Object> session;

    /**
     * <p>
     * The http servlet request. This is injected by Struts. It has both getter and setter. It cannot be null.
     * It does not need to be initialized when the instance is created. It is used in getServletRequest(),
     * setServletRequest().
     * </p>
     */
    private HttpServletRequest servletRequest;

    /**
     * <p>
     * The operation type of this action. This is configured for each subclass for auditing purpose. It has
     * both getter and setter. It cannot be null or empty. (Note that the above statement applies only after
     * the setter has been called as part of the IoC initialization. This field's value has no restriction
     * before the IoC initialization) It does not need to be initialized when the instance is created. It is
     * used in setOperationType(), audit(), getOperationType().
     * </p>
     */
    private String operationType;

    /**
     * <p>
     * This is the default constructor for the class.
     * </p>
     */
    protected BaseAction() {
        // Empty
    }

    /**
     * <p>
     * Get the web authentication object from session.
     * </p>
     *
     * @return the webAuthentication
     * @throws BasicActionException
     *             if any error occurs
     */
    protected WebAuthentication getWebAuthenticationFromSession() throws BasicActionException {
        Object obj = session.get(webAuthenticationSessionKey);
        ValidationUtility.checkNotNull(obj,
            "object with key[" + webAuthenticationSessionKey + "] in session", BasicActionException.class);
        ValidationUtility.checkNullOrInstance(obj, WebAuthentication.class, "object with key["
            + webAuthenticationSessionKey + "] in session", BasicActionException.class);
        return (WebAuthentication) obj;
    }

    /**
     * <p>
     * Perform auditing.
     * </p>
     *
     * @param newValue
     *            the new value of the operation
     * @param handle
     *            the user handle to audit
     * @param previousValue
     *            the previous value of the operation
     * @throws IllegalArgumentException
     *             if handle is null or empty
     */
    protected void audit(String handle, String previousValue, String newValue) {
        // Log the entry
        final String signature = CLASS_NAME + ".audit(String, String, String)";
        LoggingWrapperUtility.logEntrance(LOG, signature,
            new String[] {"handle", "previousValue", "newValue"}, new Object[] {handle, previousValue,
                newValue});

        ParameterCheckUtility.checkNotNullNorEmptyAfterTrimming(handle, "handle");

        // Create a new AuditRecord:
        AuditRecord auditRecord = new AuditRecord();
        // This block populates the auditRecord
        auditRecord.setHandle(handle);
        auditRecord.setIpAddress(this.getServletRequest().getRemoteAddr());
        auditRecord.setTimestamp(new Date());
        auditRecord.setOperationType(operationType);
        auditRecord.setPreviousValue(previousValue);
        auditRecord.setNewValue(newValue);

        // Persist the auditRecord:
        auditDAO.audit(auditRecord);

        // Log the exit
        LoggingWrapperUtility.logExit(LOG, signature, null);
    }

    /**
     * <p>
     * Getter method for auditDAO, simply return the value of the namesake field.
     * </p>
     *
     * @return the auditDAO
     */
    protected AuditDAO getAuditDAO() {
        return auditDAO;
    }

    /**
     * <p>
     * Setter method for the auditDAO, simply set the value to the namesake field.
     * </p>
     *
     * @param auditDAO
     *            The AuditDAO instance used to perform auditing
     */
    public void setAuditDAO(AuditDAO auditDAO) {
        this.auditDAO = auditDAO;
    }

    /**
     * <p>
     * Getter method for webAuthenticationSessionKey, simply return the value of the namesake field.
     * </p>
     *
     * @return the webAuthenticationSessionKey
     */
    protected String getWebAuthenticationSessionKey() {
        return webAuthenticationSessionKey;
    }

    /**
     * <p>
     * Setter method for the webAuthenticationSessionKey, simply set the value to the namesake field.
     * </p>
     *
     * @param webAuthenticationSessionKey
     *            The session key for WebAuthentication that is put into session
     */
    public void setWebAuthenticationSessionKey(String webAuthenticationSessionKey) {
        this.webAuthenticationSessionKey = webAuthenticationSessionKey;
    }

    /**
     * <p>
     * Getter method for session, simply return the value of the namesake field.
     * </p>
     *
     * @return the session
     */
    protected Map<String, Object> getSession() {
        return session;
    }

    /**
     * <p>
     * Setter method for the session, simply set the value to the namesake field.
     * </p>
     *
     * @param session
     *            The http session's map. This is injected by Struts
     */
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    /**
     * <p>
     * Getter method for servletRequest, simply return the value of the namesake field.
     * </p>
     *
     * @return the servletRequest
     */
    protected HttpServletRequest getServletRequest() {
        return servletRequest;
    }

    /**
     * <p>
     * Setter method for the servletRequest, simply set the value to the namesake field.
     * </p>
     *
     * @param servletRequest
     *            The http servlet request. This is injected by Struts
     */
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    /**
     * <p>
     * Getter method for operationType, simply return the value of the namesake field.
     * </p>
     *
     * @return the operationType
     */
    protected String getOperationType() {
        return operationType;
    }

    /**
     * <p>
     * Setter method for the operationType, simply set the value to the namesake field.
     * </p>
     *
     * @param operationType
     *            The operation type of this action. This is configured for each subclass for auditing purpose
     */
    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    /**
     * <p>
     * This method is called right after the dependency of this class is fully injected. It checks if the
     * injected values are valid.
     * </p>
     *
     * @throws BasicStrutsActionsConfigurationException
     *             if any of the injected values is invalid.
     */
    public void checkConfiguration() {
        // Check the value of the following fields according to their legal value specification in the field
        // variable documentation:
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(operationType, "operationType",
            BasicStrutsActionsConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(webAuthenticationSessionKey,
            "webAuthenticationSessionKey", BasicStrutsActionsConfigurationException.class);
        ValidationUtility.checkNotNull(auditDAO, "auditDAO", BasicStrutsActionsConfigurationException.class);
    }
}
