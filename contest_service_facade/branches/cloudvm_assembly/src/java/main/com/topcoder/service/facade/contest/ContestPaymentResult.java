package com.topcoder.service.facade.contest;

import com.topcoder.service.payment.PaymentResult;
import com.topcoder.service.studio.ContestData;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * This class contains payment result and contest data. Its instances are
 * created in reply to processing contest payment.
 *
 * @author Margarita
 * @version 1.0
 * @since BUGR-1494
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contestPaymentResult", propOrder =  {
    "paymentResult", "contestData"}
)
public class ContestPaymentResult implements Serializable {
    /**
     * Generated field.
     */
    private static final long serialVersionUID = -1232655979944585284L;

    /**
     * Payment result instance.
     */
    private PaymentResult paymentResult;

    /**
     * Contest data for particular contest.
     */
    private ContestData contestData;

    /**
     * Empty constructor.
     */
    public ContestPaymentResult() {
    }

    /**
     * Returns the payment result.
     *
     * @return payment result
     */
    public PaymentResult getPaymentResult() {
        return paymentResult;
    }

    /**
     * Sets the new value of payment result.
     *
     * @param paymentResult
     *            the new value
     */
    public void setPaymentResult(PaymentResult paymentResult) {
        this.paymentResult = paymentResult;
    }

    /**
     * Returns the contest data.
     *
     * @return the contest data
     */
    public ContestData getContestData() {
        return contestData;
    }

    /**
     * Sets the new value of contest data
     *
     * @param contestData
     */
    public void setContestData(ContestData contestData) {
        this.contestData = contestData;
    }
}
