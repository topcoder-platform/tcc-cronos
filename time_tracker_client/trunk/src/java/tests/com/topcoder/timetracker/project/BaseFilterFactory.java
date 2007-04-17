
package com.topcoder.timetracker.project;
import com.topcoder.search.builder.filter.Filter;
/**
 * <p>
 * This is a base FilterFactory interface that provides filter creation methods that may be used for filters
 * of any Time Tracker Bean.  It encapsulates filters for the common functionality - namely, the
 * creation and modification date and user.
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm6c6a]
 */
public interface BaseFilterFactory {
/**
 * <p>
 * Creates a Filter based on the Creation Date of the entity.  A
 * date range that may be open-ended can be specified.
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm6c32]
 * @param rangeStart The lower bound of the date criterion.
 * @param rangeEnd The upper bound of the date criterion.
 * @return A filter that will be based off the specified criteria.
 * @throws IllegalArgumentException the range specified is invalid (eg. rangeStart > rangeEnd), or if both arguments are null.
 */
    public com.topcoder.search.builder.filter.Filter createCreationDateFilter(java.util.Date rangeStart, java.util.Date rangeEnd);
/**
 * <p>
 * Creates a Filter based on the Modification Date of the entity.  A
 * date range that may be open-ended can be specified.
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm6c1a]
 * @param rangeStart The lower bound of the date criterion.
 * @param rangeEnd The upper bound of the date criterion.
 * @return A filter that will be based off the specified criteria.
 * @throws IllegalArgumentException the range specified is invalid (eg. rangeStart > rangeEnd), or if both arguments are null.
 */
    public com.topcoder.search.builder.filter.Filter createModificationDateFilter(java.util.Date rangeStart, java.util.Date rangeEnd);
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
 *    'SW:Data' would search for all names that started with 'Data'.
 * </p>
 * <p>
 * See the TC Search Builder 1.3 component's LikeFilter for more details.
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm6c02]
 * @param username The username used for searching.
 * @return A filter that will be based off the specified criteria.
 * @throws IllegalArgumentException if the String is null or an empty String.
 */
    public com.topcoder.search.builder.filter.Filter createCreationUserFilter(String username);
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
 *    'SW:Data' would search for all names that started with 'Data'.
 * </p>
 * <p>
 * See the TC Search Builder 1.3 component's LikeFilter for more details.
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm6bea]
 * @param username The username used for searching.
 * @return A filter that will be based off the specified criteria.
 * @throws IllegalArgumentException if the String is null or an empty String.
 */
    public com.topcoder.search.builder.filter.Filter createModificationUserFilter(String username);
}


