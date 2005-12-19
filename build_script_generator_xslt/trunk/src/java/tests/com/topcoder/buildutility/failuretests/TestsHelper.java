/*
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.buildutility.failuretests;

import java.lang.reflect.Constructor;

import javax.xml.transform.Transformer;

import com.topcoder.buildutility.BuildScriptGeneratorImpl;

/**
 * Helper class to simplify the tests.
 * @author albertwang
 * @version 1.0
 */
public final class TestsHelper {
    /**
     * Private constructor.
     */
    private TestsHelper() {
    }
    /**
     * Util method to create instance of <code>BuildScriptGeneratorImpl</code> thru reflection.
     * @param transformer the transformer used to initialize the generator
     * @return the BuildScriptGeneratorImpl instance or null if failed
     */
    public static BuildScriptGeneratorImpl createBuildScriptGeneratorImpl(Transformer transformer) {
        try {
            Constructor ctor = BuildScriptGeneratorImpl.class.getDeclaredConstructor(new Class[] {Transformer.class});
            ctor.setAccessible(true);
            return (BuildScriptGeneratorImpl) ctor.newInstance(new Object[] {transformer});
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
