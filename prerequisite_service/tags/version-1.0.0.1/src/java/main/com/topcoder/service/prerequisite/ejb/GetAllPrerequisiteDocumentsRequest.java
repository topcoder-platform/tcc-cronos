/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.ejb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * This class represents the "getAllPrerequisiteDocuments" element in WSDL. It is used by the web services request. It
 * has not attributes since the method has no arguments.
 * </p>
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * </p>
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *       &lt;sequence&gt;&lt;/sequence&gt;
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
@XmlType(name = "")
@XmlRootElement(name = "getAllPrerequisiteDocuments")
public class GetAllPrerequisiteDocumentsRequest implements Serializable {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 3778316451451305387L;

    /**
     * <p>
     * Creates a <code>GetAllPrerequisiteDocumentsRequest</code> instance.
     * </p>
     */
    public GetAllPrerequisiteDocumentsRequest() {
    }
}
