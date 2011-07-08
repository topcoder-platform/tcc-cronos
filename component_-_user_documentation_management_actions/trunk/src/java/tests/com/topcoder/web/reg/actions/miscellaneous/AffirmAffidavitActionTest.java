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
import com.topcoder.web.common.HibernateUtils;

/**
 * Unit test cases for {@link AffirmAffidavitAction} class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AffirmAffidavitActionTest extends TestCase {

    /**
     * Just a string representing a valid Date.
     */
    private static final String BIRTHDAY = "2010/10/10";

    /**
     * An instance of AffirmAffidavitInstance class used for testing.
     */
    private AffirmAffidavitAction instance;

    /**
     * Sets up the test environment.
     */
    public void setUp() {
        instance = new AffirmAffidavitAction();
    }

    /**
     * Tears down the test environment.
     */
    public void tearDown() {
        instance = null;
    }

    /**
     * Accuracy and failure test case for {@link AffirmAffidavitAction#validate()} method.
     */
    public void testValidateFail() {
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

        instance.setBirthday(BIRTHDAY);
        // no errors should be present as birthday is a valid string
        instance.validate();
        assertEquals("Error in validate method", 0, instance.getFieldErrors().size());

        instance.setBirthday("123456");
        instance.validate();
        assertEquals("Error in validate method", 1, instance.getFieldErrors().size());
    }

    /**
     * Accuracy test case for {@link AffirmAffidavitAction#checkConfiguration()} method.
     */
    public void testCheckConfiguration() {
        instance = (AffirmAffidavitAction) TestHelper.setFieldsInBaseAction(instance);
        instance.setDateFormatString("string");

        instance.checkConfiguration();
    }
    
    /**
     * Failure test case for {@link AffirmAffidavitAction#checkConfiguration()} method.
     * Sets an invalid dateFormatString and expects an exception.
     */
    public void testCheckConfigurationFail() {
        instance = (AffirmAffidavitAction) TestHelper.setFieldsInBaseAction(instance);
        instance.setDateFormatString("");
        try {
            instance.checkConfiguration();
            fail("Exception should be thrown");
        } catch (UserDocumentationManagementActionsConfigurationException e) {
            // pass
        }
    }

    /**
     * Accuracy test case for {@link AffirmAffidavitAction#AffirmAffidavitAction()} constructor.
     */
    public void testAffirmAffidavitAction() {
        assertNotNull("Error instantiating the class", instance);
    }

    /**
     * Accuracy test case for {@link AffirmAffidavitAction#execute()} method.
     * @throws Exception
     *             to JUnit
     */
    public void testExecute() throws Exception {
        instance = (AffirmAffidavitAction) TestHelper.setFieldsInBaseAction(instance);
        HibernateUtils.begin();

        instance.setBirthday(BIRTHDAY);
        assertTrue("Error executing the action", "success".equals(instance.execute()));

        TestHelper.closeHibernateSession();
        TestHelper.clearDatabase();
    }

    /**
     * Failure test case for {@link AffirmAffidavitAction#execute()} method. Simulates RemoteException to be thrown from
     * DataInterfaceBean
     * @throws Exception
     *             to JUnit
     */
    public void testExecuteFail1() throws Exception {
        instance = (AffirmAffidavitAction) TestHelper.setFieldsInBaseAction(instance);
        HibernateUtils.begin();

        instance.setBirthday(BIRTHDAY);
        instance.setAffidavitId(1);
        try {
            instance.execute();
            fail("Exception should be thrown");
        } catch (UserDocumentationManagementActionsException e) {
            // pass
        }

        TestHelper.closeHibernateSession();
        TestHelper.clearDatabase();
    }

    /**
     * Failure test case for {@link AffirmAffidavitAction#execute()} method. Simulates NoObjectFoundException to be
     * thrown from DataInterfaceBean
     * @throws Exception
     *             to JUnit
     */
    public void testExecuteFail2() throws Exception {
        instance = (AffirmAffidavitAction) TestHelper.setFieldsInBaseAction(instance);
        HibernateUtils.begin();

        instance.setBirthday(BIRTHDAY);
        instance.setAffidavitId(2);
        try {
            instance.execute();
            fail("Exception should be thrown");
        } catch (UserDocumentationManagementActionsException e) {
            // pass
        }

        TestHelper.closeHibernateSession();
        TestHelper.clearDatabase();
    }

    /**
     * Failure test case for {@link AffirmAffidavitAction#execute()} method. Simulates IllegalUpdateException to be
     * thrown from DataInterfaceBean
     * @throws Exception
     *             to JUnit
     */
    public void testExecuteFail3() throws Exception {
        instance = (AffirmAffidavitAction) TestHelper.setFieldsInBaseAction(instance);
        HibernateUtils.begin();

        instance.setBirthday(BIRTHDAY);
        instance.setAffidavitId(3);
        try {
            instance.execute();
            fail("Exception should be thrown");
        } catch (UserDocumentationManagementActionsException e) {
            // pass
        }

        TestHelper.closeHibernateSession();
        TestHelper.clearDatabase();
    }

    /**
     * Failure test case for {@link AffirmAffidavitAction#execute()} method. Simulates SQLException to be thrown from
     * DataInterfaceBean
     * @throws Exception
     *             to JUnit
     */
    public void testExecuteFail4() throws Exception {
        instance = (AffirmAffidavitAction) TestHelper.setFieldsInBaseAction(instance);
        HibernateUtils.begin();

        instance.setBirthday(BIRTHDAY);
        instance.setAffidavitId(4);
        try {
            instance.execute();
            fail("Exception should be thrown");
        } catch (UserDocumentationManagementActionsException e) {
            // pass
        }

        TestHelper.closeHibernateSession();
        TestHelper.clearDatabase();
    }

    /**
     * Accuracy test case for {@link AffirmAffidavitAction#setAffidavitId(long)} and
     * {@link AffirmAffidavitAction#getAffidavitId()} methods. Sets a value 1 and expects the same.
     */
    public void testGetSetAffidavitId() {
        instance.setAffidavitId(1);
        assertEquals("Error in set/get affidavitId", 1, instance.getAffidavitId());
    }

    /**
     * Accuracy test case for {@link AffirmAffidavitAction#setBirthday(String)} and
     * {@link AffirmAffidavitAction#getBirthday()} methods. Sets a value and expects the same.
     */
    public void testGetSetBirthday() {
        instance.setBirthday(BIRTHDAY);
        assertTrue("Error in set/get birthday", BIRTHDAY.equals(instance.getBirthday()));
    }
}
