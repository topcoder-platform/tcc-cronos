<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<h3>Account Changes</h3>
<ul>
  <li><a href="${ctx}/server/player/myAccount-changePassword.do">Change my password</a></li>
  <li><a href="${ctx}/server/player/myAccount-updateProfile.do">Update my profile</a></li>
</ul>
<h3><br/>Preferences</h3>
<ul>
  <li><a href="${ctx}/server/player/myAccount-setSound.do">Set my sound preferences</a></li>
  <li><a href="${ctx}/server/player/myAccount-setEmailNotifications.do">Set my e-mail notifications</a></li>
</ul>