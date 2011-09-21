<%@page language="java" contentType="text/html" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
  <head> 
    <title></title>
  </head>
  <body>      
     <s:form action="getAuditHistory">
        <s:textfield name="criteria.startDate" key="startDate"></s:textfield>
        <s:textfield name="criteria.endDate" key="endDate"></s:textfield>
      	<s:textfield name="criteria.action" key="action"></s:textfield>
      	<s:textfield name="criteria.userName" key="userName"></s:textfield>
		<s:textfield name="pageNumber" key="pageNumber"></s:textfield>
		<s:textfield name="pageSize" key="pageSize"></s:textfield>
     	<s:submit value="submit"></s:submit>
     </s:form>

  </body>
</html>
