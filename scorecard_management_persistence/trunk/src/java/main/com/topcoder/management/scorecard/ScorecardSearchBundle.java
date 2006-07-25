
package com.topcoder.management.scorecard;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.filter.Filter;
import java.util.List;

/**
 * This class contains methods to build filter instances to use in scorecard searching. It can build filter to search for scorecard based on various criteria such as: - Scorecard name - Scorecard version - Scorecard type id - Scorecard type name - Scorecard status id - Scorecard status name - Project category id that the scorecard linked to. - Project id that the scorecard linked to. Besides, this class also provides method to combine any of the above filter to make complex filters. This class is used be the client to create search filter. The created filter is used in searchScorecards() method of ScorecardManager. Thread safety: This class is immutable and thread safe.
 * 
 */
public class ScorecardSearchBundle {

/**
 * Represents the alias for score card type id column. It is used to build search filter.
 * 
 */
    public static final String SCORECARD_TYPE_ID = "ScorecardTypeID";

/**
 * Represents the alias for score card type name column. It is used to build search filter.
 * 
 */
    public static final String SCORECARD_TYPE_NAME = "ScorecardTypeName";

/**
 * Represents the alias for score card status id column. It is used to build search filter.
 * 
 */
    public static final String SCORECARD_STATUS_ID = "ScorecardStatusID";

/**
 * Represents the alias for score card status name column. It is used to build search filter.
 * 
 */
    public static final String SCORECARD_STATUS_NAME = "ScorecardStatusName";

/**
 * Represents the alias for project category id column. It is used to build search filter.
 * 
 */
    public static final String PROJECT_CATEGORY_ID = "ProjectCategoryID";

/**
 * Represents the alias for project id column. It is used to build search filter.
 * 
 */
    public static final String PROJECT_ID = "ProjectID";

/**
 * Represents the alias for score card name column. It is used to build search filter.
 * 
 */
    public static final String SCORECARD_NAME = "ScorecardName";

/**
 * Represents the alias for score card version column. It is used to build search filter.
 * 
 */
    public static final String SCORECARD_VERSION = "ScorecardVersion";

/**
 * Private constructor to prevent initialization of class instance.
 * 
 */
    private  ScorecardSearchBundle() {        
        // your code here
    } 

/**
 * Build the filter to search for scorecard with the given type id. Type id must be greater than zero.
 * Implementation notes:
 * - return SearchBundle.buildEqualToFilter(SCORECARD_TYPE_ID, new Integer(typeId));
 * 
 * 
 * @return the filter to search for scorecard.
 * @param typeId The type id to build filter.
 * @throws IllegalArgumentException if input is less than or equals to zero.
 */
    public static Filter buildTypeIdEqualFilter(int typeId) {        
        return null;
    } 

/**
 * Build the filter to search for scorecard with type id in the given type id list. Type ids must be greater
 * than zero. The list must contains Long instances.
 * Otherwise, IllegalArgumentException will be thrown.
 * Implementation notes:
 * - return SearchBundle.buildInFilter(SCORECARD_TYPE_ID, typeIds);
 * 
 * 
 * @return the filter to search for scorecard.
 * @param typeIds The type id list to build filter.
 * @throws IllegalArgumentException if one id in the list is less than or equals to zero or the list is null or empty.
 */
    public static Filter buildTypeIdInFilter(List typeIds) {        
        return null;
    } 

/**
 * Build the filter to search for scorecard with the given type name. Type name must not be null or empty string.
 * Implementation notes:
 * - return SearchBundle.buildEqualToFilter(SCORECARD_TYPE_NAME, typeName);
 * 
 * 
 * @return the filter to search for scorecard.
 * @param typeName The type name to build filter.
 * @throws IllegalArgumentException if input is null or empty string.
 */
    public static Filter buildTypeNameEqualFilter(String typeName) {        
        return null;
    } 

/**
 * Build the filter to search for scorecard with type name in the given type name list. Type names must not
 * be null or empty string. The list must contains String instances.
 * Otherwise, IllegalArgumentException will be thrown.
 * Implementation notes:
 * - return SearchBundle.buildInFilter(SCORECARD_TYPE_NAME, typeNames);
 * 
 * 
 * @return the filter to search for scorecard.
 * @param typeNames The type name list to build filter.
 * @throws IllegalArgumentException if one name in the list empty or the list is null
 * or empty.
 */
    public static Filter buildTypeNameInFilter(List typeNames) {        
        return null;
    } 

/**
 * Build the filter to search for scorecard with the given status id. Status id must be greater than zero.
 * Implementation notes:
 * - return SearchBundle.buildEqualToFilter(SCORECARD_STATUS_ID, new Integer(statusId));
 * 
 * 
 * @return the filter to search for scorecard.
 * @param statusId The status id to build filter.
 * @throws IllegalArgumentException if input is less than or equals to zero.
 */
    public static Filter buildStatusIdEqualFilter(int statusId) {        
        return null;
    } 

/**
 * Build the filter to search for scorecard with status id in the given status id list. Status ids must be greater
 * than zero. The list must contains Long instances.
 * Otherwise, IllegalArgumentException will be thrown.
 * Implementation notes:
 * - return SearchBundle.buildInFilter(SCORECARD_STATUS_ID, statusIds);
 * 
 * 
 * @return the filter to search for scorecard.
 * @param statusIds The status id list to build filter.
 * @throws IllegalArgumentException if one id in the list is less than or equals to zero or the list is null
 * or empty.
 */
    public static Filter buildStatusIdInFilter(List statusIds) {        
        return null;
    } 

/**
 * Build the filter to search for scorecard with the given status name. Status name must not be null or empty string.
 * Implementation notes:
 * - return SearchBundle.buildEqualToFilter(SCORECARD_STATUS_NAME, statusName);
 * 
 * 
 * @return the filter to search for scorecard.
 * @param statusName The status name to build filter.
 * @throws IllegalArgumentException if input is null or empty string.
 */
    public static Filter buildStatusNameEqualFilter(String statusName) {        
        return null;
    } 

/**
 * Build the filter to search for scorecard with status name in the given status name list. Status names must not
 * be null or empty string. The list must contains String instances.
 * Otherwise, IllegalArgumentException will be thrown.
 * Implementation notes:
 * - return SearchBundle.buildInFilter(SCORECARD_STATUS_NAME, statusNames);
 * 
 * 
 * @return the filter to search for scorecard.
 * @param statusNames The status name list to build filter.
 * @throws IllegalArgumentException if one name in the list empty or the list is null
 * or empty.
 */
    public static Filter buildStatusNameInFilter(List statusNames) {        
        return null;
    } 

/**
 * Build the filter to search for scorecard with the given project category id. Project category id must be
 * greater than zero.
 * Implementation notes:
 * - return SearchBundle.buildEqualToFilter(PROJECT_CATEGORY_ID, new Integer(projectCategoryId));
 * 
 * 
 * @return the filter to search for scorecard.
 * @param projectCategoryId The project category id to build filter.
 * @throws IllegalArgumentException if input is less than or equals to zero.
 */
    public static Filter buildProjectCategoryIdEqualFilter(int projectCategoryId) {        
        return null;
    } 

/**
 * Build the filter to search for scorecard with project category id in the given project category id list.
 * Project category ids must be greater than zero. The list must contains Long instances.
 * Otherwise, IllegalArgumentException will be thrown.
 * Implementation notes:
 * - return SearchBundle.buildInFilter(PROJECT_CATEGORY_ID, projectCategoryIds);
 * 
 * 
 * @return the filter to search for scorecard.
 * @param projectCategoryIds The project category id list to build filter.
 * @throws IllegalArgumentException if one id in the list is less than or equals to zero or the list is null
 * or empty.
 */
    public static Filter buildProjectCategoryIdInFilter(List projectCategoryIds) {        
        return null;
    } 

/**
 * Build the filter to search for scorecard with the given project id. Project id must be greater than zero.
 * Implementation notes:
 * - return SearchBundle.buildEqualToFilter(PROJECT_ID, new Integer(projectId));
 * 
 * 
 * @return the filter to search for scorecard.
 * @param projectId The project id to build filter.
 * @throws IllegalArgumentException if input is less than or equals to zero.
 */
    public static Filter buildProjectIdEqualFilter(int projectId) {        
        return null;
    } 

/**
 * Build the filter to search for scorecard with project id in the given project id list. Project ids must be greater
 * than zero. The list must contains Long instances.
 * Otherwise, IllegalArgumentException will be thrown.
 * Implementation notes:
 * - return SearchBundle.buildInFilter(PROJECT_ID, projectIds);
 * 
 * 
 * @return the filter to search for scorecard.
 * @param projectIds The project id list to build filter.
 * @throws IllegalArgumentException if one id in the list is less than or equals to zero or the list is null
 * or empty.
 */
    public static Filter buildProjectIdInFilter(List projectIds) {        
        return null;
    } 

/**
 * Build the filter to search for scorecard with the given name. Name must not be null or empty string.
 * Implementation notes:
 * - return SearchBundle.buildEqualToFilter(SCORECARD_NAME, name);
 * 
 * 
 * @return the filter to search for scorecard.
 * @param name The name to build filter.
 * @throws IllegalArgumentException if input is null or empty string.
 */
    public static Filter buildNameEqualFilter(String name) {        
        return null;
    } 

/**
 * Build the filter to search for scorecard with name in the given name list. Names must not
 * be null or empty string. The list must contains String instances.
 * Otherwise, IllegalArgumentException will be thrown.
 * Implementation notes:
 * - return SearchBundle.buildInFilter(SCORECARD_NAME, names);
 * 
 * 
 * @return the filter to search for scorecard.
 * @param names The name list to build filter.
 * @throws IllegalArgumentException if one name in the list empty or the list is null
 * or empty.
 */
    public static Filter buildNameInFilter(List names) {        
        return null;
    } 

/**
 * Build the filter to search for scorecard with the given version. Version must not be null or empty string.
 * Implementation notes:
 * - return SearchBundle.buildEqualToFilter(SCORECARD_VERSION, version);
 * 
 * 
 * @return the filter to search for scorecard.
 * @param version The version to build filter.
 * @throws IllegalArgumentException if input is null or empty string.
 */
    public static Filter buildVersionEqualFilter(String version) {        
        return null;
    } 

/**
 * Build the filter to search for scorecard` with version in the given version list. Versions must not
 * be null or empty string.
 * Implementation notes:
 * - return SearchBundle.buildInFilter(SCORECARD_VERSION, versions);
 * 
 * 
 * @return the filter to search for scorecard.
 * @param versions The version list to build filter.
 * @throws IllegalArgumentException if one version in the list empty or the list is null or empty.
 */
    public static Filter buildVersionInFilter(List versions) {        
        return null;
    } 

/**
 * Build the AND filter that combine the two input filters.
 * Implementation notes:
 * - return SearchBundle.buildAndFilter(f1, f2);
 * 
 * 
 * @return the combined filter.
 * @param f1 the first filter.
 * @param f2 the second filter.
 */
    public static Filter buildAndFilter(Filter f1, Filter f2) {        
        return null;
    } 

/**
 * Build the OR filter that combine the two input filters.
 * Implementation notes:
 * - return SearchBundle.buildOrFilter(f1, f2);
 * 
 * 
 * @return the combined filter.
 * @param f1 the first filter.
 * @param f2 the second filter.
 */
    public static Filter buildOrFilter(Filter f1, Filter f2) {        
        return null;
    } 

/**
 * Build the NOT filter that negate input filter.
 * Implementation notes:
 * - return SearchBundle.buildNotFilter(f1);
 * 
 * 
 * @return the negated filter.
 * @param f1 the first filter.
 */
    public static Filter buildNotFilter(Filter f1) {        
        return null;
    } 
 }
