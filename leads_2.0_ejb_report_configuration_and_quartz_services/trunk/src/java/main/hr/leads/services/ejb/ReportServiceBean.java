/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.ejb;

import hr.leads.services.EmployeeProfileService;
import hr.leads.services.EmployeeProfileServiceException;
import hr.leads.services.LDAPUserService;
import hr.leads.services.LeadsServiceConfigurationException;
import hr.leads.services.LeadsServiceException;
import hr.leads.services.model.EmployeeDTO;
import hr.leads.services.model.HighLevelSummaryReportFilter;
import hr.leads.services.model.LDAPUser;
import hr.leads.services.model.ManagerRole;
import hr.leads.services.model.Report;
import hr.leads.services.model.ReportRecord;
import hr.leads.services.model.ReportType;
import hr.leads.services.model.Role;
import hr.leads.services.model.SortOrder;
import hr.leads.services.model.UserSearchFilter;
import hr.leads.services.model.jpa.Band;
import hr.leads.services.model.jpa.EmployeeProfile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

/**
 * <p>
 * This class is an EJB that implements ReportService business interface to
 * retrieve different kinds of reports.these reports are:
 * <ul>
 * <li>direct / organization nine box report</li>
 * <li>direct / organization summary report</li>
 * <li>direct / organization roll up report</li>
 * <li>high level summary report</li>
 * </ul>
 * </p>
 * <p>
 * It extends {@link BaseReportConfigurationServiceBean} class.
 * </p>
 * <p>
 * It uses instances of EmployeeProfileService and LDAPUserService to retrieve
 * employee information.
 * </p>
 * <p>
 * It uses ReportSortingComparator to sort employees / report records based on
 * given sort columns.
 * </p>
 * <p>
 * <b>Usage:</b> Simply use the get methods to retrieve what you want, for
 * example:
 *
 * <pre>
 * List&lt;EmployeeDTO&gt; result = reportService.getDirectNineBoxReport(&quot;123&quot;, null);
 * or
 * Report report = getDirectRollupReport(&quot;123&quot;, null);
 * </pre>
 *
 * </p>
 * <p>
 * <b> Thread Safety: </b> This class is mutable and not thread safe. But it is
 * always used in thread safe manner in EJB container because its state doesn't
 * change after initialization. This bean assumes that transactions are managed
 * by the container.
 * </p>
 *
 * @author semi_sleep, TCSDEVELOPER
 * @version 1.0
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ReportServiceBean extends BaseReportConfigurationServiceBean
        implements ReportServiceLocal {

    /**
     * <p>
     * Represents the EmployeeProfileService instance used to retrieve employee profiles.
     * </p>
     * <p>
     * It cannot be null after initialization.
     * </p>
     * <p>
     * It is initialized by EJB container injection.
     * </p>
     * <p>
     * It is used by the business methods of this service bean.
     * </p>
    */
    @EJB(name = "employeeProfileService")
    private EmployeeProfileService employeeProfileService;

    /**
     * <p>
     * Represents the LDAPUserService instance used to retrieve user detail.
     * </p>
     * <p>
     * It cannot be null after initialization.
     * </p>
     * <p>
     * It is initialized by EJB container injection.
     * </p>
     * <p>
     * It is used by the business methods of this service bean.
     * </p>
     */
    @EJB(name = "ldapUserService")
    private LDAPUserService ldapUserService;

    /**
     * <p>
     * Represents the name that should match EmployeeProfileStatus#name if the status is
     * submitted.
     * </p>
     * <p>
     * It cannot not be null / empty.
     * </p>
     * <p>
     * Default to "Assessed", can be initialized by EJB container injection.
     * </p>
     * <p>
     * It's used by createReport() method.
     * </p>
     */
    @Resource
    private String submittedStatusName = "Assessed";

    /**
     * <p>
     * Represents the name that should match EmployeeProfileStatus#name if the status is
     * not submitted.
     * </p>
     * <p>
     * Can not be null / empty.
     * </p>
     * <p>
     * Default to "Not Assessed", can be initialized by EJB container injection.
     * </p>
     * <p>
     * It's used by createReport() method.
     * </p>
     */
    @Resource
    private String notSubmittedStatusName = "Not Assessed";

    /**
     * <p>
     * Creates an instance of ReportServiceBean.
     * </p>
     *
     * <p>
     * This is the default constructor.
     * </p>
     */
    public ReportServiceBean() {
        // do nothing
    }

    /**
     * <p>
     * Gets the direct nine box report by given manager CNUM and band id.
     * </p>
     *
     * <p>
     * NOTE, the band id can be null.
     * </p>
     *
     * @param managerCNUM
     *            the CNUM for manager used to generate direct nine box report.
     * @param bandId
     *            the band id used to generate direct nine box report.
     *
     * @return the EmployeeDTO list as direct nine box report.
     *
     * @throws IllegalArgumentException
     *             if managerCNUM is null / empty.
     * @throws LeadsServiceException
     *             if any error occurs.
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<EmployeeDTO> getDirectNineBoxReport(String managerCNUM,
            Long bandId) throws LeadsServiceException {

        // prepare for logging
        String methodName = getClass().getName() + ".getDirectNineBoxReport";
        Logger logger = getLogger();

        // log the entrance and the parameters
        ReportHelper.logEntrance(logger, methodName);
        ReportHelper.logParameters(logger,
                new Object[] {managerCNUM, bandId }, new String[] {"managerCNUM", "bandId"});

        // validates the parameter
        ReportHelper.checkNullOrEmpty(logger, methodName, managerCNUM, "managerCNUM");

        // get all employee using this manager's cnum, ManagerRole.DIRECT and
        // bandId
        List<EmployeeProfile> employees = employeeProfileService
                .getEmployeeProfilesByManager(managerCNUM, ManagerRole.DIRECT,
                        bandId, true, null, null);

        // create a list to store EmployeeDTO
        List<EmployeeDTO> dtos = new ArrayList<EmployeeDTO>();
        for (EmployeeProfile employee : employees) {

            EmployeeDTO dto = new EmployeeDTO();
            // set employee :
            dto.setEmployee(employee);

            // get employee detail
            LDAPUser ldapEmployee = ldapUserService.getUser(employee.getCnum());
            if (ldapEmployee == null) {
                ReportHelper.logError(logger, methodName,
                        "The ldapEmployee of Cnum: " + employee.getCnum()
                                + " does not exist.");
                throw new LeadsServiceException("the ldapEmployee of CNUM: "
                        + employee.getCnum() + " does not exist.");
            }

            // set ldapEmployee
            dto.setLdapEmployee(ldapEmployee);

            // add to the result list
            dtos.add(dto);
        }

        // log the output and exit
        ReportHelper.logOutput(logger, dtos);
        ReportHelper.logExit(logger, methodName);

        // return the value
        return dtos;
    }

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
     *
     * @return The EmployeeDTO list as organization nine box report.
     *
     * @throws IllegalArgumentException
     *             if managerCNUM is null / empty, or if filterManagerCNUM is
     *             empty.
     * @throws LeadsServiceException
     *             if any error occurs.
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<EmployeeDTO> getOrganizationNineBoxReport(String managerCNUM,
            Long bandId, String filterManagerCNUM) throws LeadsServiceException {

        // prepare for logging
        String methodName = getClass().getName() + ".getOrganizationNineBoxReport";
        Logger logger = getLogger();

        // log the entrance and the request parameters
        ReportHelper.logEntrance(logger, methodName);
        ReportHelper.logParameters(logger, new Object[] {managerCNUM, bandId, filterManagerCNUM},
            new String[] {"managerCNUM", "bandId", "filterManagerCNUM"});

        // validates the parameter
        ReportHelper.checkNullOrEmpty(logger, methodName, managerCNUM, "managerCNUM");
        if (filterManagerCNUM != null) {
            ReportHelper.checkNullOrEmpty(logger, methodName, filterManagerCNUM,
                    "filterManagerCNUM");
        }

        // get the ldap user of manager
        LDAPUser ldapManager = ldapUserService.getUser(managerCNUM);
        if (ldapManager == null) {
            ReportHelper.logError(logger, methodName,
                    "ldap user does not exist. cnum: " + managerCNUM);
            throw new LeadsServiceException("the ldapManager of managerCNUM: "
                    + managerCNUM + " does not exist.");
        }

        // search all the employees under a given manager
        List<EmployeeDTO> dtos = searchEmployees(managerCNUM);

        // get the iterator for dtos
        Iterator<EmployeeDTO> iterator = dtos.iterator();

        // iterate through it
        while (iterator.hasNext()) {

            EmployeeDTO dto = iterator.next();

            // check if we should filter it with band id
            if (bandId != null && bandId != dto.getEmployee().getBand().getId()) {
                // band id not matched, remove it
                iterator.remove();
                continue;
            }

            String matrixManagerCNUM = dto.getEmployee().getMatrixManagerCNUM();

            // get its matrix manager result
            if (matrixManagerCNUM == null) {
                // do not have matrix manager cnum, ignore it.
                iterator.remove();
                continue;
            }

            LDAPUser ldapMatrixManager = ldapUserService.getUser(matrixManagerCNUM);

            if (ldapMatrixManager == null) {
                // we should log the message and continue(no exception). Please
                // refer to:
                // http://forums.topcoder.com/?module=Thread&threadID=689307&start=0
                ReportHelper.logError(logger, methodName,
                        "ldap manager not found for cnum: "
                                + matrixManagerCNUM);

                iterator.remove();
                continue;
            }

            // check if the organization matches
            if (!ldapManager.getOrganization().equals(
                    ldapMatrixManager.getOrganization())) {
                // remove it if not match
                iterator.remove();
                continue;
            }

            // check if we should filter the manager cnum
            if (filterManagerCNUM != null) {
                // it should equals to the matrix manager cnum or bp manager
                // cnum
                if (!filterManagerCNUM.equals(matrixManagerCNUM)
                        && !filterManagerCNUM.equals(dto.getEmployee().getBpManagerCNUM())) {
                    // not match, remove it
                    iterator.remove();
                    continue;
                }
            }
        }

        // log the output and exit
        ReportHelper.logOutput(logger, dtos);
        ReportHelper.logExit(logger, methodName);

        // return the value
        return dtos;
    }

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
     *
     * @return The EmployeeDTO list as summary report.
     *
     * @throws IllegalArgumentException
     *             if managerCNUM is null / empty, or if reportType is null, or
     *             if sortColumns contains null / empty string.
     * @throws LeadsServiceException
     *             if any error occurs.
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<EmployeeDTO> getSummaryReport(String managerCNUM,
            ReportType reportType, String[] sortColumns)
        throws LeadsServiceException {

        // prepare for logging
        Logger logger = getLogger();
        String methodName = getClass().getName() + ".getSummaryReport";

        // log the entrance and parameters
        ReportHelper.logEntrance(logger, methodName);
        ReportHelper.logParameters(logger, new Object[] {managerCNUM, reportType, sortColumns},
            new String[] {"managerCNUM", "reportType", "sortColumns"});

        // validates the parameters
        ReportHelper.checkNullOrEmpty(logger, methodName, managerCNUM, "managerCNUM");
        ReportHelper.checkNull(logger, methodName, reportType, "reportType");
        checkSortColumns(logger, methodName, sortColumns, "sortColumns");

        // create the dtos to store the result
        List<EmployeeDTO> dtos = null;

        // perform differently according to the report type
        if (reportType == ReportType.DIRECT_REPORT) {
            // the same as get direct nine box report
            dtos = getDirectNineBoxReport(managerCNUM, null);
        } else {
            // the same as get organization nine box report
            dtos = getOrganizationNineBoxReport(managerCNUM, null, null);
        }

        // check if the sort columns are set
        if (sortColumns != null && sortColumns.length != 0) {
            // creates a mapping from employee to dto
            Map<EmployeeProfile, EmployeeDTO> mapping = new HashMap<EmployeeProfile, EmployeeDTO>();

            // create a employee list
            List<EmployeeProfile> employees = new ArrayList<EmployeeProfile>();

            for (EmployeeDTO dto : dtos) {

                mapping.put(dto.getEmployee(), dto);

                employees.add(dto.getEmployee());
            }

            // create a comparator to sort employees
            ReportSortingComparator<EmployeeProfile> comparator =
                new ReportSortingComparator<EmployeeProfile>(EmployeeProfile.class, sortColumns);

            // sort employees
            Collections.sort(employees, comparator);

            // clear the dtos list
            dtos = new ArrayList<EmployeeDTO>();

            // add to the result
            for (EmployeeProfile employee : employees) {
                EmployeeDTO dto = mapping.get(employee);
                dtos.add(dto);
            }
        }

        // log the output and exit
        ReportHelper.logOutput(logger, dtos);
        ReportHelper.logExit(logger, methodName);

        // return the result
        return dtos;
    }

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
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Report getDirectRollupReport(String managerCNUM, String[] sortColumns)
        throws LeadsServiceException {

        // prepare for logging
        Logger logger = getLogger();
        String methodName = getClass().getName() + ".getDirectRollupReport";

        // log the entrance and parameters
        ReportHelper.logEntrance(logger, methodName);
        ReportHelper.logParameters(logger, new Object[] {managerCNUM, sortColumns},
                new String[] {"managerCNUM", "sortColumns"});

        ReportHelper.checkNullOrEmpty(logger, methodName, managerCNUM, "managerCNUM");
        checkSortColumns(logger, methodName, sortColumns, "sortColumns");

        // get all employee using this manager's cnum and ManagerRole.DIRECT :
        List<EmployeeProfile> employees;
        try {
            employees = employeeProfileService.getEmployeeProfilesByManager(
                    managerCNUM, ManagerRole.DIRECT, null, true, "band", SortOrder.ASC);
        } catch (EmployeeProfileServiceException e) {
            ReportHelper.logError(
                    logger, methodName, "failed to get employee profiles by manager.");
            throw new LeadsServiceException(
                    "failed to get employee profiles by manager.", e);
        }

        // create report and return
        Report report = createReport(employees, sortColumns);

        // log the output and exit
        ReportHelper.logOutput(logger, report);
        ReportHelper.logExit(logger, methodName);

        return report;
    }

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
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Report getOrganizationRollupReport(String managerCNUM,
            String filterManagerCNUM, String[] sortColumns)
        throws LeadsServiceException {

        // prepare for logging
        Logger logger = getLogger();
        String methodName = getClass().getName() + ".getOrganizationRollupReport";

        // log the entrance and request parameters
        ReportHelper.logEntrance(logger, methodName);
        ReportHelper.logParameters(logger, new Object[] {managerCNUM, filterManagerCNUM, sortColumns},
            new String[] {"managerCNUM", "filterManagerCNUM", "sortColumns"});

        // validates the parameters
        ReportHelper.checkNullOrEmpty(logger, methodName, managerCNUM, "managerCNUM");
        if (filterManagerCNUM != null) {
            ReportHelper.checkNullOrEmpty(logger, methodName, filterManagerCNUM, "filterManagerCNUM");
        }
        checkSortColumns(logger, methodName, sortColumns, "sortColumns");

        // the logic is same to organization nine box report, we
        // use it to avoid code redundancy
        List<EmployeeDTO> dtos = getOrganizationNineBoxReport(
                managerCNUM, null, filterManagerCNUM);

        // change the dto to employee
        List<EmployeeProfile> employees = new ArrayList<EmployeeProfile>();
        for (EmployeeDTO dto : dtos) {
            employees.add(dto.getEmployee());
        }

        // create the report
        Report report = createReport(employees, sortColumns);

        // log the output and exit
        ReportHelper.logOutput(logger, report);
        ReportHelper.logExit(logger, methodName);

        return report;
    }

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
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Report getHighLevelSummaryReport(
            HighLevelSummaryReportFilter filter, String[] sortColumns)
        throws LeadsServiceException {
        // prepare for logging
        Logger logger = getLogger();
        String methodName = getClass().getName() + ".getHighLevelSummaryReport";

        // log the entrance and request parameters
        ReportHelper.logEntrance(logger, methodName);
        ReportHelper.logParameters(logger,
            new Object[] {filter, sortColumns}, new String[] {"filter", "sortColumns"});

        // validates the parameters
        ReportHelper.checkNull(logger, methodName, filter, "filter");
        checkSortColumns(logger, methodName, sortColumns, "sortColumns");


        List<EmployeeProfile> employees;
        try {
            // get the employees using the filter
            employees = employeeProfileService.searchEmployeeProfiles(filter);
        } catch (EmployeeProfileServiceException e) {
            ReportHelper.logError(logger, methodName,
                    "failed to search employee profiles by filter.");
            throw new LeadsServiceException(
                    "failed to search employee profiles.", e);
        }

        // create the report
        Report report = createReport(employees, sortColumns);

        // log the output and return
        ReportHelper.logOutput(logger, report);
        ReportHelper.logExit(logger, methodName);

        return report;
    }

    /**
     * <p>
     * Checks to ensure that all dependencies are properly injected.
     * </p>
     * <p>
     * This method overrides corresponding method in base class to add more
     * validations.
     * </p>
     *
     * @throws LeadsServiceConfigurationException
     *             if any injected dependency is invalid, or if any error occurs
     *             when initializing this bean.
     */
    @Override
    @PostConstruct
    protected void afterBeanInitialized() {

        // run the super method first
        super.afterBeanInitialized();

        // prepare for logging
        Logger logger = getLogger();
        String methodName = getClass().getName() + ".afterBeanInitialized";

        // check if the employeeProfileService successfully injected
        if (employeeProfileService == null) {
            ReportHelper.logError(logger, methodName,
                    "employeeProfileService is not properly injected.");
            throw new LeadsServiceConfigurationException(
                    "employeeProfileService is not properly injected.");
        }

        // check if the ldapUserService successfully injected
        if (ldapUserService == null) {
            ReportHelper.logError(logger, methodName,
                    "ldapUserService is not properly injected.");
            throw new LeadsServiceConfigurationException(
                    "ldapUserService is not properly injected.");
        }

        // check if the submittedStatusName successfully injected
        if (submittedStatusName == null
                || submittedStatusName.trim().length() == 0) {
            ReportHelper.logError(logger, methodName,
                    "submittedStatusName is not properly injected.");
            throw new LeadsServiceConfigurationException(
                    "submittedStatusName is not properly injected.");
        }

        // check if the notSubmittedStatusName successfully injected
        if (notSubmittedStatusName == null
                || notSubmittedStatusName.trim().length() == 0) {
            ReportHelper.logError(logger, methodName,
                    "notSubmittedStatusName is not properly injected.");
            throw new LeadsServiceConfigurationException(
                    "notSubmittedStatusName is not properly injected.");
        }

        ReportHelper.logExit(logger, methodName);
    }

    /**
     * <p>
     * Searches for all employees of a manager in an organization.
     * </p>
     * <p>
     * Note that while searching some employees are represented by LDAPUser,
     * others are represented by EmployeeProfile, in order to utilize all these
     * objects so that they don't need to be retrieved again in the caller
     * method, the LDAPUser and EmployeeProfile is combined to EmployeeDTO.
     * </p>
     *
     * @param managerCNUM
     *            the cnum for manager used to search employees.
     * @return A list of EmployeeDTO representing employees under of given
     *         manager.
     * @throws IllegalArgumentException
     *             if managerCNUM is null / empty.
     * @throws LeadsServiceException
     *             if any error occurs.
     */
    private List<EmployeeDTO> searchEmployees(String managerCNUM)
        throws LeadsServiceException {

        // prepare for logging
        String methodName = getClass().getName() + ".searchEmployees";
        Logger logger = getLogger();

        // validates the parameters
        ReportHelper.checkNullOrEmpty(logger, methodName, managerCNUM, "managerCNUM");

        // retrieve the ldap user
        LDAPUser manager = ldapUserService.getUser(managerCNUM);
        if (manager == null) {
            ReportHelper.logError(
                    logger, methodName, "the manager of cnum: " + managerCNUM + " does not exist.");
            throw new LeadsServiceException(
                    "the manager of cnum: " + managerCNUM + " does not exist.");
        }

        // creates the running list (to perform BFS search)
        List<EmployeeDTO> runningList = new ArrayList<EmployeeDTO>();

        // creates the list to stores the result
        List<EmployeeDTO> resultList = new ArrayList<EmployeeDTO>();

        // creates the cnums to insure no duplicated employee(to make sure the
        // BFS algorithm can terminate).
        Set<String> cnums = new HashSet<String>();

        // creates and initialize the filter
        UserSearchFilter filter = new UserSearchFilter();
        filter.setOrganization(manager.getOrganization());
        filter.setRole(Role.EMPLOYEE);

        // set the first cnum to filter
        filter.setManagerCnum(managerCNUM);

        // add all of its sub employees
        addSubEmployees(filter, cnums, runningList, true);

        // perform the BFS search
        while (!runningList.isEmpty()) {
            // retrieve the current employee dto and than remove it
            EmployeeDTO dto = runningList.remove(0);

            // add it to the running list
            resultList.add(dto);

            // set the new cnum to search
            filter.setManagerCnum(dto.getLdapEmployee().getCnum());

            // add all the sub employees to the running list
            addSubEmployees(filter, cnums, runningList, false);
        }

        return resultList;
    }

    /**
     * <p>
     * Adds all the sub-employees to the running list.
     * </p>
     *
     * @param filter
     *            the filter (including the managerCNUM)
     * @param cnums
     *            the cnums to check if the cnum is already in runninglist.
     * @param runningList
     *            the running list
     * @param first
     *            is the first time to invoke this function
     * @throws LeadsServiceException
     *             if any error occurs.
     */
    private void addSubEmployees(UserSearchFilter filter, Set<String> cnums,
            List<EmployeeDTO> runningList, boolean first) throws LeadsServiceException {

        // prepare for logging
        String methodName = getClass().getName() + ".addSubEmployees";
        Logger logger = getLogger();

        // search for employees under this manager
        List<LDAPUser> ldapEmployees = ldapUserService.searchUsers(filter);

        // check to add each ldapEmployee.
        for (LDAPUser ldapEmployee : ldapEmployees) {

            // if the cnum have already added, ignore; otherwise add it
            if (cnums.add(ldapEmployee.getCnum())) {

                // retrieve the employee info
                EmployeeProfile employee =
                    employeeProfileService.getEmployeeProfile(ldapEmployee.getCnum());

                if (employee == null) {
                    ReportHelper.logError(logger, methodName,
                            "employee for cnum: " + ldapEmployee.getCnum() + " does not exist.");
                    throw new LeadsServiceException(
                            "employee for cnum: " + ldapEmployee.getCnum() + " does not exist.");
                }

                // creates the employee dto
                EmployeeDTO dto = new EmployeeDTO();
                dto.setLdapEmployee(ldapEmployee);
                dto.setEmployee(employee);

                // add the dto the the running list
                runningList.add(dto);
            }
        }

        if (first) {
            // if it is the first time to invoke this method,
            // do not search the matrix.
            // according to:
            // http://forums.topcoder.com/?module=Thread&threadID=689312&start=0
            return;
        }
        // search to see if the employee is a matrix manager to any
        List<EmployeeProfile> subEmployees = employeeProfileService.getEmployeeProfilesByManager(
                filter.getManagerCnum(), ManagerRole.MATRIX, null, true, null, null);

        // check to add each sub-employee
        for (EmployeeProfile subEmployee : subEmployees) {

            // if the cnum have already added, ignore; otherwise add it
            if (cnums.add(subEmployee.getCnum())) {

                // retrieve the ldap employee info
                LDAPUser ldapSubEmployee = ldapUserService.getUser(subEmployee.getCnum());

                if (ldapSubEmployee == null) {
                    ReportHelper.logError(logger, methodName,
                            "the ldapSubEmployee of cnum: " + subEmployee.getCnum() + " does not exist.");
                    throw new LeadsServiceException(
                            "the ldapSubEmployee of cnum: " + subEmployee.getCnum() + " does not exist.");
                }
                // create EmployeeDTO
                EmployeeDTO dto = new EmployeeDTO();
                dto.setLdapEmployee(ldapSubEmployee);
                dto.setEmployee(subEmployee);

                // add it to the running list
                runningList.add(dto);
            }
        }

    }

    /**
     * <p>
     * Creates Report using given employeeProfiles.
     * </p>
     * <p>
     * The employees will be grouped by band, each band will have a separate
     * ReportRecord.
     * </p>
     * <p>
     * The Report#records will be sorted according to given sort columns.
     * </p>
     *
     * @param employees
     *            the employee profiles used to create report.
     * @param sortColumns
     *            the columns used to sort report records.
     * @return the Report instance created according to given parameters.
     * @throws IllegalArgumentException
     *             if employeeProfiles is null, or if employeeProfiles contains
     *             null element, or if sortColumn contains null / empty string.
     */
    private Report createReport(List<EmployeeProfile> employees,
            String[] sortColumns) {

        // prepare for logging
        final String methodName = getClass().getName() + ".createReport";
        Logger logger = getLogger();

        // validates the parameters
        checkList(logger, methodName, employees, "employees");
        checkSortColumns(logger, methodName, sortColumns, "sortColumns");

        // group them by band id:

        // create the band id --> list of employee profile mapping
        Map<Band, List<EmployeeProfile>> mappings =
            new LinkedHashMap<Band, List<EmployeeProfile>>();

        // put each employee profile to its band id list
        for (EmployeeProfile employeeProfile : employees) {
            List<EmployeeProfile> list = mappings.get(employeeProfile.getBand());

            // first employee of the band
            if (list == null) {
                // create a new list and add it to the map
                list = new ArrayList<EmployeeProfile>();
                mappings.put(employeeProfile.getBand(), list);
            }

            // add the employee profile to the list
            list.add(employeeProfile);
        }

        // creates a list of records
        List<ReportRecord> records = new ArrayList<ReportRecord>();


        // we create the report record for each band
        for (Band band : mappings.keySet()) {
            // retrieve the band list
            List<EmployeeProfile> list = mappings.get(band);

            // create the record result from this band
            ReportRecord record = new ReportRecord();

            // add it to the result list
            records.add(record);

            // set the band id of this record
            record.setBand(band);

            // set the total employees
            record.setTotalEmployees(list.size());

            // calculates the count of submitted, notsubmitted, btlr and notBtlr employees.
            int submittedEmployees = 0;
            int notSubmittedEmployees = 0;
            int btlrEmployees = 0;
            int notBtlrEmployees = 0;
            for (EmployeeProfile employeeProfile : list) {
                // retrieve the status of the employee
                String statusName =
                    employeeProfile.getEmployeeProfileStatus().getName();


                if (submittedStatusName.equals(statusName)) {
                    // submitted employee
                    submittedEmployees++;
                    if (employeeProfile.isBtlResourceIdentifier()) {
                        // submmited && btr
                        btlrEmployees++;
                    } else {
                        // submmited && notBtr
                        notBtlrEmployees++;
                    }
                } else if (notSubmittedStatusName.equals(statusName)) {
                    // notSubmmitted employees
                    notSubmittedEmployees++;
                }
            }

            // set the result to the record
            record.setSubmittedEmployees(submittedEmployees);
            record.setNotSubmittedEmployees(notSubmittedEmployees);

            record.setBtlrEmployees(btlrEmployees);
            record.setNotBtlrEmployees(notBtlrEmployees);

            // calculates the btr percentage
            if (record.getSubmittedEmployees() == 0) {
                record.setBtlrPercentage(0);
            } else {
                record.setBtlrPercentage(
                        record.getBtlrEmployees() / (float) record.getSubmittedEmployees());
            }

        }

        if (sortColumns != null && sortColumns.length != 0) {
            // sort the records for the sort columns
            ReportSortingComparator<ReportRecord> comparator = new ReportSortingComparator<ReportRecord>(
                    ReportRecord.class, sortColumns);
            Collections.sort(records, comparator);
        }

        // creates the final report
        Report report = new Report();

        // set the records to the report
        report.setRecords(records);

        // calculates the total employees and total submissions to the report
        for (ReportRecord record : records) {
            // accumulate the total employees
            report.setTotalEmployees(report.getTotalEmployees() + record.getTotalEmployees());
            // accumulate the total submissions
            report.setSubmissions(report.getSubmissions() + record.getSubmittedEmployees());
        }

        // calculates the submitted rate
        if (report.getTotalEmployees() == 0) {
            report.setCompletePercentage(0);
        } else {
            report.setCompletePercentage(
                    report.getSubmissions() / (float) report.getTotalEmployees());
        }

        // return the final report
        return report;
    }

    /**
     * <p>
     * Checks if the list is valid. If it is null or contains object which is
     * null, IllegalArgumentException is thrown.
     * </p>
     *
     * @param logger
     *            the logger to log the error.
     * @param methodName
     *            the name of the method to check the error.
     * @param argument
     *            the value of the argument.
     * @param argumentName
     *            the name of the argument.
     * @param <T>
     *            the type of the list element.
     *
     * @throws IllegalArgumentException
     *             if it is null or contains object which is null.
     */
    private <T> void checkList(Logger logger, String methodName, List<T> argument, String argumentName) {
        if (argument == null) {
            throw new IllegalArgumentException("The argument " + argumentName
                    + " cannot be null.");
        }

        for (T object : argument) {
            ReportHelper.checkNull(logger, methodName, object, "element in " + argumentName);
        }
    }

    /**
     * <p>
     * Checks if the the sort columns is valid.
     * </p>
     *
     * @param logger
     *            the logger to log the error.
     * @param methodName
     *            the name of the method to check.
     *
     * @param sortColumns
     *            the sortColumns to check.
     * @param name
     *            the name of the column.
     * @throws IllegalArgumentException
     *             if it contains null or empty.
     */
    private void checkSortColumns(Logger logger, String methodName, String[] sortColumns, String name) {
        if (sortColumns == null || sortColumns.length == 0) {
            return;
        }

        ReportHelper.checkContainsNullOrEmpty(logger, methodName, sortColumns, name);
    }
}
