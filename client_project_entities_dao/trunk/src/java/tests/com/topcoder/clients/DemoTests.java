/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients;

import java.util.List;

import javax.naming.NamingException;
import javax.persistence.Query;

import com.topcoder.clients.dao.ejb3.ProjectDAOBean;
import com.topcoder.clients.dao.ejb3.ProjectDAORemote;
import com.topcoder.clients.dao.ejb3.TestBase;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

/**
 * The demo of this component.
 * @author Mafy, TCSDEVELOPER
 * @version 1.2
 */
public class DemoTests extends TestBase {
    /**
     * This is demo for this component. For other entities and entity dao are almost the same as
     * Project, the usage of other entities is not showed.
     * @throws Exception to JUnit
     */
    public void testDemo() throws Exception {
        // prepare data
        Client client = createClient(200);
        Project project = createProjectWithClient(100, client);
        createProjectWithClient(101, client);
        createProjectWithClient(102, client);
        createProjectWithClient(103, client);
        // getEntityManager().getTransaction().commit();

        // retrieve bean
        ProjectDAORemote bean = getBean();

        Filter filter = new EqualToFilter("projectStatus", project.getProjectStatus().getId());

        List < Project > projects;

        // get project for corresponding id
        Project tempProject = bean.retrieveById(100L);

        // get all projects
        projects = bean.retrieveAll();

        // get all projects with the name "name"
        projects = bean.searchByName("name");

        // get all that match the given filter
        // projects = bean.search(filter);

        // save or update a project
        bean.save(project);

        // delete the project
        // bean.delete(project);

        // get project for corresponding id without projectChildren
        tempProject = bean.retrieveById(100L, false);

        // get project for corresponding id with projectChildren
        tempProject = bean.retrieveById(100L, true);

        // get all projects without projectChildrens
        projects = bean.retrieveAll(false);

        // get all projects with projectChildrens
        projects = bean.retrieveAll(true);

        // get projects by user
        projects = bean.getProjectsByUser("username");

        // get all projects only
        projects = bean.retrieveAllProjectsOnly();

        // search projects by project name
        projects = bean.searchProjectsByProjectName("projectname");

        // search projects by client name
        projects = bean.searchProjectsByClientName("clientname");

        // get contest fees by project
        List < ProjectContestFee > fees = bean.getContestFeesByProject(100L);

        // save contest fees
        bean.saveContestFees(fees, 100L);

        // check client project permission
        boolean clientProjectPermission = bean.checkClientProjectPermission("username", 100L);

        // check po number permission
        boolean poNumberPermission = bean.checkPoNumberPermission("username", "123456A");

        // add user to billing projects.
        createCompany(1);
        bean.addUserToBillingProjects("username", new long[] {100, 101, 102 });

        // remove user from billing projects.
        bean.removeUserFromBillingProjects("ivern", new long[] {100, 201 });

        // get the projects by the given client id.
        projects = bean.getProjectsByClientId(200);
    }

    /**
     * This is demo for Version 1.2.
     * @throws Exception to JUnit
     * @since 1.2
     */
    public void testDemo2_updateProjectBudget() throws Exception {
        // prepare data
        // create a client
        Client client = createClient(1);

        // create project with budget 4000.
        int projectId = 100;
        setInitialBudget(new Double(4000));
        createProjectWithClient(projectId, client);

        // retrieve bean
        ProjectDAORemote bean = getBean();

        // Suppose project table has the following record:
        // project_id budget
        // 100 4000

        // Increase the budget of project with id 100 with 500.
        double newBudget = bean.updateProjectBudget("ivern", 100, 500);

        /*
         * 4500 should be returned and the budget for id 100 will be updated to 4500, besides, a
         * record will be added into project_budget_audit table with project_id = 100,
         * changed_amount = 500 and creation_user="ivern".
         */
    }

    /**
     * This is demo for Version 1.2.
     * @throws Exception to JUnit
     * @since 1.2
     */
    public void testDemo2_getUsersByProject() throws Exception {
        // prepare data
        prepareDataForDemo2();

        // retrieve bean
        ProjectDAORemote bean = getBean();

        // Suppose user_account table has the following records:
        //
        // user_account_id user_name
        // 101 user1
        // 102 user2
        // 103 user3
        // 104 user4
        // 105 user5
        //
        // and project_manager table has the following records:
        //
        // active project_id user_account_id
        // 1 123 101
        // 0 123 102
        //
        // and project_worker table has the following records(suppose for all records the current
        // date is between start date and end date):
        //
        // active project_id user_account_id
        // 1 123 104
        // 0 123 105
        // Now, get the user names who have access of the billing project with id 123.
        List < String > users = bean.getUsersByProject(123);

        // users list should contain 2 elements("user1" and "user4").
    }

    /**
     * Prepare data for demo version 1.2.
     * @since 1.2
     */
    private void prepareDataForDemo2() {
        // create a client
        Client client = createClient(1);

        // create a project.
        int projectId = 123;
        createProjectWithClient(projectId, client);

        // create a company for User foreign key
        int companyId = 1;
        createCompany(companyId);

        Query query;
        // create User accounts, user1-user5
        for (int i = 1; i <= 5; i++) {
            String insertUser =
                "insert into user_account (user_account_id, company_id, account_status_id, "
                    + "user_name, password) values(:userAccountId, :companyId,"
                    + " :accountStatusId, :userName, :userName)";
            query = getEntityManager().createNativeQuery(insertUser);
            query.setParameter("userAccountId", 100 + i);
            query.setParameter("companyId", companyId);
            query.setParameter("accountStatusId", 1);
            query.setParameter("userName", "user" + i);
            query.executeUpdate();
        }

        // create project manager table
        String insertProjectManager =
            "insert into project_manager( project_id, user_account_id, cost, active)"
                + "values(:projectId, :userAccountId, 0, :active)";
        query = getEntityManager().createNativeQuery(insertProjectManager);
        query.setParameter("projectId", projectId);
        query.setParameter("userAccountId", 101);
        query.setParameter("active", 1);
        query.executeUpdate();

        query.setParameter("active", 0);
        query.setParameter("userAccountId", 102);
        query.executeUpdate();

        // create project worker table
        String insertProjectWorker =
            "insert into project_worker(project_id,user_account_id,"
                + "start_date,end_date,cost,active,creation_date,creation_user,"
                + "modification_date,modification_user) values(:projectId,:userAccountId,"
                + ":date1, :date2, 0, :active, :date1, 'x', :date1, 'x')";
        query = getEntityManager().createNativeQuery(insertProjectWorker);
        query.setParameter("projectId", projectId);
        query.setParameter("date1", "2000-01-01 01:01:01");
        query.setParameter("date2", "2100-01-01 01:01:01");

        query.setParameter("userAccountId", 104);
        query.setParameter("active", 1);
        query.executeUpdate();

        query.setParameter("userAccountId", 105);
        query.setParameter("active", 0);
        query.executeUpdate();
    }

    /**
     * create the ProjectDAO bean.
     * @return the bean created.
     * @since 1.2
     */
    private ProjectDAORemote getBean() {
        // Sorry, the following doesn't work, so I use this mock way:)

        // InitialContext ctx = new InitialContext();
        // ProjectDAORemote bean = (ProjectDAORemote)
        // ctx.lookup("client_project_entities_dao/ProjectDAOBean/remote");
        ProjectDAOBean bean = new ProjectDAOBean();
        bean.setEntityManager(getEntityManager());
        return bean;
    }
}
