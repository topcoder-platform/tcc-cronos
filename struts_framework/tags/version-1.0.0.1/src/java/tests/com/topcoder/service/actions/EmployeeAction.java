/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * The employee action used in the unit tests. It extends AbstractAction.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class EmployeeAction extends AbstractAction {

    /**
     * Prepares the aggregate data model with the employee data to display.
     *
     * @return the aggregate data model containing the employee data to display
     */
    public AggregateDataModel prepareAggregateDataModel() {
        List<Employee> employees = new ArrayList<Employee>();

        Department dept1 = new Department();
        dept1.setName("Shipping Department");
        Department dept2 = new Department();
        dept2.setName("Payroll Department");

        Employee emp = new Employee();
        emp.setFirstName("John");
        emp.setLastName("Doe");
        emp.setAddress("123 Nowhere Street");
        emp.setAge(42);
        emp.setDepartment(dept1);
        emp.setIntroducer("Jane the introducer");
        employees.add(emp);

        emp = new Employee();
        emp.setFirstName("Jane");
        emp.setLastName("Doe");
        emp.setAddress("345 Anywhere Street");
        emp.setAge(38);
        emp.setDepartment(dept2);
        emp.setIntroducer("ivern the introducer");
        employees.add(emp);

        AggregateDataModel model = new AggregateDataModel();
        model.setData("Employees", employees);
        return model;
    }

    /**
     * The execute method for the action. It loads the employee data to the aggregate data model and
     * returns success.
     *
     * @return the action result, always success in this case
     */
    public String execute() {
        // prepare the data model
        setModel(prepareAggregateDataModel());
        return SUCCESS;
    }

}
