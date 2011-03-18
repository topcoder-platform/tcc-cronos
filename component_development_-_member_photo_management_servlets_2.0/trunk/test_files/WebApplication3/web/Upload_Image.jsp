<%@page contentType="text/html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <title>JSP Page</title>
    </head>
    <body>
        <form name="form" action="/upload.do?member_id=1" method="POST" enctype="multipart/form-data"  >
            photo image file <input  name= "photoImageFormFileName" type="file"  accept= "image/jpeg,image/gif,image/png"> <br>
            <%
                        session.setAttribute("member_id_session_key", new Long(1));
            %>
            member_id<input type="text" name=member_id id=member_id value="1"><br>
            submit_action<input type="text" name="submit_action" value="preview"><br>
            <input type="submit" value="OK">
        </form>
    </body>
</html>
