
public class Memory extends Stage{
	
	public Memory(Firmware m) {
		super(m);
		this.inbuff_size=5;
		this.outbuff_size=0;
		createBuffers();
	}
	
	
	
}
