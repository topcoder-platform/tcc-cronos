/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * It represents the pre-requisite document entity (bean) used by the service. It contains the information of a
 * pre-requisite document. The information can be null or empty, therefore this check is not present in the setters. It
 * is similar and related to <code>DocumentVersion</code> if prerequisite manager.
 * </p>
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * </p>
 *
 * <pre>
 * &lt;complexType name=&quot;prerequisiteDocument&quot;&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *       &lt;sequence&gt;
 *         &lt;element name=&quot;documentId&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}long&quot;/&gt;
 *         &lt;element name=&quot;version&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}Integer&quot;/&gt;
 *         &lt;element name=&quot;versionDate&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}date&quot;/&gt;
 *         &lt;element name=&quot;name&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot;/&gt;
 *         &lt;element name=&quot;content&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot;/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 * <p>
 * <b>Thread safety</b>: This class is a bean, it's mutable, it's no threads safe, however it's not required because it
 * will be used in a single thread manner.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "prerequisiteDocument", propOrder = {"documentId", "version", "versionDate", "name", "content"})
public class PrerequisiteDocument implements Serializable {

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 5765138167147465302L;

    /**
     * <p>
     * Represents the document id.
     * </p>
     */
    private long documentId = -1;

    /**
     * <p>
     * Represents the version.
     * </p>
     */
    private Integer version;

    /**
     * <p>
     * Represents the version date.
     * </p>
     */
    @XmlElement(required = true)
    private XMLGregorianCalendar versionDate;

    /**
     * <p>
     * Represents the name of the pre-requisite document.
     * </p>
     */
    @XmlElement(required = true)
    private String name;

    /**
     * <p>
     * Represents the content of the pre-requisite document.
     * </p>
     */
    @XmlElement(required = true)
    private String content;

    /**
     * <p>
     * Creates a <code>PrerequisiteDocument</code> instance.
     * </p>
     */
    public PrerequisiteDocument() {
    }

    /**
     * <p>
     * Gets the document id.
     * </p>
     *
     * @return the document id, never less than -1, default to -1.
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
     *            the documentId to set
     * @throws IllegalArgumentException
     *             if documentId &lt; -1
     */
    public void setDocumentId(long documentId) {
        if (documentId < -1) {
            throw new IllegalArgumentException("the [documentId] should not less than -1.");
        }

        this.documentId = documentId;
    }

    /**
     * <p>
     * Gets the version.
     * </p>
     *
     * @return the version, possibly null, but its value is never negative.
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
     *            the version to set, can be null
     * @throws IllegalArgumentException
     *             if it's not null and it's less than 0
     */
    public void setVersion(Integer version) {
        if (null != version && version.intValue() < 0) {
            throw new IllegalArgumentException("The version can not be negative.");
        }

        this.version = version;
    }

    /**
     * <p>
     * Gets the version date.
     * </p>
     *
     * @return the version date, possibly null.
     */
    public XMLGregorianCalendar getVersionDate() {
        return versionDate;
    }

    /**
     * <p>
     * Sets the version date.
     * </p>
     *
     * @param versionDate
     *            the versionDate to set, can be null
     */
    public void setVersionDate(XMLGregorianCalendar versionDate) {
        this.versionDate = versionDate;

    }

    /**
     * <p>
     * Gets the name.
     * </p>
     *
     * @return the name, possibly null or empty.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Sets the name.
     * </p>
     *
     * @param name
     *            the name to set, can be null, can be empty
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Gets the content.
     * </p>
     *
     * @return the content, possibly null or empty.
     */
    public String getContent() {
        return content;
    }

    /**
     * <p>
     * Sets the content.
     * </p>
     *
     * @param content
     *            the content to set, can be null, can be empty
     */
    public void setContent(String content) {
        this.content = content;
    }
}
