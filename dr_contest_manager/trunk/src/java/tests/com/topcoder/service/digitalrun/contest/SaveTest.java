/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.topcoder.cobertura.SaveCoberturaData;

/**
 * <p>
 * Used to save Cobertura data on server side.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SaveTest extends BaseTestCase {

    /**
     * <p>
     * Call the <code>SaveCoberturaDataBean</code> to save Cobertura data.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSave() throws Exception {
        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        Context context = new InitialContext(env);
        SaveCoberturaData saveCoberturaData = (SaveCoberturaData)
            context.lookup(EAR_NAME + "/SaveCoberturaDataBean/remote");
        saveCoberturaData.saveCoberturaData();
    }

}
