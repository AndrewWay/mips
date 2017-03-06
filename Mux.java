
public class Mux {
	private Bin port0;
	private Bin port1;
	private int sel;
	public Mux(){
		sel=0; //Assume that the natural state of a multiplexer is 0.
		port0=new Bin(32);
		port1=new Bin(32);
	}

	public void setPort0(int[] bval){
		port0.overwrite(bval);
	}
	public void setPort1(int[] bval){
		port1.overwrite(bval);
	}
	public void setSelect(int val) {
		sel=val;
	}
	public Bin getPort0(){
		return port0;
	}
	public Bin getPort1(){
		return port1;
	}
	public int[] getOutput(){
		if (sel == 1) {
			sel=0;
			return port1.getArray();
		}
		else {
			return port0.getArray();
		}
	}
}
