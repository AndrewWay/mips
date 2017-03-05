
public class Fetch extends Stage{

	private int[] ir;
 	public Fetch(int ibs, int obs,Memory m) {
		super(ibs, obs, m);
	}
	public void fetch(Bin PC){
		//Increment PC
		//incrementPC();//TODO Uncomment this and get it working
		//Obtain the instruction located at PC
		Memory m = getMem();
		int[] instruction = m.getInstMem()[PC.bin_toDec()];
		setIR(instruction);//TODO Change to inst_memory[bin_toDec(PC)] after making PC a binary array
		//Load that instruction into buffer
		loadBuffer();
	}
	public int[] getIR(){
		return ir;
	}
	public void setIR(int[] a){
		ir=a;
	}
	public void loadBuffer(){
		int[] output = Arrays.concat(ir,PC);
		out_buff.overwrite()
	}
}
