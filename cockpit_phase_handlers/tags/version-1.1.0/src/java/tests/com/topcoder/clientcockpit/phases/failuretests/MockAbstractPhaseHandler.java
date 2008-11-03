/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases.failuretests;

import com.topcoder.clientcockpit.phases.AbstractPhaseHandler;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.project.phases.Phase;
import com.topcoder.service.studio.contest.ContestManager;

/**
 * <p>
 * This class extends AbstractPhaseHandler class.
 * It is only used for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockAbstractPhaseHandler extends AbstractPhaseHandler {
    /**
     * Creates a new instance using default namespace(the package name of this class).
     *
     * @throws PhaseHandlerConfigurationException
     *             if errors occur while retrieving the configured properties or if a required
     *             property is missing
     * @throws PhaseHandlingException
     *             if errors occur while initializing the bean
     */
    public MockAbstractPhaseHandler() throws PhaseHandlingException {
        super();
    }

    /**
     * Creates a new instance using default namespace(the package name of this class) and given
     * bean.
     *
     * @throws IllegalArgumentException
     *             if bean is null
     * @throws PhaseHandlerConfigurationException
     *             if errors occur while retrieving the configured properties or if a required
     *             property is missing
     * @param bean
     *            the bean used to work with contests
     */
    public MockAbstractPhaseHandler(ContestManager bean) throws PhaseHandlingException {
        super(bean);
    }

    /**
     * Creates a new instance using given namespace. It will retrieve the configured properties from
     * the given namespace using Configuration Manager component and it will initialize the fields.
     *
     * @throws IllegalArgumentException
     *             if namespace is null or empty string
     * @throws PhaseHandlerConfigurationException
     *             if errors occur while retrieving the configured properties or if a required
     *             property is missing, or any other errors occur while initializing
     *             EmailMessageGenerator instance.
     * @throws PhaseHandlingException
     *             if errors occur while initializing the bean
     * @param namespace
     *            the namespace to get property
     */
    public MockAbstractPhaseHandler(String namespace) throws PhaseHandlingException {
        super(namespace);
    }

    /**
     * Creates a new instance using given namespace and bean. It will retrieve the configured
     * properties from the given namespace using Configuration Manager component and it will
     * initialize the fields.
     *
     * @throws IllegalArgumentException
     *             if namespace is null or empty string or if bean is null
     * @throws PhaseHandlerConfigurationException
     *             if errors occur while retrieving the configured properties or if a required
     *             property is missing
     * @param namespace
     *            the namespace
     * @param bean
     *            the bean used to work with contests
     */
    public MockAbstractPhaseHandler(String namespace, ContestManager bean) throws PhaseHandlingException {
        super(namespace, bean);
    }

    /**
     * <p>
     * This method abstracts the common behavior
     * It is called by concrete implementations to determine whether phase can start or end.
     * </p>
     *
     *
     * @param phase The phase to determine whether it can start or end.
     * @param cockpitPhase The <code>CockpitPhase</code> enum identified by implementation. The implementation should
     *                     pass in a non-null value.
     *
     * @return true If the phase can be started or ended.
     *
     * @throws IllegalArgumentException If phase is null or if <code>cockpitPhase</code> is null.
     *
     * @throws PhaseHandlingException If phase's <code>phaseType</code> or <code>phaseStatus</code> property is null,
     *         or if phase's project does not contain a valid <em>contest</em> attribute.
     */
    public boolean canPerform(Phase arg0) throws PhaseHandlingException {
        return false;
    }

    /**
     * <p>
     * This method abstracts the common behavior.
     * It is called by concrete implementations to send email and update new contest status.
     * </p>
     *
     * @param phase The phase to apply an operation to.
     * @param cockpitPhase The <code>CockpitPhase</code> enum identified by implementation. The implementation should
     *                     pass in a non-null value.
     *
     * @throws IllegalArgumentException If phase is null or if the phase type does not match. Or if
     *         <code>cockpitPhase</code> is null.
     * @throws PhaseHandlingException If its <code>phaseType</code> or <code>phaseStatus</code> property is null.
     *         Or if its project does not contain a valid <em>contest</em> attribute.
     *         Or if errors occur while performing the phase's contest status transition.
     * @throws EmailMessageGenerationException If errors occur while generating the message to be sent.
     * @throws EmailSendingException If errors occur while sending the email message.
     */
    public void perform(Phase arg0, String arg1) throws PhaseHandlingException {

    }

}
