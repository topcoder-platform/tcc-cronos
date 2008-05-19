/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence.failuretests;

import java.util.Date;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.registration.entities.Contest;
import com.topcoder.registration.entities.ContestRole;
import com.topcoder.registration.entities.User;
import com.topcoder.registration.persistence.ConfigurationException;
import com.topcoder.registration.persistence.RegistrationEntitiesToResourceConverter;
import com.topcoder.registration.persistence.ResourceManagerRegistrationPersistence;
import com.topcoder.registration.persistence.client.ResourceManagerServiceClient;
import com.topcoder.registration.persistence.client.ResourceManagerServiceClientCreationException;
import com.topcoder.registration.persistence.converter.RegistrationEntitiesToResourceConverterImpl;

/**
 * Failure test for class ResourceManagerRegistrationPersistence.
 *
 * @author extra
 * @version 1.0
 */
public class ResourceManagerRegistrationPersistenceFailureTests extends BaseTestCase {

    /**
     * Represents instance of ResourceManagerRegistrationPersistence for test.
     */
    private ResourceManagerRegistrationPersistence persistence;

    /**
     * Represents instance of RegistrationEntitiesToResourceConverter for test.
     */
    private RegistrationEntitiesToResourceConverter converter;

    /**
     * Represents instance of ConfigurationObject for test.
     */
    private ConfigurationObject config;

    /**
     * Represents instance of ConfigurationObject for test.
     */
    private ConfigurationObject child;

    /**
     * Represents instance of ConfigurationObject for test.
     */
    private ConfigurationObject ofChild;

    /**
     * Represents instance of ConfigurationObject for test.
     */
    private ConfigurationObject converterChild;

    /**
     * Represents instance of Contest for test.
     */
    private Contest contest;

    /**
     * Represents instance of User for test.
     */
    private User user;

    /**
     * Represents instance of ContestRole for test.
     */
    private ContestRole contestRole;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JUnt.
     */
    protected void setUp() throws Exception {
        converter = new RegistrationEntitiesToResourceConverterImpl();
        persistence = new ResourceManagerRegistrationPersistence(CLIENT, converter);
        config = new DefaultConfigurationObject("config");
        child = new DefaultConfigurationObject("ws_wrapper_resource_management");
        child.setPropertyValue("url", WSDL);
        child.setPropertyValue("converter_key", "converterKey");
        child.setPropertyValue("object_factory_config_child_name", "objectFactoryChild");
        config.addChild(child);

        ofChild = new DefaultConfigurationObject("objectFactoryChild");
        converterChild = new DefaultConfigurationObject("converterKey");
        converterChild.setPropertyValue("type", RegistrationEntitiesToResourceConverterImpl.class.getName());
        ofChild.addChild(converterChild);
        config.addChild(ofChild);

        contest = createContest(10);
        user = createUser(5);
        contestRole = createContestRole(4);
        super.setUp();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void tearDown() throws Exception {
        this.converter = null;
        this.persistence = null;
        this.child = null;
        this.config = null;
        this.contest = null;
        this.contestRole = null;
        this.ofChild = null;
        this.converterChild = null;
        this.user = null;
        super.tearDown();
    }

    /**
     * Failure test for method
     * ResourceManagerRegistrationPersistence(ResourceManagerServiceClient,
     * RegistrationEntitiesToResourceConverter). With null client,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testResourceManagerRegistrationPersistence_NullClient() throws Exception {
        try {
            this.persistence = new ResourceManagerRegistrationPersistence((ResourceManagerServiceClient) null,
                    converter);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method
     * ResourceManagerRegistrationPersistence(ResourceManagerServiceClient,
     * RegistrationEntitiesToResourceConverter). With null converter,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testResourceManagerRegistrationPersistence_NullConverter() throws Exception {
        try {
            this.persistence = new ResourceManagerRegistrationPersistence(CLIENT,
                    (RegistrationEntitiesToResourceConverter) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method
     * ResourceManagerRegistrationPersistence(ConfigurationObject). With null
     * config, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testResourceManagerRegistrationPersistence_NullConfigurationObject() throws Exception {
        try {
            new ResourceManagerRegistrationPersistence(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method
     * ResourceManagerRegistrationPersistence(ConfigurationObject). Without
     * config of child "ws_wrapper_resource_management", ConfigurationException
     * expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testResourceManagerRegistrationPersistence_WithoutChild() throws Exception {
        config.removeChild("ws_wrapper_resource_management");
        try {
            new ResourceManagerRegistrationPersistence(config);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for method
     * ResourceManagerRegistrationPersistence(ConfigurationObject). Without
     * property "url", ConfigurationException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testResourceManagerRegistrationPersistence_WithoutURL() throws Exception {
        child.removeProperty("url");
        try {
            new ResourceManagerRegistrationPersistence(config);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for method
     * ResourceManagerRegistrationPersistence(ConfigurationObject). With empty
     * property "url", ConfigurationException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testResourceManagerRegistrationPersistence_EmptyURL() throws Exception {
        child.setPropertyValue("url", " ");
        try {
            new ResourceManagerRegistrationPersistence(config);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for method
     * ResourceManagerRegistrationPersistence(ConfigurationObject). WIth invalid
     * type of property "url", ConfigurationException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testResourceManagerRegistrationPersistence_InvalidURL1() throws Exception {
        child.setPropertyValue("url", new Date());
        try {
            new ResourceManagerRegistrationPersistence(config);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for method
     * ResourceManagerRegistrationPersistence(ConfigurationObject). With
     * multiple value of property "url", ConfigurationException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testResourceManagerRegistrationPersistence_MultiURL() throws Exception {
        child.setPropertyValues("url", new String[] {"url", "url" });
        try {
            new ResourceManagerRegistrationPersistence(config);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for method
     * ResourceManagerRegistrationPersistence(ConfigurationObject). The property
     * "converter_key" is missing, ConfigurationException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testResourceManagerRegistrationPersistence_NullConverterKey() throws Exception {
        child.removeProperty("converter_key");
        try {
            new ResourceManagerRegistrationPersistence(config);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for method
     * ResourceManagerRegistrationPersistence(ConfigurationObject).
     * With empty property "converter_key", ConfigurationException
     * expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testResourceManagerRegistrationPersistence_EmptyConverterKey() throws Exception {
        child.setPropertyValue("converter_key", " ");
        try {
            new ResourceManagerRegistrationPersistence(config);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for method
     * ResourceManagerRegistrationPersistence(ConfigurationObject).
     * With invalid property "converter_key",
     * ConfigurationException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testResourceManagerRegistrationPersistence_InvalidConverterKey() throws Exception {
        child.setPropertyValue("converter_key", 1);
        try {
            new ResourceManagerRegistrationPersistence(config);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for method
     * ResourceManagerRegistrationPersistence(ConfigurationObject). With
     * multiple values of property "converter_key", ConfigurationException
     * expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testResourceManagerRegistrationPersistence_MultiConverterKey() throws Exception {
        child.setPropertyValues("converter_key", new String[] {"1", "2" });
        try {
            new ResourceManagerRegistrationPersistence(config);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for method
     * ResourceManagerRegistrationPersistence(ConfigurationObject). Without
     * property "object_factory_config_child_name", ConfigurationException
     * expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testResourceManagerRegistrationPersistence_WithoutObjectFactoryChildName() throws Exception {
        child.removeProperty("object_factory_config_child_name");
        try {
            new ResourceManagerRegistrationPersistence(config);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for method
     * ResourceManagerRegistrationPersistence(ConfigurationObject). With empty
     * property "object_factory_config_child_name", ConfigurationException
     * expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testResourceManagerRegistrationPersistence_EmptyObjectFactoryChildName() throws Exception {
        child.setPropertyValue("object_factory_config_child_name", " ");
        try {
            new ResourceManagerRegistrationPersistence(config);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for method
     * ResourceManagerRegistrationPersistence(ConfigurationObject). With
     * multiple values property "object_factory_config_child_name",
     * ConfigurationException expected.
     *
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testResourceManagerRegistrationPersistence_MultiObjectFactoryChildName() throws Exception {
        child.setPropertyValues("object_factory_config_child_name", new String[] {"child1", "child2" });
        try {
            new ResourceManagerRegistrationPersistence(config);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for method
     * ResourceManagerRegistrationPersistence(ConfigurationObject). Without the
     * child with name given by "object_factory_config_child_name" property,
     * ConfigurationException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testResourceManagerRegistrationPersistence_WIthoutObjectFactoryChild() throws Exception {
        config.removeChild("objectFactoryChild");
        try {
            new ResourceManagerRegistrationPersistence(config);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for method
     * ResourceManagerRegistrationPersistence(ConfigurationObject). With invalid
     * object factory child, ConfigurationException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testResourceManagerRegistrationPersistence_InvalidObjectFactoryChild() throws Exception {
        ofChild.clearChildren();
        ofChild.clearProperties();
        try {
            new ResourceManagerRegistrationPersistence(config);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for method
     * ResourceManagerRegistrationPersistence(ConfigurationObject). Return type
     * is not RegistrationEntitiesToResourceConverter, ConfigurationException
     * expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testResourceManagerRegistrationPersistence_InvalidObject() throws Exception {
        converterChild.setPropertyValue("type", "java.lang.String");
        try {
            new ResourceManagerRegistrationPersistence(config);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for method
     * ResourceManagerRegistrationPersistence(ConfigurationObject). Error occurs
     * while creating ResourceManagerServiceClient,
     * ResourceManagerServiceClientCreationException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testResourceManagerRegistrationPersistence_WrongURL() throws Exception {
        child.setPropertyValue("url", "http://www.google.com");
        try {
            new ResourceManagerRegistrationPersistence(config);
            fail("ResourceManagerServiceClientCreationException expected");
        } catch (ResourceManagerServiceClientCreationException e) {
            // expected
        }
    }

    /**
     * Failure test for method register(Contest contest, User user, ContestRole
     * contestRole). With null contest, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRegister_NullContest() throws Exception {
        try {
            this.persistence.register(null, user, contestRole);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method register(Contest contest, User user, ContestRole
     * contestRole). With null user, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRegister_NullUser() throws Exception {
        try {
            this.persistence.register(contest, null, contestRole);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method register(Contest contest, User user, ContestRole
     * contestRole). With null contestRole, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRegister_NullContestRole() throws Exception {
        try {
            this.persistence.register(contest, user, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method unregister(Contest contest, User user,
     * ContestRole contestRole). With null contest, IllegalArgumentException
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUnregister_NullContest() throws Exception {
        try {
            this.persistence.unregister(null, user, contestRole);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method unregister(Contest contest, User user,
     * ContestRole contestRole). With null user, IllegalArgumentException
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUnregister_NullUser() throws Exception {
        try {
            this.persistence.unregister(contest, null, contestRole);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method unregister(Contest contest, User user,
     * ContestRole contestRole). With null contestRole, IllegalArgumentException
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUnregister_NullContestRole() throws Exception {
        try {
            this.persistence.unregister(contest, user, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
