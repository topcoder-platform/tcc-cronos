/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite;

import javax.xml.ws.WebFault;

import com.topcoder.service.prerequisite.ejb.IllegalArgumentWSFault;

/**
 * <p>
 * This exception is thrown automatically by the web service interface when the arguments are illegal. It is related
 * with "illegal_argument_faultMsg" fault in WSDL, but it contains only annotations about "illegal_argument_fault"
 * because the messages are automatically constructed. this exception is necessary because it contains the information
 * of the illegal arguments in SOAP mode. The web service can be called also from other types of clients, different from
 * java. It's necessary to store the information using the fault beans like other exception.
 * </p>
 * <p>
 * The constructors with <code>{@link IllegalArgumentWSFault}</code> and the getter are necessary for the correct
 * webservices serialization/deserialization. The other constructors are added for the future implementations of the
 * service interface.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
@WebFault(name = "illegal_argument_fault", faultBean = "com.topcoder.service.prerequisite.ejb.IllegalArgumentWSFault")
public class IllegalArgumentWSException extends IllegalArgumentException {

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 8353694402003575464L;

    /**
     * <p>
     * Represents the fault info, Java type that goes as soapenv:Fault detail element.
     * </p>
     */
    private final IllegalArgumentWSFault faultInfo;

    /**
     * <p>
     * Represents the illegal argument message.
     * </p>
     */
    private final String illegalArgumentMessage;

    /**
     * <p>
     * Creates a <code>IllegalArgumentWSException</code> instance with error message and fault info.
     * </p>
     *
     * @param message
     *            the error message
     * @param faultInfo
     *            the fault info which contains the the illegal argument message
     */
    public IllegalArgumentWSException(String message, IllegalArgumentWSFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
        this.illegalArgumentMessage = null;
    }

    /**
     * <p>
     * Creates a <code>IllegalArgumentWSException</code> instance with error message and the illegal argument message.
     * </p>
     *
     * @param message
     *            the error message
     * @param illegalArgumentMessage
     *            the illegal argument message
     */
    public IllegalArgumentWSException(String message, String illegalArgumentMessage) {
        super(message);
        this.faultInfo = null;
        this.illegalArgumentMessage = illegalArgumentMessage;
    }

    /**
     * <p>
     * Gets the illegal argument message.
     * </p>
     *
     * @return the illegal argument message, possibly null.
     */
    public String getIllegalArgumentMessage() {
        return illegalArgumentMessage;
    }

    /**
     * <p>
     * Gets the fault bean. This method is necessary for the serialization/deserialization.
     *
     * @return the fault bean, possibly null.
     */
    public IllegalArgumentWSFault getFaultInfo() {
        return faultInfo;
    }
}
