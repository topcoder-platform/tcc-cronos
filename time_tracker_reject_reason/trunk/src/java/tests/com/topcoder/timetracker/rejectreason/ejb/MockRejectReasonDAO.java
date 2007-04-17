/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.ejb;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.rejectreason.RejectReason;
import com.topcoder.timetracker.rejectreason.RejectReasonDAO;
import com.topcoder.timetracker.rejectreason.RejectReasonDAOException;

import java.util.ArrayList;
import java.util.List;


/**
 * This is a mock implementation of RejectReasonDAO interface. It simply records which method is called and all the
 * passed in parameters.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class MockRejectReasonDAO implements RejectReasonDAO {
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
     * @param rejectReason being recorded.
     * @param username begin recorded.
     * @param isAudit being recorded.
     *
     * @return null.
     *
     * @throws RejectReasonDAOException never.
     */
    public RejectReason createRejectReason(RejectReason rejectReason, String username, boolean isAudit)
        throws RejectReasonDAOException {
        method = "createRejectReason";
        params.clear();
        params.add(rejectReason);
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
     * @throws RejectReasonDAOException never.
     */
    public RejectReason retrieveRejectReason(long id) throws RejectReasonDAOException {
        method = "retrieveRejectReason";
        params.clear();
        params.add(new Long(id));

        return null;
    }

    /**
     * Records this method is called.
     *
     * @param rejectReason being recorded.
     * @param username begin recorded.
     * @param isAudit being recorded.
     *
     * @throws RejectReasonDAOException never.
     */
    public void updateRejectReason(RejectReason rejectReason, String username, boolean isAudit)
        throws RejectReasonDAOException {
        method = "updateRejectReason";
        params.clear();
        params.add(rejectReason);
        params.add(username);
        params.add(new Boolean(isAudit));
    }

    /**
     * Records this method is called.
     *
     * @param rejectReason being recorded.
     * @param username begin recorded.
     * @param isAudit being recorded.
     *
     * @throws RejectReasonDAOException never.
     */
    public void deleteRejectReason(RejectReason rejectReason, String username, boolean isAudit)
        throws RejectReasonDAOException {
        method = "deleteRejectReason";
        params.clear();
        params.add(rejectReason);
        params.add(username);
        params.add(new Boolean(isAudit));
    }

    /**
     * Records this method is called.
     *
     * @return null.
     *
     * @throws RejectReasonDAOException never.
     */
    public RejectReason[] listRejectReasons() throws RejectReasonDAOException {
        method = "listRejectReasons";
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
     * @throws RejectReasonDAOException never.
     */
    public RejectReason[] searchRejectReasons(Filter filter)
        throws RejectReasonDAOException {
        method = "searchRejectReasons";
        params.clear();
        params.add(filter);

        return null;
    }
}
