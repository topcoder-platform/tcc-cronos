/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence.failuretests;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.xml.namespace.QName;

import junit.framework.TestCase;

import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.registration.entities.Contest;
import com.topcoder.registration.entities.ContestRole;
import com.topcoder.registration.entities.Role;
import com.topcoder.registration.entities.User;
import com.topcoder.registration.persistence.client.ResourceManagerServiceClient;
import com.topcoder.registration.persistence.client.ResourceManagerServiceClientCreationException;

/**
 * The base test case for failure tests.
 *
 * @author extra
 * @version 1.0
 */
public class BaseTestCase extends TestCase {

    /**
     * This field represents the default service name.
     */
    public static final QName DEFAULT_SERVICE_NAME =
        new QName("http://www.topcoder.com/ResourceService", "ResourceService");

    /**
     * The WSDL URL.
     */
    public static final String WSDL;

    /**
     * The WebService client.
     */
    public static final ResourceManagerServiceClient CLIENT;

    //Load the configured WSDL URL, and create the service client.
    static {
        try {
            Properties properties = new Properties();
            properties.load(BaseTestCase.class.getResourceAsStream("/failuretests/url.properties"));
            WSDL = properties.getProperty("WSDL");

            CLIENT = new ResourceManagerServiceClient(WSDL);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ResourceManagerServiceClientCreationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create a new instance of ContestRole.
     *
     * @param id The id to set.
     *
     * @return a new instance of ContestRole.
     */
    protected ContestRole createContestRole(long id) {
        ContestRole contestRole = new ContestRole();
        contestRole.setId(id);
        contestRole.setContestId(id);
        contestRole.setMaxRegistrants((int) (1 * id));
        contestRole.setMinRegistrants((int) (id % 2 + 1));
        contestRole.setRegistrationEndOffset(System.currentTimeMillis() / 2 / 2);
        contestRole.setRegistrationStartOffset(System.currentTimeMillis() / 2 / 2 / 2);
        contestRole.setRole(this.createRole(id));
        contestRole.setUnadvertised(true);
        return contestRole;
    }

    /**
     * Create a new instance of Role.
     *
     * @param id The id to set.
     *
     * @return a new instance of Role.
     */
    private Role createRole(long id) {
        Role role = new Role();
        role.setId(id);
        role.setName(id % 2 == 0 ? "designer" : "developer");
        return role;
    }

    /**
     * Create a new instance of User.
     *
     * @param id The id to set.
     *
     * @return a new instance of User.
     */
    protected User createUser(long id) {
        User user = new User();
        user.setId(id);
        user.setHandle("member" + id);
        user.setEmail("email" + id + "@topcoder.com");
        user.setSuspended(id % 2 == 0);
        user.setCreateDate(new Date());
        user.setModifyDate(new Date());
        user.setCreateUserName("createUser");
        user.setModifyUsername("modifyUser");
        return user;
    }

    /**
     * Create a new instance of Contest.
     *
     * @param id The id to set.
     *
     * @return a new instance of Contest.
     */
    protected Contest createContest(long id) {
        Contest contest = new Contest();
        contest.setId(id);

        contest.setLink("http://www.topcoder.com/tc?module=ProjectDetail&pj=30004429");
        contest.setName("Registration Framework " + (id % 2 == 0 ? " Develop" : "Design"));
        contest.setRegistrationEnd(new Date());
        contest.setRegistrationStart(new Date());
        contest.setSourceName(id % 2 == 0 ? "Oracle" : "MYSQL");

        return contest;
    }

    /**
     * Create a new instance of ResourceRole.
     *
     * @return a new instance of ResourceRole.
     */
    protected ResourceRole createResourceRole() {
        ResourceRole resourceRole = new ResourceRole(1L);
        resourceRole.setPhaseType(1L);
        resourceRole.setName("name");
        resourceRole.setDescription("description");
        resourceRole.setCreationUser("user");
        resourceRole.setModificationUser("user");
        resourceRole.setCreationTimestamp(new Date());
        resourceRole.setModificationTimestamp(new Date());
        return resourceRole;
    }

    /**
     * Create a new instance of NotificationType.
     *
     * @return a new instance of NotificationType.
     */
    protected NotificationType createNotificationType() {
        NotificationType notificationType = new NotificationType(1L);
        notificationType.setName("name");
        notificationType.setDescription("description");
        notificationType.setCreationUser("user");
        notificationType.setModificationUser("user");
        notificationType.setCreationTimestamp(new Date());
        notificationType.setModificationTimestamp(new Date());
        return notificationType;
    }

    /**
     * Create a new instance of Resource.
     *
     * @param project The project id. If not given then
     *        the project will be set as 1.
     *
     * @return a new instance of Resource.
     */
    protected Resource createResource(long... project) {

        Resource resource = new Resource(1L);
        resource.setResourceRole(new ResourceRole(1L));
        resource.getResourceRole().setPhaseType(2L);
        resource.setPhase(1L);
        resource.setProject(1L);
        resource.setProperty("property1", "value1");
        resource.setProperty("property2", "value2");
        resource.setSubmissions(new Long[]{1L, 2L});
        resource.setCreationUser("user");
        resource.setModificationUser("user");
        resource.setCreationTimestamp(new Date());
        resource.setModificationTimestamp(new Date());
        if (project != null && project.length > 0) {
            resource.setProject(project[0]);
        }
        return resource;
    }
}
