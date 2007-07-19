<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/paging" prefix="p" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="nextHuntTarget" value="${requestScope['nextHuntTarget']}" scope="page"/>
<c:set var="nextHuntPage" value="${requestScope['nextHuntUrl']}" scope="page"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Brainteaser</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/plugin.css"/>
    <script type="text/javascript" xml:space="preserve" src="${ctx}/js/pluginSupport.js"></script>
    <script type="text/javascript" xml:space="preserve">
        var T;
        function setBrainteaserUpdatePeriod() {
            <c:if test="${requestScope['nextBrainteaserAvailable'] ne null}">
            T = setTimeout('updateBrainteaser()', ${requestScope['brainteaserUpdatePeriod']});
            </c:if>
        }
        function unsetBrainteaserUpdatePeriod() {
            <c:if test="${requestScope['nextBrainteaserAvailable'] ne null}">
            clearTimeout(T);
            </c:if>
        }
        <c:if test="${requestScope['nextBrainteaserAvailable'] ne null}">
        function updateBrainteaser() {
            clearTimeout(T);
            window.location = '${ctx}/server/plugin/brainteaser.do?gameId=${param['gameId']}&domain=${param['domain']}';
        }
        </c:if>

    </script>
</head>

<body id="pagePlugin"
      onload="setBrainteaserUpdatePeriod();setCurrentTarget('${nextHuntTarget.identifierHash}', '${nextHuntPage}', ${nextHuntTarget.sequenceNumber});setCurrentGame(${param['gameId']});"
      onunload="unsetBrainteaserUpdatePeriod();">
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

    <div id="pluginTitle"></div>

    <div id="wrapPlugin">
        <div class="tabletop"></div>

        <div id="plugin-table">
            <ul>
                <li class="auction-block">
                    <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td width="645" class="link">
                                <p>
                                    Unscramble the letters or fill in the missing letters to determine the first hunt
                                    item on this domain. Don't worry if you can't solve the puzzle, we will make the
                                    clue easy every few minutes. Once you know what to look for browse around the site
                                    to find the right instance of the hunt item - not all instances of the item are
                                    correct. <b>Hint:</b> To test an object, move your mouse over the word on the
                                    website, right-click with your mouse and choose "The Ball - Test Target Object".
                                </p>
                                <p>
                                    If you are unable to solve the Missing Letter Puzzle or Word Scramble, we will
                                    update the puzzle every 3-5 minutes, so that the clue becomes progressively easier.
                                    Eventually the clue will be unscrambled or filled-in completely if you wait long
                                    enough!
                                </p>
                                <p>&nbsp;</p>
                                <p>&nbsp;</p>
                                <p>${puzzle}</p>
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