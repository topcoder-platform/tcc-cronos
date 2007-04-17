
package com.topcoder.timetracker.project.db;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is an implementation of the ProjectManagerFilterFactory
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
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm62e4]
 */
public class DbProjectManagerFilterFactory extends com.topcoder.timetracker.project.db.DbBaseFilterFactory implements com.topcoder.timetracker.project.ProjectManagerFilterFactory {

/**
 * <p>Represents ...</p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm3f2a]
 */
    public static final String MANAGER_START_DATE_COLUMN_NAME = "MANAGER_START_DATE";

/**
 * <p>Represents ...</p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm3f09]
 */
    public static final String MANAGER_END_DATE_COLUMN_NAME = "MANAGER_END_DATE";

/**
 * <p>Represents ...</p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm3ee8]
 */
    public static final String MANAGER_PROJECT_ID_COLUMN_NAME = "MANAGER_PROJECT_ID";

/**
 * <p>
 * Creates a DbProjectManagerFilterFactory with the specified column definitions.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm3cef]
 * @param columnNames The column definitions to use.
 * @throws IllegalArgumentException if columnNames contains null or empty String keys or values, or if it is missing a Map Entry for the static constants defined in this class.
 */
    public  DbProjectManagerFilterFactory(java.util.Map columnNames) {        
        super(columnNames);
    } 

/**
 * <p>
 * Creates a filter that will select ProjectManagers based on the start date of the manager.
 * </p>
 * <p>
 * Implementation Note:
 *    - Return a RangeFilter with the appropriate column name retrieved
 * from the ColumnNames map.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm61bc]
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
 * Creates a filter that will select ProjectManagers based on the end date of the manager.
 * </p>
 * <p>
 * Implementation Note:
 *    - Return a RangeFilter with the appropriate column name retrieved
 * from the ColumnNames map.
 * </p>
 * 
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm61b0]
 * @return A filter that will be based off the specified criteria.
 * @param startDate The lower bound of the date criterion.
 * @param endDate The upper bound of the date criterion.
 * @throws IllegalArgumentException the range specified is invalid (eg. rangeStart > rangeEnd), or if both arguments are null.
 */
    public com.topcoder.search.builder.filter.Filter createEndDateFilter(java.util.Date startDate, java.util.Date endDate) {        
        
        // your code here
        return null;
    } 

/**
 * <p>
 * Creates a filter that will select ProjectManagers based on the id of the TIme Tracker project they
 * are managing.
 * </p>
 * <p>
 * Implementation Note:
 *    - Return an EqualsFilter with the appropriate column name returned from
 * the columnNames map.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm61a5]
 * @return A filter that will be based off the specified criteria.
 * @param projectId The id of the project which the manager is associated with.
 * @throws IllegalArgumentException if the id <= 0.
 */
    public com.topcoder.search.builder.filter.Filter createProjectIdFilter(long projectId) {        
        
        // your code here
        return null;
    } 
 }
