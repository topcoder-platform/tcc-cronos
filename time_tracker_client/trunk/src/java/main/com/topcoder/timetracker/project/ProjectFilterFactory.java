
package com.topcoder.timetracker.project;
import com.topcoder.search.builder.filter.Filter;
/**
 * <p>
 * This interface defines a factory that is capable of creating search filters used for searching through
 * Time Tracker Projects.  It offers a convenient way of specifying search criteria to use.  The
 * factory is capable of producing filters that conform to a specific schema, and is associated with
 * the ProjectUtility that supports the given schema.
 * </p>
 * <p>
 * The filters that are produced by this factory should only be used by the ProjectUtility implementation
 * that produced this ProjectFilterFactory instance.  This ensures that the Filters can be recognized by
 * the utility.
 * </p>
 * <p>
 * It may be possible to create complex criterion by combining the filters produced by this 
 * factory with any of the Composite Filters in the Search Builder Component (AndFilter, OrFilter,
 * etc.)
 * </p>
 * <p>
 * Note that all ranges specified are inclusive of the boundaries.
 * </p>
 * <p>
 * Thread Safety: This class is required to be thread-safe.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm7017]
 */
public interface ProjectFilterFactory extends com.topcoder.timetracker.project.BaseFilterFactory {
/**
 * <p>
 * This creates a Filter that will select Projects based on the id of the Time Tracker Company that
 * owns the project.
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm6fdf]
 * @param companyId the id of the Time Tracker Company that owns the project.
 * @return A filter that will be based off the specified criteria.
 * @throws IllegalArgumentException if the id <= 0.
 */
    public com.topcoder.search.builder.filter.Filter createCompanyIdFilter(long companyId);
/**
 * <p>
 * This creates a Filter that will select Projects based on the id of the Time Tracker Client for which
 * work is being done.
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm6f84]
 * @param clientId the id of the Time Tracker Client for which work is being done.
 * @return A filter that will be based off the specified criteria.
 * @throws IllegalArgumentException if the id <= 0.
 */
    public com.topcoder.search.builder.filter.Filter createClientIdFilter(long clientId);
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
 *    'SW:Data' would search for all names that started with 'Data'.
 * </p>
 * <p>
 * See the TC Search Builder 1.3 component's LikeFilter for more details.
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm6f29]
 * @param name the name of the project.  
 * @return A filter that will be based off the specified criteria.
 * @throws IllegalArgumentException if the String is null or an empty String.
 */
    public com.topcoder.search.builder.filter.Filter createNameFilter(String name);
/**
 * <p>
 * This creates a Filter that will select Projects based on the start date of the Project.  A
 * date range that may be open-ended can be specified.
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm6ece]
 * @param rangeStart The lower bound of the date criterion.
 * @param rangeEnd The upper bound of the date criterion.
 * @return A filter that will be based off the specified criteria.
 * @throws IllegalArgumentException the range specified is invalid (eg. rangeStart > rangeEnd), or if both arguments are null.
 */
    public com.topcoder.search.builder.filter.Filter createStartDateFilter(java.util.Date rangeStart, java.util.Date rangeEnd);
/**
 * <p>
 * This creates a Filter that will select Projects based on the end date of the Project.  A
 * date range that may be open-ended can be specified.
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm6e38]
 * @param rangeStart The lower bound of the date criterion.
 * @param rangeEnd The upper bound of the date criterion.
 * @return A filter that will be based off the specified criteria.
 * @throws IllegalArgumentException the range specified is invalid (eg. rangeStart > rangeEnd), or if both arguments are null.
 */
    public com.topcoder.search.builder.filter.Filter createEndDateFilter(java.util.Date rangeStart, java.util.Date rangeEnd);
/**
 * <p>
 * This creates a Filter that will select Projects based on whether a project manager with the provided
 * user Id has been assigned to it.
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm6da2]
 * @param managerId The user id of the project manager.
 * @return A filter that will be based off the specified criteria.
 * @throws IllegalArgumentException if the id <= 0.
 */
    public com.topcoder.search.builder.filter.Filter createContainsProjectManagerFilter(long managerId);
/**
 * <p>
 * This creates a Filter that will select Projects based on whether a project worker  with the provided
 * user Id has been assigned to it.
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm6d8a]
 * @param workerId The user id of the worker.
 * @return A filter that will be based off the specified criteria.
 * @throws IllegalArgumentException if the id <= 0.
 */
    public com.topcoder.search.builder.filter.Filter createContainsProjectWorkerFilter(long workerId);
/**
 * <p>
 * This creates a Filter that will select Projects based on whether an Entry (Time/Expense/Fixed Billing)
 * with the provided id has been associated with it.
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm6d72]
 * @param entryId The id of the entry.
 * @param type The type of the entry.
 * @return A filter that will be based off the specified criteria.
 * @throws IllegalArgumentException if the id <= 0 or if the type is null.
 */
    public com.topcoder.search.builder.filter.Filter createContainsEntryFilter(long entryId, com.topcoder.timetracker.project.EntryType type);
/**
 * <p>
 * This creates a Filter that will select Projects based on whether it is active or not.
 * </p>
 * 
 * @poseidon-object-id [Im73f44d97m110bc60be12mm7655]
 * @param isActive Whether the project is active or not.
 * @return A filter that will be based off the specified criteria.
 */
    public com.topcoder.search.builder.filter.Filter createIsActiveFilter(boolean isActive);
}


