import java.util.*;


public class Memory extends Stage{
	
	Bin[] dataMemory = new Bin[10000];
	Bin readData = new Bin(32);
	
	public Memory(Firmware m) {
		super(m);
		this.inbuff_size=5;
		this.outbuff_size=3;
		createBuffers();
		Bin testBin = new Bin(32);
		testBin.dec_overwrite(0);
		Arrays.fill(dataMemory, testBin);
	}
	
	public void memory(){
		
		if((getIBuffSeg(1).getArray()[1]) != 0 && (getMem().getControlVector().getArray()[4] != 0)) {
			//Send input for multiplexer in fetch stage
			//Need to wait for multiplexer objects
		}
		
		Bin writeData = getIBuffSeg(3); //Data to write
		Bin readAddress = getIBuffSeg(2), writeAddress = getIBuffSeg(2); //Address for read or write
		
		if(getMem().getControlVector().getArray()[5] != 0) { //MemRead
			//Read from Data Memory
			System.out.println("readAddress.evaluate(): " + readAddress.evaluate());
			readData = dataMemory[readAddress.evaluate()]; //Update when data memory is constructed.
		} else if(getMem().getControlVector().getArray()[6] != 0) { //MemWrite
			//Write to Data Memory
			dataMemory[writeAddress.evaluate()] = writeData;
		}
		
		loadBuffer(0,getIBuffSeg(1));
		loadBuffer(1,getIBuffSeg(2));
		loadBuffer(2,readData);
		
	}
	
}
