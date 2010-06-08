/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.direct.ejb;

import java.lang.reflect.Field;

import com.topcoder.clients.dao.ProjectDAO;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.user.UserService;

/**
 * <p>
 * Utils class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FailureTestHelper {

    /**
     * Default constructor.
     */
    private FailureTestHelper() {
    }

    /**
     * Initializes direct service facade bean.
     * @param directServiceFacadeBean instance of DirectServiceFacadeBean
     * @param contestService whether contest service should be initialized
     * @param userService whether user service should be initialized
     * @param projectDAO whether project DAO should be initialized
     * @throws Exception wraps all exceptions
     */
    public static final void initializeDirectServiceFacadeBean(
        DirectServiceFacadeBean directServiceFacadeBean, boolean contestService, boolean userService,
        boolean projectDAO) throws Exception {
        if (contestService) {
            FailureTestHelper
                .setContestServiceFacade(directServiceFacadeBean, new MockContestServiceFacade());
        }
        if (userService) {
            FailureTestHelper.setUserService(directServiceFacadeBean, new MockUserService());
        }
        if (projectDAO) {
            FailureTestHelper.setProjectDAO(directServiceFacadeBean, new MockProjectDAO());
        }
        FailureTestHelper.setLoggerName(directServiceFacadeBean, "logger");
        FailureTestHelper.setProjectManagerClassName(directServiceFacadeBean, MockProjectManager.class
            .getName());
        FailureTestHelper.setProjectLinkManagerClassName(directServiceFacadeBean,
            MockProjectLinkManager.class.getName());
        FailureTestHelper.setPhaseManagerClassName(directServiceFacadeBean, MockPhaseManager.class.getName());
        FailureTestHelper.setReceiptEmailTitleTemplateText(directServiceFacadeBean, "receipt email title");
        FailureTestHelper.setReceiptEmailBodyTemplateText(directServiceFacadeBean, "receipt email body");
        FailureTestHelper.setBudgetUpdateEmailTitleTemplateText(directServiceFacadeBean,
            "budget update email title");
        FailureTestHelper.setBudgetUpdateEmailBodyTemplateText(directServiceFacadeBean,
            "budget update email body");
        FailureTestHelper.setEmailSender(directServiceFacadeBean, "test@topcoder.com");
        directServiceFacadeBean.initialize();
    }

    /**
     * Sets contest service facade.
     * @param directServiceFacadeBean instance of DirectServiceFacadeBean
     * @param contestServiceFacade contest service facade
     * @throws Exception wraps all exceptions
     */
    public static final void setContestServiceFacade(DirectServiceFacadeBean directServiceFacadeBean,
        ContestServiceFacade contestServiceFacade) throws Exception {
        setFieldValue(directServiceFacadeBean, "contestServiceFacade", contestServiceFacade);
    }

    /**
     * Sets user service.
     * @param directServiceFacadeBean instance of DirectServiceFacadeBean
     * @param userService user facade
     * @throws Exception wraps all exceptions
     */
    public static final void setUserService(DirectServiceFacadeBean directServiceFacadeBean,
        UserService userService) throws Exception {
        setFieldValue(directServiceFacadeBean, "userService", userService);
    }

    /**
     * Sets project DAO.
     * @param directServiceFacadeBean instance of DirectServiceFacadeBean
     * @param projectDAO project DAO
     * @throws Exception wraps all exceptions
     */
    public static final void setProjectDAO(DirectServiceFacadeBean directServiceFacadeBean,
        ProjectDAO projectDAO) throws Exception {
        setFieldValue(directServiceFacadeBean, "projectDAO", projectDAO);
    }

    /**
     * Sets logger name.
     * @param directServiceFacadeBean instance of DirectServiceFacadeBean
     * @param loggerName logger name
     * @throws Exception wraps all exceptions
     */
    public static final void setLoggerName(DirectServiceFacadeBean directServiceFacadeBean, String loggerName)
        throws Exception {
        setFieldValue(directServiceFacadeBean, "loggerName", loggerName);
    }

    /**
     * Sets project manager class name.
     * @param directServiceFacadeBean instance of DirectServiceFacadeBean
     * @param projectManagerClassName project manager class name
     * @throws Exception wraps all exceptions
     */
    public static final void setProjectManagerClassName(DirectServiceFacadeBean directServiceFacadeBean,
        String projectManagerClassName) throws Exception {
        setFieldValue(directServiceFacadeBean, "projectManagerClassName", projectManagerClassName);
    }

    /**
     * Sets project manager namespace.
     * @param directServiceFacadeBean instance of DirectServiceFacadeBean
     * @param projectManagerNamespace project manager namespace
     * @throws Exception wraps all exceptions
     */
    public static final void setProjectManagerNamespace(DirectServiceFacadeBean directServiceFacadeBean,
        String projectManagerNamespace) throws Exception {
        setFieldValue(directServiceFacadeBean, "projectManagerNamespace", projectManagerNamespace);
    }

    /**
     * Sets project link manager class name.
     * @param directServiceFacadeBean instance of DirectServiceFacadeBean
     * @param projectLinkManagerClassName project link manager class name
     * @throws Exception wraps all exceptions
     */
    public static final void setProjectLinkManagerClassName(DirectServiceFacadeBean directServiceFacadeBean,
        String projectLinkManagerClassName) throws Exception {
        setFieldValue(directServiceFacadeBean, "projectLinkManagerClassName", projectLinkManagerClassName);
    }

    /**
     * Sets project link manager namespace.
     * @param directServiceFacadeBean instance of DirectServiceFacadeBean
     * @param projectLinkManagerNamespace project link manager namespace
     * @throws Exception wraps all exceptions
     */
    public static final void setProjectLinkManagerNamespace(DirectServiceFacadeBean directServiceFacadeBean,
        String projectLinkManagerNamespace) throws Exception {
        setFieldValue(directServiceFacadeBean, "projectLinkManagerNamespace", projectLinkManagerNamespace);
    }

    /**
     * Sets project link manager class name.
     * @param directServiceFacadeBean instance of DirectServiceFacadeBean
     * @param phaseManagerClassName project link manager class name
     * @throws Exception wraps all exceptions
     */
    public static final void setPhaseManagerClassName(DirectServiceFacadeBean directServiceFacadeBean,
        String phaseManagerClassName) throws Exception {
        setFieldValue(directServiceFacadeBean, "phaseManagerClassName", phaseManagerClassName);
    }

    /**
     * Sets project link manager namespace.
     * @param directServiceFacadeBean instance of DirectServiceFacadeBean
     * @param phaseManagerNamespace project link manager namespace
     * @throws Exception wraps all exceptions
     */
    public static final void setPhaseManagerNamespace(DirectServiceFacadeBean directServiceFacadeBean,
        String phaseManagerNamespace) throws Exception {
        setFieldValue(directServiceFacadeBean, "phaseManagerNamespace", phaseManagerNamespace);
    }

    /**
     * Sets receipt email title template text.
     * @param directServiceFacadeBean instance of DirectServiceFacadeBean
     * @param receiptEmailTitleTemplateText receipt email title template text
     * @throws Exception wraps all exceptions
     */
    public static final void setReceiptEmailTitleTemplateText(
        DirectServiceFacadeBean directServiceFacadeBean, String receiptEmailTitleTemplateText)
        throws Exception {
        setFieldValue(directServiceFacadeBean, "receiptEmailTitleTemplateText", receiptEmailTitleTemplateText);
    }

    /**
     * Sets receipt email body template text.
     * @param directServiceFacadeBean instance of DirectServiceFacadeBean
     * @param receiptEmailBodyTemplateText receipt email body template text
     * @throws Exception wraps all exceptions
     */
    public static final void setReceiptEmailBodyTemplateText(DirectServiceFacadeBean directServiceFacadeBean,
        String receiptEmailBodyTemplateText) throws Exception {
        setFieldValue(directServiceFacadeBean, "receiptEmailBodyTemplateText", receiptEmailBodyTemplateText);
    }

    /**
     * Sets budgetUpdate email title template text.
     * @param directServiceFacadeBean instance of DirectServiceFacadeBean
     * @param budgetUpdateEmailTitleTemplateText budgetUpdate email title
     *            template text
     * @throws Exception wraps all exceptions
     */
    public static final void setBudgetUpdateEmailTitleTemplateText(
        DirectServiceFacadeBean directServiceFacadeBean, String budgetUpdateEmailTitleTemplateText)
        throws Exception {
        setFieldValue(directServiceFacadeBean, "budgetUpdateEmailTitleTemplateText",
            budgetUpdateEmailTitleTemplateText);
    }

    /**
     * Sets budgetUpdate email title template text.
     * @param directServiceFacadeBean instance of DirectServiceFacadeBean
     * @param budgetUpdateEmailBodyTemplateText budgetUpdate email title
     *            template text
     * @throws Exception wraps all exceptions
     */
    public static final void setBudgetUpdateEmailBodyTemplateText(
        DirectServiceFacadeBean directServiceFacadeBean, String budgetUpdateEmailBodyTemplateText)
        throws Exception {
        setFieldValue(directServiceFacadeBean, "budgetUpdateEmailBodyTemplateText",
            budgetUpdateEmailBodyTemplateText);
    }

    /**
     * Sets email sender .
     * @param directServiceFacadeBean instance of DirectServiceFacadeBean
     * @param emailSender email sender
     * @throws Exception wraps all exceptions
     */
    public static final void setEmailSender(DirectServiceFacadeBean directServiceFacadeBean,
        String emailSender) throws Exception {
        setFieldValue(directServiceFacadeBean, "emailSender", emailSender);
    }

    /**
     * Setting the value of the object's field.
     * @param object provided object
     * @param fieldName the name of the field
     * @param fieldValue the value of the field
     * @throws Exception wraps all exceptions
     */
    public static final void setFieldValue(Object object, String fieldName, Object fieldValue)
        throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, fieldValue);
    }
}