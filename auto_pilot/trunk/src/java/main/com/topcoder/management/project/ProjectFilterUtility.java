/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.project;

import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.filter.Filter;
import java.util.List;

/**
 * <p>
 * This class contains methods to build filter instances to use in project searching. It can build
 * filter to search for project based on various criteria such as:
 * </p>
 * <ul>
 * <li>Project type id</li>
 * <li>Project type name</li>
 * <li>Project category id</li>
 * <li>Project category name</li>
 * <li>Project status id</li>
 * <li>Project status name</li>
 * <li>Project property name</li>
 * <li>Project property value</li>
 * <li>Project resource property name</li>
 * <li>Project resource property value</li>
 * </ul>
 * Besides, this class also provides method to combine any of the above filter to make complex
 * filters. This class is used be the client to create search filter. The created filter is used in
 * SearchProjects() method of ProjectManager.
 * <p>
 * Thread safety: This class is immutable and thread safe.
 * </p>
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class ProjectFilterUtility {

    /**
     * Represents the alias for project type id column. It is used to build search filter for this
     * column. This constant also defined in configuration settings for search builder.
     */
    public static final String PROJECT_TYPE_ID = "ProjectTypeID";

    /**
     * Represents the alias for project type name column. It is used to build search filter for this
     * column. This constant also defined in configuration settings for search builder.
     */
    public static final String PROJECT_TYPE_NAME = "ProjectTypeName";

    /**
     * Represents the alias for project category id column. It is used to build search filter for
     * this column. This constant also defined in configuration settings for search builder.
     */
    public static final String PROJECT_CATEGORY_ID = "ProjectCategoryID";

    /**
     * Represents the alias for project category name column. It is used to build search filter for
     * this column. This constant also defined in configuration settings for search builder.
     */
    public static final String PROJECT_CATEGORY_NAME = "ProjectCategoryName";

    /**
     * Represents the alias for project status id column. It is used to build search filter for this
     * column. This constant also defined in configuration settings for search builder.
     */
    public static final String PROJECT_STATUS_ID = "ProjectStatusID";

    /**
     * Represents the alias for project status name column. It is used to build search filter for
     * this column. This constant also defined in configuration settings for search builder.
     */
    public static final String PROJECT_STATUS_NAME = "ProjectStatusName";

    /**
     * Represents the alias for project property name column. It is used to build search filter for
     * this column. This constant also defined in configuration settings for search builder.
     */
    public static final String PROJECT_PROPERTY_NAME = "ProjectPropertyName";

    /**
     * Represents the alias for project property value column. It is used to build search filter for
     * this column. This constant also defined in configuration settings for search builder.
     */
    public static final String PROJECT_PROPERTY_VALUE = "ProjectPropertyValue";

    /**
     * Represents the alias for project resource property name column. It is used to build search
     * filter for this column. This constant also defined in configuration settings for search
     * builder.
     */
    public static final String PROJECT_RESOURCE_PROPERTY_NAME = "ProjectResourcePropertyName";

    /**
     * Represents the alias for project resource property value column. It is used to build search
     * filter for this column. This constant also defined in configuration settings for search
     * builder.
     */
    public static final String PROJECT_RESOURCE_PROPERTY_VALUE = "ProjectResourcePropertyValue";

    /**
     * Private constructor to prevent initialization of class instance.
     */
    private ProjectFilterUtility() {
        // your code here
    }

    /**
     * Build the filter to search for project with the given type id. Type id must be greater than
     * zero.
     * <p>
     * Implementation notes:
     * </p>
     * return SearchBundle.buildEqualToFilter(PROJECT_TYPE_ID, new Long(typeId));
     * @return the filter to search for project.
     * @param typeId The type id to build filter.
     * @throws IllegalArgumentException if input is less than or equals to zero.
     */
    public static Filter buildTypeIdEqualFilter(long typeId) {
        return SearchBundle.buildEqualToFilter(PROJECT_TYPE_ID, new Long(typeId));
    }

    /**
     * Build the filter to search for project with type id in the given type id list. Type ids must
     * be greater than zero. The content of the input list must be of Long type.
     * <p>
     * Implementation notes:
     * </p>
     * return SearchBundle.buildInFilter(PROJECT_TYPE_ID, typeIds);
     * @return the filter to search for project.
     * @param typeIds The type id list to build filter.
     * @throws IllegalArgumentException if one id in the list is less than or equals to zero or the
     *             list is null or empty or the content of the list is not of Long type.
     */
    public static Filter buildTypeIdInFilter(List typeIds) {
        return SearchBundle.buildInFilter(PROJECT_TYPE_ID, typeIds);
    }

    /**
     * Build the filter to search for project with the given type name. Type name must not be null
     * or empty string.
     * <p>
     * Implementation notes:
     * </p>
     * return SearchBundle.buildEqualToFilter(PROJECT_TYPE_NAME, name);
     * @return the filter to search for project.
     * @param name The type name to build filter.
     * @throws IllegalArgumentException if input is null or empty.
     */
    public static Filter buildTypeNameEqualFilter(String name) {
        return SearchBundle.buildEqualToFilter(PROJECT_TYPE_NAME, name);
    }

    /**
     * Build the filter to search for project with type name in the given type name list. Type names
     * must not be null or empty. The content of the input list must be of String type.
     * <p>
     * Implementation notes:
     * </p>
     * return SearchBundle.buildInFilter(PROJECT_TYPE_NAME, names);
     * @return the filter to search for project.
     * @param names The type names list to build filter.
     * @throws IllegalArgumentException if one name in the list is empty string or the list is null
     *             or empty or the content of the list is not of String type.
     */
    public static Filter buildTypeNameInFilter(List names) {
        return SearchBundle.buildInFilter(PROJECT_TYPE_NAME, names);
    }

    /**
     * Build the filter to search for project with the given category id. Category id must be
     * greater than zero.
     * <p>
     * Implementation notes:
     * </p>
     * return SearchBundle.buildEqualToFilter(PROJECT_CATEGORY_ID, new Long(categoryId));
     * @return the filter to search for project.
     * @param categoryId The category id to build filter.
     * @throws IllegalArgumentException if input is less than or equals to zero.
     */
    public static Filter buildCategoryIdEqualFilter(long categoryId) {
        return SearchBundle.buildEqualToFilter(PROJECT_CATEGORY_ID, new Long(categoryId));
    }

    /**
     * Build the filter to search for project with category id in the given category id list.
     * Category ids must be greater than zero. The content of the input list must be of Long type.
     * <p>
     * Implementation notes:
     * </p>
     * return SearchBundle.buildInFilter(PROJECT_CATEGORY_ID, categoryIds);
     * @return the filter to search for project.
     * @param categoryIds The category id list to build filter.
     * @throws IllegalArgumentException if one id in the list is less than or equals to zero or the
     *             list is null or empty or the content of the list is not of Long type.
     */
    public static Filter buildCategoryIdInFilter(List categoryIds) {
        return SearchBundle.buildInFilter(PROJECT_CATEGORY_ID, categoryIds);
    }

    /**
     * Build the filter to search for project with the given category name. Category name must not
     * be null or empty string.
     * <p>
     * Implementation notes:
     * </p>
     * return SearchBundle.buildEqualToFilter(PROJECT_CATEGORY_NAME, name);
     * @return the filter to search for project.
     * @param name The category name to build filter.
     * @throws IllegalArgumentException if input is null or empty.
     */
    public static Filter buildCategoryNameEqualFilter(String name) {
        return SearchBundle.buildEqualToFilter(PROJECT_CATEGORY_NAME, name);
    }

    /**
     * Build the filter to search for project with category name in the given category name list.
     * Category names must not be null or empty. The content of the input list must be of String
     * type.
     * <p>
     * Implementation notes:
     * </p>
     * return SearchBundle.buildInFilter(PROJECT_CATEGORY_NAME, names);
     * @return the filter to search for project.
     * @param names The category names list to build filter.
     * @throws IllegalArgumentException if one name in the list is empty string or the list is null
     *             or empty or the content of the list is not of String type.
     */
    public static Filter buildCategoryNameInFilter(List names) {
        return SearchBundle.buildInFilter(PROJECT_CATEGORY_NAME, names);
    }

    /**
     * Build the filter to search for project with the given status id. Status id must be greater
     * than zero.
     * <p>
     * Implementation notes:
     * </p>
     * return SearchBundle.buildEqualToFilter(PROJECT_STATUS_ID, new Long(statusId));
     * @return the filter to search for project.
     * @param statusId The status id to build filter.
     * @throws IllegalArgumentException if input is less than or equals to zero.
     */
    public static Filter buildStatusIdEqualFilter(long statusId) {
        return SearchBundle.buildEqualToFilter(PROJECT_STATUS_ID, new Long(statusId));
    }

    /**
     * Build the filter to search for project with status id in the given status id list. Status ids
     * must be greater than zero. The content of the input list must be of Long type.
     * <p>
     * Implementation notes:
     * </p>
     * return SearchBundle.buildInFilter(PROJECT_STATUS_ID, statusIds);
     * @return the filter to search for project.
     * @param statusIds The status id list to build filter.
     * @throws IllegalArgumentException if one id in the list is less than or equals to zero or the
     *             list is null or empty or the content of the list is not of Long type.
     */
    public static Filter buildStatusIdInFilter(List statusIds) {
        return SearchBundle.buildInFilter(PROJECT_STATUS_ID, statusIds);
    }

    /**
     * Build the filter to search for project with the given status name. Status name must not be
     * null or empty string.
     * <p>
     * Implementation notes:
     * </p>
     * return SearchBundle.buildEqualToFilter(PROJECT_STATUS_NAME, name);
     * @return the filter to search for project.
     * @param name The status name to build filter.
     * @throws IllegalArgumentException if input is null or empty.
     */
    public static Filter buildStatusNameEqualFilter(String name) {
        return SearchBundle.buildEqualToFilter(PROJECT_STATUS_NAME, name);
    }

    /**
     * Build the filter to search for project with status name in the given status name list. Status
     * names must not be null or empty. The content of the input list must be of String type.
     * <p>
     * Implementation notes:
     * </p>
     * return SearchBundle.buildInFilter(PROJECT_STATUS_NAME, names);
     * @return the filter to search for project.
     * @param names The status names list to build filter.
     * @throws IllegalArgumentException if one name in the list is empty string or the list is null
     *             or empty or the content of the list is not of String type.
     */
    public static Filter buildStatusNameInFilter(List names) {
        return SearchBundle.buildInFilter(PROJECT_STATUS_NAME, names);
    }

    /**
     * Build the filter to search for project with the given property name. Name must not be null or
     * empty string.
     * <p>
     * Implementation notes:
     * </p>
     * return SearchBundle.buildEqualToFilter(PROJECT_PROPERTY_NAME, name);
     * @return the filter to search for project.
     * @param name The property name to build filter.
     * @throws IllegalArgumentException if input is null or empty string.
     */
    public static Filter buildProjectPropertyNameEqualFilter(String name) {
        return SearchBundle.buildEqualToFilter(PROJECT_PROPERTY_NAME, name);
    }

    /**
     * Build the filter to search for project with the given list of property names. Names must not
     * be null or empty string. The content of the input list must be of String type.
     * <p>
     * Implementation notes:
     * </p>
     * return SearchBundle.buildInFilter(PROJECT_PROPERTY_NAME, names);
     * @return the filter to search for project.
     * @param names The property name list to build filter.
     * @throws IllegalArgumentException if one id in the list is less than or equals to zero or the
     *             list is null or empty or the content of the list is not of Long type.
     */
    public static Filter buildProjectPropertyNameInFilter(List names) {
        return SearchBundle.buildInFilter(PROJECT_PROPERTY_NAME, names);
    }

    /**
     * Build the filter to search for project with the given property value. Value must not be null
     * or empty string.
     * <p>
     * Implementation notes:
     * </p>
     * return SearchBundle.buildEqualToFilter(PROJECT_PROPERTY_VALUE, value);
     * @return the filter to search for project.
     * @param value The property value to build filter.
     * @throws IllegalArgumentException if input is null or empty string.
     */
    public static Filter buildProjectPropertyValueEqualFilter(String value) {
        return SearchBundle.buildEqualToFilter(PROJECT_PROPERTY_VALUE, value);
    }

    /**
     * Build the filter to search for project with the given list of property values. Values must
     * not be null or empty string. The content of the input list must be of String type.
     * <p>
     * Implementation notes:
     * </p>
     * return SearchBundle.buildInFilter(PROJECT_PROPERTY_VALUE, values);
     * @return the filter to search for project.
     * @param values The property name list to build filter.
     * @throws IllegalArgumentException if one id in the list is less than or equals to zero or the
     *             list is null or empty or the content of the list is not of Long type.
     */
    public static Filter buildProjectPropertyValueInFilter(List values) {
        return SearchBundle.buildInFilter(PROJECT_PROPERTY_VALUE, values);
    }

    /**
     * <p>
     * Build the filter to search for project with the given property name/value pair. Name and
     * value must not be null or empty string.
     * </p>
     * <p>
     * Implementation notes:
     * </p>
     * <p>
     * return buildAndFilter(buildProjectPropertyNameEqualFilter(name),
     * buildProjectPropertyValueEqualFilter(value));
     * </p>
     * @param name he property name to build filter.
     * @param value he property value to build filter.
     * @return he filter to search for project.
     * @throws IllegalArgumentException if any input is null or empty string.
     */
    public static Filter buildProjectPropertyEqualFilter(String name, String value) {
        // your code here
        return buildAndFilter(buildProjectPropertyNameEqualFilter(name),
            buildProjectPropertyValueEqualFilter(value));
    }

    /**
     * Build the filter to search for project with the given resource property name. Name must not
     * be null or empty string.
     * <p>
     * Implementation notes:
     * </p>
     * return SearchBundle.buildEqualToFilter(PROJECT_RESOURCE_PROPERTY_NAME, name);
     * @return the filter to search for project.
     * @param name The resource property name to build filter.
     * @throws IllegalArgumentException if input is null or empty string.
     */
    public static Filter buildProjectResourcePropertyNameEqualFilter(String name) {
        return SearchBundle.buildEqualToFilter(PROJECT_RESOURCE_PROPERTY_NAME, name);
    }

    /**
     * Build the filter to search for project with the given list of resource property names. Names
     * must not be null or empty string. The content of the input list must be of String type.
     * <p>
     * Implementation notes:
     * </p>
     * return SearchBundle.buildInFilter(PROJECT_RESOURCE_PROPERTY_NAME, names);
     * @return the filter to search for project.
     * @param names The resource property name list to build filter.
     * @throws IllegalArgumentException if one id in the list is less than or equals to zero or the
     *             list is null or empty or the content of the list is not of Long type.
     */
    public static Filter buildProjectResourcePropertyNameInFilter(List names) {
        return SearchBundle.buildInFilter(PROJECT_RESOURCE_PROPERTY_NAME, names);
    }

    /**
     * Build the filter to search for project with the given resource property value. Value must not
     * be null or empty string.
     * <p>
     * Implementation notes:
     * </p>
     * return SearchBundle.buildEqualToFilter(PROJECT_RESOURCE_PROPERTY_VALUE, value);
     * @return the filter to search for project.
     * @param value The property value to build filter.
     * @throws IllegalArgumentException if input is null or empty string.
     */
    public static Filter buildProjectResourcePropertyValueEqualFilter(String value) {
        return SearchBundle.buildEqualToFilter(PROJECT_RESOURCE_PROPERTY_VALUE, value);
    }

    /**
     * Build the filter to search for project with the given list of resource property values.
     * Values must not be null or empty string. The content of the input list must be of String
     * type.
     * <p>
     * Implementation notes:
     * </p>
     * return SearchBundle.buildInFilter(PROJECT_RESOURCE_PROPERTY_VALUE, values);
     * @return the filter to search for project.
     * @param values The resource property value list to build filter.
     * @throws IllegalArgumentException if one id in the list is less than or equals to zero or the
     *             list is null or empty or the content of the list is not of Long type.
     */
    public static Filter buildProjectResourcePropertyValueInFilter(List values) {
        return SearchBundle.buildInFilter(PROJECT_RESOURCE_PROPERTY_VALUE, values);
    }

    /**
     * <p>
     * Build the filter to search for project with the given resource property name/value pair. Name
     * and value must not be null or empty string.
     * </p>
     * <p>
     * Implementation notes:
     * </p>
     * <p>
     * return buildAndFilter(buildProjectResourcePropertyNameEqualFilter(name),
     * buildProjectResourcePropertyValueEqualFilter(value));
     * </p>
     * @param name The property name to build filter.
     * @param value The property value to build filter.
     * @return the filter to search for project.
     * @throws IllegalArgumentException if any input is null or empty string.
     */
    public static Filter buildProjectPropertyResourceEqualFilter(String name, String value) {
        // your code here
        return buildAndFilter(buildProjectResourcePropertyNameEqualFilter(name),
            buildProjectResourcePropertyValueEqualFilter(value));
    }

    /**
     * Build the AND filter that combine the two input filters.
     * <p>
     * Implementation notes:
     * </p>
     * return SearchBundle.buildAndFilter(f1, f2);
     * @return the combined filter.
     * @param f1 the first filter.
     * @param f2 the second filter.
     */
    public static Filter buildAndFilter(Filter f1, Filter f2) {
        return SearchBundle.buildAndFilter(f1, f2);
    }

    /**
     * Build the OR filter that combine the two input filters.
     * <p>
     * Implementation notes:
     * </p>
     * return SearchBundle.buildOrFilter(f1, f2);
     * @return the combined filter.
     * @param f1 the first filter.
     * @param f2 the second filter.
     */
    public static Filter buildOrFilter(Filter f1, Filter f2) {
        return SearchBundle.buildOrFilter(f1, f2);
    }

    /**
     * Build the NOT filter that negate input filter.
     * <p>
     * Implementation notes:
     * </p>
     * return SearchBundle.buildNotFilter(f1);
     * @return the negated filter.
     * @param f1 the first filter.
     */
    public static Filter buildNotFilter(Filter f1) {
        return SearchBundle.buildNotFilter(f1);
    }
}
