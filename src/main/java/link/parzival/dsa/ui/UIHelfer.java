/**
 * 
 */
package link.parzival.dsa.ui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.filechooser.FileFilter;

/**
 * @author Mario Enrico Ragucci
 *
 */
public class UIHelfer {

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
     * @param pathToFont the path in the jar file where the Font can be found
     * @return the Font object, or null
     */
    public static Font getFontFromResource(String pathToFont) {
        Font font      = null;
        InputStream is = null;
        try {
            is   = UIHelfer.class.getResourceAsStream(pathToFont);
            font = Font.createFont(Font.TRUETYPE_FONT,is);          
        } catch (IOException e) {
             System.err.println(e.getMessage());
        } catch (FontFormatException e1) {
            e1.printStackTrace();
        } finally {
            if(is != null) {
                try {
                    is.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        
        return font;
    }

}
