<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
    <title>Referrals</title>
  </head>
  <body>
  	referrals:
    <table>
      <s:iterator value="referrals">
        <tr>
          <td>handle: <s:property value="handle" /></td>
        </tr>
        <tr>
          <td>rating: <s:property value="rating" /></td>
        </tr>
      </s:iterator>
    </table>
  </body>
</html>
