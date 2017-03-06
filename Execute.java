import java.lang.reflect.Array;
import java.util.Arrays;


public class Execute extends Stage {
	public Execute(Firmware m){
		super(m);
		this.inbuff_size=6;//rd|rt|ext_offset|readdata2|readdata1|pc
		this.outbuff_size=5;
		createBuffers();
	}
	public void execute(){
		//MUX12
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
		//MUX11
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
		Bin Offset = getIBuffSeg(2);
		Bin PC = getIBuffSeg(5);
		int offset = Offset.evaluate();
		int pc = PC.evaluate();
		int addresult=pc+4*offset;
		//Construct bins for buffer
		//TODO Make all of these Bins accessible for printing. Make them into class variables.
		Bin AddResult = new Bin(addresult,0);
		Bin Zero = new Bin(zero);
		Bin ALUResult=new Bin(aluresult,0);
		Bin Readdata2 = getIBuffSeg(3);
		Bin Mux12 = new Bin(mux12_output);
		System.out.println("Mux12 "+Mux12.evaluate());
		System.out.println("Read data2 "+alu1.evaluate());
		System.out.println("ALUResult "+ALUResult.evaluate());
		System.out.println("Zero "+Zero.evaluate());
		System.out.println("ADDResult "+AddResult.evaluate());
		loadBuffer(4,AddResult);
		loadBuffer(3,Zero);
		loadBuffer(2,ALUResult);
		loadBuffer(1,Readdata2);
		loadBuffer(0,Mux12);
	}
}
