import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

public class Bin {
	private int bin_size;
	private int[] bin;
	
	public Bin(int bin_size){
		this.bin_size = bin_size;
		bin=new int[bin_size];
	}
	public Bin(int[] binArr){
		this.bin_size = Array.getLength(binArr);
		bin=binArr;
	}
	public Bin(int val,int flag){//TODO Fix this.
		this.bin=dec_toBin(val);
		this.bin_size=Array.getLength(this.bin);
	}
	public int getBinSize(){
		return bin_size;
	}
	public int[] getArray(){
		//Return the integer array containing Bin binary values
		return bin;
	}
	public int evaluate(){
		return bin_toDec(bin);
	}
	public Bin extract(int start,int end){
		Bin subBin = new Bin(end-start+1);
		int[] binVal = getArray();
		for(int i=0;i<subBin.getBinSize();i++){
			subBin.input(i,binVal[start+i]);
		}
		return subBin;	
	}
	public void overwrite_section(int start,Bin newBin){
		int[] newBinArray = newBin.getArray();
		if(start+newBin.getBinSize()>this.getBinSize()){
			System.out.println("ERROR: newBin cannot overwrite the specified section of the bin");
			System.out.println("Recipient Bin Size: "+getBinSize());
			System.out.println("Aborting section overwrite");
		}
		else{
			for(int i=0;i<newBin.getBinSize();i++){
				this.input(i+start, newBinArray[i]);
			}
		}
	}
	public void clearBin(){
		bin=new int[bin_size];
	}
	public String disp(){
		String output = "";
		for(int i=0;i<bin_size;i++){
			output+=Integer.toString(bin[i]);
		}
		return output+"|";
	}
	public String dispVal(){
		String output=Integer.toString(evaluate());
		return output;
	}
	public void input(int i,int val){
		//Update a section of a Bin with 0 or 1
		if(val == 0 || val == 1){
			bin[i]=val;
		}
		else
		{
			//TODO Throw InvalidBinInputException
			System.out.println("ERROR: INVALID INPUT");
		}
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
	public void overwrite(int[] newVal){
		//Overwrite the entire bin with a new integer array
		int newBinSize = Array.getLength(newVal);
		if(Array.getLength(newVal)==bin_size){
			bin=newVal;
		}
		else if(newBinSize<bin_size){
			clearBin();
			for(int i=bin_size-newBinSize;i<bin_size;i++){
				bin[i]=newVal[i-(bin_size-newBinSize)];
			}
		}
		else{
			//TODO Throw BinOverFlowException
			System.out.println("ERROR: Input Value too large for Bin");
			
		}
	}	
	public void dec_overwrite(int r){
		System.out.println("INPUT REG VALUE "+r);
		int[] newVal = dec_toBin(r);
		System.out.println(Arrays.toString(newVal));
		int newBinSize = Array.getLength(newVal);
		if(newBinSize==bin_size){
			bin=newVal;
		}
		else if(newBinSize<bin_size){
			clearBin();
			for(int i=bin_size-newBinSize;i<bin_size;i++){
				bin[i]=newVal[i-(bin_size-newBinSize)];
			}
		}
		else{
			//TODO Throw BinOverFlowException
			System.out.println("ERROR: Input Value too large for Bin");
			
		}
		System.out.println("RESULTING BIN VAL: "+bin_toDec(bin));
	}
	
	public static int[] addBins(Bin first, Bin second) {
		int dec=first.evaluate() + second.evaluate();
		return dec_toBin(dec);
	}
	
	public static int[] dec_toBin(int dec){
		
		//determine array size needed
		boolean negate=false;
		if(dec<0){
			negate=true;
			dec=Math.abs(dec);
		}
		int n=0;
		int binGuess = (int) Math.pow(2, n)-1;
		while(binGuess < dec){
			n=n+8;
			binGuess = (int) Math.pow(2, n)-1;
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
		if(negate){
			bin=negate(bin);
		}
		return bin;
	}
	public static int bin_toDec(int[] bin){
		int size = Array.getLength(bin);
		int decimal = 0;
		int parity;
		if(bin[0]==1){
			parity=-1;
			bin=negate(bin);
		}
		else{
			parity=1;
		}
		for(int i=0;i<=size-1;i++){
			//System.out.println(bin[i]);
			int term = (int) Math.pow(2, size-1-i);
			decimal=decimal+bin[i]*term;//Does not account for negative numbers
		}
		return parity*decimal;
	}
	public static int[] negate(int[] b){
			//bit inversion
			for(int i=0;i<Array.getLength(b);i++){
				if(b[i]==0){
					b[i]=1;
				}
				else if(b[i]==1){
					b[i]=0;
				}
			}
			//add 1
			int remainder=1;
			int i=Array.getLength(b)-1;
			while(remainder!=0&&i>=0){
				if(b[i]==0){
					b[i]=1;
					remainder=0;
				}
				else if(b[i]==1){
					b[i]=0;
				}
				i--;
			}
			if(remainder==0&&i<0){
				System.out.println("ERROR: OVERFLOW IN SIGN FLIP");
			}
		return b;
	}
}