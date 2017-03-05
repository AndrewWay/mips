import java.util.Arrays;


public class Decode extends Stage{

	public Decode(int ibs, int obs,Memory m) {
		super(ibs, obs, m);
	}
	public void decode(){
		Bin Opfield = getInputBuffer().extract(0,5);//Op code
		Bin ReadRegister1 = getInputBuffer().extract(6,10);//TODO Check the indices
		Bin ReadRegister2 = getInputBuffer().extract(11,15);//These indices will change whenever you change the number of registers!!
		Bin Datapath = getInputBuffer().extract(16,20);//from rd field in buffer
		Bin offset=getInputBuffer().extract(16, 31);
		Bin ShiftAmount = getInputBuffer().extract(21,25);
		Bin Function = getInputBuffer().extract(26,31);
		int op = Opfield.evaluate();
		int rs = ReadRegister1.evaluate();
		int rt = ReadRegister2.evaluate();
		int rd = Datapath.evaluate();
		int shamt = ShiftAmount.evaluate();
		int funct = Function.evaluate();
		
		Bin Readdata1=getMem().getRegister(rs);//Data located in register rs
		Bin Readdata2=getMem().getRegister(rt);//Data located in register rt
		//SIGN EXTEND
		Bin sign_ext_offset=new Bin(32);
		sign_ext_offset.overwrite_section(16,offset);
		//Load into buffer//TODO this could use some refinement. I was thinking we could make Buffers into a Queue made of Bins.
		//Loading a buffer would involve adding Bin objects one at a time into Queue, and you don't have to worry about what the actual size of each bin is. 
		//When stages read from their input buffer, they still need to read specific fields of each Bin object. I think this is inavoidable.
		loadBuffer(0,4,Datapath);
		loadBuffer(5,9,ReadRegister2);
		loadBuffer(10,31,sign_ext_offset);
		loadBuffer(32,Readdata1.getBinSize()-1,Readdata1);
		int rd1End = 32+Readdata1.getBinSize()-1;
		loadBuffer(32,rd1End,Readdata1);
		loadBuffer(rd1End+1,rd1End+getMem().getPC().getBinSize(),getMem().getPC());//Fix this ugly line
		//ID_EX=concat(Datapath,concat(ReadRegister2,concat(sign_ext,concat(Readdata2,concat(Readdata1,PC)))));//OLD LINE. Do not use.
		
	System.out.printf("OP %d RS %d RT %d RD %d SHAMT %d FUNCT %d", op,rs,rt,rd,shamt,funct);
	//CONTROL
	getMem().setControlVector(processOP(op));
	}
	
	public int[] processOP(int op){
		//Process OP code 
		int[] control_vector = new int[9];
		if(op==0){
			//R-format instruction
			control_vector = new int[]{1,1,0,0,0,0,0,1,0};
		}
		else if(op==35){
			//Load word
			control_vector = new int[]{0,0,0,1,0,1,0,1,1};
		}
		else if(op==43){
			//Store word
			control_vector = new int[]{1,0,0,1,0,0,1,0,1};
		}
		else if(op==4){
			//Branch-equal
			control_vector = new int[]{1,0,1,0,1,0,0,0,1};
		}
		else{
			//Empty array. Fall-back assignment
			control_vector = new int[9];
		}
		return control_vector;
	}
}
