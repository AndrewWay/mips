public abstract class Stage {
	protected int inbuff_size;
	protected int outbuff_size;
	private final int DEF_BIN_VAL=0;//The initial size of each bin in a buffer
	private static Firmware mem;
	private Bin[] in_buff;
	private Bin[] out_buff; 
	
	public Stage(Firmware m){
		setmem(m);
	}
	public void disp_buffers(){
		String inputBuffer="";
		String outputBuffer="";
		for(int i=0;i<getInputBufferSize();i++){
			inputBuffer+=getIBuffSeg(i).disp();
		}
		for(int i=0;i<getOutputBufferSize();i++){
			outputBuffer+=getOBuffSeg(i).disp();
		}
		System.out.println("INPUT BUFFER"+inputBuffer);
		System.out.println("OUTPUT BUFFER"+outputBuffer);	
	}
	public void setmem(Firmware m){
		mem=m;
	}
	public void createBuffers(){
		int ibb = getInputBufferSize();
		int obb = getOutputBufferSize();
		in_buff=new Bin[ibb];
		out_buff=new Bin[obb];
		for(int i=0;i<ibb;i++){
			in_buff[i]=new Bin(DEF_BIN_VAL);
		}
		for(int i=0;i<obb;i++){
			out_buff[i]=new Bin(DEF_BIN_VAL);
		}
		outbuff_size=obb;
		inbuff_size=ibb;
	}
	public int getOutputBufferSize(){
		return outbuff_size;
	}
	public int getInputBufferSize(){
		return inbuff_size;
	}
	public Bin[] getInputBuffer(){
		return in_buff;
	}
	public Bin[] getOutputBuffer(){
		return out_buff;
	}
	public static Firmware getMem(){
		return mem;
	}
	public void loadBuffer(int segID,Bin b){
		out_buff[segID]=b;
	}
	public void loadBuffer(Bin[] b){
		in_buff=b;
	}
	public Bin getOBuffSeg(int i){
		return out_buff[i];
	}
	public Bin getIBuffSeg(int i){
		return in_buff[i];
	}
}
