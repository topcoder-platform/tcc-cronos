/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services;

import hr.leads.services.model.EmployeeDTO;
import hr.leads.services.model.HighLevelSummaryReportFilter;
import hr.leads.services.model.PipelineCycleStatus;
import hr.leads.services.model.Report;
import hr.leads.services.model.ReportType;
import hr.leads.services.model.jpa.Band;
import hr.leads.services.model.jpa.EmployeeProfile;
import hr.leads.services.model.jpa.EmployeeProfileStatus;
import hr.leads.services.model.jpa.SystemConfigurationProperty;

import java.util.List;

/**
 * <p>
 * Demo tests show the usage of this component.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DemoTest extends JPATestBase {

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        openSession();
        clearTables();
        closeSession();

        insertEmployees();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        openSession();
        clearTables();
        closeSession();
        super.tearDown();
    }

    /**
     * <p>
     * Shows the usage of ReportService.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testDemoReportService() throws Exception {

        // retrieve report service
        ReportService reportService = (ReportService) lookupForTest("reportService");

        String managerCNUM = "1";
        // retrieve direct nine box report
        List<EmployeeDTO> dtos = reportService.getDirectNineBoxReport(
                managerCNUM, null);
        // the direct nine box report for given manager should be returned
        assertEquals("Should contains 2 elements.", 2, dtos.size());

        String filterManagerCNUM = null;
        // retrieve organization nine box report
        dtos = reportService.getOrganizationNineBoxReport(managerCNUM, null,
                filterManagerCNUM);
        // the organization nine box report for given manager should be
        // returned, the return list should be filtered using filterManagerCNUM
        assertEquals("Should contains 4 elements.", 4, dtos.size());

        // retrieve direct summary report
        dtos = reportService.getSummaryReport(managerCNUM,
                ReportType.DIRECT_REPORT, new String[] {"cnum"});
        // the direct summary report for given manager should be returned and
        // sorted using EmployeeDTO#employee#cnum field in ascending order

        // retrieve organization summary report
        dtos = reportService.getSummaryReport(managerCNUM,
                ReportType.ORGANIZATION, new String[] {"cnum"});
        // the organization summary report for given manager should be returned
        // and sorted using EmployeeDTO#employee#cnum field in ascending order

        // retrieve direct rollup report
        Report report = reportService.getDirectRollupReport(managerCNUM,
                new String[] {"totalEmployees"});
        // the direct rollup report for given manager should be returned and
        // Report#records should be sorted using ReportRecord#totalEmployees
        assertEquals("should have 2 total employees.", 2, report.getTotalEmployees());

        // retrieve organization rollup report
        report = reportService.getOrganizationRollupReport(managerCNUM,
                filterManagerCNUM, new String[] {"totalEmployees"});
        // the organization rollup report for given manager should be returned,
        // the data for report should be filtered by filterManagerCNUM and
        // Report#records should be sorted using ReportRecord#totalEmployees
        assertEquals("should have 2 total employees.", 4, report.getTotalEmployees());

        HighLevelSummaryReportFilter filter = new HighLevelSummaryReportFilter();
        filter.setCountryId(new Long(1));

        // retrieve high level summary report
        report = reportService.getHighLevelSummaryReport(filter,
                new String[] {"totalEmployees"});
        // the high level summary report should be retrieved based on given high
        // level summary report filter, Report#records should be sorted using
        // ReportRecord#totalEmployees

    }

    /**
     * <p>
     * Shows the usage of SystemConfigurationPropertyService.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testDemoSystemConfigurationPropertyService() throws Exception {

        openSession();
        try {
            // retrieve system configuration property service
            SystemConfigurationPropertyService scpService =
                (SystemConfigurationPropertyService) lookupForTest("systemConfigurationPropertyService");

            // set system configuration property value
            beginTransaction();
            scpService.setSystemConfigurationPropertyValue("property1",
                    "value1");
            scpService.setSystemConfigurationPropertyValue("property2",
                    "value2");
            scpService.setSystemConfigurationPropertyValue(
                    "CycleStatusProperty", "Open");
            commitTransaction();

            // assume that these properties are not set before, 2 new
            // SystemConfigurationProperty entities should be created and saved
            // to database:
            // id name value
            // 1 property1 value1
            // 2 property2 value2
            // set system configuration property value
            String value = scpService.getSystemConfigurationPropertyValue("property1");
            // the retrieved value should be "value1"
            assertEquals("value should be equal.", "value1", value);

            // get all system configuration properties
            List<SystemConfigurationProperty> properties = scpService
                    .getAllSystemConfigurationPropertyValues();
            assertEquals(3, properties.size());

            // the returned list should contain all the properties set in
            // previous steps

            // update pipeline cycle status
            scpService.updatePipelineCycleStatus(PipelineCycleStatus.OPEN);
            // assume that this is the first call to set pipeline cycle status
            // and the
            // SystemConfigurationPropertyServiceBean#pipelineCycleStatusPropertyName
            // is set to "pipelineCycleStatus", a new
            // SystemConfigurationProperty should be added to database:
            // id name value
            // 1 property1 value1
            // 2 property2 value2
            // 3 pipelineCycleStatus OPEN
            // update pipeline cycle status again
            scpService.updatePipelineCycleStatus(PipelineCycleStatus.CLOSED);
            // the SystemConfigurationProperty entity created in previous step
            // should be updated to new status:
            // id name value
            // 1 property1 value1
            // 2 property2 value2
            // 3 pipelineCycleStatus CLOSED

            // get pipeline cycle status
            PipelineCycleStatus status = scpService.getPipelineCycleStatus();
            // the status should be PipelineCycleStatus.CLOSED
            assertEquals(PipelineCycleStatus.CLOSED, status);

        } finally {
            closeSession();
        }

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
        EmployeeProfile[] profiles = new EmployeeProfile[12];
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

}
