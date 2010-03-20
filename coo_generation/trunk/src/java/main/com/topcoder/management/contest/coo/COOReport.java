/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo;

import java.util.List;

/**
 * <p>
 * This class represents a COO Report. A COO Report consists of contest data and
 * list of component dependencies as well as project id.
 * </p>
 * <p>
 * <strong> Thread safety:</strong> This class is not thread safe since it is
 * mutable.
 * </p>
 *
 * @author bramandia, TCSDEVELOPER
 * @version 1.1
 */
public class COOReport {
    /**
     * Represents the contest data.
     *
     * Initialized to default value of the data type, has getter and setter,
     * once set will not be null.
     */
    private ContestData contestData;
    /**
     * Represents the component dependencies.
     *
     * Initialized to default value of the data type, has getter and setter,
     * once set: will not be null nor contain null element.
     */
    private List<ComponentDependency> componentDependencies;
    /**
     * Represents the project id.
     *
     * Initialized to default value of the data type, has getter and setter,
     * once set will not be negative.
     */
    private long projectId;

    /**
     * Represents the presents of error in dependencies.
     */
    private boolean dependenciesError = false;

    /**
     * Empty constructor.
     */
    public COOReport() {
    }

    /**
     * Getter for the contest data.
     *
     * @return The value of the contest data.
     */
    public ContestData getContestData() {
        return contestData;
    }

    /**
     * Getter for the component dependencies.
     *
     * @return The value of the component dependencies.
     */
    public List<ComponentDependency> getComponentDependencies() {
        return componentDependencies;
    }

    /**
     * Setter for the contest data.
     *
     * @param contestData The contest data. must not be null.
     * @throws IllegalArgumentException if any argument is invalid
     *
     */
    public void setContestData(ContestData contestData) {
        Helper.checkNull("contestDate", contestData);
        this.contestData = contestData;
    }

    /**
     * Setter for the component dependencies.
     *
     * @param componentDependencies The list of component dependencies. must not
     *            be null nor contain null element.
     * @throws IllegalArgumentException if any argument is invalid
     *
     */
    public void setComponentDependencies(
            List<ComponentDependency> componentDependencies) {
        Helper.checkNull("componentDependencies", componentDependencies);
        for (ComponentDependency dep : componentDependencies) {
            Helper.checkNull(null, "element in componentDependencies", dep);
        }
        this.componentDependencies = componentDependencies;
    }

    /**
     * Getter for the project id.
     *
     * @return The value of the project id.
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Setter for the project id.
     *
     * @param projectId The project id. must be positive.
     * @throws IllegalArgumentException if any argument is invalid
     *
     */
    public void setProjectId(long projectId) {
        Helper.checkId(null, "projectId", projectId);
        this.projectId = projectId;
    }

    /**
     * Getter for dependenciesError field.
     *
     * @return The value of the dependenciesError field.
     */
    public boolean isDependenciesError() {
        return dependenciesError;
    }

    /**
     * Setter for the dependenciesError field.
     * @param dependenciesError is any error presents.
     */
    public void setDependenciesError(boolean dependenciesError) {
        this.dependenciesError = dependenciesError;
    }
}

