public class Decode extends Stage{
	
	private Bin WriteRegister;
	public Decode(Firmware m) {
		super(m);
		this.inbuff_size=2;
		this.outbuff_size=7;
		createBuffers();
	}
	public void decode(){
		System.out.println("\n\nDE STAGE \n\n");
		
		Bin Opfield = getIBuffSeg(0).extract(0,5);//Op code
		Bin ReadRegister1 = getIBuffSeg(0).extract(6,10);//TODO Check the indices
		Bin ReadRegister2 = getIBuffSeg(0).extract(11,15);//These indices will change whenever you change the number of registers!!
		Bin Datapath = getIBuffSeg(0).extract(16,20);//from rd field in buffer
		Bin offset=getIBuffSeg(0).extract(16, 31);
		Bin ShiftAmount = getIBuffSeg(0).extract(21,25);
		Bin Function = getIBuffSeg(0).extract(26,31);
		Bin PC = getIBuffSeg(1);
		
		int op = Opfield.evaluate();
		int rs = ReadRegister1.evaluate();
		int rt = ReadRegister2.evaluate();
		int rd = Datapath.evaluate();
		int shamt = ShiftAmount.evaluate();
		int funct = Function.evaluate();
		//Read data from registers
		Bin Readdata1=getMem().getRegister(rs);//Data located in register rs
		Bin Readdata2=getMem().getRegister(rt);//Data located in register rt
		//Sign extend the offset
		Bin sign_ext_offset=new Bin(32);
		sign_ext_offset.overwrite_section(16,offset);
		//Set control vector in memory
		getMem().setControlVector(processOP(op));
		//Load the outgoing buffer
		loadBuffer(0,Datapath);
		loadBuffer(1,ReadRegister2);
		loadBuffer(2,sign_ext_offset);
		loadBuffer(3,Readdata2);
		loadBuffer(4,Readdata1);
		loadBuffer(5,PC);
		loadBuffer(6, getMem().getControlVector());
		//System.out.printf("OP %d RS %d RT %d RD %d SHAMT %d FUNCT %d\n", op,rs,rt,rd,shamt,funct);

		//Output for DE stage
		System.out.println("\nData Ports :");
		System.out.println("Inputs");
		System.out.print("	Read Register 1 : " + rs + "\n	Read Register 2 : " + rt + "\n	Write Register : N/A" + "\n	Write Data : N/A\n");
		System.out.println("Outputs");
		System.out.print("	Read Data 1 : " + Readdata1.evaluate() + "\n	Read Data 2 : " + Readdata2.evaluate()+"\n");
		
		System.out.println("\nRelevant Data Paths: ");
		System.out.print("	Sign Extended IR[15-0] : " + sign_ext_offset.evaluate() + "\n	IR[20-16] : " + Datapath.evaluate() + "\n	IR[15-11] : " + rt + "\n");
		
		System.out.println("\nID/EX Buffer: ");
		System.out.print("**********************************************************\n");
		System.out.println("Format: IR[15-11] | IR[20-16] | Sign Extended IR[15-0] | ReadData2 | ReadData1 | PC+4 | Control Vector (EX, MEM, WB)");
		System.out.print("Binary: ");
		for (int i=0; i<getOutputBufferSize();i++) {
			System.out.print(getOutputBuffer()[i].disp());
		}
		System.out.print("\nDecimal: ");
		for (int i=0; i<getOutputBufferSize();i++) {
			System.out.print(getOutputBuffer()[i].dispVal());
		}
		System.out.println();
		System.out.print("**********************************************************\n");
		
		
	}
	
	public int[] processOP(int op){
		//Process OP code 
		int[] control_vector = new int[9];
		if(op==0){
			//R-format instruction
			control_vector = new int[]{1,1,0,0,0,0,0,1,0};
		}
		else if(op==35){
			//Load word
			control_vector = new int[]{0,0,0,1,0,1,0,1,1};
		}
		else if(op==43){
			//Store word
			control_vector = new int[]{1,0,0,1,0,0,1,0,1};
		}
		else if(op==4){
			//Branch-equal
			control_vector = new int[]{1,0,1,0,1,0,0,0,1};
		}
		else{
			//Empty array. Fall-back assignment
			control_vector = new int[9];
		}
		
		System.out.println("Control Vector: ");
		System.out.println("	Format: EX | MEM | WB");
		System.out.print("	Vector:");
		for( int i=0; i<4 ;i++) {
			System.out.print(" " + control_vector[i]);
		}
		System.out.print(" |");
		for( int i=4; i<7 ;i++) {
			System.out.print(" " + control_vector[i]);
		}
		System.out.print(" |");
		for( int i=7; i<9 ;i++) {
			System.out.print(" " + control_vector[i]);
		}
		System.out.println();
		
		return control_vector;
	}
	public void setWriteRegister(int reg){
		
	}
}
