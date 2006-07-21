/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template;

import java.util.Date;

import com.topcoder.project.phases.Project;

/**
 * <p>
 * A phase template is a set of predefined project phases and their
 * dependencies, the phases will not have start date defined in the templates.
 * </p>
 * <p>
 * <code>PhaseTemplate</code> interface defines the contract to access the phase templates,
 * generally the implementations will manage several different templates which
 * are used to generate different project phase hierarchies. It provides the API
 * to generate a set of project phases from a given predefined template, and
 * compose a <code>Project</code> object with those phases, the project start date will
 * be generated automatically if there's no start date specified, it also provides the
 * API to retrieve names of all templates available to use.
 * </p>
 * <p>
 * Note that the phase start dates, phase statuses will NOT be included in the template,
 * so these information will NOT be populated to the phases, they should be set by the
 * calling application.
 * </p>
 * <p>
 * The implementations should be thread-safe.
 * </p>
 * @author albertwang, flying2hk
 * @version 1.0
 */
public interface PhaseTemplate {
    /**
     * <p>
     * Return the names of all the templates available to use.
     * </p>
     * @return the names of all the templates available to use
     */
    public String[] getAllTemplateNames();

    /**
     * <p>
     * Generate a set of project phases and compose a <code>Project</code> object with those
     * phases by applying the template with the given name.The start date of the
     * project will be set to the startDate provided by the caller.
     * </p>
     * @param templateName the template name
     * @param startDate the start date of the project
     * @return a Project with the phases generated from the given template
     * @throws PhaseTemplateException if any error occured during the generation
     *             so that we can not apply the template successfully
     * @throws IllegalArgumentException if templateName or startDate is null, or
     *             templateName is an empty string, or there's no template with
     *             the given name
     */
    public Project applyTemplate(String templateName, Date startDate) throws PhaseTemplateException;

    /**
     * <p>
     * Generate a set of project phases and compose a <code>Project</code> object with those
     * phases by applying the template with the given name.The start date of the
     * project will be generated automatically according to a specific generation logic.
     * </p>
     * @param templateName the template name
     * @return a Project with the phases generated from the given template
     * @throws PhaseTemplateException if any error occured during the generation
     *             so that we can not apply the template successfully
     * @throws IllegalArgumentException if templateName is null or an empty
     *             string, or there's no template with the given name
     */
    public Project applyTemplate(String templateName) throws PhaseTemplateException;
}
