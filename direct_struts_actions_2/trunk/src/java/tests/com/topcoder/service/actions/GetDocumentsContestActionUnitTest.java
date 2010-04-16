/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.catalog.entity.CompDocumentation;
import com.topcoder.catalog.entity.CompUploadedFile;
import com.topcoder.catalog.service.AssetDTO;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.project.StudioCompetition;
import com.topcoder.service.studio.ContestData;
import com.topcoder.service.studio.UploadedDocument;

/**
 * <p>
 * Unit tests for <code>GetDocumentsContestAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GetDocumentsContestActionUnitTest extends TestCase {

    /**
     * The instance used in testing.
     */
    private GetDocumentsContestAction instance;

    /**
     * Sets up the environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new MockGetDocumentsContestAction();
        instance.prepare();
    }

    /**
     * Tears down the environment.
     *
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        instance = null;
    }

    /**
     * Tests the class inheritance to make sure it is correct.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testInheritance() throws Exception {
        TestHelper.assertSuperclass(instance.getClass().getSuperclass(), StudioOrSoftwareContestAction.class);
    }

    /**
     * Accuracy test for constructor. Verifies the new instance is created.
     */
    public void testConstructor() {
        assertNotNull("unable to create instance", instance);
    }

    /**
     * Accuracy test for executeAction. The correct document should be present in model for studio competition.
     *
     * @throws Exception to JUnit
     */
    @SuppressWarnings("unchecked")
    @Test
    public void test_executeAction_Accuracy1() throws Exception {
        ContestData contestData = new ContestData();
        UploadedDocument doc = new UploadedDocument();
        doc.setDocumentId(1);
        doc.setContestId(1);
        doc.setFileName("test.txt");
        doc.setPath("test_files");
        doc.setMimeTypeId(new MimeTypeRetriever().getMimeTypeIdFromFileName("test_files/test.txt"));
        List<UploadedDocument> docUploads = new ArrayList<UploadedDocument>();
        docUploads.add(doc);
        contestData.setDocumentationUploads(docUploads);

        StudioCompetition studioCompetition = new StudioCompetition(contestData);
        studioCompetition.setId(1);
        MockContestServiceFacade.addStudioCompetition(studioCompetition);

        instance.setContestId(1);
        instance.executeAction();

        // make sure correct data is in model
        Object ret = instance.getModel().getData("return");
        assertNotNull("return object in model should not be null", ret);
        assertTrue("return object is of wrong type", ret instanceof List);
        assertEquals("wrong number of documents returned", 1, ((List<UploadedDocument>) ret).size());
        UploadedDocument doc2 = ((List<UploadedDocument>) ret).get(0);
        assertSame("wrong document returned", doc, doc2);
    }

    /**
     * Accuracy test for executeAction. The correct document should be present in model for software competition.
     *
     * @throws Exception to JUnit
     */
    @SuppressWarnings("unchecked")
    @Test
    public void test_executeAction_Accuracy2() throws Exception {
        AssetDTO assetDTO = new AssetDTO();
        CompUploadedFile compFile = new CompUploadedFile();
        compFile.setUploadedFileName("test.txt");
        compFile.setUploadedFileDesc("test");
        CompDocumentation doc = new CompDocumentation();
        doc.setId(1L);
        doc.setUrl("http://test.com");
        assetDTO.setDocumentation(new ArrayList<CompDocumentation>());
        assetDTO.getDocumentation().add(doc);

        SoftwareCompetition softwareCompetition = new SoftwareCompetition();
        softwareCompetition.setId(1);
        softwareCompetition.setAssetDTO(assetDTO);
        MockContestServiceFacade.addSoftwareCompetition(softwareCompetition);

        instance.setContestId(0);
        instance.setProjectId(1);
        instance.executeAction();

        // make sure correct data is in model
        Object ret = instance.getModel().getData("return");
        assertNotNull("return object in model should not be null", ret);
        assertTrue("return object is of wrong type", ret instanceof List);
        assertEquals("wrong number of documents returned", 1, ((List<CompDocumentation>) ret).size());
        CompDocumentation doc2 = ((List<CompDocumentation>) ret).get(0);
        assertSame("wrong document returned", doc, doc2);
    }

    /**
     * Failure test for executeAction. The <code>contestServiceFacade</code> hasn't been injected, so
     * IllegalStateException is expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_Failure1() throws Exception {
        try {
            TestHelper.setInstanceField(instance, ContestAction.class, "contestServiceFacade", null);
            instance.executeAction();
            fail("IllegalStateException is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Accuracy test for performLogic. The correct document should be present in model.
     *
     * @throws Exception to JUnit
     */
    @SuppressWarnings("unchecked")
    @Test
    public void test_performLogic_Accuracy1() throws Exception {
        List<UploadedDocument> list = new ArrayList<UploadedDocument>();
        UploadedDocument doc = new UploadedDocument();
        doc.setDocumentId(1);
        list.add(doc);
        instance.performLogic(list);

        // make sure correct data is in model
        Object ret = instance.getModel().getData("return");
        assertNotNull("return object in model should not be null", ret);
        assertTrue("return object is of wrong type", ret instanceof List);
        assertEquals("wrong number of documents returned", 1, ((List<UploadedDocument>) ret).size());
        UploadedDocument doc2 = ((List<UploadedDocument>) ret).get(0);
        assertSame("wrong document returned", doc, doc2);
    }

    /**
     * Accuracy test for performLogic. No document should be present in model, but model should contain
     * empty list.
     *
     * @throws Exception to JUnit
     */
    @SuppressWarnings("unchecked")
    @Test
    public void test_performLogic_Accuracy2() throws Exception {
        List<UploadedDocument> list = new ArrayList<UploadedDocument>();
        instance.performLogic(list);

        // make sure correct data is in model
        Object ret = instance.getModel().getData("return");
        assertNotNull("return object in model should not be null", ret);
        assertTrue("return object is of wrong type", ret instanceof List);
        assertEquals("wrong number of documents returned", 0, ((List<UploadedDocument>) ret).size());
    }

    /**
     * Accuracy test for performLogic. Since competition documents contains null elements, no result
     * should be set in model.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_performLogic_Accuracy3() throws Exception {
        List<UploadedDocument> list = new ArrayList<UploadedDocument>();
        list.add(null);
        instance.performLogic(list);

        // make sure model is correct
        Object ret = instance.getModel().getData("return");
        assertNull("return object in model should be null", ret);
    }

    /**
     * Accuracy test for performLogic. Since competition documents is null, no result
     * should be set in model.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_performLogic_Accuracy4() throws Exception {
        instance.performLogic(null);

        // make sure model is correct
        Object ret = instance.getModel().getData("return");
        assertNull("return object in model should be null", ret);
    }

}
