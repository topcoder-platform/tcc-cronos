/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.project;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * This class holds the information about a project.
 * </p>
 *
 * <p>
 * When creating an instance of this class the user has two options:
 *
 * <ul>
 * <li> Use the default constructor and allow the GUID Generator component to
 * generate a unique id. </li>
 * <li> Use one of the parameterized constructors and provide an id for the
 * Project instance; if the id is already used by another project from the
 * persistence, then the newly created project will not be added to it. </li>
 * </ul>
 * </p>
 *
 * <p>
 * Also the user should not populate the creationDate and modificationDate
 * fields, because if he does, the project will not be added to the persistence.
 * These fields will be handled automatically by the component (the current date
 * will be used). When loading from the persistence, all the fields will be
 * properly populated.
 * </p>
 *
 * <p>
 * Version 2.0 added companyId field, setter and getter for this field and
 * constructors that initialize this new field.
 * </p>
 *
 * @author DanLazar, colau, costty000
 * @version 2.0
 *
 * @since 1.0
 */
public class Project {
    /**
     * <p>
     * Represents the id of the project. A value of -1 means that the user wants
     * this component to generate a value for this field using the GUID
     * Generator. This field can be initialized in the parameterized constructor
     * or by using the setId method.
     * </p>
     */
    private int id = -1;

    /**
     * <p>
     * Represents the name of the project. It can be set using the setName
     * method. Valid values: non-null, non-empty string.
     * </p>
     */
    private String name = null;

    /**
     * <p>
     * Represents the description of the project. Initial value is null. It can
     * be set using the setDescription method. Valid values: non-null, non-empty
     * string.
     * </p>
     */
    private String description = null;

    /**
     * <p>
     * Represents the creation date of the project, which is the date when the
     * project was newly added into the persistence. This field should not be
     * initialized by the user. It will be handled automatically by the
     * component (the current date will be used or loaded from persistence).
     * </p>
     */
    private Date creationDate = null;

    /**
     * <p>
     * Represents the user which created the project. Must be initialized by the
     * user, using the setCreationUser method. Valid values: non-null, non-empty
     * string.
     * </p>
     */
    private String creationUser = null;

    /**
     * <p>
     * Represents the modification date of the project, which is the date when
     * the project was updated in the persistence. This field should not be
     * initialized by the user. It will be handled automatically by the
     * component (the current date will be used or loaded from persistence).
     * </p>
     */
    private Date modificationDate = null;

    /**
     * <p>
     * Represents the user which modified the project. Must be initialized by
     * the user, using the setModificationUser method. Valid values: non-null,
     * non-empty string.
     * </p>
     */
    private String modificationUser = null;

    /**
     * <p>
     * Represents the estimated start date of the project. Must be initialized
     * by the user using the setter.
     * </p>
     */
    private Date startDate = null;

    /**
     * <p>
     * Represents the estimated end date of the project. Must be initialized by
     * the user using the setter.
     * </p>
     */
    private Date endDate = null;

    /**
     * <p>
     * Represents the id of the manager of the project. This field can be
     * initialized in the parameterized constructor or by using the setManagerId
     * method.
     * </p>
     */
    private int managerId = -1;

    /**
     * <p>
     * Represents the ids of the workers assigned to the project. It is
     * initialized in the constructors and the reference cannot be changed.
     * </p>
     */
    private Set workersIds = null;

    /**
     * <p>
     * Represents the ids of the time entries of the project. It is initialized
     * in the constructors and the reference cannot be changed.
     * </p>
     */
    private Set timeEntries = null;

    /**
     * <p>
     * Represents the ids of the expense entries of the project. It is
     * initialized in the constructors and the reference cannot be changed.
     * </p>
     */
    private Set expenseEntries = null;

    /**
     * <p>
     * Represents the id of the Company that this Project is bound to (the
     * company associated with this project). It will be initialized in the
     * parameterized constructor. Can also be initialized by using the setter.
     * </p>
     *
     * @since 2.0
     */
    private int companyId = -1;

    /**
     * <p>
     * Creates a new instance. The project will be assigned an id generated by
     * the GUID Generator, and have no workers, time entries and expense entries
     * associated with. The manager of this project will have an id of -1.
     * </p>
     */
    public Project() {
        this(-1); // id will be assigned by the GUID Generator
    }

    /**
     * <p>
     * Creates a new instance. The project will be assigned the given id, and
     * have no workers, time entries and expense entries associated with. The
     * manager of this project will have an id of -1.
     * </p>
     *
     * <p>
     * If the given id has the value of -1, the actual id will be assigned by
     * the GUID Generator.
     * </p>
     *
     * @param id
     *            the id of the project
     */
    public Project(int id) {
        this(id, -1, new HashSet());
    }

    /**
     * <p>
     * Creates a new instance. The project will be assigned the given id, and
     * have the workers of the given set of workersIds. The manager of this
     * project will have an id of managerId.
     * </p>
     *
     * <p>
     * If the given id has the value of -1, the actual id will be assigned by
     * the GUID Generator.
     * </p>
     *
     * @param id
     *            the id of the project
     * @param managerId
     *            the id of the manager
     * @param workersIds
     *            the ids of the workers
     *
     * @throws NullPointerException
     *             if the workersIds is null
     * @throws IllegalArgumentException
     *             if the workersIds contains null or non-Integer element
     */
    public Project(int id, int managerId, Set workersIds) {
        this.workersIds = new HashSet();
        this.timeEntries = new HashSet();
        this.expenseEntries = new HashSet();

        setId(id);
        setManagerId(managerId);
        setWorkersIds(workersIds);
    }

    /**
     * <p>
     * Create a new instance. Initialize the id field. Initialize the companyId
     * field. It will initialize the workersIds, timeEntries, expenseEntries to
     * the empty Set.
     * </p>
     *
     * @param id
     *            the value that will be assigned to the id field.
     * @param companyId
     *            the value that will be assigned to the companyId field.
     *
     * @since 2.0
     */
    public Project(int id, int companyId) {
        this(id);
        setCompanyId(companyId);
    }

    /**
     * <p>
     * Setter for the id of the project. A value of -1 means that the user wants
     * this component to generate a value for this field using the GUID
     * Generator.
     * </p>
     *
     * @param id
     *            the id of the project
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * <p>
     * Setter for the description of the project.
     * </p>
     *
     * @param description
     *            the description of the project
     *
     * @throws NullPointerException
     *             if the description is null
     * @throws IllegalArgumentException
     *             if the description is the empty string
     */
    public void setDescription(String description) {
        Util.checkString(description);
        this.description = description;
    }

    /**
     * <p>
     * Setter for the name of the project.
     * </p>
     *
     * @param name
     *            the name of the project
     *
     * @throws NullPointerException
     *             if the name is null
     * @throws IllegalArgumentException
     *             if the name is the empty string
     */
    public void setName(String name) {
        Util.checkString(name);
        this.name = name;
    }

    /**
     * <p>
     * Setter for the estimated start date of the project.
     * </p>
     *
     * @param startDate
     *            the estimated start date of the project
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
     * <p>
     * Setter for the estimated end date of the project.
     * </p>
     *
     * @param endDate
     *            the estimated end date of the project
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
     * <p>
     * Setter for the id of the manager of the project.
     * </p>
     *
     * @param managerId
     *            the id of the manager
     */
    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    /**
     * <p>
     * Setter for the ids of the workers assigned to the project. All the old
     * workers associated with the project will be cleared first before adding
     * the given workers. The workers will be copied to an inner set.
     * </p>
     *
     * @param workersIds
     *            the ids of the workers
     *
     * @throws NullPointerException
     *             if the workersIds is null
     * @throws IllegalArgumentException
     *             if the workersIds contains null or non-Integer element
     */
    public void setWorkersIds(Set workersIds) {
        Util.checkIntegerSet(workersIds);

        // if everything is fine, clear the old workersIds and add the new ones
        this.workersIds.clear();
        this.workersIds.addAll(workersIds);
    }

    /**
     * <p>
     * Setter for the creation date of the project, which is the date when the
     * project was newly added into the persistence. The user should not use
     * this method. This method should only be used by the persistence layer.
     * </p>
     *
     * @param creationDate
     *            the creation date of the project
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
     * Setter for the user which created the project.
     * </p>
     *
     * @param creationUser
     *            the user which created the project
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
     * Setter for the modification date of the project, which is the date when
     * the project was updated in the persistence. The user should not use this
     * method. This method should only be used by the persistence layer.
     * </p>
     *
     * @param modificationDate
     *            the modification date of the project
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
     * Setter for the user which modified the project.
     * </p>
     *
     * @param modificationUser
     *            the user which modified the project
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
     * Setter for the ids of the time entries assigned to the project. All the
     * old time entries associated with the project will be cleared first before
     * adding the given time entries. The time entries will be copied to an
     * inner set.
     * </p>
     *
     * @param timeEntries
     *            the ids of the time entries
     *
     * @throws NullPointerException
     *             if the timeEntries is null
     * @throws IllegalArgumentException
     *             if the timeEntries contains null or non-Integer element
     */
    public void setTimeEntries(Set timeEntries) {
        Util.checkIntegerSet(timeEntries);

        // if everything is fine, clear the old timeEntries and add the new ones
        this.timeEntries.clear();
        this.timeEntries.addAll(timeEntries);
    }

    /**
     * <p>
     * Setter for the ids of the expense entries assigned to the project. All
     * the old expense entries associated with the project will be cleared first
     * before adding the given expense entries. The expense entries will be
     * copied to an inner set.
     * </p>
     *
     * @param expenseEntries
     *            the ids of the expense entries
     *
     * @throws NullPointerException
     *             if the expenseEntries is null
     * @throws IllegalArgumentException
     *             if the expenseEntries contains null or non-Integer element
     */
    public void setExpenseEntries(Set expenseEntries) {
        Util.checkIntegerSet(expenseEntries);

        // if everything is fine, clear the old expenseEntries and add the new
        // ones
        this.expenseEntries.clear();
        this.expenseEntries.addAll(expenseEntries);
    }

    /**
     * <p>
     * Getter for the id of the project. If it returns the value of -1, the
     * actual id will be assigned by the GUID Generator.
     * </p>
     *
     * @return the id of the project
     */
    public int getId() {
        return id;
    }

    /**
     * <p>
     * Getter for the description of the project.
     * </p>
     *
     * @return the description of the project
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>
     * Getter for the name of the project.
     * </p>
     *
     * @return the name of the project
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Getter for the estimated start date of the project.
     * </p>
     *
     * @return the estimated start date of the project
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * <p>
     * Getter for the estimated end date of the project.
     * </p>
     *
     * @return the estimated end date of the project
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * <p>
     * Getter for the id of the manager of the project.
     * </p>
     *
     * @return the id of the manager of the project
     */
    public int getManagerId() {
        return managerId;
    }

    /**
     * <p>
     * Getter for the ids of the workers assigned to the project. Returns a
     * shallow copy of the inner set.
     * </p>
     *
     * @return a Set containing the ids of the workers assigned to the project
     */
    public Set getWorkersIds() {
        return new HashSet(workersIds);
    }

    /**
     * <p>
     * Getter for the creation date of the project, which is the date when the
     * project was newly added into the persistence.
     * </p>
     *
     * @return the creation date of the project
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * <p>
     * Getter for the user which created the project.
     * </p>
     *
     * @return the user which created the project
     */
    public String getCreationUser() {
        return creationUser;
    }

    /**
     * <p>
     * Getter for the modification date of the project, which is the date when
     * the project was updated in the persistence.
     * </p>
     *
     * @return the modification date of the project
     */
    public Date getModificationDate() {
        return modificationDate;
    }

    /**
     * <p>
     * Getter for the user which modified the project.
     * </p>
     *
     * @return the user which modified the project
     */
    public String getModificationUser() {
        return modificationUser;
    }

    /**
     * <p>
     * Getter for the ids of the time entries assigned to the project. Returns a
     * shallow copy of the inner set.
     * </p>
     *
     * @return a Set containing the ids of the time entries assigned to the
     *         project
     */
    public Set getTimeEntries() {
        return new HashSet(timeEntries);
    }

    /**
     * <p>
     * Getter for the ids of the expense entries assigned to the project.
     * Returns a shallow copy of the inner set.
     * </p>
     *
     * @return a Set containing the ids of the expense entries assigned to the
     *         project
     */
    public Set getExpenseEntries() {
        return new HashSet(expenseEntries);
    }

    /**
     * <p>
     * Adds the given worker id to the project. If the worker id already exists
     * in the project, it will not be added and false is returned.
     * </p>
     *
     * @param workerId
     *            the id of the worker to add
     *
     * @return true if the worker id was added, false otherwise
     */
    public boolean addWorkerId(int workerId) {
        return workersIds.add(new Integer(workerId));
    }

    /**
     * <p>
     * Removes the given worker id from the project. If the worker id does not
     * exist in the project, nothing happens and false is returned.
     * </p>
     *
     * @param workerId
     *            the id of the worker to remove
     *
     * @return true if the worker id was removed, false otherwise
     */
    public boolean removeWorkerId(int workerId) {
        return workersIds.remove(new Integer(workerId));
    }

    /**
     * <p>
     * Adds the given time entry id to the project. If the time entry id already
     * exists in the project, it will not be added and false is returned.
     * </p>
     *
     * @param entryId
     *            the id of the time entry to add
     *
     * @return true if the time entry id was added, false otherwise
     */
    public boolean addTimeEntry(int entryId) {
        return timeEntries.add(new Integer(entryId));
    }

    /**
     * <p>
     * Removes the given time entry id from the project. If the time entry id
     * does not exist in the project, nothing happens and false is returned.
     * </p>
     *
     * @param entryId
     *            the id of the time entry to remove
     *
     * @return true if the time entry id was removed, false otherwise
     */
    public boolean removeTimeEntry(int entryId) {
        return timeEntries.remove(new Integer(entryId));
    }

    /**
     * <p>
     * Adds the given expense entry id to the project. If the expense entry id
     * already exists in the project, it will not be added and false is
     * returned.
     * </p>
     *
     * @param entryId
     *            the id of the expense entry to add
     *
     * @return true if the expense entry id was added, false otherwise
     */
    public boolean addExpenseEntry(int entryId) {
        return expenseEntries.add(new Integer(entryId));
    }

    /**
     * <p>
     * Removes the given expense entry id from the project. If the expense entry
     * id does not exist in the project, nothing happens and false is returned.
     * </p>
     *
     * @param entryId
     *            the id of the expense entry to remove
     *
     * @return true if the expense entry id was removed, false otherwise
     */
    public boolean removeExpenseEntry(int entryId) {
        return expenseEntries.remove(new Integer(entryId));
    }

    /**
     * <p>
     * Setter for the companyId field.
     * </p>
     *
     * @param companyId
     *            the id of the company that this project will be assigned to
     *
     * @since 2.0
     */
    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    /**
     * <p>
     * Getter for the companyId field
     * </p>
     *
     * @return the id of the company that this project is assigned to
     *
     * @since 2.0
     */
    public int getCompanyId() {
        return companyId;
    }

}
