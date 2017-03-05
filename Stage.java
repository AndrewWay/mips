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
		System.out.println("INPUT BUFFER"+getInputBuffer().disp());
		System.out.println("OUTPUT BUFFER"+getOutputBuffer().disp());	
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
		setInputBuffer(new Bin(getInputBufferSize()));
	}
	public void createOutputBuffer(){
		setOutputBuffer(new Bin(getOutputBufferSize()));
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
	public void loadBuffer(int start,int end,Bin b){
		int[] valArr = b.getArray();
		System.out.println(getOutputBufferSize());
		for(int i=start;i<=end;i++){
			System.out.println(i);
			out_buff.input(i, valArr[i-start]);
		}
	}
	public void syncBuffers(Bin b){
		in_buff.overwrite(b.getArray());
	}
}
