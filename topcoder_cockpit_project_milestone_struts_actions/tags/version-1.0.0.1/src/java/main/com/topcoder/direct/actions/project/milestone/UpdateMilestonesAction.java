/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.actions.project.milestone;

import java.util.Date;
import java.util.List;

import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.direct.services.project.milestone.EntityNotFoundException;
import com.topcoder.direct.services.project.milestone.MilestoneService;
import com.topcoder.direct.services.project.milestone.ProjectMilestoneManagementConfigurationException;
import com.topcoder.direct.services.project.milestone.ProjectMilestoneManagementException;
import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.shared.util.logging.Logger;

/**
 * <p>
 * This action extends BaseDirectStrutsAction and provides execution to update an existing milestone.
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
 *   &lt;bean id=&quot;updateMilestonesAction&quot;
 *   class=&quot;com.topcoder.direct.actions.project.milestone.UpdateMilestonesAction&quot;
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
public class UpdateMilestonesAction extends BaseDirectStrutsAction {
    /**
     * <p>
     * Represents the logger of this action.
     * </p>
     */
    private static final Logger LOGGER = Logger.getLogger(UpdateMilestonesAction.class);

    /**
     * <p>
     * Represents the signature of the executeAction method. It is used for logging.
     * </p>
     */
    private static final String SIGNATURE = UpdateMilestonesAction.class.getName() + "#executeAction";

    /**
     * <p>
     * This is the MilestoneService used to update the milestone. It is set in the setter. It can be retrieved in the
     * getter. On initialization check, must be not null. This field will be injected by the container (expected to be
     * done only once), and is not expected to change afterward.
     * </p>
     */
    private MilestoneService milestoneService;

    /**
     * <p>
     * Represents the milestones to be updated. It is set in the setter. It can be retrieved in the getter. It may have
     * any value. This field will be set during the request phase by the container, and will not change afterwards.
     * </p>
     */
    private List<Milestone> milestones;

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    public UpdateMilestonesAction() {
        // empty
    }

    /**
     * <p>
     * Executes the action.
     * </p>
     *
     * @throws IllegalArgumentException
     *             if the milestones is null or empty, contains null element, milestone.getName() and
     *             milestone.getDescription() is null or empty, milestone.getDueDate() and milestone.getStatus() is
     *             null, milestone.getOwners() is null or empty, contain null element,
     *             milestone.getOwners()[i].getName() is null or empty.
     * @throws AuthorizationException
     *             if the current user has not write permission to the given project or fail to get permission
     * @throws ProjectMilestoneManagementException
     *             if there are any errors during the execution of this method
     * @throws EntityNotFoundException
     *             if the milestone is not found
     */
    protected void executeAction() throws ProjectMilestoneManagementException {
        Date startDate = ActionHelper.logEntrance(LOGGER, SIGNATURE);
        ActionHelper.checkMilestones(milestones, getCurrentUser(), this);
        // update the milestone
        milestoneService.update(milestones);
        ActionHelper.logExit(LOGGER, SIGNATURE, startDate);
    }

    /**
     * <p>
     * Gets the instance of milestoneService used to update milestones.
     * </p>
     *
     * @return the instance of milestoneService used to update milestones.
     */
    public MilestoneService getMilestoneService() {
        return milestoneService;
    }

    /**
     * <p>
     * Sets the instance of milestoneService used to update milestones.
     * </p>
     *
     * @param milestoneService
     *            the instance of milestoneService used to update milestones.
     */
    public void setMilestoneService(MilestoneService milestoneService) {
        this.milestoneService = milestoneService;
    }

    /**
     * <p>
     * Gets the milestones to be updated.
     * </p>
     *
     * @return the list of milestones to be updated.
     */
    public List<Milestone> getMilestones() {
        return milestones;
    }

    /**
     * <p>
     * Sets the milestones to be updated.
     * </p>
     *
     * @param milestones
     *            the list of milestones to be updated.
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
