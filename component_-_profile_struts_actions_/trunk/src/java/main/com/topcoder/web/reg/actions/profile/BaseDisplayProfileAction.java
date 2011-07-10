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
 * This action will perform some common tasks related to displaying profile information, regardless if it is in view or
 * edit mode. Extensions will provide the specifics of how data is processed. It uses UserDAO to manage the user.
 * </p>
 * <p>
 * Sample configuration:
 * <pre>
 *  &lt;bean id=&quot;action&quot; class=&lt;concrete implementation&gt;&gt;
 *  &lt;property name=&quot;logger&quot; ref=&quot;logger&quot; /&gt;
 *  &lt;property name=&quot;auditDAO&quot; ref=&quot;auditDAO&quot; /&gt;
 *  &lt;property name=&quot;authenticationSessionKey&quot; value=&quot;authenticationSessionKey&quot; /&gt;
 *  &lt;property name=&quot;userDAO&quot; ref=&quot;userDAO&quot; /&gt;
 *  &lt;property name=&quot;auditOperationType&quot; value=&quot;Base Display Operation&quot; /&gt;
 *  &lt;property name=&quot;profileCompletenessRetriever&quot; ref=&quot;profileCompletenessRetriever&quot; /&gt;
 *  &lt;/bean&gt;
 * </pre>
 * Sample usage:
 * <pre>
 * // Retrieve action from context
 * BaseDisplayProfileAction action = ...;
 * // Execute action and get result
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
public abstract class BaseDisplayProfileAction extends BaseProfileAction {

    /**
     * <p>
     * Represents execute() method constant signature name used for logging.
     * </p>
     */
    private static final String EXECUTE_METHOD_SIGNATURE = "BaseDisplayProfileAction.execute()";

    /**
     * <p>
     * Represents the User with the profile info.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * It may have any value. This field will be set by the execute method.
     * </p>
     */
    private User displayedUser;

    /**
     * <p>
     * Creates an instance of BaseDisplayProfileAction.
     * </p>
     */
    protected BaseDisplayProfileAction() {
    }

    /**
     * <p>
     * Performs some common tasks related to displaying profile information, regardless if it is in view or edit mode.
     * </p>
     * <p>
     * Extensions will need to provide the specific steps for putting user data on the stack in the processOutputData
     * method. If any additional processing needs to be done, it can be done inside the performAdditionalTasks method,
     * that the extension can also implement. Implementation is not required as the method has an empty implementation.
     * </p>
     * @return a string representing the logical result of the execution.
     * @throws ProfileActionException - If there are any errors in the action
     */
    public String execute() throws ProfileActionException {
        LoggingWrapperUtility.logEntrance(getLogger(), EXECUTE_METHOD_SIGNATURE, null, null, true, Level.DEBUG);
        User user = Helper.retrieveLoggedInUser(this, EXECUTE_METHOD_SIGNATURE);
        // Process output
        processOutputData(user);
        // Perform any additional processing on user:
        performAdditionalTasks(user);
        // Get ProfileCompletenessReport:
        Helper.getCompletenessReport(this, user, EXECUTE_METHOD_SIGNATURE);
        // Audit user action:
        Helper.makeAudit(this, null, null);
        LoggingWrapperUtility.logExit(getLogger(), EXECUTE_METHOD_SIGNATURE, new String[] {SUCCESS}, null, true,
                Level.DEBUG);
        return SUCCESS;
    }

    /**
     * <p>
     * This method is called by the execute method to process the output data.
     * </p>
     * <p>
     * This means it is responsible for putting the data in the passed user on the stack, as per extension.
     * </p>
     * @param user the user
     * @throws ProfileActionException - If there are any errors while performing this operation.
     */
    protected abstract void processOutputData(User user) throws ProfileActionException;

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
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public User getDisplayedUser() {
        return displayedUser;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param displayedUser the namesake field instance value to set
     */
    public void setDisplayedUser(User displayedUser) {
        this.displayedUser = displayedUser;
    }
}