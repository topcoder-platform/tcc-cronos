/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * 
 */
package com.cronos.onlinereview.ajax.accuracytests;

import com.topcoder.management.project.Project;


/**
 * Mock class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockProject extends Project {

    public Object getProperty(String name) {
        if (name.equals("Public")) {
            return "Yes";
        }
        return null;
    }
}
