<%@ taglib uri="webwork" prefix="webwork" %>
<%@ taglib uri="jiratags" prefix="jira" %>

<table cellpadding="3" cellspacing="0" border="0" width="200">
<tr><td bgcolor="#dddddd">
    <webwork:if test="/user == /remoteUser">
    <b><webwork:text name="'user.yourprofile'"/></b>
    </webwork:if>
    <webwork:else>
    <b><webwork:text name="'user.profile'"/></b>
    </webwork:else>
</td></tr>
</table>

<img src="<%= request.getContextPath() %>/images/bluepixel.gif" width="200" height="1" border="0" alt=""><br>

<webwork:property value="user">
<div class="vcard"><%-- hCard microformat --%>
<table cellpadding="3" cellspacing="0" border="0">
    <tr>
        <td valign=top>
            <b><webwork:text name="'common.words.username'"/>:</b><br>
            <webwork:property value="name" />
        </td>
    </tr>
    <tr>
        <td valign=top>
            <b><webwork:text name="'common.words.fullname'"/>:</b><br>
            <span class="fn"><webwork:property value="fullName" /></span><%-- hCard:fn --%>
        </td>
    </tr>
    <webwork:if test="displayEmail(email) != null">
    <tr>
        <td valign=top>
            <b><webwork:text name="'common.words.email'"/>:</b><br>
            <span class="email"><webwork:property value="displayEmail(email)" escape="false"/></span><%-- hCard:email --%>
        </td>
    </tr>
    </webwork:if>

    <webwork:if test="/userProperties != null && /userProperties/empty == false">
    <tr>
        <td valign="top">
        <b><webwork:text name="'user.properties'"/>:</b><br />
        <webwork:iterator value="/userProperties">
            <img src="<%= request.getContextPath() %>/images/icons/bullet_blue.gif" height="8" width="8" border="0" align="absmiddle" alt="">
            <webwork:property value="key" /> : <webwork:property value="value" /><br />
        </webwork:iterator>
        </td>
    </tr>
    </webwork:if>

    <webwork:if test="groups && groups/size > 0">
    <tr>
        <td valign=top>
            <b><webwork:text name="'common.words.groups'"/>:</b><br>
            <webwork:iterator value="groups">
                <webwork:if test="../hasViewGroupPermission(., /remoteUser) == true">
                    <img src="<%= request.getContextPath() %>/images/icons/bullet_blue.gif" height="8" width="8" border="0" align="absmiddle" alt="">
                    <webwork:property /><br>
                </webwork:if>
            </webwork:iterator>
        </td>
    </tr>
    </webwork:if>


</table>
</div>
</webwork:property>

<p>

<webwork:if test="/remoteUser">
    <webwork:if test="/user == /remoteUser">

        <table cellpadding="3" cellspacing="0" border="0" width="200">
        <tr><td bgcolor="#dddddd">
            <b><webwork:text name="'user.reports'"/></b>
        </td></tr>
        </table>
        <img src="<%= request.getContextPath() %>/images/bluepixel.gif" width=200 height=1 border=0><br>

        <table cellpadding=3 cellspacing=0 border=0>
            <tr><td>
                <img src="<%= request.getContextPath() %>/images/icons/bullet_creme.gif" height="8" width="8" border="0" align="absmiddle" alt="">
                <webwork:if test="/actionName != 'PersonalBrowser'">
                <b><a href="BrowsePersonalProject.jspa"><webwork:text name="'user.personalroadmap'"/></a></b>
                </webwork:if>
                <webwork:else>
                    <b><webwork:text name="'user.personalroadmap'"/></b>
                </webwork:else>
            </td></tr>
            <webwork:if test="applicationProperties/option('jira.option.voting') == true">
            <tr><td>
                <img src="<%= request.getContextPath() %>/images/icons/bullet_creme.gif" height="8" width="8" border="0" align="absmiddle" alt="">
                <webwork:if test="/actionName != 'UserVotes'">
                <b><a href="UserVotes!default.jspa"><webwork:text name="'user.yourvotes'"/></a></b>
                </webwork:if>
                <webwork:else>
                    <b><webwork:text name="'user.yourvotes'"/></b>
                </webwork:else>
            </td></tr>
            </webwork:if>
            <webwork:if test="applicationProperties/option('jira.option.watching') == true">
            <tr><td>
                <img src="<%= request.getContextPath() %>/images/icons/bullet_creme.gif" height="8" width="8" border="0" align="absmiddle" alt="">
                <webwork:if test="/actionName != 'UserWatches'">
                <b><a href="UserWatches!default.jspa"><webwork:text name="'user.yourwatches'"/></a></b>
                </webwork:if>
                <webwork:else>
                    <b><webwork:text name="'user.yourwatches'"/></b>
                </webwork:else>
            </td></tr>
            </webwork:if>

        </table>

        <p>
    </webwork:if>

<table cellpadding="3" cellspacing="0" border="0" width="200">
<tr><td bgcolor="#dddddd">
    <b><webwork:text name="'common.words.operations'"/></b>
</td></tr>
</table>
<img src="<%= request.getContextPath() %>/images/bluepixel.gif" width="200" height="1" border="0" alt=""><br>

<table cellpadding="3" cellspacing="0" border="0">

    <webwork:if test="/hasPermission('admin') == true">
        <tr><td>
            <img src="<%= request.getContextPath() %>/images/icons/bullet_creme.gif" height="8" width="8" border="0" align="absmiddle" alt="">
            <b><a href="<webwork:url page="/secure/admin/user/ViewUser.jspa"><webwork:param name="'name'" value="./user/name"/></webwork:url>"><webwork:text name="'common.concepts.administer.user'"/></a></b>
        </td></tr>
        <tr><td>
            <img src="<%= request.getContextPath() %>/images/icons/bullet_creme.gif" height="8" width="8" border="0" align="absmiddle" alt="">
            <b><a href="<webwork:url page="/secure/admin/ViewUserProjectRoles!default.jspa"><webwork:param name="'name'" value="./user/name"/><webwork:param name="'returnUrl'" value="'/secure/ViewProfile.jspa'" /></webwork:url>"><webwork:text name="'admin.viewuser.view.project.roles'"/></a></b>
        </td></tr>
    </webwork:if>

    <webwork:if test="/user == /remoteUser">
        <webwork:if test="applicationProperties/option('jira.option.user.externalpasswordmanagement') == false">
            <webwork:if test="/applicationProperties/option('jira.option.user.externalmanagement') == false">
            <tr><td>
                <img src="<%= request.getContextPath() %>/images/icons/bullet_creme.gif" height="8" width="8" border="0" align="absmiddle" alt="">
                <webwork:if test="/actionName != 'ChangePassword'">
                <b><a href="http://www.topcoder.com/reg/?nrg=false"><webwork:text name="'common.concepts.changepassword'"/></a></b>
                </webwork:if>
                <webwork:else>
                    <b><webwork:text name="'common.concepts.changepassword'"/></b>
                </webwork:else>
            </td></tr>
            </webwork:if>
        </webwork:if>
        <tr><td>
            <img src="<%= request.getContextPath() %>/images/icons/bullet_creme.gif" height="8" width="8" border="0" align="absmiddle" alt="">
            <webwork:if test="/actionName != 'ConfigurePortal' && /actionName != 'ConfigurePortalPages'">
            <b><a href="SetupPortal.jspa"><webwork:text name="'user.dashboardconfig'"/></a></b>
            </webwork:if>
            <webwork:else>
                <b><webwork:text name="'user.dashboardconfig'"/></b>
            </webwork:else>
        </td></tr>
        <tr><td>
            <img src="<%= request.getContextPath() %>/images/icons/bullet_creme.gif" height="8" width="8" border="0" align="absmiddle" alt="">
            <webwork:if test="/actionName != 'ManageFilters'">
                <b><a href="ManageFilters.jspa"><webwork:text name="'common.concepts.managefilters'"/></a></b>
            </webwork:if>
            <webwork:else>
                <b><webwork:text name="'common.concepts.managefilters'"/></b>
            </webwork:else>
        </td></tr>
        <tr><td>
            <img src="<%= request.getContextPath() %>/images/icons/bullet_creme.gif" height="8" width="8" border="0" align="absmiddle" alt="">
            <webwork:if test="/actionName != 'ViewUserIssueColumns'">
                <b><a href="<%= request.getContextPath() %>/secure/ViewUserIssueColumns!default.jspa"><webwork:text name="'common.concepts.navigatorColumns'"/></a></b>
            </webwork:if>
            <webwork:else>
                <b><webwork:text name="'common.concepts.navigatorColumns'"/></b>
            </webwork:else>
        </td></tr>
        <tr><td>
            <img src="<%= request.getContextPath() %>/images/icons/bullet_creme.gif" height="8" width="8" border="0" align="absmiddle" alt="">
            <webwork:if test="/actionName != 'ViewUserPreferences'">
            <b><a href="ViewUserPreferences.jspa"><webwork:text name="'user.viewpreferences'"/></a></b>
            </webwork:if>
            <webwork:else>
                <b><webwork:text name="'user.viewpreferences'"/></b>
            </webwork:else>
            <br>&nbsp; <img src="<%= request.getContextPath() %>/images/icons/bullet_creme.gif" height="8" width="8" border="0" align="absmiddle" alt="">
            <webwork:if test="/actionName != 'UpdateUserPreferences'">
                <b><a href="UpdateUserPreferences!default.jspa"><webwork:text name="'preferences.edit'"/></a></b>
            </webwork:if>
            <webwork:else>
                <b><webwork:text name="'preferences.edit'"/></b>
            </webwork:else>
        </td></tr>
    </webwork:if>
    <webwork:if test="/remoteUser">
        <tr><td>
            <img src="<%= request.getContextPath() %>/images/icons/bullet_creme.gif" height="8" width="8" border="0" align="absmiddle" alt="">
            <webwork:if test="/actionName == 'ViewProfile' && /user == /remoteUser">
            <b><webwork:text name="'user.viewyourprofile'"/></b>
            </webwork:if>
            <webwork:else>
            <b><a href="<%= request.getContextPath() %>/secure/ViewProfile.jspa"><webwork:text name="'user.viewyourprofile'"/></a></b>
            </webwork:else>
            <webwork:if test="/user == /remoteUser">
                <webwork:if test="/applicationProperties/option('jira.option.user.externalmanagement') == false">
                <br>&nbsp; <img src="<%= request.getContextPath() %>/images/icons/bullet_creme.gif" height="8" width="8" border="0" align="absmiddle" alt="">
                <webwork:if test="/actionName != 'EditProfile'">
                <b><a href="EditProfile!default.jspa"><webwork:text name="'user.editprofile'"/></a></b>
                </webwork:if>
                <webwork:else>
                    <b><webwork:text name="'user.editprofile'"/></b>
                </webwork:else>
                </webwork:if>
            </webwork:if>
        </td></tr>
    </webwork:if>
</table>
</webwork:if>
<br>