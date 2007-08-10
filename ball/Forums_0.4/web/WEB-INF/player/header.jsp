<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="user" value="${sessionScope['user_profile']}"/>
<script type="text/javascript">
    <!--
function SwapImage1() {
  document.image1.src="${ctx}/i/nav/player_home_on.gif"
}
function SwapImage1Back() {
  document.image1.src="${ctx}/i/nav/player_home_off.gif"
}
function SwapImage2() {
  document.image2.src="${ctx}/i/nav/player_active_on.gif"
}
function SwapImage2Back() {
  document.image2.src="${ctx}/i/nav/player_active_off.gif"
}
function SwapImage3() {
  document.image3.src="${ctx}/i/nav/player_mygames_on.gif"
}
function SwapImage3Back() {
  document.image3.src="${ctx}/i/nav/player_mygames_off.gif"
}
function SwapImage4() {
  document.image4.src="${ctx}/i/nav/player_faq_on.gif"
}
function SwapImage4Back() {
  document.image4.src="${ctx}/i/nav/player_faq_off.gif"
}
function SwapImage5() {
  document.image5.src="${ctx}/i/nav/player_myaccount_on.gif"
}
function SwapImage5Back() {
  document.image5.src="${ctx}/i/nav/player_myaccount_off.gif"
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
                <img src="${ctx}/i/nav/player_home_off.gif" name="image1" border="0" alt="Home"
                     onMouseOver="SwapImage1()" onMouseOut="SwapImage1Back()"/>
            </a>
        </li>
        <li>
            <a href="${ctx}/server/player/activeGames.do" title="Active Games">
                <c:if test="${subbar eq 'active'}">
                    <img src="${ctx}/i/nav/player_active_on.gif" alt="Active Games" name="image2"
                         onMouseOver="SwapImage2()" onMouseOut="SwapImage2()"/>
                </c:if>
                <c:if test="${subbar ne 'active'}">
                    <img src="${ctx}/i/nav/player_active_off.gif" name="image2" border="0" alt="Active Games"
                         onMouseOver="SwapImage2()" onMouseOut="SwapImage2Back()"/>
                </c:if>
            </a>
        </li>
        <li>
            <a href="${ctx}/server/player/myGames.do" title="My Games">
                <c:if test="${subbar eq 'mygames'}">
                    <img src="${ctx}/i/nav/player_mygames_on.gif" name="image3" alt="My Games"
                         onMouseOver="SwapImage3()" onMouseOut="SwapImage3()"/>
                </c:if>
                <c:if test="${subbar ne 'mygames'}">
                    <img src="${ctx}/i/nav/player_mygames_off.gif" name="image3" border="0" alt="My Games"
                         onMouseOver="SwapImage3()" onMouseOut="SwapImage3Back()"/>
                </c:if>
            </a>
        </li>
        <li>
            <a href="${ctx}/server/player/showPlayerFAQ.do" title="Player FAQ">
                <c:if test="${subbar eq 'faq'}">
                    <img src="${ctx}/i/nav/player_faq_on.gif" name="image4" alt="Player FAQ"
                         onMouseOver="SwapImage4()" onMouseOut="SwapImage4()"/>
                </c:if>
                <c:if test="${subbar ne 'faq'}">
                    <img src="${ctx}/i/nav/player_faq_off.gif" name="image4" border="0" alt="Player FAQ"
                         onMouseOver="SwapImage4()" onMouseOut="SwapImage4Back()"/>
                </c:if>
            </a>
        </li>
        <li>
            <a href="${ctx}/server/player/myAccount.do" title="My Account">
                <c:if test="${subbar eq 'myaccount'}">
                    <img src="${ctx}/i/nav/player_myaccount_on.gif" name="image5" alt="My Account"
                         onMouseOver="SwapImage5()" onMouseOut="SwapImage5()"/>
                </c:if>
                <c:if test="${subbar ne 'myaccount'}">
                    <img src="${ctx}/i/nav/player_myaccount_off.gif" name="image5" border="0" alt="My Account"
                         onMouseOver="SwapImage5()" onMouseOut="SwapImage5Back()"/>
                </c:if>
            </a>
        </li>
        <li>
            <img src="${ctx}/i/nav/player_endbar.gif" width="249" height="30" border="0"/>
        </li>
    </ul>
</div>
