/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.ejb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * This class represents the "illegal_argument_fault" element in WSDL. It's contained in the related
 * <code>IllegalArgumentWSException</code> class.
 * </p>
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * </p>
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *       &lt;sequence&gt;
 *         &lt;element name=&quot;illegal_argument_message&quot;
 *             type=&quot;{http://www.w3.org/2001/XMLSchema}String&quot;/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 * <p>
 * <b>Thread Safety</b>: This class is not thread safe because it's highly mutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"illegalArgumentMessage"})
@XmlRootElement(name = "illegal_argument_fault")
public class IllegalArgumentWSFault implements Serializable {

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 2347471089835375053L;
    /**
     * <p>
     * Represents the illegal argument message.
     * </p>
     */
    @XmlElement(name = "illegal_argument_message", required = true)
    private String illegalArgumentMessage;

    /**
     * <p>
     * This is the default constructor. It does nothing.
     * </p>
     */
    public IllegalArgumentWSFault() {
    }

    /**
     * <p>
     * Gets the illegal argument message.
     * </p>
     *
     * @return the illegal argument message. null will return by default.
     */
    public String getIllegalArgumentMessage() {
        return illegalArgumentMessage;
    }

    /**
     * <p>
     * Sets the illegal argument message.
     * </p>
     *
     * @param illegalArgumentMessage
     *            the illegal argument message, any value is valid, even null/empty.
     */
    public void setIllegalArgumentMessage(String illegalArgumentMessage) {
        this.illegalArgumentMessage = illegalArgumentMessage;
    }
}
