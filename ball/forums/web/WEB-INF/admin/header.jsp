<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="user" value="${sessionScope['user_profile']}"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
    <!--
    function SwapImage1() {
        document.image1.src = "${ctx}/i/nav/admin_home_on.gif"
    }
    function SwapImage1Back() {
        document.image1.src = "${ctx}/i/nav/admin_home_off.gif"
    }
    function SwapImage2() {
        document.image2.src = "${ctx}/i/nav/admin_sponsorapp_on.gif"
    }
    function SwapImage2Back() {
        document.image2.src = "${ctx}/i/nav/admin_sponsorapp_off.gif"
    }
    function SwapImage3() {
        document.image3.src = "${ctx}/i/nav/admin_winnerapp_on.gif"
    }
    function SwapImage3Back() {
        document.image3.src = "${ctx}/i/nav/admin_winnerapp_off.gif"
    }
    function SwapImage4() {
        document.image4.src = "${ctx}/i/nav/admin_game_on.gif"
    }
    function SwapImage4Back() {
        document.image4.src = "${ctx}/i/nav/admin_game_off.gif"
    }
    -->
</script>

<div id="header">
    <span class="links">
        Hello, ${orpheus:getHandle(user)}&nbsp;&nbsp;|&nbsp;&nbsp;
        <a href="${ctx}/server/logout.do" title="Logout">Logout</a>
    </span>
    <a href="${ctx}/server/activeGames.do" title="Home">
        <img src="${ctx}/i/logo.gif" alt="The Ball" width="130" height="65"/>
    </a>
</div>

<div id="nav">
    <ul>
        <li>
            <a href="${cxt}/server/admin/summary.do" title="Admin Home">
                <c:if test="${subbar eq 'summary'}">
                    <img src="${ctx}/i/nav/admin_home_on.gif" alt="Home"/>
                </c:if>
                <c:if test="${subbar ne 'summary'}">
                    <img src="${ctx}/i/nav/admin_home_off.gif" alt="Home"/>
                </c:if>
            </a>
        </li>
        <li>
            <a href="${ctx}/server/admin/pendingSponsors.do" title="Sponsor Approval">
                <c:if test="${subbar eq 'sponsor'}">
                    <img src="${ctx}/i/nav/admin_sponsorapp_on.gif" name="image2"
                         border="0" onMouseOver="SwapImage2()" onMouseOut="SwapImage2()" alt="Sponsor Approval"/>
                </c:if>
                <c:if test="${subbar ne 'sponsor'}">
                    <img src="${ctx}/i/nav/admin_sponsorapp_off.gif" name="image2"
                         border="0" onMouseOver="SwapImage2()" onMouseOut="SwapImage2Back()"
                         alt="Sponsor Approval"/>
                </c:if>
            </a>
        </li>
        <li>
            <a href="${ctx}/server/admin/pendingWinners.do" title="Winner Approval">
                <c:if test="${subbar eq 'winners'}">
                    <img src="${ctx}/i/nav/admin_winnerapp_on.gif" name="image3" border="0" onMouseOver="SwapImage3()"
                         onMouseOut="SwapImage3()" alt="Winner Approval"/>
                </c:if>
                <c:if test="${subbar ne 'winners'}">
                    <img src="${ctx}/i/nav/admin_winnerapp_off.gif" name="image3" border="0" onMouseOver="SwapImage3()"
                         onMouseOut="SwapImage3Back()" alt="Winner Approval"/>
                </c:if>
            </a>
        </li>
        <li>
            <a href="${ctx}/server/admin/viewGames.do" title="Game Management">
                <c:if test="${subbar eq 'games'}">
                    <img src="${ctx}/i/nav/admin_game_on.gif" name="image4" border="0"
                         onMouseOver="SwapImage4()" onMouseOut="SwapImage4()" alt="Game Management"/>
                </c:if>
                <c:if test="${subbar ne 'games'}">
                    <img src="${ctx}/i/nav/admin_game_off.gif" name="image4" border="0"
                         onMouseOver="SwapImage4()" onMouseOut="SwapImage4Back()" alt="Game Management"/>
                </c:if>
            </a>
        </li>
        <li>
            <img border="0" src="${ctx}/i/nav/admin_endbar.gif" width="208" height="30" alt=""/>
        </li>
    </ul>
    <c:if test="${subbar eq 'summary' or subbar eq 'games'}">
        <ul class="subbar admin">
            <c:import url="./subbar/${subbar}.jsp"/>
        </ul>
    </c:if>
</div>
