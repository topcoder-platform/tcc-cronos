/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases;

import com.topcoder.project.phases.Phase;

import com.topcoder.util.file.Template;


/**
 * <p>
 * This interface defines the contract for the implementation classes that will generate a message using a
 * <code>Template</code> object and a <code>Phase</code> object.
 * </p>
 *
 * <p>
 *    <strong>Thread Safety:</strong>
 *    Implementations should be thread safe.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public interface EmailMessageGenerator {

    /**
     * <p>
     * This method will generate a message from the given template using the phase to retrieve the template data.
     * </p>
     *
     * @param phase The phase.
     * @param template The template.
     *
     * @return generated message.
     *
     * @throws IllegalArgumentException If any argument is null.
     * @throws EmailMessageGenerationException If any error occur while generating the message.
     */
    String generateMessage(Template template, Phase phase) throws EmailMessageGenerationException;
}
