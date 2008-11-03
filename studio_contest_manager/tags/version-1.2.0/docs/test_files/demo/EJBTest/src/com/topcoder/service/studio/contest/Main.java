/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import com.topcoder.service.studio.contest.ContestManager;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.documentcontentservers.SocketDocumentContentServer;

import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;


/**
 * <p>
 * Demo of this component.
 * </p>
 *
 * @author Standlove, TCSDEVELOPER
 * @version 1.0
 */
public class Main {
    /**
     * <p>
     * Main program.
     * </p>
     *
     * @param args the console arugments
     * @throws Exception to console
     */
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.setProperty("java.naming.factory.initial",
            "org.jnp.interfaces.NamingContextFactory");
        props.setProperty("java.naming.provider.url", "localhost:1099");
        props.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");

        InitialContext ctx;

        SocketDocumentContentServer server = new SocketDocumentContentServer(10002,
                10, "test");

        try {
            server.start();

            ctx = new InitialContext(props);

            ContestManager bean = (ContestManager) ctx.lookup(
                    "ContestManagerBean/remote");

            bean.getAllContestChannels();

            bean.getAllStudioFileTypes();

            List<ContestStatus> contestStatues = bean.getAllContestStatuses();
        } finally {
            server.stop();
        }
    }
}
