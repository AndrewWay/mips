public class Fetch extends Stage{

	private Bin ir;
 	public Fetch(Firmware m) {
		super(m);
		ir=new Bin(getMem().getInstSize());
		this.inbuff_size=1;
		this.outbuff_size=2;
		createBuffers();
	}
	public void fetch(Bin PC){
		//Increment PC
		//incrementPC();//TODO Uncomment this and get it working
		//Obtain the instruction located at PC
		Firmware m = getMem();
		int[] instruction = m.getInstMem()[PC.evaluate()];
		setIR(instruction);
		//Load that instruction into buffer
		
		loadBuffer(0,ir);
		loadBuffer(1,PC);
	}
	public Bin getIR(){
		return ir;
	}
	public void setIR(int[] a){
		ir.overwrite(a);
	}
}
