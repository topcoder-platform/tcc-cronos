/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.handlers;

import org.w3c.dom.Element;

/**
 * This class extends the abstract class PendingWinnerApprovalRejectionHandler
 * and implements the abstract method getApprovedFlag() to simply return true in
 * order to approve the pending winner.<br/> Thread-Safety: This class is
 * thread-safe as it does not maintain any state and inherits the thread-safety
 * behaviour of the parent class.
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class PendingWinnerApprovalHandler extends
        PendingWinnerApprovalRejectionHandler {

    /**
     * Creates a PendingWinnerApprovalHandler instance.
     *
     *
     * @param handlerElement
     *            the XML element containing configuration for this handler.
     * @throws IllegalArgumentException
     *             if handlerElement is null, or contains invalid data.
     */
    public PendingWinnerApprovalHandler(Element handlerElement) {
        super(handlerElement);
    }

    /**
     * Implementation of abstract method defined in super class. Will simply
     * return true in order to approve the pending winner.
     *
     *
     * @return true.
     */
    boolean getApprovedFlag() {
        return true;
    }
}
