
public class Fetch extends Stage{

	public Fetch(int rs, int rn, int ibs, int obs) {
		super(rs, rn, ibs, obs);
		// TODO Auto-generated constructor stub
	}
	public static void fetch(){
		//Increment PC
		incrementPC();
		//Obtain the instruction located at PC
		ir = inst_memory[bin_toDec(PC)];
		//Load that instruction into buffer
		IF_ID=concat(ir,PC);
	}
}
