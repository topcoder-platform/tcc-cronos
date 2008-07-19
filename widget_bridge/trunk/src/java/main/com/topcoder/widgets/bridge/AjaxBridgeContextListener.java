/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.widgets.bridge;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContext;

/**
 * <p>The class serves as a servlet context event listener responsible for initializing the application context. It
 * binds the {@link PayPalResponseListener} instance to context which could be used for collecting and keeping track of
 * the payments made via <code>PayPal</code> service.</p>
 *
 * @author isv
 * @version 1.0
 */
public class AjaxBridgeContextListener implements ServletContextListener {

    /**
     * <p>Constructs new <code>AjaxBridgeContextListener</code> instance. This implementation does nothing.</p>
     */
    public AjaxBridgeContextListener() {
    }

    /**
     * <p>Notifies this listener on servlet context instantiation.</p>
     *
     * <p>Puts the necessary application wide attributes to servlet context.</p>
     *
     * @param event a <code>ServletContextEvent</code> providing the details for the event.
     */
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();

        try {
            // Set the application context attributes
            synchronized (context) {
                context.setAttribute("paypalListener", new PayPalResponseListener(context));
            }
        } catch (Exception e) {
            context.log("Failed to initialize AJAX Widget Bridge application context", e);
        }
    }

    /**
     * <p>Notifies this listener on servlet context destruction. This implementation does nothing.</p>
     *
     * @param event a <code>ServletContextEvent</code> providing the details for the event.
     */
    public void contextDestroyed(ServletContextEvent event) {
    }
}
