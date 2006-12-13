/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.handlers;

import org.w3c.dom.Element;

/**
 * This class extends the abstract class RegenerateBrainteaserOrPuzzleHandler
 * and implements the abstract method generatePuzzle() to simply return false in
 * order to generate brain teasers.<br/> Thread-Safety: This class is
 * thread-safe as it does not maintain any state and inherits the thread-safety
 * behaviour of the parent class.
 *
 * @author TCSDESIGNER, KKD
 * @version 1.0
 */
public class RegenerateBrainteaserHandler extends
        RegenerateBrainteaserOrPuzzleHandler {

    /**
     * Creates a RegenerateBrainteaserHandler instance.
     *
     *
     * @param handlerElement
     *            the XML element containing configuration for this handler.
     * @throws IllegalArgumentException
     *             if handlerElement is null, or contains invalid data.
     */
    public RegenerateBrainteaserHandler(Element handlerElement) {
        super(handlerElement);
    }

    /**
     * Implementation of abstract method defined in super class. Will simply
     * return false in order to generate brainteaser.
     *
     *
     * @return false.
     */
    boolean generatePuzzle() {
        return false;
    }
}
