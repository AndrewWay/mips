import java.util.*;

public class Fetch extends Stage{

	private Bin ir, PC;
	private Mux MUX3;
	private Scanner in;
	private String instruction;
	
 	public Fetch(Firmware m, int[] pval) {
		super(m);
		ir=new Bin(getMem().getInstSize());
		PC=new Bin(32);
		MUX3=new Mux();
		MUX3.setPort0(PC.getArray());
		this.inbuff_size=1;
		this.outbuff_size=2;
		createBuffers();
		in=new Scanner(System.in);
	}
	public void fetch(){
		PC.overwrite(MUX3.getOutput()); //Get the PC register value
		
		PC.overwrite(Bin.dec_toBin(PC.evaluate()+4));
		MUX3.setPort0(PC.getArray()); 		//Increment PC					
		
		//Obtain the instruction located at PC
		Firmware m = getMem();
		//int[] instruction = m.getInstMem()[PC.evaluate()];
		
		//Instead of fetching our instructions from an instruction memory, the user inputs instructions one by one.
		System.out.print("Next instruction: ");
		instruction = in.nextLine();
		parseInput(instruction);
		
		
		//Load that instruction into buffer
		
		loadBuffer(0,ir);
		loadBuffer(1,PC);
	}
	public Bin getIR(){
		return ir;
	}
	
	private void parseInput(String instr){
		String[] parse = instr.split(" ");
		Bin op = new Bin(6);
		Bin rs = new Bin(5);
		Bin rt = new Bin(5);
		
		if (parse[0].equalsIgnoreCase("add")) {
			Bin funct = new Bin(6);
			Bin rd = new Bin(5);
			Bin shamt = new Bin(5);
			
			op.dec_overwrite(0);
			rs.dec_overwrite(Integer.parseInt(parse[2]));
			rt.dec_overwrite(Integer.parseInt(parse[3]));
			rd.dec_overwrite(Integer.parseInt(parse[1]));
			shamt.dec_overwrite(0);
			funct.dec_overwrite(32);
			
			ir.overwrite_section(0, op);
			ir.overwrite_section(6, rs);
			ir.overwrite_section(11, rt);
			ir.overwrite_section(16, rd);
			ir.overwrite_section(21, shamt);
			ir.overwrite_section(26, funct);
			
			System.out.println("Semantics: ");
			System.out.println("R"+rd.evaluate()+" <- R"+rs.evaluate()+"+ R"+rt.evaluate());
		}
		else if (parse[0].equalsIgnoreCase("sub")) {
			Bin funct = new Bin(6);
			Bin rd = new Bin(5);
			Bin shamt = new Bin(5);
			
			op.dec_overwrite(0);
			rs.dec_overwrite(Integer.parseInt(parse[2]));
			rt.dec_overwrite(Integer.parseInt(parse[3]));
			rd.dec_overwrite(Integer.parseInt(parse[1]));
			shamt.dec_overwrite(0);
			funct.dec_overwrite(34);
			
			ir.overwrite_section(0, op);
			ir.overwrite_section(6, rs);
			ir.overwrite_section(11, rt);
			ir.overwrite_section(16, rd);
			ir.overwrite_section(21, shamt);
			ir.overwrite_section(26, funct);
			
			System.out.println("Semantics: ");
			System.out.println("R"+rd.evaluate()+" <- R"+rs.evaluate()+"- R"+rt.evaluate());
		}
		else if (parse[0].equalsIgnoreCase("lw")) {
			Bin imm = new Bin(16);
			
			op.dec_overwrite(35);
			rs.dec_overwrite(Integer.parseInt(parse[2]));
			rt.dec_overwrite(Integer.parseInt(parse[1]));
			imm.dec_overwrite(Integer.parseInt(parse[3]));
			
			ir.overwrite_section(0, op);
			ir.overwrite_section(6, rs);
			ir.overwrite_section(11, rt);
			ir.overwrite_section(16, imm);
			
			System.out.println("Semantics: ");
			System.out.println("R"+rt.evaluate()+" <- Mem[R"+rs.evaluate()+" + "+imm.evaluate()+"]");
		}
		else if (parse[0].equalsIgnoreCase("sw")) {
			Bin imm = new Bin(16);
			
			op.dec_overwrite(43);
			rs.dec_overwrite(Integer.parseInt(parse[2]));
			rt.dec_overwrite(Integer.parseInt(parse[1]));
			imm.dec_overwrite(Integer.parseInt(parse[3]));

			
			ir.overwrite_section(0, op);
			ir.overwrite_section(6, rs);
			ir.overwrite_section(11, rt);
			ir.overwrite_section(16, imm);
			
			System.out.println("Semantics: ");
			System.out.println("Mem[R"+rs.evaluate()+" + "+imm.evaluate()+"] <- R" + rt.evaluate());
		}
		else if (parse[0].equalsIgnoreCase("beq")) {
			Bin imm = new Bin(16);
			
			op.dec_overwrite(4);
			rs.dec_overwrite(Integer.parseInt(parse[2]));
			rt.dec_overwrite(Integer.parseInt(parse[1]));
			imm.dec_overwrite(Integer.parseInt(parse[3]));
			
			ir.overwrite_section(0, op);
			ir.overwrite_section(6, rs);
			ir.overwrite_section(11, rt);
			ir.overwrite_section(16, imm);
			
			System.out.println("Semantics: ");
			System.out.println("IF R"+rt.evaluate()+" == R"+rs.evaluate()+": Go To PC+4*"+imm.evaluate());
		}
		else {
			System.out.println("Incorrect instruction or instruction format.");
		}
		
		
	}
}
