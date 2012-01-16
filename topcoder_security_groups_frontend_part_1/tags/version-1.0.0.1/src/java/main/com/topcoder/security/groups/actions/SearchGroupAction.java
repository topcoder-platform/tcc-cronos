/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import java.util.List;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.model.GroupPermissionType;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.dto.GroupSearchCriteria;
import com.topcoder.security.groups.services.dto.PagedResult;

/**
 * <p>
 * SearchGroupAction is used to perform the search group function. It will log events and errors. It will audit the
 * methods.
 * </p>
 * <b>Configuration</b>
 * <p>
 *
 * <pre>
 *   &lt;bean id=&quot;searchGroupAction&quot;
 *         class=&quot;com.topcoder.security.groups.actions.SearchGroupAction&quot;&gt;
 *         &lt;property name=&quot;groupService&quot; ref=&quot;groupService&quot;/&gt;
 *         &lt;property name=&quot;logger&quot; ref=&quot;logger&quot;/&gt;
 *         &lt;property name=&quot;auditService&quot; ref=&quot;auditService&quot;/&gt;
 *         &lt;property name=&quot;authorizationService&quot; ref=&quot;authorizationService&quot;/&gt;
 *         &lt;!-- other properties here --&gt;
 *   &lt;/bean&gt;
 * </pre>
 *
 * </p>
 * <p>
 * <strong>Thread Safety: </strong> It's mutable and not thread safe. However the struts framework will guarantee
 * that it will be used in the thread safe model.
 * </p>
 *
 * @author woodjhon, hanshuai
 * @version 1.0
 */
@SuppressWarnings("serial")
public class SearchGroupAction extends BaseAction {
    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = SearchGroupAction.class.getName();

    /**
     * Represent the max length of string.
     */
    private static final int MAXLENGTH = 45;

    /**
     * Purpose: criteria is used to represents the criteria. Usage: It's passed as the http input parameter for
     * this action. Legal Values: Not null after set
     */
    private GroupSearchCriteria criteria;

    /**
     * Purpose: pageSize is used to represents the page size. Usage: It's passed as the http input parameter for
     * this action. Legal Values: Positive after set
     */
    private int pageSize;

    /**
     * Purpose: page is used to represents the page. Usage: It's passed as the http input parameter for this
     * action. Legal Values: Positive after set
     */
    private int page;

    /**
     * Purpose: groups is used to represents the groups. Usage: It's set by the action methods, and consumed by the
     * front end page. Legal Values: Not null after set
     */
    private PagedResult<Group> groups;

    /**
     * Purpose: groupService is used to represents the group service. It's required. Usage: It's injected. Legal
     * Values: Not null after set
     */
    private GroupService groupService;

    /**
     * <p>
     * Create the instance.
     * </p>
     */
    public SearchGroupAction() {
        // Empty Constructor.
    }

    /**
     * <p>
     * Check that the required(with config stereotype) fields are injected.
     * </p>
     *
     * @throws SecurityGroupsActionConfigurationException
     *             is thrown if any of these fields is null:<br>
     *             auditService, authorizationService, groupService
     */
    public void checkInit() {
        super.checkInit();
        ValidationUtility.checkNotNull(groupService, "groupService",
                SecurityGroupsActionConfigurationException.class);
    }

    /**
     * <p>
     * Execute method. It will search groups by the input criteria.
     * </p>
     *
     * @throws SecurityGroupsActionException
     *             if any error is caught during the operation.
     * @return The execution result
     */
    public String execute() throws SecurityGroupsActionException {
        final String signature = CLASS_NAME + ".execute()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        try {
            long userId = getCurrentUserId();
            criteria.setUserId(userId);
            groups = groupService.search(criteria, pageSize, page);
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {SUCCESS });
            return SUCCESS;
        } catch (SecurityGroupException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    "error occurs while searching the group", e));
        } catch (RuntimeException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    "RuntimeException occurs while searching the group", e));
        }
    }

    /**
     * Check the string has max 45 chars. the string can be empty.
     *
     * @param string
     *            the string
     * @param fieldName
     *            the field name
     * @throws SecurityGroupsActionValidationException
     *             if validation fails.
     */
    private void check(String string, String fieldName) {
        if (string == null) {
            return;
        }
        string = string.trim();
        if (string.length() > MAXLENGTH) {
            this.addFieldError(fieldName, getText(fieldName + ".length"));
            throw new SecurityGroupsActionValidationException(fieldName + "'s length is bigger than 45");
        }
    }

    /**
     * <p>
     * Performs complex validation that can't be easily done with declarative XML validation.
     * </p>
     *
     * @throws SecurityGroupsActionValidationException
     *             if validation fails.
     */
    public synchronized void validate() {
        final String signature = CLASS_NAME + ".validate()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        try {
            GroupSearchCriteria criteria2 = getCriteria();
            if (criteria2 != null) {
                check(criteria2.getGroupName(), "criteria.groupName");
                check(criteria2.getClientName(), "criteria.clientName");
                check(criteria2.getProjectName(), "criteria.projectName");
                List<GroupPermissionType> permissions = criteria2.getPermissions();
                if (permissions != null && permissions.size() > 1) {
                    this.addFieldError("criteria.permissions", getText("criteria.permissions.invalid"));
                    throw new SecurityGroupsActionValidationException("criteria.permissions is invalid");
                }
                check(criteria2.getBillingAccountName(), "criteria.billingAccountName");
                check(criteria2.getGroupMemberHandle(), "criteria.groupMemberHandle");
            }

            LoggingWrapperUtility.logExit(getLogger(), signature, null);
        } catch (SecurityGroupsActionValidationException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, e);
        }
    }

    /**
     * <p>
     * Get criteria.
     * </p>
     *
     * @return the criteria
     */
    public GroupSearchCriteria getCriteria() {
        return criteria;
    }

    /**
     * <p>
     * Set criteria.
     * </p>
     *
     * @param criteria
     *            the criteria to set.
     */
    public void setCriteria(GroupSearchCriteria criteria) {
        this.criteria = criteria;
    }

    /**
     * <p>
     * Get page size.
     * </p>
     *
     * @return the page size
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * <p>
     * Set page size.
     * </p>
     *
     * @param pageSize
     *            the page size to set.
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * <p>
     * Get page.
     * </p>
     *
     * @return the page
     */
    public int getPage() {
        return page;
    }

    /**
     * <p>
     * Set page.
     * </p>
     *
     * @param page
     *            the page to set.
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * <p>
     * Get groups.
     * </p>
     *
     * @return the groups
     */
    public PagedResult<Group> getGroups() {
        return groups;
    }

    /**
     * <p>
     * Set groups.
     * </p>
     *
     * @param groups
     *            the groups to set.
     */
    public void setGroups(PagedResult<Group> groups) {
        this.groups = groups;
    }

    /**
     * <p>
     * Get group service.
     * </p>
     *
     * @return the group service
     */
    public GroupService getGroupService() {
        return groupService;
    }

    /**
     * <p>
     * Set group service.
     * </p>
     *
     * @param groupService
     *            the group service to set.
     */
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }
}
