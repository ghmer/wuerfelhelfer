/**
 * 
 */
package link.parzival.dsa;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author mario
 *
 */
public class Wuerfel {
	
	public enum WuerfelTyp {
		D6,D10,D20,D100
	}

	private static final int startWert = 1;
	private int hoechsterWert;
	
	private Random random;
	
	public Wuerfel(WuerfelTyp wuerfelTyp) {
		this(0);
		switch(wuerfelTyp) {
		case D6  : setHoechsterWert(6);   break;
		case D10 : setHoechsterWert(10);  break;
		case D20 : setHoechsterWert(20);  break;
		case D100: setHoechsterWert(100); break;			
		}
	}
	
	public Wuerfel(int hoechsterWert) {
		setHoechsterWert(hoechsterWert);
		this.random = new Random();
	}

	/**
	 * @return the hoechsterWert
	 */
	public int getHoechsterWert() {
		return hoechsterWert;
	}

	/**
	 * @param hoechsterWert the hoechsterWert to set
	 */
	public void setHoechsterWert(int hoechsterWert) {
		this.hoechsterWert = hoechsterWert;
	}
	
	public int wuerfeln() {
		// Random.nextInt(int max) gibt die Range 0- (max-1) zurück.
		// entsprechend addieren wir am Ende unseren Minimalwert
		int wurf = (
				this.random.nextInt(getHoechsterWert()) + Wuerfel.startWert
		);
		
		return wurf;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Wuerfel wuerfel = new Wuerfel(WuerfelTyp.D20);
		
		Map<Integer, Integer> map = new HashMap<>();
		for(int i = 0; i < 9999999; i++){
			int wurf = wuerfel.wuerfeln();
			
			if(!map.containsKey(wurf)) {
				map.put(wurf, 0);
			}
			
			map.put(wurf, (map.get(wurf) + 1));
		}

		for(Map.Entry<Integer, Integer> entry : map.entrySet()) {
			System.out.println(String.format("%s: %s", entry.getKey(), entry.getValue()));
		}
	}

}
