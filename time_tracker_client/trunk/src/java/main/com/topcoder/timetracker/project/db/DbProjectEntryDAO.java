
package com.topcoder.timetracker.project.db;

/**
 * <p>
 * This is the interface definition for the DAO that is responsible handling the association and
 * dissassociation of an Entry identifier with a project.  It is also capable of retrieving the entries
 * that have been associated with a particular project.  The DAO is also responsible for generating
 * any identifiers (if necessary) for the associations.
 * </p>
 * <p>
 * Changes in version 3.2: Transaction management removed
 * </p>
 * <p>
 * The type of Entry (Time / Expense / Fixed Billing ) that is being supported will depend
 * on the database table and column names  that are supplied to this class.  Using the 
 * default schema in the Requirements Specification, the following will need to be provided::
 *          - Fixed Billing Entries:
 *                  - Table Name: project_fix_bill
 *                  - Entry Column Name: fix_bill_entry_id
 *                  - Project Column Name: project_id
 * 
 *          - Time Entries:
 *                  - Table Name: project_time
 *                  - Entry Column Name: time_entry_id
 *                  - Project Column Name: project_id
 * 
 *          - Expense Entries:
 *                  - Table Name: project_expense
 *                  - Entry Column Name: expense_entry_id
 *                  - Project Column Name: project_id
 * </p>
 * <p>
 * Thread safety: This class should be thread safe as far as inner state is concerned;
 * Everything should be used in read-only manner.  For database acces, the
 * Time Tracker Application will be making use of Container managed transactions,
 * so JDBC transactions should NOT be used.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm62e7]
 */
public class DbProjectEntryDAO extends com.topcoder.timetracker.project.db.BaseDAO implements com.topcoder.timetracker.project.ProjectEntryDAO {

/**
 * <p>
 * This is the name of the database table which contains information
 * regarding the relationship between a Project and an Entry.  The
 * tablename depends on the type of Entry that is being used.
 * </p>
 * <p>
 * Initial Value: Initialized in Constructor
 * </p>
 * <p>
 * Accessed In: Not Accessed
 * </p>
 * <p>
 * Modified In: Not Modified.
 * </p>
 * <p>
 * Utilized In: addEntryToProject, removeEntryFromProject, retrieveEntriesForProject
 * </p>
 * <p>
 * Valid Values: Non-null and not empty String values.
 * </p>
 * 
 * @poseidon-object-id [Im1a5bfdf4m110fd2e6e85mm35df]
 */
    private String tableName;

/**
 * <p>
 * This is the name of the database column within the table that is used to contain a foreign
 * key to the project.
 * </p>
 * <p>
 * Initial Value: Initialized in Constructor
 * </p>
 * <p>
 * Accessed In: Not Accessed
 * </p>
 * <p>
 * Modified In: Not Modified.
 * </p>
 * <p>
 * Utilized In: addEntryToProject, removeEntryFromProject, retrieveEntriesForProject
 * </p>
 * <p>
 * Valid Values: Non-null and not empty String values.
 * </p>
 * 
 * @poseidon-object-id [Im1a5bfdf4m110fd2e6e85mm35ce]
 */
    private String projectColumnName;

/**
 * <p>
 * This is the name of the database column within the table that is used to contain a foreign
 * key to the entry.
 * </p>
 * <p>
 * Initial Value: Initialized in Constructor
 * </p>
 * <p>
 * Accessed In: Not Accessed
 * </p>
 * <p>
 * Modified In: Not Modified.
 * </p>
 * <p>
 * Utilized In: addEntryToProject, removeEntryFromProject, retrieveEntriesForProject
 * </p>
 * <p>
 * Valid Values: Non-null and not empty String values.
 * </p>
 * 
 * @poseidon-object-id [Im1a5bfdf4m110fd2e6e85mm35bd]
 */
    private String entryColumnName;

/**
 * <p>
 * Constructor that allows a DbProjectEntryDAO to be created with the specified connection
 * options and id generator, and database information..
 * </p>
 * 
 * @poseidon-object-id [Im5a3b7566m110c4d1f187mm54e4]
 * @param connFact The connection factory to use.
 * @param connName The connection name to use (if null,then default connection is used)
 * @param idGen The id generator that is used.
 * @param auditor The audit manager to use.
 * @param projIdColname The column name of the project id.
 * @param entryIdColname The column name of the entry id.
 * @param tableName The database table name.
 * @throws IllegalArgumentException if any parameter is null (except connName) or any String is an empty String.
 */
    public  DbProjectEntryDAO(com.topcoder.db.connectionfactory.DBConnectionFactory connFact, String connName, com.topcoder.util.idgenerator.IDGenerator idGen, com.topcoder.timetracker.audit.AuditManager auditor, String projIdColname, String entryIdColname, String tableName) {        
        super(connFact, connName, idGen, "    ", auditor);
        
        
    } 

/**
 * <p>
 * Associates the specified entry with the project.  There is also the
 * option to perform an audit.
 * </p>
 * <p>
 * Implementation Notes:
 *   - Acquire a connection using the connection factory.
 *   - Generate an ID for the inserted row.
 *   - Execute the necessary SQL to associate the entry with the project.
 *   - Close the connection.
 * </p>
 * <p>
 *         Audit Header;
 *             Company id: Company id of the project (need to look it up from project table)
 *             Application Area: TT_PROJECT
 *             Table Name: configured tableName parameter
 *             Entity Id 1: The project_id
 *             Entity Id 2: The time_entry_id
 *             User Name: creationUser
 *             Audit Created: current Date
 *             Action Type: INSERT
 *             client id: N/A 
 *             project id: The project id 
 *             resource id: N/A 
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm628b]
 * @param projectId The id of the project for which the operation should be performed.
 * @param entryId The id of the entry for which the operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws IllegalArgumentException if any id has a value <= 0.
 * @throws InvalidCompanyException if the entry and project company ids do not match.
 * @throws UnrecognizedEntityException if a project or entry with the provided id was not found in the data store.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 * @throws DuplicateEntityException if the entry has already been associated with the project.
 */
    public void addEntryToProject(long projectId, long entryId, boolean audit) {        
        
    } 

/**
 * <p>
 * This disassociates an Entry with the project.  There is also the
 * option to perform an audit. 
 * </p>
 * <p>
 * Implementation Notes:
 *   - Acquire a connection using the connection factory.
 *   - Execute the necessary SQL to disassociate the entry with the project.
 *   - Close the connection.
 * </p>
 * <p>
 *         Audit Header;
 *             Company id: Company id of the project (need to look it up from project table)
 *             Application Area: TT_PROJECT
 *             Table Name: configured tableName parameter
 *             Entity Id 1: The project_id
 *             Entity Id 2: The time_entry_id
 *             User Name: creationUser
 *             Audit Created: current Date
 *             Action Type: DELETE
 *             client id: N/A 
 *             project id: The project id 
 *             resource id: N/A 
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm627a]
 * @param projectId The id of the project for which the operation should be performed.
 * @param entryId The id of the entry for which the operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws IllegalArgumentException if any id has a value <= 0.
 * @throws UnrecognizedEntityException if a project or entry with the provided id was not found in the data store.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 */
    public void removeEntryFromProject(long projectId, long entryId, boolean audit) {        
        
    } 

/**
 * <p>
 * Retrieves all the Entries of the specified type that are associated with the specified projects.
 * </p>
 * <p>
 * If no entities are found, then an empty array should be returned.
 * </p>
 * <p>
 * Implementation Notes:
 *   - Acquire a connection using the connection factory.
 *   - Execute the necessary SQL to retrieve the project entries.
 *   - Close the connection.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm626d]
 * @return An array of long identifiers of the Entries corresponding to the given project ids.
 * @param projectIds An array of ids of the projects for which the operation should be performed.
 * @throws IllegalArgumentException if projectIds is null or contains elements <= 0.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 */
    public long[] retrieveEntriesForProjects(long[] projectIds) {        
        return null;
    } 
 }
