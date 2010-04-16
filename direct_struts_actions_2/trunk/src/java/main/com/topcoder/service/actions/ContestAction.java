/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.util.errorhandling.ExceptionUtils;

/**
 * <p>
 * This is a base class used only to hold the contest service facade shared among several actions. It extends
 * <code>BaseDirectStrutsAction</code>.
 * </p>
 *
 * <p>
 * <b>Thread safety:</b> In Struts 2 framework the action is constructed for every request so the thread
 * safety is not required (instead in Struts 1 the thread safety is required because the action instances are
 * reused). This class is mutable and stateful, so it's not thread safe.
 * </p>
 *
 * @author fabrizyo, TCSDEVELOPER
 * @version 1.0
 */
public abstract class ContestAction extends BaseDirectStrutsAction {

    /**
     * <p>
     * The service used to perform the contest operations needed by this component.
     * </p>
     *
     * <p>
     * It's used when the action is executed. It can't be null when the logic is performed. It's injected by
     * the setter and returned by the getter.
     * </p>
     */
    private ContestServiceFacade contestServiceFacade;

    /**
     * Default constructor, creates new instance.
     */
    protected ContestAction() {
    }

    /**
     * Getter for the contest service facade.
     *
     * @return the contest service facade
     */
    public ContestServiceFacade getContestServiceFacade() {
        return contestServiceFacade;
    }

    /**
     * Setter for the contest service facade.
     *
     * @param contestServiceFacade the contest service facade
     */
    public void setContestServiceFacade(ContestServiceFacade contestServiceFacade) {
        ExceptionUtils.checkNull(contestServiceFacade, null, null, "contestServiceFacade cannot be null");
        this.contestServiceFacade = contestServiceFacade;
    }
}
