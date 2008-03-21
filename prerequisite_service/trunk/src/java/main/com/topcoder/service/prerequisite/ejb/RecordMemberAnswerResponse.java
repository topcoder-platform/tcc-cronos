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
 * This class represents the "recordMemberAnswerResponse" element in WSDL. It is used to wrap the response of the web
 * services and it contains the result of getAllPrerequisiteDocuments operation. It dosn't contain attributes because
 * the related operation in the service is void.
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
@XmlRootElement(name = "recordMemberAnswerResponse")
public class RecordMemberAnswerResponse implements Serializable {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = -2005214101733923849L;

    /**
     * <p>
     * Creates a <code>RecordMemberAnswerResponse</code> instance.
     * </p>
     */
    public RecordMemberAnswerResponse() {
    }
}
