/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence;

import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.registration.RegistrationPersistence;
import com.topcoder.registration.RegistrationPersistenceException;
import com.topcoder.registration.entities.Contest;
import com.topcoder.registration.entities.ContestRole;
import com.topcoder.registration.entities.User;
import com.topcoder.registration.persistence.client.ResourceManagerServiceClient;
import com.topcoder.registration.persistence.client.ResourceManagerServiceClientCreationException;
import com.topcoder.util.errorhandling.BaseException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigurationObjectSpecificationFactory;


/**
 * <p>
 * This class is an implementation of the <code>RegistrationPersistence</code> interface from the
 * Registration Framework component.
 * </p>
 *
 * <p>
 * It uses a <code>RegistrationEntitiesToResourceConverter</code> implementation to convert the registration
 * entities to a <code>Resource</code> from Resource Management.
 * </p>
 *
 * <p>
 * It also uses a <code>ResourceManagerServiceClient</code> object which provides access to a service that will
 * be used to update or remove the <code>Resource</code> object created with the converter.
 * </p>
 *
 * <p>
 *     <strong>Relationship & Sequence of these components:</strong>
 *     <pre>
 *  |-------------------------| Call THIS component to perform              |--------------------------------------|
 *  | Registration Framework  |-------------------------------------------->|ResourceManagerRegistrationPersistence|
 *  |-------------------------| registering/unregistering User              |--------------------------------------|
 *                                                                                           |
 *                                                                                           | Call converter
 *  |------------------------------|       Resource converted from User                      |
 *  | ResourceManagerServiceClient |<--------------------------------------------------------|
 *  |------------------------------|    Call WSClient to update/remove Resource              |
 *              |                                                                            V
 *              |                                                          |-----------------------------------------|
 *              | SOAP                                                     | RegistrationEntitiesToResourceConverter |
 *              |                                                          |-----------------------------------------|
 *              V
 *  |--------------------------| Delegate  |--------------------|
 *  |ResourceManagerServiceBean|---------->|  Resource Manager  |
 *  |--------------------------|           |--------------------|
 *     </pre>
 * </p>
 *
 * <p>
 *     <strong>Sample Usage:</strong>
 *     <pre>
 *        //create a RegistrationEntitiesToResourceConverter implementation instance
 *        RegistrationEntitiesToResourceConverter converter = new RegistrationEntitiesToResourceConverterImpl();
 *        //create a ResourceManagerServiceClient instance
 *        ResourceManagerServiceClient client = new ResourceManagerServiceClient(WSDL);
 *
 *        //create a persistence instance using the created client and converter objects
 *        RegistrationPersistence persistence = new ResourceManagerRegistrationPersistence(client, converter);
 *
 *        //create a persistence instance from a ConfigurationObject instance
 *        ConfigurationObject configuration = new DefaultConfigurationObject("config");
 *
 *        ConfigurationObject child = new DefaultConfigurationObject("ws_wrapper_resource_management");
 *        child.setPropertyValue("url", WSDL);
 *        child.setPropertyValue("converter_key", "converterKey");
 *        child.setPropertyValue("object_factory_config_child_name", "objectFactoryChild");
 *        configuration.addChild(child);
 *
 *        ConfigurationObject ofChild = new DefaultConfigurationObject("objectFactoryChild");
 *        ConfigurationObject converterChild = new DefaultConfigurationObject("converterKey");
 *        converterChild.setPropertyValue("type", RegistrationEntitiesToResourceConverterImpl.class.getName());
 *        ofChild.addChild(converterChild);
 *        configuration.addChild(ofChild);
 *
 *        persistence = new ResourceManagerRegistrationPersistence(configuration);
 *
 *        //Register user
 *        persistence.register(contest, user, contestRole);
 *
 *        //Unregister
 *        persistence.unregister(contest, user, contestRole);
 *     </pre>
 * </p>
 *
 * <p>
 *     <strong>Thread Safety:</strong>
 *     This class is thread safe since <code>ResourceManagerServiceClient</code> class is thread safe and
 *     <code>RegistrationEntitiesToResourceConverter</code> are also expected to be thread safe.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class ResourceManagerRegistrationPersistence implements RegistrationPersistence {

    /**
     * <p>
     * The name of the child used for this class.
     * </p>
     */
    private static final String CHILD_NAME = "ws_wrapper_resource_management";

    /**
     * <p>
     * The name of the property which gives the WSDL URL.
     * </p>
     */
    private static final String URL_PROPERTY = "url";

    /**
     * <p>
     * The name of the property which gives the key of converter within Object Factory.
     * </p>
     */
    private static final String CONVERTER_KEY_PROPERTY = "converter_key";

    /**
     * <p>
     * The name of the property which gives the name of the child to create Object Factory.
     * </p>
     */
    private static final String OBJECT_FACTORY_CHILD_NAME_PROPERTY = "object_factory_config_child_name";

    /**
     * <p>
     * The client used to access the resource manager service.
     * </p>
     *
     * <p>
     * It will be initialized in the constructors and never changed.
     * </p>
     *
     * <p>
     * It cannot be null after initialization.
     * </p>
     *
     * <p>
     * It will be used in both methods.
     * </p>
     */
    private final ResourceManagerServiceClient serviceClient;

    /**
     * <p>
     * The converter used to convert the registration entities to a <code>Resource</code> object.
     * </p>
     *
     * <p>
     * It will be initialized in the constructors and never changed.
     * </p>
     *
     * <p>
     * It cannot be null after initialization.
     * </p>
     *
     * <p>
     * It will be used in both methods.
     * </p>
     */
    private final RegistrationEntitiesToResourceConverter converter;

    /**
     * <p>
     * Creates a new instance with given <code>ResourceManagerServiceClient</code>
     * and <code>RegistrationEntitiesToResourceConverter</code>.
     * </p>
     *
     * @param converter The converter.
     * @param client The service client.
     *
     * @throws IllegalArgumentException If any argument is null.
     */
    public ResourceManagerRegistrationPersistence(
        ResourceManagerServiceClient client, RegistrationEntitiesToResourceConverter converter) {
        ArgumentChecker.checkNull(client, "The ResourceManagerServiceClient client");
        ArgumentChecker.checkNull(converter, "The RegistrationEntitiesToResourceConverter client");
        this.serviceClient = client;
        this.converter = converter;
    }

    /**
     * <p>
     * Creates a new instance using the configuration object.
     * </p>
     *
     * @param configuration The configuration object.
     *
     * @throws IllegalArgumentException If configuration is null.
     * @throws ResourceManagerServiceClientCreationException If errors occur when creating the client.
     * @throws ConfigurationException If any required configuration property is missing from configuration.
     */
    public ResourceManagerRegistrationPersistence(ConfigurationObject configuration)
        throws ConfigurationException, ResourceManagerServiceClientCreationException {
        ArgumentChecker.checkNull(configuration, "ConfigurationObject");

        String url = null;
        try {
            //get the child holding the configuration for this component
            ConfigurationObject config = configuration.getChild(CHILD_NAME);
            ArgumentChecker.checkNull(config, "The child '" + CHILD_NAME + "'");

            //get the url
            url = getPropertyValue(config, URL_PROPERTY);

            //get the converter key
            String converterKey = getPropertyValue(config, CONVERTER_KEY_PROPERTY);

            //get the name that identifies the child that holds the configuration of Object Factory
            String ofChildName = getPropertyValue(config, OBJECT_FACTORY_CHILD_NAME_PROPERTY);

            //get the child that holds the configuration for Object Factory
            ConfigurationObject ofConfig = configuration.getChild(ofChildName);
            ArgumentChecker.checkNull(ofConfig, "The child '" + ofChildName + "'");

            ObjectFactory factory = new ObjectFactory(new ConfigurationObjectSpecificationFactory(ofConfig));

            this.converter = (RegistrationEntitiesToResourceConverter) factory.createObject(converterKey);
        } catch (BaseException e) {
            throw new ConfigurationException(
                "BaseException occurs while instantiating ResourceManagerRegistrationPersistence.", e);
        } catch (IllegalArgumentException e) {
            throw new ConfigurationException(
                "IllegalArgumentException occurs while instantiating ResourceManagerRegistrationPersistence.", e);
        } catch (ClassCastException e) {
            throw new ConfigurationException(
                "ClassCastException occurs while instantiating ResourceManagerRegistrationPersistence.", e);
        }

        this.serviceClient = new ResourceManagerServiceClient(url);
    }

    /**
     * <p>
     * Get property value.
     * </p>
     *
     * @param config The <code>ConfigurationObject</code> which contains the property.
     * @param propertyName The name of property to retrieve its value.
     *
     * @return The value retrieved.
     *
     * @throws IllegalArgumentException If the property is missing(value is null), or if the value
     *         is empty. Or if the property has multiple values.
     * @throws ClassCastException If the property value is not type of <code>String</code>.
     * @throws ConfigurationAccessException If error occurs while accessing configuration object.
     */
    private static final String getPropertyValue(ConfigurationObject config, String propertyName)
        throws ConfigurationAccessException {
        if (config.getPropertyValuesCount(propertyName) > 1) {
            throw new IllegalArgumentException(
                "The property '" + propertyName + "' should not have multiple values.");
        }
        String result = (String) config.getPropertyValue(propertyName);
        ArgumentChecker.checkNullOrEmpty(result, "The value of property '" + propertyName + "'");
        return result;
    }

    /**
     * <p>
     * This method will register the user to the contest with the given role.
     * </p>
     *
     * <p>
     * It will first retrieve all the resource roles through WebService, call the converter which will create a
     * <code>Resource</code> object from the registration entities and resource roles and then it will call the
     * WebService to update the resource.
     * </p>
     *
     * @param contest The contest to what the user will register into.
     * @param user The user who registers.
     * @param contestRole The role in the contest.
     *
     * @throws IllegalArgumentException If any parameter is null.
     * @throws RegistrationEntitiesToResourceConversionException If any error occur while performing the conversion.
     * @throws RegistrationPersistenceException If any errors occur while registering.
     */
    public void register(Contest contest, User user, ContestRole contestRole)
        throws RegistrationPersistenceException {
        this.doRegisterOrUnregister(contest, user, contestRole, true);
    }

    /**
     * <p>
     * Do registering/unregistering.
     * </p>
     *
     * @param contest The contest to what the user will register/unregister.
     * @param user The user who registers/unregisters.
     * @param contestRole The role in the contest.
     * @param reg Indicates the action is to register or unregister.
     *
     * @throws IllegalArgumentException If any parameter is null.
     * @throws RegistrationEntitiesToResourceConversionException If any error occur while performing the conversion.
     * @throws ResourceManagementException If any errors occur while registering/unregistering.
     */
    private void doRegisterOrUnregister(Contest contest, User user, ContestRole contestRole, boolean reg)
        throws RegistrationEntitiesToResourceConversionException, ResourceManagementException {
        String action = reg ? "register" : "unregister";

        //IllegalArgumentException if any parameter is null
        ArgumentChecker.checkNull(contest, "Contest to " + action);
        ArgumentChecker.checkNull(user, "User to " + action);
        ArgumentChecker.checkNull(contestRole, "ContestRole to " + action);

        //get all resource roles
        ResourceRole[] resourceRoles = serviceClient.getAllResourceRoles();

        boolean converted = false;
        try {
            //call the converter that will create a Resource object
            Resource resource = converter.convertRegistrationEntitiesToResource(
                contest, user, contestRole, resourceRoles);

            converted = true;

            //update the resource
            if (reg) {
                serviceClient.updateResource(resource, user.getHandle());
            } else {
                serviceClient.removeResource(resource, user.getHandle());
            }
        } catch (IllegalArgumentException e) {
            if (!converted) {
                //RegistrationEntitiesToResourceConversionException if any error occur while performing the conversion
                throw new RegistrationEntitiesToResourceConversionException(
                        "Error occurs while converting registration entities to Resource", e);
            }
            //RegistrationPersistenceException if any errors occur while registering/unregistering
            throw new ResourceManagementException("Error occurs while " + action + "ing", e);
        }
    }

    /**
     * <p>
     * This method will unregister the user from the contest with the given role.
     * </p>
     *
     * <p>
     * It will first retrieve all the resource roles through WebService, call the converter which will create a
     * <code>Resource</code> object from the registration entities and resource roles and then it will call the
     * WebService to remove the resource.
     * </p>
     *
     * @param contest The contest to what the user will unregister from.
     * @param user The user who unregisters.
     * @param contestRole The role in the contest.
     *
     * @throws IllegalArgumentException If any parameter is null.
     * @throws RegistrationEntitiesToResourceConversionException If any error occur while performing the conversion.
     * @throws RegistrationPersistenceException If any errors occur while unregistering.
     */
    public void unregister(Contest contest, User user, ContestRole contestRole)
        throws RegistrationPersistenceException {
        this.doRegisterOrUnregister(contest, user, contestRole, false);
    }
}
