/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases;

import java.util.ArrayList;
import java.util.Date;

import com.topcoder.clientcockpit.phases.TestHelper.CockpitPhaseType;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestManager;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Failure tests for <code>CompletedPhaseHandler</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CompletedPhaseHandlerTestExp extends BaseTestCase {

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "bean_name" property is missing, <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_Missing_bean_name() throws Exception {
        this.removeProperty(TestHelper.DEFAULT_NAMESPACE, "bean_name");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "bean_name", "beanName");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "bean_name" property has empty value, <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_EmptyValue_bean_name() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "bean_name", " ");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "bean_name", "beanName");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "bean_name" property has multiple values, <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_MultipleValues_bean_name() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "bean_name", new String[]{"1", "2"});
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "bean_name", "beanName");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "bean_name" property points to a null value under JNDI,
     * <code>PhaseHandlingException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_Invalid_bean_name_1() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "bean_name", "NullBean");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlingException is expected");
        } catch (PhaseHandlingException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "bean_name", "beanName");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "bean_name" property points to an object not type of <code>ContestManager</code> under JNDI,
     * <code>PhaseHandlingException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_Invalid_bean_name_2() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "bean_name", "WrongTypeBean");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlingException is expected");
        } catch (PhaseHandlingException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "bean_name", "beanName");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "bean_name" property causes JNDI throw error while looking up,
     * <code>PhaseHandlingException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_Invalid_bean_name_3() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "bean_name", "ExceptionBean");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlingException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "bean_name", "beanName");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "use_cache" property has empty value, <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_EmptyValue_use_cache() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "use_cache", " ");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "use_cache", "true");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "use_cache" property has multiple values, <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_MultipleValues_use_cache() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "use_cache", new String[]{"1", "2"});
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "use_cache", "true");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "send_start_phase_email" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_EmptyValue_send_start_phase_email() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "send_start_phase_email", " ");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "send_start_phase_email", "true");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "send_start_phase_email" property has multiple values,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_MultipleValues_send_start_phase_email() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "send_start_phase_email", new String[]{"1", "2"});
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "send_start_phase_email", "true");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "start_email_template_source" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_EmptyValue_start_email_template_source() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "start_email_template_source", " ");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "start_email_template_source",
                "startTemplateSource");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "start_email_template_source" property has multiple values,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_MultipleValues_start_email_template_source() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "start_email_template_source", new String[]{"1", "2"});
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "start_email_template_source",
                "startTemplateSource");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "start_email_template_name" property is missing,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_Missing_start_email_template_name() throws Exception {
        this.removeProperty(TestHelper.DEFAULT_NAMESPACE, "start_email_template_name");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "start_email_template_name",
                "startTemplateName");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "start_email_template_name" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_EmptyValue_start_email_template_name() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "start_email_template_name", " ");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "start_email_template_name",
                "startTemplateName");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "start_email_template_name" property has multiple values,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_MultipleValues_start_email_template_name() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "start_email_template_name", new String[]{"1", "2"});
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "start_email_template_name",
                "startTemplateName");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "start_email_subject" property is missing,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_Missing_start_email_subject() throws Exception {
        this.removeProperty(TestHelper.DEFAULT_NAMESPACE, "start_email_subject");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "start_email_subject",
                "startEmailSubject");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "start_email_subject" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_EmptyValue_start_email_subject() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "start_email_subject", " ");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "start_email_subject",
                "startEmailSubject");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "start_email_subject" property has multiple values,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_MultipleValues_start_email_subject() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "start_email_subject", new String[]{"1", "2"});
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "start_email_subject",
                "startEmailSubject");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "start_email_from_address" property is missing,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_Missing_start_email_from_address() throws Exception {
        this.removeProperty(TestHelper.DEFAULT_NAMESPACE, "start_email_from_address");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "start_email_from_address",
                "address@yahoo.com");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "start_email_from_address" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_EmptyValue_start_email_from_address() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "start_email_from_address", " ");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "start_email_from_address",
                "address@yahoo.com");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "start_email_from_address" property has multiple values,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_MultipleValues_start_email_from_address() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "start_email_from_address", new String[]{"1", "2"});
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "start_email_from_address",
                "address@yahoo.com");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "send_end_phase_email" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_EmptyValue_send_end_phase_email() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "send_end_phase_email", " ");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "send_end_phase_email", "true");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "send_end_phase_email" property has multiple values,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_MultipleValues_send_end_phase_email() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "send_end_phase_email", new String[]{"1", "2"});
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "send_end_phase_email", "true");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "end_email_template_source" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_EmptyValue_end_email_template_source() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "end_email_template_source", " ");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "end_email_template_source",
                "endTemplateSource");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "end_email_template_source" property has multiple values,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_MultipleValues_end_email_template_source() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "end_email_template_source", new String[]{"1", "2"});
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "end_email_template_source",
                "endTemplateSource");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "end_email_template_name" property is missing,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_Missing_end_email_template_name() throws Exception {
        this.removeProperty(TestHelper.DEFAULT_NAMESPACE, "end_email_template_name");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "end_email_template_name",
                "endTemplateName");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "end_email_template_name" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_EmptyValue_end_email_template_name() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "end_email_template_name", " ");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "end_email_template_name",
                "endTemplateName");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "end_email_template_name" property has multiple values,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_MultipleValues_end_email_template_name() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "end_email_template_name", new String[]{"1", "2"});
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "end_email_template_name",
                "endTemplateName");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "end_email_subject" property is missing,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_Missing_end_email_subject() throws Exception {
        this.removeProperty(TestHelper.DEFAULT_NAMESPACE, "end_email_subject");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "end_email_subject",
                "endEmailSubject");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "end_email_subject" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_EmptyValue_end_email_subject() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "end_email_subject", " ");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "end_email_subject",
                "endEmailSubject");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "end_email_subject" property has multiple values,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_MultipleValues_end_email_subject() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "end_email_subject", new String[]{"1", "2"});
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "end_email_subject",
                "endEmailSubject");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "end_email_from_address" property is missing,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_Missing_end_email_from_address() throws Exception {
        this.removeProperty(TestHelper.DEFAULT_NAMESPACE, "end_email_from_address");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "end_email_from_address",
                "address@yahoo.com");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "end_email_from_address" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_EmptyValue_end_email_from_address() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "end_email_from_address", " ");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "end_email_from_address",
                "address@yahoo.com");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "end_email_from_address" property has multiple values,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_MultipleValues_end_email_from_address() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "end_email_from_address", new String[]{"1", "2"});
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "end_email_from_address",
                "address@yahoo.com");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "send_one_hour_phase_email" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_EmptyValue_send_one_hour_phase_email() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "send_one_hour_phase_email", " ");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "send_one_hour_phase_email", "true");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "send_one_hour_phase_email" property has multiple values,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_MultipleValues_send_one_hour_phase_email() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "send_one_hour_phase_email", new String[]{"1", "2"});
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "send_one_hour_phase_email", "true");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "one_hour_email_template_source" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_EmptyValue_one_hour_email_template_source() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "one_hour_email_template_source", " ");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "one_hour_email_template_source",
                "oneHourTemplateSource");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "one_hour_email_template_source" property has multiple values,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_MultipleValues_one_hour_email_template_source() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "one_hour_email_template_source",
            new String[]{"1", "2"});
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "one_hour_email_template_source",
                "oneHourTemplateSource");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "one_hour_email_template_name" property is missing,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_Missing_one_hour_email_template_name() throws Exception {
        this.removeProperty(TestHelper.DEFAULT_NAMESPACE, "one_hour_email_template_name");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "one_hour_email_template_name",
                "oneHourTemplateName");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "one_hour_email_template_name" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_EmptyValue_one_hour_email_template_name() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "one_hour_email_template_name", " ");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "one_hour_email_template_name",
                "oneHourTemplateName");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "one_hour_email_template_name" property has multiple values,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_MultipleValues_one_hour_email_template_name() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "one_hour_email_template_name",
            new String[]{"1", "2"});
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "one_hour_email_template_name",
                "oneHourTemplateName");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "one_hour_email_subject" property is missing,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_Missing_one_hour_email_subject() throws Exception {
        this.removeProperty(TestHelper.DEFAULT_NAMESPACE, "one_hour_email_subject");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "one_hour_email_subject",
                "oneHourEmailSubject");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "one_hour_email_subject" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_EmptyValue_one_hour_email_subject() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "one_hour_email_subject", " ");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "one_hour_email_subject",
                "oneHourEmailSubject");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "one_hour_email_subject" property has multiple values,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_MultipleValues_one_hour_email_subject() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "one_hour_email_subject", new String[]{"1", "2"});
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "one_hour_email_subject",
                "oneHourEmailSubject");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "one_hour_email_from_address" property is missing,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_Missing_one_hour_email_from_address() throws Exception {
        this.removeProperty(TestHelper.DEFAULT_NAMESPACE, "one_hour_email_from_address");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "one_hour_email_from_address",
                "address@yahoo.com");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "one_hour_email_from_address" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_EmptyValue_one_hour_email_from_address() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "one_hour_email_from_address", " ");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "one_hour_email_from_address",
                "address@yahoo.com");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "one_hour_email_from_address" property has multiple values,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_MultipleValues_one_hour_email_from_address() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "one_hour_email_from_address", new String[]{"1", "2"});
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "one_hour_email_from_address",
                "address@yahoo.com");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "send_eight_hours_phase_email" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_EmptyValue_send_eight_hours_phase_email() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "send_eight_hours_phase_email", " ");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "send_eight_hours_phase_email", "true");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "send_eight_hours_phase_email" property has multiple values,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_MultipleValues_send_eight_hours_phase_email() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "send_eight_hours_phase_email",
            new String[]{"1", "2"});
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "send_eight_hours_phase_email", "true");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "eight_hours_email_template_source" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_EmptyValue_eight_hours_email_template_source() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "eight_hours_email_template_source", " ");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "eight_hours_email_template_source",
                "eightHoursTemplateSource");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "eight_hours_email_template_source" property has multiple values,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_MultipleValues_eight_hours_email_template_source() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "eight_hours_email_template_source",
            new String[]{"1", "2"});
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "eight_hours_email_template_source",
                "eightHoursTemplateSource");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "eight_hours_email_template_name" property is missing,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_Missing_eight_hours_email_template_name() throws Exception {
        this.removeProperty(TestHelper.DEFAULT_NAMESPACE, "eight_hours_email_template_name");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "eight_hours_email_template_name",
                "eightHoursTemplateName");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "eight_hours_email_template_name" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_EmptyValue_eight_hours_email_template_name() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "eight_hours_email_template_name", " ");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "eight_hours_email_template_name",
                "eightHoursTemplateName");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "eight_hours_email_template_name" property has multiple values,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_MultipleValues_eight_hours_email_template_name() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "eight_hours_email_template_name",
            new String[]{"1", "2"});
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "eight_hours_email_template_name",
                "eightHoursTemplateName");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "eight_hours_email_subject" property is missing,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_Missing_eight_hours_email_subject() throws Exception {
        this.removeProperty(TestHelper.DEFAULT_NAMESPACE, "eight_hours_email_subject");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "eight_hours_email_subject",
                "eightHoursEmailSubject");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "eight_hours_email_subject" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_EmptyValue_eight_hours_email_subject() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "eight_hours_email_subject", " ");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "eight_hours_email_subject",
                "eightHoursEmailSubject");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "eight_hours_email_subject" property has multiple values,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_MultipleValues_eight_hours_email_subject() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "eight_hours_email_subject", new String[]{"1", "2"});
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "eight_hours_email_subject",
                "eightHoursEmailSubject");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "eight_hours_email_from_address" property is missing,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_Missing_eight_hours_email_from_address() throws Exception {
        this.removeProperty(TestHelper.DEFAULT_NAMESPACE, "eight_hours_email_from_address");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "eight_hours_email_from_address",
                "address@yahoo.com");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "eight_hours_email_from_address" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_EmptyValue_eight_hours_email_from_address() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "eight_hours_email_from_address", " ");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "eight_hours_email_from_address",
                "address@yahoo.com");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "eight_hours_email_from_address" property has multiple values,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_MultipleValues_eight_hours_email_from_address() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "eight_hours_email_from_address",
            new String[]{"1", "2"});
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "eight_hours_email_from_address",
                "address@yahoo.com");
        }
    }


    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "message_generator_key" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_EmptyValue_message_generator_key() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "message_generator_key", " ");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "message_generator_key",
                "messageGeneratorKey");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "message_generator_key" property has multiple values,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_MultipleValues_message_generator_key() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "message_generator_key",
            new String[]{"1", "2"});
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "message_generator_key",
                "messageGeneratorKey");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "specification_factory_namespace" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_EmptyValue_specification_factory_namespace() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "specification_factory_namespace", " ");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "specification_factory_namespace",
                "com.topcoder.clientcockpit.objectfactory");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "specification_factory_namespace" property has multiple values,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_MultipleValues_specification_factory_namespace() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "specification_factory_namespace",
            new String[]{"1", "2"});
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "specification_factory_namespace",
                "com.topcoder.clientcockpit.objectfactory");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "specification_factory_namespace" property points to a object factory namespace which
     * defines a wrong type not instance of <code>EmailMessageGenerator</code>,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_invalid_specification_factory_namespace_1() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "specification_factory_namespace",
            "objectfactory_wrong_type");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "specification_factory_namespace",
                "com.topcoder.clientcockpit.objectfactory");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The "specification_factory_namespace" property points to a object factory namespace which has
     * invalid configuration,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_invalid_specification_factory_namespace_2() throws Exception {
        this.setProperty(TestHelper.DEFAULT_NAMESPACE, "specification_factory_namespace",
            "objectfactory_invalid_configuration");
        try {
            new CompletedPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            this.setProperty(TestHelper.DEFAULT_NAMESPACE, "specification_factory_namespace",
                "com.topcoder.clientcockpit.objectfactory");
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler(ContestManager)}.
     * </p>
     *
     * <p>
     * Given <code>ContestManager</code> is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_Null_Bean_1() throws Exception {
        try {
            new CompletedPhaseHandler((ContestManager) null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler(String)}.
     * </p>
     *
     * <p>
     * Given namespace is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_Null_Namespace_1() throws Exception {
        try {
            new CompletedPhaseHandler((String) null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler(String)}.
     * </p>
     *
     * <p>
     * Given namespace is empty, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_Empty_Namespace_1() throws Exception {
        try {
            new CompletedPhaseHandler(" ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler(String)}.
     * </p>
     *
     * <p>
     * Given namespace is unknown, <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_Unknown_Namespace_1() throws Exception {
        try {
            new CompletedPhaseHandler("NoSuchNamespace");
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        }
    }


    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler(String, ContestManager)}.
     * </p>
     *
     * <p>
     * Given <code>ContestManager</code> is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_Null_Bean_2() throws Exception {
        try {
            new CompletedPhaseHandler(TestHelper.DEFAULT_NAMESPACE, null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler(String, ContestManager)}.
     * </p>
     *
     * <p>
     * Given namespace is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_Null_Namespace_2() throws Exception {
        try {
            new CompletedPhaseHandler(null, TestHelper.CONTEST_MANAGER);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler(String, ContestManager)}.
     * </p>
     *
     * <p>
     * Given namespace is empty, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_Empty_Namespace_2() throws Exception {
        try {
            new CompletedPhaseHandler(" ", TestHelper.CONTEST_MANAGER);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler(String, ContestManager)}.
     * </p>
     *
     * <p>
     * Given namespace is unknown, <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_Unknown_Namespace_2() throws Exception {
        try {
            new CompletedPhaseHandler("NoSuchNamespace", TestHelper.CONTEST_MANAGER);
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link CompletedPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The phase is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_NullPhase() throws Exception {

        try {
            this.getCompletedPhaseHandler().canPerform(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link CompletedPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The phase's status is null, <code>PhaseHandlingException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_NullPhaseStatus() throws Exception {
        Phase phase = this.createPhase(null, CockpitPhaseType.COMPLETED);

        try {
            this.getCompletedPhaseHandler().canPerform(phase);
            fail("PhaseHandlingException is expected.");
        } catch (PhaseHandlingException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link CompletedPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The phase's type is null, <code>PhaseHandlingException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_NullPhaseType() throws Exception {
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, null);

        try {
            this.getCompletedPhaseHandler().canPerform(phase);
            fail("PhaseHandlingException is expected.");
        } catch (PhaseHandlingException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link CompletedPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The "contest" attribute value is null,
     * <code>PhaseHandlingException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_NullContestAttr() throws Exception {
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.COMPLETED);

        try {
            this.getCompletedPhaseHandler().canPerform(phase);
            fail("PhaseHandlingException is expected.");
        } catch (PhaseHandlingException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link CompletedPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The "contest" attribute value is not type of <code>Contest</code>,
     * <code>PhaseHandlingException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_ContestAttr_WrongType() throws Exception {
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.COMPLETED);

        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST, "WrongType");
        try {
            this.getCompletedPhaseHandler().canPerform(phase);
            fail("PhaseHandlingException is expected.");
        } catch (PhaseHandlingException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link CompletedPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The <code>Contest.endDate</code> is null,
     * <code>PhaseHandlingException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_InvalidContestAttr() throws Exception {
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.COMPLETED);

        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST,
            this.createContest(new Date(), null, null, null));
        try {
            this.getCompletedPhaseHandler().canPerform(phase);
            fail("PhaseHandlingException is expected.");
        } catch (PhaseHandlingException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link CompletedPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The project does not have a valid "MinimumSubmissions" number,
     * <code>PhaseHandlingException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_InvalidMinimumSubmissions_1() throws Exception {
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.COMPLETED);

        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST,
            this.createContest(new Date(), new Date(), new Date(), null));
        try {
            this.getCompletedPhaseHandler().canPerform(phase);
            fail("PhaseHandlingException is expected.");
        } catch (PhaseHandlingException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link CompletedPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The project does not have a valid "MinimumSubmissions" number,
     * <code>PhaseHandlingException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_InvalidMinimumSubmissions_2() throws Exception {
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.COMPLETED);

        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST,
            this.createContest(new Date(), new Date(), new Date(), null));

        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_MINIMUM_SUBMISSIONS, new ArrayList());
        try {
            this.getCompletedPhaseHandler().canPerform(phase);
            fail("PhaseHandlingException is expected.");
        } catch (PhaseHandlingException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link CompletedPhaseHandler#perform(com.topcoder.project.phases.Phase, String)}.
     * </p>
     *
     * <p>
     * The phase is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_NullPhase() throws Exception {

        try {
            this.getCompletedPhaseHandler().perform(null, "operator");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link CompletedPhaseHandler#perform(com.topcoder.project.phases.Phase, String)}.
     * </p>
     *
     * <p>
     * The phase's status is null, <code>PhaseHandlingException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_NullPhaseStatus() throws Exception {
        Phase phase = this.createPhase(null, CockpitPhaseType.COMPLETED);

        try {
            this.getCompletedPhaseHandler().perform(phase, "operator");
            fail("PhaseHandlingException is expected.");
        } catch (PhaseHandlingException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link CompletedPhaseHandler#perform(com.topcoder.project.phases.Phase, String)}.
     * </p>
     *
     * <p>
     * The phase's type is null, <code>PhaseHandlingException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_NullPhaseType() throws Exception {
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, null);

        try {
            this.getCompletedPhaseHandler().perform(phase, "operator");
            fail("PhaseHandlingException is expected.");
        } catch (PhaseHandlingException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link CompletedPhaseHandler#perform(com.topcoder.project.phases.Phase, String)}.
     * </p>
     *
     * <p>
     * The "contest" attribute value is null,
     * <code>PhaseHandlingException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_NullContestAttr() throws Exception {
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.COMPLETED);

        try {
            this.getCompletedPhaseHandler().perform(phase, "operator");
            fail("PhaseHandlingException is expected.");
        } catch (PhaseHandlingException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link CompletedPhaseHandler#perform(com.topcoder.project.phases.Phase, String)}.
     * </p>
     *
     * <p>
     * The "contest" attribute value is not type of <code>Contest</code>,
     * <code>PhaseHandlingException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_ContestAttr_WrongType() throws Exception {
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.COMPLETED);

        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST, "WrongType");
        try {
            this.getCompletedPhaseHandler().perform(phase, "operator");
            fail("PhaseHandlingException is expected.");
        } catch (PhaseHandlingException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link CompletedPhaseHandler#perform(com.topcoder.project.phases.Phase, String)}.
     * </p>
     *
     * <p>
     * Error occurs while {@link ContestManager#updateContest(Contest)},
     * <code>PhaseHandlingException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_Error_UpdatingContestStatus() throws Exception {
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.COMPLETED);

        Contest contest = this.createContest(null, null, null, null);

        this.populateAttributes(phase, contest);

        this.mockContestManager(true);

        try {
            this.getCompletedPhaseHandler().perform(phase, "operator");
            fail("PhaseHandlingException is expected.");
        } catch (PhaseHandlingException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link CompletedPhaseHandler#perform(com.topcoder.project.phases.Phase, String)}.
     * </p>
     *
     * <p>
     * Error occurs while generating email message caused by template data missing,
     * <code>EmailMessageGenerationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_Error_MissTemplateData() throws Exception {
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.COMPLETED);

        Contest contest = this.createContest(null, null, null, null);

        this.populateAttributes(phase, contest);
        phase.removeAttribute("date");

        this.mockContestManager(false);

        try {
            this.getCompletedPhaseHandler().perform(phase, "operator");
            fail("EmailMessageGenerationException is expected.");
        } catch (EmailMessageGenerationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link CompletedPhaseHandler#perform(com.topcoder.project.phases.Phase, String)}.
     * </p>
     *
     * <p>
     * Error occurs while generating email message caused by template data type is not supported,
     * <code>EmailMessageGenerationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_Error_NotSupportedTemplateData() throws Exception {
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.COMPLETED);

        Contest contest = this.createContest(null, null, null, null);

        this.populateAttributes(phase, contest);
        //ArrayList type is not supported
        phase.setAttribute("date", new ArrayList());

        this.mockContestManager(false);

        try {
            this.getCompletedPhaseHandler().perform(phase, "operator");
            fail("EmailMessageGenerationException is expected.");
        } catch (EmailMessageGenerationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link CompletedPhaseHandler#perform(com.topcoder.project.phases.Phase, String)}.
     * </p>
     *
     * <p>
     * Error occurs while generating email message caused by "ResourceEmails" is an empty list,
     * <code>EmailMessageGenerationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_Error_EmptyResourceEmailsList() throws Exception {
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.COMPLETED);

        Contest contest = this.createContest(null, null, null, null);

        this.populateAttributes(phase, contest);

        //"ResourceEmails" is an empty list
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_RESOURCE_EMAILS, new ArrayList());

        this.mockContestManager(false);

        try {
            this.getCompletedPhaseHandler().perform(phase, "operator");
            fail("EmailMessageGenerationException is expected.");
        } catch (EmailMessageGenerationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link CompletedPhaseHandler#perform(com.topcoder.project.phases.Phase, String)}.
     * </p>
     *
     * <p>
     * Error occurs while generating email message caused by <code>EmailEngine</code> not configured,
     * <code>EmailSendingException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_Error_EmailEngineNotConfigured() throws Exception {
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.COMPLETED);

        Contest contest = this.createContest(null, null, null, null);

        this.populateAttributes(phase, contest);

        this.mockContestManager(false);

        //Remove the namespace for EmailEngine.
        ConfigManager.getInstance().removeNamespace("com.topcoder.message.email.EmailEngine");

        try {
            this.getCompletedPhaseHandler().perform(phase, "operator");
            fail("EmailSendingException is expected.");
        } catch (EmailSendingException e) {
            //pass
        }
    }
}
