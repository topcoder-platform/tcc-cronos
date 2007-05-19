/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.common.CommonManagementException;
import com.topcoder.timetracker.common.CommonManagerConfigurationException;
import com.topcoder.timetracker.common.CommonManager;
import com.topcoder.timetracker.common.SimpleCommonManager;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.AddressFilterFactory;
import com.topcoder.timetracker.contact.AddressManager;
import com.topcoder.timetracker.contact.AddressType;
import com.topcoder.timetracker.contact.AssociationException;
import com.topcoder.timetracker.contact.AuditException;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ContactFilterFactory;
import com.topcoder.timetracker.contact.ContactManager;
import com.topcoder.timetracker.contact.ContactType;
import com.topcoder.timetracker.contact.InvalidPropertyException;
import com.topcoder.timetracker.contact.PersistenceException;
import com.topcoder.timetracker.contact.ejb.AddressManagerLocalDelegate;
import com.topcoder.timetracker.contact.ejb.ContactManagerLocalDelegate;
import com.topcoder.timetracker.project.DataAccessException;
import com.topcoder.timetracker.project.Project;
import com.topcoder.timetracker.project.ProjectUtility;
import com.topcoder.timetracker.project.UnrecognizedEntityException;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.objectfactory.ObjectFactory;

/**
 * <p>
 * This class is the main class of this component. It provides various methods to retrieve and modify the client
 * information. And it provides method to allow whether audit on the specified method. It can be configured with
 * ConfigurationManager.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This class is thread-safe by being immutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public class ClientUtilityImpl implements ClientUtility {

    /**
     * <p>
     * Represents the key for id generator name.
     * </p>
     */
    private static final String ID_NAME_KEY = "IDName";

    /**
     * <p>
     * Represents the key for dao used for object factory.
     * </p>
     */
    private static final String DAO_KEY = "ClientDAO";

    /**
     * <p>
     * Represents the key for object factory namespace.
     * </p>
     */
    private static final String OBJECT_FACTORY_KEY = "ObjectFactoryNamespace";

    /**
     * <p>
     * Represents the key for address manager used for object factory.
     * </p>
     */
    private static final String ADDRESS_MANAGER_KEY = "AddressManager";

    /**
     * <p>
     * Represents the key for contact manager used for object factory.
     * </p>
     */
    private static final String CONTACT_MANAGER_KEY = "ContactManager";

    /**
     * <p>
     * Represents the key for common manager used for object factory.
     * </p>
     */
    private static final String COMMON_MANAGER_KEY = "CommonManager";

    /**
     * <p>
     * Represents the key for project utility for object factory.
     * </p>
     */
    private static final String PROJECT_UTILITY_KEY = "ProjectUtility";

    /**
     * <p>
     * Represents the ClientDAO used by this manager. This variable is set in constructor,&nbsp; is immutable and never
     * be null. It is referenced by all methods of ClientUtility.
     * </p>
     */
    private final ClientDAO dao;

    /**
     * <p>
     * Represents the IDGenerator used by this utility. This variable is set in constructor,&nbsp; is immutable and
     * never be null. It is referenced by the addClient method.
     * </p>
     */
    private final IDGenerator idGenerator;

    /**
     * <p>
     * Represents the ContactManager will be used. This variable is set in the construtor,&nbsp; is immutable and never
     * be null. It will be referenced by retrieveClient(s), getAllClients and searchClient methods.
     * </p>
     */
    private final ContactManager contactManager;

    /**
     * <p>
     * Represents the AuditManage will be used. This variable is set in the construtor,&nbsp; is immutable and never be
     * null. It will be referenced by retrieveClient(s), getAllClients and searchClient methods.
     * </p>
     */
    private final AddressManager addressManager;

    /**
     * <p>
     * Represents the AuditManage will be used. This variable is set in the construtor,&nbsp; is immutable and never be
     * null. It will be referenced by retrieveClient(s), getAllClients and searchClient methods.
     * </p>
     */
    private final CommonManager commonManager;

    /**
     * <p>
     * Represents the ProjectUtility will be used. This variable is set in the construtor,&nbsp; is immutable and never
     * be null. It will be referenced by retrieveClient(s), getAllClients and searchClient methods.
     * </p>
     */
    private final ProjectUtility projectUtility;

    /**
     * <p>
     * Constructs the ClientUtility with default namespace ClientUtility.class.getName().
     * </p>
     *
     * @throws ConfigurationException if the configuration is incorrect.
     */
    public ClientUtilityImpl() throws ConfigurationException {
        this(ClientUtilityImpl.class.getName());
    }

    /**
     * <p>
     * Constructs the ClientUtility with given namespace.
     * </p>
     *
     * @param namespace non null, non empty namespace used to construct the ClientUtility
     *
     * @throws ConfigurationException if the configuration is incorrect.
     */
    public ClientUtilityImpl(String namespace) throws ConfigurationException {
        Helper.checkString(namespace, "namespace");

        String idName = Helper.getConfigString(namespace, ID_NAME_KEY, true);

        try {
            this.idGenerator = IDGeneratorFactory.getIDGenerator(idName);
        } catch (IDGenerationException e) {
            throw new ConfigurationException("An error occurs while retrieving ID sequence configuration.", e);
        }

        String objectFactoryNamespace = Helper.getConfigString(namespace, OBJECT_FACTORY_KEY, true);

        // use object factory to create the instance.
        ObjectFactory of = Helper.getObjectFactory(objectFactoryNamespace);

        Object created = null;

        // create ClientDAO
        try {
            // ClientDAO is a required
            created = Helper.createObjectViaObjectFactory(of, DAO_KEY);

            this.dao = (ClientDAO) created;
        } catch (ClassCastException cce) {
            throw new ConfigurationException("Error when creating dao.", cce);
        }

        // create AddressManager
        try {
            created = Helper.createObjectViaObjectFactory(of, ADDRESS_MANAGER_KEY);

            this.addressManager = (created == null) ? (new AddressManagerLocalDelegate()) : (AddressManager) created;
        } catch (com.topcoder.timetracker.contact.ConfigurationException ce) {
            throw new ConfigurationException("Error when creating address manager.", ce);
        } catch (ClassCastException cce) {
            throw new ConfigurationException("Error when creating address manager.", cce);
        }

        // create CommonManager
        try {
            created = Helper.createObjectViaObjectFactory(of, COMMON_MANAGER_KEY);

            // no other common manager can be created, so get it from the common manager.
            this.commonManager = (created == null) ? (new SimpleCommonManager()) : (CommonManager) created;
        } catch (CommonManagerConfigurationException cmce) {
            throw new ConfigurationException("Error when creating common manager.", cmce);
        } catch (ClassCastException cce) {
            throw new ConfigurationException("Error when creating common manager.", cce);
        }

        // create ContactManager
        try {
            created = Helper.createObjectViaObjectFactory(of, CONTACT_MANAGER_KEY);

            this.contactManager = (created == null) ? (new ContactManagerLocalDelegate()) : (ContactManager) created;
        } catch (com.topcoder.timetracker.contact.ConfigurationException ce) {
            throw new ConfigurationException("Error when creating contact manager.", ce);
        } catch (ClassCastException cce) {
            throw new ConfigurationException("Error when creating contact manager.", cce);
        }

        // create ProjectUtility
        try {
            created = Helper.createObjectViaObjectFactory(of, PROJECT_UTILITY_KEY);

            // no no-arg constructor for ProjectUtilityDelegate
            this.projectUtility = (ProjectUtility) created;
        } catch (ClassCastException cce) {
            throw new ConfigurationException("Error when creating project utility.", cce);
        }
    }

    /**
     * <p>
     * Add the given Client. Also add the associate projects and associate the contact and address.
     * </p>
     *
     * @param client non null client to be added
     * @param doAudit whether audit this action
     *
     * @throws IllegalArgumentException if the client is null
     * @throws ClientPersistenceException if it is thrown by the dao
     * @throws InvalidClientIDException if any exception occurs while generating ID
     * @throws InvalidClientPropertyException if the properties of given Client is invalid
     * @throws ClientAuditException if it encounters exception when audit
     */
    public void addClient(Client client, boolean doAudit)
        throws ClientPersistenceException, InvalidClientIDException, InvalidClientPropertyException,
            ClientAuditException {
        Helper.checkNull(client, "client");

        addClients(new Client[]{client}, doAudit);
    }

    /**
     * <p>
     * Add the given Clients. Also add the associate projects and associate the contact and address.
     * </p>
     *
     * @param clients non null, possible empty clients to be added
     * @param doAudit whether audit on this action
     *
     * @throws InvalidClientPropertyException if the properties of given Client is invalid
     * @throws IllegalArgumentException if the client is null or any client contained is null
     * @throws ClientPersistenceException if it is thrown by the dao
     * @throws InvalidClientIDException if any exception occurs while generating ID
     * @throws ClientAuditException if it encounters exception when audit
     */
    public void addClients(Client[] clients, boolean doAudit)
        throws InvalidClientPropertyException, ClientPersistenceException, InvalidClientIDException,
            ClientAuditException {
        Helper.checkArray(clients, "clients");

        for (int i = 0; i < clients.length; i++) {
            setClientId(clients[i]);
            checkClient(clients[i]);
        }

        try {
            // add the clients
            dao.addClients(clients, doAudit);

            for (int i = 0; i < clients.length; i++) {
                // add the association, such as add to the address manager, contact manager and add the project
                addAssociation(clients[i], doAudit);
                clients[i].setChanged(false);
            }
        } catch (BatchOperationException boe) {
            // if BatchOperationException is thrown, only a piece of the client is added.
            boolean[] result = boe.getResult();

            for (int i = 0; i < result.length; i++) {
                if (result[i]) {
                    addAssociation(clients[i], doAudit);
                }
            }

            throw boe;
        }
    }

    /**
     * <p>
     * Remove the Client with given id. Remove the associate projects and de-associate from the contact
     * manager and address manager.
     * </p>
     *
     * @param id the id of the Client
     * @param doAudit whether audit on this action
     *
     * @throws IllegalArgumentException if the id is not positive
     * @throws ClientPersistenceException if any exception occurs
     * @throws ClientAuditException if it encounters exception when audit
     * @throws PropertyOperationException if any error occurred when retrieve the client
     */
    public void removeClient(long id, boolean doAudit)
        throws ClientAuditException, ClientPersistenceException, PropertyOperationException {
        Helper.checkPositive(id, "id");

        removeClients(new long[]{id}, doAudit);
    }

    /**
     * <p>
     * Remove the Clients with given ids. Remove the associate projects and de-associate from the contact
     * manager and address manager.
     * </p>
     *
     * @param ids the non null, possible empty ids of the Clients
     * @param doAudit whether audit on this action
     *
     * @throws IllegalArgumentException if any id not positive
     * @throws ClientPersistenceException if any exception occurs
     * @throws ClientAuditException if it encounters exception when audit
     * @throws PropertyOperationException if any error happened about the property
     */
    public void removeClients(long[] ids, boolean doAudit)
        throws ClientPersistenceException, ClientAuditException, PropertyOperationException {
        Helper.checkPositiveArray(ids, "ids");

        Client[] clients = retrieveClients(ids);

        for (int i = 0; i < ids.length; i++) {
            // remove from the address manager, contact manager, project.
            removeAssociation(clients[i], doAudit);
            clients[i].setChanged(false);
        }

        // remove from the database.
        dao.removeClients(ids, doAudit);
    }

    /**
     * <p>
     * Retrieve the given Client. Retrieve the contact, address, payment term and projects too.
     * </p>
     *
     * @param id the id of the client to be retrieved
     *
     * @return the client with the given id, null if the client if not found
     *
     * @throws IllegalArgumentException if the id not positive
     * @throws ClientPersistenceException if it is thrown by the dao
     * @throws PropertyOperationException if it encounters exception when trying to get the properties
     */
    public Client retrieveClient(long id) throws ClientPersistenceException, PropertyOperationException {
        Client[] clients = retrieveClients(new long[]{id});
        if (clients.length != 0) {
            return clients[0];
        }
        return null;
    }

    /**
     * <p>
     * Retrieve the Clients with given ids. Retrieve the contact, address, payment term and projects too.
     * </p>
     *
     * @param ids the non null, possible empty ids of the Clients
     *
     * @return the non null Clients with given id, the containing client may be null if not found
     *
     * @throws ClientPersistenceException if it is thrown by the dao
     * @throws IllegalArgumentException if any id not positive
     * @throws PropertyOperationException if it encounters exception when trying to get the properties
     */
    public Client[] retrieveClients(long[] ids) throws PropertyOperationException, ClientPersistenceException {
        Client[] clients = dao.retrieveClients(ids);

        for (int i = 0; i < clients.length; i++) {
            if (clients[i] == null) {
                continue;
            }
            setContact(clients[i]);
            setAddress(clients[i]);
            setPaymentTerm(clients[i]);
            setProjects(clients[i]);
            clients[i].setChanged(false);
        }

        return clients;
    }

    /**
     * <p>
     * Update the given Client.  Also set the contact, address, payment, and projects.
     * </p>
     *
     * @param client non null client to be updated
     * @param doAudit whether audit on this action
     *
     * @throws IllegalArgumentException if the client is null
     * @throws ClientPersistenceException if it is thrown by the dao
     * @throws InvalidClientPropertyException if the properties of given Client is invalid
     * @throws ClientAuditException if it encounters exception when audit
     * @throws PropertyOperationException if any error occurred with property
     */
    public void updateClient(Client client, boolean doAudit)
        throws ClientPersistenceException, InvalidClientPropertyException, ClientAuditException,
            PropertyOperationException {
        Helper.checkNull(client, "client");

        updateClients(new Client[]{client}, doAudit);
    }

    /**
     * <p>
     * Update the given Clients.
     * </p>
     *
     * @param clients non null, possible empty clients to be updated
     * @param doAudit whether audit on this action
     *
     * @throws IllegalArgumentException if any client is null
     * @throws ClientPersistenceException if it is thrown by the dao
     * @throws InvalidClientPropertyException if the properties of given Client is invalid
     * @throws ClientAuditException if it encounters exception when audit
     * @throws PropertyOperationException if any error occurred with property
     */
    public void updateClients(Client[] clients, boolean doAudit)
        throws ClientPersistenceException, InvalidClientPropertyException, ClientAuditException,
            PropertyOperationException {
        Helper.checkArray(clients, "clients");

        for (int i = 0; i < clients.length; i++) {
            checkClient(clients[i]);
        }

        // get the client list that have been changed.
        List list = new ArrayList();

        for (int i = 0; i < clients.length; i++) {
            if (clients[i].isChanged()) {
                list.add(clients[i]);
            }
        }

        clients = (Client[]) list.toArray(new Client[list.size()]);

        for (int i = 0; i < clients.length; i++) {
            // remove from the address manager, contact manager
            // remove the projects
            removeAssociation(clients[i], doAudit);
        }

        dao.updateClients(clients, doAudit);

        for (int i = 0; i < clients.length; i++) {
            // add to the address manager, contact manager
            // add the projects.
            addAssociation(clients[i], doAudit);
            clients[i].setChanged(false);
        }
    }

    /**
     * <p>
     * Retrieve all the Clients. Also set the contact, address, payment, and projects.
     * </p>
     *
     * @return Non null, possible empty array containing all non null clients
     *
     * @throws ClientPersistenceException if it is thrown by the dao
     * @throws PropertyOperationException if it encounters exception when trying to get the properties
     */
    public Client[] getAllClients() throws PropertyOperationException, ClientPersistenceException {
        Client[] clients = dao.getAllClients();

        for (int i = 0; i < clients.length; i++) {
            // set the property
            setAddress(clients[i]);
            setContact(clients[i]);
            setPaymentTerm(clients[i]);
            setProjects(clients[i]);
            clients[i].setChanged(false);
        }

        return clients;
    }

    /**
     * <p>
     * Search the Clients with the given Filter and Depth.
     * </p>
     *
     * @param filter non null filter
     * @param depth non null depth
     *
     * @return non null, possible empty array containing all client with given condition
     *
     * @throws ClientPersistenceException if any exception occurs
     * @throws IllegalArgumentException if filter or depth is null
     * @throws PropertyOperationException if it encounters exception when trying to get the properties
     */
    public Client[] searchClient(Filter filter, Depth depth)
        throws ClientPersistenceException, PropertyOperationException {
        Client[] clients = depth.buildResult(dao.searchClient(filter, depth));

        for (int i = 0; i < clients.length; i++) {
            if (depth.useAddress()) {
                setAddress(clients[i]);
            }

            if (depth.useContact()) {
                setContact(clients[i]);
            }

            if (clients[i].getPaymentTerm() != null) {
                setPaymentTerm(clients[i]);
            }

            if (depth.useProjects()) {
                setProjects(clients[i]);
            }

            if (depth.onlyProjectsIdName()) {
                // only set project id and name.
                setProjectsIdName(clients[i]);
            }
            clients[i].setChanged(false);
        }

        return clients;
    }

    /**
     * <p>
     * Get all projects of the Client.
     * </p>
     *
     * @param clientId id of the client
     *
     * @return the non null, possible empty projects of the client
     *
     * @throws IllegalArgumentException if client id is not positive
     * @throws ClientPersistenceException if any exception occurs
     */
    public Project[] getAllProjectsOfClient(long clientId)
        throws ClientPersistenceException {
        Helper.checkPositive(clientId, "clientId");

        // get the ids
        long[] ids = dao.getAllProjectIDsOfClient(clientId);

        // retrieve the projects
        Project[] projects = new Project[ids.length];

        for (int i = 0; i < ids.length; i++) {
            try {
                projects[i] = getProjectWithId(ids[i]);
            } catch (PropertyOperationException poe) {
                throw new ClientPersistenceException("Error getting the project.", poe);
            }
        }

        return projects;
    }

    /**
     * <p>
     * Remove the project from the Client.
     * </p>
     *
     * @param client the client
     * @param projectId the id of project
     * @param doAudit whether audit on this action
     *
     * @throws IllegalArgumentException if any id not positive
     * @throws ClientPersistenceException if any exception occurs
     * @throws ClientAuditException if it encounters exception when audit
     */
    public void removeProjectFromClient(Client client, long projectId, boolean doAudit)
        throws ClientAuditException, ClientPersistenceException {
        Helper.checkNull(client, "client");
        Helper.checkPositive(projectId, "projectId");

        // remove from the database
        dao.removeProjectFromClient(client, projectId, doAudit);

        // remove from the client's project list
        Project[] projects = client.getProjects();

        List list = new ArrayList();

        for (int i = 0; i < projects.length; i++) {
            if (projects[i].getId() != projectId) {
                list.add(projects[i]);
            }
        }

        client.setProjects((Project[]) list.toArray(new Project[list.size()]));
    }

    /**
     * <p>
     * Add the project to the client.
     * </p>
     *
     * @param client the positive id of the Client
     * @param project the non null project
     * @param doAudit whether audit on this action
     *
     * @throws IllegalArgumentException if project or client is null
     * @throws ClientPersistenceException if any exception occurs
     * @throws ClientAuditException if it encounters exception when audit
     */
    public void addProjectToClient(Client client, Project project, boolean doAudit)
        throws ClientPersistenceException, ClientAuditException {
        Helper.checkNull(client, "client");
        Helper.checkNull(project, "project");

        // add the the database
        dao.addProjectToClient(client, project, doAudit);

        // add the project to the client's project list.
        Project[] projects = client.getProjects();
        Arrays.asList(projects);

        List list = new ArrayList(Arrays.asList(projects));

        list.add(project);

        client.setProjects(getAllProjectsOfClient(client.getId()));
    }

    /**
     * <p>
     * Set the contact of the Client.
     * </p>
     *
     * @param client non null client
     *
     * @throws PropertyOperationException if any exception is thrown
     */
    private void setContact(Client client) throws PropertyOperationException {
        try {
            Contact[] contacts = contactManager.searchContacts(ContactFilterFactory.
                    createEntityIDFilter(client.getId(), ContactType.CLIENT));

            if (contacts.length != 0) {
                client.setContact(contacts[0]);
            }
        } catch (AssociationException ae) {
            throw new PropertyOperationException("Error get the property.", ae);
        } catch (PersistenceException pe) {
            throw new PropertyOperationException("Error get the property.", pe);
        }
    }

    /**
     * <p>
     * Set the projects of the Client.
     * </p>
     *
     * @param client non null client
     *
     * @throws PropertyOperationException if any exception is thrown
     * @throws ClientPersistenceException if it is thrown by getAllProjectsOfClient
     */
    private void setProjects(Client client) throws ClientPersistenceException, PropertyOperationException {
        long[] ids = dao.getAllProjectIDsOfClient(client.getId());

        Project[] projects = new Project[ids.length];

        for (int i = 0; i < ids.length; i++) {
            projects[i] = getProjectWithId(ids[i]);
        }

        client.setProjects(projects);
    }

    /**
     * <p>
     * Set the payment term of the Client.
     * </p>
     *
     * @param client non null client
     *
     * @throws PropertyOperationException if any exception is thrown
     */
    private void setPaymentTerm(Client client) throws PropertyOperationException {
        try {
            client.setPaymentTerm(commonManager.retrievePaymentTerm(client.getPaymentTerm().getId()));
        } catch (CommonManagementException cme) {
            throw new PropertyOperationException("Error get the client property.", cme);
        }
    }

    /**
     * <p>
     * Set the name of projects of the Client.
     * </p>
     *
     * @param client non null client
     *
     * @throws PropertyOperationException if any exception is thrown
     * @throws ClientPersistenceException if it is thrown by getAllProjectsOfClient
     */
    private void setProjectsIdName(Client client) throws PropertyOperationException, ClientPersistenceException {
        // get all the information first.
        setProjects(client);
    }

    /**
     * <p>
     * Set the address of the Client.
     * </p>
     *
     * @param client non null client
     *
     * @throws PropertyOperationException if any exception is thrown
     */
    private void setAddress(Client client) throws PropertyOperationException {
        try {
            Address[] addresses = addressManager.searchAddresses(AddressFilterFactory.
                    createEntityIDFilter(client.getId(), AddressType.CLIENT));
            if (addresses.length != 0) {
                client.setAddress(addresses[0]);
            }
        } catch (AssociationException ae) {
            throw new PropertyOperationException("Error retrieve the address.", ae);
        } catch (PersistenceException pe) {
            throw new PropertyOperationException("Error retrieve the address.", pe);
        }
    }

    /**
     * Throw InvalidClientPropertyException if: The company id &lt;= 0 Or description/name is null or empty(trim'd) Or
     * the creation/modification user is null or empty(trim'd)  Or the salesTax &lt; 0 Or the start/end Date,
     * paymentTerm, contact or address is null.
     *
     * @param client the client to check
     *
     * @throws InvalidClientPropertyException if the client is invalid
     */
    private void checkClient(Client client) throws InvalidClientPropertyException {
        if (client.getId() <= 0) {
            throw new InvalidClientPropertyException("Invalid client id.");
        }
        if (client.getCompanyId() <= 0) {
            throw new InvalidClientPropertyException("Client with negative company id is invalid.");
        }

        if ((client.getName() == null) || (client.getName().trim().length() == 0)) {
            throw new InvalidClientPropertyException("Client with null or empty name is invalid.");
        }

        if ((client.getCreationUser() == null) || (client.getCreationUser().trim().length() == 0)) {
            throw new InvalidClientPropertyException("client with null or empty creation user name is invalid.");
        }

        if ((client.getModificationUser() == null) || (client.getModificationUser().trim().length() == 0)) {
            throw new InvalidClientPropertyException("Client with null or empty modification user name is invalid.");
        }

        if (client.getSalesTax() < 0) {
            throw new InvalidClientPropertyException("Client with negative sales tax is invalid.");
        }

        if (client.getStartDate() == null) {
            throw new InvalidClientPropertyException("Client with null start date is invalid.");
        }

        if (client.getEndDate() == null) {
            throw new InvalidClientPropertyException("Client with null end date is invalid.");
        }

        if (client.getPaymentTerm() == null) {
            throw new InvalidClientPropertyException("Client with null pay term is invalid.");
        }

        if (client.getPaymentTerm().getId() <= 0) {
            throw new InvalidClientPropertyException("Client with payment with invalid id.");
        }
        if (client.getContact() == null) {
            throw new InvalidClientPropertyException("Client with null contact is invalid.");
        }

        if (client.getAddress() == null) {
            throw new InvalidClientPropertyException("Client with null address is invalid.");
        }
    }

    /**
     * Set the id of the client.
     *
     * @param client if client to be set
     *
     * @throws InvalidClientIDException if there is error when generating the id
     */
    private void setClientId(Client client) throws InvalidClientIDException {
        try {
            client.setId(idGenerator.getNextID());
        } catch (IllegalArgumentException iae) {
            throw new InvalidClientIDException("Invalid id possible a negative one or zero.", iae);
        } catch (IDGenerationException ige) {
            throw new InvalidClientIDException("Error when generating id.", ige);
        }
    }

    /**
     * Add the client to the Contact Manager.
     *
     * @param client the client to be added
     * @param doAudit audit flag
     *
     * @throws ClientPersistenceException if there is error when store the data
     * @throws ClientAuditException when there is error doing audit
     */
    private void addToContactManager(Client client, boolean doAudit)
        throws ClientPersistenceException, ClientAuditException {
        try {
            // if the contact does not exist in the database, add it
            if (client.getContact().getId() <= 0
                    || contactManager.retrieveContact(client.getContact().getId()) == null) {
                contactManager.addContact(client.getContact(), doAudit);
            }

            // associate the client and contact.
            contactManager.associate(client.getContact(), client.getId(), doAudit);
        } catch (AssociationException ae) {
            throw new ClientPersistenceException("Error associating contact information.", ae);
        } catch (PersistenceException pe) {
            throw new ClientPersistenceException("Error store the data.", pe);
        } catch (InvalidPropertyException ipe) {
            throw new ClientPersistenceException("The property of the client is invalid.", ipe);
        } catch (com.topcoder.timetracker.contact.IDGenerationException ipe) {
            throw new ClientPersistenceException("Error generating the id.");
        } catch (AuditException ae) {
            throw new ClientAuditException("Error auditing.", ae);
        }
    }

    /**
     * Delete the client from the Contact Manager.
     *
     * @param client the client to be added
     * @param doAudit audit flag
     *
     * @throws ClientPersistenceException if there is error when store the data
     * @throws ClientAuditException when there is error doing audit
     */
    private void deleteFromContactManager(Client client, boolean doAudit)
        throws ClientPersistenceException, ClientAuditException {
        try {
            contactManager.deassociate(client.getContact(), client.getId(), doAudit);
        } catch (AssociationException ae) {
            throw new ClientPersistenceException("Error deassociating contact information.", ae);
        } catch (PersistenceException pe) {
            throw new ClientPersistenceException("Error store the data.", pe);
        } catch (InvalidPropertyException ipe) {
            throw new ClientPersistenceException("The property is invalid.", ipe);
        } catch (AuditException ae) {
            throw new ClientAuditException("Error auditing.", ae);
        }
    }

    /**
     * Add the client to the AddressManager.
     *
     * @param client the client to be added
     * @param doAudit audit flag
     *
     * @throws ClientPersistenceException if there is error when store the data
     * @throws ClientAuditException when there is error doing audit
     */
    private void addToAddressManager(Client client, boolean doAudit)
        throws ClientPersistenceException, ClientAuditException {
        try {
            // if the address does not exist in the database, add it
            if (client.getAddress().getId() <= 0
                    || addressManager.retrieveAddress(client.getAddress().getId()) == null) {
                addressManager.addAddress(client.getAddress(), doAudit);
            }

            // associate the address
            addressManager.associate(client.getAddress(), client.getId(), doAudit);
        } catch (AssociationException ae) {
            throw new ClientPersistenceException("Error associating address information.", ae);
        } catch (PersistenceException pe) {
            throw new ClientPersistenceException("Error store the data.", pe);
        } catch (InvalidPropertyException ipe) {
            throw new ClientPersistenceException("The property of the client is invalid.", ipe);
        } catch (com.topcoder.timetracker.contact.IDGenerationException ipe) {
            throw new ClientPersistenceException("Error generating the id.");
        } catch (AuditException ae) {
            throw new ClientAuditException("Error auditing.", ae);
        }
    }

    /**
     * Delete the client from the AddressManager.
     *
     * @param client the client to be added
     * @param doAudit audit flag
     *
     * @throws ClientPersistenceException if there is error when store the data
     * @throws ClientAuditException when there is error doing audit
     */
    private void deleteFromAddressManager(Client client, boolean doAudit)
        throws ClientPersistenceException, ClientAuditException {
        try {
            addressManager.deassociate(client.getAddress(), client.getId(), doAudit);
        } catch (AssociationException ae) {
            throw new ClientPersistenceException("Error deassociating address information.", ae);
        } catch (PersistenceException pe) {
            throw new ClientPersistenceException("Error persistence.", pe);
        } catch (InvalidPropertyException ipe) {
            throw new ClientPersistenceException("The property of the client is invalid.", ipe);
        } catch (AuditException ae) {
            throw new ClientAuditException("Error auditing.", ae);
        }
    }

    /**
     * Get the project with specified id.
     *
     * @param id the id
     *
     * @return the project
     *
     * @throws PropertyOperationException if any error occurred
     */
    private Project getProjectWithId(long id) throws PropertyOperationException {
        try {
            return projectUtility.getProject(id);
        } catch (UnrecognizedEntityException uee) {
            throw new PropertyOperationException("Error get the entity.", uee);
        } catch (DataAccessException dae) {
            throw new PropertyOperationException("Error access the database.", dae);
        }
    }

    /**
     * Remove the association of the client, such as the project, address, contact.
     *
     * @param client the client to be removed
     * @param doAudit audit flag
     *
     * @throws PropertyOperationException if the client has wrong property
     * @throws ClientPersistenceException if there error with the dao
     * @throws ClientAuditException if there is error doing the audit
     */
    private void removeAssociation(Client client, boolean doAudit)
        throws PropertyOperationException, ClientPersistenceException, ClientAuditException {
        Client oldClient = retrieveClient(client.getId());

        if (oldClient == null) {
            return;
        }
        Project[] projects = oldClient.getProjects();

        for (int i = 0; i < projects.length; i++) {
            // remove the associations
            removeProjectFromClient(oldClient, projects[i].getId(), doAudit);
        }

        // remove from the address manager and contact manager
        deleteFromAddressManager(oldClient, doAudit);
        deleteFromContactManager(oldClient, doAudit);
    }

    /**
     * Add the association of the client, such as the project, address, contact.
     *
     * @param client the client to be add
     * @param doAudit audit flag
     *
     * @throws ClientPersistenceException if there error with the dao
     * @throws ClientAuditException if there is error doing the audit
     */
    private void addAssociation(Client client, boolean doAudit)
        throws ClientPersistenceException, ClientAuditException {
        Project[] projects = client.getProjects();

        for (int i = 0; i < projects.length; i++) {
            addProjectToClient(client, projects[i], doAudit);
        }

        addToAddressManager(client, doAudit);
        addToContactManager(client, doAudit);
    }
}
