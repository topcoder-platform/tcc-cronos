/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.depth;

import com.topcoder.timetracker.client.Client;
import com.topcoder.timetracker.client.ClientPersistenceException;
import com.topcoder.timetracker.client.Depth;
import com.topcoder.timetracker.client.Helper;
import com.topcoder.timetracker.client.db.ClientColumnName;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;

/**
 * <p>
 * This class represents the client ID only depth. It extends Depth. Search with this depth, the result will contain
 * client name and Id only.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This class is thread safe by being immutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public class ClientIDOnlyDepth extends Depth {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = -4385507092346357615L;

	/**
     * <p>
     * Represents the field will be presented in the search result. It's static and immutable, set to
     * {ColumnName.ID.tostring(),ColumnName.NAME.tostring()}initially. It will be reference in the constructor.
     * </p>
     */
    private static final String[] fields = {ClientColumnName.ID.getName(), ClientColumnName.NAME.getName()};

    /**
     * <p>
     * Constructs ClientIDOnlyDepth.
     * </p>
     */
    public ClientIDOnlyDepth() {
        super(fields, false, false, false, false);
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
            return client;
        } catch (InvalidCursorStateException icse) {
            throw new ClientPersistenceException("Error occurred when retrieve data from the result set.", icse);
        }
    }
}
