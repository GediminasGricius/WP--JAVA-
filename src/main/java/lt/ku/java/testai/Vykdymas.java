package lt.ku.java.testai;

public class Vykdymas {
	public static Vykdymas vykdymas=null;
	public int sk=0;
	
	public static int padidinkIrGauk() {
		if (vykdymas==null) {
			vykdymas=new Vykdymas();
		}
		vykdymas.sk++ ;
		return vykdymas.sk;
	}

}
