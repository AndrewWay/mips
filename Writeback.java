
public class Writeback extends Stage{
	Mux m14;
	int mux14_output;
	Bin Mux12Output;
	public Writeback(Firmware m) {
		super(m);
		inbuff_size=3;//rd|ALUResult|Readdata
		outbuff_size=0;//Will this break it?
		m14=new Mux();
	}
	public void writeback(){
		Bin Readdata = getIBuffSeg(2);
		Bin ALUResult = getIBuffSeg(1);
		System.out.println("ALURESULT IN WB "+ALUResult.evaluate());
		Mux12Output = getIBuffSeg(0);
		Bin control_vector = getMem().getControlVector();
		m14.setPort0(ALUResult.getArray());
		m14.setPort1(Readdata.getArray());
		m14.setSelect(control_vector.getArray()[8]);
		mux14_output=Bin.bin_toDec(m14.getOutput());
		System.out.println("MUX14 OUTPUT: "+mux14_output);
		
		//Writeback if needed
		if(control_vector.getArray()[7]==1){
			getMem().overwrite_register(Mux12Output.evaluate(), mux14_output);
		}
	}
	public int getMux14Output(){
		return mux14_output;
	}
	public int getMux12Output(){
		return Mux12Output.evaluate();
	}
}
