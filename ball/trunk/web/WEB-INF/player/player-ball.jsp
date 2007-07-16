<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="gameId" value="${param['gameId']}" scope="page"/>
<c:set var="gameSlots" value="${requestScope['gameSlots']}" scope="page"/>
<c:set var="slotCenters" value="${requestScope['slotCenters']}" scope="page"/>
<c:set var="circleRadius" value="${requestScope['circleRadius']}" scope="page"/>
<c:set var="completedSlots" value="${requestScope['completedSlots']}" scope="page"/>
<c:set var="imageWidth" value="${requestScope['imageWidth']}" scope="page"/>
<c:set var="imageHeight" value="${requestScope['imageHeight']}" scope="page"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Personalized Ball</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
</head>

<body id="page">
<div id="container">
    <c:set var="subbar" value="faq" scope="page"/>
    <%@ include file="header.jsp" %>
    <div id="wrap" style="text-align:center;">
        <h3>PERSONALIZED <span class="main">BALL FOR ${game.name} </span></h3><br/>
        <img src="${ctx}/server/player/getPersonalizedBallImage.do" alt="Personalized Ball for Game"
             usemap="#PersonalizedBall" width="${imageWidth}" height="${imageHeight}"/>
        <map id="PersonalizedBall" name="PersonalizedBall">
            <c:forEach items="${gameSlots}" var="slot" varStatus="slotIndex">
                <c:if test="${slot.hostingStart ne null}">
                    <c:if test="${completedSlots[slot.id] ne null}">
                        <area alt="Unlocked by you: ${slot.domain.domainName}" shape="circle"
                              coords="${slotCenters[slotIndex.index].x},${slotCenters[slotIndex.index].y},${circleRadius}"
                              target="OrpheusUnlockedSlot" href="http://${slot.domain.domainName}"/>
                    </c:if>
                    <c:if test="${completedSlots[slot.id] eq null}">
                        <c:if test="${slot.hostingEnd ne null}">
                            <area alt="Unlocked by another player" shape="circle"
                                  coords="${slotCenters[slotIndex.index].x},${slotCenters[slotIndex.index].y},${circleRadius}"
                                  href="#"/>
                        </c:if>
                        <c:if test="${slot.hostingEnd eq null}">
                            <area alt="The Ball is here" shape="circle"
                                  coords="${slotCenters[slotIndex.index].x},${slotCenters[slotIndex.index].y},${circleRadius}"
                                  href="#"/>
                        </c:if>
                    </c:if>
                </c:if>
                <c:if test="${slot.hostingStart eq null}">
                    <area alt="Upcoming site" shape="circle"
                          coords="${slotCenters[slotIndex.index].x},${slotCenters[slotIndex.index].y},${circleRadius}"
                          href="#"/>
                </c:if>
            </c:forEach>
        </map>
    </div>
</div>
<%@ include file="/includes/footer.jsp" %>
</body>
</html>