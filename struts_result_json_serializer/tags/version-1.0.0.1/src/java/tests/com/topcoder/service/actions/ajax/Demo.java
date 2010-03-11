/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.ajax;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.mock.MockActionInvocation;
import com.topcoder.service.actions.AggregateDataModel;

/**
 * <p>
 * The demo of the class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends TestCase {
    /**
     * <p>
     * Returns the test suite of this class.
     * </p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(Demo.class);
    }

    /**
     * <p>
     * The API usage of the component.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDemo() throws Exception {
        // create a CustomFormatAJAXResult instance
        CustomFormatAJAXResult instance = new CustomFormatAJAXResult();
        // create an invocation
        MockActionInvocation invocation = new MockActionInvocation();
        MockAbstractAction action = new MockAbstractAction();
        AggregateDataModel dataModel = new AggregateDataModel();

        // create a java bean object
        Project project = new Project();
        project.setId(100);
        project.setName("TC_Project");
        project.setDescription("Project description");

        dataModel.setData("return", project);
        dataModel.setAction("tc");
        action.setModel(dataModel);

        // create HttpServletResponse instance
        invocation.setAction(action);
        MockHttpServletResponse response = new MockHttpServletResponse();
        ServletActionContext.setResponse(response);

        // invoke the execute
        instance.execute(invocation);
        System.out.println(((MockServletOutputStream) response.outputStream).buf.toString());
    }
}
