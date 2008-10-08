/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.mock;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag;

import com.topcoder.clients.model.AuditableEntity;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.clients.webservices.usermapping.impl.UserClientMapping;
import com.topcoder.clients.webservices.usermapping.impl.UserProjectMapping;
import com.topcoder.security.auth.module.JBossLoginModuleDemoConfigMBean;
import com.topcoder.security.auth.module.UserProfilePrincipal;
import com.topcoder.user.manager.Profile;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.errorhandling.BaseRuntimeException;

/**
 * Test helper that provide common functionality
 * which is used in testing.
 *
 * @author  CaDenza
 * @version 1.0
 */
public class TestHelper {

    /**
     * Represents configuration for client login.
     */
    public static final String CLIENT_LOGIN_CONFIG = "java.security.auth.login.config";

    /**
     * Represents configuration for initial context.
     */
    public static final String INITIAL_CONTEXT_FACTORY = "org.jboss.security.jndi.LoginInitialContextFactory";

    /**
     * Represents demo domain.
     */
    public static final String DEMO_DOMAIN = "JBossLoginModuleDemoDomain";

    /**
     * Represents demo file name.
     */
    public static final File DEMO_FILE_NAME = new File("test_files/demoConfigs.properties");

    /**
     * Represents demo namespace.
     */
    public static final String DEMO_NAMESPACE = "DemoNamespace";

    /**
     * Represents REQUIRED flag for login module.
     */
    public static final String REQUIRED = LoginModuleControlFlag.REQUIRED.toString();

    /**
     * Represents configuration for user group.
     */
    private static final String USER_GROUP_CONFIG =
        new File("test_files/mockusergroupmanager.properties").getAbsolutePath();

    /**
     * Represents JBoss login module configuration.
     */
    private static final File DEMO_JBOSS_LOGIN_MODULE_CONFIG_FILE = new File("test_files/JBossLoginModule.xml");

    /**
     * Storing login module options.
     */
    private static Map<String, String> options = new HashMap<String, String>();

    /**
     * Represents MBean instance.
     */
    private static JBossLoginModuleDemoConfigMBean mBean = null;

    /**
     * Represents URL of provider.
     */
    private static String providerUrl;

    /**
     * Represents URL of http.
     */
    private static String httpUrl;

    /**
     * Represents RMI Adaptor JNDI.
     */
    private static String rmiAdaptorJndi;

    /**
     * Represents name of MBean.
     */
    private static String mBeanName;

    /**
     * Represents location of ClientService wsdl.
     */
    private static String wsdlLocClientService;

    /**
     * Represents location of CompanyService wsdl.
     */
    private static String wsdlLocCompanyService;

    /**
     * Represents location of ProjectService wsdl.
     */
    private static String wsdlLocProjectService;

    static {
        FileWriter fw = null;
        try {
            // build config for testing
            fw = new FileWriter(DEMO_FILE_NAME);
            fw.write(DEMO_NAMESPACE + "=" + DEMO_JBOSS_LOGIN_MODULE_CONFIG_FILE.getAbsolutePath());
            fw.close();

            options.put("fileName", DEMO_FILE_NAME.getAbsolutePath());
            options.put("configNamespace", DEMO_NAMESPACE);
            options.put("fileconfig.mockusergroupmanager", USER_GROUP_CONFIG);

            // create required properties
            Properties prop = new Properties();
            prop.load(TestHelper.class.getResourceAsStream("/JBoss.properties"));

            providerUrl = prop.getProperty("provider_url");
            rmiAdaptorJndi  = prop.getProperty("RMIAdaptor_jndi");
            mBeanName   = prop.getProperty("mBean_name");
            httpUrl = prop.getProperty("http_url");
            wsdlLocClientService = prop.getProperty("wsdl_client_service");
            wsdlLocCompanyService = prop.getProperty("wsdl_company_service");
            wsdlLocProjectService = prop.getProperty("wsdl_project_service");

            // setup env
            Hashtable<String, String> env = new Hashtable<String, String>();
            String factory = "org.jnp.interfaces.NamingContextFactory";
            env.put(Context.INITIAL_CONTEXT_FACTORY, factory);
            env.put(Context.PROVIDER_URL, providerUrl);
            Context ctx = new InitialContext(env);
            MBeanServerConnection mconn = (MBeanServerConnection) ctx.lookup(rmiAdaptorJndi);
            ObjectName name = new ObjectName(mBeanName);

            // set up mbean
            mBean = (JBossLoginModuleDemoConfigMBean) (MBeanServerInvocationHandler.newProxyInstance(mconn, name,
                JBossLoginModuleDemoConfigMBean.class, false));
        } catch (IllegalArgumentException e) {
            e.printStackTrace(System.err);
        } catch (NamingException e) {
            e.printStackTrace(System.err);
        } catch (MalformedObjectNameException e) {
            e.printStackTrace(System.err);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    /**
     * Default constructor.
     */
    private TestHelper() {
        // do nothing.
    }

    /**
     * Getter for location of ClientService wsdl.
     *
     * @return wsdl location.
     */
    public static String getWsdlLocClientService() {
        return wsdlLocClientService;
    }

    /**
     * Getter for location of CompanyService wsdl.
     *
     * @return wsdl location.
     */
    public static String getWsdlLocCompanyService() {
        return wsdlLocCompanyService;
    }

    /**
     * Getter for location of ProjectService wsdl.
     *
     * @return wsdl location.
     */
    public static String getWsdlLocProjectService() {
        return wsdlLocProjectService;
    }

    /**
     * Setter client login file.
     *
     * @throws Exception if any error occurs.
     */
    public static void setClientLoginFile() throws Exception {

        if (System.getProperty(CLIENT_LOGIN_CONFIG) == null) {
            String path = new File("test_files/auth.conf").getAbsolutePath();
            System.setProperty(CLIENT_LOGIN_CONFIG, path);
        }
    }

    /**
     * Getter for environment properties.
     *
     * @return environment properties.
     */
    public static Properties getEnv() {
        Properties env = new Properties();
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
        env.setProperty(Context.PROVIDER_URL, providerUrl);
        env.setProperty(Context.SECURITY_PROTOCOL, DEMO_DOMAIN);
        return env;
    }

    /**
     * Getter for mBean options.
     *
     * @return mBean options.
     */
    public static Map<String, String> getOptions() {
        return options;
    }

    /**
     * Getter Http url.
     *
     * @return http url.
     */
    public static String getHttpUrl() {
        return httpUrl;
    }

    /**
     * Reload JBossLoginModuleDemo configuration.
     *
     * @param controlFlag1
     *      First control flag.
     * @param controlFlag2
     *      Second control flag.
     * @param options
     *      Mbean options.
     * @param useSuccessLoginModule
     *      whether use success login module.
     * @param order
     *      Order flag.
     * @throws Exception if any failure occurs.
     */
    public static void reloadJBossLoginModuleDemoConfig(String controlFlag1,
        String controlFlag2, Map<String, String> options,
        boolean useSuccessLoginModule, boolean order) throws Exception {
        mBean.reloadJBossLoginModuleDemoConfig(controlFlag1, controlFlag2,
                    options, useSuccessLoginModule, order);
    }

    /**
     * Create simple principal.
     *
     * @param userId
     *      The id of user.
     * @param name
     *      The name of user profile principal.
     *
     * @return principal instance.
     */
    public static Principal createPrincipal(long userId, String name) {
        Profile profile = new MockProfile("type");
        Principal principal = new UserProfilePrincipal(profile, userId, name);
        return principal;
    }

    /**
     * Remove all the namespace.
     *
     * @throws Exception
     *                 to JUnit
     */
    @SuppressWarnings("unchecked")
    public static void clearConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        Iterator it = cm.getAllNamespaces();
        while (it.hasNext()) {
            cm.removeNamespace((String) it.next());
        }
    }

    /**
     * Injecting property for private field in given instance.
     *
     * @param clazz
     *      The class that contain defined private field.
     * @param propName
     *      The name of property.
     * @param objInstance
     *      The object instance.
     * @param propVal
     *      The value of property to be injected.
     */
    @SuppressWarnings("unchecked")
    public static void injectPropertyValue(Class clazz, String propName, Object objInstance, Object propVal) {
        try {
            Field field = clazz.getDeclaredField(propName);
            field.setAccessible(true);
            field.set(objInstance, propVal);
            field.setAccessible(false);
        } catch (SecurityException e) {
            throw new BaseRuntimeException("Fail inject property value.", e);
        } catch (NoSuchFieldException e) {
            throw new BaseRuntimeException("Fail inject property value.", e);
        } catch (IllegalArgumentException e) {
            throw new BaseRuntimeException("Fail inject property value.", e);
        } catch (IllegalAccessException e) {
            throw new BaseRuntimeException("Fail inject property value.", e);
        }
    }

    /**
     * Gets the value of a private field in the given class. The field has the
     * given name. The value is retrieved from the given instance. If the
     * instance is null, the field is a static field. If any error occurs, null
     * is returned.
     *
     * @param type
     *                the class which the private field belongs to
     * @param instance
     *                the instance which the private field belongs to
     * @param name
     *                the name of the private field to be retrieved
     * @return the value of the private field
     */
    @SuppressWarnings("unchecked")
    public static Object getPrivateField(Class type, Object instance, String name) {
        Field field = null;
        Object obj = null;
        try {
            // get the reflection of the field
            field = type.getDeclaredField(name);

            // set the field accessible
            field.setAccessible(true);

            // get the value
            obj = field.get(instance);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } finally {
            if (field != null) {
                // reset the accessibility
                field.setAccessible(false);
            }
        }

        return obj;
    }

    /**
     * Create client.
     *
     * @param counter
     *      The Client counter.
     * @param status
     *      The client status.
     * @param company
     *      The client company.
     *
     * @return client created
     */
    public static Client createClient(long counter, ClientStatus status, Company company) {
        Date date = new Date();

        Client client = new Client();
        client.setId(counter);
        setAuditableEntity(client);

        client.setClientStatus(status);
        client.setCodeName("codename");

        client.setCompany(company);
        client.setStartDate(date);
        client.setEndDate(new Date(date.getTime() + 10000));
        client.setPaymentTermsId(10);
        client.setSalesTax(1.1);
        client.setDeleted(false);
        return client;
    }

    /**
     * Create ProjectStatus.
     *
     * @param counter
     *      The counter of project status.
     *
     * @return ProjectStatus created
     */
    public static ProjectStatus createProjectStatus(int counter) {
        ProjectStatus projectStatus = new ProjectStatus();
        setAuditableEntity(projectStatus);
        projectStatus.setDescription("des");
        projectStatus.setId(counter);
        return projectStatus;
    }

    /**
     * Create Project.
     *
     * @param counter
     *      The counter of project.
     * @param client
     *      The project's client.
     * @param status
     *      The project status.
     * @param company
     *      The project company.
     *
     * @return Project created
     */
    public static Project createProject(long counter, Client client, ProjectStatus status, Company company) {
        Project project = new Project();
        project.setId(counter);

        setAuditableEntity(project);
        project.setActive(true);
        project.setClient(client);
        project.setProjectStatus(status);
        project.setCompany(company);
        project.setSalesTax(1.0);
        project.setDescription("desc");

        return project;
    }

    /**
     * Create company.
     *
     * @param counter
     *      The counter of Company.
     *
     * @return company created
     */
    public static Company createCompany(long counter) {
        Company company = new Company();

        company.setId(counter);
        setAuditableEntity(company);
        company.setPasscode("passcode");

        return company;
    }

    /**
     * Create ClientStatus.
     *
     * @param counter
     *      The counter of client status.
     *
     * @return ClientStatus created
     */
    public static ClientStatus createClientStatus(int counter) {
        ClientStatus clientStatus = new ClientStatus();

        clientStatus.setId(counter);
        setAuditableEntity(clientStatus);
        clientStatus.setDescription("description");

        return clientStatus;
    }

    /**
     * Create UserClientMapping.
     *
     * @param counter the counter of user client mapping.
     * @param clientId the id of client.
     * @param userId the id of user.
     *
     * @return UserClientMapping created
     */
    public static UserClientMapping createUserClientMapping(int counter, long clientId, long userId) {
        UserClientMapping entity = new UserClientMapping();

        entity.setId(counter);
        setAuditableEntity(entity);
        entity.setClientId(clientId);
        entity.setUserId(userId);

        return entity;
    }

    /**
     * Create UserProjectMapping.
     *
     * @param counter the counter of user project mapping.
     * @param projectId the id of project.
     * @param userId the id of user.
     *
     * @return UserClientMapping created
     */
    public static UserProjectMapping createUserProjectMapping(int counter, long projectId, long userId) {
        UserProjectMapping entity = new UserProjectMapping();

        entity.setId(counter);
        setAuditableEntity(entity);
        entity.setProjectId(projectId);
        entity.setUserId(userId);

        return entity;
    }

    /**
     * Set fields of auditableEntity.
     * @param auditableEntity the auditableEntity to set
     */
    private static void setAuditableEntity(AuditableEntity auditableEntity) {
        auditableEntity.setCreateUsername("createUser");
        auditableEntity.setModifyUsername("modifyUser");
        auditableEntity.setCreateDate(new Date());
        auditableEntity.setModifyDate(new Date());
        auditableEntity.setName("name");
    }
}
