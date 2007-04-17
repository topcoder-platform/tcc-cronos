
package com.topcoder.timetracker.project;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.project.EntryType;
import com.topcoder.timetracker.project.Project;
import com.topcoder.timetracker.project.ProjectFilterFactory;

/**
 * <p>
 * This is a default implementation of the ProjectUtility interface.  it utilizes instances of the
 * ProjectDAO and ProjectEntryDAO in order to fulfill the necessary CRUDE and
 * search operations defined.
 * </p>
 * <p>
 * Thread safety:
 * Thread safety is dependent on the DAO implementations.  Since the DAOs are also required
 * to be thread-safe, this class is thread safe.
 * <p>Changes in version 3.2:</p>
 * <p>Transaction management removed, audits for failed operations are no longer rolled back.</p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm64af]
 */
public class ProjectUtilityImpl implements com.topcoder.timetracker.project.ProjectUtility {

/**
 * <p>
 * This is an implementation of the ProjectDAO that is used to retrieve and modify the
 * persistent store with regards to the Project objects.  All Project-facing methods will 
 * delegate to this.
 * </p>
 * <p>
 * Initialized In:  Constructor
 * </p>
 * <p>
 * Accessed In: No Access
 * </p>
 * <p>
 * Modified In: Not Modified
 * </p>
 * <p>
 * Utilized In: All methods of this class
 * </p>
 * <p>
 * Valid Values: Not null instances of ProjectDAO
 * </p>
 * 
 * @poseidon-object-id [I7d5b1a38m110c4518e28mm5835]
 */
    private final com.topcoder.timetracker.project.ProjectDAO projectDao;

/**
 * <p>
 * This is an implementation of the ProjectEntryDAO that is used to retrieve and modify the
 * persistent store with regards to the Projects and its association with the Time entries.
 * </p>
 * <p>
 * Initialized In:  Constructor
 * </p>
 * <p>
 * Accessed In: No Access
 * </p>
 * <p>
 * Modified In: Not Modified
 * </p>
 * <p>
 * Utilized In: addEntryToProject, removeEntryFromProject, retrieveEntriesForProject,
 * retrieveEntriesForClient
 * </p>
 * <p>
 * Valid Values: Not null instances of ProjectDAO
 * </p>
 * 
 * @poseidon-object-id [I7d5b1a38m110c4518e28mm5824]
 */
    private final com.topcoder.timetracker.project.ProjectEntryDAO timeEntryDao;

/**
 * <p>
 * This is an implementation of the ProjectEntryDAO that is used to retrieve and modify the
 * persistent store with regards to the Projects and its association with the Expense entries.
 * </p>
 * <p>
 * Initialized In:  Constructor
 * </p>
 * <p>
 * Accessed In: No Access
 * </p>
 * <p>
 * Modified In: Not Modified
 * </p>
 * <p>
 * Utilized In: addEntryToProject, removeEntryFromProject, retrieveEntriesForProject,
 * retrieveEntriesForClient
 * </p>
 * <p>
 * Valid Values: Not null instances of ProjectDAO
 * </p>
 * 
 * @poseidon-object-id [I7d5b1a38m110c4518e28mm5789]
 */
    private final com.topcoder.timetracker.project.ProjectEntryDAO expenseEntryDao;

/**
 * <p>
 * This is an implementation of the ProjectEntryDAO that is used to retrieve and modify the
 * persistent store with regards to the Projects and its association with the FixedBilling entries.
 * </p>
 * <p>
 * Initialized In:  Constructor
 * </p>
 * <p>
 * Accessed In: No Access
 * </p>
 * <p>
 * Modified In: Not Modified
 * </p>
 * <p>
 * Utilized In: addEntryToProject, removeEntryFromProject, retrieveEntriesForProject,
 * retrieveEntriesForClient
 * </p>
 * <p>
 * Valid Values: Not null instances of ProjectDAO
 * </p>
 * 
 * @poseidon-object-id [I7d5b1a38m110c4518e28mm5778]
 */
    private final com.topcoder.timetracker.project.ProjectEntryDAO fixedBillEntryDao;

/**
 * <p>
 * Constructor that accepts all the needed DAO instances to perform the 
 * management operations.
 * </p>
 * 
 * @poseidon-object-id [I7d5b1a38m110c4518e28mm5813]
 * @param projectDao The DAO used to manage the project.
 * @param timeEntryDAO The DAO used to manage time entries.
 * @param expenseEntryDAO The DAO used to manage expense entries.
 * @param fixedBillEntryDAO The DAO used to manage fixed bill entries.
 * @throws IllegalArgumentException if any of the arguments are null.
 */
    public  ProjectUtilityImpl(com.topcoder.timetracker.project.ProjectDAO projectDao, com.topcoder.timetracker.project.ProjectEntryDAO timeEntryDAO, com.topcoder.timetracker.project.ProjectEntryDAO expenseEntryDAO, com.topcoder.timetracker.project.ProjectEntryDAO fixedBillEntryDAO) {        
        this.projectDao = projectDao;
        this.fixedBillEntryDao = fixedBillEntryDAO;
        this.expenseEntryDao = expenseEntryDAO;
        this.timeEntryDao = timeEntryDAO;
    } 

/**
 * <p>
 * Defines a project to be recognized within the persistent store managed by this utility.  A unique
 * project id will automatically be generated and assigned to the project.  There is also the
 * option to perform an audit. 
 * </p>
 * <p>
 * The implementation will set the Project's creation and modification date to the current
 * date.   These creation/modification details will also reflect in the persistent store.
 * </p>
 * <p>
 * Implementation Notes:
 *    - Set the modification and creation date to the current date.
 *    - Delegate to the ProjectDAO using a single-element array.
 * </p>
 * 
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm640c]
 * @param project The project for which the operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws IllegalArgumentException if project is null.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 */
    public void addProject(com.topcoder.timetracker.project.Project project, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * Modifies the persistent store so that it now reflects the data in the provided Project parameter.
 * There is also the option to perform an audit.
 * </p>
 * <p>
 * The implementation will set the Project's modification date to the current
 * date.   These modification
 * details will also reflect in the persistent store.
 * </p>
 * <p>
 * Implementation Notes:
 *    - Set the modification date to the current date.
 *    - Delegate to the ProjectDAO using a single-element array.
 * </p>
 * 
 * 
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm6400]
 * @param project The project for which the operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws IllegalArgumentException if project is null.
 * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 */
    public void updateProject(com.topcoder.timetracker.project.Project project, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * Modifies the persistent store so that it  no longer contains data on the project with the specified
 * projectId.
 * </p>
 * <p>
 * There is also the option to perform an audit.
 * </p>
 * <p>
 * Implementation Notes:
 *    - Delegate to the ProjectDAO using a single-element array.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm63f3]
 * @param projectId The projectId for which the operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
 * @throws IllegalArgumentException if projectId <= 0.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 */
    public void removeProject(long projectId, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * Retrieves a Project object that reflects the data in the persistent store on the Time Tracker Project
 * with the specified projectId.
 * </p>
 * <p>
 * Implementation Notes:
 *    - Delegate to the ProjectDAO using a single-element array.
 * </p>
 * 
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm63e7]
 * @return The project with specified id.
 * @param projectId The id of the project to retrieve.
 * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 */
    public com.topcoder.timetracker.project.Project getProject(long projectId) {        
        // your code here
        return null;
    } 

/**
 * <p>
 * Searches the persistent store for any projects that satisfy the criteria that was specified in the
 * provided search filter.  The provided filter should be created using either the filters that are
 * created using the ProjectFilterFactory returned by getProjectFilterFactory of this instance, or
 * a composite Search Filters (such as AndFilter, OrFilter and NotFilter from Search Builder component)
 * that combines the filters created using ProjectFilterFactory.
 * </p>
 * <p>
 * Implementation Notes:
 *    - Delegate to the ProjectDAO.
 * </p>
 * 
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm63dc]
 * @return The projects satisfying the conditions in the search filter.
 * @param filter The filter used to search for projects.
 * @throws IllegalArgumentException if filter is null.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 */
    public com.topcoder.timetracker.project.Project[] searchProjects(com.topcoder.search.builder.filter.Filter filter) {        
        // your code here
        return null;
    } 

/**
 * <p>
 * This is a batch version of the addProject method.
 * </p>
 * 
 * <p>
 * Implementation Notes:
 *    - Update the creation and modification date of each project (the dates for all of them should
 *      be the same, so use the same Date object for all).
 *    - Delegate to the ProjectDAO.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm63d0]
 * @param projects An array of projects for which the operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws DuplicateEntityException if a project with the same id has already been found.
 * @throws IllegalArgumentExceptionif projects is null or contains null values.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 */
    public void addProjects(com.topcoder.timetracker.project.Project[] projects, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * This is a batch version of the updateProject method.
 * </p>
 * <p>
 * Implementation Notes:
 *    - Update the modification date of each project (the dates for all of them should
 *      be the same, so use the same Date object for all).
 *    - Delegate to the ProjectDAO.
 * </p>
 * 
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm63c3]
 * @param projects An array of projects for which the operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
 * @throws IllegalArgumentExceptionif projects is null or contains null values.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 */
    public void updateProjects(com.topcoder.timetracker.project.Project[] projects, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * This is a batch version of the removeProject method. 
 * </p>
 * <p>
 * Implementation Notes:
 *    - Delegate to the ProjectDAO.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm63b6]
 * @param projectIds An array of projectIds for which the operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
 * @throws IllegalArgumentException if projectIds is null or contains values <= 0.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 */
    public void removeProjects(long[] projectIds, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * This is a batch version of the getProject method.
 * </p>
 * <p>
 * Implementation Notes:
 *    - Delegate to the ProjectDAO.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm63aa]
 * @return The projects corresponding to the provided ids.
 * @param projectsIds An array of projectIds for which projects should be retrieved.
 * @throws IllegalArgumentException if projectIds is null or contains values <= 0.
 * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 */
    public com.topcoder.timetracker.project.Project[] getProjects(long[] projectsIds) {        
        // your code here
        return null;
    } 

/**
 * <p>
 * This associates an Entry with the project.  It could be either an Expense, Time, or Fixed Billing
 * entry, as specified by the entry type provided.   There is also the
 * option to perform an audit.
 * </p>
 * <p>
 * Implementation Notes:
 *    - Depending on the type of the DAO, delegate to the time, expense, or fixedBillEntryDao.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm639b]
 * @param projectId The project for which the operation should be performed.
 * @param entryId The id of the entry for which the operation should be performed.
 * @param type The type of the entry.
 * @param audit Indicates whether an audit should be performed.
 * @throws InvalidCompanyException if the entry and project company ids do not match.
 * @throws IllegalArgumentException if any id has a value <= 0.
 * @throws UnrecognizedEntityException if a project or entry with the provided id was not found in the data store.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 * @throws DuplicateEntityException if the entry has already been associated with the project.
 */
    public void addEntryToProject(long projectId, long entryId, com.topcoder.timetracker.project.EntryType type, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * This disassociates an Entry with the project.  It could be either an Expense, Time, or Fixed Billing
 * entry, as specified by the entry type provided.   There is also the
 * option to perform an audit.
 * </p>
 * <p>
 * Implementation Notes:
 *    - Depending on the type of the DAO, delegate to the time, expense, or fixedBillEntryDao.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm6388]
 * @param projectId The project for which the operation should be performed.
 * @param entryId The id of the entry for which the operation should be performed.
 * @param type The type of the entry.
 * @param audit Indicates whether an audit should be performed.
 * @throws UnrecognizedEntityException if a project or entry with the provided id was not found in the data store.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 * @throws IllegalArgumentException if either id is <= 0.
 */
    public void removeEntryFromProject(long projectId, long entryId, com.topcoder.timetracker.project.EntryType type, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * Retrieves all the Entries of the specified type that are associated with the specified project.
 * </p>
 * <p>
 * Implementation Notes:
 *    - Depending on the type of the DAO, delegate to the time, expense, or fixedBillEntryDao
 *      using a single-element array.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm6379]
 * @return An array of long identifiers of the Entries corresponding to the given project id.
 * @param projectId The id of the project for which the operation should be performed.
 * @param type The type of Entry to be retrieved.
 * @throws IllegalArgumentException if the projectId is <= 0 or if type is null.
 * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 */
    public long[] retrieveEntriesForProject(long projectId, com.topcoder.timetracker.project.EntryType type) {        
        // your code here
        return null;
    } 

/**
 * <p>
 * Retrieves all the Entries of the specified type that are associated with projects being performed
 * for a specific client..
 * </p>
 * <p>
 * Implementation Notes:
 *    - Get the ProjectFilterFactory.
 *    - Use it to create a search filter with the specified client id.
 *    - delegate to searchProjects using created filter.
 *    - With the returned project ids, delegate to the appropriate Entry DAO.
 *  </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm636b]
 * @return An array of long identifiers of the Entries corresponding to the given client id.
 * @param clientId The id of the client for which this operation should be performed.
 * @param type The type of Entry to be retrieved.
 * @throws IllegalArgumentException if the clientId is <= 0 or if type is null.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 */
    public long[] retrieveEntriesForClient(long clientId, com.topcoder.timetracker.project.EntryType type) {        
        // your code here
        return null;
    } 

/**
 * <p>
 * Retrieves the ProjectFilterFactory that is capable of creating SearchFilters to use when searching
 * for projects.  This is used to conveniently specify the search criteria that should be used.  The
 * filters returned by the given factory should be used with this instance's searchProject method.
 * </p>
 * <p>
 * Implementation Notes:
 *    - Delegate to the ProjectDAO.
 * </p>
 * 
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm6360]
 * @return the ProjectFilterFactory that is capable of creating SearchFilters to use when searching for projects.
 */
    public com.topcoder.timetracker.project.ProjectFilterFactory getProjectFilterFactory() {        
        // your code here
        return null;
    } 

/**
 * <p>
 * Retrieves all the projects  that are currently in the persistent store.
 * </p>
 * <p>
 * Implementation Notes:
 *    - Delegate to the ProjectDAO.
 * </p>
 * 
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm6359]
 * @return An array of projects retrieved from the persistent store.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 */
    public com.topcoder.timetracker.project.Project[] enumerateProjects() {        
        // your code here
        return null;
    } 
 }
