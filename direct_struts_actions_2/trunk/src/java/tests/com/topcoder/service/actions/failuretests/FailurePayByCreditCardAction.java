/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.failuretests;

import org.springframework.test.context.ContextConfiguration;

import com.topcoder.service.actions.DeleteDocumentContestAction;
import com.topcoder.service.actions.PayByCreditCardAction;

/**
 * Mock <code>DeleteDocumentContestAction</code>.
 * @author moon.river
 * @version 1.0
 */
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class FailurePayByCreditCardAction extends PayByCreditCardAction {

    /**
     * @return the cardNumber
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * @return the cardType
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * @return the cardExpiryMonth
     */
    public String getCardExpiryMonth() {
        return cardExpiryMonth;
    }

    /**
     * @return the cardExpiryYear
     */
    public String getCardExpiryYear() {
        return cardExpiryYear;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return the zipCode
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the csc
     */
    public String getCsc() {
        return csc;
    }

    /**
     * Card number.
     */
    public static String cardNumber;

    /**
     * Card type.
     */
    public static String cardType;

    /**
     * Card expiry month.
     */
    public static String cardExpiryMonth;

    /**
     * Card expiry month.
     */
    public static String cardExpiryYear;

    /**
     * First name.
     */
    public static String firstName;

    /**
     * Last name.
     */
    public static String lastName;

    /**
     * Zip code.
     */
    public static String zipCode;

    /**
     * Address.
     */
    public static String address;

    /**
     * city.
     */
    public static String city;

    /**
     * County.
     */
    public static String country;

    /**
     * Phone.
     */
    public static String phone;

    /**
     * Email.
     */
    public static String email;

    /**
     * Csc.
     */
    public static String csc;

    /**
     * Prepares the action.
     */
    public void prepare() {
        super.prepare();
        this.setAddress(address);
        this.setCardExpiryMonth(cardExpiryMonth);
        this.setCardNumber(cardNumber);
        this.setCardExpiryYear(cardExpiryYear);
        this.setCardType(cardType);
        this.setCity(city);
        this.setCsc(csc);
        this.setEmail(email);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setPhone(phone);
        this.setZipCode(zipCode);
    }
}
