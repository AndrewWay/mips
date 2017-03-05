import java.util.Arrays;


public class Decode extends Stage{

	public Decode(int ibs, int obs,Memory m) {
		super(ibs, obs, m);
	}
	public void decode(){
		Bin ReadRegister1 = getInputBuffer().extract(6,10);//TODO Check the indices
		Bin ReadRegister2 = getInputBuffer().extract(11,16);
		Bin offset=getInputBuffer().extract(17, 32);
		int rs = ReadRegister1.evaluate();
		int rt = ReadRegister2.evaluate();
		
		System.out.println("RS: "+Integer.toString(rs)+" RT: "+Integer.toString(rt));
		Bin Readdata1=getMem().getRegister(rs);
		Bin Readdata2=getMem().getRegister(rt);
		//SIGN EXTEND-----------------
		Bin sign_ext=new Bin(16);
		sign_ext=concat(sign_ext,offset);
	//----------------------------
	int[] Datapath = Arrays.copyOfRange(IF_ID, 16, 21);//from rd field in buffer
	ID_EX=concat(Datapath,concat(ReadRegister2,concat(sign_ext,concat(Readdata2,concat(Readdata1,PC)))));//Yeah this is ugly
	
	int[] shamtarr = Arrays.copyOfRange(IF_ID, 21, 26);
	System.out.println(Arrays.toString(shamtarr));
	
	//CONTROL
	int op = bin_toDec(Arrays.copyOfRange(IF_ID, 0, 6));
	int rd = bin_toDec(Datapath);
	int shamt = bin_toDec(shamtarr);
	int funct = bin_toDec(Arrays.copyOfRange(IF_ID, 26, 32));
	System.out.printf("OP %d RS %d RT %d RD %d SHAMT %d FUNCT %d", op,rs,rt,rd,shamt,funct);
	}
	
	public void processOP(int[] oparray){
		int op=in_buff.bin_toDec(oparray);
		//Process OP code 
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
	}
}
