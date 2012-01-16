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
import com.topcoder.direct.services.project.milestone.ResponsiblePersonService;
import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.project.milestone.model.ResponsiblePerson;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.shared.util.logging.Logger;

/**
 * <p>
 * This action extends BaseDirectStrutsAction and provides execution to load information that is needed when opening a
 * page to create or edit milestones
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
 *  &lt;bean id=&quot;loadMilestonesAction&quot;
 *  class=&quot;com.topcoder.direct.actions.project.milestone.LoadMilestonesAction&quot;
 *  init-method=&quot;afterPropertiesSet&quot;&gt;
 *     &lt;property name=&quot;milestoneService&quot; ref=&quot;milestoneService&quot; /&gt;
 *     &lt;property name=&quot;responsiblePersonService&quot; ref=&quot;responsiblePersonService&quot; /&gt;
 *   &lt;/bean&gt;
 *
 *    &lt;bean id=&quot;milestoneService&quot;
 *    class=&quot;com.topcoder.direct.services.project.milestone.mock.MockMilestoneService&quot;/&gt;
 *
 *    &lt;bean id=&quot;responsiblePersonService&quot;
 *    class=&quot;com.topcoder.direct.services.project.milestone.mock.MockResponsiblePersonService&quot;/&gt;
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
public class LoadMilestonesAction extends BaseDirectStrutsAction {
    /**
     * <p>
     * Represents the logger of this action.
     * </p>
     */
    private static final Logger LOGGER = Logger.getLogger(LoadMilestonesAction.class);

    /**
     * <p>
     * Represents the signature of the executeAction method. It is used for logging.
     * </p>
     */
    private static final String SIGNATURE = LoadMilestonesAction.class.getName() + "#executeAction";

    /**
     * <p>
     * This is the MilestoneService. It is set in the setter. It can be retrieved in the getter. On initialization
     * check, must be not null. This field will be injected by the container (expected to be done only once), and is not
     * expected to change afterward.
     * </p>
     */
    private MilestoneService milestoneService;

    /**
     * <p>
     * This is the ResponsiblePersonService used to get the people. It is set in the setter. It can be retrieved in the
     * getter. On initialization check, must be not null. This field will be injected by the container (expected to be
     * done only once), and is not expected to change afterward.
     * </p>
     */
    private ResponsiblePersonService responsiblePersonService;

    /**
     * <p>
     * Represents the project ID. It is set in the setter. It can be retrieved in the getter. It may have any value.
     * This field will be set during the request phase by the container, and will not change afterwards.
     * </p>
     */
    private long projectId;

    /**
     * <p>
     * Represents the IDs of the milestones to be loaded. It is set in the setter. It can be retrieved in the getter. It
     * may have any value. This field will be set during the request phase by the container, and will not change
     * afterwards.
     * </p>
     */
    private List<Long> milestoneIds;

    /**
     * <p>
     * Represents the retrieved people. It is set in the setter. It can be retrieved in the getter. Initially null, it
     * will be set to a non-null value during the execute method. This field will be set by the execute method.
     * </p>
     */
    private List<ResponsiblePerson> responsiblePeople;

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
    public LoadMilestonesAction() {
        // empty
    }

    /**
     * <p>
     * Executes the action.
     * </p>
     *
     * @throws IllegalArgumentException
     *             if the milestoneIds is null or empty, contains null or duplicated element.
     * @throws EntityNotFoundException
     *             if any milestone is not found
     * @throws ProjectMilestoneManagementException
     *             if there are any errors during the execution of this method
     */
    protected void executeAction() throws ProjectMilestoneManagementException {
        Date startDate = ActionHelper.logEntrance(LOGGER, SIGNATURE);
        // check the milestoneIds
        ParameterCheckUtility.checkNotNullNorEmpty(milestoneIds, "milestoneIds");
        ParameterCheckUtility.checkNotNullElements(milestoneIds, "milestoneIds");
        ActionHelper.checkDuplicated(milestoneIds, "milestoneIds");
        // get milestones from service layer
        milestones = milestoneService.get(milestoneIds);
        // get responsiblePeople from service layer.
        responsiblePeople = responsiblePersonService.getAllResponsiblePeople(projectId);
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
     *            the instance of milestoneService used to ge milestones.
     */
    public void setMilestoneService(MilestoneService milestoneService) {
        this.milestoneService = milestoneService;
    }

    /**
     * <p>
     * Gets the ResponsiblePersonService used to get the responsible person.
     * </p>
     *
     * @return the ResponsiblePersonService used to get the responsible person.
     */
    public ResponsiblePersonService getResponsiblePersonService() {
        return responsiblePersonService;
    }

    /**
     * <p>
     * Sets the ResponsiblePersonService used to get the responsible person.
     * </p>
     *
     * @param responsiblePersonService
     *            the ResponsiblePersonService used to get the responsible person.
     */
    public void setResponsiblePersonService(ResponsiblePersonService responsiblePersonService) {
        this.responsiblePersonService = responsiblePersonService;
    }

    /**
     * <p>
     * Gets the ID of project.
     * </p>
     *
     * @return the ID of project
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * <p>
     * Sets the ID of project.
     * </p>
     *
     * @param projectId
     *            the project ID to set
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * <p>
     * Gets the IDs of milestones value to be loaded.
     * </p>
     *
     * @return the IDs of milestones value to be loaded.
     */
    public List<Long> getMilestoneIds() {
        return milestoneIds;
    }

    /**
     * <p>
     * Sets the IDs of milestones value to be loaded.
     * </p>
     *
     * @param milestoneIds
     *            the IDs of milestones value to be loaded.
     */
    public void setMilestoneIds(List<Long> milestoneIds) {
        this.milestoneIds = milestoneIds;
    }

    /**
     * <p>
     * Gets the list of retrieved responsible people.
     * </p>
     *
     * @return the list of retrieved responsiblePeople.
     */
    public List<ResponsiblePerson> getResponsiblePeople() {
        return responsiblePeople;
    }

    /**
     * <p>
     * Sets the list of retrieved responsible people.
     * </p>
     *
     * @param responsiblePeople
     *            the list of retrieved responsible people.
     */
    public void setResponsiblePeople(List<ResponsiblePerson> responsiblePeople) {
        this.responsiblePeople = responsiblePeople;
    }

    /**
     * <p>
     * Gets the list of retrieved milestones.
     * </p>
     *
     * @return the list of retrieved milestones.
     */
    public List<Milestone> getMilestones() {
        return milestones;
    }

    /**
     * <p>
     * Sets the list of retrieved milestones.
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
     *             if the milestoneService or responsiblePersonService is null.
     */
    public void afterPropertiesSet() {
        ValidationUtility.checkNotNull(milestoneService, "milestoneService",
                ProjectMilestoneManagementConfigurationException.class);
        ValidationUtility.checkNotNull(responsiblePersonService, "responsiblePersonService",
                ProjectMilestoneManagementConfigurationException.class);
    }
}
