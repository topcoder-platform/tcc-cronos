/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * User Project Data Store 1.0
 */
package com.cronos.onlinereview.external.accuracytests.impl;

import com.cronos.onlinereview.external.accuracytests.AccuracyHelper;
import com.cronos.onlinereview.external.impl.ExternalObjectImpl;
import com.cronos.onlinereview.external.impl.ExternalProjectImpl;


/**
 * <p>
 * Tests the ExternalProjectImpl class.
 * </p>
 *
 * @author lyt
 * @version 1.0
 */
public class ExternalProjectImplAccuracyTest extends ExternalObjectImplAccuracyTest {
    /**
     * <p>
     * The default version id of the projectImpl.
     * </p>
     */
    private static final long VERSION_ID = 100;

    /**
     * <p>
     * The default version string of the projectImpl.
     * </p>
     */
    private static final String VERSION_STRING = "version 1.0";

    /**
     * <p>
     * The default catalog id of the projectImpl.
     * </p>
     */
    private static final long CATALOG_ID = 50;

    /**
     * <p>
     * The default comment string of the projectImpl.
     * </p>
     */
    private static final String COMMENT_STRING = "Comment about User Project Data Store 1.0";

    /**
     * <p>
     * The default component id of the projectImpl.
     * </p>
     */
    private static final long COMPONENT_ID = 22826649;

    /**
     * <p>
     * The default forum id of the projectImpl.
     * </p>
     */
    private static final long FORUM_ID = 12345;

    /**
     * <p>
     * The default description string of the projectImpl.
     * </p>
     */
    private static final String defaultDescriptionString = "This componnet named User Project Data Store " +
        "and version 1.0";

    /**
     * <p>
     * The default name string of the projectImpl.
     * </p>
     */
    private static final String defaultNameString = "User Project Data Store";

    /**
     * <p>
     * An ExternalProjectImpl instance for testing.
     * </p>
     */
    private ExternalProjectImpl externalProject = null;

    /**
     * <p>
     * Initialization.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        externalProject = new ExternalProjectImpl(ID, VERSION_ID, VERSION_STRING);
        externalObject = externalProject;
    }

    /**
     * <p>
     * Tests the accuracy of the Constructor(long, long, String).
     * </p>
     * 
     * <p>
     * The ExternalProjectImpl instance should be created successfully.
     * </p>
     */
    public void testConstructor_Accuracy() {
        assertTrue("projectImpl should be instance of ExternalProjectImpl.",
            externalProject instanceof ExternalProjectImpl);
        assertEquals("Tests the accuracy of Constructor failed.", new Long(ID),
            AccuracyHelper.getPrivateField(ExternalObjectImpl.class, externalObject, "id"));
        assertEquals("Tests the accuracy of Constructor failed.", new Long(VERSION_ID),
            AccuracyHelper.getPrivateField(ExternalProjectImpl.class, externalObject, "versionId"));
        assertEquals("Tests the accuracy of Constructor failed.", VERSION_STRING,
            AccuracyHelper.getPrivateField(ExternalProjectImpl.class, externalObject, "version"));
    }

    /**
     * <p>
     * Tests the accuracy of the getter getId().
     * </p>
     */
    public void testGetId_Accuracy() {
        assertEquals("The id should be got correctly.", ID, externalProject.getId());
    }

    /**
     * <p>
     * Tests the accuracy of the getter getVersionId()().
     * </p>
     */
    public void testGetVersionId_Accuracy() {
        assertEquals("The version id should be got correctly.", VERSION_ID, externalProject.getVersionId());
    }

    /**
     * <p>
     * Tests the accuracy of the getter getVersion()().
     * </p>
     */
    public void testGetVersion_Accuracy() {
        assertEquals("The version string should be got correctly.", VERSION_STRING, externalProject.getVersion());
    }

    /**
     * <p>
     * Tests the accuracy of the setter setCatalogId(long).
     * </p>
     * 
     * <p>
     * Using reflection.
     * </p>
     */
    public void testSetCatalogId_Accuracy() {
        externalProject.setCatalogId(CATALOG_ID);

        assertEquals("The catalog id should be set correctly.", new Long(CATALOG_ID),
            AccuracyHelper.getPrivateField(ExternalProjectImpl.class, externalProject, "catalogId"));
    }

    /**
     * <p>
     * Tests the accuracy of the setter setComments(String).
     * </p>
     * 
     * <p>
     * Using reflection.
     * </p>
     */
    public void testSetComments_Accuracy() {
        externalProject.setComments(COMMENT_STRING);

        assertEquals("The comments should be set correctly.", COMMENT_STRING,
            AccuracyHelper.getPrivateField(ExternalProjectImpl.class, externalProject, "comments"));
    }

    /**
     * <p>
     * Tests the accuracy of the setter setComponentId(long).
     * </p>
     * 
     * <p>
     * Using reflection.
     * </p>
     */
    public void testSetComponentId_Accuracy() {
        externalProject.setComponentId(COMPONENT_ID);

        assertEquals("The componentId should be set correctly.", new Long(COMPONENT_ID),
            AccuracyHelper.getPrivateField(ExternalProjectImpl.class, externalProject, "componentId"));
    }

    /**
     * <p>
     * Tests the accuracy of the setter setDescription(String).
     * </p>
     * 
     * <p>
     * Using reflection.
     * </p>
     */
    public void testSetDescription_Accuracy() {
        externalProject.setDescription(defaultDescriptionString);

        assertEquals("The description should be set correctly.", defaultDescriptionString,
            AccuracyHelper.getPrivateField(ExternalProjectImpl.class, externalProject, "description"));
    }

    /**
     * <p>
     * Tests the accuracy of the setter setForumId(long).
     * </p>
     * 
     * <p>
     * Using reflection.
     * </p>
     */
    public void testSetForumId_Accuracy() {
        externalProject.setForumId(FORUM_ID);

        assertEquals("The forumId should be set correctly.", new Long(FORUM_ID),
            AccuracyHelper.getPrivateField(ExternalProjectImpl.class, externalProject, "forumId"));
    }

    /**
     * <p>
     * Tests the accuracy of the setter setName(String).
     * </p>
     * 
     * <p>
     * Using reflection.
     * </p>
     */
    public void testSetName_Accuracy() {
        externalProject.setName(defaultNameString);

        assertEquals("The name should be set correctly.", defaultNameString,
            AccuracyHelper.getPrivateField(ExternalProjectImpl.class, externalProject, "name"));
    }

    /**
     * <p>
     * Tests the accuracy of the getter getCatalogId().
     * </p>
     * 
     * <p>
     * Using validated setter to test.
     * </p>
     */
    public void testGetCatalogId() {
        externalProject.setCatalogId(CATALOG_ID);

        assertEquals("The catalog id should be got correctly.", CATALOG_ID, externalProject.getCatalogId());
    }

    /**
     * <p>
     * Tests the accuracy of the getter getCatalogId().
     * </p>
     * 
     * <p>
     * If is no component, -1 would be returned.
     * </p>
     */
    public void testGetCatalogId_NoCatalogIdSet() {
        assertEquals("The catalog id should be got correctly.", -1, externalProject.getCatalogId());
    }

    /**
     * <p>
     * Tests the accuracy of the getter getComments().
     * </p>
     * 
     * <p>
     * Using validated setter to test.
     * </p>
     */
    public void testGetComments() {
        externalProject.setComments(COMMENT_STRING);

        assertEquals("The comments should be got correctly.", COMMENT_STRING, externalProject.getComments());
    }

    /**
     * <p>
     * Tests the accuracy of the getter getComments().
     * </p>
     * 
     * <p>
     * If is no comments, null would be returned.
     * </p>
     */
    public void testGetComments_NoCommentsSet() {
        assertNull("The comments got should be null.", externalProject.getComments());
    }

    /**
     * <p>
     * Tests the accuracy of the getter getComponentId().
     * </p>
     * 
     * <p>
     * Using validated setter to test.
     * </p>
     */
    public void testGetComponentId() {
        externalProject.setComponentId(COMPONENT_ID);

        assertEquals("The componentId should be got correctly.", COMPONENT_ID, externalProject.getComponentId());
    }

    /**
     * <p>
     * Tests the accuracy of the getter getComponentId().
     * </p>
     * 
     * <p>
     * If is no component id, -1 would be returned.
     * </p>
     */
    public void testGetComponentId_NoComponentIdSet() {
        assertEquals("The component id should be got correctly.", -1, externalProject.getComponentId());
    }

    /**
     * <p>
     * Tests the accuracy of the getter getDescription().
     * </p>
     * 
     * <p>
     * Using validated setter to test.
     * </p>
     */
    public void testGetDescription() {
        externalProject.setDescription(defaultDescriptionString);

        assertEquals("The description should be got correctly.", defaultDescriptionString,
            externalProject.getDescription());
    }

    /**
     * <p>
     * Tests the accuracy of the getter getDescription().
     * </p>
     * 
     * <p>
     * If is no description, null would be returned.
     * </p>
     */
    public void testGetDescription_NoDescriptionSet() {
        assertNull("The description got should be null.", externalProject.getDescription());
    }

    /**
     * <p>
     * Tests the accuracy of the getter getForumId().
     * </p>
     * 
     * <p>
     * Using validated setter to test.
     * </p>
     */
    public void testGetForumId_Accuracy() {
        externalProject.setForumId(FORUM_ID);

        assertEquals("The forumId should be got correctly.", FORUM_ID, externalProject.getForumId());
    }

    /**
     * <p>
     * Tests the accuracy of the getter getName().
     * </p>
     * 
     * <p>
     * Using validated setter to test.
     * </p>
     */
    public void testGetName_Accuracy() {
        externalProject.setName(defaultNameString);

        assertEquals("The name should be got correctly.", defaultNameString, externalProject.getName());
    }
}
