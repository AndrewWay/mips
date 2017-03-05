
public class MIPS {
	public static void main(String[]args){
		//Hardware specification parameters
		int register_size=4;
		int register_quantity=5;
		int instr_mem_size=10;
		int instr_mem_num=10;
		//TODO Hardcode the buffer sizes into the stages
		int if_ibs=10;
		int if_obs=10;
		int id_ibs=10;
		int id_obs=10;
		int ex_ibs=10;
		int ex_obs=10;
		int wb_ibs=10;
		int wb_obs=10;
		
		//Create memory
		Memory mem = new Memory(register_size,register_quantity,instr_mem_size,instr_mem_num);
		mem.disp_registers();
		mem.randomize_register(2);
		mem.disp_registers();
		//Create the stages
		Fetch IF = new Fetch(if_ibs,if_obs,mem);
		Decode ID = new Decode(id_ibs,id_obs,mem);
		Execute EX = new Execute(ex_ibs,ex_obs,mem);
		Writeback WB = new Writeback(wb_ibs,wb_obs,mem);
		
		
	}
}
