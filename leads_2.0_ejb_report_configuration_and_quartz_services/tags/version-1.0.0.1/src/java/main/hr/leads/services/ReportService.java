/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services;

import hr.leads.services.model.EmployeeDTO;
import hr.leads.services.model.HighLevelSummaryReportFilter;
import hr.leads.services.model.Report;
import hr.leads.services.model.ReportType;

import java.util.List;

/**
 * <p>
 * This service provides methods to retrieve different kinds of reports, these
 * reports are:
 * <ul>
 * <li>direct / organization nine box report</li>
 * <li>direct / organization summary report</li>
 * <li>direct / organization roll up report</li>
 * <li>high level summary report</li>
 * </ul>
 * </p>
 *
 * <p>
 * <b>Usage:</b> Simply use the get methods to retrieve what you want, for example:
 *
 * <pre>
 * List&lt;EmployeeDTO&gt; result = reportService.getDirectNineBoxReport(&quot;123&quot;, null);
 * or
 * Report report = getDirectRollupReport(&quot;123&quot;, null);
 * </pre>
 *
 * </p>
 *
 * <p>
 * <b> Thread Safety: </b> Implementations of this interface must be thread safe
 * when their configurable properties do not change after initialization.
 * </p>
 *
 * @author semi_sleep, TCSDEVELOPER
 * @version 1.0
 */
public interface ReportService {

    /**
     * <p>
     * Gets the direct nine box report by given manager CNUM and band id.
     * </p>
     *
     * @param managerCNUM
     *            the CNUM for manager used to generate direct nine box report.
     * @param bandId
     *            the band id used to generate direct nine box report.
     *
     * @return The EmployeeDTO list as direct nine box report.
     * @throws IllegalArgumentException
     *             if managerCNUM is null / empty.
     * @throws LeadsServiceException
     *             if any error occurs.
     */
    List<EmployeeDTO> getDirectNineBoxReport(String managerCNUM, Long bandId)
        throws LeadsServiceException;

    /**
     * <p>
     * Gets the organization nine box report by given manager CNUM, band id and
     * filter manager.
     * </p>
     *
     * @param managerCNUM
     *            the CNUM for manager used to generate organization nine box
     *            report.
     * @param bandId
     *            the band id used to restrict employee's band.
     * @param filterManagerCNUM
     *            the CNUM for manager used to filter employees.
     * @return The EmployeeDTO list as organization nine box report.
     * @throws IllegalArgumentException
     *             if managerCNUM is null / empty, or if filterManagerCNUM is
     *             empty.
     * @throws LeadsServiceException
     *             if any error occurs.
     */
    List<EmployeeDTO> getOrganizationNineBoxReport(String managerCNUM,
            Long bandId, String filterManagerCNUM) throws LeadsServiceException;

    /**
     * <p>
     * Gets the summary report by given manager CNUM and report type.
     * </p>
     * <p>
     * The returned EmployeeDTO list is sorted by sortColumns for
     * EmployeeDTO#employee.
     * </p>
     *
     * @param managerCNUM
     *            the CNUM for manager used to generate summary report.
     * @param reportType
     *            the type of report to be generated.
     * @param sortColumns
     *            the columns used to sort the returned EmployeeDTO list.
     * @return The EmployeeDTO list as summary report.
     * @throws IllegalArgumentException
     *             if managerCNUM is null / empty, or if reportType is null, or
     *             if sortColumns contains null / empty string.
     * @throws LeadsServiceException
     *             if any error occurs.
     */
    List<EmployeeDTO> getSummaryReport(String managerCNUM,
            ReportType reportType, String[] sortColumns)
        throws LeadsServiceException;

    /**
     * <p>
     * Gets the direct rollup report by given manager CNUM, the report records
     * will be sorted using given sort columns.
     * </p>
     *
     * @param managerCNUM
     *            the CNUM for manager used to generate direct rollup report.
     * @param sortColumns
     *            the columns used to sort report records.
     * @return the direct rollup report.
     * @throws IllegalArgumentException
     *             if managerCNUM is null / empty, or if sortColumn contains
     *             null / empty string.
     * @throws LeadsServiceException
     *             if any error occurs.
     */
    Report getDirectRollupReport(String managerCNUM, String[] sortColumns)
        throws LeadsServiceException;

    /**
     * <p>
     * Gets the organization rollup report by given manager CNUM and filter
     * manager CNUM, the report records will be sorted using given sort
     * columns.
     * </p>
     *
     * @param managerCNUM
     *            the CNUM for manager used to generate organization rollup
     *            report.
     *
     * @param filterManagerCNUM
     *            the CNUM for filter manager used to generate organization
     *            rollup report.
     *
     * @param sortColumns
     *            the columns used to sort report records.
     * @return The organization rollup report.
     *
     * @throws IllegalArgumentException
     *             if managerCNUM is null / empty, of if filterManagerCNUM is
     *             empty, or if sortColumn contains null / empty string.
     * @throws LeadsServiceException
     *             if any error occurs.
     */
    Report getOrganizationRollupReport(String managerCNUM,
            String filterManagerCNUM, String[] sortColumns)
        throws LeadsServiceException;

    /**
     * <p>
     * Gets the high level summary report by given manager CNUM and filter, the
     * report records will be sorted using given sort columns.
     * </p>
     *
     * @param filter
     *            the high level summary report filter.
     * @param sortColumns
     *            the columns used to sort report records.
     *
     * @return The high level summary report.
     *
     * @throws IllegalArgumentException
     *             if filter is null, or if sortColumn contains null / empty
     *             string.
     * @throws LeadsServiceException
     *             if any error occurs.
     */
    Report getHighLevelSummaryReport(HighLevelSummaryReportFilter filter,
            String[] sortColumns) throws LeadsServiceException;
}
