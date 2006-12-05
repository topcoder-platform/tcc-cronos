/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests.handlers;

import java.util.Date;

import com.orpheus.administration.persistence.AdminSummary;
import com.orpheus.administration.persistence.PendingWinner;
import com.topcoder.util.puzzle.PuzzleData;

/**
 * Dummy AdminDataBean, used for providing data for tests.
 * <p>
 * <strong>Thread Safety</strong></p>
 * <p>This object is immutable and thread-safe</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DummyAdminDataBean implements javax.ejb.SessionBean {
    /**
     * <p>Empty constructor.</p>
     */
    public DummyAdminDataBean() {
    }

    /**
     * Dummy implemention.
     * @return the admin summary
     */
    public AdminSummary getAdminSummary() {

        return new AdminSummary() {
        };
    }

    /**
     * Dummy implemention.
     * 
     * @param domainId domain id
     * @param approved approved flag
     */
    public void setDomainApproval(long domainId, boolean approved) {
        DataProvider.setDomainApproval(domainId, approved);
    }

    /**
     * Dummy implemention.
     * @param imageId image id
     * @param approved approved flag
     */
    public void setImageApproval(long imageId, boolean approved) {
        DataProvider.setImageApproval(imageId, approved);
    }

    /**
     * Dummy implemention.
     * @param puzzles puzzles to store
     * @return ids of stored puzzles
     */
    public long[] storePuzzles(PuzzleData[] puzzles) {
        // your code here
        return null;
    }

    /**
     * Dummy implemention.
     * @return the pending winners in all games
     */
    public PendingWinner[] getPendingWinners() {
        PendingWinner winner = new PendingWinnerImpl(1001, 1001, 100);
        return new PendingWinner[] {winner};
    }

    /**
     * Dummy implemention.
     * @param winner winner to approve
     * @param date date of approval
     */
    public void approveWinner(PendingWinner winner, Date date) {
        DataProvider.approveWinner(winner, date);
    }

    /**
     * Dummy implemention.
     * @param winner winner to reject
     */
    public void rejectWinner(PendingWinner winner) {
        DataProvider.rejectWinner(winner);
    }

    /**
     * <p>Creates the bean. This is an empty implementation.</p>
     * 
     */
    public void ejbCreate() {
    }

    /**
     * <p>Removes the bean. This is an empty implementation.</p>
     * 
     */
    public void ejbRemove() {
    }

    /**
     * <p>Activates the bean. This is an empty implementation.</p>
     * 
     */
    public void ejbActivate() {
    }

    /**
     * <p>Passivates the bean. This is an empty implementation.</p>
     * 
     */
    public void ejbPassivate() {
    }

    /**
     * <p>Sets the session context. This is an empty implementation.</p>
     * 
     * 
     * @param ctx session context
     */
    public void setSessionContext(javax.ejb.SessionContext ctx) {
    }
}
