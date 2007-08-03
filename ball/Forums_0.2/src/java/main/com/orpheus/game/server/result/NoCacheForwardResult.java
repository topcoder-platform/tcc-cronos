/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.result;

import com.topcoder.web.frontcontroller.results.ForwardResult;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.ResultExecutionException;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.Level;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletResponse;

/**
 * <p></p>
 *
 * @author isv
 * @version 1.0
 */
public class NoCacheForwardResult extends ForwardResult {

    /**
     * <p>Constructs new <code>NoCacheForwardResult</code> instance </p>
     * @param element
     */
    public NoCacheForwardResult(Element element) {
        super(element);
    }

    public void execute(ActionContext actionContext) throws ResultExecutionException {
        execute(actionContext, null, null);
    }


    public void execute(ActionContext actionContext, Log log, Level level) throws ResultExecutionException {
        System.out.println("ISV : DISABLING CACHE FOR REQUEST 2 " + actionContext.getRequest().getRequestURL() + "?"
                           + actionContext.getRequest().getQueryString());
        HttpServletResponse response = actionContext.getResponse();
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setIntHeader("Expires", -1);
        super.execute(actionContext, log, level);
    }
}
