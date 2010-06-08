package com.topcoder.service.facade.direct.accuracytests;
/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import junit.framework.TestCase;

import com.topcoder.service.facade.direct.ContestReceiptData;
/**
 * This class contains unit tests for <code>ContestReceiptData</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestContestReceiptData extends TestCase {
    /**
     * <p>
     * Represents ContestReceiptData instance to test against.
     * </p>
     */
    private ContestReceiptData instance = null;

    /**
     * Set Up the test environment before testing.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        instance = new ContestReceiptData();
    }

    /**
     * Clean up the test environment after testing.
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * <p>
     * Test method for {@link ContestReceiptData#ContestReceiptData()}. It verifies the new instance is created.
     * </p>
     */
    public void testContestReceiptData() {
        assertNotNull("Unable to instantiate ContestReceiptData", instance);
    }
    /**
     * <p>
     * Test method for {@link ContestReceiptData#getContestId()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetContestId() {
        assertEquals("Incorrect default value for contestId", 0, instance.getContestId());

        // set a value
        instance.setContestId(8L);
        assertEquals("Incorrect contestId", 8L, instance.getContestId());
    }

    /**
     * <p>
     * Test method for {@link ContestReceiptData#setContestId(long)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetContestId() {
        // set a value
        instance.setContestId(8L);
        assertEquals("Incorrect contestId", 8L, instance.getContestId());
    }
    /**
     * <p>
     * Test method for {@link ContestReceiptData#isStudio()}. It verifies the returned value is correct.
     * </p>
     */
    public void testisStudio() {
        assertFalse("Incorrect default value for studio", instance.isStudio());

        // set a value
        instance.setStudio(true);
        assertEquals("Incorrect studio", true, instance.isStudio());
    }

    /**
     * <p>
     * Test method for {@link ContestReceiptData#setStudio(boolean)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetStudio() {
        // set a value
        instance.setStudio(true);
        assertEquals("Incorrect studio", true, instance.isStudio());
    }
    /**
     * <p>
     * Test method for {@link ContestReceiptData#getProjectName()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetProjectName() {
        assertNull("Incorrect default value for projectName", instance.getProjectName());

        // set a value
        instance.setProjectName("myString");
        assertEquals("Incorrect projectName", "myString", instance.getProjectName());
    }

    /**
     * <p>
     * Test method for {@link ContestReceiptData#setProjectName(String)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetProjectName() {
        // set a value
        instance.setProjectName("myString");
        assertEquals("Incorrect projectName", "myString", instance.getProjectName());

        // set to null
        instance.setProjectName(null);
        assertNull("Incorrect projectName after set to null", instance.getProjectName());
    }
    /**
     * <p>
     * Test method for {@link ContestReceiptData#getContestName()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetContestName() {
        assertNull("Incorrect default value for contestName", instance.getContestName());

        // set a value
        instance.setContestName("myString");
        assertEquals("Incorrect contestName", "myString", instance.getContestName());
    }

    /**
     * <p>
     * Test method for {@link ContestReceiptData#setContestName(String)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetContestName() {
        // set a value
        instance.setContestName("myString");
        assertEquals("Incorrect contestName", "myString", instance.getContestName());

        // set to null
        instance.setContestName(null);
        assertNull("Incorrect contestName after set to null", instance.getContestName());
    }
    /**
     * <p>
     * Test method for {@link ContestReceiptData#getContestFee()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetContestFee() {
        assertEquals("Incorrect default value for contestFee", 0D, instance.getContestFee());

        // set a value
        instance.setContestFee(8.888D);
        assertEquals("Incorrect contestFee", 8.888D, instance.getContestFee());
    }

    /**
     * <p>
     * Test method for {@link ContestReceiptData#setContestFee(double)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetContestFee() {
        // set a value
        instance.setContestFee(8.888D);
        assertEquals("Incorrect contestFee", 8.888D, instance.getContestFee());
    }
    /**
     * <p>
     * Test method for {@link ContestReceiptData#getStartDate()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetStartDate() {
        assertNull("Incorrect default value for startDate", instance.getStartDate());

        XMLGregorianCalendar cal = getXMLGregorianCalendar(new Date());

        // set a value
        instance.setStartDate(cal);
        assertEquals("Incorrect startDate", cal, instance.getStartDate());
    }

    /**
     * <p>
     * Test method for {@link ContestReceiptData#setStartDate(XMLGregorianCalendar)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetStartDate() {
        XMLGregorianCalendar cal = getXMLGregorianCalendar(new Date());

        // set a value
        instance.setStartDate(cal);
        assertEquals("Incorrect startDate", cal, instance.getStartDate());

        // set to null
        instance.setStartDate(null);
        assertNull("Incorrect startDate after set to null", instance.getStartDate());
    }
    /**
     * <p>
     * Test method for {@link ContestReceiptData#getContestType()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetContestType() {
        assertNull("Incorrect default value for contestType", instance.getContestType());

        // set a value
        instance.setContestType("myString");
        assertEquals("Incorrect contestType", "myString", instance.getContestType());
    }

    /**
     * <p>
     * Test method for {@link ContestReceiptData#setContestType(String)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetContestType() {
        // set a value
        instance.setContestType("myString");
        assertEquals("Incorrect contestType", "myString", instance.getContestType());

        // set to null
        instance.setContestType(null);
        assertNull("Incorrect contestType after set to null", instance.getContestType());
    }
    /**
     * <p>
     * Test method for {@link ContestReceiptData#getPrizes()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetPrizes() {
        assertNull("Incorrect default value for prizes", instance.getPrizes());

        double[] dd = new double[]{1.11};

        // set a value
        instance.setPrizes(dd);
        assertEquals("Incorrect prizes", dd, instance.getPrizes());
    }

    /**
     * <p>
     * Test method for {@link ContestReceiptData#setPrizes(double[])}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetPrizes() {
        double[] dd = new double[]{1.11};

        // set a value
        instance.setPrizes(dd);
        assertEquals("Incorrect prizes", dd, instance.getPrizes());

        // set to null
        instance.setPrizes(null);
        assertNull("Incorrect prizes after set to null", instance.getPrizes());
    }
    /**
     * <p>
     * Test method for {@link ContestReceiptData#getMilestoneDate()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetMilestoneDate() {
        assertNull("Incorrect default value for milestoneDate", instance.getMilestoneDate());

        XMLGregorianCalendar cal = getXMLGregorianCalendar(new Date());

        // set a value
        instance.setMilestoneDate(cal);
        assertEquals("Incorrect milestoneDate", cal, instance.getMilestoneDate());
    }

    /**
     * <p>
     * Test method for {@link ContestReceiptData#setMilestoneDate(XMLGregorianCalendar)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetMilestoneDate() {
        XMLGregorianCalendar cal = getXMLGregorianCalendar(new Date());

        // set a value
        instance.setMilestoneDate(cal);
        assertEquals("Incorrect milestoneDate", cal, instance.getMilestoneDate());

        // set to null
        instance.setMilestoneDate(null);
        assertNull("Incorrect milestoneDate after set to null", instance.getMilestoneDate());
    }
    /**
     * <p>
     * Test method for {@link ContestReceiptData#getStatus()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetStatus() {
        assertNull("Incorrect default value for status", instance.getStatus());

        // set a value
        instance.setStatus("myString");
        assertEquals("Incorrect status", "myString", instance.getStatus());
    }

    /**
     * <p>
     * Test method for {@link ContestReceiptData#setStatus(String)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetStatus() {
        // set a value
        instance.setStatus("myString");
        assertEquals("Incorrect status", "myString", instance.getStatus());

        // set to null
        instance.setStatus(null);
        assertNull("Incorrect status after set to null", instance.getStatus());
    }
    /**
     * <p>
     * Test method for {@link ContestReceiptData#getContestTotalCost()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetContestTotalCost() {
        assertEquals("Incorrect default value for contestTotalCost", 0D, instance.getContestTotalCost());

        // set a value
        instance.setContestTotalCost(8.888D);
        assertEquals("Incorrect contestTotalCost", 8.888D, instance.getContestTotalCost());
    }

    /**
     * <p>
     * Test method for {@link ContestReceiptData#setContestTotalCost(double)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetContestTotalCost() {
        // set a value
        instance.setContestTotalCost(8.888D);
        assertEquals("Incorrect contestTotalCost", 8.888D, instance.getContestTotalCost());
    }
    /**
     * <p>
     * Test method for {@link ContestReceiptData#getEndDate()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetEndDate() {
        assertNull("Incorrect default value for endDate", instance.getEndDate());

        XMLGregorianCalendar cal = getXMLGregorianCalendar(new Date());

        // set a value
        instance.setEndDate(cal);
        assertEquals("Incorrect endDate", cal, instance.getEndDate());
    }

    /**
     * <p>
     * Test method for {@link ContestReceiptData#setEndDate(XMLGregorianCalendar)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetEndDate() {
        XMLGregorianCalendar cal = getXMLGregorianCalendar(new Date());

        // set a value
        instance.setEndDate(cal);
        assertEquals("Incorrect endDate", cal, instance.getEndDate());

        // set to null
        instance.setEndDate(null);
        assertNull("Incorrect endDate after set to null", instance.getEndDate());
    }
    /**
     * <p>
     * Test method for {@link ContestReceiptData#getCompanyName()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetCompanyName() {
        assertNull("Incorrect default value for companyName", instance.getCompanyName());

        // set a value
        instance.setCompanyName("myString");
        assertEquals("Incorrect companyName", "myString", instance.getCompanyName());
    }

    /**
     * <p>
     * Test method for {@link ContestReceiptData#setCompanyName(String)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetCompanyName() {
        // set a value
        instance.setCompanyName("myString");
        assertEquals("Incorrect companyName", "myString", instance.getCompanyName());

        // set to null
        instance.setCompanyName(null);
        assertNull("Incorrect companyName after set to null", instance.getCompanyName());
    }
    /**
     * <p>
     * Test method for {@link ContestReceiptData#getAddress()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetAddress() {
        assertNull("Incorrect default value for address", instance.getAddress());

        // set a value
        instance.setAddress("myString");
        assertEquals("Incorrect address", "myString", instance.getAddress());
    }

    /**
     * <p>
     * Test method for {@link ContestReceiptData#setAddress(String)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetAddress() {
        // set a value
        instance.setAddress("myString");
        assertEquals("Incorrect address", "myString", instance.getAddress());

        // set to null
        instance.setAddress(null);
        assertNull("Incorrect address after set to null", instance.getAddress());
    }
    /**
     * <p>
     * Test method for {@link ContestReceiptData#getCity()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetCity() {
        assertNull("Incorrect default value for city", instance.getCity());

        // set a value
        instance.setCity("myString");
        assertEquals("Incorrect city", "myString", instance.getCity());
    }

    /**
     * <p>
     * Test method for {@link ContestReceiptData#setCity(String)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetCity() {
        // set a value
        instance.setCity("myString");
        assertEquals("Incorrect city", "myString", instance.getCity());

        // set to null
        instance.setCity(null);
        assertNull("Incorrect city after set to null", instance.getCity());
    }
    /**
     * <p>
     * Test method for {@link ContestReceiptData#getState()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetState() {
        assertNull("Incorrect default value for state", instance.getState());

        // set a value
        instance.setState("myString");
        assertEquals("Incorrect state", "myString", instance.getState());
    }

    /**
     * <p>
     * Test method for {@link ContestReceiptData#setState(String)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetState() {
        // set a value
        instance.setState("myString");
        assertEquals("Incorrect state", "myString", instance.getState());

        // set to null
        instance.setState(null);
        assertNull("Incorrect state after set to null", instance.getState());
    }
    /**
     * <p>
     * Test method for {@link ContestReceiptData#getZipCode()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetZipCode() {
        assertNull("Incorrect default value for zipCode", instance.getZipCode());

        // set a value
        instance.setZipCode("myString");
        assertEquals("Incorrect zipCode", "myString", instance.getZipCode());
    }

    /**
     * <p>
     * Test method for {@link ContestReceiptData#setZipCode(String)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetZipCode() {
        // set a value
        instance.setZipCode("myString");
        assertEquals("Incorrect zipCode", "myString", instance.getZipCode());

        // set to null
        instance.setZipCode(null);
        assertNull("Incorrect zipCode after set to null", instance.getZipCode());
    }
    /**
     * <p>
     * Test method for {@link ContestReceiptData#getCountry()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetCountry() {
        assertNull("Incorrect default value for country", instance.getCountry());

        // set a value
        instance.setCountry("myString");
        assertEquals("Incorrect country", "myString", instance.getCountry());
    }

    /**
     * <p>
     * Test method for {@link ContestReceiptData#setCountry(String)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetCountry() {
        // set a value
        instance.setCountry("myString");
        assertEquals("Incorrect country", "myString", instance.getCountry());

        // set to null
        instance.setCountry(null);
        assertNull("Incorrect country after set to null", instance.getCountry());
    }
    /**
     * <p>
     * Test method for {@link ContestReceiptData#getPhone()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetPhone() {
        assertNull("Incorrect default value for phone", instance.getPhone());

        // set a value
        instance.setPhone("myString");
        assertEquals("Incorrect phone", "myString", instance.getPhone());
    }

    /**
     * <p>
     * Test method for {@link ContestReceiptData#setPhone(String)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetPhone() {
        // set a value
        instance.setPhone("myString");
        assertEquals("Incorrect phone", "myString", instance.getPhone());

        // set to null
        instance.setPhone(null);
        assertNull("Incorrect phone after set to null", instance.getPhone());
    }
    /**
     * <p>
     * Test method for {@link ContestReceiptData#getEmail()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetEmail() {
        assertNull("Incorrect default value for email", instance.getEmail());

        // set a value
        instance.setEmail("myString");
        assertEquals("Incorrect email", "myString", instance.getEmail());
    }

    /**
     * <p>
     * Test method for {@link ContestReceiptData#setEmail(String)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetEmail() {
        // set a value
        instance.setEmail("myString");
        assertEquals("Incorrect email", "myString", instance.getEmail());

        // set to null
        instance.setEmail(null);
        assertNull("Incorrect email after set to null", instance.getEmail());
    }
    /**
     * <p>
     * Test method for {@link ContestReceiptData#getPurchaseOrderNo()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetPurchaseOrderNo() {
        assertNull("Incorrect default value for purchaseOrderNo", instance.getPurchaseOrderNo());

        // set a value
        instance.setPurchaseOrderNo("myString");
        assertEquals("Incorrect purchaseOrderNo", "myString", instance.getPurchaseOrderNo());
    }

    /**
     * <p>
     * Test method for {@link ContestReceiptData#setPurchaseOrderNo(String)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetPurchaseOrderNo() {
        // set a value
        instance.setPurchaseOrderNo("myString");
        assertEquals("Incorrect purchaseOrderNo", "myString", instance.getPurchaseOrderNo());

        // set to null
        instance.setPurchaseOrderNo(null);
        assertNull("Incorrect purchaseOrderNo after set to null", instance.getPurchaseOrderNo());
    }
    /**
     * <p>
     * Test method for {@link ContestReceiptData#getInvoiceTerms()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetInvoiceTerms() {
        assertNull("Incorrect default value for invoiceTerms", instance.getInvoiceTerms());

        // set a value
        instance.setInvoiceTerms("myString");
        assertEquals("Incorrect invoiceTerms", "myString", instance.getInvoiceTerms());
    }

    /**
     * <p>
     * Test method for {@link ContestReceiptData#setInvoiceTerms(String)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetInvoiceTerms() {
        // set a value
        instance.setInvoiceTerms("myString");
        assertEquals("Incorrect invoiceTerms", "myString", instance.getInvoiceTerms());

        // set to null
        instance.setInvoiceTerms(null);
        assertNull("Incorrect invoiceTerms after set to null", instance.getInvoiceTerms());
    }
    /**
     * <p>
     * Test method for {@link ContestReceiptData#getReferenceNo()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetReferenceNo() {
        assertNull("Incorrect default value for referenceNo", instance.getReferenceNo());

        // set a value
        instance.setReferenceNo("myString");
        assertEquals("Incorrect referenceNo", "myString", instance.getReferenceNo());
    }

    /**
     * <p>
     * Test method for {@link ContestReceiptData#setReferenceNo(String)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetReferenceNo() {
        // set a value
        instance.setReferenceNo("myString");
        assertEquals("Incorrect referenceNo", "myString", instance.getReferenceNo());

        // set to null
        instance.setReferenceNo(null);
        assertNull("Incorrect referenceNo after set to null", instance.getReferenceNo());
    }

    /**
     * Converts standard java Date object into XMLGregorianCalendar instance.
     * Returns null if parameter is null.
     *
     * @param date
     *            Date object to convert
     * @return converted calendar instance
     */
    private XMLGregorianCalendar getXMLGregorianCalendar(Date date) {
        if (date == null) {
            return null;
        }
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}