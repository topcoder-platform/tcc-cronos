/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import junit.framework.AssertionFailedError;

import java.util.ArrayList;
import java.util.List;


/**
 * This class contains the unit tests common to all {@link AbstractReport}s. Subclasses will extend this class and
 * provide the {@link Report} implementation to be tested.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public abstract class AbstractTestcaseForReports extends BaseTimeTrackerReportTest {
    /**
     * This field contains the {@link Report} implementation instance on which the tests are performed. It will be
     * initialized during {@link #setUp()} by calling {@link #getImplementation()}.
     */
    private AbstractReport report;
    /**
     * This is a {@link java.util.List} that is already containing {@link #clientFilter}, as a convenience to test
     * methods. It is instantiated in {@link #setUp()}.
     */
    private List listContainingClientFilter;
    /**
     * This is an empty {@link ArrayList} to be used as filter list in the test cases. It is instantiated in {@link
     * #setUp()}.
     */
    private ArrayList filterList;
    /**
     * This an {@link EqualityFilter} instance for {@link Column#EMPLOYEE} that is used in several test cases. It is
     * instantiated in {@link #setUp()}.
     */
    private EqualityFilter employeeFilter;
    /**
     * This an {@link EqualityFilter} instance for {@link Column#CLIENT} that is used in several test cases. It is
     * instantiated in {@link #setUp()}.
     */
    private EqualityFilter clientFilter;
    /**
     * This an {@link EqualityFilter} instance for {@link Column#BILLABLE} that is used in several test cases. It is
     * instantiated in {@link #setUp()}.
     */
    private EqualityFilter billableFilter;
    /**
     * This an {@link EqualityFilter} instance for {@link Column#PROJECT} that is used in several test cases. It is
     * instantiated in {@link #setUp()}.
     */
    private EqualityFilter projectFilter;
    /**
     * This an {@link RangeFilter} instance for {@link Column#DATE} that is used in several test cases. It is
     * instantiated in {@link #setUp()}.
     */
    private RangeFilter dateFilter;

    /**
     * This method tests {@link Report} subclass constructor for correctness.
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtor() throws Exception {
        callConstructor();
    }

    /**
     * This method tests {@link Report} subclass constructor for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: {@link ConfigManager} is completely empty
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailEmptyCM() throws Exception {
        try {
            clearConfig();
            callConstructor();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link Report} subclass constructor for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: mandatory filter {@link ConfigManager} property is no valid {@link FilterCategory} name
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailMandatoryFilterInvalid() throws Exception {
        try {
            loadFiltersConfiguration("Filters_mandatoryFilterInvalid.xml");
            callConstructor();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link Report} subclass constructor for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: mandatory filter {@link ConfigManager} property is empty String
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailMandatoryFilterEmpty() throws Exception {
        try {
            loadFiltersConfiguration("Filters_mandatoryFilterEmpty.xml");
            callConstructor();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link Report} subclass constructor for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: mandatory filter {@link ConfigManager} property is <tt>null</tt>
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailMandatoryFilterNull() throws Exception {
        try {
            loadFiltersConfiguration("Filters_mandatoryFilterNull.xml");
            callConstructor();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link Report} subclass constructor for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: mandatory filter {@link ConfigManager} property is missing
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailMandatoryFilterMissing() throws Exception {
        try {
            loadFiltersConfiguration("Filters_mandatoryFilterMissing.xml");
            callConstructor();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link Report} subclass constructor for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: optional filter {@link ConfigManager} property contains a filter name that is no valid
     * {@link FilterCategory} name
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailOptionalFilterInvalid() throws Exception {
        try {
            loadFiltersConfiguration("Filters_optionalFilterInvalid.xml");
            callConstructor();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link Report} subclass constructor for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: optional filter {@link ConfigManager} property contains a filter name that is empty
     * String
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailOptionalFilterEmpty() throws Exception {
        try {
            loadFiltersConfiguration("Filters_optionalFilterEmpty.xml");
            callConstructor();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link Report} subclass constructor for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: optional filter {@link ConfigManager} property is <tt>null</tt>
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailOptionalFilterNull() throws Exception {
        try {
            loadFiltersConfiguration("Filters_optionalFilterNull.xml");
            callConstructor();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link Report} subclass constructor for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: optional filter {@link ConfigManager} property is missing
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailOptionalFilterMissing() throws Exception {
        try {
            loadFiltersConfiguration("Filters_optionalFilterMissing.xml");
            callConstructor();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link Report} subclass constructor for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: default columns {@link ConfigManager} property contains a column name that is no valid
     * {@link Column} name
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailDefaultColumnInvalid() throws Exception {
        try {
            loadColumnsConfiguration("Columns_defaultColumnInvalid.xml");
            callConstructor();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link Report} subclass constructor for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: default column {@link ConfigManager} property contains a column name that is empty String
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailDefaultColumnEmpty() throws Exception {
        try {
            loadColumnsConfiguration("Columns_defaultColumnEmpty.xml");
            callConstructor();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link Report} subclass constructor for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: default column {@link ConfigManager} property is <tt>null</tt>
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailDefaultColumnNull() throws Exception {
        try {
            loadColumnsConfiguration("Columns_defaultColumnNull.xml");
            callConstructor();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link Report} subclass constructor for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: default column {@link ConfigManager} property is missing
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailDefaultColumnMissing() throws Exception {
        try {
            loadColumnsConfiguration("Columns_defaultColumnMissing.xml");
            callConstructor();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link Report#execute(String, ReportType, java.util.List)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: namespace is <tt>null</tt>
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testExecuteFailNamespaceNull() throws Exception {
        try {
            report.execute(null, ReportType.CLIENT, listContainingClientFilter, null);
            fail("should throw");
        } catch (NullPointerException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link Report#execute(String, ReportType, java.util.List)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: namespace is empty String
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testExecuteFailNamespaceEmpty() throws Exception {
        try {
            report.execute("  ", ReportType.CLIENT, listContainingClientFilter, null);
            fail("should throw");
        } catch (IllegalArgumentException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link Report#execute(String, ReportType, java.util.List)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: type is <tt>null</tt>
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testExecuteFailTypeNull() throws Exception {
        try {
            report.execute(DEFAULT_CONFIGURATION_NAMESPACE, null, listContainingClientFilter, null);
            fail("should throw");
        } catch (NullPointerException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link Report#execute(String, ReportType, java.util.List)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: filters is <tt>null</tt>
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testExecuteFailFiltersNull() throws Exception {
        try {
            report.execute(DEFAULT_CONFIGURATION_NAMESPACE, ReportType.CLIENT, null, null);
            fail("should throw");
        } catch (NullPointerException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link Report#execute(String, ReportType, java.util.List)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: filters does not contain the mandatory filter
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testExecuteFailMissingMandatoryFilters() throws Exception {
        try {
            report.execute(DEFAULT_CONFIGURATION_NAMESPACE, ReportType.CLIENT, filterList, null);
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link Report#execute(String, ReportType, java.util.List)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: the given namespace is unknown
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testExecuteFailUnknownNamespace() throws Exception {
        try {
            report.execute("blah", ReportType.CLIENT, listContainingClientFilter, null);
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link Report#execute(String, ReportType, java.util.List)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: the {@link ConfigManager} is empty
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testExecuteFailCMEmpty() throws Exception {
        try {
            clearConfig();
            report.execute(DEFAULT_CONFIGURATION_NAMESPACE, ReportType.CLIENT, listContainingClientFilter, null);
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link Report#execute(String, ReportType, java.util.List)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: custom columns {@link ConfigManager} property contains a column name that is no valid
     * {@link Column} name
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testExecuteFailCustomColumnInvalid() throws Exception {
        try {
            loadCustomConfiguration("Custom_customColumnInvalid.xml");
            report.execute(CUSTOM_CONFIGURATION_NAMESPACE, ReportType.CLIENT, listContainingClientFilter, null);
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link Report#execute(String, ReportType, java.util.List)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: custom column {@link ConfigManager} property contains a column name that is empty String
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testExecuteFailCustomColumnEmpty() throws Exception {
        try {
            loadCustomConfiguration("Custom_customColumnEmpty.xml");
            report.execute(CUSTOM_CONFIGURATION_NAMESPACE, ReportType.CLIENT, listContainingClientFilter, null);
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link Report#execute(String, ReportType, java.util.List)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: custom column {@link ConfigManager} property contains a column name that refers to a
     * {@link Column} which is not allowed in this report category and type
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testExecuteFailCustomColumnForbidden() throws Exception {
        try {
            loadCustomConfiguration("Custom_customColumnForbidden.xml");
            report.execute(CUSTOM_CONFIGURATION_NAMESPACE, ReportType.CLIENT, listContainingClientFilter, null);
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link AbstractReport#executeReport(ReportConfiguration)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: the given ReportConfiguration is <tt>null</tt>
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testExecuteReportFailConfigNull() throws Exception {
        try {
            report.executeReport(null);
            fail("should throw");
        } catch (NullPointerException expected) {
            //expected
        }
    }

    /**
     * This method tests the correctness of {@link #checkRenderResult(String, ReportType, java.util.List, String)}.
     * <p/>
     * An assertion failure is expected when using a wrong result file as expected result.
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testComparisonValid() throws Exception {
        employeeFilter.addFilterValue("admin");
        filterList.add(employeeFilter);
        try {
            checkRenderResult(DEFAULT_CONFIGURATION_NAMESPACE, ReportType.EMPLOYEE, filterList, "../Different.xml");
            throw new Exception("Test setup invalid, unmatched test should throw AssertionFailedError.");
        } catch (AssertionFailedError expected) {
            //expected
        }
    }

    /**
     * This method tests the correctness of {@link Report#execute(String, ReportType, java.util.List)}.
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#EMPLOYEE}, values: "admin"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testExecuteAccuracy1() throws Exception {
        employeeFilter.addFilterValue("admin");
        filterList.add(employeeFilter);
        checkRenderResult(DEFAULT_CONFIGURATION_NAMESPACE, ReportType.EMPLOYEE, filterList, "EMPLOYEE_admin.xml");
        checkRenderResult(CUSTOM_CONFIGURATION_NAMESPACE, ReportType.EMPLOYEE, filterList, "custom_EMPLOYEE_admin.xml");
    }

    /**
     * This method tests the correctness of {@link Report#execute(String, ReportType, java.util.List)}.
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#EMPLOYEE}, values: "admin","jhuges"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testExecuteAccuracy2() throws Exception {
        employeeFilter.addFilterValue("admin");
        employeeFilter.addFilterValue("jhuges");
        filterList.add(employeeFilter);
        checkRenderResult(DEFAULT_CONFIGURATION_NAMESPACE, ReportType.EMPLOYEE, filterList,
            "EMPLOYEE_admin_jhuges.xml");
        checkRenderResult(CUSTOM_CONFIGURATION_NAMESPACE, ReportType.EMPLOYEE, filterList,
            "custom_EMPLOYEE_admin_jhuges.xml");
    }

    /**
     * This method tests the correctness of {@link Report#execute(String, ReportType, java.util.List)}.
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#EMPLOYEE}, values: "admin"; {@link Column#BILLABLE}, value:"1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testExecuteAccuracy3() throws Exception {
        employeeFilter.addFilterValue("admin");
        filterList.add(employeeFilter);
        billableFilter.addFilterValue("1");
        filterList.add(billableFilter);
        checkRenderResult(DEFAULT_CONFIGURATION_NAMESPACE, ReportType.EMPLOYEE, filterList,
            "EMPLOYEE_admin_BILLABLE_1.xml");
        checkRenderResult(CUSTOM_CONFIGURATION_NAMESPACE, ReportType.EMPLOYEE, filterList,
            "custom_EMPLOYEE_admin_BILLABLE_1.xml");
    }

    /**
     * This method tests the correctness of {@link Report#execute(String, ReportType, java.util.List)}.
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#EMPLOYEE}, values: "admin"; {@link Column#DATE}, value: "01-01-2006"-"06-30-2006"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testExecuteAccuracy4() throws Exception {
        employeeFilter.addFilterValue("admin");
        filterList.add(employeeFilter);
        dateFilter.addFilterRange("01-01-2006", "06-30-2006");
        filterList.add(dateFilter);
        checkRenderResult(DEFAULT_CONFIGURATION_NAMESPACE, ReportType.EMPLOYEE, filterList,
            "EMPLOYEE_admin_DATE_01-01-2006_06-30-2006.xml");
        checkRenderResult(CUSTOM_CONFIGURATION_NAMESPACE, ReportType.EMPLOYEE, filterList,
            "custom_EMPLOYEE_admin_DATE_01-01-2006_06-30-2006.xml");
    }

    /**
     * This method tests the correctness of {@link Report#execute(String, ReportType, java.util.List)}.
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#EMPLOYEE}, values: "admin"; {@link Column#CLIENT}, value: "client1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testExecuteAccuracy5() throws Exception {
        employeeFilter.addFilterValue("admin");
        filterList.add(employeeFilter);
        clientFilter.addFilterValue("client1");
        filterList.add(clientFilter);
        checkRenderResult(DEFAULT_CONFIGURATION_NAMESPACE, ReportType.EMPLOYEE, filterList,
            "EMPLOYEE_admin_CLIENT_client1.xml");
        checkRenderResult(CUSTOM_CONFIGURATION_NAMESPACE, ReportType.EMPLOYEE, filterList,
            "custom_EMPLOYEE_admin_CLIENT_client1.xml");
    }

    /**
     * This method tests the correctness of {@link Report#execute(String, ReportType, java.util.List)}.
     * <p/>
     * Tested Report Type: {@link ReportType#EMPLOYEE}
     * <p/>
     * Tested Filter: {@link Column#EMPLOYEE}, values: "admin"; {@link Column#CLIENT}, value: "client1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testExecuteAccuracy6() throws Exception {
        employeeFilter.addFilterValue("admin");
        filterList.add(employeeFilter);
        projectFilter.addFilterValue("project1");
        filterList.add(projectFilter);
        checkRenderResult(DEFAULT_CONFIGURATION_NAMESPACE, ReportType.EMPLOYEE, filterList,
            "EMPLOYEE_admin_PROJECT_project1.xml");
        checkRenderResult(CUSTOM_CONFIGURATION_NAMESPACE, ReportType.EMPLOYEE, filterList,
            "custom_EMPLOYEE_admin_PROJECT_project1.xml");
    }

    /**
     * This method tests the correctness of {@link Report#execute(String, ReportType, java.util.List)}.
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#CLIENT}, values: "client1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testExecuteAccuracy7() throws Exception {
        clientFilter.addFilterValue("client1");
        filterList.add(clientFilter);
        checkRenderResult(DEFAULT_CONFIGURATION_NAMESPACE, ReportType.CLIENT, filterList,
            "CLIENT_client1.xml");
        checkRenderResult(CUSTOM_CONFIGURATION_NAMESPACE, ReportType.CLIENT, filterList,
            "custom_CLIENT_client1.xml");
    }

    /**
     * This method tests the correctness of {@link Report#execute(String, ReportType, java.util.List)}.
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#CLIENT}, values: "client1", "client2"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testExecuteAccuracy8() throws Exception {
        clientFilter.addFilterValue("client1");
        clientFilter.addFilterValue("client2");
        filterList.add(clientFilter);
        checkRenderResult(DEFAULT_CONFIGURATION_NAMESPACE, ReportType.CLIENT, filterList,
            "CLIENT_client1_client2.xml");
        checkRenderResult(CUSTOM_CONFIGURATION_NAMESPACE, ReportType.CLIENT, filterList,
            "custom_CLIENT_client1_client2.xml");
    }

    /**
     * This method tests the correctness of {@link Report#execute(String, ReportType, java.util.List)}.
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#CLIENT}, values: "client1"; {@link Column#BILLABLE}, value: "1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testExecuteAccuracy9() throws Exception {
        clientFilter.addFilterValue("client1");
        filterList.add(clientFilter);
        billableFilter.addFilterValue("1");
        filterList.add(billableFilter);
        checkRenderResult(DEFAULT_CONFIGURATION_NAMESPACE, ReportType.CLIENT, filterList,
            "CLIENT_client1_BILLABLE_1.xml");
        checkRenderResult(CUSTOM_CONFIGURATION_NAMESPACE, ReportType.CLIENT, filterList,
            "custom_CLIENT_client1_BILLABLE_1.xml");
    }

    /**
     * This method tests the correctness of {@link Report#execute(String, ReportType, java.util.List)}.
     * <p/>
     * Tested Report Type: {@link ReportType#CLIENT}
     * <p/>
     * Tested Filter: {@link Column#CLIENT}, values: "client1"; {@link Column#DATE}, value: "01-01-2006"-"06-30-2006"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testExecuteAccuracy10() throws Exception {
        clientFilter.addFilterValue("client1");
        filterList.add(clientFilter);
        dateFilter.addFilterRange("01-01-2006", "06-30-2006");
        filterList.add(dateFilter);
        checkRenderResult(DEFAULT_CONFIGURATION_NAMESPACE, ReportType.CLIENT, filterList,
            "CLIENT_client1_DATE_01-01-2006_06-30-2006.xml");
        checkRenderResult(CUSTOM_CONFIGURATION_NAMESPACE, ReportType.CLIENT, filterList,
            "custom_CLIENT_client1_DATE_01-01-2006_06-30-2006.xml");
    }

    /**
     * This method tests the correctness of {@link Report#execute(String, ReportType, java.util.List)}.
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#PROJECT}, values: "project1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testExecuteAccuracy11() throws Exception {
        projectFilter.addFilterValue("project1");
        filterList.add(projectFilter);
        checkRenderResult(DEFAULT_CONFIGURATION_NAMESPACE, ReportType.PROJECT, filterList,
            "PROJECT_project1.xml");
        checkRenderResult(CUSTOM_CONFIGURATION_NAMESPACE, ReportType.PROJECT, filterList,
            "custom_PROJECT_project1.xml");
    }

    /**
     * This method tests the correctness of {@link Report#execute(String, ReportType, java.util.List)}.
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#PROJECT}, values: "project1", "project2"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testExecuteAccuracy12() throws Exception {
        projectFilter.addFilterValue("project1");
        projectFilter.addFilterValue("project2");
        filterList.add(projectFilter);
        checkRenderResult(DEFAULT_CONFIGURATION_NAMESPACE, ReportType.PROJECT, filterList,
            "PROJECT_project1_project2.xml");
        checkRenderResult(CUSTOM_CONFIGURATION_NAMESPACE, ReportType.PROJECT, filterList,
            "custom_PROJECT_project1_project2.xml");
    }

    /**
     * This method tests the correctness of {@link Report#execute(String, ReportType, java.util.List)}.
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#PROJECT}, values: "project1"; {@link Column#BILLABLE}, value: "1"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testExecuteAccuracy13() throws Exception {
        projectFilter.addFilterValue("project1");
        filterList.add(projectFilter);
        billableFilter.addFilterValue("1");
        filterList.add(billableFilter);
        checkRenderResult(DEFAULT_CONFIGURATION_NAMESPACE, ReportType.PROJECT, filterList,
            "PROJECT_project1_BILLABLE_1.xml");
        checkRenderResult(CUSTOM_CONFIGURATION_NAMESPACE, ReportType.PROJECT, filterList,
            "custom_PROJECT_project1_BILLABLE_1.xml");
    }

    /**
     * This method tests the correctness of {@link Report#execute(String, ReportType, java.util.List)}.
     * <p/>
     * Tested Report Type: {@link ReportType#PROJECT}
     * <p/>
     * Tested Filter: {@link Column#PROJECT}, values: "project1"; {@link Column#DATE}, value: "01-01-2006"-"06-30-2006"
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testExecuteAccuracy14() throws Exception {
        projectFilter.addFilterValue("project1");
        filterList.add(projectFilter);
        dateFilter.addFilterRange("01-01-2006", "06-30-2006");
        filterList.add(dateFilter);
        checkRenderResult(DEFAULT_CONFIGURATION_NAMESPACE, ReportType.PROJECT, filterList,
            "PROJECT_project1_DATE_01-01-2006_06-30-2006.xml");
        checkRenderResult(CUSTOM_CONFIGURATION_NAMESPACE, ReportType.PROJECT, filterList,
            "custom_PROJECT_project1_DATE_01-01-2006_06-30-2006.xml");
    }

    /**
     * This method tests the correctness of {@link AbstractReport#executeReport(ReportConfiguration)}.
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testExecuteReport() throws Exception {
        final AbstractReport implementation = getImplementation();
        final ReportConfiguration reportConfiguration = new ReportConfiguration(implementation.getCategory(),
            ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        final ArrayList filters = new ArrayList();
        final EqualityFilter equalityFilter = new EqualityFilter(Column.CLIENT, FilterCategory.CLIENT);
        equalityFilter.addFilterValue("client1");
        filters.add(equalityFilter);
        reportConfiguration.setFilters(filters);
        final ArrayList columns = new ArrayList();
        columns.add(new BasicColumnDecorator(Column.CLIENT.getName(), Column.CLIENT.getName()));
        columns.add(new BasicColumnDecorator(Column.DATE.getName(), Column.DATE.getName()));
        reportConfiguration.setColumnDecorators(columns);
        String rendered = implementation.executeReport(reportConfiguration);

        // some synthetic root element needs to be around the rendered data,
        // as the data contains multiple elements on root level, and multiple
        // roots are no valid XML, so for being able to check the equality
        // using XML parsers, a synthetic root element needs to be added.

        rendered = "<BODY>" + rendered + "</BODY>";
        checkContentsAreEqual(getRelativePathToExpectedResultsDir() + "CLIENT_Manual.xml", rendered);
    }

    /**
     * This method does the test setup needed.
     *
     * @throws Exception in case some unexpected Exception occurs
     */
    protected void setUp() throws Exception {
        super.setUp();
        report = getImplementation();
        filterList = new ArrayList();
        listContainingClientFilter = new ArrayList();
        listContainingClientFilter.add(new EqualityFilter(Column.CLIENT, FilterCategory.CLIENT));

        employeeFilter = new EqualityFilter(Column.EMPLOYEE, FilterCategory.EMPLOYEE);
        clientFilter = new EqualityFilter(Column.CLIENT, FilterCategory.CLIENT);
        billableFilter = new EqualityFilter(Column.BILLABLE, FilterCategory.BILLABLE);
        dateFilter = new RangeFilter(Column.DATE, FilterCategory.DATE);
        projectFilter = new EqualityFilter(Column.PROJECT, FilterCategory.PROJECT);

    }

    /**
     * This method is to be implemented by subclasses and shall return an instance of the {@link AbstractReport}
     * implementation to be tested.
     *
     * @return the {@link AbstractReport} implementation to be tested.
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    protected abstract AbstractReport getImplementation() throws Exception;

    /**
     * This method is called from the various constructor failure tests. The subclass should call the constructor of
     * the to-be-tested {@link AbstractReport} subclass in this method.
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    protected abstract void callConstructor() throws Exception;

    /**
     * This method is used in the various accuracy tests. A subclass should return the relative path to the directory
     * that contains the expected render results as XML files here.
     *
     * @return the relative path to the directory containing the expected render result files
     */
    protected abstract String getRelativePathToExpectedResultsDir();

    /**
     * This method clears the {@link #CUSTOM_CONFIGURATION_NAMESPACE} and loads the file with the given name into {@link
     * ConfigManager} afterwards.
     *
     * @param filename the name of the configuration file to be loaded
     *
     * @throws ConfigManagerException in case an unexpected error occurs
     */
    private void loadCustomConfiguration(final String filename) throws ConfigManagerException {
        removeNamespace(CUSTOM_CONFIGURATION_NAMESPACE);
        ConfigManager.getInstance().add("configuration/" + filename);
    }

    /**
     * This method clears the {@link #FILTERS_CONFIGURATION_NAMESPACE} and loads the file with the given name into
     * {@link ConfigManager} afterwards.
     *
     * @param filename the name of the configuration file to be loaded
     *
     * @throws ConfigManagerException in case an unexpected error occurs
     */
    private void loadFiltersConfiguration(final String filename) throws ConfigManagerException {
        removeNamespace(FILTERS_CONFIGURATION_NAMESPACE);
        ConfigManager.getInstance().add("configuration/" + filename);
    }

    /**
     * This method clears the {@link #COLUMNS_CONFIGURATION_NAMESPACE} and loads the file with the given name into
     * {@link ConfigManager} afterwards.
     *
     * @param filename the name of the configuration file to be loaded
     *
     * @throws ConfigManagerException in case an unexpected error occurs
     */
    private void loadColumnsConfiguration(final String filename) throws ConfigManagerException {
        removeNamespace(COLUMNS_CONFIGURATION_NAMESPACE);
        ConfigManager.getInstance().add("configuration/" + filename);
    }

    /**
     * This method calls {@link AbstractReport#execute(String, ReportType, java.util.List)} with the given namespace,
     * ReportType and filters on the to-be-tested implementation instance obtained via {@link #getImplementation()}.
     * <p/>
     * Afterwards the result is tested for equality with the expected result loaded from the file with the given name in
     * the directory returned from {@link #getRelativePathToExpectedResultsDir()}. Upon match failure an {@link
     * junit.framework.AssertionFailedError} is thrown.
     *
     * @param namespace the namespace to be used as argument to {@link AbstractReport#execute(String, ReportType,
     *                  java.util.List)}
     * @param type      the ReportType to be used as argument to {@link AbstractReport#execute(String, ReportType,
     *                  java.util.List)}
     * @param filters   the list of filters to be used as argument to {@link AbstractReport#execute(String, ReportType,
     *                  java.util.List)}
     * @param filename  the name of the file containing the expected result
     *
     * @throws Exception            in case an unexpected Exception occurs
     * @throws AssertionFailedError in case the actual and expected result do not match
     */
    private void checkRenderResult(final String namespace, final ReportType type, final List filters,
                                   final String filename) throws Exception {
        String rendered = report.execute(namespace, type, filters, null);

        // some synthetic root element needs to be around the rendered data,
        // as the data contains multiple elements on root level, and multiple
        // roots are no valid XML, so for being able to check the equality
        // using XML parsers, a synthetic root element needs to be added.

        rendered = "<BODY>" + rendered + "</BODY>";

        checkContentsAreEqual(getRelativePathToExpectedResultsDir() + filename, rendered);
    }
}
