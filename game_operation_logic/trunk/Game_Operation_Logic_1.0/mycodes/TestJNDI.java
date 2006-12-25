import javax.naming.Context;

import com.orpheus.game.persistence.GameDataHome;
import com.orpheus.game.persistence.GameDataLocalHome;
import com.topcoder.naming.jndiutility.JNDIUtils;

public class TestJNDI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new TestJNDI().test();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void test() throws Exception{
		Context context = JNDIUtils.getContext("default");
		context.bind("local",new GameDataLocalHome());
		context.bind("remote",new GameDataHome());
		
		
		Object obj = JNDIUtils.getObject(context,"local");
		
		context.unbind("local");
		context.unbind("remote");
	}

}
