<%@ page import="java.util.Map" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.util.HashMap" %>
<%
    ServletContext servletContext = pageContext.getServletContext();
    servletContext.log("paypalConfirmation.jsp : Got following Return Post request from PayPal : ["
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
    servletContext.log("paypalConfirmation.jsp : Body of Return Post request from PayPal : [" + body + "]");

    Map<String, String> paramValues = new HashMap<String, String>();
    String[] params = body.toString().split("&");
    for (int i = 0; i < params.length; i++) {
        String param = params[i];
        String[] paramData = param.split("=");
        if (paramData.length > 1) {
            paramValues.put(paramData[0], URLDecoder.decode(paramData[1], "UTF-8"));
        }
    }

    final String paymentType = paramValues.get("USER3");
    if ("Submission".equals(paymentType)) {
        final String pageURL = servletContext.getInitParameter("view-submissions-confirmation-page");
        servletContext.log("paypalConfirmation.jsp : Redirecting to View Submissions Widget : [" + pageURL + "]");
        response.sendRedirect(pageURL + '?' + body);
    } else if ("PurchasedSubmissions".equals(paymentType)) {
        final String pageURL = servletContext.getInitParameter("view-submissions-purchased-confirmation-page");
        servletContext.log("paypalConfirmation.jsp : Redirecting to View Submissions Widget : [" + pageURL + "]");
        response.sendRedirect(pageURL + '?' + body);
    } else if ("Launch".equals(paymentType)) {
        final String pageURL = servletContext.getInitParameter("launch-project-confirmation-page");
        servletContext.log("paypalConfirmation.jsp : Redirecting to Launch Project Widget : [" + pageURL + "]");
        response.sendRedirect(pageURL + '?' + body);
    } else {
        servletContext.log("paypalConfirmation.jsp : Payment type is not valid. Responding with status code "
                           + HttpServletResponse.SC_INTERNAL_SERVER_ERROR + " : [" + paymentType + "]");
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Invalid payment type");
    }
%>