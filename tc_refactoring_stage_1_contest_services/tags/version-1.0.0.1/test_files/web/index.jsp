<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
	<title>TC_Refactoring_Stage_1_Contest_Service</title>
</head>
<body>
    <s:form action="showResult" namespace="/">
        <s:textfield name="parameter" label="JSON" value="{\"columnName\":\"projectGroupCategory.name\", \"sortingOrder\":\"ASCENDING\", \"pageNumber\":1, \"pageSize\":\"2\", \"filter\":{\"contestName\":\"ProjectContestName\", \"prizeLowerBound\":-1, \"prizeUpperBound\":1000}}"/>
        <s:submit value="Show result" />
    </s:form>
</body>
</html>
