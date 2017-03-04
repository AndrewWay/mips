
public class Memory {
	private int regsize;
	private int regnum;
	private Bin[] registers;
	private Bin[] PC;//TODO Initialize this
	
	public Memory(int rs, int rn){
		setRegSize(rs);
		setRegNum(rn);
		createRegisters();
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
}
