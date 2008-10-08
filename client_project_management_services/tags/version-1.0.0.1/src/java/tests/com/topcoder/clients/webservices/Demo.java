/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices;

import java.util.Date;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.clients.webservices.beans.ClientServiceBean;
import com.topcoder.clients.webservices.beans.ProjectServiceBean;
import com.topcoder.clients.webservices.mock.MockSessionContext;
import com.topcoder.clients.webservices.mock.TestBase;
import com.topcoder.clients.webservices.mock.TestHelper;
import com.topcoder.clients.webservices.webserviceclients.ClientServiceClient;
import com.topcoder.clients.webservices.webserviceclients.CompanyServiceClient;
import com.topcoder.clients.webservices.webserviceclients.ProjectServiceClient;

/**
 * Demo class to give explanation how to use this component.
 * Assume if administratorRole is 'Admin' and clientAndProjectUserRole is 'User'.
 * This role can be modified based on your own configuration.
 *
 * @author  Mafy, CaDenza
 * @version 1.0
 */
public class Demo extends TestBase {

    /**
     * Represents Session context instance.
     */
    private MockSessionContext session;

    /**
     * Setup environment for testing.
     *
     * @throws Exception into JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        session = new MockSessionContext();
        session.addRole("admin");
        session.setPrincipal(TestHelper.createPrincipal(1, "dev"));
    }

    /**
     * Teardown environment for testing.
     *
     * @throws Exception into JUnit.
     */
    protected void tearDown() throws Exception {
        session.clearRoles();
        session = null;

        super.tearDown();
    }

    /**
     * Demo section to give explanation how to use CompanyService in this component.
     * This demo section will explain how to use remote bean invocation to create
     * CompanyService instance and perform any business process.
     *
     * @throws Exception into JUnit.
     */
    public void testDemoCompanyService_WithRemoteBean() throws Exception {
        // --- create CompanyService using remote bean invocation.
        // We will use JBossLoginModule, so authentication is required here.
        String username = "webservices_test";
        String password = "pass";
        String jndiName = "services/" + CompanyServiceRemote.JNDI_NAME;
        CompanyService companyService = getRemoteBean(CompanyService.class, username, password, jndiName);

        baseDemoCompanyService(companyService);
    }

    /**
     * Demo section to give explanation how to use CompanyService in this component.
     * This demo section will explain how to use Webservice invocation to create
     * CompanyService instance and perform any business process.
     *
     * @throws Exception into JUnit.
     */
    public void testDemoCompanyService_WithWebservices() throws Exception {
        TestHelper.setClientLoginFile();
        TestHelper.reloadJBossLoginModuleDemoConfig(TestHelper.REQUIRED, TestHelper.REQUIRED,
            TestHelper.getOptions(), true, true);

        // ---- create CompanyService using webservice.
        // For example we have the following WSDL document location:
        // http://topcoder.com:8080/jaxws/clientProject/CompanyService/?wsdl

        // Create a CompanyServiceClient client:
        CompanyServiceClient companyServiceClient = new CompanyServiceClient(TestHelper.getWsdlLocCompanyService());

        // get CompanyService endpoint:
        CompanyService companyService = companyServiceClient.getCompanyServicePort();

        baseDemoCompanyService(companyService);
    }

    /**
     * Demo section to give explanation how to use ClientService in this component.
     * This demo section will explain how to use remote bean invocation to create
     * ClientService instance and perform any business process.
     *
     * @throws Exception into JUnit.
     */
    public void testDemoClientService_WithRemoteBean() throws Exception {

        // While using remote bean invocation, authentication is required here.
        String username = "webservices_test";
        String password = "pass";
        String jndiName = "services/" + ClientServiceRemote.JNDI_NAME;
        ClientService clientService   = getRemoteBean(ClientServiceBean.class, username, password, jndiName);

        baseDemoClientService(clientService);
    }

    /**
     * Demo section to give explanation how to use ClientService in this component.
     * This demo section will explain how to use Webservice invocation to create
     * ClientService instance and perform any business process.
     *
     * @throws Exception into JUnit.
     */
    public void testDemoClientService_WithWebservice() throws Exception {

        // setup configuration for JBossLoginModule demo.
        // Authentication can be configured in JbossLoginModule.xml config file.
        TestHelper.setClientLoginFile();
        TestHelper.reloadJBossLoginModuleDemoConfig(TestHelper.REQUIRED, TestHelper.REQUIRED,
            TestHelper.getOptions(), true, true);

        // We have the following WSDL document location, for example:
        // "http://topcoder.com:8080/jaxws/clientProject/ClientService/?wsdl"
        String wsdlLocation = TestHelper.getWsdlLocClientService();

        // Create a ClientServiceClient client:
        ClientServiceClient clientServiceClient = new ClientServiceClient(wsdlLocation);

        // Get ClientService instance.
        ClientService clientService = clientServiceClient.getClientServicePort();

        baseDemoClientService(clientService);
    }

    /**
     * This section will explain how to use ProjectService in this component.
     * This demo section will explain how to use remote bean invocation to create
     * ProjectService instance and perform any business process.
     *
     * @throws Exception into JUnit.
     */
    public void testDemoProjectService_WithRemoteBean() throws Exception {
        // While using remote bean invocation, authentication is required here.
        String username = "webservices_test";
        String password = "pass";
        String jndiName = "services/" + ProjectServiceRemote.JNDI_NAME;
        ProjectService projectService  = getRemoteBean(ProjectServiceBean.class, username, password, jndiName);

        baseDemoProjectService(projectService);
    }

    /**
     * This section will explain how to use ProjectService in this component.
     * This demo section will explain how to use Webservice invocation to create
     * ProjectService instance and perform any business process.
     *
     * @throws Exception into JUnit.
     */
    public void testDemoProjectService_WithWebservice() throws Exception {
        TestHelper.setClientLoginFile();
        TestHelper.reloadJBossLoginModuleDemoConfig(TestHelper.REQUIRED, TestHelper.REQUIRED,
            TestHelper.getOptions(), true, true);

      // we have the following WSDL document location, for example:
      // "http://topcoder.com:8080/jaxws/clientProject/ProjectService/?wsdl"
        String wsdlLocation = TestHelper.getWsdlLocProjectService();

        // create a ProjectServiceClient client:
        ProjectServiceClient projectServiceClient =  new ProjectServiceClient(wsdlLocation);

        // get ProjectService endpoint:
        ProjectService projectService =  projectServiceClient.getProjectServicePort();

        baseDemoProjectService(projectService);
    }

    /**
     * Get remote bean instance.
     *
     * @param <T>
     *      The generic class of remote bean.
     * @param clazz
     *      The bean class.
     * @param username
     *      The username to login via JbossLoginModule.
     * @param pwd
     *      The password to login via JBossLoginModule.
     * @param jndiName
     *      The name of JNDI.
     *
     * @return remote bean instance.
     *
     * @throws Exception if any failure occurs.
     */
    @SuppressWarnings("unchecked")
    private <T extends Object> T getRemoteBean(Class<T> clazz, String username,
        String pwd, String jndiName) throws Exception {

        TestHelper.setClientLoginFile();
        TestHelper.reloadJBossLoginModuleDemoConfig(TestHelper.REQUIRED, TestHelper.REQUIRED,
            TestHelper.getOptions(), true, true);

        Properties env = TestHelper.getEnv();
        env.setProperty(Context.SECURITY_PRINCIPAL, username);
        env.setProperty(Context.SECURITY_CREDENTIALS, pwd);
        InitialContext ctx = new InitialContext(env);

        return (T) ctx.lookup(jndiName);
    }

    /**
     * This is base demo to explain how to use ClientService in this component.
     * Any failure should not be expected to be thrown while this method is executed.
     * ClientService instance can be taken from remote bean or webservice invocation.
     *
     * @param clientService
     *      The ClientService instance.
     *
     * @throws Exception if any failure occurs.
     */
    private void baseDemoClientService(ClientService clientService) throws Exception {

        // we assume that there is already a client status in the persistence:
        ClientStatus clientStatus = createClientStatus();
        Client client;
        final double salesTax = 2223.5;

        client = new Client();
        client.setName("client name");
        client.setSalesTax(salesTax);
        client.setStartDate(new Date());
        client.setEndDate(new Date(client.getStartDate().getTime() + 1));
        client.setClientStatus(clientStatus);

        // create a client:
        client = clientService.createClient(client);

        // if the user cannot pass authentication, then AuthorizationFailedException will be thrown here.

        // update a client:
        client.setName("client's another name");
        client.setSalesTax(salesTax);
        client = clientService.updateClient(client);
        // if the user is in "User" or "Admin" roles then the operation
        // has been performed otherwise an AuthorizationFailedException has been thrown.

        // set a client's code name:
        client = clientService.setClientCodeName(client, "client code name");
        // if the user is in "User" or "Admin" roles then the operation
        // has been performed otherwise an AuthorizationFailedException has been thrown.

        // set a client's status:
        clientStatus.setDescription("client status description");
        client = clientService.setClientStatus(client, clientStatus);
        // if the user is in "User" or "Admin" roles then the operation has been
        // performed otherwise an AuthorizationFailedException has been thrown.

        // delete a client:
        clientService.deleteClient(client);
        // if the user is in "User" or "Admin" roles then the operation
        // has been performed otherwise an AuthorizationFailedException has been thrown.
    }

    /**
     * This is base demo to explain how to use ProjectService in this component.
     * Any failure should not be expected to be thrown while this method is executed.
     * ProjectService instance can be taken from remote bean or webservice invocation.
     *
     * @param projectService
     *      The ProjectService instance.
     *
     * @throws Exception if any failure occurs.
     */
    private void baseDemoProjectService(ProjectService projectService) throws Exception {

        // we assume that there is already a client and a project status
        // in the persistence:
        Client client = createClient();
        ProjectStatus projectStatus = createProjectStatus();
        Project project;
        final double salesTax = 2223.5;

        project = new Project();
        project.setName("project name");
        project.setDescription("project's description");
        project.setPOBoxNumber("111222333");
        project.setParentProjectId(0);
        project.setSalesTax(salesTax);
        project.setProjectStatus(projectStatus);

        client.setName("client name");
        client.setSalesTax(salesTax);

        // create a project for the given client:
        project = projectService.createProject(project, client);

        // if the user cannot pass authentication, then AuthorizationFailedException will be thrown here.

        // update a project:
        project.setName("project's another name");
        project.setDescription("project's another description");
        project.setPOBoxNumber("555222333");
        client.setName("client's another name");
        project.setClient(client);

        project = projectService.updateProject(project);
        // if the user is in "User" or "Admin" roles then
        // the operation has been performed otherwise an AuthorizationFailedException has been thrown.

        // set a project's status:
        projectStatus.setName("project status name");
        projectStatus.setDescription("project status description");
        project = projectService.setProjectStatus(project, projectStatus);

        // delete a project:
        projectService.deleteProject(project);
        // if the user is in "User" or "Admin" roles then the operation has been performed
        // otherwise an AuthorizationFailedException has been thrown.
    }

    /**
     * This is base demo to explain how to use CompanyService in this component.
     * Any failure should not be expected to be thrown while this method is executed.
     * CompanyService instance can be taken from remote bean or webservice invocation.
     *
     * @param companyService
     *      The CompanyService instance.
     *
     * @throws Exception if any failure occurs.
     */
    private void baseDemoCompanyService(CompanyService companyService) throws Exception {

        // Create new entity of Company.
        Company company = new Company();
        company.setName("company name");
        company.setPasscode("12213");

        // create a company and persist it into persistence layer.
        company = companyService.createCompany(company);

        // if the user cannot pass authentication, then AuthorizationFailedException will be thrown here.

        // update a company:
        company.setName("company's another name");
        company.setPasscode("23324");
        company = companyService.updateCompany(company);

        // if the user is in "User" or "Admin" roles then the operation
        // has been performed otherwise an AuthorizationFailedException has been thrown.

        // delete a company:
        companyService.deleteCompany(company);

        // if the user is in "User" or "Admin" roles then the operation
        // has been performed otherwise an AuthorizationFailedException has been thrown.
    }
}
