/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

/**
 * <p>
 * This exception indicates that an entry of <code>Address</code>/<code>Contact</code> is associated with multiple
 * entities. According to design and specification, any given record of <code>Address</code>/<code>Contact</code> may
 * be associated with zero/one entity at one time. It also may indicate that the
 * <code>Address</code>/<code>Contact</code> itself does not exist in persistence when trying to
 * associating/deassociating it with some entity.
 * </p>
 *
 * <p>
 * This exception will be thrown by the Manager classes, EJB entities, and DAO classes when trying to
 * retrieving/searching/updating/removing the existing <code>Contact</code>/<code>Address</code>, or trying to
 * associating/deassociating entity. And it will be exposed to the caller methods.
 * </p>
 *
 * <p>
 *  <strong>Thread Safety:</strong>
 *  This exception is thread safe by being immutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public class AssociationException extends ContactException {
    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = 1163607370898005307L;

    /**
     * <p>Constructs the exception with given message.</p>
     *
     * @param message a possible null, possible empty(trim'd) error message
     */
    public AssociationException(String message) {
        super(message);
    }

    /**
     * <p>Constructs the exception with given message and cause.</p>
     *
     * @param message a possible null, possible empty(trim'd) error message
     * @param cause a possibly null cause exception
     */
    public AssociationException(String message, Exception cause) {
        super(message, cause);
    }
}
