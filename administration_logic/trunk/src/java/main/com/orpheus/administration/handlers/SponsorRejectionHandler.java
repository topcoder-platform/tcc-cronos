/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.handlers;

import org.w3c.dom.Element;

/**
 * This class extends the abstract class SponsorApprovalRejectionHandler and
 * implements the abstract method getIsApprovedPropertyValue() to simply return
 * "N" as value to be set for sponsor-is-approved property in order to reject the
 * sponsor.<br/> Thread-Safety: This class is thread-safe as it does not
 * maintain any state and inherits the thread-safety behaviour of the parent
 * class.
 *
 * @author bose_java, KKD
 * @version 1.0
 */
public class SponsorRejectionHandler extends SponsorApprovalRejectionHandler {

    /**
     * Creates a SponsorRejectionHandler instance.
     *
     *
     * @param handlerElement
     *            the XML element containing configuration for this handler.
     * @throws IllegalArgumentException
     *             if handlerElement is null, or contains invalid data.
     */
    public SponsorRejectionHandler(Element handlerElement) {
        super(handlerElement);
    }

    /**
     * Implementation of abstract method defined in super class. Will simply
     * return "N" as value to be set for sponsor-is-approved property in order to reject
     * the sponsor.
     *
     *
     * @return the value "N".
     */
    String getIsApprovedPropertyValue() {
        return "N";
    }
}
