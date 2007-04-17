
package com.topcoder.timetracker.project.db;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.project.ProjectManager;
import com.topcoder.timetracker.project.ProjectManagerFilterFactory;

/**
 * <p>
 * This is default implementation of the ProjectManagerDAO that is for the database schema that was defined
 * in the Requirements for Time Tracker Project 3.1  It supports all the methods needed to
 * manipulate and retrieve a ProjectManager in the data store.
 * </p>
 * <p>
 * Changes in version 3.2: Transaction management removed
 * </p>
 * <p>
 * Thread safety: This class should be thread safe as far as inner state is concerned;
 * Everything should be used in read-only manner.  For database acces, the
 * Time Tracker Application will be making use of Container managed transactions,
 * so JDBC transactions should NOT be used.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm62e9]
 */
public class DbProjectManagerDAO extends com.topcoder.timetracker.project.db.BaseDAO implements com.topcoder.timetracker.project.ProjectManagerDAO {

/**
 * <p>
 * This is the filter factory that is used to create Search Filters for
 * searching the datastore for ProjectManagers using this implementation.  It will
 * be initialized in the constructor with the parameters depending on the
 * search query used by the developer.
 * </p>
 * <p>
 * Initial Value: Initialized in Constructor
 * </p>
 * <p>
 * Accessed In: getProjectManagerFilterFactory
 * </p>
 * <p>
 * Modified In: Not Modified.
 * </p>
 * <p>
 * Utilized In: The filters it produces are used in searchProjectManagers
 * </p>
 * <p>
 * Valid Values: Non-null ProjectManagerFilterFactory object.
 * </p>
 * 
 * @poseidon-object-id [Im5a3b7566m110c4d1f187mm4b05]
 */
    private final com.topcoder.timetracker.project.db.DbProjectManagerFilterFactory filterFactory;

/**
 * <p>
 * This is the application space that will be used and provided to the Time Tracker Auditor
 * if an audit is requested.
 * </p>
 * 
 * @poseidon-object-id [Im5a3b7566m110c4d1f187mm8f1]
 */
    public static final String AUDIT_APPLICATION_AREA = "TT_PROJECT_MANAGER";

/**
 * <p>
 * Contructor that accepts the necessary parameters needed for the Database Dao
 * to function properly.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im5a3b7566m110c4d1f187mm52ad]
 * @param connName The connection name to use (if null,then default connection is used)
 * @param connFactory The connection factory to use.
 * @param idGen The id generator that is used.
 * @param searchStrategyNamespace the configuration namespace used to initialize the search strategy.
 * @param auditor The auditor that is going to be used.
 * @throws IllegalArgumentException if connFactory, idgen, auditor or searchStrategy is null., or if connName is an empty String.
 */
    public  DbProjectManagerDAO(String connName, com.topcoder.db.connectionfactory.DBConnectionFactory connFactory, com.topcoder.util.idgenerator.IDGenerator idGen, String searchStrategyNamespace, com.topcoder.timetracker.audit.AuditManager auditor) {        
        super(connFactory, connName, idGen, searchStrategyNamespace, auditor);
        
        this.filterFactory = null;
    } 

/**
 * <p>
 * Defines a set of  ProjectManagers to be recognized within the persistent store managed by this utility.  A unique
 * ids will automatically be generated and assigned to the ProjectManagers.  There is also the
 * option to perform an audit. 
 * </p>
 * <p>
 * Implementation Notes:
 *   - Acquire a connection using the connection factory.
 *   - For each ProjectManager:
 *       - Generate an ID for the inserted row. 
 *       - Execute the necessary SQL to add the manager.
 *        - If an audit was specified, then perform the audit.
 *   - Close the connection.
 * </p>
 * <p>
 *         Audit Header;
 *             Company id: Company id of the project (will need to look it up from project table using project_id FK)
 *             Application Area: TT_PROJECT
 *             Table Name: project_manager
 *             Entity Id 1: The project_id
 *             Entity Id 2: The user_account_id
 *             User Name: creationUser
 *             Audit Created: current Date
 *             Action Type: CREATE
 *             client id: N/A 
 *             project id: The project id
 *             resource id: N/A 
 * </p>
 * 
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm61fe]
 * @param managers An array of managers for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws IllegalArgumentException if managers is null or contains null elements.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 */
    public void addProjectManagers(com.topcoder.timetracker.project.ProjectManager[] managers, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * Modifies the persistent store so that it now reflects the data in the provided
 * array of ProjectManagers.
 * There is also the option to perform an audit.
 * </p>
 * <p>
 * Implementation Notes:
 *   - Acquire a connection using the connection factory.
 *   - For each ProjectManager:
 *       - Execute the necessary SQL to update the manager.
 *        - If an audit was specified, then perform the audit.
 *   - Close the connection.
 * </p>
 * <p>
 *         Audit Header;
 *             Company id: Company id of the project (will need to look it up from project table using project_id FK)
 *             Application Area: TT_PROJECT
 *             Table Name: project_manager
 *             Entity Id 1: The project_id
 *             Entity Id 2: The user_account_id
 *             User Name: creationUser
 *             Audit Created: current Date
 *             Action Type: UPDATE
 *             client id: N/A 
 *             project id: The project id
 *             resource id: N/A 
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm61f2]
 * @param managers An array of managers for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws IllegalArgumentException if managers is null or contains null elements.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws UnrecognizedEntityException if a manager with the given id was not found in the persistent store.
 */
    public void updateProjectManagers(com.topcoder.timetracker.project.ProjectManager[] managers, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * Modifies the persistent store so that it no longer contains data on the ProjectManagers with the specified
 * ids.
 * </p>
 * <p>
 * Implementation Notes:
 *   - Acquire a connection using the connection factory.
 *   - For each ProjectManager:
 *       - Execute the necessary SQL to remove the manager.
 *        - If an audit was specified, then perform the audit.
 *   - Close the connection.
 * </p>
 * <p>
 *         Audit Header;
 *             Company id: Company id of the project (will need to look it up from project table using project_id FK)
 *             Application Area: TT_PROJECT
 *             Table Name: project_manager
 *             Entity Id 1: The project_id
 *             Entity Id 2: The user_account_id
 *             User Name: creationUser
 *             Audit Created: current Date
 *             Action Type: DELETE
 *             client id: N/A 
 *             project id: The project id
 *             resource id: N/A 
 * </p>
 * 
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm61e5]
 * @param managerIds An array of managerIds for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws IllegalArgumentException if the aray is null or contains values <=0.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws UnrecognizedEntityException if a manager with the given id was not found in the persistent store.
 */
    public void removeProjectManagers(long[] managerIds, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * Searches the persistent store for any ProjectManagers that satisfy the criteria that was specified in the
 * provided search filter.  The provided filter should be created using either the filters that are
 * created using the ProjectManagerFilterFactory returned by getProjectManagerFilterFactory of this instance, or
 * a composite Search Filters (such as AndFilter, OrFilter and NotFilter from Search Builder component)
 * that combines the filters created using ProjectManagerFilterFactory.
 * </p>
 * <p>
 * If no entities are found, then an empty array should be returned.
 * </p>
 * <p>
 * Implementation Notes:
 * - Delegate to the searchStrategy#search method with the correct context and the Filter
 * (providing returnFields and aliasMap is at the discretion of the developer, but not necessary).
 * - Cast the returned Object to a ResultSet and use the data to create the necessary
 * ProjectManager Objects.
 * - See algorithm section of the Component Specification for more details on searching.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm61d9]
 * @return An array of managers satisfying the criteria.
 * @param filter The filter containing the search criteria.
 * @throws IllegalArgumentException if filter is null.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 */
    public com.topcoder.timetracker.project.ProjectManager[] searchProjectManagers(com.topcoder.search.builder.filter.Filter filter) {        
        // your code here
        return null;
    } 

/**
 * <p>
 * Retrieves the ProjectManagerFilterFactory that is capable of creating SearchFilters to use when searching
 * for ProjectManagers.  This is used to conveniently specify the search criteria that should be used.  The
 * filters returned by the given factory should be used with this instance's searchProjectManagers method.
 * </p>
 * 
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm61cf]
 * @throws DataAccessException if a problem occurs while accessing the data store.
 */
    public ProjectManagerFilterFactory getProjectManagerFilterFactory() {        
        return null;
    } 

/**
 * <p>
 * Retrieves all the project managers that are currently in the persistent store.
 * </p>
 * <p>
 * If no entities are found, then an empty array should be returned.
 * </p>
 * <p>
 * Implementation Notes:
 *   - Acquire a connection using the connection factory.
 *   - Execute the necessary SQL to retrieve all managers.
 *   - Close the connection.
 * </p>
 * 
 * 
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm61c7]
 * @return An array of managers currently in the persistent store.
 */
    public com.topcoder.timetracker.project.ProjectManager[] enumerateProjectManagers() {        
        // your code here
        return null;
    } 
 }
