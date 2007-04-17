
package com.topcoder.timetracker.project.db;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is an implementation of the ProjectWorkerFilterFactory
 * that may be used for building searches in the database.
 * It maintains a set of column names that are necessary
 * for the filter criterion that it supports, and builds filters
 * according to the specified column names.
 * </p>
 * <p>
 * It extends DbBaseFilterFactory to implement the base functionality
 * that is needed.
 * </p>
 * <p>
 * Thread Safety: This class is thread safe.
 * </p>
 * 
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm62ea]
 */
public class DbProjectWorkerFilterFactory extends com.topcoder.timetracker.project.db.DbBaseFilterFactory implements com.topcoder.timetracker.project.ProjectWorkerFilterFactory {

/**
 * <p>Represents ...</p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm3ec7]
 */
    public static final String WORKER_START_DATE_COLUMN_NAME = "WORKER_START_DATE";

/**
 * <p>Represents ...</p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm3ea6]
 */
    public static final String WORKER_END_DATE_COLUMN_NAME = "WORKER_END_DATE";

/**
 * <p>Represents ...</p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm3e85]
 */
    public static final String WORKER_PROJECT_ID_COLUMN_NAME = "WORKER_PROJECT_ID";

/**
 * <p>Represents ...</p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm3e64]
 */
    public static final String PAY_RATE_COLUMN_NAME = "PAY_RATE";

/**
 * <p>
 * Creates a DbProjectWorkerFilterFactory with the specified column definitions.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm3c6e]
 * @param columnNames The column definitions to use.
 * @throws IllegalArgumentException if columnNames contains null or empty String keys or values, or if it is missing a Map Entry for the static constants defined in this class.
 */
    public  DbProjectWorkerFilterFactory(java.util.Map columnNames) {        
        super(columnNames);
    } 

/**
 * <p>
 * Creates a filter that will select ProjectWorkers based on the id of the TIme Tracker project they
 * are working on.
 * </p>
 * <p>
 * Implementation Note:
 *    - Return an EqualsFilter with the appropriate column name returned from
 * the columnNames map.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm6157]
 * @return A filter that will be based off the specified criteria.
 * @param projectId the id of the TIme Tracker project.
 * @throws IllegalArgumentException if the id <= 0.
 */
    public com.topcoder.search.builder.filter.Filter createProjectIdFilter(long projectId) {        
        // your code here
        return null;
    } 

/**
 * <p>
 * Creates a filter that will select ProjectWorkers based on the start date of the worker.
 * </p>
 * <p>
 * Implementation Note:
 *    - Return a RangeFilter with the appropriate column name retrieved
 * from the ColumnNames map.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm614c]
 * @return A filter that will be based off the specified criteria.
 * @param rangeStart The lower bound of the date criterion.
 * @param rangeEnd The upper bound of the date criterion.
 * @throws IllegalArgumentException the range specified is invalid (eg. rangeStart > rangeEnd), or if both arguments are null.
 */
    public com.topcoder.search.builder.filter.Filter createStartDateFilter(java.util.Date rangeStart, java.util.Date rangeEnd) {        
        // your code here
        return null;
    } 

/**
 * <p>
 * Creates a filter that will select ProjectWorkers based on the end date of the worker.
 * </p>
 * <p>
 * Implementation Note:
 *    - Return a RangeFilter with the appropriate column name retrieved
 * from the ColumnNames map.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm6140]
 * @return A filter that will be based off the specified criteria.
 * @param rangeStart The lower bound of the date criterion.
 * @param rangeEnd The upper bound of the date criterion.
 * @throws IllegalArgumentException the range specified is invalid (eg. rangeStart > rangeEnd), or if both arguments are null.
 */
    public com.topcoder.search.builder.filter.Filter createEndDateFilter(java.util.Date rangeStart, java.util.Date rangeEnd) {        
        // your code here
        return null;
    } 

/**
 * <p>
 * Creates a filter that will select ProjectWorkers based on the end date of the worker.
 * </p>
 * <p>
 * Implementation Note:
 *    - Return a RangeFilter with the appropriate column name retrieved
 * from the ColumnNames map.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm6134]
 * @return A filter that will be based off the specified criteria.
 * @param rangeStart The lower bound of the int criterion.
 * @param rangeEnd The upper bound of the int criterion.
 * @throws IllegalArgumentException if the range is invalid (eg. rangeStart is > rangeEnd)
 */
    public com.topcoder.search.builder.filter.Filter createPayRateFilter(int rangeStart, int rangeEnd) {        
        // your code here
        return null;
    } 
 }
