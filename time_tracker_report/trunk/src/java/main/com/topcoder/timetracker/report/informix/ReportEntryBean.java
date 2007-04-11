/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.report.informix;

import com.topcoder.timetracker.client.Client;
import com.topcoder.timetracker.project.Project;
import com.topcoder.timetracker.user.User;
import java.io.Serializable;

/**
 * <p>
 * This class represents the father report for the specified children reports about entry types.
 * This class contains all the common fields. An instance of this class represents an entry with all
 * informations. It is a bean which implements the Serializable interface: this is possible because
 * all fields are bean.
 * </p>
 *
 * <p>
 * <strong>Thread-safety:</strong> This class isn't thread safe because it's a bean and haven't the
 * setter method synchronized.
 * </p>
 *
 * @author fabrizyo, rinoavd
 * @version 3.1
 */
public abstract class ReportEntryBean implements Serializable {

    /**
     * <p>
     * Represents the client associate to the entry. It's changeable and initialized to null.
     * </p>
     */
    private Client client;

    /**
     * <p>
     * Represents the project associate to the entry. It's changeable and initialized to null.
     * </p>
     */
    private Project project;

    /**
     * <p>
     * Represents the user associate to the entry. It's changeable and initialized to null.
     * </p>
     */
    private User user;

    /**
     * <p>
     * Constructs an empty <code>ReportEntryBean</code>.
     * </p>
     */
    public ReportEntryBean() {
        // empty
    }

    /**
     * <p>
     * Returns the client.
     * </p>
     *
     * @return the client.
     */
    public Client getClient() {
        return this.client;
    }

    /**
     * <p>
     * Sets the client.
     * </p>
     *
     * @param client the client to set
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * <p>
     * Returns the project.
     * </p>
     *
     * @return the project
     */
    public Project getProject() {
        return this.project;
    }

    /**
     * <p>
     * Sets the project.
     * </p>
     *
     * @param project the project to set
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * <p>
     * Returns the user.
     * </p>
     *
     * @return the user
     */
    public User getUser() {
        return this.user;
    }

    /**
     * <p>
     * Set the user.
     * </p>
     *
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }
}
