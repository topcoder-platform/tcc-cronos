/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.direct;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for the <code>ContestReceiptData</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestReceiptDataTest extends TestCase {

    /**
     * <p>
     * Represents the <code>ContestReceiptData</code> instance for test.
     * </p>
     */
    private ContestReceiptData contestReceiptData;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        contestReceiptData = new ContestReceiptData();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ContestReceiptData</code>, expects the instance is created properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtorAccuracy() throws Exception {
        assertNotNull("Failed to create the ContestReceiptData instance.", contestReceiptData);
    }

    /**
     * <p>
     * Accuracy test for the <code>getContestId</code> method, expects the contestId is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestIdAccuracy() throws Exception {
        assertEquals("Expects the contestId is 0 by default.", 0, contestReceiptData.getContestId());
    }

    /**
     * <p>
     * Accuracy test for the <code>setContestId</code> method, expects the contestId is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetContestIdAccuracy() throws Exception {
        contestReceiptData.setContestId(1);
        assertEquals("Expects the contestId is set properly.", 1, contestReceiptData.getContestId());
    }

    /**
     * <p>
     * Accuracy test for the <code>isStudio</code> method, expects the studio is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testIsStudioAccuracy() throws Exception {
        assertFalse("Expects the studio is false by default.", contestReceiptData.isStudio());
    }

    /**
     * <p>
     * Accuracy test for the <code>setStudio</code> method, expects the studio is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetStudioAccuracy() throws Exception {
        contestReceiptData.setStudio(true);
        assertTrue("Expects the studio is set properly.", contestReceiptData.isStudio());
    }

    /**
     * <p>
     * Accuracy test for the <code>getProjectName</code> method, expects the projectName is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetProjectNameAccuracy() throws Exception {
        assertNull("Expects the projectName is null by default.", contestReceiptData.getProjectName());
    }

    /**
     * <p>
     * Accuracy test for the <code>setProjectName</code> method, expects the projectName is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetProjectNameAccuracy() throws Exception {
        contestReceiptData.setProjectName("projectName");
        assertEquals("Expects the projectName is set properly.", "projectName", contestReceiptData.getProjectName());
    }

    /**
     * <p>
     * Accuracy test for the <code>getContestName</code> method, expects the contestName is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestNameAccuracy() throws Exception {
        assertNull("Expects the contestName is null by default.", contestReceiptData.getContestName());
    }

    /**
     * <p>
     * Accuracy test for the <code>setContestName</code> method, expects the contestName is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetContestNameAccuracy() throws Exception {
        contestReceiptData.setContestName("contestName");
        assertEquals("Expects the contestName is set properly.", "contestName", contestReceiptData.getContestName());
    }

    /**
     * <p>
     * Accuracy test for the <code>getContestFee</code> method, expects the contestFee is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestFeeAccuracy() throws Exception {
        assertEquals("Expects the contestFee is 0.0 by default.", 0.0, contestReceiptData.getContestFee());
    }

    /**
     * <p>
     * Accuracy test for the <code>setContestFee</code> method, expects the contestFee is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetContestFeeAccuracy() throws Exception {
        contestReceiptData.setContestFee(1.23);
        assertEquals("Expects the contestFee is set properly.", 1.23, contestReceiptData.getContestFee());
    }

    /**
     * <p>
     * Accuracy test for the <code>getStartDate</code> method, expects the startDate is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetStartDateAccuracy() throws Exception {
        assertEquals("Expects the startDate is 0 by default.", 0, 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>setStartDate</code> method, expects the startDate is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetStartDateAccuracy() throws Exception {
        XMLGregorianCalendar startDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(
                new GregorianCalendar());
        contestReceiptData.setStartDate(startDate);
        assertEquals("Expects the startDate is set properly.", startDate, contestReceiptData.getStartDate());
    }

    /**
     * <p>
     * Accuracy test for the <code>getContestType</code> method, expects the contestType is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestTypeAccuracy() throws Exception {
        assertNull("Expects the contestType is null by default.", contestReceiptData.getContestType());
    }

    /**
     * <p>
     * Accuracy test for the <code>setContestType</code> method, expects the contestType is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetContestTypeAccuracy() throws Exception {
        contestReceiptData.setContestType("contestType");
        assertEquals("Expects the contestType is set properly.", "contestType", contestReceiptData.getContestType());
    }

    /**
     * <p>
     * Accuracy test for the <code>getPrizes</code> method, expects the prizes is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetPrizesAccuracy() throws Exception {
        assertNull("Expects the prizes is null by default.", contestReceiptData.getPrizes());
    }

    /**
     * <p>
     * Accuracy test for the <code>setPrizes</code> method, expects the prizes is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetPrizesAccuracy() throws Exception {
        contestReceiptData.setPrizes(new double[] {1.23});

        double[] prizes = contestReceiptData.getPrizes();
        assertEquals("Expects the prizes is set properly.", 1, prizes.length);
        assertEquals("Expects the prizes is set properly.", 1.23, prizes[0]);
    }

    /**
     * <p>
     * Accuracy test for the <code>getMilestoneDate</code> method, expects the milestoneDate is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetMilestoneDateAccuracy() throws Exception {
        assertNull("Expects the milestoneDate is null by default.", contestReceiptData.getMilestoneDate());
    }

    /**
     * <p>
     * Accuracy test for the <code>setMilestoneDate</code> method, expects the milestoneDate is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetMilestoneDateAccuracy() throws Exception {
        XMLGregorianCalendar milestoneDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(
                new GregorianCalendar());
        contestReceiptData.setMilestoneDate(milestoneDate);
        assertEquals("Expects the milestoneDate is set properly.", milestoneDate, contestReceiptData
                .getMilestoneDate());
    }

    /**
     * <p>
     * Accuracy test for the <code>getStatus</code> method, expects the status is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetStatusAccuracy() throws Exception {
        assertNull("Expects the status is null by default.", contestReceiptData.getStatus());
    }

    /**
     * <p>
     * Accuracy test for the <code>setStatus</code> method, expects the status is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetStatusAccuracy() throws Exception {
        contestReceiptData.setStatus("status");
        assertEquals("Expects the status is set properly.", "status", contestReceiptData.getStatus());
    }

    /**
     * <p>
     * Accuracy test for the <code>getContestTotalCost</code> method, expects the contestTotalCost is returned
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestTotalCostAccuracy() throws Exception {
        assertEquals("Expects the contestTotalCost is 0.0 by default.", 0.0, contestReceiptData.getContestTotalCost());
    }

    /**
     * <p>
     * Accuracy test for the <code>setContestTotalCost</code> method, expects the contestTotalCost is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetContestTotalCostAccuracy() throws Exception {
        contestReceiptData.setContestTotalCost(1.23);
        assertEquals("Expects the contestTotalCost is set properly.", 1.23, contestReceiptData.getContestTotalCost());
    }

    /**
     * <p>
     * Accuracy test for the <code>getEndDate</code> method, expects the endDate is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetEndDateAccuracy() throws Exception {
        assertNull("Expects the endDate is null by default.", contestReceiptData.getEndDate());
    }

    /**
     * <p>
     * Accuracy test for the <code>setEndDate</code> method, expects the endDate is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetEndDateAccuracy() throws Exception {
        XMLGregorianCalendar endDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar());
        contestReceiptData.setEndDate(endDate);
        assertEquals("Expects the endDate is set properly.", endDate, contestReceiptData.getEndDate());
    }

    /**
     * <p>
     * Accuracy test for the <code>getCompanyName</code> method, expects the companyName is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetCompanyNameAccuracy() throws Exception {
        assertNull("Expects the companyName is null by default.", contestReceiptData.getCompanyName());
    }

    /**
     * <p>
     * Accuracy test for the <code>setCompanyName</code> method, expects the companyName is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetCompanyNameAccuracy() throws Exception {
        contestReceiptData.setCompanyName("companyName");
        assertEquals("Expects the companyName is set properly.", "companyName", contestReceiptData.getCompanyName());
    }

    /**
     * <p>
     * Accuracy test for the <code>getAddress</code> method, expects the address is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAddressAccuracy() throws Exception {
        assertNull("Expects the address is null by default.", contestReceiptData.getAddress());
    }

    /**
     * <p>
     * Accuracy test for the <code>setAddress</code> method, expects the address is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetAddressAccuracy() throws Exception {
        contestReceiptData.setAddress("address");
        assertEquals("Expects the address is set properly.", "address", contestReceiptData.getAddress());
    }

    /**
     * <p>
     * Accuracy test for the <code>getCity</code> method, expects the city is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetCityAccuracy() throws Exception {
        assertNull("Expects the city is null by default.", contestReceiptData.getCity());
    }

    /**
     * <p>
     * Accuracy test for the <code>setCity</code> method, expects the city is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetCityAccuracy() throws Exception {
        contestReceiptData.setCity("city");
        assertEquals("Expects the city is set properly.", "city", contestReceiptData.getCity());
    }

    /**
     * <p>
     * Accuracy test for the <code>getState</code> method, expects the state is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetStateAccuracy() throws Exception {
        assertNull("Expects the state is 0 by default.", contestReceiptData.getState());
    }

    /**
     * <p>
     * Accuracy test for the <code>setState</code> method, expects the state is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetStateAccuracy() throws Exception {
        contestReceiptData.setState("state");
        assertEquals("Expects the state is set properly.", "state", contestReceiptData.getState());
    }

    /**
     * <p>
     * Accuracy test for the <code>getZipCode</code> method, expects the zipCode is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetZipCodeAccuracy() throws Exception {
        assertNull("Expects the zipCode is null by default.", contestReceiptData.getZipCode());
    }

    /**
     * <p>
     * Accuracy test for the <code>setZipCode</code> method, expects the zipCode is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetZipCodeAccuracy() throws Exception {
        contestReceiptData.setZipCode("zipCode");
        assertEquals("Expects the zipCode is set properly.", "zipCode", contestReceiptData.getZipCode());
    }

    /**
     * <p>
     * Accuracy test for the <code>getCountry</code> method, expects the country is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetCountryAccuracy() throws Exception {
        assertNull("Expects the country is null by default.", contestReceiptData.getCountry());
    }

    /**
     * <p>
     * Accuracy test for the <code>setCountry</code> method, expects the country is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetCountryAccuracy() throws Exception {
        contestReceiptData.setCountry("country");
        assertEquals("Expects the country is set properly.", "country", contestReceiptData.getCountry());
    }

    /**
     * <p>
     * Accuracy test for the <code>getPhone</code> method, expects the phone is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetPhoneAccuracy() throws Exception {
        assertNull("Expects the phone is null by default.", contestReceiptData.getPhone());
    }

    /**
     * <p>
     * Accuracy test for the <code>setPhone</code> method, expects the phone is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetPhoneAccuracy() throws Exception {
        contestReceiptData.setPhone("phone");
        assertEquals("Expects the phone is set properly.", "phone", contestReceiptData.getPhone());
    }

    /**
     * <p>
     * Accuracy test for the <code>getEmail</code> method, expects the email is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetEmailAccuracy() throws Exception {
        assertNull("Expects the email is null by default.", contestReceiptData.getEmail());
    }

    /**
     * <p>
     * Accuracy test for the <code>setEmail</code> method, expects the email is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetEmailAccuracy() throws Exception {
        contestReceiptData.setEmail("email");
        assertEquals("Expects the email is set properly.", "email", contestReceiptData.getEmail());
    }

    /**
     * <p>
     * Accuracy test for the <code>getPurchaseOrderNo</code> method, expects the purchaseOrderNo is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetPurchaseOrderNoAccuracy() throws Exception {
        assertNull("Expects the purchaseOrderNo is null by default.", contestReceiptData.getPurchaseOrderNo());
    }

    /**
     * <p>
     * Accuracy test for the <code>setPurchaseOrderNo</code> method, expects the purchaseOrderNo is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetPurchaseOrderNoAccuracy() throws Exception {
        contestReceiptData.setPurchaseOrderNo("purchaseOrderNo");
        assertEquals("Expects the purchaseOrderNo is set properly.", "purchaseOrderNo", contestReceiptData
                .getPurchaseOrderNo());
    }

    /**
     * <p>
     * Accuracy test for the <code>getInvoiceTerms</code> method, expects the invoiceTerms is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetInvoiceTermsAccuracy() throws Exception {
        assertNull("Expects the invoiceTerms is null by default.", contestReceiptData.getInvoiceTerms());
    }

    /**
     * <p>
     * Accuracy test for the <code>setInvoiceTerms</code> method, expects the invoiceTerms is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetInvoiceTermsAccuracy() throws Exception {
        contestReceiptData.setInvoiceTerms("invoiceTerms");
        assertEquals("Expects the invoiceTerms is set properly.", "invoiceTerms", contestReceiptData
                .getInvoiceTerms());
    }

    /**
     * <p>
     * Accuracy test for the <code>getReferenceNo</code> method, expects the referenceNo is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetReferenceNoAccuracy() throws Exception {
        assertNull("Expects the referenceNo is null by default.", contestReceiptData.getReferenceNo());
    }

    /**
     * <p>
     * Accuracy test for the <code>setReferenceNo</code> method, expects the referenceNo is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetReferenceNoAccuracy() throws Exception {
        contestReceiptData.setReferenceNo("referenceNo");
        assertEquals("Expects the referenceNo is set properly.", "referenceNo", contestReceiptData.getReferenceNo());
    }
}
