public class Firmware {
	private int regsize;
	private int regnum;
	private final int inst_mem_size=32;
	private int inst_mem_num;
	private Bin control_vector;
	private Bin[] registers;
	private Bin PC;
	static int[][] inst_memory;
	
	public Firmware(int rs, int rn, int imn){
		setRegSize(rs);
		setRegNum(rn);
		setInstNum(imn);
		createPC();
		setPC(0);
		createRegisters();
		createControlVector();
		createInstMem();
		loadInstructions();
	}
	public void createPC(){
		PC=new Bin(getInstNum());//TODO Set PC Bin size according to the number of instructions in the instructions textfile
	}
	public void setPC(int p){
		PC.dec_overwrite(p);
	}
	public Bin getPC(){
		return PC;
	}
	public void createControlVector(){
		control_vector=new Bin(9);
	}
	public void setControlVector(int[] cv){
		control_vector.overwrite(cv);
	}
	public Bin getControlVector(){
		return control_vector;
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
		//TODO Read instructions from a textfile, either in the form of binary or in semantics "add $1 $8 $3" etc
		inst_memory[0]=new int[]{0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,1,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0};//TODO Read instructions from memory
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
	public Bin getRegister(int i){
		return registers[i];
	}
}
