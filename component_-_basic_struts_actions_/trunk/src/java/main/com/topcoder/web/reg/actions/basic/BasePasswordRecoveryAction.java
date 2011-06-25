/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.basic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;
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
import com.topcoder.util.file.fieldconfig.Node;
import com.topcoder.util.file.fieldconfig.NodeList;
import com.topcoder.util.file.fieldconfig.NodeListUtility;
import com.topcoder.util.file.fieldconfig.TemplateFields;
import com.topcoder.util.file.templatesource.FileTemplateSource;
import com.topcoder.util.file.templatesource.TemplateSource;
import com.topcoder.util.file.templatesource.TemplateSourceException;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.common.model.PasswordRecovery;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.BasicStrutsActionsConfigurationException;

/**
 * <p>
 * This is the base action of actions related to password recovery. It has a method to send password recovery
 * email.
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
public abstract class BasePasswordRecoveryAction extends BasePasswordAction {
    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = BasePasswordRecoveryAction.class.getName();

    /**
     * <p>
     * The Log object used for logging. It's a constant. So it can only be that constant value It is final and
     * won't change once it is initialized as part of variable declaration to:
     * LogManager.getLog(BasePasswordRecoveryAction.class.toString()). It is used throughout the class
     * wherever logging is needed.
     * </p>
     */
    private static final Log LOG = LogManager.getLog(BasePasswordRecoveryAction.class.toString());

    /**
     * <p>
     * Represent the buffer size.
     * </p>
     */
    private static final int BUFFER_SIZE = 1024;

    /**
     * <p>
     * The email subject of the password recovery email. It is set through setter and doesn't have a getter.
     * It cannot be null but can be empty. (Note that the above statement applies only after the setter has
     * been called as part of the IoC initialization. This field's value has no restriction before the IoC
     * initialization) It does not need to be initialized when the instance is created. It is used in
     * setEmailSubject(), sendPasswordRecoveryEmail().
     * </p>
     */
    private String emailSubject;

    /**
     * <p>
     * The file path of the email body template. The template should be in the format of Document Generator
     * component. The template must contain two variables '%handle%' and '%link%'. It is set through setter
     * and doesn't have a getter. It cannot be null or empty. (Note that the above statement applies only
     * after the setter has been called as part of the IoC initialization. This field's value has no
     * restriction before the IoC initialization) It does not need to be initialized when the instance is
     * created. It is used in sendPasswordRecoveryEmail(), setEmailBodyTemplateFilePath().
     * </p>
     */
    private String emailBodyTemplateFilePath;

    /**
     * <p>
     * The email from address of the password recovery email. It is set through setter and doesn't have a
     * getter. It cannot be null or empty. (Note that the above statement applies only after the setter has
     * been called as part of the IoC initialization. This field's value has no restriction before the IoC
     * initialization) It does not need to be initialized when the instance is created. It is used in
     * sendPasswordRecoveryEmail(), setEmailFromAddress().
     * </p>
     */
    private String emailFromAddress;

    /**
     * <p>
     * The template of the link for resetting password. It is set through setter and doesn't have a getter. It
     * cannot be null or empty. It must have two variables '%passwordRecoveryId%' and '%hashCode%' in its
     * content, and the content must have 2 http parameters "passwordRecoveryId=%passwordRecoveryId%" and
     * "hashCode=%hashCode%". (Note that the above statement applies only after the setter has been called as
     * part of the IoC initialization. This field's value has no restriction before the IoC initialization) It
     * does not need to be initialized when the instance is created. It is used in
     * sendPasswordRecoveryEmail(), setResetPasswordLinkTemplate().
     * </p>
     */
    private String resetPasswordLinkTemplate;

    /**
     * <p>
     * This is the default constructor for the class.
     * </p>
     */
    protected BasePasswordRecoveryAction() {
        // Empty
    }

    /**
     * <p>
     * Send the password recovery email to the user.
     * </p>
     *
     * @param passwordRecovery
     *            the PasswordRecovery containing information of the email
     * @throws IllegalArgumentException
     *             if passwordRecovery is null
     * @throws BasicActionException
     *             if any error occurs
     */
    protected void sendPasswordRecoveryEmail(PasswordRecovery passwordRecovery) throws BasicActionException {
        // Log the entry
        final String signature = CLASS_NAME + ".sendPasswordRecoveryEmail(PasswordRecovery)";
        LoggingWrapperUtility.logEntrance(LOG, signature, new String[] {"passwordRecovery"},
            new Object[] {passwordRecovery});

        ParameterCheckUtility.checkNotNull(passwordRecovery, "passwordRecovery");

        try {
            // Create a new message:
            TCSEmailMessage mail = new TCSEmailMessage();
            // Set the subject:
            mail.setSubject(emailSubject);
            // Fill in the variables in the link template:
            String link = resetPasswordLinkTemplate.replace("%passwordRecoveryId%",
                passwordRecovery.getId().toString()).replace("%hashCode%", passwordRecovery.hash());
            // Get the user from passwordRecovery:
            User user = passwordRecovery.getUser();
            // Create a DocumentGenerator:
            DocumentGenerator documentGenerator = new DocumentGenerator();
            // Create a FileTemplateSource:
            TemplateSource templateSource = new FileTemplateSource();
            // Set the default template source:
            documentGenerator.setDefaultTemplateSource(templateSource);
            // Get the template from the file path:

            Template template = documentGenerator.getTemplate(this.emailBodyTemplateFilePath);
            // Get the template fields:
            TemplateFields root = documentGenerator.getFields(template);
            Node[] nodes = root.getNodes();
            NodeList nodeList = new NodeList(nodes);
            // This map will store the template variable values:
            Map<String, String> variableValues = new HashMap<String, String>();
            variableValues.put("handle", user.getHandle());
            variableValues.put("link", link);
            // Populate the template fields:
            NodeListUtility.populateNodeList(nodeList, variableValues);
            // Generate the body using DocumentGenerator:
            String body = documentGenerator.applyTemplate(root);
            // Set the mail body:
            mail.setBody(body);
            String recoveryAddress = passwordRecovery.getRecoveryAddress();
            if (recoveryAddress == null) {
                throw new BasicActionException(
                    "The recovery address can not be null, it's in PasswordRecovery with id:"
                        + passwordRecovery.getId());
            }
            // Set the to address:
            mail.setToAddress(recoveryAddress, TCSEmailMessage.TO);
            // Set the from address:
            mail.setFromAddress(emailFromAddress);
            // Send the mail:
            EmailEngine.send(mail);

            // Log the exit
            LoggingWrapperUtility.logExit(LOG, signature, null);
        } catch (TemplateSourceException e) {
            throw new BasicActionException("TemplateSourceException occurs while getting template", e);
        } catch (TemplateFormatException e) {
            throw new BasicActionException("TemplateFormatException occurs while getting template", e);
        } catch (TemplateDataFormatException e) {
            throw new BasicActionException("TemplateDataFormatException occurs while applying template", e);
        } catch (AddressException e) {
            throw new BasicActionException("AddressException occurs while setting address", e);
        } catch (ConfigManagerException e) {
            throw new BasicActionException("ConfigManagerException occurs while sending email", e);
        } catch (SendingException e) {
            throw new BasicActionException("SendingException occurs while sending email", e);
        }
    }

    /**
     * <p>
     * Setter method for the emailSubject, simply set the value to the namesake field.
     * </p>
     *
     * @param emailSubject
     *            The email subject of the password recovery email
     */
    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    /**
     * <p>
     * Setter method for the emailBodyTemplateFilePath, simply set the value to the namesake field.
     * </p>
     *
     * @param emailBodyTemplateFilePath
     *            The file path of the email body template. The template should be in the format of Document
     *            Generator component. The template must contain two variables 'handle' and 'link'
     */
    public void setEmailBodyTemplateFilePath(String emailBodyTemplateFilePath) {
        this.emailBodyTemplateFilePath = emailBodyTemplateFilePath;
    }

    /**
     * <p>
     * Setter method for the emailFromAddress, simply set the value to the namesake field.
     * </p>
     *
     * @param emailFromAddress
     *            The email from address of the password recovery email
     */
    public void setEmailFromAddress(String emailFromAddress) {
        this.emailFromAddress = emailFromAddress;
    }

    /**
     * <p>
     * Setter method for the resetPasswordLinkTemplate, simply set the value to the namesake field.
     * </p>
     *
     * @param resetPasswordLinkTemplate
     *            The template of the link for resetting password
     */
    public void setResetPasswordLinkTemplate(String resetPasswordLinkTemplate) {
        this.resetPasswordLinkTemplate = resetPasswordLinkTemplate;
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
    @Override
    public void checkConfiguration() {
        super.checkConfiguration();
        // Check the value of the following fields according to their legal value specification in the field
        // variable documentation:
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(emailBodyTemplateFilePath,
            "emailBodyTemplateFilePath", BasicStrutsActionsConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(emailFromAddress, "emailFromAddress",
            BasicStrutsActionsConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(emailSubject, "emailSubject",
            BasicStrutsActionsConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(resetPasswordLinkTemplate,
            "resetPasswordLinkTemplate", BasicStrutsActionsConfigurationException.class);

        try {
            String emailBody = readFileContent(new File(emailBodyTemplateFilePath));
            if (emailBody.indexOf("%handle%") == -1 || emailBody.indexOf("%link%") == -1) {
                throw new BasicStrutsActionsConfigurationException(
                    "The content of emailBodyTemplateFilePath must contain two variables '%handle%' and '%link%'");
            }
            if (resetPasswordLinkTemplate.indexOf("%passwordRecoveryId%") == -1
                || resetPasswordLinkTemplate.indexOf("%hashCode%") == -1) {
                throw new BasicStrutsActionsConfigurationException(
                    "The resetPasswordLinkTemplate must contain two variables '%passwordRecoveryId%' and '%hashCode%'");
            }
        } catch (IOException e) {
            throw new BasicStrutsActionsConfigurationException("IOException occurs while reading file", e);
        }
    }

    /**
     * <p>
     * Read the content form the specified file.
     * </p>
     *
     * @param file
     *            the file
     * @return the file content
     * @throws IOException
     *             IO exception while reading content
     */
    private static String readFileContent(File file) throws IOException {
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();
        try {
            br = new BufferedReader(new FileReader(file));
            char[] cbuf = new char[BUFFER_SIZE];
            while (br.read(cbuf) != -1) {
                sb.append(cbuf);
            }
            return sb.toString();
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }
}
