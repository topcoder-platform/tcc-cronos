/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Element;

import com.orpheus.administration.TestHelper;
import com.topcoder.web.frontcontroller.ActionContext;

import junit.framework.TestCase;

/**
 * <p>
 * Test the <code>RegenerateBrainteaserOrPuzzleHandler</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class RegenerateBrainteaserOrPuzzleHandlerUnitTests extends TestCase {
    /**
     * Represents the xml string.
     */
    public static final String XMLSTRING = "<handler type=\"regenerateBrainteaser\">"
                    + "<game-data-jndi-name>"
                    + TestHelper.GAME_DATA_JNDI_NAME
                    + "</game-data-jndi-name>"
                    + "<slot-id-request-param>slotId</slot-id-request-param>"
                    + "<admin-mgr-app-attr>adminMgr</admin-mgr-app-attr >"
                    + "<fail-result>Failed</fail-result><fail-request-attribute>fail"
                    + "</fail-request-attribute></handler>";

    /**
     * This holds the JNDI name to use to look up the GameDataHome service.
     *
     */
    private final String gameDataJndiName = TestHelper.GAME_DATA_JNDI_NAME;

    /**
     * This holds the name of the request parameter which will contain the slot id
     * for which to regenerate brainteasers. The value should be able to be parsed into a long value.
     * It will never be null or empty.<br/>
     *
     */
    private final String slotIdRequestParamName = "slotId";

    /**
     * This holds the name of the application context attribute which will hold the AdministrationManager
     * instance to use to regenerate brainteasers.
     *
     */
    private final String adminMgrAttrName = "adminMgr";

    /**
     * This holds the name of the result (as configured in Front Controller component) which
     * should execute in case of execution failure.
     *
     */
    private final String failedResult = "Failed";

    /**
     * This holds the name of the request attribute to which HandlerResult instance should be
     * assigned to, in case of execution failure.
     *
     */
    private final String failRequestAttrName = "fail";

    /**
     * The HttpServletRequest instance used in tests.
     */
    private HttpServletRequest request = new MockHttpRequest();

    /**
     * The HttpServletResponse instance used in tests.
     */
    private HttpServletResponse response = new MockHttpResponse();

    /**
     * The ActionContext used in tests.
     */
    private ActionContext context;

    /**
     * <p>
     * An instance of <code>RegenerateBrainteaserOrPuzzleHandler</code> which is tested.
     * </p>
     */
    private RegenerateBrainteaserOrPuzzleHandler target = null;

    /**
     * <p>
     * setUp() routine. Creates test RegenerateBrainteaserOrPuzzleHandler instance and other
     * instances used in tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.prepareTest();
        // initialize instances used in tests
        context = new ActionContext(request, response);
        //      the xml string used for test
        String xml = XMLSTRING;
        Element element = TestHelper.loadXmlString(xml);
        target = new RegenerateBrainteaserHandler(element);
    }

    /**
     * <p>
     * Clean up all namespace of ConfigManager.
     * </p>
     *
     * @throws Exception
     *             the exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearTestEnvironment();
    }

    /**
     * <p>
     * Accuracy test. Tests the
     * <code>RegenerateBrainteaserOrPuzzleHandler(Element)</code> Create for proper
     * behavior. Verify that all fields are set correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor_1_accuracy() throws Exception {
        assertNotNull(
                "Failed to initialize RegenerateBrainteaserOrPuzzleHandler.",
                target);
        assertEquals("adminMgrAttrName", this.adminMgrAttrName, TestHelper
                .getPrivateField(RegenerateBrainteaserOrPuzzleHandler.class,
                        target, "adminMgrAttrName"));
        assertEquals("failedResult", this.failedResult, TestHelper
                .getPrivateField(RegenerateBrainteaserOrPuzzleHandler.class,
                        target, "failedResult"));
        assertEquals("failRequestAttrName", this.failRequestAttrName,
                TestHelper.getPrivateField(
                        RegenerateBrainteaserOrPuzzleHandler.class, target,
                        "failRequestAttrName"));
        assertEquals("gameDataJndiName", this.gameDataJndiName, TestHelper
                .getPrivateField(RegenerateBrainteaserOrPuzzleHandler.class,
                        target, "gameDataJndiName"));
        assertEquals("slotIdRequestParamName", this.slotIdRequestParamName,
                TestHelper.getPrivateField(
                        RegenerateBrainteaserOrPuzzleHandler.class, target,
                        "slotIdRequestParamName"));
    }

    /**
     * <p>
     * Failure test. Tests the <code>RegenerateBrainteaserOrPuzzleHandler(Element)</code> for
     * proper behavior. Verify that IllegalArgumentException is thrown if any
     * argument is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorElement_1_failure() throws Exception {
        try {
            new RegenerateBrainteaserHandler(null);
            fail("IllegalArgumentException expected if the 1st argument is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>RegenerateBrainteaserOrPuzzleHandler(Element)</code> for
     * proper behavior. Verify that IllegalArgumentException is thrown if handlerElement.getTagName() is not 'handler'.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorElement_2_failure() throws Exception {
        try {
            // the xml string used for test
            String xml = "<x type=\"regenerateBrainteaser\">"
                    + "<game-data-jndi-name>"
                    + TestHelper.GAME_DATA_JNDI_NAME
                    + "</game-data-jndi-name>"
                    + "<slot-id-request-param>slotId</slot-id-request-param>"
                    + "<admin-mgr-app-attr>adminMgr</admin-mgr-app-attr >"
                    + "<fail-result>Failed</fail-result><fail-request-attribute>fail"
                    + "</fail-request-attribute></x>";
            Element element = TestHelper.loadXmlString(xml);
            new RegenerateBrainteaserHandler(element);
            fail("IllegalArgumentException expected if handlerElement.getTagName() is not 'handler'.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>execute(ActionContext)</code> for
     * proper behavior. Verifies if the return value is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecute_1_accuracy() throws Exception {
        assertEquals("'failedResult' should be returned if success.", failedResult, target
                .execute(context));
    }

    /**
     * <p>
     * Failure test. Tests the <code>execute(ActionContext)</code> for proper
     * behavior. Verify that IllegalArgumentException is thrown if any argument
     * is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecute_1_failure() throws Exception {
        try {
            target.execute(null);
            fail("IllegalArgumentException expected if the 1st argument is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }
}
