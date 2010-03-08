/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

/**
 * <p>
 * The Employee entity used in the unit tests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Employee {

    /**
     * The employee first name. Accessed and modified using the namesake getter/setter.
     */
    private String firstName;

    /**
     * The employee last name. Accessed and modified using the namesake getter/setter.
     */
    private String lastName;

    /**
     * The employee address. Accessed and modified using the namesake getter/setter.
     */
    private String address;

    /**
     * The employee age. Accessed and modified using the namesake getter/setter.
     */
    private int age;

    /**
     * The employee department. Accessed and modified using the namesake getter/setter.
     */
    private Department department;

    /**
     * The employee introducer. Accessed and modified using the namesake getter/setter.
     */
    private String introducer;

    /**
     * Getter for employee first name.
     *
     * @return employee first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for employee first name.
     *
     * @param firstName employee first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter for employee last name.
     *
     * @return employee last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter for employee last name.
     *
     * @param lastName employee last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter for employee address.
     *
     * @return employee address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter for employee address.
     *
     * @param address employee address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter for employee age.
     *
     * @return employee age
     */
    public int getAge() {
        return age;
    }

    /**
     * Setter for employee age.
     *
     * @param age employee age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Getter for employee department.
     *
     * @return employee department
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * Setter for employee department.
     *
     * @param department employee department
     */
    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     * Getter for employee introducer.
     *
     * @return employee introducer
     */
    public String getIntroducer() {
        return introducer;
    }

    /**
     * Setter for employee introducer.
     *
     * @param introducer employee introducer
     */
    public void setIntroducer(String introducer) {
        this.introducer = introducer;
    }
}
