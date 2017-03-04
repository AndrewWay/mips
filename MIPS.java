import java.lang.reflect.Array;
import java.util.Arrays;
import java.math.*;

public class MIPS {
//Hardware specifications
//Specify register sizes
//TODO Allow hardware spec parameters to be set
	
//Specifiy register sizes	

//BUFFERS
static int[] IF_ID = new int [36];//32 bits for Inst | 4 bits for PC
static int[] ID_EX = new int [110];//5 for inst[15-11] | 5 for inst[20-16] 
//| 32 for sign extended inst[15-0] | 32 bits for read data 1 
//| 32 bits for read data 2 | 4 bits for PC
static int[] EX_MEM = new int [6];
static int[] MEM_WB = new int [6];
static int[][] registers = new int[32][4];//Do we assume the registers are 32 bits? I am setting these to 4 bits for now
static int[][] inst_memory = new int[32][32];
static int[] ir = new int [32];
static int[] PC = new int[4];
static int op,rs,rt,rd,shamt,funct;
static int[] control_vector;//{RegDst,ALUOp1,ALUOp0,ALUSrc,Branch,MemRead,MemWrite,RegWrite,MemtoReg}

public static void main(String args[]){
	PC=new int[] {0,1,0,0};
	registers[2]=new int[] {1,0,0,1};//9
	registers[4]=new int[] {1,0,1,0};//10
	registers[7]=new int[] {0,1,1,0};//6
	inst_memory[8]=new int[] {0,0,0,0,0,0,0,0,1,0,0,0,0,1,1,1,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0};
	String inst = Arrays.toString(inst_memory[0]);
	int instSize = Array.getLength(inst_memory[0]);
	fetch();
	decode();
	execute();
}

public static void fetch(){
	//Increment PC
	incrementPC();
	//Obtain the instruction located at PC
	ir = inst_memory[bin_toDec(PC)];
	//Load that instruction into buffer
	IF_ID=concat(ir,PC);
}
public static void decode(){
	int[] ReadRegister1 = Arrays.copyOfRange(IF_ID, 6, 11);;//from rs field in buffer
	int[] ReadRegister2 = Arrays.copyOfRange(IF_ID, 11, 16);//from rt field in buffer
	int rs=bin_toDec(ReadRegister1);
	int rt=bin_toDec(ReadRegister2);
	
	System.out.println("RS: "+Integer.toString(rs)+" RT: "+Integer.toString(rt));
	
	int[] Readdata1=registers[rs];
	int[] Readdata2=registers[rt];
	int[] offset=Arrays.copyOfRange(IF_ID, 16, 32);
	//SIGN EXTEND-----------------
	int[] sign_ext=new int[16];
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
public static void execute(){
	int[] mux12_port0 = Arrays.copyOfRange(ID_EX, 5, 10);//rt
	int[] mux12_port1 = Arrays.copyOfRange(ID_EX, 0, 5);//rd
	int RegDst1 = 1;//Is this arbitrarily set? Or is it 1 because inst is for add?
	int[] mux12_output;
	if(RegDst1 == 1)
	{
		mux12_output = mux12_port1;
	}
	else if(RegDst1 == 0)
	{
		mux12_output = mux12_port0;
	}
	else{
		//Something went very wrong
		throw new RuntimeException();		
	}
	
	//MUX11
	int[] mux11_port0 = Arrays.copyOfRange(ID_EX, Array.getLength(ID_EX)-11, Array.getLength(ID_EX)-6);
	int[] alu_input1;
	int[] mux11_port1 = 
	//ALU-------------------
	alu_input1 = Arrays.copyOfRange(ID_EX, Array.getLength(ID_EX)-6, Array.getLength(ID_EX)-1);
	int[] alu_input2 = Arrays.copyOfRange(ID_EX, 10, 42);
	int ALUSRC = 0;//TODO Where does this zero come from? op code?
	//TODO
	//determine what output to give
	
	
}
public void memory(){
	
}

public void writeback(){
	
}


public static void incrementPC(){
	int pc_val = bin_toDec(PC);
	pc_val=pc_val+4;
	PC=dec_toBin(pc_val);
}
public static int bin_toDec(int[] bin){
	int size = Array.getLength(bin);
	int decimal = 0;
	for(int i=0;i<=size-1;i++){
		//System.out.println(bin[i]);
		int term = (int) Math.pow(2, size-1-i);
		decimal=decimal+bin[i]*term;//Does not account for negative numbers
	}
	return decimal;
}
public static int[] dec_toBin(int dec){
	//determine array size needed
	int n=0;
	int binGuess = (int) Math.pow(2, n);
	while(binGuess < dec){
		n=n+4;
		binGuess = (int) Math.pow(2, n);
	}
	int[] bin = new int[n];
	for(int i=0;i<n;i++){
		int term = (int) Math.pow(2,n-i-1);
		if (term > dec){
			bin[i]=0;
		}
		else{
			bin[i]=1;
			dec=dec-term;
		}
	}
	return bin;
}
public static int[] concat(int[] a, int[] b) {
	   int aLen = a.length;
	   int bLen = b.length;
	   int[] c= new int[aLen+bLen];
	   System.arraycopy(a, 0, c, 0, aLen);
	   System.arraycopy(b, 0, c, aLen, bLen);
	   return c;
	}
}
