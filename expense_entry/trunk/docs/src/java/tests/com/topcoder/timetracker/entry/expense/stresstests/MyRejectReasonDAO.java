/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.stresstests;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.rejectreason.RejectReason;
import com.topcoder.timetracker.rejectreason.RejectReasonDAO;
import com.topcoder.timetracker.rejectreason.RejectReasonDAOException;

/**
 * A mocked class of RejectReasonDAO for testing.
 *
 * @author Chenhong
 * @version 3.2
 */
public class MyRejectReasonDAO implements RejectReasonDAO {

    /**
     * Represents the number of RejectReason created.
     */
    private static int count = 0;

    /**
     * It will store all the RejectReason instance.
     */
    private List list = new ArrayList();

    /**
     * Constructor.
     *
     * <p>
     * It will add some RejectReason instances.
     * </p>
     *
     */
    public MyRejectReasonDAO() {
        for (int i = 1; i <= 20; i++) {
            RejectReason reason = new RejectReason();
            reason.setCompanyId(1);
            reason.setId(i);

            list.add(reason);
        }
    }

    /**
     * @see com.topcoder.timetracker.rejectreason.RejectReasonDAO
     *      #createRejectReason(com.topcoder.timetracker.rejectreason.RejectReason, java.lang.String, boolean)
     */
    public RejectReason createRejectReason(RejectReason reason, String arg1, boolean arg2)
            throws RejectReasonDAOException {
        count++;
        reason.setId(count);

        list.add(reason);
        return reason;
    }

    /**
     * @see com.topcoder.timetracker.rejectreason.RejectReasonDAO#retrieveRejectReason(long)
     */
    public RejectReason retrieveRejectReason(long id) throws RejectReasonDAOException {
        for (int i = 0; i < list.size(); i++) {
            RejectReason reason = (RejectReason) list.get(i);

            if (reason.getId() == id) {
                return reason;
            }
        }

        return null;
    }

    /**
     * @see com.topcoder.timetracker.rejectreason.RejectReasonDAO
     *      #updateRejectReason(com.topcoder.timetracker.rejectreason.RejectReason, java.lang.String, boolean)
     */
    public void updateRejectReason(RejectReason r, String arg1, boolean arg2) throws RejectReasonDAOException {
        for (int i = 0; i < list.size(); i++) {
            RejectReason reason = (RejectReason) list.get(i);

            if (reason.getId() == r.getId()) {
                list.remove(i);
                break;
            }
        }

        list.add(r);
    }

    /**
     * @see com.topcoder.timetracker.rejectreason.RejectReasonDAO
     *      #deleteRejectReason(com.topcoder.timetracker.rejectreason.RejectReason, java.lang.String, boolean)
     */
    public void deleteRejectReason(RejectReason r, String arg1, boolean arg2) throws RejectReasonDAOException {
        for (int i = 0; i < list.size(); i++) {
            RejectReason reason = (RejectReason) list.get(i);

            if (reason.getId() == r.getId()) {
                list.remove(i);
                break;
            }
        }
    }

    /**
     * @see com.topcoder.timetracker.rejectreason.RejectReasonDAO#listRejectReasons()
     */
    public RejectReason[] listRejectReasons() throws RejectReasonDAOException {
        return (RejectReason[]) list.toArray(new RejectReason[list.size()]);
    }

    /**
     * @see com.topcoder.timetracker.rejectreason.RejectReasonDAO
     *      #searchRejectReasons(com.topcoder.search.builder.filter.Filter)
     */
    public RejectReason[] searchRejectReasons(Filter arg0) throws RejectReasonDAOException {
        return (RejectReason[]) list.toArray(new RejectReason[list.size()]);
    }
}