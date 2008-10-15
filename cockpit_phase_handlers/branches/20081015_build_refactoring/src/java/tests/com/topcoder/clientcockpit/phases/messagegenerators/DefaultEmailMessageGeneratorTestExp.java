/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases.messagegenerators;

import java.lang.reflect.Field;

import com.topcoder.clientcockpit.phases.BaseTestCase;
import com.topcoder.clientcockpit.phases.EmailMessageGenerationException;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.file.DocumentGenerator;

/**
 * <p>
 * Failure tests for <code>DefaultEmailMessageGenerator</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DefaultEmailMessageGeneratorTestExp extends BaseTestCase {

    /**
     * <p>
     * Test {@link DefaultEmailMessageGenerator#generateMessage(
     * com.topcoder.util.file.Template, com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * Given template is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGenerateMessage_NullTemplate() throws Exception {
        try {
            new DefaultEmailMessageGenerator().generateMessage(null, this.createPhase(null, null));
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test {@link DefaultEmailMessageGenerator#generateMessage(
     * com.topcoder.util.file.Template, com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * Given phase is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGenerateMessage_NullPhase() throws Exception {
        try {
            new DefaultEmailMessageGenerator().generateMessage(new MockTemplate(), null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test {@link DefaultEmailMessageGenerator#generateMessage(
     * com.topcoder.util.file.Template, com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * Error occurs while get instance of <code>DocumentGenerator</code>,
     * <code>EmailMessageGenerationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGenerateMessage_CannotGetDocumentGenerator() throws Exception {
        Field field = DocumentGenerator.class.getDeclaredField("instance");
        field.setAccessible(true);
        field.set(null, null);

        //Remove the namespace for DocumentGenerator
        ConfigManager.getInstance().removeNamespace("com.topcoder.util.file");

        try {
            new DefaultEmailMessageGenerator().generateMessage(new MockTemplate(), this.createPhase(null, null));
            fail("EmailMessageGenerationException is expected.");
        } catch (EmailMessageGenerationException e) {
            //pass
        }
    }
}
