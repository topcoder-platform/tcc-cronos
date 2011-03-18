<%-- 
    Document   : upload
    Created on : 2011-3-3, 17:45:43
    Author     : MicroSky
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
                    session.setAttribute("member_id_session_key", new Long(1));
        %>
        <form name="form" action="/upload.do?member_id=1&submit_action=preview" method="POST" enctype="multipart/form-data"  >
            <table border="0">
                <tbody>
                    <tr>
                        <td>member_id</td>
                        <td><input type="text" name="member_id" value="1" size="10" /></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>action</td>
                        <td><input type="text" name="submit_action" value="preview" size="10" /></td>
                        <td></td>
                    </tr>

                    <tr>
                        <td>file</td>
                        <td><input type="test" name="photoImageFormFileName" value="d:/test.jpg"></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td></td>
                        <td><input type="submit" value="ok" name="submit" /></td>
                    </tr>
                </tbody>
            </table>
        </form>
    </body>
</html>
