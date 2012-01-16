package demo;

import java.util.Date;
import java.util.List;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.payments.amazonfps.model.PaymentEvent;
import com.topcoder.payments.amazonfps.subscribers.jms.JMSAmazonPaymentEventReceiver;

public class TestReceiver {
    public static void main(String[] args) throws Exception {
        // load configuration
        ConfigurationFileManager configurationFileManager = new ConfigurationFileManager();
        ConfigurationObject configuration = configurationFileManager.getConfiguration("JMSReceiver");
        configuration = configuration.getChild("JMSReceiver");

        // create receiver
        JMSAmazonPaymentEventReceiver receiver = new JMSAmazonPaymentEventReceiver(configuration);

        System.out.print("Start reading payment events");

        // read payment events forever
        long i = 0;
        while (true) {
            List<PaymentEvent> events = receiver.receivePaymentEvents(1000L);
            if (!events.isEmpty()) {
                System.out.println("New event at " + (new Date()));
                for (PaymentEvent event : events) {
                    String message = String.format("%d: %s", i+1, event.toString());
                    System.out.println(message);
                    i++;
                }
            }

        }
    }
}
