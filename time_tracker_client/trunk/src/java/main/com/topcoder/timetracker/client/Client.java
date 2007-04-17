/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.topcoder.timetracker.common.PaymentTerm;
import com.topcoder.timetracker.common.TimeTrackerBean;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.project.Project;


/**
 * <p>
 * This class holds the information of a client.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This class is not thread safe by being mutable. This class is not supposed to be used in
 * multi-thread environment. If it would be used in multi-thread environment, it should be synchronized externally.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public class Client extends TimeTrackerBean {
    /**
     * <p>
     * Represents the the id of the company. This variable is set to 0 initially, is mutable. It is only allowed
     * to be set to &gt;0 by the setter. It is access by its getter and setter methods.
     * </p>
     */
    private long companyId = 0;

    /**
     * <p>
     * Represents the name of this Client. This variable is set to null initially; is mutable. It is only allowed
     * to be set to non null, non empty string by the setter. It is access by its getter and setter methods.
     * </p>
     */
    private String name = null;

    /**
     * <p>
     * Represents the salesTax of this Client. This variable is set to 0 initially; is mutable. It is only
     * allowed to be set to &gt;=0 by the setter. It is access by its getter and setter methods.
     * </p>
     */
    private double salesTax = 0;

    /**
     * <p>
     * Represents the start date. This variable is set to null initially; is mutable. It is only allowed to be
     * set to non null, non empty string by the setter. It is access by its getter and setter methods.
     * </p>
     */
    private Date startDate = null;

    /**
     * <p>
     * Represents the end date. This variable is set to null initially; is mutable. It is only allowed to be set
     * to non null, non empty string by the setter. It is access by its getter and setter methods.
     * </p>
     */
    private Date endDate = null;

    /**
     * <p>
     * Represents whether this client is active. This variable is set to false initially; is mutable. It is get
     * by the isActive and set by the setActive method.
     * </p>
     */
    private boolean active = false;

    /**
     * <p>
     * Represents the payment term associated with this Client. This variable is set to null initially; is
     * mutable. It is only allowed to be set to non null by the setter. It is access by its getter and setter methods.
     * </p>
     */
    private PaymentTerm paymentTerm = null;

    /**
     * <p>
     * Represents the projects associated with this Client. This variable is set to null initially; is mutable.
     * It's immutable(reference), and never be null, possible empty. It will contain non null Projects. It is access
     * by its getter/setter methods.
     * </p>
     */
    private List projects = null;

    /**
     * <p>
     * Represents the contact associated with this Client. This variable is set to null initially; is mutable. It
     * is only allowed to be set to non null by the setter. It is access by its getter and setter methods.
     * </p>
     */
    private Contact contact = null;

    /**
     * <p>
     * Represents the address associated with this Client. This variable is set to null initially; is mutable. It
     * is only allowed to be set to non null by the setter. It is access by its getter and setter methods.
     * </p>
     */
    private Address address = null;

    /**
     * <p>
     * Constructs the Client.
     * </p>
     */
    public Client() {
        // empty
    }

    /**
     * <p>
     * Get the Company ID.
     * </p>
     *
     * @return the positive company id of this client
     */
    public long getCompanyId() {
        return companyId;
    }

    /**
     * <p>
     * Set the company id.
     * </p>
     *
     * @param companyId the company id of this client
     *
     * @throws IllegalArgumentException if id is not positive
     */
    public void setCompanyId(long companyId) {
        Helper.checkPositive(companyId, "companyId");

        this.companyId = companyId;
        this.setChanged(true);
    }

    /**
     * <p>
     * Get whether this client is active.
     * </p>
     *
     * @return whether this client is active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * <p>
     * Set the active property.
     * </p>
     *
     * @param active whether this client is active
     */
    public void setActive(boolean active) {
        this.active = active;
        this.setChanged(true);
    }

    /**
     * <p>
     * Get the name.
     * </p>
     *
     * @return possible null, non empty name of this client
     */
    public String getName() {
        return this.name;
    }

    /**
     * <p>
     * Set the name.
     * </p>
     *
     * @param name the non null, non empty(trim'd) name
     *
     * @throws IllegalArgumentException if the name is null or empty(trim'd)
     */
    public void setName(String name) {
        Helper.checkString(name, "name");
        this.name = name;

        this.setChanged(true);
    }

    /**
     * <p>
     * Get the sales tax.
     * </p>
     *
     * @return the sales tax
     */
    public double getSalesTax() {
        return salesTax;
    }

    /**
     * <p>
     * Set the sales tax.
     * </p>
     *
     * @param salesTax the sales tax
     *
     * @throws IllegalArgumentException if the tax less than 0
     */
    public void setSalesTax(double salesTax) {
        checkNotNegative(salesTax, "salesTax");

        this.salesTax = salesTax;
        this.setChanged(true);
    }

    /**
     * <p>
     * Get the start date.
     * </p>
     *
     * @return possible null start date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * <p>
     * Set the start date.
     * </p>
     *
     * @param startDate non null start date
     *
     * @throws IllegalArgumentException if the date is null
     */
    public void setStartDate(Date startDate) {
        Helper.checkNull(startDate, "startDate");

        this.startDate = startDate;
        this.setChanged(true);
    }

    /**
     * <p>
     * Get the end date.
     * </p>
     *
     * @return possible null end date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * <p>
     * Set the end date.
     * </p>
     *
     * @param endDate non null end date
     *
     * @throws IllegalArgumentException if the date is null
     */
    public void setEndDate(Date endDate) {
        Helper.checkNull(endDate, "endDate");

        this.endDate = endDate;
        this.setChanged(true);
    }

    /**
     * <p>
     * Get the associated contact.
     * </p>
     *
     * @return the possible null contact
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * <p>
     * Set the contact.
     * </p>
     *
     * @param contact non null contact
     *
     * @throws IllegalArgumentException if the contact is null
     */
    public void setContact(Contact contact) {
        Helper.checkNull(contact, "contact");
        this.contact = contact;

        this.setChanged(true);
    }

    /**
     * <p>
     * Get the associated address.
     * </p>
     *
     * @return possible null address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * <p>
     * Set the address.
     * </p>
     *
     * @param address non null address
     *
     * @throws IllegalArgumentException if the address is null
     */
    public void setAddress(Address address) {
        Helper.checkNull(address, "address");

        this.address = address;

        this.setChanged(true);
    }

    /**
     * <p>
     * Set the projects.
     * </p>
     *
     * @param projects non null, possible empty projects containing non null project
     *
     * @throws IllegalArgumentException if projects is null or containing null project
     */
    public void setProjects(Project[] projects) {
        Helper.checkArray(projects, "projects");

        this.projects = Arrays.asList(projects);

        this.setChanged(true);
    }

    /**
     * <p>
     * Get the associated projects.
     * </p>
     *
     * @return non null, possible empty array containing non null project
     */
    public Project[] getProjects() {
        return (Project[]) this.projects.toArray(new Project[this.projects.size()]);
    }

    /**
     * <p>
     * Get the associated payment term.
     * </p>
     *
     * @return possible null payment term
     */
    public PaymentTerm getPaymentTerm() {
        return paymentTerm;
    }

    /**
     * <p>
     * Set the paymentTerm.
     * </p>
     *
     * @param paymentTerm non null payment term
     *
     * @throws IllegalArgumentException if the payment term is null
     */
    public void setPaymentTerm(PaymentTerm paymentTerm) {
        Helper.checkNull(paymentTerm, "paymentTerm");

        this.paymentTerm = paymentTerm;
        this.setChanged(true);
    }


    /**
     * Checks whether the given number is not negative.
     *
     * @param number the number to be checked
     * @param name the name of the number
     *
     * @throws IllegalArgumentException if the given number is negative
     */
    private void checkNotNegative(double number, String name) {
        if (number < 0) {
            throw new IllegalArgumentException(name + " should be positive.");
        }
    }
    
}
