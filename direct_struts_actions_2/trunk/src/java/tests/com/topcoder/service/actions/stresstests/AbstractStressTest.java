/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.stresstests;

import org.apache.struts2.StrutsSpringTestCase;
import org.easymock.classextension.IMocksControl;
import org.easymock.classextension.internal.MocksClassControl;
import org.easymock.internal.MocksControl.MockType;
import org.junit.After;
import org.junit.Before;

import com.topcoder.service.facade.contest.ContestServiceFacade;

/**
 * AbstractStressTest.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public abstract class AbstractStressTest extends StrutsSpringTestCase {

    /**
     * <p>
     * The stress instance.
     * </p>
     */
    protected StressHelper stress;

    /**
     * <p>
     * The contest service facade.
     * </p>
     */
    protected ContestServiceFacade contestServiceFacade;

    /**
     * <p>
     * The control.
     * </p>
     */
    protected IMocksControl control;

    /**
     * Set up.
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        stress = new StressHelper();
        control = new MocksClassControl(MockType.DEFAULT);
        contestServiceFacade = control.createMock(ContestServiceFacade.class);

    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() throws Exception {
        stress = null;
        contestServiceFacade = null;
        super.tearDown();
    }

}
