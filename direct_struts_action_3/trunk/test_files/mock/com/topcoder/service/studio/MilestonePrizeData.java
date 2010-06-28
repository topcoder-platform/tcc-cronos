/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * It is the DTO class which is used to transfer contest milestone prize data.
 * The information can be null or can be empty, therefore this check is not
 * present in the setters. It's the related to the equivalent MilestonePrize
 * entity.
 * </p>
 *
 * <p>
 * Changes in v1.4 (Studio Multi-Rounds Assembly - Launch Contest): Added id and createDate 
 * attributes with corresponding getters and setters. 'XmlType' was also updated to include these new fields.
 * Default serialVersionUID was also added.
 * </p>
 *
 * <p>
 * This class is not thread safe because it's highly mutable.
 * </p>
 *
 * @author pulky
 * @version 1.4
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "milestonePrizeData", propOrder = { "id", "createDate", "amount", "numberOfSubmissions" })
public class MilestonePrizeData implements Serializable {
    /**
     * Default serial version id.
     *
     * @since 1.4
     */
    private static final long serialVersionUID = 1704571622645031121L;

    /**
     * <p>
     * Represents the milestone prize data Id.
     * </p>
     *
     * @since 1.4
     */
    private long id = -1;

    /**
     * <p>
     * Represents the milestone prize data creation date.
     * </p>
     *
     * @since 1.4
     */
    private XMLGregorianCalendar createDate;

    /**
     * The amount. Can be any value. Has getter and setter.
     */
    private Double amount;
    /**
     * The number of submissions that get prize. Can be any value. Has getter
     * and setter.
     */
    private Integer numberOfSubmissions;

    /**
     * Creates an instance of MilestonePrizeData.
     *
     */
    public MilestonePrizeData() {
    }

    /**
     * Gets the milestone prize data id
     *
     * @return the value of the milestone prize data id
     * @since 1.4
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the milestone prize data id
     *
     * @param id the value of the milestone prize data id to set
     * @since 1.4
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the milestone prize data creation date
     *
     * @return the value of the milestone prize creation date
     * @since 1.4
     */
    public XMLGregorianCalendar getCreateDate() {
        return createDate;
    }

    /**
     * Sets the milestone prize data creation date
     *
     * @param createDate the value of the milestone prize data creation date to set
     * @since 1.4
     */
    public void setCreateDate(XMLGregorianCalendar createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets the value of the amount attribute.
     *
     * @return the value of the amount attribute
     */
    public Double getAmount() {
        return this.amount;
    }

    /**
     * Sets the value of the amount attribute.
     *
     * @param amount the new value for the amount attribute
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * Gets the value of the number of submissions that get prize.
     *
     * @return the value of the number of submissions that get prize
     */
    public Integer getNumberOfSubmissions() {
        return this.numberOfSubmissions;
    }

    /**
     * Sets the value of the number of submissions that get prize.
     *
     * @param numberOfSubmissions the new value for the number of submissions
     *        that get prize.
     */
    public void setNumberOfSubmissions(Integer numberOfSubmissions) {
        this.numberOfSubmissions = numberOfSubmissions;
    }
}
