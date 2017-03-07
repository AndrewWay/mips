
public class Memory extends Stage{
	
	Bin[] dataMemory = new Bin[10000];//Are the bins inside the array initialized at any point??
	Bin readData=new Bin(1);//Default size of array
	Bin Mux12_Output,Readdata2,ALUResult,Zero,AddResult,CV;
	
	public Memory(Firmware m) {
		super(m);
		this.inbuff_size=5;
		this.outbuff_size=3;
		createBuffers();
	}
	
	public void memory(){
		CV=getMem().getControlVector();
		readIngoingBuffer();
		andGate();
		Bin writeData = Readdata2; //Data to write
		Bin readAddress = ALUResult, writeAddress = ALUResult; //Address for read or write
		if(CV.getArray()[5] != 0) { //MemRead
			//Read from Data Memory
			readData = dataMemory[readAddress.evaluate()]; //Update when data memory is constructed.
		} else if(CV.getArray()[6] != 0) { //MemWrite
			//Write to Data Memory
			dataMemory[writeAddress.evaluate()] = writeData;
		}
		loadOutgoingBuffer();
	}
	public void loadOutgoingBuffer(){
		loadBuffer(2,readData);
		loadBuffer(1,getIBuffSeg(2));
		loadBuffer(0,getIBuffSeg(0));
	}
	public void andGate(){
		if((Zero.getArray()[0]) != 0 && (getMem().getControlVector().getArray()[4] != 0)) {
			//Send input for multiplexer in fetch stage
			//Need to wait for multiplexer objects
		}
	}
	public void readIngoingBuffer(){
		Mux12_Output=getIBuffSeg(0);
		Readdata2=getIBuffSeg(1);
		ALUResult=getIBuffSeg(2);
		Zero=getIBuffSeg(3);
		AddResult=getIBuffSeg(4);
		CV=getIBuffSeg(5);;
	}
	public Bin getReadData(int i){
		return dataMemory[i];
	}
	public void writeData(int i,Bin b){
		if(b.getBinSize()==32){
			dataMemory[i]=b;//Return 32 bit Bin
		}
		else
			System.out.println("MEMORY WRITE ERROR: Input data not 32bits");
	}
}
