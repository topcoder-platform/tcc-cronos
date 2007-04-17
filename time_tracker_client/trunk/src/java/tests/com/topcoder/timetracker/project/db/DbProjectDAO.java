
package com.topcoder.timetracker.project.db;

/**
 * <p>
 * This is default implementation of the ProjectDAO that is for the database schema that was defined
 * in the Requirements for Time Tracker Project 3.1.  It supports all the methods needed to
 * manipulate and retrieve a Project in the data store.
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
 * 
 * @poseidon-object-id [I7d5b1a38m110c4518e28m313]
 */
public class DbProjectDAO extends com.topcoder.timetracker.project.db.BaseDAO implements com.topcoder.timetracker.project.ProjectDAO {

/**
 * <p>
 * This is the class provided by the Time Tracker Common component to
 * persist and retrieve the term into/from the data store.
 * (Note that this may change, depending on the final look of the Time
 * Tracker Common winning submission)
 * </p>
 * <p>
 * Initial Value: Initialized in Constructor
 * </p>
 * <p>
 * Accessed In: Not accessed.
 * </p>
 * <p>
 * Modified In: Not Modified.
 * </p>
 * <p>
 * Utilized In: All methods of this class.
 * </p>
 * <p>
 * Valid Values: Nulls, or Strings that are not empty.
 * </p>
 * 
 * @poseidon-object-id [I7d5b1a38m110c4518e28m5e2]
 */
    

/**
 * <p>
 * This is the class provided by the Time Tracker Contract component to
 * persist and retrieve the Contact and Address into/from the data store.
 * (Note that this may change, depending on the final look of the Time
 * Tracker Contract winning submission)
 * </p>
 * <p>
 * Initial Value: Initialized in Constructor
 * </p>
 * <p>
 * Accessed In: Not accessed.
 * </p>
 * <p>
 * Modified In: Not Modified.
 * </p>
 * <p>
 * Utilized In: All methods of this class.
 * </p>
 * <p>
 * Valid Values: Nulls, or Strings that are not empty.
 * </p>
 * 
 * @poseidon-object-id [I7d5b1a38m110c4518e28m5f3]
 */
    private final com.topcoder.timetracker.contact.ContactManager contactManager;

/**
 * <p>
 * This is the filter factory that is used to create Search Filters for
 * searching the datastore for Projects using this implementation.  It will
 * be initialized in the constructor with the parameters depending on the
 * search query used by the developer.
 * </p>
 * <p>
 * Initial Value: Initialized in Constructor
 * </p>
 * <p>
 * Accessed In: getProjectFilterFactory
 * </p>
 * <p>
 * Modified In: Not Modified.
 * </p>
 * <p>
 * Utilized In: The filters it produces are used in searchProjects
 * </p>
 * <p>
 * Valid Values: Non-null ProjectFilterFactory object.
 * </p>
 * 
 * @poseidon-object-id [Im5a3b7566m110c4d1f187mm55d9]
 */
    private final com.topcoder.timetracker.project.db.DbProjectFilterFactory filterFactory;

/**
 * <p>
 * This is the application space that will be used and provided to the Time Tracker Auditor
 * if an audit is requested.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im5a3b7566m110c4d1f187mm90d]
 */
    public static final String AUDIT_APPLICATION_AREA = "TT_PROJECT";

/**
 * <p>
 * Contructor that accepts the necessary parameters needed for the Db Dao
 * to function properly.
 * </p>
 * 
 * 
 * @poseidon-object-id [I7d5b1a38m110c4518e28m360]
 * @param connFactory The connection factory to use.
 * @param connName The connection name to use (if null,then default connection is used)
 * @param idGen The id generator that is used.
 * @param contact The Time Tracker Contact class that is used to persist the contacts.
 * @param termManager The Time Tracker Common class that is used to persist the Payment Terms.
 * @param searchStrategyNamespace the configuration namespace used to initialize the search strategy.
 * @param auditor The auditor that is going to be used.
 * @throws IllegalArgumentException if any of the parameters (except connName) is null, or if connName is
 * an empty String.
 */
    public  DbProjectDAO(com.topcoder.db.connectionfactory.DBConnectionFactory connFactory, String connName, com.topcoder.util.idgenerator.IDGenerator idGen, com.topcoder.timetracker.contact.ContactManager contact, String searchStrategyNamespace, com.topcoder.timetracker.audit.AuditManager auditor) {        
        super(connFactory, connName, idGen, searchStrategyNamespace, auditor);
        
        this.filterFactory = null;
        this.contactManager = null;
    } 

/**
 * <p>
 * Adds the specified projects into the persistent store. Unique project ids will automatically
 * be generated and assigned to the projects. There is also the option to perform an audit. If
 * an audit is specified, then the operation must be rolled back in the case that the audit
 * fails.
 * </p>
 * <p>
 * Implementation Note:
 * - Acquire a connection using the connection factory.
 * - For each project in the array:
 * - Generate an ID for the Project and set it in the project.
 * - Execute the necessary SQL to insert it into the data store.
 * - Delegate the Project's Contact and Address to the Time Tracker Contact component.
 * - Delegate the Project's Payment terms to the Time Tracker Common component.
 * - If an audit was specified, then delegate the audit to the Time Tracker Audit component.
 * </p>
 * <p>
 *         Audit Header;
 *             Company id: Company id of the project
 *             Application Area: TT_PROJECT
 *             Table Name: project
 *             Entity Id: The project id
 *             User Name: creationUser
 *             Audit Created: current Date
 *             Action Type: INSERT
 *             client id: N/A 
 *             project id: The project id
 *             resource id: N/A 
 * </p>
 * 
 * @poseidon-object-id [I7d5b1a38m110c4518e28m36f]
 * @param project An array of projects for which the operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws IllegalArgumentExceptionif projects is null or contains null values.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 */
    public void addProjects(com.topcoder.timetracker.project.Project[] project, boolean audit) {        
        
    } 

/**
 * <p>
 * Modifies the persistent store so that it now reflects the data in the provided Project
 * parameter. There is also the option to perform an audit. If an audit is specified, then the
 * operation must be rolled back in the case that the audit fails.
 * </p>
 * <p>
 * Implementation Note:
 * - Acquire a connection using the connection factory.
 * - For each project in the array:
 * - Execute the necessary SQL to update it into the data store.
 * - Delegate the Project's Contact and Address to the Time Tracker Contact component.
 * - Delegate the Project's Payment terms to the Time Tracker Common component.
 * - If an audit was specified, then delegate the audit to the Time Tracker Audit component.
 * - Close the connection.
 * </p>
 * <p>
 *         Audit Header;
 *             Company id: Company id of the project
 *             Application Area: TT_PROJECT
 *             Table Name: project
 *             Entity Id: The project id
 *             User Name: creationUser
 *             Audit Created: current Date
 *             Action Type: UPDATE
 *             client id: N/A 
 *             project id: The project id
 *             resource id: N/A 
 * </p>
 * 
 * @poseidon-object-id [I7d5b1a38m110c4518e28m37b]
 * @param project An array of projects for which the operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws UnrecognizedEntityException if a project with the provided id was not found in the
 * data store.
 * @throws IllegalArgumentExceptionif projects is null or contains null values.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 */
    public void updateProjects(com.topcoder.timetracker.project.Project[] project, boolean audit) {        
        
    } 

/**
 * <p>
 * Modifies the persistent store so that it no longer contains data on the project with the
 * specified projectId.
 * </p>
 * <p>
 * Implementation Note:
 * - Acquire a connection using the connection factory.
 * - For each project in the array:
 * - Execute the necessary SQL to remove it from the data store.
 * - There is no need to delegate to the payment term and contact for this
 * operation.
 * - If an audit was specified, then delegate the audit to the Time Tracker Audit component.
 * - Close the connection.
 * </p>
 * <p>
 *         Audit Header;
 *             Company id: Company id of the project
 *             Application Area: TT_PROJECT
 *             Table Name: project
 *             Entity Id: The project id
 *             User Name: creationUser
 *             Audit Created: current Date
 *             Action Type: DELETE
 *             client id: N/A 
 *             project id: The project id
 *             resource id: N/A 
 * </p>
 * 
 * @poseidon-object-id [I7d5b1a38m110c4518e28m388]
 * @param projectId An array of projectIds for which the operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws UnrecognizedEntityException if a project with the provided id was not found in the
 * data store.
 * @throws IllegalArgumentException if projectIds is null or contains values <= 0.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 */
    public void removeProjects(long[] projectId, boolean audit) {        
        
    } 

/**
 * <p>
 * Retrieves a Project object that reflects the data in the persistent store on the Time Tracker Project
 * with the specified projectId.
 * </p>
 * <p>
 * If no entities are found, then an empty array should be returned.
 * </p>
 * <p>
 * Implementation Note:
 * - Acquire a connection using the connection factory.
 * - For each projectId in the array:
 * - Execute the necessary SQL to retrieve it from the data store.
 * - The Address and Contact is retrieved using Time Tracker Contact component.
 * - The Payment Terms are retrieved using Time Tracker Common component.
 * - Close the connection.
 * </p>
 * 
 * 
 * @poseidon-object-id [I7d5b1a38m110c4518e28m394]
 * @return The projects corresponding to the provided ids.
 * @param projectIds An array of projectIds for which the operation should be performed.
 * @throws IllegalArgumentException if projectIds is null or contains values <= 0.
 * @throws UnrecognizedEntityException if a project with the provided id was not found in the
 * data store.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 */
    public com.topcoder.timetracker.project.Project[] getProjects(long[] projectIds) {        
        return null;
    } 

/**
 * <p>
 * Searches the persistent store for any projects that satisfy the criteria that was specified
 * in the provided search filter. The provided filter should be created using either the filters
 * that are created using the ProjectFilterFactory returned by getProjectFilterFactory of this
 * instance, or a composite Search Filters (such as AndFilter, OrFilter and NotFilter from
 * Search Builder component) that combines the filters created using ProjectFilterFactory.
 * </p>
 * <p>
 * If no entities are found, then an empty array should be returned.
 * </p>
 * 
 * <p>
 * Implementation Notes:
 * - Delegate to the searchStrategy#search method with the correct context and the Filter
 * (providing returnFields and aliasMap is at the discretion of the developer, but not necessary).
 * - Cast the returned Object to a ResultSet and use the data to create the necessary
 * Project Objects.
 * - The Address and Contact is retrieved using Time Tracker Contact component.
 * - The Payment Terms are retrieved using Time Tracker Common component.
 * - See algorithm section of the Component Specification for more details on searching.
 * </p>
 * 
 * 
 * @poseidon-object-id [I7d5b1a38m110c4518e28m3a0]
 * @return The projects satisfying the conditions in the search filter.
 * @param filter The filter used to search for projects.
 * @throws IllegalArgumentException if filter is null.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 */
    public com.topcoder.timetracker.project.Project[] searchProjects(com.topcoder.search.builder.filter.Filter filter) {        
        return null;
    } 

/**
 * <p>
 * Retrieves the ProjectFilterFactory that is capable of creating SearchFilters to use when
 * searching for projects. This is used to conveniently specify the search criteria that should
 * be used. The filters returned by the given factory should be used with this instance's
 * searchProject method.
 * </p>
 * 
 * 
 * @poseidon-object-id [I7d5b1a38m110c4518e28m3aa]
 * @return the ProjectFilterFactory that is capable of creating SearchFilters to use when
 * searching for projects.
 */
    public com.topcoder.timetracker.project.ProjectFilterFactory getProjectFilterFactory() {        
        return null;
    } 

/**
 * <p>
 * Retrieves all the projects that are currently in the persistent store.
 * </p>
 * <p>
 * If no entities are found, then an empty array should be returned.
 * </p>
 * <p>
 * Implementation Note:
 * - Acquire a connection using the connection factory.
 * - Execute the necessary SQL to retrieve all the projects.
 * - The Address and Contact is retrieved using Time Tracker Contact component.
 * - The Payment Terms are retrieved using Time Tracker Common component.
 * - Close the connection.
 * </p>
 * 
 * 
 * @poseidon-object-id [I7d5b1a38m110c4518e28m3b1]
 * @return An array of all projects retrieved from the persistent store.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 */
    public com.topcoder.timetracker.project.Project[] enumerateProjects() {        
        return null;
    } 
 }
