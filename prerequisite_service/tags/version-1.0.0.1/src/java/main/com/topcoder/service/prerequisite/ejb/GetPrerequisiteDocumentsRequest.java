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
 * This class represents the "getPrerequisiteDocuments" element in WSDL. It is used by the web services request of the
 * related operation.
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
@XmlType(name = "", propOrder = {"competitionId", "roleId"})
@XmlRootElement(name = "getPrerequisiteDocuments")
public class GetPrerequisiteDocumentsRequest implements Serializable {

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 4230914854507451791L;

    /**
     * <p>
     * Represents the competition id of the argument request.
     * </p>
     */
    private long competitionId;

    /**
     * <p>
     * Represents the role id of the argument request.
     * </p>
     */
    private long roleId;

    /**
     * <p>
     * This is the default constructor. It does nothing.
     * </p>
     */
    public GetPrerequisiteDocumentsRequest() {
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
