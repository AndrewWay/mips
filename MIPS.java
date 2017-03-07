import java.util.*;


public class MIPS {

	
	public static void main(String[]args){
		Scanner in = new Scanner(System.in);
		
		//Hardware specification parameters
		int register_size=32;
		int register_quantity=30;
		int instr_mem_num=1;
		

		
		//Create memory
		Firmware mem = new Firmware(register_size,register_quantity,instr_mem_num);
		
		System.out.println("Please input initial register values R1 - R30 (format R1 <value>)");
		System.out.println("Input null field when finished");
		String input;
		while(!(input = in.nextLine()).isEmpty()) {
			String[] parse = input.split(" ");
			int regNum = Integer.parseInt(parse[0].substring(1))-1;
			mem.overwrite_register(regNum, Integer.parseInt(parse[1]));
		}
		
		//Create the stages
		Fetch IF = new Fetch(mem,Bin.dec_toBin(0));
		Decode ID = new Decode(mem);
		Execute EX = new Execute(mem);
		Memory MEM = new Memory(mem);
		Writeback WB = new Writeback(mem);
		
		//System.out.println("Please input data memory in format <address> <value>");
		//System.out.println("Input null field when finished");
		//String line;
		//while(!(line = in.nextLine()).isEmpty()){
		//	String[] parse = line.split(" ");
			
		//}
		
		mem.disp_registers();
		//mem.randomize_register(1);
		//mem.getRegister(2).dec_overwrite(10);
		//mem.getRegister(3).dec_overwrite(6);
		//mem.disp_registers();

		IF.fetch();
		ID.loadBuffer(IF.getOutputBuffer());
		ID.decode();
		EX.loadBuffer(ID.getOutputBuffer());
		EX.execute();
		MEM.loadBuffer(EX.getOutputBuffer());
		MEM.memory();
		WB.loadBuffer(MEM.getOutputBuffer());
		WB.writeback();
	}
}
