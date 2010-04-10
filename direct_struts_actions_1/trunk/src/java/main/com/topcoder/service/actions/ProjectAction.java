/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions;

import com.topcoder.service.facade.project.ProjectServiceFacade;

/**
 * <p>
 * This is a base class only to hold the project service facade shared among several actions.
 * </p>
 * <p>
 * <b>Thread Safety</b>: In <b>Struts 2</b> framework, the action is constructed for every request so the thread safety
 * is not required (instead in Struts 1 the thread safety is required because the action instances are reused). This
 * class is mutable and stateful: it's not thread safe.
 * </p>
 *
 * @author fabrizyo, FireIce
 * @version 1.0
 */
public abstract class ProjectAction extends BaseDirectStrutsAction {
    /**
     * <p>
     * Represents the unique serial version id.
     * </p>
     */
    private static final long serialVersionUID = -8666921545810824646L;

    /**
     * <p>
     * It's the service used to performing the creation or update of the project.
     * </p>
     * <p>
     * It's used in the <code>executeAction</code> method. It can't be null when the logic is performed.
     * </p>
     * *
     * <p>
     * It's injected by the setter and returned by the getter.
     * </p>
     */
    private ProjectServiceFacade projectServiceFacade;

    /**
     * <p>
     * Creates a <code>ProjectAction</code> instance.
     * </p>
     */
    protected ProjectAction() {
    }

    /**
     * <p>
     * Gets the project service facade.
     * </p>
     *
     * @return the project service facade
     */
    public ProjectServiceFacade getProjectServiceFacade() {
        return projectServiceFacade;
    }

    /**
     * <p>
     * Set the project service facade.
     * </p>
     *
     * @param projectServiceFacade
     *            the project service facade to set
     * @throws IllegalArgumentException
     *             if <b>projectServiceFacade</b> is <code>null</code>
     */
    public void setProjectServiceFacade(ProjectServiceFacade projectServiceFacade) {
        DirectStrutsActionsHelper.checkNull(projectServiceFacade, "projectServiceFacade");

        this.projectServiceFacade = projectServiceFacade;
    }
}
