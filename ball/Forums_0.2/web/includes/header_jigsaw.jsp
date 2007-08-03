<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
<!--
    function SwapImage1() {
        document.image1.src = "${ctx}/i/nav/public_home_on.gif"
    }
    function SwapImage1Back() {
        document.image1.src = "${ctx}/i/nav/public_home_off.gif"
    }
    function SwapImage2() {
        document.image2.src = "${ctx}/i/nav/public_howtoplay_on.gif"
    }
    function SwapImage2Back() {
        document.image2.src = "${ctx}/i/nav/public_howtoplay_off.gif"
    }
    function SwapImage3() {
        document.image3.src = "${ctx}/i/nav/public_download_on.gif"
    }
    function SwapImage3Back() {
        document.image3.src = "${ctx}/i/nav/public_download_off.gif"
    }
    function SwapImage4() {
        document.image4.src = "${ctx}/i/nav/public_sponsor_on.gif"
    }
    function SwapImage4Back() {
        document.image4.src = "${ctx}/i/nav/public_sponsor_off.gif"
    }
-->
</script>
<script type="text/javascript" src="${ctx}/js/download.js"></script>

<!-- Header -->
<div id="header">
    <span class="links">
        <c:if test="${sessionScope['user_profile'] eq null}">
            <a href="${ctx}/public/login.jsp" title="Login">Login</a>&nbsp;&nbsp;|&nbsp;&nbsp;
        </c:if>
        <c:if test="${sessionScope['user_profile'] ne null}">
            <a href="${ctx}/server/logout.do" title="Logout">Logout</a>&nbsp;&nbsp;|&nbsp;&nbsp;
            <a href="${ctx}/server/welcome.do" title="Home">Home</a>&nbsp;&nbsp;|&nbsp;&nbsp;
        </c:if>
        <a href="${ctx}/public/credits.jsp" title="Credits">Credits</a>&nbsp;&nbsp;|&nbsp;&nbsp;
        <a href="${ctx}/public/help_howtoplay.jsp" title="Help">Help</a>
    </span>
    <a href="${ctx}/server/activeGames.do" title="Home">
        <img src="${ctx}/i/logo.gif" alt="The Ball" width="130" height="65"/>
    </a>
</div>

<!-- Navigation Public -->
<div id="nav" style="width: 940px; height: 50px;">
    <ul>
        <li>
            <a href="${ctx}/server/activeGames.do" title="Home">
                <img src="${ctx}/i/nav/public_home_off.gif" name="image1" border="0" alt="Home"
                     onMouseOver="SwapImage1()" onMouseOut="SwapImage1Back()"/>
            </a>
        </li>
        <li>
            <a href="${ctx}/public/howtoplay.jsp" title="How to Play">
                <c:if test="${subbar eq 'howto'}">
                    <img src="${ctx}/i/nav/public_howtoplay_on.gif" name="image2" alt="How to Play"
                         onMouseOver="SwapImage2()" onMouseOut="SwapImage2()"/>
                </c:if>
                <c:if test="${subbar ne 'howto'}">
                    <img src="${ctx}/i/nav/public_howtoplay_off.gif" name="image2" border="0" alt="How to Play"
                         onMouseOver="SwapImage2()" onMouseOut="SwapImage2Back()"/>
                </c:if>
            </a>
        </li>
        <li>
            <a href="#" onclick="showDownload('${ctx}');return false;" title="Download">
                <c:if test="${subbar eq 'download'}">
                    <img src="${ctx}/i/nav/public_download_on.gif" name="image3" alt="Download"
                         onMouseOver="SwapImage3()" onMouseOut="SwapImage3()"/>
                </c:if>
                <c:if test="${subbar ne 'download'}">
                    <img src="${ctx}/i/nav/public_download_off.gif" name="image3" border="0" alt="Download"
                         onMouseOver="SwapImage3()" onMouseOut="SwapImage3Back()"/>
                </c:if>
            </a>
        </li>
        <li>
            <a href="${ctx}/public/sponsor.jsp" title="Sponsor a Game">
                <c:if test="${subbar eq 'sponsor'}">
                    <img src="${ctx}/i/nav/public_sponsor_on.gif" name="image4" alt="Sponsor a Game"
                         onMouseOver="SwapImage4()" onMouseOut="SwapImage4()"/>
                </c:if>
                <c:if test="${subbar ne 'sponsor'}">
                    <img src="${ctx}/i/nav/public_sponsor_off.gif" name="image4" border="0" alt="Sponsor a Game"
                         onMouseOver="SwapImage4()" onMouseOut="SwapImage4Back()"/>
                </c:if>
            </a>
        </li>
        <li>
            <img src="${ctx}/i/nav/public_endbar.gif" height="30" width=100% alt="spacer"/>
        </li>
    </ul>
    <c:if test="${not empty subbar}">
        <ul class="subbar public">
            <c:import url="/public/subbar/${subbar}.jsp"/>
        </ul>
    </c:if>
</div>
