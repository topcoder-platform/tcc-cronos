/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.cronos.im.persistence.failuretests;

import com.cronos.im.persistence.rolecategories.Category;
import com.topcoder.chat.user.profile.ChatUserProfile;
import com.topcoder.chat.user.profile.ProfileKey;

import com.topcoder.database.statustracker.EntityKey;

import com.topcoder.util.datavalidator.ObjectValidator;


/**
 * <p>Mock class of ObjectValidator.</p>
 * 
 * @author waits
 * @version 1.0
 */
public class MockValidator implements ObjectValidator {
    /**
     * Implement getMessage for validate.
     *
     * @param obj object to validate
     *
     * @return error message
     */
    public String getMessage(Object obj) {
        if (obj instanceof EntityKey) {
            if (((EntityKey) obj).getType().getName().equalsIgnoreCase("entity")) {
                return null;
            } else {
                return "invalid entity type";
            }
        } else if (obj instanceof ProfileKey) {
            if (((ProfileKey) obj).getUsername().equalsIgnoreCase("john")) {
                return null;
            } else {
                return "invalid profikeKey";
            }
        } else if (obj instanceof ChatUserProfile) {
            if (!((ChatUserProfile) obj).getUsername().equalsIgnoreCase("-1")) {
                return null;
            }
        }else if ( obj instanceof Category[]){
        	if (((Category[])obj).length == 2){
        		return null;
        	}
        }

        return "invalid profikeKey";
    }

    /**
     * Validate the object.
     *
     * @param obj object to validate
     *
     * @return true is ok
     */
    public boolean valid(Object obj) {
        if (obj instanceof EntityKey) {
            if (((EntityKey) obj).getType().getName().equalsIgnoreCase("entity")) {
                return true;
            } else {
                return false;
            }
        } else if (obj instanceof ProfileKey) {
            if (((ProfileKey) obj).getUsername().equalsIgnoreCase("john")) {
                return true;
            } else {
                return false;
            }
        } else if (obj instanceof ChatUserProfile) {
            if (!((ChatUserProfile) obj).getUsername().equalsIgnoreCase("-1")) {
                return true;
            }
        } else if ( obj instanceof Category[]){
        	if (((Category[])obj).length == 2){
        		return true;
        	}
        }

        return false;
    }
}
