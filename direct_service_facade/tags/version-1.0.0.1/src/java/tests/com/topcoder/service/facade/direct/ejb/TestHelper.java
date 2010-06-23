/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.direct.ejb;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Helper class for unit test.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class TestHelper {

    /**
     * <p>
     * Represents the receipt email body template text for testing.
     * </p>
     */
    private static final String RECEIPT_EMAIL_BODY_TEMPLATE_TEXT = "Project Name %projectName%"
            + "Contest Fee %contestFee% Start Date %startDate% Prizes %prizes% Total Cost %contestTotalCost%"
            + "See details here %contestId%";

    /**
     * <p>
     * Represents the budget update email body template text for testing.
     * </p>
     */
    private static final String BUDGET_UPDATE_EMAIL_BODY_TEMPLATE_TEXT = "Project Name %billingProjectName%"
            + "Old Budget Amount %oldBudget% New Budget Amount %newBudget%";

    /**
     * <p>
     * This private constructor prevents the creation of a new instance.
     * </p>
     */
    private TestHelper() {
        // empty
    }

    /**
     * <p>
     * Converts specified <code>Date</code> instance into <code>XMLGregorianCalendar</code> instance.
     * </p>
     *
     * @param date
     *            a <code>Date</code> representing the date to be converted.
     * @return a <code>XMLGregorianCalendar</code> providing the converted value of specified date or
     *         <code>null</code> if specified <code>date</code> is <code>null</code> or if it can't be converted to
     *         calendar.
     */
    static XMLGregorianCalendar getXMLGregorianCalendar(Date date) {
        if (date == null) {
            return null;
        }

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);

        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException ex) {
            return null;
        }
    }

    /**
     * <p>
     * Gets direct service facade bean for test.
     * </p>
     *
     * @return the direct service facade bean
     */
    static DirectServiceFacadeBean geDirectServiceFacadeBean() {
        DirectServiceFacadeBean bean = new DirectServiceFacadeBean();

        TestHelper.setPrivateField(bean, "loggerName", "direct_service_facade_log");
        TestHelper.setPrivateField(bean, "projectManagerClassName", MockProjectManager.class.getName());
        TestHelper.setPrivateField(bean, "projectLinkManagerClassName", MockProjectLinkManager.class.getName());
        TestHelper.setPrivateField(bean, "phaseManagerClassName", MockPhaseManager.class.getName());
        TestHelper.setPrivateField(bean, "receiptEmailTitleTemplateText", "Receipt for contest \"%contestName%\"");
        TestHelper.setPrivateField(bean, "receiptEmailBodyTemplateText", RECEIPT_EMAIL_BODY_TEMPLATE_TEXT);
        TestHelper.setPrivateField(bean, "budgetUpdateEmailTitleTemplateText", "Budget Updated");
        TestHelper.setPrivateField(bean, "budgetUpdateEmailBodyTemplateText", BUDGET_UPDATE_EMAIL_BODY_TEMPLATE_TEXT);
        TestHelper.setPrivateField(bean, "emailSender", "test@mydomain.com");

        TestHelper.setPrivateField(bean, "contestServiceFacade", new MockContestServiceFacade());
        TestHelper.setPrivateField(bean, "userService", new MockUserService());
        TestHelper.setPrivateField(bean, "projectDAO", new MockProjectDAO());

        bean.initialize();

        return bean;
    }

    /**
     * <p>
     * Gets the value of a private field in the given instance by given name.
     * </p>
     *
     * @param instance
     *            the instance which the private field belongs to.
     * @param name
     *            the name of the private field to be retrieved.
     * @return the value of the private field.
     */
    static Object getPrivateField(Object instance, String name) {
        Field field = null;
        Object obj = null;

        try {
            field = instance.getClass().getDeclaredField(name);

            // Set the field accessible.
            field.setAccessible(true);

            // Get the value
            obj = field.get(instance);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } finally {
            if (field != null) {
                // reset the accessibility
                field.setAccessible(false);
            }
        }

        return obj;
    }

    /**
     * <p>
     * Sets the value of a private field in the given instance.
     * </p>
     *
     * @param instance
     *            the instance which the private field belongs to
     * @param name
     *            the name of the private field to be set
     * @param value
     *            the value to set
     */
    static void setPrivateField(Object instance, String name, Object value) {
        Field field = null;

        try {
            // get the reflection of the field
            field = instance.getClass().getDeclaredField(name);

            // set the field accessible
            field.setAccessible(true);

            // set the value
            field.set(instance, value);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } finally {
            if (field != null) {
                // reset the accessibility
                field.setAccessible(false);
            }
        }
    }
}
