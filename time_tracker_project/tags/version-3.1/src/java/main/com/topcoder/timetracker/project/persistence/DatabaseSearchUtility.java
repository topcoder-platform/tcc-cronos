/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.persistence;

import com.topcoder.timetracker.project.ConfigUtil;
import com.topcoder.timetracker.project.ConfigurationException;
import com.topcoder.timetracker.project.searchfilters.BinaryOperationFilter;
import com.topcoder.timetracker.project.searchfilters.Filter;
import com.topcoder.timetracker.project.searchfilters.MultiValueFilter;
import com.topcoder.timetracker.project.searchfilters.NotFilter;
import com.topcoder.timetracker.project.searchfilters.ValueFilter;

import com.topcoder.util.config.Property;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * This class is used to build the prepared statement for a given search filter. It can be reused by other DB
 * persistence implementations following the SQL standard.
 * </p>
 *
 * <p>
 * It supports configuration via the Configuration Manager to load the fixed part of the SQL query. Here is an example
 * of the configuration file:
 * <pre>
 * &lt;?xml version="1.0"?&gt;
 * &lt;CMConfig&gt;
 *   &lt;Config name="com.topcoder.timetracker.project.persistence.DatabaseSearchUtility.clients"&gt;
 *     &lt;!-- The SQL query template, required --&gt;
 *     &lt;Property name="query_template"&gt;
 *       &lt;Value&gt;select distinct Clients.ClientsID from Clients where &lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;!-- The aliases of the DB column names, required --&gt;
 *     &lt;Property name="column_aliases"&gt;
 *       &lt;!-- Name of a particular property is a name of the alias --&gt;
 *       &lt;!-- Value of a particular property is a value of the alias --&gt;
 *       &lt;Property name="Name"&gt;
 *         &lt;Value&gt;Clients.Name&lt;/Value&gt;
 *       &lt;/Property&gt;
 *       &lt;Property name="Creation User"&gt;
 *         &lt;Value&gt;Clients.CreationUser&lt;/Value&gt;
 *       &lt;/Property&gt;
 *       &lt;Property name="Modification User"&gt;
 *         &lt;Value&gt;Clients.ModificationUser&lt;/Value&gt;
 *       &lt;/Property&gt;
 *       &lt;Property name="Creation Date"&gt;
 *         &lt;Value&gt;Clients.CreationDate&lt;/Value&gt;
 *       &lt;/Property&gt;
 *       &lt;Property name="Modification Date"&gt;
 *         &lt;Value&gt;Clients.ModificationDate&lt;/Value&gt;
 *       &lt;/Property&gt;
 *     &lt;/Property&gt;
 *   &lt;/Config&gt;
 * &lt;/CMConfig&gt;
 * </pre>
 * Note that the query template must select the id of the entity, e.g. client id for searching clients, project id for
 * searching projects.
 * </p>
 *
 * <p>
 * This class is mutable, i.e. not thread-safe. However it should be used by the persistence implementations in a
 * thread-safe manner.
 * </p>
 *
 * @author real_vg, TCSDEVELOPER
 * @version 1.1
 */
public final class DatabaseSearchUtility {
    /**
     * <p>
     * Constant for the property specifying the query template. It is the SQL statement with unfinished WHERE clause.
     * </p>
     */
    private static final String QUERY_TEMPLATE_PROPERTY = "query_template";

    /**
     * <p>
     * Constant for the property specifying the column aliases. It contains subproperties specifying the mappings from
     * column aliases to the real names of columns of the DB table.
     * </p>
     */
    private static final String COLUMN_ALIASES_PROPERTY = "column_aliases";

    /**
     * <p>
     * The database connection to be used to generate the prepared statements. It is initialized in constructor, cannot
     * be null. The reference is not changed after initialization, but the state of Connection can be modified from
     * the outside.
     * </p>
     */
    private final Connection connection;

    /**
     * <p>
     * The template of the SQL query to be used for search. It is the SQL statement with unfinished WHERE clause. The
     * conditions will be appended according to the search filter. It is initialized in constructor (loaded from the
     * configuration) and then never changed. It cannot be null or empty string.
     * </p>
     */
    private final String queryTemplate;

    /**
     * <p>
     * The mappings from column aliases (String) to the real names of columns of the DB table (String). It is
     * initialized in constructor (loaded from the configuration) and then never changed. It cannot be null. Both keys
     * and values should be non-empty String instances.
     * </p>
     */
    private final Map columnAliases;

    /**
     * <p>
     * Creates an instance of the class. Loads the query template and column aliases under the specified namespace.
     * </p>
     *
     * @param connection the database connection to be used to generate the prepared statements.
     * @param namespace the configuration namespace to use.
     *
     * @throws IllegalArgumentException if any of the parameters is null, or namespace is empty string.
     * @throws ConfigurationException if any error happens when loading the configuration.
     */
    public DatabaseSearchUtility(Connection connection, String namespace)
        throws ConfigurationException {
        if (connection == null) {
            throw new IllegalArgumentException("connection is null");
        }
        DBUtil.checkStringIAE(namespace, "namespace");

        this.connection = connection;
        columnAliases = new HashMap();

        // read the property for query template
        Property root = ConfigUtil.getRootProperty(namespace);

        queryTemplate = ConfigUtil.getPropertyValue(root, QUERY_TEMPLATE_PROPERTY);

        // read the subproperties for column aliases
        Property property = ConfigUtil.getProperty(root, COLUMN_ALIASES_PROPERTY);

        for (Enumeration e = property.propertyNames(); e.hasMoreElements();) {
            String name = (String) e.nextElement();

            if (name.trim().length() == 0) {
                throw new ConfigurationException(COLUMN_ALIASES_PROPERTY + " property contains empty property name");
            }

            // add the column aliases to the map
            columnAliases.put(name, ConfigUtil.getPropertyValue(property, name));
        }
    }

    /**
     * <p>
     * Prepares the SQL statement representing the search query for the given search filter. The SQL query is built
     * from two parts: the template of the SQL query, and the search condition represented by the specified filter.
     * The PreparedStatement instance is returned, with all the parameters set with values.
     * </p>
     *
     * @param searchFilter the search filter to apply
     *
     * @return the PreparedStatement representing the search query with all the parameters set with values.
     *
     * @throws IllegalArgumentException if the searchFilter is null, or the searchFilter is not supported, or no column
     *         name is found for the column alias.
     * @throws PersistenceException if any SQL error occurs.
     */
    public PreparedStatement prepareSearchStatement(Filter searchFilter)
        throws PersistenceException {
        if (searchFilter == null) {
            throw new IllegalArgumentException("searchFilter is null");
        }

        // build the SQL query string
        StringBuffer buffer = new StringBuffer(queryTemplate);
        List parameterValues = new ArrayList();

        buildCondition(searchFilter, buffer, parameterValues);

        PreparedStatement pstmt = null;

        try {
            // generate the prepared statement
            pstmt = connection.prepareStatement(buffer.toString());

            int parameterIndex = 1;

            // set the parameters of prepared statement
            for (Iterator i = parameterValues.iterator(); i.hasNext();) {
                pstmt.setObject(parameterIndex++, i.next());
            }
            return pstmt;
        } catch (SQLException e) {
            DBUtil.close(pstmt);
            throw new PersistenceException("Fails to prepare SQL statement", e);
        }
    }

    /**
     * <p>
     * Recursively builds a condition string (for SQL WHERE clause) from the specified search filter. Also populates
     * the list of values with the SQL query parameters.
     * </p>
     *
     * @param searchFilter the search filter to build condition string from.
     * @param buffer the StringBuffer where the output condition string is stored.
     * @param parameterValues the List where the values of parameters of the SQL query are added.
     *
     * @throws IllegalArgumentException if the searchFilter is not supported, or no column name is found for the column
     *         alias.
     */
    private void buildCondition(Filter searchFilter, StringBuffer buffer, List parameterValues) {
        if (searchFilter instanceof ValueFilter) {
            ValueFilter filter = (ValueFilter) searchFilter;

            // get the column name of the DB table from the alias
            String columnName = getColumnName(filter.getFieldName());

            // build the SQL query with compare operation
            buffer.append("(");
            buffer.append(columnName);
            buffer.append(" ");
            buffer.append(filter.getOperation().toString());
            buffer.append(" ?)");

            // add the parameter value to the list
            parameterValues.add(filter.getValue());
        } else if (searchFilter instanceof MultiValueFilter) {
            MultiValueFilter filter = (MultiValueFilter) searchFilter;

            // get the column name of the DB table from the alias
            String columnName = getColumnName(filter.getFieldName());

            // build the SQL query with "IN" predicate
            buffer.append("(");
            buffer.append(columnName);
            buffer.append(" IN (");

            // add the parameter values to the list at the same time
            Object[] values = filter.getValues();

            for (int i = 0; i < values.length; i++) {
                buffer.append("?,");
                parameterValues.add(values[i]);
            }

            // remove the last comma
            buffer.setLength(buffer.length() - 1);
            buffer.append("))");
        } else if (searchFilter instanceof BinaryOperationFilter) {
            BinaryOperationFilter filter = (BinaryOperationFilter) searchFilter;

            // build the SQL query with binary operation, the left and right operands
            // generate the SQL query and add parameter values recursively
            buffer.append("(");
            buildCondition(filter.getLeftOperand(), buffer, parameterValues);
            buffer.append(filter.getOperation().toString());
            buildCondition(filter.getRightOperand(), buffer, parameterValues);
            buffer.append(")");
        } else if (searchFilter instanceof NotFilter) {
            NotFilter filter = (NotFilter) searchFilter;

            // build the SQL query with "NOT" operation, the operand
            // generates the SQL query and adds parameter value recursively
            buffer.append("(NOT");
            buildCondition(filter.getOperand(), buffer, parameterValues);
            buffer.append(")");
        } else {
            throw new IllegalArgumentException("searchFilter is not supported");
        }
    }

    /**
     * <p>
     * Gets the column name for the given column alias. This is based on the mappings of column aliases initialized in
     * the constructor.
     * </p>
     *
     * @param columnAlias the column alias to look up.
     *
     * @return the real name of column of the DB table.
     *
     * @throws IllegalArgumentException if no column name is found for the column alias.
     */
    private String getColumnName(String columnAlias) {
        String columnName = (String) columnAliases.get(columnAlias);

        if (columnName == null) {
            throw new IllegalArgumentException("No column name found for column alias " + columnAlias);
        }
        return columnName;
    }
}
