<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/>
<c:set var="slotCompletion" value="${requestScope['slotCompletion']}" scope="page"/>
<c:set var="upcomingDomains" value="${requestScope['upcomingDomains']}" scope="page"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Key Found</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/plugin.css"/>
    <script type="text/javascript" xml:space="preserve" src="${ctx}/js/pluginSupport.js">
    </script>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta http-equiv="Expires" content="Wed, 09 Aug 2000 08:21:57 GMT"/>
    <style type="text/css">
        <!--
        .style1 {
            color: #FFFFFF
        }

        -->
    </style>
</head>
<body id="pagePlugin" onload="setCurrentGame(${param['gameId']});pollMessages();">
<div id="containerPlugin">
    <div id="mastHead" style="height:120px">
        <div align="center">
            <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
                    codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0"
                    width="670" height="120">
                <param name="movie" value="${ctx}/swf/alert-header-foundkey.swf"/>
                <param name="quality" value="high"/>
                <embed src="${ctx}/swf/alert-header-foundkey.swf" quality="high"
                       pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash"
                       width="670" height="120">
                </embed>
            </object>
        </div>
    </div>
    <div id="pluginTitle">Congratulations! You've unlocked a key to The Ball!</div>
    <div id="wrapPlugin">
        <div class="tabletop"></div>
        <div id="plugin-table">
            <ul>
                <li class="auction-block">
                    <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td width="645" class="link">
                                <p align="center">Don&rsquo;t lose it &ndash; you&rsquo;ll need it to unlock The Ball
                                    and
                                    win.</p>

                                <p align="center">
                                    <img src="${ctx}/server/getImage.do?downloadId=${slotCompletion.keyImageId}"
                                         alt="Key"/>
                                </p>

<%--
                                <p align="center">&nbsp;</p>

--%>
                                <p align="center">
                                    <c:if test="${preceedingDomain ne null}">
                                        The previous site in the hunt is:<br/>
                                    <span>
                                        <a href="${orpheus:buildDomainUrl(preceedingDomain.domainName)}"
                                           onclick="openInParentWindow(this);return false;">
                                                ${preceedingDomain.domainName}
                                        </a>
                                    </span><br/>
                                    </c:if>
                                    The next site in the hunt is:<br/>
                                    <span>
                                        <a href="${orpheus:buildDomainUrl(upcomingDomain.domainName)}"
                                           onclick="openInParentWindow(this);return false;">
                                            ${upcomingDomain.domainName}
                                        </a>
                                    </span>
                                </p>

                                <p>&nbsp;</p>

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
