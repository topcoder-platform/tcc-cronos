/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests;

import java.io.File;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NameAlreadyBoundException;

import com.orpheus.game.persistence.MockGameDataHome;
import com.orpheus.game.persistence.MockGameDataLocalHome;
import com.topcoder.naming.jndiutility.JNDIUtils;

/**
 * Helper class used to init JNDI.
 * 
 * @author Zulander
 * @version 1.0
 */
public final class JNDIHelper {

	/**
	 * Initialize JNDI.
	 * @throws Exception exception thrown to JUnit
	 */
    public static final void initJNDI() throws Exception {
        Properties prop = new Properties();
        prop.load(JNDIHelper.class.getResourceAsStream("/com/topcoder/naming/jndiutility/JNDIUtils.properties"));
        
        String dir = prop.getProperty("context.default.url");
        dir = dir.replaceFirst("file://","");
        
        File file = new File(dir+"/.bindings");
        file.delete();
        
        Context context = JNDIUtils.getContext("default");
        try {
            context.bind("local", new MockGameDataLocalHome());
            context.bind("remote", new MockGameDataHome());
        } catch (NameAlreadyBoundException nabe) {
        	// already bound
        }
    }
}
