/**
 * 
 */
package link.parzival.dsa;

import java.util.Random;

/**
 * @author Mario Enrico Ragucci
 *
 */
public class Wuerfel {
    
    /**
     * @author Mario Enrico Ragucci
     *
     */
    public enum WuerfelTyp {
        D6,D10,D20,D100
    }

    private static final int startWert = 1;
    private int hoechsterWert;
    
    private Random random;
    
    /**
     * Konstruktor des Würfel Objektes
     * @param hoechsterWert der höchste Wert, den der Würfel erwürfeln kann
     */
    public Wuerfel(int hoechsterWert) {
        setHoechsterWert(hoechsterWert);
        this.random = new Random();
    }
    
    /**
     * Konstruktor mit vordefinierten Würfeltypen
     * @param wuerfelTyp der Würfeltyp, der verwendet werden soll
     */
    public Wuerfel(WuerfelTyp wuerfelTyp) {
        this(0);
        switch(wuerfelTyp) {
        case D6  : setHoechsterWert(6);   break;
        case D10 : setHoechsterWert(10);  break;
        case D20 : setHoechsterWert(20);  break;
        case D100: setHoechsterWert(100); break;            
        }
    }

    /**
     * @return der Höchstwert, der vom Würfel erwürfelt werden kann
     */
    public int getHoechsterWert() {
        return hoechsterWert;
    }

    /**
     * @param hoechsterWert der höchste Wert, den der Würfel erwürfeln kann
     */
    public void setHoechsterWert(int hoechsterWert) {
        this.hoechsterWert = hoechsterWert;
    }
    
    public int wuerfeln() {
        /* 
         * Random.nextInt(int max) gibt die Range 0- (max-1) zurück.
         * Entsprechend addieren wir am Ende unseren Minimalwert
         */
        int wurf = (
                this.random.nextInt(getHoechsterWert()) + Wuerfel.startWert
        );
        
        return wurf;
    }
}
