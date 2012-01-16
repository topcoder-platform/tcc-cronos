package webdemo;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.topcoder.payments.amazonfps.AmazonFlexiblePaymentManagementException;
import com.topcoder.payments.amazonfps.AmazonPaymentManager;
import com.topcoder.payments.amazonfps.AmazonPaymentManagerImpl;

/**
 * Servlet implementation class AuthorizationReadyServlet
 */
public class AuthorizationReadyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthorizationReadyServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    @SuppressWarnings("unchecked")
        Map<Object, Object> requestParams = request.getParameterMap();

	    try {
    	    AmazonPaymentManager amazonPaymentManager = new AmazonPaymentManagerImpl();
    	    amazonPaymentManager.handleRequestFromCoBrandedService(requestParams);
	    } catch (AmazonFlexiblePaymentManagementException e) {
            e.printStackTrace();
        }

	    String paymentIdParam = request.getParameter("paymentId");
	    String tokenIdParam = request.getParameter("tokenID");
	    String callerReference = request.getParameter("callerReference");
	    String authorizationIdStr = callerReference.substring(callerReference.indexOf('_') + 1);
	    response.sendRedirect("operation_completed.jsp?paymentId=" + paymentIdParam
	            + "&authorizationId=" + authorizationIdStr + "&tokenID=" + tokenIdParam);
	}
}
