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
 * This class represents the "getPrerequisiteDocument" element in WSDL. It is used by the web services request to handle
 * the arguments of the related method. It is not used directly, it is used by the container.
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
 *         &lt;element name=&quot;documentId&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}long&quot;/&gt;
 *         &lt;element name=&quot;version&quot;
 *             type=&quot;{http://www.w3.org/2001/XMLSchema}int&quot; minOccurs=&quot;0&quot;/&gt;
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
@XmlType(name = "", propOrder = {"documentId", "version"})
@XmlRootElement(name = "getPrerequisiteDocument")
public class GetPrerequisiteDocumentRequest implements Serializable {

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = -4432991339624105060L;

    /**
     * <p>
     * Represents the document id.
     * </p>
     */
    private long documentId;

    /**
     * <p>
     * Represents the version.
     * </p>
     */
    private Integer version;

    /**
     * <p>
     * This is the default constructor. It does nothing.
     * </p>
     */
    public GetPrerequisiteDocumentRequest() {
    }

    /**
     * <p>
     * Gets the document id.
     * </p>
     *
     * @return the document id. 0 will return by default.
     */
    public long getDocumentId() {
        return documentId;
    }

    /**
     * <p>
     * Sets the document id.
     * </p>
     *
     * @param documentId
     *            the document id. no validation is performed.
     */
    public void setDocumentId(long documentId) {
        this.documentId = documentId;
    }

    /**
     * <p>
     * Gets the version.
     * </p>
     *
     * @return the version. null will return by default.
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * <p>
     * Sets the version.
     * </p>
     *
     * @param version
     *            the version. no validation is performed.
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

}
