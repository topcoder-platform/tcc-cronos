/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.failuretests;

import org.apache.log4j.Logger;

import hr.leads.services.EmployeeProfileService;
import hr.leads.services.LDAPUserService;
import hr.leads.services.ejb.ReportServiceBean;
import hr.leads.services.model.HighLevelSummaryReportFilter;
import hr.leads.services.model.ReportType;
import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for ReportServiceBean.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class ReportServiceBeanFailureTests extends TestCase {

    /**
     * <p>
     * The ReportServiceBean instance for testing.
     * </p>
     */
    private ReportServiceBean instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new ReportServiceBean();

        EmployeeProfileService employeeProfileService = new MockEmployeeProfileService();
        LDAPUserService ldapUserService = new MockLDAPUserService();
        TestsHelper.setField(instance, "employeeProfileService", employeeProfileService);
        TestsHelper.setField(instance, "ldapUserService", ldapUserService);
        TestsHelper.setField(instance, "logger", Logger.getLogger(ReportServiceBean.class));
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ReportServiceBeanFailureTests.class);
    }

    /**
     * <p>
     * Tests ReportServiceBean#getDirectNineBoxReport(String,Long) for failure.
     * It tests the case that when managerCNUM is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetDirectNineBoxReport_NullManagerCNUM() throws Exception {
        try {
            instance.getDirectNineBoxReport(null, 1L);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ReportServiceBean#getDirectNineBoxReport(String,Long) for failure.
     * It tests the case that when managerCNUM is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetDirectNineBoxReport_EmptyManagerCNUM() throws Exception {
        try {
            instance.getDirectNineBoxReport(" ", 1L);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ReportServiceBean#getOrganizationNineBoxReport(String,Long,String) for failure.
     * It tests the case that when managerCNUM is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetOrganizationNineBoxReport_NullManagerCNUM() throws Exception {
        try {
            instance.getOrganizationNineBoxReport(null, 1L, "filterManagerCNUM");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ReportServiceBean#getOrganizationNineBoxReport(String,Long,String) for failure.
     * It tests the case that when managerCNUM is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetOrganizationNineBoxReport_EmptyManagerCNUM() throws Exception {
        try {
            instance.getOrganizationNineBoxReport(" ", 1L, "filterManagerCNUM");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ReportServiceBean#getOrganizationNineBoxReport(String,Long,String) for failure.
     * It tests the case that when filterManagerCNUM is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetOrganizationNineBoxReport_EmptyFilterManagerCNUM() throws Exception {
        try {
            instance.getOrganizationNineBoxReport("managerCNUM", 1L, " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ReportServiceBean#getSummaryReport(String,ReportType,String[]) for failure.
     * It tests the case that when managerCNUM is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetSummaryReport_NullManagerCNUM() throws Exception {
        try {
            instance.getSummaryReport(null, ReportType.DIRECT_REPORT, new String[] {});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ReportServiceBean#getSummaryReport(String,ReportType,String[]) for failure.
     * It tests the case that when managerCNUM is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetSummaryReport_EmptyManagerCNUM() throws Exception {
        try {
            instance.getSummaryReport(" ", ReportType.DIRECT_REPORT, new String[] {});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ReportServiceBean#getSummaryReport(String,ReportType,String[]) for failure.
     * It tests the case that when reportType is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetSummaryReport_NullReportType() throws Exception {
        try {
            instance.getSummaryReport("managerCNUM", null, new String[] {});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ReportServiceBean#getSummaryReport(String,ReportType,String[]) for failure.
     * It tests the case that when sortColumns contains null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetSummaryReport_NullInSortColumns() throws Exception {
        try {
            instance.getSummaryReport("managerCNUM", ReportType.DIRECT_REPORT, new String[] {null});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ReportServiceBean#getSummaryReport(String,ReportType,String[]) for failure.
     * It tests the case that when sortColumns contains empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetSummaryReport_EmptyInSortColumns() throws Exception {
        try {
            instance.getSummaryReport("managerCNUM", ReportType.DIRECT_REPORT, new String[] {" "});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ReportServiceBean#getDirectRollupReport(String,String[]) for failure.
     * It tests the case that when managerCNUM is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetDirectRollupReport_NullManagerCNUM() throws Exception {
        try {
            instance.getDirectRollupReport(null, new String[] {});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ReportServiceBean#getDirectRollupReport(String,String[]) for failure.
     * It tests the case that when managerCNUM is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetDirectRollupReport_EmptyManagerCNUM() throws Exception {
        try {
            instance.getDirectRollupReport(" ", new String[] {});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ReportServiceBean#getDirectRollupReport(String,String[]) for failure.
     * It tests the case that when sortColumns contains null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetDirectRollupReport_NullInSortColumns() throws Exception {
        try {
            instance.getDirectRollupReport("managerCNUM", new String[] {null});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ReportServiceBean#getDirectRollupReport(String,String[]) for failure.
     * It tests the case that when sortColumns contains empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetDirectRollupReport_EmptyInSortColumns() throws Exception {
        try {
            instance.getDirectRollupReport("managerCNUM", new String[] {" "});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ReportServiceBean#getOrganizationRollupReport(String,String,String[]) for failure.
     * It tests the case that when managerCNUM is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetOrganizationRollupReport_NullManagerCNUM() throws Exception {
        try {
            instance.getOrganizationRollupReport(null, "filterManagerCNUM", new String[] {});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ReportServiceBean#getOrganizationRollupReport(String,String,String[]) for failure.
     * It tests the case that when managerCNUM is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetOrganizationRollupReport_EmptyManagerCNUM() throws Exception {
        try {
            instance.getOrganizationRollupReport(" ", "filterManagerCNUM", new String[] {});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ReportServiceBean#getOrganizationRollupReport(String,String,String[]) for failure.
     * It tests the case that when filterManagerCNUM is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetOrganizationRollupReport_EmptyFilterManagerCNUM() throws Exception {
        try {
            instance.getOrganizationRollupReport("managerCNUM", " ", new String[] {});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ReportServiceBean#getOrganizationRollupReport(String,String,String[]) for failure.
     * It tests the case that when sortColumns contains null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetOrganizationRollupReport_NullInSortColumns() throws Exception {
        try {
            instance.getOrganizationRollupReport("managerCNUM", "filterManagerCNUM", new String[] {null});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ReportServiceBean#getOrganizationRollupReport(String,String,String[]) for failure.
     * It tests the case that when sortColumns contains empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetOrganizationRollupReport_EmptyInSortColumns() throws Exception {
        try {
            instance.getOrganizationRollupReport("managerCNUM", "filterManagerCNUM", new String[] {" "});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ReportServiceBean#getHighLevelSummaryReport(HighLevelSummaryReportFilter,String[]) for failure.
     * It tests the case that when filter is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetHighLevelSummaryReport_NullFilter() throws Exception {
        try {
            instance.getHighLevelSummaryReport(null, new String[] {});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ReportServiceBean#getHighLevelSummaryReport(HighLevelSummaryReportFilter,String[]) for failure.
     * It tests the case that when sortColumns contains null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetHighLevelSummaryReport_NullInSortColumns() throws Exception {
        try {
            instance.getHighLevelSummaryReport(new HighLevelSummaryReportFilter(), new String[] {null});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ReportServiceBean#getHighLevelSummaryReport(HighLevelSummaryReportFilter,String[]) for failure.
     * It tests the case that when sortColumns contains empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetHighLevelSummaryReport_EmptyInSortColumns() throws Exception {
        try {
            instance.getHighLevelSummaryReport(new HighLevelSummaryReportFilter(), new String[] {" "});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

}