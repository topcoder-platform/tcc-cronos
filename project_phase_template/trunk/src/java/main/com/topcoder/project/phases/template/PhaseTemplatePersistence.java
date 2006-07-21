/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template;

import com.topcoder.project.phases.Project;

/**
 * <p>
 * <code>PhaseTemplatePersistence</code> interface acts as the persistence
 * layer of the phase templates, so that the persistence is pluggable and can be
 * added without code changes. It manages a set of templates, provides the API
 * to generate an array of Phases from a template it manages and add the phases
 * to a given <code>Project</code>.
 * </p>
 * <p>
 * Note that this interface generates only phases, the <code>Project</code> generation
 * is out of the scope of this interface.
 * </p>
 * <p>
 * Note that the phase start dates, phase statuses will NOT be included in the template,
 * so these information will NOT be populated to the phases, they should be set by the
 * calling application.
 * </p>
 * <p>
 * In this initial release, the persistence is readonly, all templates are not
 * modifiable with this component.The template authoring functionalities may be
 * added in the future versions.
 * </p>
 * <p>
 * If a concrete implementation need configuration, it must define a constructor
 * with a string namespace argument, otherwise a default constructor without any
 * argument must be provided.<code>{@link DefaultPhaseTemplate}</code> will
 * try these two constructors.
 * </p>
 * <p>
 * Implementations should be thread safe, and it is easy to achieve in the
 * initial release as the templates are readonly.
 * </p>
 * @author albertwang, flying2hk
 * @version 1.0
 */
public interface PhaseTemplatePersistence {
    /**
     * <p>
     * Generates an array of <code>Phase</code>s from the template with the given name and
     * add them into the given <code>Project</code> object.The dependency hierarchy will be
     * populated too.
     * </p>
     * @param templateName the template name
     * @param project the project which the phases will be added to
     * @throws IllegalArgumentException if the templateName is null or empty
     *             string, or the project is null
     * @throws PhaseGenerationException if any error occured in the phase
     *             generation(e.g. cyclic dependency, etc.) so that the
     *             generation process can not complete successfully
     * @throws PersistenceException if error occurs while accessing the
     *             persistence layer
     */
    public void generatePhases(String templateName, Project project)
        throws PhaseGenerationException, PersistenceException;

    /**
     * <p>
     * Return the names of all templates defined in the persistence layer.
     * </p>
     * @return the names of all templates defined in the persistence layer.
     */
    public String[] getAllTemplateNames();
}
