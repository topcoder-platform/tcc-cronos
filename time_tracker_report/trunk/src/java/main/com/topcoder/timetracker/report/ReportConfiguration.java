/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * This class represents the ReportConfiguration that is interpreted for processing and the display of the report. This
 * contains the full information required for the rendering of the report. This class is as such not thread-safe, but
 * the component uses this class in a thread-safe manner (no reusing of the instance, no use of the same instance across
 * threads)
 * <p/>
 * This class is used by the implementation of the Report and the DBHandler interface to fetch the data for the Report
 * and display it.
 *
 * @author fastprogrammer, traugust
 * @version 1.0
 */
public class ReportConfiguration {

    /**
     * Represents the {@link com.topcoder.util.config.ConfigManager} namespace in the configuration from which the
     * configuration properties required for the report are looked up.
     * <p/>
     * This instance member will not be <tt>null</tt> in an instantiated object.
     */
    private final String namespace;

    /**
     * Represents the type of the Report represented by this ReportConfiguration.
     * <p/>
     * This instance member will not be <tt>null</tt> in an instantiated object.
     */
    private final ReportType type;

    /**
     * Holds the {@link Filter} objects that are used during the fetching of the data for the Report.
     * <p/>
     * This instance member will not be <tt>null</tt> in an instantiated object.
     * <p/>
     * Note, all the reports as a part of the Time Tracker Report component, have at least one mandatory filter.
     * <p/>
     * This List cannot hold <tt>null</tt> elements.
     */
    private final List filters = new ArrayList();

    /**
     * Holds the {@link Column}s to be displayed during the rendering of the report.
     * <p/>
     * It also defines the Display Labels for the columns and the manner in which the column data will be decorated.
     * <p/>
     * This instance member will not be <tt>null</tt> in an instantiated object.
     * <p/>
     * This List cannot hold <tt>null</tt> elements.
     */
    private final List columnDecorators = new ArrayList();

    /**
     * Represents the CSS styles for the various elements in the Report.
     * <p/>
     * The key in this Map will be one of enumeration values defined by the {@link StyleConstant} class and the value
     * will be a String containing the CSS Style value.
     * <p/>
     * This List cannot hold <tt>null</tt> elements but can be empty.
     */
    private final Map styles = new HashMap();

    /**
     * Represents the category of the Report represented by this ReportConfiguration.
     * <p/>
     * This instance member will not be <tt>null</tt> in an instantiated object.
     */
    private final ReportCategory category;

    /**
     * <p>
     * Represents the header string of the report.
     * </p>
     */
    private String header;

    /**
     * <p>
     * Represents the sort by clause.
     * </p>
     */
    private String sortBy = null;

    /**
     * Constructor. Simply initializes the corresponding instance members from the values of the parameters passed.
     *
     * @param category  the category of the Report
     * @param type      the type of the Report
     * @param namespace the namespace from which the properties for the Report are looked up in the {@link
     *                  com.topcoder.util.config.ConfigManager}
     *
     * @throws NullPointerException     if any arg is <tt>null</tt>
     * @throws IllegalArgumentException if namespace is an empty (trim'd) String
     */
    public ReportConfiguration(final ReportCategory category, final ReportType type, final String namespace) {
        if (category == null) {
            throw new NullPointerException("The parameter named [category] was null.");
        }
        if (namespace == null) {
            throw new NullPointerException("The parameter named [namespace] was null.");
        }
        if (namespace.trim().length() == 0) {
            throw new IllegalArgumentException("The parameter named [namespace] was an empty String.");
        }
        if (type == null) {
            throw new NullPointerException("The parameter named [type] was null.");
        }

        this.category = category;
        this.namespace = namespace;
        this.type = type;
    }

    /**
     * Sets the List of Filters for this Report. Each element of the List must be of the type {@link Filter}.
     *
     * @param filters a List containing the Filters for the report.
     *
     * @throws NullPointerException     if filters passed is <tt>null</tt>
     * @throws IllegalArgumentException if filters passed contains <tt>null</tt> values or values not of type {@link
     *                                  Filter}
     */
    public void setFilters(final List filters) {
        if (filters == null) {
            throw new NullPointerException("The parameter named [filters] was null.");
        }
        if (filters.size() == 0) {
            throw new IllegalArgumentException("The parameter named [filters] was an empty List.");
        }

        for (Iterator iterator = filters.iterator(); iterator.hasNext();) {
            final Object o = iterator.next();
            if (o == null) {
                throw new NullPointerException("There was a null value in the given List.");
            }
            if (!(o instanceof Filter)) {
                throw new IllegalArgumentException(
                    "The list contained a value which  was not of type Filter, but was of type ["
                        + o.getClass().getName() + "]");
            }
        }
        this.filters.clear();
        this.filters.addAll(filters);
    }

    /**
     * Sets the Columns to be displayed for the Report, along with the decoration information for the Column. Only the
     * columns specified as a part of this List will be displayed for the Report. Each element of the List must be of
     * type {@link ColumnDecorator}.
     *
     * @param columnDecorators a List containing the columns to be displayed for the Report.
     *
     * @throws NullPointerException     if columnDecorators passed is <tt>null</tt>
     * @throws IllegalArgumentException if columnDecorators passed contains <tt>null</tt> values or values not of type
     *                                  {@link Filter}
     */
    public void setColumnDecorators(final List columnDecorators) {
        if (columnDecorators == null) {
            throw new NullPointerException("The parameter named [filters] was null.");
        }
        if (columnDecorators.size() == 0) {
            throw new IllegalArgumentException("The parameter named [columnDecorators] was an empty List.");
        }

        for (Iterator iterator = columnDecorators.iterator(); iterator.hasNext();) {
            final Object o = iterator.next();
            if (o == null) {
                throw new NullPointerException("The list contained a null value.");
            }
            if (!(o instanceof ColumnDecorator)) {
                throw new IllegalArgumentException(
                    "The list contained a value which was not of type ColumnDecorator, but was of type ["
                        + o.getClass().getName() + "]");
            }
        }
        this.columnDecorators.clear();
        this.columnDecorators.addAll(columnDecorators);
    }

    /**
     * Sets the CSS style values to be used for the Report. The key in this Map must be one of enumeration values
     * defined by the {@link StyleConstant} class and the value must be a String containing the CSS Style value.
     *
     * @param styles a Map containing the CSS styles information to be used for the Report.
     *
     * @throws NullPointerException     if styles passed is <tt>null</tt>
     * @throws IllegalArgumentException if styles passed contains <tt>null</tt> keys/values or keys not of type {@link
     *                                  StyleConstant} or values not of type {@link String} or empty (trim'd) String
     */
    public void setStyles(final Map styles) {
        if (styles == null) {
            throw new NullPointerException("The parameter named [styles] was null.");
        }
        if (styles.size() == 0) {
            throw new IllegalArgumentException("The parameter named [styles] was an empty String.");
        }

        final Set styleEntries = styles.entrySet();
        for (Iterator iterator = styleEntries.iterator(); iterator.hasNext();) {
            final Map.Entry entry = (Map.Entry) iterator.next();
            final Object key = entry.getKey();
            final Object value = entry.getValue();
            if (key == null) {
                throw new NullPointerException("The map contained a null key.");
            }
            if (value == null) {
                throw new NullPointerException("The map contained a null value.");
            }
            if (!(key instanceof StyleConstant)) {
                throw new IllegalArgumentException(
                    "The map contained a key which was not of type StyleConstant, but was of type ["
                        + key.getClass().getName() + "]");
            }
            if (!(value instanceof String)) {
                throw new IllegalArgumentException(
                    "The map contained a value which was not of type String, but was of type ["
                        + key.getClass().getName() + "]");
            }
        }
        this.styles.clear();
        this.styles.putAll(styles);
    }

    /**
     * Returns the namespace from which the configuration properties for the report needs to be looked up in  {@link
     * com.topcoder.util.config.ConfigManager}.
     *
     * @return the namespace from which the configuration properties for the report needs to be looked up in  {@link
     *         com.topcoder.util.config.ConfigManager}
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * Returns the type of the Report.
     *
     * @return the type of the Report
     */
    public ReportType getType() {
        return type;
    }

    /**
     * Returns the List of {@link Filter}s for the {@link Report}.
     *
     * @return the List of {@link Filter}s for the {@link Report} as unmodifiable List
     */
    public List getFilters() {
        return Collections.unmodifiableList(filters);
    }

    /**
     * Returns the List of {@link ColumnDecorator}s for the {@link Report}.
     *
     * @return the List of {@link ColumnDecorator}s for the {@link Report} as unmodifiable List
     */
    public List getColumnDecorators() {
        return Collections.unmodifiableList(columnDecorators);
    }

    /**
     * Returns the CSS Styles to be used for the {@link Report}'s elements. The returned map has keys of type {@link
     * StyleConstant} and values of type {@link String}.
     *
     * @return the CSS Styles to be used for the {@link Report}'s elements as unmodifiable Map
     */
    public Map getStyles() {
        return Collections.unmodifiableMap(styles);
    }

    /**
     * Returns the category of the Report.
     *
     * @return the category of the Report
     */
    public ReportCategory getCategory() {
        return category;
    }

    /**
     * <p>
     * Get the header of the report.
     * </p>
     *
     * @return the header.
     */
    public String getHeader() {
        return header;
    }

    /**
     * <p>
     * Set the header for report. Can be null.
     * </p>
     * @param header the header.
     */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * <p>
     * Get the sort by clause of the report.
     * </p>
     *
     * @return the sort by clause.
     */
    public String getSortBy() {
        return sortBy;
    }

    /**
     * <p>
     * Set the sort by clause for report. Can be null.
     * </p>
     * @param sortBy the sort by clause.
     */
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
