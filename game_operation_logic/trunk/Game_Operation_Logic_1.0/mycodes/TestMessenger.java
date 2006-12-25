import com.topcoder.message.messenger.MessageAPI;
import com.topcoder.message.messenger.Messenger;
import com.topcoder.message.messenger.MessengerPlugin;

public class TestMessenger {
	public static void main(String[] args) throws Exception {
			String msgPluginName = "mockPlugin"; 
		
		 Messenger messenger = Messenger.createInstance();
		 messenger.registerPlugin("mockPlugin","com.topcoder.message.messenger.MockMessengerPlugin");
         MessengerPlugin plugin = messenger.getPlugin(msgPluginName);
         MessageAPI api = plugin.createMessage();
          api.setParameterValue("property", "value");

         plugin.sendMessage(api);
		
	}

}
