
public class TestArrayCopy {
	public static void main(String[] args){
		String[] src = new String[]{};
		String[] tgt = new String[src.length];
		
		System.arraycopy(src,0,tgt,0,src.length);
		
	}
}
