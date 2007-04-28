/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseEntryManager;
import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryManager;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.TimeEntryManager;
import com.topcoder.timetracker.project.db.Util;

/**
 * <p>
 * This is a default implementation of the <code>ProjectUtility</code> interface.
 * </p>
 *
 * <p>
 * It utilizes instances of the <code>ProjectDAO</code> and <code>ProjectEntryDAO</code> in order
 * to fulfill the necessary CRUDE and search operations defined.
 * </p>
 *
 * <p>
 * Thread safety: Thread safety is dependent on the DAO implementations. Since the DAOs are also required
 * to be thread-safe, this class is thread safe.
 * </p>
 *
 * @author ShindouHikaru, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class ProjectUtilityImpl implements ProjectUtility {
    /**
     * <p>
     * This is an implementation of the <code>ProjectDAO</code> that is used to retrieve and modify the
     * persistent store with regards to the Project objects.
     * </p>
     *
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     *
     * <p>
     * It will never be null.
     * </p>
     */
    private final ProjectDAO projectDao;

    /**
     * <p>
     * This is an implementation of the <code>ProjectEntryDAO</code> that is used to retrieve and modify the
     * persistent store with regards to the Projects and its association with the Time entries.
     * </p>
     *
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     *
     * <p>
     * It will never be null.
     * </p>
     */
    private final ProjectEntryDAO timeEntryDao;

    /**
     * <p>
     * This is an implementation of the <code>ProjectEntryDAO</code> that is used to retrieve and modify the
     * persistent store with regards to the Projects and its association with the Expense entries.
     * </p>
     *
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     *
     * <p>
     * It will never be null.
     * </p>
     */
    private final ProjectEntryDAO expenseEntryDao;

    /**
     * <p>
     * This is an implementation of the <code>ProjectEntryDAO</code> that is used to retrieve and modify the
     * persistent store with regards to the Projects and its association with the FixedBilling entries.
     * </p>
     *
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     *
     * <p>
     * It will never be null.
     * </p>
     */
    private final ProjectEntryDAO fixedBillEntryDao;

    /**
     * <p>
     * Represents the time entry manager for managing the time entries.
     * </p>
     *
     * <p>
     * This class utilities this instance to get the company id for a given time entry id.
     * </p>
     *
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     *
     * <p>
     * It will never be null.
     * </p>
     */
    private final TimeEntryManager timeManager;

    /**
     * <p>
     * Represents the time entry manager for managing the fixed bill entries.
     * </p>
     *
     * <p>
     * This class utilities this instance to get the company id for a given fixed bill entry id.
     * </p>
     *
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     *
     * <p>
     * It will never be null.
     * </p>
     */
    private final FixedBillingEntryManager fixedBillingManager;

    /**
     * <p>
     * Represents the time entry manager for managing the expense entries.
     * </p>
     *
     * <p>
     * This class utilities this instance to get the company id for a given expense entry id.
     * </p>
     *
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     *
     * <p>
     * It will never be null.
     * </p>
     */
    private final ExpenseEntryManager expenseManager;

    /**
     * <p>
     * Constructor that accepts all the needed DAO instances to perform the
     * management operations.
     * </p>
     *
     * @param projectDao The DAO used to manage the project.
     * @param timeEntryDao The DAO used to manage time entries.
     * @param expenseEntryDao The DAO used to manage expense entries.
     * @param fixedBillEntryDao The DAO used to manage fixed bill entries.
     * @param timeManager The manager to get the company ids for time entries
     * @param fixedBillingManager The manager to get the company ids for fixed bill entries
     * @param expenseManager The manager to get the company ids for expense entries
     *
     * @throws IllegalArgumentException if any of the arguments are null.
     */
    public ProjectUtilityImpl(ProjectDAO projectDao, ProjectEntryDAO timeEntryDao, ProjectEntryDAO expenseEntryDao,
        ProjectEntryDAO fixedBillEntryDao, TimeEntryManager timeManager, FixedBillingEntryManager fixedBillingManager,
        ExpenseEntryManager expenseManager) {
        Util.checkNull(projectDao, "projectDao");
        Util.checkNull(timeEntryDao, "timeEntryDAO");
        Util.checkNull(expenseEntryDao, "expenseEntryDAO");
        Util.checkNull(fixedBillEntryDao, "fixedBillEntryDAO");
        Util.checkNull(timeManager, "timeManager");
        Util.checkNull(fixedBillingManager, "fixedBillingManager");
        Util.checkNull(expenseManager, "expenseManager");

        this.projectDao = projectDao;
        this.timeEntryDao = timeEntryDao;
        this.expenseEntryDao = expenseEntryDao;
        this.fixedBillEntryDao = fixedBillEntryDao;

        this.timeManager = timeManager;
        this.fixedBillingManager = fixedBillingManager;
        this.expenseManager = expenseManager;
    }

    /**
     * <p>
     * Defines a project to be recognized within the persistent store managed by this utility.
     * </p>
     *
     * <p>
     * A unique project id will automatically be generated and assigned to the project.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * <p>
     * The implementation will set the Project's creation and modification date to the current
     * date. These creation/modification details will also reflect in the persistent store.
     * </p>
     *
     * @param project The project for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if project is null, or the project contains null property
     * which is required in the persistence
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void addProject(Project project, boolean audit) throws DataAccessException {
        addProjects(new Project[] {project}, audit);
    }

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided Project parameter.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * <p>
     * The implementation will set the Project's modification date to the current
     * date. These modification details will also reflect in the persistent store.
     * </p>
     *
     * @param project The project for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if project is null, or the project contains null property
     * which is required in the persistence
     * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void updateProject(Project project, boolean audit) throws UnrecognizedEntityException, DataAccessException {
        updateProjects(new Project[] {project}, audit);
    }

    /**
     * <p>
     * Modifies the persistent store so that it  no longer contains data on the project with the specified
     * projectId.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param projectId The projectId for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if projectId &lt;= 0.
     * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void removeProject(long projectId, boolean audit) throws UnrecognizedEntityException, DataAccessException {
        removeProjects(new long[] {projectId}, audit);
    }

    /**
     * <p>
     * Retrieves a Project object that reflects the data in the persistent store on the Time Tracker Project
     * with the specified projectId.
     * </p>
     *
     * @param projectId The id of the project to retrieve.
     * @return The project with specified id.
     *
     * @throws IllegalArgumentException if projectId &lt;= 0.
     * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public Project getProject(long projectId) throws UnrecognizedEntityException, DataAccessException {
        return getProjects(new long[] {projectId})[0];
    }

    /**
     * <p>
     * Searches the persistent store for any projects that satisfy the criteria that was specified in the
     * provided search filter.
     * </p>
     *
     * <p>
     * The provided filter should be created using either the filters that are created using the
     * <code>ProjectFilterFactory</code> returned by {@link ProjectUtilityImpl#searchProjects(Filter)}, or
     * a composite Search Filters (such as <code>AndFilter</code>, <code>OrFilter</code> and
     * <code>NotFilter</code> from Search Builder component) that combines the filters created
     * using <code>ProjectFilterFactory</code>.
     * </p>
     *
     * <p>
     * An empty array is returned if no entities are found.
     * </p>
     *
     * @param filter The filter used to search for projects.
     * @return The projects satisfying the conditions in the search filter.
     *
     * @throws IllegalArgumentException if filter is null.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public Project[] searchProjects(Filter filter) throws DataAccessException {
        return projectDao.searchProjects(filter);
    }

    /**
     * <p>
     * This is a batch version of {@link ProjectUtilityImpl#addProject(Project, boolean)} method.
     * </p>
     *
     * @param projects An array of projects for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if projects is null, empty or contains null values, or some
     * project contains null property which is required in the persistence
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void addProjects(Project[] projects, boolean audit) throws DataAccessException {
        Util.updateTimeTrackerBeanDates(projects, "projects", true);

        projectDao.addProjects(projects, audit);
    }

    /**
     * <p>
     * This is a batch version of {@link ProjectUtilityImpl#updateProject(Project, boolean)} method.
     * </p>
     *
     * @param projects An array of projects for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentExceptionif projects is null, empty or contains null values, or some
     * project contains null property which is required in the persistence
     * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void updateProjects(Project[] projects, boolean audit) throws DataAccessException {
        Util.updateTimeTrackerBeanDates(projects, "projects", false);

        projectDao.updateProjects(projects, audit);
    }

    /**
     * <p>
     * This is a batch version of {@link ProjectUtilityImpl#removeProject(long, boolean)} method.
     * </p>
     *
     * @param projectIds An array of projectIds for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if projectIds is null, empty or contains values &lt;= 0.
     * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void removeProjects(long[] projectIds, boolean audit) throws DataAccessException {
        projectDao.removeProjects(projectIds, audit);
    }

    /**
     * <p>
     * This is a batch version of the {@link ProjectUtilityImpl#getProject(long)} method.
     * </p>
     *
     * @param projectsIds An array of projectIds for which projects should be retrieved.
     * @return The projects corresponding to the provided ids.
     *
     * @throws IllegalArgumentException if projectIds is null, empty or contains values &lt;= 0.
     * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public Project[] getProjects(long[] projectsIds) throws DataAccessException {
        return projectDao.getProjects(projectsIds);
    }

    /**
     * <p>
     * This associates an Entry with the project.
     * </p>
     *
     * <p>
     * It could be either an Expense, Time, or Fixed Billing entry, as specified by the entry type provided.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param projectId The project for which the operation should be performed.
     * @param entryId The id of the entry for which the operation should be performed.
     * @param type The type of the entry.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if any id has a value &lt;= 0 or type is null.
     * @throws InvalidCompanyException if the entry and project company ids do not match.
     * @throws UnrecognizedEntityException if a project or entry with the provided id was not found in the data store.
     * @throws DuplicateEntityException if the entry has already been associated with the project.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void addEntryToProject(long projectId, long entryId, EntryType type, boolean audit)
        throws UnrecognizedEntityException, InvalidCompanyException, DuplicateEntityException, DataAccessException {
        Util.checkNull(type, "type");

        Project project = projectDao.getProjects(new long[] {projectId})[0];

        try {
            if (type.equals(EntryType.EXPENSE_ENTRY)) {
                ExpenseEntry entry = expenseManager.retrieveEntry((int) entryId);
                if (entry == null) {
                    throw new UnrecognizedEntityException(entryId);
                }

                if (project.getCompanyId() != entry.getCompanyId()) {
                    throw new InvalidCompanyException(entry.getCompanyId(), project.getCompanyId());
                }

                expenseEntryDao.addEntryToProject(project, entryId, audit);
            }

            if (type.equals(EntryType.FIXED_BILLING_ENTRY)) {
                FixedBillingEntry entry = fixedBillingManager.getFixedBillingEntry(entryId);
                if (project.getCompanyId() != entry.getCompanyId()) {
                    throw new InvalidCompanyException(entry.getCompanyId(), project.getCompanyId());
                }

                fixedBillEntryDao.addEntryToProject(project, entryId, audit);
            }

            if (type.equals(EntryType.TIME_ENTRY)) {
                TimeEntry entry = timeManager.getTimeEntry(entryId);
                if (project.getCompanyId() != entry.getCompanyId()) {
                    throw new InvalidCompanyException(entry.getCompanyId(), project.getCompanyId());
                }

                timeEntryDao.addEntryToProject(project, entryId, audit);
            }
        } catch (PersistenceException e) {
            throw new DataAccessException("Unable to retrieve the expense entry.", e);
        } catch (com.topcoder.timetracker.entry.fixedbilling.UnrecognizedEntityException e) {
            throw new UnrecognizedEntityException(e.getId(), e.getMessage());
        } catch (com.topcoder.timetracker.entry.time.UnrecognizedEntityException e) {
            throw new UnrecognizedEntityException(e.getId(), e.getMessage());
        } catch (com.topcoder.timetracker.entry.fixedbilling.DataAccessException e) {
            throw new DataAccessException("Unable to retrieve the fixed billing entry.", e);
        } catch (com.topcoder.timetracker.entry.time.DataAccessException e) {
            throw new DataAccessException("Unable to retrieve the time entry.", e);
        }
    }

    /**
     * <p>
     * This disassociates an Entry with the project.
     * </p>
     *
     * <p>
     * It could be either an Expense, Time, or Fixed Billing entry, as specified by the entry type provided.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param projectId The project for which the operation should be performed.
     * @param entryId The id of the entry for which the operation should be performed.
     * @param type The type of the entry.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if either id is &lt;= 0 or type is null.
     * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void removeEntryFromProject(long projectId, long entryId, EntryType type, boolean audit)
        throws UnrecognizedEntityException, DataAccessException {
        Util.checkNull(type, "type");

        Project project = projectDao.getProjects(new long[] {projectId})[0];

        if (type.equals(EntryType.EXPENSE_ENTRY)) {
            expenseEntryDao.removeEntryFromProject(project, entryId, audit);
        }

        if (type.equals(EntryType.FIXED_BILLING_ENTRY)) {
            fixedBillEntryDao.removeEntryFromProject(project, entryId, audit);
        }

        if (type.equals(EntryType.TIME_ENTRY)) {
            timeEntryDao.removeEntryFromProject(project, entryId, audit);
        }
    }

    /**
     * <p>
     * Retrieves all the Entries of the specified type that are associated with the specified project.
     * </p>
     *
     * <p>
     * An empty array is returned if no entities are found.
     * </p>
     *
     * @param projectId The id of the project for which the operation should be performed.
     * @param type The type of Entry to be retrieved.
     * @return An array of long identifiers of the Entries corresponding to the given project id.
     *
     * @throws IllegalArgumentException if the projectId is &lt;= 0 or if type is null.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public long[] retrieveEntriesForProject(long projectId, EntryType type) throws DataAccessException {
        Util.checkNull(type, "type");

        long[] projectsIds = new long[] {projectId};

        if (type.equals(EntryType.EXPENSE_ENTRY)) {
            return expenseEntryDao.retrieveEntriesForProjects(projectsIds)[0];
        }

        if (type.equals(EntryType.FIXED_BILLING_ENTRY)) {
            return fixedBillEntryDao.retrieveEntriesForProjects(projectsIds)[0];
        }

        if (type.equals(EntryType.TIME_ENTRY)) {
            return timeEntryDao.retrieveEntriesForProjects(projectsIds)[0];
        }

        return new long[0];
    }

    /**
     * <p>
     * Retrieves all the Entries of the specified type that are associated with projects being performed
     * for a specific client.
     * </p>
     *
     * <p>
     * An empty array is returned if no entities are found.
     * </p>
     *
     * @param clientId The id of the client for which this operation should be performed.
     * @param type The type of Entry to be retrieved.
     * @return An array of long identifiers of the Entries corresponding to the given client id.
     *
     * @throws IllegalArgumentException if the clientId is &lt;= 0 or if type is null.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public long[] retrieveEntriesForClient(long clientId, EntryType type) throws DataAccessException {
        Util.checkNull(type, "type");

        Filter filter = getProjectFilterFactory().createClientIdFilter(clientId);
        Project[] projects = searchProjects(filter);

        if (projects.length == 0) {
            return new long[0];
        }

        long[] projectsIds = new long[projects.length];
        for (int i = 0; i < projects.length; i++) {
            projectsIds[i] = projects[i].getId();
        }

        if (type.equals(EntryType.EXPENSE_ENTRY)) {
            return toSingleLongArray(expenseEntryDao.retrieveEntriesForProjects(projectsIds));
        }

        if (type.equals(EntryType.FIXED_BILLING_ENTRY)) {
            return toSingleLongArray(fixedBillEntryDao.retrieveEntriesForProjects(projectsIds));
        }

        if (type.equals(EntryType.TIME_ENTRY)) {
            return toSingleLongArray(timeEntryDao.retrieveEntriesForProjects(projectsIds));
        }

        return new long[0];
    }

    /**
     * <p>
     * This method aggregates all the elements in a 2D array to a 1D array.
     * </p>
     *
     * @param values the 2D array
     * @return the 1D array that contains all the elements in the given 2D array
     */
    private long[] toSingleLongArray(long[][] values) {
        int size = 0;
        for (int i = 0; i < values.length; i++) {
            size += values[i].length;
        }

        long[] result = new long[size];
        int index = 0;
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                result[index++] = values[i][j];
            }
        }

        return result;
    }

    /**
     * <p>
     * Retrieves the <code>ProjectFilterFactory</code> that is capable of creating SearchFilters
     * to use when searching for projects.
     * </p>
     *
     * <p>
     * This is used to conveniently specify the search criteria that should be used. The filters
     * returned by the given factory should be used with {@link ProjectUtilityImpl#searchProjects(Filter)}.
     * </p>
     *
     * @return the <code>ProjectFilterFactory</code> that is capable of creating SearchFilters to
     * use when searching for projects.
     */
    public ProjectFilterFactory getProjectFilterFactory() {
        return projectDao.getProjectFilterFactory();
    }

    /**
     * <p>
     * Retrieves all the projects that are currently in the persistent store.
     * </p>
     *
     * <p>
     * An empty array is returned if no entities are found.
     * </p>
     *
     * @return An array of projects retrieved from the persistent store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public Project[] enumerateProjects() throws DataAccessException {
        return projectDao.enumerateProjects();
    }
}
