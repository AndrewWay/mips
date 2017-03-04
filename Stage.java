
public abstract class Stage {
	private int inbuff_size;
	private int outbuff_size;
	private Memory mem;
	private Bin in_buff;
	private Bin out_buff; 
	
	public Stage(int ibs, int obs,Memory m){
		setmem(m);
		setInputBufferSize(ibs);
		setOutputBufferSize(obs);
	}
	public void disp_buffers(){
		System.out.println("INPUT BUFFER"+in_buff.disp());
		System.out.println("OUTPUT BUFFER"+out_buff.disp());	
	}
	public void setmem(Memory m){
		mem=m;
	}
	public void setInputBufferSize(int ibs){
		inbuff_size=ibs;
	}
	public void setOutputBufferSize(int obs){
		outbuff_size=obs;
	}
	public int getOutputBufferSize(){
		return outbuff_size;
	}
	public int getInputBufferSize(){
		return inbuff_size;
	}
	public void setInputBuffer(){
		in_buff=new Bin(getInputBufferSize());
	}
	public void setOutputBuffer(){
		out_buff=new Bin(getOutputBufferSize());
	}
}
