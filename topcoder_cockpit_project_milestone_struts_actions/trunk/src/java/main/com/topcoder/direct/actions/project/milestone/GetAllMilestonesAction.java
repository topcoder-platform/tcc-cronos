/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.actions.project.milestone;

import java.util.Date;
import java.util.List;

import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.direct.services.project.milestone.EntityNotFoundException;
import com.topcoder.direct.services.project.milestone.MilestoneService;
import com.topcoder.direct.services.project.milestone.ProjectMilestoneManagementConfigurationException;
import com.topcoder.direct.services.project.milestone.ProjectMilestoneManagementException;
import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.project.milestone.model.MilestoneStatus;
import com.topcoder.direct.services.project.milestone.model.SortOrder;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.shared.util.logging.Logger;

/**
 * <p>
 * This action extends BaseDirectStrutsAction and provides execution to get all milestones in a project with the given
 * statuses with the given sort order.
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong> This class is mutable and not thread safe, but used in thread safe manner by
 * framework.
 * </p>
 *
 * <p>
 * Sample configuration:
 *
 * <pre>
 * &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
 * &lt;beans xmlns=&quot;http://www.springframework.org/schema/beans&quot;
 *        xmlns:xsi=&quot;http://www.w3.org/2001/XMLSchema-instance&quot;
 *        xsi:schemaLocation=&quot;http://www.springframework.org/schema/beans
 *        http://www.springframework.org/schema/beans/spring-beans.xsd&quot;&gt;
 *
 *  &lt;bean id=&quot;getAllMilestonesAction&quot;
 *  class=&quot;com.topcoder.direct.actions.project.milestone.GetAllMilestonesAction&quot;
 *  init-method=&quot;afterPropertiesSet&quot;&gt;
 *     &lt;property name=&quot;milestoneService&quot; ref=&quot;milestoneService&quot; /&gt;
 *   &lt;/bean&gt;
 *
 *    &lt;bean id=&quot;milestoneService&quot;
 *    class=&quot;com.topcoder.direct.services.project.milestone.mock.MockMilestoneService&quot;/&gt;
 *
 * &lt;/beans&gt;
 *
 * </pre>
 *
 * </p>
 *
 *
 * @author argolite, fish_ship
 * @version 1.0
 */
@SuppressWarnings("serial")
public class GetAllMilestonesAction extends BaseDirectStrutsAction {
    /**
     * <p>
     * Represents the logger of this action.
     * </p>
     */
    private static final Logger LOGGER = Logger.getLogger(GetAllMilestonesAction.class);

    /**
     * <p>
     * Represents the signature of the executeAction method. It is used for logging.
     * </p>
     */
    private static final String SIGNATURE = GetAllMilestonesAction.class.getName() + "#executeAction";

    /**
     * <p>
     * This is the MilestoneService used to get the milestones. It is set in the setter. It can be retrieved in the
     * getter. On initialization check, must be not null. This field will be injected by the container (expected to be
     * done only once), and is not expected to change afterward.
     * </p>
     */
    private MilestoneService milestoneService;

    /**
     * <p>
     * Represents the project ID. It is set in the setter. It can be retrieved in the getter. It may have any value.
     * This field will be set during the request phase by the container, and will not change afterwards.
     * </p>
     */
    private long projectId;

    /**
     * <p>
     * Represents the requested statuses. It is set in the setter. It can be retrieved in the getter. It may have any
     * value. This field will be set during the request phase by the container, and will not change afterwards.
     * </p>
     */
    private List<MilestoneStatus> requestedStatuses;

    /**
     * <p>
     * Represents the milestones to be created. It is set in the setter. It can be retrieved in the getter. It may have
     * any value. This field will be set during the request phase by the container, and will not change afterwards.
     * </p>
     */
    private SortOrder sortOrder;

    /**
     * <p>
     * Represents the retrieved milestones. It is set in the setter. It can be retrieved in the getter. Initially null,
     * it will be set to a non-null value during the execute method. This field will be set by the execute method.
     * </p>
     */
    private List<Milestone> milestones;

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    public GetAllMilestonesAction() {
        // empty
    }

    /**
     * <p>
     * Executes the action.
     * </p>
     *
     * @throws IllegalArgumentException
     *             if the requestedStatuses contains null element or duplicated element.
     * @throws EntityNotFoundException
     *             if any milestone is not found
     * @throws ProjectMilestoneManagementException
     *             if there are any errors during the execution of this method
     */
    protected void executeAction() throws ProjectMilestoneManagementException {
        Date startDate = ActionHelper.logEntrance(LOGGER, SIGNATURE);
        // check the requestedStatuses
        ParameterCheckUtility.checkNotNullElements(requestedStatuses, "requestedStatuses");
        ActionHelper.checkDuplicated(requestedStatuses, "requestedStatuses");
        // get milestones from service layer
        milestones = milestoneService.getAll(projectId, requestedStatuses, sortOrder);
        ActionHelper.logExit(LOGGER, SIGNATURE, startDate);
    }

    /**
     * <p>
     * Gets the instance of milestoneService used to get milestones.
     * </p>
     *
     * @return the instance of milestoneService used to get milestones.
     */
    public MilestoneService getMilestoneService() {
        return milestoneService;
    }

    /**
     * <p>
     * Sets the instance of milestoneService used to get milestones.
     * </p>
     *
     * @param milestoneService
     *            the instance of milestoneService used to get milestones.
     */
    public void setMilestoneService(MilestoneService milestoneService) {
        this.milestoneService = milestoneService;
    }

    /**
     * <p>
     * Gets the ID of projectId.
     * </p>
     *
     * @return the ID of project
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * <p>
     * Sets the ID of projectId.
     * </p>
     *
     * @param projectId
     *            the ID of project to set
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * <p>
     * Gets the requested statuses.
     * </p>
     *
     * @return the requested statuses
     */
    public List<MilestoneStatus> getRequestedStatuses() {
        return requestedStatuses;
    }

    /**
     * <p>
     * Sets the requested statuses.
     * </p>
     *
     * @param requestedStatuses
     *            the requested statuses to set
     */
    public void setRequestedStatuses(List<MilestoneStatus> requestedStatuses) {
        this.requestedStatuses = requestedStatuses;
    }

    /**
     * <p>
     * Gets the sort order value.
     * </p>
     *
     * @return the sort order
     */
    public SortOrder getSortOrder() {
        return sortOrder;
    }

    /**
     * <p>
     * Sets the sort order value.
     * </p>
     *
     * @param sortOrder
     *            the sort order to set
     */
    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * <p>
     * Gets the retrieved milestones.
     * </p>
     *
     * @return the list of retrieved milestones.
     */
    public List<Milestone> getMilestones() {
        return milestones;
    }

    /**
     * <p>
     * Sets the retrieved milestones.
     * </p>
     *
     * @param milestones
     *            the list of retrieved milestones.
     */
    public void setMilestones(List<Milestone> milestones) {
        this.milestones = milestones;
    }

    /**
     * <p>
     * This method mainly checks if the IOC operations succeeds. It's supposed to be invoked immediately after IOC
     * completes. Generally, you need to specify its name at init-method property of spring bean definition.
     * </p>
     *
     * @throws ProjectMilestoneManagementConfigurationException
     *             if the milestoneService is null.
     */
    public void afterPropertiesSet() {
        ValidationUtility.checkNotNull(milestoneService, "milestoneService",
                ProjectMilestoneManagementConfigurationException.class);
    }
}
