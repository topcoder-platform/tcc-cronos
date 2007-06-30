<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: TESTING MODE :: Puzzle Solution Submitted</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/plugin.css"/>
    <script type="text/javascript" xml:space="preserve" src="${ctx}/js/pluginSupport.js">
    </script>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta http-equiv="Expires" content="Wed, 09 Aug 2000 08:21:57 GMT"/>
</head>
<body id="page" onload="pollMessages();">
<div id="container">
    <%@ include file="../includes/header.jsp" %>
    <div id="pluginTestTitle">Practice: ${puzzleName}</div>
    
    <div id="wrap">
    	<img src="${ctx}/i/tabletop.gif" width=100%/>
    
        <div id="plugin-table-full">
            <ul>
                <li class="auction-block">
                    <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td width="645" class="link">
                                <p>
                                     <b>${message}</b>
                                </p>
                            </td>
                        </tr>
                    </table>
                </li>
            </ul>
        </div>

        <img src="${ctx}/i/tablebot.gif" width=100%/>
    </div>
</div>
<script type="text/javascript" xml:space="preserve">
    <!--
    window.focus();
    -->
</script>
</body>
</html>
