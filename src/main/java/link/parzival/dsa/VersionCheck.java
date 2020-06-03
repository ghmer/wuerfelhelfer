/**
 * 
 */
package link.parzival.dsa;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author mario
 *
 */
public class VersionCheck {
    /**
     * @param localVersion the localVersion of the program
     * @return true if there is a newer version on the remote site
     */
    public static boolean checkForNewVersion(int localVersion) {
        boolean result          = false;
        int remoteVersion       = -1;
        BufferedInputStream bis = null;
        try {
            URL url = new URL(Constants.VERSION_URL);
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
            System.err.println("File could not be found");
        } finally {
            if(bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        
        if(remoteVersion != -1 && localVersion < remoteVersion) {
            result = true;
        }
        
        return result;
    }
}
