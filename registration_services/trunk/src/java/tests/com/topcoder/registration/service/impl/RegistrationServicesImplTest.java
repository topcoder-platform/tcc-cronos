/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.impl;

import java.io.File;
import java.util.Date;

import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.resource.Resource;
import com.topcoder.registration.service.InvalidRoleException;
import com.topcoder.registration.service.RegistrationInfo;
import com.topcoder.registration.service.RegistrationPosition;
import com.topcoder.registration.service.RegistrationResult;
import com.topcoder.registration.service.RegistrationServiceConfigurationException;
import com.topcoder.registration.service.RegistrationServiceException;
import com.topcoder.registration.service.RegistrationValidationException;
import com.topcoder.registration.service.RemovalResult;

import junit.framework.TestCase;

/**
 * <p>
 * This is a test case for <code>RegistrationServicesImpl</code> class.
 * </p>
 * @author moonli
 * @version 1.0
 */
public class RegistrationServicesImplTest extends TestCase {

    /**
     * <p>
     * Represents an instance of <code>RegistrationServicesImpl</code> class.
     * </p>
     */
    private RegistrationServicesImpl services;

    /**
     * <p>
     * Represents an instance of <code>RegistrationInfo</code>.
     * </p>
     */
    private RegistrationInfo regInfo;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "configuration.xml");
        services = new RegistrationServicesImpl();

        regInfo = new RegistrationInfoImpl(1, 1, 1);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
        services = null;
        regInfo = null;
    }

    /**
     * <p>
     * Test for default constructor.
     * </p>
     * <p>
     * Tests it for accuracy, a non-null instance should be created.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorAccuracy() throws Exception {
        assertNotNull("'services' should not be null.", services);
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against null namespace, Expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithNullNamespace() throws Exception {
        try {
            new RegistrationServicesImpl(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against empty namespace, Expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithEmptyNamespace() throws Exception {
        try {
            new RegistrationServicesImpl("   ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with activeProjectStatusId missing. Expects
     * <code>RegistrationServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithActiveProjectStatusIdMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_configs" + File.separator
            + "active_project_status_id_missing.xml");
        try {
            new RegistrationServicesImpl(
                "com.topcoder.registration.service.impl.RegistrationServicesImpl");
            fail("RegistrationServiceConfigurationException should be thrown.");
        } catch (RegistrationServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with banManagerKey missing. Expects
     * <code>RegistrationServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithBanManagerKeyMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_configs" + File.separator
            + "ban_manager_key_missing.xml");
        try {
            new RegistrationServicesImpl(
                "com.topcoder.registration.service.impl.RegistrationServicesImpl");
            fail("RegistrationServiceConfigurationException should be thrown.");
        } catch (RegistrationServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with contactMemberServiceKey missing. Expects
     * <code>RegistrationServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithContactMemberServiceKeyMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_configs" + File.separator
            + "contact_member_service_key_missing.xml");
        try {
            new RegistrationServicesImpl(
                "com.topcoder.registration.service.impl.RegistrationServicesImpl");
            fail("RegistrationServiceConfigurationException should be thrown.");
        } catch (RegistrationServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with operator missing. Expects
     * <code>RegistrationServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithOperatorMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_configs" + File.separator
            + "operator_missing.xml");
        try {
            new RegistrationServicesImpl(
                "com.topcoder.registration.service.impl.RegistrationServicesImpl");
            fail("RegistrationServiceConfigurationException should be thrown.");
        } catch (RegistrationServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with phaseManagerKey missing. Expects
     * <code>RegistrationServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithPhaseManagerKeyMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_configs" + File.separator
            + "phase_manager_key_missing.xml");
        try {
            new RegistrationServicesImpl(
                "com.topcoder.registration.service.impl.RegistrationServicesImpl");
            fail("RegistrationServiceConfigurationException should be thrown.");
        } catch (RegistrationServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with projectServicesKey missing. Expects
     * <code>RegistrationServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithProjectServicesKeyMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_configs" + File.separator
            + "project_services_key_missing.xml");
        try {
            new RegistrationServicesImpl(
                "com.topcoder.registration.service.impl.RegistrationServicesImpl");
            fail("RegistrationServiceConfigurationException should be thrown.");
        } catch (RegistrationServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with registrationPhaseId having invalid value type.
     * Expects <code>RegistrationServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithregistrationPhaseIdHasInvalidValueType() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_configs" + File.separator
            + "registration_phase_id_invalid_type.xml");
        try {
            new RegistrationServicesImpl(
                "com.topcoder.registration.service.impl.RegistrationServicesImpl");
            fail("RegistrationServiceConfigurationException should be thrown.");
        } catch (RegistrationServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with registrationPhaseId missing. Expects
     * <code>RegistrationServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithRegistrationPhaseIdMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_configs" + File.separator
            + "registration_phase_id_missing.xml");
        try {
            new RegistrationServicesImpl(
                "com.topcoder.registration.service.impl.RegistrationServicesImpl");
            fail("RegistrationServiceConfigurationException should be thrown.");
        } catch (RegistrationServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with removeMessageTemplateName missing. Expects
     * <code>RegistrationServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithRemoveMessageTemplateNameMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_configs" + File.separator
            + "remove_message_template_name_missing.xml");
        try {
            new RegistrationServicesImpl(
                "com.topcoder.registration.service.impl.RegistrationServicesImpl");
            fail("RegistrationServiceConfigurationException should be thrown.");
        } catch (RegistrationServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with resourceManager having invalid class type in
     * ObjectFactory configuration. Expects <code>RegistrationServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithResourceManagerHavingInvalidClassType() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_configs" + File.separator
            + "resource_manager_invalid_class_type.xml");
        try {
            new RegistrationServicesImpl(
                "com.topcoder.registration.service.impl.RegistrationServicesImpl");
            fail("RegistrationServiceConfigurationException should be thrown.");
        } catch (RegistrationServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with resourceManagerKey missing. Expects
     * <code>RegistrationServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithResourceManagerKeyMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_configs" + File.separator
            + "resource_manager_key_missing.xml");
        try {
            new RegistrationServicesImpl(
                "com.topcoder.registration.service.impl.RegistrationServicesImpl");
            fail("RegistrationServiceConfigurationException should be thrown.");
        } catch (RegistrationServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with resourceManagerKey missing for resourceManager in
     * ObjectFactory configuration. Expects <code>RegistrationServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithResourceManagerMissingInObjectFactoryConfig() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_configs" + File.separator
            + "resource_manager_missing_in_obj_factory_config.xml");
        try {
            new RegistrationServicesImpl(
                "com.topcoder.registration.service.impl.RegistrationServicesImpl");
            fail("RegistrationServiceConfigurationException should be thrown.");
        } catch (RegistrationServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with specNamespace invalid. Expects
     * <code>RegistrationServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithspecNamespaceInvalid() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_configs" + File.separator
            + "spec_namespace_invalid.xml");
        try {
            new RegistrationServicesImpl(
                "com.topcoder.registration.service.impl.RegistrationServicesImpl");
            fail("RegistrationServiceConfigurationException should be thrown.");
        } catch (RegistrationServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with specNamespace missing. Expects
     * <code>RegistrationServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithSpecNamespaceMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_configs" + File.separator
            + "spec_namespace_missing.xml");
        try {
            new RegistrationServicesImpl(
                "com.topcoder.registration.service.impl.RegistrationServicesImpl");
            fail("RegistrationServiceConfigurationException should be thrown.");
        } catch (RegistrationServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with teamCaptainRoleId missing. Expects
     * <code>RegistrationServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithTeamCaptainRoleIdMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_configs" + File.separator
            + "team_captain_role_id_missing.xml");
        try {
            new RegistrationServicesImpl(
                "com.topcoder.registration.service.impl.RegistrationServicesImpl");
            fail("RegistrationServiceConfigurationException should be thrown.");
        } catch (RegistrationServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with teamServicesKey missing. Expects
     * <code>RegistrationServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithTeamServicesKeyMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_configs" + File.separator
            + "team_services_key_missing.xml");
        try {
            new RegistrationServicesImpl(
                "com.topcoder.registration.service.impl.RegistrationServicesImpl");
            fail("RegistrationServiceConfigurationException should be thrown.");
        } catch (RegistrationServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with unknown namespace. Expects
     * <code>RegistrationServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithUnknownNamespace() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_configs" + File.separator
            + "unknown_namespace.xml");
        try {
            new RegistrationServicesImpl(
                "com.topcoder.registration.service.impl.RegistrationServicesImpl");
            fail("RegistrationServiceConfigurationException should be thrown.");
        } catch (RegistrationServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with userRetrievalKey missing. Expects
     * <code>RegistrationServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithUserRetrievalKeyMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_configs" + File.separator
            + "user_retrieval_key_missing.xml");
        try {
            new RegistrationServicesImpl(
                "com.topcoder.registration.service.impl.RegistrationServicesImpl");
            fail("RegistrationServiceConfigurationException should be thrown.");
        } catch (RegistrationServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with validatorKey invalid. Expects
     * <code>RegistrationServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithValidatorKeyInvalid() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_configs" + File.separator
            + "validator_key_invalid.xml");
        try {
            new RegistrationServicesImpl(
                "com.topcoder.registration.service.impl.RegistrationServicesImpl");
            fail("RegistrationServiceConfigurationException should be thrown.");
        } catch (RegistrationServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with validatorKey missing. Expects
     * <code>RegistrationServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithValidatorKeyMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_configs" + File.separator
            + "validator_key_missing.xml");
        try {
            new RegistrationServicesImpl(
                "com.topcoder.registration.service.impl.RegistrationServicesImpl");
            fail("RegistrationServiceConfigurationException should be thrown.");
        } catch (RegistrationServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it for accuracy, a non-null instance should be created.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithParamAccuracy() throws Exception {
        services = new RegistrationServicesImpl(
            "com.topcoder.registration.service.impl.RegistrationServicesImpl");
        assertNotNull("'services' should not be null.", services);
    }

    /**
     * <p>
     * Test for <code>validateRegistration</code> method.
     * </p>
     * <p>
     * Tests it with null parameter. Expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testValidateRegistrationWithNullArg() throws Exception {
        try {
            services.validateRegistration(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>validateRegistration</code> method.
     * </p>
     * <p>
     * Tests it with parameter containing negative user id. Expects
     * <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testValidateRegistrationWithParaHasNegUserId() throws Exception {
        regInfo = new RegistrationInfoImpl();
        regInfo.setProjectId(1);
        regInfo.setRoleId(1);
        try {
            services.validateRegistration(regInfo);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>validateRegistration</code> method.
     * </p>
     * <p>
     * Tests it with parameter containing negative project id. Expects
     * <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testValidateRegistrationWithParaHasNegProjectId() throws Exception {
        regInfo = new RegistrationInfoImpl();
        regInfo.setUserId(1);
        regInfo.setRoleId(1);
        try {
            services.validateRegistration(regInfo);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>validateRegistration</code> method.
     * </p>
     * <p>
     * Tests it with parameter containing negative role id. Expects
     * <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testValidateRegistrationWithParaHasNegRoleId() throws Exception {
        regInfo = new RegistrationInfoImpl();
        regInfo.setProjectId(1);
        regInfo.setUserId(1);
        try {
            services.validateRegistration(regInfo);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>validateRegistration</code> method.
     * </p>
     * <p>
     * Tests it while error occurred when using UserRetrieval, expects
     * <code>RegistrationServiceException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testValidateRegistrationWithRetrievalExceptionOccurring() throws Exception {
        regInfo.setUserId(1000);
        try {
            services.validateRegistration(regInfo);
            fail("RegistrationServiceException should be thrown.");
        } catch (RegistrationServiceException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>validateRegistration</code> method.
     * </p>
     * <p>
     * Tests it for error occurred when using RegistrationValidator, expects
     * <code>RegistrationValidationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testValidateRegistrationWithValidationErrorOccurring() throws Exception {
        regInfo.setProjectId(1000);
        try {
            services.validateRegistration(regInfo);
            fail("RegistrationValidationException should be thrown.");
        } catch (RegistrationValidationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>validateRegistration</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, successful validation.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testValidateRegistrationAccuracy() throws Exception {
        RegistrationResult result = services.validateRegistration(regInfo);

        assertTrue("True should be returned.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>registerForProject</code> method.
     * </p>
     * <p>
     * Tests it against null argument, Expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRegisterForProjectWithNullArg() throws Exception {
        try {
            services.registerForProject(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>registerForProject</code> method.
     * </p>
     * <p>
     * Tests it with parameter containing negative user id. Expects
     * <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRegisterForProjectWithNegUserId() throws Exception {
        regInfo = new RegistrationInfoImpl();
        regInfo.setProjectId(1);
        regInfo.setRoleId(1);
        try {
            services.registerForProject(regInfo);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>registerForProject</code> method.
     * </p>
     * <p>
     * Tests it with parameter containing negative project id. Expects
     * <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRegisterForProjectWithNegProjectId() throws Exception {
        regInfo = new RegistrationInfoImpl();
        regInfo.setUserId(1);
        regInfo.setRoleId(1);
        try {
            services.registerForProject(regInfo);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>registerForProject</code> method.
     * </p>
     * <p>
     * Tests it with parameter containing negative role id. Expects
     * <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRegisterForProjectWithNegRoleId() throws Exception {
        regInfo = new RegistrationInfoImpl();
        regInfo.setProjectId(1);
        regInfo.setUserId(1);
        try {
            services.registerForProject(regInfo);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>registerForProject</code> method.
     * </p>
     * <p>
     * Tests it while error occurred when using UserRetrieval, expects
     * <code>RegistrationServiceException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRegisterForProjectWithRetrievalExceptionOccurring() throws Exception {
        regInfo.setUserId(1000);
        try {
            services.registerForProject(regInfo);
            fail("RegistrationServiceException should be thrown.");
        } catch (RegistrationServiceException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>registerForProject</code> method.
     * </p>
     * <p>
     * Tests it for error occurred when using RegistrationValidator, expects
     * <code>RegistrationValidationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRegisterForProjectWithValidationErrorOccurring() throws Exception {
        regInfo.setProjectId(1000);
        try {
            services.registerForProject(regInfo);
            fail("RegistrationValidationException should be thrown.");
        } catch (RegistrationValidationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>registerForProject</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, registration should be successfully done.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRegisterForProjectAccuracy() throws Exception {
        RegistrationResult result = services.registerForProject(regInfo);

        assertTrue("True should be returned.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>getRegistration</code> method.
     * </p>
     * <p>
     * Tests it against negative user id. expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetRegistrationWithNegUserId() throws Exception {
        try {
            services.getRegistration(-1, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>getRegistration</code> method.
     * </p>
     * <p>
     * Tests it against negative project id. expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetRegistrationWithNegProjectId() throws Exception {
        try {
            services.getRegistration(1, -1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>getRegistration</code> method.
     * </p>
     * <p>
     * Tests it while error occurred when using UserRetrieval, expects
     * <code>RegistrationServiceException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetRegistrationWithRetrievalErrorOccuring() throws Exception {
        try {
            services.getRegistration(1000, 1);
            fail("RegistrationServiceException should be thrown.");
        } catch (RegistrationServiceException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>getRegistration</code> method.
     * </p>
     * <p>
     * Tests it for error occurred when retrieving external user, expects
     * <code>RegistrationServiceException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetRegistrationWithInvalidRegInfo() throws Exception {
        try {
            services.getRegistration(1000, 1);
            fail("RegistrationServiceException should be thrown.");
        } catch (RegistrationServiceException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>getRegistration</code> method.
     * </p>
     * <p>
     * Tests for accuracy, Non-null RegistrationInfo with userId=1, projectId=1, resourceRoleId=1
     * should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetRegistrationAccuracy() throws Exception {
        regInfo = services.getRegistration(1, 1);

        assertEquals("User id should be 1.", 1, regInfo.getUserId());
        assertEquals("Project id should be 1.", 1, regInfo.getProjectId());
        assertEquals("Resource role id should be 1.", 1, regInfo.getRoleId());
    }

    /**
     * <p>
     * Test for <code>findAvailableRegistrationPositions</code> method.
     * </p>
     * <p>
     * Tests it against null argument. Expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testFindAvailableRegistrationPositionsWithNullArg() throws Exception {
        try {
            services.findAvailableRegistrationPositions(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>findAvailableRegistrationPositions</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, only one RegistrationPosition instance should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testFindAvailableRegistrationPositionsAccuracy() throws Exception {
        RegistrationPosition[] positions = services
            .findAvailableRegistrationPositions(new ProjectCategory(1, "cate-1", new ProjectType(1,
                "type-1")));

        assertEquals("There should be only one elements returned.", 1, positions.length);
        assertEquals("Project id should be 1.", 1, positions[0].getProject().getId());
        assertEquals("Phase id should be 1.", 1, positions[0].getPhase().getId());
        assertEquals("There should be 2 available role.", 2,
            positions[0].getAvailableRoles().length);
    }

    /**
     * <p>
     * Test for <code>removeRegistration</code> method.
     * </p>
     * <p>
     * Tests it against null RegistrationInfo, Expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveRegistrationWithNullRegInfo() throws Exception {
        try {
            services.removeRegistration(null, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>removeRegistration</code> method.
     * </p>
     * <p>
     * Tests it against RegistrationInfo having negative user id, Expects
     * <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveRegistrationWithRegInfoHasNegUserId() throws Exception {
        regInfo = new RegistrationInfoImpl();
        regInfo.setProjectId(1);
        regInfo.setRoleId(1);
        try {
            services.removeRegistration(regInfo, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>removeRegistration</code> method.
     * </p>
     * <p>
     * Tests it against RegistrationInfo having negative project id, Expects
     * <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveRegistrationWithRegInfoHasNegProjectId() throws Exception {
        regInfo = new RegistrationInfoImpl();
        regInfo.setUserId(1);
        regInfo.setRoleId(1);
        try {
            services.removeRegistration(regInfo, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>removeRegistration</code> method.
     * </p>
     * <p>
     * Tests it against RegistrationInfo having negative role id, Expects
     * <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveRegistrationWithRegInfoHasNegRoleId() throws Exception {
        regInfo = new RegistrationInfoImpl();
        regInfo.setProjectId(1);
        regInfo.setUserId(1);
        try {
            services.removeRegistration(regInfo, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>removeRegistration</code> method.
     * </p>
     * <p>
     * Tests it against negative banDays, Expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveRegistrationWithNegBanDays() throws Exception {
        try {
            services.removeRegistration(regInfo, -1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>removeRegistration</code> method.
     * </p>
     * <p>
     * Tests it while error occurred when using UserRetrieval, expects
     * <code>RegistrationServiceException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveRegistrationWithInvalidRegInfo() throws Exception {
        regInfo.setUserId(1000);
        try {
            services.removeRegistration(regInfo, 1);
            fail("RegistrationServiceException should be thrown.");
        } catch (RegistrationServiceException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>removeRegistration</code> method.
     * </p>
     * <p>
     * Tests it while error occurred when retrieving Resource instance, expects
     * <code>RegistrationServiceException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveRegistrationWithErrorInRetrievingResource() throws Exception {
        regInfo.setUserId(1000);
        try {
            services.removeRegistration(regInfo, 1);
            fail("RegistrationServiceException should be thrown.");
        } catch (RegistrationServiceException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>removeRegistration</code> method.
     * </p>
     * <p>
     * Tests it with invalid template file, expects <code>RegistrationServiceException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveRegistrationWithInvalidTemplate() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_configs" + File.separator
            + "invalid_template_1.xml");

        services = new RegistrationServicesImpl(
            "com.topcoder.registration.service.impl.RegistrationServicesImpl");

        try {
            services.removeRegistration(regInfo, 1);
            fail("RegistrationServiceException should be thrown.");
        } catch (RegistrationServiceException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>removeRegistration</code> method.
     * </p>
     * <p>
     * Tests it with unknown template source, expects <code>RegistrationServiceException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveRegistrationWithUnknownTemplateSource() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_configs" + File.separator
            + "unknown_template.xml");

        services = new RegistrationServicesImpl(
            "com.topcoder.registration.service.impl.RegistrationServicesImpl");

        try {
            services.removeRegistration(regInfo, 1);
            fail("RegistrationServiceException should be thrown.");
        } catch (RegistrationServiceException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>removeRegistration</code> method.
     * </p>
     * <p>
     * Tests it for accuracy with projectId=2,userId=1,roleId=1, expects
     * <code>InvalidRoleException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveRegistrationWithInvalidRole() throws Exception {
        regInfo.setProjectId(2);
        try {
            services.removeRegistration(regInfo, 1);
            fail("InvalidRoleException should be thrown.");
        } catch (InvalidRoleException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>removeRegistration</code> method.
     * </p>
     * <p>
     * Tests it for accuracy with projectId=2,userId=1,roleId=2, registration should be removed
     * successfully.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveRegistrationAccuracy1() throws Exception {
        regInfo.setProjectId(2);
        regInfo.setRoleId(2);
        RemovalResult result = services.removeRegistration(regInfo, 3);
        assertTrue("True should be returned.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>removeRegistration</code> method.
     * </p>
     * <p>
     * Tests it for accuracy projectId=1,userId=1,roleId=1, given registration should be removed
     * successful.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveRegistrationAccuracy() throws Exception {
        RemovalResult result = services.removeRegistration(regInfo, 3);
        assertTrue("True should be returned.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>getRegisteredResources</code> method.
     * </p>
     * <p>
     * Tests it against negative project id, Expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void getRegisteredResourcesWithNegProjectId() throws Exception {
        try {
            services.getRegisteredResources(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>getRegisteredResources</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, one Resource instance should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetRegisteredResourcesAccuracy() throws Exception {
        Resource[] resources = services.getRegisteredResources(1);

        assertEquals("There should be only one resource.", 1, resources.length);
        assertEquals("Resource id should be 1", 1, resources[0].getId());
        assertEquals("Resource property mismatched.", resources[0]
            .getProperty("External reference ID"), new Long(1));
        assertEquals("Resource property mismatched.", resources[0].getProperty("Handle"), "tomek");
        assertEquals("Resource property mismatched.", resources[0].getProperty("Email"),
            "tomek@topcoder.com");
        assertTrue("True should be returned.", ((Date) resources[0]
            .getProperty("Registration Date")).before(new Date()));
    }

    /**
     * <p>
     * Test for <code>getRegisteredProjects</code> method.
     * </p>
     * <p>
     * Tests it against negative user id, Expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetRegisteredProjectWithNegUserId() throws Exception {
        try {
            services.getRegisteredProjects(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>getRegisteredProjects</code> method.
     * </p>
     * <p>
     * Tests it while error occurred when using UserRetrieval, expects
     * <code>RegistrationServiceException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetRegisteredProjectsWithErrorOccuringInUserRetrieval() throws Exception {
        try {
            services.getRegisteredProjects(1000);
            fail("RegistrationServiceException should be thrown.");
        } catch (RegistrationServiceException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>getRegisteredProjects</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, one project should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetRegisteredProjectsAccuracy() throws Exception {
        Project[] projects = services.getRegisteredProjects(1);

        assertEquals("There should be only one project returned.", 1, projects.length);
        assertEquals("ID of the project should be 1", 1, projects[0].getId());
        assertEquals("Name of the project should be 'Registration Services'.",
            "Registration Services", projects[0].getProperty("Project Name"));
    }

}
