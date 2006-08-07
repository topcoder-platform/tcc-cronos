/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.phase;

import com.topcoder.project.phases.Phase;

/**
 * <p>
 * Optional, pluggable phase handling mechanism that can be configured per phase type/operation. The
 * handler will provide the decision of whether the start, end or cancel operations can be performed
 * as well as extra logic when the phase is starting, ending or canceling. Notice that the status
 * and timestamp persistence is still handled by the component.
 * </p>
 * <p>
 * Cycle of usage for a handler: A handler will be plugged into a PhaseManager implementation. When
 * a user wants a phase to be changed, the manager will check if a handler for that phase (i.e. for
 * that PhaseType and for the operation being done such as START, END, or CANCEL a phase) exists and
 * will then use the handler to make decisions about what to do, as well as use the handler for
 * additional work if phase can be changed. We have the following invocation scenarios from a
 * PhaseManager implementation with reference to phase handlers: - PhaseManager.canStart() - if a
 * handler exists in the registry then the manager invokes the handler's canPerform() method to see
 * if we can change phase (i.e. start a new phase) and if yes, then we use handler.perform() for any
 * additional tasks to be performed. - PhaseManager.canEnd() - if a handler exists in the registry
 * use handler.canPerform()method to see if we can change phase (i.e. end current phase) and then we
 * use handler.perform() for any additional tasks to be performed. - PhaseManager.canCancel() - if a
 * handler exists use handler.canPerform()method to see if we can change phase (i.e. cancel current
 * phase) and then we use handler.perform() for any additional tasks to be performed. -
 * PhaseManager.start() - if a handler exists do handler.perform(). - PhaseManager.end() - if a
 * handler exists do handler.perform(). - PhaseManager.cancel() - if a handler exists do
 * handler.perform().
 * </p>
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public interface PhaseHandler {
    /**
     * <p>
     * The handler will provide the decision of whether the start, end or cancel operations can be
     * performed for the input phase.
     * </p>
     * <p>
     * @throws IllegalArgumentException if input parameter is null.
     * </p>
     * @param phase phase to test
     * @return true if an associated action can be performed
     */
    public boolean canPerform(Phase phase);

    /**
     * <p>
     * Extra logic to be used when the phase is starting, ending or canceling. This will be called
     * by the PhaseManager implementation at phase change time to perform additional tasks that are
     * due when the input phase has changed (moved to the next phase)
     * </p>
     * <p>
     * @throws IllegalArgumentException if any input parameter is null, or if
     * operator is an empty string
     * </p>
     * @param phase phase to apply an operation to
     * @param operator operator applying
     */
    public void perform(Phase phase, String operator);
}
