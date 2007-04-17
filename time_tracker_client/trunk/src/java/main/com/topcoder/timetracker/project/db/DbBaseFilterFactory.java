
package com.topcoder.timetracker.project.db;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is an implementation of the BaseFilterFactory
 * that may be used for building searches in the database.
 * It maintains a set of column names that are necessary
 * for the filter criterion that it supports, and builds filters
 * according to the specified column names.
 * </p>
 * <p>
 * Thread Safety: Implementations are required to be thread safe.
 * </p>
 * 
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm62e8]
 */
public class DbBaseFilterFactory implements com.topcoder.timetracker.project.BaseFilterFactory {

/**
 * <p>
 * This is the map key to use to specify the column name for the
 * Creation Date.
 * </p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm4403]
 */
    public static final String CREATION_DATE_COLUMN_NAME = "CREATION_DATE_COLUMN_NAME";

/**
 * <p>
 * This is the map key to use to specify the column name for the
 * Modification Date.
 * </p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm43f2]
 */
    public static final String MODIFICATION_DATE_COLUMN_NAME = "MODIFICATION_DATE_COLUMN_NAME";

/**
 * <p>
 * This is the map key to use to specify the column name for the
 * Creation User.
 * </p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm43e1]
 */
    public static final String CREATION_USER_COLUMN_NAME = "CREATION_USER_COLUMN_NAME";

/**
 * <p>
 * This is the map key to use to specify the column name for the
 * Modification User.
 * </p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm43d0]
 */
    public static final String MODIFICATION_USER_COLUMN_NAME = "MODIFICATION_USER_COLUMN_NAME";

/**
 * <p>
 * This is a mapping of column names to use.  The FilterFactory will use
 * these column names when determining the column name to use when
 * providing a Filter.
 * </p>
 * <p>
 * Initial Value: Null
 * </p>
 * <p>
 * Accessed In: getColumnNames
 * </p>
 * <p>
 * Modified In: Not Modified
 * </p>
 * <p>
 * Utilized In: All methods
 * </p>
 * <p>
 * Valid Keys: Not null and not empty Strings.  The keys will be static constants specified
 * in this and any subclasses.
 * </p>
 * <p>
 * Valid Values: Not null and not empty Strings.  The values will be the assigned column
 * names to the respective columns by the DAO that is using/supporting this FilterFactory.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm4076]
 */
    private final java.util.Map columnNames;

/**
 * <p>
 * Creates a DbBaseFilterFactory with the specified column definitions.
 * </p>
 * <p>
 * Implementation Note:
 *    - Note that a defensive copy is made to make sure it is not modified
 * afterwards.
 * </p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm43bf]
 * @param columnNames The column definitions to use.
 * @throws IllegalArgumentException if columnNames contains null or empty String keys or values, or if it is missing a Map Entry for the static constants defined in this class.
 */
    public  DbBaseFilterFactory(java.util.Map columnNames) {        
        this.columnNames = columnNames;
    } 

/**
 * <p>
 * Creates a Filter based on the Creation Date of the entity.  A
 * date range that may be open-ended can be specified.
 * </p>
 * <p>
 * Implementation Note:
 *    - Return a RangeFilter with the appropriate column name retrieved
 * from the ColumnNames map.
 * </p>
 * 
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm62ba]
 * @return A filter that will be based off the specified criteria.
 * @param rangeStart The lower bound of the date criterion.
 * @param rangeEnd The upper bound of the date criterion.
 * @throws IllegalArgumentException the range specified is invalid (eg. rangeStart > rangeEnd), or if both arguments are null.
 */
    public com.topcoder.search.builder.filter.Filter createCreationDateFilter(java.util.Date rangeStart, java.util.Date rangeEnd) {        
        return null;
    } 

/**
 * <p>
 * Creates a Filter based on the Modification Date of the entity.  A
 * date range that may be open-ended can be specified.
 * </p>
 * <p>
 * Implementation Note:
 *    - Return a RangeFilter with the appropriate column name retrieved
 * from the ColumnNames map.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm62ae]
 * @return A filter that will be based off the specified criteria.
 * @param rangeStart The lower bound of the date criterion.
 * @param rangeEnd The upper bound of the date criterion.
 * @throws IllegalArgumentException the range specified is invalid (eg. rangeStart > rangeEnd), or if both arguments are null.
 */
    public com.topcoder.search.builder.filter.Filter createModificationDateFilter(java.util.Date rangeStart, java.util.Date rangeEnd) {        
        return null;
    } 

/**
 * <p>
 * Creates a Filter based on the Creation User of the entity.
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
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm62a3]
 * @return A filter that will be based off the specified criteria.
 * @param username The username used for searching.
 * @throws IllegalArgumentException if the String is null or an empty String.
 */
    public com.topcoder.search.builder.filter.Filter createCreationUserFilter(String username) {        
        return null;
    } 

/**
 * <p>
 * Creates a Filter based on the Modification User of the entity.
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
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm6299]
 * @return A filter that will be based off the specified criteria.
 * @param username The username used for searching.
 * @throws IllegalArgumentException if the String is null or an empty String.
 */
    public com.topcoder.search.builder.filter.Filter createModificationUserFilter(String username) {        
        return null;
    } 

/**
 * <p>
 * Protected getter for the column names.  It is protected in order for the
 * subclasses to be able to access the map.
 * </p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm4064]
 * @return 
 */
    protected java.util.Map getColumnNames() {        
        return columnNames;
    } 
 }
