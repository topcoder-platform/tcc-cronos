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
 * This class represents the "competition_not_found_fault" element in WSDL. It's contained in
 * <code>CompetitionNotFoundException</code> class, it's the fault bean.
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
 *         &lt;element name=&quot;competition_not_found&quot;
 *             type=&quot;{http://www.w3.org/2001/XMLSchema}long&quot;/&gt;
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
@XmlType(name = "", propOrder = {"competitionIdNotFound"})
@XmlRootElement(name = "competition_not_found_fault")
public class CompetitionNotFoundFault implements Serializable {

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = -2561662223705533157L;

    /**
     * <p>
     * Represents the competition id not found.
     * </p>
     */
    @XmlElement(name = "competition_not_found", required = true)
    private long competitionIdNotFound;

    /**
     * <p>
     * Creates a <code>CompetitionNotFoundFault</code> instance.
     * </p>
     */
    public CompetitionNotFoundFault() {
    }

    /**
     * <p>
     * Gets the competition id not found.
     * </p>
     *
     * @return the competition id not found. it should return 0, by default.
     */
    public long getCompetitionIdNotFound() {
        return competitionIdNotFound;
    }

    /**
     * <p>
     * Sets the competition id not found.
     * </p>
     *
     * @param competitionIdNotFound
     *            the competition id not found, any value is valid.
     */
    public void setCompetitionIdNotFound(long competitionIdNotFound) {
        this.competitionIdNotFound = competitionIdNotFound;
    }
}
