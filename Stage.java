import java.util.Arrays;
public abstract class Stage {
	private int inbuff_size;
	private int outbuff_size;
	private static Memory mem;
	private Bin in_buff;
	private Bin out_buff; 
	
	public Stage(int ibs, int obs,Memory m){
		setmem(m);
		setInputBufferSize(ibs);
		setOutputBufferSize(obs);
		createInputBuffer();
		createOutputBuffer();
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
	public void createInputBuffer(){
		in_buff=new Bin(getInputBufferSize());
	}
	public void createOutputBuffer(){
		out_buff=new Bin(getOutputBufferSize());
	}
	public void setInputBuffer(Bin buffData){
		in_buff=buffData;
	}
	public void setOutputBuffer(Bin buffData){
		out_buff=buffData;
	}
	public Bin getInputBuffer(){
		return in_buff;
	}
	public Bin getOutputBuffer(){
		return out_buff;
	}
	public static Memory getMem(){
		return mem;
	}
}
