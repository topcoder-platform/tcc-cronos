/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report.dbhandler;

import com.topcoder.db.connectionfactory.ConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.UnknownConnectionException;
import com.cronos.timetracker.report.EqualityFilter;
import com.cronos.timetracker.report.Filter;
import com.cronos.timetracker.report.FilterType;
import com.cronos.timetracker.report.InFilter;
import com.cronos.timetracker.report.RangeFilter;
import com.cronos.timetracker.report.ReportCategory;
import com.cronos.timetracker.report.ReportConfiguration;
import com.cronos.timetracker.report.ReportConfigurationException;
import com.cronos.timetracker.report.ReportType;
import com.topcoder.util.collection.typesafeenum.Enum;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.Property;
import com.topcoder.util.config.UnknownNamespaceException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * This class implements the DBHandler interface and handles the Database specific operation defined by the interface
 * for the Informix Database.
 * <p/>
 * This class is thread-safe, because its instance member baseQueries is immutable and the access to the only other
 * instance member resultsetConnectionMap is synchronized. (By making the map a synchronized Map).
 *
 * @author fastprogrammer, traugust
 * @version 1.0
 */
public class InformixDBHandler implements DBHandler {

    /**
     * This is the name of the {@link ConfigManager} namespace used for lookup of {@link #baseQueries} during invocation
     * of {@link #loadConfiguration()}.
     */
    private static final String QUERIES_CONFIGURATION_NAMESPACE
        = "com.cronos.timetracker.report.QueriesConfiguration";
    /**
     * This is the prefix from which the {@link ConfigManager} property names used for lookup of {@link #baseQueries}
     * during invocation of {@link #loadConfiguration()} are constructed.
     */
    private static final String BASE_QUERY_LOOKUP_KEY_PREFIX = "BASE_QUERY_";
    /**
     * This is the name of the {@link ConfigManager} namespace used for lookup of column types by column name.
     */
    private static final String COLUMN_TYPES_NAMESPACE = "com.cronos.timetracker.report.ColumnTypes";
    /**
     * This is the suffix from which the {@link ConfigManager} property names used for lookup of column types by column
     * name are constructed.
     */
    private static final String COLUMN_TYPE_LOOKUP_KEY_SUFFIX = "_TYPE";
    /**
     * This is the prefix from which the {@link ConfigManager} property names used for lookup of SQL fragments for a
     * filter are constructed.
     */
    private static final String FILTER_LOOKUP_KEY_PREFIX = "FILTER_";

    /**
     * This is the prefix from which the {@link ConfigManager} property names used for lookup of SQL fragments for a
     * sort by are constructed.
     */
    private static final String SORT_BY_LOOKUP_KEY_PREFIX = "SORT_BY_";

    /**
     * This map maintains a mapping between the {@link java.sql.ResultSet} object generated as a part of the call to the
     * {@link #getReportData(ReportConfiguration)} method and the corresponding {@link java.sql.Connection} object used
     * to obtain the ResultSet. This is required for releasing the connection back to the pool, when the usage of the
     * ResultSet is completed.
     * <p/>
     * The entries are removed from the Map during the call to the {@link #release(java.sql.ResultSet)} method.
     * <p/>
     * This instance member cannot be <tt>null</tt>. This Map cannot hold <tt>null</tt> elements, but can be empty.
     */
    private final Map resultsetConnectionMap = Collections.synchronizedMap(new HashMap());

    /**
     * This is Map contains all the Base-Queries, for all the different Reports possible by the {@link ReportType} and
     * the {@link ReportCategory} defined. The Base-Queries for all the type and category of the {@link
     * com.cronos.timetracker.report.Report}, are specified in the configuration that is loaded via the {@link
     * com.topcoder.util.config.ConfigManager} an then put into in the Map.
     * <p/>
     * The key for the Map will be a string which is combination of {@link ReportType} and the {@link ReportCategory}.
     * (example: EMPLOYEE_EXPENSE) and the value will be string with the Base-Query for that report.
     * <p/>
     * This instance member will not be <tt>null</tt>.
     * <p/>
     * (Note that the final query will be a combination of the BaseQuery and the dynamic generated part of the query
     * based on the Filters used).
     * <p/>
     * This Map cannot hold <tt>null</tt> elements, but can be empty.
     */
    private final Map baseQueries = new HashMap();
    /**
     * This date format instance will be used for parsing Strings into {@link java.util.Date} instances for the query.
     */
    private final SimpleDateFormat dateParser = new SimpleDateFormat("MM-dd-yyyy");

    /**
     * Constructor. Simply loads the configuration.
     *
     * @throws ReportConfigurationException if the exception is thrown during the configuration
     */
    public InformixDBHandler() throws ReportConfigurationException {
        loadConfiguration();

        // We need this, as otherwise parsing '2006-01-01' would not throw an
        // exception but would be parsed as January, 6th, 197 AD
        dateParser.setLenient(false);
    }

    /**
     * Fetches the Report data from the Database based on the {@link ReportConfiguration} passed and returns the fetched
     * data in the form of a {@link java.sql.ResultSet}.
     *
     * @param config the ReportConfiguration which specifies all the parameters required to fetch the data for the
     *               report.
     *
     * @return the {@link java.sql.ResultSet} returned from the database query
     *
     * @throws ReportSQLException           if there are any errors during Database access
     * @throws NullPointerException         if the parameter "config" is <tt>null</tt>
     * @throws ReportConfigurationException in case the report configuration fails due to invalid or missing
     *                                      configuration data
     */
    public ResultSet getReportData(final ReportConfiguration config)
        throws ReportSQLException, ReportConfigurationException {
        if (config == null) {
            throw new NullPointerException("The parameter named [config] was null.");
        }
        final String namespace = config.getNamespace();
        final String connectionName = getStringPropertyFromConfigManager(namespace, "Connection");
        final String dbConnectionFactoryNamespace = getStringPropertyFromConfigManager(namespace,
            "DBConnectionFactoryNamespace");

        final DBConnectionFactory factory;
        try {
            factory = new DBConnectionFactoryImpl(dbConnectionFactoryNamespace);
        } catch (ConfigurationException e) {
            throw new ReportConfigurationException(
                "DBConnectionFactory could not be initialized because of configuration errors.", e);
        } catch (UnknownConnectionException e) {
            throw new ReportConfigurationException(
                "DBConnectionFactory could not be initialized because of configuration "
                    + "errors with the default connection configured.", e);
        }
        final Connection con;
        try {
            con = factory.createConnection(connectionName);
        } catch (DBConnectionException e) {
            throw new ReportSQLException("The was an error while connecting to the database.", e);
        }
        final String reportType = config.getType().getType();
        final String reportCategory = config.getCategory().getCategory();
        PreparedStatement statement = null;
        String sql = "";
        String sortBy = getOptionalStringPropertyFromConfigManager(QUERIES_CONFIGURATION_NAMESPACE,
                SORT_BY_LOOKUP_KEY_PREFIX + reportType + "_" + reportCategory);

        final String queryString = (String) baseQueries.get(reportType + "_" + reportCategory);
        if (queryString == null) {
            throw new ReportConfigurationException("The base query for the report of type [" + reportType
                + "] and category [" + reportCategory
                + "] has not been found in the configuration (loaded from namespace ["
                + QUERIES_CONFIGURATION_NAMESPACE + "])");
        }
        try {
            //  Needs special handling for TIMEEXPENSE reports because the queries for
            // TIMEEXPENSE reports involve a union in the query and hence
            // the filterpart of the query and the setting up of parameters for
            // the query from the filter values are handled
            // differently and needs to be looked at with more care.
            if (config.getCategory() == ReportCategory.TIMEEXPENSE) {
                // since the base query involves a union, we split the base query at
                // the union point and then append the filter conditions to both the
                // parts of the query and then join these two parts back
                // with a union to get the final query.
                final String[] baseQueryParts = splitIntoTwoParts(queryString, "UNION");
                int totalParams = 0;
                final List filters = config.getFilters();
                final StringBuffer baseQueryPart1 = new StringBuffer(baseQueryParts[0]);
                final StringBuffer baseQueryPart2 = new StringBuffer(baseQueryParts[1]);
                for (Iterator iterator = filters.iterator(); iterator.hasNext();) {
                    final Filter filter = (Filter) iterator.next();

                    final String colName = filter.getColumn().getName();
                    final String filterQueryPart = getStringPropertyFromConfigManager(QUERIES_CONFIGURATION_NAMESPACE,
                        FILTER_LOOKUP_KEY_PREFIX + colName + "_" + reportType + "_" + reportCategory);

                    // since the filter part of the query for the TIMEEXPENSE reports are configured
                    // with a UNION and each side part of the UNION of the filterQueryPart will be
                    // appended to the corresponding part of the BaseQuery.
                    final String[] filterparts = splitIntoTwoParts(filterQueryPart, "UNION");

                    int count = 0;
                    if (filter instanceof RangeFilter) {
                        count = ((RangeFilter) filter).getLowerRangeValues().size();
                    } else if (filter instanceof EqualityFilter) {
                        count = ((EqualityFilter) filter).getFilterValues().size();
                    } else { // in filter
                        count = 1;
                    }
                    if (count > 0) { //don't put an empty 'AND()' into the query string if filter is empty
                        baseQueryPart1.append(" AND (");
                        baseQueryPart2.append(" AND (");
                        for (int i = 0; i < count; i++) {

                            if (i > 0) {
                                baseQueryPart1.append(" OR ");
                                baseQueryPart2.append(" OR ");
                            }
                            baseQueryPart1.append("(");
                            baseQueryPart1.append(filterparts[0]);
                            baseQueryPart1.append(")");

                            baseQueryPart2.append("(");
                            baseQueryPart2.append(filterparts[1]);
                            baseQueryPart2.append(")");
                        }
                        baseQueryPart1.append(") ");
                        baseQueryPart2.append(") ");
                    }

                    count = (filter.getType() == FilterType.RANGE)
                            ? count * 2
                            : count;
                    totalParams += count;
                }
                String baseQuery = baseQueryPart1.append(" UNION ").append(baseQueryPart2).toString();
                if (config.getSortBy() == null) {
                    sql = baseQuery + (sortBy == null ? "" : " Order By " + sortBy);
                } else {
                    sql = baseQuery + " Order By " + config.getSortBy();
                }
                statement = con.prepareStatement(sql);

                int counter = 0;
                for (Iterator iterator = filters.iterator(); iterator.hasNext();) {
                    final Filter filter = (Filter) iterator.next();

                    final ColumnType colType = lookupColumnType(filter.getColumn().getName());

                    if (filter instanceof RangeFilter) {
                        final List lowerValues = ((RangeFilter) filter).getLowerRangeValues();
                        final List upperValues = ((RangeFilter) filter).getUpperRangeValues();

                        for (int i = 0; i < lowerValues.size(); i++) {
                            counter++;
                            final String lowerVal = (String) lowerValues.get(i);
                            final String upperVal = (String) upperValues.get(i);
                            setValueInStatement(statement, counter, lowerVal, colType);
                            // we use +totalParams
                            // because this is the equivalent second half of the placeholder
                            // list (all params exist duplicated in the
                            // union so the placeholder list is 2*totalParams long)
                            setValueInStatement(statement, counter + totalParams, lowerVal, colType);
                            counter++;
                            setValueInStatement(statement, counter, upperVal, colType);
                            // we use +totalParams
                            // because this is the equivalent second half of the placeholder
                            // list (all params exist duplicated in the
                            // union so the placeholder list is 2*totalParams long)
                            setValueInStatement(statement, counter + totalParams, upperVal, colType);
                        }

                    } else if (filter instanceof EqualityFilter){
                        // filter is an EqualityFilter
                        final List values = ((EqualityFilter) filter).getFilterValues();
                        for (Iterator iterator1 = values.iterator(); iterator1.hasNext();) {
                            counter++;
                            final String value = (String) iterator1.next();
                            setValueInStatement(statement, counter, value, colType);
                            // we use +totalParams
                            // because this is the equivalent second half of the placeholder
                            // list (all params exist duplicated in the
                            // union so the placeholder list is 2*totalParams long)
                            setValueInStatement(statement, counter + totalParams, value, colType);
                        }
                    } else {
                        String value = ((InFilter) filter).toString();
                        counter++;
                        setValueInStatement(statement, counter, value, colType);
                        setValueInStatement(statement, counter + totalParams, value, colType);
                    }
                }

                final ResultSet resultSet = statement.executeQuery();
                resultsetConnectionMap.put(resultSet, con);
                return resultSet;
            } else {
                // For reports belonging to the category of TIME or EXPENSE reports.
                final StringBuffer baseQuery = new StringBuffer(queryString);
                final List filters = config.getFilters();
                for (Iterator iterator = filters.iterator(); iterator.hasNext();) {
                    final Filter filter = (Filter) iterator.next();

                    final String colName = filter.getColumn().getName();
                    final String filterQueryPart = getStringPropertyFromConfigManager(QUERIES_CONFIGURATION_NAMESPACE,
                        FILTER_LOOKUP_KEY_PREFIX + colName + "_" + reportType + "_" + reportCategory);

                    int count = 0;
                    if (filter instanceof RangeFilter) {
                        count = ((RangeFilter) filter).getLowerRangeValues().size();
                    } else if (filter instanceof EqualityFilter) {
                        count = ((EqualityFilter) filter).getFilterValues().size();
                    } else {
                        count = ((InFilter) filter).getValues().size();
                    }
                    if (count > 0) { //don't put an empty 'AND()' into the query string if filter is empty
                        baseQuery.append(" AND (");
                        for (int i = 0; i < count; i++) {
                            if (i > 0) {
                                baseQuery.append(" OR ");
                            }
                            baseQuery.append("(");
                            baseQuery.append(filterQueryPart);
                            baseQuery.append(")");
                        }
                        baseQuery.append(")");
                    }
                }
                if (config.getSortBy() == null) {
                    sql = baseQuery + (sortBy == null ? "" : " Order By " + sortBy);
                } else {
                    sql = baseQuery + " Order By " + config.getSortBy();
                }
                statement = con.prepareStatement(sql);
                int counter = 0;

                for (Iterator iterator = filters.iterator(); iterator.hasNext();) {
                    final Filter filter = (Filter) iterator.next();

                    final String colName = filter.getColumn().getName();
                    final ColumnType colType = lookupColumnType(colName);

                    if (filter instanceof RangeFilter) {
                        final List lowerValues = ((RangeFilter) filter).getLowerRangeValues();
                        final List upperValues = ((RangeFilter) filter).getUpperRangeValues();
                        final int size = lowerValues.size();
                        for (int i = 0; i < size; i++) {
                            counter++;
                            final String lowerVal = (String) lowerValues.get(i);
                            final String upperVal = (String) upperValues.get(i);
                            setValueInStatement(statement, counter, lowerVal, colType);
                            counter++;
                            setValueInStatement(statement, counter, upperVal, colType);
                        }

                    } else if (filter instanceof EqualityFilter) {
                        // filter is an EqualityFilter
                        final List values = ((EqualityFilter) filter).getFilterValues();
                        for (Iterator iterator1 = values.iterator(); iterator1.hasNext();) {
                            counter++;
                            final String value = (String) iterator1.next();
                            setValueInStatement(statement, counter, value, colType);
                        }
                    } else {
                        String value = ((InFilter) filter).toString();
                        counter++;
                        setValueInStatement(statement, counter, value, colType);
                    }
                }
                final ResultSet resultSet = statement.executeQuery();
                resultsetConnectionMap.put(resultSet, con);
                return resultSet;
            }
        } catch (SQLException e) {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e1) {
                    //there's nothing we can do about this one, we only wanted to cleanup any resources
                    // As we already have an exception when being here, it makes no sense
                    // to wrap and throw this one as it would mask the original exception
                }
            }
            throw new ReportSQLException("Unable to execute database query [" + sql + "] .", e);
        }
    }

    /**
     * Releases the {@link java.sql.ResultSet} and the corresponding {@link java.sql.Connection} associated with the
     * {@link java.sql.ResultSet}.
     *
     * @param resultset the {@link ResultSet} to be released
     *
     * @throws NullPointerException if the resultset is <tt>null</tt>.
     * @throws ReportSQLException   if there are errors from the Database during the release operation.
     */
    public void release(final ResultSet resultset) throws ReportSQLException {
        if (resultset == null) {
            throw new NullPointerException("The parameter named [resultset] was null.");
        }

        final Connection connection = (Connection) resultsetConnectionMap.remove(resultset);
        if (connection != null) {
            // store a flag whether any exception occurring in the closing of Result set,
            // as we want to proceed closing the connection even if closing the resultSet fails
            // so we close connection in the finally-block of that try-statement
            //
            // If this variable is true, the exception of this try-block will be thrown
            // from this method rather than the second one that occurred during close.
            //
            // So this is only functionality to decide whether an exception in connection
            // close is allowed to be thrown or shall be ignored as it would otherwise
            // mask the exception thrown in resultSet.close
            boolean haveException = false;

            try {
                resultset.close();
            } catch (SQLException e) {
                haveException = true;
                throw new ReportSQLException(
                    "An error occurred while closing the ResultSet that was about to be released.", e);
            } finally {

                // close the underlying connection of the resultset
                try {
                    connection.close();
                } catch (SQLException e) {
                    // as we have no multi-caused Exception class, there's nothing we can do about
                    // the second exception, as only one exception can be thrown from the method.
                    // As the closing of the ResultSet is the primary use of this method and
                    // closing the underlying connection is somewhat a cleanup task,
                    // we prefer to encapsulate and throw the Exception from resultSet
                    // closing in case there already was one.
                    //
                    // unconditionally Throwing the exception from the finally-block
                    // would mask any exception that occurred in resultSet.close
                    // So we only throw if there was not already an exception in resultSet.close
                    if (!haveException) {
                        throw new ReportSQLException(
                            "An error occurred while closing the underlying Connection "
                                + "of the ResultSet that was about to be released.", e);
                    }

                }
            }
        }
    }

    /**
     * Loads the baseQueries Map from the Base Queries (properties whose name starts with {@link
     * #BASE_QUERY_LOOKUP_KEY_PREFIX} followed by a {@link ReportType} name, a '<tt>_</tt>' in between and a {@link
     * ReportCategory} name) specified in the configuration in namespace {@link #QUERIES_CONFIGURATION_NAMESPACE}.
     * <p/>
     * The Base Queries are looked up for all possible combinations of {@link ReportType} and {@link ReportCategory}.
     * The possible values for both of the {@link com.topcoder.util.collection.typesafeenum.Enum} types are looked up
     * using {@link ReportCategory#getReportCategories()} and {@link ReportType#getReportTypes()} respectively.
     *
     * @throws ReportConfigurationException if there are errors during the reading of the configuration from the
     *                                      Configuration file.
     */
    private void loadConfiguration() throws ReportConfigurationException {
        final List types = ReportType.getReportTypes();   // Getting all the ReportTypes.
        for (Iterator iterator = types.iterator(); iterator.hasNext();) {
            final ReportType reportType = (ReportType) iterator.next();

            final List categories = ReportCategory.getReportCategories();  // Getting all the ReportCategories.
            for (Iterator iterator1 = categories.iterator(); iterator1.hasNext();) {
                final ReportCategory reportCategory = (ReportCategory) iterator1.next();

                // the composite key of type and category
                final String key = reportType + "_" + reportCategory;
                final String baseQuery = getStringPropertyFromConfigManager(QUERIES_CONFIGURATION_NAMESPACE,
                    BASE_QUERY_LOOKUP_KEY_PREFIX + key);
                baseQueries.put(key, baseQuery);
            }
        }
    }

    /**
     * This method looks up a configuration property of type {@link String} from the {@link ConfigManager}.
     *
     * @param namespace A non-<tt>null</tt>, non-empty (trim'd) string representing the {@link ConfigManager} namespace
     *                  in which to lookup the configuration property
     * @param key       A non-<tt>null</tt>, non-empty (trim'd) string representing the name of the property to be
     *                  looked up
     *
     * @return the String value looked up from the {@link ConfigManager}
     *
     * @throws IllegalArgumentException     if namespace or key are empty (trim'd) strings
     * @throws NullPointerException         if namespace or key are <tt>null</tt>
     * @throws ReportConfigurationException in case the property lookup fails, returns <tt>null</tt>
     */
    private static String getStringPropertyFromConfigManager(final String namespace, final String key) throws
        ReportConfigurationException {
        if (namespace == null) {
            throw new NullPointerException("The parameter named [namespace] was null.");
        }
        if (key == null) {
            throw new NullPointerException("The parameter named [key] was null.");
        }
        if (namespace.trim().length() == 0) {
            throw new IllegalArgumentException("The parameter named [namespace] was an empty String.");
        }
        if (key.trim().length() == 0) {
            throw new IllegalArgumentException("The parameter named [key] was an empty String.");
        }

        final Property propertyObject;
        try {
            propertyObject = ConfigManager.getInstance().getPropertyObject(namespace, key);
        } catch (UnknownNamespaceException e) {
            throw new ReportConfigurationException(
                "Unable to find [" + key + "] property, namespace [" + namespace + "] was not found.", e);
        }
        if (propertyObject == null) {
            throw new ReportConfigurationException(
                "The property [" + key + "] did not exist in configuration namespace [" + namespace + "].");
        }
        final String value = propertyObject.getValue();
        if (value == null) {
            throw new ReportConfigurationException(
                "The configuration value for the property [" + key + "] in namespace [" + namespace + "] was null.");
        }
        if (value.trim().length() == 0) {
            throw new ReportConfigurationException("The configuration value for the property [" + key
                + "] in namespace [" + namespace + "] was an empty String.");
        }
        return value;
    }

    /**
     * This method looks up a configuration property of type {@link String} from the {@link ConfigManager}.
     *
     * @param namespace A non-<tt>null</tt>, non-empty (trim'd) string representing the {@link ConfigManager} namespace
     *                  in which to lookup the configuration property
     * @param key       A non-<tt>null</tt>, non-empty (trim'd) string representing the name of the property to be
     *                  looked up
     *
     * @return the String value looked up from the {@link ConfigManager}
     *
     * @throws IllegalArgumentException     if namespace or key are empty (trim'd) strings
     * @throws NullPointerException         if namespace or key are <tt>null</tt>
     * @throws ReportConfigurationException in case the property lookup fails, returns <tt>null</tt>
     */
    private static String getOptionalStringPropertyFromConfigManager(final String namespace, final String key) throws
        ReportConfigurationException {
        if (namespace == null) {
            throw new NullPointerException("The parameter named [namespace] was null.");
        }
        if (key == null) {
            throw new NullPointerException("The parameter named [key] was null.");
        }
        if (namespace.trim().length() == 0) {
            throw new IllegalArgumentException("The parameter named [namespace] was an empty String.");
        }
        if (key.trim().length() == 0) {
            throw new IllegalArgumentException("The parameter named [key] was an empty String.");
        }

        final Property propertyObject;
        try {
            propertyObject = ConfigManager.getInstance().getPropertyObject(namespace, key);
        } catch (UnknownNamespaceException e) {
            throw new ReportConfigurationException(
                "Unable to find [" + key + "] property, namespace [" + namespace + "] was not found.", e);
        }
        if (propertyObject == null) {
            return null;
        }
        final String value = propertyObject.getValue();
        if (value == null) {
            throw new ReportConfigurationException(
                "The configuration value for the property [" + key + "] in namespace [" + namespace + "] was null.");
        }
        if (value.trim().length() == 0) {
            throw new ReportConfigurationException("The configuration value for the property [" + key
                + "] in namespace [" + namespace + "] was an empty String.");
        }
        return value;
    }

    /**
     * This method looks up the {@link ColumnType} defined for the column with the given name.
     *
     * @param colName the name of the column to lookup the type for
     *
     * @return the type of the column with the given name
     *
     * @throws ReportConfigurationException in case there is no data in {@link ConfigManager} for the given columnName
     *                                      or the {@link ConfigManager} information is invalid (e.g. a String not
     *                                      matching any {@link ColumnType})
     */
    private ColumnType lookupColumnType(final String colName) throws ReportConfigurationException {
        final String key = colName + COLUMN_TYPE_LOOKUP_KEY_SUFFIX;
        final String typeName = getStringPropertyFromConfigManager(COLUMN_TYPES_NAMESPACE, key);

        if (typeName == null) {
            throw new ReportConfigurationException("The config value specifying the ColumnType for column [" + colName
                + "]  was null (The offending value was looked up from namespace [" + COLUMN_TYPES_NAMESPACE
                + "], property [" + key + "] ).");
        }
        if (typeName.trim().length() == 0) {
            throw new ReportConfigurationException(
                "The config value specifying the ColumnType for column [" + colName
                    + "] was an empty String (The offending value was looked up from namespace ["
                    + COLUMN_TYPES_NAMESPACE + "], property [" + key + "] ).");
        }

        ColumnType type = ColumnType.DATE;
        final ColumnType ret = (ColumnType) Enum.getEnumByStringValue(typeName, ColumnType.class);

        if (ret == null) {
            throw new ReportConfigurationException("The config value specifying the ColumnType ([" + typeName
                + "]) for column [" + colName
                + "] was not a valid ColumnType name (The offending value was looked up from namespace ["
                + COLUMN_TYPES_NAMESPACE + "], property [" + key + "] ).");
        }
        return ret;
    }

    /**
     * This method splits the given String baseQuery into two parts : the part before the occurrence of separator and
     * the part after the separator. The occurrence of the separator itself is fully eliminated and not part of any of
     * the to given parts.
     *
     * @param baseQuery The string that shall be split
     * @param separator the separator that defines the split location
     *
     * @return a String[] containing exactly two elements, the first element is the part before the separator occurrence
     *         and the second element is the part after the separator
     *
     * @throws ReportConfigurationException in case the split fails because the separator does not occur in the given
     *                                      baseQuery or does occur more than once
     */
    private static String[] splitIntoTwoParts(final String baseQuery, final String separator)
        throws ReportConfigurationException {
        final int endIndexOfFirstPart = baseQuery.indexOf(separator);
        if (endIndexOfFirstPart == -1) {
            throw new ReportConfigurationException(
                "The Query part [" + baseQuery + "] contained no Split token [" + separator + "] occurrence.");
        }

        final int startIndexOfSecondPart = endIndexOfFirstPart + separator.length();
        if (baseQuery.indexOf(separator, startIndexOfSecondPart) != -1) {
            throw new ReportConfigurationException(
                "The Query part [" + baseQuery + "] contained more than one Split token [" + separator
                    + "] occurrence.");
        }

        final String [] ret = new String[2];
        ret[0] = baseQuery.substring(0, endIndexOfFirstPart);
        ret[1] = baseQuery.substring(endIndexOfFirstPart + separator.length());
        return ret;
    }

    /**
     * This method converts the given String value into the target type and then sets the converted value in the given
     * statement at the given position.
     *
     * @param statement The statement into which the value should be inserted
     * @param index     the index (as parameter index in the given statement) to which to assign the converted value
     * @param value     the value to be converted and assigned
     * @param type      the target type of the value as one of the constants defined in {@link ColumnType}
     *
     * @throws SQLException                 in case some operation on the statement fails
     * @throws ReportSQLException           in case the given value cannot be converted into the given target type
     * @throws ReportConfigurationException in case the given {@link ColumnType} is unknown to this method and thus the
     *                                      given String value cannot be converted
     */
    private void setValueInStatement(final PreparedStatement statement, final int index, final String value,
                                     final ColumnType type)
        throws
        SQLException, ReportSQLException, ReportConfigurationException {

        try {
            if (type == ColumnType.INTEGER) {
                statement.setInt(index, Integer.parseInt(value));
            } else if (type == ColumnType.DATE) {
                statement.setDate(index, new Date(dateParser.parse(value).getTime()));
            } else if (type == ColumnType.FLOAT) {
                statement.setObject(index, value, Types.DOUBLE);
            } else if (type == ColumnType.MONEY) {
                statement.setObject(index, statement, Types.DECIMAL);
            } else if (type == ColumnType.SMALLINT) {
                statement.setShort(index, Short.parseShort(value));
            } else if (type == ColumnType.VARCHAR) {
                statement.setString(index, value);
            } else {
                throw new ReportConfigurationException("The given ColumnType [" + type
                    + "] cannot be converted into an Informix JDBC SQL type as it is unknown to InformixDBHandler "
                    + "(maybe the ColumnType enum got a new enum value without adjusting this method).");
            }
        } catch (ParseException e) {
            throw new ReportSQLException(
                "The filter value [" + value + "] could not be converted into a valid  [" + type + "].", e);
        } catch (NumberFormatException e) {
            throw new ReportSQLException(
                "The filter value [" + value + "] could not be converted into a valid  [" + type + "].", e);
        }
    }
}
