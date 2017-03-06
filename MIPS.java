
public class MIPS {
	public static void main(String[]args){
		//Hardware specification parameters
		int register_size=4;
		int register_quantity=5;
		int instr_mem_num=1;
		
		//Create memory
		Memory mem = new Memory(register_size,register_quantity,instr_mem_num);
		//Create the stages
		Fetch IF = new Fetch(mem);
		Decode ID = new Decode(mem);
		Execute EX = new Execute(mem);
		Writeback WB = new Writeback(mem);
		
		mem.disp_registers();
		mem.randomize_register(2);
		mem.randomize_register(1);
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
	}
}
