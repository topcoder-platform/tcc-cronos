/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.message.email.AddressException;
import com.topcoder.message.email.EmailEngine;
import com.topcoder.message.email.SendingException;
import com.topcoder.message.email.TCSEmailMessage;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.file.Template;
import com.topcoder.util.file.TemplateDataFormatException;
import com.topcoder.util.file.TemplateFormatException;
import com.topcoder.util.file.templatesource.TemplateSourceException;
import com.topcoder.util.log.Log;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.security.BasicAuthentication;

/**
 * <p>
 * This is the base class for all actions in this component. It aggregates the common variables and provides methods to
 * get the logged in user and to send the notification email.
 * </p>
 * <p>
 * All its instance variables have public setters/getters and will be injected.
 * </p>
 * <p>
 * Thread safety: This class is mutable and not thread safe, however will be used thread-safely in Struts framework.
 * </p>
 * @author maksimilian, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public abstract class BasePreferencesAction extends ActionSupport implements Preparable {

    /**
     * <p>
     * Represents sendEmail() method signature.
     * </p>
     */
    private static final String SEND_EMAIL_METHOD_SIGNATURE = "BasePreferencesAction.sendEmail()";

    /**
     * <p>
     * Represents getLoggedInUser() method signature.
     * </p>
     */
    private static final String GET_LOGGED_IN_USER_METHOD_SIGNATURE = "BasePreferencesAction.getLoggedInUser()";

    /**
     * <p>
     * Represents default action value.
     * </p>
     */
    private static final String DEFAULT_ACTION_VALUE = "view";

    /**
     * <p>
     * Represents the input parameter that indicates the type of the action.
     * </p>
     * <p>
     * Can be null (in that case action will be treated as "view"). It is used in corresponding getter/setter and
     * execute method. Used in getter/setter and execute method.
     * </p>
     */
    private String action;

    /**
     * <p>
     * Represents the audit DAO which will be used to perform audit operations.
     * </p>
     * <p>
     * It's injected by the container. After injection, it must be non-null. Used in getter/setter.
     * </p>
     */
    private AuditDAO auditDao;

    /**
     * <p>
     * Represents the backup key that will be used to store the backup object in the session in case user discard the
     * changes.
     * </p>
     * <p>
     * It's injected by the container. After injection, it must be non-null, non-empty string. Used in getter/setter
     * and execute method.
     * </p>
     */
    private String backupSessionKey;

    /**
     * <p>
     * Represents the key for the BasicAuthentication instance in session.
     * </p>
     * <p>
     * It's injected by the container. After injection, it must be non-null, non-empty string. Used in getter/setter.
     * </p>
     */
    private String basicAuthenticationSessionKey;

    /**
     * <p>
     * Represents the document generator to generate the email body from template.
     * </p>
     * <p>
     * It's injected by the container. After injection, it must be non-null. Used in getter/setter and execute method.
     * </p>
     */
    private DocumentGenerator documentGenerator;

    /**
     * <p>
     * Represents the file containing the template data for the email body of the notification email.
     * </p>
     * <p>
     * It's injected by the container. After injection, it must be non-null, non-empty string. Used in getter/setter
     * and execute method.
     * </p>
     */
    private String emailBodyTemplateFileName;

    /**
     * <p>
     * Represents the flag that controls if the email notification is sent or not.
     * </p>
     * <p>
     * It's injected by the container. After injection, it must be boolean. Used in getter/setter and execute method.
     * </p>
     */
    private boolean emailSendFlag;

    /**
     * <p>
     * Represents the subject of the notification email.
     * </p>
     * <p>
     * It's injected by the container. After injection, it must be non-null, non-empty string. Used in getter/setter
     * and execute method.
     * </p>
     */
    private String emailSubject;

    /**
     * <p>
     * Represents the email body text.
     * </p>
     * <p>
     * It's injected by the container. After injection, it must be non-null, non-empty string. Used in getter/setter
     * and execute method.
     * </p>
     */
    private String emailText;

    /**
     * <p>
     * Represents the from email address of the notification email. It's injected by the container.
     * </p>
     * <p>
     * After injection, it must be non-null, non-empty string. Used in getter/setter and execute method.
     * </p>
     */
    private String fromEmailAddress;

    /**
     * <p>
     * Represents the logger which will be used to perform logging operations.
     * </p>
     * <p>
     * It's injected by the container. After injection, it must be non-null. Used in getter/setter.
     * </p>
     */
    private Log logger;

    /**
     * <p>
     * Represents the user DAO which will be used to perform user related operations.
     * </p>
     * <p>
     * It's injected by the container. After injection, it must be non-null. Used in getter/setter.
     * </p>
     */
    private UserDAO userDao;

    /**
     * <p>
     * Protected constructor.
     * </p>
     */
    protected BasePreferencesAction() {
        super();
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the auditDao
     */
    public AuditDAO getAuditDao() {
        return auditDao;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the backupSessionKey
     */
    public String getBackupSessionKey() {
        return backupSessionKey;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the basicAuthenticationSessionKey
     */
    public String getBasicAuthenticationSessionKey() {
        return basicAuthenticationSessionKey;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the documentGenerator
     */
    public DocumentGenerator getDocumentGenerator() {
        return documentGenerator;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the emailBodyTemplateFileName
     */
    public String getEmailBodyTemplateFileName() {
        return emailBodyTemplateFileName;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the emailSubject
     */
    public String getEmailSubject() {
        return emailSubject;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the emailText
     */
    public String getEmailText() {
        return emailText;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the fromEmailAddress
     */
    public String getFromEmailAddress() {
        return fromEmailAddress;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the logger
     */
    public Log getLogger() {
        return logger;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the userDao
     */
    public UserDAO getUserDao() {
        return userDao;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the emailSendFlag
     */
    public boolean isEmailSendFlag() {
        return emailSendFlag;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param auditDao the auditDao to set
     */
    public void setAuditDao(AuditDAO auditDao) {
        this.auditDao = auditDao;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param backupSessionKey the backupSessionKey to set
     */
    public void setBackupSessionKey(String backupSessionKey) {
        this.backupSessionKey = backupSessionKey;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param basicAuthenticationSessionKey the basicAuthenticationSessionKey to set
     */
    public void setBasicAuthenticationSessionKey(String basicAuthenticationSessionKey) {
        this.basicAuthenticationSessionKey = basicAuthenticationSessionKey;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param documentGenerator the documentGenerator to set
     */
    public void setDocumentGenerator(DocumentGenerator documentGenerator) {
        this.documentGenerator = documentGenerator;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param emailBodyTemplateFileName the emailBodyTemplateFileName to set
     */
    public void setEmailBodyTemplateFileName(String emailBodyTemplateFileName) {
        this.emailBodyTemplateFileName = emailBodyTemplateFileName;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param emailSendFlag the emailSendFlag to set
     */
    public void setEmailSendFlag(boolean emailSendFlag) {
        this.emailSendFlag = emailSendFlag;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param emailSubject the emailSubject to set
     */
    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param emailText the emailText to set
     */
    public void setEmailText(String emailText) {
        this.emailText = emailText;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param fromEmailAddress the fromEmailAddress to set
     */
    public void setFromEmailAddress(String fromEmailAddress) {
        this.fromEmailAddress = fromEmailAddress;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param logger the logger to set
     */
    public void setLogger(Log logger) {
        this.logger = logger;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param userDao the userDao to set
     */
    public void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }

    /**
     * <p>
     * Escapes characters in given string.
     * </p>
     * @param value the string to be escaped
     * @return string with escaped characters
     */
    private String processStr(String value) {
        return value == null ? value : value.replace("&", "&amp;").replace("'", "&apos;").replace("\"", "&quot;")
                .replace("<", "&lt;").replace(">", "&gt;");
    }

    /**
     * <p>
     * Performs audit.
     * </p>
     * @param newValue the new value
     * @param operationType the operation type
     * @param oldValue the old value
     * @throws UserPreferencesActionExecutionException if any error occurs in this method
     */
    protected void audit(String oldValue, String newValue, String operationType)
        throws UserPreferencesActionExecutionException {
        // get HTTP request:
        HttpServletRequest request = ServletActionContext.getRequest();
        // get logged in user
        User user = getLoggedInUser();
        // create the audit record and audit using audit DAO:
        AuditRecord auditRecord = new AuditRecord();
        auditRecord.setOperationType(operationType);
        auditRecord.setHandle(user.getHandle());
        auditRecord.setIpAddress(request.getRemoteAddr());
        auditRecord.setPreviousValue(oldValue);
        auditRecord.setNewValue(newValue);
        auditRecord.setTimestamp(new Date());
        auditDao.audit(auditRecord);
    }

    /**
     * <p>
     * Returns the logged in user.
     * </p>
     * <p>
     * This method gets the com.topcoder.shared.security.User from session, gets its id and then gets the
     * com.topcoder.web.common.model.User by this id.
     * </p>
     * @return the logged in user
     * @throws UserPreferencesActionExecutionException if any error while error while getting logged user
     */
    protected User getLoggedInUser() throws UserPreferencesActionExecutionException {
        Object obj = ActionContext.getContext().getSession().get(basicAuthenticationSessionKey);
        if (obj == null) {
            throw Helper.logAndWrapException(logger, GET_LOGGED_IN_USER_METHOD_SIGNATURE,
                    "Authentication is not present in session for key " + basicAuthenticationSessionKey, null,
                    UserPreferencesActionExecutionException.class);
        }
        if (!(obj instanceof BasicAuthentication)) {
            throw Helper.logAndWrapException(logger, GET_LOGGED_IN_USER_METHOD_SIGNATURE,
                    "Authentication object in session should be BasicAuthentication class. Actual class "
                            + obj.getClass().getName(), null, UserPreferencesActionExecutionException.class);
        }
        BasicAuthentication auth = (BasicAuthentication) obj;
        Long userId = auth.getActiveUser().getId();
        User user = userDao.find(userId);
        // check if user is found
        if (user == null) {
            throw Helper.logAndWrapException(getLogger(), GET_LOGGED_IN_USER_METHOD_SIGNATURE,
                    "User is not found for given id.", null, UserPreferencesActionExecutionException.class);
        }
        return user;
    }

    /**
     * <p>
     * Sends the email.
     * </p>
     * @throws UserPreferencesActionExecutionException if any error occurs in this method
     */
    protected void sendEmail() throws UserPreferencesActionExecutionException {
        // get user:
        User user = getLoggedInUser();
        // send email:
        // constructor an xml string in the following format, and store
        // the result into bodyTemplateData local variable.
        // <data>
        // <memberName>{name}</memberName>
        // <memberHandle>{handle}</memberHandle>
        // <memberId>{memberId}</memberId>
        // <emailText>{emailText}</emailText>
        // </data>
        StringBuilder sb = new StringBuilder();
        sb.append("<DATA><memberName>");
        sb.append(processStr(user.getFirstName() + " " + user.getLastName()));
        sb.append("</memberName><memberHandle>");
        sb.append(processStr(user.getHandle()));
        sb.append("</memberHandle><memberId>");
        sb.append(user.getId());
        sb.append("</memberId><emailText>");
        sb.append(processStr(emailText));
        sb.append("</emailText></DATA>");
        String bodyTemplateData = sb.toString();
        // constructor email body
        Template template = null;
        try {
            template = documentGenerator.getTemplate("file", emailBodyTemplateFileName);
            String emailBody = documentGenerator.applyTemplate(template, bodyTemplateData);
            // Send notification
            TCSEmailMessage message = new TCSEmailMessage();
            message.setFromAddress(fromEmailAddress);
            // get the user's primary email address from or null if it's not present
            String emailAdress =
                    (user.getPrimaryEmailAddress() == null ? null : user.getPrimaryEmailAddress().getAddress());
            if (emailAdress == null) {
                throw Helper.logAndWrapException(logger, SEND_EMAIL_METHOD_SIGNATURE,
                        "User's primary address should not be null.", null,
                        UserPreferencesActionExecutionException.class);
            }
            message.setToAddress(emailAdress, TCSEmailMessage.TO);
            message.setSubject(emailSubject);
            message.setBody(emailBody);
            // send email to primary address
            EmailEngine.send(message);
        } catch (IllegalArgumentException e) {
            throw Helper.logAndWrapException(logger, SEND_EMAIL_METHOD_SIGNATURE,
                    "Error occurs when getTemplate:template is empty.", e,
                    UserPreferencesActionExecutionException.class);
        } catch (TemplateSourceException e) {
            throw Helper
                    .logAndWrapException(logger, SEND_EMAIL_METHOD_SIGNATURE,
                            "The template cannot be loaded from the source.", e,
                            UserPreferencesActionExecutionException.class);
        } catch (TemplateFormatException e) {
            throw Helper.logAndWrapException(logger, SEND_EMAIL_METHOD_SIGNATURE,
                    "Template format error is found applying the template.", e,
                    UserPreferencesActionExecutionException.class);
        } catch (AddressException e) {
            throw Helper.logAndWrapException(logger, SEND_EMAIL_METHOD_SIGNATURE, "Address exception occurred.", e,
                    UserPreferencesActionExecutionException.class);
        } catch (ConfigManagerException e) {
            throw Helper.logAndWrapException(logger, SEND_EMAIL_METHOD_SIGNATURE,
                    "Config manager exception occurred.", e, UserPreferencesActionExecutionException.class);
        } catch (SendingException e) {
            throw Helper.logAndWrapException(logger, SEND_EMAIL_METHOD_SIGNATURE, "Sending exception occurred.", e,
                    UserPreferencesActionExecutionException.class);
        } catch (TemplateDataFormatException e) {
            throw Helper.logAndWrapException(logger, SEND_EMAIL_METHOD_SIGNATURE, "The template data is invalid .", e,
                    UserPreferencesActionExecutionException.class);
        }
    }

    /**
     * <p>
     * Checks injected fields on valid values.
     * </p>
     * @throws Exception if any injected field has invalid value
     */
    public void prepare() throws Exception {
        ValidationUtility.checkNotNull(auditDao, "auditDAO", UserPreferencesActionConfigurationException.class);
        ValidationUtility.checkNotNull(userDao, "userDao", UserPreferencesActionConfigurationException.class);
        ValidationUtility.checkNotNull(documentGenerator, "documentGenerator",
                UserPreferencesActionConfigurationException.class);
        ValidationUtility.checkNotNull(logger, "logger", UserPreferencesActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(backupSessionKey, "backupSessionKey",
                UserPreferencesActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(basicAuthenticationSessionKey,
                "basicAuthenticationSessionKey", UserPreferencesActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(emailBodyTemplateFileName, "emailBodyTemplateFileName",
                UserPreferencesActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(emailSubject, "emailSubject",
                UserPreferencesActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(emailText, "emailText",
                UserPreferencesActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(fromEmailAddress, "fromEmailAddress",
                UserPreferencesActionConfigurationException.class);
        if (action == null || action.trim().isEmpty()) {
            // user default value
            action = DEFAULT_ACTION_VALUE;
        }
    }
}
