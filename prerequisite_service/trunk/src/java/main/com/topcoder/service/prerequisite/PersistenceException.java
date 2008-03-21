/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite;

import javax.xml.ws.WebFault;

import com.topcoder.service.prerequisite.ejb.PersistenceFault;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown automatically by the web service interface when an error occurs in the persistence layer. It
 * wraps the generic persistence exceptions (not specific thrown by the interface of this component) thrown by the
 * <code>DocumentManagerBean</code> used in the EJB implementation. It is related with "persistence_faultMsg" fault in
 * WSDL, but it contains only annotations about "persistence_fault" because the messages are automatically constructed.
 * </p>
 * <p>
 * The constructors with <code>{@link PersistenceFault}</code> and the getter are necessary for the correct
 * webservices serialization/deserialization. The other constructors are added for the future implementations of the
 * service interface.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
@WebFault(name = "persistence_fault", faultBean = "com.topcoder.service.prerequisite.ejb.PersistenceFault")
public class PersistenceException extends PrerequisiteServiceException {

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 8353694402003575464L;

    /**
     * <p>
     * Represents the fault info, Java type that goes as soapenv:Fault detail element.
     * </p>
     */
    private final PersistenceFault faultInfo;

    /**
     * <p>
     * Represents the persistence message.
     * </p>
     */
    private final String persistenceMessage;

    /**
     * <p>
     * Creates a <code>PersistenceException</code> instance with error message and fault info.
     * </p>
     *
     * @param message
     *            the error message
     * @param faultInfo
     *            the fault info which contains the persistence message
     */
    public PersistenceException(String message, PersistenceFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
        this.persistenceMessage = null;
    }

    /**
     * <p>
     * Creates a <code>PersistenceException</code> instance with error message, cause exception and fault info.
     * </p>
     *
     * @param message
     *            the error message
     * @param faultInfo
     *            the fault info which contains the persistence message
     * @param cause
     *            the cause exception
     */
    public PersistenceException(String message, PersistenceFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
        this.persistenceMessage = null;
    }

    /**
     * <p>
     * Creates a <code>PersistenceException</code> instance with error message and persistence message.
     * </p>
     *
     * @param message
     *            the error message
     * @param persistenceMessage
     *            the persistence message
     */
    public PersistenceException(String message, String persistenceMessage) {
        super(message);
        this.faultInfo = null;
        this.persistenceMessage = persistenceMessage;
    }

    /**
     * <p>
     * Creates a <code>PersistenceException</code> instance with error message, inner cause and persistence message.
     * </p>
     *
     * @param message
     *            the error message
     * @param cause
     *            the cause of this exception
     * @param persistenceMessage
     *            the persistence message
     */
    public PersistenceException(String message, Throwable cause, String persistenceMessage) {
        super(message, cause);
        this.faultInfo = null;
        this.persistenceMessage = persistenceMessage;
    }

    /**
     * <p>
     * Creates a <code>PersistenceException</code> instance with error message, exception data and persistence
     * message.
     * </p>
     *
     * @param message
     *            the error message
     * @param data
     *            the exception data
     * @param persistenceMessage
     *            the persistence message
     */
    public PersistenceException(String message, ExceptionData data, String persistenceMessage) {
        super(message, data);
        this.faultInfo = null;
        this.persistenceMessage = persistenceMessage;
    }

    /**
     * <p>
     * Creates a <code>PersistenceException</code> instance with error message, exception data, inner cause and
     * persistence message.
     * </p>
     *
     * @param message
     *            the error message
     * @param cause
     *            the cause of this exception
     * @param data
     *            the exception data
     * @param persistenceMessage
     *            the persistence message
     */
    public PersistenceException(String message, Throwable cause, ExceptionData data, String persistenceMessage) {
        super(message, cause, data);
        this.faultInfo = null;
        this.persistenceMessage = persistenceMessage;
    }

    /**
     * <p>
     * Gets the persistence message.
     * </p>
     *
     * @return the persistence message, possibly null.
     */
    public String getPersistenceMessage() {
        return persistenceMessage;
    }

    /**
     * <p>
     * Gets the fault bean. This method is necessary for the serialization/deserialization.
     * </p>
     *
     * @return the fault bean, possibly null.
     */
    public PersistenceFault getFaultInfo() {
        return faultInfo;
    }
}
