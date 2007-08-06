<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/paging" prefix="p" %>
<%@ taglib uri="/orpheus_admin" prefix="admin" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: View Puzzle</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <script defer type="text/javascript" src="${ctx}/js/pngfix.js"></script>
    <script type="text/javascript">
        var debug = true;

        var T;
        var expirationTime;

        var T2;
        var timeLeftOnServer;
        var req;

        function setClockUpdatePeriod() {
            var timeLeft = ${requestScope['timeLeft']};
            timeLeftOnServer = timeLeft;
            expirationTime = new Date().getTime() + timeLeft * 1000;
            if (timeLeft > 0) {
                T = setInterval('updateClock()', 1000);
                T2 = setInterval('updateClockFromServer()', 20000);
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
            clearInterval(T2);
        }

        function updateClock() {
            var currTime = new Date().getTime();
            var c = Math.floor((expirationTime - currTime) / 1000);
            c = Math.max(c, 0);
            if (c >= 0) {
                var span = document.getElementById("clock");
                var text = document.createTextNode(c + ' (Time Left On Server: ' + timeLeftOnServer + ')');
                span.removeChild(span.firstChild);
                span.appendChild(text);
                if (c == 0) {
                    alert('Sorry, the time for solving the puzzle is up!');
                    unsetClockUpdatePeriod();
                }
            }
        }

        function updateClockFromServer() {
            var url = '${ctx}' + '/server/getPuzzleTimeLeft.do?gameId=${param['gameId']}&slotId=${param['slotId']}';
            sendRequest(url);
        }

        function sendRequest(url) {
            // branch for native XMLHttpRequest object
            if (window.XMLHttpRequest) {
                req = new XMLHttpRequest();
                req.onreadystatechange = processReqChange;
                req.open("GET", url, true);
                try {
                    req.overrideMimeType('text/xml');
                } catch(ex) {
                    // ignore, the browser does not support that method
                }
                req.send(null);
                // branch for IE/Windows ActiveX version
            } else if (window.ActiveXObject) {
                req = new ActiveXObject("Microsoft.XMLHTTP");
                if (req) {
                    req.onreadystatechange = processReqChange;
                    req.open("GET", url, true);
                    req.send();
                }
            }
        }

        function processReqChange() {
            if (req.readyState == 4) {
                if (req.status == 200) {
                    var response = req.responseXML.documentElement;
                    var tl = response.getElementsByTagName('timeLeft');
                    if (tl.length > 0) {
                        var t = '';
                        if (tl[0].firstChild != null) {
                            t = response.getElementsByTagName('timeLeft')[0].firstChild.data;
                        }
                        timeLeftOnServer = parseInt(t);
                    }

                } else {
                    if (debug) {
                        alert("Could not obtain a time left on a server :\n" + req.statusText);
                    }
                }
            }
        }
    </script>
</head>
<body onunload="unsetClockUpdatePeriod();">
<p>
    Time left: <span id="clock">&nbsp;</span> sec.
</p>
${puzzle}
    <br/>
    <a href="${ctx}/server/admin/regeneratePuzzle.do?slotId=${param['slotId']}&gameId=${param['gameId']}" >Regenerate Puzzle</a>
    <script type="text/javascript" xml:space="preserve">
        <!--
        setClockUpdatePeriod();
        -->
    </script>
</body>
</html>