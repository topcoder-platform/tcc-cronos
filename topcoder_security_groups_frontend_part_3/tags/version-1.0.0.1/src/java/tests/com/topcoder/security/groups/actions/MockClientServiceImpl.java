/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import java.util.Arrays;
import java.util.List;

import com.topcoder.security.groups.model.Client;
import com.topcoder.security.groups.services.ClientService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.dto.ClientSearchCriteria;
import com.topcoder.security.groups.services.dto.PagedResult;

/**
 * Mock service implementation class for the demo usage.
 *
 * @author progloco
 * @version 1.0
 */
public class MockClientServiceImpl implements ClientService {
    /**
     * <p>
     * This method gets all clients. If not found, returns an empty list.
     * </p>
     *
     * @param id the id.
     * @throws SecurityGroupException If there are any errors during the
     *             execution of this method.
     * @return the Clients
     */
    public Client get(long id) throws SecurityGroupException {
        Client c = new Client();
        c.setName("gevak");
        return c;
    }

    /**
     * <p>
     * This method gets a client. If not found, returns null.
     * </p>
     *
     * @throws SecurityGroupException If there are any errors during the
     *             execution of this method.
     * @return the requested Client
     */
    public List<Client> getAllClients() throws SecurityGroupException {
        Client c = new Client();
        c.setName("gevak");
        return Arrays.asList(c);
    }

    /**
     * <p>
     * This method retrieves the list of Clients for the given search criteria
     * and paging requirements. The result will not be null, and the inner list
     * will also never be null.
     * </p>
     *
     * @param criteria the filter with the search criteria: if null, assume no
     *            filtering.
     * @param page the 1 based number of the page to return. If 0, then all
     *            pages are returned.
     * @param pageSize the size of the page to return. Will be a positive
     *            number, but ignored if page=0.
     * @throws SecurityGroupException If there are any errors during the
     *             execution of this method.
     * @return A result object with the list of applicable clients and the page
     *         and total data
     */
    public PagedResult<Client> search(ClientSearchCriteria criteria,
            int pageSize, int page) throws SecurityGroupException {
        return null;
    }

}
