import java.lang.reflect.Array;
import java.util.Arrays;


public class Execute extends Stage {
	Bin RT,RS,RD,AddResult,Zero,ALUResult,Readdata1,Readdata2,Mux12_Output,PC,Offset;
	int addresult,zero,aluresult,readdata2,mux12_output,pc,offset;
	Mux m11,m12;
	public Execute(Firmware m){
		super(m);
		this.inbuff_size=6;//rd|rt|ext_offset|readdata2|readdata1|pc
		this.outbuff_size=5;
		createBuffers();
		m11=new Mux();
		m12=new Mux();
	}
	public void execute(){
		readIngoingBuffer();
		//MUX12
		//m12.setMuxPort0(RT);
		//m12.setMuxPort1(RD);
		//m12.setMuxOutput();
		//MUX11
		//m11.setMuxPort0(Readdata2);
		//m11.setMuxPort1(Offset);
		//m11.setMuxOutput();
		AddResult = new Bin(addresult,0);
		Zero = new Bin(zero);
		ALUResult=new Bin(aluresult,0);
		Mux12_Output = new Bin(mux12_output);
		//ALU Control
		Bin test = getIBuffSeg(2);
		Bin Funct = getIBuffSeg(2).extract(26, 31);//TODO Change this so you dont use a fixed integer argument
		int funct = Funct.evaluate();
		//ALU-------------------
		int[] alu_input0 = getIBuffSeg(4).getArray();//input is equal to Readdata1
		Bin alu0=new Bin(alu_input0);
		Bin alu1=new Bin(alu_input1);
		int aluresult;
		int[] zero=new int[1];
		if(funct==32){
			if(alu_input0==alu_input1){
				zero[0]=1;
			}
			else{
				zero[0]=0;
			}
			aluresult=alu0.evaluate()+alu1.evaluate();
		}
		else{
			aluresult=0;//TODO Make ALUResult Bin null/nonsense??
			zero[0]=0;//TODO Make zero null?
		}
		//Add
		//Construct bins for buffer
	}
	public void evaluateBins(){
		offset = Offset.evaluate();
		pc = PC.evaluate();
		addresult=pc+4*offset;
	}
	public void readIngoingBuffer(){
		PC = getIBuffSeg(5);
		Readdata1 = getIBuffSeg(4);
		Readdata2 = getIBuffSeg(3);
		Offset = getIBuffSeg(2);
		RT = getIBuffSeg(1);
		RD = getIBuffSeg(0);
	}
	public void loadOutgoingBuffer(){
		loadBuffer(4,AddResult);
		loadBuffer(3,Zero);
		loadBuffer(2,ALUResult);
		loadBuffer(1,Readdata2);
		loadBuffer(0,Mux12_Output);
	}
	public void displayOutgoingBuffer(){//TODO Delete. Redundant
		System.out.println("Mux12 "+Mux12_Output.evaluate());
		System.out.println("Read data2 "+alu1.evaluate());
		System.out.println("ALUResult "+ALUResult.evaluate());
		System.out.println("Zero "+Zero.evaluate());
		System.out.println("ADDResult "+AddResult.evaluate());
	}
	public void setMux12Output(){
		int[] mux12_port0 = getIBuffSeg(1).getArray();//rt
		int[] mux12_port1 = getIBuffSeg(0).getArray();//rd
		int RegDst1 = getMem().getControlVector().getArray()[0];
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
			System.out.println("BAD CONTROL VECTOR");
			throw new RuntimeException();		
		}
	}
	public void setMux11Output(){
		int[] mux11_port0 = getIBuffSeg(3).getArray();//Make these into integers?
		int[] mux11_port1 = getIBuffSeg(2).getArray();
		int[] alu_input1;
		int ALUSRC=getMem().getControlVector().getArray()[3];
		if(ALUSRC == 1){
			alu_input1=mux11_port1;
		}
		else if(ALUSRC == 0){
			alu_input1=mux11_port0;
		}
		else{
			System.out.println("BAD CONTROL VECTOR");
			throw new RuntimeException();
		}
	}
}

