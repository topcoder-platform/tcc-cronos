<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
	<title>Rating Preferences</title>
</head>
<body>
Current action: <s:property value="action"/> </br>

    <s:form action="forumrating" namespace="/">	
	<s:hidden name="action" value="submit" />
	
	   <s:label value="Show ratings:" /> <s:radio name="showRatings" list="{'Yes','No'}"/> </br>
	   <s:label value="Highlight posts:" />
	   <s:textfield name="ratingHighlightThreshold" list="{'2, 20','40','80'}" /> <s:label value="% or higher rating with" />
	   <s:textfield name="ratingHighlightMinCount" list="{'3','10','50','100'}" /> <s:label value="or more votes" /> </br>
	
	<s:label value="Collapse posts:"/>	 <s:textfield name="ratingCollapseThreshold" list="{'20','40','60'}" />% 
	<s:label value="or lower rating with " /> <s:textfield name="ratingCollapseMinCount" list="{'10','50','100'}" />  
	<s:label value="or more votes in threads with " /> <s:textfield name="ratingCollapseMinMessages" list="{'10','50','100'}" />   <s:label value="or more messages" /></br>
	
	
	<s:submit align="left"/>
    </s:form>
	<s:form action="forumrating" namespace="/">	
	<s:hidden name="action" value="discard" />
	<s:submit value="Discard" />
    </s:form>
</body>
</html>