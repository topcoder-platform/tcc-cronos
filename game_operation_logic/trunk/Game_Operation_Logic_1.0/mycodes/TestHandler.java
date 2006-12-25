import java.util.Map;

import org.w3c.dom.Element;

import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

public class TestHandler implements Handler {

	public TestHandler(String pluginName, Map propertyNameMap) {
		System.out.print(pluginName);
	}

	public TestHandler(Element element) {
		System.out.println(element.toString());
	}

	public String execute(ActionContext context)
			throws HandlerExecutionException {
		System.out.print(context);
		return null;
	}

}
