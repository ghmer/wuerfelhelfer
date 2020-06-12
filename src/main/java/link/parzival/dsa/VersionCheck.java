/**
 * 
 */
package link.parzival.dsa;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * @author Mario Enrico Ragucci
 *
 */
public class VersionCheck {
	
	public static final Logger _LOG = Logger.getLogger(VersionCheck.class.getName());
	
    /**
     * @param localVersion die lokale Versions des Programms
     * @return true wenn es eine neue Version zum Download gibt
     */
    public static boolean checkForNewVersion(int localVersion) {
        boolean result          = false;
        int remoteVersion       = -1;
        BufferedInputStream bis = null;
        try {
            URL url = new URL(Konstanten.VERSION_URL);
            bis     = new BufferedInputStream(url.openStream());
            
            byte dataBuffer[]   = new byte[1024];
            String buffer       = new String();
            @SuppressWarnings("unused")
            int bytesRead;
            while ((bytesRead   = bis.read(dataBuffer, 0, 1024)) != -1) {
                String s        = new String(dataBuffer);
                buffer          = buffer.concat(s);
            }
            
            if(buffer != null && !buffer.isEmpty()) {
                remoteVersion = Integer.parseInt(buffer.trim());
            }
            
        } catch (MalformedURLException e) {
            remoteVersion = -1;
        } catch (IOException e) {
            remoteVersion = -1;
            _LOG.severe("File could not be found");
        } finally {
            if(bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    _LOG.severe(e.getMessage());
                }
            }
        }
        
        if(remoteVersion != -1 && localVersion < remoteVersion) {
            result = true;
        }
        
        return result;
    }
}
