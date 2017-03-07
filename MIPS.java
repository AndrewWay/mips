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
			int regNum = Integer.parseInt(parse[0].substring(1));
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
		
		//mem.disp_registers();
		//mem.randomize_register(1);
		//mem.getRegister(2).dec_overwrite(10);
		//mem.getRegister(3).dec_overwrite(6);
		//mem.disp_registers();

		while(true) {
			IF.fetch();
			ID.loadBuffer(IF.getOutputBuffer());
			ID.decode();

			EX.loadBuffer(ID.getOutputBuffer());
			EX.execute();

			MEM.loadBuffer(EX.getOutputBuffer());
			MEM.memory();

			System.out.println();
			WB.loadBuffer(MEM.getOutputBuffer());
			WB.writeback();
			
			//Write output options and give option to break
			
			while(true) {
				System.out.println("Please select option to continue: (Select number)");
				System.out.print("	1. Display Full Trace.\n	2. Display Memory Registers\n	3. Display Memory Buffers\n	4. Input next instruction\n	5.Restart\n");
				
				String line = in.nextLine();
				
				int c = Integer.parseInt(line);
				if(c==1) {
					//TODO
				}
				else if(c==2){
					mem.disp_registers();
				}
				else if(c==3){
					System.out.println("IF/ID Input Buffer: ");
					System.out.print("**********************************************************\n*");
					System.out.println("Format: IR | PC+4");
					System.out.print("Binary: ");
					for (int i=0; i<ID.getInputBufferSize();i++) {
						System.out.print(ID.getInputBuffer()[i].disp());
					}
					System.out.print("\n Decimal: ");
					for (int i=0; i<ID.getInputBufferSize();i++) {
						System.out.print(ID.getInputBuffer()[i].dispVal());
					}
					System.out.println();
					System.out.print("**********************************************************\n");
					
					System.out.println("ID/EX Input Buffer: ");
					System.out.print("**********************************************************\n*");
					System.out.println("Format: IR[15-11] | IR[20-16] | Sign Extended IR[15-0] | ReadData2 | ReadData1 | PC+4");
					System.out.print("Binary: ");
					for (int i=0; i<EX.getInputBufferSize();i++) {
						System.out.print(EX.getInputBuffer()[i].disp());
					}
					System.out.print("\nDecimal: ");
					for (int i=0; i<EX.getInputBufferSize();i++) {
						System.out.print(EX.getInputBuffer()[i].dispVal());
					}
					System.out.println();
					System.out.print("**********************************************************\n");
					
					System.out.println("EX/MEM Input Buffer: ");
					System.out.print("**********************************************************\n*");
					System.out.println("Format: Mux12 Output | ReadData2 | ALUResult | Zero | AddResult");
					System.out.print("Binary: ");
					for (int i=0; i<MEM.getInputBufferSize();i++) {
						System.out.print(MEM.getInputBuffer()[i].disp());
					}
					System.out.print("\nDecimal: ");
					for (int i=0; i<MEM.getInputBufferSize();i++) {
						System.out.print(MEM.getInputBuffer()[i].dispVal());
					}
					System.out.println();
					System.out.print("**********************************************************\n");
					
					System.out.println("MEM/WB Input Buffer: ");
					System.out.print("**********************************************************\n*");
					System.out.println("Format: Mux12 Output | ALU Result | ReadData");
					System.out.print("Binary: ");
					for (int i=0; i<WB.getInputBufferSize();i++) {
						System.out.print(WB.getInputBuffer()[i].disp());
					}
					System.out.print("\nDecimal: ");
					for (int i=0; i<WB.getInputBufferSize();i++) {
						System.out.print(WB.getInputBuffer()[i].dispVal());
					}
					System.out.println();
					System.out.print("**********************************************************\n");
				}
				else if(c==4){
					break;
				}
				else if(c==5){
					break;
				}
				else {
					System.out.println("Please input valid integer.");
				}
			
				
			}
		}
	}
		

	
	
}
