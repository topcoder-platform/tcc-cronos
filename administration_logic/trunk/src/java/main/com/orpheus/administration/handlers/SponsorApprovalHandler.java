/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.handlers;

import org.w3c.dom.Element;

/**
 * This class extends the abstract class SponsorApprovalRejectionHandler and
 * implements the abstract method getIsApprovedPropertyValue() to simply return
 * "Y" as value to be set for sponsor-is-approved property in order to approve the
 * sponsor.<br/> Thread-Safety: This class is thread-safe as it does not
 * maintain any state and inherits the thread-safety behaviour of the parent
 * class.
 *
 * @author TCSDESIGNER, KKD
 * @version 1.0
 */
public class SponsorApprovalHandler extends SponsorApprovalRejectionHandler {

    /**
     * Creates a SponsorApprovalHandler instance.
     *
     *
     * @param handlerElement
     *            the XML element containing configuration for this handler.
     * @throws IllegalArgumentException
     *             if handlerElement is null, or contains invalid data.
     */
    public SponsorApprovalHandler(Element handlerElement) {
        super(handlerElement);
    }

    /**
     * Implementation of abstract method defined in super class. Will simply
     * return "Y" as value to be set for sponsor-is-approved property in order to
     * approve the sponsor.
     *
     *
     * @return the value "Y".
     */
    String getIsApprovedPropertyValue() {
        return "Y";
    }
}
