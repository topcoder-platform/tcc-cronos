/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence;

import java.net.URL;

import javax.xml.namespace.QName;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;

import com.topcoder.registration.RegistrationPersistence;
import com.topcoder.registration.persistence.client.ResourceManagerServiceClient;
import com.topcoder.registration.persistence.converter.RegistrationEntitiesToResourceConverterImpl;


/**
 * <p>
 * Demo the usage of this component.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends BaseTestCase {

    /**
     * <p>
     * Demo the usage of the web service client.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testDemoClient() throws Exception {
        String url = WSDL;

        //create a client
        ResourceManagerServiceClient client = new ResourceManagerServiceClient(url);
        //or create a client with URL
        client = new ResourceManagerServiceClient(new URL(url));
        //or create a client with URL and QName
        client = new ResourceManagerServiceClient(new URL(url),
            new QName("http://www.topcoder.com/ResourceService", "ResourceService"));

        Resource resource = this.createResource(1L);

        //update a resource
        client.updateResource(resource, "John");

        //remove a resource
        client.removeResource(resource, "John");

        //update resources for a project
        client.updateResources(new Resource[] {resource }, 1, "John");

        //get a resource by id
        resource = client.getResource(2);

        ResourceRole resourceRole = this.createResourceRole();
        //update a resource role
        client.updateResourceRole(resourceRole, "John");

        //remove a resource role
        client.removeResourceRole(resourceRole, "John");

        //get all resource roles
        ResourceRole[] resourceRoles = client.getAllResourceRoles();
        System.out.println("Demo: size of all resource roles: " + resourceRoles.length);

        //add notifications, of a given type, for users for a project
        client.addNotifications(new long[] {1, 2 }, 2, 2, "John");

        //remove notifications, of a given type, from users for a project
        client.removeNotifications(new long[] {1, 2 }, 2, 2, "John");

        //get users ids for all notifications for the given project and type
        long[] users = client.getNotifications(2, 1);
        NotificationType notificationType = this.createNotificationType();
        System.out.println("Demo: users size retrieved for given project and notification types: " + users.length);

        //update a notification type
        client.updateNotificationType(notificationType, "John");

        //remove a notification type
        client.removeNotificationType(notificationType, "John");

        //get all notification types
        NotificationType[] notificationTypes = client.getAllNotificationTypes();
        System.out.println("Demo: size of all notification types: " + notificationTypes.length);
    }

    /**
     * <p>
     * Demo the usage of the registration persistence.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testDemoPersistence() throws Exception {

        //create a RegistrationEntitiesToResourceConverter implementation instance
        RegistrationEntitiesToResourceConverter converter = new RegistrationEntitiesToResourceConverterImpl();
        //create a ResourceManagerServiceClient instance
        ResourceManagerServiceClient client = new ResourceManagerServiceClient(WSDL);

        //create a persistence instance using the created client and converter objects
        RegistrationPersistence persistence = new ResourceManagerRegistrationPersistence(client, converter);

        //create a persistence instance from a ConfigurationObject instance
        ConfigurationObject configuration = new DefaultConfigurationObject("config");

        ConfigurationObject child = new DefaultConfigurationObject("ws_wrapper_resource_management");
        child.setPropertyValue("url", WSDL);
        child.setPropertyValue("converter_key", "converterKey");
        child.setPropertyValue("object_factory_config_child_name", "objectFactoryChild");
        configuration.addChild(child);

        ConfigurationObject ofChild = new DefaultConfigurationObject("objectFactoryChild");
        ConfigurationObject converterChild = new DefaultConfigurationObject("converterKey");
        converterChild.setPropertyValue("type", RegistrationEntitiesToResourceConverterImpl.class.getName());
        ofChild.addChild(converterChild);
        configuration.addChild(ofChild);

        persistence = new ResourceManagerRegistrationPersistence(configuration);

        long userId = MockResourceManager.CONVERTED_RESOURCE_ID;

        //Register user
        persistence.register(this.createContest(1L), this.createUser(userId), this.createContestRole(1L));

        //Unregister
        persistence.unregister(this.createContest(1L), this.createUser(userId), this.createContestRole(1L));
    }
}
