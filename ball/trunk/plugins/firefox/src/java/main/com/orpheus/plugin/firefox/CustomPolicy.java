/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.orpheus.plugin.firefox;

import java.io.FilePermission;
import java.security.AllPermission;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.Policy;

/**
 * Custom security policy used by the used to grant the read permissions on file in secure
 * context.
 *  
 * @author kr00tki
 * @version 1.0
 */
public class CustomPolicy extends Policy {
    
    /**
     * The permissions collection. 
     */
    private PermissionCollection pc = new Permissions();
    
    /**
     * Creates the CustomPlicy with all permission granted.
     */
    public CustomPolicy() {
        pc.add(new FilePermission("<<ALL FILES>>", "read"));
        pc.add(new AllPermission());
    }
    
    /**
     * Returns the permisions for the given code source.
     * 
     * @param codesource the codesource.
     */
    public PermissionCollection getPermissions(CodeSource codesource) {
        return pc;
    }

    /**
     * Not implemented.
     */
    public void refresh() {
    }

}
