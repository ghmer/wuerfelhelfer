/**
 * 
 */
package link.parzival.dsa;

/**
 * @author Mario Enrico Ragucci
 *
 */
public class Konstanten {
    /**
     * URL zur aktuellen Version des Würfelhelfers
     */
    public static final String APP_URL          = "https://parzival.link/dz-dice-helper-latest.jar";
    /**
     * URL zur aktuellen Betriebsanleitung des Würfelhelfers
     */
    public static final String MANUAL_URL       = "https://parzival.link/Wuerfelhelfer-Anleitung.pdf";
    /**
     * URL zur Versionsprüfung des Würfelhelfers
     */
    public static final String VERSION_URL      = "https://parzival.link/dz-helper-version.txt";
    /**
     * Nach außen angezeigte Version, matched mit der Version aus der pom.xml
     */
    public static final String VERSION_EXTERNAL = "0.7.1";
    /**
     * Intern verwendete Version, z.B. für Abfrage, ob eine neuere Version vorliegt
     */
    public static final int VERSION             = 21;
    /**
     * Status, der von Modal-Dialogen zurückgegeben wird, falls der Cancel Button gedrückt wurde
     */
    public static final int DIALOG_CANCEL_STATE = 1;
    /**
     * Status, der von Modal-Dialogen zurückgegeben wird, falls der OK Button gedrückt wurde
     */
    public static final int DIALOG_OK_STATE     = 0;    
}
