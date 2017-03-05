
public class Memory {
	private int regsize;
	private int regnum;
	private int inst_mem_size;
	private int inst_mem_num;
	private Bin[] registers;
	private Bin[] PC;//TODO Initialize this
	static int[][] inst_memory;
	
	public Memory(int rs, int rn,int ims, int imn){
		setRegSize(rs);
		setRegNum(rn);
		setInstSize(ims);
		setInstNum(imn);
		createRegisters();
		loadInstructions();
	}
	private void setInstSize(int ims) {
		inst_mem_size=ims;
		
	}
	private void setInstNum(int imn) {
		inst_mem_num=imn;
	}
	public void disp_registers(){
		for(int i=0;i<getRegNum();i++){
			System.out.println("r"+i+" "+registers[i].disp());
		}
	}
	public void disp_register(int i){
		System.out.println("r"+i+" "+registers[i].disp());
	}
	public void setRegSize(int rs){
		regsize=rs;
	}
	public void setRegNum(int rn){
		regnum=rn;
	}
	public int getRegSize(){
		return regsize;
	}
	public int getRegNum(){
		return regnum;
	}
	public void createRegisters(){
		registers=new Bin[getRegNum()];
		for(int i=0;i<getRegNum();i++){
			registers[i]=new Bin(getRegSize());
			registers[i].disp();
		}
	}
	public void overwrite_register(int i,int[] d){
		registers[i].overwrite(d);
	}
	public void randomize_register(int i){
		registers[i].randomize();
	}
	public void loadInstructions(){
		inst_memory=new int[getInstNum()][getInstSize()];
	}
	private int getInstSize() {
		return inst_mem_size;
	}
	private int getInstNum() {
		return inst_mem_num;
	}
}
