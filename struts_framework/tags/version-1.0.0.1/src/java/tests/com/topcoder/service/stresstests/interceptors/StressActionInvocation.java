/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.stresstests.interceptors;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionEventListener;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.interceptor.PreResultListener;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * <p>
 * This is a mocked class which is used for stress tests.
 * </p>
 *
 * @author iKnown
 * @version 1.0
 */
public class StressActionInvocation implements ActionInvocation {
    /**
     * This method does nothing.
     *
     * @param arg0 no used.
     */
    public void addPreResultListener(PreResultListener arg0) {
        // TODO Auto-generated method stub
    }

    /**
     * This method does nothing.
     *
     * @return null.
     */
    public Object getAction() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Returns one mocked context.
     *
     * @return null.
     */
    public ActionContext getInvocationContext() {
        ActionContext context = new ActionContext(new HashMap());
        Map session = new HashMap();
        session.put("tc", "value");
        context.setSession(session);

        return context;
    }

    /**
     * This method returns null.
     */
    public ActionProxy getProxy() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * This method returns null.
     *
     * @return null.
     * 
     */
    public Result getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * This method returns null.
     *
     * @return null.
     */
    public String getResultCode() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * This method return null.
     *
     * @return null.
     */
    public ValueStack getStack() {
        // TODO Auto-generated method stub
        return new StressValueStack();
    }

    /**
     * This method does nothing.
     *
     * @param arg0 not used.
     */
    public void init(ActionProxy arg0) {
        // TODO Auto-generated method stub
    }

    /**
     * Returns 'stress_result' for test.
     *
     * @return the constant string 'stress_result'.
     */
    public String invoke() throws Exception {
        // TODO Auto-generated method stub
        return "stress_result";
    }

    /**
     * This method returns null.
     *
     * @return null.
     */
    public String invokeActionOnly() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * This method returns false.
     *
     * @return false.
     */
    public boolean isExecuted() {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * This method does nothing.
     *
     * @param arg0 not used.
     */
    public void setActionEventListener(ActionEventListener arg0) {
        // TODO Auto-generated method stub
    }

    /**
     * This method does nothing.
     *
     * @param arg0 not used.
     */
    public void setResultCode(String arg0) {
        // TODO Auto-generated method stub
    }
}
