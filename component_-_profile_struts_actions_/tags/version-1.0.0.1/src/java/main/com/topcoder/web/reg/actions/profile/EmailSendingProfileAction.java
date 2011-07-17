/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import javax.annotation.PostConstruct;

import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.shared.util.EmailEngine;
import com.topcoder.shared.util.TCSEmailMessage;
import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.file.Template;
import com.topcoder.util.file.TemplateDataFormatException;
import com.topcoder.util.file.TemplateFormatException;
import com.topcoder.util.file.templatesource.TemplateSourceException;
import com.topcoder.web.reg.ProfileActionConfigurationException;
import com.topcoder.web.reg.ProfileActionException;

/**
 * <p>
 * This is a base class for the actions that send an email, so it provides common fields and utilities for doing that,
 * including a helper method to generate the body text, package the email message, and dispatch it. The generation of
 * the body text is done with the Document Generator, and the email is sent with the EmailEngine. Also provides a flag
 * whether the email should be sent.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe, but used in thread safe manner by framework.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public abstract class EmailSendingProfileAction extends BaseProfileAction {

    /**
     * <p>
     * Represents sendEmail() method signature constant value.
     * </p>
     */
    private static final String SEND_EMAIL_METHOD_SIGNATURE = "EmailSendingProfileAction.sendEmail()";

    /**
     * <p>
     * This is the DocumentGenerator instance used to generate email bodies from a template.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * On initialization check, must be not null. This field will be injected by the container (expected to be done
     * only once), and is not expected to change afterward.
     * </p>
     */
    private DocumentGenerator documentGenerator;

    /**
     * <p>
     * This is the subject that will be in the email with the recovery directions sent to the user.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * On initialization check, must be not null/empty. This field will be injected by the container (expected to be
     * done only once), and is not expected to change afterward.
     * </p>
     */
    private String emailSubject;

    /**
     * <p>
     * This is the ID source of the template of the body that will be generated for the email with the recovery
     * directions sent to the user.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * It may have any value. Not required in initialization. This field will be injected by the container (expected to
     * be done only once), and is not expected to change afterward.
     * </p>
     */
    private String emailBodyTemplateSourceId;

    /**
     * <p>
     * This is the template of the body that will be generated for the email with the recovery directions sent to the
     * user.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * On initialization check, must be not null/empty. This field will be injected by the container (expected to be
     * done only once), and is not expected to change afterward.
     * </p>
     */
    private String emailBodyTemplateName;

    /**
     * <p>
     * This is the email of the sender that will be in the email with the recovery directions sent to the user.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * On initialization check, must be not null/empty. This field will be injected by the container (expected to be
     * done only once), and is not expected to change afterward.
     * </p>
     */
    private String emailSender;

    /**
     * <p>
     * This is the flag as to whether an email should be sent. Default is true.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * It may have any value. This field will be injected by the container (expected to be done only once), and is not
     * expected to change afterward.
     * </p>
     */
    private boolean sendEmail = true;

    /**
     * <p>
     * Creates an instance of EmailSendingProfileAction.
     * </p>
     */
    protected EmailSendingProfileAction() {
    }

    /**
     * <p>
     * Checks that all required values have been injected by the system right after construction and injection.
     * </p>
     * <p>
     * This is used to obviate the need to check these values each time in the execute method.
     * </p>
     * @throws ProfileActionConfigurationException - If any required value has not been injected into this class
     */
    @Override
    @PostConstruct
    protected void checkInitialization() {
        super.checkInitialization();
        ValidationUtility.checkNotNull(documentGenerator, "documentGenerator",
                ProfileActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(emailSubject, "emailSubject",
                ProfileActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(emailBodyTemplateName, "emailBodyTemplateName",
                ProfileActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(emailSender, "emailSender",
                ProfileActionConfigurationException.class);
    }

    /**
     * <p>
     * Generates an email from the passed parameters and dispatches it.
     * </p>
     * @param emailBodyTemplateData the data to put into the template body
     * @param email the receiver email address
     * @throws ProfileActionException - If there are any errors in the email process
     */
    protected void sendEmail(String email, String emailBodyTemplateData) throws ProfileActionException {
        // Generate email message:
        Template template;
        try {
            // Get Template:
            template =
                    (this.emailBodyTemplateSourceId != null ? this.documentGenerator.getTemplate(
                            emailBodyTemplateSourceId, emailBodyTemplateName) : this.documentGenerator
                            .getTemplate(emailBodyTemplateName));
            // Generate message text:
            String body = this.documentGenerator.applyTemplate(template, emailBodyTemplateData);
            // Prepare email message: create a new TCSEmailMessage()
            TCSEmailMessage emailMessage = new TCSEmailMessage();
            emailMessage.setFromAddress(emailSender);
            emailMessage.setBody(body);
            emailMessage.setToAddress(email, TCSEmailMessage.TO);
            emailMessage.setSubject(emailSubject);
            // send email:
            EmailEngine.send(emailMessage);
        } catch (TemplateFormatException e) {
            throw Helper.logAndWrapException(getLogger(), SEND_EMAIL_METHOD_SIGNATURE,
                    "Template format error is found applying the template.", e);
        } catch (TemplateDataFormatException e) {
            throw Helper.logAndWrapException(getLogger(), SEND_EMAIL_METHOD_SIGNATURE,
                    "The template data is invalid .", e);
        } catch (TemplateSourceException e) {
            throw Helper.logAndWrapException(getLogger(), SEND_EMAIL_METHOD_SIGNATURE,
                    "Template source id is invalid.", e);
        } catch (Exception e) {
            throw Helper.logAndWrapException(getLogger(), SEND_EMAIL_METHOD_SIGNATURE,
                    "Error occurred while sending email.", e);
        }
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public DocumentGenerator getDocumentGenerator() {
        return documentGenerator;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param documentGenerator the namesake field instance value to set
     */
    public void setDocumentGenerator(DocumentGenerator documentGenerator) {
        this.documentGenerator = documentGenerator;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public String getEmailSubject() {
        return emailSubject;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param emailSubject the namesake field instance value to set
     */
    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public String getEmailBodyTemplateSourceId() {
        return emailBodyTemplateSourceId;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param emailBodyTemplateSourceId the namesake field instance value to set
     */
    public void setEmailBodyTemplateSourceId(String emailBodyTemplateSourceId) {
        this.emailBodyTemplateSourceId = emailBodyTemplateSourceId;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public String getEmailBodyTemplateName() {
        return emailBodyTemplateName;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param emailBodyTemplateName the namesake field instance value to set
     */
    public void setEmailBodyTemplateName(String emailBodyTemplateName) {
        this.emailBodyTemplateName = emailBodyTemplateName;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public String getEmailSender() {
        return emailSender;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param emailSender the namesake field instance value to set
     */
    public void setEmailSender(String emailSender) {
        this.emailSender = emailSender;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public boolean isSendEmail() {
        return sendEmail;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param sendEmail the namesake field instance value to set
     */
    public void setSendEmail(boolean sendEmail) {
        this.sendEmail = sendEmail;
    }
}