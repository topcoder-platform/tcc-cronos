/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox.accuracytests;

import com.orpheus.plugin.firefox.FirefoxExtensionHelper;
import com.orpheus.plugin.firefox.OrpheusServer;

/**
 * Defines a mocked <code>OrpheusServer</code> class. All methods used in <code>FirefoxExtensionHelper</code> would
 * be mocked.
 * 
 * @author visualage
 * @version 1.0
 */
public class OrpheusServerMock extends OrpheusServer {
    /**
     * Represents the last called method name.
     */
    private String method;

    /**
     * Represents the query domain result to be returned.
     */
    private double count = 0.0;

    /**
     * Creates a new instance of <code>OrpheusServerMock</code> class. The <code>FirefoxExtensionHelper</code>
     * instance is given.
     * 
     * @param helper the <code>FirefoxExtensionHelper</code> instance used by the orpheus server.
     */
    public OrpheusServerMock(FirefoxExtensionHelper helper) {
        super("http://localhost:8080/server", "time", null, 1, helper);
    }

    /**
     * Defines a mocked version of <code>pollServerNow()</code>.
     */
    public synchronized void pollServerNow() {
        method = "pollServerNow()";
    }

    /**
     * Defines a mocked version of <code>run()</code>.
     */
    public void run() {
        method = "run()";
    }

    /**
     * Defines a mocked version of <code>stopThread()</code>.
     */
    public void stopThread() {
        method = "stopThread()";
    }

    /**
     * Defines a mocked version of <code>queryDomain(String, String)</code>.
     */
    public double queryDomain(String domainParameter, String domain) {
        method = "queryDomain(\"" + domainParameter + "\",\"" + domain + "\")";

        return count;
    }

    /**
     * Sets the query domain result.
     * 
     * @param count the query domain result.
     */
    public void setQueryDomainResult(double count) {
        this.count = count;
    }

    /**
     * Gets the last called method in this instance.
     * 
     * @return the last called method in this instance.
     */
    public String getCalledMethod() {
        return method;
    }

    /**
     * Defines a mocked version of <code>getPollTime()</code>.
     */
    public int getPollTime() {
        method = "getPollTime()";

        return super.getPollTime();
    }

    /**
     * Defines a mocked version of <code>setPollTime(int)</code>.
     */
    public void setPollTime(int time) {
        method = "setPollTime(" + time + ")";

        super.setPollTime(time);
    }
}
