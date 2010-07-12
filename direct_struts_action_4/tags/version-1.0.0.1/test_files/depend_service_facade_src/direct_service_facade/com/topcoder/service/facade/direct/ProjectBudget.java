/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.direct;

import java.io.Serializable;

/**
 * <p>
 * This class is a container for project budget data. It is a simple JavaBean (POJO) that provides getters and setters
 * for all private attributes and performs no validation in the setters.
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class ProjectBudget implements Serializable {

    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = 428801491472530631L;

    /**
     * <p>
     * The billing project name. Can be any value. Has getter and setter.
     * </p>
     */
    private String billingProjectName;

    /**
     * <p>
     * The old budget amount. Can be any value. Has getter and setter.
     * </p>
     */
    private double oldBudget;

    /**
     * <p>
     * The new budget amount. Can be any value. Has getter and setter.
     * </p>
     */
    private double newBudget;

    /**
     * <p>
     * The changed amount. Can be any value. Has getter and setter.
     * </p>
     */
    private double changedAmount;

    /**
     * <p>
     * Creates an instance of ProjectBudget.
     * </p>
     */
    public ProjectBudget() {
        // Do nothing.
    }

    /**
     * <p>
     * Retrieves the billing project name.
     * </p>
     *
     * @return the billing project name
     */
    public String getBillingProjectName() {
        return billingProjectName;
    }

    /**
     * <p>
     * Sets the billing project name.
     * </p>
     *
     * @param billingProjectName
     *            the billing project name
     */
    public void setBillingProjectName(String billingProjectName) {
        this.billingProjectName = billingProjectName;
    }

    /**
     * <p>
     * Retrieves the old budget amount.
     * </p>
     *
     * @return the old budget amount
     */
    public double getOldBudget() {
        return oldBudget;
    }

    /**
     * <p>
     * Sets the old budget amount.
     * </p>
     *
     * @param oldBudget
     *            the old budget amount
     */
    public void setOldBudget(double oldBudget) {
        this.oldBudget = oldBudget;
    }

    /**
     * <p>
     * Retrieves the new budget amount.
     * </p>
     *
     * @return the new budget amount
     */
    public double getNewBudget() {
        return newBudget;
    }

    /**
     * <p>
     * Sets the new budget amount.
     * </p>
     *
     * @param newBudget
     *            the new budget amount
     */
    public void setNewBudget(double newBudget) {
        this.newBudget = newBudget;
    }

    /**
     * <p>
     * Retrieves the changed amount.
     * </p>
     *
     * @return the changed amount
     */
    public double getChangedAmount() {
        return changedAmount;
    }

    /**
     * <p>
     * Sets the changed amount.
     * </p>
     *
     * @param changedAmount
     *            the changed amount
     */
    public void setChangedAmount(double changedAmount) {
        this.changedAmount = changedAmount;
    }
}
