/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import junit.framework.TestCase;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.mock.web.MockServletContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.config.providers.XWorkConfigurationProvider;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.ValueStackFactory;
import com.topcoder.web.reg.actions.miscellaneous.mock.MockBaseRangeAction;

/**
 * Unit test cases for {@link BaseRangeAction} class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseRangeActionTest extends TestCase {

    /**
     * A BaseRangeAction instance used for testing.
     */
    private BaseRangeAction instance;

    /**
     * Sets up the test environment.
     */
    public void setUp() {
        instance = new MockBaseRangeAction();
    }

    /**
     * Tears down the test environment.
     */
    public void tearDown() {
        instance = null;
    }

    /**
     * Accuracy test case for {@link BaseRangeAction#validate()} method.
     */
    public void testValidate() {
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.addContainerProvider(new XWorkConfigurationProvider());
        Configuration config = configurationManager.getConfiguration();
        Container container = config.getContainer();
        ValueStack stack = container.getInstance(ValueStackFactory.class).createValueStack();

        Map < String, Object > context = new HashMap < String, Object >();
        ActionContext.setContext(ServletActionContext.getActionContext(TestHelper.getHttpServletRequest()));
        ServletContext servletContext = (ServletContext) new MockServletContext(new FileSystemResourceLoader());
        context.put(StrutsStatics.SERVLET_CONTEXT, servletContext);
        ActionContext.setContext(new ActionContext(context));
        ActionContext.getContext().setValueStack(stack);

        instance.setStartRank(5);
        instance.setEndRank(1);
        instance.validate();

        assertEquals("Error in validate method", 1, instance.getActionErrors().size());
    }

    /**
     * Accuracy test case for {@link BaseRangeAction#checkConfiguration()} method.
     */
    public void testCheckConfiguration() {
        instance = (BaseRangeAction) TestHelper.setFieldsInBaseAction(instance);

        instance.setDefaultSortColumn(1);
        instance.checkConfiguration();
    }

    /**
     * Failure test case for {@link BaseRangeAction#checkConfiguration()} method. Sets a negative value and expects an
     * exception.
     */
    public void testCheckConfigurationFail() {
        instance = (BaseRangeAction) TestHelper.setFieldsInBaseAction(instance);

        instance.setDefaultSortColumn(-1);
        try {
            instance.checkConfiguration();
            fail("Exception should be thrown");
        } catch (UserDocumentationManagementActionsConfigurationException e) {
            // pass
        }
    }

    /**
     * Accuracy test case for {@link BaseRangeAction#BaseRangeAction()} constructor. Checks for null after
     * instantiation.
     */
    public void testBaseRangeAction() {
        assertNotNull("Error instantiating the class", instance);
    }

    /**
     * Accuracy test case for {@link BaseRangeAction#setDefaultStartAndEndRankValues(int)} method.
     */
    public void testSetDefaultStartAndEndRankValues() {
        instance.setDefaultStartAndEndRankValues(10);
        assertEquals("Error setting default start and end ranks", (Integer) 10, instance.getEndRank());
    }

    /**
     * Accuracy test case for {@link BaseRangeAction#setDefaultStartAndEndRankValues(int)} method.
     */
    public void testSetDefaultStartAndEndRankValues1() {
        instance.setEndRank(300);
        instance.setDefaultStartAndEndRankValues(10);
        assertEquals("Error setting default start and end ranks", (Integer) 201, instance.getEndRank());
    }

    /**
     * Failure test case for {@link BaseRangeAction#setDefaultStartAndEndRankValues(int)} method. Tries to set a
     * negative number and expects an exception.
     */
    public void testSetDefaultStartAndEndRankValuesFail() {
        try {
            instance.setDefaultStartAndEndRankValues(-1);
            fail("Exception should be thrown");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test {@link BaseRangeAction#setDefaultSortColumnValue()} method.
     */
    public void testSetDefaultSortColumnValue() {
        instance.setDefaultSortColumn(1);
        instance.setDefaultSortColumnValue();
        assertEquals("Error setting default sort column value", (Integer) 1, instance.getSortColumn());
    }

    /**
     * Accuracy test case for {@link BaseRangeAction#setFullList(boolean)} and {@link BaseRangeAction#isFullList()}
     * methods. Sets true and expects the same from getter.
     */
    public void testIsFullList() {
        instance.setFullList(true);
        assertTrue("Error in setter or getter of fullList", instance.isFullList());
    }

    /**
     * Accuracy test case for {@link BaseRangeAction#setSortColumn(Integer)} and {@link BaseRangeAction#getSortColumn()}
     * methods. Sets 1 and expects the same.
     */
    public void testGetSetSortColumn() {
        instance.setSortColumn(1);
        assertEquals("Error in set/get sortColumn methods", (Integer) 1, instance.getSortColumn());
    }

    /**
     * Accuracy test case for {@link BaseRangeAction#setAscending(boolean)} and {@link BaseRangeAction#isAscending()}
     * methods. Sets true and expects the same.
     */
    public void testSetIsAscending() {
        instance.setAscending(true);
        assertTrue("Error in set/get ascending methods", instance.isAscending());
    }

    /**
     * Accuracy test case for {@link BaseRangeAction#setStartRank(Integer)} and {@link BaseRangeAction#getStartRank()}
     * methods. Sets a value 1 and expects the same.
     */
    public void testGetStartRank() {
        instance.setStartRank(1);
        assertEquals("Error in set/get startRank methods", (Integer) 1, instance.getStartRank());
    }

    /**
     * Accuracy test case for {@link BaseRangeAction#setEndRank(Integer)} and {@link BaseRangeAction#getEndRank()}
     * methods. Sets a value of 1 and expects the same.
     */
    public void testGetEndRank() {
        instance.setEndRank(1);
        assertEquals("Error in set/get endRank methods", (Integer) 1, instance.getEndRank());
    }

    /**
     * Accuracy test case for {@link BaseRangeAction#setDefaultSortColumn(int)} method. Sets a value of 1 and expects
     * the same.
     */
    public void testSetDefaultSortColumn() {
        instance.setDefaultSortColumn(1);

        instance.setDefaultSortColumnValue();
        assertEquals("Error in setDefaultSortColumn method", (Integer) 1, instance.getSortColumn());
    }
}
