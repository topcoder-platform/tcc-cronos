/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import java.util.List;

import javax.annotation.PostConstruct;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.security.groups.model.Client;
import com.topcoder.security.groups.model.CustomerAdministrator;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.UserService;
import com.topcoder.security.groups.services.dto.UserDTO;

/**
 * <p>
 * This Struts action allows to create customer administrator. This action will
 * be used by JSP corresponding to administrator-create-new-administrator.html
 * from the prototype.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class is technically mutable and not thread-safe,
 * however it's expected to be used as Spring bean and thus it will be immutable
 * after initialization, so, since it inherits from thread-safe (under same
 * conditions) base class and utilizes only the thread-safe tools, it's
 * thread-safe.
 * </p>
 * <p>
 * <b>Sample Configuration:</b> The spring configuration is below:
 *
 * <pre>
 *  &lt;bean id="CreateCustomerAdminAction"
 *     class="com.topcoder.security.groups.actions.CreateCustomerAdminAction"&gt;
 *     &lt;property name="logger" ref="logger"/&gt;
 *     &lt;property name="clientService" ref="clientService"/&gt;
 *     &lt;property name="groupService" ref="groupService"/&gt;
 *     &lt;property name="userService" ref="userService"/&gt;
 *     &lt;property name="customerAdministratorService" ref="customerAdministratorService"/&gt;
 *     &lt;property name="groupMemberService" ref="groupMemberService"/&gt;
 *     &lt;property name="directProjectService" ref="directProjectService"/&gt;
 *  &lt;/bean&gt;
 * </pre>
 *
 * The struts configuration is below:
 *
 * <pre>
 *   &lt;action name="createCustomerAdminAction" class="CreateCustomerAdminAction" method="input"&gt;
 *     &lt;result name="INPUT"&gt;accessAuditingInfo.jsp&lt;/result&gt;
 *   &lt;/action&gt;
 *
 * </pre>
 *
 * </p>
 *
 * @author gevak, progloco
 * @version 1.0
 */
@SuppressWarnings("serial")
public class CreateCustomerAdminAction extends ClientsPrepopulatingBaseAction {
    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = CreateCustomerAdminAction.class
            .getName();
    /**
     * ID of client who will become a customer administrator. It is used in
     * execute() method. It is injected via the setter with no validation, thus
     * can be any value. When execute() is called, XML validations are applied
     * on this variable. Mutable via setter.
     */
    private Long clientId;

    /**
     * Handle of user who will become a customer administrator. It is used in
     * execute() method. It is injected via the setter with no validation, thus
     * can be any value. When execute() is called, XML validations are applied
     * on this variable. Mutable via setter.
     */
    private String handle;

    /**
     * UserService used to search user by handle. It is used in execute()
     * method. It is injected via the setter with no validation, thus can be any
     * value. However, <code>checkInit</code> method will ensure that it's not null.
     * Mutable via setter.
     */
    private UserService userService;

    /**
     * Empty default constructor.
     */
    public CreateCustomerAdminAction() {
    }

    /**
     * Create customer administrator.
     *
     * @return SUCCESS to indicate that the operation was successful.
     * @throws SecurityGroupsActionException if any error occurs when performing
     *             the operation.
     *
     */
    public String execute() throws SecurityGroupsActionException {
        final String signature = CLASS_NAME + ".execute()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature,
                new String[]{"clientId", "handle"},
                new Object[] {clientId, handle});
        try {
            // Execute search user of the handle
            List<UserDTO> users = userService.search(handle);
            // If users does not contain exactly one user, throw exception
            HelperUtiliy.checkSingleElementList(users, "users");
            // Get found user ID
            long userId = users.get(0).getUserId();
            // Get client
            Client client = getClientService().get(clientId);
            ValidationUtility.checkNotNull(client, "client",
                    SecurityGroupsActionException.class);
            // Create and populate customer admin
            CustomerAdministrator ca = new CustomerAdministrator();
            ca.setUserId(userId);
            ca.setClient(client);
            // Persist it
            getCustomerAdministratorService().add(ca);
            // do the audit.
            HelperUtiliy.audit(getAuditService(), null, "client ID = "
                    + clientId + " name = " + client.getName()
                    + "; user handle = " + handle);
        } catch (SecurityGroupException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature,
                    new SecurityGroupsActionException(
                            "error occurs while creating customer admin", e));
        } catch (SecurityGroupsActionException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, e);
        }
        LoggingWrapperUtility.logExit(getLogger(), signature,
                new Object[] {SUCCESS });
        return SUCCESS;
    }

    /**
     * Checks whether this class was configured by Spring properly.
     *
     * @throws SecurityGroupsActionConfigurationException - if the class was not
     *             configured properly (e.g. when required property was not
     *             injected or property value is invalid).
     *
     */
    @PostConstruct
    public void checkInit() {
        super.checkInit();
        ValidationUtility.checkNotNull(userService, "userService",
                SecurityGroupsActionConfigurationException.class);
    }

    /**
     * Sets ID of client who will become a customer administrator.
     *
     * @param clientId ID of client who will become a customer administrator.
     *
     */
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    /**
     * Sets handle of user who will become a customer administrator.
     *
     * @param handle Handle of user who will become a customer administrator.
     *
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     * Sets UserService used to search user by handle.
     *
     * @param userService UserService used to search user by handle.
     *
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
