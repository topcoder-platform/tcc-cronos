<%@ page language="java" import="com.topcoder.web.reg.actions.photo.*" contentType="text/html"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html" charset="ISO-8859-1">
        <title>Photo Struts Actions Demo</title>
    </head>
    <body>
    	<% 
    	request.getSession().setAttribute("authenticationkey", new MockBasicAuthentication()); %>
    	My Id: <% out.println(((MockBasicAuthentication)session.getAttribute("authenticationkey")).getActiveUser().getId()) ;%>
    </br>                                                                                                     
    	My Name: <% out.println(((MockBasicAuthentication)session.getAttribute("authenticationkey")).getActiveUser().getUserName()); %>
    </br>    
    	(The username/id is just mocked, check MockBasicAuthentication.java for more details.)
    </br>
    </br>    
    </br>    
    <a href="upload.jsp">Upload Photo</a>
    </br>
     <a href="removePhoto.action?removalReason=noreason">Remove Photo</a>
     </body>
</html>
