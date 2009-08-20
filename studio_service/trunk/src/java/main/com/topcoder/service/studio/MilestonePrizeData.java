/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * It is the DTO class which is used to transfer contest milestone prize data.
 * The information can be null or can be empty, therefore this check is not
 * present in the setters. It's the related to the equivalent MilestonePrize
 * entity.
 * </p>
 * <p>
 * This class is not thread safe because it's highly mutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.3
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "milestonePrizeData", propOrder = { "amount", "numberOfSubmissions" })
public class MilestonePrizeData implements Serializable {
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
