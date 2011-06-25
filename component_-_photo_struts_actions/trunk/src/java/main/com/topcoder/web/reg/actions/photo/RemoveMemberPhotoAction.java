/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.photo;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import com.topcoder.commons.utils.LoggingWrapperUtility;
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
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.common.model.Email;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.memberphoto.entities.Image;
import com.topcoder.web.memberphoto.manager.MemberPhotoManagementException;
import com.topcoder.web.memberphoto.manager.MemberPhotoManager;

/**
 * This Struts action is responsible for remove profile photo operation. It inherits from BaseMemberPhotoAction and
 * leverages the configured DAOs and managers to perform it's job.
 *
 *
 * Thread Safety:
 * This class is mutable and technically not thread safe. However, when used as Spring bean (which is the
 * expected usage), the properties will be populated before execution of the business methods and thus this class
 * will be thread-safe. Utilized external classes are thread-safe under expected usage (i.e. when using in the
 * Spring framework).
 *
 * @author gevak, mumujava
 * @version 1.0
 */
public class RemoveMemberPhotoAction extends BaseMemberPhotoAction {
    /**
    * <p>
    * The serial version uid.
    * </p>
    */
    private static final long serialVersionUID = 3031582778380377058L;

    /**
     * Object used to generate bodies of the notification emails. Used in execute() method.
     * Mutable, has getter and setter. Technically can be any value. Expected to be injected by Spring and
     *  @PostConstruct method will ensure that it's not null (it will NOT be validated if emailSendFlag is false).
     */
    private DocumentGenerator documentGenerator;

    /**
     * HTTP parameter name under which the authentication data will be looked for. It's used in execute() method.
     * Has default value. Mutable, has getter and setter. Technically can be any value. Expected to be injected by
     * Spring and @PostConstruct method will ensure that it's not null and not empty.
     */
    private String removalReasonParameterName = "removalReason";

    /**
     * Flag indicating whether to send a notification email or not. Used in execute() method.
     * False by default. Mutable, has getter and setter. False by default. Technically can be any value. Expected to
     * be injected by Spring and @PostConstruct method will allow it to any value.
     */
    private boolean emailSendFlag;

    /**
     * Path of the directory where the removed member profile photo images are stored. Used in execute() method.
     * Mutable, has getter and setter. Technically can be any value. Expected to be injected by Spring and
     * @PostConstruct method will ensure that it's not null and not empty.
     */
    private String photoRemovedDirectory;

    /**
     * Points to file which contains a template for generating the notification emails. Used in execute() method.
     * Mutable, has getter and setter. Technically can be any value. Expected to be injected by Spring and
     * @PostConstruct method will ensure that it's not null and not empty (it will NOT be
     * validated if emailSendFlag is false).
     */
    private String emailBodyTemplateFileName;

    /**
     * Subject of the notification emails. Used in execute() method.
     * Mutable, has getter and setter. Technically can be any value. Expected to be injected by Spring and
     * @PostConstruct method will ensure that it's not null and not empty (it will NOT be validated if
     * emailSendFlag is false).
     */
    private String emailSubject;

    /**
     * Value for the "From" address for the notification emails. Used in execute() method.
     * Mutable, has getter and setter. Technically can be any value. Expected to be injected by Spring and
     * @PostConstruct method will ensure that it's not null and not empty (it will NOT be validated
     * if emailSendFlag is false).
     */
    private String fromEmailAddress;
    /**
     * The http request.
     */
    private HttpServletRequest request;

    /**
     * Default empty constructor.
     */
    public RemoveMemberPhotoAction() {
    }

    /**
     * This method will be called by Spring framework after initialization (configuration) of this bean. It validates
     * the configuration parameters.
     *
     * @throws MemberPhotoActionConfigurationException - if any configuration parameter has invalid value as per
     *  class variable docs.
     */
    @Override
    @PostConstruct
    public void checkParameters() {
        super.checkParameters();
        if (emailSendFlag) {
            ValidationUtility.checkNotNull(documentGenerator, "documentGenerator",
                    MemberPhotoActionConfigurationException.class);
            ValidationUtility.checkNotNullNorEmptyAfterTrimming(emailBodyTemplateFileName,
                    "emailBodyTemplateFileName", MemberPhotoActionConfigurationException.class);
            ValidationUtility.checkNotNullNorEmptyAfterTrimming(emailSubject, "emailSubject",
                    MemberPhotoActionConfigurationException.class);
            ValidationUtility.checkNotNullNorEmptyAfterTrimming(fromEmailAddress, "fromEmailAddress",
                    MemberPhotoActionConfigurationException.class);
        }
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(removalReasonParameterName,
                "removalReasonParameterName", MemberPhotoActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(photoRemovedDirectory, "photoRemovedDirectory",
                MemberPhotoActionConfigurationException.class);
    }

    /**
     * This is where the logic of the action is executed. It allows to remove particular member photo.
     *
     * @throws MemberPhotoRemovalException if any error occurs.
     * @return A string representing the logical result of the execution.
     */
    @Override
    public String execute() throws MemberPhotoRemovalException {
        final String methodSignature = "RemoveMemberPhotoAction.execute";
        LoggingWrapperUtility.logEntrance(getLog(), methodSignature, null, null);
        logRequestParameter(request);
        try {
            long userId = getActiveUserId(request, methodSignature);
            // Load User info from DAO:
            User user = getUserDAO().find(userId);
            //Get removal reason explanation from request:
            String removalReason = request.getParameter(removalReasonParameterName);
            MemberPhotoManager memberPhotoManager = getMemberPhotoManager();
            //Get current member photo:
            Image image = memberPhotoManager.getMemberPhoto(userId);
            if (image == null) {
                addActionError("No photo exists for the user");
                throw LoggingWrapperUtility.logException(getLog(), methodSignature,
                        new MemberPhotoRemovalException("No photo exists for the user " + userId));
            }
            //Remove the photo from local directory:
            synchronized (this) {
                move(new File(getPhotoStoredDirectory(), image.getFileName()).getAbsolutePath(), new File(
                        photoRemovedDirectory, image.getFileName()).getAbsolutePath());
            }
            //Remove the photo from DB:
            memberPhotoManager.removeMemberPhoto(userId, String.valueOf(userId));
            //Make audit record:
            AuditRecord auditRecord = new AuditRecord();
            auditRecord.setOperationType("Remove Profile Photo");
            auditRecord.setHandle(user.getHandle());
            auditRecord.setIpAddress(request.getRemoteAddr());
            auditRecord.setPreviousValue(image.getFileName());
            auditRecord.setNewValue(null);
            auditRecord.setTimestamp(new Date());
            AuditDAO auditDAO = getAuditDAO();
            auditDAO.audit(auditRecord);
            //Generate and send notification email:
            if (emailSendFlag) {
                sendNotification(methodSignature, userId, user, removalReason);
            }
        }  catch (MemberPhotoManagementException e) {
            addActionError("MemberPhotoManagementException occurs when removing the photo");
            throw LoggingWrapperUtility.logException(getLog(), methodSignature,
                    new MemberPhotoRemovalException(
                            "MemberPhotoManagementException occurs when removing the photo", e));
        } catch (IOException e) {
            addActionError("IO error occurs when removing the photo");
            throw LoggingWrapperUtility.logException(getLog(), methodSignature,
                    new MemberPhotoRemovalException("IO error occurs when removing the photo", e));
        }
        LoggingWrapperUtility.logExit(getLog(), methodSignature, new Object[]{SUCCESS});
        return SUCCESS;
    }

    /**
     * Sends the notification email.
     *
     * @param methodSignature the method signature
     * @param userId the user id
     * @param user the user
     * @param removalReason the removal reason
     * @throws MemberPhotoRemovalException if any error occurs.
     */
    private void sendNotification(final String methodSignature, long userId, User user, String removalReason)
        throws MemberPhotoRemovalException {
        try {
            // Construct an xml string in the following format, and store the result
            StringBuilder sb = new StringBuilder();
            sb.append("<DATA><memberName>").append(processStr(user.getUserProfile().getFirstName() + " "
                + user.getUserProfile().getLastName())).append("</memberName><memberHandle>").append(
                processStr(user.getHandle())).append("</memberHandle><memberId>").append(userId).append(
                "</memberId><photoRemovalReason>").append((removalReason != null) ? removalReason : "")
                .append("</photoRemovalReason></DATA>");
            // construct email body
            Template template = documentGenerator.getTemplate("file", emailBodyTemplateFileName);
            String emailBody = documentGenerator.applyTemplate(template, sb.toString());
            // send email
            Email userPrimaryAddress = user.getPrimaryEmailAddress();
            if (userPrimaryAddress == null) {
                addActionError("Cannot find the primary email address of the user");
                throw LoggingWrapperUtility.logException(getLog(), methodSignature,
                        new MemberPhotoRemovalException(
                                "Cannot find the primary email address of the user"));
            }
            TCSEmailMessage message = new TCSEmailMessage();
            message.setFromAddress(fromEmailAddress);
            message.setToAddress(userPrimaryAddress.getAddress(), TCSEmailMessage.TO);
            message.setSubject(emailSubject);
            message.setBody(emailBody);
            EmailEngine.send(message);
        } catch (AddressException e) {
            addActionError("AddressException occurs when removing the photo");
            throw LoggingWrapperUtility.logException(getLog(), methodSignature,
                    new MemberPhotoRemovalException("AddressException occurs when removing the photo", e));
        } catch (TemplateSourceException e) {
            addActionError("TemplateSourceException occurs when removing the photo");
            throw LoggingWrapperUtility.logException(getLog(), methodSignature,
                    new MemberPhotoRemovalException("TemplateSourceException occurs when removing the photo",
                            e));
        } catch (ConfigManagerException e) {
            addActionError("ConfigManagerException occurs when removing the photo");
            throw LoggingWrapperUtility.logException(getLog(), methodSignature,
                    new MemberPhotoRemovalException("ConfigManagerException occurs when removing the photo",
                            e));
        } catch (SendingException e) {
            addActionError("SendingException occurs when removing the photo");
            throw LoggingWrapperUtility.logException(getLog(), methodSignature,
                    new MemberPhotoRemovalException("SendingException occurs when removing the photo", e));
        } catch (TemplateFormatException e) {
            addActionError("TemplateFormatException occurs when removing the photo");
            throw LoggingWrapperUtility.logException(getLog(), methodSignature,
                    new MemberPhotoRemovalException("TemplateFormatException occurs when removing the photo",
                            e));
        } catch (TemplateDataFormatException e) {
            addActionError("TemplateDataFormatException occurs when removing the photo");
            throw LoggingWrapperUtility.logException(getLog(), methodSignature,
                    new MemberPhotoRemovalException(
                            "TemplateDataFormatException occurs when removing the photo", e));
        }
    }

    /**
     * Retrieve the current user id from BasicAuthentication.
     *
     * @param request the http request.
     * @param methodSignature the method signature
     * @throws MemberPhotoRemovalException if any error occurs
     * @return the user id
     * */
    private long getActiveUserId(HttpServletRequest request, String methodSignature)
        throws MemberPhotoRemovalException {
        BasicAuthentication auth = (BasicAuthentication) request.getSession().getAttribute(
                getAuthenticationSessionKey());
        if (auth == null) {
            addActionError("The BasicAuthentication should be injected.");
            throw LoggingWrapperUtility.logException(getLog(), methodSignature,
                    new MemberPhotoRemovalException("The BasicAuthentication should be injected for key '"
            		+ getAuthenticationSessionKey() + "'."));
        }
        com.topcoder.shared.security.User activeUser = auth.getActiveUser();
        if (activeUser == null) {
            addActionError("No active user in the session.");
            throw LoggingWrapperUtility.logException(getLog(), methodSignature,
                    new MemberPhotoRemovalException("No active user in the session."));
        }
        long userId = activeUser.getId();
        return userId;
    }

    /**
     * <p>
     * Change null string to empty string.
     * </p>
     * @param input the input string
     * @return the processed string
     */
    private static String processStr(String input) {
        if (input == null) {
            return "";
        } else {
            return input;
        }
    }

    /**
     * Gets Object used to generate bodies of the notification emails.
     *
     * @return Object used to generate bodies of the notification emails.
     */
    public DocumentGenerator getDocumentGenerator() {
        return documentGenerator;
    }

    /**
     * Sets Object used to generate bodies of the notification emails.
     *
     * @param documentGenerator Object used to generate bodies of the notification emails.
     */
    public void setDocumentGenerator(DocumentGenerator documentGenerator) {
        this.documentGenerator = documentGenerator;
    }

    /**
     * Gets HTTP parameter name under which the authentication data will be looked for.
     *
     * @return HTTP parameter name under which the authentication data will be looked for.
     */
    public String getRemovalReasonParameterName() {
        return removalReasonParameterName;
    }

    /**
     * Sets HTTP parameter name under which the authentication data will be looked for.
     *
     * @param removalReasonParameterName HTTP parameter name under which the authentication data will be looked for.
     */
    public void setRemovalReasonParameterName(String removalReasonParameterName) {
        this.removalReasonParameterName = removalReasonParameterName;
    }

    /**
     * Gets Flag indicating whether to send a notification email or not.
     *
     * @return Flag indicating whether to send a notification email or not.
     */
    public boolean isEmailSendFlag() {
        return emailSendFlag;
    }

    /**
     * Sets Flag indicating whether to send a notification email or not.
     *
     * @param emailSendFlag Flag indicating whether to send a notification email or not.
     */
    public void setEmailSendFlag(boolean emailSendFlag) {
        this.emailSendFlag = emailSendFlag;
    }

    /**
     * Gets Path of the directory where the removed member profile photo images are stored.
     *
     * @return Path of the directory where the removed member profile photo images are stored.
     */
    public String getPhotoRemovedDirectory() {
        return photoRemovedDirectory;
    }

    /**
     * Sets Path of the directory where the removed member profile photo images are stored.
     *
     * @param photoRemovedDirectory Path of the directory where the removed member profile photo images are stored.
     */
    public void setPhotoRemovedDirectory(String photoRemovedDirectory) {
        this.photoRemovedDirectory = photoRemovedDirectory;
    }

    /**
     * Gets Points to file which contains a template for generating the notification emails.
     *
     * @return Points to file which contains a template for generating the notification emails.
     */
    public String getEmailBodyTemplateFileName() {
        return emailBodyTemplateFileName;
    }

    /**
     * Sets Points to file which contains a template for generating the notification emails.
     *
     * @param emailBodyTemplateFileName Points to file which contains a template for generating the notification emails.
     */
    public void setEmailBodyTemplateFileName(String emailBodyTemplateFileName) {
        this.emailBodyTemplateFileName = emailBodyTemplateFileName;
    }

    /**
     * Gets Subject of the notification emails.
     *
     * @return Subject of the notification emails.
     */
    public String getEmailSubject() {
        return emailSubject;
    }

    /**
     * Sets Subject of the notification emails.
     *
     * @param emailSubject Subject of the notification emails.
     */
    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    /**
     * Gets Value for the "From" address for the notification emails.
     *
     * @return Value for the "From" address for the notification emails.
     */
    public String getFromEmailAddress() {
        return fromEmailAddress;
    }

    /**
     * Sets Value for the "From" address for the notification emails.
     *
     * @param fromEmailAddress Value for the "From" address for the notification emails.
     */
    public void setFromEmailAddress(String fromEmailAddress) {
        this.fromEmailAddress = fromEmailAddress;
    }

    /**
     * Sets the http servlet request.
     *
     * @param request the http servlet request.
     */
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}
