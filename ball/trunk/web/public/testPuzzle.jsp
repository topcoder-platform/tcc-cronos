<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%  String puzzleName = (String) request.getAttribute("puzzleName"); %>

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

<c:if test="${puzzleName eq 'Jigsaw Puzzle'}">
	<body id="page-full" onunload="unsetClockUpdatePeriod();">
</c:if>
<c:if test="${puzzleName ne 'Jigsaw Puzzle'}">
	<body id="page" onunload="unsetClockUpdatePeriod();">
</c:if>

<div id="container">
	<c:if test="${puzzleName eq 'Jigsaw Puzzle'}">
    	<%@ include file="../includes/header_jigsaw.jsp" %>
    </c:if>
    <c:if test="${puzzleName ne 'Jigsaw Puzzle'}">
    	<%@ include file="../includes/header.jsp" %>
    </c:if>

    <div id="pluginTestTitle">
	    Practice:
	    <%	String[] puzzleNames = {"Jigsaw Puzzle", "Sliding Tile Puzzle"}; 
	    	String[] puzzleURLs = {"jigsaw.do", "tile.do"}; %>
	    <select size="1" name="puzzleType" id="puzzleType" onchange="location = this.options[this.selectedIndex].value;">
			<%	for (int i=0; i<puzzleNames.length; i++) { %>
			<%		if (puzzleNames[i].equals(puzzleName)) { %>
						<option value=<%=puzzleURLs[i]%> selected><%=puzzleNames[i]%></option>
			<%		} else { %>
						<option value=<%=puzzleURLs[i]%>><%=puzzleNames[i]%></option>
			<%		} %>
			<%	} %>
		</select>
    	<form name="SelectPuzzleForm" id="SelectPuzzleForm">
	    	<select size="1" name="puzzleId" id="puzzleId">
	    	   	<c:forEach items="${puzzleIDs}" var="puzzleID">
	    	 		<c:if test="${puzzleID ne selPuzzleID}">
	    	 			<option value="${puzzleID}">${puzzleID}</option>
	    	 		</c:if>
	    	 		<c:if test="${puzzleID eq selPuzzleID}">
	    	 			<option value="${puzzleID}" selected>${puzzleID}</option>
	    	 		</c:if>
	    	 	</c:forEach>
	    	</select>
	    	<input onclick="gotoURL(document.getElementById('puzzleId'))" type="image" src="${ctx}/i/b/btn_go.gif" alt="Go">
    	</form>
    </div>

    <div id="wrap">
    	<c:if test="${puzzleName eq 'Jigsaw Puzzle'}">
    		<img src="${ctx}/i/tabletop.gif" width="940"/>
    	</c:if>
    	<c:if test="${puzzleName ne 'Jigsaw Puzzle'}">
    		<img src="${ctx}/i/tabletop.gif" width="100%"/>
    	</c:if>
    
        <div id="plugin-table-full">
            <ul>
                <li class="auction-block">
                    <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td class="link">
                                <p>
                                    The following puzzle is similar to the kind that you will find in the actual Ball game.  
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

        <c:if test="${puzzleName eq 'Jigsaw Puzzle'}">
    		<img src="${ctx}/i/tablebot.gif" width="940"/>
    	</c:if>
    	<c:if test="${puzzleName ne 'Jigsaw Puzzle'}">
    		<img src="${ctx}/i/tablebot.gif" width="100%"/>
    	</c:if>
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
