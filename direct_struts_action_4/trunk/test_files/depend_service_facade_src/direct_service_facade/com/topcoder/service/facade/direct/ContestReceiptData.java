/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.direct;

import java.io.Serializable;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * This class is a container for contest receipt data. It is a simple JavaBean (POJO) that provides getters and
 * setters for all private attributes and performs no validation in the setters.
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class ContestReceiptData implements Serializable {

    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = 916662604468465235L;

    /**
     * <p>
     * The ID of the contest. Can be any value. Has getter and setter.
     * </p>
     */
    private long contestId;

    /**
     * <p>
     * The flag indicating whether the contest is studio contest or software competition. Can be any value. Has getter
     * and setter.
     * </p>
     */
    private boolean studio;

    /**
     * <p>
     * The name of the project for this contest. Can be any value. Has getter and setter.
     * </p>
     */
    private String projectName;

    /**
     * <p>
     * The contest name. Can be any value. Has getter and setter.
     * </p>
     */
    private String contestName;

    /**
     * <p>
     * The contest fee. Can be any value. Has getter and setter.
     * </p>
     */
    private double contestFee;

    /**
     * <p>
     * The start date of the contest. Can be any value. Has getter and setter.
     * </p>
     */
    private XMLGregorianCalendar startDate;

    /**
     * <p>
     * The type of the contest (including the sub-type for the studio). Can be any value. Has getter and setter.
     * </p>
     */
    private String contestType;

    /**
     * <p>
     * The prizes for the contest competitors. Can be any value. Has getter and setter.
     * </p>
     */
    private double[] prizes;

    /**
     * <p>
     * The assigned date for the milestone. Can be any value. Has getter and setter.
     * </p>
     */
    private XMLGregorianCalendar milestoneDate;

    /**
     * <p>
     * The status of the contest. Can be any value. Has getter and setter.
     * </p>
     */
    private String status;

    /**
     * <p>
     * The total amount of contest cost. Can be any value. Has getter and setter.
     * </p>
     */
    private double contestTotalCost;

    /**
     * <p>
     * The end date of the contest. Can be any value. Has getter and setter.
     * </p>
     */
    private XMLGregorianCalendar endDate;

    /**
     * <p>
     * The name of the user's company. Can be any value. Has getter and setter.
     * </p>
     */
    private String companyName;

    /**
     * <p>
     * The user address where to send bills. Can be any value. Has getter and setter.
     * </p>
     */
    private String address;

    /**
     * <p>
     * The city of the user. Can be any value. Has getter and setter.
     * </p>
     */
    private String city;

    /**
     * <p>
     * The state of the user. Is used when the user is from US. Can be any value. Has getter and setter.
     * </p>
     */
    private String state;

    /**
     * <p>
     * The ZIP code of the user. Can be any value. Has getter and setter.
     * </p>
     */
    private String zipCode;

    /**
     * <p>
     * The country of the user. Can be any value. Has getter and setter.
     * </p>
     */
    private String country;

    /**
     * <p>
     * The phone number of the user. Can be any value. Has getter and setter.
     * </p>
     */
    private String phone;

    /**
     * <p>
     * The e-mail address of the user. Can be any value. Has getter and setter.
     * </p>
     */
    private String email;

    /**
     * <p>
     * The purchase order number. Can be any value. Has getter and setter.
     * </p>
     */
    private String purchaseOrderNo;

    /**
     * <p>
     * The description of the invoice terms. Can be any value. Has getter and setter.
     * </p>
     */
    private String invoiceTerms;

    /**
     * <p>
     * The reference number of the order. Can be any value. Has getter and setter.
     * </p>
     */
    private String referenceNo;

    /**
     * <p>
     * Creates an instance of ContestReceiptData.
     * </p>
     */
    public ContestReceiptData() {
        // Do nothing.
    }

    /**
     * <p>
     * Retrieves the ID of the contest.
     * </p>
     *
     * @return the ID of the contest
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * <p>
     * Sets the ID of the contest.
     * </p>
     *
     * @param contestId
     *            the ID of the contest
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * <p>
     * Retrieves the flag indicating whether the contest is studio contest or software competition.
     * </p>
     *
     * @return the flag indicating whether the contest is studio contest or software competition
     */
    public boolean isStudio() {
        return studio;
    }

    /**
     * <p>
     * Sets the flag indicating whether the contest is studio contest or software competition.
     * </p>
     *
     * @param studio
     *            the flag indicating whether the contest is studio contest or software competition
     */
    public void setStudio(boolean studio) {
        this.studio = studio;
    }

    /**
     * <p>
     * Retrieves the name of the project for this contest.
     * </p>
     *
     * @return the name of the project for this contest
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * <p>
     * Sets the name of the project for this contest.
     * </p>
     *
     * @param projectName
     *            the name of the project for this contest
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * <p>
     * Retrieves the contest name.
     * </p>
     *
     * @return the contest name
     */
    public String getContestName() {
        return contestName;
    }

    /**
     * <p>
     * Sets the contest name.
     * </p>
     *
     * @param contestName
     *            the contest name
     */
    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    /**
     * <p>
     * Retrieves the contest fee.
     * </p>
     *
     * @return the contest fee
     */
    public double getContestFee() {
        return contestFee;
    }

    /**
     * <p>
     * Sets the contest fee.
     * </p>
     *
     * @param contestFee
     *            the contest fee
     */
    public void setContestFee(double contestFee) {
        this.contestFee = contestFee;
    }

    /**
     * <p>
     * Retrieves the start date of the contest.
     * </p>
     *
     * @return the start date of the contest
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * <p>
     * Sets the start date of the contest.
     * </p>
     *
     * @param startDate
     *            the start date of the contest
     */
    public void setStartDate(XMLGregorianCalendar startDate) {
        this.startDate = startDate;
    }

    /**
     * <p>
     * Retrieves the type of the contest (including the sub-type for the studio).
     * </p>
     *
     * @return the type of the contest (including the sub-type for the studio)
     */
    public String getContestType() {
        return contestType;
    }

    /**
     * <p>
     * Sets the type of the contest (including the sub-type for the studio).
     * </p>
     *
     * @param contestType
     *            the type of the contest (including the sub-type for the studio)
     */
    public void setContestType(String contestType) {
        this.contestType = contestType;
    }

    /**
     * <p>
     * Retrieves the prizes for the contest competitors.
     * </p>
     *
     * @return the prizes for the contest competitors
     */
    public double[] getPrizes() {
        return prizes;
    }

    /**
     * <p>
     * Sets the prizes for the contest competitors.
     * </p>
     *
     * @param prizes
     *            the prizes for the contest competitors
     */
    public void setPrizes(double[] prizes) {
        this.prizes = prizes;
    }

    /**
     * <p>
     * Retrieves the assigned date for the milestone.
     * </p>
     *
     * @return the assigned date for the milestone
     */
    public XMLGregorianCalendar getMilestoneDate() {
        return milestoneDate;
    }

    /**
     * <p>
     * Sets the assigned date for the milestone.
     * </p>
     *
     * @param milestoneDate
     *            the assigned date for the milestone
     */
    public void setMilestoneDate(XMLGregorianCalendar milestoneDate) {
        this.milestoneDate = milestoneDate;
    }

    /**
     * <p>
     * Retrieves the status of the contest.
     * </p>
     *
     * @return the status of the contest
     */
    public String getStatus() {
        return status;
    }

    /**
     * <p>
     * Sets the status of the contest.
     * </p>
     *
     * @param status
     *            the status of the contest
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * <p>
     * Retrieves the total amount of contest cost.
     * </p>
     *
     * @return the total amount of contest cost
     */
    public double getContestTotalCost() {
        return contestTotalCost;
    }

    /**
     * <p>
     * Sets the total amount of contest cost.
     * </p>
     *
     * @param contestTotalCost
     *            the total amount of contest cost
     */
    public void setContestTotalCost(double contestTotalCost) {
        this.contestTotalCost = contestTotalCost;
    }

    /**
     * <p>
     * Retrieves the end date of the contest.
     * </p>
     *
     * @return the end date of the contest
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * <p>
     * Sets the end date of the contest.
     * </p>
     *
     * @param endDate
     *            the end date of the contest
     */
    public void setEndDate(XMLGregorianCalendar endDate) {
        this.endDate = endDate;
    }

    /**
     * <p>
     * Retrieves the name of the user's company.
     * </p>
     *
     * @return the name of the user's company
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * <p>
     * Sets the name of the user's company.
     * </p>
     *
     * @param companyName
     *            the name of the user's company
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * <p>
     * Retrieves the user address where to send bills.
     * </p>
     *
     * @return the user address where to send bills
     */
    public String getAddress() {
        return address;
    }

    /**
     * <p>
     * Sets the user address where to send bills.
     * </p>
     *
     * @param address
     *            the user address where to send bills
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * <p>
     * Retrieves the city of the user.
     * </p>
     *
     * @return the city of the user
     */
    public String getCity() {
        return city;
    }

    /**
     * <p>
     * Sets the city of the user.
     * </p>
     *
     * @param city
     *            the city of the user
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * <p>
     * Retrieves the state of the user.
     * </p>
     *
     * @return the state of the user
     */
    public String getState() {
        return state;
    }

    /**
     * <p>
     * Sets the state of the user.
     * </p>
     *
     * @param state
     *            the state of the user
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * <p>
     * Retrieves the ZIP code of the user.
     * </p>
     *
     * @return the ZIP code of the user
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * <p>
     * Sets the ZIP code of the user.
     * </p>
     *
     * @param zipCode
     *            the ZIP code of the user
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * <p>
     * Retrieves the country of the user.
     * </p>
     *
     * @return the country of the user
     */
    public String getCountry() {
        return country;
    }

    /**
     * <p>
     * Sets the country of the user.
     * </p>
     *
     * @param country
     *            the country of the user
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * <p>
     * Retrieves the phone number of the user.
     * </p>
     *
     * @return the phone number of the user
     */
    public String getPhone() {
        return phone;
    }

    /**
     * <p>
     * Sets the phone number of the user.
     * </p>
     *
     * @param phone
     *            the phone number of the user
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * <p>
     * Retrieves the e-mail address of the user.
     * </p>
     *
     * @return the e-mail address of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * <p>
     * Sets the e-mail address of the user.
     * </p>
     *
     * @param email
     *            the e-mail address of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * <p>
     * Retrieves the purchase order number.
     * </p>
     *
     * @return the purchase order number
     */
    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    /**
     * <p>
     * Sets the purchase order number.
     * </p>
     *
     * @param purchaseOrderNo
     *            the purchase order number
     */
    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    /**
     * <p>
     * Retrieves the description of the invoice terms.
     * </p>
     *
     * @return the description of the invoice terms
     */
    public String getInvoiceTerms() {
        return invoiceTerms;
    }

    /**
     * <p>
     * Sets the description of the invoice terms.
     * </p>
     *
     * @param invoiceTerms
     *            the description of the invoice terms
     */
    public void setInvoiceTerms(String invoiceTerms) {
        this.invoiceTerms = invoiceTerms;
    }

    /**
     * <p>
     * Retrieves the reference number of the order.
     * </p>
     *
     * @return the reference number of the order
     */
    public String getReferenceNo() {
        return referenceNo;
    }

    /**
     * <p>
     * Sets the reference number of the order.
     * </p>
     *
     * @param referenceNo
     *            the reference number of the order
     */
    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }
}
