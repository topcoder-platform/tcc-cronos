/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.jira;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.topcoder.jira.managers.entities.JiraProject;
import com.topcoder.jira.managers.entities.JiraVersion;

/**
 * This java bean holds result of JIRA project retrieval. It consists of retrieval result, project
 * and version. Any attribute in this bean is optional.
 * <p>
 * This entity can be serialized to XML. It is done through the use of class annotations:
 * <ol>
 * <li>XmlAccessorType(XmlAccessType.FIELD)</li>
 * <li>XmlType(name = "jiraProject", propOrder = {"project, retrievalResult, version"})</li>
 * </ol>
 * <p>
 * Thread-safety. This class is not thread-safe. It doesn't provide setters for the inner fields but
 * fields still can be changed because getters return objects themselves (not deep copies). The same
 * situation with constructor that propagates parameters themselves. Nevertheless application will
 * use this class in a thread-safe manner. It will not change objects returned from getters and it
 * will not change objects passed to constructor.
 *
 * @author Mafy, agh
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "jiraProjectRetrievalResult", propOrder = {"project", "retrievalResult", "version"})
public class JiraProjectRetrievalResult implements Serializable {

    /**
     * Generated serial version ID.
     */
    private static final long serialVersionUID = 8069550934629344991L;

    /**
     * This field represents the retrieved project.
     * <p>
     * Initialized in constructor. Can be accessed through the corresponding getter.
     * <p>
     * Can take any value.
     */
    private final JiraProject project;

    /**
     * This field represents the retrieval result.
     * <p>
     * Initialized in constructor. Can be accessed through the corresponding getter.
     * <p>
     * Can take any value.
     */
    private final JiraProjectRetrievalResultAction retrievalResult;

    /**
     * This field represents the retrieved version.
     * <p>
     * Initialized in constructor. Can be accessed through the corresponding getter.
     * <p>
     * Can take any value.
     */
    private final JiraVersion version;

    /**
     * Creates a new <code>JiraProjectRetrievalResult</code> with the given project, version and
     * retrieval result.
     *
     * @param project retrieved project. It can be any value.
     * @param version retrieved version. It can be any value.
     * @param retrievalResult retrieval result. It can be any value.
     */
    public JiraProjectRetrievalResult(JiraProject project, JiraVersion version,
        JiraProjectRetrievalResultAction retrievalResult) {
        this.project = project;
        this.version = version;
        this.retrievalResult = retrievalResult;
    }

    /**
     * Getter for the retrieved project.
     *
     * @return retrieved project. It can be any value.
     */
    public JiraProject getProject() {
        return project;
    }

    /**
     * Getter for the retrieval result.
     *
     * @return retrieval result. It can be any value.
     */
    public JiraProjectRetrievalResultAction getRetrievalResult() {
        return retrievalResult;
    }

    /**
     * Getter for the retrieved version.
     *
     * @return retrieved version. It can be any value.
     */
    public JiraVersion getVersion() {
        return version;
    }
}
