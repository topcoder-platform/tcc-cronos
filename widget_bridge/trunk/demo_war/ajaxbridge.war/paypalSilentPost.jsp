<%@ page import="com.topcoder.widgets.bridge.PayPalResponseListener" %>
<%
    pageContext.getServletContext().log("paypalSilentPost.jsp : Got following Silent Post request from PayPal : ["
                                        + request.getQueryString() + "]");
    System.out.println("paypalSilentPost.jsp : Got following Silent Post request from PayPal : ["
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
    System.out.println("paypalSilentPost.jsp : Body of Silent Post request from PayPal : [" + body + "]");

    boolean successful = false;
    if (session != null) {
        // Verify that the original session ID matches the current session ID
        // Persist the submitted PayPal order ID to session only if session IDs match
        // TODO : It would be nice to verify that the request originated from PayPal
        final String paypalOrderId = request.getParameter("PNREF");
        final String originalSessionId = request.getParameter("USER1");
        final String originalPrincipalName = request.getParameter("USER2");
        final String originalPaymentType = request.getParameter("USER3");
        final String originalContestId = request.getParameter("USER4");
        final String originalPaymentAmount = request.getParameter("USER5");
        PayPalResponseListener paypalListener
            = (PayPalResponseListener) session.getServletContext().getAttribute("paypalListener");
        successful = paypalListener.savePayPalOrder(originalSessionId, originalPrincipalName, originalContestId,
                                                    originalPaymentType, originalPaymentAmount, paypalOrderId);
    }

    if (successful) {
        pageContext.getServletContext().log("paypalSilentPost.jsp : Paypal Silent Post request has been processed successfully ; Responding with status code " + HttpServletResponse.SC_OK);
        System.out.println("paypalSilentPost.jsp : Paypal Silent Post request has been processed successfully ; Responding with status code " + HttpServletResponse.SC_OK);
        response.setStatus(HttpServletResponse.SC_OK);
    } else {
        pageContext.getServletContext().log("paypalSilentPost.jsp : failed to process Paypal Silent Post request successfully : Responding with status code " + HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        System.out.println("paypalSilentPost.jsp : failed to process Paypal Silent Post request successfully : Responding with status code " + HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
%>
