/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.termsofuse.accuracytests;

import java.util.List;

import com.cronos.termsofuse.dao.TermsOfUseDao;
import com.cronos.termsofuse.dao.impl.TermsOfUseDaoImpl;
import com.cronos.termsofuse.model.TermsOfUse;
import com.cronos.termsofuse.model.TermsOfUseType;

/**
 * <p>
 * This class contains Accuracy tests for TermsOfUseDaoImpl.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class TermsOfUseDaoImplAccuracyTest extends BaseAccuracyTest {

    /**
     * <p>
     * Represents TermsOfUseDao for testing.
     * </p>
     */
    private TermsOfUseDao dao;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        dao = new TermsOfUseDaoImpl(getConfiguration(TERMS_OF_USE_CONFIGURATION));
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void tearDown() throws Exception {
        super.tearDown();
        dao = null;
    }

    /**
     * <p>
     * Tests TermsOfUseDaoImpl constructor.
     * </p>
     * <p>
     * TermsOfUseDaoImpl instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("TermsOfUseDaoImpl instance should be created successfully", dao);
    }

    /**
     * <p>
     * Tests {@link TermsOfUseDaoImpl#createTermsOfUse(com.cronos.termsofuse.model.TermsOfUse, String)} with valid
     * arguments passed.
     * </p>
     * <p>
     * Terms of use should be added to database successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCreateTermsOfUse() throws Exception {
        TermsOfUse termsOfUse = createTermsOfUse(0, 1, "title1", false, "some url", false);
        String termsText = "terms text";
        TermsOfUse actualTermsOfUse = dao.createTermsOfUse(termsOfUse, termsText);
        assertTrue("Terms of use id should be generated.", actualTermsOfUse.getTermsOfUseId() != 0);
        assertTrue("Terms of use should be created in database.",
                compareTermsOfUse(termsOfUse, dao.getTermsOfUse(actualTermsOfUse.getTermsOfUseId())));
    }

    /**
     * <p>
     * Tests {@link TermsOfUseDaoImpl#createTermsOfUse(com.cronos.termsofuse.model.TermsOfUse, String)} with null terms
     * text passed.
     * </p>
     * <p>
     * Terms of use should be added to database successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCreateTermsOfUse_Null_TermsText() throws Exception {
        TermsOfUse termsOfUse = createTermsOfUse(0, 1, "title1", false, "some url", false);
        String termsText = null;
        TermsOfUse actualTermsOfUse = dao.createTermsOfUse(termsOfUse, termsText);
        assertTrue("Terms of use id should be generated.", actualTermsOfUse.getTermsOfUseId() != 0);
        assertTrue("Terms of use should be created in database.",
                compareTermsOfUse(termsOfUse, dao.getTermsOfUse(actualTermsOfUse.getTermsOfUseId())));
    }

    /**
     * <p>
     * Tests {@link TermsOfUseDaoImpl#updateTermsOfUse(TermsOfUse)} with valid arguments passed.
     * </p>
     * <p>
     * Terms of use should be updated in database successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testUpdateTermsOfUse() throws Exception {
        TermsOfUse termsOfUse = createTermsOfUse(1, 1, "title1", true, "some url", true);
        TermsOfUse actualTermsOfUse = dao.updateTermsOfUse(termsOfUse);
        assertTrue("Terms of use should be updated in database.",
                compareTermsOfUse(termsOfUse, dao.getTermsOfUse(actualTermsOfUse.getTermsOfUseId())));
    }

    /**
     * <p>
     * Tests {@link TermsOfUseDaoImpl#getTermsOfUse(long)} with valid arguments passed.
     * </p>
     * <p>
     * Terms of use should be retrieved successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetTermsOfUse() throws Exception {
        long id = 1;
        TermsOfUse termsOfUse = createTermsOfUse(id, 1, "t1", false, "url1", true);
        assertTrue("Terms of use should be retrieved successfully.",
                compareTermsOfUse(termsOfUse, dao.getTermsOfUse(id)));
    }

    /**
     * <p>
     * Tests {@link TermsOfUseDaoImpl#getTermsOfUse(long)} with id for non existing terms of use passed.
     * </p>
     * <p>
     * null should be retrieved successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetTermsOfUse_Null() throws Exception {
        long id = 0;
        assertNull("null should be retrieved successfully.", dao.getTermsOfUse(id));
    }

    /**
     * <p>
     * Tests {@link TermsOfUseDaoImpl#deleteTermsOfUse(long)} with valid arguments passed.
     * </p>
     * <p>
     * Terms of use should be deleted successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testDeleteTermsOfUse() throws Exception {
        TermsOfUse termsOfUse = dao.createTermsOfUse(createTermsOfUse(0, 1, "title", true, "url", true), "termsText");
        dao.deleteTermsOfUse(termsOfUse.getTermsOfUseId());
        assertNull("Terms of use should be deleted successfully.", dao.getTermsOfUse(termsOfUse.getTermsOfUseId()));
    }

    /**
     * <p>
     * Tests {@link TermsOfUseDaoImpl#getTermsOfUseByTypeId(int)} with valid arguments passed.
     * </p>
     * <p>
     * Terms of use should be retrieved successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetTermsOfUseByTypeId() throws Exception {
        int id = 1;
        List<TermsOfUse> termsOfUses = dao.getTermsOfUseByTypeId(id);
        assertEquals("Terms of use for given type should be retrieved successfully.", 3, termsOfUses.size());
        for (TermsOfUse termsOfUse : termsOfUses) {
            assertEquals("Terms of use type should be correct.", id, termsOfUse.getTermsOfUseTypeId());
        }
    }

    /**
     * <p>
     * Tests {@link TermsOfUseDaoImpl#getTermsOfUseByTypeId(int)} with id for non existing type passed.
     * </p>
     * <p>
     * Empty collection of terms of use should be retrieved successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetTermsOfUseByTypeId_Empty() throws Exception {
        int id = 0;
        List<TermsOfUse> termsOfUses = dao.getTermsOfUseByTypeId(id);
        assertEquals("Terms of use for given type should be retrieved successfully.", 0, termsOfUses.size());
    }

    /**
     * <p>
     * Tests {@link TermsOfUseDaoImpl#getAllTermsOfUse()}.
     * </p>
     * <p>
     * Terms of use should be retrieved successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetAllTermsOfUse() throws Exception {
        List<TermsOfUse> termsOfUses = dao.getAllTermsOfUse();
        assertEquals("All terms of use should be retrieved successfully.", 4, termsOfUses.size());
    }

    /**
     * <p>
     * Tests {@link TermsOfUseDaoImpl#getTermsOfUseType(int)} with valid arguments passed.
     * </p>
     * <p>
     * Terms of use should be retrieved successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetTermsOfUseType() throws Exception {
        int id = 2;
        TermsOfUseType type = dao.getTermsOfUseType(id);
        assertNotNull("Terms of use type should be retrieved successfully.", type);
        assertTrue("Terms of use type id and description should have correct value.",
                compareTermsOfUseType(type, createTermsType(id, "type2")));
    }

    /**
     * <p>
     * Tests {@link TermsOfUseDaoImpl#getTermsOfUseType(int)} with non existing id for terms type.
     * </p>
     * <p>
     * null should be retrieved successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetTermsOfUseType_Null() throws Exception {
        int id = 0;
        TermsOfUseType type = dao.getTermsOfUseType(id);
        assertNull("null should be retrieved successfully.", type);
    }

    /**
     * <p>
     * Tests {@link TermsOfUseDaoImpl#updateTermsOfUseType(TermsOfUseType)} with valid arguments passed.
     * </p>
     * <p>
     * Terms of use should be updated successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testUpdateTermsOfUseType() throws Exception {
        int id = 2;
        TermsOfUseType type = createTermsType(id, "description");
        dao.updateTermsOfUseType(type);
        assertTrue("Terms of use type description should be updated successfully.",
                compareTermsOfUseType(type, dao.getTermsOfUseType(id)));
    }

    /**
     * <p>
     * Tests {@link TermsOfUseDaoImpl#getTermsOfUseText(long)} with valid arguments passed.
     * </p>
     * <p>
     * Terms of use text should be retrieved successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetTermsOfUseText() throws Exception {
        long id = 1;
        String text = dao.getTermsOfUseText(id);
        assertEquals("Terms of use text should be retrieved successfully.", "text1", text);
    }

    /**
     * <p>
     * Tests {@link TermsOfUseDaoImpl#getTermsOfUseText(long)} with valid arguments passed for null text.
     * </p>
     * <p>
     * null terms of use text should be retrieved successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetTermsOfUseText_Null() throws Exception {
        TermsOfUse termsOfUse = dao.createTermsOfUse(createTermsOfUse(0, 1, "title", false, "url", true), null);
        String text = dao.getTermsOfUseText(termsOfUse.getTermsOfUseId());
        assertNull("null terms of use text should be retrieved successfully.", text);
    }

    /**
     * <p>
     * Tests {@link TermsOfUseDaoImpl#setTermsOfUseText(long, String)} with valid arguments passed.
     * </p>
     * <p>
     * Terms of use text should be set successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testSetTermsOfUseText() throws Exception {
        long id = 1;
        String text = "some new text";
        dao.setTermsOfUseText(id, text);
        assertEquals("Terms of use text should be set successfully.", text, dao.getTermsOfUseText(id));
    }
}
