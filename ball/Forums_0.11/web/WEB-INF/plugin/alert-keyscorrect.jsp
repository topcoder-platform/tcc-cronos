<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Keys Correct</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/plugin.css"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta http-equiv="Expires" content="Wed, 09 Aug 2000 08:21:57 GMT"/>
    <script type="text/javascript">
        var debug = true;
            
        var T;
        var expirationTime;
        
        function setClockUpdatePeriod() {
            var timeLeft = ${requestScope['timeLeft']};
            expirationTime = new Date().getTime() + timeLeft * 1000;
            if (timeLeft > 0) {
                T = setInterval('updateClock()', 1000);
            } else {
                var span = document.getElementById("clock");
                var text = document.createTextNode('' + timeLeft);
                span.removeChild(span.firstChild);
                span.appendChild(text);
                alert('Sorry, the time for solving the puzzle is up!');
            }
        }
        
        function unsetClockUpdatePeriod() {
            clearInterval(T);
        }

        function updateClock() {
            var currTime = new Date().getTime();
            var c = Math.round((expirationTime - currTime) / 1000);
            c = Math.max(c, 0);
            if (c >= 0) {
                var span = document.getElementById("clock");
                var text = document.createTextNode('' + c);
                span.removeChild(span.firstChild);
                span.appendChild(text);
                if (c == 0) {
                    alert('Sorry, the time for solving the puzzle is up!');
                    unsetClockUpdatePeriod();
                }
            }
        }
    </script>
</head>

<body id="pagePlugin" onunload="unsetClockUpdatePeriod();">
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

    <div id="pluginTitle">Correct! Good job!</div>

    <div id="wrapPlugin">
        <div class="tabletop"></div>

        <div id="plugin-table">
            <ul>
                <li class="auction-block">
                    <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td width="645" class="link">
                                <p>
                                    There&rsquo;s just one final step between you and the prize money. Solve the next
                                    puzzle, and you win&hellip;
                                </p>

                                <p>
                                    <span><a href="#"></a></span>
                                    Don&rsquo;t dawdle, though. This one is timed, so work quickly!
                                </p>

                                <p>
                                    Time left: <span id="clock">&nbsp;</span> sec.
                                </p>

                                <p><span><a href="#">Solve puzzle</a></span></p>

                                <p align="center">
                                    ${orpheus:fixTestPuzzleUrl(puzzle, gameId, slotId)}
                                </p>

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
<script type="text/javascript">
    <!--
    setClockUpdatePeriod();
    window.focus();
    -->
</script>
</body>
</html>
