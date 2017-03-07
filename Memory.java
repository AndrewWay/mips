import java.util.Arrays;

public class Memory extends Stage{
	
	Bin[] dataMemory = new Bin[10000];//Are the bins inside the array initialized at any point??
	Bin readData=new Bin(1);//Default size of array
	Bin Mux12_Output,Readdata2,ALUResult,Zero,AddResult,CV;
	
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
		System.out.println("MEM STAGE\n\n");
		
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
	}
	public void loadOutgoingBuffer(){
		loadBuffer(3,new Bin(Arrays.copyOfRange(CV.getArray(),3,CV.getArray().length)));
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
		CV=getIBuffSeg(5);
		
		System.out.println("CV");
		for(int j=0; j<CV.getBinSize(); j++){
			System.out.println(CV.getArray()[j]);
		}
	}
	public Bin getReadData(int i){
		return dataMemory[i];
	}
	public void writeData(int i,int v){

			dataMemory[i].dec_overwrite(v);//Return 32 bit Bin
	}

}
