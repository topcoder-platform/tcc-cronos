<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
	<title>Forum rating preferences changed.</title>
</head>
<body>
    showRatings: <s:property value="showRatings" /> </br>
	ratingHighlightThreshold: <s:property value="ratingHighlightThreshold" /> </br>
	ratingHighlightMinCount: <s:property value="ratingHighlightMinCount" /> </br>
	ratingCollapseThreshold: <s:property value="ratingCollapseThreshold" /> </br>
	ratingCollapseMinMessages: <s:property value="ratingCollapseMinMessages" /> </br>
	ratingCollapseMinCount: <s:property value="ratingCollapseMinCount" /> </br>
	<a href="<%= request.getContextPath() %>/">Go to main page.</a>
</body>
</html>