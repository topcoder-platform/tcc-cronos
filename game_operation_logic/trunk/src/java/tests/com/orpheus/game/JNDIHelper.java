/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.orpheus.game.persistence.GameDataHome;
import com.orpheus.game.persistence.GameDataLocalHome;

import com.topcoder.naming.jndiutility.JNDIUtils;

import java.io.File;

import java.util.Properties;

import javax.naming.Context;


/**
 * Helper class for File System JNI Provider configuration.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class JNDIHelper {
/**
         * Private ctor preventing this class from being initiated.
         */
    private JNDIHelper() {
    }

    /**
     * Initiate the JNI configuration. It will delete the already existing JNI configuration and bin new value
     * to it.
     *
     * @throws Exception if any error occurred
     */
    public static final void initJNDI() throws Exception {
        Properties prop = new Properties();
        prop.load(JNDIHelper.class.getResourceAsStream("/com/topcoder/naming/jndiutility/JNDIUtils.properties"));

        String dir = prop.getProperty("context.default.url");
        dir = dir.replaceFirst("file://", "");

        File file = new File(dir + "/.bindings");
        file.delete();

        Context context = JNDIUtils.getContext("default");
        context.bind("local", new GameDataLocalHome());
        context.bind("remote", new GameDataHome());
    }
}
