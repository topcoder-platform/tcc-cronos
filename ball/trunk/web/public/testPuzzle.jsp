<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: TESTING MODE :: Puzzle</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/plugin.css"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta http-equiv="Expires" content="Wed, 09 Aug 2000 08:21:57 GMT"/>
    <script defer type="text/javascript" src="${ctx}/js/pngfix.js"></script>
    <script type="text/javascript">
        var debug = true;

        var T;
        var expirationTime;
        var c;
        
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
            var c = Math.floor((expirationTime - currTime) / 1000);
            c = Math.max(c, 0);
            if (c >= 0) {
                var span = document.getElementById("clock");
                var text = document.createTextNode(c);
                span.removeChild(span.firstChild);
                span.appendChild(text);
                if (c == 0) {
                    alert('Sorry, the time for solving the puzzle is up!');
                    unsetClockUpdatePeriod();
                }
            }
        } 
   
     	function gotoURL(mySelect) { 
     	 	myIndex = mySelect.selectedIndex; 
      		myValue = mySelect.options[myIndex].value; 
      		window.location.href = myValue; 
		} 
    </script>
</head>

<body id="page" onunload="unsetClockUpdatePeriod();">
<div id="container">
    <%@ include file="../includes/header.jsp" %>

    <div id="pluginTestTitle">
    	Practice: ${puzzleName}
    	<form name="SelectPuzzleForm" id="SelectPuzzleForm">
	    	<select size="1" name="puzzleImage" id="puzzleImage">
	    	   	<c:forEach items="${puzzleIDs}" var="puzzleID">
	    	 		<c:if test="${puzzleID ne selPuzzleID}">
	    	 			<option value="${ctx}/server/puzzle/${urlPatternSuffix}?puzzleId=${puzzleID}">${puzzleID}</option>
	    	 		</c:if>
	    	 		<c:if test="${puzzleID eq selPuzzleID}">
	    	 			<option value="${ctx}/server/puzzle/${urlPatternSuffix}?puzzleId=${puzzleID}" selected>${puzzleID}</option>
	    	 		</c:if>
	    	 	</c:forEach>
	    	</select>
	    	<input onclick="gotoURL(document.getElementById('puzzleImage'))" type="image" src="${ctx}/i/b/btn_go.gif" alt="Go">
    	</form>
    </div>

    <div id="wrap">
    	<img src="${ctx}/i/tabletop.gif" width=100%/>
    
        <div id="plugin-table-full">
            <ul>
                <li class="auction-block">
                    <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td width="645" class="link">
                                <p>
                                    The following are puzzles similar to the kind that you will find in the Ball game itself.  
                                    You are welcome to use these practice puzzles to try to improve your skill at them.
                                </p>

                                <p>
                                    Time left: <span id="clock">&nbsp;</span> sec. 
                                </p>

                                <p><span><a href="#">Solve puzzle</a></span></p>

                                <p align="center">
                                    ${orpheus:fixTestPuzzleUrlForTest(puzzle, puzzleId)}
                                </p>

                                <p>&nbsp;</p>
                            </td>
                        </tr>
                    </table>
                </li>
            </ul>
        </div>

        <img src="${ctx}/i/tablebot.gif" width=100%/>
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
