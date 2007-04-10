/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company;

import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.Contact;

import junit.framework.TestCase;


/**
 * <p>
 * Tests functionality and error cases of <code>Company</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class CompanyUnitTest extends TestCase {
    /** Represents the <code>Company</code> instance used for testing. */
    private Company company = null;

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     */
    protected void setUp() {
        company = new Company();
    }

    /**
     * <p>
     * Tests the accuracy of constructor <code>Company()</code>.
     * </p>
     */
    public void testCompany_Accuracy() {
        assertNotNull("The Company instance should be created.", company);
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getAddress()</code>, should return the default value.
     * </p>
     */
    public void testGetAddress_Default_Accuracy() {
        assertEquals("The address value should be got properly.", null, company.getAddress());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getAddress()</code>, should return the set value.
     * </p>
     */
    public void testGetAddress_Accuracy() {
        Address address = new Address();
        UnitTestHelper.setPrivateField(Company.class, company, "address", address);
        assertEquals("The address value should be got properly.", address, company.getAddress());
    }

    /**
     * <p>
     * Tests the method <code>setAddress(Address address)</code> when the given address is null,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetAddress_NullAddress() {
        try {
            company.setAddress(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setAddress(Address address)</code>. The changed flag of the company should be
     * updated as the new value is different from the old one.
     * </p>
     */
    public void testSetAddress_Changed_Accuracy() {
        Address address = new Address();
        company.setAddress(address);
        assertEquals("The address value should be set properly.", address,
            UnitTestHelper.getPrivateField(Company.class, company, "address"));
        assertEquals("The changed flag of the company should be true.", true, company.isChanged());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setAddress(Address address)</code>. The changed flag of the company should
     * not be updated as the new value is the same as the old one.
     * </p>
     */
    public void testSetAddress_NotChanged_Accuracy() {
        Address address = new Address();
        company.setAddress(address);

        // reset the value of the change flag.
        company.setChanged(false);

        // set it again and check
        company.setAddress(address);

        assertEquals("The address value should be set properly.", address,
            UnitTestHelper.getPrivateField(Company.class, company, "address"));
        assertEquals("The changed flag of the company should be false.", false, company.isChanged());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getContact()</code>, should return the default value.
     * </p>
     */
    public void testGetContact_Default_Accuracy() {
        assertEquals("The contact value should be got properly.", null, company.getContact());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getContact()</code>, should return the set value.
     * </p>
     */
    public void testGetContact_Accuracy() {
        Contact contact = new Contact();
        UnitTestHelper.setPrivateField(Company.class, company, "contact", contact);
        assertEquals("The contact value should be got properly.", contact, company.getContact());
    }

    /**
     * <p>
     * Tests the method <code>setContact(Contact contact)</code> when the given contact is null,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetContact_NullContact() {
        try {
            company.setContact(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setContact(Contact contact)</code>. The changed flag of the company should be
     * updated as the new value is different from the old one.
     * </p>
     */
    public void testSetContact_Changed_Accuracy() {
        Contact contact = new Contact();
        company.setContact(contact);
        assertEquals("The contact value should be set properly.", contact,
            UnitTestHelper.getPrivateField(Company.class, company, "contact"));
        assertEquals("The changed flag of the company should be true.", true, company.isChanged());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setContact(Contact contact)</code>. The changed flag of the company should
     * not be updated as the new value is the same as the old one.
     * </p>
     */
    public void testSetContact_NotChanged_Accuracy() {
        Contact contact = new Contact();
        company.setContact(contact);

        // reset the value of the change flag.
        company.setChanged(false);

        // set it again and check
        company.setContact(contact);

        assertEquals("The contact value should be set properly.", contact,
            UnitTestHelper.getPrivateField(Company.class, company, "contact"));
        assertEquals("The changed flag of the company should be false.", false, company.isChanged());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getCompanyName()</code>, should return the default value.
     * </p>
     */
    public void testGetCompanyName_Default_Accuracy() {
        assertEquals("The companyName value should be got properly.", null, company.getCompanyName());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getCompanyName()</code>, should return the set value.
     * </p>
     */
    public void testGetCompanyName_Accuracy() {
        String companyName = "companyName";
        UnitTestHelper.setPrivateField(Company.class, company, "companyName", companyName);
        assertEquals("The companyName value should be got properly.", companyName, company.getCompanyName());
    }

    /**
     * <p>
     * Tests the method <code>setCompanyName(String companyName)</code> when the given companyName is null,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetCompanyName_NullCompanyName() {
        try {
            company.setCompanyName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>setCompanyName(String companyName)</code> when the given companyName is empty string,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetCompanyName_EmptyCompanyName() {
        try {
            company.setCompanyName(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setCompanyName(String companyName)</code>. The changed flag of the company
     * should be updated as the new value is different from the old one.
     * </p>
     */
    public void testSetCompanyName_Changed_Accuracy() {
        String companyName = "companyName";
        company.setCompanyName(companyName);
        assertEquals("The companyName value should be set properly.", companyName,
            UnitTestHelper.getPrivateField(Company.class, company, "companyName"));
        assertEquals("The changed flag of the company should be true.", true, company.isChanged());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setCompanyName(String companyName)</code>. The changed flag of the company
     * should not be updated as the new value is the same as the old one.
     * </p>
     */
    public void testSetCompanyName_NotChanged_Accuracy() {
        String companyName = "companyName";
        company.setCompanyName(companyName);

        // reset the value of the change flag.
        company.setChanged(false);

        // set it again and check
        company.setCompanyName(companyName);

        assertEquals("The companyName value should be set properly.", companyName,
            UnitTestHelper.getPrivateField(Company.class, company, "companyName"));
        assertEquals("The changed flag of the company should be false.", false, company.isChanged());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getPassCode()</code>, should return the default value.
     * </p>
     */
    public void testGetPassCode_Default_Accuracy() {
        assertEquals("The passCode value should be got properly.", null, company.getPassCode());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getPassCode()</code>, should return the set value.
     * </p>
     */
    public void testGetPassCode_Accuracy() {
        String passCode = "passCode";
        UnitTestHelper.setPrivateField(Company.class, company, "passCode", passCode);
        assertEquals("The passCode value should be got properly.", passCode, company.getPassCode());
    }

    /**
     * <p>
     * Tests the method <code>setPassCode(String passCode)</code> when the given passCode is null,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetPassCode_NullPassCode() {
        try {
            company.setPassCode(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>setPassCode(String passCode)</code> when the given passCode is empty string,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetPassCode_EmptyPassCode() {
        try {
            company.setPassCode(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setPassCode(String passCode)</code>. The changed flag of the company should
     * be updated as the new value is different from the old one.
     * </p>
     */
    public void testSetPassCode_Changed_Accuracy() {
        String passCode = "passCode";
        company.setPassCode(passCode);
        assertEquals("The passCode value should be set properly.", passCode,
            UnitTestHelper.getPrivateField(Company.class, company, "passCode"));
        assertEquals("The changed flag of the company should be true.", true, company.isChanged());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setPassCode(String passCode)</code>. The changed flag of the company should
     * not be updated as the new value is the same as the old one.
     * </p>
     */
    public void testSetPassCode_NotChanged_Accuracy() {
        String passCode = "passCode";
        company.setPassCode(passCode);

        // reset the value of the change flag.
        company.setChanged(false);

        // set it again and check
        company.setPassCode(passCode);

        assertEquals("The passCode value should be set properly.", passCode,
            UnitTestHelper.getPrivateField(Company.class, company, "passCode"));
        assertEquals("The changed flag of the company should be false.", false, company.isChanged());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getAlgorithmName()</code>, should return the default value.
     * </p>
     */
    public void testGetAlgorithmName_Default_Accuracy() {
        assertEquals("The algorithmName value should be got properly.", null, company.getAlgorithmName());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getAlgorithmName()</code>, should return the set value.
     * </p>
     */
    public void testGetAlgorithmName_Accuracy() {
        String algorithmName = "algorithmName";
        UnitTestHelper.setPrivateField(Company.class, company, "algorithmName", algorithmName);
        assertEquals("The algorithmName value should be got properly.", algorithmName, company.getAlgorithmName());
    }

    /**
     * <p>
     * Tests the method <code>setAlgorithmName(String algorithmName)</code> when the given algorithmName is null,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetAlgorithmName_NullAlgorithmName() {
        try {
            company.setAlgorithmName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>setAlgorithmName(String algorithmName)</code> when the given algorithmName is empty
     * string, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetAlgorithmName_EmptyAlgorithmName() {
        try {
            company.setAlgorithmName(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setAlgorithmName(String algorithmName)</code>.
     * </p>
     */
    public void testSetAlgorithmName_Accuracy() {
        String algorithmName = "algorithmName";
        company.setAlgorithmName(algorithmName);
        assertEquals("The algorithmName value should be set properly.", algorithmName,
            UnitTestHelper.getPrivateField(Company.class, company, "algorithmName"));
    }
}
