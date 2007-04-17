
package com.topcoder.timetracker.project.db;
import com.topcoder.search.builder.SearchStrategy;
//import com.topcoder.security.authorization.Principal;

/**
 * <p>
 * This is a base DAO class that encapsulates the common
 * elements that may be found within a DAO such as the
 * connection details, id gneerator, search strategy and audit
 * manager.
 * </p>
 * <p>
 * Thread safety:
 * This class is thread safe due to immutability, and the
 * thread safety of the subcomponnets that it uses.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im1a5bfdf4m110fd798f2dmm100a]
 */
public abstract class BaseDAO {

/**
 * <p>
 * This is the connection name that is provided to the
 * connection factory when a connection is acquired.  If not
 * specified, then the default connection is used.
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
 * Utilized In: getConnection
 * </p>
 * <p>
 * Valid Values: Nulls, or Strings that are not empty
 * </p>
 * 
 * 
 * @poseidon-object-id [Im1a5bfdf4m110fd798f2dmmffd]
 */
    private final String connName;

/**
 * <p>
 * This is the connection factory that is used to acquire a connection to the persistent store
 * when it is needed.
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
 * Utilized In: getConnection
 * </p>
 * <p>
 * Valid Values: Not Null DBConnectionFactory instances
 * </p>
 * 
 * @poseidon-object-id [Im1a5bfdf4m110fd798f2dmmdc5]
 */
    private com.topcoder.db.connectionfactory.DBConnectionFactory connFactory;

/**
 * <p>
 * This is the DatabaseSearchStrategy used to search the
 * database.  It may be null in case the concrete class doesn't pass any to the constructor.
 * </p>
 * <p>
 * Initial Value: Initialized in Constructor
 * </p>
 * <p>
 * Accessed In: getSearchStrategy
 * </p>
 * <p>
 * Modified In: Not Modified.
 * </p>
 * <p>
 * Utilized In: used by concrete subclasses to search
 * </p>
 * <p>
 * Valid Values: Possibly null SearchStrategy object.
 * </p>
 * 
 * @poseidon-object-id [Im1a5bfdf4m110fd798f2dmmdb4]
 */
    private SearchStrategy searchStrategy;

/**
 * <p>
 * This is the id generator that is used to generate an id for any
 * new entities that are added to the persistent store.
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
 * Utilized In: getNextID
 * </p>
 * <p>
 * Valid Values: Nulls, or Strings that are not empty
 * </p>
 * 
 * @poseidon-object-id [Im1a5bfdf4m110fd798f2dmmda3]
 */
    private com.topcoder.util.idgenerator.IDGenerator idGenerator;

/**
 * <p>
 * This is the AuditManager from the Time Tracker Audit component that is used to
 * perform audits upon a database change.  It may be null in case the concrete class doesn't pass any to the constructor.
 * </p>
 * <p>
 * Initial Value: Initialized in Constructor
 * </p>
 * <p>
 * Accessed In: getAuditManager
 * </p>
 * <p>
 * Modified In: Not Modified.
 * </p>
 * <p>
 * Utilized In: Concrete Subclasses of this class.
 * </p>
 * <p>
 * Valid Values: Possibly null AuditManager object.
 * </p>
 * 
 * @poseidon-object-id [Im1a5bfdf4m110fd798f2dmmd92]
 */
    private com.topcoder.timetracker.audit.AuditManager auditManager;

/**
 * <p>
 * Constructor for a BaseDAO that accepts the necessary parameters
 * for the DAO to function property.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im1a5bfdf4m110fd798f2dmmd80]
 * @param connFactory The connection factory to use.
 * @param connName The connection name to use (or null if the default connection should be used).
 * @param idGen The name of the id generator to use.
 * @param searchStrategyNamespace The configuration namespace of the database search strategy that will be used.
 * @param auditor The AuditManager used to create audit entries.
 * @throws IllegalArgumentException if any argument except for connName, searchStrategyNamespace or auditor is null or if connName or searchStrategyNamespace is an empty String.
 */
    protected  BaseDAO(com.topcoder.db.connectionfactory.DBConnectionFactory connFactory, String connName, com.topcoder.util.idgenerator.IDGenerator idGen, String searchStrategyNamespace, com.topcoder.timetracker.audit.AuditManager auditor) {        
        this.connFactory = connFactory;
        this.connName = connName;
        this.auditManager = auditManager;
        this.idGenerator = idGenerator;
        this.searchStrategy = searchStrategy;
    } 

/**
 * <p>
 * Retrieves a connection to the database.  It
 * is expected to be used by concrete subclasses.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im1a5bfdf4m110fd798f2dmmfc8]
 * @return a connection to the database.
 */
    protected java.sql.Connection getConnection() {        
        
        // your code here
        return null;
    } 

/**
 * <p>
 * Retrieves a unique identifier to use.  It
 * is expected to be used by concrete subclasses.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im1a5bfdf4m110fd798f2dmmfc1]
 * @return a unique identifier to use.
 */
    protected long getNextId() {        
        
        // your code here
        return 0;
    } 

/**
 * <p>
 * Retrieves the DatabaseSearchStrategy used to search the
 * database.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im1a5bfdf4m110fd798f2dmmfba]
 * @return the DatabaseSearchStrategy used to search the database.
 */
    protected SearchStrategy getSearchStrategy() {        
        
        // your code here
        return null;
    } 

/**
 * <p>
 * Retrieves the AuditManager from the Time Tracker Audit component that is used to
 * perform audits upon a database change.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im1a5bfdf4m110fd798f2dmmfb3]
 * @return the AuditManager from the Time Tracker Audit component that is used to perform audits upon a database change.
 */
    protected com.topcoder.timetracker.audit.AuditManager getAuditManager() {        
        
        // your code here
        return null;
    } 
 }
