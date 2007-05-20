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
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;

/**
 * <p>
 * This class represents the client only depth. It extends Depth. Search with this depth, the result will contain only
 * the client information.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This class is thread safe by being immutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public class ClientOnlyDepth extends Depth {

    /**
     * Automatically generated unique ID for use with serialization.
     */
    private static final long serialVersionUID = 6732977006276150592L;

    /**
     * <p>
     * Represents the field will be presented in the search result. It's static and immutable. It will be initially set
     * to included all the column name except ALL, PROJECT_ID. It will be reference in the constructor.
     * </p>
     */
    private static final String[] fields = {
            ClientColumnName.ID.getName(), ClientColumnName.NAME.getName(), ClientColumnName.COMPANY_ID.getName(),
            ClientColumnName.CREATION_DATE.getName(), ClientColumnName.CREATION_USER.getName(),
            ClientColumnName.MODIFICATION_DATE.getName(), ClientColumnName.MODIFICATION_USER.getName(),
            ClientColumnName.PAYMENT_TERM_ID.getName(), ClientColumnName.ACTIVE.getName(),
            ClientColumnName.SALES_TAX.getName(), ClientColumnName.START_DATE.getName(),
            ClientColumnName.END_DATE.getName()
    };

    /**
     * <p>
     * Constructs ClientOnlyDepth.
     * </p>
     */
    public ClientOnlyDepth() {
        super(fields, false, false, true, true);
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

            return client;
        } catch (InvalidCursorStateException icse) {
            throw new ClientPersistenceException("Error occurred when retrieve data from the result set.", icse);
        }
    }
}
