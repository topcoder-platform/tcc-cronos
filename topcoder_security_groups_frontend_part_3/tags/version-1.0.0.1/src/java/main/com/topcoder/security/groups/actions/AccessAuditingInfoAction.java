/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.model.GroupMember;
import com.topcoder.security.groups.model.GroupPermissionType;
import com.topcoder.security.groups.services.DirectProjectService;
import com.topcoder.security.groups.services.GroupMemberService;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.UserService;
import com.topcoder.security.groups.services.dto.GroupMemberAccessHistoricalData;
import com.topcoder.security.groups.services.dto.GroupMemberSearchCriteria;
import com.topcoder.security.groups.services.dto.PagedResult;
import com.topcoder.security.groups.services.dto.ProjectDTO;
import com.topcoder.security.groups.services.dto.UserDTO;

/**
 * <p>
 * This Struts action allows to access auditing information. This action will be
 * used by JSP corresponding to administrator-acc-auditing-information.html from
 * the prototype.
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
 *  &lt;bean id="AccessAuditingInfoAction"
 *     class="com.topcoder.security.groups.actions.AccessAuditingInfoAction"&gt;
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
 *   &lt;action name="accessAuditingInfoAction" class="AccessAuditingInfoAction" method="input"&gt;
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
public class AccessAuditingInfoAction extends ClientsPrepopulatingBaseAction {
    /**
     * Represent the class name.
     */
    private static final String CLASS_NAME = AccessAuditingInfoAction.class
            .getName();
    /**
     * The maximum length of a string.
     */
    private static final int MAX_LENGTH = 45;

    /** The default page size. */
    private static final int DEFAUL_PAGESIZE = 10;
    /**
     * Group member search criteria, used for searching the group member access
     * historical data. It is used in execute() method. It is injected via the
     * setter with no validation, thus can be any value. When execute() is
     * called, XML validations are applied on this variable. Has default value
     * (in order to allow page to load first time (before user clicks
     * search/filter button)). Mutable via public setter.
     */
    private GroupMemberSearchCriteria criteria = new GroupMemberSearchCriteria();

    /**
     * Amount of found records to show on a single page (used for search result
     * pagination). It is used in execute() method. It is injected via the
     * setter with no validation, thus can be any value. When execute() is
     * called, XML validations are applied on this variable. Has default value
     * (in order to allow page to load first time (before user clicks
     * search/filter button)). Mutable via public setter.
     */
    private int pageSize = DEFAUL_PAGESIZE;

    /**
     * 1-based index of page to show (used for search result pagination). 0 for
     * retrieving all records. It is used in execute() method. It is injected
     * via the setter with no validation, thus can be any value. When execute()
     * is called, XML validations are applied on this variable. Has default
     * value (in order to allow page to load first time (before user clicks
     * search/filter button)). Mutable via public setter.
     */
    private int page = 1;

    /**
     * Group member access historical data search result. Initially null, but
     * will be populated by execute() method with non-null value. Has public
     * getter.
     */
    private PagedResult<GroupMemberAccessHistoricalData> historicalData;

    /**
     * List, containing Group entities for each item in the search result (in
     * same order as there). This will be used by JSP to render necessary group
     * related info. Initially null, but will be populated by execute() method
     * with non-null list of non-null values. Has public getter.
     */
    private List<Group> groups;

    /**
     * Contains projects per ID (project ID is key and project DTO is value).
     * This will be used by JSP to render content of the "Projects" column of
     * the table. Initially null, but will be populated by execute() method with
     * non-null map composed of non-null keys and non-null values. Has public
     * getter.
     */
    private Map<Long, ProjectDTO> projectById;

    /**
     * Contains users per ID (user ID is key and user DTO is value). This will
     * be used by JSP to render content of the "Members" column of the table.
     * Initially null, but will be populated by execute() method with non-null
     * map composed of non-null keys and non-null values. Has public getter.
     */
    private Map<Long, UserDTO> userById;

    /**
     * GroupMemberService used to perform search. It is used in execute()
     * method. It is injected via the setter with no validation, thus can be any
     * value. However, <code>checkInit</code> method will ensure that it's not
     * null. Mutable via setter.
     */
    private GroupMemberService groupMemberService;

    /**
     * GroupService used to retrieve group info. It is used in execute() method.
     * It is injected via the setter with no validation, thus can be any value.
     * However, <code>checkInit</code> method will ensure that it's not null.
     * Mutable via setter.
     */
    private GroupService groupService;

    /**
     * DirectProjectService used to retrieve project info. It is used in
     * execute() method. It is injected via the setter with no validation, thus
     * can be any value. However, the post construct method will ensure that
     * it's not null. Mutable via setter.
     */
    private DirectProjectService directProjectService;

    /**
     * UserService used to manage user information. It is used in execute()
     * method. It is injected via the setter with no validation, thus can be any
     * value. However, the post construct method will ensure that it's not null.
     * Mutable via setter.
     */
    private UserService userService;

    /**
     * Empty default constructor.
     */
    public AccessAuditingInfoAction() {
    }

    /**
     * Searches auditing information.
     *
     * @return SUCCESS to indicate that the operation was successful.
     * @throws SecurityGroupsActionException if any error occurs when performing
     *             the operation.
     *
     */
    public String execute() throws SecurityGroupsActionException {
        final String signature = CLASS_NAME + ".execute()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, new String[] {
            "criteria", "pageSize", "page" }, new Object[] {criteria,
                pageSize, page });
        try {
            // Get current user id
            long userId = getCurrentUserId();
            // Populate user id into search criteria
            criteria.setUserId(userId);
            // Search historical data
            historicalData = groupMemberService.searchHistoricalData(criteria,
                    pageSize, page);
            List<GroupMemberAccessHistoricalData> historicalDataValues = historicalData
                    .getValues();
            ValidationUtility
                    .checkNotNullElements(historicalDataValues,
                            "historicalDataValues",
                            SecurityGroupsActionException.class);
            groups = new ArrayList<Group>();
            projectById = new HashMap<Long, ProjectDTO>();
            for (GroupMemberAccessHistoricalData data : historicalDataValues) {
                // Get groups of the historical data
                Group group = groupService.get(data.getGroupId());
                ValidationUtility.checkNotNull(group, "group",
                        SecurityGroupsActionException.class);
                groups.add(group);

                // Populate project by ID map
                List<Long> directProjectIds = data.getDirectProjectIds();
                ValidationUtility
                        .checkNotNull(directProjectIds, "directProjectIds",
                                SecurityGroupsActionException.class);
                ValidationUtility
                        .checkNotNullElements(directProjectIds,
                                "directProjectIds",
                                SecurityGroupsActionException.class);
                for (Long projectId : directProjectIds) {
                    if (!projectById.containsKey(projectId)) {
                        ProjectDTO project = directProjectService
                                .get(projectId);
                        ValidationUtility.checkNotNull(project, "project",
                                SecurityGroupsActionException.class);
                        projectById.put(projectId, project);
                    }
                }
            }

            // Populate user by ID map
            userById = new HashMap<Long, UserDTO>();
            for (Group group : groups) {
                List<GroupMember> groupMembers = group.getGroupMembers();
                ValidationUtility.checkNotNull(groupMembers, "groupMembers",
                        SecurityGroupsActionException.class);
                ValidationUtility.checkNotNullElements(groupMembers,
                        "groupMembers", SecurityGroupsActionException.class);
                for (GroupMember groupMember : groupMembers) {
                    if (!userById.containsKey(groupMember.getUserId())) {
                        UserDTO user = userService.get(groupMember.getUserId());
                        ValidationUtility.checkNotNull(user, "user",
                                SecurityGroupsActionException.class);
                        userById.put(userId, user);
                    }
                }
            }
        } catch (SecurityGroupException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature,
                    new SecurityGroupsActionException(
                            "error occurs while executing the action", e));
        } catch (SecurityGroupsActionException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, e);
        }
        LoggingWrapperUtility.logExit(getLogger(), signature,
                getOutputParametersMessage(new String[] {
                    "historicalData", "groups", "projectById", "userById", "executeOutput"},
                        new Object[] {historicalData, groups, projectById,
                            userById, SUCCESS }));
        return SUCCESS;
    }

    /**
     * <p>
     * Retrieves the log message for the given output parameters. It's assumed
     * that paramNames and paramValues contain the same number of elements.
     * </p>
     *
     * @param paramValues the values of output parameters (not null).
     * @param paramNames the names of output parameters (not null).
     *
     * @return the constructed log message.
     */
    private Object[] getOutputParametersMessage(String[] paramNames,
            Object[] paramValues) {
        StringBuilder sb = new StringBuilder("Output parameters [");
        int paramNamesLen = paramNames.length;
        for (int i = 0; i < paramNamesLen; i++) {
            if (i != 0) {
                // Append a comma
                sb.append(", ");
            }
            sb.append(paramNames[i]).append(":").append(paramValues[i]);
        }
        sb.append("]");
        return new Object[] {sb.toString() };
    }

    /**
     * Performs complex validation that can't be easily done with declarative
     * XML validation.
     *
     */
    public void validate() {
        final String signature = CLASS_NAME + ".validate()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        addErrorMessage(criteria.getGroupName(), "criteria.groupName");
        addErrorMessage(criteria.getClientName(), "criteria.clientName");
        addErrorMessage(criteria.getProjectName(), "criteria.projectName");
        addErrorMessage(criteria.getBillingAccountName(),
                "criteria.billingAccountName");

        addIntegerErrorMessage(page, "page");
        addIntegerErrorMessage(pageSize, "pageSize");

        // validate the permission list.
        List<GroupPermissionType> permissions = criteria.getPermissions();
        if (permissions == null) {
            addFieldError("criteria.permissions",
                    "criteria.permissions can't be null.");
            // if null, no need to validate
            LoggingWrapperUtility.logExit(getLogger(), signature, null);
            return;
        }
        if (permissions.size() == 0) {
            this.addFieldError("criteria.permissions",
                    "criteria.permissions can't be empty.");
            LoggingWrapperUtility.logExit(getLogger(), signature, null);
            return;
        }
        for (GroupPermissionType permission : permissions) {
            if (permission == null) {
                addFieldError("criteria.permissions",
                        "criteria.permissions can't contain null.");
            }
        }
        Set<GroupPermissionType> set = new HashSet<GroupPermissionType>(
                permissions);
        if (permissions.size() != set.size()) {
            addFieldError("criteria.permissions",
                    "criteria.permissions can't contain null or equal element.");
        }

        LoggingWrapperUtility.logExit(getLogger(), signature, null);
    }

    /**
     * Add error message for integer.
     *
     * @param value the field value.
     * @param name of the field.
     */
    private void addIntegerErrorMessage(int value, String name) {
        if (value < 0) {
            addFieldError(name, name + " should not be negative.");
        }
    }

    /**
     * Add error message.
     *
     * @param value the field value.
     * @param name of the field.
     */
    private void addErrorMessage(String value, String name) {
        if (value != null) {
            if (value.trim().length() == 0 || value.length() > MAX_LENGTH) {
                addFieldError(name, name
                        + " can't be empty and not longer than 45 characters.");
            }
        }
    }

    /**
     * Checks whether this class was configured by Spring properly.
     *
     * @throws SecurityGroupsActionConfigurationException if the class was not
     *             configured properly (e.g. when required property was not
     *             injected or property value is invalid).
     *
     */
    @PostConstruct
    public void checkInit() {
        super.checkInit();
        ValidationUtility.checkNotNull(groupMemberService,
                "groupMemberService",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(groupService, "groupService",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(directProjectService,
                "directProjectService",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(userService, "userService",
                SecurityGroupsActionConfigurationException.class);
    }

    /**
     * Sets group member search criteria, used for searching the group member
     * access historical data.
     *
     * @param criteria Group member search criteria, used for searching the
     *            group member access historical data.
     *
     */
    public void setCriteria(GroupMemberSearchCriteria criteria) {
        this.criteria = criteria;
    }

    /**
     * Sets amount of found records to show on a single page (used for search
     * result pagination).
     *
     * @param pageSize Amount of found records to show on a single page (used
     *            for search result pagination).
     *
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Sets 1-based index of page to show (used for search result pagination). 0
     * for retrieving all records.
     *
     * @param page 1-based index of page to show (used for search result
     *            pagination). 0 for retrieving all records.
     *
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * Gets group member access historical data search result.
     *
     * @return Group member access historical data search result.
     *
     */
    public PagedResult<GroupMemberAccessHistoricalData> getHistoricalData() {
        return historicalData;
    }

    /**
     * Gets list, containing Group entities for each item in the search result
     * (in same order as there).
     *
     * @return List, containing Group entities for each item in the search
     *         result (in same order as there).
     *
     */
    public List<Group> getGroups() {
        return groups;
    }

    /**
     * Gets map containing projects per ID (project ID is key and project DTO is
     * value). This will be used by JSP to render content of the "Projects"
     * column of the table.
     *
     * @return Contains projects per ID (project ID is key and project DTO is
     *         value). This will be used by JSP to render content of the
     *         "Projects" column of the table.
     *
     */
    public Map<Long, ProjectDTO> getProjectById() {
        return projectById;
    }

    /**
     * Gets map containing users per ID (user ID is key and user DTO is value).
     * This will be used by JSP to render content of the "Members" column of the
     * table.
     *
     * @return Contains users per ID (user ID is key and user DTO is value).
     *         This will be used by JSP to render content of the "Members"
     *         column of the table.
     *
     */
    public Map<Long, UserDTO> getUserById() {
        return userById;
    }

    /**
     * Sets GroupMemberService used to perform search.
     *
     * @param groupMemberService GroupMemberService used to perform search.
     *
     */
    public void setGroupMemberService(GroupMemberService groupMemberService) {
        this.groupMemberService = groupMemberService;
    }

    /**
     * Sets GroupService used to retrieve group info.
     *
     * @param groupService GroupService used to retrieve group info.
     *
     */
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    /**
     * Sets DirectProjectService used to retrieve project info.
     *
     * @param directProjectService DirectProjectService used to retrieve project
     *            info.
     *
     */
    public void setDirectProjectService(
            DirectProjectService directProjectService) {
        this.directProjectService = directProjectService;
    }

    /**
     * Sets UserService used to manage user information.
     *
     * @param userService UserService used to manage user information.
     *
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
