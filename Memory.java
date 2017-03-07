import java.util.Arrays;

public class Memory extends Stage{
	
	Bin[] dataMemory = new Bin[10000];//Are the bins inside the array initialized at any point??
	Bin readData=new Bin(1);//Default size of array
	Bin Mux12_Output,Readdata2,ALUResult,Zero,AddResult,CV;
	Boolean branchGate;
	
	public Memory(Firmware m) {
		super(m);
		this.inbuff_size=6;
		this.outbuff_size=4;
		createBuffers();
		//Assume data starts as zeros
		for(int i = 0; i<dataMemory.length; i++) {
			dataMemory[i]=new Bin(32);
		}
	}
	
	public void memory(){
		System.out.println("\n\nMEM STAGE\n\n");
		branchGate=false;
		
		//CV=getMem().getControlVector();
		readIngoingBuffer();
		andGate();
		Bin writeData = Readdata2; //Data to write
		Bin readAddress = ALUResult, writeAddress = ALUResult; //Address for read or write
		if(CV.getArray()[1] != 0) { //MemRead
			//Read from Data Memory
			readData = dataMemory[readAddress.evaluate()]; //Update when data memory is constructed.
		} else if(CV.getArray()[2] != 0) { //MemWrite
			//Write to Data Memory
			dataMemory[writeAddress.evaluate()] = writeData;
		}
		loadOutgoingBuffer();
		
		//Display MEM outputs
		System.out.println("\nData Ports :");
		System.out.println("Inputs");
		System.out.print("	AND 0 : " + CV.getArray()[0] + "\n	AND 1 : " + Zero.evaluate() + "\n	Read Address : " + ALUResult.evaluate() +  "\n	Write Address :" + ALUResult.evaluate() + "\n	Write Data : " + Readdata2.evaluate() + "\n");
		System.out.println("Outputs");
		System.out.print("	AND GATE : " + branchGate + "\n	Read Data : " + readData.evaluate()+"\n");
		
		System.out.println("\nRelevant Data Paths: ");
		System.out.print("	ALUResult : " + ALUResult.evaluate() + "\n	Multiplexer 12 Output : " + Mux12_Output.evaluate() + "\n");
		
		System.out.println("\nRelevant Control Vector Components: ");
		System.out.print("	Branch : " + CV.getArray()[0] + "\n	MemRead : " + CV.getArray()[1] + "\n	MemWrite : " + CV.getArray()[2] + "\n");
		
		System.out.println("\nMEM/WB Buffer: ");
		System.out.print("**********************************************************\n");
		System.out.println("Format: Mux12 Output | ALU Result | ReadData | Control Vector (WB)");
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
	public void loadOutgoingBuffer(){
		loadBuffer(3,new Bin(Arrays.copyOfRange(CV.getArray(),3,CV.getArray().length)));
		loadBuffer(2,readData);
		loadBuffer(1,getIBuffSeg(2));
		loadBuffer(0,getIBuffSeg(0));
	}
	public void andGate(){
		if((Zero.getArray()[0]) != 0 && (CV.getArray()[0] != 0)) {
			branchGate=true;
		}
	}
	public void readIngoingBuffer(){
		Mux12_Output=getIBuffSeg(0);
		Readdata2=getIBuffSeg(1);
		ALUResult=getIBuffSeg(2);
		Zero=getIBuffSeg(3);
		AddResult=getIBuffSeg(4);
		CV=getIBuffSeg(5);
	}
	public Bin getReadData(int i){
		return dataMemory[i];
	}
	public void writeData(int i,int v){

			dataMemory[i].dec_overwrite(v);//Return 32 bit Bin
	}
	
	public boolean getBGate() {
		return branchGate;
	}

}
