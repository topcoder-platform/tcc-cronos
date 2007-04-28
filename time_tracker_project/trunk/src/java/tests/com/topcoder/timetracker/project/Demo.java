/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.project.ejb.MockUserTransaction;
import com.topcoder.timetracker.project.ejb.ProjectManagerUtilityDelegate;
import com.topcoder.timetracker.project.ejb.ProjectManagerUtilityLocal;
import com.topcoder.timetracker.project.ejb.ProjectManagerUtilityLocalHome;
import com.topcoder.timetracker.project.ejb.ProjectManagerUtilitySessionBean;
import com.topcoder.timetracker.project.ejb.ProjectUtilityDelegate;
import com.topcoder.timetracker.project.ejb.ProjectUtilityLocal;
import com.topcoder.timetracker.project.ejb.ProjectUtilityLocalHome;
import com.topcoder.timetracker.project.ejb.ProjectUtilitySessionBean;
import com.topcoder.timetracker.project.ejb.ProjectWorkerUtilityDelegate;
import com.topcoder.timetracker.project.ejb.ProjectWorkerUtilityLocal;
import com.topcoder.timetracker.project.ejb.ProjectWorkerUtilityLocalHome;
import com.topcoder.timetracker.project.ejb.ProjectWorkerUtilitySessionBean;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * The unit test class is used for component demonstration.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class Demo extends TestCase {
    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE);
        TestHelper.loadXMLConfig(TestHelper.SEARCH_CONFIG_FILE);
        TestHelper.setUpDataBase();

        /* We need to set MockContextFactory as our JNDI provider.
         * This method sets the necessary system properties.
         */
        MockContextFactory.setAsInitial();

        // create the initial context that will be used for binding EJBs
        Context context = new InitialContext();

        // Create an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        // we use MockTransaction outside of the app server
        MockUserTransaction mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);

        // Deploy ProjectManagerUtilitySessionBean
        ProjectManagerUtilitySessionBean managerBean = new ProjectManagerUtilitySessionBean();
        SessionBeanDescriptor sampleServiceDescriptor = new SessionBeanDescriptor("projectManagerDelegateLocalHome",
            ProjectManagerUtilityLocalHome.class, ProjectManagerUtilityLocal.class, managerBean);
        mockContainer.deploy(sampleServiceDescriptor);

        // Deploy ProjectWorkerUtilitySessionBean
        ProjectWorkerUtilitySessionBean workerBean = new ProjectWorkerUtilitySessionBean();
        sampleServiceDescriptor = new SessionBeanDescriptor("projectWorkerDelegateLocalHome",
            ProjectWorkerUtilityLocalHome.class, ProjectWorkerUtilityLocal.class, workerBean);
        mockContainer.deploy(sampleServiceDescriptor);

        // Deploy ProjectUtilitySessionBean
        ProjectUtilitySessionBean projectBean = new ProjectUtilitySessionBean();
        sampleServiceDescriptor = new SessionBeanDescriptor("projectDelegateLocalHome", ProjectUtilityLocalHome.class,
            ProjectUtilityLocal.class, projectBean);
        mockContainer.deploy(sampleServiceDescriptor);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        TestHelper.tearDownDataBase();
        TestHelper.clearConfig();
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(Demo.class);
    }

    /**
     * <p>
     * This test case demonstrates the usage of this component.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDemo() throws Exception {
        Date startDate = new Date();
        Date endDate = new Date(System.currentTimeMillis() + 1000000);

        // Create a ProjectUtility Delegate
        ProjectUtility projectUtil = new ProjectUtilityDelegate(ProjectUtilityDelegate.class.getName());

        // Create a new Project.
        Project project = new Project();
        project.setCreationUser("TCUSER");
        project.setModificationUser("TCUSER");
        project.setName("Time Tracker Project");
        project.setDescription("Topcoder Component Competition");
        project.setCompanyId(1);
        project.setClientId(1);
        project.setSalesTax(6.5);
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setContact(TestHelper.createTestingContact());
        project.setAddress(TestHelper.createTestingAddress());
        project.setTerms(TestHelper.createTestingPaymentTerm());

        // Store it in persistence, with auditing
        projectUtil.addProject(project, true);

        // Search for a Project that started in a specified date range, and whose manager has a userId of 50.
        ProjectFilterFactory factory = projectUtil.getProjectFilterFactory();
        Filter filter1 = factory.createContainsProjectManagerFilter(50);
        Filter filter2 = factory.createCompanyIdFilter(1);
        Filter andFilter = new AndFilter(filter1, filter2);

        Project[] searchResults = projectUtil.searchProjects(andFilter);

        // Associate a Time Entry with the Project (no auditing)
        projectUtil.addEntryToProject(project.getId(), 1, EntryType.EXPENSE_ENTRY, false);

        // Add a new ProjectManager to a project.

        // First we need a ProjectManagerUtility Delegate
        ProjectManagerUtility projectManagerUtil = new ProjectManagerUtilityDelegate(
            ProjectManagerUtilityDelegate.class.getName());

        // Create and define a new ProjectManager
        ProjectManager projectManager = new ProjectManager();
        projectManager.setCreationUser("TCUSER");
        projectManager.setModificationUser("TCUSER");
        projectManager.setUserId(1);
        projectManager.setProjectId(project.getId());

        // Store the defined ProjectManager
        projectManagerUtil.addProjectManager(projectManager, true);

        // Double the pay rate of all ProjectWorkers that are working on Time Tracker Project

        // First we need a ProjectWorkerUtilityDelegate
        ProjectWorkerUtility projectWorkerUtil = new ProjectWorkerUtilityDelegate(
            ProjectWorkerUtilityDelegate.class.getName());
        ProjectWorker worker = TestHelper.createTestingProjectWorker(null);
        worker.setProjectId(project.getId());
        projectWorkerUtil.addProjectWorker(worker, false);

        // Retrieve the filter factory and search for all workers associated with time tracker project.
        ProjectWorkerFilterFactory filterFactory = projectWorkerUtil.getProjectWorkerFilterFactory();
        Filter criterion = filterFactory.createProjectIdFilter(project.getId());

        // Perform the search
        ProjectWorker[] timeTrackerWorkers = projectWorkerUtil.searchProjectWorkers(criterion);

        // Double the pay rate of the workers
        for (int x = 0; x < timeTrackerWorkers.length; x++) {
            timeTrackerWorkers[x].setPayRate(timeTrackerWorkers[x].getPayRate() * 2);
        }

        // Perform a batch update on the workers, with audit.
        projectWorkerUtil.updateProjectWorkers(timeTrackerWorkers, false);
    }
}
