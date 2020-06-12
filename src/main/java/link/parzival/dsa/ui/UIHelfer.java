/**
 * 
 */
package link.parzival.dsa.ui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.swing.filechooser.FileFilter;

import link.parzival.dsa.object.enumeration.LizenzTypEnum;
import link.parzival.dsa.ui.dialog.AboutDialog;

/**
 * @author Mario Enrico Ragucci
 *
 */
public class UIHelfer {
    
    public static final Logger _LOG = Logger.getLogger(UIHelfer.class.getName());

    /**
     * @return a FileFilter filtering for .html files
     */
    public static FileFilter getHtmlFileFilter() {
        return new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    return f.getName().toLowerCase().endsWith(".html");
                }
            }                    
            @Override
            public String getDescription() {
                return "*.html";
            }
        };
    }
    
    /**
     * @param pathToFont der Pfad, an dem die Font-Datei gefunden werden kann
     * @return das Font-Objekt, oder null
     */
    public static Font getFontFromResource(String pathToFont) {
        Font font      = null;
        InputStream is = null;
        try {
            is   = UIHelfer.class.getResourceAsStream(pathToFont);
            font = Font.createFont(Font.TRUETYPE_FONT,is);          
        } catch (IOException e) {
             _LOG.severe(e.getMessage());
        } catch (FontFormatException e1) {
            _LOG.severe(e1.getMessage());
        } finally {
            if(is != null) {
                try {
                    is.close();
                } catch (IOException e1) {
                    _LOG.severe(e1.getMessage());
                }
            }
        }
        
        return font;
    }
    
    /**
     * @param lizenzTyp welche Lizenz soll geholt werden
     * @return der Lizenztext als String Objekt
     */
    @SuppressWarnings("resource")
    public static String getLizenz(LizenzTypEnum lizenzTyp) {
        String text = null;
        switch (lizenzTyp) {
        case Apache: {
            text = new Scanner(AboutDialog.class.getResourceAsStream("/LICENCE_Flatlaf_Apache.txt"), "UTF-8")
                    .useDelimiter("\\A").next();
            break;
        }
        case GPL: {
            text = new Scanner(AboutDialog.class.getResourceAsStream("/LICENCE_Friedolin_Font.txt"), "UTF-8")
                    .useDelimiter("\\A").next();
            break;
        }
        case MIT: {
            text = new Scanner(AboutDialog.class.getResourceAsStream("/LICENCE_MIT.txt"), "UTF-8")
                    .useDelimiter("\\A").next();
            break;
        }
        }

        return text;
    }
}
