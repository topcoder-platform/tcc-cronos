package com.topcoder.clients.webservices.failuretests;

import java.util.List;

import com.topcoder.clients.manager.ClientEntityNotFoundException;
import com.topcoder.clients.manager.ClientManager;
import com.topcoder.clients.manager.ClientManagerException;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.search.builder.filter.Filter;

public class DummyClientManager implements ClientManager {

    public Client createClient(Client client) throws ClientManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public ClientStatus createClientStatus(ClientStatus status) throws ClientManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public Client deleteClient(Client client) throws ClientEntityNotFoundException, ClientManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public ClientStatus deleteClientStatus(ClientStatus status) throws ClientManagerException,
            ClientEntityNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<ClientStatus> getAllClientStatuses() throws ClientManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public ClientStatus getClientStatus(long id) throws ClientManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Client> getClientsForStatus(ClientStatus status) throws ClientManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Client> retrieveAllClients() throws ClientManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public Client retrieveClient(long id) throws ClientManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Client> searchClients(Filter filter) throws ClientManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Client> searchClientsByName(String name) throws ClientManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public Client setClientCodeName(Client client, String codeName) throws ClientManagerException,
            ClientEntityNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    public Client setClientStatus(Client client, ClientStatus status) throws ClientManagerException,
            ClientEntityNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    public Client updateClient(Client client) throws ClientManagerException, ClientEntityNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    public ClientStatus updateClientStatus(ClientStatus status) throws ClientManagerException,
            ClientEntityNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

}
