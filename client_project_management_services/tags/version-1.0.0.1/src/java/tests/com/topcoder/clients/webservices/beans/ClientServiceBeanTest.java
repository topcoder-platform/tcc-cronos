/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.beans;

import com.topcoder.clients.manager.dao.DAOClientManager;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.webservices.ClientServiceException;
import com.topcoder.clients.webservices.mock.MockClientDAO;
import com.topcoder.clients.webservices.mock.MockSessionContext;
import com.topcoder.clients.webservices.mock.TestBase;
import com.topcoder.clients.webservices.mock.TestHelper;
import com.topcoder.clients.webservices.usermapping.impl.JPAUserMappingRetriever;

/**
 * Unit test for {@link ClientServiceBean}.
 *
 * @author  CaDenza
 * @version 1.0
 */
public class ClientServiceBeanTest extends TestBase {

    /**
     * Represents IAE fail message.
     */
    private static final String T_IAE = "IllegalArgumentException is expected to be thrown.";

    /**
     * Represents ISE fail message.
     */
    private static final String T_ISE = "IllegalStateException is expected to be thrown.";

    /**
     * Represents AFE fail message.
     */
    private static final String T_AFE = "AuthorizationFailedException is expected to be thrown";

    /**
     * Represents CSE fail message.
     */
    private static final String T_CSE = "ClientServiceException is expected to be thrown.";

    /**
     * Represents mockup for session context.
     */
    private MockSessionContext session;

    /**
     * Represents ClientServiceBean instance.
     */
    private ClientServiceBean beanLocal;

    /**
     * Setup environment for testing.
     *
     * @throws Exception into JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        session = new MockSessionContext();
        beanLocal = new ClientServiceBean();

        TestHelper.injectPropertyValue(ClientServiceBean.class,
                "clientManagerFile", beanLocal, "clientManagerFileLocal.properties");
        TestHelper.injectPropertyValue(ClientServiceBean.class,
                "clientManagerNamespace", beanLocal, "clientManagerNamespace");
        TestHelper.injectPropertyValue(ClientServiceBean.class,
                "userMappingRetrieverFile", beanLocal, "userMappingRetrieverFileLocal.properties");
        TestHelper.injectPropertyValue(ClientServiceBean.class,
                "userMappingRetrieverNamespace", beanLocal, "userMappingRetrieverNamespace");

        TestHelper.injectPropertyValue(ClientServiceBean.class,
                "sessionContext", beanLocal, session);

        beanLocal.initialize();

        JPAUserMappingRetriever jpa =
            (JPAUserMappingRetriever) TestHelper.getPrivateField(ClientServiceBean.class,
                    beanLocal, "userMappingRetriever");
        jpa.setEntityManager(getEntityManager());
    }

    /**
     * Teardown environment for testing.
     *
     * @throws Exception into JUnit.
     */
    protected void tearDown() throws Exception {
        session.clearRoles();

        session = null;
        beanLocal = null;

        super.tearDown();
    }

    /**
     * Test for {@link ClientServiceBean#ClientServiceBean()}.
     */
    public void testClientServiceBean() {
        assertNotNull("fail create new instance.", beanLocal);
    }

    /**
     * Test for {@link ClientServiceBean#initialize()}.
     */
    public void testInitialize() {
        assertNotNull("Fail perform initialization", TestHelper.getPrivateField(ClientServiceBean.class,
                beanLocal, "userMappingRetriever"));
        assertNotNull("Fail perform initialization", TestHelper.getPrivateField(ClientServiceBean.class,
                beanLocal, "log"));
        assertNotNull("Fail perform initialization", TestHelper.getPrivateField(ClientServiceBean.class,
                beanLocal, "clientManager"));
    }

    /**
     * Test for {@link ClientServiceBean#initialize()}.
     *
     * Caused by: Invalid configuration.
     * Expected : {@link ClientServiceBeanConfigurationException}.
     */
    public void testInitialize_WithInvalidConfiguration() {
        TestHelper.injectPropertyValue(ClientServiceBean.class,
                "clientManagerFile", beanLocal, "\t \n");

        try {
            beanLocal.initialize();
            fail("ClientServiceBeanConfigurationException is expected to be thrown.");
        } catch (ClientServiceBeanConfigurationException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ClientServiceBean#createClient(Client)}.
     *
     * @throws Exception into JUnit.
     */
    public void testCreateClient() throws Exception {
        Client client = TestHelper.createClient(0, null, null);
        assertEquals("Initial value is invalid.", 0, client.getId());

        session.addRole("admin");
        initRole("admin", "");
        session.setPrincipal(TestHelper.createPrincipal(1, "dev"));

        beanLocal.setVerboseLogging(true);
        client = beanLocal.createClient(client);
        assertTrue("Fail create client.", client.getId() > 0);
    }

    /**
     * Test for {@link ClientServiceBean#createClient(Client)}.
     *
     * Caused by: Null Client.
     * Expected : {@link IllegalArgumentException}.
     *
     * @throws Exception into JUnit.
     */
    public void testCreateClient_WithNullClient() throws Exception {
        Client client = TestHelper.createClient(0, null, null);
        assertEquals("Initial value is invalid.", 0, client.getId());

        session.addRole("admin");
        initRole("admin", "");
        session.setPrincipal(TestHelper.createPrincipal(1, "dev"));

        try {
            beanLocal.createClient(null);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ClientServiceBean#createClient(Client)}.
     *
     * Caused by: Some of manager is null
     * Expected : {@link IllegalStateException}.
     *
     * @throws Exception into JUnit.
     */
    public void testCreateClient_WithNullManager() throws Exception {
        Client client = TestHelper.createClient(0, null, null);
        assertEquals("Initial value is invalid.", 0, client.getId());

        session.addRole("admin");
        initRole("admin", "");
        session.setPrincipal(TestHelper.createPrincipal(1, "dev"));

        try {
            TestHelper.injectPropertyValue(ClientServiceBean.class,
                    "clientManager", beanLocal, null);
            beanLocal.createClient(new Client());
            fail(T_ISE);
        } catch (IllegalStateException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ClientServiceBean#createClient(Client)}.
     *
     * Caused by: any failure occurs in service.
     * Expected : {@link ClientServiceException}.
     *
     * @throws Exception into JUnit.
     */
    public void testCreateClient_WithFailService() throws Exception {
        Client client = TestHelper.createClient(0, null, null);
        assertEquals("Initial value is invalid.", 0, client.getId());

        session.addRole("admin");
        initRole("admin", "");
        session.setPrincipal(TestHelper.createPrincipal(1, "dev"));

        try {
            beanLocal.createClient(new Client());
            fail(T_CSE);
        } catch (ClientServiceException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ClientServiceBean#createClient(Client)}.
     *
     * Caused by: Invalid role.
     * Expected : {@link AuthorizationFailedException}.
     *
     * @throws Exception into JUnit.
     */
    public void testCreateClient_WithFailAuthentication() throws Exception {
        Client client = TestHelper.createClient(0, null, null);
        assertEquals("Initial value is invalid.", 0, client.getId());

        session.addRole("administrator");
        initRole("admin", "");
        session.setPrincipal(TestHelper.createPrincipal(1, "dev"));

        try {
            beanLocal.createClient(new Client());
            fail(T_AFE);
        } catch (AuthorizationFailedException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ClientServiceBean#updateClient(Client)}.
     *
     * @throws Exception into JUnit.
     */
    public void testUpdateClient() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Client client = beanLocal.createClient(TestHelper.createClient(0, null, null));
        createClient(client.getId());
        createUserClientMapping(client.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "developer");

        long t = client.getModifyDate().getTime();
        client = beanLocal.updateClient(client);
        assertTrue(client.getModifyDate().getTime() > t);
    }

    /**
     * Test for {@link ClientServiceBean#updateClient(Client)}.
     *
     * Caused by: Null client
     * Expected : {@link IllegalArgumentException}.
     *
     * @throws Exception into JUnit.
     */
    public void testUpdateClient_WithNullClient() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Client client = beanLocal.createClient(TestHelper.createClient(0, null, null));
        createClient(client.getId());
        createUserClientMapping(client.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "developer");

        try {
            beanLocal.updateClient(null);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ClientServiceBean#updateClient(Client)}.
     *
     * Caused by: Some of manager has null value.
     * Expected : {@link IllegalStateException}.
     *
     * @throws Exception into JUnit.
     */
    public void testUpdateClient_WithNullManager() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Client client = beanLocal.createClient(TestHelper.createClient(0, null, null));
        createClient(client.getId());
        createUserClientMapping(client.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "developer");

        try {
            TestHelper.injectPropertyValue(ClientServiceBean.class,
                    "userMappingRetriever", beanLocal, null);
            beanLocal.updateClient(new Client());
            fail(T_ISE);
        } catch (IllegalStateException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ClientServiceBean#updateClient(Client)}.
     *
     * Caused by: Any failure occurs in operation.
     * Expected : {@link ClientServiceException}.
     *
     * @throws Exception into JUnit.
     */
    public void testUpdateClient_WithFailService() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Client client = beanLocal.createClient(TestHelper.createClient(0, null, null));
        createClient(client.getId());
        createUserClientMapping(client.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "developer");

        try {
            client.setStartDate(null);
            beanLocal.updateClient(client);
            fail(T_CSE);
        } catch (ClientServiceException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ClientServiceBean#updateClient(Client)}.
     *
     * Caused by: Fail authentication.
     * Expected : {@link AuthorizationFailedException}.
     *
     * @throws Exception into JUnit.
     */
    public void testUpdateClient_WithFailAuthentication() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Client client = beanLocal.createClient(TestHelper.createClient(0, null, null));
        createClient(client.getId());
        createUserClientMapping(client.getId(), userId);

        session.clearRoles();
        session.addRole("dev");
        initRole(null, "developer");

        try {
            beanLocal.updateClient(new Client());
            fail(T_AFE);
        } catch (AuthorizationFailedException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ClientServiceBean#deleteClient(Client)}.
     *
     * @throws Exception into JUnit.
     */
    public void testDeleteClient() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Client client = beanLocal.createClient(TestHelper.createClient(0, null, null));
        createClient(client.getId());
        createUserClientMapping(client.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "developer");

        assertFalse(client.isDeleted());
        client = beanLocal.deleteClient(client);
        assertTrue(client.isDeleted());
    }

    /**
     * Test for {@link ClientServiceBean#deleteClient(Client)}.
     *
     * Caused by: Null client.
     * Expected : {@link IllegalArgumentException}.
     *
     * @throws Exception into JUnit.
     */
    public void testDeleteClient_WithNullClient() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Client client = beanLocal.createClient(TestHelper.createClient(0, null, null));
        createClient(client.getId());
        createUserClientMapping(client.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "developer");

        try {
            beanLocal.deleteClient(null);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ClientServiceBean#deleteClient(Client)}.
     *
     * Caused by: Some of manager is null.
     * Expected : {@link IllegalStateException}.
     *
     * @throws Exception into JUnit.
     */
    public void testDeleteClient_WithNullManager() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Client client = beanLocal.createClient(TestHelper.createClient(0, null, null));
        createClient(client.getId());
        createUserClientMapping(client.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "developer");

        try {
            TestHelper.injectPropertyValue(ClientServiceBean.class,
                    "userMappingRetriever", beanLocal, null);
            beanLocal.deleteClient(client);
            fail(T_ISE);
        } catch (IllegalStateException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ClientServiceBean#deleteClient(Client)}.
     *
     * Caused by: Fail service occurs.
     * Expected : {@link ClientServiceException}.
     *
     * @throws Exception into JUnit.
     */
    public void testDeleteClient_WithFailService() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Client client = beanLocal.createClient(TestHelper.createClient(0, null, null));
        createClient(client.getId());
        createUserClientMapping(client.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "developer");

        try {
            getClientDAO().setFlag(true);
            beanLocal.deleteClient(client);
            fail(T_CSE);
        } catch (ClientServiceException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ClientServiceBean#deleteClient(Client)}.
     *
     * Caused by: Fail authorization.
     * Expected : {@link AuthorizationFailedException}.
     *
     * @throws Exception into JUnit.
     */
    public void testDeleteClient_WithFailAuthorization() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Client client = beanLocal.createClient(TestHelper.createClient(0, null, null));
        createClient(client.getId());
        createUserClientMapping(client.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "dev");

        try {
            beanLocal.deleteClient(client);
            fail(T_AFE);
        } catch (AuthorizationFailedException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ClientServiceBean#setClientCodeName(Client, String)}.
     *
     * @throws Exception into JUnit.
     */
    public void testSetClientCodeName() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Client client = beanLocal.createClient(TestHelper.createClient(0, null, null));
        createClient(client.getId());
        createUserClientMapping(client.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "developer");

        String codeName = "new Code name";
        assertFalse(codeName.equals(client.getCodeName()));

        client = beanLocal.setClientCodeName(client, codeName);

        assertTrue(codeName.equals(client.getCodeName()));
    }

    /**
     * Test for {@link ClientServiceBean#setClientCodeName(Client, String)}.
     *
     * Caused by: Null client.
     * Expected : {@link IllegalArgumentException}.
     *
     * @throws Exception into JUnit.
     */
    public void testSetClientCodeName_withNullClient() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Client client = beanLocal.createClient(TestHelper.createClient(0, null, null));
        createClient(client.getId());
        createUserClientMapping(client.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "developer");

        String codeName = "new Code name";
        try {
            beanLocal.setClientCodeName(null, codeName);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ClientServiceBean#setClientCodeName(Client, String)}.
     *
     * Caused by: Null code name.
     * Expected : {@link IllegalArgumentException}.
     *
     * @throws Exception into JUnit.
     */
    public void testSetClientCodeName_withNullCodeName() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Client client = beanLocal.createClient(TestHelper.createClient(0, null, null));
        createClient(client.getId());
        createUserClientMapping(client.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "developer");

        try {
            beanLocal.setClientCodeName(client, null);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }

    }

    /**
     * Test for {@link ClientServiceBean#setClientCodeName(Client, String)}.
     *
     * Caused by: empty code name.
     * Expected : {@link IllegalArgumentException}.
     *
     * @throws Exception into JUnit.
     */
    public void testSetClientCodeName_withEmptyCodeName() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Client client = beanLocal.createClient(TestHelper.createClient(0, null, null));
        createClient(client.getId());
        createUserClientMapping(client.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "developer");

        String codeName = "\t \n";
        try {
            beanLocal.setClientCodeName(client, codeName);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ClientServiceBean#setClientCodeName(Client, String)}.
     *
     * Caused by: Null manager.
     * Expected : {@link IllegalStateException}.
     *
     * @throws Exception into JUnit.
     */
    public void testSetClientCodeName_withNullManager() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Client client = beanLocal.createClient(TestHelper.createClient(0, null, null));
        createClient(client.getId());
        createUserClientMapping(client.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "developer");

        TestHelper.injectPropertyValue(ClientServiceBean.class,
                "userMappingRetriever", beanLocal, null);
        String codeName = "new Code name";
        try {
            beanLocal.setClientCodeName(client, codeName);
            fail(T_ISE);
        } catch (IllegalStateException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ClientServiceBean#setClientCodeName(Client, String)}.
     *
     * Caused by: Fail service occurs.
     * Expected : {@link ClientServiceException}.
     *
     * @throws Exception into JUnit.
     */
    public void testSetClientCodeName_withNFailService() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Client client = beanLocal.createClient(TestHelper.createClient(0, null, null));
        createClient(client.getId());
        createUserClientMapping(client.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "developer");

        String codeName = "new Code name";
        try {
            getClientDAO().setFlag(true);
            beanLocal.setClientCodeName(client, codeName);
            fail(T_CSE);
        } catch (ClientServiceException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ClientServiceBean#setClientCodeName(Client, String)}.
     *
     * Caused by: Fail authorization.
     * Expected : {@link AuthorizationFailedException}.
     *
     * @throws Exception into JUnit.
     */
    public void testSetClientCodeName_withFailAuthorization() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Client client = beanLocal.createClient(TestHelper.createClient(0, null, null));
        createClient(client.getId());
        createUserClientMapping(client.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "dev");

        String codeName = "new Code name";
        try {
            beanLocal.setClientCodeName(client, codeName);
            fail(T_AFE);
        } catch (AuthorizationFailedException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ClientServiceBean#setClientStatus(Client, ClientStatus)}.
     *
     * @throws Exception into JUnit.
     */
    public void testSetClientStatus() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Client client = beanLocal.createClient(TestHelper.createClient(0, null, null));
        createClient(client.getId());
        createUserClientMapping(client.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "developer");

        ClientStatus status = createClientStatus();

        assertFalse(status.equals(client.getClientStatus()));
        client = beanLocal.setClientStatus(client, status);

        assertTrue(status.equals(client.getClientStatus()));
    }

    /**
     * Test for {@link ClientServiceBean#setClientStatus(Client, ClientStatus)}.
     *
     * Caused by: Null Client.
     * Expected : {@link IllegalArgumentException}.
     *
     * @throws Exception into JUnit.
     */
    public void testSetClientStatus_WithNullClient() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Client client = beanLocal.createClient(TestHelper.createClient(0, null, null));
        createClient(client.getId());
        createUserClientMapping(client.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "developer");

        ClientStatus status = createClientStatus();

        try {
            beanLocal.setClientStatus(null, status);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ClientServiceBean#setClientStatus(Client, ClientStatus)}.
     *
     * Caused by: Null Client status.
     * Expected : {@link IllegalArgumentException}.
     *
     * @throws Exception into JUnit.
     */
    public void testSetClientStatus_WithNullStatus() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Client client = beanLocal.createClient(TestHelper.createClient(0, null, null));
        createClient(client.getId());
        createUserClientMapping(client.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "developer");

        try {
            beanLocal.setClientStatus(client, null);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ClientServiceBean#setClientStatus(Client, ClientStatus)}.
     *
     * Caused by: Some of manager is null.
     * Expected : {@link IllegalStateException}.
     *
     * @throws Exception into JUnit.
     */
    public void testSetClientStatus_WithNullManager() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Client client = beanLocal.createClient(TestHelper.createClient(0, null, null));
        createClient(client.getId());
        createUserClientMapping(client.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "developer");

        ClientStatus status = createClientStatus();

        TestHelper.injectPropertyValue(ClientServiceBean.class,
                "userMappingRetriever", beanLocal, null);

        try {
            beanLocal.setClientStatus(client, status);
            fail(T_ISE);
        } catch (IllegalStateException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ClientServiceBean#setClientStatus(Client, ClientStatus)}.
     *
     * Caused by: Fail services occurs.
     * Expected : {@link ClientServiceException}.
     *
     * @throws Exception into JUnit.
     */
    public void testSetClientStatus_WithFailService() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Client client = beanLocal.createClient(TestHelper.createClient(0, null, null));
        createClient(client.getId());
        createUserClientMapping(client.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "developer");

        ClientStatus status = createClientStatus();
        try {
            getClientDAO().setFlag(true);
            beanLocal.setClientStatus(client, status);
            fail(T_CSE);
        } catch (ClientServiceException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ClientServiceBean#setClientStatus(Client, ClientStatus)}.
     *
     * @throws Exception into JUnit.
     */
    public void testSetClientStatus_WithFailAuthorization() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Client client = beanLocal.createClient(TestHelper.createClient(0, null, null));
        createClient(client.getId());
        createUserClientMapping(client.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "dev");

        ClientStatus status = createClientStatus();
        try {
            beanLocal.setClientStatus(client, status);
            fail(T_AFE);
        } catch (AuthorizationFailedException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ClientServiceBean#setVerboseLogging(boolean)}.
     */
    public void testSetVerboseLogging() {
        assertFalse("Fail setup verbose logging", beanLocal.isVerboseLogging());
        beanLocal.setVerboseLogging(true);
        assertTrue("Fail setup verbose logging.", beanLocal.isVerboseLogging());
    }

    /**
     * Test for {@link ClientServiceBean#setLog(com.topcoder.util.log.Log)}.
     */
    public void testSetLog() {
        assertNotNull("Fail setup log", beanLocal.getLog());
        beanLocal.setLog(null);
        assertNull("Fail setup log", beanLocal.getLog());
    }

    /**
     * Initialize property in bean using injection mechanism.
     *
     * @param adminRole
     *      The name of admin role.
     * @param clientProjectUserRole
     *      The name of client project user role.
     */
    private void initRole(String adminRole, String clientProjectUserRole) {
        TestHelper.injectPropertyValue(ClientServiceBean.class, "administratorRole", beanLocal, adminRole);
        TestHelper.injectPropertyValue(ClientServiceBean.class,
                "clientAndProjectUserRole", beanLocal, clientProjectUserRole);
    }

    /**
     * Simple getter for Client DAO.
     *
     * @return client DAO instance.
     * @throws Exception into JUnit.
     */
    private MockClientDAO getClientDAO() throws Exception {
        DAOClientManager manager = (DAOClientManager) TestHelper.getPrivateField(ClientServiceBean.class,
                beanLocal, "clientManager");
        return (MockClientDAO) TestHelper.getPrivateField(DAOClientManager.class, manager, "clientDAO");
    }
}
