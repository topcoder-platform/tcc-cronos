/**
 * LiquidPortalService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.liquid.portal.service;

public interface LiquidPortalService extends java.rmi.Remote {
    public com.liquid.portal.service.CreateCompetitonResult createCompetition(java.lang.String arg0, com.liquid.portal.service.CompetitionData arg1, java.lang.String[] arg2) throws java.rmi.RemoteException, com.liquid.portal.service.HandleNotFoundException, com.liquid.portal.service.LiquidPortalServicingException, com.liquid.portal.service.ActionNotPermittedException, com.liquid.portal.service.LiquidPortalIllegalArgumentException, com.liquid.portal.service.InvalidHandleException;
    public void decommissionUser(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException, com.liquid.portal.service.HandleNotFoundException, com.liquid.portal.service.LiquidPortalServicingException, com.liquid.portal.service.ActionNotPermittedException, com.liquid.portal.service.LiquidPortalIllegalArgumentException;
    public void deleteCompetition(java.lang.String arg0, long arg1, boolean arg2, java.lang.String arg3) throws java.rmi.RemoteException, com.liquid.portal.service.HandleNotFoundException, com.liquid.portal.service.LiquidPortalServicingException, com.liquid.portal.service.ActionNotPermittedException, com.liquid.portal.service.LiquidPortalIllegalArgumentException, com.liquid.portal.service.ContestNotFoundException;
    public com.liquid.portal.service.Warning[] provisionProject(java.lang.String arg0, java.lang.String arg1, java.lang.String[] arg2) throws java.rmi.RemoteException, com.liquid.portal.service.HandleNotFoundException, com.liquid.portal.service.LiquidPortalServicingException, com.liquid.portal.service.ActionNotPermittedException, com.liquid.portal.service.LiquidPortalIllegalArgumentException;
    public com.liquid.portal.service.ProvisionUserResult provisionUser(java.lang.String arg0, java.lang.String arg1, boolean arg2, java.lang.String[] arg3, long[] arg4) throws java.rmi.RemoteException, com.liquid.portal.service.HandleNotFoundException, com.liquid.portal.service.LiquidPortalServicingException, com.liquid.portal.service.LiquidPortalIllegalArgumentException, com.liquid.portal.service.InvalidHandleException;
    public com.liquid.portal.service.RegisterUserResult registerUser(com.liquid.portal.service.User arg0, java.util.Calendar arg1) throws java.rmi.RemoteException, com.liquid.portal.service.LiquidPortalServicingException, com.liquid.portal.service.LiquidPortalIllegalArgumentException, com.liquid.portal.service.HandleCreationException;
    public com.liquid.portal.service.Warning[] validateUser(com.liquid.portal.service.UserInfo arg0, boolean arg1) throws java.rmi.RemoteException, com.liquid.portal.service.HandleNotFoundException, com.liquid.portal.service.LiquidPortalServicingException, com.liquid.portal.service.LiquidPortalIllegalArgumentException;
}
