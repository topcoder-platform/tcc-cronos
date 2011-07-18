/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.stresstests;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;

import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.reg.actions.registration.PreCreateAccountAction;
import com.topcoder.web.tc.Constants;

/**
 * <p>
 * This class contains Stress tests for PreCreateAccountAction.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class PreCreateAccountActionStressTest extends BaseStressTest {

    /**
     * <p>
     * Represents PreCreateAccountAction instance for testing.
     * </p>
     */
    private PreCreateAccountAction action;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        action = getBean("preCreateAccountAction");
        MockFactory.createUserInSession(action);
        new File(getCaptchaDir()).mkdirs();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void tearDown() throws Exception {
        super.tearDown();
        action = null;
        clearDirectory(getCaptchaDir());
    }

    /**
     * <p>
     * Clears given directory and it's subdirectories.
     * </p>
     * @param directory the directory to clear
     */
    private void clearDirectory(String directory) {
        File dir = new File(directory);
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                clearDirectory(file.getAbsolutePath());
            } else {
                file.delete();
            }
        }
        dir.delete();
    }

    /**
     * <p>
     * Tests PreCreateAccountAction#execute() method with valid fields set.
     * </p>
     * <p>
     * Action should be executed successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute() throws Exception {
        for (int i = 0; i < getRunCount(); i++) {
            assertEquals("Action should be executed successfully", ActionSupport.SUCCESS, action.execute());
            assertNotNull("Captcha word should be added to session.", action.getSession().get(Constants.CAPTCHA_WORD));
            action.getSession().remove(Constants.CAPTCHA_WORD);
        }
        assertEquals("Captches should be created successfully.", getRunCount(),
                new File(getCaptchaDir()).listFiles().length);
        verify(action.getAuditDAO(), times(getRunCount())).audit(any(AuditRecord.class));
    }
}
