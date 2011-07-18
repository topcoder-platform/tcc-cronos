/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.registration;

import javax.annotation.PostConstruct;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.shared.util.EmailEngine;
import com.topcoder.shared.util.TCSEmailMessage;
import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.file.Template;
import com.topcoder.util.file.TemplateDataFormatException;
import com.topcoder.util.file.TemplateFormatException;
import com.topcoder.util.file.templatesource.TemplateSourceException;
import com.topcoder.web.common.TCWebException;
import com.topcoder.web.reg.RegistrationActionConfigurationException;

/**
 * <p>
 * This is a base class for the actions that send an email, so it provides common fields and utilities for
 * doing that, including a helper method to generate the body text, package the email message, and dispatch
 * it. The generation of the body text is done with the Document Generator, and the email is sent with the
 * EmailEngine.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe, but used in thread safe manner by framework.
 * </p>
 *
 * @author argolite, stevenfrog
 * @version 1.0
 */
public abstract class EmailSendingAction extends BaseRegistrationAction {
    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = EmailSendingAction.class.getName();

    /**
     * <p>
     * This is the DocumentGenerator instance used to generate email bodies from a template. It is set in the
     * setter. It can be retrieved in the getter. It may have any value. This field will be injected by the
     * container (expected to be done only once), and is not expected to change afterward.
     * </p>
     */
    private DocumentGenerator documentGenerator;

    /**
     * <p>
     * This is the subject that will be in the email with the recovery directions sent to the user. It is set
     * in the setter. It can be retrieved in the getter. It may have any value. This field will be injected by
     * the container (expected to be done only once), and is not expected to change afterward.
     * </p>
     */
    private String emailSubject;

    /**
     * <p>
     * This is the source ID of the template of the body that will be generated for the email with the
     * recovery directions sent to the user. It is set in the setter. It can be retrieved in the getter. It
     * may have any value. This field will be injected by the container (expected to be done only once), and
     * is not expected to change afterward.
     * </p>
     */
    private String emailBodyTemplateSourceId;

    /**
     * <p>
     * This is the template of the body that will be generated for the email with the recovery directions sent
     * to the user. It is set in the setter. It can be retrieved in the getter. It may have any value. This
     * field will be injected by the container (expected to be done only once), and is not expected to change
     * afterward.
     * </p>
     */
    private String emailBodyTemplateName;

    /**
     * <p>
     * This is the email of the sender that will be in the email with the recovery directions sent to the
     * user. It is set in the setter. It can be retrieved in the getter. It may have any value. This field
     * will be injected by the container (expected to be done only once), and is not expected to change
     * afterward.
     * </p>
     */
    private String emailSender;

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    protected EmailSendingAction() {
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
    @Override
    @PostConstruct
    protected void checkInitialization() {
        super.checkInitialization();
        ValidationUtility.checkNotNull(documentGenerator, "documentGenerator",
            RegistrationActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(emailSubject, "emailSubject",
            RegistrationActionConfigurationException.class);
        ValidationUtility.checkNotEmpty(emailBodyTemplateSourceId, "emailBodyTemplateSourceId",
            RegistrationActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(emailBodyTemplateName, "emailBodyTemplateName",
            RegistrationActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(emailSender, "emailSender",
            RegistrationActionConfigurationException.class);
    }

    /**
     * <p>
     * This method generates an email from the passed parameters and dispatches it.
     * </p>
     *
     * @param emailBodyTemplateData
     *            the data to put into the template body
     * @param email
     *            the receiver email address
     * @throws TCWebException
     *             If there are any errors in the email process
     */
    protected void sendEmail(String email, String emailBodyTemplateData) throws TCWebException {
        // Log the entry
        final String signature = CLASS_NAME + ".sendEmail(String, String)";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, new String[] {"email",
            "emailBodyTemplateData"}, new Object[] {email, emailBodyTemplateData});

        try {
            // Generate email message:
            // Get Template:
            Template template;
            if (emailBodyTemplateSourceId != null) {
                template = documentGenerator.getTemplate(emailBodyTemplateSourceId, emailBodyTemplateName);
            } else {
                template = documentGenerator.getTemplate(emailBodyTemplateName);
            }
            // Generate message text:
            String body = documentGenerator.applyTemplate(template, emailBodyTemplateData);

            // Prepare email message:
            TCSEmailMessage message = new TCSEmailMessage();
            // set fromAddress to this.emailSender
            message.setFromAddress(emailSender);
            // set body to body
            message.setBody(body);
            // set toAddress to email
            message.setToAddress(email, TCSEmailMessage.TO);
            // set subject to this.emailSubject
            message.setSubject(emailSubject);

            // send email
            EmailEngine.send(message);

            // Log the exit
            LoggingWrapperUtility.logExit(getLogger(), signature, null);
        } catch (TemplateSourceException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new TCWebException(
                "TemplateSourceException occurs while applying template", e));
        } catch (TemplateFormatException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new TCWebException(
                "TemplateFormatException occurs while applying template", e));
        } catch (TemplateDataFormatException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new TCWebException(
                "TemplateDataFormatException occurs while applying template", e));
        } catch (Exception e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new TCWebException(
                "Exception occurs while sending email", e));
        }
    }

    /**
     * <p>
     * Getter method for documentGenerator, simply return the value of the namesake field.
     * </p>
     *
     * @return the documentGenerator
     */
    public DocumentGenerator getDocumentGenerator() {
        return documentGenerator;
    }

    /**
     * <p>
     * Setter method for the documentGenerator, simply set the value to the namesake field.
     * </p>
     *
     * @param documentGenerator
     *            the documentGenerator to set
     */
    public void setDocumentGenerator(DocumentGenerator documentGenerator) {
        this.documentGenerator = documentGenerator;
    }

    /**
     * <p>
     * Getter method for emailSubject, simply return the value of the namesake field.
     * </p>
     *
     * @return the emailSubject
     */
    public String getEmailSubject() {
        return emailSubject;
    }

    /**
     * <p>
     * Setter method for the emailSubject, simply set the value to the namesake field.
     * </p>
     *
     * @param emailSubject
     *            the emailSubject to set
     */
    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    /**
     * <p>
     * Getter method for emailBodyTemplateSourceId, simply return the value of the namesake field.
     * </p>
     *
     * @return the emailBodyTemplateSourceId
     */
    public String getEmailBodyTemplateSourceId() {
        return emailBodyTemplateSourceId;
    }

    /**
     * <p>
     * Setter method for the emailBodyTemplateSourceId, simply set the value to the namesake field.
     * </p>
     *
     * @param emailBodyTemplateSourceId
     *            the emailBodyTemplateSourceId to set
     */
    public void setEmailBodyTemplateSourceId(String emailBodyTemplateSourceId) {
        this.emailBodyTemplateSourceId = emailBodyTemplateSourceId;
    }

    /**
     * <p>
     * Getter method for emailBodyTemplateName, simply return the value of the namesake field.
     * </p>
     *
     * @return the emailBodyTemplateName
     */
    public String getEmailBodyTemplateName() {
        return emailBodyTemplateName;
    }

    /**
     * <p>
     * Setter method for the emailBodyTemplateName, simply set the value to the namesake field.
     * </p>
     *
     * @param emailBodyTemplateName
     *            the emailBodyTemplateName to set
     */
    public void setEmailBodyTemplateName(String emailBodyTemplateName) {
        this.emailBodyTemplateName = emailBodyTemplateName;
    }

    /**
     * <p>
     * Getter method for emailSender, simply return the value of the namesake field.
     * </p>
     *
     * @return the emailSender
     */
    public String getEmailSender() {
        return emailSender;
    }

    /**
     * <p>
     * Setter method for the emailSender, simply set the value to the namesake field.
     * </p>
     *
     * @param emailSender
     *            the emailSender to set
     */
    public void setEmailSender(String emailSender) {
        this.emailSender = emailSender;
    }

}
