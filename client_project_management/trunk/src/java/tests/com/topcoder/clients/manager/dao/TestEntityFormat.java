/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.dao;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;

import junit.framework.TestCase;

/**
 * Test all entity format methods in the Helper class.
 *
 * @author Chenhong
 * @version 1.0
 *
 */
public class TestEntityFormat extends TestCase {

    /**
     * Test method for 'Helper.formatClient(Client)'.
     * <p>
     * Format a null client.
     * </p>
     */
    public void testFormatClient() {
        String ret = Helper.formatClient(null);

        assertEquals("Equal to 'null'", "null", ret);
    }

    /**
     * Test method for 'Helper.formatClient(Client)'.
     * <p>
     * Format a client. Print to console to see if format is correct.
     * </p>
     */
    public void testFormatClient_2() {
        Client client = new Client();

        Company c = new Company();
        c.setDeleted(false);
        c.setPasscode("passcode");
        c.setName("company");
        c.setId(1L);

        ClientStatus s = new ClientStatus();
        s.setDeleted(false);
        s.setDescription("d");
        s.setName("status");
        s.setId(10L);

        client.setClientStatus(s);

        client.setCodeName("code");
        client.setCompany(c);
        client.setId(1L);
        client.setName("client");

        client.setSalesTax(10.999);

        String ret = Helper.formatClient(client);

        System.out.println("The ret string for format client is:" + ret);
    }

    /**
     * Test method for 'Helper.formatClient(Client)'.
     * <p>
     * Format a client. Print to console to see if format is correct.
     * </p>
     */
    public void testFormatClient_3() {
        Client client = new Client();

        Company c = null;

        ClientStatus s = null;

        client.setClientStatus(s);

        client.setCodeName("code");
        client.setCompany(c);
        client.setId(1L);
        client.setName("client");

        client.setSalesTax(10.999);

        String ret = Helper.formatClient(client);

        System.out.println("The ret string for format client is:" + ret);
    }

    /**
     * Test method for 'Helper.formatCompany(Company)'.
     *
     * <p>
     * Print to console to see if format is correct.
     * </p>
     */
    public void testFormatCompany_1() {
        Company c = new Company();
        c.setDeleted(false);
        c.setPasscode("passcode");
        c.setName("company");
        c.setId(1L);

        String ret = Helper.formatCompany(c);

        System.out.println("The ret string for format company is:" + ret);
    }

    /**
     * Test method for 'Helper.formatCompany(Company)'.
     *
     * Format null company.
     */
    public void testFormatCompany_2() {
        Company c = null;

        String ret = Helper.formatCompany(c);

        assertEquals("Equal to 'null'", "null", ret);
    }

    /**
     * Test method for 'formatClientStatus(ClientStatus)'.
     *
     * Format a null ClientStatus.
     */
    public void testFormatClientStatus() {

        String ret = Helper.formatClientStatus(null);

        assertEquals("Equal to 'null'", "null", ret);
    }

    /**
     * Test method for 'formatClientStatus(ClientStatus)'.
     * <p>
     * Print to console to see if format is correct.
     * </p>
     */
    public void testFormatClientStatus_2() {

        ClientStatus s = new ClientStatus();
        s.setDeleted(false);
        s.setDescription("d");
        s.setName("status");
        s.setId(10L);

        String ret = Helper.formatClientStatus(s);

        System.out.println("The ret string for format ClientStatus  is:" + ret);
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.Helper.formatClientList(List clients)'.
     *
     * <p>
     * Print to console to see if format is correct.
     * </p>
     */
    public void testFormatClientList() {
        List<Client> clients = new ArrayList<Client>();

        Client client = new Client();

        Company c = new Company();
        c.setDeleted(false);
        c.setPasscode("passcode");
        c.setName("company");
        c.setId(1L);

        ClientStatus s = new ClientStatus();
        s.setDeleted(false);
        s.setDescription("d");
        s.setName("status");
        s.setId(10L);

        client.setClientStatus(s);

        client.setCodeName("code");
        client.setCompany(c);
        client.setId(1L);
        client.setName("client");

        client.setSalesTax(10.999);

        clients.add(new Client());
        clients.add(client);
        clients.add(client);

        System.out.println("The ret string for format client list is:" + Helper.formatClientList(clients));
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.Helper.formatClientStatusList(List clientStatuses)'.
     *
     *
     * <p>
     * Print to console to see if format is correct.
     * </p>
     */
    public void testFormatClientStatusList() {

        ClientStatus s = new ClientStatus();
        s.setDeleted(false);
        s.setDescription("d");
        s.setName("status");
        s.setId(10L);

        List<ClientStatus> list = new ArrayList<ClientStatus>();
        list.add(new ClientStatus());
        list.add(s);

        System.out.println("The ret string for format ClientStatus list is:" + Helper.formatClientStatusList(list));

    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.Helper.formatCompanyList(List companies)'.
     * <p>
     * Print to console to see if format is correct.
     * </p>
     */
    public void testFormatCompanyList() {
        Company c = new Company();
        c.setDeleted(false);
        c.setPasscode("passcode");
        c.setName("company");
        c.setId(1L);

        List<Company> list = new ArrayList<Company>();

        list.add(new Company());
        list.add(c);

        System.out.println("The ret string for format company list is:" + Helper.formatCompanyList(list));

    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.Helper.formatProject(Project)'.
     * <p>
     * Print to console to see if format is correct.
     * </p>
     */
    public void testFormatProject() {

        Project project = new Project();
        project.setSalesTax(10.0);
        project.setName("name");
        project.setDescription("description");
        project.setDeleted(false);
        project.setId(10L);

        List<Project> list = new ArrayList<Project>();

        Project p = new Project();
        list.add(project);

        p.setChildProjects(list);

        System.out.println("The ret string for format project is:" + Helper.formatProject(p));
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.Helper.formatProjectList(List projects)'.
     *
     * <p>
     * Print to console to see if format is correct.
     * </p>
     */
    public void testFormatProjectList() {

        Project project = new Project();
        project.setSalesTax(10.0);
        project.setName("name");
        project.setDescription("description");
        project.setDeleted(false);
        project.setId(10L);

        List<Project> list = new ArrayList<Project>();

        Project p = new Project();
        list.add(project);

        p.setChildProjects(list);

        List<Project> projects = new ArrayList<Project>();
        projects.add(p);
        projects.add(project);

        System.out.println("The ret string for format project list is:" + Helper.formatProjectList(projects));
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.Helper.formatProjectStatus(ProjectStatus)'.
     * <p>
     * Print to console to see if format is correct.
     * </p>
     */
    public void testFormatProjectStatus() {

        ProjectStatus s = new ProjectStatus();
        s.setDeleted(false);
        s.setDescription("des");
        s.setName("name");

        System.out.println("The ret string for format ProjectStatus  is:" + Helper.formatProjectStatus(s));

    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.Helper.formatProjectStatusList(List projectStatuses)'.
     *
     * <p>
     * Print to console to see if format is correct.
     * </p>
     */
    public void testFormatProjectStatusList() {
        ProjectStatus s = new ProjectStatus();
        s.setDeleted(false);
        s.setDescription("des");
        s.setName("name");

        List<ProjectStatus> list = new ArrayList<ProjectStatus>();
        list.add(new ProjectStatus());
        list.add(s);

        System.out.println("The ret string for format ProjectStatus list is:" + Helper.formatProjectStatusList(list));
    }
}
