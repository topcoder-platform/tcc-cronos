/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.direct.ejb;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.service.facade.direct.DirectServiceFacadeConfigurationException;

/**
 * <p>
 * This test case aggregates failure test cases for
 * {@link DirectServiceFacadeBean#initialize()}.
 * </p>
 * @author vilain
 * @version 1.0
 */
public class DirectServiceFacadeBeanInitializeFailureTests {

    /**
     * Instance of DirectServiceFacadeBean for testing.
     */
    private DirectServiceFacadeBean directServiceFacadeBean;

    /**
     * Setting up environment.
     */
    @Before
    public void setUp() {
        directServiceFacadeBean = new DirectServiceFacadeBean();
    }

    /**
     * Method under test {@link DirectServiceFacadeBean#initialize()}. Failure
     * testing of exception in case the value of field loggerName is empty.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = DirectServiceFacadeConfigurationException.class)
    public final void initializeFailureLoggerNameEmpty() throws Exception {
        FailureTestHelper.setContestServiceFacade(directServiceFacadeBean, new MockContestServiceFacade());
        FailureTestHelper.setUserService(directServiceFacadeBean, new MockUserService());
        FailureTestHelper.setProjectDAO(directServiceFacadeBean, new MockProjectDAO());
        FailureTestHelper.setLoggerName(directServiceFacadeBean, "");
        FailureTestHelper.setProjectManagerClassName(directServiceFacadeBean, MockProjectManager.class
            .getName());
        FailureTestHelper.setProjectLinkManagerClassName(directServiceFacadeBean,
            MockProjectLinkManager.class.getName());
        FailureTestHelper.setPhaseManagerClassName(directServiceFacadeBean, MockPhaseManager.class.getName());
        FailureTestHelper.setReceiptEmailTitleTemplateText(directServiceFacadeBean, "receipt email title");
        FailureTestHelper
            .setReceiptEmailBodyTemplateText(directServiceFacadeBean, "budget update email body");
        FailureTestHelper.setBudgetUpdateEmailTitleTemplateText(directServiceFacadeBean,
            "receipt email title");
        FailureTestHelper.setBudgetUpdateEmailBodyTemplateText(directServiceFacadeBean,
            "budget update email body");
        FailureTestHelper.setEmailSender(directServiceFacadeBean, "test@topcoder.com");
        directServiceFacadeBean.initialize();
    }

    /**
     * Method under test {@link DirectServiceFacadeBean#initialize()}. Failure
     * testing of exception in case the value of field loggerName is empty
     * trimmed.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = DirectServiceFacadeConfigurationException.class)
    public final void initializeFailureLoggerNameEmptyTrimmed() throws Exception {
        FailureTestHelper.setContestServiceFacade(directServiceFacadeBean, new MockContestServiceFacade());
        FailureTestHelper.setUserService(directServiceFacadeBean, new MockUserService());
        FailureTestHelper.setProjectDAO(directServiceFacadeBean, new MockProjectDAO());
        FailureTestHelper.setLoggerName(directServiceFacadeBean, "  ");
        FailureTestHelper.setProjectManagerClassName(directServiceFacadeBean, MockProjectManager.class
            .getName());
        FailureTestHelper.setProjectLinkManagerClassName(directServiceFacadeBean,
            MockProjectLinkManager.class.getName());
        FailureTestHelper.setPhaseManagerClassName(directServiceFacadeBean, MockPhaseManager.class.getName());
        FailureTestHelper.setReceiptEmailTitleTemplateText(directServiceFacadeBean, "receipt email title");
        FailureTestHelper
            .setReceiptEmailBodyTemplateText(directServiceFacadeBean, "budget update email body");
        FailureTestHelper.setBudgetUpdateEmailTitleTemplateText(directServiceFacadeBean,
            "receipt email title");
        FailureTestHelper.setBudgetUpdateEmailBodyTemplateText(directServiceFacadeBean,
            "budget update email body");
        FailureTestHelper.setEmailSender(directServiceFacadeBean, "test@topcoder.com");
        directServiceFacadeBean.initialize();
    }

    /**
     * Method under test {@link DirectServiceFacadeBean#initialize()}. Failure
     * testing of exception in case the value of field projectManagerClassName
     * is null.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = DirectServiceFacadeConfigurationException.class)
    public final void initializeFailureProjectManagerClassNameNull() throws Exception {
        FailureTestHelper.setContestServiceFacade(directServiceFacadeBean, new MockContestServiceFacade());
        FailureTestHelper.setUserService(directServiceFacadeBean, new MockUserService());
        FailureTestHelper.setProjectDAO(directServiceFacadeBean, new MockProjectDAO());
        FailureTestHelper.setLoggerName(directServiceFacadeBean, "logger");
        FailureTestHelper.setProjectLinkManagerClassName(directServiceFacadeBean,
            MockProjectLinkManager.class.getName());
        FailureTestHelper.setPhaseManagerClassName(directServiceFacadeBean, MockPhaseManager.class.getName());
        FailureTestHelper.setReceiptEmailTitleTemplateText(directServiceFacadeBean, "receipt email title");
        FailureTestHelper
            .setReceiptEmailBodyTemplateText(directServiceFacadeBean, "budget update email body");
        FailureTestHelper.setBudgetUpdateEmailTitleTemplateText(directServiceFacadeBean,
            "receipt email title");
        FailureTestHelper.setBudgetUpdateEmailBodyTemplateText(directServiceFacadeBean,
            "budget update email body");
        FailureTestHelper.setEmailSender(directServiceFacadeBean, "test@topcoder.com");
        directServiceFacadeBean.initialize();
    }

    /**
     * Method under test {@link DirectServiceFacadeBean#initialize()}. Failure
     * testing of exception in case the value of field projectManagerClassName
     * is empty.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = DirectServiceFacadeConfigurationException.class)
    public final void initializeFailureProjectManagerClassNameEmpty() throws Exception {
        FailureTestHelper.setContestServiceFacade(directServiceFacadeBean, new MockContestServiceFacade());
        FailureTestHelper.setUserService(directServiceFacadeBean, new MockUserService());
        FailureTestHelper.setProjectDAO(directServiceFacadeBean, new MockProjectDAO());
        FailureTestHelper.setLoggerName(directServiceFacadeBean, "logger");
        FailureTestHelper.setProjectManagerClassName(directServiceFacadeBean, "");
        FailureTestHelper.setProjectLinkManagerClassName(directServiceFacadeBean,
            MockProjectLinkManager.class.getName());
        FailureTestHelper.setPhaseManagerClassName(directServiceFacadeBean, MockPhaseManager.class.getName());
        FailureTestHelper.setReceiptEmailTitleTemplateText(directServiceFacadeBean, "receipt email title");
        FailureTestHelper
            .setReceiptEmailBodyTemplateText(directServiceFacadeBean, "budget update email body");
        FailureTestHelper.setBudgetUpdateEmailTitleTemplateText(directServiceFacadeBean,
            "receipt email title");
        FailureTestHelper.setBudgetUpdateEmailBodyTemplateText(directServiceFacadeBean,
            "budget update email body");
        FailureTestHelper.setEmailSender(directServiceFacadeBean, "test@topcoder.com");
        directServiceFacadeBean.initialize();
    }

    /**
     * Method under test {@link DirectServiceFacadeBean#initialize()}. Failure
     * testing of exception in case the value of field projectManagerClassName
     * is empty trimmed.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = DirectServiceFacadeConfigurationException.class)
    public final void initializeFailureProjectManagerClassNameEmptyTrimmed() throws Exception {
        FailureTestHelper.setContestServiceFacade(directServiceFacadeBean, new MockContestServiceFacade());
        FailureTestHelper.setUserService(directServiceFacadeBean, new MockUserService());
        FailureTestHelper.setProjectDAO(directServiceFacadeBean, new MockProjectDAO());
        FailureTestHelper.setLoggerName(directServiceFacadeBean, "logger");
        FailureTestHelper.setProjectManagerClassName(directServiceFacadeBean, "     ");
        FailureTestHelper.setProjectLinkManagerClassName(directServiceFacadeBean,
            MockProjectLinkManager.class.getName());
        FailureTestHelper.setPhaseManagerClassName(directServiceFacadeBean, MockPhaseManager.class.getName());
        FailureTestHelper.setReceiptEmailTitleTemplateText(directServiceFacadeBean, "receipt email title");
        FailureTestHelper
            .setReceiptEmailBodyTemplateText(directServiceFacadeBean, "budget update email body");
        FailureTestHelper.setBudgetUpdateEmailTitleTemplateText(directServiceFacadeBean,
            "receipt email title");
        FailureTestHelper.setBudgetUpdateEmailBodyTemplateText(directServiceFacadeBean,
            "budget update email body");
        FailureTestHelper.setEmailSender(directServiceFacadeBean, "test@topcoder.com");
        directServiceFacadeBean.initialize();
    }

    /**
     * Method under test {@link DirectServiceFacadeBean#initialize()}. Failure
     * testing of exception in case the value of field projectManagerClassName
     * is null.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = DirectServiceFacadeConfigurationException.class)
    public final void initializeFailureProjectLinkManagerClassNameNull() throws Exception {
        FailureTestHelper.setContestServiceFacade(directServiceFacadeBean, new MockContestServiceFacade());
        FailureTestHelper.setUserService(directServiceFacadeBean, new MockUserService());
        FailureTestHelper.setProjectDAO(directServiceFacadeBean, new MockProjectDAO());
        FailureTestHelper.setLoggerName(directServiceFacadeBean, "logger");
        FailureTestHelper.setProjectManagerClassName(directServiceFacadeBean, MockProjectManager.class
            .getName());
        FailureTestHelper.setPhaseManagerClassName(directServiceFacadeBean, MockPhaseManager.class.getName());
        FailureTestHelper.setReceiptEmailTitleTemplateText(directServiceFacadeBean, "receipt email title");
        FailureTestHelper
            .setReceiptEmailBodyTemplateText(directServiceFacadeBean, "budget update email body");
        FailureTestHelper.setBudgetUpdateEmailTitleTemplateText(directServiceFacadeBean,
            "receipt email title");
        FailureTestHelper.setBudgetUpdateEmailBodyTemplateText(directServiceFacadeBean,
            "budget update email body");
        FailureTestHelper.setEmailSender(directServiceFacadeBean, "test@topcoder.com");
        directServiceFacadeBean.initialize();
    }

    /**
     * Method under test {@link DirectServiceFacadeBean#initialize()}. Failure
     * testing of exception in case the value of field projectManagerClassName
     * is empty.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = DirectServiceFacadeConfigurationException.class)
    public final void initializeFailureProjectLinkManagerClassNameEmpty() throws Exception {
        FailureTestHelper.setContestServiceFacade(directServiceFacadeBean, new MockContestServiceFacade());
        FailureTestHelper.setUserService(directServiceFacadeBean, new MockUserService());
        FailureTestHelper.setProjectDAO(directServiceFacadeBean, new MockProjectDAO());
        FailureTestHelper.setLoggerName(directServiceFacadeBean, "logger");
        FailureTestHelper.setProjectLinkManagerClassName(directServiceFacadeBean, "");
        FailureTestHelper.setProjectManagerClassName(directServiceFacadeBean, MockProjectManager.class
            .getName());
        FailureTestHelper.setPhaseManagerClassName(directServiceFacadeBean, MockPhaseManager.class.getName());
        FailureTestHelper.setReceiptEmailTitleTemplateText(directServiceFacadeBean, "receipt email title");
        FailureTestHelper
            .setReceiptEmailBodyTemplateText(directServiceFacadeBean, "budget update email body");
        FailureTestHelper.setBudgetUpdateEmailTitleTemplateText(directServiceFacadeBean,
            "receipt email title");
        FailureTestHelper.setBudgetUpdateEmailBodyTemplateText(directServiceFacadeBean,
            "budget update email body");
        FailureTestHelper.setEmailSender(directServiceFacadeBean, "test@topcoder.com");
        directServiceFacadeBean.initialize();
    }

    /**
     * Method under test {@link DirectServiceFacadeBean#initialize()}. Failure
     * testing of exception in case the value of field projectManagerClassName
     * is empty trimmed.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = DirectServiceFacadeConfigurationException.class)
    public final void initializeFailureProjectLinkManagerClassNameEmptyTrimmed() throws Exception {
        FailureTestHelper.setContestServiceFacade(directServiceFacadeBean, new MockContestServiceFacade());
        FailureTestHelper.setUserService(directServiceFacadeBean, new MockUserService());
        FailureTestHelper.setProjectDAO(directServiceFacadeBean, new MockProjectDAO());
        FailureTestHelper.setLoggerName(directServiceFacadeBean, "logger");
        FailureTestHelper.setProjectLinkManagerClassName(directServiceFacadeBean, "     ");
        FailureTestHelper.setProjectManagerClassName(directServiceFacadeBean, MockProjectManager.class
            .getName());
        FailureTestHelper.setPhaseManagerClassName(directServiceFacadeBean, MockPhaseManager.class.getName());
        FailureTestHelper.setReceiptEmailTitleTemplateText(directServiceFacadeBean, "receipt email title");
        FailureTestHelper
            .setReceiptEmailBodyTemplateText(directServiceFacadeBean, "budget update email body");
        FailureTestHelper.setBudgetUpdateEmailTitleTemplateText(directServiceFacadeBean,
            "receipt email title");
        FailureTestHelper.setBudgetUpdateEmailBodyTemplateText(directServiceFacadeBean,
            "budget update email body");
        FailureTestHelper.setEmailSender(directServiceFacadeBean, "test@topcoder.com");
        directServiceFacadeBean.initialize();
    }

    /**
     * Method under test {@link DirectServiceFacadeBean#initialize()}. Failure
     * testing of exception in case the value of field projectManagerClassName
     * is null.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = DirectServiceFacadeConfigurationException.class)
    public final void initializeFailurePhaseManagerClassNameNull() throws Exception {
        FailureTestHelper.setContestServiceFacade(directServiceFacadeBean, new MockContestServiceFacade());
        FailureTestHelper.setUserService(directServiceFacadeBean, new MockUserService());
        FailureTestHelper.setProjectDAO(directServiceFacadeBean, new MockProjectDAO());
        FailureTestHelper.setLoggerName(directServiceFacadeBean, "logger");
        FailureTestHelper.setProjectLinkManagerClassName(directServiceFacadeBean,
            MockProjectLinkManager.class.getName());
        FailureTestHelper.setProjectManagerClassName(directServiceFacadeBean, MockProjectManager.class
            .getName());
        FailureTestHelper.setReceiptEmailTitleTemplateText(directServiceFacadeBean, "receipt email title");
        FailureTestHelper
            .setReceiptEmailBodyTemplateText(directServiceFacadeBean, "budget update email body");
        FailureTestHelper.setBudgetUpdateEmailTitleTemplateText(directServiceFacadeBean,
            "receipt email title");
        FailureTestHelper.setBudgetUpdateEmailBodyTemplateText(directServiceFacadeBean,
            "budget update email body");
        FailureTestHelper.setEmailSender(directServiceFacadeBean, "test@topcoder.com");
        directServiceFacadeBean.initialize();
    }

    /**
     * Method under test {@link DirectServiceFacadeBean#initialize()}. Failure
     * testing of exception in case the value of field projectManagerClassName
     * is empty.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = DirectServiceFacadeConfigurationException.class)
    public final void initializeFailurePhaseManagerClassNameEmpty() throws Exception {
        FailureTestHelper.setContestServiceFacade(directServiceFacadeBean, new MockContestServiceFacade());
        FailureTestHelper.setUserService(directServiceFacadeBean, new MockUserService());
        FailureTestHelper.setProjectDAO(directServiceFacadeBean, new MockProjectDAO());
        FailureTestHelper.setLoggerName(directServiceFacadeBean, "logger");
        FailureTestHelper.setPhaseManagerClassName(directServiceFacadeBean, "");
        FailureTestHelper.setProjectLinkManagerClassName(directServiceFacadeBean,
            MockProjectLinkManager.class.getName());
        FailureTestHelper.setProjectManagerClassName(directServiceFacadeBean, MockProjectManager.class
            .getName());
        FailureTestHelper.setReceiptEmailTitleTemplateText(directServiceFacadeBean, "receipt email title");
        FailureTestHelper
            .setReceiptEmailBodyTemplateText(directServiceFacadeBean, "budget update email body");
        FailureTestHelper.setBudgetUpdateEmailTitleTemplateText(directServiceFacadeBean,
            "receipt email title");
        FailureTestHelper.setBudgetUpdateEmailBodyTemplateText(directServiceFacadeBean,
            "budget update email body");
        FailureTestHelper.setEmailSender(directServiceFacadeBean, "test@topcoder.com");
        directServiceFacadeBean.initialize();
    }

    /**
     * Method under test {@link DirectServiceFacadeBean#initialize()}. Failure
     * testing of exception in case the value of field projectManagerClassName
     * is empty trimmed.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = DirectServiceFacadeConfigurationException.class)
    public final void initializeFailurePhaseManagerClassNameEmptyTrimmed() throws Exception {
        FailureTestHelper.setContestServiceFacade(directServiceFacadeBean, new MockContestServiceFacade());
        FailureTestHelper.setUserService(directServiceFacadeBean, new MockUserService());
        FailureTestHelper.setProjectDAO(directServiceFacadeBean, new MockProjectDAO());
        FailureTestHelper.setLoggerName(directServiceFacadeBean, "logger");
        FailureTestHelper.setPhaseManagerClassName(directServiceFacadeBean, "     ");
        FailureTestHelper.setProjectLinkManagerClassName(directServiceFacadeBean,
            MockProjectLinkManager.class.getName());
        FailureTestHelper.setProjectManagerClassName(directServiceFacadeBean, MockProjectManager.class
            .getName());
        FailureTestHelper.setReceiptEmailTitleTemplateText(directServiceFacadeBean, "receipt email title");
        FailureTestHelper
            .setReceiptEmailBodyTemplateText(directServiceFacadeBean, "budget update email body");
        FailureTestHelper.setBudgetUpdateEmailTitleTemplateText(directServiceFacadeBean,
            "receipt email title");
        FailureTestHelper.setBudgetUpdateEmailBodyTemplateText(directServiceFacadeBean,
            "budget update email body");
        FailureTestHelper.setEmailSender(directServiceFacadeBean, "test@topcoder.com");
        directServiceFacadeBean.initialize();
    }

    /**
     * Method under test {@link DirectServiceFacadeBean#initialize()}. Failure
     * testing of exception in case the value of field
     * receiptEmailTitleTemplateText is null.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = DirectServiceFacadeConfigurationException.class)
    public final void initializeFailureReceiptEmailTitleTemplateTextNull() throws Exception {
        FailureTestHelper.setContestServiceFacade(directServiceFacadeBean, new MockContestServiceFacade());
        FailureTestHelper.setUserService(directServiceFacadeBean, new MockUserService());
        FailureTestHelper.setProjectDAO(directServiceFacadeBean, new MockProjectDAO());
        FailureTestHelper.setLoggerName(directServiceFacadeBean, "logger");
        FailureTestHelper.setProjectManagerClassName(directServiceFacadeBean, MockProjectManager.class
            .getName());
        FailureTestHelper.setProjectLinkManagerClassName(directServiceFacadeBean,
            MockProjectLinkManager.class.getName());
        FailureTestHelper.setPhaseManagerClassName(directServiceFacadeBean, MockPhaseManager.class.getName());
        FailureTestHelper
            .setReceiptEmailBodyTemplateText(directServiceFacadeBean, "budget update email body");
        FailureTestHelper.setBudgetUpdateEmailTitleTemplateText(directServiceFacadeBean,
            "receipt email title");
        FailureTestHelper.setBudgetUpdateEmailBodyTemplateText(directServiceFacadeBean,
            "budget update email body");
        FailureTestHelper.setEmailSender(directServiceFacadeBean, "test@topcoder.com");
        directServiceFacadeBean.initialize();
    }

    /**
     * Method under test {@link DirectServiceFacadeBean#initialize()}. Failure
     * testing of exception in case the value of field
     * receiptEmailBodyTemplateText is null.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = DirectServiceFacadeConfigurationException.class)
    public final void initializeFailureReceiptEmailBodyTemplateTextNull() throws Exception {
        FailureTestHelper.setContestServiceFacade(directServiceFacadeBean, new MockContestServiceFacade());
        FailureTestHelper.setUserService(directServiceFacadeBean, new MockUserService());
        FailureTestHelper.setProjectDAO(directServiceFacadeBean, new MockProjectDAO());
        FailureTestHelper.setLoggerName(directServiceFacadeBean, "logger");
        FailureTestHelper.setProjectManagerClassName(directServiceFacadeBean, MockProjectManager.class
            .getName());
        FailureTestHelper.setProjectLinkManagerClassName(directServiceFacadeBean,
            MockProjectLinkManager.class.getName());
        FailureTestHelper.setPhaseManagerClassName(directServiceFacadeBean, MockPhaseManager.class.getName());
        FailureTestHelper.setReceiptEmailTitleTemplateText(directServiceFacadeBean, "receipt email title");
        FailureTestHelper.setBudgetUpdateEmailTitleTemplateText(directServiceFacadeBean,
            "receipt email title");
        FailureTestHelper.setBudgetUpdateEmailBodyTemplateText(directServiceFacadeBean,
            "budget update email body");
        FailureTestHelper.setEmailSender(directServiceFacadeBean, "test@topcoder.com");
        directServiceFacadeBean.initialize();
    }

    /**
     * Method under test {@link DirectServiceFacadeBean#initialize()}. Failure
     * testing of exception in case the value of field
     * budgetUpdateEmailTitleTemplateText is null.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = DirectServiceFacadeConfigurationException.class)
    public final void initializeFailureBudgetUpdateEmailTitleTemplateTextNull() throws Exception {
        FailureTestHelper.setContestServiceFacade(directServiceFacadeBean, new MockContestServiceFacade());
        FailureTestHelper.setUserService(directServiceFacadeBean, new MockUserService());
        FailureTestHelper.setProjectDAO(directServiceFacadeBean, new MockProjectDAO());
        FailureTestHelper.setLoggerName(directServiceFacadeBean, "logger");
        FailureTestHelper.setProjectManagerClassName(directServiceFacadeBean, MockProjectManager.class
            .getName());
        FailureTestHelper.setProjectLinkManagerClassName(directServiceFacadeBean,
            MockProjectLinkManager.class.getName());
        FailureTestHelper.setPhaseManagerClassName(directServiceFacadeBean, MockPhaseManager.class.getName());
        FailureTestHelper.setReceiptEmailTitleTemplateText(directServiceFacadeBean, "receipt email title");
        FailureTestHelper.setReceiptEmailBodyTemplateText(directServiceFacadeBean, "receipt email body");
        FailureTestHelper.setBudgetUpdateEmailBodyTemplateText(directServiceFacadeBean,
            "budget update email body");
        FailureTestHelper.setEmailSender(directServiceFacadeBean, "test@topcoder.com");
        directServiceFacadeBean.initialize();
    }

    /**
     * Method under test {@link DirectServiceFacadeBean#initialize()}. Failure
     * testing of exception in case the value of field
     * budgetUpdateEmailBodyTemplateText is null.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = DirectServiceFacadeConfigurationException.class)
    public final void initializeFailureBudgetUpdateEmailBodyTemplateTextNull() throws Exception {
        FailureTestHelper.setContestServiceFacade(directServiceFacadeBean, new MockContestServiceFacade());
        FailureTestHelper.setUserService(directServiceFacadeBean, new MockUserService());
        FailureTestHelper.setProjectDAO(directServiceFacadeBean, new MockProjectDAO());
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
        FailureTestHelper.setEmailSender(directServiceFacadeBean, "test@topcoder.com");
        directServiceFacadeBean.initialize();
    }

    /**
     * Method under test {@link DirectServiceFacadeBean#initialize()}. Failure
     * testing of exception in case the value of field emailSender is null.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = DirectServiceFacadeConfigurationException.class)
    public final void initializeFailureEmailSenderNull() throws Exception {
        FailureTestHelper.setContestServiceFacade(directServiceFacadeBean, new MockContestServiceFacade());
        FailureTestHelper.setUserService(directServiceFacadeBean, new MockUserService());
        FailureTestHelper.setProjectDAO(directServiceFacadeBean, new MockProjectDAO());
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
        directServiceFacadeBean.initialize();
    }

    /**
     * Method under test {@link DirectServiceFacadeBean#initialize()}. Failure
     * testing of exception in case the value of field emailSender is empty.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = DirectServiceFacadeConfigurationException.class)
    public final void initializeFailureEmailSenderEmpty() throws Exception {
        FailureTestHelper.setContestServiceFacade(directServiceFacadeBean, new MockContestServiceFacade());
        FailureTestHelper.setUserService(directServiceFacadeBean, new MockUserService());
        FailureTestHelper.setProjectDAO(directServiceFacadeBean, new MockProjectDAO());
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
        FailureTestHelper.setEmailSender(directServiceFacadeBean, "");
        directServiceFacadeBean.initialize();
    }

    /**
     * Method under test {@link DirectServiceFacadeBean#initialize()}. Failure
     * testing of exception in case the value of field emailSender is empty
     * trimmed.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = DirectServiceFacadeConfigurationException.class)
    public final void initializeFailureEmailSenderEmptyTrimmed() throws Exception {
        FailureTestHelper.setContestServiceFacade(directServiceFacadeBean, new MockContestServiceFacade());
        FailureTestHelper.setUserService(directServiceFacadeBean, new MockUserService());
        FailureTestHelper.setProjectDAO(directServiceFacadeBean, new MockProjectDAO());
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
        FailureTestHelper.setEmailSender(directServiceFacadeBean, "     ");
        directServiceFacadeBean.initialize();
    }
}