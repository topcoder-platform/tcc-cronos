<%@page language="java" contentType="text/html" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
  <head> 
    <title></title>
  </head>
  <body>      
     <s:form action="getBillingCostExportDetails">
      	<s:textfield name="billingCostExportId" key="billingCostExportId"></s:textfield>
		<s:textfield name="pageNumber" key="pageNumber"></s:textfield>
		<s:textfield name="pageSize" key="pageSize"></s:textfield>
     	<s:submit value="submit"></s:submit>
     </s:form>
  </body>
</html>
