/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report;

import com.topcoder.timetracker.report.dbhandler.DBHandlerFactory;
import com.topcoder.util.collection.typesafeenum.Enum;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * This is an abstract Base class that can be used to implement {@link Report}.
 *
 * @author fastprogrammer, traugust
 * @version 1.0
 */
public abstract class AbstractReport implements Report {
    /**
     * This is the namespace from which the column type configuration is looked up in {@link ConfigManager}.
     */
    private static final String COLUMNS_CONFIG_NAMESPACE = "com.topcoder.timetracker.report.ColumnsConfiguration";
    /**
     * This is the namespace from which the filters configuration is looked up in {@link ConfigManager}.
     */
    private static final String FILTERS_CONFIGURATION_NAMESPACE
        = "com.topcoder.timetracker.report.FiltersConfiguration";

    /**
     * This is a prefix used to construct the {@link ConfigManager} property name for default column configuration for a
     * specific {@link ReportType} and {@link ReportCategory}.
     */
    private static final String DEFAULT_COLUMNS_PROPERTY_PREFIX = "";

    /**
     * This is a prefix used to construct the {@link ConfigManager} property name for custom column configuration for a
     * specific {@link ReportType} and {@link ReportCategory}.
     */
    private static final String CUSTOM_COLUMNS_PROPERTY_PREFIX = "CUSTOM_";

    /**
     * Represents the {@link DBHandlerFactory} instance used by all {@link AbstractReport}s. This is a static field, as
     * the instance is to be shared between all instances of AbstractReport.
     * <p/>
     * <b>Note:</b> As the initialization of this field is executed lazily upon first usage, it is necessary that all
     * instances call {@link #getDBHandlerFactory()} to obtain or create (depending on whether the caller is the first
     * to call this method) the value of this field instead of accessing the field directly.
     * <p/>
     * This static field holds a DBHandlerFactory instance, which is used by the subclasses.
     */
    private static DBHandlerFactory dbHandlerFactory;

    /**
     * Represents the defaultColumnSet to be displayed for different type of reports.
     * <p/>
     * Example: For the Employee Report, belonging to the time Category, the default set will be {&quot;DATE&quot;,
     * &quot;CLIENT&quot;, &quot;PROJECT&quot;, &quot;TASK&quot;, &quot;HOURS&quot;, &quot;PAY_RATE&quot;,
     * &quot;BILLABLE&quot;}.
     * <p/>
     * This HashMap will contain the defaultColumn sets for the different types belonging to this category.
     * <p/>
     * The keys of this Map will be the possible {@link ReportType}s of this Report.
     * <p/>
     * The value in the Map corresponding to the key will be a {@link java.util.List} object, containing the default set
     * of {@link Column}s for that particular type of report. Note that the values in the List will be a subset of the
     * enumeration values defined by the {@link Column} enumeration class. This Map cannot hold <tt>null</tt> elements.
     */
    private final Map defaultColumnsForType = new HashMap();

    /**
     * Represents the defaultFilter for the type of reports.
     * <p/>
     * The keys of this Map will be the possible {@link ReportType}s of this Report.
     * <p/>
     * The value in the Map corresponding to the key will be a value among the enumeration values defined by {@link
     * FilterCategory}.
     * <p/>
     * This Map cannot hold <tt>null</tt> elements.
     */
    private final Map defaultFilterForType = new HashMap();

    /**
     * Represents the valid sets of filters for the different types of reports belonging to this category. Note that
     * valid filter List will also include the mandatory filter for the report.
     * <p/>
     * The keys of this Map will be the possible {@link ReportType}s of this Report.
     * <p/>
     * The value in the Map corresponding to the key will be a {@link java.util.List}, containing the valid set of
     * filters for that particular type of report. Note that the values in the List will be a subset of the enumeration
     * values defined by {@link FilterCategory}. This Map cannot hold <tt>null</tt> elements.
     */
    private final Map validFiltersForType = new HashMap();

    /**
     * Represents the Format in which this Report instance will render its data.
     */
    private final String format;

    /**
     * Represents the category of the Report, this Report instance handles.
     */
    private final ReportCategory category;

    /**
     * Creates a new AbstractReport. The configuration for this Report's category (as returned by {@link
     * #getCategory()}) is loaded.
     *
     * @param category the category of the instance to be created
     * @param format   the format of the instance to be created
     *
     * @throws ReportConfigurationException in case the configuration of this report fails
     */
    protected AbstractReport(final ReportCategory category, final String format) throws ReportConfigurationException {
        if (category == null) {
            throw new NullPointerException("The parameter named [category] was null.");
        }
        if (format == null) {
            throw new NullPointerException("The parameter named [format] was null.");
        }
        if (format.trim().length() == 0) {
            throw new IllegalArgumentException("The parameter named [format] was an empty String.");
        }

        this.category = category;
        this.format = format;

        for (Iterator iterator = ReportType.getReportTypes().iterator(); iterator.hasNext();) {
            final ReportType reportType = (ReportType) iterator.next();
            final FilterCategory mandatoryFilter = lookupMandatoryFilterConfiguration(reportType);
            final List validFilters = lookupOptionalFiltersConfiguration(reportType);
            validFilters.add(mandatoryFilter);
            defaultFilterForType.put(reportType, mandatoryFilter);
            validFiltersForType.put(reportType, validFilters);
            defaultColumnsForType.put(reportType, lookupDefaultColumnsConfiguration(reportType));
        }
    }

    /**
     * Creates a {@link ReportConfiguration} and then executes the Report by calling {@link
     * #executeReport(ReportConfiguration)} method.
     *
     * @param namespace the {@link ConfigManager} namespace from which the configuration properties for the Report are
     *                  read
     * @param type      the type of the report
     * @param filters   the filters for filtering the Report data
     * @param sortBy    the sort by clause
     *
     * @return a String containing the formatted report data
     *
     * @throws NullPointerException     if any arg is <tt>null</tt>
     * @throws ReportException          if there are problems during the processing of the report
     * @throws IllegalArgumentException if the namespace passed is an empty (trim'd) String
     */
    public String execute(String namespace, ReportType type, List filters, String sortBy) throws ReportException {
        // illegal args checked in createConfiguration
        return executeReport(createConfiguration(namespace, type, filters, sortBy));
    }

    /**
     * looks up the {@link DBHandlerFactory} instances shred among all instances of AbstractReport. In case the shared
     * instance has not yet been created, it will be created in this call and stored into the static field
     * dbHandlerFactory for later share re-use.
     *
     * @return the shared DBHandlerFactory instance
     *
     * @throws ReportConfigurationException in case the creation of the DBHandlerFactory instance fails
     */
    protected DBHandlerFactory getDBHandlerFactory() throws ReportConfigurationException {
        //fast access outside the synchronized block
        if (dbHandlerFactory != null) {
            return dbHandlerFactory;
        }
        //if still null create the reportFactory
        synchronized (AbstractReport.class) {
            // This is checked a second time inside the synchronized-block as
            // another thread could have created the instance while we were
            // waiting on the monitor to enter the synchronized-block
            if (dbHandlerFactory != null) {
                return dbHandlerFactory;
            }
            dbHandlerFactory = new DBHandlerFactory();
            return dbHandlerFactory;
        }
    }

    /**
     * This method returns the category of this Report instance.
     *
     * @return the category of the report.
     */
    public ReportCategory getCategory() {
        return category;
    }

    /**
     * This method returns the format in which the report data will be rendered by them upon call to {@link
     * #executeReport(ReportConfiguration)}.
     *
     * @return the format in which the report data will be rendered
     */
    public String getFormat() {
        return format;
    }

    /**
     * This is an abstract method and needs to be implemented by the subclasses. This method fetches and formats the
     * report data based on the {@link ReportConfiguration} specified.
     *
     * @param config the configuration for the Report
     *
     * @return a String containing the formatted report data
     *
     * @throws NullPointerException if the config passed is <tt>null</tt>
     * @throws ReportException      in case the report execution fails
     */
    protected abstract String executeReport(ReportConfiguration config) throws ReportException;

    /**
     * Creates a configuration based on the parameter being passed. This method also validates if the Filters, Columns
     * etc. for the report is valid. If found invalid, a {@link ReportConfigurationException} is thrown.
     *
     * @param namespace the {@link ConfigManager} namespace from which the properties for the report are fetched
     * @param type      the type of the report
     * @param filters   the filters to be used for the Report.
     * @param sortBy    the sort by clause for the Report.
     *
     * @return the configuration for the report
     *
     * @throws NullPointerException         if any arg is <tt>null</tt>
     * @throws ReportConfigurationException if there are problems creating the ReportConfiguration
     * @throws IllegalArgumentException     if the namespace passed is an empty (trim'd) String
     */
    private ReportConfiguration createConfiguration(final String namespace, final ReportType type, final List filters,
        final String sortBy) throws ReportConfigurationException {
        if (namespace == null) {
            throw new NullPointerException("The parameter named [namespace] was null.");
        }
        if (type == null) {
            throw new NullPointerException("The parameter named [type] was null.");
        }
        if (filters == null) {
            throw new NullPointerException("The parameter named [filters] was null.");
        }

        if (namespace.trim().length() == 0) {
            throw new IllegalArgumentException("The parameter named [namespace] was an empty String.");
        }

        //    check the validity of given filters
        final List validFilters = (List) validFiltersForType.get(type);
        final FilterCategory requiredDefaultFilter = (FilterCategory) defaultFilterForType.get(type);
        boolean defaultFilterExist = false;
        String header = null;
        String dateInfo = null;
        for (Iterator iterator = filters.iterator(); iterator.hasNext();) {
            final Filter filter = (Filter) iterator.next();
            if (filter == null) {
                throw new IllegalArgumentException("The parameter named [filters] contained a null value.");
            }
            final FilterCategory current = filter.getCategory();
            if (!validFilters.contains(current)) {
                throw new ReportConfigurationException("The specified Filter of category [" + current
                    + "] is not allowed for Reports of type [" + type + "] and report category ["
                    + category.getCategory() + "].");
            }
            if ((filter instanceof EqualityFilter) && ((EqualityFilter) filter).getFilterValues().isEmpty()) {
                throw new ReportConfigurationException("The specified Filter of category [" + current
                    + "] was empty.");

            } else if (filter instanceof RangeFilter) {
                if (((RangeFilter) filter).getLowerRangeValues().isEmpty()) {
                    throw new ReportConfigurationException("The specified Filter of category [" + current
                            + "] was empty.");
                } else {
                    dateInfo = "";
                    if (!ReportDisplayTag.MIN_DATE.equals(((RangeFilter) filter).getLowerRangeValues().get(0))) {
                        dateInfo = " " + ((RangeFilter) filter).getLowerRangeValues().get(0);
                    }
                    if (!ReportDisplayTag.MAX_DATE.equals(((RangeFilter) filter).getUpperRangeValues().get(0))) {
                        dateInfo = dateInfo + " to " + ((RangeFilter) filter).getUpperRangeValues().get(0);
                    } else {
                        dateInfo = " from " + dateInfo;
                    }
                }
            } else if (filter instanceof InFilter) {
                if (((InFilter) filter).getValues().isEmpty()) {
                    throw new ReportConfigurationException("The specified Filter of category [" + current
                            + "] was empty.");
                }
            }
            if (current.equals(requiredDefaultFilter)) {
                header = ((EqualityFilter) filter).getFilterValues().get(0).toString();
                defaultFilterExist = true;
            }
        }
        if (!defaultFilterExist) {
            throw new ReportConfigurationException("The required Filter of category [" + requiredDefaultFilter
                + "] for Reports of type [" + type + "] and report category ["
                + category.getCategory() + "] was not present in the given configuration.");
        }

        // create the styles map
        try {
            final ReportConfiguration reportConfiguration = new ReportConfiguration(category, type, namespace);
            reportConfiguration.setColumnDecorators(lookupColumnConfiguration(type, namespace));
            reportConfiguration.setStyles(lookupStyleConfiguration(namespace));
            reportConfiguration.setFilters(filters);
            reportConfiguration.setHeader(header + (dateInfo == null ? "" : dateInfo));
            reportConfiguration.setSortBy(sortBy);
            return reportConfiguration;
        } catch (UnknownNamespaceException e) {
            throw new ReportConfigurationException(
                "The namespace [" + namespace + "] to be used for report configuration did not exist.", e);
        }

    }

    /**
     * This method looks up the Style configuration from the given {@link ConfigManager} namespace.
     *
     * @param namespace the namespace to lookup the styles configuration from
     *
     * @return the style configuration looked up from the given {@link ConfigManager} namespace
     *
     * @throws UnknownNamespaceException in case the given namespace does not exist in {@link ConfigManager}
     */
    private Map lookupStyleConfiguration(final String namespace) throws UnknownNamespaceException {
        final List styleConstants = StyleConstant.getStyleConstants();
        final Map styles = new HashMap(styleConstants.size());

        for (Iterator iterator = styleConstants.iterator(); iterator.hasNext();) {
            final StyleConstant styleConstant = (StyleConstant) iterator.next();
            final String styleVal = ConfigManager.getInstance().getString(namespace, styleConstant.getName());
            styles.put(styleConstant, styleVal != null ? styleVal : "");
        }
        return styles;
    }

    /**
     * This method looks up the Column configuration for the given {@link ReportType} from the given {@link
     * ConfigManager} namespace.
     *
     * @param type      the ReportType to lookup the column configuration for
     * @param namespace the {@link ConfigManager} namespace from which to lookup the column configuration
     *
     * @return the column configuration for the given {@link ReportType} as looked up from the given {@link
     *         ConfigManager} namespace
     *
     * @throws UnknownNamespaceException    in case the given namespace does not exist in {@link ConfigManager}
     * @throws ReportConfigurationException in case the column configuration looked up from {@link ConfigManager}
     *                                      contains a {@link Column} that is not allowed for the given {@link
     *                                      ReportType}
     */
    private List lookupColumnConfiguration(final ReportType type, final String namespace)
        throws UnknownNamespaceException,
        ReportConfigurationException {
        final List defaultColumns = (List) defaultColumnsForType.get(type);
        final List ret = new ArrayList(defaultColumns.size());

        //try to find custom columns
        List columnsOfReport = lookupColumnsConfiguration(type, CUSTOM_COLUMNS_PROPERTY_PREFIX, namespace);
        if (columnsOfReport == null || columnsOfReport.isEmpty()) {
            // else use default columns defined for report
            columnsOfReport = defaultColumns;
        }

        for (Iterator iterator = columnsOfReport.iterator(); iterator.hasNext();) {
            final Column column = (Column) iterator.next();
            final String key = "COLUMN_" + column.getName();
            final String customColName = ConfigManager.getInstance().getString(namespace, key);

            // check whether column is allowed
            if (!defaultColumns.contains(column)) {
                throw new ReportConfigurationException("The given custom column [" + column
                    + "] configured to be in Report category [" + category + "] type [" + type
                    + "] is not allowed for that category and type "
                    + "(the offending value has been looked up in namespace [" + namespace + "]).");
            }
            //add column with custom or default name depending whether custom name was found
            if (customColName != null) {
                ret.add(new BasicColumnDecorator(column.getName(), customColName));
            } else {
                ret.add(new BasicColumnDecorator(column.getName(), column.getName()));
            }
        }

        //find prefixes and suffixes
        for (Iterator iterator = ret.iterator(); iterator.hasNext();) {
            final BasicColumnDecorator basicColumnDecorator = (BasicColumnDecorator) iterator.next();
            final String prefixkey = "PREFIX_COLUMN_" + basicColumnDecorator.getColumnName();
            final String prefix = ConfigManager.getInstance().getString(namespace, prefixkey);
            if (prefix != null && prefix.trim().length() > 0) {
                basicColumnDecorator.setPrefix(prefix);
            }
            final String suffixkey = "SUFFIX_COLUMN_" + basicColumnDecorator.getColumnName();
            final String suffix = ConfigManager.getInstance().getString(namespace, suffixkey);
            if (suffix != null && suffix.trim().length() > 0) {
                basicColumnDecorator.setSuffix(suffix);
            }
        }
        return ret;
    }

    /**
     * This method looks up the mandatory filter configuration for the given {@link ReportType} from the given {@link
     * ConfigManager} namespace.
     *
     * @param reportType the ReportType to lookup the mandatory filter configuration for
     *
     * @return the mandatory filter for the given {@link ReportType}  and this instance's {@link ReportCategory} (as
     *         retrieved from {@link #getCategory()}), looked up from {@link ConfigManager} namespace {@link
     *         #filtersConfigurationNamespace}
     *
     * @throws ReportConfigurationException in case the mandatory filter configuration lookup from {@link ConfigManager}
     *                                      fails
     */
    private FilterCategory lookupMandatoryFilterConfiguration(final ReportType reportType) throws
        ReportConfigurationException {
        final String key = reportType.getType() + "_" + category.getCategory() + "_MANDATORY_FILTER";
        final String enumValueName;
        try {
            enumValueName = ConfigManager.getInstance().getString(FILTERS_CONFIGURATION_NAMESPACE, key);
        } catch (UnknownNamespaceException e) {
            throw new ReportConfigurationException("Unable to load the mandatory filter configuration for report type ["
                + reportType + "] and report category [" + category
                + "] (looked up from config manager namespace [" + FILTERS_CONFIGURATION_NAMESPACE + "] property ["
                + key
                + "]), as the namespace [" + FILTERS_CONFIGURATION_NAMESPACE
                + "] was not found in ConfigManager configuration.", e);

        }
        if (enumValueName == null) {
            throw new ReportConfigurationException("Unable to load the mandatory filter configuration for report type ["
                + reportType + "] and report category [" + category
                + "] (looked up from config manager namespace [" + FILTERS_CONFIGURATION_NAMESPACE + "] property ["
                + key
                + "]), as the ConfigManager property ["
                + key + "]  in namespace [" + FILTERS_CONFIGURATION_NAMESPACE
                + "] was not defined.");
        }
        if (enumValueName.trim().length() == 0) {
            throw new ReportConfigurationException("Unable to load the mandatory filter configuration for report type ["
                + reportType + "] and report category [" + category
                + "] (looked up from config manager namespace [" + FILTERS_CONFIGURATION_NAMESPACE + "] property ["
                + key
                + "]), as the ConfigManager property ["
                + key + "]  in namespace [" + FILTERS_CONFIGURATION_NAMESPACE
                + "] was an empty String.");
        }
        try {
            FilterCategory bill = FilterCategory.BILLABLE;
            return (FilterCategory) safeConvertToEnumValue(enumValueName, FilterCategory.class);
        } catch (ReportConfigurationException e) {
            throw new ReportConfigurationException("Conversion of mandatory filter configuration for report type ["
                + reportType + "] and report category [" + category
                + "] (looked up from config manager namespace [" + FILTERS_CONFIGURATION_NAMESPACE + "] property ["
                + key
                + "]) into valid FilterCategory enum failed.", e);
        }
    }

    /**
     * This method looks up the optional filter configuration for the given {@link ReportType} from the given {@link
     * ConfigManager} namespace.
     *
     * @param reportType the ReportType to lookup the optional filter configuration for
     *
     * @return the list of available optional filters for the given {@link ReportType}  and this instance's {@link
     *         ReportCategory} (as retrieved from {@link #getCategory()}), looked up from {@link ConfigManager}
     *         namespace {@link #FILTERS_CONFIGURATION_NAMESPACE}
     *
     * @throws ReportConfigurationException in case the optional filter configuration lookup from {@link ConfigManager}
     *                                      fails
     */

    private List lookupOptionalFiltersConfiguration(final ReportType reportType) throws
        ReportConfigurationException {
        final String key = reportType.getType() + "_" + category.getCategory() + "_OPTIONAL_FILTER";
        final String[] stringArray;
        try {
            stringArray = ConfigManager.getInstance().getStringArray(FILTERS_CONFIGURATION_NAMESPACE, key);
        } catch (UnknownNamespaceException e) {
            throw new ReportConfigurationException("Unable to load the optional filter configuration for report type ["
                + reportType + "] and report category [" + category
                + "] (looked up from config manager namespace [" + FILTERS_CONFIGURATION_NAMESPACE + "] property ["
                + key
                + "]), as the namespace [" + FILTERS_CONFIGURATION_NAMESPACE
                + "] was not found in ConfigManager configuration.", e);

        }
        if (stringArray == null || stringArray.length == 0) {
            throw new ReportConfigurationException("Unable to load the optional filter configuration for report type ["
                + reportType + "] and report category [" + category
                + "] (looked up from config manager namespace [" + FILTERS_CONFIGURATION_NAMESPACE + "] property ["
                + key
                + "]), as the ConfigManager property ["
                + key + "]  in namespace [" + FILTERS_CONFIGURATION_NAMESPACE
                + "] was not defined or did not contain any values.");
        }
        final List ret = new ArrayList();
        for (int i = 0; i < stringArray.length; i++) {
            final String enumName = stringArray[i];
            try {
                ret.add(safeConvertToEnumValue(enumName, FilterCategory.class));
            } catch (ReportConfigurationException e) {
                throw new ReportConfigurationException("Conversion of optional filter configuration for report type ["
                    + reportType + "] and report category [" + category
                    + "] (looked up from config manager namespace [" + FILTERS_CONFIGURATION_NAMESPACE + "] property ["
                    + key
                    + "]) into valid FilterCategory enums failed.", e);
            }
        }
        return ret;
    }

    /**
     * This method looks up the default column configuration for the given {@link ReportType} from the given {@link
     * ConfigManager} namespace.
     *
     * @param reportType the ReportType to lookup default the column configuration for
     *
     * @return the list of default {@link Column}s for the given {@link ReportType}  and this instance's {@link
     *         ReportCategory} (as retrieved from {@link #getCategory()}), looked up from {@link ConfigManager}
     *         namespace {@link #COLUMNS_CONFIG_NAMESPACE}
     *
     * @throws ReportConfigurationException in case the column configuration lookup from {@link ConfigManager} fails ort
     *                                      the lookup returned a <tt>null</tt> / empty columns list
     */
    private List lookupDefaultColumnsConfiguration(final ReportType reportType) throws ReportConfigurationException {
        final List ret = lookupColumnsConfiguration(reportType,
            DEFAULT_COLUMNS_PROPERTY_PREFIX,
            COLUMNS_CONFIG_NAMESPACE);
        if (ret == null) {
            throw new ReportConfigurationException("Unable to load the default columns configuration for report type ["
                + reportType + "] and report category [" + category
                + "] (looked up from config manager namespace [" + COLUMNS_CONFIG_NAMESPACE
                + "] , as the ConfigManager property was not defined or did not contain any values.");
        }
        return ret;
    }

    /**
     * This method looks up the column configuration for the given {@link ReportType} from the given {@link
     * ConfigManager} namespace.
     *
     * @param reportType     the ReportType to lookup the column configuration for
     * @param propertyPrefix the prefix to be used when constructing the property name to be used for lookup of column
     *                       configuration
     * @param namespace      the namespace to lookup the column configuration from
     *
     * @return the list of {@link Column}s for the given {@link ReportType}  and this instance's {@link ReportCategory}
     *         (as retrieved from {@link #getCategory()}), looked up from {@link ConfigManager} namespace {@link
     *         #COLUMNS_CONFIG_NAMESPACE} using the prefix specified, or <tt>null</tt> if the property does not exist or
     *         was empty
     *
     * @throws ReportConfigurationException in case the column configuration lookup from {@link ConfigManager} fails
     */

    private List lookupColumnsConfiguration(final ReportType reportType, final String propertyPrefix,
                                            final String namespace)
        throws
        ReportConfigurationException {
        final String key = propertyPrefix + reportType.getType() + "_" + category.getCategory() + "_COLUMNS";
        final String[] stringArray;

        try {
            stringArray = ConfigManager.getInstance().getStringArray(namespace, key);
        } catch (UnknownNamespaceException e) {
            throw new ReportConfigurationException("Unable to load the default columns configuration for report type ["
                + reportType + "] and report category [" + category
                + "] (looked up from config manager namespace [" + namespace + "] property [" + key
                + "]), as the namespace [" + namespace
                + "] was not found in ConfigManager configuration.", e);

        }
        if (stringArray == null || stringArray.length == 0) {
            return null;
        }
        final List ret = new ArrayList();
        for (int i = 0; i < stringArray.length; i++) {
            final String enumName = stringArray[i];
            try {
                ret.add(safeConvertToEnumValue(enumName, Column.class));
            } catch (ReportConfigurationException e) {
                throw new ReportConfigurationException("Conversion of default columns configuration for report type ["
                    + reportType + "] and report category [" + category
                    + "] (looked up from config manager namespace [" + namespace + "] property [" + key
                    + "]) into valid ColumnType enums failed.", e);
            }
        }
        return ret;
    }

    /**
     * This method converts the given String value into an {@link Enum} of the given enum class.
     *
     * @param enumValueName the value to be converted into an enum
     * @param enumClass     the enum class in which to lookup the enum value corresponding to the given enumValueName
     *
     * @return the enum looked up from the given enumValueName as instance of the given enumValueClass
     *
     * @throws ReportConfigurationException in case the enumValueName does not correspond to any of the enum values
     *                                      defined in the given enumClass
     */
    private static Enum safeConvertToEnumValue(final String enumValueName, final Class enumClass)
        throws ReportConfigurationException {
        if (enumValueName == null) {
            throw new ReportConfigurationException("There was a null value given as enumValueName.");
        }
        if (enumValueName.trim().length() == 0) {
            throw new ReportConfigurationException(
                "The given enumValueName was an empty String.");
        }

        final Enum ret = Enum.getEnumByStringValue(enumValueName, enumClass);

        if (ret == null) {
            throw new ReportConfigurationException("The given enumValueName ([" + enumValueName
                + "]) was not a valid enum value of enum type [" + enumClass.getName() + "].");
        }
        return ret;
    }

}
