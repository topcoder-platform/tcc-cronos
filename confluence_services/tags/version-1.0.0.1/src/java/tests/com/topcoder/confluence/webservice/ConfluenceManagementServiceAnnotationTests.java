/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import junit.framework.TestCase;

import com.topcoder.confluence.entities.ComponentType;
import com.topcoder.confluence.entities.ConfluenceAssetType;
import com.topcoder.confluence.entities.ConfluenceCatalog;
import com.topcoder.confluence.entities.Page;

/**
 * <p>
 * UnitTest cases of the <code>ConfluenceManagementService</code> class for annotations.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ConfluenceManagementServiceAnnotationTests extends TestCase {

    /**
     * <p>
     * Verify that <code>ConfluenceManagementService</code> contains WebService annotation.
     * </p>
     */
    public void testClassAnnotation() {
        TestHelper.assertClassAnnotation(ConfluenceManagementService.class, WebService.class);
    }

    /**
     * <p>
     * Verify that login method is annotated by WebMethod.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_login_annotation() throws Exception {
        TestHelper.assertMethodAnnotation(ConfluenceManagementService.class.getMethod("login", String.class,
            String.class), WebMethod.class);
    }

    /**
     * <p>
     * Verify that logout method is annotated by WebMethod.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_logout_annotation() throws Exception {
        TestHelper.assertMethodAnnotation(ConfluenceManagementService.class.getMethod("logout", String.class),
            WebMethod.class);
    }

    /**
     * <p>
     * Verify that createPage with ComponentType method is annotated by WebMethod.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage1_annotation() throws Exception {
        TestHelper.assertMethodAnnotation(ConfluenceManagementService.class.getMethod("createPage", String.class,
            String.class, String.class, ConfluenceAssetType.class, ConfluenceCatalog.class, ComponentType.class),
            WebMethod.class);

        TestHelper.assertMethodAnnotation(ConfluenceManagementService.class.getMethod("createPage", String.class,
            String.class, String.class, ConfluenceAssetType.class, ConfluenceCatalog.class, ComponentType.class),
            RequestWrapper.class);

        TestHelper.assertMethodAnnotation(ConfluenceManagementService.class.getMethod("createPage", String.class,
            String.class, String.class, ConfluenceAssetType.class, ConfluenceCatalog.class, ComponentType.class),
            ResponseWrapper.class);
    }

    /**
     * <p>
     * Verify that createPage with applicationCode method is annotated by WebMethod.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage2_annotation() throws Exception {
        TestHelper.assertMethodAnnotation(ConfluenceManagementService.class.getMethod("createPage", String.class,
            String.class, String.class, ConfluenceAssetType.class, ConfluenceCatalog.class, String.class),
            WebMethod.class);

        TestHelper.assertMethodAnnotation(ConfluenceManagementService.class.getMethod("createPage", String.class,
            String.class, String.class, ConfluenceAssetType.class, ConfluenceCatalog.class, String.class),
            RequestWrapper.class);

        TestHelper.assertMethodAnnotation(ConfluenceManagementService.class.getMethod("createPage", String.class,
            String.class, String.class, ConfluenceAssetType.class, ConfluenceCatalog.class, String.class),
            ResponseWrapper.class);
    }

    /**
     * <p>
     * Verify that createPage with page method is annotated by WebMethod.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage3_annotation() throws Exception {
        TestHelper.assertMethodAnnotation(ConfluenceManagementService.class.getMethod("createPage", String.class,
            Page.class), WebMethod.class);

        TestHelper.assertMethodAnnotation(ConfluenceManagementService.class.getMethod("createPage", String.class,
            Page.class), RequestWrapper.class);

        TestHelper.assertMethodAnnotation(ConfluenceManagementService.class.getMethod("createPage", String.class,
            Page.class), ResponseWrapper.class);
    }

    /**
     * <p>
     * Verify that retrievePage with ComponentType method is annotated by WebMethod.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage1_annotation() throws Exception {
        TestHelper.assertMethodAnnotation(ConfluenceManagementService.class.getMethod("retrievePage",
            String.class, String.class, String.class, ConfluenceAssetType.class, ConfluenceCatalog.class,
            ComponentType.class), WebMethod.class);

        TestHelper.assertMethodAnnotation(ConfluenceManagementService.class.getMethod("retrievePage",
            String.class, String.class, String.class, ConfluenceAssetType.class, ConfluenceCatalog.class,
            ComponentType.class), RequestWrapper.class);

        TestHelper.assertMethodAnnotation(ConfluenceManagementService.class.getMethod("retrievePage",
            String.class, String.class, String.class, ConfluenceAssetType.class, ConfluenceCatalog.class,
            ComponentType.class), ResponseWrapper.class);
    }

    /**
     * <p>
     * Verify that retrievePage with applicationCode method is annotated by WebMethod.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage2_annotation() throws Exception {
        TestHelper.assertMethodAnnotation(ConfluenceManagementService.class.getMethod("createPage", String.class,
            String.class, String.class, ConfluenceAssetType.class, ConfluenceCatalog.class, String.class),
            WebMethod.class);

        TestHelper.assertMethodAnnotation(ConfluenceManagementService.class.getMethod("createPage", String.class,
            String.class, String.class, ConfluenceAssetType.class, ConfluenceCatalog.class, String.class),
            RequestWrapper.class);

        TestHelper.assertMethodAnnotation(ConfluenceManagementService.class.getMethod("createPage", String.class,
            String.class, String.class, ConfluenceAssetType.class, ConfluenceCatalog.class, String.class),
            ResponseWrapper.class);
    }
}