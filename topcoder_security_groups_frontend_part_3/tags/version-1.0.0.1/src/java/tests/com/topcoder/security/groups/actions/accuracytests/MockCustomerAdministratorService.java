package com.topcoder.security.groups.actions.accuracytests;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.security.groups.model.Client;
import com.topcoder.security.groups.model.CustomerAdministrator;
import com.topcoder.security.groups.services.CustomerAdministratorService;
import com.topcoder.security.groups.services.EntityNotFoundException;
import com.topcoder.security.groups.services.SecurityGroupException;

public class MockCustomerAdministratorService implements
		CustomerAdministratorService {

	public long add(CustomerAdministrator customerAdministrator)
			throws SecurityGroupException {
		
		return 0;
	}

	public void delete(long id) throws EntityNotFoundException,
			SecurityGroupException {
		

	}

	public List<CustomerAdministrator> getAdministratorsForCustomer(
			long clientId) throws EntityNotFoundException,
			SecurityGroupException {
		
		return null;
	}

	public List<Client> getCustomersForAdministrator(long userId) {
		List<Client> clients = new ArrayList<Client>();
		Client client = new Client();
		client.setId(2l);
		clients.add(client);
		return clients;
	}

	public void update(CustomerAdministrator customerAdministrator)
			throws EntityNotFoundException, SecurityGroupException {
		

	}

}
