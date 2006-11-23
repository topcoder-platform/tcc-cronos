package com.orpheus.auction;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.cactus.ServletTestCase;
import org.apache.cactus.WebRequest;

import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

/**
 * <p>
 * Unit test cases for the Helper class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HelperUnitTests extends ServletTestCase {

    /**
     * <p>
     * Creates required instances.
     * </p>
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        UnitTestsHelper.loadConfig(UnitTestsHelper.CONFIG_FILE);
    }

    /**
     * <p>
     * Clears configuration.
     * </p>
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        UnitTestsHelper.clearConfig();

        HttpSession session = request.getSession(false);
        if (session != null) {
            // invalidate session if it exists
            session.invalidate();
        }
    }

    /**
     * <p>
     * Tests that checkNotNull(Object, String) correctly throws exception.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testCheckNotNull_1() throws Exception {
        try {
            Helper.checkNotNull(null, "param");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests that checkNotNull(Object, String) works correctly.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testCheckNotNull_2() throws Exception {
        Helper.checkNotNull(Boolean.TRUE, "param");
    }

    /**
     * <p>
     * Tests that checkString(String, String) correctly throws exception.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testCheckString_1() throws Exception {
        try {
            Helper.checkString("  ", "param");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests that checkString(String, String) correctly throws exception.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testCheckString_2() throws Exception {
        try {
            Helper.checkString(null, "param");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests that checkString(String, String) works correctly.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testCheckString_3() throws Exception {
        Helper.checkString("param", "param");
    }

    /**
     * <p>
     * Tests that getString(String, String) correctly throws Exception.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testGetString_1() throws Exception {
        try {
            Helper.getString("helper", "bad_namespace");
            fail("AuctionLogicConfigException is expected");
        } catch (AuctionLogicConfigException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests that getString(String, String) correctly throws Exception.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testGetString_2() throws Exception {
        try {
            Helper.getString("helper", "missed_property");
            fail("AuctionLogicConfigException is expected");
        } catch (AuctionLogicConfigException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests that getString(String, String) correctly throws Exception.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testGetString_3() throws Exception {
        try {
            Helper.getString("helper", "empty_property");
            fail("AuctionLogicConfigException is expected");
        } catch (AuctionLogicConfigException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests that getString(String, String) works correctly.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testGetString_4() throws Exception {
        assertEquals("String is incorrect", "abc", Helper.getString("helper", "abc"));
    }

    /**
     * <p>
     * Tests that parseHandlerElement(Element, String[]) correctly throws Exception.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testParseHandlerElement_1() throws Exception {
        try {
            Helper.parseHandlerElement(UnitTestsHelper.createHandlerElement("abc", new String[] {
                "a",
                "b"}, new String[] {"a", "b"}), new String[] {"a", "d"});
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests that parseHandlerElement(Element, String[]) correctly throws Exception. Some properties
     * are empty.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testParseHandlerElement_2() throws Exception {
        try {
            Helper.parseHandlerElement(UnitTestsHelper.createHandlerElement("abc", new String[] {
                "a",
                "b"}, new String[] {"a", "b"}), new String[] {" ", "d"});
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests that parseHandlerElement(Element, String[]) correctly throws Exception. Some properties
     * are empty.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testParseHandlerElement_3() throws Exception {
        try {
            Helper.parseHandlerElement(null, new String[] {"d"});
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests that parseHandlerElement(Element, String[]) returns correct values.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testParseHandlerElement_4() throws Exception {
        String[] res = Helper.parseHandlerElement(UnitTestsHelper.createHandlerElement("abc",
            new String[] {"a", "b"}, new String[] {"a ", "b "}), new String[] {"a", "b"});
        assertEquals("There are exactly two values", 2, res.length);
        assertEquals("The returned value is incorrect", "a", res[0]);
        assertEquals("The returned value is incorrect", "b", res[1]);
    }

    /**
     * <p>
     * Tests that getServletContext(ActionContext) returns correct value.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testGetServletContext_1() throws Exception {
        ActionContext actionContext = new ActionContext(request, response);
        request.getSession();

        assertTrue("The returned value is incorrect",
            Helper.getServletContext(actionContext) instanceof ServletContext);
    }

    /**
     * <p>
     * Tests that getServletContext(ActionContext) correctly throws Exception.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testGetServletContext_2() throws Exception {
        ActionContext actionContext = new ActionContext(request, response);
        request.getSession().invalidate();

        try {
            Helper.getServletContext(actionContext);
            fail("HandlerExecutionException is expected");
        } catch (HandlerExecutionException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests that getServletContextAttribute(ServletContext, String, Class) correctly throws
     * Exception.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testGetServletContextAttribute_1() throws Exception {
        ServletContext servletContext = request.getSession().getServletContext();
        servletContext.removeAttribute("a");
        try {
            Helper.getServletContextAttribute(servletContext, "a", Boolean.class);
            fail("HandlerExecutionException is expected");
        } catch (HandlerExecutionException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests that getServletContextAttribute(ServletContext, String, Class) correctly throws
     * Exception.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testGetServletContextAttribute_2() throws Exception {
        ServletContext servletContext = request.getSession().getServletContext();
        servletContext.setAttribute("a", "a");
        try {
            Helper.getServletContextAttribute(servletContext, "a", Boolean.class);
            fail("HandlerExecutionException is expected");
        } catch (HandlerExecutionException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests that getServletContextAttribute(ServletContext, String, Class) returns correct value.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testGetServletContextAttribute_3() throws Exception {
        ServletContext servletContext = request.getSession().getServletContext();
        servletContext.setAttribute("a", Boolean.TRUE);
        assertEquals("The returned value is incorrect", Boolean.TRUE, Helper
            .getServletContextAttribute(servletContext, "a", Boolean.class));
    }

    /**
     * <p>
     * Tests that getAuction(AuctionManager, long) correctly throws Exception.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testGetAuction_1() throws Exception {

        try {
            Helper.getAuction(new MockAuctionManager(), 239);
            fail("HandlerExecutionException is expected");
        } catch (HandlerExecutionException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests that getAuction(AuctionManager, long) correctly throws Exception.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testGetAuction_2() throws Exception {
        MockAuctionManager manager = new MockAuctionManager();
        MockAuctionPersistence auctionPersistence = new MockAuctionPersistence();
        manager.setAuctionPersistence(auctionPersistence);

        try {
            Helper.getAuction(new MockAuctionManager(), 239);
            fail("HandlerExecutionException is expected");
        } catch (HandlerExecutionException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests that getAuction(AuctionManager, long) correctly returns auction
     * </p>
     * @throws Exception to JUnit.
     */
    public void testGetAuction_3() throws Exception {
        MockAuctionManager manager = new MockAuctionManager();
        MockAuctionPersistence auctionPersistence = new MockAuctionPersistence();
        manager.setAuctionPersistence(auctionPersistence);

        MockAuction auction = new MockAuction();
        auction.setId(239);
        auctionPersistence.addAuction(auction);

        assertEquals("The returned value is incorrect", auction, Helper.getAuction(manager, 239));
    }

    /**
     * <p>
     * Tests that parseIntValue(ActionContext, String) correctly throws Exception.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testParseIntValue_1() throws Exception {
        ActionContext actionContext = new ActionContext(request, response);

        try {
            Helper.parseIntValue(actionContext, "key");
            fail("HandlerExecutionException is expected");
        } catch (HandlerExecutionException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Initializes HTTP related parameters that are used in the testParseIntValue_2 method.
     * </p>
     * @param theRequest WebRequest instance to setup initial parameters.
     */
    public void beginParseIntValue_2(WebRequest theRequest) {
        theRequest.addParameter("key", "2.39");
    }

    /**
     * <p>
     * Tests that parseIntValue(ActionContext, String) correctly throws Exception.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testParseIntValue_2() throws Exception {
        ActionContext actionContext = new ActionContext(request, response);

        try {
            Helper.parseIntValue(actionContext, "key");
            fail("HandlerExecutionException is expected");
        } catch (HandlerExecutionException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Initializes HTTP related parameters that are used in the testParseIntValue_3 method.
     * </p>
     * @param theRequest WebRequest instance to setup initial parameters.
     */
    public void beginParseIntValue_3(WebRequest theRequest) {
        theRequest.addParameter("key", "239");
    }

    /**
     * <p>
     * Tests that parseIntValue(ActionContext, String) returns correct value.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testParseIntValue_3() throws Exception {
        ActionContext actionContext = new ActionContext(request, response);

        assertEquals("The returned value is incorrect", 239, Helper.parseIntValue(actionContext,
            "key"));
    }

    /**
     * <p>
     * Tests that parseLongValue(ActionContext, String) correctly throws Exception.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testParseLongValue_1() throws Exception {
        ActionContext actionContext = new ActionContext(request, response);

        try {
            Helper.parseLongValue(actionContext, "key");
            fail("HandlerExecutionException is expected");
        } catch (HandlerExecutionException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Initializes HTTP related parameters that are used in the testParseLongValue_2 method.
     * </p>
     * @param theRequest WebRequest instance to setup initial parameters.
     */
    public void beginParseLongValue_2(WebRequest theRequest) {
        theRequest.addParameter("key", "2.39");
    }

    /**
     * <p>
     * Tests that parseLongValue(ActionContext, String) correctly throws Exception.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testParseLongValue_2() throws Exception {
        ActionContext actionContext = new ActionContext(request, response);

        try {
            Helper.parseLongValue(actionContext, "key");
            fail("HandlerExecutionException is expected");
        } catch (HandlerExecutionException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Initializes HTTP related parameters that are used in the testParseLongValue_3 method.
     * </p>
     * @param theRequest WebRequest instance to setup initial parameters.
     */
    public void beginParseLongValue_3(WebRequest theRequest) {
        theRequest.addParameter("key", "239239239239");
    }

    /**
     * <p>
     * Tests that parseLongValue(ActionContext, String) returns correct value.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testParseLongValue_3() throws Exception {
        ActionContext actionContext = new ActionContext(request, response);

        assertEquals("The returned value is incorrect", 239239239239L, Helper.parseLongValue(
            actionContext, "key"));
    }

    /**
     * <p>
     * Tests that getLoggedUserId(ActionContext) correctly throws Exception.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testgetLoggedUserId_1() throws Exception {
        ActionContext actionContext = new ActionContext(request, response);

        try {
            Helper.getLoggedUserId(actionContext);
            fail("HandlerExecutionException is expected");
        } catch (HandlerExecutionException e) {
            // do nothing
        }
    }
}
