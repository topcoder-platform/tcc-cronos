<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/paging" prefix="p" %>
<%@ taglib uri="/dropdown" prefix="dd" %>
<%@ taglib uri="/orpheus_admin" prefix="admin" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="gameId" value="${param['gameId']}" scope="page"/>
<c:set var="blockId" value="${param['blockId']}" scope="page"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Add Slot</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <script type="text/javascript" xml:space="preserve" src="${ctx}/js/forms.js"></script>
    <script type="text/javascript">
        function addSlot() {
            var sponsor = document.getElementById('sponsorId');
            var domain = document.getElementById('domainId');
            var image = document.getElementById('imageId');
            var bid = document.getElementById('maxBid');
            var sponsorId = sponsor.options[sponsor.selectedIndex].value;
            var domainId = domain.options[domain.selectedIndex].value;
            var imageId = image.options[image.selectedIndex].value;
            if (sponsor.selectedIndex == 0) {
                alert('Plase, select a sponsor');
                return;
            }
            if (domain.selectedIndex == 0) {
                alert('Plase, select a domain');
                return;
            }
            if (image.selectedIndex == 0) {
                alert('Plase, select an image');
                return;
            }
            if (bid.value == null || bid.value == '') {
                alert('Plase, enter a bid value');
                return;
            }
            document.AddSlotForm.submit();
            self.close();
        }
    </script>
</head>

<body>
<form action="${ctx}/server/admin/addSlot.do" method="POST" name="AddSlotForm" id="AddSlotForm" target="Orpheus">
    <input type="hidden" name="gameId" value="${gameId}"/>
    <input type="hidden" name="blockId" value="${blockId}"/>
    <table border="0" cellpadding="0" cellspacing="0">
        <tr>
            <th width="20">Sponsor</th>
            <th width="20%">Domain</th>
            <th width="20%">Image</th>
            <th width="20%">Bid</th>
            <th width="20%">Action</th>
        </tr>
        <tr>
            <dd:dropdownlist>
                <td>
                    <dd:dropdown id="sel0" classStyle="inputBox" prompt="Select Sponsor"
                                 name="sponsorId" data="${sponsors}"/>
                </td>
                <td>
                    <dd:dropdown id="sel1" classStyle="inputBox" prompt="Select Domain"
                                 name="domainId" data="${domains}"/>
                </td>
                <td>
                    <dd:dropdown id="sel2" classStyle="inputBox" prompt="Select Image"
                                 name="imageId" data="${images}"/>
                </td>
            </dd:dropdownlist>
            <td>
                $<input name="maxBid" type="text" id="maxBid" class="inputBox" size="9"
                        maxlength="9" value="${param['maxBid']}"
                        onkeypress="return acceptDigit(event);"/>
            </td>
            <td>
                <a href="#" onclick="addSlot();return false;">
                    <img src="${ctx}/i/b/btn_admin_addslot.gif" width="55" height="15" class="btn"
                         alt="Add Slot"/>
                </a>
            </td>
        </tr>
    </table>
</form>
</body>
</html>