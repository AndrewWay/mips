import java.lang.reflect.Array;
import java.util.Arrays;

public class Execute extends Stage {
	private Bin RT,RS,RD,Readdata1,Readdata2,Mux12_Output,PC,Offset,Funct,ALUResult,AddResult,Zero,CV;
	int addresult,zero,aluresult,readdata2,mux12_output,pc,offset,funct,op;
	int RegDst,ALUOp1,ALUOp0,ALUSrc;
	Mux m11,m12;
	public Execute(Firmware m){
		super(m);
		this.inbuff_size=7;//rd|rt|ext_offset|readdata2|readdata1|pc|control vector
		this.outbuff_size=6;
		createBuffers();
		m11=new Mux();
		m12=new Mux();
	}
	public void execute(){
		System.out.println("\n\nEX STAGE \n\n");
		
		readIngoingBuffer();
		getControlVectorValues();
		evaluateBins();
		setMux();
		addResult();
		extractFunct();
		ALU();
		loadOutgoingBuffer();
		displayOutgoingBuffer();
	}
	public void ALU(){
		int[] alu_input0 = Readdata1.getArray();
		int[] alu_input1 = m11.getOutput();
		Bin alu0=new Bin(alu_input0);
		Bin alu1=new Bin(alu_input1);
		int[] zero=new int[1];
		if(ALUOp1==1) { //ALU Control
			if(funct==32){ //Funct of add
				if(alu0.evaluate()==alu1.evaluate()){
					zero[0]=1;
				}
				else{
					zero[0]=0;
				}
				aluresult=alu0.evaluate()+alu1.evaluate();
			}
			else { //must be subtract
				if(alu0.evaluate()==alu1.evaluate()){
					zero[0]=1;
				}
				else{
					zero[0]=0;
				}
				aluresult=alu0.evaluate()-alu1.evaluate();
			}
		}
		else if (ALUOp0==1){ //beq is only possibility
			aluresult=0;//TODO Make ALUResult Bin null/nonsense??
			if (alu0.evaluate()==alu1.evaluate()){
				zero[0]=1;
			}
			else {
				zero[0]=0;
			}
		}
		else { //Must be lw or sw, in which case ALUOp = 0 0 and adding occurs
			aluresult=alu0.evaluate()+alu1.evaluate();
			zero[0]=0; //unimportant
		}
		Zero = new Bin(zero);
		System.out.println(aluresult);
		ALUResult=new Bin(aluresult,0);
		System.out.println("ALURESULT "+AddResult.disp());
	}
	public void extractFunct(){
		Funct = Offset.extract(21, 31);//TODO Change this so you dont use a fixed integer argument
		funct = Funct.evaluate();
	}
	public void setMux(){//TODO Bad method, clean this up.
		//MUX12
		m12.setPort0(RT.getArray());
		m12.setPort1(RD.getArray());
		m12.setSelect(RegDst);
		//MUX11
		m11.setPort0(Readdata2.getArray());
		m11.setPort1(Offset.getArray());
		m11.setSelect(ALUSrc);
		Mux12_Output=new Bin(m12.getOutput());
	}
	public void addResult(){
		addresult=pc+4*offset;
		AddResult = new Bin(addresult,10);
	}
	public void getControlVectorValues(){
		int[] cv = getMem().getControlVector().getArray();
		RegDst=cv[0];
		ALUOp1=cv[1];
		ALUOp0=cv[2];
		ALUSrc=cv[3];
	}
	public void evaluateBins(){
		offset = Offset.evaluate();
		pc = PC.evaluate();
		addresult=pc+4*offset;
	}
	public void readIngoingBuffer(){
		CV = getIBuffSeg(6);
		PC = getIBuffSeg(5);
		Readdata1 = getIBuffSeg(4);
		Readdata2 = getIBuffSeg(3);
		Offset = getIBuffSeg(2);
		RT = getIBuffSeg(1);
		RD = getIBuffSeg(0);
	}
	public void loadOutgoingBuffer(){;
		loadBuffer(5,new Bin(Arrays.copyOfRange(CV.getArray(),4,CV.getArray().length)));
		loadBuffer(4,AddResult);
		loadBuffer(3,Zero);
		loadBuffer(2,ALUResult);
		loadBuffer(1,Readdata2);
		loadBuffer(0,Mux12_Output);
	}
	public void displayOutgoingBuffer(){//TODO Delete. Redundant
		//System.out.println("Mux12 "+Mux12_Output.evaluate());
		System.out.println("Read data2 "+Readdata2.evaluate());
		System.out.println("ALUResult "+ALUResult.evaluate());
		System.out.println("Zero "+Zero.evaluate());
		System.out.println("ADDResult "+AddResult.evaluate());
	}
}

