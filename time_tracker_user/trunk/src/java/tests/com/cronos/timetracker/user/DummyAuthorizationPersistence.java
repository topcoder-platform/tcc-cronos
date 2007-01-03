/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.topcoder.security.authorization.Action;
import com.topcoder.security.authorization.ActionContext;
import com.topcoder.security.authorization.AuthorizationPersistence;
import com.topcoder.security.authorization.AuthorizationPersistenceException;
import com.topcoder.security.authorization.Principal;
import com.topcoder.security.authorization.SecurityRole;

/**
 * <p>
 * Dummy implementation of the AuthorizationPersistence interface, for unit testing purposes only.
 * Most methods do nothing and/or return null.
 * </p>
 *
 * @see UserPersistence
 *
 * @author TCSDEVELOPER
 * @version 1.0
*/
class DummyAuthorizationPersistence implements AuthorizationPersistence {
    /**
     * Maps a username to a Set of roles.
     */
    private Map rolesByPrincipal = new HashMap();


    /**
     * Does nothing.
     * @param principal ignored.
     */
    public void addPrincipal(Principal principal) {
    }


    /**
     * Does nothing.
     * @param principal ignored.
     */
    public void removePrincipal(Principal principal) {
    }


    /**
     * Does nothing.
     * @param role ignored.
     */
    public void addRole(SecurityRole role) {
    }


    /**
     * Does nothing.
     * @param role ignored.
     */
    public void removeRole(SecurityRole role) {
    }


    /**
     * Add the role to the set of roles for this principal.
     * @param principal the configured principal
     * @param role the role to be added for this principal
     * @throws AuthorizationPersistenceException If anything goes wrong.
     */
    public void addRoleForPrincipal(Principal principal, SecurityRole role)
        throws AuthorizationPersistenceException {

        Collection roles = getRolesForPrincipal(principal);
        if (roles == null) {
            roles = new HashSet();
        }
        roles.add(role);
        rolesByPrincipal.put(principal.getName(), roles);
    }


    /**
     * Removes the roles from the list of roles in memory for this principal.
     * @param principal the configured principal
     * @param role the role to be removed for this principal
     * @throws AuthorizationPersistenceException If anything goes wrong.
     */
    public void removeRoleForPrincipal(Principal principal, SecurityRole role)
        throws AuthorizationPersistenceException {

        Collection roles = getRolesForPrincipal(principal);
        if (roles == null) {
            return;
        } else {
            roles.remove(role);
        }
    }


    /**
     * Returns the roles for this principal.
     * @param principal the configured principal
     * @return the roles for this principal.
     * @throws AuthorizationPersistenceException If anything goes wrong.
     */
    public Collection getRolesForPrincipal(Principal principal)
        throws AuthorizationPersistenceException {

        return (Collection) rolesByPrincipal.get(principal.getName());
    }


    /**
     * Does nothing.
     * @param role ignored.
     * @param action ignored.
     * @param context ignored.
     * @param permission ignored.
     */
    public void setPermissionForRole(SecurityRole role,
                                     Action action,
                                     ActionContext context,
                                     int permission) {
    }


    /**
     * Does nothing.
     * @param role ignored.
     * @param action ignored.
     * @param context ignored.
     * @return null
     */
    public int checkPermissionForRole(SecurityRole role, Action action, ActionContext context) {

        return 0;
    }


    /**
     * Does nothing.
     * @param role ignored.
     * @param action ignored.
     * @param context ignored.
     */
    public void removePermissionForRole(SecurityRole role, Action action, ActionContext context) {
    }


    /**
     * Does nothing.
     */
    public void resetCache() {
    }


    /**
     * Does nothing.
     * @param action ignored. ignored.
     */
    public void addAction(Action action) {
    }


    /**
     * Does nothing.
     * @param action ignored.
     */
    public void removeAction(Action action) {
    }


    /**
     * Always returns null.
     * @return null null
     */
    public Collection getActions() {
        return null;
    }


    /**
     * Does nothing.
     * @param actionContext ignored
     */
    public void addActionContext(ActionContext actionContext) {
    }


    /**
     * Does nothing.
     * @param actionContext ignored.
     */
    public void removeActionContext(ActionContext actionContext) {

    }


    /**
     * Always returns null.
     * @return null
     */
    public Collection getActionContexts() {

        return null;
    }


    /**
     * Does nothing.
     * @param principal ignored.
     * @param action ignored.
     * @param context ignored.
     * @param permission ignored.
     */
    public void setPermissionForPrincipal(Principal principal,
                                          Action action,
                                          ActionContext context,
                                          int permission) {
    }


    /**
     * Does nothing.
     * @param principal ignored.
     * @param action ignored.
     * @param context ignored.
     * @return 0
     */
    public int checkPermissionForPrincipal(Principal principal, Action action, ActionContext context) {

        return 0;
    }


    /**
     * Does nothing.
     * @param principal ignored.
     * @param action ignored.
     * @param context ignored.
     */
    public void removePermissionForPrincipal(Principal principal,
                                             Action action,
                                             ActionContext context) {
    }


    /**
     * Always returns null.
     * @param principal ignored.
     * @return null
     */
    public Collection getPermissionsForPrincipal(Principal principal) {

        return null;
    }


    /**
     * Always returns null.
     * @param role ignored.
     * @return null
     */
    public Collection getPermissionsForRole(SecurityRole role) {

        return null;
    }


    /**
     * Always returns null.
     * @return null
     */
    public Collection getSecurityRoles() {

        return null;
    }


    /**
     * Always returns null.
     * @return null
     */
    public Collection getPrincipals() {

        return null;
    }


    /**
     * Always returns null.
     * @param principalName ignored.
     * @return null
     */
    public Collection searchPrincipals(String principalName) {

        return null;
    }


    /**
     * Always returns null.
     * @param roleName ignored.
     * @return null
     */
    public Collection searchRoles(String roleName) {

        return null;
    }


    /**
     * Always returns null.
     * @param actionName ignored.
     * @return null
     */
    public Collection searchActions(String actionName) {

        return null;
    }


    /**
     * Always returns null.
     * @param contextName ignored.
     * @return null
     */
    public Collection searchContexts(String contextName) {

        return null;
    }


    /**
     * Does nothing.
     * @param principal ignored.
     */
    public void updatePrincipal(Principal principal) {
    }


    /**
     * Does nothing.
     * @param action ignored.
     */
    public void updateAction(Action action) {
    }


    /**
     * Does nothing.
     * @param actionContext ignored.
     */
    public void updateActionContext(ActionContext actionContext) {
    }
}
