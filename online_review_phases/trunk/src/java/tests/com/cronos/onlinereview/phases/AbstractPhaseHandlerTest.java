/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases;

import com.topcoder.management.phase.PhaseHandlingException;

import com.topcoder.project.phases.Phase;

import com.topcoder.util.config.ConfigManager;

import java.sql.Connection;

import java.util.HashMap;
import java.util.Map;


/**
 * All test cases for AbstractPhaseHandler class.
 *
 * <p>
 * Version 1.2 change notes : since the email-templates and role-supported has been enhanced.
 * The test cases will try to do on that way while for email content, please check it manually.
 * </p>
 * @author bose_java, waits
 * @version 1.2
 */
public class AbstractPhaseHandlerTest extends BaseTest {
    /**
     * sets up the environment required for test cases for this class.
     *
     * @throws Exception not under test.
     */
    protected void setUp() throws Exception {
        super.setUp();

        ConfigManager configManager = ConfigManager.getInstance();

        configManager.add(PHASE_HANDLER_CONFIG_FILE);
        configManager.add(MANAGER_HELPER_CONFIG_FILE);
        configManager.add(DOC_GENERATOR_CONFIG_FILE);
        configManager.add(EMAIL_CONFIG_FILE);

        // add the component configurations as well
        for (int i = 0; i < COMPONENT_FILE_NAMES.length; i++) {
            configManager.add(COMPONENT_FILE_NAMES[i]);
        }
    }

    /**
     * cleans up the environment required for test cases for this class.
     *
     * @throws Exception not under test.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Tests the constructor AbstractPhaseHandler(String) will null argument.
     *
     * @throws ConfigurationException not under test.
     */
    public void testAbstractPhaseHandlerWithNull() throws ConfigurationException {
        try {
            new AbstractPhaseHandlerSubClass(null);
            fail("AbstractPhaseHandler(String) did not throw IllegalArgumentException for null argument.");
        } catch (IllegalArgumentException ex) {
            // expected.
        }
    }

    /**
     * Tests the constructor AbstractPhaseHandler(String) will empty argument.
     *
     * @throws ConfigurationException not under test.
     */
    public void testAbstractPhaseHandlerWithEmpty() throws ConfigurationException {
        try {
            new AbstractPhaseHandlerSubClass("  ");
            fail("AbstractPhaseHandler(String) did not throw IllegalArgumentException for empty argument.");
        } catch (IllegalArgumentException ex) {
            // expected.
        }
    }

    /**
     * Tests the constructor AbstractPhaseHandler(String) will not-exists argument.
     *
     * @throws Exception into JUnit
     * @since 1.2
     */
    public void testAbstractPhaseHandlerWithNotExistNS()
        throws Exception {
        try {
            new AbstractPhaseHandlerSubClass("notExist");
            fail("The namespace does not exist.");
        } catch (ConfigurationException ex) {
            // expected.
        }
    }

    /**
     * Tests the constructor AbstractPhaseHandler(String) will invalid config, the 'ConnectionFactoryNS' is missing.
     *
     * @throws Exception into JUnit
     * @since 1.2
     */
    public void testAbstractPhaseHandlerWithInvalidCF_ConnectionFactoryNS_invalid()
        throws Exception {
        try {
            new AbstractPhaseHandlerSubClass("com.cronos.onlinereview.phases.AbstractPhaseHandler.Invalid1");
            fail("The namespace is invalid.");
        } catch (ConfigurationException ex) {
            // expected.
        }
    }

    /**
     * Tests the constructor AbstractPhaseHandler(String) will invalid config, the 'ManagerHelperNamespace' is invalid.
     *
     * @throws Exception into JUnit
     * @since 1.2
     */
    public void testAbstractPhaseHandlerWithInvalidCF_ManagerHelperNamespace_invalid()
        throws Exception {
        try {
            new AbstractPhaseHandlerSubClass("com.cronos.onlinereview.phases.AbstractPhaseHandler.Invalid2");
            fail("The namespace is invalid.");
        } catch (ConfigurationException ex) {
            // expected.
        }
    }

    /**
     * Tests the constructor AbstractPhaseHandler(String) will invalid config, the 'StartPhaseEmail' is missing.
     *
     * @throws Exception into JUnit
     * @since 1.2
     */
    public void testAbstractPhaseHandlerWithInvalidCF_StartPhaseEmail_invalid()
        throws Exception {
        try {
            new AbstractPhaseHandlerSubClass("com.cronos.onlinereview.phases.AbstractPhaseHandler.Invalid3");
            fail("The namespace is invalid.");
        } catch (ConfigurationException ex) {
            // expected.
        }
    }

    /**
     * Tests the constructor AbstractPhaseHandler(String) will invalid config, the 'EndPhaseEmail' is missing.
     *
     * @throws Exception into JUnit
     * @since 1.2
     */
    public void testAbstractPhaseHandlerWithInvalidCF_EndPhaseEmail_invalid()
        throws Exception {
        try {
            new AbstractPhaseHandlerSubClass("com.cronos.onlinereview.phases.AbstractPhaseHandler.Invalid4");
            fail("The namespace is invalid.");
        } catch (ConfigurationException ex) {
            // expected.
        }
    }

    /**
     * Tests the constructor AbstractPhaseHandler(String) will invalid config, the 'StartPhaseEmail/SendEmail' is
     * invalid.
     *
     * @throws Exception into JUnit
     * @since 1.2
     */
    public void testAbstractPhaseHandlerWithInvalidCF_SendEmail_invalid()
        throws Exception {
        try {
            new AbstractPhaseHandlerSubClass("com.cronos.onlinereview.phases.AbstractPhaseHandler.Invalid5");
            fail("The namespace is invalid.");
        } catch (ConfigurationException ex) {
            // expected.
        }
    }

    /**
     * Tests the constructor AbstractPhaseHandler(String) will invalid config, the 'StartPhaseEmail/EmailFromAddress'
     * is missing.
     *
     * @throws Exception into JUnit
     * @since 1.2
     */
    public void testAbstractPhaseHandlerWithInvalidCF_EmailFromAddress_missing()
        throws Exception {
        try {
            new AbstractPhaseHandlerSubClass("com.cronos.onlinereview.phases.AbstractPhaseHandler.Invalid6");
            fail("The namespace is invalid.");
        } catch (ConfigurationException ex) {
            // expected.
        }
    }

    /**
     * Tests the constructor AbstractPhaseHandler(String) will valid argument.
     *
     * @throws ConfigurationException not under test.
     * @since 1.2
     */
    @SuppressWarnings("unchecked")
    public void testAbstractPhaseHandler_accuracy() throws ConfigurationException {
        AbstractPhaseHandler handler = new AbstractPhaseHandlerSubClass(ABSTRACT_HANDLER_NAMESPACE);

        //verify
        Map<String, EmailOptions> startPhaseEmailOptions =
            (Map<String, EmailOptions>) getPrivateField(AbstractPhaseHandler.class, handler, "startPhaseEmailOptions");
        assertNotNull("The field is null.", startPhaseEmailOptions);
        assertEquals("The field is invalid.", 6, startPhaseEmailOptions.size());
        assertEquals("The value is invalid.", "topcoder.developer@gmail.com",
                startPhaseEmailOptions.get("default").getFromAddress());
        assertEquals("The value is invalid.", "Phase Start", startPhaseEmailOptions.get("Manager").getSubject());
        assertNotNull("The value is invalid.", startPhaseEmailOptions.get("Reviewer"));
        assertEquals("The value is invalid.", 2, startPhaseEmailOptions.get("Reviewer").getPriority());
        //verify
        Map<String, EmailOptions> endPhaseEmailOptions =
            (Map<String, EmailOptions>) getPrivateField(AbstractPhaseHandler.class, handler, "endPhaseEmailOptions");
        assertNotNull("The field is null.", endPhaseEmailOptions);
        assertEquals("The field is invalid.", 6, endPhaseEmailOptions.size());
        assertEquals("The value is invalid.", "topcoder.developer@gmail.com",
                endPhaseEmailOptions.get("default").getFromAddress());
        assertEquals("The value is invalid.", "Phase Start", endPhaseEmailOptions.get("Manager").getSubject());
        assertNotNull("The value is invalid.", endPhaseEmailOptions.get("Reviewer"));
        assertEquals("The value is invalid.", 0, endPhaseEmailOptions.get("Reviewer").getPriority());
    }

    /**
     * Tests the constructor AbstractPhaseHandler(String) will valid argument. The configuration is the simplest
     * config.
     *
     * @throws ConfigurationException not under test.
     * @since 1.2
     */
    public void testAbstractPhaseHandlerWithValid() throws ConfigurationException {
        new AbstractPhaseHandlerSubClass("com.cronos.onlinereview.phases.AbstractPhaseHandler.simplest");
    }

    /**
     * Tests the AbstractPhaseHandler.sendEmail(Phase) method with null.
     *
     * @throws PhaseHandlingException not under test.
     * @throws ConfigurationException not under test.
     * @since 1.0
     */
    public void testSendEmail_nullPhase() throws PhaseHandlingException, ConfigurationException {
        AbstractPhaseHandler handler = new AbstractPhaseHandlerSubClass(ABSTRACT_HANDLER_NAMESPACE);

        try {
            handler.sendEmail(null);
            fail("sendEmail(Phase) did not throw IllegalArgumentException for null argument.");
        } catch (IllegalArgumentException ex) {
            // expected.
        }
    }

    /**
     * Tests the AbstractPhaseHandler.sendEmail(Phase, Map) method with null phase.
     *
     * @throws PhaseHandlingException not under test.
     * @throws ConfigurationException not under test.
     * @since 1.0
     */
    public void testSendEmailpb_nullPhase() throws PhaseHandlingException, ConfigurationException {
        AbstractPhaseHandler handler = new AbstractPhaseHandlerSubClass(ABSTRACT_HANDLER_NAMESPACE);

        try {
            handler.sendEmail(null, new HashMap<String, Object>());
            fail("sendEmail(Phase, Map) did not throw IllegalArgumentException for null argument.");
        } catch (IllegalArgumentException ex) {
            // expected.
        }
    }

    /**
     * Tests the AbstractPhaseHandler.sendEmail(Phase, Map) method with null Map.
     *
     * @throws Exception into JUnit
     * @since 1.0
     */
    public void testSendEmailpb_nullMap() throws Exception {
        AbstractPhaseHandler handler = new AbstractPhaseHandlerSubClass(ABSTRACT_HANDLER_NAMESPACE);

        try {
            handler.sendEmail(createPhase(1, 1, "Scheduled", 1, "Registration"), null);
            fail("sendEmail(Phase, Map) did not throw IllegalArgumentException for null Map.");
        } catch (IllegalArgumentException ex) {
            //expected
        }
    }

    /**
     * Tests the AbstractPhaseHandler.sendEmail(Phase, Map) method with null Map.
     *
     * @throws Exception into JUnit
     * @since 1.2
     */
    public void testSendEmailpb_invalidMap() throws Exception {
        AbstractPhaseHandler handler = new AbstractPhaseHandlerSubClass(ABSTRACT_HANDLER_NAMESPACE);

        try {
            Map<String, Object> values = new HashMap<String, Object>();

            //invalid value
            values.put("SUBMITTER", null);
            handler.sendEmail(createPhase(1, 1, "Scheduled", 1, "Registration"), values);
            fail("sendEmail(Phase, Map) did not throw IllegalArgumentException for invalid Map.");
        } catch (IllegalArgumentException ex) {
            //expected
        }
    }

    /**
     * Tests the AbstractPhaseHandler.sendEmail(Phase, Map) method with null Map.
     *
     * @throws Exception into JUnit
     * @since 1.2
     */
    public void testSendEmailpb_invalidMap2() throws Exception {
        AbstractPhaseHandler handler = new AbstractPhaseHandlerSubClass(ABSTRACT_HANDLER_NAMESPACE);

        try {
            Map<String, Object> values = new HashMap<String, Object>();

            //invalid value
            values.put(" ", "value");
            handler.sendEmail(createPhase(1, 1, "Scheduled", 1, "Registration"), values);
            fail("sendEmail(Phase, Map) did not throw IllegalArgumentException for invalid Map.");
        } catch (IllegalArgumentException ex) {
            //expected
        }
    }

    /**
     * Tests the AbstractPhaseHandler.sendEmail(Phase) method with Scheduled phase status.
     * Update the case in version 1.2.
     *
     * @throws Exception not under test.
     * @since 1.0
     */
    public void testSendEmailWithStartPhase() throws Exception {
        AbstractPhaseHandler handler = new AbstractPhaseHandlerSubClass(ABSTRACT_HANDLER_NAMESPACE);

        try {
            cleanTables();
            setupProjectResourcesNotification("All");

            Phase phase = createPhase(1, 1, "Scheduled", 1, "Registration");
            handler.sendEmail(phase);

            // manually check mailbox.
        } finally {
            cleanTables();
        }
    }

    /**
     * Tests the AbstractPhaseHandler.sendEmail(Phase) method with Open phase status.
     * Update the case in version 1.2.
     * @throws Exception not under test.
     * @since 1.0
     */
    public void testSendEmailWithStopPhase() throws Exception {
        AbstractPhaseHandler handler = new AbstractPhaseHandlerSubClass(ABSTRACT_HANDLER_NAMESPACE);

        try {
            cleanTables();
            setupProjectResourcesNotification("All");

            Phase phase = createPhase(1, 2, "Open", 1, "Registration");

            handler.sendEmail(phase);

            // manually check mailbox.
        } finally {
            cleanTables();
        }
    }

    /**
     * Tests AbstractPhaseHandler.createConnection() for accuracy.
     *
     * @throws Exception not under test.
     * @since 1.0
     */
    public void testCreateConnection() throws Exception {
        AbstractPhaseHandler handler = new AbstractPhaseHandlerSubClass(ABSTRACT_HANDLER_NAMESPACE);
        Connection conn = null;

        try {
            conn = handler.createConnection();
            assertNotNull("create Connection returned null.", conn);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * Tests AbstractPhaseHandler.getManagerHelper() returns non-null.
     *
     * @throws Exception not under test.
     * @since 1.0
     */
    public void testGetManagerHelper() throws Exception {
        AbstractPhaseHandler handler = new AbstractPhaseHandlerSubClass(ABSTRACT_HANDLER_NAMESPACE);
        assertNotNull("ManagerHelper not instantiated.", handler.getManagerHelper());
    }
}
