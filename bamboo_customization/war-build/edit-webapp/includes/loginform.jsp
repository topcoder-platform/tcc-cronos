<%@ page import="com.atlassian.seraph.filter.LoginFilter,
                 com.atlassian.core.util.WebRequestUtils,
                 com.atlassian.jira.ManagerFactory,
                 com.atlassian.jira.config.properties.APKeys,
                 com.atlassian.jira.util.JiraUtils,
                 com.atlassian.jira.security.JiraRoleMapper,
                 com.opensymphony.util.TextUtils"%>
<%@ taglib uri="webwork" prefix="webwork" %>

	<form method="POST" action="<%= request.getContextPath() %>/login.jsp" name="loginform">

		<table align=center cellpadding=4 cellspacing=0 border=0>
<%
    Object authStatusKey = request.getAttribute(LoginFilter.OS_AUTHSTATUS_KEY);
    if (authStatusKey != null)
    {
        if (authStatusKey.equals(LoginFilter.LOGIN_FAILED))
        {
            // We could check that value is Boolean.FALSE, but mere presence of the sttribute will do for now
            if (request.getAttribute(JiraRoleMapper.HAS_PERMISSION) != null)
            {
%>		<tr>
            <td valign="top" colspan="2" bgcolor="ffcccc">
            <p style="color: #C00; font-weight: bold;"><webwork:text name="'login.error.permission'">
                <webwork:param name="'value0'"><a href="<%= request.getContextPath() %>/secure/Administrators.jspa" style="color: #C00"></webwork:param>
                <webwork:param name="'value1'"></a></webwork:param>
            </webwork:text></p>
            </td>
        </tr>
<%
            }
            else
            {
%>		<tr>
            <td valign="top" colspan="2" bgcolor="ffcccc">
            <p style="color: #C00; font-weight: bold;"><webwork:text name="'login.error.lpincorrect'"/></p>
			</td>
		</tr>
<%
            }
        }
        else if (authStatusKey.equals(LoginFilter.LOGIN_ERROR))
        {
%>		<tr>
			<td valign=top colspan=2 bgcolor=ffcccc>
			<p>
			<b><font color=cc0000>Sorry, an error occurred trying to log you in - please try again.</font></b>
			</p>
			</td>
		</tr>
<%
        }
    }
%>		<tr>
			<td valign=middle align=right width=25%><b><webwork:text name="'alt.text.username'"/></b></td>
			<td valign=middle>
				<input style="width: 12em;" type="text" name="os_username" size="25" tabindex=1 accessKey="<webwork:text name="'alt.text.username.accessKey'"/>" value="<webwork:property value="$os_username" />">
			</td>
		</tr>

		<tr>
			<td valign=middle align=right width=25%><b><webwork:text name="'alt.text.password'"/></b></td>
			<td valign=middle>
				<input style="width: 12em;" type="password" name="os_password" size="25" tabindex=2 accessKey="<webwork:text name="'alt.text.password.accessKey'"/>"> &nbsp;
            </td>
		</tr>

        <% if (ManagerFactory.getApplicationProperties().getOption(APKeys.JIRA_OPTION_ALLOW_COOKIES)) { %>
		<tr>
			<td valign=middle align=right width=25%><input type="checkbox" name="os_cookie" id="os_cookie_id" value="true" tabindex=4></td>
			<td valign=middle>
				<label for="os_cookie_id" accesskey="<webwork:text name="'alt.text.rememberlogin.accessKey'"/>"><webwork:text name="'alt.text.rememberlogin'"/></label>
			</td>
		</tr>
        <% } %>

		<tr>
			<td valign=middle align=center colspan=2>
				<input id="login" type="submit" value="<webwork:text name="'common.concepts.login'"/>" tabindex=4>
			</td>
		</tr>

		<%
			if (!ManagerFactory.getApplicationProperties().getOption(APKeys.JIRA_OPTION_USER_EXTERNALMGT) &&
                !ManagerFactory.getApplicationProperties().getOption(APKeys.JIRA_OPTION_USER_PASSWORD_EXTERNALMGT)) {
		%>
		<tr>
			<td valign=middle align=right width=25%>&nbsp;</td>
			<td valign=top><font size=1><a href="http://www.topcoder.com/tc?module=RecoverPassword"><webwork:text name="'common.concepts.forgotpassword'"/></a></font></td>
		</tr>
		<% } %>

		<tr>
			<td valign=middle colspan=2>
				<br>
				<% if (JiraUtils.isPublicMode()) { %>
                    <webwork:if test="extUserManagement != true">
                        <webwork:text name="'login.signup'">
                            <webwork:param name="'value0'"><b><a id="signup" href="<%= request.getContextPath() %>/secure/Signup!default.jspa"></webwork:param>
                            <webwork:param name="'value1'"></a></b></webwork:param>
                        </webwork:text>
                    </webwork:if>
                <% } else { %>
                    <webwork:text name="'login.requestaccount'">
                        <webwork:param name="'value0'"><a href="<%= request.getContextPath() %>/secure/Administrators.jspa"></webwork:param>
                        <webwork:param name="'value1'"></a></webwork:param>
                    </webwork:text>
				<% } %>
			</td>
		</tr>
		</table>

		<input type="hidden" name="os_destination" value="<%= (request.getParameter("os_destination") == null ? "/secure/" : TextUtils.htmlEncode(request.getParameter("os_destination"))) %>">
	</form>
    <%-- Don't do this for netscape - stuffs up the tabbing --%>
    <% if (WebRequestUtils.isGoodBrowser(request)) { %>
	<script language="javascript">
	document.loginform.elements[0].focus();
	</script>
    <% } %>