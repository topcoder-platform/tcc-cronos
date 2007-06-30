<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="user" value="${sessionScope['user_profile']}"/>
<script type="text/javascript">
    <!--
function SwapImage1() {
  document.image1.src = "${ctx}/i/nav/sponsor_home_on.gif"
    }
    function SwapImage1Back() {
        document.image1.src = "${ctx}/i/nav/sponsor_home_off.gif"
    }
    function SwapImage2() {
        document.image2.src = "${ctx}/i/nav/sponsor_auctions_on.gif"
    }
    function SwapImage2Back() {
        document.image2.src = "${ctx}/i/nav/sponsor_auctions_off.gif"
    }
    function SwapImage3() {
        document.image3.src = "${ctx}/i/nav/sponsor_faq_on.gif"
    }
    function SwapImage3Back() {
        document.image3.src = "${ctx}/i/nav/sponsor_faq_off.gif"
    }
-->
</script>

<div id="header">
    <span class="links">
        Hello, ${orpheus:getHandle(user)}&nbsp;&nbsp;|&nbsp;&nbsp;
        <a href="${ctx}/server/logout.do" title="Logout">Logout</a>&nbsp;&nbsp;|&nbsp;&nbsp;
        <a href="${ctx}/public/credits.jsp" title="Credits">Credits</a>&nbsp;&nbsp;|&nbsp;&nbsp;
        <a href="${ctx}/public/help_howtoplay.jsp" title="Help">Help</a>
    </span>
    <a href="${ctx}/server/activeGames.do" title="Home">
        <img src="${ctx}/i/logo.gif" alt="The Ball" width="130" height="65"/>
    </a>
</div>

<div id="nav">
    <ul>
        <li>
            <a href="${ctx}/server/activeGames.do" title="Home">
                <img src="${ctx}/i/nav/sponsor_home_off.gif" name="image1" border="0" onMouseOver="SwapImage1()"
                     onMouseOut="SwapImage1Back()" alt="Home"/>
            </a>
        </li>
        <li>
            <a href="${ctx}/server/sponsor/openAuctions.do" title="Open Auctions">
                <c:if test="${subbar eq 'auctions'}">
                    <img src="${ctx}/i/nav/sponsor_auctions_on.gif" name="image2" border="0" alt="Open Auctions"/>
                </c:if>
                <c:if test="${subbar ne 'auctions'}">
                    <img src="${ctx}/i/nav/sponsor_auctions_off.gif" name="image2" border="0" onMouseOver="SwapImage2()"
                         onMouseOut="SwapImage2Back()" alt="Open Auctions"/>
                </c:if>
            </a>
        </li>
        <li>
            <a href="${ctx}/server/sponsor/showFAQ.do" title="Bidding FAQ">
                <c:if test="${subbar eq 'faq'}">
                    <img src="${ctx}/i/nav/sponsor_faq_on.gif" name="image3" border="0" alt="Bidding FAQ"/>
                </c:if>
                <c:if test="${subbar ne 'faq'}">
                    <img src="${ctx}/i/nav/sponsor_faq_off.gif" name="image3" border="0" onMouseOver="SwapImage3()"
                         onMouseOut="SwapImage3Back()" alt="Bidding FAQ"/>
                </c:if>
            </a>
        </li>
        <li><img border="0" src="${ctx}/i/nav/sponsor_endbar.gif" width="446" height="30" alt=""/></li>
    </ul>
</div>
