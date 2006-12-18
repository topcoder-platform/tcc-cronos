/*
 * @(#) TestObjectFactory.java
 *
 * 1.0 08/09/2003
 * Copyright ? 2003, TopCoder, Inc. All rights reserved
 */
package com.orpheus.game;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

import com.orpheus.game.persistence.GameDataHome;
import com.orpheus.game.persistence.GameDataLocalHome;
import com.orpheus.game.persistence.MockGameDataHome;
import com.orpheus.game.persistence.MockGameDataLocalHome;


/**
 * A factory used to create GameDataLocalHome, GameDataHome objects.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestObjectFactory implements ObjectFactory {
    /**
     * Get an object instance.
     *
     * @param obj an object(a Reference)
     * @param name the name of the obj
     * @param nameCtx the context for the nam
     * @param environment the environment.
     *
     * @return an Object null if an Object can not be created.
     *
     * @throws Exception if any Exception occurs
     */
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable environment)
        throws Exception {
        //If obj is not a reference just return null
        if (!(obj instanceof Reference)) {
            return null;
        }

        Reference reference = (Reference) obj;
        String className = reference.getClassName();

        if (className.equals(GameDataHome.class.getName())) {
            return new MockGameDataHome();
        }else if (className.equals(GameDataLocalHome.class.getName())) {
            return new MockGameDataLocalHome();
        }

        return null;
    }
}
