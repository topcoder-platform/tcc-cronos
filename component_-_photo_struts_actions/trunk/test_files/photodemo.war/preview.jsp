<%@ page language="java" import="com.topcoder.web.reg.actions.photo.*" contentType="text/html"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html" charset="ISO-8859-1">
        <title>Photo Struts Actions Demo</title>
    </head>
    <body>
    	Photo preview.<br/>
    	<%out.println("<img src ='previewed/" + request.getParameter("previewImageFileName") + "' /> ");%>
    	<%out.println("<a href='uploadPhoto.action?photoUploadAction=commit&previewImageFileName=" + request.getParameter("previewImageFileName") + "' >Commit upload</a>");%>
    	
       
        
     </body>
</html>
