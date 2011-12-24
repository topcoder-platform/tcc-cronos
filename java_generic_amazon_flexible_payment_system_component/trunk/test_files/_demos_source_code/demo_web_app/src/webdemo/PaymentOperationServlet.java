package webdemo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.topcoder.payments.amazonfps.AmazonFlexiblePaymentManagementException;
import com.topcoder.payments.amazonfps.AmazonPaymentManager;
import com.topcoder.payments.amazonfps.AmazonPaymentManagerImpl;

/**
 * Servlet implementation class PaymentOperationServlet
 */
public class PaymentOperationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentOperationServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // parse request parameters
        String operation = request.getParameter("operation");
        String paymentIdStr = request.getParameter("paymentId");

        long paymentId = Long.parseLong(paymentIdStr);

        // initiate authorization request
        try {
            AmazonPaymentManager amazonPaymentManager = new AmazonPaymentManagerImpl();
            if (operation.equals("0")) {
                amazonPaymentManager.cancelPayment(paymentId);
            } else if (operation.equals("1")) {
                amazonPaymentManager.settlePayment(paymentId);
            } else if (operation.equals("2")) {
                amazonPaymentManager.refundPayment(paymentId);
            }
        } catch (AmazonFlexiblePaymentManagementException e) {
            e.printStackTrace();
        }

        response.sendRedirect("operation_completed3.jsp");
	}
}
