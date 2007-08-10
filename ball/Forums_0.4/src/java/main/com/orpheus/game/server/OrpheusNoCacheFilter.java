/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>A custom {@link Filter} implementation to be used for setting the response headers causing the client to disable
 * response caching. This filter currently is used for disabling caching of all requests issued by plugins.</p>
 *
 * @author isv
 * @version 1.0
 */
public class OrpheusNoCacheFilter implements Filter {

    /**
     * <p>Constructs new <code>OrpheusNoCacheFilter</code> instance. This implementation does nothing.</p>
     */
    public OrpheusNoCacheFilter() {
    }

    /**
     * <p>Initializes this filter. This implementation does nothing.</p>
     *
     * @param filterConfig a <code>FilterConfig</code> used to configure this filter instance.
     */
    public void init(FilterConfig filterConfig) {
    }

    /**
     * <p>Pre-processes the specified request-response pair. This implementation sets the response headers causing the
     * client to disable caching the specified response if the specified request is issued by a plugin.</p>
     *
     * @param request a <code>ServletRequest</code> representing the incoming request from the client.
     * @param response a <code>ServletResponse</code> representing the outgoing response. 
     * @param filterChain a <code>FilterChain</code> which this filter is part of. 
     * @throws IOException if an I/O error occurs.
     * @throws ServletException if an error preventing the filtering is encountered.
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
        throws IOException, ServletException {
        // Set the response headers to tell the client that no caching should be used for such sorts of requests
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Pragma", "no-cache");
        httpResponse.setHeader("Cache-Control", "no-cache");
        httpResponse.setIntHeader("Expires", -1);
        filterChain.doFilter(request, response);
    }

    /**
     * <p>De-initializes this filter. This implementation does nothing.</p>
     */
    public void destroy() {
    }
}
