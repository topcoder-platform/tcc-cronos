/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 *
 * TCS Template Loader 1.0
 */
package com.topcoder.buildutility.template.persistence;

import com.topcoder.buildutility.template.TemplateHierarchyPersistence;
import com.topcoder.buildutility.template.Template;
import com.topcoder.buildutility.template.ConfigurationException;
import com.topcoder.buildutility.template.PersistenceException;
import com.topcoder.buildutility.template.TemplateHierarchy;
import com.topcoder.buildutility.template.UnknownTemplateHierarchyException;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.UnknownConnectionException;

import com.topcoder.util.config.Property;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import java.text.MessageFormat;
import java.util.ArrayList;

import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

/**
 * <p>An implementation of <code>TemplateHierarchyPersistence</code> interface which may be used to load the top-level
 * template hierarchies from the JDBC-compliant database with schema initialized as specified by the Component
 * Specification.</p>
 *
 * <p>This class utilizes the <code>DB Connection Factory</code> component to obtain the connections to the target
 * database. Therefore a name of such a pre-configured connection must be provided. If such a name is omitted then a
 * default pre-configured connections is used.</p>
 *
 * <p>This class provides a set of constructors which could be used to instantiate the instances of this class via Java
 * Reflection API as specified by the contract for <code>TemplateHierarchyPersistence</code> interface. Also a name of
 * an optional configuration property which must provide the name of pre-configured DB connection is provided by this
 * class.</p>
 *
 * <p>Thread safety: this class is thread safe. The private state is never changed after the instantiation.</p>
 *
 *
 * @author isv
 * @author oldbig
 *
 * @version 1.0
 */
public class JDBCPersistence implements TemplateHierarchyPersistence {


    /**
     * <p>A <code>String</code> providing the name of optional configuration property which (if present) must provide
     * a name of pre-configured DB connection to be used by new instance of <code>JDBCPersistence</code>. If such a
     * property is missing then a default DB connection will be used.</p>
     */
    public static final String DB_CONNECTION_NAME_PROPERTY = "db_connection";

    /**
     * <p>A <code>String</code> providing the name of optional configuration property which (if present) must
     * provide a name of configuration namespace which must be used to initialize the instance of
     * DBConnectionFactroyImpl to be used by new instance of JDBCPersistence. If such a property is missing
     * then a default namespace will be used.</p>
     */
    public static final String DB_FACTORY_NAMESPACE_PROPERTY = "db_factory_namespace";

    /**
     * <p>A <code>String</code> providing the default configuration namespace which must be used by the
     * constructed instance of JDBCPersistence to initialize the DBConnectionFactoryImpl instance if no
     * other namespace have been specified by the client.</p>
     */
    public static final String DEFAULT_DB_FACTORY_NAMESPACE = "com.topcoder.buildutility.template.persistence";

    /**
     * The name of the templates table.
     */
    private static final String TEMPLATES_TABLE = "templates";

    /**
     * The name of the template hierarchy table.
     */
    private static final String TEMP_HIER_TABLE = "temp_hier";

    /**
     * The name of the template hierarchy mapping table.
     */
    private static final String TEMP_HIER_MAPPING_TABLE = "temp_hier_mapping";

    /**
     * The name of the template id column.
     */
    private static final String TEMPLATE_ID_COLUMN = "template_id";

    /**
     * The name of the template name column.
     */
    private static final String TEMPLATE_NAME_COLUMN = "template_name";

    /**
     * The name of the description column.
     */
    private static final String DESCRIPTION_COLUMN = "description";

    /**
     * The name of the uri column.
     */
    private static final String URI_COLUMN = "uri";

    /**
     * The name of the destination file name column.
     */
    private static final String DEST_FILENAME_COLUMN = "dest_filename";

    /**
     * The name of the template hierarchy id column.
     */
    private static final String TEMP_HIER_ID_COLUMN = "temp_hier_id";

    /**
     * The name of the template hierarchy name column.
     */
    private static final String TEMP_HIERL_NAME_COLUMN = "temp_hier_name";

    /**
     * The name of the parent template hierarchy id column.
     */
    private static final String PARENT_TEMP_HIER_ID_COLUMN = "parent_temp_hier_id";

    /**
     * The SQL query used to select top template hierarchy with the given name.
     */
    private static final String SELECT_TOP_HIER_QUERY = MessageFormat.format(
        "SELECT {0}, {1}, {2} FROM {3} WHERE {1} = ? AND {0} = {2}",
        new String[] {TEMP_HIER_ID_COLUMN, TEMP_HIERL_NAME_COLUMN, PARENT_TEMP_HIER_ID_COLUMN, TEMP_HIER_TABLE});

    /**
     * The SQL query used to select the child template hierarchies of the given parents.
     */
    private static final String SELECT_CHILD_HIER_QUERY = MessageFormat.format(
        "SELECT {0}, {1}, {2} FROM {3} WHERE {0} != {2} AND {2} IN ",
        new String[] {TEMP_HIER_ID_COLUMN, TEMP_HIERL_NAME_COLUMN, PARENT_TEMP_HIER_ID_COLUMN, TEMP_HIER_TABLE});

    /**
     * The SQL query used to select the templates by the given parents id.
     */
    private static final String SELECT_TEMPLATES_QUERY = MessageFormat.format(
        "SELECT m.{0}, t.{1}, t.{2}, t.{3}, t.{4}, t.{5} FROM {6} AS t LEFT OUTER JOIN {7} AS m ON m.{1} = t.{1} "
        + "WHERE m.{0} IN ",
        new String[] {TEMP_HIER_ID_COLUMN, TEMPLATE_ID_COLUMN, TEMPLATE_NAME_COLUMN, DESCRIPTION_COLUMN,
                      URI_COLUMN, DEST_FILENAME_COLUMN, TEMPLATES_TABLE, TEMP_HIER_MAPPING_TABLE});

    /**
     * <p>A <code>String</code> specifying the name of DB connection configured within the <code>DB Connection Factory
     * </code> component which must be used by this instance to obtain the connections to target database. This
     * instance field is initialized within the constructors and is never changed during the lifetime of
     * <code>JDBCPersistence</code> instance. This instance field maybe set to <code>null</code>. In such a case,
     * a default DB connection will be used.</p>
     */
    private String connectionName = null;

    /**
     * <p>A <code>DBConnectionFactory</code> which is used by JDBCPersistence to obtain the connections
     * to a database. This instance field is initialized within the constructors and is never changed during
     * the lifetime of JDBCPersistence instance. The 'getTemplateHierarchy' method uses this factory to obtain
     * a connection to a database."</p>
     */
    private DBConnectionFactory connectionFactory = null;

    /**
     * <p>Constructs new JDBCPersistence which will use the default connection to the database as
     * configured within the DB Connection Factory component. The DB Connection Factory will be
     * initialized with the parameters provided by the default configuration namespace.</p>
     *
     * @throws ConfigurationException if the connection factory could not be successfully initialized."
     */
    public JDBCPersistence() throws ConfigurationException {
        initFields(null, DEFAULT_DB_FACTORY_NAMESPACE);
    }

    /**
     * <p>Constructs new <code>JDBCPersistence</code> which is initialized based on the values of configuration
     * properties provided by the specified Property. This implementation expects the "db_connection" subproperty
     * to be provided. This subproperty should provide the name of pre-configured DB connection to be used by new
     * instance. If such a property is missing then a default connection will be used instead. Also the
     * implementation expects the "db_factory_namespace" subproperty to be provided. This subproperty (if present)
     * must provide the configuration namespace to be used to initialize the DB Connection Factory.
     * If such a property is missing then a default namespace will be used instead.</p>
     *
     * @throws NullPointerException if specified argument is <code>null</code>.
     * @throws IllegalArgumentException if specified property contains a "db_connection" or "db_factory_namespace"
     * subproperty which is an empty <code>String</code> being trimmed.
     * @throws ConfigurationException if the connection factory could not be successfully initialized.
     *
     * @param property a <code>Property</code> providing the configuration properties to be used to initialize new
     * instance.
     */
    public JDBCPersistence(Property property) throws ConfigurationException {
        checkNull(property, "property");

        // retrieve the connection name and the namespace from the given property
        String name = property.getValue(DB_CONNECTION_NAME_PROPERTY);
        String namespace = property.getValue(DB_FACTORY_NAMESPACE_PROPERTY);

        // delegate to initFields method
        initFields(name, namespace);
    }

    /**
     * <p>Constructs new <code>JDBCPersistence</code> which will use the specified connection to the database as
     * configured within the specified DBConnectionFactory.</p>
     *
     * @param connectionName a String providing the name of a pre-configured connection to database to be used
     * by this instance.
     * @param dbFactory a DBConnectionFactory to be used by new JDBCPersistence instance to obtain the connections
     * to database.
     * @throws NullPointerException if dbFactory is null.
     * @throws IllegalArgumentException if specified argument is an empty String being trimmed.
     */
    public JDBCPersistence(String connectionName, DBConnectionFactory dbFactory) {
        // validates arguments
        checkEmpty(connectionName, "connectionName");
        checkNull(dbFactory, "dbFactory");

        // initializes the inner fields
        this.connectionName = connectionName;
        this.connectionFactory = dbFactory;
    }

    /**
     * Initializes the connectionName and connectionFactory fields.
     *
     * @param connectionName a String providing the name of a pre-configured connection to database to be used
     * by this instance.
     * @param namespace the configuration namespace to be used to initialize the DB Connection Factory.
     * @throws IllegalArgumentException if connectionName or namespace is an empty <code>String</code> being trimmed.
     * @throws ConfigurationException if the connection factory could not be successfully initialized.
     */
    private void initFields(String connectionName, String namespace) throws ConfigurationException {

        // connectionName cannot be empty string
        checkEmpty(connectionName, "connectionName");
        checkEmpty(namespace, "namespace");

        // initializes the connectionName field
        this.connectionName = connectionName;

        // if the namespace is null, use the default namespace
        if (namespace == null) {
            namespace = DEFAULT_DB_FACTORY_NAMESPACE;
        }

        try {
            // initializes the connectionFactory field
            this.connectionFactory = new DBConnectionFactoryImpl(namespace);
        } catch (UnknownConnectionException uce) {
            throw new ConfigurationException("the connection factory could not be successfully initialized", uce);
        } catch (com.topcoder.db.connectionfactory.ConfigurationException ce) {
            throw new ConfigurationException("the connection factory could not be successfully initialized", ce);
        }
    }

    /**
     * <p>Gets the top-level template hierarchy corresponding to specified name.</p>
     *
     *
     * @throws UnknownTemplateHierarchyException if the specified top-level template
     * hierarchy is not recognized.
     * @throws NullPointerException if specified argument is <code>null</code>.
     * @throws IllegalArgumentException if specified argument is an empty <code>String</code> being trimmed.
     * @throws PersistenceException if any unexpected error occurs.
     *
     * @return a <code>TemplateHierarchy</code> corresponding to specified name.
     * @param name a <code>String</code> specifying the name of the requested top-level template hierarchy.
     */
    public TemplateHierarchy getTemplateHierarchy(String name) throws PersistenceException  {

        // validate the name argument
        checkString(name, "name");

        Connection connection = null;

        // the top level TemplateHierarchy
        TemplateHierarchy root = null;

        try {

            //////////////////////////////////////////////////////////////////////////////////////////////
            // NOTE: This implementation uses H + 2 sql 'select' queries to retrieve all TemplateHierarchies,
            //       where H is the height of the TemplateHierarchy.
            //       H + 1 queries are used to retrieve the H levels(the last query returns nothing)
            //       and another query is used to retrieve all Templates


            // Obtain a connection from connection factory
            if (connectionName == null) {
                connection = connectionFactory.createConnection();
            } else {
                connection = connectionFactory.createConnection(connectionName);
            }

            // get the top level TemplateHierarchy
            root = getTopHierarchy(connection, name);

            // a map used to store all template hierarchies
            // the key is the id, and the value is the TemplateHierarchy itself
            Map allHierarchies = new HashMap();

            // a map used to store current level template hierarchies
            // the key is the id, and the value is the TemplateHierarchy itself
            Map currentLevel = new HashMap();

            // put the root to the current level map
            currentLevel.put(new Long(root.getId()), root);

            //////////////////////////////////////////////////////////
            // STEP 1, build the hierarchies

            // start looping, until the current level is empty
            while (currentLevel.size() > 0) {
                // add the current level TemplateHierarchy instances to allHierarchies map
                allHierarchies.putAll(currentLevel);

                // retrieve a map containing all children of current level template hierarchies
                // the key is the id, and the value is the TemplateHierarchy itself
                Map children = getChildHierarchies(connection, currentLevel.values());

                // for each child, add it to his parent
                for (Iterator itr = children.values().iterator(); itr.hasNext();) {
                    TemplateHierarchy child = (TemplateHierarchy) itr.next();

                    // get the parent
                    TemplateHierarchy parent = (TemplateHierarchy) currentLevel.get(new Long(child.getParentId()));
                    // add to parent
                    parent.addNestedHierarchy(child);

                }

                // replace the currentLevel with the children
                currentLevel = children;
            }

            //////////////////////////////////////////////////////////
            // STEP 2, load all templates, and add them to the hierarchies

            // get a map containing all related templates
            // the key is the template itself, and the value is the id of the parent hierarchy
            Map templates = getAllTemplates(connection, allHierarchies.values());

            // add the templates to
            for (Iterator itr = templates.entrySet().iterator(); itr.hasNext();) {
                Map.Entry entry = (Map.Entry) itr.next();

                // retrieve the template, and the id the its parent hierarchy
                Template template = (Template) entry.getKey();
                Long parentId = (Long) entry.getValue();

                // get the parent hierarchy and add the template
                ((TemplateHierarchy) allHierarchies.get(parentId)).addTemplate(template);
            }

        } catch (UnknownTemplateHierarchyException uthe) {
            // do not wrap the UnknownTemplateHierarchyException
            throw uthe;
        } catch (Exception e) {
            throw new PersistenceException("error occurs while getting the template hierarchy", e);
        } finally {
            // finally, we must close the connection.
            close(connection);
        }

        return root;
    }

    /**
     * <p>Gets the single top-level template hierarchy corresponding to specified name.</p>
     *
     * @param connection the connection to the database.
     * @param name the name of the top level template hierarchy.
     *
     * @throws UnknownTemplateHierarchyException if the specified top-level template
     * hierarchy is not recognized.
     * @throws SQLException if any unexpected error occurs.
     *
     * @return the desired top-level TemplateHierarchy.
     */
    private static TemplateHierarchy getTopHierarchy(Connection connection, String name) throws SQLException {
        PreparedStatement pstatement = null;

        // prepare the sql query used to select the top-level template hierarchy
        pstatement = connection.prepareStatement(SELECT_TOP_HIER_QUERY);
        pstatement.setString(1, name);

        // NOTE: it's the application's responsibility to make sure there is
        // no more than 1 top-level template hierarchy with the same name

        // get a list containing the top-level template hierarchy
        List hierarchies = getHierarchies(pstatement);
        if (hierarchies.size() == 0) {
            throw new UnknownTemplateHierarchyException("the specified top-level template hierarchy does not exist");
        }

        return (TemplateHierarchy) hierarchies.get(0);
    }

    /**
     * <p>Gets a list containing all template hierarchies selected by the given statement.</p>
     *
     * @param pstatement the statement used to select the desired template hierarchies
     *
     * @throws SQLException if any unexpected error occurs.
     *
     * @return a <code>List</code> containing all template hierarchies selected by the given statement.
     */
    private static List getHierarchies(PreparedStatement pstatement) throws SQLException {
        List hierarchies = new ArrayList();
        ResultSet rs = null;
        try {
            // execute this query
            rs = pstatement.executeQuery();

            // for each selected row, create the TemplateHierarchy instance
            while (rs.next()) {

                // get the id, name and parent id, and create the TemplateHierarchy instance
                long id = rs.getLong(TEMP_HIER_ID_COLUMN);
                String name = rs.getString(TEMP_HIERL_NAME_COLUMN);
                long parentId = rs.getLong(PARENT_TEMP_HIER_ID_COLUMN);
                hierarchies.add(new TemplateHierarchy(id, name, parentId));

            }
        } finally {
            // finally, we should close the PreparedStatement and the result set
            close(pstatement);
            close(rs);
        }

        return hierarchies;
    }


    /**
     * <p>Gets a <code>Map</code> containing all children of the given parent template hierarchies.</p>
     *
     * @param connection the connection to the database.
     * @param parents a collection contains all parent hierarchies
     *
     * @throws SQLException if any unexpected error occurs.
     *
     * @return a <code>Map</code> containing all children of the parent hierarchies,
     * the key is the id, and the value is the TemplateHierarchy itself
     */
    private static Map getChildHierarchies(Connection connection, Collection parents) throws SQLException {

        // prepare the sql query used to select the children template hierarchy
        PreparedStatement pstatement = connection.prepareStatement(
            SELECT_CHILD_HIER_QUERY + getIdSet(parents) + " ORDER BY " + PARENT_TEMP_HIER_ID_COLUMN);

        // get a list containing the all child template hierarchies
        List children = getHierarchies(pstatement);

        Map result = new HashMap();
        // for each child, add its id and itself to the map
        for (Iterator itr = children.iterator(); itr.hasNext();) {
            TemplateHierarchy child = (TemplateHierarchy) itr.next();
            result.put(new Long(child.getId()), child);
        }

        return result;

    }

    /**
     * <p>Builds a id set using the given template hierarchies. The id set string is like '(10, 21, 5)'.</p>
     *
     * @param hierarchies a collection contains all hierarchies
     *
     * @return a id set using the given template hierarchies.
     */
    private static String getIdSet(Collection hierarchies) {

        StringBuffer ids = new StringBuffer();
        boolean isFirst = true;
        ids.append("(");
        for (Iterator itr = hierarchies.iterator(); itr.hasNext();) {

            // add a comma if this not the first id
            if (isFirst) {
                isFirst = false;
            } else {
                ids.append(", ");
            }

            // append the TemplateHierarchy id
            ids.append(((TemplateHierarchy) itr.next()).getId());
        }
        ids.append(")");

        return ids.toString();
    }

    /**
     * <p>Gets a <code>Map</code> containing all children of the given parent template hierarchies.</p>
     *
     * @param connection the connection to the database.
     * @param hierarchies a collection contains all parent hierarchies
     *
     * @throws SQLException if any unexpected error occurs.
     *
     * @return a <code>Map</code> containing all children of the parent hierarchies,
     * the key is the id, and the value is the TemplateHierarchy itself
     */
    private static Map getAllTemplates(Connection connection, Collection hierarchies) throws SQLException {

        // prepare the sql query used to select the children template hierarchy
        PreparedStatement pstatement = connection.prepareStatement(SELECT_TEMPLATES_QUERY + getIdSet(hierarchies));

        Map result = new HashMap();
        ResultSet rs = null;
        try {

            // execute the query to select all desired templates
            rs = pstatement.executeQuery();

            // for each selected row, create the TemplateHierarchy instance
            while (rs.next()) {
                // get the parent id
                long parentId = rs.getLong(TEMP_HIER_ID_COLUMN);

                // get the id, name, description, uri, and destFileName
                long id = rs.getLong(TEMPLATE_ID_COLUMN);
                String name = rs.getString(TEMPLATE_NAME_COLUMN);
                String description = rs.getString(DESCRIPTION_COLUMN);
                String uri = rs.getString(URI_COLUMN);
                String destFileName = rs.getString(DEST_FILENAME_COLUMN);

                // put the Template instance and the parent id to result map
                result.put(new Template(id, name, description, uri, destFileName), new Long(parentId));
            }

        } finally {
            close(rs);
            close(pstatement);
        }
        return result;
    }

    /**
     * Checks whether the given Object is null.
     *
     * @param arg the argument to check
     * @param name the name of the argument
     * @throws NullPointerException if the given Object is null
     */
    private static void checkNull(Object arg, String name) {
        if (arg == null) {
            throw new NullPointerException(name + " should not be null.");
        }
    }

    /**
     * Checks whether the given String is empty.
     *
     * @param arg the String to check
     * @param name the name of the argument
     * @throws IllegalArgumentException if the given string is empty
     */
    private static void checkEmpty(String arg, String name) {
        if (arg != null && arg.trim().length() == 0) {
            throw new IllegalArgumentException(name + " should not be empty.");
        }
    }

    /**
     * Checks whether the given String is null or empty.
     *
     * @param arg the String to check
     * @param name the name of the argument
     * @throws NullPointerException if the given string is null
     * @throws IllegalArgumentException if the given string is empty
     */
    private static void checkString(String arg, String name) {
        checkNull(arg, name);
        checkEmpty(arg, name);
    }


    /**
     * Close the ResultSet used in database operation.
     *
     * @param rs the ResultSet of query
     * @throws SQLException if the ResultSet cannot be closed
     */
    static void close(ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
    }

    /**
     * Close the PreparedStatement used in database operation.
     *
     * @param statement the PreparedStatement parameter
     * @throws SQLException if the statement cannot be closed
     */
    static void close(PreparedStatement statement) throws SQLException {
        if (statement != null) {
            statement.close();
        }
    }

    /**
     * Close the connection of database.
     *
     * @param connection the connection of database
     */
    static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException sqle) {
                // ignore it
            }
        }
    }
}
