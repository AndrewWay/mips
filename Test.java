import java.lang.reflect.Array;

//Use this class for any small tests
public class Test {
	public static void main(String[]args){
		Bin test = new Bin(3,0);
		System.out.println(test.disp());
		int[] tbin = test.getArray();
		System.out.println(test.getBinSize());
		System.out.println(Array.getLength(tbin));
	}
}
