<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: The Ball Found</title>
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
    <script type="text/javascript" src="${ctx}/js/forms.js"></script>
    <script type="text/javascript" xml:space="preserve" src="${ctx}/js/pluginSupport.js">
    </script>
</head>

<body id="pagePlugin" onload="setCurrentGame(${param['gameId']});">
<div id="containerPlugin">
    <div id="mastHead" style="height:220px">
        <div align="center">
            <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
                    codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0"
                    width="670" height="220">
                <param name="movie" value="${ctx}/swf/alert-foundball.swf"/>
                <param name="quality" value="high"/>
                <embed src="${ctx}/swf/alert-foundball.swf" quality="high"
                       pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash"
                       width="670" height="220">
                </embed>
            </object>
        </div>
    </div>

    <div id="pluginTitle">Don&rsquo;t freak out or anything, but you&rsquo;ve found The Ball!</div>

    <div id="wrapPlugin">
        <div class="tabletop"></div>

        <div id="plugin-table">
            <ul>
                <li class="auction-block">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td width="645" class="link" colspan="2">
                                    <span style="font-size:13px;color:black;font-weight:normal;">
                                        Enter the keys from the previous ${game.keyCount} Ball hosts to unlock the ball.
                                    </span>
                                    <br/><br/>
                                    <span style="font-size:11px;color:red;font-weight:normal;">
                                        You can access your saved keys by clicking
                                        <a href="${ctx}/server/plugin/unlockedDomains.do?gameId=${game.id}"
                                           style="font-size:11px;color:red;font-weight:normal;text-decoration:underline;"
                                           target="_blank">here</a> or by right-clicking on the
                                        "Unlocked Sites" tab and choosing to open in a new window.
                                    </span>
                                    <br/><br/>
                                    If you don't have all the keys don't worry - you can go back and find other keys
                                    that you missed or you can keep following the ball as it moves on. <b>Hint:</b>
                                    Click on "Unlocked Sites" to see domains where other players have found keys for
                                    this game.
                                    <br/>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdTop" colspan="2"></td>
                            </tr>
                            <c:if test="${param['failed'] eq 'true'}">
                                <tr>
                                    <td colspan="2">
                                        <span class="fBold cRed">
                                            At least one of the specified key is not valid or missing.
                                            Please, re-enter the keys once again.
                                        </span>
                                        <br/>
                                    </td>
                                </tr>
                            </c:if>
                            <c:if test="${param['inactive'] eq 'true'}">
                                <tr>
                                    <td colspan="2">
                                        <span class="fBold cRed">
                                            The game is no longer active.
                                        </span>
                                        <br/>
                                    </td>
                                </tr>
                            </c:if>
                            <form action="${ctx}/server/plugin/unlockBall.do" name="UnlockBallForm"
                                  id="UnlockBallForm" method="POST">
                                <input type="hidden" name="gameId" value="${game.id}"/>
                                <input type="hidden" name="domain" value="${param['domain']}"/>
                            <c:forEach begin="0" end="${game.keyCount - 1}" varStatus="index">
                                <tr>
                                    <td>Key # ${index.index + 1}:</td>
                                    <td>
                                        <input name="ballKey" type="text" id="ballKey${index.index}" class="inputBox"
                                               style="width:143px;" value="${paramValues['ballKey'][index.index]}"/>
                                    </td>
                                </tr>
                            </c:forEach>
                            </form>
                            <tr>
                                <td class="buttons" colspan="2">
                                    <a href="#" onclick="submitForm(document.UnlockBallForm);return false;"
                                       title="Login">
                                        <img src="${ctx}/i/b/btn_submit.gif" alt="Submit">
                                    </a>
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
