public class Fetch extends Stage{

	private Bin ir, PC;
	private Mux MUX3;
 	public Fetch(Firmware m, int[] pval) {
		super(m);
		ir=new Bin(getMem().getInstSize());
		PC=new Bin(pval);
		MUX3=new Mux();
		MUX3.setPort0(PC.getArray());
		this.inbuff_size=1;
		this.outbuff_size=2;
		createBuffers();
	}
	public void fetch(){
		PC.overwrite(MUX3.getOutput()); //Get the PC register value
		
		MUX3.setPort0(Bin.dec_toBin(PC.evaluate()+4)); 		//Increment PC

		System.out.println("!!!!!!!!!!!!!!!" + MUX3.getPort0().evaluate());
		
		int i = PC.getArray()[33];
		
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
