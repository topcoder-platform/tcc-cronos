/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import com.topcoder.web.reg.actions.basic.RecoverPasswordAction;

/**
 * Mock class for test.
 *
 * @author extra
 * @version 1.0
 */
public class MockRecoverPasswordAction extends RecoverPasswordAction {

    /**
     * Mock method.
     */
    public String getText(String aTextName) {
        return "message of " + aTextName;
    }
}
