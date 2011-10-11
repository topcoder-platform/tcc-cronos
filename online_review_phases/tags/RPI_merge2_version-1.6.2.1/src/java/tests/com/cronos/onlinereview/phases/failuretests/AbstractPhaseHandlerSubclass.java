/*
 * Copyright (C) 2006-2011, TopCoder Inc. All Rights Reserved.
 */
package com.cronos.onlinereview.phases.failuretests;

import com.cronos.onlinereview.phases.AbstractPhaseHandler;
import com.cronos.onlinereview.phases.ConfigurationException;
import com.topcoder.management.phase.OperationCheckResult;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.project.phases.Phase;

import java.sql.Connection;

/**
 * <p>A subclass of <code>AbstractPhaseHandler</code> class to be used to test the protected methods
 * of the super class. Overrides the protected methods declared by a super-class.
 * The overridden methods are declared with package private access so only the test cases could invoke them.
 * The overridden methods simply call the corresponding method of a super-class.
 * <p>
 * Version 1.6.2 (Online Review Phases) Change notes:
 * <ol>
 * <li>Changed return type of canPerform() methods from boolean to OperationCheckResult.</li>
 * <li>update the JavaDoc.</li>
 * </ol>
 * </p>
 *
 * @author isv, TMALBONPH
 * @version 1.6.2
 * @since 1.0
 */
class AbstractPhaseHandlerSubclass extends AbstractPhaseHandler {

    /**
     * <p>Constructs new <code>AbstractPhaseHandlerSubclass</code> instance. Nothing special occurs here.</p>
     *
     * @param namespace the namespace to load configuration settings from.
     *
     * @throws IllegalArgumentException if the input is null or empty string.
     * @throws ConfigurationException if errors occurred while loading configuration settings
     * or required properties missing.
     */
    public AbstractPhaseHandlerSubclass(String namespace) throws ConfigurationException {
        super(namespace);
    }

    /**
     * <p>Calls corresponding method of a super class providing the specified argument.
     * Nothing special occurs here.</p>
     *
     * @param phase If this is true, start phase email will be sent. Otherwise, end phase email will be sent.
     * @throws IllegalArgumentException if the input is null or empty string.
     * @throws PhaseHandlingException if there was a problem when sending email.
     */
    public void sendEmail(Phase phase) throws PhaseHandlingException {
        super.sendEmail(phase);
    }

    /**
     * <p>Calls corresponding method of a super class providing the specified argument.
     * Nothing special occurs here.</p>
     *
     * @throws PhaseHandlingException if connection could not be created.
     */
    public Connection createConnection() throws PhaseHandlingException {
        return super.createConnection();
    }

    /**
     * The handler will make the decision as to whether the start, end or cancel operations
     * can be performed for the specified phase.
     *
     * @param phase phase to test
     * @return a non-null <code>OperationCheckResult</code>.
     * @throws IllegalArgumentException if <code>phase</code> is <code>null</code>
     * @throws PhaseHandlingException If an error occurs while performing the operations.
     */
    public OperationCheckResult canPerform(Phase phase) throws PhaseHandlingException {

        return new OperationCheckResult(false, "Force failure for AbstractPhaseHandlerSubclass#canPerform(Phase)");
    }

    /**
     * Extra logic to be used when the phase is starting, ending or canceling. This will be called by the {@link
     * PhaseManager PhaseManager} implementation at phase change time to perform additional tasks that are due when the
     * specified phase has changed (moved to the next phase).
     *
     * @param phase phase to apply an operation to
     * @param operator operator applying
     * @throws IllegalArgumentException if any argument is <code>null</code>,
     * or if <code>operator</code> is an empty string
     * @throws PhaseHandlingException If an error occurs while performing the operations.
     */
    public void perform(Phase phase, String operator) throws PhaseHandlingException {
        // does nothing.
    }
}
