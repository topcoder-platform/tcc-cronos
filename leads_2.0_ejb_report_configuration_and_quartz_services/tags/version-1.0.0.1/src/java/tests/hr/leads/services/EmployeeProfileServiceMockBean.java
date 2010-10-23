/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services;

import hr.leads.services.model.HighLevelSummaryReportFilter;
import hr.leads.services.model.ManagerRole;
import hr.leads.services.model.SortOrder;
import hr.leads.services.model.jpa.EmployeeProfile;
import hr.leads.services.model.jpa.QuestionResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * A mock up class (ejb) which implements EmployeeProfileServiceLocal for unit tests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class EmployeeProfileServiceMockBean implements EmployeeProfileServiceLocal {

    /**
     * <p>
     * Represents the mapping from the CNUM to employee profile.
     * </p>
     */
    private Map<String, EmployeeProfile> map = new HashMap<String, EmployeeProfile>();

    /**
     * <p>
     * Creates an instance of EmployeeProfileServiceMockBean.
     * </p>
     */
    public EmployeeProfileServiceMockBean() {
        // do nothing
    }

    /**
     * <p>
     * Gets an employee profile via cnum.
     * </p>
     *
     * @param cnum the cnum.
     * @return the employee profile.
     */
    public EmployeeProfile getEmployeeProfile(String cnum) {
        return map.get(cnum);
    }

    /**
     * <p>
     * Gets the employee profiles by manager.
     * </p>
     *
     * @param managerCNUM the cnum of the manager.
     * @param managerRole the type of manager role.
     * @param bandId the bandId.
     * @param full is full or not.
     * @param sortColumn the column to sort.
     * @param sortOrder the sorted order.
     *
     * @return the list of employee profile.
     */
    @Override
    public List<EmployeeProfile> getEmployeeProfilesByManager(
            String managerCNUM, ManagerRole managerRole,
            Long bandId, boolean full, String sortColumn, SortOrder sortOrder) {
        List<EmployeeProfile> result = new ArrayList<EmployeeProfile>();
        for (String cnum : map.keySet()) {
            EmployeeProfile employee = map.get(cnum);
            if (ManagerRole.DIRECT == managerRole
                    && (employee.getBpManagerCNUM() == null
                            || !employee.getBpManagerCNUM().equals(managerCNUM))) {
                continue;
            }
            if (ManagerRole.MATRIX == managerRole
                    && (employee.getMatrixManagerCNUM() == null
                            || !employee.getMatrixManagerCNUM().equals(managerCNUM))) {
                continue;
            }

            if (bandId != null) {
                if (bandId != employee.getBand().getId()) {
                    continue;
                }
            }
            result.add(employee);
        }
        return result;
    }

    /**
     * <p>
     * Searches employee profiles via filter.
     * </p>
     *
     * @param filter the filter.
     *
     * @return the list of employee profiles.
     */
    @Override
    public List<EmployeeProfile> searchEmployeeProfiles(
            HighLevelSummaryReportFilter filter) {
        List<EmployeeProfile> result = new ArrayList<EmployeeProfile>();
        for (String cnum : map.keySet()) {
            EmployeeProfile employee = map.get(cnum);
            if (employee.getCountryId() != filter.getCountryId()) {
                continue;
            }
            result.add(employee);
        }
        return result;
    }

    /**
     * <p>
     * Adds a employee.
     * </p>
     * @param employee the employee to add.
     */
    public void addEmployee(EmployeeProfile employee) {
        map.put(employee.getCnum(), employee);
    }

    /**
     * <p>
     * Assigns a employee to a matrix manager.
     * </p>
     * @param employeeCNUM the employeeCNUM.
     * @param matrixManagerCNUM the matrixManagerCNUM.
     *
     * @throws UnsupportedOperationException always.
     * @throws EmployeeProfileServiceException never.
     * @throws UserServicesEntityNotFoundException never.
     */
    @Override
    public void assignEmployeeToMatrixManager(String employeeCNUM,
            String matrixManagerCNUM) throws EmployeeProfileServiceException,
            UserServicesEntityNotFoundException {
        throw new UnsupportedOperationException("The operation is not supported.");

    }

    /**
     * <p>
     * Gets employee question responses.
     * </p>
     *
     * @param employeeCNUM the employeeCNUM.
     * @return nothing.
     * @throws UnsupportedOperationException always.
     * @throws EmployeeProfileServiceException never.
     */
    @Override
    public List<QuestionResponse> getEmployeeQuestionResponses(
            String employeeCNUM) throws EmployeeProfileServiceException {
        throw new UnsupportedOperationException("The operation is not supported.");
    }

    /**
     * <p>
     * Gets full employee profile.
     * </p>
     *
     * @param employeeCNUM the employeeCNUM.
     *
     * @return nothing.
     *
     * @throws UnsupportedOperationException always.
     * @throws EmployeeProfileServiceException never.
     */
    @Override
    public EmployeeProfile getFullEmployeeProfile(String employeeCNUM)
        throws EmployeeProfileServiceException {
        throw new UnsupportedOperationException("The operation is not supported.");
    }

    /**
     * <p>
     * Removes matrix manager from employee.
     * </p>
     *
     * @param employeeCNUM the employeeCNUM.
     * @throws UnsupportedOperationException always.
     * @throws EmployeeProfileServiceException never.
     */
    @Override
    public void removeMatrixManagerFromEmployee(String employeeCNUM)
        throws EmployeeProfileServiceException {
        throw new UnsupportedOperationException("The operation is not supported.");

    }

    /**
     * <p>
     * Saves the employee with question responses.
     * </p>
     *
     * @param employeeCNUM the employeeCNUM.
     * @param responses the responses.
     *
     * @throws UnsupportedOperationException always.
     * @throws EmployeeProfileServiceException never.
     * @throws UserServicesEntityNotFoundException never.
     */
    @Override
    public void saveEmployeeWithQuestionResponses(String employeeCNUM, List<QuestionResponse> responses)
        throws EmployeeProfileServiceException, UserServicesEntityNotFoundException {
        throw new UnsupportedOperationException("The operation is not supported.");
    }

}
