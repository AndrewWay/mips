import java.util.Arrays;


public class Fetch extends Stage{

	private Bin ir;
 	public Fetch(int ibs, int obs,Memory m) {
		super(ibs, obs, m);
		ir=new Bin(getMem().getInstSize());
	}
	public void fetch(Bin PC){
		//Increment PC
		//incrementPC();//TODO Uncomment this and get it working
		//Obtain the instruction located at PC
		Memory m = getMem();
		int[] instruction = m.getInstMem()[PC.evaluate()];
		setIR(instruction);
		//Load that instruction into buffer
		
		loadBuffer(0,ir.getBinSize()-1,ir);
		loadBuffer(ir.getBinSize(),PC.getBinSize()-1,PC);
	}
	public Bin getIR(){
		return ir;
	}
	public void setIR(int[] a){
		ir.overwrite(a);
	}
}
