<%@ page import="com.topcoder.widgets.bridge.PayPalResponseListener" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.net.URLDecoder" %>
<%
    pageContext.getServletContext().log("paypalSilentPost.jsp : Got following Silent Post request from PayPal : ["
                                        + request.getQueryString() + "]");
    int c;
    StringBuilder body = new StringBuilder();
    byte[] b = new byte[4096];
    ServletInputStream inputStream = request.getInputStream();
    while ((c = inputStream.read(b)) >= 0) {
        if (c > 0) {
            body.append(new String(b, 0, c));
        }
    }
    pageContext.getServletContext().log("paypalSilentPost.jsp : Body of Silent Post request from PayPal : [" + body + "]");

    Map<String, String> paramValues = new HashMap<String, String>();
    String[] params = body.toString().split("&");
    for (int i = 0; i < params.length; i++) {
        String param = params[i];
        String[] paramData = param.split("=");
        paramValues.put(paramData[0], URLDecoder.decode(paramData[1], "UTF-8"));
    }


    boolean successful = false;
    // Verify that the original session ID matches the current session ID
    // Persist the submitted PayPal order ID to session only if session IDs match
    // TODO : It would be nice to verify that the request originated from PayPal
    final String paypalOrderId = paramValues.get("PNREF");
    final String originalSessionId = paramValues.get("USER1");
    final String originalPrincipalName = paramValues.get("USER2");
    final String originalPaymentType = paramValues.get("USER3");
    final String originalContestId = paramValues.get("USER4");
    final String originalPaymentAmount = paramValues.get("USER5");

    PayPalResponseListener paypalListener
            = (PayPalResponseListener) pageContext.getServletContext().getAttribute("paypalListener");
    successful = paypalListener.savePayPalOrder(originalSessionId, originalPrincipalName, originalContestId,
                                                originalPaymentType, originalPaymentAmount, paypalOrderId);
    if (successful) {
        pageContext.getServletContext().log("paypalSilentPost.jsp : Paypal Silent Post request has been processed successfully ; Responding with status code " + HttpServletResponse.SC_OK);
        response.setStatus(HttpServletResponse.SC_OK);
    } else {
        pageContext.getServletContext().log("paypalSilentPost.jsp : failed to process Paypal Silent Post request successfully : Responding with status code " + HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
%>
