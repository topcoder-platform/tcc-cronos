/**
 * ConfluenceSoapService.java This file was auto-generated from WSDL by the Apache Axis 1.2.1 Jun 14, 2005
 * (09:15:57 EDT) WSDL2Java emitter.
 */

package com.atlassian.www.software.confluence.$Proxy77;

public interface ConfluenceSoapService extends java.rmi.Remote {
    public java.lang.String[] getPermissions(java.lang.String arg0, java.lang.String arg1)
        throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteSearchResult[] search(
        java.lang.String arg0, java.lang.String arg1, int arg2) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteSearchResult[] search(
        java.lang.String arg0, java.lang.String arg1, com.atlassian.www._package.java_util.Map arg2, int arg3)
        throws java.rmi.RemoteException, com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteComment getComment(
        java.lang.String arg0, long arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteServerInfo getServerInfo(
        java.lang.String arg0) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemotePageSummary[] getChildren(
        java.lang.String arg0, long arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteUser getUser(
        java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteSpace getSpace(
        java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemotePage getPage(
        java.lang.String arg0, java.lang.String arg1, java.lang.String arg2) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemotePage getPage(
        java.lang.String arg0, long arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public java.lang.String login(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.AuthenticationFailedException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteClusterInformation getClusterInformation(
        java.lang.String arg0) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteComment addComment(
        java.lang.String arg0,
        com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteComment arg1)
        throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean removeGroup(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2)
        throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public java.lang.String[] getGroups(java.lang.String arg0) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean logout(java.lang.String arg0) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteAttachment getAttachment(
        java.lang.String arg0, long arg1, java.lang.String arg2, int arg3) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteComment[] getComments(
        java.lang.String arg0, long arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean removeComment(java.lang.String arg0, long arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteAttachment[] getAttachments(
        java.lang.String arg0, long arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteAttachment addAttachment(
        java.lang.String arg0,
        com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteAttachment arg1, byte[] arg2)
        throws java.rmi.RemoteException, com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteAttachment addAttachment(
        java.lang.String arg0, long arg1,
        com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteAttachment arg2, byte[] arg3)
        throws java.rmi.RemoteException, com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean removeAttachment(java.lang.String arg0, long arg1, java.lang.String arg2)
        throws java.rmi.RemoteException, com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteContentPermissionSet getContentPermissionSet(
        java.lang.String arg0, long arg1, java.lang.String arg2) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteContentPermissionSet[] getContentPermissionSets(
        java.lang.String arg0, long arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemotePageSummary[] getDescendents(
        java.lang.String arg0, long arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemotePageSummary[] getAncestors(
        java.lang.String arg0, long arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public void addUser(java.lang.String arg0,
        com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteUser arg1, java.lang.String arg2)
        throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteLabel[] getRelatedLabels(
        java.lang.String arg0, java.lang.String arg1, int arg2) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteSpaceSummary[] getSpaces(
        java.lang.String arg0) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteSpaceGroup getSpaceGroup(
        java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.AlreadyExistsException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteSpaceGroup[] getSpaceGroups(
        java.lang.String arg0) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public java.lang.Boolean removeSpace(java.lang.String arg0, java.lang.String arg1)
        throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean convertToPersonalSpace(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2,
        java.lang.String arg3, boolean arg4) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemotePageSummary[] getPages(
        java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public java.lang.String[] getActiveUsers(java.lang.String arg0, boolean arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean setContentPermissions(java.lang.String arg0, long arg1, java.lang.String arg2,
        com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteContentPermission[] arg3)
        throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean moveAttachment(java.lang.String arg0, long arg1, java.lang.String arg2, long arg3,
        java.lang.String arg4) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteComment editComment(
        java.lang.String arg0,
        com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteComment arg1)
        throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemotePageSummary[] getTopLevelPages(
        java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public byte[] getAttachmentData(java.lang.String arg0, long arg1, java.lang.String arg2, int arg3)
        throws java.rmi.RemoteException, com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean removeUser(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean deactivateUser(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean reactivateUser(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteSpace addSpace(
        java.lang.String arg0, com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteSpace arg1)
        throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.AlreadyExistsException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteLabel[] getRecentlyUsedLabels(
        java.lang.String arg0, int arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteLabel[] getRecentlyUsedLabelsInSpace(
        java.lang.String arg0, java.lang.String arg1, int arg2) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteLabel[] getMostPopularLabels(
        java.lang.String arg0, int arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteLabel[] getMostPopularLabelsInSpace(
        java.lang.String arg0, java.lang.String arg1, int arg2) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean setEnableWysiwyg(java.lang.String arg0, boolean arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean addGroup(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean removeAllPermissionsForGroup(java.lang.String arg0, java.lang.String arg1)
        throws java.rmi.RemoteException, com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteLabel[] getLabelsByDetail(
        java.lang.String arg0, java.lang.String arg1, java.lang.String arg2, java.lang.String arg3,
        java.lang.String arg4) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteLabel[] getRelatedLabelsInSpace(
        java.lang.String arg0, java.lang.String arg1, java.lang.String arg2, int arg3)
        throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteSpace[] getSpacesContainingContentWithLabel(
        java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteSpace[] getSpacesWithLabel(
        java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean removeSpaceGroup(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public java.lang.Boolean movePageToTopLevel(java.lang.String arg0, long arg1, java.lang.String arg2)
        throws java.rmi.RemoteException, com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public java.lang.Boolean movePage(java.lang.String arg0, long arg1, long arg2, java.lang.String arg3)
        throws java.rmi.RemoteException, com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public java.lang.String exportSpace(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2)
        throws java.rmi.RemoteException, com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean hasUser(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemotePageHistory[] getPageHistory(
        java.lang.String arg0, long arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public java.lang.Boolean removePage(java.lang.String arg0, long arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public java.lang.String renderContent(java.lang.String arg0, java.lang.String arg1, long arg2,
        java.lang.String arg3, com.atlassian.www._package.java_util.Map arg4) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public java.lang.String renderContent(java.lang.String arg0, java.lang.String arg1, long arg2,
        java.lang.String arg3) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemotePage storePage(
        java.lang.String arg0, com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemotePage arg1)
        throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.VersionMismatchException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteSpaceSummary[] getSpacesInGroup(
        java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteSpace storeSpace(
        java.lang.String arg0, com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteSpace arg1)
        throws java.rmi.RemoteException, com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteSpaceGroup addSpaceGroup(
        java.lang.String arg0,
        com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteSpaceGroup arg1)
        throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.AlreadyExistsException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteSpace addPersonalSpace(
        java.lang.String arg0,
        com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteSpace arg1, java.lang.String arg2)
        throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.AlreadyExistsException;

    public java.lang.String[] getSpaceLevelPermissions(java.lang.String arg0) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean addPermissionToSpace(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2,
        java.lang.String arg3) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean addGlobalPermissions(java.lang.String arg0, java.lang.String[] arg1, java.lang.String arg2)
        throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean addGlobalPermission(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2)
        throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean addAnonymousUsePermission(java.lang.String arg0) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean removeGlobalPermission(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2)
        throws java.rmi.RemoteException, com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean addPermissionsToSpace(java.lang.String arg0, java.lang.String[] arg1, java.lang.String arg2,
        java.lang.String arg3) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean removePermissionFromSpace(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2,
        java.lang.String arg3) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean editUser(java.lang.String arg0,
        com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteUser arg1)
        throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public java.lang.String[] getUserGroups(java.lang.String arg0, java.lang.String arg1)
        throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean addUserToGroup(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2)
        throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean removeUserFromGroup(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2)
        throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean changeMyPassword(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2)
        throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean changeUserPassword(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2)
        throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean setUserInformation(java.lang.String arg0,
        com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteUserInformation arg1)
        throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteUserInformation getUserInformation(
        java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean hasGroup(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean addProfilePicture(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2,
        java.lang.String arg3, byte[] arg4) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteBlogEntry getBlogEntryByDayAndTitle(
        java.lang.String arg0, java.lang.String arg1, int arg2, java.lang.String arg3)
        throws java.rmi.RemoteException, com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteBlogEntry getBlogEntry(
        java.lang.String arg0, long arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteBlogEntrySummary[] getBlogEntries(
        java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteBlogEntry storeBlogEntry(
        java.lang.String arg0,
        com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteBlogEntry arg1)
        throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.VersionMismatchException;

    public java.lang.String exportSite(java.lang.String arg0, boolean arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean flushIndexQueue(java.lang.String arg0) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean clearIndexQueue(java.lang.String arg0) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteNodeStatus[] getClusterNodeStatuses(
        java.lang.String arg0) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean importSpace(java.lang.String arg0, byte[] arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean isRpcPluginEnabled(java.lang.String arg0) throws java.rmi.RemoteException;

    public boolean setEnableAnonymousAccess(java.lang.String arg0, boolean arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteLabel[] getLabelsById(
        java.lang.String arg0, long arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteSearchResult[] getLabelContentById(
        java.lang.String arg0, long arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteSearchResult[] getLabelContentByName(
        java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteSearchResult[] getLabelContentByObject(
        java.lang.String arg0, com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteLabel arg1)
        throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean addLabelByName(java.lang.String arg0, java.lang.String arg1, long arg2)
        throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean addLabelById(java.lang.String arg0, long arg1, long arg2) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean addLabelByObject(java.lang.String arg0,
        com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteLabel arg1, long arg2)
        throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean addLabelByNameToSpace(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2)
        throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean removeLabelByName(java.lang.String arg0, java.lang.String arg1, long arg2)
        throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean removeLabelById(java.lang.String arg0, long arg1, long arg2) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean removeLabelByObject(java.lang.String arg0,
        com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteLabel arg1, long arg2)
        throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean removeLabelByNameFromSpace(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2)
        throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public java.lang.String[] getPermissionsForUser(java.lang.String arg0, java.lang.String arg1,
        java.lang.String arg2) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean removeAnonymousUsePermission(java.lang.String arg0) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean addAnonymousPermissionToSpace(java.lang.String arg0, java.lang.String arg1,
        java.lang.String arg2) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean addAnonymousPermissionsToSpace(java.lang.String arg0, java.lang.String[] arg1,
        java.lang.String arg2) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public boolean removeAnonymousPermissionFromSpace(java.lang.String arg0, java.lang.String arg1,
        java.lang.String arg2) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.NotPermittedException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;

    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemotePermission[] getPagePermissions(
        java.lang.String arg0, long arg1) throws java.rmi.RemoteException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.InvalidSessionException,
        com.atlassian.www._package.com_atlassian_confluence_rpc.RemoteException;
}
