/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.ejb;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.rejectreason.RejectEmail;
import com.topcoder.timetracker.rejectreason.RejectEmailDAO;
import com.topcoder.timetracker.rejectreason.RejectEmailDAOException;

import java.util.ArrayList;
import java.util.List;


/**
 * This is a mock implementation of RejectEmailDAO interface. It simply records which method is called and all the
 * passed in parameters.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class MockRejectEmailDAO implements RejectEmailDAO {
    /** The name of the method last called. */
    private String method = null;

    /** The parameter list passed in. */
    private List params = new ArrayList();

    /**
     * Gets the name of the method last called.
     *
     * @return the name of the method last called.
     */
    public String getMethod() {
        return method;
    }

    /**
     * Gets the parameter list passed in.
     *
     * @return the parameter list passed in.
     */
    public List getParameters() {
        return params;
    }

    /**
     * Records this method is called.
     *
     * @param rejectEmail being recorded.
     * @param username begin recorded.
     * @param isAudit being recorded.
     *
     * @return null.
     *
     * @throws RejectEmailDAOException never.
     */
    public RejectEmail createRejectEmail(RejectEmail rejectEmail, String username, boolean isAudit)
        throws RejectEmailDAOException {
        method = "createRejectEmail";
        params.clear();
        params.add(rejectEmail);
        params.add(username);
        params.add(new Boolean(isAudit));

        return null;
    }

    /**
     * Records this method is called.
     *
     * @param id being recorded.
     *
     * @return null.
     *
     * @throws RejectEmailDAOException never.
     */
    public RejectEmail retrieveRejectEmail(long id) throws RejectEmailDAOException {
        method = "retrieveRejectEmail";
        params.clear();
        params.add(new Long(id));

        return null;
    }

    /**
     * Records this method is called.
     *
     * @param rejectEmail being recorded.
     * @param username begin recorded.
     * @param isAudit being recorded.
     *
     * @throws RejectEmailDAOException never.
     */
    public void updateRejectEmail(RejectEmail rejectEmail, String username, boolean isAudit)
        throws RejectEmailDAOException {
        method = "updateRejectEmail";
        params.clear();
        params.add(rejectEmail);
        params.add(username);
        params.add(new Boolean(isAudit));
    }

    /**
     * Records this method is called.
     *
     * @param rejectEmail being recorded.
     * @param username begin recorded.
     * @param isAudit being recorded.
     *
     * @throws RejectEmailDAOException never.
     */
    public void deleteRejectEmail(RejectEmail rejectEmail, String username, boolean isAudit)
        throws RejectEmailDAOException {
        method = "deleteRejectEmail";
        params.clear();
        params.add(rejectEmail);
        params.add(username);
        params.add(new Boolean(isAudit));
    }

    /**
     * Records this method is called.
     *
     * @return null.
     *
     * @throws RejectEmailDAOException never.
     */
    public RejectEmail[] listRejectEmails() throws RejectEmailDAOException {
        method = "listRejectEmails";
        params.clear();

        return null;
    }

    /**
     * Records this method is called.
     *
     * @param filter being recorded.
     *
     * @return null.
     *
     * @throws RejectEmailDAOException never.
     */
    public RejectEmail[] searchRejectEmails(Filter filter)
        throws RejectEmailDAOException {
        method = "searchRejectEmails";
        params.clear();
        params.add(filter);

        return null;
    }
}
