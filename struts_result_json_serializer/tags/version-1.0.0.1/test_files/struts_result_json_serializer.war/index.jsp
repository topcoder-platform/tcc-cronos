<%@pagelanguage="java"import="java.util.*"pageEncoding="GBK"%>
　<%@taglibprefix="s"uri="/struts-tags"%>
　<html>
　<head>
　<title>demo</title>
　</head>
　<body>
　	<h2>
        <s:property value="message" escape="false"/>
        <br>Message from session: <s:property value="#session.msg"/>
    </h2>
　<br/>
　</body>
　</html>