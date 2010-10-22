/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.accuracytests;

import hr.leads.services.ejb.BaseReportConfigurationServiceBean;
import hr.leads.services.ejb.ReportServiceBean;
import hr.leads.services.ejb.ReportServiceLocal;
import hr.leads.services.model.EmployeeDTO;
import hr.leads.services.model.HighLevelSummaryReportFilter;
import hr.leads.services.model.Report;
import hr.leads.services.model.ReportType;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * <p>
 * This class is the accuracy tests for class <code>ReportServiceBean</code>.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class ReportServiceBeanAccuracyTests extends AccuracyBaseTest {

    /**
     * Represents the logger instance.
     */
    private static final Logger LOGGER = Logger.getLogger("name");

    /**
     * <p>
     * Represents the ReportServiceBean instance for accuracy test.
     * </p>
     */
    private ReportServiceBean bean;

    /**
     * <p>
     * Represents the employee profile service used in tests.
     * </p>
     */
    private MockEmployeeProfileService employeeProfileService;

    /**
     * <p>
     * Represents the ldap user service used in tests.
     * </p>
     */
    private MockLDAPUserService ldapUserService;

    /**
     * <p>
     * Represents the band id used in tests.
     * </p>
     */
    private Long bandId = 1L;

    /**
     * <p>
     * Represents the filter manager CNUM used in tests.
     * </p>
     */
    private String filterManagerCNUM = "manager1";

    /**
     * <p>
     * Represents the report type used in tests.
     * </p>
     */
    private ReportType reportType = ReportType.DIRECT_REPORT;

    /**
     * <p>
     * Represents the sort columns used in tests.
     * </p>
     */
    private String[] sortColumns = new String[]{"cnum" };

    /**
     * <p>
     * Represents the filter used in tests.
     * </p>
     */
    private HighLevelSummaryReportFilter filter;

    /**
     * Sets up the test environment.
     *
     * @throws Exception if any exception occurred
     */
    public void setUp() throws Exception {
        bean = new ReportServiceBean();

        employeeProfileService = new MockEmployeeProfileService();
        ldapUserService = new MockLDAPUserService();
        setField(ReportServiceBean.class, bean, "employeeProfileService", employeeProfileService);
        setField(ReportServiceBean.class, bean, "ldapUserService", ldapUserService);
        setField(BaseReportConfigurationServiceBean.class, bean, "logger", LOGGER);

        filter = new HighLevelSummaryReportFilter();
        filter.setBusinessUnitId(1L);
    }

    /**
     * Tears down the test environment.
     *
     * @throws Exception if any exception occurred
     */
    public void tearDown() {
        filter = null;
        ldapUserService = null;
        employeeProfileService = null;
        bean = null;
    }

    /**
     * <p>
     * Accuracy test for the constructor.
     * </p>
     */
    public void testCtor1() {
        ReportServiceBean local = new ReportServiceBean();
        assertNotNull(local);
        assertTrue(local instanceof BaseReportConfigurationServiceBean);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ReportServiceBean()</code>.
     * </p>
     */
    public void testCtor2() {
        ReportServiceBean local = new ReportServiceBean();
        assertNotNull(local);
        assertTrue(local instanceof ReportServiceLocal);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getDirectNineBoxReport</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetDirectNineBoxReportAccuracy1() throws Exception {
        List<EmployeeDTO> res = bean.getDirectNineBoxReport("manager1", bandId);

        assertEquals(2, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getOrganizationNineBoxReport()</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetDirectNineBoxReportAccuracy2() throws Exception {
        filterManagerCNUM = null;

        List<EmployeeDTO> res = bean.getOrganizationNineBoxReport("manager1", bandId, filterManagerCNUM);
        assertEquals(3, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getSummaryReport()</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetSummaryReportAccuracy1() throws Exception {
        List<EmployeeDTO> res = bean.getSummaryReport("manager1", reportType, sortColumns);

        assertEquals("'getSummaryReport' should be correct.", 2, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getSummaryReport()</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetSummaryReportAccuracy2() throws Exception {
        sortColumns = null;

        List<EmployeeDTO> res = bean.getSummaryReport("manager1", reportType, sortColumns);

        assertEquals("'getSummaryReport' should be correct.", 2, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getSummaryReport()</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetSummaryReportAccuracy3() throws Exception {
        sortColumns = new String[0];

        List<EmployeeDTO> res = bean.getSummaryReport("manager1", reportType, sortColumns);

        assertEquals(2, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getDirectRollupReport()</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetDirectRollupReportAccuracy1() throws Exception {
        Report res = bean.getDirectRollupReport("manager1", new String[]{"submittedEmployees" });
        assertNotNull(res);

        res = bean.getDirectRollupReport("manager", null);
        assertNotNull(res);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getOrganizationRollupReport()</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetOrganizationRollupReportAccuracy1() throws Exception {
        Report res = bean.getOrganizationRollupReport("manager1", filterManagerCNUM, sortColumns);
        assertNotNull(res);

        res = bean.getOrganizationRollupReport("manager1", null, null);
        assertNotNull(res);

        res = bean.getOrganizationRollupReport("manager1", filterManagerCNUM, new String[]{});
        assertNotNull(res);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getHighLevelSummaryReport()</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetHighLevelSummaryReportAccuracy() throws Exception {
        Report res = bean.getHighLevelSummaryReport(filter, new String[]{"submittedEmployees" });
        assertNotNull(res);

        res = bean.getHighLevelSummaryReport(filter, null);
        assertNotNull(res);
    }
}
