
public class Writeback extends Stage{
	Mux m14;
	int Mux14Output;
	Bin Mux12Output;
	public Writeback(Firmware m) {
		super(m);
		inbuff_size=4;//rd|ALUResult|Readdata|CV
		outbuff_size=0;//Will this break it?
		m14=new Mux();
	}
	public void writeback(){
		System.out.println("\n\nWB STAGE\n\n");
		
		Bin Readdata = getIBuffSeg(2);
		Bin ALUResult = getIBuffSeg(1);
		Mux12Output = getIBuffSeg(0);
		Bin control_vector = getIBuffSeg(3);
		m14.setPort0(ALUResult.getArray());
		m14.setPort1(Readdata.getArray());
		m14.setSelect(control_vector.getArray()[1]);
		Mux14Output=Bin.bin_toDec(m14.getOutput());
		
		//Writeback if needed
		if(control_vector.getArray()[0]==1){
			getMem().overwrite_register(Mux12Output.evaluate(), Mux14Output);
		}
		
		//Display EX outputs
		System.out.println("\nData Ports :");
		System.out.println("N/A");
		
		System.out.println("\nRelevant Data Paths: ");
		System.out.print("	Multiplexer 14 Output : " + Mux14Output + "\n");
		
		System.out.println("\nRelevant Control Vector Components: ");
		System.out.print("	MemToReg : " + control_vector.getArray()[0] + "\n	RegToWrite : " + control_vector.getArray()[1] + "\n");
	
	}
	public int getMux14Output(){
		return Mux14Output;
	}
	public int getMux12Output(){
		return Mux12Output.evaluate();
	}
}
