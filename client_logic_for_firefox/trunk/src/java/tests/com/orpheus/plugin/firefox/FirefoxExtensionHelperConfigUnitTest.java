/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox;

import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

import java.util.Map;


/**
 * <p>
 * Tests functionality and error cases for the config related methods of {@link FirefoxExtensionHelper} class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FirefoxExtensionHelperConfigUnitTest extends TestCase {
    /** Represents the valid namespace for testing. */
    private static final String NAME_SPACE = FirefoxExtensionHelper.class.getName();

    /** Represents the JSObject for testing. */
    private MockJSObject jsObject = null;

    /** Represents the {@link FirefoxExtensionHelper} instance used for testing. */
    private FirefoxExtensionHelper helper;

    /**
     * <p>
     * Sets up the test environment. The test instance is created. The test configuration namespace will be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        UnitTestHelper.addConfig();

        // create the FirefoxExtensionHelper instance and initialize it
        helper = new FirefoxExtensionHelper();
        jsObject = new MockJSObject();
        UnitTestHelper.setPrivateField(helper.getClass(), helper, "clientWindow", jsObject);
    }

    /**
     * <p>
     * Clears the test environment. The test configuration namespace will be cleared.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        UnitTestHelper.clearConfig();
    }

    /**
     * <p>
     * Tests the accuracy of constructor {@link FirefoxExtensionHelper#FirefoxExtensionHelper()}. No exception is
     * expected.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testFirefoxExtensionHelper_Accuracy() throws Exception {
        assertNotNull("The FirefoxExtensionHelper instance should be created properly.", new FirefoxExtensionHelper());
    }

    /**
     * <p>
     * Tests the accuracy of method {@link FirefoxExtensionHelper#initialize()} when the given namespace is correct,
     * all the config values should be loaded properly.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInitializeNoArg_Accuracy() throws Exception {
        helper.initialize();

        // check
        assertEquals("The domainParameter should be loaded.", "domainParameter",
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "domainParameter"));
        assertNotNull("The hashMatchURL should be loaded.",
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "hashMatchURL"));
        assertEquals("The hashMatchDomainParameter should be loaded.", "hashMatchDomainParameter",
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "hashMatchDomainParameter"));
        assertEquals("The hashMatchSequenceNumberParameter should be loaded.", "hashMatchSequenceNumberParameter",
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "hashMatchSequenceNumberParameter"));
        assertEquals("The targetTextParameter should be loaded.", "targetTextParameter",
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "targetTextParameter"));
        assertEquals("The defaultPopupHeight should be loaded.", "" + 300,
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "defaultPopupHeight").toString());
        assertEquals("The defaultPopupWidth should be loaded.", "" + 400,
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "defaultPopupWidth").toString());
        assertNotNull("The pageChangedURL should be loaded.",
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "pageChangedURL"));
        assertNotNull("The orpheusUrl should be loaded.",
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "pollURL"));
        assertEquals("The orpheusPollTime should be loaded.", "" + 1,
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "pollTime").toString());
        assertEquals("The orpheusTimestampParameter should be loaded.", "timestampParameter",
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "timestampParameter"));

        assertNotNull("The persistence should be loaded.",
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "persistence"));
        assertNull("The server should be the default value.",
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "server"));
        assertEquals("The eventPages should be loaded.", 7,
            ((Map) UnitTestHelper.getPrivateField(helper.getClass(), helper, "eventPages")).size());
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the given namespace is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_NullNamespace() throws Exception {
        try {
            helper.initialize(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the given namespace is an empty string,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_EmptyNamespace() throws Exception {
        try {
            helper.initialize(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the given namespace does not exist,
     * FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_NotExistNamespace() throws Exception {
        try {
            helper.initialize("NotExist");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the domain_parameter_name property is
     * missing, FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_MissingDomainParameterName() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/MissingDomainParameterName.xml");

        try {
            helper.initialize("MissingDomainParameterName");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the domain_parameter_name property value
     * is an empty string, FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_EmptyDomainParameterName() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/EmptyDomainParameterName.xml");

        try {
            helper.initialize("EmptyDomainParameterName");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the hash_match_URL property is missing,
     * FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_MissingHashMatchURL() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/MissingHashMatchURL.xml");

        try {
            helper.initialize("MissingHashMatchURL");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the hash_match_URL property value is an
     * empty string, FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_EmptyHashMatchURL() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/EmptyHashMatchURL.xml");

        try {
            helper.initialize("EmptyHashMatchURL");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the hash_match_URL property value is not
     * a valid url, FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_UnknownProtocolhashMatchURL() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/UnknownProtocolhashMatchURL.xml");

        try {
            helper.initialize("UnknownProtocolhashMatchURL");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the hash_match_domain_parameter property
     * is missing, FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_MissHashMatchDomainParameter() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/MissHashMatchDomainParameter.xml");

        try {
            helper.initialize("MissHashMatchDomainParameter");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the hash_match_domain_parameter property
     * value is an empty string, FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_EmptyHashMatchDomainParameter() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/EmptyHashMatchDomainParameter.xml");

        try {
            helper.initialize("EmptyHashMatchDomainParameter");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the hash_match_sequence_number_parameter
     * property is missing, FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_MissingHashMatchSequenceNumberParameter() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/MissingHashMatchSequenceNumberParameter.xml");

        try {
            helper.initialize("MissingHashMatchSequenceNumberParameter");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the hash_match_sequence_number_parameter
     * property value is an empty string, FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_EmptyHashMatchSequenceNumberParameter() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/EmptyHashMatchSequenceNumberParameter.xml");

        try {
            helper.initialize("EmptyHashMatchSequenceNumberParameter");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the target_text_parameter property is
     * missing, FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_MissingTargetTextParameter() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/MissingTargetTextParameter.xml");

        try {
            helper.initialize("MissingTargetTextParameter");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the target_text_parameter property value
     * is an empty string, FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_EmptyTargetTextParameter() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/EmptyTargetTextParameter.xml");

        try {
            helper.initialize("EmptyTargetTextParameter");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the default_height property is missing,
     * default value will be used.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_MissingDefaultPopupHeight() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/MissingDefaultPopupHeight.xml");
        helper.initialize("MissingDefaultPopupHeight");
        assertEquals("The defaultPopupHeight should be default.", "" + 200,
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "defaultPopupHeight").toString());
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the default_height property value is an
     * empty string, FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_EmptyDefaultPopupHeight() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/EmptyDefaultPopupHeight.xml");

        try {
            helper.initialize("EmptyDefaultPopupHeight");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the default_height property value is not
     * an integer, FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_NotNumricDefaultPopupHeight() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/NotNumricDefaultPopupHeight.xml");

        try {
            helper.initialize("NotNumricDefaultPopupHeight");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the default_height property value is not
     * positive integer, FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_NotPositiveDefaultPopupHeight() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/NotPositiveDefaultPopupHeight.xml");

        try {
            helper.initialize("NotPositiveDefaultPopupHeight");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the default_width property is missing,
     * default value will be used.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_MissingDefaultPopupWidth() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/MissingDefaultPopupWidth.xml");
        helper.initialize("MissingDefaultPopupWidth");
        assertEquals("The defaultPopupWidth should be default.", "" + 200,
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "defaultPopupWidth").toString());
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the default_width property value is an
     * empty string, FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_EmptyDefaultPopupWidth() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/EmptyDefaultPopupWidth.xml");

        try {
            helper.initialize("EmptyDefaultPopupWidth");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the default_width property value is not
     * an integer, FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_NotNumricDefaultPopupWidth() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/NotNumricDefaultPopupWidth.xml");

        try {
            helper.initialize("NotNumricDefaultPopupWidth");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the default_width property value is not
     * positive integer, FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_NotPositiveDefaultPopupWidth() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/NotPositiveDefaultPopupWidth.xml");

        try {
            helper.initialize("NotPositiveDefaultPopupWidth");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the page_changed_URL property is
     * missing, FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_MissingPageChangedURL() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/MissingPageChangedURL.xml");

        try {
            helper.initialize("MissingPageChangedURL");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the page_changed_URL property value is
     * an empty string, FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_EmptyPageChangedURL() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/EmptyPageChangedURL.xml");

        try {
            helper.initialize("EmptyPageChangedURL");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the event_pages property is missing,
     * FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_MissingEventPages() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/MissingEventPages.xml");

        try {
            helper.initialize("MissingEventPages");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the event_pages property is invalid,
     * FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_OneParamEventPages() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/OneParamEventPages.xml");

        try {
            helper.initialize("OneParamEventPages");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the event_pages property is invalid,
     * FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_EmptyTypeNameEventPages() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/EmptyTypeNameEventPages.xml");

        try {
            helper.initialize("EmptyTypeNameEventPages");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the event_pages property is invalid,
     * FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_NotEixstEvnetTypeEventPages() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/NotEixstEvnetTypeEventPages.xml");

        try {
            helper.initialize("NotEixstEvnetTypeEventPages");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the event_pages property is invalid,
     * FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_EmptyURLEventPages() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/EmptyURLEventPages.xml");

        try {
            helper.initialize("EmptyURLEventPages");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the persistence_class property is
     * missing, FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_MissingPersistence() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/MissingPersistence.xml");

        try {
            helper.initialize("MissingPersistence");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the persistence_class property value is
     * an empty string, FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_EmptyPersistence() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/EmptyPersistence.xml");

        try {
            helper.initialize("EmptyPersistence");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the persistence_class property value is
     * invalid, FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_ClassNotFindPersistence() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/ClassNotFindPersistence.xml");

        try {
            helper.initialize("ClassNotFindPersistence");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the persistence_class property value is
     * invalid, FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_NotExtensionPersistencePersistence() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/NotExtensionPersistencePersistence.xml");

        try {
            helper.initialize("NotExtensionPersistencePersistence");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the orpheus_url property is missing,
     * FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_MissingOrpheusUrl() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/MissingOrpheusUrl.xml");

        try {
            helper.initialize("MissingOrpheusUrl");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the orpheus_url property value is an
     * empty string, FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_EmptyOrpheusRrl() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/EmptyOrpheusRrl.xml");

        try {
            helper.initialize("EmptyOrpheusRrl");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the orpheus_url property value is not a
     * valid url, FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_UnknownProtocolUrl() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/UnknownProtocolUrl.xml");

        try {
            helper.initialize("UnknownProtocolUrl");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the orpheus_poll_time property is
     * missing, FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_MissingOrpheusPollTime() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/MissingOrpheusPollTime.xml");

        try {
            helper.initialize("MissingOrpheusPollTime");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the orpheus_poll_time property value is
     * an empty string, FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_EmptyOrpheusPollTime() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/EmptyOrpheusPollTime.xml");

        try {
            helper.initialize("EmptyOrpheusPollTime");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the orpheus_poll_time property value is
     * invalid, FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_NotPositiveOrpheusPollTime() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/NotPositiveOrpheusPollTime.xml");

        try {
            helper.initialize("NotPositiveOrpheusPollTime");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the orpheus_timestamp_parameter property
     * is missing, FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_MissingOrpheusTimestampParameter() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/MissingOrpheusTimestampParameter.xml");

        try {
            helper.initialize("MissingOrpheusTimestampParameter");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link FirefoxExtensionHelper#initialize(String)} when the orpheus_timestamp_parameter property
     * value is an empty string, FirefoxExtensionConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInitializeOneArg_EmptyOrpheusTimestampParameter() throws Exception {
        ConfigManager.getInstance().add("InvalidConfig/EmptyOrpheusTimestampParameter.xml");

        try {
            helper.initialize("EmptyOrpheusTimestampParameter");
            fail("FirefoxExtensionConfigurationException should be thrown.");
        } catch (FirefoxExtensionConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method {@link FirefoxExtensionHelper#initialize(String)} when the given namespace is
     * correct, all the config values should be loaded properly.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInitializeOneArg_Accuracy() throws Exception {
        helper.initialize(NAME_SPACE);

        // check
        assertEquals("The domainParameter should be loaded.", "domainParameter",
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "domainParameter"));
        assertNotNull("The hashMatchURL should be loaded.",
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "hashMatchURL"));
        assertEquals("The hashMatchDomainParameter should be loaded.", "hashMatchDomainParameter",
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "hashMatchDomainParameter"));
        assertEquals("The hashMatchSequenceNumberParameter should be loaded.", "hashMatchSequenceNumberParameter",
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "hashMatchSequenceNumberParameter"));
        assertEquals("The targetTextParameter should be loaded.", "targetTextParameter",
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "targetTextParameter"));
        assertEquals("The defaultPopupHeight should be loaded.", "" + 300,
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "defaultPopupHeight").toString());
        assertEquals("The defaultPopupWidth should be loaded.", "" + 400,
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "defaultPopupWidth").toString());
        assertNotNull("The pageChangedURL should be loaded.",
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "pageChangedURL"));
        assertNotNull("The orpheusUrl should be loaded.",
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "pollURL"));
        assertEquals("The orpheusPollTime should be loaded.", "" + 1,
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "pollTime").toString());
        assertEquals("The orpheusTimestampParameter should be loaded.", "timestampParameter",
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "timestampParameter"));

        assertNotNull("The persistence should be loaded.",
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "persistence"));
        assertNull("The server should be the default value.",
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "server"));
        assertEquals("The eventPages should be loaded.", 7,
            ((Map) UnitTestHelper.getPrivateField(helper.getClass(), helper, "eventPages")).size());
    }
}
