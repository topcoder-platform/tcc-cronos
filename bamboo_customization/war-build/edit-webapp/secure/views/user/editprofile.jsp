<%@ taglib uri="webwork" prefix="webwork" %>
<%@ taglib uri="webwork" prefix="ui" %>
<%@ taglib uri="sitemesh-page" prefix="page" %>
<html>
<head>
	<title><webwork:text name="'editprofile.title'"/>: <webwork:property value="remoteUser/fullName" /></title>
</head>

<body>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tr>
<td width="200" bgcolor="#f0f0f0" valign="top">
    <jsp:include page="/includes/panels/user_summary_pane.jsp" />
</td>
<td bgcolor="#ffffff" valign="top">
<table width="100%" cellpadding="10" cellspacing="0" border="0"><tr><td>

    <page:applyDecorator name="jiraform">
        <page:param name="title"><webwork:text name="'editprofile.title'"/></page:param>
        <page:param name="description">
            <%-- PLEASE DO NOT BREAK THIS LINE INTO TWO, WEBLOGIC DOES NOT LIKE IT --%>
            <webwork:if test="/applicationProperties/option('jira.option.user.externalpasswordmanagement') == false && /applicationProperties/option('jira.option.user.externalmanagement') == false">
                <webwork:text name="'editprofile.desc.change.password'">
                    <webwork:param name="'value0'"><a href="http://www.topcoder.com/reg/?nrg=false"></webwork:param>
                    <webwork:param name="'value1'"></a></webwork:param>
                </webwork:text>
            </webwork:if>
            <webwork:else>
                <webwork:text name="'editprofile.desc'"/>
            </webwork:else>
        </page:param>
        <page:param name="action">EditProfile.jspa</page:param>
        <page:param name="submitName"><webwork:text name="'common.forms.update'"/></page:param>
        <page:param name="cancelURI">ViewProfile.jspa</page:param>
        <page:param name="width">100%</page:param>

        <ui:textfield label="text('common.words.fullname')" name="'fullName'" size="40" />
        <ui:textfield label="text('common.words.email')" name="'email'" size="40" />
    </page:applyDecorator>

</td></tr></table>
</td></tr></table>
</body>
</html>
