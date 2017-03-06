
public class Writeback extends Stage{

	public Writeback(Firmware m) {
		super(m);
		inbuff_size=3;//rd|ALUResult|Readdata
		outbuff_size=0;//Will this break it?
	}
	public void writeback(){
		Bin Readdata = getIBuffSeg(2);
		Bin ALUResult = getIBuffSeg(1);
		Bin Datapath = getIBuffSeg(0);
		int mux14_port1 = Readdata.evaluate();//Make this int or int[]?
		int mux14_port0 = ALUResult.evaluate();
		int mux14_output;
		Bin control_vector = getMem().getControlVector();
		int MemtoReg = control_vector.getArray()[8];
		if(MemtoReg == 1){
			mux14_output = mux14_port1;
		}
		else if(MemtoReg == 0){
			mux14_output = mux14_port0;
		}
		else{
			System.out.println("Invalid Control vector value");
			System.out.println("FATAL SYSTEM ERROR");
		}
	}
}
