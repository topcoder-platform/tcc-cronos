/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.accuracytests;

import com.topcoder.timetracker.client.Client;
import com.topcoder.timetracker.project.Project;
import com.topcoder.timetracker.report.informix.ExpenseEntryReport;
import com.topcoder.timetracker.report.informix.ReportEntryBean;
import com.topcoder.timetracker.user.User;

import junit.framework.TestCase;

/**
 * The test of ReportEntryBean.
 *
 * @author brain_cn
 * @version 1.0
 */
public class ReportEntryBeanAccuracyTests extends TestCase {
    /** The tset reportEntry bean for testing. */
    private ReportEntryBean instance;

    /**
     * <p>
     * Setup the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        instance = new ExpenseEntryReport();
    }

    /**
     * <p>
     * Tear down the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
    }

    /**
     * Test method for 'ReportEntryBean()'
     */
    public void testReportEntryBean() {
        assertNotNull("the instance is created", instance);
    }

    /**
     * Test method for 'getClient()'
     */
    public void testGetClient() {
        Client client = new Client();
        instance.setClient(client);
        assertEquals("incorrect client", client, instance.getClient());
    }

    /**
     * Test method for 'setClient(Client)'
     */
    public void testSetClient() {
        Client client = new Client();
        instance.setClient(client);
        assertEquals("incorrect client", client, instance.getClient());

        Client client1 = new Client();
        instance.setClient(client1);
        assertEquals("incorrect client", client1, instance.getClient());
    }

    /**
     * Test method for 'getProject()'
     */
    public void testGetProject() {
        Project project = new Project();
        instance.setProject(project);
        assertEquals("incorrect Project", project, instance.getProject());
    }

    /**
     * Test method for 'setProject(Project)'
     */
    public void testSetProject() {
        Project project = new Project();
        instance.setProject(project);
        assertEquals("incorrect Project", project, instance.getProject());

        Project project1 = new Project();
        instance.setProject(project1);
        assertEquals("incorrect Project", project1, instance.getProject());
    }

    /**
     * Test method for 'getUser()'
     */
    public void testGetUser() {
        User user = new User();
        instance.setUser(user);
        assertEquals("incorrect User", user, instance.getUser());
    }

    /**
     * Test method for 'setUser(User)'
     */
    public void testSetUser() {
        User user = new User();
        instance.setUser(user);
        assertEquals("incorrect User", user, instance.getUser());

        User user1 = new User();
        instance.setUser(user1);
        assertEquals("incorrect User", user1, instance.getUser());
    }
}
