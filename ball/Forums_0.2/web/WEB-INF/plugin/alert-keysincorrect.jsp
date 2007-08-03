<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Invalid Keys</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/plugin.css"/>
    <script type="text/javascript" xml:space="preserve" src="${ctx}/js/pluginSupport.js">
    </script>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta http-equiv="Expires" content="Wed, 09 Aug 2000 08:21:57 GMT"/>
</head>
<body id="pagePlugin" onload="pollMessages();">
<div id="containerPlugin">
    <div id="mastHead" style="height:100px">
        <div align="center">
            <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
                    codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0"
                    width="670" height="100">
                <param name="movie" value="${ctx}/swf/template.swf"/>
                <param name="quality" value="high"/>
                <embed src="${ctx}/swf/template.swf" quality="high"
                       pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash"
                       width="670" height="100">
                </embed>
            </object>
        </div>
    </div>
    <div id="pluginTitle">Sorry, you have not entered the correct keys.</div>
    <div id="wrapPlugin">
        <div class="tabletop"></div>
        <div id="plugin-table">
            <ul>
                <li class="auction-block">
                    <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td width="645" class="link">
                                <p align="center">&nbsp;</p>

                                <p align="center">Please make sure you have the correct keys and try your luck again by
                                    following the ball to its next home at:&nbsp; <br/>
                                    <strong>
                                        <a href="${orpheus:buildDomainUrl(upcomingDomain.domainName)}"
                                           onclick="openInParentWindow(this);return false;">
                                            ${upcomingDomain.domainName}
                                        </a>
                                    </strong>
                                </p>

                                <p align="center">In the meantime, the key for this site is:<br/>
                                    <strong>
                                        <img src="${ctx}/server/getImage.do?downloadId=${slotCompletion.keyImageId}"
                                             alt="Key"/>
                                    </strong>
                                </p>

                                <p align="center">&nbsp;</p>

                                <p>&nbsp;</p>
                            </td>
                        </tr>
                    </table>
                </li>
            </ul>
        </div>
        <div class="tablebot"></div>
    </div>
</div>
<script type="text/javascript" xml:space="preserve">
    <!--
    window.focus();
    -->
</script>
</body>
</html>
