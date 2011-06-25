<%@ page language="java" contentType="text/html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags"  prefix="s"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <title>Photo Struts Actions Demo</title>
    <s:head/>
    </head>
    <body>
        <s:form action="uploadPhoto.action?photoUploadAction=preview" method="post" enctype="multipart/form-data">
            <s:file name="upload" label="User Image"/> (allowed types: jpg, gif, jpeg,png)
            <s:submit value="Preview" />
        </s:form>
     </body>
</html>

