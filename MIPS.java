
public class MIPS {
	public static void main(String[]args){
		//Hardware specification parameters
		int register_size=5;
		int register_quantity=5;
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
		System.out.println("IF BUFFERS");
		IF.disp_buffers();
		System.out.println("ID BUFFERS");
		ID.disp_buffers();
		System.out.println("RUNNING THROUGH FETCH");
		IF.fetch();
		ID.loadBuffer(IF.getOutputBuffer());
		System.out.println("IF BUFFERS");
		IF.disp_buffers();
		System.out.println("ID BUFFERS");
		ID.disp_buffers();
		ID.decode();
		System.out.println("ID BUFFERS");
		ID.disp_buffers();
		EX.loadBuffer(ID.getOutputBuffer());
		System.out.println("EX BUFFERS");
		EX.disp_buffers();
		System.out.println("RUNNING THROUGH EXECUTE");
		EX.execute();
		EX.disp_buffers();
		MEM.loadBuffer(EX.getOutputBuffer());
		System.out.println("MEM BUFFERS");
		MEM.disp_buffers();
		System.out.println("RUNNING THROUGH MEMORY");
		MEM.memory();
		MEM.disp_buffers();
		WB.loadBuffer(MEM.getOutputBuffer());
		System.out.println("WB BUFFERS");
		WB.disp_buffers();
		WB.writeback();
		//Get write register
		int WriteData = WB.getMux14Output();
		int WriteRegister = WB.getMux12Output();
		System.out.println("WRITEDATA "+WriteData);
		System.out.println("WRITEREG "+WriteRegister);
		mem.overwrite_register(WriteRegister,WriteData);
		
		
		add(1,2,3);
	}
	public static void add(int rs,int rt, int rd){
		Bin instruction = new Bin(32);
		Bin OP = new Bin(6);
		Bin RS = new Bin(5);
		Bin RT = new Bin(5);
		Bin RD = new Bin(5);
		Bin SHAMT = new Bin(5);
		Bin FUNCT = new Bin(6);
		
		OP.dec_overwrite(0);
		System.out.println("RS"+rs);
		RS.dec_overwrite(rs);
		System.out.println(RS.disp());
		RT.dec_overwrite(rt);
		RD.dec_overwrite(rd);
		SHAMT.dec_overwrite(0);
		FUNCT.dec_overwrite(32);
		
		instruction.overwrite_section(0,OP);
		instruction.overwrite_section(6,RS);
		instruction.overwrite_section(11,RT);
		instruction.overwrite_section(16,RD);
		instruction.overwrite_section(21,SHAMT);
		instruction.overwrite_section(26, FUNCT);
		System.out.println("Your instruction is :"+instruction.disp());
		System.out.printf("OP %d RS %d RT %d RD %d SHAMT %d FUNCT %d\n", OP.evaluate(),RS.evaluate(),RT.evaluate(),RD.evaluate(),SHAMT.evaluate(),FUNCT.evaluate());
		
	}
}
