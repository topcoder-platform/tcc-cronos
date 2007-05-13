/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.depth;

import com.topcoder.timetracker.client.Client;
import com.topcoder.timetracker.client.ClientPersistenceException;
import com.topcoder.timetracker.client.Depth;
import com.topcoder.timetracker.client.Helper;
import com.topcoder.timetracker.client.db.ClientColumnName;
import com.topcoder.timetracker.common.PaymentTerm;
import com.topcoder.timetracker.project.Project;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;

/**
 * <p>
 * This class represents the client project depth. It extends Depth. Search with this depth, the result will contain
 * all clients and all projects.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This class is thread safe by being immutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public class ClientProjectDepth extends Depth {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = 8808544308167934429L;

	/**
     * <p>
     * Represents the field will be presented in the search result. It's static and immutable, set to initially. It
     * will be reference in the constructor.
     * </p>
     */
    private static final String[] fields = {ClientColumnName.ALL.getName()};

    /**
     * <p>
     * Constructs ClientProjectDepth.
     * </p>
     */
    public ClientProjectDepth() {
        super(fields, false, true, true, true);
    }

    /**
     * <p>
     * Build the client according to current row of the result set.
     * </p>
     *
     * @param result non null result set used to build result
     *
     * @return non null Client build from current row of result set
     *
     * @throws IllegalArgumentException if any arguments is null
     * @throws ClientPersistenceException if any error occurred
     */
    protected Client buildClient(CustomResultSet result)
        throws ClientPersistenceException {
        Helper.checkNull(result, "result");

        Client client = new Client();

        try {
            client.setId(result.getLong("client_id"));
            client.setName(result.getString("name"));
            client.setCompanyId(result.getLong("company_id"));
            client.setCreationDate(result.getDate("creation_date"));
            client.setCreationUser(result.getString("creation_user"));
            client.setModificationDate(result.getDate("modification_date"));
            client.setModificationUser(result.getString("modification_user"));

            PaymentTerm paymentTerm = new PaymentTerm();
            long paymentTermId = result.getLong("payment_term_id");
            paymentTerm.setId(paymentTermId);
            client.setPaymentTerm(paymentTerm);
            client.setActive((result.getInt("status") != 0) ? true : false);
            client.setSalesTax(result.getDouble("salestax"));
            client.setStartDate(result.getDate("start_date"));
            client.setEndDate(result.getDate("end_date"));

            long projectId = result.getLong("project_id");
            Project project = new Project();

            project.setId(projectId);
            client.setProjects(new Project[] {project});

            return client;
        } catch (InvalidCursorStateException icse) {
            throw new ClientPersistenceException("Error occurred when retrieve data from the result set.", icse);
        }
    }
}
