
public class Stage {
	private int regsize;
	private int reg_num;
	private int inbuff_size;
	private int outbuff_size;
	private Bin[] registers;
	private Bin in_buff;
	private Bin out_buff; 
	
	public Stage(int rs, int rn,int ibs, int obs){
		inbuff_size=ibs;
		outbuff_size=obs;
		regsize=rs;
		reg_num=rn;
		registers=new Bin[rn];
		for(int i=0;i<reg_num;i++){
			registers[i]=new Bin(regsize);
			registers[i].disp();
		}
		in_buff=new Bin(inbuff_size);
		out_buff=new Bin(outbuff_size);
	}
	public void disp_registers(){
		for(int i=0;i<reg_num;i++){
			System.out.println("r"+i+" "+registers[i].disp());
		}
	}
	public void disp_register(int i){
		System.out.println("r"+i+" "+registers[i].disp());
	}
	public void disp_buffers(){
		System.out.println("INPUT BUFFER"+in_buff.disp());
		System.out.println("OUTPUT BUFFER"+out_buff.disp());	
	}
}
