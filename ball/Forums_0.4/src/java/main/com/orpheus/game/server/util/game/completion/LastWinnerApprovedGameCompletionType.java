/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util.game.completion;

import com.orpheus.game.server.framework.game.completion.GameCompletion;
import com.orpheus.game.server.framework.game.completion.GameCompletionException;
import com.orpheus.game.server.util.AdminDataEJBAdapter;
import com.orpheus.administration.persistence.AdminDataHome;
import com.orpheus.administration.persistence.AdminDataLocalHome;
import com.topcoder.naming.jndiutility.JNDIUtils;

import javax.naming.Context;

/**
 * <p></p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class LastWinnerApprovedGameCompletionType extends AbstractGameCompletionType {

    private final String adminDataJndiName;
    private final String jndiContextName;
    private final boolean useRemoteInterface;


    public LastWinnerApprovedGameCompletionType(int id, String name, String jndiContextName, String adminDataJndiName, 
                                                boolean useRemoteInterface) {
        super(id, name);
        if ((adminDataJndiName == null) || (adminDataJndiName.trim().length() == 0)) {
            throw new IllegalArgumentException("The parameter [adminDataJndiName] is not valid. ["
                                               + adminDataJndiName + "]");
        }
        if ((jndiContextName == null) || (jndiContextName.trim().length() == 0)) {
            throw new IllegalArgumentException("The parameter [jndiContextName] is not valid. ["
                                               + jndiContextName + "]");
        }
        this.adminDataJndiName = adminDataJndiName;
        this.jndiContextName = jndiContextName;
        this.useRemoteInterface = useRemoteInterface;
    }

    /**
     * <p>Gets the bounce point calculator of this type. The implementations may choose to create a new instance of
     * calculator on each call to this method or return the same cached calculator instance. In latter case the returned
     * instance must be thread-safe.</p>
     *
     * @return a <code>BouncePointCalculator</code> to be used for calculating the bounce points in accordance with
     *         rules set for this type.
     * @throws GameCompletionException if an unexpected error occurs while creating thew new bounce point calculator
     * instance of this type.
     */
    public GameCompletion getGameCompletion() throws GameCompletionException {
        try {
            AdminDataEJBAdapter adminData;
            Context jndiContext = JNDIUtils.getContext(this.jndiContextName);
            if (this.useRemoteInterface) {
                AdminDataHome adminDataHome = (AdminDataHome) JNDIUtils.getObject(jndiContext, this.adminDataJndiName,
                                                                                  AdminDataHome.class);
                 adminData = new AdminDataEJBAdapter(adminDataHome.create());
            } else {
                AdminDataLocalHome adminDataLocalHome
                    = (AdminDataLocalHome) JNDIUtils.getObject(jndiContext, this.adminDataJndiName,
                                                               AdminDataLocalHome.class);
                adminData = new AdminDataEJBAdapter(adminDataLocalHome.create());
            }
            return new LastWinnerApprovedGameCompletion(adminData);
        } catch (Exception e) {
            throw new GameCompletionException("Could not ", e);
        }
    }
}
