
public class Memory extends Stage{
	
	public Memory(Firmware m) {
		super(m);
		this.inbuff_size=5;
		this.outbuff_size=3;
		createBuffers();
	}
	
	public void memory(){
		
		if((getIBuffSeg(1).getArray()[1]) != 0 && (getMem().getControlVector().getArray()[4] != 0)) {
			//Send input for multiplexer in fetch stage
		}
		
		int[] writeData = getIBuffSeg(3).getArray(); //Data to write
		int[] readAddress, writeAddress = getIBuffSeg(2).getArray(); //Address for read or write
		
		if(getMem().getControlVector().getArray()[5] != 0) { //MemRead
			//Read from Data Memory
			Bin readData = new Bin(new int[]{0}); //Update when data memory is constructed.
		} else if(getMem().getControlVector().getArray()[6] != 0) { //MemWrite
			//Write to Data Memory
		}
		
		loadBuffer(0,getIBuffSeg(2));
		loadBuffer(1,readData);
		loadBuffer(2,getIBuffSeg(1));
		
	}
	
}
