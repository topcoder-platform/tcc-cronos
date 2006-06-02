/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.project;

import java.util.Date;

/**
 * <p>
 * This class holds the information about a project manager associated with a
 * project.
 * </p>
 *
 * <p>
 * When creating an instance of this class the user has two options:
 *
 * <ul>
 * <li> Use the default constructor which does nothing. </li>
 * <li> Use the parameterized constructor and provide an id and a project
 * argument. </li>
 * </ul>
 * </p>
 *
 * <p>
 * Also the user should not populate the creationDate and modificationDate
 * fields, because if he does, the project manager will not be added to the
 * persistence. These fields will be handled automatically by the component (the
 * current date will be used). When loading from the persistence, all the fields
 * will be properly populated.
 * </p>
 *
 * @author DanLazar, colau
 * @version 1.1
 *
 * @since 1.0
 */
public class ProjectManager {
    /**
     * <p>
     * Represents a project which is assigned to the project manager. It will be
     * initialized in the parameterized constructor. It can be also initialized
     * using the setProject method.
     * </p>
     */
    private Project project = null;

    /**
     * <p>
     * Represents the id of the project manager. This field can be initialized
     * in the parameterized constructor or by using the setManagerId method.
     * </p>
     */
    private int managerId = -1;

    /**
     * <p>
     * Represents the creation date of the project manager, which is the date
     * when the project manager was newly added into the persistence. This field
     * should not be initialized by the user. It will be handled automatically
     * by the component (the current date will be used or loaded from
     * persistence).
     * </p>
     */
    private Date creationDate = null;

    /**
     * <p>
     * Represents the user which created the project manager. Must be
     * initialized by the user, using the setCreationUser method. Valid values:
     * non-null, non-empty string.
     * </p>
     */
    private String creationUser = null;

    /**
     * <p>
     * Represents the modification date of the project manager, which is the
     * date when the project manager was updated in the persistence. This field
     * should not be initialized by the user. It will be handled automatically
     * by the component (the current date will be used or loaded from
     * persistence).
     * </p>
     */
    private Date modificationDate = null;

    /**
     * <p>
     * Represents the user which modified the project manager. Must be
     * initialized by the user, using the setModificationUser method. Valid
     * values: non-null, non-empty string.
     * </p>
     */
    private String modificationUser = null;

    /**
     * <p>
     * Creates a new instance. The project manager will have an id of -1, and
     * have no project associated with.
     * </p>
     */
    public ProjectManager() {
    }

    /**
     * <p>
     * Creates a new instance. The project manager will be assigned the given id
     * and the given project.
     * </p>
     *
     * @param project
     *            the project to assign to the project manager
     * @param managerId
     *            the id of the project manager
     *
     * @throws NullPointerException
     *             if the project is null
     */
    public ProjectManager(Project project, int managerId) {
        setProject(project);
        setManagerId(managerId);
    }

    /**
     * <p>
     * Setter for the project assigned to the project manager.
     * </p>
     *
     * @param project
     *            the project to assign to the project manager
     *
     * @throws NullPointerException
     *             if the project is null
     */
    public void setProject(Project project) {
        if (project == null) {
            throw new NullPointerException("project is null");
        }
        this.project = project;
    }

    /**
     * <p>
     * Setter for the id of the project manager.
     * </p>
     *
     * @param managerId
     *            the id of the project manager
     */
    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    /**
     * <p>
     * Setter for the creation date of the project manager, which is the date
     * when the project manager was newly added into the persistence. The user
     * should not use this method. This method should only be used by the
     * persistence layer.
     * </p>
     *
     * @param creationDate
     *            the creation date of the project manager
     *
     * @throws NullPointerException
     *             if the creationDate is null
     */
    public void setCreationDate(Date creationDate) {
        if (creationDate == null) {
            throw new NullPointerException("creationDate is null");
        }
        this.creationDate = creationDate;
    }

    /**
     * <p>
     * Setter for the user which created the project manager.
     * </p>
     *
     * @param creationUser
     *            the user which created the project manager
     *
     * @throws NullPointerException
     *             if the creationUser is null
     * @throws IllegalArgumentException
     *             if the creationUser is the empty string
     */
    public void setCreationUser(String creationUser) {
        Util.checkString(creationUser);
        this.creationUser = creationUser;
    }

    /**
     * <p>
     * Setter for the modification date of the project manager, which is the
     * date when the project manager was updated in the persistence. The user
     * should not use this method. This method should only be used by the
     * persistence layer.
     * </p>
     *
     * @param modificationDate
     *            the modification date of the project manager
     *
     * @throws NullPointerException
     *             if the modificationDate is null
     */
    public void setModificationDate(Date modificationDate) {
        if (modificationDate == null) {
            throw new NullPointerException("modificationDate is null");
        }
        this.modificationDate = modificationDate;
    }

    /**
     * <p>
     * Setter for the user which modified the project manager.
     * </p>
     *
     * @param modificationUser
     *            the user which modified the project manager
     *
     * @throws NullPointerException
     *             if the modificationUser is null
     * @throws IllegalArgumentException
     *             if the modificationUser is the empty string
     */
    public void setModificationUser(String modificationUser) {
        Util.checkString(modificationUser);
        this.modificationUser = modificationUser;
    }

    /**
     * <p>
     * Getter for the project assigned to the project manager.
     * </p>
     *
     * @return the project assigned to the project manager
     */
    public Project getProject() {
        return project;
    }

    /**
     * <p>
     * Getter for the id of the project manager.
     * </p>
     *
     * @return the id of the project manager
     */
    public int getManagerId() {
        return managerId;
    }

    /**
     * <p>
     * Getter for the creation date of the project manager, which is the date
     * when the project manager was newly added into the persistence.
     * </p>
     *
     * @return the creation date of the project manager
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * <p>
     * Getter for the user which created the project manager.
     * </p>
     *
     * @return the user which created the project manager
     */
    public String getCreationUser() {
        return creationUser;
    }

    /**
     * <p>
     * Getter for the modification date of the project manager, which is the
     * date when the project manager was updated in the persistence.
     * </p>
     *
     * @return the modification date of the project manager
     */
    public Date getModificationDate() {
        return modificationDate;
    }

    /**
     * <p>
     * Getter for the user which modified the project manager.
     * </p>
     *
     * @return the user which modified the project manager
     */
    public String getModificationUser() {
        return modificationUser;
    }
}
