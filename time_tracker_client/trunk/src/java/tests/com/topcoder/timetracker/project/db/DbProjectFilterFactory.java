
package com.topcoder.timetracker.project.db;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is an implementation of the ProjectFilterFactory
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
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm62e5]
 */
public class DbProjectFilterFactory extends com.topcoder.timetracker.project.db.DbBaseFilterFactory implements com.topcoder.timetracker.project.ProjectFilterFactory {

/**
 * <p>
 * This is the map key to use to specify the column name for the
 * Company Id.
 * </p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm423c]
 */
    public static final String COMPANY_ID_COLUMN_NAME = "COMPANY_ID";

/**
 * <p>
 * This is the map key to use to specify the column name for the
 * Client Id.
 * </p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm421b]
 */
    public static final String CLIENT_ID_COLUMN_NAME = "CLIENT_ID";

/**
 * <p>
 * This is the map key to use to specify the column name for the
 * Project Name.
 * </p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm41fa]
 */
    public static final String PROJECT_NAME_COLUMN_NAME = "PROJECT_NAME";

/**
 * <p>
 * This is the map key to use to specify the column name for the
 * Project Start Date.
 * </p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm41d9]
 */
    public static final String PROJECT_START_DATE_COLUMN_NAME = "PROJECT_START_DATE";

/**
 * <p>
 * This is the map key to use to specify the column name for the
 * Project End Date.
 * </p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm41b8]
 */
    public static final String PROJECT_END_DATE_COLUMN_NAME = "PROJECT_END_DATE";

/**
 * <p>
 * This is the map key to use to specify the column name for the
 * Project Manager Id.
 * </p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm4197]
 */
    public static final String PROJECT_MANAGER_ID_COLUMN_NAME = "PROJECT_MANAGER_ID";

/**
 * <p>
 * This is the map key to use to specify the column name for the
 * Project Worker Id.
 * </p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm4176]
 */
    public static final String PROJECT_WORKER_ID_COLUMN_NAME = "PROJECT_WORKER_ID";

/**
 * <p>
 * This is the map key to use to specify the column name for the
 * Time Entry  Id.
 * </p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm4155]
 */
    public static final String PROJECT_TIME_ENTRY_COLUMN_NAME = "PROJECT_TIME_ENTRY";

/**
 * <p>
 * This is the map key to use to specify the column name for the
 * Expense Entry  Id.
 * </p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm4026]
 */
    public static final String PROJECT_EXPENSE_ENTRY_COLUMN_NAME = "PROJECT_EXPENSE_ENTRY";

/**
 * <p>
 * This is the map key to use to specify the column name for the
 * Fixed Bill  Id.
 * </p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm4005]
 */
    public static final String PROJECT_FIXED_BILL_ENTRY_COLUMN_NAME = "PROJECT_FIXED_BILL_ENTRY";

/**
 * <p>
 * This is the map key to use to specify the column name for the
 * Active Column.
 * </p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm3fe4]
 */
    public static final String PROJECT_ACTIVE_COLUMN_NAME = "PROJECT_ACTIVE";

/**
 * <p>
 * Creates a DbProjectFilterFactory with the specified column definitions.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm3fc3]
 * @param columnNames The column definitions to use.
 * @throws IllegalArgumentException if columnNames contains null or empty String keys or values, or if it is missing a Map Entry for the static constants defined in this class.
 */
    public  DbProjectFilterFactory(java.util.Map columnNames) {        
        super(columnNames);
    } 

/**
 * <p>
 * This creates a Filter that will select Projects based on the id of the Time Tracker Company that
 * owns the project.
 * </p>
 * <p>
 * Implementation Note:
 *    - Return an EqualsFilter with the appropriate column name returned from
 * the columnNames map.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm6260]
 * @return A filter that will be based off the specified criteria.
 * @param companyId the id of the Time Tracker Company that owns the project.
 * @throws IllegalArgumentException if the id <= 0.
 */
    public com.topcoder.search.builder.filter.Filter createCompanyIdFilter(long companyId) {        
        // your code here
        return null;
    } 

/**
 * <p>
 * This creates a Filter that will select Projects based on the id of the Time Tracker Client for which
 * work is being done.
 * </p>
 * <p>
 * Implementation Note:
 *    - Return an EqualsFilter with the appropriate column name returned from
 * the columnNames map.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm6256]
 * @return A filter that will be based off the specified criteria.
 * @param clientId the id of the Time Tracker Client for which work is being done.
 * @throws IllegalArgumentException if the id <= 0.
 */
    public com.topcoder.search.builder.filter.Filter createClientIdFilter(long clientId) {        
        // your code here
        return null;
    } 

/**
 * <p>
 * This creates a Filter that will select Projects based on the name of the project.
 * </p>
 * <p>
 * It can also support substring searches by prefixing the String with 'SW:', 'EW:', and 'SS:'.  SW: is used to search
 * for strings that "Start With" the provided value.  'EW:' is used to search for strings that 'End With'
 * the provided value. 'SS:' is used to search for Strings that 'Contain' the provided value.
 * </p>
 * <p>
 * An example would be:
 * 'SW:Data' would search for all names that started with 'Data'.
 * </p>
 * <p>
 * See the TC Search Builder 1.3 component's LikeFilter for more details.
 * </p>
 * <p>
 * Implementation Note:
 *    - Return a LikeFilter with the appropriate name retrieved from columnNames
 * map.
 * </p>
 * 
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm624c]
 * @return A filter that will be based off the specified criteria.
 * @param name the name of the project.
 * @throws IllegalArgumentException if the String is null or an empty String.
 */
    public com.topcoder.search.builder.filter.Filter createNameFilter(String name) {        
        // your code here
        return null;
    } 

/**
 * <p>
 * This creates a Filter that will select Projects based on the start date of the Project.  A
 * date range that may be open-ended can be specified.
 * </p>
 * <p>
 * Implementation Note:
 *    - Return a RangeFilter with the appropriate column name retrieved
 * from the ColumnNames map.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm6241]
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
 * This creates a Filter that will select Projects based on the end date of the Project.  A
 * date range that may be open-ended can be specified.
 * </p>
 * <p>
 * Implementation Note:
 *    - Return a RangeFilter with the appropriate column name retrieved
 * from the ColumnNames map.
 * </p>
 * 
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm6235]
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
 * This creates a Filter that will select Projects based on whether a project manager with the provided
 * user Id has been assigned to it.
 * </p>
 * <p>
 * Implementation Note:
 *    - Return an EqualsFilter with the appropriate column name returned from
 * the columnNames map.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm622a]
 * @return A filter that will be based off the specified criteria.
 * @param managerId The user id of the project manager.
 * @throws IllegalArgumentException if the id <= 0.
 */
    public com.topcoder.search.builder.filter.Filter createContainsProjectManagerFilter(long managerId) {        
        // your code here
        return null;
    } 

/**
 * <p>
 * This creates a Filter that will select Projects based on whether a project worker  with the provided
 * user Id has been assigned to it.
 * </p>
 * <p>
 * Implementation Note:
 *    - Return an EqualsFilter with the appropriate column name returned from
 * the columnNames map.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm6220]
 * @return A filter that will be based off the specified criteria.
 * @param workerId The user id of the worker.
 * @throws IllegalArgumentException if the id <= 0.
 */
    public com.topcoder.search.builder.filter.Filter createContainsProjectWorkerFilter(long workerId) {        
        // your code here
        return null;
    } 

/**
 * <p>
 * This creates a Filter that will select Projects based on whether an Entry (Time/Expense/Fixed Billing)
 * with the provided id has been associated with it.
 * </p>
 * <p>
 * Implementation Note:
 *    - Return an EqualsFilter with the appropriate column name returned from
 * the columnNames map.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm6215]
 * @return A filter that will be based off the specified criteria.
 * @param entryId The id of the entry.
 * @param type The type of the entry.
 * @throws IllegalArgumentException if the id <= 0 or if the type is null.
 */
    public com.topcoder.search.builder.filter.Filter createContainsEntryFilter(long entryId, com.topcoder.timetracker.project.EntryType type) {        
        // your code here
        return null;
    } 

/**
 * <p>
 * This creates a Filter that will select Projects based on whether it is active or not.
 * </p>
 * <p>
 * Implementation Note:
 *    - Return an EqualsFilter with the appropriate column name returned from
 * the columnNames map.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm620a]
 * @return A filter that will be based off the specified criteria.
 * @param isActive Whether the project is active or not.
 */
    public com.topcoder.search.builder.filter.Filter createIsActiveFilter(boolean isActive) {        
        // your code here
        return null;
    } 
 }
