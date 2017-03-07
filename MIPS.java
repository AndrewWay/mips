
public class MIPS {
	public static void main(String[]args){
		//Hardware specification parameters
		int register_size=32;
		int register_quantity=30;
		int instr_mem_num=1;
		
		//Create memory
		Firmware mem = new Firmware(register_size,register_quantity,instr_mem_num);
		//Create the stages
		Fetch IF = new Fetch(mem,Bin.dec_toBin(0));
		Decode ID = new Decode(mem);
		Execute EX = new Execute(mem);
		Memory MEM = new Memory(mem);
		Writeback WB = new Writeback(mem);
		
		mem.disp_registers();
		mem.randomize_register(1);
		mem.getRegister(2).dec_overwrite(10);
		mem.getRegister(3).dec_overwrite(6);
		mem.disp_registers();

		IF.fetch();
		ID.loadBuffer(IF.getOutputBuffer());
		ID.decode();
		EX.loadBuffer(ID.getOutputBuffer());
		EX.execute();
		MEM.loadBuffer(EX.getOutputBuffer());
		MEM.memory();
		WB.loadBuffer(MEM.getOutputBuffer());
		WB.writeback();
		//Get write register
		int WriteData = WB.getMux14Output();
		int WriteRegister = WB.getMux12Output();
		System.out.println("WRITEDATA "+WriteData);
		System.out.println("WRITEREG "+WriteRegister);
		mem.overwrite_register(WriteRegister,WriteData);
	}
}
