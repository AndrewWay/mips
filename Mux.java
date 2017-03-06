
public class Mux {
	private Bin port0;
	private Bin port1;
	public Mux(){
		
	}
	public void initializePorts(){
		port0=new Bin(0);
	}
	public void setPort0(int[] bval){
		port0.overwrite(bval);
	}
	public void setPort1(int[] bval){
		port1.overwrite(bval);
	}
	public Bin getPort0(){
		return port0;
	}
	public Bin getPort1(){
		return port1;
	}
}
