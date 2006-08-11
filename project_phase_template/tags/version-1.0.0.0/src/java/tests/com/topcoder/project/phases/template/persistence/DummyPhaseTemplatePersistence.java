/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template.persistence;

import com.topcoder.project.phases.Project;
import com.topcoder.project.phases.template.PersistenceException;
import com.topcoder.project.phases.template.PhaseGenerationException;
import com.topcoder.project.phases.template.PhaseTemplatePersistence;
/**
 * <p>
 * Dummy implementation of <code>PhaseTemplatePersistence</code> for test.
 * </p>
 * @author flying2hk
 * @version 1.0
 */
public class DummyPhaseTemplatePersistence implements PhaseTemplatePersistence {
    /**
     * <p>
     * Generates an array of Phases from the template with the given name and
     * add them into the given Project object.The dependency hierarchy will be
     * generated too.
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
    public void generatePhases(String templateName, Project project) throws PhaseGenerationException,
            PersistenceException {
    }
    /**
     * <p>
     * Return the names of all templates defined in the persistence layer.
     * </p>
     * @return the names of all templates defined in the persistence layer.
     */
    public String[] getAllTemplateNames() {
        return null;
    }

}
