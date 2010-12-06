/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence;

import java.sql.Connection;
import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectPersistence;
import com.topcoder.management.project.ProjectPropertyType;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.util.config.ConfigManager;

/**
 * This TestCase demonstrates the usage of this component.
 * @author urtks
 * @version 1.0
 */
public class DemoTest extends TestCase {
    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(DemoTest.class);
    }

    /**
     * Sets up the test environment. The configuration will be loaded.
     * @throws Exception
     *             throw any exception to JUnit
     */
    protected void setUp() throws Exception {
        tearDown();

        ConfigManager cm = ConfigManager.getInstance();

        // load the configurations for db connection factory
        cm.add("dbfactory.xml");

        // load the configurations for InformixProjectPersistence
        cm.add("informix_persistence.xml");

        // create the connection
        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();
        conn.setAutoCommit(false);

        // insert data into project_type_lu table
        Helper.doDMLQuery(conn, "INSERT INTO project_type_lu "
            + "(project_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (1, 'Topcoder', 'Topcoder Component', "
            + "'topcoder', CURRENT, 'topcoder', CURRENT)", new Object[] {});
        Helper.doDMLQuery(conn, "INSERT INTO project_type_lu "
            + "(project_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (2, 'Customer', 'Customer Component', "
            + "'topcoder', CURRENT, 'topcoder', CURRENT)", new Object[] {});

        // insert data into project_category_lu table
        Helper.doDMLQuery(conn, "INSERT INTO project_category_lu "
            + "(project_category_id, project_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (1, 1, '.Net', '.NET Component', "
            + "'topcoder', CURRENT, 'topcoder', CURRENT)", new Object[] {});
        Helper.doDMLQuery(conn, "INSERT INTO project_category_lu "
            + "(project_category_id, project_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (2, 1, 'Java', 'JAVA Component', "
            + "'topcoder', CURRENT, 'topcoder', CURRENT)", new Object[] {});
        Helper.doDMLQuery(conn, "INSERT INTO project_category_lu "
            + "(project_category_id, project_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (3, 2, 'Customer .Net', 'Customer .NET Component', "
            + "'topcoder', CURRENT, 'topcoder', CURRENT)", new Object[] {});
        Helper.doDMLQuery(conn, "INSERT INTO project_category_lu "
            + "(project_category_id, project_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (4, 2, 'Customer Java', 'Customer JAVA Component', "
            + "'topcoder', CURRENT, 'topcoder', CURRENT)", new Object[] {});

        // insert data into project_info_type_lu table
        Helper.doDMLQuery(conn, "INSERT INTO project_info_type_lu "
            + "(project_info_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (1, 'property 1', 'project property 1', "
            + "'topcoder', CURRENT, 'topcoder', CURRENT)", new Object[] {});
        Helper.doDMLQuery(conn, "INSERT INTO project_info_type_lu "
            + "(project_info_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (2, 'property 2', 'project property 2', "
            + "'topcoder', CURRENT, 'topcoder', CURRENT)", new Object[] {});
        Helper.doDMLQuery(conn, "INSERT INTO project_info_type_lu "
            + "(project_info_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (3, 'property 3', 'project property 3', "
            + "'topcoder', CURRENT, 'topcoder', CURRENT)", new Object[] {});
        Helper.doDMLQuery(conn, "INSERT INTO project_info_type_lu "
            + "(project_info_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (4, 'property 4', 'project property 4', "
            + "'topcoder', CURRENT, 'topcoder', CURRENT)", new Object[] {});

        conn.commit();
        conn.close();
    }

    /**
     * Clean up the test environment. The configuration will be unloaded.
     * @throws Exception
     *             throw any exception to JUnit
     */
    protected void tearDown() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        Iterator it = cm.getAllNamespaces();
        while (it.hasNext()) {
            cm.removeNamespace((String) it.next());
        }

        // load the configurations for db connection factory
        cm.add("dbfactory.xml");

        // create the connection
        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();
        conn.setAutoCommit(false);

        // clear the tables
        Helper.doDMLQuery(conn, "DELETE FROM project_audit", new Object[] {});
        Helper.doDMLQuery(conn, "DELETE FROM project_info", new Object[] {});
        Helper.doDMLQuery(conn, "DELETE FROM project_info_type_lu", new Object[] {});
        Helper.doDMLQuery(conn, "DELETE FROM project", new Object[] {});
        Helper.doDMLQuery(conn, "DELETE FROM project_status_lu", new Object[] {});
        Helper.doDMLQuery(conn, "DELETE FROM project_category_lu", new Object[] {});
        Helper.doDMLQuery(conn, "DELETE FROM project_type_lu", new Object[] {});

        // insert data into project_status_lu table
        Helper.doDMLQuery(conn,
            "INSERT INTO project_status_lu (project_status_id, name, description, "
                + "create_user, create_date, modify_user, modify_date) "
                + "VALUES (1, 'Active', 'Active', 'System', CURRENT, 'System', CURRENT)",
            new Object[] {});
        Helper.doDMLQuery(conn,
            "INSERT INTO project_status_lu(project_status_id, name, description, "
                + "create_user, create_date, modify_user, modify_date) "
                + "VALUES (2, 'Inactive', 'Inactive', 'System', CURRENT, 'System', CURRENT)",
            new Object[] {});
        Helper.doDMLQuery(conn,
            "INSERT INTO project_status_lu(project_status_id, name, description, "
                + "create_user, create_date, modify_user, modify_date) "
                + "VALUES (3, 'Deleted', 'Deleted', 'System', CURRENT, 'System', CURRENT)",
            new Object[] {});
        Helper.doDMLQuery(conn,
            "INSERT INTO project_status_lu(project_status_id, name, description, "
                + "create_user, create_date, modify_user, modify_date) "
                + "VALUES (4, 'Cancelled - Failed Review', 'Cancelled - Failed Review', "
                + "'System', CURRENT, 'System', CURRENT)", new Object[] {});
        Helper.doDMLQuery(conn,
            "INSERT INTO project_status_lu(project_status_id, name, description, "
                + "create_user, create_date, modify_user, modify_date) "
                + "VALUES (5, 'Cancelled - Failed Screening', 'Cancelled - Failed Screening', "
                + "'System', CURRENT, 'System', CURRENT)", new Object[] {});
        Helper.doDMLQuery(conn,
            "INSERT INTO project_status_lu(project_status_id, name, description, "
                + "create_user, create_date, modify_user, modify_date)"
                + "VALUES (6, 'Cancelled - Zero Submissions', 'Cancelled - Zero Submissions', "
                + "'System', CURRENT, 'System', CURRENT)", new Object[] {});
        Helper.doDMLQuery(conn,
            "INSERT INTO project_status_lu(project_status_id, name, description, "
                + "create_user, create_date, modify_user, modify_date) "
                + "VALUES (7, 'Completed', 'Completed', 'System', CURRENT, 'System', CURRENT)",
            new Object[] {});

        conn.commit();
        conn.close();

        it = cm.getAllNamespaces();
        while (it.hasNext()) {
            cm.removeNamespace((String) it.next());
        }
    }

    /**
     * This method demonstrates the method to create a
     * InformixProjectPersistence.
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testCreation() throws Exception {
        // create the instance with the given namespace
        ProjectPersistence persistence = new InformixProjectPersistence(
            "InformixProjectPersistence.CustomNamespace");
        
        ((InformixProjectPersistence)persistence).setUserManualCommit(true);
        
    }

    /**
     * This method demonstrates the management of projects.
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testManageProject() throws Exception {
        // first create an instance of InformixProjectPersistence class
        ProjectPersistence persistence = new InformixProjectPersistence(
            "InformixProjectPersistence.CustomNamespace");
        
        ((InformixProjectPersistence)persistence).setUserManualCommit(true);
        
        // get all project categories from the persistence
        ProjectCategory[] projectCategories = persistence.getAllProjectCategories();

        // get all project statuses from the persistence
        ProjectStatus[] projectStatuses = persistence.getAllProjectStatuses();

        // project types can also be retrieved from the persistence, each
        // project type can contains 0-n project category.
        ProjectType[] projectTypes = persistence.getAllProjectTypes();

        // get all project property types from the persistence.
        ProjectPropertyType[] propertyTypes = persistence.getAllProjectPropertyTypes();

        // create the project using a project category and a project status. The
        // project id will be zero, which mean it isn't persisted
        Project project = new Project(projectCategories[0], projectStatuses[0]);

        // persist the project instance with the operator name "admin"
        // the operator will be the creation/modification of this project
        // instance
        persistence.createProject(project, "admin");

        // after creation, new values will be set to the project instance such
        // as project id, creation user, creation date, modification user,
        // modification date.

        // set new project category to the project instance
        project.setProjectCategory(projectCategories[1]);

        // update project with the operator "programmer"
        persistence.updateProject(project, "update reason", "programmer");

        // finally, we can get the project from the persistence.
        Project project1 = persistence.getProject(1);

        // we can also get a set of projects from the persistence in one method.
        Project[] projects = persistence.getProjects(new long[] {1, 2, 3, 4});
    }
}
