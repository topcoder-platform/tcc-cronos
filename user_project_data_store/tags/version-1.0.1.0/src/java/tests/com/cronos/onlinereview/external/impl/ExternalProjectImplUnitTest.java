/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * User Project Data Store 1.0
 */
package com.cronos.onlinereview.external.impl;

import com.cronos.onlinereview.external.UnitTestHelper;

import junit.framework.TestCase;

/**
 * <p>Tests the ExternalProjectImpl class.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ExternalProjectImplUnitTest extends TestCase {

    /**
     * <p>The default id of the projectImpl.</p>
     */
    private static final long defaultID = 123;

    /**
     * <p>The default version id of the projectImpl.</p>
     */
    private static final long defaultVersionID = 100;

    /**
     * <p>The default version string of the projectImpl.</p>
     */
    private static final String defaultVersionString = "version 1.0";

    /**
     * <p>The default catalog id of the projectImpl.</p>
     */
    private static final long defaultCatalogID = 50;

    /**
     * <p>The default comment string of the projectImpl.</p>
     */
    private static final String defaultCommentString = "Comment about User Project Data Store 1.0";

    /**
     * <p>The default component id of the projectImpl.</p>
     */
    private static final long defaultComponentID = 22826649;

    /**
     * <p>The default forum id of the projectImpl.</p>
     */
    private static final long defaultForumId = 12345;

    /**
     * <p>The default description string of the projectImpl.</p>
     */
    private static final String defaultDescriptionString = "This componnet named User Project Data Store "
        + "and version 1.0";

    /**
     * <p>The default name string of the projectImpl.</p>
     */
    private static final String defaultNameString = "User Project Data Store";

    /**
     * <p>An ExternalProjectImpl instance for testing.</p>
     */
    private ExternalProjectImpl projectImpl = null;

    /**
     * <p>Initialization.</p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        projectImpl = new ExternalProjectImpl(defaultID, defaultVersionID, defaultVersionString);
    }

    /**
     * <p>Set objectImpl to null.</p>
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        projectImpl = null;
    }

    /**
     * <p>Tests the accuracy of the ctor(long, long, String).</p>
     * <p>The ExternalProjectImpl instance should be created successfully.</p>
     */
    public void testCtor_LongLongString() {

        assertNotNull("ExternalProjectImpl should be accurately created.", projectImpl);
        assertTrue("projectImpl should be instance of ExternalProjectImpl.",
                projectImpl instanceof ExternalProjectImpl);
    }

    /**
     * <p>Tests the accuracy of the getter getId().</p>
     */
    public void testGetter_GetId() {

        assertEquals("The id should be got correctly.",
                defaultID, projectImpl.getId());
    }

    /**
     * <p>Tests the accuracy of the getter getVersionId()().</p>
     */
    public void testGetter_GetVersionId() {

        assertEquals("The version id should be got correctly.",
                defaultVersionID, projectImpl.getVersionId());
    }

    /**
     * <p>Tests the accuracy of the getter getVersion()().</p>
     */
    public void testGetter_GetVersion() {

        assertEquals("The version string should be got correctly.",
                defaultVersionString, projectImpl.getVersion());
    }

    /**
     * <p>Tests the failure of the ctor(long, long, String).</p>
     * <p>If the given id is negative, IllegalArgumentException should be thrown.</p>
     */
    public void testCtor_LongLongString_NegativeId() {

        try {
            new ExternalProjectImpl(-1, defaultVersionID, defaultVersionString);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>Tests the failure of the ctor(long, long, String).</p>
     * <p>If the given version id is negative, IllegalArgumentException should be thrown.</p>
     */
    public void testCtor_LongLongString_NegativeVersionId() {

        try {
            new ExternalProjectImpl(defaultID, -2, defaultVersionString);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>Tests the failure of the ctor(long, long, String).</p>
     * <p>If the given version is null, IllegalArgumentException should be thrown.</p>
     */
    public void testCtor_LongLongString_NullVersion() {

        try {
            new ExternalProjectImpl(defaultID, defaultVersionID, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>Tests the accuracy of the setter setCatalogId(long).</p>
     * <p>Using reflection.</p>
     */
    public void testSetter_SetCatalogId() {

        projectImpl.setCatalogId(defaultCatalogID);

        // Gets catalogID by reflection.
        Object catalogId = UnitTestHelper.getPrivateField(ExternalProjectImpl.class, projectImpl, "catalogId");

        assertEquals("The catalog id should be set correctly.",
                new Long(defaultCatalogID), catalogId);
    }

    /**
     * <p>Tests the accuracy of the setter setComments(String).</p>
     * <p>Using reflection.</p>
     */
    public void testSetter_SetComments() {

        projectImpl.setComments(defaultCommentString);

        // Gets comments by reflection.
        Object comments = UnitTestHelper.getPrivateField(ExternalProjectImpl.class, projectImpl, "comments");

        assertEquals("The comments should be set correctly.",
                defaultCommentString, comments);
    }

    /**
     * <p>Tests the accuracy of the setter setComponentId(long).</p>
     * <p>Using reflection.</p>
     */
    public void testSetter_SetComponentId() {

        projectImpl.setComponentId(defaultComponentID);

        // Gets componentId by reflection.
        Object componentId = UnitTestHelper.getPrivateField(ExternalProjectImpl.class, projectImpl, "componentId");

        assertEquals("The componentId should be set correctly.",
                new Long(defaultComponentID), componentId);
    }

    /**
     * <p>Tests the accuracy of the setter setDescription(String).</p>
     * <p>Using reflection.</p>
     */
    public void testSetter_SetDescription() {

        projectImpl.setDescription(defaultDescriptionString);

        // Gets description by reflection.
        Object description = UnitTestHelper.getPrivateField(ExternalProjectImpl.class, projectImpl, "description");

        assertEquals("The description should be set correctly.",
                defaultDescriptionString, description);
    }

    /**
     * <p>Tests the accuracy of the setter setForumId(long).</p>
     * <p>Using reflection.</p>
     */
    public void testSetter_SetForumId() {

        projectImpl.setForumId(defaultForumId);

        // Gets forumId by reflection.
        Object forumId = UnitTestHelper.getPrivateField(ExternalProjectImpl.class, projectImpl, "forumId");

        assertEquals("The forumId should be set correctly.",
                new Long(defaultForumId), forumId);
    }

    /**
     * <p>Tests the accuracy of the setter setName(String).</p>
     * <p>Using reflection.</p>
     */
    public void testSetter_SetName() {

        projectImpl.setName(defaultNameString);

        // Gets name by reflection.
        Object name = UnitTestHelper.getPrivateField(ExternalProjectImpl.class, projectImpl, "name");

        assertEquals("The name should be set correctly.",
                defaultNameString, name);
    }

    /**
     * <p>Tests the failure of the setter setCatalogId(long).</p>
     * <p>If the given catalogId is negative, IllegalArgumentException should be thrown.</p>
     */
    public void testSetter_SetCatalogId_NegativeCatalogId() {

        try {
            projectImpl.setCatalogId(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>Tests the failure of the setter setCatalogId(long).</p>
     * <p>If the catalogId field is already set, IllegalArgumentException should be thrown.</p>
     */
    public void testSetter_SetCatalogId_CatalogIdAlreadySet() {

        projectImpl.setCatalogId(defaultCatalogID);

        try {
            projectImpl.setCatalogId(defaultCatalogID);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>Tests the failure of the setter setComments(String).</p>
     * <p>If the given comments is null, IllegalArgumentException should be thrown.</p>
     */
    public void testSetter_SetComments_NullComments() {

        try {
            projectImpl.setComments(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>Tests the failure of the setter setComments(String).</p>
     * <p>If the comments field is already set, IllegalArgumentException should be thrown.</p>
     */
    public void testSetter_SetComments_CommentsAlreadySet() {

        projectImpl.setComments(defaultCommentString);

        try {
            projectImpl.setComments(defaultCommentString);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>Tests the failure of the setter setComponentId(long).</p>
     * <p>If the given componentId is negative, IllegalArgumentException should be thrown.</p>
     */
    public void testSetter_SetComponentId_NegativeComponentId() {

        try {
            projectImpl.setComponentId(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>Tests the failure of the setter setComponentId(long).</p>
     * <p>If the componentId field is already set, IllegalArgumentException should be thrown.</p>
     */
    public void testSetter_SetComponentId_ComponentIdAlreadySet() {

        projectImpl.setComponentId(defaultComponentID);

        try {
            projectImpl.setComponentId(defaultComponentID);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>Tests the failure of the setter setDescription(String).</p>
     * <p>If the given description is null, IllegalArgumentException should be thrown.</p>
     */
    public void testSetter_SetDescriptions_NullDescriptions() {

        try {
            projectImpl.setDescription(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>Tests the failure of the setter setDescription(String).</p>
     * <p>If the description field is already set, IllegalArgumentException should be thrown.</p>
     */
    public void testSetter_SetDescription_DescriptionsAlreadySet() {

        projectImpl.setDescription(defaultDescriptionString);

        try {
            projectImpl.setDescription(defaultDescriptionString);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>Tests the failure of the setter setForumId(long).</p>
     * <p>If the given forumId is negative, IllegalArgumentException should be thrown.</p>
     */
    public void testSetter_SetForumId_NegativeForumId() {

        try {
            projectImpl.setForumId(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>Tests the failure of the setter setForumId(long).</p>
     * <p>If the forumId field is already set, IllegalArgumentException should be thrown.</p>
     */
    public void testSetter_SetForumId_ForumIdAlreadySet() {

        projectImpl.setForumId(defaultForumId);

        try {
            projectImpl.setForumId(defaultForumId);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>Tests the failure of the setter setName(String).</p>
     * <p>If the given name is null, IllegalArgumentException should be thrown.</p>
     */
    public void testSetter_SetName_NullDescriptions() {

        try {
            projectImpl.setName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>Tests the failure of the setter setName(String).</p>
     * <p>If the name field is already set, IllegalArgumentException should be thrown.</p>
     */
    public void testSetter_SetName_DescriptionsAlreadySet() {

        projectImpl.setName(defaultNameString);

        try {
            projectImpl.setName(defaultNameString);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>Tests the accuracy of the getter getCatalogId().</p>
     * <p>Using validated setter to test.</p>
     */
    public void testGetter_GetCatalogId() {

        projectImpl.setCatalogId(defaultCatalogID);

        assertEquals("The catalog id should be got correctly.",
                defaultCatalogID, projectImpl.getCatalogId());
    }

    /**
     * <p>Tests the accuracy of the getter getCatalogId().</p>
     * <p>If is no component, -1 would be returned.</p>
     */
    public void testGetter_GetCatalogId_NoCatalogIdSet() {

        assertEquals("The catalog id should be got correctly.",
                -1, projectImpl.getCatalogId());
    }

    /**
     * <p>Tests the accuracy of the getter getComments().</p>
     * <p>Using validated setter to test.</p>
     */
    public void testGetter_GetComments() {

        projectImpl.setComments(defaultCommentString);

        assertEquals("The comments should be got correctly.",
                defaultCommentString, projectImpl.getComments());
    }

    /**
     * <p>Tests the accuracy of the getter getComments().</p>
     * <p>If is no comments, null would be returned.</p>
     */
    public void testGetter_GetComments_NoCommentsSet() {

        assertNull("The comments got should be null.", projectImpl.getComments());
    }

    /**
     * <p>Tests the accuracy of the getter getComponentId().</p>
     * <p>Using validated setter to test.</p>
     */
    public void testGetter_GetComponentId() {

        projectImpl.setComponentId(defaultComponentID);

        assertEquals("The componentId should be got correctly.",
                defaultComponentID, projectImpl.getComponentId());
    }

    /**
     * <p>Tests the accuracy of the getter getComponentId().</p>
     * <p>If is no component id, -1 would be returned.</p>
     */
    public void testGetter_GetComponentId_NoComponentIdSet() {

        assertEquals("The component id should be got correctly.",
                -1, projectImpl.getComponentId());
    }

    /**
     * <p>Tests the accuracy of the getter getDescription().</p>
     * <p>Using validated setter to test.</p>
     */
    public void testGetter_GetDescription() {

        projectImpl.setDescription(defaultDescriptionString);

        assertEquals("The description should be got correctly.",
                defaultDescriptionString, projectImpl.getDescription());
    }

    /**
     * <p>Tests the accuracy of the getter getDescription().</p>
     * <p>If is no description, null would be returned.</p>
     */
    public void testGetter_GetDescription_NoDescriptionSet() {

        assertNull("The description got should be null.", projectImpl.getDescription());
    }

    /**
     * <p>Tests the accuracy of the getter getForumId().</p>
     * <p>Using validated setter to test.</p>
     */
    public void testGetter_GetForumId() {

        projectImpl.setForumId(defaultForumId);

        assertEquals("The forumId should be got correctly.",
                defaultForumId, projectImpl.getForumId());
    }

    /**
     * <p>Tests the accuracy of the getter getForumId().</p>
     * <p>If is no forumId id, -1 would be returned.</p>
     */
    public void testGetter_GetForumId_NoForumIdSet() {

        assertEquals("The forum id should be got correctly.",
                -1, projectImpl.getForumId());
    }

    /**
     * <p>Tests the accuracy of the getter getName().</p>
     * <p>Using validated setter to test.</p>
     */
    public void testGetter_GetName() {

        projectImpl.setName(defaultNameString);

        assertEquals("The name should be got correctly.",
                defaultNameString, projectImpl.getName());
    }

    /**
     * <p>Tests the accuracy of the getter getName().</p>
     * <p>If is no name, null would be returned.</p>
     */
    public void testGetter_GetName_NoNameSet() {

        assertNull("The name got should be null.", projectImpl.getName());
    }
}
