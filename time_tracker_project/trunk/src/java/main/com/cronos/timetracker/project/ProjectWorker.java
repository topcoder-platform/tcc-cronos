/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.project;

import java.math.BigDecimal;

import java.util.Date;

/**
 * <p>
 * This class holds the information about a project worker associated with a
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
 * fields, because if he does, the project worker will not be added to the
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
public class ProjectWorker {
    /**
     * <p>
     * Represents a project which is assigned to the project worker. It will be
     * initialized in the parameterized constructor. It can be also initialized
     * using the setProject method.
     * </p>
     */
    private Project project = null;

    /**
     * <p>
     * Represents the id of the project worker. This field can be initialized in
     * the parameterized constructor or by using the setWorkerId method.
     * </p>
     */
    private int workerId = -1;

    /**
     * <p>
     * Represents the estimated date of starting work on the project. It must be
     * initialized by the user using the setter.
     * </p>
     */
    private Date startDate = null;

    /**
     * <p>
     * Represents the estimated date of ending work on the project. It must be
     * initialized by the user using the setter.
     * </p>
     */
    private Date endDate = null;

    /**
     * <p>
     * Represents the hourly pay rate of the worker for the project. It must be
     * initialized by the user using the setter. Valid values: positive values.
     * </p>
     */
    private BigDecimal payRate = null;

    /**
     * <p>
     * Represents the creation date of the project worker, which is the date
     * when the project worker was newly added into the persistence. This field
     * should not be initialized by the user. It will be handled automatically
     * by the component (the current date will be used or loaded from
     * persistence).
     * </p>
     */
    private Date creationDate = null;

    /**
     * <p>
     * Represents the user which created the project worker. Must be initialized
     * by the user, using the setCreationUser method. Valid values: non-null,
     * non-empty string.
     * </p>
     */
    private String creationUser = null;

    /**
     * <p>
     * Represents the modification date of the project worker, which is the date
     * when the project worker was updated in the persistence. This field should
     * not be initialized by the user. It will be handled automatically by the
     * component (the current date will be used or loaded from persistence).
     * </p>
     */
    private Date modificationDate = null;

    /**
     * <p>
     * Represents the user which modified the project worker. Must be
     * initialized by the user, using the setModificationUser method. Valid
     * values: non-null, non-empty string.
     * </p>
     */
    private String modificationUser = null;

    /**
     * <p>
     * Creates a new instance. The project worker will have an id of -1, and
     * have no project associated with.
     * </p>
     */
    public ProjectWorker() {
    }

    /**
     * <p>
     * Creates a new instance. The project worker will be assigned the given id
     * and the given project.
     * </p>
     *
     * @param project
     *            the project to assign to the project worker
     * @param workerId
     *            the id of the project worker
     *
     * @throws NullPointerException
     *             if the project is null
     */
    public ProjectWorker(Project project, int workerId) {
        setProject(project);
        setWorkerId(workerId);
    }

    /**
     * <p>
     * Setter for the project assigned to the project worker.
     * </p>
     *
     * @param project
     *            the project to assign to the project worker
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
     * Setter for the id of the project worker.
     * </p>
     *
     * @param workerId
     *            the id of the project worker
     */
    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    /**
     * Setter for the estimated date of starting work on the project.
     *
     * @param startDate
     *            the estimated date of starting work on the project
     *
     * @throws NullPointerException
     *             if the startDate is null
     */
    public void setStartDate(Date startDate) {
        if (startDate == null) {
            throw new NullPointerException("startDate is null");
        }
        this.startDate = startDate;
    }

    /**
     * Setter for the estimated date of ending work on the project.
     *
     * @param endDate
     *            the estimated date of ending work on the project
     *
     * @throws NullPointerException
     *             if the endDate is null
     */
    public void setEndDate(Date endDate) {
        if (endDate == null) {
            throw new NullPointerException("endDate is null");
        }
        this.endDate = endDate;
    }

    /**
     * Setter for the hourly pay rate of the worker for the project.
     *
     * @param payRate
     *            the hourly pay rate of the worker for the project
     *
     * @throws NullPointerException
     *             if the payRate is null
     * @throws IllegalArgumentException
     *             if the value of the payRate is less than 0
     */
    public void setPayRate(BigDecimal payRate) {
        if (payRate == null) {
            throw new NullPointerException("payRate is null");
        }
        if (payRate.compareTo(new BigDecimal(0)) < 0) {
            throw new IllegalArgumentException("payRate is less than 0");
        }
        this.payRate = payRate;
    }

    /**
     * <p>
     * Setter for the creation date of the project worker, which is the date
     * when the project worker was newly added into the persistence. The user
     * should not use this method. This method should only be used by the
     * persistence layer.
     * </p>
     *
     * @param creationDate
     *            the creation date of the project worker
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
     * Setter for the user which created the project worker.
     * </p>
     *
     * @param creationUser
     *            the user which created the project worker
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
     * Setter for the modification date of the project worker, which is the date
     * when the project worker was updated in the persistence. The user should
     * not use this method. This method should only be used by the persistence
     * layer.
     * </p>
     *
     * @param modificationDate
     *            the modification date of the project worker
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
     * Setter for the user which modified the project worker.
     * </p>
     *
     * @param modificationUser
     *            the user which modified the project worker
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
     * Getter for the project assigned to the project worker.
     * </p>
     *
     * @return the project assigned to the project worker
     */
    public Project getProject() {
        return project;
    }

    /**
     * <p>
     * Getter for the id of the project worker.
     * </p>
     *
     * @return the id of the project worker
     */
    public int getWorkerId() {
        return workerId;
    }

    /**
     * Getter for the estimated date of starting work on the project.
     *
     * @return the estimated date of starting work on the project
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Getter for the estimated date of ending work on the project.
     *
     * @return the estimated date of ending work on the project
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Getter for the hourly pay rate of the worker for the project.
     *
     * @return the hourly pay rate of the worker for the project
     */
    public BigDecimal getPayRate() {
        return payRate;
    }

    /**
     * <p>
     * Getter for the creation date of the project worker, which is the date
     * when the project worker was newly added into the persistence.
     * </p>
     *
     * @return the creation date of the project worker
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * <p>
     * Getter for the user which created the project worker.
     * </p>
     *
     * @return the user which created the project worker
     */
    public String getCreationUser() {
        return creationUser;
    }

    /**
     * <p>
     * Getter for the modification date of the project worker, which is the date
     * when the project worker was updated in the persistence.
     * </p>
     *
     * @return the modification date of the project worker
     */
    public Date getModificationDate() {
        return modificationDate;
    }

    /**
     * <p>
     * Getter for the user which modified the project worker.
     * </p>
     *
     * @return the user which modified the project worker
     */
    public String getModificationUser() {
        return modificationUser;
    }
}
