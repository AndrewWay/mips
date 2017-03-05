import java.lang.reflect.Array;
import java.util.Random;

public class Bin {
	private int bin_size;
	private int[] bin;
	
	public Bin(int bin_size){
		this.bin_size = bin_size;
		bin=new int[bin_size];
	}
	
	public String disp(){
		String output = "";
		for(int i=0;i<bin_size;i++){
			output+="|"+Integer.toString(bin[i]);
		}
		output+="| val= "+Integer.toString(evaluate());
		return output;
	}
	public void overwrite(int[] r){
		if(Array.getLength(r)==bin_size){
			bin=r;
		}
		else{
			//TODO Throw IncompatibleBinException
			System.out.println("ERROR: INPUT NOT COMPATIBLE");
		}
	}
	public void input(int i,int val){
		if(val == 0 || val == 1){
			bin[i]=val;
		}
		else
		{
			//TODO Throw InvalidBinInputException
			System.out.println("ERROR: INVALID INPUT");
		}
	}
	public int[] get(){
		return bin;
	}
	public void randomize(){
		Random rand = new Random();
		for(int i=0;i<bin_size;i++){
			int n = rand.nextInt(99);
			if(n>=50){
				bin[i]=1;
			}
			else
			{
				bin[i]=0;
			}
		}
	}
	public int evaluate(){
		return bin_toDec(bin);
	}
	public static int bin_toDec(int[] bin){
		int size = Array.getLength(bin);
		int decimal = 0;
		for(int i=0;i<=size-1;i++){
			//System.out.println(bin[i]);
			int term = (int) Math.pow(2, size-1-i);
			decimal=decimal+bin[i]*term;//Does not account for negative numbers
		}
		return decimal;
	}
	public static int[] dec_toBin(int dec){
		//determine array size needed
		int n=0;
		int binGuess = (int) Math.pow(2, n);
		while(binGuess < dec){
			n=n+4;
			binGuess = (int) Math.pow(2, n);
		}
		int[] bin = new int[n];
		for(int i=0;i<n;i++){
			int term = (int) Math.pow(2,n-i-1);
			if (term > dec){
				bin[i]=0;
			}
			else{
				bin[i]=1;
				dec=dec-term;
			}
		}
		return bin;
	}
	//TODO Create method for overwriting register with decimal input
}