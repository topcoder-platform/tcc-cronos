/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.ejb;

import hr.leads.services.EmployeeProfileServiceMockBean;
import hr.leads.services.JPATestBase;
import hr.leads.services.LDAPUserServiceMockBean;
import hr.leads.services.LeadsServiceConfigurationException;
import hr.leads.services.LeadsServiceException;
import hr.leads.services.ReportService;
import hr.leads.services.model.EmployeeDTO;
import hr.leads.services.model.HighLevelSummaryReportFilter;
import hr.leads.services.model.Report;
import hr.leads.services.model.ReportType;
import hr.leads.services.model.jpa.Band;
import hr.leads.services.model.jpa.EmployeeProfile;
import hr.leads.services.model.jpa.EmployeeProfileStatus;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
/**
 * <p>
 * All tests for <code>ReportServiceBean</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReportServiceBeanTest extends JPATestBase {

    /**
     * <p>
     * Represents the ReportServiceBean for unit tests.
     * </p>
     */
    private ReportServiceBean reportService  = null;

    /**
     * <p>
     * Represents the profiles for unit tests.
     * </p>
     */
    private EmployeeProfile[] profiles = null;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        reportService = (ReportServiceBean) lookupForTest("reportService");

        insertEmployees();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * Insert employees. This is a helper method for tests.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    private void insertEmployees() throws Exception {
        EmployeeProfileServiceMockBean employeeProfileService =
            (EmployeeProfileServiceMockBean) lookupForTest("employeeProfileService");
        profiles  = new EmployeeProfile[12];
        for (int i = 0; i < profiles.length; i++) {
            EmployeeProfile employeeProfile = new EmployeeProfile();
            employeeProfile.setCnum(new Integer(i + 1).toString());
            employeeProfile.setMatrixManagerCNUM("11");
            employeeProfile.setId(i + 1);
            Band band = new Band();
            band.setId(1);
            employeeProfile.setBand(band);
            profiles[i] = employeeProfile;

        }

        // assign the relationships
        profiles[1].setBpManagerCNUM("1");
        profiles[2].setBpManagerCNUM("1");
        profiles[3].setBpManagerCNUM("3");
        profiles[4].setBpManagerCNUM("3");

        profiles[6].setBpManagerCNUM("6");
        profiles[7].setBpManagerCNUM("6");
        profiles[8].setBpManagerCNUM("7");
        profiles[9].setBpManagerCNUM("7");

        profiles[6].setMatrixManagerCNUM("12");
        profiles[7].setMatrixManagerCNUM("12");
        profiles[8].setMatrixManagerCNUM("12");
        profiles[9].setMatrixManagerCNUM("12");


        // set for columns sort
        profiles[1].setCountryId(3);
        profiles[2].setCountryId(7);
        profiles[3].setCountryId(2);
        profiles[4].setCountryId(4);

        EmployeeProfileStatus employeeProfileStatus = new EmployeeProfileStatus();
        employeeProfileStatus.setName("NotSubmitttedStatus");

        profiles[1].setEmployeeProfileStatus(employeeProfileStatus);
        profiles[2].setEmployeeProfileStatus(employeeProfileStatus);

        employeeProfileStatus = new EmployeeProfileStatus();
        employeeProfileStatus.setName("SubmittedStatus");
        profiles[3].setEmployeeProfileStatus(employeeProfileStatus);
        profiles[4].setEmployeeProfileStatus(employeeProfileStatus);
        profiles[4].setBtlResourceIdentifier(true);

        // add to the service
        for (EmployeeProfile profile : profiles) {
            employeeProfileService.addEmployee(profile);
        }

        EmployeeProfile employeeProfile = new EmployeeProfile();
        employeeProfile.setCnum("101");
        employeeProfile.setMatrixManagerCNUM("999"); // not exist
        employeeProfileService.addEmployee(employeeProfile);

        employeeProfile = new EmployeeProfile();
        employeeProfile.setCnum("102");
        employeeProfile.setBpManagerCNUM("101");
        employeeProfile.setMatrixManagerCNUM("999"); // not exist
        employeeProfileService.addEmployee(employeeProfile);

        employeeProfile = new EmployeeProfile();
        employeeProfile.setCnum("20");
        employeeProfileService.addEmployee(employeeProfile);

        employeeProfile = new EmployeeProfile();
        employeeProfile.setCnum("21");
        employeeProfile.setBpManagerCNUM("20");
        employeeProfile.setMatrixManagerCNUM("20");
        employeeProfileService.addEmployee(employeeProfile);

    }

    /**
     * <p>
     * Tests constructor: {@link ReportServiceBean#ReportServiceBean()}.
     * </p>
     *
     * <p>
     * Tests if the instance can be successfully created and it has correct hierarchy.
     * </p>
     */
    public void testReportServiceBean() {
        assertNotNull("the instance of ReportServiceBean should not be null.", reportService);
        assertTrue("Incorrect hierarchy.", reportService instanceof ReportServiceBean);
        assertTrue("Incorrect hierarchy.", reportService instanceof BaseReportConfigurationServiceBean);
        assertTrue("Incorrect hierarchy.", reportService instanceof ReportServiceLocal);
        assertTrue("Incorrect hierarchy.", reportService instanceof ReportService);
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getDirectNineBoxReport(String, Long)}.
     * </p>
     *
     * <p>
     * If ManagerCNUM is null or empty, IllegalArgumentException should be thrown.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testGetDirectNineBoxReportManagerCNUMNullOrEmpty() throws Exception {
        try {
            reportService.getDirectNineBoxReport(null, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }

        try {
            reportService.getDirectNineBoxReport("  ", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getDirectNineBoxReport(String, Long)}.
     * </p>
     *
     * <p>
     * If BandId is null, IllegalArgumentException should NOT be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetDirectNineBoxReportBandIdNull() throws Exception {
        try {
            reportService.getDirectNineBoxReport("4", null);
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should not be thrown.");
        }
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getDirectNineBoxReport(String, Long)}.
     * </p>
     *
     * <p>
     * Accuracy test for the empty result.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetDirectNineBoxReportAccuracyEmpty() throws Exception {
        List<EmployeeDTO> result = reportService.getDirectNineBoxReport("4", null);
        assertNotNull("the result cannot be null.", result);
        assertEquals("the result should be empty", 0, result.size());
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getDirectNineBoxReport(String, Long)}.
     * </p>
     *
     * <p>
     * Accuracy test for non-empty result, checks if the returned value is correct.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetDirectNineBoxReportAccuracyNonEmpty() throws Exception {
        List<EmployeeDTO> result = reportService.getDirectNineBoxReport("1", null);
        assertNotNull("the result cannot be null.", result);
        assertEquals("the result should be empty", 2, result.size());
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getOrganizationNineBoxReport(String, Long, String)}.
     * </p>
     *
     * <p>
     * If the ManagerCNUM is null or empty, IllegalArgumentException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetOrganizationNineBoxReportManagerCNUMNullOrEmpty() throws Exception {
        try {
            reportService.getOrganizationNineBoxReport(null, null, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }

        try {
            reportService.getOrganizationNineBoxReport("   ", null, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getOrganizationNineBoxReport(String, Long, String)}.
     * </p>
     *
     * <p>
     * If the filterManagerCNUM is  empty, IllegalArgumentException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetOrganizationNineBoxReportFilterManagerCNUMEmpty() throws Exception {
        try {
            reportService.getOrganizationNineBoxReport("1", null, "  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getOrganizationNineBoxReport(String, Long, String)}.
     * </p>
     *
     * <p>
     * Accuracy test if the BandId is null, check if the result correctly returned.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetOrganizationNineBoxReportBandIdNull() throws Exception {
        List<EmployeeDTO> result = reportService.getOrganizationNineBoxReport("1", null, null);
        assertEquals("it should contains 4 result.", 4, result.size());
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getOrganizationNineBoxReport(String, Long, String)}.
     * </p>
     *
     * <p>
     * Accuracy test if the BandId is not null, and some employees are not equal to the band id.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetOrganizationNineBoxReportBandIdNotEqual() throws Exception {
        List<EmployeeDTO> result = reportService.getOrganizationNineBoxReport("1", new Long(2), null);
        assertEquals("it should contains 0 result.", 0, result.size());
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getOrganizationNineBoxReport(String, Long, String)}.
     * </p>
     *
     * <p>
     * Accuracy test if the BandId is not null, and some employees are equal to the band id.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetOrganizationNineBoxReportBandIdEqual() throws Exception {
        List<EmployeeDTO> result = reportService.getOrganizationNineBoxReport("1", new Long(1), null);
        assertEquals("it should contains 4 result.", 4, result.size());
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getOrganizationNineBoxReport(String, Long, String)}.
     * </p>
     *
     * <p>
     * If LdapMatrixManager is not found, LeadsServiceException not is thrown, and the process continues.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetOrganizationNineBoxReportLdapMatrixManagerNotFound() throws Exception {
        try {
            List<EmployeeDTO> result = reportService.getOrganizationNineBoxReport("101", null, null);
            assertEquals("should be 0", 0, result.size());
        } catch (LeadsServiceException e) {
            fail("LeadsServiceException should be not thrown.");
        }
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getOrganizationNineBoxReport(String, Long, String)}.
     * </p>
     *
     * <p>
     * Accuracy test if the organizations in LdapMatrixManager and ldapManager are matched.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetOrganizationNineBoxReportOrganizationEqual() throws Exception {
        List<EmployeeDTO> result = reportService.getOrganizationNineBoxReport("1", null, null);
        assertEquals("it should contains 4 result.", 4, result.size());
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getOrganizationNineBoxReport(String, Long, String)}.
     * </p>
     *
     * <p>
     * Accuracy test if the organizations in LdapMatrixManager and ldapManager are not matched.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetOrganizationNineBoxReportOrganizationNotEqual() throws Exception {
        List<EmployeeDTO> result = reportService.getOrganizationNineBoxReport("6", null, null);
        assertEquals("it should contains 0 result.", 0, result.size());
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getOrganizationNineBoxReport(String, Long, String)}.
     * </p>
     *
     * <p>
     * Accuracy test if filterManagerCNUM equals to MatrixManagerCNUM and BpManagerCNUM.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetOrganizationNineBoxReportFilterManagerEqualsEquals() throws Exception {
        List<EmployeeDTO> result = reportService.getOrganizationNineBoxReport("20", null, "20");
        assertEquals("it should contains 1 result.", 1, result.size());
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getOrganizationNineBoxReport(String, Long, String)}.
     * </p>
     *
     * <p>
     * Accuracy test if filterManagerCNUM equals to MatrixManagerCNUM but not equals to BpManagerCNUM.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetOrganizationNineBoxReportFilterManagerEqualsNotEquals() throws Exception  {
        List<EmployeeDTO> result = reportService.getOrganizationNineBoxReport("3", null, "11");
        assertEquals("it should contains 2 result.", 2, result.size());
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getOrganizationNineBoxReport(String, Long, String)}.
     * </p>
     *
     * <p>
     * Accuracy test if filterManagerCNUM does not equal to MatrixManagerCNUM but equals to BpManagerCNUM.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetOrganizationNineBoxReportFilterManagerNotEqualsEquals() throws Exception {
        List<EmployeeDTO> result = reportService.getOrganizationNineBoxReport("3", null, "3");
        assertEquals("it should contains 1 result.", 2, result.size());
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getOrganizationNineBoxReport(String, Long, String)}.
     * </p>
     *
     * <p>
     * Accuracy test if filterManagerCNUM not equals to both of  MatrixManagerCNUM and BpManagerCNUM.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetOrganizationNineBoxReportFilterManagerNotEqualsNotEquals() throws Exception {
        List<EmployeeDTO> result = reportService.getOrganizationNineBoxReport("3", null, "1");
        assertEquals("it should contains 0 result.", 0, result.size());
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getSummaryReport(String, ReportType, String[])}.
     * </p>
     *
     * <p>
     * If ManagerCNUM is null or empty, IllegalArgumentException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetSummaryReportManagerCNUMNullOrEmpty() throws Exception {
        try {
            reportService.getSummaryReport(null, ReportType.DIRECT_REPORT, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }

        try {
            reportService.getSummaryReport("   ", ReportType.DIRECT_REPORT, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getSummaryReport(String, ReportType, String[])}.
     * </p>
     *
     * <p>
     * If ReportType is null, IllegalArgumentException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetSummaryReportReportTypeNull() throws Exception {
        try {
            reportService.getSummaryReport("1", null, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getSummaryReport(String, ReportType, String[])}.
     * </p>
     *
     * <p>
     * If SortColumns contains null or empty string, IllegalArgumentException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetSummaryReportSortColumnsContainsNullOrEmpty() throws Exception {
        try {
            reportService.getSummaryReport("1", null, new String[] {"1", null});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }

        try {
            reportService.getSummaryReport("1", null, new String[] {"1", "   "});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getSummaryReport(String, ReportType, String[])}.
     * </p>
     *
     * <p>
     * If SortColumns is null or empty, IllegalArgumentException should not be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetSummaryReportSortIsNullOrEmpty() throws Exception {
        try {
            reportService.getSummaryReport("1", ReportType.DIRECT_REPORT, new String[] {});

        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should be thrown.");
        }

        try {
            reportService.getSummaryReport("1", ReportType.DIRECT_REPORT, null);

        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should be thrown.");
        }

    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getSummaryReport(String, ReportType, String[])}.
     * </p>
     *
     * <p>
     * Accuracy test if the report type is DIRECT.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetSummaryReportDirectReport() throws Exception {
        List<EmployeeDTO> result = reportService.getSummaryReport("1", ReportType.DIRECT_REPORT, null);
        assertEquals("Should contains 2 elements.", 2, result.size());
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getSummaryReport(String, ReportType, String[])}.
     * </p>
     *
     * <p>
     * Accuracy test if the report type is not DIRECT.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetSummaryReportNotDirectReportOrganizationEquals() throws Exception {
        List<EmployeeDTO> result = reportService.getSummaryReport("1", ReportType.ORGANIZATION, null);
        assertEquals("Should contains 4 elements.", 4, result.size());
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getSummaryReport(String, ReportType, String[])}.
     * </p>
     *
     * <p>
     * Accuracy test if the SortColumns is valid, order of the result should be sorted.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetSummaryReportSortColumnsValid() throws Exception {
        List<EmployeeDTO> result = reportService.getSummaryReport(
                "1", ReportType.ORGANIZATION, new String[] {"countryId"});

        assertEquals("Should contains 4 elements.", 4, result.size());

        // check the order
        for (int i = 1; i < result.size(); i++) {
            long prevCountryId = result.get(i - 1).getEmployee().getCountryId();
            long currentCountryId = result.get(i).getEmployee().getCountryId();
            // System.out.println("currentCountryId: " + currentCountryId);
            assertTrue("countryId should be sorted", currentCountryId > prevCountryId);
        }
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getDirectRollupReport(String, String[])}.
     * </p>
     *
     * <p>
     * If ManagerCNUM is null or empty, IllegalArgumentException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetDirectRollupReportManagerCNUMNullOrEmpty() throws Exception {
        try {
            reportService.getDirectRollupReport(null, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }

        try {
            reportService.getDirectRollupReport("   ", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getDirectRollupReport(String, String[])}.
     * </p>
     *
     * <p>
     * If SortColumns contains null or empty, IllegalArgumentException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetDirectRollupReportManagerSortColumnsContainsNullOrEmpty() throws Exception {
        try {
            reportService.getDirectRollupReport("1", new String[] {null});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getDirectRollupReport(String, String[])}.
     * </p>
     *
     * <p>
     * If SortColumns is null or empty, no IllegalArgumentException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetDirectRollupReportManagerSortColumnsIsNullOrEmpty() throws Exception {
        try {
            reportService.getDirectRollupReport("1", null);
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should be thrown.");
        }

        try {
            reportService.getDirectRollupReport("1", new String[]{});

        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should be thrown.");
        }
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getDirectRollupReport(String, String[])}.
     * </p>
     *
     * <p>
     * Test the case when has btlr.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetDirectRollupReportHasBtlr() throws Exception {
        Report report = reportService.getDirectRollupReport("3", null);
        assertEquals(1, report.getRecords().get(0).getBtlrEmployees());
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getDirectRollupReport(String, String[])}.
     * </p>
     *
     * <p>
     * Test the case when the status is not submmited or nonsubmmitd.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetDirectRollupBadStatus() throws Exception {
        Report report = reportService.getDirectRollupReport("1", null);
        assertEquals(2, report.getRecords().get(0).getNotSubmittedEmployees());
        List<EmployeeDTO> result = reportService.getDirectNineBoxReport("1", null);
        EmployeeProfileStatus status = new EmployeeProfileStatus();
        status.setName("unknown.");
        result.get(0).getEmployee().setEmployeeProfileStatus(status);
        report = reportService.getDirectRollupReport("1", null);
        assertEquals(1, report.getRecords().get(0).getNotSubmittedEmployees());
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getOrganizationNineBoxReport(String, Long, String).}.
     * </p>
     *
     * <p>
     * Test the case when has cycle relationship.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetOrganizationNineBoxReportHasCycleRelationship() throws Exception {
        List<EmployeeDTO> report = reportService.getOrganizationNineBoxReport("1", null, null);
        assertEquals(4, report.size());

        // add a cycle relationship

        // check again
        profiles[0].setMatrixManagerCNUM("5");
        report = reportService.getOrganizationNineBoxReport("1", null, null);
        assertEquals(5, report.size());
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getOrganizationNineBoxReport(String, Long, String).}.
     * </p>
     *
     * <p>
     * Test the case when has cycle relationship.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetOrganizationNineBoxReportHasCycleRelationship2() throws Exception {
        profiles[5].setBpManagerCNUM("5");
        List<EmployeeDTO> report = reportService.getOrganizationNineBoxReport("5", null, null);
        assertEquals(1, report.size());

        // add a cycle relationship

        // check again
        profiles[5].setMatrixManagerCNUM("10");
        report = reportService.getOrganizationNineBoxReport("5", null, null);
        assertEquals(1, report.size());
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getDirectRollupReport(String, String[])}.
     * </p>
     *
     * <p>
     * Accuracy test to check if the returned value is correct.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetDirectRollupReportManagerAccuracy() throws Exception {
        Report result = reportService.getDirectRollupReport("3", null);
        assertEquals("should be 1.", 0, Float.compare(result.getCompletePercentage(), 1));
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getDirectRollupReport(String, String[])}.
     * </p>
     *
     * <p>
     * Accuracy test to check if the returned value is correct.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetDirectRollupReportManagerAccuracy2() throws Exception {
        Report result = reportService.getDirectRollupReport("1", null);
        assertEquals("should be 0.", 0, Float.compare(result.getCompletePercentage(), 0));
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getOrganizationRollupReport(String, String, String[])}.
     * </p>
     *
     * <p>
     * If ManagerCNUM is null or empty, IllegalArgumentException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetOrganizationRollupReportManagerCNUMNullOrEmpty() throws Exception {
        try {
            reportService.getOrganizationRollupReport(null, null, null);
            fail("IllegalArgumentException should thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }

        try {
            reportService.getOrganizationRollupReport("   ", null, null);
            fail("IllegalArgumentException should thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getOrganizationRollupReport(String, String, String[])}.
     * </p>
     *
     * <p>
     * If FilterManagerCNUM is empty, IllegalArgumentException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetOrganizationRollupFilterManagerCNUMEmpty() throws Exception {
        try {
            reportService.getOrganizationRollupReport("1", "  ", null);
            fail("IllegalArgumentException should thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getOrganizationRollupReport(String, String, String[])}.
     * </p>
     *
     * <p>
     * If SortColumns contains null or empty, IllegalArgumentException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetOrganizationRollupSortColumnsContainsNullOrEmpty() throws Exception {
        try {
            reportService.getOrganizationRollupReport("1", null, new String[] {null});
            fail("IllegalArgumentException should thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }

        try {
            reportService.getOrganizationRollupReport("1", null, new String[] {"  "});
            fail("IllegalArgumentException should thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getOrganizationRollupReport(String, String, String[])}.
     * </p>
     *
     * <p>
     * If LdapManager does not exist, LeadsServiceException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetOrganizationRollupLdapManagerDoesNotExist() throws Exception {
        try {
            reportService.getOrganizationRollupReport("999", null, null);
            fail("LeadsServiceException should thrown.");
        } catch (LeadsServiceException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getOrganizationRollupReport(String, String, String[])}.
     * </p>
     *
     * <p>
     * If LdapMatrixManager does not exist, LeadsServiceException should not be thrown,
     * and the process should continue.
     * </p>
     */
    public void testGetOrganizationRollupLdapMatrixManagerDoesNotExist() {
        try {
            Report result = reportService.getOrganizationRollupReport("101", null, null);
            assertEquals("should be 0", 0, result.getTotalEmployees());
        } catch (LeadsServiceException e) {
            fail("LeadsServiceException should not be thrown.");
        }
    }


    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getOrganizationRollupReport(String, String, String[])}.
     * </p>
     *
     * <p>
     * Accuracy test for FilterManagerCNUM is Null.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetOrganizationRollupFilterManagerCNUMNull() throws Exception {
        Report result = reportService.getOrganizationRollupReport("1", null, null);
        assertEquals("should be 0.5", 0, Float.compare(result.getCompletePercentage(), 0.5f));
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getHighLevelSummaryReport(HighLevelSummaryReportFilter, String[])}.
     * </p>
     *
     * <p>
     * If filter is null, IllegalArgumentException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetHighLevelSummaryReportFilterNull() throws Exception {
        try {
            reportService.getHighLevelSummaryReport(null, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getHighLevelSummaryReport(HighLevelSummaryReportFilter, String[])}.
     * </p>
     *
     * <p>
     * If SortColumns contains null or empty, IllegalArgumentException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetHighLevelSummaryReportSortColumnsContainsNullOrEmpty() throws Exception {
        try {
            reportService.getHighLevelSummaryReport(new HighLevelSummaryReportFilter(), new String[] {null});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#getHighLevelSummaryReport(HighLevelSummaryReportFilter, String[])}.
     * </p>
     *
     * <p>
     * Accuracy test for this method, checks if the result correctly returned.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetHighLevelSummaryReportAccuracy() throws Exception {
        HighLevelSummaryReportFilter filter = new HighLevelSummaryReportFilter();
        filter.setCountryId(new Long(2));
        Report result = reportService.getHighLevelSummaryReport(filter, null);
        assertEquals("should be 1", 1, result.getTotalEmployees());
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#afterBeanInitialized()}.
     * </p>
     *
     * <p>
     * If EmployeeProfileService is null, LeadsServiceConfigurationException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAfterBeanInitializedEmployeeProfileServiceNull() throws Exception {
        reportService = new ReportServiceBean();

        Field field = reportService.getClass().getDeclaredField("employeeProfileService");
        field.setAccessible(true);
        //field.set(reportService, new EmployeeProfileServiceMockBean());

        field = reportService.getClass().getDeclaredField("ldapUserService");
        field.setAccessible(true);
        field.set(reportService, new LDAPUserServiceMockBean());

        field = reportService.getClass().getDeclaredField("submittedStatusName");
        field.setAccessible(true);
        field.set(reportService, new String("abc"));

        field = reportService.getClass().getDeclaredField("notSubmittedStatusName");
        field.setAccessible(true);
        field.set(reportService, new String("abc"));

        try {
            reportService.afterBeanInitialized();
            fail("LeadsServiceConfigurationException should thrown.");
        } catch (LeadsServiceConfigurationException e) {
            // success
        }

    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#afterBeanInitialized()}.
     * </p>
     *
     * <p>
     * If LdapUserService is null, LeadsServiceConfigurationException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAfterBeanInitializedLdapUserServiceNull() throws Exception {
        reportService = new ReportServiceBean();

        Field field = reportService.getClass().getDeclaredField("employeeProfileService");
        field.setAccessible(true);
        field.set(reportService, new EmployeeProfileServiceMockBean());

        field = reportService.getClass().getDeclaredField("ldapUserService");
        field.setAccessible(true);
        // field.set(reportService, new LDAPUserServiceMockBean());

        field = reportService.getClass().getDeclaredField("submittedStatusName");
        field.setAccessible(true);
        field.set(reportService, new String("abc"));

        field = reportService.getClass().getDeclaredField("notSubmittedStatusName");
        field.setAccessible(true);
        field.set(reportService, new String("abc"));

        try {
            reportService.afterBeanInitialized();
            fail("LeadsServiceConfigurationException should thrown.");
        } catch (LeadsServiceConfigurationException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#afterBeanInitialized()}.
     * </p>
     *
     * <p>
     * If SubmittedStatusName is null or empty, LeadsServiceConfigurationException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAfterBeanInitializedSubmittedStatusNameNullOrEmpty() throws Exception {
        reportService = new ReportServiceBean();

        Field field = reportService.getClass().getDeclaredField("employeeProfileService");
        field.setAccessible(true);
        field.set(reportService, new EmployeeProfileServiceMockBean());

        field = reportService.getClass().getDeclaredField("ldapUserService");
        field.setAccessible(true);
        field.set(reportService, new LDAPUserServiceMockBean());

        field = reportService.getClass().getDeclaredField("submittedStatusName");
        field.setAccessible(true);
        field.set(reportService, new String("   "));

        field = reportService.getClass().getDeclaredField("notSubmittedStatusName");
        field.setAccessible(true);
        field.set(reportService, new String("abc"));

        try {
            reportService.afterBeanInitialized();
            fail("LeadsServiceConfigurationException should thrown.");
        } catch (LeadsServiceConfigurationException e) {
            // success
        }

        field = reportService.getClass().getDeclaredField("submittedStatusName");
        field.setAccessible(true);
        String name = null;
        field.set(reportService, name);

        try {
            reportService.afterBeanInitialized();
            fail("LeadsServiceConfigurationException should thrown.");
        } catch (LeadsServiceConfigurationException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#afterBeanInitialized()}.
     * </p>
     *
     * <p>
     * If NotSubmittedStatusName is null or empty, LeadsServiceConfigurationException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAfterBeanInitializedNotSubmittedStatusNameNullOrEmpty() throws Exception {
        reportService = new ReportServiceBean();

        Field field = reportService.getClass().getDeclaredField("employeeProfileService");
        field.setAccessible(true);
        field.set(reportService, new EmployeeProfileServiceMockBean());

        field = reportService.getClass().getDeclaredField("ldapUserService");
        field.setAccessible(true);
        field.set(reportService, new LDAPUserServiceMockBean());

        field = reportService.getClass().getDeclaredField("submittedStatusName");
        field.setAccessible(true);
        field.set(reportService, new String("abc"));

        field = reportService.getClass().getDeclaredField("notSubmittedStatusName");
        field.setAccessible(true);
        field.set(reportService, new String("   "));

        try {
            reportService.afterBeanInitialized();
            fail("LeadsServiceConfigurationException should thrown.");
        } catch (LeadsServiceConfigurationException e) {
            // success
        }

        field = reportService.getClass().getDeclaredField("notSubmittedStatusName");
        field.setAccessible(true);
        field.set(reportService, null);

        try {
            reportService.afterBeanInitialized();
            fail("LeadsServiceConfigurationException should thrown.");
        } catch (LeadsServiceConfigurationException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#afterBeanInitialized()}.
     * </p>
     *
     * <p>
     * No exception should be thrown if the instance is properly injected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAfterBeanInitializedAccuracy() throws Exception {
        reportService = new ReportServiceBean();

        Field field = reportService.getClass().getDeclaredField("employeeProfileService");
        field.setAccessible(true);
        field.set(reportService, new EmployeeProfileServiceMockBean());

        field = reportService.getClass().getDeclaredField("ldapUserService");
        field.setAccessible(true);
        field.set(reportService, new LDAPUserServiceMockBean());

        field = reportService.getClass().getDeclaredField("submittedStatusName");
        field.setAccessible(true);
        field.set(reportService, new String("abc"));

        field = reportService.getClass().getDeclaredField("notSubmittedStatusName");
        field.setAccessible(true);
        field.set(reportService, new String("abc"));

        try {
            reportService.afterBeanInitialized();
             // success
        } catch (LeadsServiceConfigurationException e) {

            fail("no LeadsServiceConfigurationException should thrown.");
        }
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#createReport(List<EmployeeProfile>, String[])}.
     * </p>
     *
     * <p>
     * If the employees is null, IllegalArgumentException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateReportEmployeesNull() throws Exception {
        Method method = ReportServiceBean.class.getDeclaredMethod("createReport", List.class, String[].class);
        method.setAccessible(true);
        try {
            List<EmployeeProfile> employeeProfiles = null;
            String[] sortColumns = null;
            method.invoke(reportService, employeeProfiles, sortColumns);
            fail("Exception should be thrown.");
        } catch (Exception e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#createReport(List<EmployeeProfile>, String[])}.
     * </p>
     *
     * <p>
     * If the employees contains null, IllegalArgumentException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateReportEmployeesContainsNull() throws Exception {
        Method method = ReportServiceBean.class.getDeclaredMethod("createReport", List.class, String[].class);
        method.setAccessible(true);
        try {
            List<EmployeeProfile> employeeProfiles = new ArrayList<EmployeeProfile>();
            employeeProfiles.add(null);
            String[] sortColumns = null;
            method.invoke(reportService, employeeProfiles, sortColumns);
            fail("Exception should be thrown.");
        } catch (Exception e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#createReport(List<EmployeeProfile>, String[])}.
     * </p>
     *
     * <p>
     * If the SortColumns contains null or empty, IllegalArgumentException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateReportSortColumnsContainsNullOrEmpty() throws Exception {
        Method method = ReportServiceBean.class.getDeclaredMethod("createReport", List.class, String[].class);
        method.setAccessible(true);
        try {
            List<EmployeeProfile> employeeProfiles = new ArrayList<EmployeeProfile>();
            employeeProfiles.add(new EmployeeProfile());
            String[] sortColumns = new String[]{"  "};
            method.invoke(reportService, employeeProfiles, sortColumns);
            fail("Exception should be thrown.");
        } catch (Exception e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#createReport(List<EmployeeProfile>, String[])}.
     * </p>
     *
     * <p>
     * Accuracy test for zero submitted employees.
     * </p>
     *
     * @throws Exceptio to JUnit.
     */
    public void testCreateReportZeroSubmittedEmployees() throws Exception {
        Method method = ReportServiceBean.class.getDeclaredMethod("createReport", List.class, String[].class);
        method.setAccessible(true);

        List<EmployeeProfile> employeeProfiles = new ArrayList<EmployeeProfile>();

        Report report = (Report) method.invoke(reportService, employeeProfiles, null);

        assertEquals(0, report.getSubmissions());

    }

    /**
     * <p>
     * Tests method: {@link ReportServiceBean#createReport(List<EmployeeProfile>, String[])}.
     * </p>
     *
     * <p>
     * Accuracy test for zero total employees.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateReportZeroTotalEmployees() throws Exception {
        Method method = ReportServiceBean.class.getDeclaredMethod("createReport", List.class, String[].class);
        method.setAccessible(true);

        List<EmployeeProfile> employeeProfiles = new ArrayList<EmployeeProfile>();

        Report report = (Report) method.invoke(reportService, employeeProfiles, null);

        assertEquals(0, report.getTotalEmployees());
    }


    /**
     * <p>
     * Tests method: {@link ReportServiceBean#searchEmployees(String)}.
     * </p>
     *
     * <p>
     * Accuracy test, if the search employee perform properly.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    public void testSearchEmployeesAccuracy() throws Exception {
        Method method = reportService.getClass().getDeclaredMethod("searchEmployees", String.class);
        method.setAccessible(true);
        List<EmployeeDTO> employees = (List<EmployeeDTO>) method.invoke(reportService, new Object[] {"1"});
        assertEquals("There should be 4 elements.", 4, employees.size());

    }
}
