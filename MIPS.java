
public class MIPS {
	public static void main(String[]args){
		//Hardware specification parameters
		int register_size=4;
		int register_quantity=5;
		int instr_mem_num=1;
		
		//Create memory
		Firmware mem = new Firmware(register_size,register_quantity,instr_mem_num);
		//Create the stages
		Fetch IF = new Fetch(mem);
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
		IF.fetch(mem.getPC());
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
	}
}
