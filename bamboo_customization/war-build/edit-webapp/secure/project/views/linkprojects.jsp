<!-- Copyright (C) 2007 TopCoder Inc., All Rights Reserved. -->
<!-- JSP used to link project dependencies. -->

<%@ taglib uri="webwork" prefix="webwork" %>
<%@ taglib uri="webwork" prefix="ui" %>
<%@ taglib uri="sitemesh-page" prefix="page" %>
<html>
<head>
	<title><webwork:text name="'admin.projects.link.project'"/>: <webwork:property value="project/string('name')" /></title>
</head>
<body>
<page:applyDecorator name="jiraform">
	<page:param name="title"><webwork:text name="'admin.projects.link.project'"/>: <webwork:property value="project/string('name')" /></page:param>
	<page:param name="width">100%</page:param>
    <page:param name="description"><webwork:text name="'admin.projects.link.project.caption'"/></page:param>
	<page:param name="action">LinkProjects.jspa</page:param>
	<page:param name="submitName"><webwork:text name="'common.forms.update'"/></page:param>
	<page:param name="cancelURI">ViewProject.jspa?pid=<webwork:property value="pid" /></page:param>

	<webwork:property value="project">
		<ui:select label="text('common.concepts.version')" name="'versionId'" list="versionManager/versions(.)"
	               listKey="'id'" listValue="'name'" 
	               onchange="text('link.project.onchange')">
			<ui:param name="'mandatory'" value="'true'" />
		</ui:select>
	</webwork:property>

	<ui:select label="text('link.project.dependency.project')" name="'dependencyProjectId'" list="projectsExcludingCurrent()"
	               listKey="'string('id')'" listValue="'string('name')'" 
	               onchange="text('link.project.onchange')">
		<ui:param name="'mandatory'" value="'true'" />
	</ui:select>
	
	<ui:select label="text('link.project.dependency.project.version')" name="'dependencyVersionId'" 
				   list="versions(dependencyProjectId)"
	               listKey="'string('id')'" listValue="'string('name')'">
		<ui:param name="'mandatory'" value="'true'" />
	</ui:select>

    <ui:component name="'pid'" template="hidden.jsp" />
</page:applyDecorator>
<p>
<page:applyDecorator name="jirapanel">
    <page:param name="title"><webwork:text name="'link.project.dependency.projects'"/></page:param>
    <page:param name="width">100%</page:param>

    <p>
    	<webwork:text name="'link.project.dependency.projects.description'" />
    </p>
</page:applyDecorator>
</p>
<p>
<table class="grid maxWidth">
<tr>
    <th><webwork:text name="'common.words.name'"/></th>
    <th><webwork:text name="'issue.field.key'"/></th>
    <th><webwork:text name="'common.concepts.version'"/></th>
    <th class="minWidth"><webwork:text name="'common.words.operations'"/></th>
</tr>

<webwork:iterator value="dependencies()" >
	<tr>
		<td align="left"><webwork:property value="projectObject/name" /></td>
		<td align="left"><webwork:property value="projectObject/key" /></td>
		<td align="left"><webwork:property value="name" /></td>
		<td align="left"><a href="RemoveLink.jspa?pid=<webwork:property value="pid"/>&versionId=<webwork:property value="versionId"/>&dependencyProjectId=<webwork:property value="projectObject/id" />&dependencyVersionId=<webwork:property value="id"/>"><webwork:text name="'link.project.remove'"/></a></td>
	</tr>	
</webwork:iterator>
</table>

<script language="javascript">
<!--
function changeUrl() { 
	var versionSelect = document.getElementById('versionId');
	var dependencyProjectSelect = document.getElementById('dependencyProjectId');

    versionValue = versionSelect.options[versionSelect.selectedIndex].value; 
    dependencyProjectValue = dependencyProjectSelect.options[dependencyProjectSelect.selectedIndex].value; 

   window.location.href = 'LinkProjects!default.jspa?pid=<webwork:property value="pid" />&versionId=' + versionValue + '&dependencyProjectId=' + dependencyProjectValue; 
}
// -->
</script>

</body>
</html>