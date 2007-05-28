/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.team.service.impl;

import java.io.File;

import com.topcoder.management.resource.Resource;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.TeamPosition;
import com.topcoder.management.team.offer.Offer;
import com.topcoder.management.team.offer.OfferStatus;
import com.topcoder.management.team.offer.OfferStatusType;
import com.topcoder.registration.team.service.OperationResult;
import com.topcoder.registration.team.service.ResourcePosition;
import com.topcoder.registration.team.service.TeamServiceConfigurationException;
import com.topcoder.registration.team.service.UnknownEntityException;

import junit.framework.TestCase;

/**
 * <p>
 * This is a test case for <code>TeamServicesImpl</code> class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TeamServicesImplTest extends TestCase {

    /**
     * <p>
     * Represents the namespace.
     * </p>
     */
    private static final String NAMESPACE = "com.topcoder.registration.team.service.impl.TeamServicesImpl";

    /**
     * <p>
     * Represents an instance of <code>TeamServicesImpl</code> class.
     * </p>
     */
    private TeamServicesImpl services;

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
        services = new TeamServicesImpl();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        services = null;
    }

    /**
     * <p>
     * Test for default constructor.
     * </p>
     * <p>
     * Tests it for accuracy, non-null instance should be created.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1Accuracy() throws Exception {
        assertNotNull("'services' should not be null.", services);
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against null namespace,expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithNullNamespace() throws Exception {
        try {
            new TeamServicesImpl(null);
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
     * Tests it against empty namespace,expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithEmptyNamespace() throws Exception {
        try {
            new TeamServicesImpl("     ");
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
     * Tests it against invalid configuration with accepteOfferTemplateName missing, expects
     * <code>TeamServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithAccepteOfferTemplateNameMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "accepte_offer_template_name_missing.xml");
        try {
            new TeamServicesImpl(NAMESPACE);
            fail("TeamServiceConfigurationException should be thrown.");
        } catch (TeamServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with contactMemberServiceKey missing, expects
     * <code>TeamServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithContactMemberServiceKeyMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "contact_member_service_key_missing.xml");
        try {
            new TeamServicesImpl(NAMESPACE);
            fail("TeamServiceConfigurationException should be thrown.");
        } catch (TeamServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with freeAgentRole missing, expects
     * <code>TeamServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithFreeAgentRoleMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "free_agent_role_missing.xml");
        try {
            new TeamServicesImpl(NAMESPACE);
            fail("TeamServiceConfigurationException should be thrown.");
        } catch (TeamServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with offerManagerKey missing, expects
     * <code>TeamServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithOfferManagerKeyMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "offer_manager_key_missing.xml");
        try {
            new TeamServicesImpl(NAMESPACE);
            fail("TeamServiceConfigurationException should be thrown.");
        } catch (TeamServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with phaseManagerKey missing, expects
     * <code>TeamServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithPhaseManagerKeyMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "phase_manager_key_missing.xml");
        try {
            new TeamServicesImpl(NAMESPACE);
            fail("TeamServiceConfigurationException should be thrown.");
        } catch (TeamServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with projectManagerKey missing, expects
     * <code>TeamServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithProjectManagerKeyMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "project_manager_key_missing.xml");
        try {
            new TeamServicesImpl(NAMESPACE);
            fail("TeamServiceConfigurationException should be thrown.");
        } catch (TeamServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with projectServicesKey missing, expects
     * <code>TeamServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithProjectServicesKeyMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "project_services_key_missing.xml");
        try {
            new TeamServicesImpl(NAMESPACE);
            fail("TeamServiceConfigurationException should be thrown.");
        } catch (TeamServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with registrationPhaseId invalid, expects
     * <code>TeamServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithRegistrationPhaseIdInvalid() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "registration_phase_id_invalid_value.xml");
        try {
            new TeamServicesImpl(NAMESPACE);
            fail("TeamServiceConfigurationException should be thrown.");
        } catch (TeamServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with registrationPhaseId missing, expects
     * <code>TeamServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithRegistrationPhaseIdMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "registration_phase_id_missing.xml");
        try {
            new TeamServicesImpl(NAMESPACE);
            fail("TeamServiceConfigurationException should be thrown.");
        } catch (TeamServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with registrationPhaseId negative, expects
     * <code>TeamServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithRegistrationPhaseIdNegative() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "registration_phase_id_negative.xml");
        try {
            new TeamServicesImpl(NAMESPACE);
            fail("TeamServiceConfigurationException should be thrown.");
        } catch (TeamServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with rejectOfferTemplateName missing, expects
     * <code>TeamServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithRejectOfferTemplateNameMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "reject_offer_template_name_missing.xml");
        try {
            new TeamServicesImpl(NAMESPACE);
            fail("TeamServiceConfigurationException should be thrown.");
        } catch (TeamServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with removeMemberMessageTemplateName missing, expects
     * <code>TeamServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithRemoveMemberMessageTemplateNameMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "remove_member_message_template_name_missing.xml");
        try {
            new TeamServicesImpl(NAMESPACE);
            fail("TeamServiceConfigurationException should be thrown.");
        } catch (TeamServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with removeTeamMessageTemplateName missing, expects
     * <code>TeamServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithRemoveTeamMessageTemplateNameMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "remove_team_message_template_name_missing.xml");
        try {
            new TeamServicesImpl(NAMESPACE);
            fail("TeamServiceConfigurationException should be thrown.");
        } catch (TeamServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with resourceManagementKey missing, expects
     * <code>TeamServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithResourceManagementKeyMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "resource_management_key_missing.xml");
        try {
            new TeamServicesImpl(NAMESPACE);
            fail("TeamServiceConfigurationException should be thrown.");
        } catch (TeamServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with sendOfferTemplateName missing, expects
     * <code>TeamServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithSendOfferTemplateNameMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "send_offer_template_name_missing.xml");
        try {
            new TeamServicesImpl(NAMESPACE);
            fail("TeamServiceConfigurationException should be thrown.");
        } catch (TeamServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with specNamespace missing, expects
     * <code>TeamServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithSpecNamespaceMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "spec_namespace_missing.xml");
        try {
            new TeamServicesImpl(NAMESPACE);
            fail("TeamServiceConfigurationException should be thrown.");
        } catch (TeamServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with specNamespace unknown, expects
     * <code>TeamServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithSpecNamespaceUnknown() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "spec_namespace_unknown.xml");
        try {
            new TeamServicesImpl(NAMESPACE);
            fail("TeamServiceConfigurationException should be thrown.");
        } catch (TeamServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with submitterRoleId missing, expects
     * <code>TeamServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithSubmitterRoleIdMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "submitter_role_id_missing.xml");
        try {
            new TeamServicesImpl(NAMESPACE);
            fail("TeamServiceConfigurationException should be thrown.");
        } catch (TeamServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with teamCaptainRoleId missing, expects
     * <code>TeamServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithTeamCaptainRoleIdMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "team_captain_role_id_missing.xml");
        try {
            new TeamServicesImpl(NAMESPACE);
            fail("TeamServiceConfigurationException should be thrown.");
        } catch (TeamServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with teamManagerKey missing, expects
     * <code>TeamServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithTeamManagerKeyMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "team_manager_key_missing.xml");
        try {
            new TeamServicesImpl(NAMESPACE);
            fail("TeamServiceConfigurationException should be thrown.");
        } catch (TeamServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with unknown namespace, expects
     * <code>TeamServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithUnknownNamespace() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "unknown_namespace.xml");
        try {
            new TeamServicesImpl(NAMESPACE);
            fail("TeamServiceConfigurationException should be thrown.");
        } catch (TeamServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with userRetrieval class type invalid, expects
     * <code>TeamServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithUserRetrievalClassTypeInvalid() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "user_retrieval_class_type_invalid.xml");
        try {
            new TeamServicesImpl(NAMESPACE);
            fail("TeamServiceConfigurationException should be thrown.");
        } catch (TeamServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with userRetrieval class type wrong, expects
     * <code>TeamServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithUserRetrievalClassTypeWrong() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "user_retrieval_class_type_wrong.xml");
        try {
            new TeamServicesImpl(NAMESPACE);
            fail("TeamServiceConfigurationException should be thrown.");
        } catch (TeamServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with userRetrieval invalid, expects
     * <code>TeamServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithUserRetrievalInvalid() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "user_retrieval_invalid.xml");
        try {
            new TeamServicesImpl(NAMESPACE);
            fail("TeamServiceConfigurationException should be thrown.");
        } catch (TeamServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against invalid configuration with userRetrievalKey missing, expects
     * <code>TeamServiceConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithUserRetrievalKeyMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "user_retrieval_key_missing.xml");
        try {
            new TeamServicesImpl(NAMESPACE);
            fail("TeamServiceConfigurationException should be thrown.");
        } catch (TeamServiceConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>createOrUpdateTeam(team, userId)</code> method.
     * </p>
     * <p>
     * Tests it against null team,expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdateTeamWithNullTeam() throws Exception {
        try {
            services.createOrUpdateTeam(null, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>createOrUpdateTeam(team, userId)</code> method.
     * </p>
     * <p>
     * Tests it against negative userId,expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdateTeamWithNegativeUserId() throws Exception {
        try {
            services.createOrUpdateTeam(new TeamHeader(), -1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>createOrUpdateTeam(team, userId)</code> method.
     * </p>
     * <p>
     * Tests it with team.id is negative and team having name already existing in this
     * project,expects unsuccessful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdateTeamWithDupTeamName() throws Exception {
        TeamHeader team = new TeamHeader();
        team.setTeamId(-1);
        team.setName("Robot Team");
        OperationResult result = services.createOrUpdateTeam(team, 1);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>createOrUpdateTeam(team, userId)</code> method.
     * </p>
     * <p>
     * Tests it with team.id is negative and team having name matching handle of certain resource in
     * this project,expects unsuccessful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdateTeamWithTeamNameMatchResourceHandle() throws Exception {
        TeamHeader team = new TeamHeader();
        team.setTeamId(-1);
        team.setName("argolite");
        OperationResult result = services.createOrUpdateTeam(team, 1);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>createOrUpdateTeam(team, userId)</code> method.
     * </p>
     * <p>
     * Tests it with team.id is NOT negative and team is not known, expects
     * <code>UnknownEntityException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdateTeamWithUnknownTeam() throws Exception {
        TeamHeader team = new TeamHeader();
        team.setTeamId(10);
        team.setName("name");
        try {
            services.createOrUpdateTeam(team, 1);
            fail("UnknownEntityException should be thrown.");
        } catch (UnknownEntityException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>createOrUpdateTeam(team, userId)</code> method.
     * </p>
     * <p>
     * Tests it with team.id=11 and InvalidTeamException occurred when updating team using
     * TeamManager,expects unsuccessful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdateTeamWithInvalidTeamExceptionOccuring() throws Exception {
        TeamHeader team = new TeamHeader();
        team.setTeamId(11);
        team.setName("name");
        OperationResult result = services.createOrUpdateTeam(team, 1);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>createOrUpdateTeam(team, userId)</code> method.
     * </p>
     * <p>
     * Tests it with team.id=11 and team having name matching handle of certain resource in this
     * project,expects unsuccessful operation result.expects unsuccessful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdateTeamWithPositiveTeamIdAndDupName() throws Exception {
        TeamHeader team = new TeamHeader();
        team.setTeamId(2);
        team.setName("argolite");
        OperationResult result = services.createOrUpdateTeam(team, 1);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>createOrUpdateTeam(team, userId)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy with team.id=-1,expects successful operation result.expects
     * unsuccessful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdateTeamAccuracy1() throws Exception {
        TeamHeader team = new TeamHeader();
        team.setTeamId(-1);
        team.setName("new team");
        OperationResult result = services.createOrUpdateTeam(team, 1);
        assertTrue("Result should be successful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>createOrUpdateTeam(team, userId)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy with team.id=2,expects successful operation result.expects unsuccessful
     * operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdateTeamAccuracy2() throws Exception {
        TeamHeader team = new TeamHeader();
        team.setTeamId(2);
        team.setName("update team");
        OperationResult result = services.createOrUpdateTeam(team, 1);
        assertTrue("Result should be successful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>getTeams(projectId)</code> method.
     * </p>
     * <p>
     * Tests it against negative id,expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetTeamsWithNegId() throws Exception {
        try {
            services.getTeams(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>getTeams(projectId)</code> method.
     * </p>
     * <p>
     * Tests it against unknown id,expects <code>UnkownEnityException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetTeamsWithUnknownProjectID() throws Exception {
        try {
            services.getTeams(10);
            fail("UnknownEntityException should be thrown.");
        } catch (UnknownEntityException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>getTeams(projectId)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, an array containing only one team should be returned, team.id=1 .
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetTeamsAccuracy() throws Exception {
        TeamHeader[] teams = services.getTeams(1);
        assertEquals("There should be only one team.", 1, teams.length);
        assertEquals("Team id should be 1.", 1, teams[0].getTeamId());
    }

    /**
     * <p>
     * Test for <code>getTeamMembers(teamId)</code> method.
     * </p>
     * <p>
     * Tests it against negative team id. expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetTeamMembersWithNegId() throws Exception {
        try {
            services.getTeamMembers(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>getTeamMembers(teamId)</code> method.
     * </p>
     * <p>
     * Tests it against teamId=10. expects <code>UnknownEntityException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetTeamMembersWithUnknownTeam() throws Exception {
        try {
            services.getTeamMembers(10);
            fail("UnknownEntityException should be thrown.");
        } catch (UnknownEntityException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>getTeamMembers(teamId)</code> method.
     * </p>
     * <p>
     * Tests it against teamId=1. an array containing three resources should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetTeamMembersAccuracy() throws Exception {
        Resource[] rs = services.getTeamMembers(1);
        assertEquals("There should be three resources returned.", 3, rs.length);
    }

    /**
     * <p>
     * Test for <code>removeTeam(teamId, userId)</code> method.
     * </p>
     * <p>
     * Tests it against negative team id, expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveTeamWithNegTeamId() throws Exception {
        try {
            services.removeTeam(-1, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>removeTeam(teamId, userId)</code> method.
     * </p>
     * <p>
     * Tests it against negative user id, expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveTeamWithNegUserId() throws Exception {
        try {
            services.removeTeam(1, -1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>removeTeam(teamId, userId)</code> method.
     * </p>
     * <p>
     * Test it with unknown userId, expects unsuccessful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveTeamWithUnknownUserId() throws Exception {
        OperationResult result = services.removeTeam(1, 11);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>removeTeam(teamId, userId)</code> method.
     * </p>
     * <p>
     * Test it with error occurring when retrieving user, expects unsuccessful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveTeamWithRetrievalExceptionOccuring() throws Exception {
        OperationResult result = services.removeTeam(1, 10);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>removeTeam(teamId, userId)</code> method.
     * </p>
     * <p>
     * Tests it against teamId=10. expects <code>UnknownEntityException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveTeamWithUnknownTeam() throws Exception {
        try {
            services.removeTeam(10, 1);
            fail("UnknownEntityException should be thrown.");
        } catch (UnknownEntityException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>removeTeam(teamId, userId)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy with teamId=1, team-1 should be removed successfully.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveTeamAccuracy1() throws Exception {
        OperationResult result = services.removeTeam(1, 2);
        assertTrue("Result should be successful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>removeTeam(teamId, userId)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy with teamId=2, team-1 should be removed successfully.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveTeamAccuracy2() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "config.xml");
        services = new TeamServicesImpl();

        OperationResult result = services.removeTeam(1, 2);
        assertTrue("Result should be successful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>getTeamPositionDetails(teamId)</code> method.
     * </p>
     * <p>
     * Tests it against negative team id. expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetTeamPositionDetailsWithNegativeTeamid() throws Exception {
        try {
            services.getTeamPositionsDetails(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>getTeamPositionDetails(teamId)</code> method.
     * </p>
     * <p>
     * Tests it against teamId=10. expects <code>UnknownEntityException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetTeamPositionDetailsWithUnknownTeam() throws Exception {
        try {
            services.getTeamPositionsDetails(10);
            fail("UnknownEntityException should be thrown.");
        } catch (UnknownEntityException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>getTeamPositionDetails(teamId)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy with teamid=1, an array containing two elements should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetTeamPositionDetailsAccuracy() throws Exception {
        ResourcePosition[] rp = services.getTeamPositionsDetails(1);
        assertEquals("There should be two results.", 2, rp.length);
    }

    /**
     * <p>
     * Test for <code>findFreeAgents(projectId)</code> method.
     * </p>
     * <p>
     * Tests it against negative project id, expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testFindFreeAgentsWithNegId() throws Exception {
        try {
            services.findFreeAgents(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>findFreeAgents(projectId)</code> method.
     * </p>
     * <p>
     * Tests it unknown project id=10, expects <code>UnknownEntityException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testFindFreeAgentsWithUnknownProjectId() throws Exception {
        try {
            services.findFreeAgents(10);
            fail("UnknownEntityException should be thrown.");
        } catch (UnknownEntityException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>findFreeAgents(projectId)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy with projectId=2, there should be no free agents.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testFindFreeAgentsAccuracy1() throws Exception {
        Resource[] resource = services.findFreeAgents(1);
        assertEquals("There should be no free agent.", 0, resource.length);
    }

    /**
     * <p>
     * Test for <code>findFreeAgents(projectId)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy with projectId=1, an array containing one resource should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testFindFreeAgentsAccuracy() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "config.xml");
        services = new TeamServicesImpl();

        Resource[] resource = services.findFreeAgents(1);
        assertEquals("There should be one free agent.", 1, resource.length);
    }

    /**
     * <p>
     * Test for <code>createOrUpdatePosition(position,teamId,userId)</code> method.
     * </p>
     * <p>
     * Tests it against null position, expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdatePositionWithNullPosition() throws Exception {
        try {
            services.createOrUpdatePosition(null, 1, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>createOrUpdatePosition(position,teamId,userId)</code> method.
     * </p>
     * <p>
     * Tests it against negative teamId, expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdatePositionWithNegTeamId() throws Exception {
        try {
            services.createOrUpdatePosition(new TeamPosition(), -1, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>createOrUpdatePosition(position,teamId,userId)</code> method.
     * </p>
     * <p>
     * Tests it against negative userId, expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdatePositionWithNegUserId() throws Exception {
        try {
            services.createOrUpdatePosition(new TeamPosition(), 1, -1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>createOrUpdatePosition(position,teamId,userId)</code> method.
     * </p>
     * <p>
     * Tests it with position name match one handle, expects unsuccessful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdatePositionWithUnknownUser() throws Exception {
        TeamPosition pos = new TeamPosition();
        pos.setPositionId(-1);
        pos.setName("name");
        pos.setMemberResourceId(1);

        OperationResult result = services.createOrUpdatePosition(pos, 1, 11);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>createOrUpdatePosition(position,teamId,userId)</code> method.
     * </p>
     * <p>
     * Tests it with error occurred when retrieving user, expects unsuccessful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdatePositionWithErrorOccurredRetirevingUser() throws Exception {
        TeamPosition pos = new TeamPosition();
        pos.setPositionId(-1);
        pos.setName("exa");
        pos.setMemberResourceId(1);

        OperationResult result = services.createOrUpdatePosition(pos, 1, 10);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>createOrUpdatePosition(position,teamId,userId)</code> method.
     * </p>
     * <p>
     * Tests it with position.id=-1, position.name already used. expects unsuccessful operation
     * result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdatePositionAccuracy1() throws Exception {
        TeamPosition pos = new TeamPosition();
        pos.setPositionId(-1);
        pos.setName("Good Position");
        pos.setMemberResourceId(1);

        OperationResult result = services.createOrUpdatePosition(pos, 1, 1);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>createOrUpdatePosition(position,teamId,userId)</code> method.
     * </p>
     * <p>
     * Tests it with position.id=-1, position.filled=true, but associated resource is not free
     * agent. expects unsuccessful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdatePositionAccuracy2() throws Exception {
        TeamPosition pos = new TeamPosition();
        pos.setPositionId(-1);
        pos.setName("new position");
        pos.setFilled(true);
        pos.setMemberResourceId(1);

        OperationResult result = services.createOrUpdatePosition(pos, 1, 1);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>createOrUpdatePosition(position,teamId,userId)</code> method.
     * </p>
     * <p>
     * Tests it with position.id=-1, position.filled=true, resouceId=1. expects successful operation
     * result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdatePositionAccuracy3() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "config.xml");
        services = new TeamServicesImpl();

        TeamPosition pos = new TeamPosition();
        pos.setPositionId(-1);
        pos.setFilled(true);
        pos.setName("new position");
        pos.setMemberResourceId(1);

        OperationResult result = services.createOrUpdatePosition(pos, 1, 1);
        assertTrue("Result should be successful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>createOrUpdatePosition(position,teamId,userId)</code> method.
     * </p>
     * <p>
     * Tests it with position.id=-1, position.filled=false, expects successful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdatePositionAccuracy7() throws Exception {
        TeamPosition pos = new TeamPosition();
        pos.setPositionId(-1);
        pos.setName("new position");
        pos.setFilled(false);
        pos.setMemberResourceId(1);

        OperationResult result = services.createOrUpdatePosition(pos, 1, 1);
        assertTrue("Result should be successful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>createOrUpdatePosition(position,teamId,userId)</code> method.
     * </p>
     * <p>
     * Tests it with position.id=1, position.name already used. expects unsuccessful operation
     * result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdatePositionAccuracy4() throws Exception {
        TeamPosition pos = new TeamPosition();
        pos.setPositionId(2);
        pos.setName("Good Position");
        pos.setMemberResourceId(1);

        OperationResult result = services.createOrUpdatePosition(pos, 1, 1);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>createOrUpdatePosition(position,teamId,userId)</code> method.
     * </p>
     * <p>
     * Tests it with position.id=4,expects <code>UnknownEntityException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdatePositionWithUnknownPosition() throws Exception {
        TeamPosition pos = new TeamPosition();
        pos.setPositionId(4);
        pos.setName("Good Position");
        pos.setMemberResourceId(1);
        try {
            OperationResult result = services.createOrUpdatePosition(pos, 1, 1);
            fail("UnknownEntityException should be thrown.");
        } catch (UnknownEntityException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>createOrUpdatePosition(position,teamId,userId)</code> method.
     * </p>
     * <p>
     * Tests it with position.id=2, position.filled=true, but associated resource is not free agent.
     * expects unsuccessful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdatePositionAccuracy5() throws Exception {
        TeamPosition pos = new TeamPosition();
        pos.setPositionId(2);
        pos.setName("new position");
        pos.setFilled(true);
        pos.setMemberResourceId(4);

        OperationResult result = services.createOrUpdatePosition(pos, 1, 1);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>createOrUpdatePosition(position,teamId,userId)</code> method.
     * </p>
     * <p>
     * Tests it with position.id=2, position.filled=true, memberResourceId=1, expects unsuccessful
     * operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdatePositionAccuracy6() throws Exception {
        TeamPosition pos = new TeamPosition();
        pos.setPositionId(2);
        pos.setName("new position");
        pos.setFilled(true);
        pos.setMemberResourceId(1);

        OperationResult result = services.createOrUpdatePosition(pos, 1, 1);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>createOrUpdatePosition(position,teamId,userId)</code> method.
     * </p>
     * <p>
     * Tests it with position.id=2, position.filled=false, expects successful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdatePositionAccuracy8() throws Exception {
        TeamPosition pos = new TeamPosition();
        pos.setPositionId(2);
        pos.setName("new position");
        pos.setFilled(false);
        pos.setMemberResourceId(1);

        OperationResult result = services.createOrUpdatePosition(pos, 1, 1);
        assertTrue("Result should be successful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>createOrUpdatePosition(position,teamId,userId)</code> method.
     * </p>
     * <p>
     * Tests it with position.id=2, position.filled=false, but error occurred when updating the
     * position, expects unsuccessful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdatePositionAccuracy9() throws Exception {
        TeamPosition pos = new TeamPosition();
        pos.setPositionId(2);
        pos.setName("new position");
        pos.setFilled(false);
        pos.setMemberResourceId(1);

        OperationResult result = services.createOrUpdatePosition(pos, 1, 100);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>getPosition(positionId)</code> method.
     * </p>
     * <p>
     * Tests it against negative id, expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetPositionWithNegId() throws Exception {
        try {
            services.getPosition(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>getPosition(positionId)</code> method.
     * </p>
     * <p>
     * Tests it with unknown position id, expects <code>UnknownEntityException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetPositionWithUnknownPosition() throws Exception {
        try {
            services.getPosition(100);
            fail("UnknownEntityException should be thrown.");
        } catch (UnknownEntityException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>getPosition(positionId)</code> method.
     * </p>
     * <p>
     * Tests it with positionId=2, the valid TeamPosition instance should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetPositionAccuracy() throws Exception {
        TeamPosition pos = services.getPosition(2);
        assertNotNull("'pos' should not be null.", pos);
        assertEquals("Name mismatched.", "Good Position", pos.getName());
    }

    /**
     * <p>
     * Test for <code>removePosition(positionId,userId)</code> method.
     * </p>
     * <p>
     * Tests it against negative position id, expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemovePositionWithNegPositionId() throws Exception {
        try {
            services.removePosition(-1, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>removePosition(positionId,userId)</code> method.
     * </p>
     * <p>
     * Tests it against negative user id, expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemovePositionWithNegUserId() throws Exception {
        try {
            services.removePosition(11, -1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>removePosition(positionId,userId)</code> method.
     * </p>
     * <p>
     * Tests it against unknown position id, expects <code>UnknownEntityException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemovePositionWithUnknownPositionId() throws Exception {
        try {
            services.removePosition(100, 1);
            fail("UnknownEntityException should be thrown.");
        } catch (UnknownEntityException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>removePosition(positionId,userId)</code> method.
     * </p>
     * <p>
     * Tests it with error occurred when removing position using TeamManager, expects unsuccessful
     * result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemovePositionAccuracy1() throws Exception {
        OperationResult result = services.removePosition(2, 100);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>removePosition(positionId,userId)</code> method.
     * </p>
     * <p>
     * Tests it with positionId=2, userId=1, expects successful result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemovePositionAccuracy2() throws Exception {
        OperationResult result = services.removePosition(2, 1);
        assertTrue("Result should be successful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>validateFinalization(teamId,userId)</code> method.
     * </p>
     * <p>
     * Tests it against negative team id. expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testValidateFinalizationWithNegTeamId() throws Exception {
        try {
            services.validateFinalization(-1, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>validateFinalization(teamId,userId)</code> method.
     * </p>
     * <p>
     * Tests it against negative user id. expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testValidateFinalizationWithNegUserId() throws Exception {
        try {
            services.validateFinalization(1, -1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>validateFinalization(teamId,userId)</code> method.
     * </p>
     * <p>
     * Tests it against unknown team id. expects <code>UnknownEntityException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testValidateFinalizationWithUnknownTeamId() throws Exception {
        try {
            services.validateFinalization(10, 1);
            fail("UnknownEntityException should be thrown.");
        } catch (UnknownEntityException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>validateFinalization(teamId,userId)</code> method.
     * </p>
     * <p>
     * Tests it with team's project not active. expects unsuccessful result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testValidateFinalizationWithInactiveProject() throws Exception {
        OperationResult result = services.validateFinalization(2, 1);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>validateFinalization(teamId,userId)</code> method.
     * </p>
     * <p>
     * Tests it with user not team's captain. expects unsuccessful result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testValidateFinalizationWithUserIdNotCaptain() throws Exception {
        OperationResult result = services.validateFinalization(19, 2);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>validateFinalization(teamId,userId)</code> method.
     * </p>
     * <p>
     * Tests it with team already finalized. expects unsuccessful result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testValidateFinalizationWithTeamAlreadyFinalized() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "config.xml");
        services = new TeamServicesImpl();

        OperationResult result = services.validateFinalization(18, 1);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>validateFinalization(teamId,userId)</code> method.
     * </p>
     * <p>
     * Tests it with teamId=19, userId=1. expects successful result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testValidateFinalizationAccuracy() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "config.xml");
        services = new TeamServicesImpl();

        OperationResult result = services.validateFinalization(19, 1);
        assertTrue("Result should be successful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>finalizeTeam(teamId, userId)</code> method.
     * </p>
     * <p>
     * Tests it with negative teamId, expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testFinalizeTeamWithNegTeamId() throws Exception {
        try {
            services.finalizeTeam(-1, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>finalizeTeam(teamId, userId)</code> method.
     * </p>
     * <p>
     * Tests it with negative userId, expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testFinalizeTeamWithNegUserId() throws Exception {
        try {
            services.finalizeTeam(1, -1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>finalizeTeam(teamId, userId)</code> method.
     * </p>
     * <p>
     * Tests it with teamId=1,userId=1, expects unsuccessful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testFinalizeTeamAccuracy1() throws Exception {
        OperationResult result = services.finalizeTeam(1, 1);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>finalizeTeam(teamId, userId)</code> method.
     * </p>
     * <p>
     * Tests it with teamId=19,userId=10, expects successful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testFinalizeTeamAccuracy2() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "config.xml");
        services = new TeamServicesImpl();

        OperationResult result = services.finalizeTeam(19, 1);
        assertTrue("Result should be successful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>sendOffer(offer)</code> method.
     * </p>
     * <p>
     * Tests it against null offer. expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSendOfferWithNullOffer() throws Exception {
        try {
            services.sendOffer(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>sendOffer(offer)</code> method.
     * </p>
     * <p>
     * Tests it against negative offer id. expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSendOfferWithNegOfferId() throws Exception {
        Offer offer = new Offer();
        offer.setOfferId(-1);

        try {
            services.sendOffer(offer);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>sendOffer(offer)</code> method.
     * </p>
     * <p>
     * Tests it with unknown position id. expects unsuccessful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSendOfferWithUnknownPositionId() throws Exception {
        Offer offer = new Offer();
        offer.setOfferId(1);
        offer.setPositionId(100);

        OperationResult result = services.sendOffer(offer);
        assertFalse("result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>sendOffer(offer)</code> method.
     * </p>
     * <p>
     * Tests it with position already filled. expects unsuccessful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSendOfferWithPositionFilled() throws Exception {
        Offer offer = new Offer();
        offer.setOfferId(1);
        offer.setPositionId(101);

        OperationResult result = services.sendOffer(offer);
        assertFalse("result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>sendOffer(offer)</code> method.
     * </p>
     * <p>
     * Tests it accuracy. expects successful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSendOfferAccuracy() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "config.xml");
        services = new TeamServicesImpl();

        Offer offer = new Offer();
        offer.setOfferId(1);
        offer.setPositionId(1);
        offer.setFromUserId(1);
        offer.setToUserId(1);
        OfferStatus status = new OfferStatus();
        status.setStatusType(OfferStatusType.OFFERED);
        offer.setStatus(status);

        OperationResult result = services.sendOffer(offer);
        assertTrue("result should be successful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>getOffers(userId)</code> method.
     * </p>
     * <p>
     * Tests it against negative id, expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetOffersWithNegativeId() throws Exception {
        try {
            services.getOffers(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>getOffers(userId)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy with userId=1, one offer should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetOffersAccuracy() throws Exception {
        Offer[] offers = services.getOffers(1);
        assertEquals("There should be one offer.", 1, offers.length);
    }

    /**
     * <p>
     * Test for <code>accepteOffer(offerId,userId)</code> method.
     * </p>
     * <p>
     * Tests it against negative offer id, expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testAccepteOfferWithNegOfferId() throws Exception {
        try {
            services.acceptOffer(-1, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>accepteOffer(offerId,userId)</code> method.
     * </p>
     * <p>
     * Tests it against negative user id, expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testAccepteOfferWithNegUserId() throws Exception {
        try {
            services.acceptOffer(1, -1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>accepteOffer(offerId,userId)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, expects successful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testAccepteOfferAccuracy1() throws Exception {
        OperationResult result = services.acceptOffer(1, 1);
        assertTrue("Result should be successful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>rejectOffer(offerId,cause,userId)</code> method.
     * </p>
     * <p>
     * Tests it against negative offer id,expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRejectOfferWithNegOfferId() throws Exception {
        try {
            services.rejectOffer(-1, "cause", 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>rejectOffer(offerId,cause,userId)</code> method.
     * </p>
     * <p>
     * Tests it against negative user id,expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRejectOfferWithNegUserId() throws Exception {
        try {
            services.rejectOffer(1, "cause", -1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }

    }

    /**
     * <p>
     * Test for <code>rejectOffer(offerId,cause,userId)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy with offerId=1, expects successful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRejectOfferAccuracy() throws Exception {
        OperationResult result = services.rejectOffer(1, "cause", 1);
        assertTrue("Result should be successful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>removeMember(resourceId, usreId)</code> method.
     * </p>
     * <p>
     * Tests it against negative resource id.expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveMemberWithNegResourceId() throws Exception {
        try {
            services.removeMember(-1, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>removeMember(resourceId, usreId)</code> method.
     * </p>
     * <p>
     * Tests it against negative user id.expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveMemberWithNegUserId() throws Exception {
        try {
            services.removeMember(1, -1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>removeMember(resourceId, usreId)</code> method.
     * </p>
     * <p>
     * Tests it with unknown resource id.expects <code>UnknownEntityException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveMemberWithUnknownResourceId() throws Exception {
        try {
            services.removeMember(11, 1);
            fail("UnknownEntityException should be thrown.");
        } catch (UnknownEntityException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>removeMember(resourceId, usreId)</code> method.
     * </p>
     * <p>
     * Tests it with error occurring when retrieving resource.expects unsuccessful result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveMemberWithErrorOccurRetrievingResource() throws Exception {
        OperationResult result = services.removeMember(12, 1);
        assertFalse("'result' should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>removeMember(resourceId, usreId)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy. expects successful result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveMemberAccuracy() throws Exception {
        OperationResult result = services.removeMember(1, 1);
        assertTrue("'result' should be successful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>removeMember(resourceId, usreId)</code> method.
     * </p>
     * <p>
     * Tests it with InvalidPositionException occurring when updating position. expects unsuccessful
     * result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveMemberAccuracy2() throws Exception {
        OperationResult result = services.removeMember(1, 100);
        assertFalse("'result' should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>removeMember(resourceId, usreId)</code> method.
     * </p>
     * <p>
     * Tests it with PositionRemovalException occurring when removing position. expects unsuccessful
     * result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveMemberAccuracy3() throws Exception {
        OperationResult result = services.removeMember(1, 105);
        assertFalse("'result' should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>removeMember(resourceId, usreId)</code> method.
     * </p>
     * <p>
     * Tests it with UnknownEntityException occurring when removing position. expects unsuccessful
     * result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveMemberAccuracy4() throws Exception {
        OperationResult result = services.removeMember(1, 106);
        assertFalse("'result' should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>forceFinalization(projectId, userId)</code> method.
     * </p>
     * <p>
     * Tests it against negative project id,expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testForceFinalizationWithNegProjectId() throws Exception {
        try {
            services.forceFinalization(-1, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>forceFinalization(projectId, userId)</code> method.
     * </p>
     * <p>
     * Tests it against negative user id,expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testForceFinalizationWithNegUserId() throws Exception {
        try {
            services.forceFinalization(1, -1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>forceFinalization(projectId, userId)</code> method.
     * </p>
     * <p>
     * Tests it against unknown project id,expects <code>UnknownEntityException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testForceFinalizationWithUnknownProjectId() throws Exception {
        try {
            services.forceFinalization(10, 1);
            fail("UnknownEntityException should be thrown.");
        } catch (UnknownEntityException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>forceFinalization(projectId, userId)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy with projectId=1, expects successful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testForceFinalizationAccuracy() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "config.xml");
        services = new TeamServicesImpl();

        OperationResult result = services.forceFinalization(6, 1);
        assertTrue("'result' should be successful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>forceFinalization(projectId, userId)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy with PositionRemovalException occurring when removing position, expects
     * unsuccessful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testForceFinalizationAccuracy1() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "config1.xml");
        services = new TeamServicesImpl();

        OperationResult result = services.forceFinalization(6, 100);
        assertFalse("'result' should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>forceFinalization(projectId, userId)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy with UnknownEntityException occurring when removing position, expects
     * unsuccessful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testForceFinalizationAccuracy2() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "config1.xml");
        services = new TeamServicesImpl();

        OperationResult result = services.forceFinalization(6, 101);
        assertFalse("'result' should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>forceFinalization(projectId, userId)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy with InvalidPositionException occurring when updating position, expects
     * unsuccessful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testForceFinalizationAccuracy3() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "config1.xml");
        services = new TeamServicesImpl();

        OperationResult result = services.forceFinalization(6, 102);
        assertFalse("'result' should be unsuccessful.", result.isSuccessful());
    }
}
