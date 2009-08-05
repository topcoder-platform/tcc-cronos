/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao;

import java.util.List;

import javax.naming.InitialContext;

import com.topcoder.clients.dao.ejb3.ProjectDAORemote;
import com.topcoder.clients.dao.ejb3.TestBase;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

/**
 * The demo of this component.
 *
 * @author Mafy, TCSDEVELOPER
 * @version 1.0
 */
public class DemoTests extends TestBase {
    /**
     * This is demo for this component. For other entities and entity dao are
     * almost the same as Project, the usage of other entities is not showed.
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testDemo() throws Exception {
        // prepare data
        Client client = createClient(100);
        Project project = createProjectWithClient(200, client);
        createProjectWithClient(201, client);
        createProjectWithClient(202, client);
        createProjectWithClient(203, client);
        getEntityManager().getTransaction().commit();

        // retrieve bean
        InitialContext ctx = new InitialContext();
        ProjectDAORemote bean = (ProjectDAORemote) ctx
                .lookup("client_project_entities_dao/ProjectDAOBean/remote");

        Filter filter = new EqualToFilter("projectStatus", project
                .getProjectStatus().getId());

        List<Project> projects;

        // get project for corresponding id
        Project tempProject = bean.retrieveById(100L);

        // get all projects
        projects = bean.retrieveAll();

        // get all projects with the name "name"
        projects = bean.searchByName("name");

        // get all that match the given filter
        projects = bean.search(filter);

        // save or update a project
        bean.save(project);

        // delete the project
        bean.delete(project);

        // get project for corresponding id without projectChildren
        tempProject = bean.retrieveById(100L, false);

        // get project for corresponding id with projectChildren
        tempProject = bean.retrieveById(100L, true);

        // get all projects without projectChildrens
        projects = bean.retrieveAll(false);

        // get all projects with projectChildrens
        projects = bean.retrieveAll(true);
    }

}
