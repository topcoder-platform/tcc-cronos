/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import com.topcoder.timetracker.common.TimeTrackerBean;
import com.topcoder.timetracker.contact.Address;
import java.util.Date;
import com.topcoder.timetracker.common.PaymentTerm;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.project.db.Util;

/**
 * <p>
 * This is the main data class of the component, and includes getters and setters to
 * access the various properties of a Time Tracker Project.
 * </p>
 *
 * <p>
 * This class encapsulates the project information such as the project name, timeline,
 * contact information, and address.
 * </p>
 *
 * <p>
 * It also extends from the base <code>TimeTrackerBean</code> to include the  creation and
 * modification details.
 * </p>
 *
 * <p>
 * Thread Safety: This class is not thread safe. Each thread is expected to work on it's own instance,
 * or this class should be used in a read-only manner for concurrent access.
 * </p>
 *
 * @author ShindouHikaru, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class Project extends TimeTrackerBean {

    /**
     * Automatically generated unique ID for use with serialization.
     */
    private static final long serialVersionUID = 3094214342878839547L;

    /**
     * <p>
     * This is the description of a project, which is a human-readable String that briefly
     * describes the project.
     * </p>
     *
     * <p>
     * It is null initially and can be accessed by getter and setter.
     * </p>
     *
     * <p>
     * It can not be null or empty string after it is set.
     * </p>
     */
    private String description = "";

    /**
     * <p>
     * This is the name of the project.
     * </p>
     *
     * <p>
     * It is null initially and can be accessed by getter and setter.
     * </p>
     *
     * <p>
     * It can not be null or empty string after it is set.
     * </p>
     */
    private String name;

    /**
     * <p>
     * This is the date when the project started, or is estimated to start.
     * </p>
     *
     * <p>
     * It is null initially and can be accessed by getter and setter.
     * </p>
     *
     * <p>
     * It can not be null after it is set.
     * </p>
     */
    private Date startDate;

    /**
     * <p>
     * This is the date when the project ended, or is estimated to end.
     * </p>
     *
     * <p>
     * It is null initially and can be accessed by getter and setter.
     * </p>
     *
     * <p>
     * It can not be null after it is set.
     * </p>
     */
    private Date endDate;

    /**
     * <p>
     * This is a number that uniquely identifies the company that this project is associated with.
     * </p>
     *
     * <p>
     * This is the company that owns the project. This is used to tie into the Time Tracker Company
     * component.
     * </p>
     *
     * <p>
     * It is zero initially and can be accessed by getter and setter.
     * </p>
     *
     * <p>
     * It can not be negative or zero after it is set.
     * </p>
     */
    private long companyId;

    /**
     * <p>
     * This is the relevant sales tax that may be applied for any invoices provided for the project.
     * </p>
     * <p>
     * It is zero initially and can be accessed by getter and setter.
     * </p>
     *
     * <p>
     * It will never be negative.
     * </p>
     */
    private double salesTax;

    /**
     * <p>
     * This is the P.O. box number for the project.
     * </p>
     * <p>
     * It is null initially and can be accessed by getter and setter.
     * </p>
     *
     * <p>
     * It can not be null after it is set.
     * </p>
     */
    private String poBoxNumber;

    /**
     * <p>
     * This is the contact information that may be used in order to get in touch with
     * the project.
     * </p>
     *
     * <p>
     * It is null initially and can be accessed by getter and setter.
     * </p>
     *
     * <p>
     * It can not be null after it is set.
     * </p>
     */
    private Contact contact;

    /**
     * <p>
     * This is the <code>Address</code>, representing the location where the project is based.
     * </p>
     *
     * <p>
     * It is null initially and can be accessed by getter and setter.
     * </p>
     *
     * <p>
     * It can not be null after it is set.
     * </p>
     */
    private Address address;

    /**
     * <p>
     * This is an identifier that is used to represent the client for the project.
     * </p>
     *
     * <p>
     * This is the entity that has contracted the company involved to perform work on the project.
     * </p>
     *
     * <p>
     * This is used to tie into the Time Tracker Client component.
     * </p>
     *
     * <p>
     * It is zero initially and can be accessed by getter and setter.
     * </p>
     *
     * <p>
     * It can not be negative or zero after it is set.
     * </p>
     */
    private long clientId;

    /**
     * <p>
     * This indicates whether the Project is currently active or inactive.  It defaults to "false".
     * </p>
     *
     * <p>
     * It is false initially and can be accessed by getter and setter.
     * </p>
     */
    private boolean active;

    /**
     * <p>
     * This represents the Terms of payment for this project.
     * </p>
     *
     * <p>
     * It is null initially and can be accessed by getter and setter.
     * </p>
     *
     * <p>
     * It can not be null after it is set.
     * </p>
     */
    private PaymentTerm terms;

    /**
     * <p>
     * Default Constructor.
     * </p>
     */
    public Project() {
        // empty
    }

    /**
     * <p>
     * Retrieves the description of a project, which is a human-readable String that briefly
     * describes the project.
     * </p>
     *
     * @return the description of a project.
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>
     * Sets the description of a project, which is a human-readable String that briefly
     * describes the project.
     * </p>
     *
     * @param description the description of a project.
     *
     * @throws IllegalArgumentException if description is null or an empty String.
     */
    public void setDescription(String description) {
        if (description == null || description.trim().length() == 0) {
            description = "";
        }

        if (!description.equals(this.description)) {
            this.description = description;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Retrieves the name of the project.
     * </p>
     *
     * @return the name of the project.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Sets the name of the project.
     * </p>
     *
     * @param name the name of the project.
     *
     * @throws IllegalArgumentException if name is null or an empty String.
     */
    public void setName(String name) {
        Util.checkString(name, "name");

        if (!name.equals(this.name)) {
            this.name = name;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Retrieves the date when the project started, or is estimated to start.
     * </p>
     *
     * @return the date when the project started, or is estimated to start.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * <p>
     * Sets the date when the project started, or is estimated to start.
     * </p>
     *
     * @param startDate the date when the project started, or is estimated to start.
     *
     * @throws IllegalArgumentException if startDate is null.
     */
    public void setStartDate(Date startDate) {
        Util.checkNull(startDate, "startDate");

        if (!startDate.equals(this.startDate)) {
            this.startDate = startDate;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Retrieves the date when the project ended, or is estimated to end.
     * </p>
     *
     * @return the date when the project ended, or is estimated to end.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * <p>
     * Sets the date when the project ended, or is estimated to end.
     * </p>
     *
     * @param endDate the date when the project ended, or is estimated to end.
     *
     * @throws IllegalArgumentException if endDate is null.
     */
    public void setEndDate(Date endDate) {
        Util.checkNull(endDate, "endDate");

        if (!endDate.equals(this.endDate)) {
            this.endDate = endDate;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Retrieves a number that uniquely identifies the company that this project is associated with.
     * </p>
     *
     * <p>
     * This is the company that owns the project. This is used to tie into the Time Tracker Company
     * component.
     * </p>
     *
     * @return a number that uniquely identifies the company that this project is associated with.
     */
    public long getCompanyId() {
        return companyId;
    }

    /**
     * <p>
     * Sets a number that uniquely identifies the company that this project is associated with.
     * This is the company that owns the project.
     * </p>
     *
     * <p>
     * This is used to tie into the Time Tracker Company component.
     * </p>
     *
     * @param companyId a number that uniquely identifies the company that this project is associated with.
     *
     * @throws IllegalArgumentException if companyId is a negative number or 0.
     */
    public void setCompanyId(long companyId) {
        Util.checkIdValue(companyId, "company");

        if (companyId != this.companyId) {
            this.companyId = companyId;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Retrieves the relevant sales tax that may be applied for any invoices provided for the project.
     * </p>
     *
     * @return the relevant sales tax that may be applied for any invoices provided for the project.
     */
    public double getSalesTax() {
        return salesTax;
    }

    /**
     * <p>
     * Sets the relevant sales tax that may be applied for any invoices provided for the project.
     * </p>
     *
     * @param salesTax the relevant sales tax that may be applied for any invoices provided for the project.
     *
     * @throws IllegalArgumentException if salesTax is a negative number.
     */
    public void setSalesTax(double salesTax) {
        if (salesTax < 0) {
            throw new IllegalArgumentException("The given sales tax is negative.");
        }

        if (salesTax != this.salesTax) {
            this.salesTax = salesTax;
            setChanged(true);
        }
    }

    /**
     * Retrieves the P.O. box number for the project.
     *
     * @return the P.O. box number for the project.
     */
    public String getPoBoxNumber() {
        return this.poBoxNumber;
    }

    /**
     * Sets the P.O. box number for the project.
     *
     * @param poBoxNumber
     *            the P.O. box number for the project.
     * @throws IllegalArgumentException
     *             if <code>poBoxNumber</code> is <code>null</code> or an empty string.
     */
    public void setPoBoxNumber(String poBoxNumber) {
        Util.checkString(poBoxNumber, "poBoxNumber");

        if (!poBoxNumber.equals(this.poBoxNumber)) {
            this.poBoxNumber = poBoxNumber;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Retrieves the contact information that may be used in order to get in touch with someone related
     * with the project.
     * </p>
     *
     * @return the contact information for the project.
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * <p>
     * Sets the contact information that may be used in order to get in touch with someone related
     * with the project.
     * </p>
     *
     * @param contact the contact information for the project.
     *
     * @throws IllegalArgumentException if contact is null.
     */
    public void setContact(Contact contact) {
        Util.checkNull(contact, "contact");

        if (!contact.equals(this.contact)) {
            this.contact = contact;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Gets the Address, representing the location where this project is based.
     * </p>
     *
     * @return The Address of the project.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * <p>
     * Sets the Address, representing the location where this project is based.
     * </p>
     *
     * @param address The Address of the project.
     *
     * @throws IllegalArgumentException if address is null.
     */
    public void setAddress(Address address) {
        Util.checkNull(address, "address");

        if (!address.equals(this.address)) {
            this.address = address;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Gets an identifier that is used to represent the client for the project.
     * </p>
     *
     * <p>
     * This is the entity that has contracted the company involved to perform work on the project.
     * This is used to tie into the Time Tracker Client component.
     * </p>
     *
     * @return The identifier for the client associated with this project,
     */
    public long getClientId() {
        return clientId;
    }

    /**
     * <p>
     * Sets an identifier that is used to represent the client for the project.
     * </p>
     *
     * <p>
     * This is the entity that has contracted the company involved to perform work on the project.
     * This is used to tie into the Time Tracker Client component.
     * </p>
     *
     * @param clientId The identifier for the client associated with this project
     *
     * @throws IllegalArgumentException if clientId is &lt;= 0.
     */
    public void setClientId(long clientId) {
        Util.checkIdValue(clientId, "client");

        if (clientId != this.clientId) {
            this.clientId = clientId;
            setChanged(true);
        }
    }

    /**
     * <p>
     * This indicates whether the Project is currently active or inactive.
     * </p>
     *
     * <p>
     * Note, it defaults to &quot;false&quot;.
     * </p>
     *
     * @return whether the Project is currently active or inactive.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * <p>
     * Sets whether the Project is currently active or inactive.
     * </p>
     *
     * <p>
     * Note, it defaults to "false".
     * </p>
     *
     * @param active whether the Project is currently active or inactive.
     */
    public void setActive(boolean active) {
        if (active != this.active) {
            this.active = active;
            setChanged(true);
        }
    }

    /**
     * <p>
     * This indicates whether the Project is currently active or inactive.
     * </p>
     *
     * <p>
     * Note, it defaults to "false".
     * </p>
     *
     * @return whether the Project is currently active or inactive.
     */
    public boolean getActive() {
        return this.active;
    }

    /**
     * <p>
     * Gets the Terms of payment for this project.
     * </p>
     *
     * @return The terms of payment for the project.
     */
    public PaymentTerm getTerms() {
        return terms;
    }

    /**
     * <p>
     * Sets the Terms of payment for this project.
     * </p>
     *
     * @param terms the Terms of payment for this project.
     *
     * @throws IllegalArgumentException if terms is null.
     */
    public void setTerms(PaymentTerm terms) {
        Util.checkNull(terms, "terms");

        if (!terms.equals(this.terms)) {
            this.terms = terms;
            setChanged(true);
        }
    }
}
