/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.ejb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.topcoder.service.prerequisite.PrerequisiteDocument;

/**
 * <p>
 * This class represents the "getPrerequisiteDocumentResponse" element in WSDL. It is used to wrap the response of the
 * web services and it contains the result of getPrerequisiteDocument operation.
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
 *         &lt;element name=&quot;prerequisiteDocument&quot;
 *             type=&quot;{http://www.example.org/PrerequisiteService/}prerequisiteDocument&quot;
 *             minOccurs=&quot;0&quot;/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 * <p>
 * <b>Thread Safety</b>: This class is not thread safe because it's higly mutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"prerequisiteDocument"})
@XmlRootElement(name = "getPrerequisiteDocumentResponse")
public class GetPrerequisiteDocumentResponse implements Serializable {

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = -7363717387390128281L;
    /**
     * <p>
     * Represents the prerequisiteDocument of the response.
     * </p>
     */
    private PrerequisiteDocument prerequisiteDocument;

    /**
     * <p>
     * This is the default constructor. It does nothing.
     * </p>
     */
    public GetPrerequisiteDocumentResponse() {
    }

    /**
     * <p>
     * Gets the prerequisite document.
     * </p>
     *
     * @return the prerequisite document, null will return by default.
     */
    public PrerequisiteDocument getPrerequisiteDocument() {
        return prerequisiteDocument;
    }

    /**
     * <p>
     * Sets the prerequisite document.
     * </p>
     *
     * @param prerequisiteDocument
     *            the prerequisite document, no validation performed.
     */
    public void setPrerequisiteDocument(PrerequisiteDocument prerequisiteDocument) {
        this.prerequisiteDocument = prerequisiteDocument;

    }
}
