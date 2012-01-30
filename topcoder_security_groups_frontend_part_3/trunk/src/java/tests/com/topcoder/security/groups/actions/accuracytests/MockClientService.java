package com.topcoder.security.groups.actions.accuracytests;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.security.groups.model.Client;
import com.topcoder.security.groups.services.ClientService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.dto.ClientSearchCriteria;
import com.topcoder.security.groups.services.dto.PagedResult;

public class MockClientService implements ClientService {

	public Client get(long id) throws SecurityGroupException {
		
		return null;
	}

	public List<Client> getAllClients() throws SecurityGroupException {
		List<Client> clients = new ArrayList<Client>();
		Client client = new Client();
		client.setId(2l);
		clients.add(client);
		return clients;
	}

	public PagedResult<Client> search(ClientSearchCriteria criteria,
			int pageSize, int page) throws SecurityGroupException {
		
		return null;
	}

}
