/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence;

import java.net.URL;
import java.util.Date;

import javax.xml.namespace.QName;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.registration.entities.Contest;
import com.topcoder.registration.entities.ContestRole;
import com.topcoder.registration.entities.User;
import com.topcoder.registration.persistence.client.ResourceManagerServiceClient;
import com.topcoder.registration.persistence.client.ResourceManagerServiceClientCreationException;
import com.topcoder.registration.persistence.converter.RegistrationEntitiesToResourceConverterImpl;

/**
 * <p>
 * Unit test for <code>{@link ResourceManagerRegistrationPersistence}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ResourceManagerRegistrationPersistenceUnitTests extends BaseTestCase {

    /**
     * <p>
     * The <code>ResourceManagerRegistrationPersistence</code> to be tested.
     * </p>
     */
    private ResourceManagerRegistrationPersistence persistence;

    /**
     * <p>
     * Set up the test case.
     * </p>
     */
    @Override
    protected void setUp() {
        this.persistence = new ResourceManagerRegistrationPersistence(CLIENT,
            new RegistrationEntitiesToResourceConverterImpl());
    }

    /**
     * <p>
     * Tear down the test case.
     * </p>
     */
    @Override
    protected void tearDown() {
        this.persistence = null;
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerRegistrationPersistence
     * #ResourceManagerRegistrationPersistence(
     * ResourceManagerServiceClient, RegistrationEntitiesToResourceConverter)}.
     * </p>
     *
     * <p>
     * The instance of <code>ResourceManagerRegistrationPersistence</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_Accuracy() throws Exception {
        assertNotNull("ResourceManagerRegistrationPersistence should be created", this.persistence);
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerRegistrationPersistence
     * #ResourceManagerRegistrationPersistence(
     * ResourceManagerServiceClient, RegistrationEntitiesToResourceConverter)}.
     * </p>
     *
     * <p>
     * Given client is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_NullClient() throws Exception {
        try {
            new ResourceManagerRegistrationPersistence(null,
                new RegistrationEntitiesToResourceConverterImpl());
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerRegistrationPersistence
     * #ResourceManagerRegistrationPersistence(
     * ResourceManagerServiceClient, RegistrationEntitiesToResourceConverter)}.
     * </p>
     *
     * <p>
     * Given converter is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_NullConverter() throws Exception {
        try {
            new ResourceManagerRegistrationPersistence(CLIENT, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerRegistrationPersistence
     * #ResourceManagerRegistrationPersistence(ConfigurationObject)}.
     * </p>
     *
     * <p>
     * Given <code>ConfigurationObject</code> is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_NullConfigurationObject() throws Exception {
        try {
            new ResourceManagerRegistrationPersistence(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerRegistrationPersistence
     * #ResourceManagerRegistrationPersistence(ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The child "ws_wrapper_resource_management" is missing, <code>ConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_MissingChild() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("config");
        try {
            new ResourceManagerRegistrationPersistence(config);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerRegistrationPersistence
     * #ResourceManagerRegistrationPersistence(ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "url" is missing, <code>ConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_URLPropertyMissing() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("config");
        config.addChild(new DefaultConfigurationObject("ws_wrapper_resource_management"));
        try {
            new ResourceManagerRegistrationPersistence(config);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerRegistrationPersistence
     * #ResourceManagerRegistrationPersistence(ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "url" has an empty value, <code>ConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_URLPropertyEmptyValue() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("config");
        ConfigurationObject child = new DefaultConfigurationObject("ws_wrapper_resource_management");
        child.setPropertyValue("url", " ");
        config.addChild(child);
        try {
            new ResourceManagerRegistrationPersistence(config);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerRegistrationPersistence
     * #ResourceManagerRegistrationPersistence(ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "url" has a value which is not type of <code>String</code>,
     * <code>ConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_URLPropertyNotString() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("config");
        ConfigurationObject child = new DefaultConfigurationObject("ws_wrapper_resource_management");
        child.setPropertyValue("url", new Date());
        config.addChild(child);
        try {
            new ResourceManagerRegistrationPersistence(config);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerRegistrationPersistence
     * #ResourceManagerRegistrationPersistence(ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "url" has multiple values,
     * <code>ConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_URLPropertyHasMultipleValue() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("config");
        ConfigurationObject child = new DefaultConfigurationObject("ws_wrapper_resource_management");
        child.setPropertyValues("url", new String[]{"1", "2"});
        config.addChild(child);
        try {
            new ResourceManagerRegistrationPersistence(config);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerRegistrationPersistence
     * #ResourceManagerRegistrationPersistence(ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "converter_key" is missing, <code>ConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_ConverterKeyPropertyMissing() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("config");
        ConfigurationObject child = new DefaultConfigurationObject("ws_wrapper_resource_management");
        child.setPropertyValue("url", WSDL);
        config.addChild(child);
        try {
            new ResourceManagerRegistrationPersistence(config);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerRegistrationPersistence
     * #ResourceManagerRegistrationPersistence(ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "converter_key" has an empty value, <code>ConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_ConverterKeyPropertyEmptyValue() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("config");
        ConfigurationObject child = new DefaultConfigurationObject("ws_wrapper_resource_management");
        child.setPropertyValue("url", WSDL);
        child.setPropertyValue("converter_key", " ");
        config.addChild(child);
        try {
            new ResourceManagerRegistrationPersistence(config);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerRegistrationPersistence
     * #ResourceManagerRegistrationPersistence(ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "converter_key" has a value which is not type of <code>String</code>,
     * <code>ConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_ConverterKeyPropertyNotString() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("config");
        ConfigurationObject child = new DefaultConfigurationObject("ws_wrapper_resource_management");
        child.setPropertyValue("url", WSDL);
        child.setPropertyValue("converter_key", new Date());
        config.addChild(child);
        try {
            new ResourceManagerRegistrationPersistence(config);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerRegistrationPersistence
     * #ResourceManagerRegistrationPersistence(ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "converter_key" has multiple values,
     * <code>ConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_ConverterKeyPropertyHasMultipleValue() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("config");
        ConfigurationObject child = new DefaultConfigurationObject("ws_wrapper_resource_management");
        child.setPropertyValue("url", WSDL);
        child.setPropertyValues("converter_key", new String[]{"1", "2"});
        config.addChild(child);
        try {
            new ResourceManagerRegistrationPersistence(config);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerRegistrationPersistence
     * #ResourceManagerRegistrationPersistence(ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "object_factory_config_child_name" is missing,
     * <code>ConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_ObjectFactoryChildNamePropertyMissing() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("config");
        ConfigurationObject child = new DefaultConfigurationObject("ws_wrapper_resource_management");
        child.setPropertyValue("url", WSDL);
        child.setPropertyValue("converter_key", "converterKey");
        config.addChild(child);
        try {
            new ResourceManagerRegistrationPersistence(config);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerRegistrationPersistence
     * #ResourceManagerRegistrationPersistence(ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "object_factory_config_child_name" has empty value,
     * <code>ConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_ObjectFactoryChildNamePropertyEmptyValue() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("config");
        ConfigurationObject child = new DefaultConfigurationObject("ws_wrapper_resource_management");
        child.setPropertyValue("url", WSDL);
        child.setPropertyValue("converter_key", "converterKey");
        child.setPropertyValue("object_factory_config_child_name", " ");
        config.addChild(child);
        try {
            new ResourceManagerRegistrationPersistence(config);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerRegistrationPersistence
     * #ResourceManagerRegistrationPersistence(ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "object_factory_config_child_name" has multiple values,
     * <code>ConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_ObjectFactoryChildNamePropertyHasMultipleValues() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("config");
        ConfigurationObject child = new DefaultConfigurationObject("ws_wrapper_resource_management");
        child.setPropertyValue("url", WSDL);
        child.setPropertyValue("converter_key", "converterKey");
        child.setPropertyValues("object_factory_config_child_name", new String[]{"1", "2"});
        config.addChild(child);
        try {
            new ResourceManagerRegistrationPersistence(config);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerRegistrationPersistence
     * #ResourceManagerRegistrationPersistence(ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The child with name given by "object_factory_config_child_name" property is missing,
     * <code>ConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_ObjectFactoryChildMissing() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("config");
        ConfigurationObject child = new DefaultConfigurationObject("ws_wrapper_resource_management");
        child.setPropertyValue("url", WSDL);
        child.setPropertyValue("converter_key", "converterKey");
        child.setPropertyValue("object_factory_config_child_name", "objectFactoryChild");
        config.addChild(child);
        try {
            new ResourceManagerRegistrationPersistence(config);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerRegistrationPersistence
     * #ResourceManagerRegistrationPersistence(ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The object factory child is invalid,
     * <code>ConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_ObjectFactoryChildInvalid() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("config");

        ConfigurationObject child = new DefaultConfigurationObject("ws_wrapper_resource_management");
        child.setPropertyValue("url", WSDL);
        child.setPropertyValue("converter_key", "converterKey");
        child.setPropertyValue("object_factory_config_child_name", "objectFactoryChild");
        config.addChild(child);

        //The object factory child is completely an empty ConfigurationObject
        ConfigurationObject ofChild = new DefaultConfigurationObject("objectFactoryChild");
        config.addChild(ofChild);

        try {
            new ResourceManagerRegistrationPersistence(config);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerRegistrationPersistence
     * #ResourceManagerRegistrationPersistence(ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The object factory returns an object which is not type of <code>RegistrationEntitiesToResourceConverter</code>,
     * <code>ConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_WrongTypeCreatedByObjectFactory() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("config");

        ConfigurationObject child = new DefaultConfigurationObject("ws_wrapper_resource_management");
        child.setPropertyValue("url", WSDL);
        child.setPropertyValue("converter_key", "converterKey");
        child.setPropertyValue("object_factory_config_child_name", "objectFactoryChild");
        config.addChild(child);

        ConfigurationObject ofChild = new DefaultConfigurationObject("objectFactoryChild");
        ConfigurationObject converterChild = new DefaultConfigurationObject("converterKey");
        converterChild.setPropertyValue("type", "java.lang.String");
        ofChild.addChild(converterChild);
        config.addChild(ofChild);

        try {
            new ResourceManagerRegistrationPersistence(config);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerRegistrationPersistence
     * #ResourceManagerRegistrationPersistence(ConfigurationObject)}.
     * </p>
     *
     * <p>
     * Error occurs while creating <code>ResourceManagerServiceClient</code>,
     * <code>ResourceManagerServiceClientCreationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_ResourceManagerServiceClientCreationException() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("config");

        ConfigurationObject child = new DefaultConfigurationObject("ws_wrapper_resource_management");
        child.setPropertyValue("url", "http://www.google.com");
        child.setPropertyValue("converter_key", "converterKey");
        child.setPropertyValue("object_factory_config_child_name", "objectFactoryChild");
        config.addChild(child);

        ConfigurationObject ofChild = new DefaultConfigurationObject("objectFactoryChild");
        ConfigurationObject converterChild = new DefaultConfigurationObject("converterKey");
        converterChild.setPropertyValue("type", RegistrationEntitiesToResourceConverterImpl.class.getName());
        ofChild.addChild(converterChild);
        config.addChild(ofChild);

        try {
            new ResourceManagerRegistrationPersistence(config);
            fail("ResourceManagerServiceClientCreationException expected");
        } catch (ResourceManagerServiceClientCreationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor {@link ResourceManagerRegistrationPersistence
     * #ResourceManagerRegistrationPersistence(ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The instance of <code>ResourceManagerRegistrationPersistence</code>
     * is successfully created with given <code>ConfigurationObject</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_Accuracy() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("config");

        ConfigurationObject child = new DefaultConfigurationObject("ws_wrapper_resource_management");
        child.setPropertyValue("url", WSDL);
        child.setPropertyValue("converter_key", "converterKey");
        child.setPropertyValue("object_factory_config_child_name", "objectFactoryChild");
        config.addChild(child);

        ConfigurationObject ofChild = new DefaultConfigurationObject("objectFactoryChild");
        ConfigurationObject converterChild = new DefaultConfigurationObject("converterKey");
        converterChild.setPropertyValue("type", RegistrationEntitiesToResourceConverterImpl.class.getName());
        ofChild.addChild(converterChild);
        config.addChild(ofChild);

        assertNotNull("ResourceManagerRegistrationPersistence should be created.",
            new ResourceManagerRegistrationPersistence(config));
    }

    /**
     * <p>
     * Test method {@link ResourceManagerRegistrationPersistence#register(Contest, User, ContestRole)}.
     * </p>
     *
     * <p>
     * Given contest is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRegister_NullContest() throws Exception {
        try {
            this.persistence.register(null, this.createUser(1L), this.createContestRole(1L));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerRegistrationPersistence#register(Contest, User, ContestRole)}.
     * </p>
     *
     * <p>
     * Given user is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRegister_NullUser() throws Exception {
        try {
            this.persistence.register(this.createContest(1L), null, this.createContestRole(1L));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerRegistrationPersistence#register(Contest, User, ContestRole)}.
     * </p>
     *
     * <p>
     * Given contest role is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRegister_NullContestRole() throws Exception {
        try {
            this.persistence.register(this.createContest(1L), this.createUser(1L), null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerRegistrationPersistence#register(Contest, User, ContestRole)}.
     * </p>
     *
     * <p>
     * The array of <code>ResoureRole</code> returned by service client is empty,
     * cannot match <code>ContestRole</code> to <code>ResoureRole</code>,
     * <code>RegistrationEntitiesToResourceConversionException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRegister_RegistrationEntitiesToResourceConversionException() throws Exception {
        this.persistence = new ResourceManagerRegistrationPersistence(
            new Client1(WSDL), new RegistrationEntitiesToResourceConverterImpl());
        try {
            this.persistence.register(this.createContest(1L), this.createUser(1L), this.createContestRole(1L));
            fail("RegistrationEntitiesToResourceConversionException expected");
        } catch (RegistrationEntitiesToResourceConversionException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerRegistrationPersistence#register(Contest, User, ContestRole)}.
     * </p>
     *
     * <p>
     * The <code>User.handle</code> is null, it will be used as the operator,
     * Then the service client will throw IAE, and that IAE should be wrapped to
     * <code>ResourceManagementException</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRegister_ResourceManagementException() throws Exception {
        User user = this.createUser(1L);
        user.setHandle(null);
        try {
            this.persistence.register(this.createContest(1L), user, this.createContestRole(1L));
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerRegistrationPersistence#register(Contest, User, ContestRole)}.
     * </p>
     *
     * <p>
     * Accuracy tests, no exception expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRegister_Accuracy() throws Exception {
        Contest contest = this.createContest(1L);
        User user = this.createUser(MockResourceManager.CONVERTED_RESOURCE_ID);
        ContestRole contestRole = this.createContestRole(1L);

        this.persistence.register(contest, user, contestRole);
    }

    /**
     * <p>
     * Test method {@link ResourceManagerRegistrationPersistence#unregister(Contest, User, ContestRole)}.
     * </p>
     *
     * <p>
     * Given contest is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUnregister_NullContest() throws Exception {
        try {
            this.persistence.unregister(null, this.createUser(1L), this.createContestRole(1L));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerRegistrationPersistence#unregister(Contest, User, ContestRole)}.
     * </p>
     *
     * <p>
     * Given user is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUnregister_NullUser() throws Exception {
        try {
            this.persistence.unregister(this.createContest(1L), null, this.createContestRole(1L));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerRegistrationPersistence#unregister(Contest, User, ContestRole)}.
     * </p>
     *
     * <p>
     * Given contest role is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUnregister_NullContestRole() throws Exception {
        try {
            this.persistence.unregister(this.createContest(1L), this.createUser(1L), null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerRegistrationPersistence#unregister(Contest, User, ContestRole)}.
     * </p>
     *
     * <p>
     * The array of <code>ResoureRole</code> returned by service client is empty,
     * cannot match <code>ContestRole</code> to <code>ResoureRole</code>,
     * <code>RegistrationEntitiesToResourceConversionException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUnregister_RegistrationEntitiesToResourceConversionException() throws Exception {
        this.persistence = new ResourceManagerRegistrationPersistence(
            new Client1(WSDL), new RegistrationEntitiesToResourceConverterImpl());
        try {
            this.persistence.unregister(this.createContest(1L), this.createUser(1L), this.createContestRole(1L));
            fail("RegistrationEntitiesToResourceConversionException expected");
        } catch (RegistrationEntitiesToResourceConversionException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerRegistrationPersistence#unregister(Contest, User, ContestRole)}.
     * </p>
     *
     * <p>
     * The <code>User.handle</code> is null, it will be used as the operator,
     * Then the service client will throw IAE, and that IAE should be wrapped to
     * <code>ResourceManagementException</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUnregister_ResourceManagementException() throws Exception {
        User user = this.createUser(1L);
        user.setHandle(null);
        try {
            this.persistence.unregister(this.createContest(1L), user, this.createContestRole(1L));
            fail("ResourceManagementException expected");
        } catch (ResourceManagementException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ResourceManagerRegistrationPersistence#unregister(Contest, User, ContestRole)}.
     * </p>
     *
     * <p>
     * Accuracy tests, no exception expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUnregister_Accuracy() throws Exception {
        Contest contest = this.createContest(1L);
        User user = this.createUser(MockResourceManager.CONVERTED_RESOURCE_ID);
        ContestRole contestRole = this.createContestRole(1L);

        this.persistence.unregister(contest, user, contestRole);
    }

    /**
     * <p>
     * A client which extends <code>ResourceManagerServiceClient</code>.
     * </p>
     *
     * <p>
     * It overrides the <code>getAllResourceRoles()</code> method to ALWAYS return an empty array.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    private static final class Client1 extends ResourceManagerServiceClient {

        /**
         * <p>
         * Simple delegates to super constructor.
         * </p>
         *
         * @param url The WSDL url.
         *
         * @throws IllegalArgumentException If url is null or empty.
         * @throws ResourceManagerServiceClientCreationException If error occurs when creating the WSDL
         *         service or creating the SEI proxy.
         */
        public Client1(String url) throws ResourceManagerServiceClientCreationException {
            super(url);
        }

        /**
         * <p>
         * Simple delegates to super constructor.
         * </p>
         *
         * @param url The WSDL url.
         *
         * @throws IllegalArgumentException If url is null.
         * @throws ResourceManagerServiceClientCreationException if error occurs when creating the WSDL
         *         service or creating the SEI proxy.
         */
        public Client1(URL url) throws ResourceManagerServiceClientCreationException {
            super(url);
        }

        /**
         * <p>
         * Simple delegates to super constructor.
         * </p>
         *
         * @param url The WSDL url.
         * @param serviceName The <code>QName</code> represents service name.
         *
         * @throws IllegalArgumentException If any argument is null.
         * @throws ResourceManagerServiceClientCreationException If error occurs when creating the WSDL
         *         service or creating the SEI proxy.
         */
        public Client1(URL url, QName serviceName)
            throws ResourceManagerServiceClientCreationException {
            super(url, serviceName);
        }


        /**
         * <p>
         * Override the base method to return an empty array always.
         * </p>
         *
         * @return an empty array always.
         *
         * @throws ResourceManagementException Never.
         */
        @Override
        public ResourceRole[] getAllResourceRoles() throws ResourceManagementException {
            return new ResourceRole[0];
        }
    }
}
