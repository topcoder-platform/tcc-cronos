<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="user" value="${sessionScope['user_profile']}"/>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/>
<c:set var="nextHuntTarget" value="${requestScope['nextHuntTarget']}" scope="page"/>
<c:set var="nextHuntPage" value="${requestScope['nextHuntUrl']}" scope="page"/>
<c:set var="s" value="${orpheus:getPreferredSound(user)}" scope="page"/>
<c:set var="soundFile"
       value="${s eq 1 ? 'default' : (s eq 2 ? 'days' : (s eq 3 ? 'klasik' : (s eq 4 ? 'march' : (s eq 5 ? 's1' : (s eq 6 ? 'studio' : 'nosound')))))}"
       scope="page"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <c:if test="${empty param['notitle']}">
        <title>The Ball :: Hunt Target Found</title>
    </c:if>
    <c:if test="${not empty param['notitle']}">
        <title>The Ball :: Next Hunt Target</title>
    </c:if>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/plugin.css"/>
    <script type="text/javascript" xml:space="preserve" src="${ctx}/js/pluginSupport.js"></script>
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

<body id="pagePlugin"
      onload="setCurrentTarget('${nextHuntTarget.identifierHash}', '${nextHuntPage}', ${nextHuntTarget.sequenceNumber});setCurrentGame(${param['gameId']});">
<div id="containerPlugin">
    <div id="mastHead" style="height:100px">
        <div align="center">
            <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
                    codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0"
                    width="670" height="100">
                <param name="movie" value="${ctx}/swf/prefs/${soundFile}.swf"/>
                <param name="quality" value="high"/>
                <embed src="${ctx}/swf/prefs/${soundFile}.swf" quality="high"
                       pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash"
                       width="670" height="100">
                </embed>
            </object>
        </div>
    </div>

    <div id="pluginTitle">
        <c:if test="${empty param['notitle']}">
            You&rsquo;re a shrewd and ingenious tracker. You&rsquo;ve found the next object in the hunt!
        </c:if>
    </div>

    <div id="wrapPlugin">
        <div class="tabletop"></div>

        <div id="plugin-table">
            <ul>
                <li class="auction-block">
                    <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td width="645" class="link">
                                <p>
                                    Your next clue is:
                                    <span>
                                        <img src="${ctx}/server/getImage.do?downloadId=${nextHuntTarget.clueImageId}" alt="Next Clue"/>
                                    </span>
                                </p>

                                <p>
                                    Search this site to find that text. When you find it, click it with right mouse
                                    button and select "The Ball - Test Object" from context menu.
                                </p>

                                <p>&nbsp;</p>

                                <p>
                                    <img src="${ctx}/i/logo.gif" alt="Logo" width="130" height="65"/>
                                </p>
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
