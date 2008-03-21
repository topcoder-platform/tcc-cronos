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
import javax.xml.datatype.XMLGregorianCalendar;

import com.topcoder.service.prerequisite.PrerequisiteDocument;

/**
 * <p>
 * This class represents the "recordMemberAnswer" element in WSDL. It is used by the web services request to handle the
 * arguments of the related method. It
 * is not used directly, it is used by the container.
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
 *         &lt;element name=&quot;competitionId&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}long&quot;/&gt;
 *         &lt;element name=&quot;timestamp&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}date&quot;/&gt;
 *         &lt;element name=&quot;agrees&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}boolean&quot;/&gt;
 *         &lt;element name=&quot;prerequisiteDocument&quot;
 *             type=&quot;{http://www.example.org/PrerequisiteService/}prerequisiteDocument&quot;/&gt;
 *         &lt;element name=&quot;roleId&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}long&quot;/&gt;
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
@XmlType(name = "", propOrder = {"competitionId", "timestamp", "agrees", "prerequisiteDocument", "roleId"})
@XmlRootElement(name = "recordMemberAnswer")
public class RecordMemberAnswerRequest implements Serializable {

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = -6490228828299293585L;

    /**
     * <p>
     * Represents the competition id of the arguments request.
     * </p>
     */
    private long competitionId;

    /**
     * <p>
     * Represents the timestamp of the arguments request.
     * </p>
     */
    @XmlElement(required = true)
    private XMLGregorianCalendar timestamp;

    /**
     * <p>
     * Represents the agrees of the arguments request.
     * </p>
     */
    private boolean agrees;

    /**
     * <p>
     * Represents the prerequisite document of the arguments request.
     * </p>
     */
    @XmlElement(required = true)
    private PrerequisiteDocument prerequisiteDocument;

    /**
     * <p>
     * Represents the role id.
     * </p>
     */
    private long roleId;

    /**
     * <p>
     * This is the default constructor. It does nothing.
     * </p>
     */
    public RecordMemberAnswerRequest() {
    }

    /**
     * <p>
     * Gets the competition id.
     * </p>
     *
     * @return the competition id, 0 will return by default.
     */
    public long getCompetitionId() {
        return competitionId;
    }

    /**
     * <p>
     * Sets the competition id.
     * </p>
     *
     * @param competitionId
     *            the competition id, no validation performed.
     */
    public void setCompetitionId(long competitionId) {
        this.competitionId = competitionId;
    }

    /**
     * <p>
     * Gets the timestamp.
     * </p>
     *
     * @return the timestamp. null will return by default.
     */
    public XMLGregorianCalendar getTimestamp() {
        return timestamp;
    }

    /**
     * <p>
     * Sets the timestamp.
     * </p>
     *
     * @param timestamp
     *            the timestamp. no validation performed.
     */
    public void setTimestamp(XMLGregorianCalendar timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * <p>
     * Gets the agrees of the arguments request.
     * </p>
     *
     * @return the agrees of the arguments request.
     */
    public boolean isAgrees() {
        return agrees;
    }

    /**
     * <p>
     * Sets the agrees of the arguments request.
     * </p>
     *
     * @param agrees
     *            the agrees of the arguments request.
     */
    public void setAgrees(boolean agrees) {
        this.agrees = agrees;
    }

    /**
     * <p>
     * Gets the prerequisite document.
     * </p>
     *
     * @return the prerequisite document. null will return by default.
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
     *            the prerequisite document. no validation performed.
     */
    public void setPrerequisiteDocument(PrerequisiteDocument prerequisiteDocument) {
        this.prerequisiteDocument = prerequisiteDocument;
    }

    /**
     * <p>
     * Gets the role id.
     * </p>
     *
     * @return the role id, 0 will return by default.
     */
    public long getRoleId() {
        return roleId;
    }

    /**
     * <p>
     * Sets the role id.
     * </p>
     *
     * @param roleId
     *            the role id, no validation performed.
     */
    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
}
