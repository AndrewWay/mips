import java.lang.reflect.Array;


public class Memory {
	private int regsize;
	private int regnum;
	private int inst_mem_size;
	private int inst_mem_num;
	private Bin[] registers;
	private Bin PC;//TODO Initialize this. Also make this into a binary array. Will need a bintodec converter in here. 
	//Might need to make a new class that handles binary arithmetic and binary-decimal conversion.
	static int[][] inst_memory;
	
	public Memory(int rs, int rn,int ims, int imn){
		setRegSize(rs);
		setRegNum(rn);
		setInstSize(ims);
		setInstNum(imn);
		createPC();
		setPC(0);
		createRegisters();
		createInstMem();
		loadInstructions();
	}
	public void createPC(){
		PC=new Bin(getInstSize());
	}
	public void setPC(int p){
		PC.dec_overwrite(p);
	}
	public Bin getPC(){
		return PC;
	}
	public void setInstSize(int ims) {
		inst_mem_size=ims;
		inst_mem_size=32;//HARDCODED INSTRUCTION SIZE. Comment this line to use custom instruction size.
	}
	public void setInstNum(int imn) {
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
	public void createInstMem(){
		inst_memory=new int[getInstNum()][getInstSize()];
	}
	public void loadInstructions(){
		inst_memory[0]=new int[]{0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,1,0,0,0,0,1,0,0,0,0,0,1,1,1,1,1,1};//TODO Read instructions from memory
	}
	public int getInstSize() {
		return inst_mem_size;
	}
	public int getInstNum() {
		return inst_mem_num;
	}
	public int[][] getInstMem(){
		return inst_memory;
	}
}
