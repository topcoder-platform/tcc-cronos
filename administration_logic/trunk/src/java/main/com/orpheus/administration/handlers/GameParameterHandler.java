/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.handlers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.w3c.dom.Element;

import com.orpheus.administration.Helper;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

/**
 * Provides a Handler implementation that accepts general information about a
 * game to be created, and records it in the user's session in
 * configurably-named attributes. Information will be parsed from HTTP request
 * parameters, whose names will also be configurable. In case of failure, i.e.
 * in case of missing parameters for example, the execute method will return a
 * configurable result name and set a HandlerResult instance as a request
 * attribute so that an appropriate response or user message can be generated.
 * In case of success, the execute() method will return null.<br/> For
 * configuration details on this handler, please see Section 3.2.9 of Comp Spec.<br/>
 * Thread-Safety: This class is thread-safe as is required of Handler
 * implementations. This class is immutable and automatically thread-safe.
 * 
 * @author TCSDESIGNER, KKD
 * @version 1.0
 */
public class GameParameterHandler implements Handler {

    /**
     * This holds the name of the request parameter which will contain the ball
     * color id. The value should be able to be parsed into a long value.<br/>
     * It is initialized in the constructor and does not change after that.<br/>
     * It will never be null or empty.<br/>
     * 
     */
    private final String ballColorIdParamName;

    /**
     * This holds the name of the session attribute which will contain the
     * parsed ball color id. It will be stored as a Long value.<br/> It is
     * initialized in the constructor and does not change after that.<br/> It
     * will never be null or empty.<br/>
     * 
     */
    private final String ballColorIdAttrName;

    /**
     * This holds the name of the request parameter which will contain the key
     * count. The value should be able to be parsed into an int value.<br/> It
     * is initialized in the constructor and does not change after that.<br/>
     * It will never be null or empty.<br/>
     * 
     */
    private final String keyCountParamName;

    /**
     * This holds the name of the session attribute which will contain the
     * parsed key count. It will be stored as an Integer value.<br/> It is
     * initialized in the constructor and does not change after that.<br/> It
     * will never be null or empty.<br/>
     * 
     */
    private final String keyCountAttrName;

    /**
     * This holds the name of the request parameter which will contain the block
     * count. The value should be able to be parsed into an int value.<br/> It
     * is initialized in the constructor and does not change after that.<br/>
     * It will never be null or empty.<br/>
     * 
     */
    private final String blockCountParamName;

    /**
     * This holds the name of the session attribute which will contain the
     * parsed block count. It will be stored as an Integer value.<br/> It is
     * initialized in the constructor and does not change after that.<br/> It
     * will never be null or empty.<br/>
     * 
     */
    private final String blockCountAttrName;

    /**
     * This holds the name of the request parameter which will contain the game
     * start date. The value should be able to be parsed into a java.util.Date
     * object. It should have day,month,year and time of day data. The format of
     * the date string will also be taken as request parameter.<br/> It is
     * initialized in the constructor and does not change after that.<br/> It
     * will never be null or empty.<br/>
     * 
     */
    private final String startDateParamName;

    /**
     * This holds the name of the request parameter which will contain the date
     * format to use to parse a string date value into a java.util.Date object.<br/>
     * It is initialized in the constructor and does not change after that.<br/>
     * It will never be null or empty.<br/>
     * 
     */
    private final String dtFormatParamName;

    /**
     * This holds the name of the session attribute which will contain the
     * parsed start Date. It will be stored as java.util.Date.<br/> It is
     * initialized in the constructor and does not change after that.<br/> It
     * will never be null or empty.<br/>
     * 
     */
    private final String startDateAttrName;

    /**
     * This holds the name of the result (as configured in Front Controller
     * component) which should execute in case of execution failure.<br/> It is
     * initialized in the constructor and does not change after that.<br/> It
     * will never be null or empty.<br/>
     * 
     */
    private final String failedResult;

    /**
     * This holds the name of the request attribute to which HandlerResult
     * instance should be assigned to, in case of execution failure.<br/> It is
     * initialized in the constructor and does not change after that.<br/> It
     * will never be null or empty.<br/>
     * 
     */
    private final String failRequestAttrName;

    /**
     * Creates a GameParameterHandler instance configured from the given xml
     * element. It will initialize the instance variables. It will throw an
     * IllegalArgumentException if configuration details are missing in the
     * handlerElement argument.
     * 
     * Example xml structure:
     * 
     * <pre>
     *      &lt;handler type=&quot;gameParameter&quot;&gt;
     *      &lt;!--  name of request parameter which will contain the game id --&gt;
     *      &lt;ballcolor-id-request-param&gt;ballColorId&lt;/ballcolor-id-request-param&gt;  
     *      &lt;!-- name of session attribute which will contain the parsed ball color id --&gt;
     *      &lt;ballcolor-id-session-attr&gt;ballColorId&lt;/ballcolor-id-session-attr&gt;
     *      &lt;!--  name of request parameter which will contain the key count --&gt;
     *      &lt;key-count-request-param&gt;keyCount&lt;/key-count-request-param&gt;  
     *      &lt;!-- name of session attribute which will contain the parsed key count --&gt;
     *      &lt;key-count-session-attr&gt;keyCount&lt;/key-count-session-attr&gt;
     *      &lt;!--  name of request parameter which will contain the block count --&gt;
     *      &lt;block-count-request-param&gt;blockCount&lt;/block-count-request-param&gt;  
     *      &lt;!-- name of session attribute which will contain the parsed block count --&gt;
     *      &lt;block-count-session-attr&gt;blockCount&lt;/block-count-session-attr&gt;
     *      &lt;!--  name of request parameter which will contain the start date --&gt;
     *      &lt;start-date-request-param&gt;startDate&lt;/start-date-request-param&gt;
     *      &lt;!--  name of request parameter which will contain the date format --&gt;
     *      &lt;date-format-request-param&gt;dateFormat&lt;/date-format-request-param&gt;  
     *      &lt;!-- name of session attribute which will contain the parsed start date --&gt;
     *      &lt;start-date-session-attr&gt;startDate&lt;/start-date-session-attr&gt;
     *      &lt;!--  name of result to return in case of execution failure --&gt;
     *      &lt;fail-result&gt;Failed&lt;/fail-result&gt;
     *      &lt;!--  name of request attribute to store HandlerResult in case of failure--&gt;
     *      &lt;fail-request-attribute&gt;Failed&lt;/fail-request-attribute&gt;
     *      &lt;/handler&gt;
     * 
     * </pre>
     * 
     * @param handlerElement
     *            the XML element containing configuration for this handler.
     * @throws IllegalArgumentException
     *             if handlerElement is null, or contains invalid data.
     */
    public GameParameterHandler(Element handlerElement) {
        Helper.checkHandlerElement(handlerElement);
        ballColorIdParamName = Helper.getValue(handlerElement,
                "/handler/ballcolor-id-request-param");
        ballColorIdAttrName = Helper.getValue(handlerElement,
                "/handler/ballcolor-id-session-attr");
        keyCountParamName = Helper.getValue(handlerElement,
                "/handler/key-count-request-param");
        keyCountAttrName = Helper.getValue(handlerElement,
                "/handler/key-count-session-attr");
        blockCountParamName = Helper.getValue(handlerElement,
                "/handler/block-count-request-param");
        blockCountAttrName = Helper.getValue(handlerElement,
                "/handler/block-count-session-attr");
        startDateParamName = Helper.getValue(handlerElement,
                "/handler/start-date-request-param");
        dtFormatParamName = Helper.getValue(handlerElement,
                "/handler/date-format-request-param");
        startDateAttrName = Helper.getValue(handlerElement,
                "/handler/start-date-session-attr");
        failedResult = Helper.getValue(handlerElement, "/handler/fail-result");
        failRequestAttrName = Helper.getValue(handlerElement,
                "/handler/fail-request-attribute");
    }

    /**
     * <p>
     * This method will parse information about a game to be created from
     * request parameters into appropriate data types and set them as session
     * attributes. If there is no session associated with current request, this
     * method will throw a HandlerExecutionException. This is different from
     * other error situations. No session situation is not expected to happen.
     * For other error situation such as missing parameters this method will
     * return a configurable result name and set a HandlerResult as request
     * attribute. In case of success, this method will return null.
     * </p>
     * 
     * @param actionContext
     *            the ActionContext object.
     * 
     * @return null if operation succeeds, otherwise a configurable result name.
     * @throws IllegalArgumentException
     *             if the context is null.
     * @throws HandlerExecutionException
     *             in case no session is associated with the request.
     */
    public String execute(ActionContext actionContext)
        throws HandlerExecutionException {
        Helper.checkNull(actionContext, "actionContext");
        HttpServletRequest request = actionContext.getRequest();
        HttpSession session = Helper.getSession(request);
        // Get ball color id from request parameter
        String ballColorIdString = Helper.getRequestParameter(request,
                ballColorIdParamName, failRequestAttrName);
        // Get start date from request parameter
        String startDtString = Helper.getRequestParameter(request,
                startDateParamName, failRequestAttrName);
        // Get date format from request parameter String
        String dtFormat = Helper.getRequestParameter(request,
                dtFormatParamName, failRequestAttrName);
        // Get block count from request parameter
        String blockCountString = Helper.getRequestParameter(request,
                blockCountParamName, failRequestAttrName);
        // Get key count from request parameter
        String keyCountString = Helper.getRequestParameter(request,
                keyCountParamName, failRequestAttrName);
        if (ballColorIdString == null || startDtString == null
                || dtFormat == null || blockCountString == null
                || keyCountString == null) {
            return failedResult;
        }
        // Parse it into Long value
        Long ballColorId = Helper.parseLong(request, ballColorIdString,
                "ballColorId", failRequestAttrName);

        // Parse it into Integer value
        Integer keyCount = Helper.parseInteger(request, keyCountString,
                "keyCount", failRequestAttrName);

        // Parse it into Integer value
        Integer blockCount = Helper.parseInteger(request, blockCountString,
                "blockCount", failRequestAttrName);

        // Parse start date into java.util.Date using dtFormat
        DateFormat dateFormat = new SimpleDateFormat(dtFormat);
        Date startDate = Helper.parseDate(request, dateFormat, startDtString,
                "startDate", failRequestAttrName);
        if (startDate == null || ballColorId == null || keyCount == null
                || blockCount == null) {
            return failedResult;
        }
        // set attributes to session
        session.setAttribute(ballColorIdAttrName, ballColorId);
        session.setAttribute(keyCountAttrName, keyCount);
        session.setAttribute(blockCountAttrName, blockCount);
        session.setAttribute(startDateAttrName, startDate);

        // return null for successful execution.
        return null;
    }
}
