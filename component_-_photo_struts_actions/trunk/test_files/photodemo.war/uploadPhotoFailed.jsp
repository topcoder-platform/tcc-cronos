<%@ page language="java" import="com.topcoder.web.reg.actions.photo.*" contentType="text/html"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html" charset="ISO-8859-1">
        <title>Photo Struts Actions Demo</title>
    </head>
    <body>
    	Failed to commit uploading your photo:


<s:if test="hasActionErrors()">
<s:iterator id="actionError" value="actionErrors">
	action error:
<s:property />
</s:iterator>
</sif>
     </body>
</html>
