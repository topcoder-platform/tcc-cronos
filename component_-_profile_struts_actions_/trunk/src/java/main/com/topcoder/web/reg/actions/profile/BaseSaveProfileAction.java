/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.util.log.Level;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.ProfileActionException;

/**
 * <p>
 * This action will perform some common tasks related to saving profile information. Extensions will provide the
 * specifics of how data is processed. It uses UserDAO to manage the user. It will send an email if the configuration
 * asks for it.
 * </p>
 * <p>
 * Sample configuration:
 * <pre>
 *  &lt;bean id=&quot;action&quot; class=&lt;concrete implementation&gt;&gt;
 *  &lt;!-- base properties --&gt;
 *  &lt;property name=&quot;logger&quot; ref=&quot;logger&quot; /&gt;
 *  &lt;property name=&quot;auditDAO&quot; ref=&quot;auditDAO&quot; /&gt;
 *  &lt;property name=&quot;authenticationSessionKey&quot; value=&quot;authenticationSessionKey&quot; /&gt;
 *  &lt;property name=&quot;userDAO&quot; ref=&quot;userDAO&quot; /&gt;
 *  &lt;property name=&quot;auditOperationType&quot; value=&quot;Email Sending Operation&quot; /&gt;
 *  &lt;property name=&quot;profileCompletenessRetriever&quot; ref=&quot;profileCompletenessRetriever&quot; /&gt;
 *  &lt;!-- email sending properties --&gt;
 *  &lt;property name=&quot;documentGenerator&quot; ref=&quot;documentGenerator&quot; /&gt;
 *  &lt;property name=&quot;emailSubject&quot; value=&quot;This is email subject&quot; /&gt;
 *  &lt;property name=&quot;emailSender&quot; value=&quot;sender@example.com&quot; /&gt;
 *  &lt;property name=&quot;emailBodyTemplateSourceId&quot; value=&quot;file&quot; /&gt;
 *  &lt;property name=&quot;emailBodyTemplateName&quot; value=&quot;template.txt&quot; /&gt;
 *  &lt;/bean&gt;
 * </pre>
 * Sample usage:
 * <pre>
 * // Retrieve action from context
 * BaseSaveProfileAction action = ...;
 *  // Execute action and get result
 * action.execute();
 * </pre>
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe, but used in thread safe manner by framework.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public abstract class BaseSaveProfileAction extends EmailSendingProfileAction {

    /**
     * <p>
     * Represents execute() method constant signature name used for logging.
     * </p>
     */
    private static final String EXECUTE_METHOD_SIGNATURE = "BaseDisplayProfileAction.execute()";

    /**
     * <p>
     * Represents the User with the updated profile info.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * It may have any value. This field will be set during the request phase by the container, and will not change
     * afterwards.
     * </p>
     */
    private User savedUser;

    /**
     * <p>
     * Creates an instance of BaseSaveProfileAction.
     * </p>
     */
    protected BaseSaveProfileAction() {
    }

    /**
     * <p>
     * Performs some common tasks related to saving profile information.
     * </p>
     * <p>
     * Extensions will need to provide the specific steps for putting the specific profile information into the user in
     * the processInputData method that must be implemented. If any additional processing needs to be done, it can be
     * done inside the performAdditionalTasks method, that the extension can also implement.
     * </p>
     * <p>
     * Implementation is not required as the method has an empty implementation. Finally, the extension can provide a
     * generateEmailTemplateData method implementation that provides the data for the email text generation.
     * </p>
     * <p>
     * Again, this is optional, as the default method returns an empty template data. This can be left as is if for
     * example, email sending is turned off.
     * </p>
     * @return a string representing the logical result of the execution.
     * @throws ProfileActionException - If there are any errors in the action
     */
    public String execute() throws ProfileActionException {
        LoggingWrapperUtility.logEntrance(getLogger(), EXECUTE_METHOD_SIGNATURE, null, null, true, Level.DEBUG);
        // Get user
        User user = Helper.retrieveLoggedInUser(this, EXECUTE_METHOD_SIGNATURE);
        // Process input data:
        processInputData(user);
        // Save user:
        getUserDAO().saveOrUpdate(user);
        // Perform any additional processing on user:
        performAdditionalTasks(user);
        // Send email if needed
        if (isSendEmail()) {
            String templateData = generateEmailTemplateData(user);
            sendEmail(user.getPrimaryEmailAddress().getAddress(), templateData);
        }
        // Get completeness report:
        Helper.getCompletenessReport(this, user, EXECUTE_METHOD_SIGNATURE);
        // Make audit:
        Helper.makeAudit(this, null, null);
        LoggingWrapperUtility.logExit(getLogger(), EXECUTE_METHOD_SIGNATURE, new String[] {SUCCESS}, null, true,
                Level.DEBUG);
        return SUCCESS;
    }

    /**
     * <p>
     * This method is called by the execute method to process the input data.
     * </p>
     * <p>
     * This means it is responsible for putting the input data into the passed user, as per extension.
     * </p>
     * @param user the user
     * @throws ProfileActionException - If there are any errors while performing this operation.
     */
    protected abstract void processInputData(User user) throws ProfileActionException;

    /**
     * <p>
     * This method is called by the execute method to perform any additional tasks that the extension may require.
     * </p>
     * @param user the user
     * @throws ProfileActionException - If there are any errors while performing this operation.
     */
    protected void performAdditionalTasks(User user) throws ProfileActionException {
        // Do nothing
    }

    /**
     * <p>
     * This method is called by the execute method to generate the email template data.
     * </p>
     * <p>
     * Default implementation just returns empty template data that can be safely used with the Document Generator.
     * </p>
     * @param user the user
     * @return the template data string
     * @throws ProfileActionException - If there are any errors while performing this operation
     */
    protected String generateEmailTemplateData(User user) throws ProfileActionException {
        return "<DATA></DATA>";
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public User getSavedUser() {
        return savedUser;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param savedUser the namesake field instance value to set
     */
    public void setSavedUser(User savedUser) {
        this.savedUser = savedUser;
    }
}