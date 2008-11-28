/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.jira;

import com.topcoder.jira.managers.entities.JiraProject;
import com.topcoder.jira.managers.entities.JiraVersion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * This java bean holds result of JIRA project creation. It consists of creation result, project and version. Any
 * attribute in this bean is optional. <p> This entity can be serialized to XML. It is done through the use of class
 * annotations: <ol> <li>XmlAccessorType(XmlAccessType.FIELD)</li> <li>XmlType(name = "jiraProject", propOrder =
 * {"project, actionTaken, version"})</li> </ol> <p> Thread-safety. This class is not thread-safe. It doesn't provide
 * setters for the inner fields but fields still can be changed because getters return objects themselves (not deep
 * copies). The same situation with constructor that propagates parameters themselves. Nevertheless application will use
 * this class in a thread-safe manner. It will not change objects returned from getters and it will not change objects
 * passed to constructor.
 *
 * @author Mafy, agh
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "jiraProjectCreationResult", propOrder = {"project", "actionTaken", "version"})
public class JiraProjectCreationResult implements Serializable {

    /**
     * Generated serial version ID.
     */
    private static final long serialVersionUID = 4230317782495786977L;

    public void setProject(JiraProject project) {
        this.project = project;
    }

    public void setActionTaken(JiraProjectCreationAction actionTaken) {
        this.actionTaken = actionTaken;
    }

    public void setVersion(JiraVersion version) {
        this.version = version;
    }

    /**
     * This field represents the created project. <p> Initialized in constructor. Can be accessed through the
     * corresponding getter. <p> Can take any value.
     */
    private JiraProject project;

    /**
     * This field represents the result of project creation. <p> Initialized in constructor. Can be accessed through the
     * corresponding getter. <p> Can take any value.
     */
    private JiraProjectCreationAction actionTaken;

    /**
     * This field represents the created version. <p> Initialized in constructor. Can be accessed through the
     * corresponding getter. <p> Can take any value.
     */
    private JiraVersion version;

    /**
     * Creates a new <code>JiraProjectCreationResult</code> with the given project, version and creation result.
     *
     * @param project     created project. It can be any value.
     * @param version     created version. It can be any value.
     * @param actionTaken result of project creation. It can be any value.
     */
    public JiraProjectCreationResult(
            JiraProject project, JiraVersion version, JiraProjectCreationAction actionTaken) {
        this.project = project;
        this.version = version;
        this.actionTaken = actionTaken;
    }

    public JiraProjectCreationResult() {
    }

    /**
     * Getter for the created project.
     *
     * @return retrieved project. It can be any value.
     */
    public JiraProject getProject() {
        return project;
    }

    /**
     * Getter for the result of project creation.
     *
     * @return result of project creation. It can be any value.
     */
    public JiraProjectCreationAction getActionTaken() {
        return actionTaken;
    }

    /**
     * Getter for the created version.
     *
     * @return retrieved version. It can be any value.
     */
    public JiraVersion getVersion() {
        return version;
    }
}
