/**
 * 
 */
package link.parzival.dsa;

/**
 * @author mario
 *
 */
public class Regelwerk {
    
    /**
     * Berechnet einen Basiswert anhand der Drei Eigenschaftswerte und einem Divisor
     * @param wert1 Der erste Eigenschaftswert
     * @param wert2 Der zweite Eigenschaftswert
     * @param wert3 Der dritte Eigenschaftswert
     * @param divisor Der Divisor, mit dem geteilt werden soll
     * @return der berechnete Basiswert als Integer
     */
    public static int berechneBasiswert(int wert1, int wert2, int wert3, int divisor) {
        int result = 0;
        
        double berechnung = (
                (wert1 * 1.0) +
                (wert2 * 1.0) +
                (wert3 * 1.0)
                ) / divisor;
        
        result = Math.toIntExact(Math.round(berechnung));
        
        return result;
    }
    
    /**
     * Berechnet die Basis Lebenspunkte eines Charakters
     * @param konstitution Die Konstitution des Charakters
     * @param koerperkraft Die Körperkraft des Charakters
     * @return der berechnete Basiswert als Integer
     */
    public static int berechneBasisLebenspunkte(int konstitution, int koerperkraft) {
        int result = 0;
        
        result = berechneBasiswert(konstitution, konstitution, koerperkraft, 2);
        
        return result;
    }
    
    /**
     * @param mut Der Mut des Charakters
     * @param konstitution Die Konstitution des Charakters
     * @param gewandtheit Die Gewandtheit des Charakters
     * @return der berechnete Basiswert als Integer
     */
    public static int berechneBasisAusdauer(int mut, int konstitution, int gewandtheit) {
        int result = 0;
        
        result = berechneBasiswert(mut, konstitution, gewandtheit, 2);
        
        return result;
    }
    
    /**
     * @param mut Der Mut des Charakters
     * @param intuition Die Intuition des Charakters
     * @param charisma Das Charisma des Charakters
     * @return der berechnete Basiswert als Integer
     */
    public static int berechneBasisAstralenergie(int mut, int intuition, int charisma) {
        int result = 0;
        
        result = berechneBasiswert(mut, intuition, charisma, 2);
        
        return result;
    }
    
    /**
     * @param mut Der Mut des Charakters
     * @param klugheit Die Klugkeit des Charakters
     * @param konstitution Die Konstitution des Charakters
     * @return der berechnete Basiswert als Integer
     */
    public static int berechneBasisMagieresistenz(int mut, int klugheit, int konstitution) {
        int result = 0;
        
        result = berechneBasiswert(mut, klugheit, konstitution, 5);
        
        return result;
    }
    
    /**
     * @param mut Der Mut des Charakters
     * @param gewandtheit Die Gewandtheit des Charakters
     * @param koerperkraft Die Körperkraft des Charakters
     * @return der berechnete Basiswert als Integer
     */
    public static int berechneBasisAttacke(int mut, int gewandtheit, int koerperkraft) {
        int result = 0;
        
        result = berechneBasiswert(mut, gewandtheit, koerperkraft, 5);
        
        return result;
    }
    
    /**
     * @param intuition Die Intuition des Charakters
     * @param gewandtheit Die Gewandtheit des Charakters
     * @param koerperkraft Die Körperkraft des Charakters
     * @return der berechnete Basiswert als Integer
     */
    public static int berechneBasisParade(int intuition, int gewandtheit, int koerperkraft) {
        int result = 0;
        
        result = berechneBasiswert(intuition, gewandtheit, koerperkraft, 5);
        
        return result;
    }
    
    /**
     * @param intuition Die Intuition des Charakters
     * @param fingerfertigkeit Die Fingerfertigkeit des Charakters
     * @param koerperkraft Die Körperkraft des Charakters
     * @return der berechnete Basiswert als Integer
     */
    public static int berechneBasisFernkampf(int intuition, int fingerfertigkeit, int koerperkraft) {
        int result = 0;
        
        result = berechneBasiswert(intuition, fingerfertigkeit, koerperkraft, 5);
        
        return result;
    }

    /**
     * @param mut Der Mut des Charakters
     * @param intuition Die Intuition des Charakters
     * @param gewandtheit Die Gewandtheit des Charakters
     * @return der berechnete Basiswert als Integer
     */
    public static int berechneBasisInitiative(int mut, int intuition, int gewandtheit) {
        int result = 0;
        
        double berechnung = (
                (mut * 1.0) +
                (mut * 1.0) +
                (intuition   * 1.0) +
                (gewandtheit * 1.0)
                ) / 5;
        
        result = Math.toIntExact(Math.round(berechnung));
        
        return result;
    }
    
    public static void main(String[] args) {
        System.out.println(Regelwerk.berechneBasisLebenspunkte(12, 12));
    }
}
