import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ConfigManager.getInstance().getString("com.topcoder.web.frontcontroller","action_selector");
		} catch (UnknownNamespaceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
