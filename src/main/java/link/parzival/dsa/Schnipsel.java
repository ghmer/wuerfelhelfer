/**
 * 
 */
package link.parzival.dsa;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author mario
 *
 */
public class Schnipsel {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String zahl1 = "10";
		String zahl2 = "-8";
		
		int zahl1Int = Integer.parseInt(zahl1);
		int zahl2Int = Integer.parseInt(zahl2);
		
		System.out.println(zahl1Int);
		System.out.println(zahl2Int);
		System.out.println(zahl1Int + zahl2Int);
		System.out.println(zahl1Int - zahl2Int);
	}

}
