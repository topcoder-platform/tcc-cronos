<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/paging" prefix="p" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Domain Games</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/plugin.css"/>
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
    <script type="text/javascript" xml:space="preserve" src="${ctx}/js/popup.js">
    </script>
</head>

<body id="pagePlugin" onload="window.focus();">
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

    <div id="pluginTitle"><c:if test="${not empty games}">You&rsquo;re getting warmer&hellip;.</c:if></div>

    <div id="wrapPlugin">
        <div class="tabletop"></div>

        <div id="plugin-table">
            <ul>
                <li class="auction-block">
                    <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td width="645" class="link">
                                <c:if test="${not empty games}">
                                    <p align="center">
                                        <strong>
                                            This site contains keys for the following game
                                            <c:if test="${orpheus:getLength(games) > 1}">s</c:if>
                                            :
                                        </strong>
                                    </p>

                                    <c:forEach items="${games}" var="game">
                                        <c:set var="startGameUrl"
                                               value="${ctx}/server/plugin/startGamePlay.do?gameId=${game.id}&domain=${param['domain']}"
                                               scope="page"/>
                                        <p align="center">
                                        <span>
                                            <a href="${startGameUrl}">
                                                <img
                                                    src="${ctx}/server/getImage.do?downloadId=${game.ballColor.imageId}"
                                                    width="27" height="27" align="absmiddle" alt="Play"/> ${game.name}
                                            </a>
                                        </span>
                                        </p>
                                    </c:forEach>

                                    <p align="center">&nbsp; </p>

                                    <p align="center">
                                        Choose a game from the list and click on it to follow the ball's trail!
                                    </p>
                                </c:if>
                                <c:if test="${empty games}">
                                    <p align="center">
                                        <strong>There are no active games for domain ${param['domain']} currently</strong>
                                    </p>
                                </c:if>

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
