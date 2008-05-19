/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence;

import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;

/**
 * <p>
 * Unit test for <code>{@link ArgumentChecker}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HelperUnitTests extends BaseTestCase {

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNull(Object, String)}.
     * </p>
     *
     * <p>
     * Given object is null, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckNull_NullObject() {
        try {
            ArgumentChecker.checkNull(null, "test");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNull(Object, String)}.
     * </p>
     *
     * <p>
     * Given object is not null, <code>IllegalArgumentException</code> not expected.
     * </p>
     */
    public void testCheckNull_Accuracy() {
        try {
            ArgumentChecker.checkNull(" ", "test");
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException not expected");
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNull(Object, String)}.
     * </p>
     *
     * <p>
     * Given string is null, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckNullOrEmpty_NullString() {
        try {
            ArgumentChecker.checkNullOrEmpty(null, "test");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNull(Object, String)}.
     * </p>
     *
     * <p>
     * Given string is empty, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckNullOrEmpty_EmptyString() {
        try {
            ArgumentChecker.checkNullOrEmpty(" ", "test");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNull(Object, String)}.
     * </p>
     *
     * <p>
     * Given string is not null and not empty, <code>IllegalArgumentException</code> not expected.
     * </p>
     */
    public void testCheckNullOrEmpty_Accuracy() {
        try {
            ArgumentChecker.checkNullOrEmpty("string", "test");
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException not expected");
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNull(Object, String)}.
     * </p>
     *
     * <p>
     * Given long value is not positive, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckLongPositive_NegativeId() {
        try {
            ArgumentChecker.checkLongPositive(-1, "test");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNull(Object, String)}.
     * </p>
     *
     * <p>
     * Given long value is positive, <code>IllegalArgumentException</code> not expected.
     * </p>
     */
    public void testCheckLongPositive_Accuracy() {
        try {
            ArgumentChecker.checkLongPositive(1, "test");
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException not expected");
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourceToBeUpdated(Resource, String)}.
     * </p>
     *
     * <p>
     * Given <code>Resource</code> is null, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckResourceToBeUpdated_NullResource() {
        try {
            ArgumentChecker.checkResourceToBeUpdated(null, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourceToBeUpdated(Resource, String)}.
     * </p>
     *
     * <p>
     * Given <code>Resource</code> does not have role set, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckResourceToBeUpdated_RoleNotSet() {
        try {
            ArgumentChecker.checkResourceToBeUpdated(new Resource(1L), "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourceToBeUpdated(Resource, String)}.
     * </p>
     *
     * <p>
     * Given resource's role has phase type, but the resource does not have a phase,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckResourceToBeUpdated_PhaseNotSet() {
        Resource resource = new Resource(1L);
        resource.setResourceRole(new ResourceRole());
        resource.getResourceRole().setPhaseType(2L);
        try {
            ArgumentChecker.checkResourceToBeUpdated(resource, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourceToBeUpdated(Resource, String)}.
     * </p>
     *
     * <p>
     * Given <code>Resource</code> does not have id set,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckResourceToBeUpdated_IdNotSet() {
        Resource resource = new Resource();
        resource.setResourceRole(new ResourceRole());
        resource.getResourceRole().setPhaseType(2L);
        resource.setPhase(1L);
        try {
            ArgumentChecker.checkResourceToBeUpdated(resource, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourceToBeUpdated(Resource, String)}.
     * </p>
     *
     * <p>
     * Given operator is null,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckResourceToBeUpdated_NullOperator() {
        Resource resource = new Resource(1L);
        resource.setResourceRole(new ResourceRole());
        resource.getResourceRole().setPhaseType(2L);
        resource.setPhase(1L);
        try {
            ArgumentChecker.checkResourceToBeUpdated(resource, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourceToBeUpdated(Resource, String)}.
     * </p>
     *
     * <p>
     * Given operator is empty,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckResourceToBeUpdated_EmptyOperator() {
        Resource resource = new Resource(1L);
        resource.setResourceRole(new ResourceRole(1L));
        resource.getResourceRole().setPhaseType(2L);
        resource.setPhase(1L);
        try {
            ArgumentChecker.checkResourceToBeUpdated(resource, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourceToBeUpdated(Resource, String)}.
     * </p>
     *
     * <p>
     * Given <code>Resource</code> and operator are valid,
     * <code>IllegalArgumentException</code> not expected.
     * </p>
     */
    public void testCheckResourceToBeUpdated_Accuracy() {
        Resource resource = this.createResource();
        try {
            ArgumentChecker.checkResourceToBeUpdated(resource, "operator");
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException not expected");
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourcesToBeUpdated(Resource[], long, String)}.
     * </p>
     *
     * <p>
     * Given resources array is null,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckResourcesToBeUpdated_NullResourcesArray() {
        try {
            ArgumentChecker.checkResourcesToBeUpdated(null, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourcesToBeUpdated(Resource[], long, String)}.
     * </p>
     *
     * <p>
     * Given resources array contains null element,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckResourcesToBeUpdated_ResourcesArrayContainsNullElement() {
        try {
            ArgumentChecker.checkResourcesToBeUpdated(new Resource[]{null}, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourcesToBeUpdated(Resource[], long, String)}.
     * </p>
     *
     * <p>
     * Given project is negative,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckResourcesToBeUpdated_NegativeProject() {
        try {
            ArgumentChecker.checkResourcesToBeUpdated(new Resource[]{}, -1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourcesToBeUpdated(Resource[], long, String)}.
     * </p>
     *
     * <p>
     * The given array has a <code>Resource</code> which does not have project set,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckResourcesToBeUpdated_ProjectNotSet() {
        Resource resource = new Resource(1L);
        resource.setResourceRole(new ResourceRole());
        resource.getResourceRole().setPhaseType(2L);
        resource.setPhase(1L);
        try {
            ArgumentChecker.checkResourcesToBeUpdated(new Resource[]{resource}, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourcesToBeUpdated(Resource[], long, String)}.
     * </p>
     *
     * <p>
     * The given array has a <code>Resource</code> which has project not same as given project,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckResourcesToBeUpdated_ProjectNotSame() {
        Resource resource = new Resource(1L);
        resource.setResourceRole(new ResourceRole());
        resource.getResourceRole().setPhaseType(2L);
        resource.setPhase(1L);
        resource.setProject(2L);
        try {
            ArgumentChecker.checkResourcesToBeUpdated(new Resource[]{resource}, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourcesToBeUpdated(Resource[], long, String)}.
     * </p>
     *
     * <p>
     * The given array has a <code>Resource</code> which does not have <code>ResourceRole</code> set,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckResourcesToBeUpdated_RoleNotSet() {
        Resource resource = new Resource(1L);
        resource.setPhase(1L);
        resource.setProject(1L);
        try {
            ArgumentChecker.checkResourcesToBeUpdated(new Resource[]{resource}, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourcesToBeUpdated(Resource[], long, String)}.
     * </p>
     *
     * <p>
     * The given array has a <code>Resource</code>'s role has phase type, but the resource does not have a phase,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckResourcesToBeUpdated_PhaseNotSet() {
        Resource resource = new Resource(1L);
        resource.setResourceRole(new ResourceRole());
        resource.getResourceRole().setPhaseType(2L);
        resource.setProject(1L);
        try {
            ArgumentChecker.checkResourcesToBeUpdated(new Resource[]{resource}, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourcesToBeUpdated(Resource[], long, String)}.
     * </p>
     *
     * <p>
     * The given array has a <code>Resource</code> which does not have id set,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckResourcesToBeUpdated_IdNotSet() {
        Resource resource = new Resource();
        resource.setResourceRole(new ResourceRole());
        resource.getResourceRole().setPhaseType(2L);
        resource.setPhase(1L);
        resource.setProject(1L);
        try {
            ArgumentChecker.checkResourcesToBeUpdated(new Resource[]{resource}, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourcesToBeUpdated(Resource[], long, String)}.
     * </p>
     *
     * <p>
     * Given operator is null,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckResourcesToBeUpdated_NullOperator() {
        Resource resource = new Resource(1L);
        resource.setResourceRole(new ResourceRole());
        resource.getResourceRole().setPhaseType(2L);
        resource.setPhase(1L);
        resource.setProject(1L);
        try {
            ArgumentChecker.checkResourcesToBeUpdated(new Resource[]{resource}, 1L, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourcesToBeUpdated(Resource[], long, String)}.
     * </p>
     *
     * <p>
     * Given operator is empty,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckResourcesToBeUpdated_EmptyOperator() {
        Resource resource = new Resource(1L);
        resource.setResourceRole(new ResourceRole());
        resource.getResourceRole().setPhaseType(2L);
        resource.setPhase(1L);
        resource.setProject(1L);
        try {
            ArgumentChecker.checkResourcesToBeUpdated(new Resource[]{resource}, 1L, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourcesToBeUpdated(Resource[], long, String)}.
     * </p>
     *
     * <p>
     * Given resources, project and operator are valid,
     * <code>IllegalArgumentException</code> not expected.
     * </p>
     */
    public void testCheckResourcesToBeUpdated_Accuracy() {
        Resource resource = this.createResource(1L);
        try {
            ArgumentChecker.checkResourcesToBeUpdated(new Resource[]{resource}, 1L, "operator");
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException not expected");
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourceToBeRemoved(Resource, String)}.
     * </p>
     *
     * <p>
     * Given <code>Resource</code> is null, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckResourceToBeRemoved_NullResource() {
        try {
            ArgumentChecker.checkResourceToBeRemoved(null, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourceToBeRemoved(Resource, String)}.
     * </p>
     *
     * <p>
     * Given <code>Resource</code> does not have id set, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckResourceToBeRemoved_IdNotSet() {
        try {
            ArgumentChecker.checkResourceToBeRemoved(new Resource(), "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourceToBeRemoved(Resource, String)}.
     * </p>
     *
     * <p>
     * Given operator is null, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckResourceToBeRemoved_NullOperator() {
        Resource resource = new Resource(1L);
        try {
            ArgumentChecker.checkResourceToBeRemoved(resource, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourceToBeRemoved(Resource, String)}.
     * </p>
     *
     * <p>
     * Given operator is empty, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckResourceToBeRemoved_EmptyOperator() {
        Resource resource = new Resource(1L);
        try {
            ArgumentChecker.checkResourceToBeRemoved(resource, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourceToBeRemoved(Resource, String)}.
     * </p>
     *
     * <p>
     * Given <code>Resource</code> and operator are valid, <code>IllegalArgumentException</code> not expected.
     * </p>
     */
    public void testCheckResourceToBeRemoved_Accuracy() {
        Resource resource = new Resource(1L);
        try {
            ArgumentChecker.checkResourceToBeRemoved(resource, "operator");
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException not expected");
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourceRoleToBeUpdated(ResourceRole, String)}.
     * </p>
     *
     * <p>
     * Given <code>ResourceRole</code> is null, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckResourceRoleToBeUpdated_NullResourceRole() {
        try {
            ArgumentChecker.checkResourceRoleToBeUpdated(null, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourceRoleToBeUpdated(ResourceRole, String)}.
     * </p>
     *
     * <p>
     * Given <code>ResourceRole</code> does not have name set,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckResourceRoleToBeUpdated_NameNotSet() {
        ResourceRole resourceRole = new ResourceRole(1L);
        resourceRole.setDescription("description");
        try {
            ArgumentChecker.checkResourceRoleToBeUpdated(resourceRole, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourceRoleToBeUpdated(ResourceRole, String)}.
     * </p>
     *
     * <p>
     * Given <code>ResourceRole</code> does not have description set,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckResourceRoleToBeUpdated_DescriptionNotSet() {
        ResourceRole resourceRole = new ResourceRole(1L);
        resourceRole.setName("name");
        try {
            ArgumentChecker.checkResourceRoleToBeUpdated(resourceRole, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourceRoleToBeUpdated(ResourceRole, String)}.
     * </p>
     *
     * <p>
     * Given <code>ResourceRole</code> does not have id set, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckResourceRoleToBeUpdated_IdNotSet() {
        ResourceRole resourceRole = new ResourceRole();
        resourceRole.setName("name");
        resourceRole.setDescription("description");
        try {
            ArgumentChecker.checkResourceRoleToBeUpdated(resourceRole, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourceRoleToBeUpdated(ResourceRole, String)}.
     * </p>
     *
     * <p>
     * Given operator is null, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckResourceRoleToBeUpdated_NullOperator() {
        ResourceRole resourceRole = new ResourceRole(1L);
        resourceRole.setName("name");
        resourceRole.setDescription("description");
        try {
            ArgumentChecker.checkResourceRoleToBeUpdated(resourceRole, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourceRoleToBeUpdated(ResourceRole, String)}.
     * </p>
     *
     * <p>
     * Given operator is empty, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckResourceRoleToBeUpdated_EmptyOperator() {
        ResourceRole resourceRole = new ResourceRole(1L);
        resourceRole.setName("name");
        resourceRole.setDescription("description");
        try {
            ArgumentChecker.checkResourceRoleToBeUpdated(resourceRole, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourceRoleToBeUpdated(ResourceRole, String)}.
     * </p>
     *
     * <p>
     * Given <code>ResourceRole</code> and operator are valid, <code>IllegalArgumentException</code> not expected.
     * </p>
     */
    public void testCheckResourceRoleToBeUpdated_Accuracy() {
        ResourceRole resourceRole = this.createResourceRole();
        try {
            ArgumentChecker.checkResourceRoleToBeUpdated(resourceRole, "operator");
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException not expected");
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourceRoleToBeRemoved(Resource, String)}.
     * </p>
     *
     * <p>
     * Given <code>ResourceRole</code> is null, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckResourceRoleToBeRemoved_NullResourceRole() {
        try {
            ArgumentChecker.checkResourceRoleToBeRemoved(null, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourceRoleToBeRemoved(Resource, String)}.
     * </p>
     *
     * <p>
     * Given <code>ResourceRole</code> does not have id set, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckResourceRoleToBeRemoved_IdNotSet() {
        try {
            ArgumentChecker.checkResourceRoleToBeRemoved(new ResourceRole(), "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourceRoleToBeRemoved(Resource, String)}.
     * </p>
     *
     * <p>
     * Given operator is null, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckResourceRoleToBeRemoved_NullOperator() {
        ResourceRole resourceRole = new ResourceRole(1L);
        try {
            ArgumentChecker.checkResourceRoleToBeRemoved(resourceRole, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourceRoleToBeRemoved(Resource, String)}.
     * </p>
     *
     * <p>
     * Given operator is empty, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckResourceRoleToBeRemoved_EmptyOperator() {
        ResourceRole resourceRole = new ResourceRole(1L);
        try {
            ArgumentChecker.checkResourceRoleToBeRemoved(resourceRole, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkResourceRoleToBeRemoved(Resource, String)}.
     * </p>
     *
     * <p>
     * Given <code>ResourceRole</code> and operator are valid, <code>IllegalArgumentException</code> not expected.
     * </p>
     */
    public void testCheckResourceRoleToBeRemoved_Accuracy() {
        ResourceRole resourceRole = new ResourceRole(1L);
        try {
            ArgumentChecker.checkResourceRoleToBeRemoved(resourceRole, "operator");
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException not expected");
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNotificationTypeToBeUpdated(NotificationType, String)}.
     * </p>
     *
     * <p>
     * Given <code>NotificationType</code> is null, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckNotificationTypeToBeUpdated_NullNotificationType() {
        try {
            ArgumentChecker.checkNotificationTypeToBeUpdated(null, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNotificationTypeToBeUpdated(NotificationType, String)}.
     * </p>
     *
     * <p>
     * Given <code>NotificationType</code> does not have name set,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckNotificationTypeToBeUpdated_NameNotSet() {
        NotificationType notificationType = new NotificationType(1L);
        notificationType.setDescription("description");
        try {
            ArgumentChecker.checkNotificationTypeToBeUpdated(notificationType, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNotificationTypeToBeUpdated(NotificationType, String)}.
     * </p>
     *
     * <p>
     * Given <code>NotificationType</code> does not have description set,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckNotificationTypeToBeUpdated_DescriptionNotSet() {
        NotificationType notificationType = new NotificationType(1L);
        notificationType.setName("name");
        try {
            ArgumentChecker.checkNotificationTypeToBeUpdated(notificationType, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNotificationTypeToBeUpdated(NotificationType, String)}.
     * </p>
     *
     * <p>
     * Given <code>NotificationType</code> does not have id set,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckNotificationTypeToBeUpdated_IdNotSet() {
        NotificationType notificationType = new NotificationType();
        notificationType.setName("name");
        notificationType.setDescription("description");
        try {
            ArgumentChecker.checkNotificationTypeToBeUpdated(notificationType, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNotificationTypeToBeUpdated(NotificationType, String)}.
     * </p>
     *
     * <p>
     * Given operator is null, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckNotificationTypeToBeUpdated_NullOperator() {
        NotificationType notificationType = new NotificationType(1L);
        notificationType.setName("name");
        notificationType.setDescription("description");
        try {
            ArgumentChecker.checkNotificationTypeToBeUpdated(notificationType, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNotificationTypeToBeUpdated(NotificationType, String)}.
     * </p>
     *
     * <p>
     * Given operator is empty, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckNotificationTypeToBeUpdated_EmptyOperator() {
        NotificationType notificationType = new NotificationType(1L);
        notificationType.setName("name");
        notificationType.setDescription("description");
        try {
            ArgumentChecker.checkNotificationTypeToBeUpdated(notificationType, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNotificationTypeToBeUpdated(NotificationType, String)}.
     * </p>
     *
     * <p>
     * Given <code>NotificationType</code> and operator are valid, <code>IllegalArgumentException</code> not expected.
     * </p>
     */
    public void testCheckNotificationTypeToBeUpdated_Accuracy() {
        NotificationType notificationType = this.createNotificationType();
        try {
            ArgumentChecker.checkNotificationTypeToBeUpdated(notificationType, "operator");
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException not expected");
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNotificationTypeToBeRemoved(Resource, String)}.
     * </p>
     *
     * <p>
     * Given <code>NotificationType</code> is null, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckNotificationTypeToBeRemoved_NullNotificationType() {
        try {
            ArgumentChecker.checkNotificationTypeToBeRemoved(null, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNotificationTypeToBeRemoved(Resource, String)}.
     * </p>
     *
     * <p>
     * Given <code>NotificationType</code> does not have id set, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckNotificationTypeToBeRemoved_IdNotSet() {
        try {
            ArgumentChecker.checkNotificationTypeToBeRemoved(new NotificationType(), "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNotificationTypeToBeRemoved(Resource, String)}.
     * </p>
     *
     * <p>
     * Given operator is null, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckNotificationTypeToBeRemoved_NullOperator() {
        NotificationType notificationType = new NotificationType(1L);
        try {
            ArgumentChecker.checkNotificationTypeToBeRemoved(notificationType, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNotificationTypeToBeRemoved(Resource, String)}.
     * </p>
     *
     * <p>
     * Given operator is empty, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckNotificationTypeToBeRemoved_EmptyOperator() {
        NotificationType notificationType = new NotificationType(1L);
        try {
            ArgumentChecker.checkNotificationTypeToBeRemoved(notificationType, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNotificationTypeToBeRemoved(Resource, String)}.
     * </p>
     *
     * <p>
     * Given <code>NotificationType</code> and operator are valid, <code>IllegalArgumentException</code> not expected.
     * </p>
     */
    public void testCheckNotificationTypeToBeRemoved_Accuracy() {
        NotificationType notificationType = new NotificationType(1L);
        try {
            ArgumentChecker.checkNotificationTypeToBeRemoved(notificationType, "operator");
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException not expected");
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNotificationsToBeRetrieved(long, long)}.
     * </p>
     *
     * <p>
     * Given project is negative, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckNotificationsToBeRetrieved_NegativeProject() {
        try {
            ArgumentChecker.checkNotificationsToBeRetrieved(-1L, 1L);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNotificationsToBeRetrieved(long, long)}.
     * </p>
     *
     * <p>
     * Given notification type is negative, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckNotificationsToBeRetrieved_NegativeNotificationType() {
        try {
            ArgumentChecker.checkNotificationsToBeRetrieved(1L, -1L);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNotificationsToBeRetrieved(long, long)}.
     * </p>
     *
     * <p>
     * Given project and notification type are valid, <code>IllegalArgumentException</code> not expected.
     * </p>
     */
    public void testCheckNotificationsToBeRetrieved_Accuracy() {
        try {
            ArgumentChecker.checkNotificationsToBeRetrieved(1L, 1L);
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException expected");
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNotificationsToBeAdded(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Given array of user ids is null, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckNotificationsToBeAdded_NullUserIdsArray() {
        try {
            ArgumentChecker.checkNotificationsToBeAdded(null, 1L, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNotificationsToBeAdded(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Given array of user ids is empty, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckNotificationsToBeAdded_EmptyUserIdsArray() {
        try {
            ArgumentChecker.checkNotificationsToBeAdded(new long[0], 1L, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNotificationsToBeAdded(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Given array contains an user id which is negative,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckNotificationsToBeAdded_NegativeUserId() {
        try {
            ArgumentChecker.checkNotificationsToBeAdded(new long[]{-1L}, 1L, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNotificationsToBeAdded(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Given project is negative,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckNotificationsToBeAdded_NegativeProject() {
        try {
            ArgumentChecker.checkNotificationsToBeAdded(new long[]{1L}, -1L, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNotificationsToBeAdded(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Given notification type is negative,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckNotificationsToBeAdded_NegativeNotificationType() {
        try {
            ArgumentChecker.checkNotificationsToBeAdded(new long[]{1L}, 1L, -1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNotificationsToBeAdded(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Given operator is null,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckNotificationsToBeAdded_NullOperator() {
        try {
            ArgumentChecker.checkNotificationsToBeAdded(new long[]{1L}, 1L, 1L, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNotificationsToBeAdded(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Given operator is empty,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckNotificationsToBeAdded_EmptyOperator() {
        try {
            ArgumentChecker.checkNotificationsToBeAdded(new long[]{1L}, 1L, 1L, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNotificationsToBeAdded(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Given arguments are valid,
     * <code>IllegalArgumentException</code> not expected.
     * </p>
     */
    public void testCheckNotificationsToBeAdded_Accuracy() {
        try {
            ArgumentChecker.checkNotificationsToBeAdded(new long[]{1L}, 1L, 1L, "operator");
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException not expected");
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNotificationsToBeRemoved(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Given array of user ids is null, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckNotificationsToBeRemoved_NullUserIdsArray() {
        try {
            ArgumentChecker.checkNotificationsToBeRemoved(null, 1L, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNotificationsToBeRemoved(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Given array of user ids is empty, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckNotificationsToBeRemoved_EmptyUserIdsArray() {
        try {
            ArgumentChecker.checkNotificationsToBeRemoved(new long[0], 1L, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNotificationsToBeRemoved(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Given array contains an user id which is negative,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckNotificationsToBeRemoved_NegativeUserId() {
        try {
            ArgumentChecker.checkNotificationsToBeRemoved(new long[]{-1L}, 1L, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNotificationsToBeRemoved(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Given project is negative,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckNotificationsToBeRemoved_NegativeProject() {
        try {
            ArgumentChecker.checkNotificationsToBeRemoved(new long[]{1L}, -1L, 1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNotificationsToBeRemoved(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Given notification type is negative,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckNotificationsToBeRemoved_NegativeNotificationType() {
        try {
            ArgumentChecker.checkNotificationsToBeRemoved(new long[]{1L}, 1L, -1L, "operator");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNotificationsToBeRemoved(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Given operator is null,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckNotificationsToBeRemoved_NullOperator() {
        try {
            ArgumentChecker.checkNotificationsToBeRemoved(new long[]{1L}, 1L, 1L, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNotificationsToBeRemoved(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Given operator is empty,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckNotificationsToBeRemoved_EmptyOperator() {
        try {
            ArgumentChecker.checkNotificationsToBeRemoved(new long[]{1L}, 1L, 1L, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link ArgumentChecker#checkNotificationsToBeRemoved(long[], long, long, String)}.
     * </p>
     *
     * <p>
     * Given arguments are valid,
     * <code>IllegalArgumentException</code> not expected.
     * </p>
     */
    public void testCheckNotificationsToBeRemoved_Accuracy() {
        try {
            ArgumentChecker.checkNotificationsToBeRemoved(new long[]{1L}, 1L, 1L, "operator");
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException not expected");
        }
    }
}
