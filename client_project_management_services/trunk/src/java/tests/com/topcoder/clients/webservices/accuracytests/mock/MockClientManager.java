package com.topcoder.clients.webservices.accuracytests.mock;

import java.util.List;

import com.topcoder.clients.manager.ClientEntityNotFoundException;
import com.topcoder.clients.manager.ClientManager;
import com.topcoder.clients.manager.ClientManagerException;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.search.builder.filter.Filter;

public class MockClientManager implements ClientManager {

    public Client createClient(Client arg0) throws ClientManagerException {
        Client c = new Client();
        c.setName("AAA");
        return c;
    }

    public ClientStatus createClientStatus(ClientStatus arg0) throws ClientManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public Client deleteClient(Client arg0) throws ClientEntityNotFoundException,
        ClientManagerException {
        Client c = new Client();
        c.setName("BBB");
        return c;
    }

    public ClientStatus deleteClientStatus(ClientStatus arg0) throws ClientManagerException,
        ClientEntityNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<ClientStatus> getAllClientStatuses() throws ClientManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public ClientStatus getClientStatus(long arg0) throws ClientManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Client> getClientsForStatus(ClientStatus arg0) throws ClientManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Client> retrieveAllClients() throws ClientManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public Client retrieveClient(long arg0) throws ClientManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Client> searchClients(Filter arg0) throws ClientManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Client> searchClientsByName(String arg0) throws ClientManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public Client setClientCodeName(Client arg0, String arg1) throws ClientManagerException,
        ClientEntityNotFoundException {
        Client c = new Client();
        c.setName("EEE");
        return c;
    }

    public Client setClientStatus(Client arg0, ClientStatus arg1) throws ClientManagerException,
        ClientEntityNotFoundException {
        Client c = new Client();
        c.setName("DDD");
        return c;
    }

    public Client updateClient(Client arg0) throws ClientManagerException,
        ClientEntityNotFoundException {
        Client c = new Client();
        c.setName("CCC");
        return c;
    }

    public ClientStatus updateClientStatus(ClientStatus arg0) throws ClientManagerException,
        ClientEntityNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

}
