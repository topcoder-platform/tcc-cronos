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
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.shared.util.logging.Logger;

/**
 * <p>
 * This action extends BaseDirectStrutsAction and provides execution to delete an existing milestone.
 * </p>
 *
 * <p>
 * <strong> Thread Safety:</strong> This class is mutable and not thread safe, but used in thread safe manner by
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
 *   &lt;bean id=&quot;deleteMilestonesAction&quot;
 *   class=&quot;com.topcoder.direct.actions.project.milestone.DeleteMilestonesAction&quot;
 *   init-method=&quot;afterPropertiesSet&quot;&gt;
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
 * @author argolite, fish_ship
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DeleteMilestonesAction extends BaseDirectStrutsAction {
    /**
     * <p>
     * Represents the logger of this action.
     * </p>
     */
    private static final Logger LOGGER = Logger.getLogger(DeleteMilestonesAction.class);

    /**
     * <p>
     * Represents the signature of the executeAction method. It is used for logging.
     * </p>
     */
    private static final String SIGNATURE = DeleteMilestonesAction.class.getName() + "#executeAction";

    /**
     * <p>
     * This is the MilestoneService used to delete the milestone. It is set in the setter. It can be retrieved in the
     * getter. On initialization check, must be not null. This field will be injected by the container (expected to be
     * done only once), and is not expected to change afterward.
     * </p>
     */
    private MilestoneService milestoneService;

    /**
     * <p>
     * Represents the IDs of the milestones to be deleted. It is set in the setter. It can be retrieved in the getter.
     * It may have any value. This field will be set during the request phase by the container, and will not change
     * afterwards.
     * </p>
     */
    private List<Long> milestoneIds;

    /**
     * <p>
     * Represents the id of project. It is set in the setter. It can be retrieved in the getter. It may have any value.
     * This field will be set during the request phase by the container, and will not change afterwards.
     * </p>
     */
    private long projectId;

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    public DeleteMilestonesAction() {
        // empty
    }

    /**
     * <p>
     * Executes the action.
     * </p>
     *
     * @throws IllegalArgumentException
     *             if the milestoneIds is null or empty, contain null element or duplicated element.
     * @throws AuthorizationException
     *             if the current user has not write permission to the given project or fail to get permission
     * @throws EntityNotFoundException
     *             if any milestone is not found
     * @throws ProjectMilestoneManagementException
     *             if there are any errors during the execution of this method
     */
    protected void executeAction() throws ProjectMilestoneManagementException {
        Date startDate = ActionHelper.logEntrance(LOGGER, SIGNATURE);
        // check milestoneIds
        ParameterCheckUtility.checkNotNullNorEmpty(milestoneIds, "milestoneIds");
        ParameterCheckUtility.checkNotNullElements(milestoneIds, "milestoneIds");
        ActionHelper.checkDuplicated(milestoneIds, "milestoneIds");
        // validate the write permission.
        ActionHelper.validatePermission(getCurrentUser(), projectId, this);
        // delete the milestones
        milestoneService.delete(milestoneIds);
        ActionHelper.logExit(LOGGER, SIGNATURE, startDate);
    }

    /**
     * <p>
     * Gets the instance of milestoneService used to delete milestones.
     * </p>
     *
     * @return the instance of milestoneService used to delete milestones
     */
    public MilestoneService getMilestoneService() {
        return milestoneService;
    }

    /**
     * <p>
     * Sets the instance of milestoneService used to delete milestones.
     * </p>
     *
     * @param milestoneService
     *            the instance of milestoneService used to delete milestones.
     */
    public void setMilestoneService(MilestoneService milestoneService) {
        this.milestoneService = milestoneService;
    }

    /**
     * <p>
     * Gets the IDs of the milestones to be deleted.
     * </p>
     *
     * @return the IDs of the milestones to be deleted.
     */
    public List<Long> getMilestoneIds() {
        return milestoneIds;
    }

    /**
     * <p>
     * Sets the IDs of the milestones to be deleted.
     * </p>
     *
     * @param milestoneIds
     *             the IDs of the milestones to be deleted.
     */
    public void setMilestoneIds(List<Long> milestoneIds) {
        this.milestoneIds = milestoneIds;
    }

    /**
     * <p>
     * Gets the ID of project.
     * </p>
     *
     * @return the ID of the project
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
