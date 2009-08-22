/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import com.topcoder.service.studio.ejb.IllegalArgumentWSFault;

import javax.xml.ws.WebFault;

/**
 * <p>
 * This exception is thrown automatically by the web service interface when the
 * arguments are illegal. It is related with "illegal_argument_faultMsg" fault
 * in WSDL, but it contains only annotations about "illegal_argument_fault"
 * because the messages are automatically constructed. this exception is
 * necessary because it contains the information of the illegal arguments in
 * SOAP mode. The web service can be called also from other types of clients,
 * different from java. It's necessary to store the information using the fault
 * beans like other exception. WS is WebServices.
 * </p>
 * <p>
 * The constructors with IllegalArgumentWSFault and the getter are necessary for
 * the correct webservices serialization/deserialization. The other constructors
 * are added for the future implementations of the service interface.
 * </p>
 *
 * @author fabrizyo, TCSDEVELOPER
 * @version 1.0
 */
@WebFault(name = "illegal_argument_fault", faultBean = "com.topcoder.service.studio.ejb.IllegalArgumentWSFault")
public class IllegalArgumentWSException extends IllegalArgumentException {
    /**
     * <p>
     * Represents the faultInfo, Java type that goes as soapenv:Fault detail
     * element.
     * </p>
     */
    private final IllegalArgumentWSFault faultInfo;

    /**
     * <p>
     * Represents the roleNotFound. It is retrieved from IllegalArgumentWSFault.
     * </p>
     */
    private final String illegalArgumentMessage;

    /**
     * <p>
     * Construct the exception. Call the super constructor,set the fault info
     * and set the illegalArgumentMessage from the faultInfo.
     * </p>
     *
     * @param message the message
     * @param faultInfo the fault info which contains the illegalArgumentMessage
     */
    public IllegalArgumentWSException(String message, IllegalArgumentWSFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
        this.illegalArgumentMessage = (faultInfo == null) ? null : faultInfo.getIllegalArgumentMessage();
    }

    /**
     * <p>
     * Constructor with error message.
     * </p>
     *
     * @param message the error message
     * @param illegalArgumentMessage the illegal argument message
     */
    public IllegalArgumentWSException(String message, String illegalArgumentMessage) {
        super(message);
        this.faultInfo = null;
        this.illegalArgumentMessage = illegalArgumentMessage;
    }

    /**
     * <p>
     * Return the illegalArgument Message.
     * </p>
     *
     * @return the illegalArgument Message
     */
    public String getIllegalArgumentMessage() {
        return illegalArgumentMessage;
    }

    /**
     * <p>
     * Return the fault bean. This method is necessary for the
     * serialization/deserialization. it returns null if the constructors
     * without fault bean are used.
     * </p>
     *
     * @return returns fault bean
     */
    public IllegalArgumentWSFault getFaultInfo() {
        return faultInfo;
    }
}
