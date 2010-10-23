/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.failuretests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.leads.services.EmployeeProfileService;
import hr.leads.services.model.HighLevelSummaryReportFilter;
import hr.leads.services.model.ManagerRole;
import hr.leads.services.model.SortOrder;
import hr.leads.services.model.jpa.Band;
import hr.leads.services.model.jpa.EmployeeProfile;
import hr.leads.services.model.jpa.EmployeeProfileStatus;
import hr.leads.services.model.jpa.QuestionResponse;

/**
 * <p>
 * A mock implementation of EmployeeProfileService. used for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockEmployeeProfileService implements EmployeeProfileService {
    /**
     * <p>
     * Represents the result.
     * </p>
     */
    private Object result;

    /**
     * <p>
     * Creates an instance of MockEmployeeProfileService.
     * </p>
     */
    public MockEmployeeProfileService() {
        Band band1 = new Band();
        band1.setId(1);
        Band band2 = new Band();
        band2.setId(2);

        EmployeeProfileStatus submitted = new EmployeeProfileStatus();
        submitted.setName("Assessed");
        EmployeeProfileStatus notSubmitted = new EmployeeProfileStatus();
        notSubmitted.setName("Not Assessed");

        EmployeeProfile employee1 = new EmployeeProfile();
        employee1.setBand(band1);
        employee1.setCnum("employee1");
        employee1.setMatrixManagerCNUM("manager1");
        employee1.setEmployeeProfileStatus(submitted);
        EmployeeProfile employee2 = new EmployeeProfile();
        employee2.setBand(band2);
        employee2.setCnum("employee2");
        employee2.setMatrixManagerCNUM("manager2");
        employee2.setEmployeeProfileStatus(notSubmitted);

        result = new ArrayList<EmployeeProfile>(Arrays.asList(employee1, employee2));
    }

    /**
     * <p>
     * This method will select all EmployeeProfile records where the managerCNUM is equal to either bpManagerCNUM (if
     * managerRole is ManagerRole.DIRECT) or matrixManagerCNUM (if managerRole is ManagerRole.MATRIX). The profile's
     * question responses will be loaded only if the full flag is true. If the bandId is provided, it will filter by
     * band.id. If sortColumn is not null, sort by it (it would match the name of the field) in the direction
     * specified by sortOrder.
     * </p>
     *
     * @param sortColumn
     *            if it's not null, the retrieved EmployeeProfile records will be sorted on this field.
     * @param bandId
     *            the id of EmployeeProfile.band to filter.
     * @param full
     *            if this is true, EmployeeProfile.questionResponses will be loaded
     * @param sortOrder
     *            this denotes whether the sorting is in ascending or descending order.
     * @param managerRole
     *            the manager role. If it's ManagerRole.DIRECT, then retrieved EmployeeProfile instances'
     *            bpManagerCNUM should match managerCNUM, otherwise matrixManagerCNUM should match managerCNUM
     * @param managerCNUM
     *            the manager CNUM that the EmployeeProfile's bpManagerCNUM or matrixManagerCNUM should match
     *            depending on the value of managerRole.
     *
     * @return the retrieved EmployeeProfile instances, probably after sorting. It won't be null but could be empty.
     *
     * @throws EmployeeProfileServiceException
     *             if managerCNUM is null or empty.
     * @throws EmployeeProfileServiceException
     *             if any error occurs.
     */
    @SuppressWarnings("unchecked")
    public List<EmployeeProfile> getEmployeeProfilesByManager(String managerCNUM, ManagerRole managerRole, Long bandId,
        boolean full, String sortColumn, SortOrder sortOrder) {
        checkNullOrEmpty(managerCNUM, "managerCNUM");
        return (List<EmployeeProfile>) result;
    }

    /**
     * <p>
     * Assign an employee to a matrix manager.
     * </p>
     *
     * @param matrixManagerCNUM
     *            the CNUM of the matrix manager to assign the employee to.
     * @param employeeCNUM
     *            the CNUM of the employee to assign.
     */
    public void assignEmployeeToMatrixManager(String employeeCNUM, String matrixManagerCNUM) {
    }

    /**
     * <p>
     * Remove an employee's assigned matrix manager.
     * </p>
     *
     * @param employeeCNUM
     *            the CNUM of the employee.
     */
    public void removeMatrixManagerFromEmployee(String employeeCNUM) {
    }

    /**
     * <p>
     * Get an EmployeeProfile by CNUM, without question responses being loaded.
     * </p>
     *
     * @param employeeCNUM
     *            the CNUM of the employee to get.
     *
     * @return the EmployeeProfile matching the given CNUM, or null if it's not found.
     *
     * @throws EmployeeProfileServiceException
     *             if employeeCNUM is null or empty.
     * @throws EmployeeProfileServiceException
     *             if any error occurs.
     */
    @SuppressWarnings("unchecked")
    public EmployeeProfile getEmployeeProfile(String employeeCNUM) {
        checkNullOrEmpty(employeeCNUM, "employeeCNUM");

        return ((List<EmployeeProfile>) result).get(0);
    }

    /**
     * <p>
     * Get full employee profile that matches the given employeeCNUM with question responses being loaded.
     * </p>
     *
     * @param employeeCNUM
     *            the CNUM of the employee to get.
     *
     * @return the retrieved full employee profile or null if not found.
     *
     * @throws EmployeeProfileServiceException
     *             if employeeCNUM is null or empty.
     * @throws EmployeeProfileServiceException
     *             if any error occurs.
     */
    @SuppressWarnings("unchecked")
    public EmployeeProfile getFullEmployeeProfile(String employeeCNUM) {
        checkNullOrEmpty(employeeCNUM, "employeeCNUM");

        return ((List<EmployeeProfile>) result).get(0);
    }

    /**
     * <p>
     * Search employee profiles by filtering conditions given in highLevelSummaryReportFilter.
     * </p>
     *
     * @param highLevelSummaryReportFilter
     *            the high level summary report filter containing filtering conditions.
     *
     * @return the employee profiles matching the filtering condition. It won't be null but could be empty.
     *
     * @throws EmployeeProfileServiceException
     *             if highLevelSummaryReportFilter is null.
     * @throws EmployeeProfileServiceException
     *             if any error occurs.
     */
    @SuppressWarnings("unchecked")
    public List<EmployeeProfile> searchEmployeeProfiles(HighLevelSummaryReportFilter highLevelSummaryReportFilter) {
        checkNull(highLevelSummaryReportFilter, "highLevelSummaryReportFilter");
        return (List<EmployeeProfile>) result;
    }

    /**
     * <p>
     * Get question responses of the employee that match the given employeeCNUM.
     * </p>
     *
     * @param employeeCNUM
     *            the CNUM of the employee to match.
     *
     * @return the retrieved question responses of that employee.
     */
    public List<QuestionResponse> getEmployeeQuestionResponses(String employeeCNUM) {
        return null;
    }

    /**
     * <p>
     * Save the question responses of an employee. This method will calculate employee potential and performance, as
     * well as update the profile status, before saving the employee profile and responses.
     * </p>
     *
     * @param employeeCNUM
     *            the CNUM of the employee that the responses are associated with.
     * @param responses
     *            the question responses.
     */
    public void saveEmployeeWithQuestionResponses(String employeeCNUM, List<QuestionResponse> responses) {

    }

    /**
     * <p>
     * Sets the result.
     * </p>
     *
     * @param result
     *            the result.
     */
    public void setResult(Object result) {
        this.result = result;
    }

    /**
     * <p>
     * Validates the value of a string. The value can not be <code>null</code> or an empty string.
     * </p>
     *
     * @param value
     *            the value of the variable to be validated.
     * @param name
     *            the name of the variable to be validated.
     *
     * @throws IllegalArgumentException
     *             if the given string is <code>null</code> or an empty string.
     */
    private static void checkNullOrEmpty(String value, String name) {
        checkNull(value, name);

        if (value.trim().length() == 0) {
            throw new IllegalArgumentException("'" + name + "' should not be an empty string.");
        }
    }

    /**
     * <p>
     * Validates the value of a variable. The value can not be <code>null</code>.
     * </p>
     *
     * @param value
     *            the value of the variable to be validated.
     * @param name
     *            the name of the variable to be validated.
     *
     * @throws IllegalArgumentException
     *             if the value of the variable is <code>null</code>.
     */
    private static void checkNull(Object value, String name) {
        if (value == null) {
            throw new IllegalArgumentException("'" + name + "' should not be null.");
        }
    }
}
