/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions;

import com.topcoder.service.facade.contest.ContestServiceFacade;

/**
 * <p>
 * This is a base class only to hold the contest service facade shared among several actions.
 * </p>
 * <p>
 * <p>
 * <b>Thread Safety</b>: In <b>Struts 2</b> framework, the action is constructed for every request so the thread safety
 * is not required (instead in Struts 1 the thread safety is required because the action instances are reused). This
 * class is mutable and stateful: it's not thread safe.
 * </p>
 *
 * @author fabrizyo, FireIce
 * @version 1.0
 */
public abstract class ContestAction extends BaseDirectStrutsAction {
    /**
     * <p>
     * Represents the unique serial version id.
     * </p>
     */
    private static final long serialVersionUID = -329554689416913910L;

    /**
     * <p>
     * Represents the service used to performing the creation or update of the contest.
     * </p>
     * <p>
     * It's used in the <code>executeAction</code> method. It can't be null when the logic is performed.
     * </p>
     * <p>
     * It's injected by the setter and returned by the getter.
     * </p>
     */
    private ContestServiceFacade contestServiceFacade;

    /**
     * <p>
     * Creates a <code>ContestAction</code> instance.
     * </p>
     */
    protected ContestAction() {
    }

    /**
     * <p>
     * Gets the contest service facade.
     * </p>
     *
     * @return the contest service facade
     */
    public ContestServiceFacade getContestServiceFacade() {
        return contestServiceFacade;
    }

    /**
     * <p>
     * Sets the contest service facade.
     * </p>
     *
     * @param contestServiceFacade
     *            the contest service facade to set
     * @throws IllegalArgumentException
     *             if <b>contestServiceFacade</b> is <code>null</code>
     */
    public void setContestServiceFacade(ContestServiceFacade contestServiceFacade) {
        DirectStrutsActionsHelper.checkNull(contestServiceFacade, "contestServiceFacade");

        this.contestServiceFacade = contestServiceFacade;
    }
}
